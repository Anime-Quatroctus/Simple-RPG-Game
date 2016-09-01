package dev.anime.rpg.base.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.characters.Character;
import dev.anime.rpg.base.characters.Party;
import dev.anime.rpg.base.data.ISaveable;
import dev.anime.rpg.base.data.SaveManager;
import dev.anime.rpg.base.gfx.Assets;
import dev.anime.rpg.base.gfx.RenderUtils;
import dev.anime.rpg.base.gfx.animation.AnimationBasic;
import dev.anime.rpg.base.input.KeyManager;
import dev.anime.rpg.base.input.KeyManager.KeyConstants;

public class EntityPlayer extends EntityAlive implements ISaveable {
	
	private String[] saveTags = new String[]{"X-Pos", "Y-Pos", "Facing"};
	
	private Party currentParty;
	
	public EntityPlayer(GameHandler handler) {
		super(handler);
		SaveManager.load(this);
	}
	
	@Override
	public void tickEntity() {
		recieveInput();
		if (isMoving()) move(true);
		getWalkingAnimation().updateFrameData(facing);
		getWalkingAnimation().tickAnimation();
	}

	@Override
	public void renderEntity(Graphics g) {
		if (!isMoving()) {
			RenderUtils.drawImage(g, getStillSprite(), (int) (x - handler.getWorldCamera().getXOffset()), (int) (y - handler.getWorldCamera().getYOffset()), getWidth(), getHeight());
		} else RenderUtils.drawImage(g, getWalkingAnimation().getCurrentRenderImage(), (int) (x - handler.getWorldCamera().getXOffset()), (int) (y - handler.getWorldCamera().getYOffset()), getWidth(), getHeight());
	}

	@Override
	public Rectangle getCollisionBox() {
		if (facing == EntityFacing.DOWN || facing == EntityFacing.UP) {
			return EntityConstants.HUMANOID_COLLISION_BOXES[0];
		} else if (facing == EntityFacing.LEFT) {
			return EntityConstants.HUMANOID_COLLISION_BOXES[1];
		}
		return EntityConstants.HUMANOID_COLLISION_BOXES[2];
	}

	@Override
	public int getWidth() {
		return EntityConstants.HUMANOID_WIDTH;
	}

	@Override
	public int getHeight() {
		return EntityConstants.HUMANOID_HEIGHT;
	}
	
	@Override
	public BufferedImage[] getSprites() {
		return Assets.CHARACTER_IMAGES;
	}
	
	private AnimationBasic getWalkingAnimation() {
		if (movementAnimation == null) movementAnimation = new AnimationBasic(getSprites(), EntityConstants.NUM_WALKING_SPRITES, EntityFacing.RIGHT.getID() + (facing.getID() * 2) + 1, 250);
		return movementAnimation;
	}
	
	public Party getParty() {
		return currentParty;
	}
	
	public void setParty(Party newParty) {
		this.currentParty = newParty;
	}
	
	/** Adds a member to this players party. **/
	public boolean addMember(Character newCharacter) {
		return currentParty.addCharacter(newCharacter);
	}
	
	/** Receives movement input. **/
	private void recieveInput() {
		motionX = 0;
		motionY = 0;
		if (KeyManager.PRESSED_KEYBINDS[KeyManager.KeyConstants.UP])  if (motionY != -(movementSpeed)) motionY -= movementSpeed;
		if (KeyManager.PRESSED_KEYBINDS[KeyManager.KeyConstants.DOWN]) if (motionY != movementSpeed) motionY += movementSpeed;
		if (KeyManager.PRESSED_KEYBINDS[KeyManager.KeyConstants.LEFT]) if (motionX != -(movementSpeed)) motionX -= movementSpeed;
		if (KeyManager.PRESSED_KEYBINDS[KeyManager.KeyConstants.RIGHT]) if (motionX != (movementSpeed)) motionX += movementSpeed;
		// Resets motion if no movement key is down.
		if (!(KeyManager.PRESSED_KEYBINDS[KeyConstants.LEFT] || KeyManager.PRESSED_KEYBINDS[KeyConstants.RIGHT])) motionX = 0;
		if (!(KeyManager.PRESSED_KEYBINDS[KeyConstants.UP] || KeyManager.PRESSED_KEYBINDS[KeyConstants.DOWN])) motionY = 0;
		correctFacing();
	}
	
	@Override
	public boolean isGlobal() {
		return false;
	}

	@Override
	public String getFileName() {
		return "player";
	}
	
	@Override
	public String getFileDir() {
		return "/";
	}

	@Override
	public String[] getSaveTags() {
		return saveTags;
	}

	@Override
	public Object[] getSaveData() {
		return new Object[]{x, y, facing.getID()};
	}

	@Override
	public void setDataFromLoad(Object[] newData) {
		x = (Integer) newData[0];
		y = (Integer) newData[1];
		facing = EntityFacing.getFacingFromID((Integer) newData[2]);
	}
	
}
