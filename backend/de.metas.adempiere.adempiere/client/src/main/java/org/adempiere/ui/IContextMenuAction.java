package org.adempiere.ui;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import javax.swing.KeyStroke;

import org.compiere.grid.ed.VEditor;

/**
 * Context menu action (i.e. displayed on {@link VEditor}'s right click
 * 
 * @author tsa
 */
public interface IContextMenuAction extends Runnable
{
	/**
	 * Set evaluation context.
	 * 
	 * NOTE: don't call directly, this method is called by API once, when the context menu is initialized.
	 * 
	 * @param menuCtx
	 */
	void setContext(final IContextMenuActionContext menuCtx);

	/** @return action's title, already translated */
	String getTitle();

	/**
	 * Return the filename of the icon to use, <b>without the file ending</b>. Or return <code>null</code> if their shall be no icon.
	 * 
	 * @return
	 */
	String getIcon();

	KeyStroke getKeyStroke();

	/**
	 * @return True if the action should exist in the context menu.
	 */
	boolean isAvailable();

	/**
	 * @return True if the action can be run in the context menu.
	 */
	boolean isRunnable();

	/**
	 * Returns how the action shall be displayed when not runnable.
	 * 
	 * @return <ul>
	 *         <li>true if this action shall be hidden when not runnable
	 *         <li>false if this action shall be displayed but grayed when not runnable
	 *         </ul>
	 */
	boolean isHideWhenNotRunnable();

	/**
	 * @return child {@link IContextMenuAction}
	 */
	List<IContextMenuAction> getChildren();

	@Override
	void run();

	/**
	 * If is a long operation, menu renderer will:
	 * <ul>
	 * <li>show waiting cursor
	 * </ul>
	 * 
	 * @return true if executing this action can take a while.
	 */
	boolean isLongOperation();
}
