import java.util.ArrayList;
import java.util.Comparator;



  abstract class AbstractPriorityQueue<K,V> implements PriorityQueue<K,V>{
        protected static class PQEntry<K,V> implements Entry<K,V>{
            private K k;
            private V v;
            public PQEntry(K key, V value){
                k = key;
                v= value;
            }
            public K getKey(){return k;}
            public V getValue() {return v;}
            protected void setKey(K key) {k = key;}
            protected void setValue(V value) {v = value;}
        }
        private Comparator<K> comp;
        protected AbstractPriorityQueue(Comparator<K> c){comp = c;}
        protected int compare(Entry<K,V> a,Entry<K,V> b){
            return comp.compare(a.getKey(), b.getKey());
        }
        protected boolean checkKey(K key) throws IllegalArgumentException{
            try {
                return (comp.compare(key,key)==0);
            }catch (ClassCastException e){
                throw new IllegalArgumentException("incompatible key");
            }
        }
        public boolean isEmpty() {return size()==0;}
    }
    public class heapPriorityQueue<K,V> extends AbstractPriorityQueue<K,V>{
        protected ArrayList<Entry<K,V>> heap = new ArrayList<>();
        public heapPriorityQueue(Comparator<K> comp) {super(comp);}
        protected int parent(int j) {return (j-1) /2;}
        protected int left(int j) {return j*2 +1;}
        protected int right(int j) {return j*2 +2;}
        protected boolean hasLeft(int j) {return left(j) < heap.size();}
        protected boolean hasRight(int j) {return right(j) < heap.size();}
        protected void swap(int i, int j){
            Entry<K,V> temp = heap.get(i);
            heap.set(i,heap.get(j));
            heap.set(j,temp);
        }
        protected void upheap(int j){
            while(j>0){
                int p = parent(j);
                if (compare(heap.get(j),heap.get(p))>=0) break;;
                swap(j,p);
                j = p;
            }
        }
        protected void downheap(int j){
            while (hasLeft(j)){
                int leftIndex = left(j);
                int smallChildIndex = leftIndex;
                if (hasRight(j)){
                    int rightIndex = right(j);
                    if (compare(heap.get(leftIndex),heap.get(rightIndex))> 0)
                        smallChildIndex = rightIndex;
                }
                if (compare(heap.get(smallChildIndex),heap.get(j))>=0) break;;
                swap(j,smallChildIndex);
                j = smallChildIndex;
            }
        }
        public int size(){return heap.size();}
        public Entry<K,V> min(){
            if (heap.isEmpty()) return null;
            return heap.get(0);
        }
        public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
            checkKey(key);
            Entry<K,V> newest = new PQEntry<>(key,value);
            heap.add(newest);
            upheap(heap.size()-1);
            return newest;
        }
        public Entry<K,V> removeMin(){
            if (heap.isEmpty()) return null;
            Entry<K,V> answer = heap.get(0);
            swap(0,heap.size()-1);
            heap.remove(heap.size()-1);
            downheap(0);
            return answer;
        }

    }

