package de.metas.security.impl;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;

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
	public static RolesTreeNode of(final RoleId adRoleId, final UserId substituteForUserId, final LocalDate substituteDate)
	{
		final int seqNo = 0;

		if (substituteForUserId != null)
		{
			Check.assumeNotNull(substituteDate, "substituteDate not null");
			return new RolesTreeNode(adRoleId, seqNo, substituteForUserId, substituteDate);
		}
		else
		{
			return new RolesTreeNode(adRoleId, seqNo, substituteForUserId, null);
		}
	}

	private final RoleId roleId;
	private final int seqNo;
	private final UserId substituteForUserId;
	private final LocalDate substituteDate;
	private final Supplier<ImmutableList<IRolesTreeNode>> childrenSupplier = Suppliers.memoize(this::retrieveChildren);

	private RolesTreeNode(
			@NonNull final RoleId adRoleId,
			final int seqNo)
	{
		this(adRoleId, seqNo, (UserId)null, (LocalDate)null);
	}

	private RolesTreeNode(
			@NonNull final RoleId adRoleId,
			final int seqNo,
			final UserId substituteForUserId,
			final LocalDate substituteDate)
	{
		this.roleId = adRoleId;
		this.seqNo = seqNo;

		if (substituteForUserId != null)
		{
			this.substituteForUserId = substituteForUserId;

			Check.assumeNotNull(substituteDate, "substituteDate not null");
			this.substituteDate = substituteDate;
		}
		else
		{
			this.substituteForUserId = null;
			this.substituteDate = null;
		}
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

	private ImmutableList<IRolesTreeNode> retrieveChildren()
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
		if (substituteForUserId != null)
		{
			for (final RoleId childRoleId : roleDAO.getSubstituteRoleIds(substituteForUserId, substituteDate))
			{
				final int childSeqNo = -1; // no particular sequence number
				final RolesTreeNode child = new RolesTreeNode(childRoleId, childSeqNo);
				childrenBuilder.add(child);
			}
		}

		return childrenBuilder.build();
	}

	@Override
	public <ValueType, AggregatedValueType> ValueType aggregateBottomUp(final BottomUpAggregator<ValueType, AggregatedValueType> aggregator)
	{
		final LinkedHashMap<RoleId, RolesTreeNode> trace = new LinkedHashMap<>();
		return aggregateBottomUp(aggregator, trace);
	}

	private <ValueType, AggregatedValueType> ValueType aggregateBottomUp(
			final BottomUpAggregator<ValueType, AggregatedValueType> aggregator,
			final LinkedHashMap<RoleId, RolesTreeNode> trace)
	{
		// Skip already evaluated notes
		final RoleId nodeId = getRoleId();
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
