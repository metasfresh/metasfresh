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

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public class Window extends org.zkoss.zul.Window
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -977158068979292880L;
	/*** Show as modal window ***/
    public static final String MODE_MODAL = "modal";
    /*** Show as popup window ***/
    public static final String MODE_POPUP =  "popup";
    /*** Show as floating window ***/
    public static final String MODE_OVERLAPPED =  "overlapped";
    /*** Add to the tabbed window container ***/
    public static final String MODE_EMBEDDED =  "embedded";
    /*** Show as fake modal window ***/
    public static final String MODE_HIGHLIGHTED = "highlighted";
    /*** attribute key to store window display mode ***/
    public static final String MODE_KEY = "mode";
    
    /*** attribute key to store insert position for embedded mode window ***/
    public static final String INSERT_POSITION_KEY = "insertPosition";
    /*** Append to the end of tabs of the tabbed window container ***/
    public static final String INSERT_END = "insertEnd";
    /*** Insert next to the active tab of the tabbed window container ***/
    public static final String INSERT_NEXT = "insertNext";
    
    public Window()
    {
        super();
        setShadow(false);
    }
    
    /**
     * alias for detach, to ease porting of swing form
     */
    public void dispose()
    {
    	detach();
    }
}
