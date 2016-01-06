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
package org.fe.gameplay.types.entities;

import org.fe.Main;
import org.fe.gameplay.types.*;
import org.fe.gameplay.types.effects.*;
import org.fe.graphics.FImage;

/**
 *
 * @author yew_mentzaki
 */
public class KaiseratStiletto extends Entity {

    static FImage[] stands = {
        new FImage("entities/kaiserat/stiletto/stand/0"),
        new FImage("entities/kaiserat/stiletto/stand/1"),
        new FImage("entities/kaiserat/stiletto/stand/2"),
        new FImage("entities/kaiserat/stiletto/stand/3"),
        new FImage("entities/kaiserat/stiletto/stand/4"),
        new FImage("entities/kaiserat/stiletto/stand/5"),
        new FImage("entities/kaiserat/stiletto/stand/6"),
        new FImage("entities/kaiserat/stiletto/stand/7")
    };

    static FImage[] dead = {
        new FImage("entities/kaiserat/stiletto/dead/0"),
        new FImage("entities/kaiserat/stiletto/dead/1"),
        new FImage("entities/kaiserat/stiletto/dead/2"),
        new FImage("entities/kaiserat/stiletto/dead/3"),
        new FImage("entities/kaiserat/stiletto/dead/4")};

    static FImage[] prism = {
        new FImage("entities/kaiserat/stiletto/prism/0"),
        new FImage("entities/kaiserat/stiletto/prism/1"),
        new FImage("entities/kaiserat/stiletto/prism/2"),
        new FImage("entities/kaiserat/stiletto/prism/3")};

    static FImage[] prism_team = {
        new FImage("entities/kaiserat/stiletto/prism/0_team"),
        new FImage("entities/kaiserat/stiletto/prism/1_team"),
        new FImage("entities/kaiserat/stiletto/prism/2_team"),
        new FImage("entities/kaiserat/stiletto/prism/3_team")};

    public KaiseratStiletto() {
        hp = maxHp = 10;
        shield = maxShield = 200;

        width = 32;
        speed = 3;
        attack1 = new Attack(1, true, Attack.POINTLESS, 400, 0, 350, 1) {
            @Override
            protected void attack(Entity en, Entity list[], Entity t) {
                int additionaldamage = 0;
                for (Entity e : list) {
                    if (e instanceof KaiseratStiletto && e.owner == owner) {
                        double d = dist(x, y, e.x, e.y);
                        if (d < 200) {
                            additionaldamage++;
                            e.world.add(new Laser(en, e));
                        }
                    }
                }
                t.hit(damage + additionaldamage);
                en.world.add(new Rainbow(en, t));
            }
        };
    }

    @Override
    public void renderShadow() {

    }

    @Override
    public void tick(Entity[] e) {
        super.tick(e); //To change body of generated methods, choose Tools | Templates.
        prismframe++;
        if (prismframe >= 60) {
            prismframe -= 60;
        }
    }

    int prismframe = Main.RANDOM.nextInt(60);

    @Override
    public void renderBody() {
        if (hp > 0) {
            stands[mileage - mileage / 8 * 8].draw(x - stands[0].getWidth() / 2, y / 2 - 25);
            prism[prismframe / 15].draw(x - prism[0].getWidth() / 2, y / 2 - 25);
            prism_team[prismframe / 15].setColor(world.playerHandler[owner].color);
            prism_team[prismframe / 15].draw(x - prism[0].getWidth() / 2, y / 2 - 25);
        } else {
            dead[mileage].draw(x - dead[mileage].getWidth() / 2, y / 2 - 25);
        }
    }

}
