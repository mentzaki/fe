/*
 * Copyright (C) 2016 yew_mentzaki
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fe.gameplay.types;

import org.fe.gameplay.types.effects.Laser;

/**
 *
 * @author yew_mentzaki
 */
public class Attack {

    public static final byte HEADLESS = 0, POINTLESS = 1, WITH_HEAD = 2;

    public int type, damage;

    public double distance;
    public int reloadTime, ammoRealoadTime, currentReloadTime;
    public int ammo, currentAmmo;

    public boolean animation;
    public boolean ready, primary;

    private int animationTime;

    public Entity target;

    public Attack(int damage,
            boolean primary, int type, double distance, int reloadTime,
            int ammoReloadTime, int ammo) {
        this.damage = damage;
        this.primary = primary;
        this.type = type;
        this.distance = distance;
        this.reloadTime = reloadTime;
        this.ammoRealoadTime = ammoReloadTime;
        this.ammo = this.currentAmmo = ammo;
    }

    public void tick(Entity e, Entity list[]) {

        if (e._own_target == -1) {
            Entity t = null;
            int maxhp = Integer.MAX_VALUE;
            for (Entity en : list) {
                int hp = (int) (((double) en.hp) / en.armor + en.shield);
                if (en.owner != e.owner && maxhp > hp && hp > 0) {
                    int d = e.dist(e.x, e.y, en.x, en.y);
                    if (d <= distance) {
                        maxhp = hp;
                        t = en;
                    }
                }
            }
            if (t != null) {
                target = t;
                if (primary) {
                    e._own_target = t.id;
                }
            }
        } else {
            target = e.world.getEntity(e._own_target);
            if (target == null || target.hp <= 0) {
                target = null;
                if (primary) {
                    e._own_target = -1;
                }
            }
        }

        if (currentReloadTime > 0) {
            currentReloadTime--;
        } else {
            ready = true;
        }
        if (type == HEADLESS) {
            if (target != null) {
                int d = e.dist(e.x, e.y, target.x, target.y);
                if (d <= distance) {
                    double a = Math.atan2(target.y - e.y, target.x - e.x);
                    if (e.way == null) {
                        e.a = a;
                        if (ready) {
                            attack(e, list, target);
                            reload();
                        }
                    } else if (e.angleToDir8(a) == e.angleToDir8(e.a)) {
                        if (ready) {
                            attack(e, list, target);
                            reload();
                        }
                    }
                } else {
                    target = null;
                    if (primary) {
                        e._own_target = -1;
                    }
                }
            }
        }
        if (type == POINTLESS) {
            if (target != null) {
                int d = e.dist(e.x, e.y, target.x, target.y);
                if (d <= distance) {
                    double a = Math.atan2(target.y - e.y, target.x - e.x);

                    if (ready) {
                        attack(e, list, target);
                        reload();
                    }
                } else {
                    target = null;
                    if (primary) {
                        e._own_target = -1;
                    }
                }
            }
        }
        if (animationTime > 0) {
            animationTime--;
            animation = true;
        } else {
            animation = false;
        }

    }

    protected void attack(Entity e, Entity list[], Entity t) {
        t.hit(damage);
    }

    private void reload() {
        currentAmmo--;
        if (currentAmmo == 0) {
            currentAmmo = ammo;
            currentReloadTime = ammoRealoadTime;
        } else {
            currentReloadTime = reloadTime;
        }
        animationTime = 8;
        ready = false;
    }

}
