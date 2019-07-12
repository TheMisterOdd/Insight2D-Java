package world;

public class Tiles {
	public static Tiles tiles[] = new Tiles[16];
	public static byte not = 0;
	
	public static final Tiles grass = new Tiles("tileset/default");//0
	public static final Tiles grass_top = new Tiles("tileset/grass_top");//1
	public static final Tiles grass_corner_left_up = new Tiles("tileset/grass_corner_left_up");//2
	public static final Tiles grass_corner_right_up = new Tiles("tileset/grass_corner_right_up");//3
    //public static final Tiles grass_corner_left_down = new Tiles("tileset/grass_corner_right_down");//3
    public static final Tiles grass_left = new Tiles("tileset/grass_left").setSolid();//4
    public static final Tiles grass_right = new Tiles("tileset/grass_right").setSolid();//5
	public static final Tiles test = new Tiles("test").setSolid();//6
	
	private byte id;
	private boolean solid;
	private String texture;
	
	public Tiles(String texture) {
		this.id = not;
		not++;
		this.texture = texture;
		this.solid = false;
		if (tiles[id] != null) throw new IllegalStateException("Tiles at ["+id+"] is already being used!");
		tiles[id] = this;
	}
	
	public Tiles setSolid() {
		this.solid = true;
		return this;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public byte getId() {
		return id;
	}
	
	public String getTexture() {
		return texture;
	}
}