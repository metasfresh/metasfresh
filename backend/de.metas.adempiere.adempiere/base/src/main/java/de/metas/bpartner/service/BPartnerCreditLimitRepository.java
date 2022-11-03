/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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
 *
 */
package de.metas.bpartner.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.creditLimit.BPartnerCreditLimitId;
import de.metas.bpartner.creditLimit.CreditLimitTypeId;
import de.metas.bpartner.service.creditlimit.BPartnerCreditLimit;
import de.metas.cache.CCache;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_C_CreditLimit_Type;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Repository
public class BPartnerCreditLimitRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, CreditLimitType> cache_creditLimitById = CCache.newCache(
			I_C_CreditLimit_Type.Table_Name + "#by#" + I_C_CreditLimit_Type.COLUMNNAME_C_CreditLimit_Type_ID,
			10, // initial size
			CCache.EXPIREMINUTES_Never);

	private final Comparator<I_C_BPartner_CreditLimit> comparator = createComparator();

	@NonNull
	public BigDecimal retrieveCreditLimitByBPartnerId(final int bpartnerId, @NonNull final Timestamp date)
	{
		return retrieveCreditLimitsToEnforceByBPartnerId(bpartnerId, date)
				.stream()
				.sorted(comparator)
				.findFirst()
				.map(I_C_BPartner_CreditLimit::getAmount)
				.orElse(BigDecimal.ZERO);
	}

	@NonNull
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
				.stream()
				.min(comparator);
	}

	@NonNull
	public ImmutableListMultimap<BPartnerId, I_C_BPartner_CreditLimit> getAllByBPartnerIds(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		if (bpartnerIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryBL.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartnerIds)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> BPartnerId.ofRepoId(record.getC_BPartner_ID()),
						record -> record));
	}

	@Nullable
	public BPartnerCreditLimit getById(final BPartnerCreditLimitId bPartnerCreditLimitId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_CreditLimit_ID, bPartnerCreditLimitId.getRepoId())
				.create()
				.firstOnlyOptional(I_C_BPartner_CreditLimit.class)
				.map(BPartnerCreditLimitRepository::ofRecord)
				.orElse(null);
	}

	@NonNull
	public I_C_CreditLimit_Type getCreditLimitTypeByName(@NonNull final String typeName)
	{
		return queryBL.createQueryBuilder(I_C_CreditLimit_Type.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CreditLimit_Type.COLUMNNAME_Name, typeName)
				.create()
				.firstOnlyNotNull(I_C_CreditLimit_Type.class);
	}

	public void deactivateCreditLimitsByBPartnerExcept(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final ImmutableList<BPartnerCreditLimitId> exceptIds)
	{
		final ICompositeQueryUpdater<I_C_BPartner_CreditLimit> columnUpdater = queryBL
				.createCompositeQueryUpdater(I_C_BPartner_CreditLimit.class)
				.addSetColumnValue(I_C_BPartner_CreditLimit.COLUMNNAME_IsActive, false);

		queryBL.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addNotInArrayFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_CreditLimit_ID, exceptIds)
				.create()
				.update(columnUpdater);
	}

	public void deleteRecordsForBPartnerAndOrg(@NonNull final BPartnerId bpartnerId, @NonNull final OrgId orgId)
	{
		queryBL.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.stream()
				.forEach(InterfaceWrapperHelper::deleteRecord);
	}

	@NonNull
	private List<I_C_BPartner_CreditLimit> retrieveCreditLimitsToEnforceByBPartnerId(final int bpartnerId, @NonNull final Timestamp date)
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
				Comparator.<I_C_BPartner_CreditLimit, Integer>comparing(bpCreditLimit -> getCreditLimitTypeById(bpCreditLimit.getC_CreditLimit_Type_ID()).getSeqNo()).reversed();

		final Comparator<I_C_BPartner_CreditLimit> byDateFromRevesed = //
				Comparator.<I_C_BPartner_CreditLimit, Date>comparing(bpCreditLimit -> bpCreditLimit.getDateFrom()).reversed();

		return byDateFromRevesed.thenComparing(byTypeSeqNoReversed);
	}

	@Builder
	@Value
	private static class CreditLimitType
	{
		int seqNo;
		int creditLimitTypeId;
	}

	private CreditLimitType getCreditLimitTypeById(final int C_CreditLimit_Type_ID)
	{
		return cache_creditLimitById.getOrLoad(C_CreditLimit_Type_ID, () -> retrieveCreditLimitTypePOJO(C_CreditLimit_Type_ID));
	}

	private CreditLimitType retrieveCreditLimitTypePOJO(final int C_CreditLimit_Type_ID)
	{
		final I_C_CreditLimit_Type type = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_CreditLimit_Type.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CreditLimit_Type.COLUMN_C_CreditLimit_Type_ID, C_CreditLimit_Type_ID)
				.create()
				.firstOnlyNotNull(I_C_CreditLimit_Type.class);

		return CreditLimitType.builder()
				.creditLimitTypeId(type.getC_CreditLimit_Type_ID())
				.seqNo(type.getSeqNo())
				.build();
	}

	@NonNull
	public static BPartnerCreditLimit ofRecord(@NonNull final I_C_BPartner_CreditLimit creditLimit)
	{
		return BPartnerCreditLimit.builder()
				.id(BPartnerCreditLimitId.ofRepoId(creditLimit.getC_BPartner_ID(), creditLimit.getC_BPartner_CreditLimit_ID()))
				.amount(creditLimit.getAmount())
				.creditLimitTypeId(CreditLimitTypeId.ofRepoId(creditLimit.getC_CreditLimit_Type_ID()))
				.currencyId(CurrencyId.ofRepoIdOrNull(creditLimit.getC_Currency_ID()))
				.dateFrom(creditLimit.getDateFrom().toInstant())
				.active(creditLimit.isActive())
				.build();
	}
}
