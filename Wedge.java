/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package woffortune;

/**
 * This class defines one wedge of a wheel for the wheel of fortune game
 *
 * @author clatulip Fernanda Sanchez
 */
public class Wedge {

    private Wheel.WedgeType type;
    private int amount = 0;

    /**
     * Constructor
     *
     * @param type Wheel.WedgeType
     */
    public Wedge(Wheel.WedgeType type) {
        this.type = type;
        if (type == Wheel.WedgeType.MONEY) {
            amount = (int) (Math.random() * 20 + 1) * 25;
        }
    }

    /**
     * Getter
     *
     * @return Wheel.WedgeType
     */
    public Wheel.WedgeType getType() {
        return type;
    }

    /**
     * Getter
     *
     * @return int amount (only for wedges of Wheel.WedgeType.MONEY)
     */
    public int getAmount() {
        return amount;
    }

}
