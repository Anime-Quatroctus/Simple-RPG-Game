package dev.anime.rpg.base.gfx.animation;

import java.awt.image.BufferedImage;

import dev.anime.rpg.base.entity.Entity.EntityFacing;

public class AnimationBasic {

	protected BufferedImage[] animationSprites;

	protected int frames, startFrame, lastFrame, index, animLastTime;
	
	protected long lastTime, passedTime;
	
	/** Basic animation constructor, sets index to 0. **/
	public AnimationBasic(BufferedImage[] animationSprites, int frames, int startFrame, int animLastTime) {
		this.animationSprites = animationSprites;
		this.frames = frames;
		this.startFrame = startFrame;
		this.lastFrame = startFrame + frames;
		this.animLastTime = animLastTime;
		this.index = startFrame;
	}

	public void tickAnimation() {
		passedTime += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		if (passedTime >= animLastTime) {
			if (index < lastFrame) {
				index++;
			} else {
				index = startFrame + 1;
			}
			if (index >= lastFrame) index = startFrame;
			passedTime = 0;
		}
		
	}
	
	public void updateFrameData(EntityFacing facing) {
		startFrame = (EntityFacing.RIGHT.getID() + (facing.getID() * 2) + 1);
		lastFrame = startFrame + frames;
		if (index < startFrame) index = startFrame;
		if (index >= lastFrame) index= startFrame;
	}

	public BufferedImage getCurrentRenderImage() {
		return animationSprites[index];
	}

}
