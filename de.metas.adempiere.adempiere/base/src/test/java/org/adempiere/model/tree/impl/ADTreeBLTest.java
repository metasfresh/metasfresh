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


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.compiere.model.MTreeNode;
import org.junit.Test;

public class ADTreeBLTest
{
	/**
	 * this test verifies that if a summary node is selected, then itself and also its parents are displayed.
	 */
	@Test
	public void testFilterNodesLeavesParentDisplayed()
	{
		final MTreeNode parent = new MTreeNode(10, 10, "parent", "test", 0, true, null, false, null);
		final MTreeNode child1 = new MTreeNode(11, 10, "child1", "test", 10, true, null, false, null);
		final MTreeNode child2 = new MTreeNode(12, 10, "child2", "test", 10, true, null, false, null);
		
		parent.add(child1);
		parent.add(child2);
		
		assertThat(parent.isDisplayed(), is(true));
		assertThat(child1.isDisplayed(), is(true));
		assertThat(child2.isDisplayed(), is(true));
		
		new ADTreeBL().filterIds(parent, Collections.singletonList(11)); // only child1 is selected.
		
		assertThat(parent.isDisplayed(), is(true)); // because one of its children was selected
		assertThat(child1.isDisplayed(), is(true)); // because it was explicitly selected
		assertThat(child2.isDisplayed(), is(false)); // because it wasn't selected
	}

	
	@Test
	public void testFilterNodesLeavesParentDisplayedMultilevelWithSummaryGrandchildren()
	{
		boolean grandChildrenAreSummary = true;
		testWithTreeLayerTree(grandChildrenAreSummary);
	}

	@Test
	public void testFilterNodesLeavesParentDisplayedMultilevelWithNonSummaryGrandchildren()
	{
		boolean grandChildrenAreSummary = false;
		testWithTreeLayerTree(grandChildrenAreSummary);
	}
	
	private void testWithTreeLayerTree(boolean grandChildrenAreSummary)
	{
		final MTreeNode node1 = new MTreeNode(1, 10, "node1", "test", 0, true, null, false, null);
		final MTreeNode node11 = new MTreeNode(11, 10, "node11", "test", 1, true, null, false, null);
		final MTreeNode node111 = new MTreeNode(111, 10, "node111", "test", 11, grandChildrenAreSummary, null, false, null);
		final MTreeNode node112 = new MTreeNode(112, 20, "node112", "test", 11, grandChildrenAreSummary, null, false, null);
		final MTreeNode node12 = new MTreeNode(12, 20, "node12", "test", 1, true, null, false, null);
				
		node1.add(node11);
		node11.add(node111);
		node11.add(node112);
		node1.add(node12);
				
		assertThat(node1.isDisplayed(), is(true));
		assertThat(node11.isDisplayed(), is(true));
		assertThat(node111.isDisplayed(), is(true));
		assertThat(node112.isDisplayed(), is(true));
		assertThat(node12.isDisplayed(), is(true));
		
		new ADTreeBL().filterIds(node1, Collections.singletonList(111)); // only node111 is selected.
		
		assertThat(node111.isDisplayed(), is(true)); // because it was explicitly selected
		assertThat(node11.isDisplayed(), is(true)); // because one of its children was selected
		assertThat(node1.isDisplayed(), is(true)); // because one of its grand-children was selected
		assertThat(node112.isDisplayed(), is(false)); // because it wasn't selected
		assertThat(node12.isDisplayed(), is(false)); // because it wasn't selected
	}
}
