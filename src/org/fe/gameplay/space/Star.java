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
package org.fe.gameplay.space;

import java.io.Serializable;
import org.fe.Main;
import org.fe.graphics.FImage;
import org.newdawn.slick.Color;

/**
 *
 * @author fax
 */
public class Star implements Serializable {

    static float GRAVITATIONAL_CONSTANT = 50;

    static final float GALAXY_SIZE = 50000;

    static final FImage[] STARS = {
        new FImage("space/stars/a"),
        new FImage("space/stars/b"),
        new FImage("space/stars/c"),
        new FImage("space/stars/d"),
        new FImage("space/stars/e")
    };

    int name;
    double mass;
    int color;
    double x, y;
    double vx, vy;

    public Star(int name) {
        this.name = name;
        if (name == 1) {
            mass = 3;
        } else {
            this.mass = (Main.RANDOM.nextFloat() * 5 + 1f);
        }
        this.color = 5 - (int) mass;
        this.x = (1 + Main.RANDOM.nextFloat()) * GALAXY_SIZE / 3;
        this.y = (1 + Main.RANDOM.nextFloat()) * GALAXY_SIZE / 3;
        if (name > 51) {
            vx = -200;
            this.x = (Main.RANDOM.nextFloat() * 2) * GALAXY_SIZE / 3;
            if (name > 51 && name <= 151) {
                this.y = (Main.RANDOM.nextFloat()) * GALAXY_SIZE / 3;
            }
            if (name > 151 && name <= 251) {
                this.y = (2 + Main.RANDOM.nextFloat()) * GALAXY_SIZE / 3;
            }
            if (name > 251) {
                this.x = (Main.RANDOM.nextFloat()) * GALAXY_SIZE / 3;
                this.y = (1 + Main.RANDOM.nextFloat()) * GALAXY_SIZE / 3;
            }
        }

    }

    public void countVelocity(Star[] stars) {
        if (name != 1) {
            double ax = 0;
            double ay = 0;
            for (Star s : stars) {
                if (s != this) {
                    double dist = Math.sqrt(Math.pow(x - s.x, 2) + Math.pow(y - s.y, 2));
                    if (dist > mass + s.mass) {
                        double force = (GRAVITATIONAL_CONSTANT * (mass * s.mass) / (dist * dist));
                        double alpha = Math.atan2(s.y - y, s.x - x);
                        ax += Math.cos(alpha) * force / mass;
                        ay += Math.sin(alpha) * force / mass;
                    }
                }
            }
            vx += ax;
            vy += ay;
        }
    }

    public void applyVelocity() {
        x += vx;
        y += vy;
    }

    public void render(double width, double height) {
        if (name != 1 || Main.RANDOM.nextBoolean()) {
            STARS[color].draw((float) (x / GALAXY_SIZE * width - STARS[color].getWidth()/2), (float) (y / GALAXY_SIZE * height - STARS[color].getHeight()/2));
        }
    }

}
