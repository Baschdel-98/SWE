package rekit.mymod.inanimates;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.inanimate.Inanimate;
import rekit.mymod.Beobachter;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;

public class CannonFuse extends Inanimate{

	private Beobachter beobachter;
	
	public void setBeobachter(Beobachter beobachter) {
		this.beobachter = beobachter;
	}
	
	public Beobachter getBeobachter() {
		return beobachter;
	}
	
	public CannonFuse(Vec start, Beobachter beobachter) {
		super(start, new Vec(0.8,0.3), new RGBAColor(0, 0, 0, 255));
		setBeobachter(beobachter);
	}
	
	public CannonFuse(Vec start) {
		super(start, new Vec(0.8,0.3), new RGBAColor(0, 0, 0, 255));
	}

	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos().sub(new Vec(0.1,0.7)), this.getSize(), "trinity/laserwall_002.png");
	}
	

	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		// Only continue if the element is hostile to the enemy
		// (meaning element is Player)
		if(beobachter.containsCannonBullet(element)) {
			beobachter.sendMessage();
			destroy();
		}
		if (!this.getTeam().isHostile(element.getTeam())) {
			return;
		}
		
		beobachter.sendMessage();
		element.addDamage(3);
		this.destroy();
	}
	
	@Override
	public Inanimate create(Vec startPos, String... options) {
		return new CannonFuse(startPos);
	}
}
