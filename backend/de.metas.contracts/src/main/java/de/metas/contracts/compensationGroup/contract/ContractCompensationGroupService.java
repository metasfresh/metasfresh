package de.metas.contracts.compensationGroup.contract;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.DocTypeId;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupRepository;
import de.metas.order.OrderFreightCostsService;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupCompensationUtils;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.product.IProductBL;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
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
 * (Re)creates the compensation group that a {@code Type_Conditions=CompensationGroup} contract adds to an
 * order on every completion attempt (sales or purchase; remove-then-recreate, so a reactivated order that is
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
	@NonNull private final ContractCompensationGroupTakeOverService takeOverService;

	private final IProductBL productBL = Services.get(IProductBL.class);

	private static final AdMessageKey MSG_ReactivateInvoiced = AdMessageKey.of("ContractCompensationGroup_ReactivateInvoiced");

	/**
	 * Removes this order's contract-created compensation group(s) (if any), then, if the order's invoice
	 * partner has an active {@code CompensationGroup} contract whose settings list the order's document type
	 * and whose period covers the order date, (re)creates the group from the order's current, not-yet-grouped
	 * lines that fall into one of the schema's applies-to categories.
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

		final GroupTemplate schema = groupTemplateRepository.getById(termMatch.getSettings().getSchemaId());

		// Drop-ship take-over: fold the vendor's take-over records (the percentages taken over from the linked sales
		// order's contract discount lines) into the schema's compensation lines BEFORE candidate selection, so the
		// take-over category joins candidate selection and an appended own line computes on its own base.
		// A no-op (same schema) for the sales-order path and the non-drop-ship purchase-order path.
		final GroupTemplate schemaWithTakeOvers = applyTakeOvers(schema, order, termMatch.getSettings());

		final CandidateSelection candidateSelection = findCandidateLines(orderId, schemaWithTakeOvers);
		if (candidateSelection.getLineIds().isEmpty())
		{
			return;
		}

		// A discount line whose applies-to category matches none of the candidate lines is skipped (no 0.00 line);
		// a discount line with no applies-to category always applies to the whole group.
		final GroupTemplate schemaWithoutUnmatchedCompensationLines = schemaWithTakeOvers.toBuilder()
				.clearCompensationLines()
				.compensationLines(schemaWithTakeOvers.getCompensationLines().stream()
						.filter(compensationLine -> compensationLine.getAppliesToProductCategoryId() == null
								|| candidateSelection.getMatchedAppliesToCategoryIds().contains(compensationLine.getAppliesToProductCategoryId()))
						.collect(ImmutableList.toImmutableList()))
				.build();

		// the header carries the term before its lines join the group, so that the lines' interceptors already see a contract-created group
		orderGroupRepository.prepareNewGroup()
				.groupTemplate(schemaWithoutUnmatchedCompensationLines)
				.flatrateTermId(FlatrateTermId.ofRepoId(termMatch.getTerm().getC_Flatrate_Term_ID()))
				.createGroup(candidateSelection.getLineIds());
	}

	/**
	 * Folds the vendor's take-over records into the schema's compensation lines. Each record whose listed customer discount
	 * products were discounted on the linked sales order (nonzero summed percentage) either merges into, or is appended to,
	 * the schema's lines (see {@link #applyTakeOver}).
	 * <p>
	 * Returns the <b>unchanged</b> schema when nothing is taken over — i.e. the sales-order path, the non-drop-ship
	 * purchase-order path, and the drop-ship path with no matching listed product — so those paths behave exactly as before.
	 */
	private GroupTemplate applyTakeOvers(
			@NonNull final GroupTemplate schema,
			@NonNull final I_C_Order order,
			@NonNull final ContractCompensationGroupSettings settings)
	{
		final List<TakeOverResult> takeOvers = takeOverService.computeTakeOvers(order, settings);
		if (takeOvers.isEmpty())
		{
			return schema;
		}

		final List<GroupTemplateCompensationLine> adjustedLines = new ArrayList<>(schema.getCompensationLines());
		for (final TakeOverResult takeOver : takeOvers)
		{
			applyTakeOver(adjustedLines, takeOver);
		}

		return schema.toBuilder()
				.clearCompensationLines()
				.compensationLines(adjustedLines)
				.build();
	}

	/**
	 * Applies one take-over to the (mutable) compensation-line list:
	 * <ul>
	 * <li><b>merge</b> — if the schema has a percentage discount line on the record's category, the first such line (by order)
	 * is replaced with one summed line (vendor percentage + taken-over percentage, a nominal add — 3% + 3% &rarr; one 6% line,
	 * never compounded);</li>
	 * <li><b>append</b> — else an own discount line with the record's discount product is appended on the record's category,
	 * carrying only the taken-over percentage (computed on its own base) and the take-over record id, so the own line keeps
	 * its category across reload / invoice-candidate rebuild (the repositories resolve the category of a line without a schema
	 * line from the take-over record, via {@code TakeOverCategoryProvider}).</li>
	 * </ul>
	 */
	private void applyTakeOver(
			@NonNull final List<GroupTemplateCompensationLine> lines,
			@NonNull final TakeOverResult takeOver)
	{
		final TakeOverRecord record = takeOver.getRecord();
		final ProductCategoryId categoryId = record.getProductCategoryId();
		final Percent takenOverPercent = takeOver.getSummedPercent();

		final int mergeIndex = findMergeableLineIndex(lines, categoryId, this::isEffectiveDiscountPercentLine);
		if (mergeIndex >= 0)
		{
			final GroupTemplateCompensationLine vendorLine = lines.get(mergeIndex);
			final Percent vendorPercent = vendorLine.getPercentage(); // non-null by findMergeableLineIndex
			lines.set(mergeIndex, vendorLine.toBuilder()
					.percentage(vendorPercent.add(takenOverPercent))
					.description(createMergedLineDescription(vendorPercent, vendorLine.getProductId(), takeOver))
					.build());
		}
		else
		{
			lines.add(GroupTemplateCompensationLine.builder()
					.productId(record.getOwnLineProductId())
					.compensationType(GroupCompensationType.Discount)
					.percentage(takenOverPercent)
					.appliesToProductCategoryId(categoryId)
					.takeOverId(record.getTakeOverId())
					.description(createAppendedLineDescription(takeOver))
					.build());
		}
	}

	/**
	 * @return the index of the first <b>effective</b> percentage discount line on {@code categoryId} (the merge target), or
	 * {@code -1} when the schema has none. A line qualifies when it carries a percentage, is on the category, and
	 * {@code isEffectiveDiscountPercent} holds for it. "Effective" is essential: a schema line leaves {@code compensationType}
	 * null and its real type/amt-type come from the product at line creation, so a line whose product is a Surcharge or a
	 * non-Percent amt-type (which would collapse to 0% at creation and silently drop the take-over) must NOT be a merge target
	 * and falls through to APPEND. Keyed strictly off the completing purchase order's own vendor schema line for that category.
	 */
	@VisibleForTesting
	static int findMergeableLineIndex(
			@NonNull final List<GroupTemplateCompensationLine> lines,
			@NonNull final ProductCategoryId categoryId,
			@NonNull final Predicate<GroupTemplateCompensationLine> isEffectiveDiscountPercent)
	{
		for (int i = 0; i < lines.size(); i++)
		{
			final GroupTemplateCompensationLine line = lines.get(i);
			if (line.getPercentage() != null
					&& categoryId.equals(line.getAppliesToProductCategoryId())
					&& isEffectiveDiscountPercent.test(line))
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * @return whether the line's <b>effective</b> compensation type/amt-type — resolved from its product exactly as at line
	 * creation ({@link GroupCompensationLineCreateRequestFactory#resolveGroupCompensationType} /
	 * {@link GroupCompensationLineCreateRequestFactory#extractGroupCompensationAmtType}) — is a percentage discount, the only
	 * kind a take-over may merge into. A Surcharge-by-product or non-Percent line would be computed as 0% at creation, so
	 * merging the take-over into it would silently lose the taken-over percentage.
	 */
	private boolean isEffectiveDiscountPercentLine(@NonNull final GroupTemplateCompensationLine line)
	{
		final I_M_Product product = productBL.getById(line.getProductId());
		final GroupCompensationType type = GroupCompensationLineCreateRequestFactory.resolveGroupCompensationType(line.getCompensationType(), product);
		final GroupCompensationAmtType amtType = GroupCompensationLineCreateRequestFactory.extractGroupCompensationAmtType(product);
		return type == GroupCompensationType.Discount && amtType == GroupCompensationAmtType.Percent;
	}

	/** e.g. {@code "3% Bonus Vendor + 3% Bonus Ware"}: the vendor's own percentage with its discount product, then the taken-over percentage with the customer discount products taken over. */
	private String createMergedLineDescription(
			@NonNull final Percent vendorPercent,
			@NonNull final ProductId vendorProductId,
			@NonNull final TakeOverResult takeOver)
	{
		return formatPercent(vendorPercent) + " " + productBL.getProductName(vendorProductId)
				+ " + " + createAppendedLineDescription(takeOver);
	}

	/** e.g. {@code "3% Bonus Ware"}: the taken-over percentage with the customer discount products that were actually taken over (not every product the record lists). */
	private String createAppendedLineDescription(@NonNull final TakeOverResult takeOver)
	{
		return formatPercent(takeOver.getSummedPercent()) + " " + getTakenOverProductNames(takeOver);
	}

	private String getTakenOverProductNames(@NonNull final TakeOverResult takeOver)
	{
		return productBL.getProductNames(takeOver.getTakenOverProductIds())
				.values()
				.stream()
				.sorted()
				.collect(Collectors.joining(", "));
	}

	private static String formatPercent(@NonNull final Percent percent)
	{
		return percent.toBigDecimal().toPlainString() + "%";
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
	 * and, when every one of the schema's discount lines has an applies-to category, whose product's category (or
	 * an ancestor of it) is one of those categories (when at least one discount line has none, every eligible
	 * line is a candidate) — together with the subset of the schema's applies-to categories that at least one of
	 * those candidate lines actually falls into. The latter is used to drop a discount line whose applies-to category
	 * matches none of the order's lines, so it never becomes a spurious 0.00 line.
	 */
	private CandidateSelection findCandidateLines(@NonNull final OrderId orderId, @NonNull final GroupTemplate schema)
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

		final ImmutableSet<ProductCategoryId> declaredAppliesToCategoryIds = schema.getCompensationLines().stream()
				.map(GroupTemplateCompensationLine::getAppliesToProductCategoryId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		final boolean hasCompensationLineWithoutAppliesToCategory = schema.getCompensationLines().stream()
				.anyMatch(compensationLine -> compensationLine.getAppliesToProductCategoryId() == null);

		final ImmutableMap<ProductId, ImmutableSet<ProductCategoryId>> productCategoryIdAndAncestorsByProductId =
				orderGroupRepository.retrieveProductCategoryIdAndAncestorsByProductId(eligibleOrderLines);

		final ImmutableList.Builder<OrderLineId> candidateLineIds = ImmutableList.builder();
		final ImmutableSet.Builder<ProductCategoryId> matchedAppliesToCategoryIds = ImmutableSet.builder();
		for (final I_C_OrderLine orderLine : eligibleOrderLines)
		{
			final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID()); // safe: filtered above
			final ImmutableSet<ProductCategoryId> productCategoryIdAndAncestors = productCategoryIdAndAncestorsByProductId.getOrDefault(productId, ImmutableSet.of());

			final ImmutableSet<ProductCategoryId> lineMatchedAppliesToCategoryIds = declaredAppliesToCategoryIds.stream()
					.filter(productCategoryIdAndAncestors::contains)
					.collect(ImmutableSet.toImmutableSet());

			final boolean isCandidate = hasCompensationLineWithoutAppliesToCategory || !lineMatchedAppliesToCategoryIds.isEmpty();
			if (isCandidate)
			{
				candidateLineIds.add(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
				matchedAppliesToCategoryIds.addAll(lineMatchedAppliesToCategoryIds);
			}
		}

		return new CandidateSelection(candidateLineIds.build(), matchedAppliesToCategoryIds.build());
	}

	@Value
	private static class CandidateSelection
	{
		static final CandidateSelection NONE = new CandidateSelection(ImmutableList.of(), ImmutableSet.of());

		ImmutableList<OrderLineId> lineIds;
		ImmutableSet<ProductCategoryId> matchedAppliesToCategoryIds;
	}
}
