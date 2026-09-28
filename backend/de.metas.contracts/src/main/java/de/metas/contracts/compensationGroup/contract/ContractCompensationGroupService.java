package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.document.DocTypeId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupCompensationUtils;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
 * order on completion (sales or purchase), and removes it again on reactivate.
 * <p>
 * The order is matched to its contract by <b>invoice partner</b> ({@code Bill_BPartner_ID}), not by the
 * order partner ({@code C_BPartner_ID}) — a store (delivery address) whose invoice partner is a head office
 * is covered by the head office's contract, never by one the store itself might hold.
 */
@Service
public class ContractCompensationGroupService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	private final OrderGroupRepository orderGroupRepository;
	private final GroupTemplateRepository groupTemplateRepository;
	private final ContractCompensationGroupSettingsRepository settingsRepository;

	public ContractCompensationGroupService(
			@NonNull final OrderGroupRepository orderGroupRepository,
			@NonNull final GroupTemplateRepository groupTemplateRepository,
			@NonNull final ContractCompensationGroupSettingsRepository settingsRepository)
	{
		this.orderGroupRepository = orderGroupRepository;
		this.groupTemplateRepository = groupTemplateRepository;
		this.settingsRepository = settingsRepository;
	}

	/**
	 * Removes this order's contract-created compensation group(s) (if any), then, if the order's invoice
	 * partner has an active {@code CompensationGroup} contract whose settings list the order's document type
	 * and whose period covers the order date, (re)creates the group from the order's current, not-yet-grouped
	 * lines that lie in one of the schema's bases.
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

		final I_C_Flatrate_Term term = findActiveTerm(billPartnerId, orderDate, docTypeId).orElse(null);
		if (term == null)
		{
			return;
		}

		final ContractCompensationGroupSettings settings = settingsRepository.getBySettingsId(term.getC_Flatrate_Conditions().getC_CompensationGroup_ContractSettings_ID());
		final GroupTemplate schema = groupTemplateRepository.getById(settings.getSchemaId());

		final CandidateSelection candidateSelection = findCandidateLines(orderId, schema);
		if (candidateSelection.getLineIds().isEmpty())
		{
			return;
		}

		// A discount line whose base matches none of the candidate lines is skipped (no 0.00 line);
		// a discount line with no base at all always applies to the whole group.
		final GroupTemplate schemaWithoutEmptyBases = GroupTemplate.builder()
				.id(schema.getId())
				.name(schema.getName())
				.isNamePrinted(schema.isNamePrinted())
				.isInheritPackingInstruction(schema.isInheritPackingInstruction())
				.activityId(schema.getActivityId())
				.productCategoryId(schema.getProductCategoryId())
				.additive(schema.isAdditive())
				.regularLinesToAdd(schema.getRegularLinesToAdd())
				.compensationLines(schema.getCompensationLines().stream()
						.filter(compensationLine -> compensationLine.getAppliesToProductCategoryId() == null
								|| candidateSelection.getMatchedBases().contains(compensationLine.getAppliesToProductCategoryId()))
						.collect(ImmutableList.toImmutableList()))
				.build();

		final Group group = orderGroupRepository.prepareNewGroup()
				.groupTemplate(schemaWithoutEmptyBases)
				.createGroup(candidateSelection.getLineIds());

		final I_C_Order_CompensationGroup groupRecord = load(group.getGroupId().getOrderCompensationGroupId(), I_C_Order_CompensationGroup.class);
		groupRecord.setC_Flatrate_Term_ID(term.getC_Flatrate_Term_ID());
		saveRecord(groupRecord);
	}

	/**
	 * Removes every contract-created compensation group of the given order (identified by
	 * {@code C_Order_CompensationGroup.C_Flatrate_Term_ID} being set): deletes the group's compensation lines,
	 * then ungroups its regular lines and deletes the (now empty) group header.
	 */
	public void removeContractGroups(@NonNull final OrderId orderId)
	{
		final List<Integer> contractGroupIds = queryBL.createQueryBuilder(I_C_Order_CompensationGroup.class)
				.addEqualsFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Order_ID, orderId)
				.addCompareFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Flatrate_Term_ID, Operator.GREATER, 0)
				.create()
				.listIds();

		for (final int orderCompensationGroupId : contractGroupIds)
		{
			final de.metas.order.compensationGroup.GroupId groupId = OrderGroupRepository.createGroupId(orderId, orderCompensationGroupId);

			queryBL.createQueryBuilder(I_C_OrderLine.class)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID, orderCompensationGroupId)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, true)
					.create()
					.delete();

			final Group group = orderGroupRepository.retrieveGroup(groupId);
			orderGroupRepository.destroyGroup(group);
		}
	}

	/**
	 * @return the active {@code CompensationGroup} term for the given invoice partner, order date and order
	 * document type — DocStatus completed/closed, ContractStatus not voided, {@code StartDate <= date <= EndDate},
	 * and the term's settings list {@code docTypeId}. Never cached: overlap between contracts is prevented at
	 * term completion, so at most one term is expected to match.
	 */
	public Optional<I_C_Flatrate_Term> findActiveTerm(
			@NonNull final BPartnerId billPartnerId,
			@NonNull final LocalDate date,
			@NonNull final DocTypeId docTypeId)
	{
		final List<I_C_Flatrate_Term> candidateTerms = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, billPartnerId)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.COMPENSATION_GROUP.getCode())
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, ImmutableList.of(X_C_Flatrate_Term.DOCSTATUS_Completed, X_C_Flatrate_Term.DOCSTATUS_Closed))
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Voided.getCode())
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(date))
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, TimeUtil.asTimestamp(date))
				.create()
				.list(I_C_Flatrate_Term.class);

		return candidateTerms.stream()
				.filter(term -> termAppliesToDocType(term, docTypeId))
				.findFirst();
	}

	private boolean termAppliesToDocType(@NonNull final I_C_Flatrate_Term term, @NonNull final DocTypeId docTypeId)
	{
		final int settingsId = term.getC_Flatrate_Conditions().getC_CompensationGroup_ContractSettings_ID();
		if (settingsId <= 0)
		{
			return false;
		}

		return settingsRepository.getBySettingsId(settingsId).getDocTypeIds().contains(docTypeId);
	}

	/**
	 * @return this order's candidate regular lines — not (yet) in any compensation group, and, when every one
	 * of the schema's discount lines has an applies-to base, whose product's category (or an ancestor of it)
	 * is one of those bases (when at least one discount line has no base, every not-yet-grouped regular line
	 * is a candidate) — together with the subset of the schema's declared bases that at least one of those
	 * candidate lines actually falls into. The latter is used to drop a discount line whose base matches none
	 * of the order's lines, so it never becomes a spurious 0.00 line.
	 */
	private CandidateSelection findCandidateLines(@NonNull final OrderId orderId, @NonNull final GroupTemplate schema)
	{
		// NOTE: "not (yet) grouped" is filtered in Java, not SQL — C_Order_CompensationGroup_ID is NULL (not 0)
		// for an ungrouped line, and OrderGroupCompensationUtils.isNotInGroup(..) already coalesces that via the
		// int getter; a SQL "<= 0" compare filter would wrongly exclude NULL rows (three-valued SQL logic).
		final List<I_C_OrderLine> ungroupedOrderLines = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, false)
				.create()
				.list(I_C_OrderLine.class)
				.stream()
				.filter(OrderGroupCompensationUtils::isNotInGroup)
				.collect(ImmutableList.toImmutableList());

		if (ungroupedOrderLines.isEmpty())
		{
			return CandidateSelection.NONE;
		}

		final ImmutableSet<ProductCategoryId> declaredBases = schema.getCompensationLines().stream()
				.map(GroupTemplateCompensationLine::getAppliesToProductCategoryId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		final boolean hasUnbasedCompensationLine = schema.getCompensationLines().stream()
				.anyMatch(compensationLine -> compensationLine.getAppliesToProductCategoryId() == null);

		final ImmutableSet<ProductId> productIds = ungroupedOrderLines.stream()
				.map(orderLine -> ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<ProductId, ProductCategoryId> productCategoryIdByProductId = productDAO.retrieveProductAndCategoryIdsByProductIds(productIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(ProductAndCategoryId::getProductId, ProductAndCategoryId::getProductCategoryId));

		final ImmutableList.Builder<OrderLineId> candidateLineIds = ImmutableList.builder();
		final ImmutableSet.Builder<ProductCategoryId> matchedBases = ImmutableSet.builder();
		for (final I_C_OrderLine orderLine : ungroupedOrderLines)
		{
			final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
			final ProductCategoryId productCategoryId = productCategoryIdByProductId.get(productId);
			final ImmutableSet<ProductCategoryId> productCategoryIdAndAncestors = productCategoryId != null
					? productDAO.getProductCategoryIdAndAncestors(productCategoryId)
					: ImmutableSet.of();

			final ImmutableSet<ProductCategoryId> lineMatchedBases = declaredBases.stream()
					.filter(productCategoryIdAndAncestors::contains)
					.collect(ImmutableSet.toImmutableSet());

			final boolean isCandidate = hasUnbasedCompensationLine || !lineMatchedBases.isEmpty();
			if (isCandidate)
			{
				candidateLineIds.add(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
				matchedBases.addAll(lineMatchedBases);
			}
		}

		return new CandidateSelection(candidateLineIds.build(), matchedBases.build());
	}

	@Value
	private static class CandidateSelection
	{
		static final CandidateSelection NONE = new CandidateSelection(ImmutableList.of(), ImmutableSet.of());

		ImmutableList<OrderLineId> lineIds;
		ImmutableSet<ProductCategoryId> matchedBases;
	}
}
