package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2ab.Point;
import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import bearmaps.proj2c.utils.Constants;
import bearmaps.proj2c.utils.Rectangle;
import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

import static bearmaps.proj2c.utils.Constants.*;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {




    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        Map<String, Object> results = new HashMap<>();
        String[][] render_grid;
        Boolean query_success = false;


        Double LonDPP = calcLonDPP(requestParams.get("lrlon"), requestParams.get("ullon"), requestParams.get("w"));
        int depth = findDepth(LonDPP);

        ArrayList<String> images = buildImageTree(depth);
        ArrayList<String> renderImages =  findIntersectionQuery(images, requestParams.get("ullon"),
                requestParams.get("lrlon"), requestParams.get("lrlat"), requestParams.get("ullat"));

        render_grid = buildRenderGrid(renderImages, requestParams.get("w"), requestParams.get("h"));

        if (render_grid == null) {
           return queryFail();
        }

        query_success = true;
        Rectangle topLeft = boundingBox(render_grid[0][0]);
        Rectangle bottomRight = boundingBox(render_grid[render_grid.length - 1]
                [render_grid[render_grid.length-1].length - 1]);

        // Add stuff to the return Map
        results.put("raster_ul_lon", topLeft.getTopLeft().getX());
        results.put("depth", depth);
        results.put("raster_lr_lon", bottomRight.getBottomRight().getX());
        results.put("raster_lr_lat", bottomRight.getBottomRight().getY());
        results.put("render_grid", render_grid);
        results.put("raster_ul_lat", topLeft.getTopLeft().getY());
        results.put("query_success", query_success);


        System.out.println(results);

        return results;

    }

    private Double calcLonDPP(Double lrlon, Double ullon, Double w) {
       return (lrlon - ullon)/w;
    }

    private String[][] buildRenderGrid(ArrayList<String> renderImages, Double w, Double h) {

        if (renderImages.isEmpty()){
            return null;
        }

        String firstImage = renderImages.get(0);

        Scanner in = new Scanner(firstImage).useDelimiter("[^0-9]+");
        in.nextInt();
        int firstX = in.nextInt();
        int firstY = in.nextInt();

        String lastImage = renderImages.get(renderImages.size()-1);
        Scanner in2 = new Scanner(lastImage).useDelimiter("[^0-9]+");
        in2.nextInt();
        int secondX = in2.nextInt();
        int secondY = in2.nextInt();

        int width = (secondX - firstX) + 1;
        int height = (secondY - firstY) + 1;

        String[][] results;

       results = new String[height][width];

        int counter = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                results[i][j] = renderImages.get(counter);
                counter++;
            }
        }
        return results;
    }

    private ArrayList<String> findIntersectionQuery(ArrayList<String> images, Double ullon,
                                                           Double lrlon, Double lrlat, Double ullat) {
        ArrayList<String> overlappingRectangles = new ArrayList<>();
        Rectangle queryBox = new Rectangle(new Point(ullon, ullat), new Point(lrlon, lrlat));

        for (String s: images) {
            Rectangle imageBox = boundingBox(s);
            if (queryBox.isOverlapping(imageBox)) {
                overlappingRectangles.add(s);
            }
        }

        return overlappingRectangles;
    }


    /**
     * Builds a tree of "filenames" of size 4^Depth. Assumes 7 is the max depth.
     * @author Ariel Delgado
     */
    private ArrayList<String> buildImageTree(int depth) {

        ArrayList<String> returnTree = new ArrayList<>();
        Double totalImages = Math.pow(4, depth);
        int position = 0;
        for (int x = 0; x < Math.sqrt(totalImages); x++) {
            for (int  y = 0; y <  Math.sqrt(totalImages); y++ ){
                String fileName = "d" + depth + "_x" + y + "_y" + x + ".png";
                returnTree.add(fileName);
                position +=1;
            }
        }
        return returnTree;
    }

    /**
     * Calculates depth, assume depth is at least 1
     * @author Ariel Delgado
     */
    private int findDepth(Double LonDPP){
        Double res =  ( ROOT_LRLON - ROOT_ULLON) / TILE_SIZE;
        int n = 0;
        Double testRes;
        while (res/Math.pow(2, n) >= LonDPP) {
            testRes = res/Math.pow(2, n);
            if (testRes <= LonDPP) {
                return n;
            }
            if (n == 7) {
                return 7;
            }
            n += 1;
        }
        return n;
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private  void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }

    /**
     * Returns bounding box of image
     * @author Ariel Delgado
     */
     public Rectangle boundingBox(Object image) {

        List<String> numbers = extractNumbersFromString((String) image); // d = 0, x =1, y =2
         Double d = Double.parseDouble(numbers.get(0)) ;
         Double x = Double.parseDouble(numbers.get(1)) ;
         Double y = Double.parseDouble(numbers.get(2)) ;

        Double lonDPP = ((ROOT_LRLON - ROOT_ULLON )/(TILE_SIZE)) / Math.pow(2, d);
        Double latDPP = ((ROOT_ULLAT - ROOT_LRLAT)/TILE_SIZE) / Math.pow(2, d) ;

         // calculate d
         Double dx = ((Math.pow(2, d) - 1) * TILE_SIZE  * lonDPP) ;
         Double dy = ((Math.pow(2, d) - 1) * TILE_SIZE * latDPP) ;
         Double LRLON = ROOT_LRLON - dx; //Moving by d changes LRLON
         Double LRLAT = ROOT_LRLAT + dy;
         Double ULLON = ROOT_ULLON;
         Double ULLAT = ROOT_ULLAT;
         // Calculate x
         Double moveX = (x * 256 * lonDPP);
         LRLON = LRLON + moveX;
         ULLON = ULLON + moveX;
         // Calculate y
         Double moveY = (y * 256* latDPP);
         LRLAT = LRLAT - moveY;
         ULLAT = ULLAT - moveY;
         // Create Points
         Point upperLeft = new Point(ULLON, ULLAT);
         Point bottomRight = new Point(LRLON, LRLAT);
         return new Rectangle(upperLeft, bottomRight);
    }

    private List<String> extractNumbersFromString(String s) {
        s = s.replaceAll("[^-?0-9]+", " ");
        List<String> result = Arrays.asList(s.trim().split(" "));
        return result;
    }

    public static void main(String[] args) {

        RasterAPIHandler test = new RasterAPIHandler();

        Double lonDPPd1 = (0.000171661376953125);
        Double lonDPPd2 = (0.0000858306884765625);

        System.out.println(test.findDepth( 0.000002682209014892578 ));

        Rectangle testRect = test.boundingBox("d2_x3_y3.png");


    }
}
