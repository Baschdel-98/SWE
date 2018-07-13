package rekit.mymod.enemies;

import java.util.Random;

import rekit.core.GameGrid;
import rekit.logic.gameelements.type.Enemy;
import rekit.mymod.Beobachter;
import rekit.primitives.geometry.Polygon;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

@LoadMe
public class Cannon extends Enemy implements Beobachter.Messages {
	private static final Vec SIZE_OUTER = new Vec(0.7f, 0.7f);
	private static final RGBAColor COLOR_CANNON_SCHAFT = new RGBAColor(248, 199, 101, 255);

	private int randomBorder;
	private int randomCounter;
	private Random RNG = new Random();
	private Beobachter beobachter = null;
	private boolean shoot = true;
	private int ctr = 0;

	public Cannon(Vec startPos) {
		super(startPos, new Vec(), Cannon.SIZE_OUTER);

		setRandom();
	}

	public Cannon(Vec startPos, Beobachter beobachter) {
		super(startPos, new Vec(), Cannon.SIZE_OUTER);
		setBeobachter(beobachter);
		setRandom();
	}
	
	public Beobachter getBeobachter() {
		return beobachter;
	}
	
	public void setBeobachter(Beobachter beobachter) {
		this.beobachter = beobachter;
		beobachter.addToMessage(this);
	}

	private void setRandom() {
		randomBorder = RNG.nextInt(25) + 10;
		randomCounter = 0;
	}

	@Override
	protected void innerLogicLoop() {
		// Do NOT call super.innerLogicLoop to prevent physics
		shoot();
	}

	private void shoot() {
		if(shoot) {
			if (randomCounter < randomBorder) {
				randomCounter++;
			} else {
				setRandom();
				Vec direction = getDirection();
				CannonBullet bullet = new CannonBullet(getMiddle(), direction);
				if(beobachter != null) {
					beobachter.addCannonBullet(bullet);
				}
				this.getScene().addGameElement(bullet);
			}
		}
	}

	private Vec getMiddle() {
		return this.getPos().add(new Vec(0.15, 0.1));
	}

	private Vec getDirection() {
		return getMiddle().sub(this.getScene().getPlayer().getPos()).multiply(new Vec(-1, -1));
	}

	private Polygon createPolygon() {
		Vec[] rel = new Vec[4];

		Vec start = getMiddle();

		rel[0] = new Vec(0.15, 0);
		rel[1] = new Vec(0.15, 0.75);
		rel[2] = new Vec(-0.15, 0.75);
		rel[3] = new Vec(-0.15, 0);
		Polygon pol = new Polygon(start, rel);

		Vec sub = getDirection();

		float rotate = (-1) * (float) Math.atan(sub.x / sub.y);

		return pol.rotate(rotate, start);
	}

	@Override
	public void internalRender(GameGrid f) {
		if(shoot) {
		f.drawPolygon(createPolygon(), COLOR_CANNON_SCHAFT, true);

		f.drawImage(getPos(), this.getSize(), "roboindustries/cannon_back.png");
		f.drawImage(this.getPos(), this.getSize(), "roboindustries/cannon_front.png");
		
		} else {
			Vec positionExplosion = getPos().sub(new Vec(0.5,0.5));
			if (ctr <= 15) {
				f.drawImage(positionExplosion, this.getSize(), "arcadeflavor/breakableblock_breaking_03.png");
			} else if (ctr <= 30) {
				f.drawImage(positionExplosion, this.getSize(), "arcadeflavor/breakableblock_breaking_04.png");
			} else if (ctr <= 45) {
				f.drawImage(positionExplosion, this.getSize(), "arcadeflavor/breakableblock_breaking_05.png");
			} else {
				destroy();
			}
ctr++;
		}
	}

	@Override
	public Enemy create(Vec arg0, String... arg1) {
		return new Cannon(arg0);
	}

	@Override
	public void messageDestroy() {
		shoot = false;
	}

}
