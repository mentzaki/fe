/*
 * Copyright (C) 2015 yew_mentzaki
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
package org.fe.gui;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.main.FLocale;

/**
 *
 * @author yew_mentzaki
 */
public class FElement extends ArrayList<FElement> implements Serializable{

    public FElement() {
    }

    boolean initialized;
    
    public String toolTip;
    public FElement parent;
    public double x, y, width, height;
    public FAlignment horisontalAlignment = FAlignment.LEFT,
            verticalAlignment = FAlignment.TOP;

    public double x() {
        if (horisontalAlignment == FAlignment.LEFT) {
            if (parent != null) {
                return parent.x() + x;
            } else {
                return x;
            }
        }
        if (horisontalAlignment == FAlignment.CENTER) {
            if (parent != null) {
                return parent.x() + parent.width / 2 + x - width / 2;
            } else {
                return x - width / 2;
            }
        }
        if (horisontalAlignment == FAlignment.RIGHT) {
            if (parent != null) {
                return parent.x() + parent.width + x - width;
            } else {
                return x - width;
            }
        }
        return x;
    }

    public double y() {
        if (verticalAlignment == FAlignment.TOP) {
            if (parent != null) {
                return parent.y() + y;
            } else {
                return y;
            }
        }
        if (verticalAlignment == FAlignment.CENTER) {
            if (parent != null) {
                return parent.y() + parent.height / 2 + y - height / 2;
            } else {
                return y - height / 2;
            }
        }
        if (verticalAlignment == FAlignment.BOTTOM) {
            if (parent != null) {
                return parent.y() + parent.height + y - height;
            } else {
                return y - height;
            }
        }
        return y;
    }

    @Override
    public boolean add(FElement e) {
        if (!e.initialized) {
            e.init();
            e.initialized = true;
        }
        e.parent = this;
        return super.add(e);
    }

    @Override
    public boolean remove(Object o) {
        ((FElement) o).parent = null;
        return super.remove(o); //To change body of generated methods, choose Tools | Templates.
    }

    public void init() {

    }

    public String toolTip() {
        return FLocale.get(toolTip);
    }

    public boolean click;

    public void handleClick(double mx, double my) {
        if (mx >= x()
                && my >= y()
                && mx <= width + x()
                && my <= height + y()) {
            for (int i = 0; i < size(); i++) {
                get(i).handleClick(mx, my);
            }
            click(mx, my);
            click = true;
        } else {
            click = false;
        }
    }

    public void click(double mx, double my) {

    }

    public boolean hover;

    public void handleHover(double mx, double my) {
        if (mx >= x()
                && my >= y()
                && mx <= width + x()
                && my <= height + y()) {
            for (int i = 0; i < size(); i++) {
                get(i).handleHover(mx, my);
            }
            hover(mx, my);
            hover = true;
        } else {
            hover = false;
        }
    }

    public void hover(double mx, double my) {

    }

    public String showToolTip(double mx, double my) {
        if (mx >= x()
                && my >= y()
                && mx <= width + x()
                && my <= height + y()) {
            for (int i = 0; i < size(); i++) {
                String s = get(i).showToolTip(mx, my);
                if (s != null) {
                    return s;
                }
            }
            return toolTip();
        }
        return null;
    }

    public void handleKeyPress(int keyCode) {

    }

    public void handleType(char keyChar) {

    }

    public void handleTick() {
        for (int i = 0; i < size(); i++) {
            get(i).handleTick();
        }
        tick();
    }

    public void tick() {

    }

    public void render() {
        hover = false;
        click = false;
    }
}
