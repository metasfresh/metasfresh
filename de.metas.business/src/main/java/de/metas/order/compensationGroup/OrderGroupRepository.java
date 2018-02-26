package de.metas.order.compensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.compensationGroup.Group.GroupBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
public class OrderGroupRepository implements GroupRepository
{
	// NOTE: we cannot have it here because the impl is not in the same package and unit tests are failing
	// private final transient IOrderBL orderBL = Services.get(IOrderBL.class);
	// private final transient IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final ImmutableList<OrderGroupRepositoryAdvisor> advisors;

	public OrderGroupRepository(final Optional<List<OrderGroupRepositoryAdvisor>> advisors)
	{
		this.advisors = ImmutableList.copyOf(advisors.orElse(ImmutableList.of()));
	}

	public static GroupId extractGroupId(final I_C_OrderLine orderLine)
	{
		OrderGroupCompensationUtils.assertInGroup(orderLine);
		return extractGroupIdOrNull(orderLine);
	}

	public static GroupId extractGroupIdOrNull(final I_C_OrderLine orderLine)
	{
		if (OrderGroupCompensationUtils.isInGroup(orderLine))
		{
			return GroupId.of(I_C_Order.Table_Name, orderLine.getC_Order_ID(), orderLine.getC_Order_CompensationGroup_ID());
		}
		else
		{
			return null;
		}
	}

	private static GroupId extractSingleGroupId(final List<I_C_OrderLine> orderLines)
	{
		Check.assumeNotEmpty(orderLines, "orderLines is not empty");
		return orderLines.stream()
				.map(OrderGroupRepository::extractGroupId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Order lines are not part of the same group: " + orderLines)));
	}

	@Override
	public Group retrieveGroup(@NonNull final GroupId groupId)
	{
		final Group group = retrieveGroupIfExists(groupId);
		if (group == null)
		{
			throw new AdempiereException("Group not found for " + groupId);
		}
		return group;
	}

	public Group retrieveGroupIfExists(@NonNull final GroupId groupId)
	{
		groupId.assertDocumentTableName(I_C_Order.Table_Name);

		final List<I_C_OrderLine> groupOrderLines = retrieveGroupOrderLines(groupId);
		if (groupOrderLines.isEmpty())
		{
			return null;
		}

		final Group group = createGroupFromOrderLines(groupOrderLines);
		assertGroupId(group, groupId);
		return group;
	}

	private static void assertGroupId(final Group group, final GroupId expectedGroupId)
	{
		if (!group.getGroupId().equals(expectedGroupId))
		{
			// shall not happen
			throw new AdempiereException("Invalid groupId for group " + group)
					.setParameter("expectedGroupId", expectedGroupId)
					.appendParametersToMessage();
		}
	}

	private Group createGroupFromOrderLines(final List<I_C_OrderLine> groupOrderLines)
	{
		Check.assumeNotEmpty(groupOrderLines, "groupOrderLines is not empty");

		final GroupId groupId = extractSingleGroupId(groupOrderLines);

		final I_C_OrderLine groupFirstOrderLine = groupOrderLines.get(0);
		final I_C_Order order = groupFirstOrderLine.getC_Order();
		final I_C_Order_CompensationGroup orderCompensationGroupPO = groupFirstOrderLine.getC_Order_CompensationGroup();
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final int precision = orderBL.getPrecision(order);

		final GroupBuilder groupBuilder = Group.builder()
				.groupId(groupId)
				.groupTemplateId(orderCompensationGroupPO.getC_CompensationGroup_Schema_ID())
				.precision(precision)
				.bpartnerId(order.getC_BPartner_ID())
				.isSOTrx(order.isSOTrx());

		for (final I_C_OrderLine groupOrderLine : groupOrderLines)
		{
			if (!groupOrderLine.isGroupCompensationLine())
			{
				final GroupRegularLine regularLine = createReqularLine(groupOrderLine);
				groupBuilder.regularLine(regularLine);
			}
			else
			{
				final GroupCompensationLine compensationLine = createCompensationLine(groupOrderLine);
				groupBuilder.compensationLine(compensationLine);
			}
		}

		advisors.forEach(advisor -> advisor.customizeFromOrder(groupBuilder, order, groupOrderLines));

		return groupBuilder.build();
	}

	private List<I_C_OrderLine> retrieveGroupOrderLines(final GroupId groupId)
	{
		final List<I_C_OrderLine> groupOrderLines = retrieveGroupOrderLinesQuery(groupId)
				.create()
				.list(I_C_OrderLine.class);
		return groupOrderLines;
	}

	public IQueryBuilder<I_C_OrderLine> retrieveGroupOrderLinesQuery(@NonNull final GroupId groupId)
	{
		final int orderId = groupId.getDocumentIdAssumingTableName(I_C_Order.Table_Name);
		return queryBL
				.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID, groupId.getOrderCompensationGroupId())
				.orderBy()
				.addColumn(org.compiere.model.I_C_OrderLine.COLUMNNAME_Line)
				.endOrderBy();
	}

