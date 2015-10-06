/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.component;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Mar 12, 2007
 * @version $Revision: 0.10 $
 */
public class Button extends org.zkoss.zul.Button
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6562573800018819691L;
	private String name;
    
    public Button()
    {
        super();
    }
    
    public Button(String label)
    {
        super(label);
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setEnabled(boolean enabled)
    {
        super.setDisabled(!enabled);
    }
    
    public boolean isEnabled()
    {
        return !super.isDisabled();
    }

    /**
     * shortcut for addEventListener(Events.ON_CLICK, listener) to ease porting of swing form
     * @param listener
     */
	public void addActionListener(EventListener listener) {
		addEventListener(Events.ON_CLICK, listener);
	}	
}
