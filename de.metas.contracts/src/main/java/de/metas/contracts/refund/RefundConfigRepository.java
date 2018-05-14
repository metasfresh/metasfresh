package de.metas.contracts.refund;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundConfigBuilder;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceScheduleId;
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
	public boolean hasRefundConfig(@NonNull final ConditionsId conditionsId)
	{
		return createRefundConfigQuery(conditionsId)
				.match();
	}

	public RefundConfig getByConditionsIdAndProductId(
			@NonNull final ConditionsId conditionsId,
			@NonNull final ProductId productId)
	{
		final I_C_Flatrate_RefundConfig configRecord = createRefundConfigQueryBuilder(conditionsId)
				.addInArrayFilter(
						I_C_Flatrate_RefundConfig.COLUMN_M_Product_ID,
						null, productId.getRepoId())
				.orderBy()
				.addColumn(I_C_Flatrate_RefundConfig.COLUMNNAME_M_Product_ID, IQueryOrderBy.Direction.Descending, IQueryOrderBy.Nulls.Last)
				.endOrderBy()
				.create()
				.first();
		return ofRecord(configRecord);
	}

	public List<RefundConfig> getByConditionsId(@NonNull final ConditionsId conditionsId)
	{
		return createRefundConfigQuery(conditionsId)
				.stream()
				.map(RefundConfigRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private IQuery<I_C_Flatrate_RefundConfig> createRefundConfigQuery(@NonNull final ConditionsId conditionsId)
	{
		return createRefundConfigQueryBuilder(conditionsId)
				.create();
	}

	private IQueryBuilder<I_C_Flatrate_RefundConfig> createRefundConfigQueryBuilder(final ConditionsId conditionsId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_RefundConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMN_C_Flatrate_Conditions_ID, conditionsId.getRepoId());
	}

	private static RefundConfig ofRecord(@Nullable final I_C_Flatrate_RefundConfig record)
	{
		if (record == null)
		{
			return null;
		}
		final RefundConfigBuilder builder = RefundConfig.builder()
				.conditionsId(ConditionsId.ofRepoId(record.getC_Flatrate_Conditions_ID()))
				.docTypeId(DocTypeId.ofRepoId(record.getC_DocTypeInvoice_ID()))
				.invoiceScheduleId(InvoiceScheduleId.ofRepoId(record.getC_InvoiceSchedule_ID()))
				.percent(record.getPercent());
		if (record.getM_Product_ID() > 0)
		{
			builder.productId(ProductId.ofRepoId(record.getM_Product_ID()));
		}
		return builder.build();
	}
}
