import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean add(T value) {
        Node<T> temp = new Node<>(value);
        if (head == null) {
            head = temp;
            tail = temp;
        } else {
            try {
                tail.next = temp;
                temp.prev = tail;
                tail = temp;
            } catch (NullPointerException e) {
                e.getMessage();
            }
        }
        size++;
        return true;
    }

    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> temp = new Node<>(value);
        if (index == 0) {
            if (isEmpty()) {
                head = temp;
                tail = temp;
                size++;
                return;
            } else {
                head.prev = temp;
                temp.next = head;
                head = temp;
                size++;
                return;
            }
        }
        if (index == size) {
            tail.next = temp;
            tail = temp;
            size++;
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        Node<T> oldPrevious = current.prev;
        oldPrevious.next = temp;
        current.prev = temp;
        current.prev = oldPrevious;
        temp.next = current;
        size++;
    }

    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    public T get(int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public T set(T value, int index) {
        checkIndex(index);
        Node<T> temp = new Node<>(value);
        Node<T> current = head;
        if (index == 0) {
            head.next = temp;
            temp.next = head.next;
            head = temp;
            return temp.data;
        }
        int count = 0;

        while (index != count && current != null) {
            current = current.next;
            count++;
        }
        current.next = temp;
        temp.prev = current;
        return value;
    }

    public T remove(int index) {
        checkIndex(index);
        int count = 0;
        Node<T> current = head;
        while (index != count) {
            current = current.next;
            count++;
            if (current == null) {
                return null;
            }
        }
        if (index == 0) {
            removeFirst();
            return current.data;
        }
        if (current == tail) {
            removeLast();
            return current.data;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
            return current.data;
        }
    }

    public boolean remove(T t) {
        Node<T> current = head;
        try {
            while (current.data.equals(t) != true) {
                current = current.next;
                if (current == null) {
                    return false;
                }
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
        if (current == head) {
            removeFirst();
            return true;
        }
        if (current == tail) {
            removeLast();
            return true;
        }
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return true;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void removeFirst() {
        Node<T> temp = head;
        if (head.next == null) {
            head = null;
            tail = null;
        } else {
            head.next.prev = null;
            head = head.next;
        }
        size--;
    }

    private void removeLast() {
        if (head.next == null) {
            head = null;
        } else {
            tail.prev.next = null;
            tail = tail.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data) {
            this.data = data;
        }
    }
}

