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
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Data
public class Node<T>
{
	@NonNull
	private T value;
	@Nullable
	private Node<T> parent;
	@NonNull
	private List<Node<T>> children;

	public static <T> Node<T> of(@NonNull final T value,@NonNull final List<Node<T>> children)
	{
		return new Node<>(value, null, children);
	}

	private Node(@NonNull final T value,@Nullable final Node<T> parent,@NonNull final List<Node<T>> children)
	{
		this.value = value;
		this.parent = parent;
		this.children = children;
	}

	public void addChild(final Node<T> child)
	{
		this.children.add(child);
	}

	public boolean isLeaf()
	{
		return getChildren().isEmpty();
	}

	/**
	 *  Creates and returns an ordered list with {@code this} and all
	 *  the nodes found below it on the same branch in the tree.
	 *
	 * e.g given the following tree:
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 *
	 *  if {@code listAllNodes()} it's called for node 1,
	 *  it will return: [1,2,4,6,7,8,3,5]
	 */
	@NonNull
	public ImmutableList<Node<T>> listAllNodesBelow()
	{
		final ArrayList<Node<T>> list = new ArrayList<>();

		listAllNodesBelow_rec(this, list);

		return ImmutableList.copyOf(list);
	}

	/**
	 *  Creates and returns an ordered list with {@code this} and all
	 *  the nodes found above it on the same branch in the tree.
	 *
	 * e.g given the following tree:
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 *
	 *  if {@code getUpStream()} it's called for node 8,
	 *  it will return: [8,4,2,1]
	 */
	public ArrayList<Node<T>> getUpStream()
	{
		Node<T> currentNode = this;

		final ArrayList<Node<T>> upStream = new ArrayList<>();
		upStream.add(currentNode);

		while (currentNode.parent != null)
		{
			upStream.add(currentNode.parent);
			currentNode = currentNode.parent;
		}

		return upStream;
	}

	/**
	 *  Tries to found a node among the ones found below {@code this} on the same branch
	 *  in the tree with {@code value} equal to the given one.
	 *
	 * e.g given the following tree:
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 *
	 *  if {@code getNode(4)} it's called for node 1,
	 *  it will return: Optional.of(Node(4)).
	 */
	@NonNull
	public Optional<Node<T>> getNode(final T value)
	{
		return Optional.ofNullable(getNode_rec(this, value));
	}

	@NonNull
	public String toString()
	{
		return this.getValue().toString();
	}

	@Nullable
	private Node<T> getNode_rec(final Node<T> currentNode, final T value)
	{
		if (currentNode.value.equals(value))
		{
			return currentNode;
		}
		else
		{
			final Iterator<Node<T>> iterator = currentNode.getChildren().iterator();
			Node<T> requestedNode = null;

			while (requestedNode == null && iterator.hasNext())
			{
				requestedNode = getNode_rec(iterator.next(), value);
			}
			return requestedNode;
		}

	}

	private void listAllNodesBelow_rec(final Node<T> currentNode, final List<Node<T>> nodeList)
	{
		nodeList.add(currentNode);

		if (!currentNode.isLeaf())
		{
			currentNode.getChildren().forEach(child -> listAllNodesBelow_rec(child, nodeList));
		}
	}
}
