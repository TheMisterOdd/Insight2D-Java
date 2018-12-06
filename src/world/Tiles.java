package world;

public class Tiles {
	public static Tiles tiles[] = new Tiles[255];
	public static byte not = 0;
	
	public static final Tiles grass = new Tiles("tiles/grass");//0
	public static final Tiles test = new Tiles("test").setSolid();//1
	public static final Tiles grass_top = new Tiles("tiles/grass_up");//2
	public static final Tiles grass_down = new Tiles("tiles/grass_down");//3
	public static final Tiles grass_left = new Tiles("tiles/grass_left");//4
	public static final Tiles grass_right = new Tiles("tiles/grass_right");//5
	public static final Tiles topleft_grass = new Tiles("tiles/grass_topleft");//6
	public static final Tiles topright_grass = new Tiles("tiles/grass_topright");//7
	public static final Tiles downleft_grass = new Tiles("tiles/grass_downleft");//8
	public static final Tiles downright_grass = new Tiles("tiles/grass_downright");//9
	public static final Tiles topleft_grass_sand = new Tiles("tiles/grasssand_topleft");//10
	public static final Tiles cut_tree = new Tiles("tiles/cut_tree_grass").setSolid();//11
	public static final Tiles tree_grass_trunk = new Tiles("tiles/tree_grass");//12
	public static final Tiles tree_grass_center = new Tiles("tiles/tree_grass_center").setSolid();//13
	public static final Tiles tree_grass_downleft = new Tiles("tiles/tree_grass_downleft");//14
	public static final Tiles tree_grass_downright = new Tiles("tiles/tree_grass_downright");//15
	public static final Tiles tree_grass_top = new Tiles("tiles/tree_grass_top").setSolid();//16
	public static final Tiles tree_grass_topleft = new Tiles("tiles/tree_grass_topleft");//17
	public static final Tiles tree_grass_topright = new Tiles("tiles/tree_grass_topright");//18
	public static final Tiles grass_sand_down = new Tiles("tiles/grasssand_down");//19
	public static final Tiles grass_sand_downleft = new Tiles("tiles/grasssand_downleft");//20
	
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