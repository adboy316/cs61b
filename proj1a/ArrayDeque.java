/* Invariants:
    addLast: The next item we want to add will go into position size.
    getLast: The item we want to return is in position size -1.
    size: The number of items in the list should be size.
    nextFirst: The nextFirst item in the array
    nextLast: The nextLast item in the array
 */

import static java.lang.StrictMath.floorMod;

public class ArrayDeque <Item> {


    private Item[] items;
    public int size;
    private int nextFirst;
    private int nextLast;

    private static final int defaultSize = 8;
    private int capacity;

     //getters
    public Item[] getItems() {return items;}
    public int size() {return size;}
    public int getNextFirst() {return nextFirst;}
    public int getNextLast() {return nextLast;}
    public Item getFirstItem() {return items[floorMod(nextFirst -1,  capacity)];}
    public Item getLastItem() {return items[floorMod(nextLast + 1,  capacity)];}



    public ArrayDeque () {
        items = (Item[]) new Object[defaultSize];
        nextFirst = 1;
        nextLast = 0;
        size = 0;
        capacity = defaultSize;
    }

    public ArrayDeque(ArrayDeque other){
        nextFirst = 1;
        nextLast = 0;
        size = 0;
        capacity = defaultSize;
        items = (Item[]) new Object[defaultSize];
        for (int i = 0; i < other.size(); i++) {
            addLast((Item) other.get(i));
        }
    }

    public void addFirst (Item x) {
        if (size == capacity) {
            resize();
        }
        items[nextFirst] = x;
        if (nextFirst == nextLast) {
            nextLast = floorMod(nextLast,  capacity);
        }
        nextFirst = floorMod((nextFirst + 1),  capacity);
        size ++;

    }


    public void addLast(Item x){
        if (size == capacity) {
            resize();
        }

        items[nextLast] = x;
        if (nextFirst == nextLast) {
            nextFirst = floorMod(nextFirst,  capacity);
        }
        nextLast = floorMod((nextLast - 1),  capacity);
        size ++;
    }

    public Item removeFirst(){
        if (nextFirst == 0) {
            nextFirst = capacity ;
        }
        Item x = items[nextFirst-1];
        items[nextFirst-1] = null;
        nextFirst = floorMod((nextFirst - 1),  capacity);
        size --;
        return x;
    }

    public Item removeLast(){
        if (nextLast == capacity -1) {
            nextLast = -1 ;
        }

        Item x = items[nextLast + 1];
        items[nextLast + 1] = null;
        nextLast = floorMod((nextLast + 1),  capacity);
        size--;
        return x;
    }


    public boolean isEmpty(){
        if (size != 0) {
            return false;
        }
        return true;
    }

    public void printDeque(){
        int sizeCounter = size;
        int firstCounter;

        if (nextFirst == 0){
            firstCounter = nextFirst;
        } else {
            firstCounter = nextFirst - 1;
        }
        if (size == 0) {
            System.out.println("empty");
            return;
        } else {
            while (sizeCounter != 0) {
                if (firstCounter == -1 || items[firstCounter] == null ) {
                    firstCounter = items.length-1;
                }

                System.out.print(items[firstCounter  ] + " ");
                sizeCounter--;
                firstCounter--;
            }
            System.out.println();
        }

    }

    public Item get(int index){
        if (index > size-1 || index < 0) {
            return null;
        }
        return items[floorMod(nextLast - index, size)];
    }

    private void resize() {
        int lastItem = floorMod(nextLast + 1, capacity);
        int lastLenght = items.length;
        int firstItem = lastLenght - lastItem;
        capacity = lastLenght * 2;
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, lastItem, a, 0, firstItem);
        System.arraycopy(items, 0, a, firstItem, lastItem);
        items = a;
        nextLast = capacity-1;
        nextFirst = lastLenght;
    }


    // testing
    public static void main(String[] args) {

        ArrayDeque L = new ArrayDeque<String>();
        L.addLast("a");
        L.addLast("b");
        L.addLast("c");
        L.addLast("d");
        L.addLast("e");
        L.addFirst("j");
        L.printDeque();
        System.out.println(L.removeFirst());
        System.out.println(L.removeFirst());
        System.out.println(L.removeFirst());
        L.printDeque();
        L.addLast("f");
        System.out.println(L.removeFirst());
        L.addLast("g");
        L.addLast("h");
        L.addFirst("j");
        System.out.println(L.removeFirst());
        L.addFirst("i");
        L.addFirst("j");
        L.printDeque();
        L.addFirst("k");
        L.addFirst("l");
        L.addLast("m");
        L.addLast("n");
        L.addLast("o");
        L.addFirst("p");
//        L.addFirst("q");
//        L.addFirst("r");
//        L.addLast("s");
//        L.printDeque();
//        L.addLast("t");
//        L.addLast("u");
//        L.addLast("v");
//        L.addLast("w");
//        L.addFirst("x");
//        L.addFirst("y");
//        L.addFirst("z");
        L.printDeque();

        System.out.println(" ------------------ ");

        ArrayDeque L2 = new ArrayDeque<String>();
        L2.addLast("a");
        L2.addLast("b");
        L2.addLast("c");
        L2.addLast("d");
        L2.addLast("e");
        L2.printDeque();
        System.out.println(L2.removeLast());
        L2.printDeque();

        System.out.println(L2.removeLast());
        L2.printDeque();

        System.out.println(L2.removeLast());
        L2.printDeque();

        System.out.println(L2.removeLast());
        L2.printDeque();

        System.out.println(L2.removeLast());
        L2.printDeque();

        L2.addLast("f");
        L2.addLast("g");
        L2.addLast("h");
        L2.addFirst("i");
        L2.addFirst("j");
        L2.printDeque();
        L2.addFirst("k");
        L2.addFirst("l");

        L2.addLast("m");
        L2.addLast("n");
        L2.addLast("o");
        L2.addFirst("p");
        L2.addFirst("q");
//        L2.addFirst("r");
//        L2.addLast("s");
//        L2.printDeque();
//        L2.addLast("t");
//        L2.addLast("u");
//        L2.addLast("v");
//        L2.addLast("w");
//        L2.addFirst("x");
//        L2.addFirst("y");
//        L2.addFirst("z");
        L2.printDeque();
        L2.removeFirst();
        L2.removeFirst();
        L2.removeLast();
        L2.removeLast();
        L2.printDeque();
        System.out.println(L2.get(0));
        System.out.println(L2.get(1));
        System.out.println(L2.get(2));
        System.out.println(L2.get(3));
        System.out.println(L2.get(7));
        System.out.println(L2.get(8));
        System.out.println(L2.get(-1));

        ArrayDeque L3 = new ArrayDeque(L2);
        L2.printDeque();
        L3.printDeque();
        L3.addFirst("first");
        L2.printDeque();
        L3.printDeque();

        System.out.println(L2.size);


    }



}
