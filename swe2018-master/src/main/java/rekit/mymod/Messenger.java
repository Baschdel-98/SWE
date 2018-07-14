package rekit.mymod;

import java.util.ArrayList;
import java.util.List;

import rekit.logic.gameelements.GameElement;
import rekit.mymod.enemies.Cannon;
import rekit.mymod.enemies.CannonBullet;
import rekit.mymod.inanimates.CannonFuse;

public class Messenger {
	private String id;
	private List<CannonBullet> bullets = new ArrayList<>();
	private List<Cannon> cannons = new ArrayList<>();
	private List<CannonFuse> fuses = new ArrayList<>();
	
	public Messenger(String id) {
		this.id = id;
	}

	public String getMessage() {
		return id;
	}
	
	public void addBullet(CannonBullet bullet) {
		bullets.add(bullet);
	}
	
	public boolean containsBullet(GameElement element) {
		for (CannonBullet cannonBullet : bullets) {
			if(cannonBullet == element) {
				return true;
			}
		}
		return false;
	}
	
	public Messenger addCannon(Cannon cannon) {
		cannons.add(cannon);
		return this;
	}

	public Messenger addFuse(CannonFuse	fuse) {
		fuses.add(fuse);
		return this;
	}
	
	public void FuseExplode(CannonFuse fuse) {
		fuses.remove(fuse);
		if(fuses.size() <= 0) {
			for (CannonBullet cannonBullet : bullets) {
				cannonBullet.destroy();
			}
			for (Cannon cannon : cannons) {
				cannon.explode();
			}
		}
	}
}
