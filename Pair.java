public class Pair<T, Q> {

    private T first;
    private Q second;

    public Pair(T first, Q second) {
        this.first = first;
        this.second = second;
    }

    public T First() {
        return first;
    }

    public Q Second() {
        return second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(Q second) {
        this.second = second;
    }

}