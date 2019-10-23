package org.adempiere.ui.sideactions.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import javax.swing.ListModel;

/**
 * A group of {@link ISideAction}s.
 *
 * @author tsa
 *
 */
public interface ISideActionsGroupModel
{
	/** @return group's unique ID */
	String getId();

	/** @return group's user friendly name */
	String getTitle();

	/** @return true if the group is collapsed by default */
	boolean isDefaultCollapsed();

	/** @return the list model of contained actions */
	ListModel<ISideAction> getActions();

	/** @return a <b>copy</b> of current actions, as list */
	List<ISideAction> getActionsList();

	/**
	 * Add an action to underlying list model.
	 *
	 * @param action
	 */
	void addAction(ISideAction action);

	/**
	 * Clear/remove all contained side actions from this group.
	 */
	void removeAllActions();

	/**
	 * Remove the {@link ISideAction} at given <code>index</code>
	 *
	 * @param index
	 * @throws IndexOutOfBoundsException in case the index is not valid
	 */
	void removeAction(int index);

	/**
	 * Sets given actions.
	 * Previous actions will be discarded.
	 * New actions which will be set, will be sorted by {@link ISideAction#getDisplayName()}.
	 *
	 * @param actions
	 */
	void setActions(Iterable<? extends ISideAction> actions);

}
