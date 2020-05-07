package de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;

import de.metas.contracts.commission.Beneficiary;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.contracts
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

@Immutable
@ToString
public class Hierarchy
{
	private final ImmutableMap<HierarchyNode, HierarchyNode> child2Parent;
	private final ImmutableListMultimap<HierarchyNode, HierarchyNode> parent2Children;
	private ImmutableMap<Beneficiary, HierarchyNode> beneficiary2Node;

	public static HierarchyBuilder builder()
	{
		return new HierarchyBuilder();
	}

	private Hierarchy(
			@NonNull final ImmutableMap<Beneficiary, HierarchyNode> beneficiary2Node,
			@NonNull final ImmutableListMultimap<HierarchyNode, HierarchyNode> parentsAndChildren)
	{
		this.beneficiary2Node = beneficiary2Node;
		this.parent2Children = parentsAndChildren;

		final ImmutableMap.Builder<HierarchyNode, HierarchyNode> childAndParentBuilder = ImmutableMap.builder();
		final ImmutableCollection<Entry<HierarchyNode, HierarchyNode>> entries = parentsAndChildren.entries();

		entries.forEach(e -> childAndParentBuilder.put(e.getValue(), e.getKey()));
		this.child2Parent = childAndParentBuilder.build();
	}

	public Optional<HierarchyNode> getParent(HierarchyNode child)
	{
		return Optional.ofNullable(child2Parent.get(child));
	}

	public ImmutableList<HierarchyNode> getChildren(@NonNull final HierarchyNode parent)
	{
		return parent2Children.get(parent);
	}

	public static class HierarchyBuilder
	{
		private final ImmutableListMultimap.Builder<HierarchyNode, HierarchyNode> parent2Children = ImmutableListMultimap.builder();
		private final HashMap<Beneficiary, HierarchyNode> beneficiary2Node = new HashMap<>();

		public HierarchyBuilder addChildren(HierarchyNode parent, Collection<HierarchyNode> children)
		{
			beneficiary2Node.put(parent.getBeneficiary(), parent);
			children.forEach(child -> beneficiary2Node.put(child.getBeneficiary(), child));

			parent2Children.putAll(parent, children);
			return this;
		}

		public Hierarchy build()
		{
			return new Hierarchy(
					ImmutableMap.copyOf(beneficiary2Node),
					parent2Children.build());
		}
	}

	/**
	 * @return the given {@code beneficiary}'s node, followed by its parent, grandparent and so on.
	 */
	public Iterable<HierarchyNode> getUpStream(@NonNull final Beneficiary beneficiary)
	{
		final HierarchyNode node = beneficiary2Node.get(beneficiary);
		return new Iterable<HierarchyNode>()
		{
			@Override
			public Iterator<HierarchyNode> iterator()
			{
				return new ParentNodeIterator(child2Parent, node);
			}
		};
	}

	public static class ParentNodeIterator implements Iterator<HierarchyNode>
	{
		private final ImmutableMap<HierarchyNode, HierarchyNode> child2Parent;
		private HierarchyNode next;
		private HierarchyNode previous;

		private ParentNodeIterator(
				@NonNull final ImmutableMap<HierarchyNode, HierarchyNode> childAndParent,
				@NonNull final HierarchyNode first)
		{
			this.child2Parent = childAndParent;
			this.next = first;
		}

		@Override
		public boolean hasNext()
		{
			return next != null;
		}

		@Override
		public HierarchyNode next()
		{
			final HierarchyNode result = next;
			if (result == null)
			{
				throw new NoSuchElementException("Previous HierarchyNode=" + previous + " had no parent");
			}
			previous = next;
			next = child2Parent.get(next);
			return result;
		}
	}
}
