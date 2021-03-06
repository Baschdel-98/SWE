package rekit.mymod.inanimates;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.inanimate.Inanimate;
import rekit.mymod.Beobachter;
import rekit.mymod.Messenger;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;

public class CannonFuse extends Inanimate{

	private Messenger messenger = null;
	
	public void setMessenger(String id) {
		messenger = Beobachter.getBeobachter().addFuse(id, this);
	}
	
	public Messenger getMessenger() {
		return messenger;
	}
	
	public CannonFuse(Vec start, String id) {
		super(start, new Vec(0.8,0.3), new RGBAColor(0, 0, 0, 255));
		setMessenger(id);
	}
	
	public CannonFuse(Vec start) {
		super(start, new Vec(0.8,0.3), new RGBAColor(0, 0, 0, 255));
	}

	
	public CannonFuse(Vec startPos, String[] options) {
		this(startPos, options[0]);
	}

	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos().sub(new Vec(0.1,0.7)), this.getSize(), "trinity/laserwall_002.png");
	}
	

	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		// Only continue if the element is hostile to the enemy
		// (meaning element is Player)
		if(messenger != null && messenger.containsBullet(element)) {
			messenger.FuseExplode(this);
			this.destroy();
		}
		if (!this.getTeam().isHostile(element.getTeam())) {
			return;
		}
		if(messenger != null) {
			messenger.FuseExplode(this);
		}
		element.addDamage(3);
		this.destroy();
	}
	
	@Override
	public Inanimate create(Vec startPos, String... options) {
		return new CannonFuse(startPos, options);
	}
}
