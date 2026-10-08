package de.metas.invoicecandidate.compensationGroup;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.Group.GroupBuilder;
import de.metas.order.compensationGroup.CompensationLineOrigin;
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupCompensationBase;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupCreator;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.GroupRegularLine;
import de.metas.order.compensationGroup.GroupRepository;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.ForUpdate;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Repository Tables: C_Invoice_Candidate, C_Order_CompensationGroup (read)
 * <p>
 * Repository Cluster: InvoiceCandidateGroupRepository, {@code IInvoiceCandDAO}/{@code InvoiceCandDAO}, {@link OrderGroupRepository} —
 * {@code InvoiceCandDAO} is the generic invoice-candidate DAO; this one handles the candidates of a compensation group
 * (group reference, lock and ungroup). {@link OrderGroupRepository} owns {@code C_Order_CompensationGroup}; this class only reads it.
 */
@Component
@RequiredArgsConstructor
public class InvoiceCandidateGroupRepository implements GroupRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);
	@NonNull private final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory;

	@NonNull private final OrderGroupRepository orderGroupRepository;

	@VisibleForTesting
	public static InvoiceCandidateGroupRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				InvoiceCandidateGroupRepository.class,
				() -> new InvoiceCandidateGroupRepository(
						new GroupCompensationLineCreateRequestFactory(),
						OrderGroupRepository.newInstanceForUnitTesting()));
	}

	@Override
	public GroupCreator.GroupCreatorBuilder prepareNewGroup()
	{
		return GroupCreator.builder()
				.groupsRepo(this)
				.compensationLineCreateRequestFactory(compensationLineCreateRequestFactory);
	}

	@Override
	public Group retrieveGroup(@NonNull final GroupId groupId)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = retrieveInvoiceCandidatesForGroup(groupId);
		if (invoiceCandidates.isEmpty())
		{
			throw new AdempiereException("No invoice candidates found for " + groupId);
		}

		final Group group = createGroupFromInvoiceCandidates(invoiceCandidates);
		if (!group.getGroupId().equals(groupId))
		{
			// shall not happen
			throw new AdempiereException("Invalid groupId for group " + group)
					.setParameter("expectedGroupId", groupId)
					.appendParametersToMessage();
		}
		return group;

	}

	private Group createGroupFromInvoiceCandidates(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final GroupId groupId = extractSingleGroupId(invoiceCandidates);

		final I_C_Order order = invoiceCandidates.get(0).getC_Order();
		if (order == null)
		{
			throw new AdempiereException("Invoice candidate has no order: " + invoiceCandidates);
		}

		final GroupBuilder groupBuilder = Group.builder()
				.groupId(groupId)
				.pricePrecision(orderBL.getPricePrecision(order))
				.amountPrecision(orderBL.getAmountPrecision(order))
				.bpartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
				.additive(orderGroupRepository.isAdditive(groupId));

		final Map<ProductId, ImmutableSet<ProductCategoryId>> productCategoryIdsByProductId =
				retrieveProductCategoryIdAndAncestorsByProductId(invoiceCandidates);

		final List<I_C_Invoice_Candidate> compensationLineCandidates = invoiceCandidates.stream()
				.filter(I_C_Invoice_Candidate::isGroupCompensationLine)
				.collect(ImmutableList.toImmutableList());
		final Map<InvoiceCandidateId, CompensationLineOrigin> originByInvoiceCandidateId =
				retrieveCompensationLineOriginsByInvoiceCandidateId(compensationLineCandidates);

		// the packing is needed only when a discount line is restricted to a packing-material category
		final boolean packingMaterialCategoryNeeded = originByInvoiceCandidateId.values().stream()
				.anyMatch(origin -> origin.getBase().getPackingMaterialProductCategoryId() != null);
		final Map<OrderLineId, ImmutableSet<ProductCategoryId>> packingMaterialCategoryIdsByOrderLineId = packingMaterialCategoryNeeded
				? retrievePackingMaterialProductCategoryIdsByOrderLineId(regularCandidates(invoiceCandidates))
				: ImmutableMap.of();

		for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidates)
		{
			if (!invoiceCandidate.isGroupCompensationLine())
			{
				final GroupRegularLine regularLine = createReqularLine(invoiceCandidate, productCategoryIdsByProductId, packingMaterialCategoryIdsByOrderLineId);
				groupBuilder.regularLine(regularLine);
			}
			else
			{
				final GroupCompensationLine compensationLine = createCompensationLine(invoiceCandidate, originByInvoiceCandidateId);
				groupBuilder.compensationLine(compensationLine);
			}
		}

		return groupBuilder.build();
	}

	/**
	 * @return product id -> that product's category id plus all ancestor category ids, for the regular (non-compensation) candidates
	 * (see {@link IProductDAO#getProductCategoryIdAndAncestorsByProductIds})
	 */
	private ImmutableMap<ProductId, ImmutableSet<ProductCategoryId>> retrieveProductCategoryIdAndAncestorsByProductId(
			final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final ImmutableSet<ProductId> productIds = invoiceCandidates.stream()
				.filter(invoiceCandidate -> !invoiceCandidate.isGroupCompensationLine())
				.map(invoiceCandidate -> ProductId.ofRepoId(invoiceCandidate.getM_Product_ID()))
				.collect(ImmutableSet.toImmutableSet());
		return productDAO.getProductCategoryIdAndAncestorsByProductIds(productIds);
	}

	private static List<I_C_Invoice_Candidate> regularCandidates(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		return invoiceCandidates.stream()
				.filter(invoiceCandidate -> !invoiceCandidate.isGroupCompensationLine())
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Batch-resolves, in one query for the order lines and one provider call for the whole group, the packing-material
	 * product categories (plus ancestors) of the regular candidates' order lines' packing instructions. The packing is
	 * the one of the order line, so it is the same on every partial invoice.
	 *
	 * @return order line id -> packing-material category ids; an order line without packing instruction is simply absent
	 */
	private ImmutableMap<OrderLineId, ImmutableSet<ProductCategoryId>> retrievePackingMaterialProductCategoryIdsByOrderLineId(
			@NonNull final List<I_C_Invoice_Candidate> regularCandidates)
	{
		final ImmutableSet<OrderLineId> orderLineIds = regularCandidates.stream()
				.map(invoiceCandidate -> OrderLineId.ofRepoIdOrNull(invoiceCandidate.getC_OrderLine_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (orderLineIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final List<I_C_OrderLine> orderLines = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addInArrayFilter(I_C_OrderLine.COLUMN_C_OrderLine_ID, orderLineIds)
				.create()
				.list();

		final Map<HUPIItemProductId, ImmutableSet<ProductCategoryId>> packingMaterialCategoryIdsByPIItemProductId =
				OrderGroupRepository.retrievePackingMaterialProductCategoryIdAndAncestorsByPIItemProductId(orderLines);
		if (packingMaterialCategoryIdsByPIItemProductId.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<OrderLineId, ImmutableSet<ProductCategoryId>> result = ImmutableMap.builder();
		for (final I_C_OrderLine orderLine : orderLines)
		{
			final HUPIItemProductId piItemProductId = OrderGroupRepository.extractPIItemProductId(orderLine);
			final ImmutableSet<ProductCategoryId> categoryIds = piItemProductId != null ? packingMaterialCategoryIdsByPIItemProductId.get(piItemProductId) : null;
			if (categoryIds != null)
			{
				result.put(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()), categoryIds);
			}
		}
		return result.build();
	}

	private GroupRegularLine createReqularLine(
			final I_C_Invoice_Candidate invoiceCandidate,
			final Map<ProductId, ImmutableSet<ProductCategoryId>> productCategoryIdsByProductId,
			final Map<OrderLineId, ImmutableSet<ProductCategoryId>> packingMaterialCategoryIdsByOrderLineId)
	{
		final ProductId productId = ProductId.ofRepoId(invoiceCandidate.getM_Product_ID());
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(invoiceCandidate.getC_OrderLine_ID());
		return GroupRegularLine.builder()
				// invoiced and to invoice now: a percent discount is computed on both, then its invoiced part is subtracted (see C_OrderLine_Handler#calculatePriceAndTax)
				.lineNetAmt(invoiceCandidate.getNetAmtToInvoice().add(invoiceCandidate.getNetAmtInvoiced()))
				.productCategoryIds(productCategoryIdsByProductId.getOrDefault(productId, ImmutableSet.of()))
				.packingMaterialProductCategoryIds(orderLineId != null
						? packingMaterialCategoryIdsByOrderLineId.getOrDefault(orderLineId, ImmutableSet.of())
						: ImmutableSet.of())
				.build();
	}

	/**
	 * note to dev: keep in sync with {@link #updateInvoiceCandidateFromCompensationLine(I_C_Invoice_Candidate, GroupCompensationLine)}
	 */
	private GroupCompensationLine createCompensationLine(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@NonNull final Map<InvoiceCandidateId, CompensationLineOrigin> originByInvoiceCandidateId)
	{
		// invoiced and to invoice now, like the regular lines: a compounding percent line's base deducts the whole amount of the lines before it
		final BigDecimal qtyInvoicedAndToInvoice = invoiceCandidate.getQtyInvoiced().add(invoiceCandidate.getQtyToInvoice());
		final ProductId productId = ProductId.ofRepoId(invoiceCandidate.getM_Product_ID());

		final BigDecimal price = invoiceCandidate.getPriceEntered();

		final UomId priceUomId = UomId.ofRepoId(invoiceCandidate.getPrice_UOM_ID());
		final BigDecimal qtyInPriceUom = uomConversionBL.convertFromProductUOM(productId, priceUomId, qtyInvoicedAndToInvoice);

		final UomId qtyEnteredUomId = UomId.ofRepoId(invoiceCandidate.getC_UOM_ID());
		final BigDecimal qtyEntered = uomConversionBL.convertFromProductUOM(productId, qtyEnteredUomId, qtyInvoicedAndToInvoice);

		final BigDecimal lineNetAmt = price.multiply(qtyInPriceUom);

		final InvoiceCandidateId invoiceCandidateId = extractLineId(invoiceCandidate);
		final CompensationLineOrigin origin = originByInvoiceCandidateId.getOrDefault(invoiceCandidateId, CompensationLineOrigin.NONE);
		final GroupCompensationBase base = origin.getBase();
		return GroupCompensationLine.builder()
				.repoId(invoiceCandidateId)
				.seqNo(invoiceCandidate.getLine())
				.productId(productId)
				.uomId(qtyEnteredUomId)
				.type(GroupCompensationType.ofAD_Ref_List_Value(invoiceCandidate.getGroupCompensationType()))
				.amtType(GroupCompensationAmtType.ofAD_Ref_List_Value(invoiceCandidate.getGroupCompensationAmtType()))
				.percentage(Percent.of(invoiceCandidate.getGroupCompensationPercentage()))
				.baseAmt(invoiceCandidate.getGroupCompensationBaseAmt())
				.price(price)
				.qtyEntered(qtyEntered)
				.lineNetAmt(lineNetAmt)
				.appliesToProductCategoryId(base.getProductCategoryId())
				.packingMaterialProductCategoryId(base.getPackingMaterialProductCategoryId())
				.isOwnBase(origin.isOwnBase())
				.build();
	}

	/**
	 * Batch-resolves each compensation invoice candidate's order line's base (product category and packing-material category)
	 * and whether it is the order line's own base, see {@link OrderGroupRepository#retrieveCompensationLineOrigins(Set)}.
	 *
	 * @return invoice candidate id -> origin of its order line; a candidate without an order line is absent
	 */
	private ImmutableMap<InvoiceCandidateId, CompensationLineOrigin> retrieveCompensationLineOriginsByInvoiceCandidateId(
			@NonNull final List<I_C_Invoice_Candidate> compensationLineCandidates)
	{
		final ImmutableMap<OrderLineId, CompensationLineOrigin> originsByOrderLineId = orderGroupRepository.retrieveCompensationLineOrigins(
				compensationLineCandidates.stream()
						.map(invoiceCandidate -> OrderLineId.ofRepoIdOrNull(invoiceCandidate.getC_OrderLine_ID()))
						.filter(Objects::nonNull)
						.collect(ImmutableSet.toImmutableSet()));

		final ImmutableMap.Builder<InvoiceCandidateId, CompensationLineOrigin> result = ImmutableMap.builder();
		for (final I_C_Invoice_Candidate invoiceCandidate : compensationLineCandidates)
		{
			final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(invoiceCandidate.getC_OrderLine_ID());
			final CompensationLineOrigin origin = orderLineId != null ? originsByOrderLineId.get(orderLineId) : null;
			if (origin != null)
			{
				result.put(extractLineId(invoiceCandidate), origin);
			}
		}
		return result.build();
	}

	public InvoiceCandidateId extractLineId(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		return InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());
	}

	private GroupId extractSingleGroupId(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		Check.assumeNotEmpty(invoiceCandidates, "orderLines is not empty");
		return invoiceCandidates.stream()
				.map(this::extractGroupId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Invoice candidates are not part of the same group: " + invoiceCandidates)));
	}

	public GroupId extractGroupId(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		InvoiceCandidateCompensationGroupUtils.assertInGroup(invoiceCandidate);
		final OrderId orderId = OrderId.ofRepoId(invoiceCandidate.getC_Order_ID());
		return OrderGroupRepository.createGroupId(orderId, invoiceCandidate.getC_Order_CompensationGroup_ID());
	}

	@Override
	public void saveGroup(@NonNull final Group group)
	{
		final GroupId groupId = group.getGroupId();
		final InvoiceCandidatesStorage invoiceCandidatesStorage = retrieveStorage(groupId);
		saveGroup(group, invoiceCandidatesStorage);
	}

	public void saveGroup(final Group group, final InvoiceCandidatesStorage invoiceCandidatesStorage)
	{
		// Save compensation lines
		for (final GroupCompensationLine compensationLine : group.getCompensationLines())
		{
			final InvoiceCandidateId invoiceCandidateId = extractInvoiceCandidateId(compensationLine);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandidatesStorage.getByIdIfPresent(invoiceCandidateId);
			if (invoiceCandidate == null)
			{
				// shall not happen
				throw new AdempiereException("No invoice candidate found for compensation line: " + compensationLine);
			}

			updateInvoiceCandidateFromCompensationLine(invoiceCandidate, compensationLine);
			invoiceCandidatesStorage.save(invoiceCandidate);
		}
	}

	private InvoiceCandidateId extractInvoiceCandidateId(final GroupCompensationLine compensationLine)
	{
		return (InvoiceCandidateId)compensationLine.getRepoId();
	}

	/**
	 * note to dev: keep in sync with {@link #createCompensationLine(I_C_Invoice_Candidate)}
	 */
	private void updateInvoiceCandidateFromCompensationLine(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@NonNull final GroupCompensationLine compensationLine)
	{
		invoiceCandidate.setGroupCompensationBaseAmt(compensationLine.getBaseAmt());

		final ProductId productId = ProductId.ofRepoId(invoiceCandidate.getM_Product_ID());

		final BigDecimal qtyToInvoice = uomConversionBL.convertToProductUOM(productId,
				compensationLine.getQtyEntered(),
				compensationLine.getUomId());

		invoiceCandidate.setQtyToInvoice(qtyToInvoice);

		invoiceCandidate.setQtyEntered(compensationLine.getQtyEntered());
		invoiceCandidate.setC_UOM_ID(UomId.toRepoId(compensationLine.getUomId()));

		invoiceCandidate.setPriceEntered(compensationLine.getPrice());
		invoiceCandidate.setPriceActual(compensationLine.getPrice());

		// PriceEntered == PriceActual is only correct if the candidate carries no discount
		invoiceCandidate.setDiscount(BigDecimal.ZERO);
	}

	@Override
	public Group retrieveOrCreateGroup(final RetrieveOrCreateGroupRequest request)
	{
		throw new UnsupportedOperationException();
	}

	private List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForGroup(@NonNull final GroupId groupId)
	{
		return retrieveInvoiceCandidatesForGroupQuery(groupId).create().list(I_C_Invoice_Candidate.class);
	}

	private IQueryBuilder<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForGroupQuery(@NonNull final GroupId groupId)
	{
		final OrderId orderId = OrderGroupRepository.extractOrderIdFromGroupId(groupId);
		final int orderCompensationGroupId = groupId.getOrderCompensationGroupId();

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_Order_ID, orderId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_Order_CompensationGroup_ID, orderCompensationGroupId);
	}

	private InvoiceCandidatesStorage retrieveStorage(final GroupId groupId)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = retrieveInvoiceCandidatesForGroupQuery(groupId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_IsGroupCompensationLine, true)
				.create()
				.list(I_C_Invoice_Candidate.class);

		return InvoiceCandidatesStorage.builder()
				.groupId(groupId)
				.invoiceCandidates(invoiceCandidates)
				.performDatabaseChanges(true)
				.build();
	}

	public Group createPartialGroupFromCompensationLine(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		InvoiceCandidateCompensationGroupUtils.assertCompensationLine(invoiceCandidate);

		final Map<InvoiceCandidateId, CompensationLineOrigin> originByInvoiceCandidateId =
				retrieveCompensationLineOriginsByInvoiceCandidateId(ImmutableList.of(invoiceCandidate));
		final GroupCompensationLine compensationLine = createCompensationLine(invoiceCandidate, originByInvoiceCandidateId);
		final GroupRegularLine aggregatedRegularLine = OrderGroupRepository.toAggregatedRegularLine(compensationLine);

		final I_C_Order order = invoiceCandidate.getC_Order();
		if (order == null)
		{
			throw new AdempiereException("Invoice candidate has no order: " + invoiceCandidate);
		}

		return Group.builder()
				.groupId(extractGroupId(invoiceCandidate))
				.pricePrecision(orderBL.getPricePrecision(order))
				.amountPrecision(orderBL.getAmountPrecision(order))
				.bpartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
				.regularLine(aggregatedRegularLine)
				.compensationLine(compensationLine)
				.build();
	}

	public InvoiceCandidatesStorage createNotSaveableSingleOrderLineStorage(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		return InvoiceCandidatesStorage.builder()
				.groupId(extractGroupId(invoiceCandidate))
				.invoiceCandidate(invoiceCandidate)
				.performDatabaseChanges(false)
				.build();
	}

	/**
	 * @return the subset of {@code groupIds} whose {@code C_Order_CompensationGroup} header was created by a contract, in one query
	 */
	public ImmutableSet<GroupId> retrieveContractCreatedGroupIds(@NonNull final Set<GroupId> groupIds)
	{
		return OrderGroupRepository.filterContractCreatedGroupIds(groupIds);
	}

	/**
	 * @return the invoice candidates of the given order line (regardless of their group)
	 */
	public List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesOfOrderLine(@NonNull final OrderLineId orderLineId)
	{
		return invoiceCandDAO.retrieveReferencing(TableRecordReference.of(I_C_OrderLine.Table_Name, orderLineId));
	}

	/**
	 * Locks the order's invoice candidates in the order in which the invoice-candidate recompute takes them
	 * ({@code InvoiceCandDAO.fetchInvalidInvoiceCandidates}: IsFreightCost, IsManual, C_Invoice_Candidate_ID).
	 * <p>
	 * Removing a group touches its candidates in a different order (discount line first, then the regular lines); without locking them
	 * upfront, a recompute running at the same time deadlocks with it and one of the two candidate updates is lost.
	 */
	public void lockInvoiceCandidatesOfOrder(@NonNull final OrderId orderId)
	{
		queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, orderId)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_IsFreightCost)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_IsManual)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.create()
				.setForUpdate(ForUpdate.FOR_NO_KEY_UPDATE)
				.listIds();
	}

	/**
	 * Removes the group reference from the group's not yet invoiced invoice candidates, also from those its order lines don't reach
	 * (e.g. a candidate committed by another transaction after its order line was ungrouped), so that the group header can be deleted.
	 * Invoiced candidates keep it.
	 */
	public void ungroupNotInvoicedInvoiceCandidates(@NonNull final GroupId groupId)
	{
		retrieveInvoiceCandidatesForGroupQuery(groupId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced, BigDecimal.ZERO)
				.create()
				.update(invoiceCandidate -> {
					invoiceCandidate.setC_Order_CompensationGroup_ID(-1);
					return IQueryUpdater.MODEL_UPDATED;
				});
	}

	/**
	 * @return {@code true} if at least one regular (non-compensation) invoice candidate of the group is not yet processed, i.e. still has goods to invoice
	 */
	public boolean hasNotProcessedRegularInvoiceCandidates(@NonNull final GroupId groupId)
	{
		return retrieveInvoiceCandidatesForGroupQuery(groupId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsGroupCompensationLine, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
				.create()
				.anyMatch();
	}

	/**
	 * @return {@code true} if at least one regular (non-compensation) invoice candidate of the group still has goods that are neither invoiced
	 * nor to invoice now, i.e. goods that follow with a later invoice
	 */
	public boolean hasRegularInvoiceCandidatesWithGoodsStillToCome(@NonNull final GroupId groupId)
	{
		return retrieveInvoiceCandidatesForGroupQuery(groupId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsGroupCompensationLine, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
				.filter(GoodsStillToComeFilter.INSTANCE)
				.create()
				.anyMatch();
	}

	/**
	 * @return {@code true} if at least one regular (non-compensation) invoice candidate of the group has something to invoice now
	 * that the invoicing will not leave out (see {@link #retrieveRegularInvoiceCandidatesToInvoiceQuery(GroupId)})
	 */
	public boolean hasRegularInvoiceCandidatesToInvoice(@NonNull final GroupId groupId)
	{
		return retrieveRegularInvoiceCandidatesToInvoiceQuery(groupId)
				.create()
				.anyMatch();
	}

	/**
	 * Regular (non-compensation) invoice candidates of the group with something to invoice now, leaving out those the invoicing skips:
	 * this mirrors {@code InvoiceCandBL#getInvoicingSkipReasonOrNull} (processed, IsError, IsToClear, IsInDispute, IsSimulation).
	 */
	private IQueryBuilder<I_C_Invoice_Candidate> retrieveRegularInvoiceCandidatesToInvoiceQuery(@NonNull final GroupId groupId)
	{
		return retrieveInvoiceCandidatesForGroupQuery(groupId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsGroupCompensationLine, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice, BigDecimal.ZERO)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsError, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsToClear, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsInDispute, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsSimulation, false);
	}

	/**
	 * @return {@code true} if goods of the group were invoiced on an invoice (or credit memo) that has no line of the given compensation candidate,
	 * i.e. goods were invoiced without their discount
	 */
	public boolean hasRegularInvoiceLinesWithout(@NonNull final GroupId groupId, @NonNull final InvoiceCandidateId compensationInvoiceCandidateId)
	{
		final ImmutableSet<Integer> goodsInvoiceIds = retrieveInvoiceCandidatesForGroupQuery(groupId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsGroupCompensationLine, false)
				.andCollectChildren(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID)
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID)
				.create()
				.listDistinctAsImmutableSet(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, Integer.class);
		if (goodsInvoiceIds.isEmpty())
		{
			return false;
		}

		final ImmutableSet<Integer> compensationInvoiceIds = queryBL.createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, compensationInvoiceCandidateId)
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID)
				.create()
				.listDistinctAsImmutableSet(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, Integer.class);
		return !compensationInvoiceIds.containsAll(goodsInvoiceIds);
	}

	/** Goods that are neither invoiced nor to invoice now; the SQL is used on the database, {@link #accept} in unit tests. */
	private static final class GoodsStillToComeFilter implements IQueryFilter<I_C_Invoice_Candidate>, ISqlQueryFilter
	{
		static final GoodsStillToComeFilter INSTANCE = new GoodsStillToComeFilter();

		@Override
		public String getSql()
		{
			return "ABS(" + I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced + " + " + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice + ") < ABS(" + I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered + ")";
		}

		@Override
		public List<Object> getSqlParams(final Properties ctx)
		{
			return ImmutableList.of();
		}

		@Override
		public boolean accept(final I_C_Invoice_Candidate ic)
		{
			return ic.getQtyInvoiced().add(ic.getQtyToInvoice()).abs().compareTo(ic.getQtyOrdered().abs()) < 0;
		}
	}

	/**
	 * @return the first (by ID) regular invoice candidate of the group that currently has something to invoice that the invoicing will not leave out
	 */
	public Optional<I_C_Invoice_Candidate> retrieveFirstRegularInvoiceCandidateToInvoice(@NonNull final GroupId groupId)
	{
		return Optional.ofNullable(retrieveRegularInvoiceCandidatesToInvoiceQuery(groupId)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.create()
				.first(I_C_Invoice_Candidate.class));
	}

	public void invalidateCompensationInvoiceCandidatesOfGroup(final GroupId groupId)
	{
		final IQuery<I_C_Invoice_Candidate> query = retrieveInvoiceCandidatesForGroupQuery(groupId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_IsGroupCompensationLine, true) // only compensation lines
				.create();
		invoiceCandDAO.invalidateCandsFor(query);
	}

	public void invalidatePercentCompensationInvoiceCandidatesOfGroup(@NonNull final GroupId groupId)
	{
		final IQuery<I_C_Invoice_Candidate> query = retrieveInvoiceCandidatesForGroupQuery(groupId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_IsGroupCompensationLine, true)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_GroupCompensationAmtType, X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_Percent)
				.create();
		invoiceCandDAO.invalidateCandsFor(query);
	}
}
