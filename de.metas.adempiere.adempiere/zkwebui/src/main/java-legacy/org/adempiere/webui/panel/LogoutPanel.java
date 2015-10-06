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

package org.adempiere.webui.panel;

import org.adempiere.webui.component.Panel;
import org.adempiere.webui.session.SessionManager;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Mar 2, 2007
 * @version $Revision: 0.10 $
 */

public class LogoutPanel extends Panel implements EventListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -456860093240111236L;
	private Button btnLogOut; 

    public LogoutPanel()
    {
        super();
        init();
    }
    
    private void init()
    {
        btnLogOut = new Button();
        btnLogOut.setImage("/images/Logout24.png");
        btnLogOut.addEventListener(Events.ON_CLICK, this);
    
        this.appendChild(btnLogOut);
	}
    
    public void onEvent(Event event)
    {
        if (btnLogOut == event.getTarget())
        {
            SessionManager.logoutSession();
        }
    }
}
