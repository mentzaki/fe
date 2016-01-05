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

/**
 *
 * @author yew_mentzaki
 */
public class Attack {
    public double distance;
    public int reloadTime, ammoRealoadTime, currentReloadTime;
    public int ammo, currentAmmo;
    
    public boolean animation;
    public boolean ready;
    
    private int animationTime;

    public Attack(double distance, int reloadTime, int ammoReloadTime,  int ammo) {
        this.distance = distance;
        this.reloadTime = reloadTime;
        this.ammoRealoadTime = ammoReloadTime;
        this.ammo = this.currentAmmo = ammo;
    }
    
    public void tick(Entity e, Entity list[]){
        if(currentReloadTime > 0){
            currentReloadTime--;
        }else{
            ready = true;
        }
        if(ready){
            currentAmmo--;
            if(currentAmmo == 0){
                currentAmmo = ammo;
                currentReloadTime = ammoRealoadTime;
            }else{
                currentReloadTime = reloadTime;
            }
            animationTime = 8;
            ready = false;
        }
        if(animationTime > 0){
            animationTime--;
            animation = true;
        }else{
            animation = false;
        }
    }
    
}
