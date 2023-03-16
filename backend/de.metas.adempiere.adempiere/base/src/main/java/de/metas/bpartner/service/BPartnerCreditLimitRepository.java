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

package de.metas.bpartner.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.creditLimit.BPartnerCreditLimit;
import de.metas.bpartner.creditLimit.BPartnerCreditLimitCreateRequest;
import de.metas.bpartner.creditLimit.BPartnerCreditLimitId;
import de.metas.bpartner.creditLimit.CreditLimitTypeId;
import de.metas.cache.CCache;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_C_CreditLimit_Type;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * @author metas-dev <dev@metasfresh.com>
 */
@Repository
public class BPartnerCreditLimitRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

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
	public Optional<I_C_BPartner_CreditLimit> retrieveCreditLimitByBPartnerId(@NonNull final BPartnerId bpartnerId, @NonNull final CreditLimitTypeId typeId)
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
						Function.identity()));
	}

	@NonNull
	public BPartnerCreditLimit getById(@NonNull final BPartnerCreditLimitId bPartnerCreditLimitId)
	{
		final I_C_BPartner_CreditLimit foundRecord = InterfaceWrapperHelper.load(bPartnerCreditLimitId.getRepoId(), I_C_BPartner_CreditLimit.class);

		return ofRecord(foundRecord);
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

	@NonNull
	public ImmutableList<JsonMetasfreshId> deleteRecordsForBPartnerAndOrg(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final OrgId orgId,
			final boolean includingProcessed)
	{
		final IQueryBuilder<I_C_BPartner_CreditLimit> queryBuilder = queryBL.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_AD_Org_ID, orgId);

		if (!includingProcessed)
		{
			queryBuilder.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_Processed, false);
		}

		final ImmutableList<I_C_BPartner_CreditLimit> recordsToDelete = queryBuilder.create()
				.stream()
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<JsonMetasfreshId> deletedIds = recordsToDelete.stream()
				.map(I_C_BPartner_CreditLimit::getC_BPartner_CreditLimit_ID)
				.map(JsonMetasfreshId::of)
				.collect(ImmutableList.toImmutableList());

		recordsToDelete
				.forEach((record) -> InterfaceWrapperHelper.delete(record, !includingProcessed));

		return deletedIds;
	}

	@NonNull
	public BPartnerCreditLimit createOrUpdate(@NonNull final BPartnerCreditLimitCreateRequest request)
	{
		final BPartnerCreditLimit creditLimit = request.getCreditLimit();

		Check.assumeEquals(creditLimit.getAmount().getCurrencyId(), getOrgCurrencyId(request.getOrgId()));

		final I_C_BPartner_CreditLimit creditLimitRecord = loadOrNew(creditLimit.getId(), I_C_BPartner_CreditLimit.class);

		if (request.getOrgId() != null)
		{
			creditLimitRecord.setAD_Org_ID(request.getOrgId().getRepoId());
		}

		creditLimitRecord.setC_CreditLimit_Type_ID(creditLimit.getCreditLimitTypeId().getRepoId());
		creditLimitRecord.setC_BPartner_ID(request.getBpartnerId().getRepoId());

		creditLimitRecord.setAmount(creditLimit.getAmount().toBigDecimal());
		creditLimitRecord.setDateFrom(TimeUtil.asTimestamp(creditLimit.getDateFrom()));

		creditLimitRecord.setIsActive(creditLimit.isActive());
		creditLimitRecord.setProcessed(creditLimit.isProcessed());

		creditLimitRecord.setApprovedBy_ID(UserId.toRepoId(creditLimit.getApprovedBy()));

		creditLimitRecord.setAD_Org_Mapping_ID(OrgMappingId.toRepoId(creditLimit.getOrgMappingId()));

		Optional.ofNullable(request.getValidatePermissions())
				.ifPresent(permissionValidator -> permissionValidator.accept(creditLimitRecord));

		saveRecord(creditLimitRecord);

		return ofRecord(creditLimitRecord);
	}

	@NonNull
	public BPartnerCreditLimit ofRecord(@NonNull final I_C_BPartner_CreditLimit creditLimit)
	{
		final OrgId orgId = OrgId.ofRepoId(creditLimit.getAD_Org_ID());
		final CurrencyId orgCurrencyId = currencyBL.getBaseCurrencyId(ClientId.ofRepoId(creditLimit.getAD_Client_ID()), orgId);
		final Money moneyInOrgCurrency = Money.of(creditLimit.getAmount(), orgCurrencyId);

		return BPartnerCreditLimit.builder()
				.id(BPartnerCreditLimitId.ofRepoId(creditLimit.getC_BPartner_CreditLimit_ID()))
				.bPartnerId(BPartnerId.ofRepoIdOrNull(creditLimit.getC_BPartner_ID()))
				.amount(moneyInOrgCurrency)
				.creditLimitTypeId(CreditLimitTypeId.ofRepoId(creditLimit.getC_CreditLimit_Type_ID()))
				.dateFrom(TimeUtil.asInstant(creditLimit.getDateFrom()))
				.orgMappingId(OrgMappingId.ofRepoIdOrNull(creditLimit.getAD_Org_Mapping_ID()))
				.active(creditLimit.isActive())
				.processed(creditLimit.isProcessed())
				.approvedBy(UserId.ofRepoIdOrNullIfSystem(creditLimit.getApprovedBy_ID()))
				.build();
	}

	@NonNull
	private List<I_C_BPartner_CreditLimit> retrieveCreditLimitsToEnforceByBPartnerId(final int bpartnerId, @NonNull final Timestamp date)
	{
		@NonNull
		final IQueryFilter<I_C_BPartner_CreditLimit> deactivatedButNotApproved = queryBL.createCompositeQueryFilter(I_C_BPartner_CreditLimit.class)
				.setJoinAnd()
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_IsActive, false)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_Processed, false);

		@NonNull
		final IQueryFilter<I_C_BPartner_CreditLimit> activatedAndApproved = queryBL.createCompositeQueryFilter(I_C_BPartner_CreditLimit.class)
				.setJoinAnd()
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_IsActive, true)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_Processed, true);

		final @NonNull IQueryFilter<I_C_BPartner_CreditLimit> activeFilter = queryBL.createCompositeQueryFilter(I_C_BPartner_CreditLimit.class)
				.setJoinOr()
				.addFilter(activatedAndApproved)
				.addFilter(deactivatedButNotApproved);

		return queryBL
				.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addCompareFilter(I_C_BPartner_CreditLimit.COLUMNNAME_DateFrom, Operator.LESS_OR_EQUAL, date)
				.addFilter(activeFilter)

				.create()
				.list();
	}

	@NonNull
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
	private CurrencyId getOrgCurrencyId(@Nullable final OrgId orgId)
	{
		final OrgId actualOrgId = Optional.ofNullable(orgId).orElseGet(Env::getOrgId);

		return currencyBL.getBaseCurrencyId(Env.getClientId(), actualOrgId);
	}
}
