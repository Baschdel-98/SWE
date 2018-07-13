package rekit.mymod;
import java.util.ArrayList;
import java.util.List;

import rekit.logic.gameelements.GameElement;
import rekit.mymod.enemies.Cannon;
import rekit.mymod.enemies.CannonBullet;

public class Beobachter {
	public interface Messages{
		public void messageDestroy();
	}
	
	List<Messages> toMessage = new ArrayList<>();
	List<CannonBullet> cannonBullets = new ArrayList<>();
	
	public void addCannonBullet(CannonBullet cannonBullet) {
		cannonBullets.add(cannonBullet);
		toMessage.add(cannonBullet);
	}
	
	public boolean containsCannonBullet(GameElement argCannon) {
		for (CannonBullet cannonBullet : cannonBullets) {
			if(cannonBullet == argCannon) {
				return true;
			}
		}
		return false;
	}
	
	public void addToMessage(Messages message) {
		toMessage.add(message);
	}
	
	public void sendMessage() {
		for (Messages messages : toMessage) {
			messages.messageDestroy();
		}
	}
}
