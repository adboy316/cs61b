public class UnionFind  {


        private int[] parent;
        private int[] size;
        private int count;

        public UnionFind(int n) {
            this.count = n;
            this.parent = new int[n];
            this.size = new int[n];

            for (int i = 0; i < n; ++i) {
                this.parent[i] = i;
                this.size[i] = 1;
            }

        }

        public int count() {
            return this.count;
        }

        public int find(int p) {
            this.validate(p);

            while (p != this.parent[p]) {
                p = this.parent[p];
            }

            return p;
        }

        private void validate(int p) {
            int n = this.parent.length;
            if (p < 0 || p >= n) {
                throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
            }
        }

        public boolean connected(int p, int q) {
            return this.find(p) == this.find(q);
        }

        public void union(int p, int q) {
            int rootP = this.find(p);
            int rootQ = this.find(q);
            if (rootP != rootQ) {
                int[] var10000;
                if (this.size[rootP] < this.size[rootQ]) {
                    this.parent[rootP] = rootQ;
                    var10000 = this.size;
                    var10000[rootQ] += this.size[rootP];
                } else {
                    this.parent[rootQ] = rootP;
                    var10000 = this.size;
                    var10000[rootP] += this.size[rootQ];
                }

                --this.count;
            }
        }

        public int size(int v1){
            int x = find(v1);

            if (size[x] == 1) {
                return 0;
            }

            return size[x];
        }



    public static void main(String[] args) {

        UnionFind test = new UnionFind(10);
        test.union(0, 1);
        test.union(0, 2);

        System.out.println(test.size(2));


    }
}
