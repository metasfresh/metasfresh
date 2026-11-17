package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.compensationGroup.contract.CandidateSelection.PositionSelection;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupRepository;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderFreightCostsService;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationBase;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.GroupRegularLine;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupCompensationUtils;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.createFrom.po_from_so.DropshipPOFromSOService;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * (Re)creates the compensation group(s) that a {@code Type_Conditions=CompensationGroup} contract adds to an
 * order on every completion attempt, one group per position (sales or purchase; remove-then-recreate, so a reactivated order that is
 * never re-completed simply keeps the group from its last completion attempt).
 * <p>
 * The order is matched to its contract by <b>invoice partner</b> ({@code Bill_BPartner_ID}), not by the
 * order partner ({@code C_BPartner_ID}) — a store (delivery address) whose invoice partner is a head office
 * is covered by the head office's contract, never by one the store itself might hold.
 * <p>
 * <b>Interceptor order (do not weaken):</b> registered via its own {@code AD_ModelValidator} row
 * (migration {@code 5826810_sys_AddContractCompensationGroupOrderValidator.sql}, {@code SeqNo = 550}):
 * after HU's packing-material lines ({@code de.metas.handlingunits.model.validator.Main}, {@code SeqNo
 * = 500}) and before every loose interceptor, including freight — so a packaging-type applies-to
 * category matches the HU lines, and a {@code FlatShippingFee} tier's shipment value already includes the bonus.
 * Keep {@code SeqNo} above 500; never register via {@code de.metas.contracts.interceptor.MainValidator}
 * ({@code SeqNo = 0}). Deactivating the {@code AD_ModelValidator} row (or its entity type) disables this
 * interceptor entirely. Pinned by the {@code compensationGroupContract_salesOrder.feature} scenario
 * where the packaging discount is computed on the HU packing-material lines: if the HU ordering ever
 * broke, the packaging discount line would silently disappear.
 * <p>
 * A <b>position</b> is a {@code C_Project_ID}: an order line's own, else its order's.
 */
@Service
@RequiredArgsConstructor
public class ContractCompensationGroupService
{
	@NonNull private final OrderGroupRepository orderGroupRepository;
	@NonNull private final GroupTemplateRepository groupTemplateRepository;
	@NonNull private final ContractCompensationGroupSettingsRepository settingsRepository;
	@NonNull private final ContractCompensationGroupTermRepository termRepository;
	@NonNull private final ContractCompensationGroupRepository contractGroupRepository;
	@NonNull private final OrderFreightCostsService orderFreightCostService;
	@NonNull private final InvoiceCandidateGroupRepository invoiceCandidateGroupRepository;
	@NonNull private final ContractSettingsTakeOverService takeOverService;
	@NonNull private final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory;

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	private static final AdMessageKey MSG_ReactivateInvoiced = AdMessageKey.of("ContractCompensationGroup_ReactivateInvoiced");

	/**
	 * Removes this order's contract-created compensation group(s) (if any), then, if the order's invoice
	 * partner has an active {@code CompensationGroup} contract whose settings list the order's document type
	 * and whose period covers the order date, (re)creates the group(s) from the order's current, not-yet-grouped
	 * lines that fall into the base (product and/or packing-material category) of one of the schema's discount lines:
	 * one group per position, whose discount lines carry that position; the lines without any position share one group.
	 * <p>
	 * A no-op (beyond the removal) when no contract matches, or when no order line qualifies as a candidate.
	 */
	public void recreateContractGroups(@NonNull final I_C_Order order)
	{
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		removeContractGroups(orderId);

		final BPartnerId billPartnerId = BPartnerId.ofRepoId(order.getBill_BPartner_ID());
		final DocTypeId docTypeId = DocTypeId.ofRepoId(order.getC_DocTypeTarget_ID());
		final LocalDate orderDate = TimeUtil.asLocalDate(order.getDateOrdered());

		final TermMatch termMatch = findMatchingTerm(billPartnerId, orderDate, docTypeId).orElse(null);
		if (termMatch == null)
		{
			return;
		}

		final GroupTemplate schemaFromRepository = groupTemplateRepository.getById(termMatch.getSettings().getSchemaId());
		final GroupTemplate schema = order.isSOTrx() ? schemaFromRepository : new PurchaseDiscountLineDescriber(compensationLineCreateRequestFactory).describe(schemaFromRepository);

		// before candidate selection, so that a take-over category joins it
		final GroupTemplate schemaWithTakeOvers = takeOverService.applyToSchema(schema, extractDropShipInfo(order), termMatch.getSettings());

		final ProjectId orderPositionId = ProjectId.ofRepoIdOrNull(order.getC_Project_ID());
		final CandidateSelection candidateSelection = findCandidateLines(orderId, schemaWithTakeOvers, orderPositionId);
		if (candidateSelection.isEmpty())
		{
			return;
		}

		// One group per position; the goods without any position share one group.
		// Each group computes on its own goods, so percentages, revenue-break and fixed-amount discount lines apply per position.
		// Everything downstream (computation, shipment, invoicing) is keyed by group and so works per position.
		candidateSelection.splitByPosition()
				.forEach(positionSelection -> createGroup(termMatch, schemaWithTakeOvers, positionSelection));
	}

