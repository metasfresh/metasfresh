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

import java.util.List;

import org.zkoss.zk.ui.Component;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public class Rows extends org.zkoss.zul.Rows
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5100123951371296683L;
	private boolean noStrip = false;
    
    public Row newRow() {
    	Row row = new Row();    	
    	appendChild(row);
    	return row;
    }

	public void setNoStrip(boolean b) {
		noStrip  = b;
		String style = noStrip ? "border: none" : null;
		List list = getChildren();
		for (Object o : list) {
			if (o instanceof Row) {
				Row row = (Row) o;
				row.setStyle(style);
			}
		}
	}

	@Override
	public boolean insertBefore(Component child, Component refChild) {
		boolean b = super.insertBefore(child, refChild);
		if (b && child instanceof Row && noStrip) {
			Row row = (Row) child;
			row.setStyle("border: none");
		}
		return b;
	}
}
