/*
 * Copyright (C) 2015 fax
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

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.fe.Main;
import org.fe.gameplay.world.World;
import org.fe.graphics.FImage;

/**
 *
 * @author fax
 */
public abstract class Entity implements Serializable, Comparable<Entity> {

    public int id, type, owner;
    public double x, y, z, a;
    public int width = 72, height = 25;
    public int _own_tx = -1, _own_ty;
    public int _own_target;
    public float speed = 1.5f;
    public transient int mileage;
    public transient boolean selected, selectable = true, stoped;
    public transient Point[] way;
    public transient int currentPoint = 0;
    public World world;
    
    public Attack attack1, attack2;
    

    public static FImage frame[] = {
        new FImage("gui/unit_frame/0"),
        new FImage("gui/unit_frame/1"),
        new FImage("gui/unit_frame/2"),
        new FImage("gui/unit_frame/3"),
        new FImage("gui/unit_frame/4"),
        new FImage("gui/unit_frame/5"),
        new FImage("gui/unit_frame/6"),
        new FImage("gui/unit_frame/7"),
        new FImage("gui/unit_frame/8"),
        new FImage("gui/unit_frame/9"),};

    public Entity() {
    }

    public void tick(Entity[] e) {
        tickMove(e);
        if(attack1 != null){
            attack1.tick(this, e);
        }
        if(attack2 != null){
            attack2.tick(this, e);
        }
    }

    public void tickMove(Entity[] e) {
        if (_own_tx == -1) {
            if (way != null && way.length > 0) {
                currentPoint = 0;
                _own_tx = way[currentPoint].x * 64 + 32;
                _own_ty = way[currentPoint].y * 64 + 32;
            }
        } else {
            a = Math.atan2(_own_ty - y, _own_tx - x) + Math.PI * 2;
            double d = Math.sqrt(Math.pow(x - _own_tx, 2)
                    + Math.pow(y - _own_ty, 2));

            int tx = (int) (_own_tx / 64);
            int ty = (int) (_own_ty / 64);
            int cx = (int) (x / 64);
            int cy = (int) (y / 64);
            stoped = false;

            for (Entity i : e) {
                int ex = (int) (i.x / 64);
                int ey = (int) (i.y / 64);

                if (i != this && ((ex == cx && ey == cy) || (ex == tx && ey == ty))) {
                    if (i._own_tx == -1) {
                        int dx = 0, dy = 0;

                        do {
                            dx = Main.RANDOM.nextInt(3) - 1;
                            dy = Main.RANDOM.nextInt(3) - 1;
                        } while ((ex + dx == tx && ey + dy == ty)
                                || (ex + dx == cx && ey + dy == cy)
                                || !world.terrain.getTile(ex + dx, ey + dy).passable);

                        i._own_tx = (ex + dx) * 64 + 32;
                        i._own_ty = (ey + dy) * 64 + 32;
                    } else if (!i.stoped) {
                        stoped = true;
                    }
                }
            }
            if (!stoped) {
                if (d > speed) {
                    x += speed * Math.cos(a);
                    y += speed * Math.sin(a);
                    mileage += speed;
                } else {
                    x = _own_tx;
                    y = _own_ty;
                    currentPoint++;
                    if (way != null && currentPoint < way.length) {
                        _own_tx = way[currentPoint].x * 64 + 32;
                        _own_ty = way[currentPoint].y * 64 + 32;
                    } else {
                        _own_tx = -1;
                        _own_ty = -1;
                        way = null;
                    }
                    mileage += d;
                }
            }
        }
    }

    public int compareTo(Entity t) {
        if (t.y < y) {
            return 1;
        } else if (t.y > y) {
            return -1;
        } else {
            return 0;
        }
    }

