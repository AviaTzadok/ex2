package tergol;


public class Node {

    private int data;
    private Node next;

    public Node(int d) {
        data = d;
        next = null;
    }

    public void setNext(Node n) {
        next = n;
    }

    public Node getNext() {
        return this.next;
    }

    public int getData() {
        return data;
    }

    public String ToString() {
        return "" + this.data;
    }


    public static void main(String[] args) {
        Node node1 = new Node(0);
        Node node2 = new Node(10);
        Node node3 = new Node(20);
        Node node4 = new Node(30);
        System.out.println("node1 : " + node1.toString());
        System.out.println("node2 : " + node2.toString());
        System.out.println("node3 : " + node3.toString());
        System.out.println("node4 : " + node4.toString());
    }


    private class CircleLinkedList{
        private Node  head;
        private Node tile;
        private int size;












        }



}
