public class Refractor {

    private Lens lens;
    private Ray[] ray;

    public Refractor(Lens lens) {
        setLens(lens);
        generateRays();
    }

    public void setLens(Lens lens) {
        this.lens = (lens != null) ? lens : null;
    }

    public generateRays(double angle) {
    }
    
}
