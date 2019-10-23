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


import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import de.metas.util.Check;

/**
 * {@link ISideActionsGroupsListModel} default implemetation.
 * 
 * @author tsa
 *
 */
public class SideActionsGroupsListModel implements ISideActionsGroupsListModel
{
	private final DefaultListModel<ISideActionsGroupModel> groups = new DefaultListModel<>();
	private final Map<String, ISideActionsGroupModel> groupId2group = new HashMap<>();

	public SideActionsGroupsListModel()
	{
		super();
	}

	@Override
	public void addGroup(final ISideActionsGroupModel group)
	{
		Check.assumeNotNull(group, "group not null");
		final String groupId = group.getId();
		this.groups.addElement(group);
		this.groupId2group.put(groupId, group);
	}

	@Override
	public ListModel<ISideActionsGroupModel> getGroups()
	{
		return this.groups;
	}

	@Override
	public ISideActionsGroupModel getGroupByIdOrNull(final String id)
	{
		return groupId2group.get(id);
	}

	@Override
	public final ISideActionsGroupModel getGroupById(final String id)
	{
		final ISideActionsGroupModel group = getGroupByIdOrNull(id);
		Check.assumeNotNull(group, "group shall exist for ID={}", id);
		return group;
	}
}
