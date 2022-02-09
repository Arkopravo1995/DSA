//  METHOD 1


// In this question I tried to implement an algorithm for which
// recently used node will be at next to head
// least recently used node will be at tail prev

// for implementing this algorithm I used hashmap for maping keys with their corresponding nodes


class LRUCache {
    
    // doubly linked list having key and value
    class Node{
        Node prev;
        Node next;
        int key;
        int value;
        
        Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }
    
    // map for maping keys with their corresponding nodes
    HashMap<Integer, Node> map = new HashMap<>();
    Node head = new Node(-1, -1);
    Node tail = new Node(-1, -1);
    int capacity;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        join(head, tail);
    }
    
    private void join(Node n1, Node n2){
        n1.next = n2;
        n2.prev = n1;
    }
    
    public int get(int key) {
        if(!map.containsKey(key))
            return -1;
        
        // if the key is present in the map 
        // then that node of the corresponding key will be considered as recently used node
        // so I will remove that node from its current position and move that node to next to head;
        
        Node node = map.get(key);
        join(node.prev, node.next);     // removing node from its current position
        moveToHead(node);               // joining node next to head;
        
        return node.value;
    }
    
    private void moveToHead(Node node){
        Node nextNode = head.next;
        join(head, node);
        join(node, nextNode);
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            join(node.prev, node.next);
            moveToHead(node);
            node.value = value;
        } else{
                // if we hit the capacity then its time to remove least recently used node from the map
                // and add the new node into the map and doubly linked list;
            
            if(map.size() == capacity){
                map.remove(tail.prev.key);      // removing the key or node from the map;
                join(tail.prev.prev, tail);     // removing the node from the doubly linked list
            }
            
            // adding a new node into the map and to doubly linked list;
            Node node = new Node(key, value);
            moveToHead(node);
            map.put(key, node);
        }
    }
}

 


// METHOD 2

class LRUCache {  
  Node head = new Node(0, 0), tail = new Node(0, 0);
  Map<Integer, Node> map = new HashMap();
  int capacity;
  
  public LRUCache(int _capacity) {
    capacity = _capacity;
    head.next = tail;
    tail.prev = head;
  }

  public int get(int key) {
    if(map.containsKey(key)) {
      Node node = map.get(key);
      remove(node);
      insert(node);
      return node.value;
    } else {
      return -1;
    }
  }

  public void put(int key, int value) {
    if(map.containsKey(key)) {
      remove(map.get(key));
    }
    if(map.size() == capacity) {
      remove(tail.prev);
    }
    insert(new Node(key, value));
  }
  
  private void remove(Node node) {
    map.remove(node.key);
    node.prev.next = node.next;
    node.next.prev = node.prev;
  }
  
  private void insert(Node node){
    map.put(node.key, node);
    node.next = head.next;
    node.next.prev = node;
    head.next = node;
    node.prev = head;
  }
  
  class Node{
    Node prev, next;
    int key, value;
    Node(int _key, int _value) {
      key = _key;
      value = _value;
    }
  }
}
