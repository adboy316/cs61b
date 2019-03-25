/** LinkedListDeque is a double ended queue */
public class LinkedListDeque<T> {

    private class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node (Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private Node sentinel;
    private int size;
    protected Node start;
    protected Node end;

    /** Creates an empty LinkedListDeque */
    public LinkedListDeque() {
        sentinel = new Node(null, (T) "??", null);
        size = 0;
    }

    /** Creates a non-empty LinkedListDeque */
    public LinkedListDeque(T x) {

        sentinel = new Node(null, (T) "??", null);

        Node newNode = new Node(null, x, null);
        newNode.next = newNode;
        newNode.prev = newNode;
        sentinel.next = newNode;
        sentinel.prev = newNode;
        start = newNode;
        end = newNode;
        size = 1;
    }

    /** Creates a deep copy of other */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null, (T) "??", null);
        size = 0;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    /** Returns the first element of the list */
     public T getFirst() {return sentinel.next.item;}
     public Node getLast() {return end;}

     public T getSecond() {return sentinel.next.next.item;}
     public T getThird() {return sentinel.next.next.next.item;}
     public T getFourth() {return sentinel.next.next.next.next.item;}



    /** Adds an item of T x to the front of the deque. */
    public void addFirst(T x) {

        Node newNode = new Node(null, x, null);

        if (start == null) {
            sentinel.next = newNode;
            sentinel.prev = newNode;
            start = newNode;
            end = newNode;
        } else {
            newNode.prev = sentinel;
            start.prev = newNode;
            newNode.next = start;
            sentinel.next =  newNode;
            start = newNode;
            end.next = newNode;
        }
        size += 1;

    }

    /** Adds an item of T x to the back of the deque. */
    public void addLast(T x) {

        Node newNode = new Node (null, x, null);
        if (start == null) {
            sentinel.next = newNode;
            sentinel.prev = newNode;
            start = newNode;
            end = newNode;

        } else {
            newNode.prev = end;
            end.next = newNode;
            start.prev = newNode;
            newNode.next = start;
            end = newNode;
        }
        size += 1;
    }
    /** Returns the sie of the deque */
    public int size() {
        return size;
    }

    /** Returns true if deque is empty, false otherwise */
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {


        Node counter = sentinel;

        if (this.size == 0) {
            System.out.print("Empty List");
            System.out.println();
            return;
        }

        while (counter != end) {
            System.out.print(counter.next.item + " ");
            counter = counter.next;
        }
        System.out.println();
    }


    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (this.size != 0) {
            T first = start.item;
            start.item = null;
            if (this.size == 1) {
                sentinel.next = null;
                sentinel.prev = null;
                start = null;
                end = null;
                size = 0;
                return first;
            }
            sentinel.next = start.next;
            start.next.prev = sentinel.next;
            start = start.next;
            size -= 1;
            return first;


        }
        return null;
    }


    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        if (this.size != 0) {
            T last = end.item;
            end.item = null;
            if (this.size == 1) {
                sentinel.next = null;
                sentinel.prev = null;
                start = null;
                end = null;
                size = 0;
                return last;
            }
            end.prev.next = start;
            start.prev = end.prev;
            end = end.prev;
            size -= 1;
            return last;
        }
        return null;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {

        Node counter = start;
        for (int i = 0; i < index; i++) {
            if (counter == null || index >= this.size) {
                return null;
            }
            counter = counter.next;
        }
        return counter.item;
    }



    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque! RECURSIVE*/
    public T getRecursive (int index) {

        if (index >= size) {
            return null;
        }

       return getRecursive(index, start);

    }
    /** Helper method for getRecursive(int index) that uses a node */
    public T getRecursive (int index, Node s) {

        if (index == 0) {
            return s.item;
        }
        return getRecursive(index-1, s.next);
    }





    // Main method for testing
    public static void main(String[] args) {

        System.out.println("Create a non empty list and print it");
        LinkedListDeque<Integer> L2  = new LinkedListDeque(3);
        L2.printDeque();
        System.out.println("Create an empty list and print it");
        LinkedListDeque<String>  L1  = new LinkedListDeque();
        L1.printDeque();

        System.out.println("Remove the first element from the non-empty list, and add it back");
        System.out.println(L2.removeFirst());
        L2.printDeque();
        L2.addFirst(3);
        L2.printDeque();

        System.out.println("Remove the last element from the non-empty list, and add it back");
        System.out.println(L2.removeLast());
        L2.printDeque();
        L2.addLast(3);
        L2.printDeque();

        System.out.println("Add more elements to the non empty list");
        L2.addFirst(2);
        L2.addLast(7);
        L2.printDeque();

        System.out.println("Add a copy of the non empty list and print the copy");
        LinkedListDeque copyL2 = new LinkedListDeque(L2);
        copyL2.printDeque();

        System.out.println("Get the 0th element, then 1th, then 2nd, and then non existing");
        System.out.println(L2.get(0));
        System.out.println(L2.get(1));
        System.out.println(L2.get(2));
        System.out.println(L2.get(3));

        System.out.println("Remove first element and print, remove last and print");
        System.out.println(L2.removeFirst());
        L2.printDeque();
        System.out.println(L2.removeLast());
        L2.printDeque();

        System.out.println("Remove first element from empty list, remove last");
        System.out.println(L1.removeFirst());
        System.out.println(L1.removeLast());

        System.out.println("Add last element to empty list, then remove");
        L1.addLast("hello");
        L1.printDeque();
        L1.removeLast();
        L1.printDeque();

        System.out.println("Add first element to empty list, then remove");
        L1.addFirst("hello");
        L1.printDeque();
        L1.removeFirst();
        L1.printDeque();

        System.out.println("Test getRecursive");
        L1.addLast("hello");
        L1.addLast("blue");
        L1.addLast("sky");
        L1.printDeque();
        System.out.println(L1.getRecursive(0));
        System.out.println(L1.getRecursive(1));
        System.out.println(L1.getRecursive(2));
        System.out.println(L1.getRecursive(3));


    }

}
