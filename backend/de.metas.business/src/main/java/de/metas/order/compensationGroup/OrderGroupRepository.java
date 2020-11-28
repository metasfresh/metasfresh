package de.metas.order.compensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.MutableInt;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.eevolution.api.ProductBOMId;
import org.springframework.stereotype.Component;

import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.Group.GroupBuilder;
import de.metas.product.ProductId;
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

	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory;

	private final ImmutableList<OrderGroupRepositoryAdvisor> advisors;

	private static ModelDynAttributeAccessor<I_C_OrderLine, Boolean> ATTR_IsRepoUpdate = new ModelDynAttributeAccessor<>(
			OrderGroupRepository.class.getName(),
			"IsRepoUpdate",
			Boolean.class);

	public OrderGroupRepository(
			final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory,
			final Optional<List<OrderGroupRepositoryAdvisor>> advisors)
	{
		this.compensationLineCreateRequestFactory = compensationLineCreateRequestFactory;
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
			OrderId orderId = OrderId.ofRepoId(orderLine.getC_Order_ID());
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
				.map(map -> extractGroupIdOrNull(map))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static final GroupId extractGroupIdOrNull(final Map<String, Object> map)
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
	public GroupCreator prepareNewGroup()
	{
		return new GroupCreator(this, compensationLineCreateRequestFactory);
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
		return ATTR_IsRepoUpdate.isSet(orderLine);
	}

	private Group createGroupFromOrderLines(final List<I_C_OrderLine> groupOrderLines)
	{
		Check.assumeNotEmpty(groupOrderLines, "groupOrderLines is not empty");

		final GroupId groupId = extractSingleGroupId(groupOrderLines);

		final I_C_OrderLine groupFirstOrderLine = groupOrderLines.get(0);
		final I_C_Order order = groupFirstOrderLine.getC_Order();
		final I_C_Order_CompensationGroup orderCompensationGroupPO = groupFirstOrderLine.getC_Order_CompensationGroup();
		final IOrderBL orderBL = Services.get(IOrderBL.class);

		final GroupBuilder groupBuilder = Group.builder()
				.groupId(groupId)
				.groupTemplateId(GroupTemplateId.ofRepoIdOrNull(orderCompensationGroupPO.getC_CompensationGroup_Schema_ID()))
				.pricePrecision(orderBL.getPricePrecision(order))
				.amountPrecision(orderBL.getAmountPrecision(order))
				.bpartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()));

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
				.groupTemplateLineId(GroupTemplateLineId.ofRepoIdOrNull(groupOrderLine.getC_CompensationGroup_SchemaLine_ID()))
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
		final OrderId orderId = extractOrderIdFromGroupId(groupId);
		final I_C_Order order = Services.get(IOrderDAO.class).getById(orderId);
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
		compensationLinePO.setGroupCompensationPercentage(compensationLine.getPercentage() != null ? compensationLine.getPercentage().toBigDecimal() : null);
		compensationLinePO.setGroupCompensationBaseAmt(compensationLine.getBaseAmt());

		compensationLinePO.setM_Product_ID(compensationLine.getProductId().getRepoId());

		final Quantity qtyEntered = Quantitys.create(compensationLine.getQtyEntered(), compensationLine.getUomId());
		compensationLinePO.setC_UOM_ID(qtyEntered.getUomId().getRepoId());
		compensationLinePO.setQtyEntered(qtyEntered.toBigDecimal());

		final Quantity qtyOrdered = uomConversionBL.convertToProductUOM(qtyEntered, compensationLine.getProductId());
		compensationLinePO.setQtyOrdered(qtyOrdered.toBigDecimal());

		compensationLinePO.setIsManualPrice(true);
		compensationLinePO.setPriceEntered(compensationLine.getPrice());
		compensationLinePO.setPriceActual(compensationLine.getPrice());

		compensationLinePO.setC_CompensationGroup_SchemaLine_ID(GroupTemplateLineId.toRepoId(compensationLine.getGroupTemplateLineId()));

		Services.get(IOrderLineBL.class).updateLineNetAmtFromQtyEntered(compensationLinePO);
	}

	@Override
	public Group retrieveOrCreateGroup(final RetrieveOrCreateGroupRequest request)
	{
		final List<I_C_OrderLine> orderLines = retrieveOrderLineRecords(request.getOrderLineIds());
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

		final OrderId orderId = extractOrderId(orderLines);
		final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
		final I_C_Order order = ordersRepo.getById(orderId);
		assertOrderNotProcessed(order);

		final GroupId groupId = createNewGroupId(GroupCreateRequest.builder()
				.orderId(orderId)
				.name(newGroupTemplate.getName())
				.productCategoryId(newGroupTemplate.getProductCategoryId())
				.groupTemplateId(newGroupTemplate.getId())
				.build());
		setGroupIdToLines(orderLines, groupId);

		return createGroupFromOrderLines(orderLines);
	}

	private static OrderId extractOrderId(final List<? extends I_C_OrderLine> orderLines)
	{
		return orderLines.stream()
				.map(I_C_OrderLine::getC_Order_ID)
				.map(OrderId::ofRepoId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("All order lines shall be from same order")));
	}

	public static OrderId extractOrderIdFromGroups(final List<Group> groups)
	{
		return groups.stream()
				.map(group -> extractOrderIdFromGroup(group))
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
		if (request.getProductCategoryId() != null)
		{
			groupPO.setM_Product_Category_ID(request.getProductCategoryId().getRepoId());
		}
		if (request.getGroupTemplateId() != null)
		{
			groupPO.setC_CompensationGroup_Schema_ID(request.getGroupTemplateId().getRepoId());
		}
		saveRecord(groupPO);

		return createGroupId(request.getOrderId(), groupPO.getC_Order_CompensationGroup_ID());
	}

	private static final void setGroupIdToLines(
			@NonNull final List<I_C_OrderLine> regularOrderLines,
			@Nullable final GroupId groupId)
	{
		for (final I_C_OrderLine regularLinePO : regularOrderLines)
		{
			if (groupId != null)
			{
				regularLinePO.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
			}
			else
			{
				regularLinePO.setC_Order_CompensationGroup_ID(-1);
			}
			saveRecord(regularLinePO);
		}
	}

	public void destroyGroup(final Group group)
	{
		assertOrderGroupId(group.getGroupId());

		if (group.hasCompensationLines())
		{
			throw new AdempiereException("Cannot destroy compensation group if there are compensation lines: " + group);
		}

		final List<OrderLineId> orderLineIds = group.getRegularLines().stream()
				.map(GroupRegularLine::getRepoId)
				.map(OrderLineId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		final List<I_C_OrderLine> orderLines = retrieveOrderLineRecords(orderLineIds);

		setGroupIdToLines(orderLines, null);

		final I_C_Order_CompensationGroup orderCompensationGroup = retrieveGroupRecord(group.getGroupId());
		delete(orderCompensationGroup);
	}

	private I_C_Order_CompensationGroup retrieveGroupRecord(final GroupId groupId)
	{
		assertOrderGroupId(groupId);
		return load(groupId.getOrderCompensationGroupId(), I_C_Order_CompensationGroup.class);
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

		return Group.builder()
				.groupId(extractGroupId(compensationLinePO))
				.pricePrecision(orderBL.getPricePrecision(order))
				.amountPrecision(orderBL.getAmountPrecision(order))
				.bpartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
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
				ATTR_IsRepoUpdate.setValue(compensationLinePO, Boolean.TRUE);
				try
				{
					saveRecord(compensationLinePO);
				}
				finally
				{
					ATTR_IsRepoUpdate.reset(compensationLinePO);
				}
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
			orderLinesToDelete.forEach(this::deleteOrderLineRecord);
		}

		private void deleteOrderLineRecord(final I_C_OrderLine orderLine)
		{
			ATTR_IsRepoUpdate.setValue(orderLine, Boolean.TRUE);
			try
			{
				delete(orderLine);
			}
			finally
			{
				ATTR_IsRepoUpdate.reset(orderLine);
			}
		}
	}

	public void renumberOrderLinesForOrderId(@NonNull final OrderId orderId)
	{
		final List<I_C_OrderLine> allOrderLines = Services.get(IOrderDAO.class).retrieveOrderLines(orderId)
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

		final Consumer<Collection<I_C_OrderLine>> orderLinesSequenceUpdater = orderLines -> orderLines.stream()
				.sorted(Comparator.<I_C_OrderLine, Integer> comparing(orderLine -> !orderLine.isGroupCompensationLine() ? 0 : 1)
						.thenComparing(orderLine -> OrderGroupCompensationUtils.isGeneratedCompensationLine(orderLine) ? 0 : 1)
						.thenComparing(I_C_OrderLine::getLine)
						.thenComparing(I_C_OrderLine::getC_OrderLine_ID))
				.forEach(orderLineSequenceUpdater);

		//
		// Renumber grouped order lines first
		orderLinesByGroupId
				.asMap()
				.values()
				.forEach(orderLinesSequenceUpdater);

		//
		// Remaining ungrouped order lines
		notGroupedOrderLines.forEach(orderLineSequenceUpdater);
	}

	public OrderGroupInfo getGroupInfoById(@NonNull final GroupId groupId)
	{
		final I_C_Order_CompensationGroup groupRecord = retrieveGroupRecord(groupId);
		return OrderGroupInfo.builder()
				.groupId(groupId)
				.name(groupRecord.getName())
				.bomId(ProductBOMId.optionalOfRepoId(groupRecord.getPP_Product_BOM_ID()))
				.build();
	}

	public void setGroupProductBOMId(@NonNull final GroupId groupId, @NonNull ProductBOMId bomId)
	{
		final I_C_Order_CompensationGroup groupRecord = retrieveGroupRecord(groupId);
		groupRecord.setPP_Product_BOM_ID(bomId.getRepoId());
		saveRecord(groupRecord);
	}
}
