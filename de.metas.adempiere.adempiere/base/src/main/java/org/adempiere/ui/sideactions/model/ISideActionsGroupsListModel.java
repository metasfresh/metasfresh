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


import javax.swing.ListModel;

import org.adempiere.exceptions.AdempiereException;

/**
 * Contains a list of side action groups ({@link ISideActionsGroupModel}s).
 * 
 * @author tsa
 *
 */
public interface ISideActionsGroupsListModel
{
	/**
	 * Add a group
	 * 
	 * @param group
	 */
	void addGroup(ISideActionsGroupModel group);

	/**
	 * @return groups list model
	 */
	ListModel<ISideActionsGroupModel> getGroups();

	/**
	 * @param id
	 * @return group identified by given group id or <code>null</code> if no group was found.
	 */
	ISideActionsGroupModel getGroupByIdOrNull(String id);

	/**
	 * @param id
	 * @return group identified by given group id; never returns null
	 * @throws AdempiereException if group was not found
	 */
	ISideActionsGroupModel getGroupById(String id);
}
