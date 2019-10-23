package org.adempiere.model.tree.impl;

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

import org.adempiere.model.tree.IADTreeBL;
import org.compiere.model.MTreeNode;

import de.metas.util.Check;

public class ADTreeBL implements IADTreeBL
{
	@Override
	public boolean filterIds(final MTreeNode node, final List<Integer> ids)
	{
		Check.assumeNotNull(ids, "Param 'ids' is not null");

		boolean atLeastOneChildIsDisplayed = false;

		if (node == null)
		{
			return false; // nothing to do
		}

		for (final MTreeNode currentChild : node.getChildrenAll())
		{
				// recurse
				atLeastOneChildIsDisplayed = filterIds(currentChild, ids) || atLeastOneChildIsDisplayed;
		}
		
		if (ids.contains(node.getNode_ID()) || atLeastOneChildIsDisplayed)
		{
			node.setDisplayed(true);
			atLeastOneChildIsDisplayed = true;
		}
		else
		{
			node.setDisplayed(false);
		}	
		return atLeastOneChildIsDisplayed;
	}
}
