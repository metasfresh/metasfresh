package de.metas.contracts.compensationGroup.contract;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
 * Finds the refund contracts whose percentage a drop-ship purchase order's take-over line takes over.
 */
@Service
public class ContractSettingsTakeOverRefundService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final RefundConfigRepository refundConfigRepository;

	public ContractSettingsTakeOverRefundService(@NonNull final RefundConfigRepository refundConfigRepository)
	{
		this.refundConfigRepository = refundConfigRepository;
	}

	@VisibleForTesting
	public static ContractSettingsTakeOverRefundService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				ContractSettingsTakeOverRefundService.class,
				() -> new ContractSettingsTakeOverRefundService(
						SpringContextHolder.getBeanOrSupply(
								RefundConfigRepository.class,
								() -> new RefundConfigRepository(new InvoiceScheduleRepository()))));
	}

	/**
	 * @return per refund condition in {@code conditionsIds}, the qualifying refund contracts of {@code invoicePartnerId} valid at {@code date}:
	 * active, Type_Conditions=Refund, DocStatus CO/CL, ContractStatus not voided, StartDate <= date <= EndDate, no M_Product_ID on the term,
	 * and the condition has exactly one active refund setting with no product, RefundBase=percentage, MinQty=0 and no packing-option restriction.
	 * A product category, bonus product or "deducted at payment" on the setting does not matter.
	 */
	public ImmutableListMultimap<ConditionsId, TakenOverRefund> findTakenOverRefunds(
			@NonNull final BPartnerId invoicePartnerId,
			@NonNull final LocalDate date,
			@NonNull final Set<ConditionsId> conditionsIds)
	{
		if (conditionsIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		final List<I_C_Flatrate_Term> terms = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, invoicePartnerId)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.REFUND)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, conditionsIds)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, ImmutableList.of(DocStatus.Completed, DocStatus.Closed))
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Voided)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID, null)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(date))
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, TimeUtil.asTimestamp(date))
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.endOrderBy()
				.create()
				.list(I_C_Flatrate_Term.class);

		final Map<ConditionsId, Optional<Percent>> qualifyingPercentByConditions = new HashMap<>();
		final Map<ConditionsId, String> nameByConditions = new HashMap<>();
		final ImmutableListMultimap.Builder<ConditionsId, TakenOverRefund> result = ImmutableListMultimap.builder();
		for (final I_C_Flatrate_Term term : terms)
		{
			final ConditionsId conditionsId = ConditionsId.ofRepoId(term.getC_Flatrate_Conditions_ID());
			final Optional<Percent> percent = qualifyingPercentByConditions.computeIfAbsent(conditionsId, this::retrieveQualifyingPercent);
			if (!percent.isPresent())
			{
				continue;
			}

			result.put(conditionsId, TakenOverRefund.builder()
					.termId(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()))
					.conditionsId(conditionsId)
					.conditionsName(nameByConditions.computeIfAbsent(conditionsId, ContractSettingsTakeOverRefundService::retrieveConditionsName))
					.percent(percent.get())
					.build());
		}
		return result.build();
	}

	private Optional<Percent> retrieveQualifyingPercent(@NonNull final ConditionsId conditionsId)
	{
		return extractQualifyingPercent(
				refundConfigRepository.getAllActiveByConditions(conditionsId),
				!refundConfigRepository.retrievePackingMaterialIdsOfPackingOptionFilteredConfigs(conditionsId).isEmpty());
	}

	private static String retrieveConditionsName(@NonNull final ConditionsId conditionsId)
	{
		return InterfaceWrapperHelper.load(conditionsId, I_C_Flatrate_Conditions.class).getName();
	}

	/**
	 * @param activeConfigs    all active refund settings of one refund condition
	 * @param packingRestricted whether a setting of the condition is restricted to packing options that it lists
	 * @return the setting's percentage if the condition has exactly one active setting with no product, RefundBase=percentage, MinQty=0
	 * and no packing-option restriction; empty otherwise
	 */
	@VisibleForTesting
	static Optional<Percent> extractQualifyingPercent(@NonNull final List<RefundConfig> activeConfigs, final boolean packingRestricted)
	{
		if (activeConfigs.size() != 1 || packingRestricted)
		{
			return Optional.empty();
		}
		final RefundConfig config = activeConfigs.get(0);
		if (config.getProductId() != null
				|| config.getRefundBase() != RefundConfig.RefundBase.PERCENTAGE
				|| config.getMinQty().signum() != 0)
		{
			return Optional.empty();
		}
		return Optional.of(config.getPercent());
	}
}