	private OrderLinesStorage retrieveOrderLinesStorage(@NonNull final GroupId groupId)
	{
		final List<I_C_OrderLine> orderLines = retrieveGroupOrderLinesQuery(groupId)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, true)
				.create()
				.list(I_C_OrderLine.class);

		return OrderLinesStorage.builder()
				.groupId(groupId)
				.orderLines(orderLines)
				.performDatabaseChanges(true)
				.build();
	}

	public final OrderLinesStorage createNotSaveableSingleOrderLineStorage(final I_C_OrderLine compensationLinePO)
	{
		return OrderLinesStorage.builder()
				.groupId(extractGroupId(compensationLinePO))
				.orderLine(compensationLinePO)
				.performDatabaseChanges(false)
				.build();
	}

	private GroupRegularLine createReqularLine(final I_C_OrderLine groupOrderLine)
	{
		return GroupRegularLine.builder()
				.repoId(groupOrderLine.getC_OrderLine_ID())
				.lineNetAmt(groupOrderLine.getLineNetAmt())
				.build();
	}

	/**
	 * note to dev: keep in sync with {@link #updateOrderLineFromCompensationLine(I_C_OrderLine, GroupCompensationLine, GroupId)}
	 */
	private GroupCompensationLine createCompensationLine(final I_C_OrderLine groupOrderLine)
	{
		return GroupCompensationLine.builder()
				.repoId(groupOrderLine.getC_OrderLine_ID())
				.seqNo(groupOrderLine.getLine())
				.productId(groupOrderLine.getM_Product_ID())
				.uomId(groupOrderLine.getC_UOM_ID())
				.type(GroupCompensationType.ofAD_Ref_List_Value(groupOrderLine.getGroupCompensationType()))
				.amtType(GroupCompensationAmtType.ofAD_Ref_List_Value(groupOrderLine.getGroupCompensationAmtType()))
				.percentage(groupOrderLine.getGroupCompensationPercentage())
				.baseAmt(groupOrderLine.getGroupCompensationBaseAmt())
				.price(groupOrderLine.getPriceEntered())
				.qty(groupOrderLine.getQtyEntered())
				.lineNetAmt(groupOrderLine.getLineNetAmt())
				.build();
	}

	@Override
	public void saveGroup(@NonNull final Group group)
	{
		final GroupId groupId = group.getGroupId();
		final OrderLinesStorage orderLinesStorage = retrieveOrderLinesStorage(groupId);
		saveGroup(group, orderLinesStorage);
	}

	public void saveGroup(@NonNull final Group group, final OrderLinesStorage orderLinesStorage)
	{
		final GroupId groupId = group.getGroupId();
		final int orderId = groupId.getDocumentIdAssumingTableName(I_C_Order.Table_Name);
		final I_C_Order order = load(orderId, I_C_Order.class);
		assertOrderNotProcessed(order);

		// Save compensation lines
		final Set<Integer> savedOrderLineIds = new HashSet<>();
		for (final GroupCompensationLine compensationLine : group.getCompensationLines())
		{
			int orderLineId = compensationLine.getRepoId();
			I_C_OrderLine compensationLinePO = orderLinesStorage.getOrderLineByIdIfPresent(orderLineId);
			if (compensationLinePO == null)
			{
				// new line or missing line(in case orderLineId > 0)
				final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
				compensationLinePO = orderLineBL.createOrderLine(order);
			}

			updateOrderLineFromCompensationLine(compensationLinePO, compensationLine, groupId);
			orderLinesStorage.save(compensationLinePO);

			orderLineId = compensationLinePO.getC_OrderLine_ID();
			compensationLine.setRepoId(orderLineId);
			savedOrderLineIds.add(orderLineId);
		}

		// Remove remaining lines
		orderLinesStorage.deleteAllNotIn(savedOrderLineIds);
	}

	private void assertOrderNotProcessed(final I_C_Order order)
	{
		Check.assume(!order.isProcessed(), "order not processed: {}", order);
	}

	/**
	 * note to dev: keep in sync with {@link #createCompensationLine(I_C_OrderLine)}
	 */
	private void updateOrderLineFromCompensationLine(final I_C_OrderLine compensationLinePO, final GroupCompensationLine compensationLine, final GroupId groupId)
	{
		compensationLinePO.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		compensationLinePO.setIsGroupCompensationLine(true);
		compensationLinePO.setGroupCompensationType(compensationLine.getType().getAdRefListValue());
		compensationLinePO.setGroupCompensationAmtType(compensationLine.getAmtType().getAdRefListValue());
		compensationLinePO.setGroupCompensationPercentage(compensationLine.getPercentage());
		compensationLinePO.setGroupCompensationBaseAmt(compensationLine.getBaseAmt());

		compensationLinePO.setM_Product_ID(compensationLine.getProductId());
		compensationLinePO.setC_UOM_ID(compensationLine.getUomId());

		compensationLinePO.setQtyEntered(compensationLine.getQty());
		compensationLinePO.setQtyOrdered(compensationLine.getQty());
		compensationLinePO.setIsManualPrice(true);
		compensationLinePO.setPriceEntered(compensationLine.getPrice());
		compensationLinePO.setPriceActual(compensationLine.getPrice());
	}

	@Override
	public Group retrieveOrCreateGroup(final RetrieveOrCreateGroupRequest request)
	{
		final List<I_C_OrderLine> orderLines = retrieveC_OrderLines(request.getOrderLineIds());
		Check.assumeNotEmpty(orderLines, "orderLines is not empty");

		final List<GroupId> groupIds = orderLines.stream()
				.map(OrderGroupRepository::extractGroupIdOrNull)
				.distinct()
				.collect(Collectors.toList());

		if (groupIds.size() > 1)
		{
			throw new AdempiereException("Mixed groups not allowed")
					.setParameter("groupIds", groupIds);
		}
		else if (groupIds.isEmpty() || groupIds.get(0) == null)
		{
			return createNewGroupFromOrderLines(orderLines, request.getNewGroupTemplate());
		}
		else
		{
			final GroupId groupId = groupIds.get(0);
			// NOTE: it's not enough to create the Group using currently loaded orderLines because those might not be the all of them
			return retrieveGroup(groupId);
		}
	}

	private Group createNewGroupFromOrderLines(@NonNull final List<I_C_OrderLine> orderLines, @NonNull final GroupTemplate newGroupTemplate)
	{
		Check.assumeNotEmpty(orderLines, "orderLines is not empty");
		orderLines.forEach(OrderGroupCompensationUtils::assertNotInGroup);

		final int orderId = extractOrderId(orderLines);
		final I_C_Order order = load(orderId, I_C_Order.class);
		assertOrderNotProcessed(order);

		final GroupId groupId = createNewGroupId(orderId, newGroupTemplate);
		setGroupIdToLines(orderLines, groupId);

		return createGroupFromOrderLines(orderLines);
	}

	private static int extractOrderId(final List<? extends I_C_OrderLine> orderLines)
	{
		return orderLines.stream()
				.map(I_C_OrderLine::getC_Order_ID)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("All order lines shall be from same order")));
	}

	public static int extractOrderIdFromGroups(final List<Group> groups)
	{
		return groups.stream()
				.map(group -> group.getGroupId().getDocumentIdAssumingTableName(I_C_Order.Table_Name))
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("All groups shall be from same order")));
	}

	private List<I_C_OrderLine> retrieveC_OrderLines(final Collection<Integer> orderLineIds)
	{
		if (orderLineIds.isEmpty())
		{
			return ImmutableList.of();
		}
		final List<I_C_OrderLine> regularOrderLines = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addInArrayFilter(I_C_OrderLine.COLUMN_C_OrderLine_ID, orderLineIds)
				.create()
				.list(I_C_OrderLine.class);
		return regularOrderLines;
	}

	private static final GroupId createNewGroupId(final int orderId, final GroupTemplate template)
	{
		final I_C_Order_CompensationGroup groupPO = newInstance(I_C_Order_CompensationGroup.class);
		groupPO.setC_Order_ID(orderId);
		groupPO.setName(template.getName());
		if (template.getProductCategoryId() > 0)
		{
			groupPO.setM_Product_Category_ID(template.getProductCategoryId());
		}
		if (template.getId() > 0)
		{
			groupPO.setC_CompensationGroup_Schema_ID(template.getId());
		}
		InterfaceWrapperHelper.save(groupPO);

		return GroupId.of(I_C_Order.Table_Name, orderId, groupPO.getC_Order_CompensationGroup_ID());
	}

	private static final void setGroupIdToLines(final List<I_C_OrderLine> regularOrderLines, final GroupId groupId)
	{
		regularOrderLines.forEach(regularLinePO -> {
			if (groupId != null)
			{
				regularLinePO.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
			}
			else
			{
				regularLinePO.setC_Order_CompensationGroup_ID(-1);
			}
			save(regularLinePO);
		});
	}

	public void destroyGroup(final Group group)
	{
		group.getGroupId().assertDocumentTableName(I_C_Order.Table_Name);

		if (group.hasCompensationLines())
		{
			throw new AdempiereException("Cannot destroy compensation group if there are compensation lines: " + group);
		}

		final List<Integer> orderLineIds = group.getRegularLines().stream()
				.map(GroupRegularLine::getRepoId)
				.filter(orderLineId -> orderLineId > 0)
				.collect(ImmutableList.toImmutableList());

		final List<I_C_OrderLine> orderLines = retrieveC_OrderLines(orderLineIds);

		setGroupIdToLines(orderLines, null);

		final I_C_Order_CompensationGroup orderCompensationGroup = load(group.getGroupId().getOrderCompensationGroupId(), I_C_Order_CompensationGroup.class);
		delete(orderCompensationGroup);
	}

	public Group createPartialGroupFromCompensationLine(final I_C_OrderLine compensationLinePO)
	{
		OrderGroupCompensationUtils.assertCompensationLine(compensationLinePO);

		final GroupCompensationLine compensationLine = createCompensationLine(compensationLinePO);
		final GroupRegularLine aggregatedRegularLine = GroupRegularLine.builder()
				.lineNetAmt(compensationLine.getBaseAmt())
				.build();

		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final I_C_Order order = compensationLinePO.getC_Order();
		final int precision = orderBL.getPrecision(order);

		return Group.builder()
				.groupId(extractGroupId(compensationLinePO))
				.precision(precision)
				.bpartnerId(order.getC_BPartner_ID())
				.isSOTrx(order.isSOTrx())
				.regularLine(aggregatedRegularLine)
				.compensationLine(compensationLine)
				.build();
	}

	public static final class OrderLinesStorage
	{
		@Getter
		private final GroupId groupId;
		private final boolean performDatabaseChanges;
		private final ImmutableMap<Integer, I_C_OrderLine> orderLinesById;

		@Builder
		private OrderLinesStorage(
				@NonNull final GroupId groupId,
				@NonNull @Singular final List<I_C_OrderLine> orderLines,
				@NonNull final Boolean performDatabaseChanges)
		{
			this.groupId = groupId;
			this.performDatabaseChanges = performDatabaseChanges;
			orderLinesById = orderLines.stream()
					.peek(OrderGroupCompensationUtils::assertCompensationLine)
					.collect(GuavaCollectors.toImmutableMapByKey(I_C_OrderLine::getC_OrderLine_ID));
		}

		public void save(final I_C_OrderLine compensationLinePO)
		{
			Check.assume(!compensationLinePO.isProcessed(), "Changing a processed line is not allowed: {}", compensationLinePO); // shall not happen

			setGroupNoToOrderLine(compensationLinePO);

			if (performDatabaseChanges)
			{
				InterfaceWrapperHelper.save(compensationLinePO);
			}
		}

		private void setGroupNoToOrderLine(final I_C_OrderLine compensationLinePO)
		{
			if (compensationLinePO.getC_Order_CompensationGroup_ID() <= 0)
			{
				compensationLinePO.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
			}
			else if (compensationLinePO.getC_Order_CompensationGroup_ID() != groupId.getOrderCompensationGroupId())
			{
				throw new AdempiereException("Order line has already another groupNo set: " + compensationLinePO)
						.setParameter("expectedGroupId", groupId.getOrderCompensationGroupId())
						.appendParametersToMessage();
			}
		}

		public I_C_OrderLine getOrderLineByIdIfPresent(final int orderLineId)
		{
			return orderLinesById.get(orderLineId);
		}

		public void deleteAllNotIn(final Collection<Integer> orderIdsToSkipDeleting)
		{
			if (!performDatabaseChanges)
			{
				return;
			}

			final List<I_C_OrderLine> orderLinesToDelete = orderLinesById.values()
					.stream()
					.filter(orderLine -> !orderIdsToSkipDeleting.contains(orderLine.getC_OrderLine_ID()))
					.collect(ImmutableList.toImmutableList());
			orderLinesToDelete.forEach(InterfaceWrapperHelper::delete);
		}
	}
}
