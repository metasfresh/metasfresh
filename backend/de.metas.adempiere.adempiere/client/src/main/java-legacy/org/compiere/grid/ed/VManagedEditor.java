/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 Low Heng Sin All Rights Reserved.                		  *
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
package org.compiere.grid.ed;

/**
 * Interface for editor with a commit and rollback lifecycle.
 * @author Low Heng Sin
 *
 */
public interface VManagedEditor {
	/**
	 * Commit pending changes
	 */
	public void commitChanges();
	
	/**
	 * Rollback pending changes
	 */
	public void rollbackChanges();
	
	/**
	 * Are there any pending changes
	 * @return boolean
	 */
	public boolean isDirty();	
}
