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

import org.zkoss.zk.ui.Component;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public class Grid extends org.zkoss.zul.Grid
{
	private static final long serialVersionUID = -4483759833677794926L;
	private boolean noStrip = false;
	private String oddRowSclass;

    public Grid() {
		super();
		//cache default
		oddRowSclass = super.getOddRowSclass();
		super.setOddRowSclass(oddRowSclass);
	}

	public void makeNoStrip() {
    	setStyle("border: none");
    	setOddRowSclass(null);
        noStrip = true;
    }

	public Rows newRows() {
		Rows rows = new Rows();
		appendChild(rows);
		
		return rows;
	}    
	
	public boolean insertBefore(Component child, Component refChild) {
		boolean b = super.insertBefore(child, refChild);
		if (b && child instanceof Rows && noStrip) {
			Rows rows = (Rows) child;
			rows.setNoStrip(true);
		}
		return b;
	}

	@Override
	public String getOddRowSclass() {
		if (oddRowSclass == null)
			return null;
		else
			return super.getOddRowSclass();
	}

	@Override
	public void setOddRowSclass(String scls) {
		if (scls != null && scls.length() == 0)
			oddRowSclass = null;
		else
			oddRowSclass = scls;
		super.setOddRowSclass(scls);
	}
}
