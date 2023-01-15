import java.util.ArrayList;

public interface IVertex<V> {
    public V getElement();
    public V getName();
    public V getDateOfBirth();
    public V getUniversityLoca();
    public V getField();
    public V getWorkPlace();
    public ArrayList<V> getSpecialist();
    public int getPoint();
}