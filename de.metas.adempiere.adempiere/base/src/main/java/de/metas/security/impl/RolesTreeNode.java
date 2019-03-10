package de.metas.security.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;

import de.metas.security.IRoleDAO;
import de.metas.security.IRolesTreeNode;
import de.metas.security.RoleId;
import de.metas.security.RoleInclude;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

class RolesTreeNode implements IRolesTreeNode
{
	public static RolesTreeNode of(final RoleId adRoleId, final UserId substitute_ForUserId, final Date substituteDate)
	{
		final int seqNo = 0;
		final RolesTreeNode node = new RolesTreeNode(adRoleId, seqNo);

		if (substitute_ForUserId != null)
		{
			node.substitute_ForUserId = substitute_ForUserId;

			Check.assumeNotNull(substituteDate, "substituteDate not null");
			node.substitute_Date = (Date)substituteDate.clone();
		}

		return node;
	}

	private final RoleId roleId;
	private final int seqNo;
	private UserId substitute_ForUserId;
	private Date substitute_Date = null;
	private final Supplier<ImmutableList<IRolesTreeNode>> childrenSupplier = Suppliers.memoize(new Supplier<ImmutableList<IRolesTreeNode>>()
	{

		@Override
		public ImmutableList<IRolesTreeNode> get()
		{
			final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

			ImmutableList.Builder<IRolesTreeNode> childrenBuilder = ImmutableList.builder();
			for (final RoleInclude roleIncludeLink : roleDAO.retrieveRoleIncludes(getRoleId()))
			{
				final RoleId childRoleId = roleIncludeLink.getChildRoleId();
				final int childSeqNo = roleIncludeLink.getSeqNo();
				final RolesTreeNode child = new RolesTreeNode(childRoleId, childSeqNo);
				childrenBuilder.add(child);
			}

			//
			// Load roles which are temporary assigned to given user
			if (substitute_ForUserId != null)
			{
				for (final RoleId childRoleId : roleDAO.getSubstituteRoleIds(substitute_ForUserId, substitute_Date))
				{
					final int childSeqNo = -1; // no particular sequence number
					final RolesTreeNode child = new RolesTreeNode(childRoleId, childSeqNo);
					childrenBuilder.add(child);
				}
			}

			return childrenBuilder.build();
		}

	});

	private RolesTreeNode(@NonNull final RoleId adRoleId, final int seqNo)
	{
		this.roleId = adRoleId;
		this.seqNo = seqNo;
	}

	@Override
	public RoleId getRoleId()
	{
		return roleId;
	}

	@Override
	public int getSeqNo()
	{
		return seqNo;
	}

	@Override
	public List<IRolesTreeNode> getChildren()
	{
		return childrenSupplier.get();
	}

	@Override
	public <ValueType, AggregatedValueType> ValueType aggregateBottomUp(final BottomUpAggregator<ValueType, AggregatedValueType> aggregator)
	{
		final LinkedHashMap<Object, RolesTreeNode> trace = new LinkedHashMap<Object, RolesTreeNode>();
		return aggregateBottomUp(aggregator, trace);
	}

	private <ValueType, AggregatedValueType> ValueType aggregateBottomUp(final BottomUpAggregator<ValueType, AggregatedValueType> aggregator, final LinkedHashMap<Object, RolesTreeNode> trace)
	{
		// Skip already evaluated notes
		final Object nodeId = getRoleId();
		if (trace.containsKey(nodeId))
		{
			return null;
		}
		trace.put(nodeId, this);

		//
		// If this is a leaf node, return it's leaf value
		final List<IRolesTreeNode> children = getChildren();
		final boolean isLeaf = children.isEmpty();
		if (isLeaf)
		{
			ValueType value = aggregator.leafValue(this);
			return value;
		}

		final AggregatedValueType valueAgg = aggregator.initialValue(this);
		Check.assumeNotNull(valueAgg, "valueAgg shall not be null");

		for (final IRolesTreeNode child : children)
		{
			final RolesTreeNode childImpl = (RolesTreeNode)child;
			final ValueType childValue = childImpl.aggregateBottomUp(aggregator, trace);
			if (childValue == null)
			{
				// do nothing
				continue;
			}

			aggregator.aggregateValue(valueAgg, child, childValue);
		}

		final ValueType value = aggregator.finalValue(valueAgg);
		return value;
	}
}
