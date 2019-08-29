/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui;

import racingplatformer.Game;

/**
 *
 * @author Luke
 */
public class PopupGui extends Gui
{
    protected Gui parent;
    
    public PopupGui(Game game, Gui parent) 
    {
        super(game);
        this.parent = parent;
    }
    
    @Override
    public void close()
    {
        this.parent.setCurrentPopup(null);
    }
}
