package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.Group.GroupBuilder;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.MutableInt;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.eevolution.api.ProductBOMId;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory;

	private final ImmutableList<OrderGroupRepositoryAdvisor> advisors;

	private final ThreadLocal<OrderIdsToRenumber> currentOrderIdsToRenumberThreadLocal = new ThreadLocal<>();

	public OrderGroupRepository(
			final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory,
			final Optional<List<OrderGroupRepositoryAdvisor>> advisors)
	{
		this.compensationLineCreateRequestFactory = compensationLineCreateRequestFactory;
		this.advisors = ImmutableList.copyOf(advisors.orElse(ImmutableList.of()));
	}

	@Nullable
	public static GroupId extractGroupId(@NonNull final I_C_OrderLine orderLine)
	{
		OrderGroupCompensationUtils.assertInGroup(orderLine);
		return extractGroupIdOrNull(orderLine);
	}

	@Nullable
	public static GroupId extractGroupIdOrNull(@NonNull final I_C_OrderLine orderLine)
	{
		if (OrderGroupCompensationUtils.isInGroup(orderLine))
		{
			final OrderId orderId = OrderId.ofRepoId(orderLine.getC_Order_ID());
			return createGroupId(orderId, orderLine.getC_Order_CompensationGroup_ID());
		}
		else
		{
			return null;
		}
	}

	public static GroupId extractSingleGroupId(final Collection<I_C_OrderLine> orderLines)
	{
		Check.assumeNotEmpty(orderLines, "orderLines is not empty");
		return orderLines.stream()
				.map(OrderGroupRepository::extractGroupId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Order lines are not part of the same group: " + orderLines)));
	}

	public GroupId extractSingleGroupIdFromOrderLineIds(final Collection<OrderLineId> orderLineIds)
	{
		final Set<GroupId> groupIds = extractGroupIdsFromOrderLineIds(orderLineIds);
		return CollectionUtils.singleElement(groupIds);
	}

	public Set<GroupId> extractGroupIdsFromOrderLineIds(final Collection<OrderLineId> orderLineIds)
	{
		if (orderLineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addInArrayFilter(I_C_OrderLine.COLUMN_C_OrderLine_ID, orderLineIds)
				.create()
				.listDistinct(I_C_OrderLine.COLUMNNAME_C_Order_ID, I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID)
				.stream()
				.map(OrderGroupRepository::extractGroupIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	private static GroupId extractGroupIdOrNull(final Map<String, Object> map)
	{
		final int orderCompensationGroupId = NumberUtils.asInt(map.get(I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID), -1);
		if (orderCompensationGroupId <= 0)
		{
			return null;
		}

		final OrderId orderId = OrderId.ofRepoIdOrNull(NumberUtils.asInt(map.get(I_C_OrderLine.COLUMNNAME_C_Order_ID), -1));
		if (orderId == null)
		{
			// shall not happen
			return null;
		}

		return createGroupId(orderId, orderCompensationGroupId);
	}

	@Override
	public GroupCreator.GroupCreatorBuilder prepareNewGroup()
	{
		return GroupCreator.builder()
				.groupsRepo(this)
				.compensationLineCreateRequestFactory(compensationLineCreateRequestFactory);
	}

	public static GroupId createGroupId(final OrderId orderId, final int orderCompensationGroupId)
	{
		return GroupId.of(I_C_Order.Table_Name, orderId.getRepoId(), orderCompensationGroupId);
	}

	private static void assertOrderGroupId(@NonNull final GroupId groupId)
	{
		groupId.assertDocumentTableName(I_C_Order.Table_Name);
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

	@Nullable
	public Group retrieveGroupIfExists(@NonNull final GroupId groupId)
	{
		assertOrderGroupId(groupId);

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

	public static boolean isRepositoryUpdate(final I_C_OrderLine orderLine)
	{
		return OrderLinesStorage.isRepositoryUpdate(orderLine);
	}

	private Group createGroupFromOrderLines(final List<I_C_OrderLine> groupOrderLines)
	{
		Check.assumeNotEmpty(groupOrderLines, "groupOrderLines is not empty");

		final GroupId groupId = extractSingleGroupId(groupOrderLines);

		final I_C_OrderLine groupFirstOrderLine = groupOrderLines.get(0);
		final I_C_Order order = groupFirstOrderLine.getC_Order();
		final I_C_Order_CompensationGroup orderCompensationGroupPO = groupFirstOrderLine.getC_Order_CompensationGroup();

		final GroupBuilder groupBuilder = Group.builder()
				.groupId(groupId)
				.groupTemplateId(GroupTemplateId.ofRepoIdOrNull(orderCompensationGroupPO.getC_CompensationGroup_Schema_ID()))
				.activityId(ActivityId.ofRepoIdOrNull(orderCompensationGroupPO.getC_Activity_ID()))
				.pricePrecision(orderBL.getPricePrecision(order))
				.amountPrecision(orderBL.getAmountPrecision(order))
				.bpartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()));

		for (final I_C_OrderLine groupOrderLine : groupOrderLines)
		{
			if (!groupOrderLine.isGroupCompensationLine())
			{
				final GroupRegularLine regularLine = toGroupRegularLine(groupOrderLine);
				groupBuilder.regularLine(regularLine);
			}
			else
			{
				final GroupCompensationLine compensationLine = toGroupCompensationLine(groupOrderLine);
				groupBuilder.compensationLine(compensationLine);
			}
		}

		advisors.forEach(advisor -> advisor.customizeFromOrder(groupBuilder, order, groupOrderLines));

		return groupBuilder.build();
	}

	private List<I_C_OrderLine> retrieveGroupOrderLines(final GroupId groupId)
	{
		return retrieveGroupOrderLinesQuery(groupId)
				.create()
				.list(I_C_OrderLine.class);
	}

	public IQueryBuilder<I_C_OrderLine> retrieveGroupOrderLinesQuery(@NonNull final GroupId groupId)
	{
		final OrderId orderId = extractOrderIdFromGroupId(groupId);
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

		final Group group = retrieveGroup(groupId);

		final ActivityId groupActivityId = group.getActivityId();

		return OrderLinesStorage.builder()
				.groupId(groupId)
				.activityId(groupActivityId)
				.orderLines(orderLines)
				.performDatabaseChanges(true)
				.build();
	}

	public final OrderLinesStorage createNotSaveableSingleOrderLineStorage(@NonNull final I_C_OrderLine compensationLinePO)
	{
		return OrderLinesStorage.builder()
				.groupId(extractGroupId(compensationLinePO))
				.activityId(ActivityId.ofRepoIdOrNull(compensationLinePO.getC_Activity_ID()))
				.orderLine(compensationLinePO)
				.performDatabaseChanges(false)
				.build();
	}

	private static GroupRegularLine toGroupRegularLine(final I_C_OrderLine record)
	{
		return GroupRegularLine.builder()
				.repoId(OrderLineId.ofRepoId(record.getC_OrderLine_ID()))
				.lineNetAmt(record.getLineNetAmt())
				.build();
	}

	/**
	 * note to dev: keep in sync with {@link #updateOrderLineFromCompensationLine(I_C_OrderLine, GroupCompensationLine, GroupId)}
	 */
	private static GroupCompensationLine toGroupCompensationLine(final I_C_OrderLine groupOrderLine)
	{
		return GroupCompensationLine.builder()
				.repoId(OrderLineId.ofRepoId(groupOrderLine.getC_OrderLine_ID()))
				.groupTemplateLineId(OrderGroupCompensationUtils.extractGroupTemplateLineId(groupOrderLine))
				.seqNo(groupOrderLine.getLine())
				.productId(ProductId.ofRepoId(groupOrderLine.getM_Product_ID()))
				.qtyEntered(groupOrderLine.getQtyEntered())
				.uomId(UomId.ofRepoId(groupOrderLine.getC_UOM_ID()))
				.type(GroupCompensationType.ofAD_Ref_List_Value(groupOrderLine.getGroupCompensationType()))
				.amtType(GroupCompensationAmtType.ofAD_Ref_List_Value(groupOrderLine.getGroupCompensationAmtType()))
				.percentage(Percent.of(groupOrderLine.getGroupCompensationPercentage()))
				.baseAmt(groupOrderLine.getGroupCompensationBaseAmt())
				.price(groupOrderLine.getPriceEntered())
				.lineNetAmt(groupOrderLine.getLineNetAmt())
				.manualCompensationLinePosition(ManualCompensationLinePosition.ofNullableCode(groupOrderLine.getManualCompensationLinePosition()))
				.build();
	}

	@Override
	public void saveGroup(@NonNull final Group group)
	{
		final GroupId groupId = group.getGroupId();
		final OrderLinesStorage orderLinesStorage = retrieveOrderLinesStorage(groupId);
		saveGroup(group, orderLinesStorage);
	}

	void saveGroup(@NonNull final Group group, final OrderLinesStorage orderLinesStorage)
	{
		final GroupId groupId = group.getGroupId();
		final OrderId orderId = extractOrderIdFromGroupId(groupId);
		final I_C_Order order = orderDAO.getById(orderId);
		assertOrderNotProcessed(order);

		// Save compensation lines
		final Set<OrderLineId> savedOrderLineIds = new HashSet<>();
		for (final GroupCompensationLine compensationLine : group.getCompensationLines())
		{
			OrderLineId orderLineId = extractOrderLineId(compensationLine);
			I_C_OrderLine compensationLinePO = orderLinesStorage.getOrderLineByIdIfPresent(orderLineId);
			if (compensationLinePO == null)
			{
				// new line or missing line(in case orderLineId > 0)
				compensationLinePO = orderLineBL.createOrderLine(order);
			}

			updateOrderLineFromCompensationLine(compensationLinePO, compensationLine, groupId);
			orderLinesStorage.save(compensationLinePO);

			orderLineId = OrderLineId.ofRepoId(compensationLinePO.getC_OrderLine_ID());
			compensationLine.setRepoId(orderLineId);
			savedOrderLineIds.add(orderLineId);
		}

		// Remove remaining lines
		orderLinesStorage.deleteAllNotIn(savedOrderLineIds);
	}

	private static OrderLineId extractOrderLineId(final GroupCompensationLine compensationLine)
	{
		return (OrderLineId)compensationLine.getRepoId();
	}

	private void assertOrderNotProcessed(final I_C_Order order)
	{
		Check.assume(!order.isProcessed(), "order not processed: {}", order);
	}

	/**
	 * note to dev: keep in sync with {@link #toGroupCompensationLine(I_C_OrderLine)}
	 */
	private void updateOrderLineFromCompensationLine(final I_C_OrderLine compensationLinePO,
													 final GroupCompensationLine compensationLine,
													 final GroupId groupId)
	{
		compensationLinePO.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		compensationLinePO.setIsGroupCompensationLine(true);
		compensationLinePO.setGroupCompensationType(compensationLine.getType().getAdRefListValue());
		compensationLinePO.setGroupCompensationAmtType(compensationLine.getAmtType().getAdRefListValue());
		compensationLinePO.setGroupCompensationPercentage(compensationLine.getPercentage() != null ? compensationLine.getPercentage().toBigDecimal() : null);
		compensationLinePO.setGroupCompensationBaseAmt(compensationLine.getBaseAmt());
		compensationLinePO.setManualCompensationLinePosition(ManualCompensationLinePosition.toCode(compensationLine.getManualCompensationLinePosition()));

		compensationLinePO.setM_Product_ID(compensationLine.getProductId().getRepoId());

		final Quantity qtyEntered = Quantitys.of(compensationLine.getQtyEntered(), compensationLine.getUomId());
		compensationLinePO.setC_UOM_ID(qtyEntered.getUomId().getRepoId());
		compensationLinePO.setQtyEntered(qtyEntered.toBigDecimal());

		final Quantity qtyOrdered = uomConversionBL.convertToProductUOM(qtyEntered, compensationLine.getProductId());
		compensationLinePO.setQtyOrdered(qtyOrdered.toBigDecimal());

		compensationLinePO.setIsManualPrice(true);
		compensationLinePO.setPriceEntered(compensationLine.getPrice());
		compensationLinePO.setPriceActual(compensationLine.getPrice());

		compensationLinePO.setC_CompensationGroup_SchemaLine_ID(GroupTemplateLineId.toRepoId(compensationLine.getGroupTemplateLineId()));

		orderLineBL.updateLineNetAmtFromQtyEntered(compensationLinePO);
	}

	@Override
	public Group retrieveOrCreateGroup(final RetrieveOrCreateGroupRequest request)
	{
		final List<I_C_OrderLine> orderLines = retrieveOrderLineRecords(request.getOrderLineIds());

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
			return createNewGroupFromOrderLines(
					request.getOrderId(),
					orderLines,
					request.getNewGroupTemplate(),
					request.getNewContractConditionsId(),
					request.getGroupCompensationOrderBy());
		}
		else
		{
			final GroupId groupId = groupIds.get(0);
			// NOTE: it's not enough to create the Group using currently loaded orderLines because those might not be the all of them
			return retrieveGroup(groupId);
		}
	}

	private Group createNewGroupFromOrderLines(
			@Nullable final OrderId expectedOrderId,
			@NonNull final List<I_C_OrderLine> existingRegularOrderLines,
			@NonNull final GroupTemplate newGroupTemplate,
			@Nullable final ConditionsId contractConditionsId,
			@Nullable final GroupCompensationOrderBy groupCompensationOrderBy)
	{
		existingRegularOrderLines.forEach(OrderGroupCompensationUtils::assertNotInGroup);

		final OrderId orderId = extractOrderId(existingRegularOrderLines, expectedOrderId);
		final I_C_Order order = orderDAO.getById(orderId);
		assertOrderNotProcessed(order);

		final ArrayList<I_C_OrderLine> allRegularOrderLines = new ArrayList<>(existingRegularOrderLines);

		for (final GroupTemplateRegularLine regularLineToAdd : newGroupTemplate.getRegularLinesToAdd())
		{
			if (!regularLineToAdd.isMatching(contractConditionsId))
			{
				continue;
			}

			final I_C_OrderLine regularOrderLine = createRegularLineFromTemplate(regularLineToAdd, order, contractConditionsId);
			allRegularOrderLines.add(regularOrderLine);
		}

		if (allRegularOrderLines.isEmpty())
		{
			throw new AdempiereException("No regular order lines to group");
		}

		final GroupId groupId = createNewGroupId(GroupCreateRequest.builder()
														 .orderId(orderId)
														 .name(newGroupTemplate.getName())
														 .isNamePrinted(newGroupTemplate.isNamePrinted())
														 .activityId(newGroupTemplate.getActivityId())
														 .productCategoryId(newGroupTemplate.getProductCategoryId())
														 .groupTemplateId(newGroupTemplate.getId())
														 .groupCompensationOrderBy(groupCompensationOrderBy)
														 .build());

		setGroupIdToLines(allRegularOrderLines, groupId);
		setActivityToLines(allRegularOrderLines, newGroupTemplate.getActivityId());

		return createGroupFromOrderLines(allRegularOrderLines);
	}

	private static OrderId extractOrderId(
			@NonNull final List<? extends I_C_OrderLine> orderLines,
			@Nullable final OrderId expectedOrderId)
	{
		if (orderLines.isEmpty())
		{
			if (expectedOrderId != null)
			{
				return expectedOrderId;
			}
			else
			{
				throw new AdempiereException("Cannot determine the order ID when there are no order lines provided and no expectedOrderId");
			}
		}
		else
		{
			final OrderId determinedOrderId = extractOrderId(orderLines);
			if (expectedOrderId != null && !OrderId.equals(expectedOrderId, determinedOrderId))
			{
				throw new AdempiereException("Determined order ID from order lines is not matching the expected one")
						.setParameter("orderLines", orderLines)
						.setParameter("determinedOrderId", determinedOrderId)
						.setParameter("expectedOrderId", expectedOrderId)
						.appendParametersToMessage();
			}

			return determinedOrderId;
		}
	}

	private static OrderId extractOrderId(final List<? extends I_C_OrderLine> orderLines)
	{
		final ImmutableSet<OrderId> orderIds = orderLines.stream()
				.map(I_C_OrderLine::getC_Order_ID)
				.map(OrderId::ofRepoId)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());
		if (orderIds.isEmpty())
		{
			throw new AdempiereException("Cannot determine orderId from an empty list of order lines");
		}
		else if (orderIds.size() == 1)
		{
			return orderIds.iterator().next();
		}
		else
		{
			throw new AdempiereException("More than one orderId found for " + orderLines);
		}
	}

	public static OrderId extractOrderIdFromGroups(final List<Group> groups)
	{
		return groups.stream()
				.map(OrderGroupRepository::extractOrderIdFromGroup)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("All groups shall be from same order")));
	}

	public static OrderId extractOrderIdFromGroup(@NonNull final Group group)
	{
		return extractOrderIdFromGroupId(group.getGroupId());
	}

	public static OrderId extractOrderIdFromGroupId(@NonNull final GroupId groupId)
	{
		return OrderId.ofRepoId(groupId.getDocumentIdAssumingTableName(I_C_Order.Table_Name));
	}

	private List<I_C_OrderLine> retrieveOrderLineRecords(final Collection<OrderLineId> orderLineIds)
	{
		if (orderLineIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addInArrayFilter(I_C_OrderLine.COLUMN_C_OrderLine_ID, orderLineIds)
				.create()
				.list(I_C_OrderLine.class);
	}

	public final GroupId createNewGroupId(@NonNull final GroupCreateRequest request)
	{
		final I_C_Order_CompensationGroup groupPO = newInstance(I_C_Order_CompensationGroup.class);
		groupPO.setC_Order_ID(request.getOrderId().getRepoId());
		groupPO.setName(request.getName());
		groupPO.setIsNamePrinted(request.isNamePrinted());

		groupPO.setC_Activity_ID(ActivityId.toRepoId(request.getActivityId()));

		if (request.getProductCategoryId() != null)
		{
			groupPO.setM_Product_Category_ID(request.getProductCategoryId().getRepoId());
		}
		if (request.getGroupTemplateId() != null)
		{
			groupPO.setC_CompensationGroup_Schema_ID(request.getGroupTemplateId().getRepoId());
		}
		if (request.getGroupCompensationOrderBy() != null)
		{
			groupPO.setCompensationGroupOrderBy(request.getGroupCompensationOrderBy().getCode());
		}

		saveRecord(groupPO);

		return createGroupId(request.getOrderId(), groupPO.getC_Order_CompensationGroup_ID());
	}

	private static void setGroupIdToLines(
			@NonNull final List<I_C_OrderLine> orderLines,
			@Nullable final GroupId groupId)
	{
		//dev-note: needed to make sure `de.metas.activity.model.validator.C_OrderLine.updateActivity` doesn't fail
		final List<I_C_OrderLine> sortedOrderLines = orderLines.stream()
				.sorted(Comparator.comparing(I_C_OrderLine::isGroupCompensationLine))
				.collect(ImmutableList.toImmutableList());
		for (final I_C_OrderLine regularLinePO : sortedOrderLines)
		{
			if (groupId != null)
			{
				regularLinePO.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
			}
			else
			{
				regularLinePO.setC_Order_CompensationGroup_ID(-1);
			}
			save(regularLinePO);
		}
	}

	private static void setActivityToLines(
			@NonNull final List<I_C_OrderLine> orderLines,
			@Nullable final ActivityId activityId)
	{
		for (final I_C_OrderLine orderLine : orderLines)
		{
			orderLine.setC_Activity_ID(ActivityId.toRepoId(activityId));

			saveRecord(orderLine);
		}
	}

	public void destroyGroup(final Group group)
	{
		assertOrderGroupId(group.getGroupId());

		if (group.hasCompensationLines())
		{
			throw new AdempiereException("Cannot destroy compensation group if there are compensation lines: " + group);
		}

		final ImmutableSet<OrderLineId> orderLineIds = group.getRegularLines().stream()
				.map(GroupRegularLine::getRepoId)
				.filter(Objects::nonNull)
				.map(OrderLineId::cast)
				.collect(ImmutableSet.toImmutableSet());

		final List<I_C_OrderLine> orderLines = retrieveOrderLineRecords(orderLineIds);

		setGroupIdToLines(orderLines, null);

		setActivityToLines(orderLines, null);

		final I_C_Order_CompensationGroup orderCompensationGroup = retrieveGroupRecord(group.getGroupId());
		delete(orderCompensationGroup);
	}

	private I_C_Order_CompensationGroup retrieveGroupRecord(final GroupId groupId)
	{
		assertOrderGroupId(groupId);
		return load(groupId.getOrderCompensationGroupId(), I_C_Order_CompensationGroup.class);
	}

	public Group createPartialGroupFromCompensationLine(final I_C_OrderLine compensationLineRecord)
	{
		OrderGroupCompensationUtils.assertCompensationLine(compensationLineRecord);

		final GroupCompensationLine compensationLine = toGroupCompensationLine(compensationLineRecord);
		final GroupRegularLine aggregatedRegularLine = GroupRegularLine.builder()
				.lineNetAmt(compensationLine.getBaseAmt())
				.build();

		final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(compensationLineRecord.getC_Order_ID()));

		return Group.builder()
				.groupId(extractGroupId(compensationLineRecord))
				.pricePrecision(orderBL.getPricePrecision(order))
				.amountPrecision(orderBL.getAmountPrecision(order))
				.bpartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
				.regularLine(aggregatedRegularLine)
				.compensationLine(compensationLine)
				.build();
	}

	public class OrderIdsToRenumber implements IAutoCloseable
	{
		@Nullable private OrderIdsToRenumber parent;
		@NonNull private final AtomicBoolean closed = new AtomicBoolean(false);
		@NonNull final LinkedHashSet<OrderId> orderIds = new LinkedHashSet<>();

		private void open()
		{
			this.parent = currentOrderIdsToRenumberThreadLocal.get();
			currentOrderIdsToRenumberThreadLocal.set(this);
		}

		@Override
		public void close()
		{
			if (closed.getAndSet(true))
			{
				return; // already closed;
			}

			if (currentOrderIdsToRenumberThreadLocal.get() != this)
			{
				throw new AdempiereException("Invalid current collector. Expected " + this + " but got " + currentOrderIdsToRenumberThreadLocal.get());
			}

			flush();

			currentOrderIdsToRenumberThreadLocal.set(parent);
		}

		private void assertNotClosed()
		{
			if (closed.get())
			{
				throw new AdempiereException("Already closed");
			}
		}

		public void addOrderIds(final Collection<OrderId> newOrderIds)
		{
			assertNotClosed();
			this.orderIds.addAll(newOrderIds);
		}

		public void addOrderId(final OrderId newOrderId)
		{
			assertNotClosed();
			this.orderIds.add(newOrderId);
		}

		public void flush()
		{
			final ImmutableSet<OrderId> orderIdsToClear = ImmutableSet.copyOf(orderIds);
			orderIds.clear();
			if (orderIdsToClear.isEmpty())
			{
				return;
			}

			if (parent != null)
			{
				parent.addOrderIds(orderIdsToClear);
			}
			else
			{
				renumberOrderLinesForOrderIds(orderIdsToClear);
			}
		}
	}

	public OrderIdsToRenumber delayOrderLinesRenumbering()
	{
		final OrderIdsToRenumber orderIdsToRenumber = new OrderIdsToRenumber();
		orderIdsToRenumber.open();
		return orderIdsToRenumber;
	}

	@Nullable
	public OrderIdsToRenumber getOrderIdsScheduledForRenumbering()
	{
		return currentOrderIdsToRenumberThreadLocal.get();
	}

	public void scheduleOrderLinesRenumbering(@NonNull final OrderId orderId)
	{
		final OrderIdsToRenumber orderIdsToRenumber = getOrderIdsScheduledForRenumbering();
		if (orderIdsToRenumber != null)
		{
			orderIdsToRenumber.addOrderId(orderId);
		}
		else
		{
			renumberOrderLinesForOrderId(orderId);
		}
	}

	private void renumberOrderLinesForOrderIds(@NonNull final Set<OrderId> orderIds)
	{
		if (orderIds.isEmpty())
		{
			return;
		}

		final ImmutableListMultimap<OrderId, I_C_OrderLine> allOrderLinesByOrderId = Multimaps.index(
				orderDAO.retrieveOrderLinesByOrderIds(orderIds, I_C_OrderLine.class),
				orderLine -> OrderId.ofRepoId(orderLine.getC_Order_ID())
		);

		for (final OrderId orderId : allOrderLinesByOrderId.keySet())
		{
			final List<I_C_OrderLine> allOrderLines = allOrderLinesByOrderId.get(orderId)
					.stream()
					.sorted(Comparator.comparing(I_C_OrderLine::getLine))
					.collect(ImmutableList.toImmutableList());

			final ListMultimap<GroupId, I_C_OrderLine> orderLinesByGroupId = allOrderLines
					.stream()
					.filter(OrderGroupCompensationUtils::isInGroup)
					.collect(ImmutableListMultimap.toImmutableListMultimap(OrderGroupRepository::extractGroupId, Function.identity()));

			final List<I_C_OrderLine> notGroupedOrderLines = allOrderLines
					.stream()
					.filter(OrderGroupCompensationUtils::isNotInGroup)
					.collect(ImmutableList.toImmutableList());

			final MutableInt nextLineNo = new MutableInt(10);
			final Consumer<I_C_OrderLine> orderLineSequenceUpdater = orderLine -> {
				orderLine.setLine(nextLineNo.getValue());
				saveRecord(orderLine);
				nextLineNo.add(10);
			};

		final BiConsumer<GroupId, Collection<I_C_OrderLine>> orderLinesSequenceUpdater = (groupId, orderLines) -> {
			final GroupCompensationOrderBy orderBy = Optional
					.ofNullable(getGroupInfoById(groupId).getGroupCompensationOrderBy())
					.orElse(GroupCompensationOrderBy.CompensationGroupLast);

			orderLines
					.stream()
					.sorted(orderBy.getComparator()
									.thenComparing(OrderGroupRepository::extractCompensationLineOrderBy)
									.thenComparing(I_C_OrderLine::getLine)
									.thenComparing(I_C_OrderLine::getC_OrderLine_ID))
					.forEach(orderLineSequenceUpdater);
		};

			//
			// Renumber grouped order lines first
			orderLinesByGroupId
					.asMap()
					.forEach(orderLinesSequenceUpdater);

			//
			// Remaining ungrouped order lines
			notGroupedOrderLines.forEach(orderLineSequenceUpdater);
		}
	}

	public void renumberOrderLinesForOrderId(@NonNull final OrderId orderId)
	{
		renumberOrderLinesForOrderIds(ImmutableSet.of(orderId));
	}

	private static int extractCompensationLineOrderBy(@NonNull final I_C_OrderLine orderLine)
	{
		final boolean isGroupCompensationLine = orderLine.isGroupCompensationLine();
		if (!isGroupCompensationLine)
		{
			return 0;
		}
		else if (OrderGroupCompensationUtils.isManualLine(orderLine))
		{
			final ManualCompensationLinePosition manualCompensationLinePosition = ManualCompensationLinePosition.ofNullableCodeOrDefault(orderLine.getManualCompensationLinePosition());
			switch (manualCompensationLinePosition)
			{
				case BEFORE_GENERATED_COMPENSATION_LINES:
					return 10;
				case LAST:
					return 999;
				default:
					throw new AdempiereException("Unknown: " + manualCompensationLinePosition);
			}
		}
		else // generated compensation line
		{
			return 100;
		}
	}

	public OrderGroupInfo getGroupInfoById(@NonNull final GroupId groupId)
	{
		final I_C_Order_CompensationGroup groupRecord = retrieveGroupRecord(groupId);
		return OrderGroupInfo.builder()
				.groupId(groupId)
				.name(groupRecord.getName())
				.bomId(ProductBOMId.optionalOfRepoId(groupRecord.getPP_Product_BOM_ID()))
				.groupCompensationOrderBy(GroupCompensationOrderBy.ofCodeOrNull(groupRecord.getCompensationGroupOrderBy()))
				.build();
	}

	public void setGroupProductBOMId(@NonNull final GroupId groupId, @NonNull final ProductBOMId bomId)
	{
		final I_C_Order_CompensationGroup groupRecord = retrieveGroupRecord(groupId);
		groupRecord.setPP_Product_BOM_ID(bomId.getRepoId());
		saveRecord(groupRecord);
	}

	@Nullable
	public GroupTemplateId getGroupTemplateId(@NonNull final GroupId groupId)
	{
		return queryBL
				.createQueryBuilder(I_C_Order_CompensationGroup.class)
				.addEqualsFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Order_CompensationGroup_ID, groupId.getOrderCompensationGroupId())
				.andCollect(I_C_Order_CompensationGroup.COLUMNNAME_C_CompensationGroup_Schema_ID, I_C_CompensationGroup_Schema.class)
				.create()
				.firstIdOnly(GroupTemplateId::ofRepoIdOrNull);
	}

	public I_C_OrderLine createRegularLineFromTemplate(
			@NonNull final GroupTemplateRegularLine from,
			@NonNull final I_C_Order targetOrder,
			@Nullable final ConditionsId contractConditionsId)
	{
		final I_C_OrderLine orderLine = orderLineBL.createOrderLine(targetOrder);
		orderLine.setM_Product_ID(from.getProductId().getRepoId());
		orderLine.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		orderLine.setC_UOM_ID(from.getQty().getUomId().getRepoId());
		orderLine.setQtyEntered(from.getQty().toBigDecimal());
		orderLine.setC_CompensationGroup_Schema_TemplateLine_ID(from.getId().getRepoId());
		orderLine.setC_Flatrate_Conditions_ID(ConditionsId.toRepoId(contractConditionsId));
		orderLineBL.save(orderLine);

		return orderLine;
	}
}
