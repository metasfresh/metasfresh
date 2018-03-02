/**
 *
 */
package org.adempiere.bpartner.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_C_CreditLimit_Type;
import org.compiere.util.CCache;
import org.springframework.stereotype.Repository;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Repository
public class BPartnerCreditLimitRepository
{
	private final CCache<Integer, I_C_CreditLimit_Type> cache_creditLimitById = CCache.newCache(I_C_CreditLimit_Type.Table_Name + "#CreditLimitType#by#Id", 10, CCache.EXPIREMINUTES_Never);

	public BigDecimal getCreditLimitByBPartner(@NonNull final I_C_BPartner bpartner, @NonNull final Timestamp date)
	{
		final List<I_C_BPartner_CreditLimit> bpCreditLimits = retrieveCreditLimitsByBPartner(bpartner, date);
		if (bpCreditLimits.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		sort(bpCreditLimits);
		return bpCreditLimits.get(0).getAmount();
	}

	public List<I_C_BPartner_CreditLimit> retrieveCreditLimitsByBPartner(@NonNull final I_C_BPartner bpartner, @NonNull final Timestamp date)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_CreditLimit.class, bpartner)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_IsApproved, true)
				.addCompareFilter(I_C_BPartner_CreditLimit.COLUMNNAME_DateFrom, Operator.LESS_OR_EQUAL, date)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.list();
	}

	private void sort(final List<I_C_BPartner_CreditLimit> bpCreditLimits)
	{
		final Comparator<I_C_BPartner_CreditLimit> ORDERING_BPCreditLimitByTypeSeqNo = Comparator.comparing(bpCreditLimit -> getCreditLimitTypeById(bpCreditLimit.getC_CreditLimit_Type_ID()).getSeqNo());
		final Comparator<I_C_BPartner_CreditLimit> ORDERING_BPCreditLimitByTypeSeqNoReversed = ORDERING_BPCreditLimitByTypeSeqNo.reversed();

		final Comparator<I_C_BPartner_CreditLimit> ORDERING_BPCreditLimitByDateFrom = Comparator.comparing(bpCreditLimit -> bpCreditLimit.getDateFrom());
		final Comparator<I_C_BPartner_CreditLimit> ORDERING_BPCreditLimitByDateFromReversed = ORDERING_BPCreditLimitByDateFrom.reversed();

		bpCreditLimits.sort(ORDERING_BPCreditLimitByTypeSeqNoReversed.thenComparing(ORDERING_BPCreditLimitByDateFromReversed));
	}

	@Builder
	@Value
	private static class CreditLimitTypePOJO
	{
		private final int seqNo;
		private final int C_CreditLimit_Type_ID;
	}

	private CreditLimitTypePOJO getCreditLimitTypeById(final int C_CreditLimit_Type_ID)
	{
		final I_C_CreditLimit_Type type = cache_creditLimitById.getOrLoad(C_CreditLimit_Type_ID, () -> retrieveRecordFromDB(C_CreditLimit_Type_ID));

		return CreditLimitTypePOJO.builder()
				.C_CreditLimit_Type_ID(type.getC_CreditLimit_Type_ID())
				.seqNo(type.getSeqNo())
				.build();
	}

	private I_C_CreditLimit_Type retrieveRecordFromDB(final int C_CreditLimit_Type_ID)
	{
		final I_C_CreditLimit_Type result = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_CreditLimit_Type.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CreditLimit_Type.COLUMN_C_CreditLimit_Type_ID, C_CreditLimit_Type_ID)
				.create()
				.firstOnly(I_C_CreditLimit_Type.class);

		return result;
	}
}
