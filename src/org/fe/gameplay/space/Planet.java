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
import org.fe.main.FLocale;

/**
 *
 * @author fax
 */
public class Planet extends SpaceLocation implements Serializable {

    private final String[] NUMBER_NAMES = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI"};

    private static FImage[] PLANET_SPRITES = {
        new FImage("space/planets/rock"),
        new FImage("space/planets/rock"),
        new FImage("space/planets/rock")
    };

    int name = -1, number, type;

    double orbitRadius, omega, alpha, mass;

    Star star;

    public Planet(Star star, int number) {
        this.number = number;
        this.star = star;
        orbitRadius = star.mass * 300 + number * 500 + 500 + Main.RANDOM.nextInt(250);
        alpha = Main.RANDOM.nextDouble() * 2 * Math.PI;
        omega = (Main.RANDOM.nextBoolean()?1:-1) / orbitRadius;
        if (number <= 4) {
            type = Main.RANDOM.nextInt(2);
        } else {
            type = Main.RANDOM.nextInt(3);
        }
        mass = Main.RANDOM.nextDouble() * ((double) type) / 9.0 * star.mass;
    }

    @Override
    public String getName() {
        if (star.name == 1 && number == 1) {
            return FLocale.get("history.homeworld");
        } else if (name == -1) {
            return star.getName() + " " + NUMBER_NAMES[number];
        } else {
            return FLocale.getAsData().get("catnames").get("female").get(name).toString();
        }
    }

    @Override
    public void render(double width, double height, double systemSize) {
        double x = Math.cos(alpha) * orbitRadius / systemSize;
        double y = Math.sin(alpha) * orbitRadius / systemSize;
        x *= width;
        y *= height;
        x += width / 2;
        y += height / 2;
        PLANET_SPRITES[type].draw(x-PLANET_SPRITES[type].getWidth()/2, y-PLANET_SPRITES[type].getHeight()/2);
    }

    @Override
    public void tick() {
        alpha += omega;
    }

}