	private void createGroup(
			@NonNull final TermMatch termMatch,
			@NonNull final GroupTemplate schema,
			@NonNull final PositionSelection positionSelection)
	{
		final CandidateSelection candidates = positionSelection.getCandidates();

		// the header carries the term before its lines join the group, so that the lines' interceptors already see a contract-created group
		final Group group = orderGroupRepository.prepareNewGroup()
				.groupTemplate(withoutUnmatchedCompensationLines(schema, candidates.getMatchedBases()))
				.flatrateTermId(FlatrateTermId.ofRepoId(termMatch.getTerm().getC_Flatrate_Term_ID()))
				.createGroup(candidates.getLineIds());

		contractGroupRepository.setProjectOnCompensationLines(group.getGroupId(), positionSelection.getPositionId());
	}

	/**
	 * @return the schema without the discount lines whose base (product category, packing-material category) is none of the given ones,
	 * so that no 0.00 line is created; a discount line with no base at all always applies to the whole group and is kept
	 */
	private static GroupTemplate withoutUnmatchedCompensationLines(
			@NonNull final GroupTemplate schema,
			@NonNull final ImmutableSet<GroupCompensationBase> matchedBases)
	{
		return schema.toBuilder()
				.clearCompensationLines()
				.compensationLines(schema.getCompensationLines().stream()
						.filter(compensationLine -> compensationLine.getBase().isNone()
								|| matchedBases.contains(compensationLine.getBase()))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private OrderDropShipInfo extractDropShipInfo(@NonNull final I_C_Order order)
	{
		final OrderId linkedOrderId = OrderId.ofRepoIdOrNull(order.getLink_Order_ID());

		final OrderDropShipInfo.OrderDropShipInfoBuilder builder = OrderDropShipInfo.builder()
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
				.isDropShip(order.isDropShip())
				.linkedOrderId(linkedOrderId);

		// the fee is keyed by the linked SALES order's customer and date, not this order's partner/date.
		// only a drop-ship purchase order takes over (see OrderDropShipInfo.getDropShipLinkedSalesOrderId),
		// so only then is the linked order the sales order whose customer/date the fee needs.
		if (linkedOrderId != null && order.isDropShip() && !order.isSOTrx())
		{
			final I_C_Order linkedSalesOrder = orderDAO.getById(linkedOrderId);
			builder.invoicePartnerId(BPartnerId.ofRepoId(linkedSalesOrder.getBill_BPartner_ID()))
					.soDate(TimeUtil.asZonedDateTime(linkedSalesOrder.getDateOrdered(), OrgId.ofRepoId(linkedSalesOrder.getAD_Org_ID())))
					// while the sales order completes (and creates + completes this PO) it is not yet completed, just like a reactivated one;
					// only the call-context flag tells the two apart
					.refundTakeOverAllowed(DropshipPOFromSOService.isCompletedWithinSalesOrderCompletion(order)
							|| DocStatus.ofNullableCodeOrUnknown(linkedSalesOrder.getDocStatus()).isCompletedOrClosed());
		}

		return builder.build();
	}

	/** When all goods lines of the changed line's contract-created group carry one position, sets it on the group's compensation lines that have an open invoice candidate or none. */
	public void copyPositionToCompensationLines(@NonNull final I_C_OrderLine changedLine)
	{
		final GroupId groupId = findContractGroupIdOfGoodsLineOrNull(changedLine);
		if (groupId == null)
		{
			return;
		}

		final ProjectId positionId = findSinglePositionOfGoodsOrNull(groupId);
		if (positionId == null)
		{
			return;
		}

		final ImmutableList<I_C_OrderLine> compensationLinesWithOtherPosition = retrieveCompensationLinesWithOtherPosition(groupId, positionId);
		contractGroupRepository.setProjectOnOrderLines(retainLinesWithOpenOrNoInvoiceCandidate(compensationLinesWithOtherPosition), positionId);
	}

	/** @return the contract-created group of the given goods line; null if it is a compensation or packing material line, or not in such a group */
	@Nullable
	private GroupId findContractGroupIdOfGoodsLineOrNull(@NonNull final I_C_OrderLine orderLine)
	{
		if (orderLine.isGroupCompensationLine() || orderLine.isPackagingMaterial())
		{
			return null;
		}

		final int orderCompensationGroupRepoId = orderLine.getC_Order_CompensationGroup_ID();
		if (!orderGroupRepository.isContractCreatedGroup(orderCompensationGroupRepoId))
		{
			return null;
		}

		return OrderGroupRepository.createGroupId(OrderId.ofRepoId(orderLine.getC_Order_ID()), orderCompensationGroupRepoId);
	}

	/** @return the position all of the group's active goods lines carry; null if they carry none or different ones */
	@Nullable
	private ProjectId findSinglePositionOfGoodsOrNull(@NonNull final GroupId groupId)
	{
		final List<ProjectId> distinctPositionIds = contractGroupRepository.retrieveActiveGoodsLines(groupId)
				.stream()
				.map(goodsLine -> ProjectId.ofRepoIdOrNull(goodsLine.getC_Project_ID()))
				.distinct()
				.collect(Collectors.toList()); // tolerates the null of the lines without a position

		return distinctPositionIds.size() == 1 ? distinctPositionIds.get(0) : null;
	}

	private ImmutableList<I_C_OrderLine> retrieveCompensationLinesWithOtherPosition(
			@NonNull final GroupId groupId,
			@NonNull final ProjectId positionId)
	{
		return contractGroupRepository.retrieveCompensationLines(groupId)
				.stream()
				.filter(compensationLine -> !ContractCompensationGroupRepository.hasPosition(compensationLine, positionId))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Reads the active invoice candidates of all given lines with one query.
	 *
	 * @return those of the given lines that have at least one unprocessed invoice candidate, or no invoice candidate at all
	 */
	private ImmutableList<I_C_OrderLine> retainLinesWithOpenOrNoInvoiceCandidate(@NonNull final List<I_C_OrderLine> orderLines)
	{
		final ImmutableSet<OrderLineId> orderLineIds = orderLines.stream()
				.map(ContractCompensationGroupService::extractOrderLineId)
				.collect(ImmutableSet.toImmutableSet());

		final Map<OrderLineId, Boolean> hasOpenInvoiceCandidateByOrderLineId = invoiceCandDAO.retrieveInvoiceCandidatesForOrderLineIds(orderLineIds)
				.stream()
				.collect(Collectors.toMap(
						invoiceCandidate -> OrderLineId.ofRepoId(invoiceCandidate.getC_OrderLine_ID()),
						invoiceCandidate -> !invoiceCandidate.isProcessed(),
						Boolean::logicalOr));

		final boolean lineWithoutInvoiceCandidateCountsAsOpen = true;
		return orderLines.stream()
				.filter(orderLine -> hasOpenInvoiceCandidateByOrderLineId.getOrDefault(extractOrderLineId(orderLine), lineWithoutInvoiceCandidateCountsAsOpen))
				.collect(ImmutableList.toImmutableList());
	}

	private static OrderLineId extractOrderLineId(@NonNull final I_C_OrderLine orderLine)
	{
		return OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID());
	}

	/**
	 * Refuses reactivating the given order while any compensation (discount) line of its contract-created
	 * groups is (partially) invoiced — reactivating would otherwise leave an invoiced line unprocessed once
	 * {@link #removeContractGroups} ungroups it, or dangling once the group's regular lines are re-grouped
	 * differently on the next completion.
	 */
	public void assertNoInvoicedContractGroupLines(@NonNull final I_C_Order order)
	{
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		if (contractGroupRepository.hasInvoicedContractGroupLines(orderId))
		{
			throw new AdempiereException(MSG_ReactivateInvoiced);
		}
	}

	/**
	 * To be called before the reactivation touches anything: locks the order's invoice candidates in the recompute's order,
	 * so that {@link #removeContractGroups} cannot deadlock with an invoice-candidate recompute that runs at the same time.
	 */
	public void lockInvoiceCandidatesForGroupRemoval(@NonNull final I_C_Order order)
	{
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		if (contractGroupRepository.retrieveContractGroupIds(orderId).isEmpty())
		{
			return;
		}
		invoiceCandidateGroupRepository.lockInvoiceCandidatesOfOrder(orderId);
	}

	/**
	 * Removes every contract-created compensation group of the given order: deletes the group's compensation
	 * lines, then ungroups its regular lines and deletes the (now empty) group header. If the header's regular
	 * lines were themselves removed beforehand, there is no rebuildable {@link Group} left — the orphaned
	 * header is then deleted directly instead.
	 */
	public void removeContractGroups(@NonNull final OrderId orderId)
	{
		final List<GroupId> contractGroupIds = contractGroupRepository.retrieveContractGroupIds(orderId);

		for (final GroupId groupId : contractGroupIds)
		{
			contractGroupRepository.deleteCompensationLines(groupId);
			invoiceCandidateGroupRepository.ungroupNotInvoicedInvoiceCandidates(groupId);

			final Group group = orderGroupRepository.retrieveGroupIfExists(groupId);
			if (group != null)
			{
				orderGroupRepository.destroyGroup(group);
			}
			else
			{
				orderGroupRepository.deleteGroupById(groupId);
			}
		}
	}

	/**
	 * @return the active {@code CompensationGroup} term (bundled with its already-resolved settings) for the
	 * given invoice partner, order date and order document type — DocStatus completed/closed, ContractStatus
	 * not voided, {@code StartDate <= date <= EndDate}, and the term's settings list {@code docTypeId}. Never
	 * cached. At most one term is expected to match; throws if more than one genuinely does.
	 */
	private Optional<TermMatch> findMatchingTerm(
			@NonNull final BPartnerId billPartnerId,
			@NonNull final LocalDate date,
			@NonNull final DocTypeId docTypeId)
	{
		final List<I_C_Flatrate_Term> candidateTerms = termRepository.findActiveTerms(billPartnerId, date);

		final ImmutableList<TermMatch> matches = candidateTerms.stream()
				.map(this::resolveTermMatchOrNull)
				.filter(Objects::nonNull)
				.filter(match -> match.getSettings().getDocTypeIds().contains(docTypeId))
				.collect(ImmutableList.toImmutableList());

		if (matches.size() > 1)
		{
			throw new AdempiereException("More than one active CompensationGroup term matches this Bill partner, date and document type")
					.setParameter("billPartnerId", billPartnerId)
					.setParameter("date", date)
					.setParameter("docTypeId", docTypeId)
					.setParameter("termIds", matches.stream().map(match -> match.getTerm().getC_Flatrate_Term_ID()).collect(ImmutableList.toImmutableList()))
					.appendParametersToMessage();
		}

		return matches.stream().findFirst();
	}

	/** Resolves the term's settings (via its {@code C_Flatrate_Conditions_ID}), or {@code null} when the conditions carry no compensation-group settings. */
	@Nullable
	private TermMatch resolveTermMatchOrNull(@NonNull final I_C_Flatrate_Term term)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoId(term.getC_Flatrate_Conditions_ID());
		final ContractCompensationGroupSettings settings = settingsRepository.getByConditionsId(conditionsId);
		if (settings == null)
		{
			return null;
		}

		return new TermMatch(term, settings);
	}

	@Value
	private static class TermMatch
	{
		I_C_Flatrate_Term term;
		ContractCompensationGroupSettings settings;
	}

	/**
	 * @return this order's candidate regular lines — not (yet) in any compensation group, active, not a
	 * freight-cost line, carrying a product (a product-less charge line cannot be part of a {@link Group}) —
	 * and, when every one of the schema's discount lines has a base (product category and/or packing-material category),
	 * only those lines that match the base of at least one discount line (when at least one discount line has no base,
	 * every eligible line is a candidate) — each with its position and the subset of the schema's bases it falls into.
	 * The latter is used to drop, per position, a discount line whose base matches none of the position's lines,
	 * so it never becomes a spurious 0.00 line.
	 * <p>
	 * A line's product categories include its ancestors; its packing-material categories come from the packing
	 * instruction of the order line itself (none for a line without one).
	 */
	private CandidateSelection findCandidateLines(
			@NonNull final OrderId orderId,
			@NonNull final GroupTemplate schema,
			@Nullable final ProjectId orderPositionId)
	{
		final ImmutableList<I_C_OrderLine> eligibleOrderLines = contractGroupRepository.retrieveActiveRegularOrderLines(orderId)
				.stream()
				.filter(OrderGroupCompensationUtils::isNotInGroup)
				.filter(orderLine -> !orderFreightCostService.isFreightCostOrderLine(orderLine))
				.filter(orderLine -> ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID()) != null)
				.collect(ImmutableList.toImmutableList());

		if (eligibleOrderLines.isEmpty())
		{
			return CandidateSelection.NONE;
		}

		final ImmutableSet<GroupCompensationBase> declaredBases = schema.getCompensationLines().stream()
				.map(GroupTemplateCompensationLine::getBase)
				.filter(base -> !base.isNone())
				.collect(ImmutableSet.toImmutableSet());
		final boolean hasCompensationLineWithoutBase = schema.getCompensationLines().stream()
				.anyMatch(compensationLine -> compensationLine.getBase().isNone());

		final ImmutableMap<ProductId, ImmutableSet<ProductCategoryId>> productCategoryIdAndAncestorsByProductId =
				orderGroupRepository.retrieveProductCategoryIdAndAncestorsByProductId(eligibleOrderLines);
		final ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> packingMaterialCategoryIdsByPIItemProductId =
				declaredBases.stream().anyMatch(base -> base.getPackingMaterialProductCategoryId() != null)
						? OrderGroupRepository.retrievePackingMaterialProductCategoryIdAndAncestorsByPIItemProductId(eligibleOrderLines)
						: ImmutableMap.of();

		final ImmutableList.Builder<CandidateLine> candidateLines = ImmutableList.builder();
		for (final I_C_OrderLine orderLine : eligibleOrderLines)
		{
			final GroupRegularLine regularLine = OrderGroupRepository.buildCategoryOnlyRegularLine(
					orderLine,
					productCategoryIdAndAncestorsByProductId,
					packingMaterialCategoryIdsByPIItemProductId);

			final ImmutableSet<GroupCompensationBase> lineMatchedBases = declaredBases.stream()
					.filter(base -> base.isMatching(regularLine))
					.collect(ImmutableSet.toImmutableSet());

			final boolean isCandidate = hasCompensationLineWithoutBase || !lineMatchedBases.isEmpty();
			if (isCandidate)
			{
				candidateLines.add(new CandidateLine(extractOrderLineId(orderLine), resolvePositionId(orderLine, orderPositionId), lineMatchedBases));
			}
		}

		return new CandidateSelection(candidateLines.build());
	}

	/** @return the line's own position, else the order's; null if neither has one */
	@Nullable
	private static ProjectId resolvePositionId(@NonNull final I_C_OrderLine orderLine, @Nullable final ProjectId orderPositionId)
	{
		final ProjectId linePositionId = ProjectId.ofRepoIdOrNull(orderLine.getC_Project_ID());
		return linePositionId != null ? linePositionId : orderPositionId;
	}
}
