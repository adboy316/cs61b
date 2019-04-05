public interface Deque<Item> {


    public void addFirst(Item item);
    public void addLast(Item item);
    public int size();
    public boolean isEmpty();
    public void printDeque();
    public Item removeFirst();
    public Item removeLast();
    public Item get(int index);

}