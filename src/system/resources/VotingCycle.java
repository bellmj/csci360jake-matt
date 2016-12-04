package system.resources;

import java.lang.Object;

public class VotingCycle {

    private final Node<Object> head;
    private int index;
    private int size;

    public VotingCycle() {
        this.head = new Node<>("");
        this.index = 0;
        this.size = 0;
    }

    public void add(Object thing) {
        Node newNode = new Node<>(thing);
        Node lastNode = getLast();
        lastNode.setNext(newNode);
        newNode.setPrevious(lastNode);
        size++;
    }

    public void add(int index, Object thing) {
        Node newNode = new Node<>(thing);
        if (index >= 0) {
            if (index == 0) {
                head.setNext(newNode);
                newNode.setPrevious(head);
            } else {
                Node node = head.getNext();
                for (int i = 0; i < index; i++) {
                    node = node.getNext();
                }
                node.setNext(newNode);
                newNode.setPrevious(node);
                size++;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void addAll(Node... nodes) {
        for (Node node : nodes) {
            add(node);
        }
        size += nodes.length;
    }

    public Node get(int index) {
        if (index >= 0) {
            if (!isEmpty()) {
                Node node = head.getNext();
                for (int i = 0;
                     i < index;
                     i++) {
                    node = node.getNext();
                }
                return node;
            } else {
                return null;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node getLast() {
        Node node = head;
        while (node.getNext() != null) {
            node = node.getNext();
        }
        return node;
    }

    public void clear() {
        head.setNext(null);
        size = 0;
    }

    public boolean isEmpty() {
        return head.getNext() == null;
    }

    class Node<Object> {

        private Node<Object> next;
        private Object item;
        private Node<Object> previous;

        public Node(Object item) {
            this.item = item;
        }

        public Node<Object> getPrevious() {
            return previous;
        }

        public void setPrevious(Node node) {
            this.previous = node;
        }

        public Node<Object> getNext() {
            return next;
        }

        public void setNext(Node node) {
            this.next = node;
        }

        public Object getItem() {
            return item;
        }

        public void setItem(Object item) {
            this.item = item;
        }

    }
}
