/**
 *
 */
package de.metas.bpartner.service;

import com.google.common.collect.ImmutableListMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_C_CreditLimit_Type;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	private @NonNull final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, CreditLimitType> cache_creditLimitById = CCache.newCache(
			I_C_CreditLimit_Type.Table_Name + "#by#" + I_C_CreditLimit_Type.COLUMNNAME_C_CreditLimit_Type_ID,
			10, // initial size
			CCache.EXPIREMINUTES_Never);

	private final Comparator<I_C_BPartner_CreditLimit> comparator = createComparator();

	public ImmutableListMultimap<BPartnerId, I_C_BPartner_CreditLimit> getAllByBPartnerIds(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		if (bpartnerIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryBL.createQueryBuilderOutOfTrx(I_C_BPartner_CreditLimit.class)
				.addInArrayFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartnerIds)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> BPartnerId.ofRepoId(record.getC_BPartner_ID()),
						record -> record));
	}

	public BigDecimal retrieveCreditLimitByBPartnerId(final int bpartnerId, @NonNull final Timestamp date)
	{
		return retrieveCreditLimitsByBPartnerId(bpartnerId, date)
				.stream().min(comparator)
				.map(I_C_BPartner_CreditLimit::getAmount)
				.orElse(BigDecimal.ZERO);
	}

	private List<I_C_BPartner_CreditLimit> retrieveCreditLimitsByBPartnerId(final int bpartnerId, @NonNull final Timestamp date)
	{
		return queryBL
				.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_Processed, true)
				.addCompareFilter(I_C_BPartner_CreditLimit.COLUMNNAME_DateFrom, Operator.LESS_OR_EQUAL, date)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.list();
	}

	private Comparator<I_C_BPartner_CreditLimit> createComparator()
	{
		final Comparator<I_C_BPartner_CreditLimit> byTypeSeqNoReversed = //
				Comparator.<I_C_BPartner_CreditLimit, SeqNo>comparing(bpCreditLimit -> getCreditLimitTypeById(bpCreditLimit.getC_CreditLimit_Type_ID()).getSeqNo()).reversed();

		final Comparator<I_C_BPartner_CreditLimit> byDateFromRevesed = //
				Comparator.<I_C_BPartner_CreditLimit, Date>comparing(I_C_BPartner_CreditLimit::getDateFrom).reversed();

		return byDateFromRevesed.thenComparing(byTypeSeqNoReversed);
	}

	public CreditLimitType getCreditLimitTypeById(final int C_CreditLimit_Type_ID)
	{
		return cache_creditLimitById.getOrLoad(C_CreditLimit_Type_ID, () -> retrieveCreditLimitTypePOJO(C_CreditLimit_Type_ID));
	}

	private CreditLimitType retrieveCreditLimitTypePOJO(final int C_CreditLimit_Type_ID)
	{
		final I_C_CreditLimit_Type type = queryBL
				.createQueryBuilder(I_C_CreditLimit_Type.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CreditLimit_Type.COLUMN_C_CreditLimit_Type_ID, C_CreditLimit_Type_ID)
				.create()
				.firstOnlyNotNull(I_C_CreditLimit_Type.class);

		return CreditLimitType.builder()
				.creditLimitTypeId(CreditLimitTypeId.ofRepoId(type.getC_CreditLimit_Type_ID()))
				.seqNo(SeqNo.ofInt(type.getSeqNo()))
				.name(type.getName())
				.autoApproval(type.isAutoApproval())
				.build();
	}

	public Optional<I_C_BPartner_CreditLimit> retrieveCreditLimitByBPartnerId(final int bpartnerId, final int typeId)
	{
		return queryBL
				.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_Processed, true)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_CreditLimit_Type_ID, typeId)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.stream().min(comparator);
	}
}
