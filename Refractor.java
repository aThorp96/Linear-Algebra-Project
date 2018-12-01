public class Refractor {

    private Lens lens;
    private Ray[] ray;

    public Refractor(Lens lens) {
        setLens(lens);
    }

    public void setLens(Lens lens) {
        this.lens = (lens != null) ? lens : null;
    }
}
