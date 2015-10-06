/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin  All Rights Reserved.                      *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.part;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;

/**
 * 
 * @author Low Heng Sin
 *
 */
public abstract class AbstractUIPart implements UIPart {
	
	protected Page page = null;
	
	public Component createPart(Object parent) {
		if (parent == null)
			throw new IllegalArgumentException("Null parent.");
		
		Component component = null;
		if (parent instanceof Page) {
			page = (Page)parent;
		} else if (parent instanceof Component) {
			component = (Component)parent;
			page = component.getPage();
		} else {
			throw new IllegalArgumentException("Parent must of class Page or Component.");
		}
		
		return doCreatePart(component);
	}
	
	protected abstract Component doCreatePart(Component parent);

}
