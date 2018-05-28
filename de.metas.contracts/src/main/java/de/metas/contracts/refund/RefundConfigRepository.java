package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.CCache;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundConfigBuilder;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceScheduleRepository;
import de.metas.lang.Percent;
import de.metas.product.ProductId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class RefundConfigRepository
{
	private final InvoiceScheduleRepository invoiceScheduleRepository;

	public RefundConfigRepository(@NonNull final InvoiceScheduleRepository invoiceScheduleRepository)
	{
		this.invoiceScheduleRepository = invoiceScheduleRepository;
	}

	public boolean hasRefundConfig(@NonNull final ConditionsId conditionsId)
	{
		return createRefundConfigQuery(conditionsId)
				.match();
	}

	public List<RefundConfig> getByConditionsId(
			@NonNull final ConditionsId conditionsId)
	{
		return createRefundConfigQuery(conditionsId)
				.stream()
				.map(this::ofNullableRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private final CCache<FlatrateTermId, RefundConfig> CACHE = CCache.<FlatrateTermId, RefundConfig> newCache(
			I_C_Flatrate_RefundConfig.Table_Name + "#by#"
					+ I_C_Flatrate_RefundConfig.COLUMNNAME_C_Flatrate_Conditions_ID + "#"
					+ I_C_Flatrate_RefundConfig.COLUMNNAME_M_Product_ID,
			0,
			CCache.EXPIREMINUTES_Never);

	public RefundConfig getByRefundContractId(
			@NonNull final FlatrateTermId flatrateTermId)
	{
		return CACHE.getOrLoad(flatrateTermId, () -> getByRefundContractIdForCache(flatrateTermId));
	}

	private RefundConfig getByRefundContractIdForCache(
			@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term term = Check.assumeNotNull(
				load(flatrateTermId.getRepoId(), I_C_Flatrate_Term.class),
				"The C_Flatrate_Term record for flatrateTermId={}, is not null",
				flatrateTermId);

		final ConditionsId conditionsId = ConditionsId.ofRepoId(term.getC_Flatrate_Conditions_ID());
		final ProductId productId = ProductId.ofRepoId(term.getM_Product_ID());

		final RefundConfig config = getByConditionsIdAndProductId(conditionsId, productId);
		return Check.assumeNotNull(config, "The refundConfig for flatrateTermId={} is not null", flatrateTermId);
	}

	private RefundConfig getByConditionsIdAndProductId(
			@NonNull final ConditionsId conditionsId,
			@NonNull final ProductId productId)
	{
		final I_C_Flatrate_RefundConfig configRecord = createRefundConfigQueryBuilder(conditionsId)
				.addInArrayFilter(
						I_C_Flatrate_RefundConfig.COLUMN_M_Product_ID,
						null,
						productId.getRepoId())
				.orderBy()
				.addColumn(
						I_C_Flatrate_RefundConfig.COLUMNNAME_M_Product_ID,
						Direction.Descending,
						Nulls.Last)
				.endOrderBy()
				.create()
				.first();
		return ofNullableRecord(configRecord);
	}

	private IQuery<I_C_Flatrate_RefundConfig> createRefundConfigQuery(
			@NonNull final ConditionsId conditionsId)
	{
		return createRefundConfigQueryBuilder(conditionsId)
				.create();
	}

	private IQueryBuilder<I_C_Flatrate_RefundConfig> createRefundConfigQueryBuilder(
			@Nullable final ConditionsId conditionsId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_RefundConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMN_C_Flatrate_Conditions_ID, conditionsId.getRepoId());
	}

	private RefundConfig ofNullableRecord(
			@Nullable final I_C_Flatrate_RefundConfig record)
	{
		if (record == null)
		{
			return null;
		}

		final RefundInvoiceType refundInvoiceType;
		if (X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Creditmemo.equals(record.getRefundInvoiceType()))
		{
			refundInvoiceType = RefundInvoiceType.CREDITMEMO;
		}
		else if (X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice.equals(record.getRefundInvoiceType()))
		{
			refundInvoiceType = RefundInvoiceType.INVOICE;
		}
		else
		{
			Check.fail(
					"The given C_Flatrate_RefundConfig has an unsupposed refundInvoiceType={}; record={}",
					record.getRefundInvoiceType(), record);
			return null;
		}

		final InvoiceSchedule invoiceSchedule = invoiceScheduleRepository.ofRecord(record.getC_InvoiceSchedule());

		final RefundConfigBuilder builder = RefundConfig.builder()
				.conditionsId(ConditionsId.ofRepoId(record.getC_Flatrate_Conditions_ID()))
				.refundInvoiceType(refundInvoiceType)
				.invoiceSchedule(invoiceSchedule)
				.percent(Percent.of(record.getPercent()));

		if (record.getM_Product_ID() > 0)
		{
			builder.productId(ProductId.ofRepoId(record.getM_Product_ID()));
		}
		return builder.build();
	}
}
