/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.util;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NodeTests
{
	/**
	 * Given:
	 *
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 *
	 * when {@link Node#listAllNodesBelow()} for node 1
	 * then return: [1,2,4,6,7,8,3,5]
	 */
	@Test
	public void listAllNodes()
	{
		//Given
		final ImmutableList<Node<Integer>> nodeListOrderedByIndex = buildNodeStructure();

		//When
		final List<Node<Integer>> nodeList = nodeListOrderedByIndex.get(0).listAllNodesBelow();

		//Then
		assertEquals(nodeList.size(), 8);
		assertEquals(nodeList.get(0).getValue(), Integer.valueOf(1));
		assertEquals(nodeList.get(1).getValue(), Integer.valueOf(2));
		assertEquals(nodeList.get(2).getValue(), Integer.valueOf(4));
		assertEquals(nodeList.get(3).getValue(), Integer.valueOf(6));
		assertEquals(nodeList.get(4).getValue(), Integer.valueOf(7));
		assertEquals(nodeList.get(5).getValue(), Integer.valueOf(8));
		assertEquals(nodeList.get(6).getValue(), Integer.valueOf(3));
		assertEquals(nodeList.get(7).getValue(), Integer.valueOf(5));
	}

	/**
	 * Given:
	 *
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 *
	 * when {@link Node#getUpStream()} for node 5
	 * then return: [5,3,1]
	 */
	@Test
	public void getUpStream()
	{
		//Given
		final ImmutableList<Node<Integer>> nodeListOrderedByIndex = buildNodeStructure();

		final Node<Integer> node5 = nodeListOrderedByIndex.get(4);

		//When
		final List<Node<Integer>> upStream = node5.getUpStream();

		//Then
		assertEquals(upStream.size(), 3);
		assertEquals(upStream.get(0).getValue(), Integer.valueOf(5));
		assertEquals(upStream.get(1).getValue(), Integer.valueOf(3));
		assertEquals(upStream.get(2).getValue(), Integer.valueOf(1));
	}

	@Test
	public void getNode()
	{
		//Given
		final ImmutableList<Node<Integer>> nodeListOrderedByIndex = buildNodeStructure();

		final Node<Integer> root = nodeListOrderedByIndex.get(0);

		//When
		final Optional<Node<Integer>> node5 = root.getNode(5);

		//Then
		assertTrue(node5.isPresent());
		assertEquals(node5.get().getValue(), Integer.valueOf(5));
	}

	private ImmutableList<Node<Integer>> buildNodeStructure()
	{
		final Node<Integer> node8 = Node.of(8, new ArrayList<>());
		final Node<Integer> node7 = Node.of(7, new ArrayList<>());
		final Node<Integer> node6 = Node.of(6, new ArrayList<>());
		final Node<Integer> node5 = Node.of(5, new ArrayList<>());

		final Node<Integer> node4 = Node.of(4, Arrays.asList(node6, node7, node8));
		node6.setParent(node4);
		node7.setParent(node4);
		node8.setParent(node4);

		final Node<Integer> node3 = Node.of(3, Collections.singletonList(node5));
		node5.setParent(node3);

		final Node<Integer> node2 = Node.of(2, Collections.singletonList(node4));
		node4.setParent(node2);

		final Node<Integer> node1 = Node.of(1, Arrays.asList(node2, node3));
		node2.setParent(node1);
		node3.setParent(node1);

		return ImmutableList.of(node1,node2,node3,node4,node5,node6,node7,node8);
	}
}
