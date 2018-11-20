// Enumerated type for lens materials.
// Each material has an associated refraction index.
//
// Indexes accessable at https://en.wikipedia.org/wiki/List_of_refractive_indices
public enum Material
{
    ICE     (1.31),
    GLASS   (1.58),
    ACRYLIC (1.491),
    DIAMOND (2.42);

    private final double refractionIndex;

    Material(double rID) {
        refractionIndex = rID;
    }

    double getRefractionIndex() {
        return refractionIndex;
    }
}
