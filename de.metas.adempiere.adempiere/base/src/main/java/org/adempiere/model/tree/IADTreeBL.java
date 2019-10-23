package org.adempiere.model.tree;

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

import org.compiere.model.MTreeNode;

import de.metas.util.ISingletonService;

public interface IADTreeBL extends ISingletonService
{

	/**
	 * For the given <code>parent</code> and list of <code>ids</code>, this method iterates the parent's child nodes (recursing if a child has {@link MTreeNode#isSummary()} <code>== true</code>) and
	 * sets invokes {@link MTreeNode#setDisplayed(boolean)} base on whether the node is contained in <code>ids</code> or not.
	 * 
	 * @param parent
	 * @param ids
	 */
	boolean filterIds(MTreeNode parent, List<Integer> ids);

}