    public void goTo(int tx, int ty) {
        final int targetX = tx / 64,
                targetY = ty / 64;
        final int currentX = (int) (x / 64),
                currentY = (int) (y / 64);
        final boolean terr[][] = world.terrain.getMap(Chassis.TRACKS);
        class Step {

            Step parent;
            int x, y;

            public Step(List<Step> list, Step parent, int x, int y) {
                this.parent = parent;
                this.x = x;
                this.y = y;
                /*
                if (!check(0, 0)) {
                    growThere(dir(x, targetX), dir(y, targetY), list);
                }//*/
            }

            public int dir(int x1, int x2) {
                if (x1 < x2) {
                    return 1;
                } else if (x1 > x2) {
                    return -1;
                } else {
                    return 0;
                }
            }

            public boolean check(int dx, int dy) {
                return (x + dx == targetX) && (y + dy == targetY);
            }

            public void growThere(int dx, int dy, List<Step> list) {
                if (x + dx >= 0
                        && y + dy >= 0
                        && x + dx < terr.length
                        && y + dy < terr[0].length) {
                    boolean b = terr[x + dx][y + dy];
                    if (b) {
                        terr[x + dx][y + dy] = false;
                        list.add(
                                new Step(list,
                                        this,
                                        x + dx,
                                        y + dy)
                        );
                    }
                }
            }

            public boolean grow(List<Step> list) {
                if (check(0, 0)) {
                    return true;
                }
                growThere(1, 0, list);
                growThere(-1, 0, list);
                growThere(0, 1, list);
                growThere(0, -1, list);

                growThere(1, 1, list);
                growThere(1, -1, list);
                growThere(-1, 1, list);
                growThere(-1, -1, list);
                return false;
            }
        }
        List<Step> s1 = new ArrayList<Step>(), s2 = new ArrayList<Step>(), s3 = null;
        s2.add(new Step(s2, null, currentX, currentY));
        Step end = null;
        while (s2.size() > 0) {
            s3 = s1;
            s3.clear();
            s1 = s2;
            s2 = s3;
            for (Step s : s1) {
                if (s.grow(s2)) {
                    end = s;
                    s2.clear();
                    break;
                }
            }
        }

        s1.clear();
        while (end != null) {
            s1.add(end);
            end = end.parent;
        }

        int size = s1.size();
        Point[] way = new Point[size];
        for (int i = 0; i < size; i++) {
            way[i] = new Point(s1.get(size - i - 1).x, s1.get(size - i - 1).y);
        }
        this.currentPoint = 0;
        this.way = way;
    }

    public abstract void renderShadow();

    public abstract void renderBody();

    protected int angleToDir8(double a) {
        if (a > 2 * Math.PI || a < 0) {
            a -= (double) ((int) (a / 2.0 / Math.PI)) * 2 * Math.PI;
        }
        if ((a >= 0 && a <= Math.PI / 8) || (a <= Math.PI * 2 && a >= Math.PI * 15 / 8)) {
            return 0;
        }
        if (a >= Math.PI / 8 && a <= Math.PI * 3 / 8) {
            return 1;
        }
        if (a >= Math.PI * 3 / 8 && a <= Math.PI * 5 / 8) {
            return 2;
        }
        if (a >= Math.PI * 5 / 8 && a <= Math.PI * 7 / 8) {
            return 3;
        }
        if (a >= Math.PI * 7 / 8 && a <= Math.PI * 9 / 8) {
            return 4;
        }
        if (a >= Math.PI * 9 / 8 && a <= Math.PI * 11 / 8) {
            return 5;
        }
        if (a >= Math.PI * 11 / 8 && a <= Math.PI * 13 / 8) {
            return 6;
        }
        if (a >= Math.PI * 13 / 8 && a <= Math.PI * 15 / 8) {
            return 7;
        }

        return 6;
    }

    public void render() {
        if (selected) {
            frame[0].draw(x - 8, y / 2 - width / 4 - 8);
            frame[1].draw(x - 8 - width / 2, y / 2 - 8);
            frame[3].draw(x - 8 + width / 2, y / 2 - 8);
        }

        renderBody();

        if (selected) {
            frame[2].draw(x - 8, y / 2 + width / 4 - 8);
            frame[4].draw(x - 8, y / 2 - width / 4 - 8 - height);
            frame[5].draw(x - 8 - width / 2, y / 2 - 8 - height);
            frame[6].draw(x - 8, y / 2 + width / 4 - 8 - height);
            frame[7].draw(x - 8 + width / 2, y / 2 - 8 - height);
        }
    }

    public void remove() {
        world.remove(this);
    }
}
