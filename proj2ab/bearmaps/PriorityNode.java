package bearmaps;

class PriorityNode<T> implements Comparable<PriorityNode> {

    private T item;
    private double priority;


    PriorityNode(T e, double p, int pos) {
        this.item = e;
        this.priority = p;

    }

    public T returnItem(){return item;};
    public void setPriority(Double p){priority = p;}



    @Override
    public int compareTo(PriorityNode o) {
        if (this.priority > o.priority) return 1;
        if (this.priority < o.priority) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        } else {
            return ((PriorityNode) o).item.equals(item);
        }
    }

    @Override
    public int hashCode() {
        return item.hashCode();
    }
}