package rekit.mymod;
import java.util.ArrayList;
import java.util.List;

import rekit.logic.gameelements.GameElement;
import rekit.mymod.enemies.Cannon;
import rekit.mymod.enemies.CannonBullet;
import rekit.mymod.inanimates.CannonFuse;

public class Beobachter {
	
	private static Beobachter beobachter = null;
	private List<Messenger> messenger = new ArrayList<>();
	
	public void addBullet(String id, CannonBullet bullet) {
		getMesenger(id, true).addBullet(bullet);
	}
	
	public Messenger addCannon(String id, Cannon cannon) {
		return getMesenger(id, true).addCannon(cannon);
	}
	
	public Messenger addFuse(String id, CannonFuse fuse) {
		return getMesenger(id, true).addFuse(fuse);
	}
	
	private Messenger getMesenger(String id, boolean createNew) {
		Messenger mes = getMesenger(id);
		if(mes == null && createNew) {
			mes = new Messenger(id);
			messenger.add(mes);
		}
		return mes;
	}
	
	private Messenger getMesenger(String id) {
		for (Messenger mes : messenger) {
			if(mes.getMessage() == id) {
				return mes;
			}
		}
		return null;
	}
	
	private Beobachter() {
		
	}
	
	public static Beobachter getBeobachter() {
		if(beobachter == null) {
			beobachter = new Beobachter();
		}
		return beobachter;
	}
}
