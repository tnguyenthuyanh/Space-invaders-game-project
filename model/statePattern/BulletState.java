package model.statePattern;

import model.Bullet;

public interface BulletState {

    public void goNext(Bullet context);
} 