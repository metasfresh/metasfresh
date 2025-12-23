/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.payment.paymentterm.repository.impl;

import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.repository.IPaymentTermRepository;
import de.metas.payment.paymentterm.repository.PaymentTermQuery;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_PaySchedule;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static de.metas.util.Check.isNotBlank;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class PaymentTermRepository implements IPaymentTermRepository
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, PaymentTermMap> cache = CCache.<Integer, PaymentTermMap>builder()
			.tableName(I_C_PaymentTerm.Table_Name)
			.additionalTableNameToResetFor(I_C_PaymentTerm_Break.Table_Name)
			.additionalTableNameToResetFor(I_C_PaySchedule.Table_Name)
			.initialCapacity(1)
			.build();

	private final CCache<BasePaymentTermIdAndDiscount, PaymentTermId> derivedPaymentTermsCache = CCache.newCache(I_C_PaymentTerm.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	@Override
	public @NonNull PaymentTerm getById(@NonNull final PaymentTermId paymentTermId)
	{
		return getByIdIfExists(paymentTermId)
				.orElseThrow(() -> new AdempiereException("No payment term found for " + paymentTermId));
	}

	public Optional<PaymentTerm> getByIdIfExists(@NonNull final PaymentTermId paymentTermId)
	{
		return getIndexedPaymentTerms().getById(paymentTermId);
	}

	// this method is implemented after a code block from MOrder.beforeSave()
	@Override
	public @NonNull Optional<PaymentTermId> getDefaultPaymentTermId()
	{
		return queryBL.createQueryBuilder(I_C_PaymentTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsDefault, true)
				.addOnlyContextClient(Env.getCtx())
				.create()
				.firstIdOptional(PaymentTermId::ofRepoIdOrNull);
	}

	@NonNull
	public PaymentTermId retrievePaymentTermIdNotNull(@NonNull final PaymentTermQuery query)
	{
		return firstIdOnly(query)
				.orElseThrow(() -> new AdempiereException("Could not find a payment term for the given query")
						.appendParametersToMessage()
						.setParameter("PaymentTermQuery", query));
	}

	@NonNull
	@Override
	public Optional<PaymentTermId> firstIdOnly(@NonNull final PaymentTermQuery query)
	{
		final IQueryBuilder<I_C_PaymentTerm> queryBuilder;
		if (query.getBPartnerId() != null)
		{
			final IQueryBuilder<I_C_BPartner> tmp = queryBL.createQueryBuilder(I_C_BPartner.class)
					.addEqualsFilter(I_C_BPartner.COLUMN_C_BPartner_ID, query.getBPartnerId());
			if (query.getSoTrx().isPurchase())
			{
				queryBuilder = tmp.andCollect(I_C_BPartner.COLUMNNAME_PO_PaymentTerm_ID, I_C_PaymentTerm.class);
			}
			else
			{
				queryBuilder = tmp.andCollect(I_C_BPartner.COLUMNNAME_C_PaymentTerm_ID, I_C_PaymentTerm.class);
			}
			queryBuilder.addOnlyActiveRecordsFilter();
		}
		else
		{
			queryBuilder = queryBL
					.createQueryBuilder(I_C_PaymentTerm.class)
					.addOnlyActiveRecordsFilter();
		}

		if (query.getOrgId() != null)
		{
			queryBuilder.addInArrayFilter(I_C_PaymentTerm.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY);
		}

		if (query.getExternalId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_ExternalId, query.getExternalId().getValue());
		}

		if (isNotBlank(query.getValue()))
		{
			queryBuilder.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Value, query.getValue());
		}

		try
		{
			final PaymentTermId firstId = queryBuilder
					.create()
					.firstIdOnly(PaymentTermId::ofRepoIdOrNull);

			if (firstId == null && query.isFallBackToDefault())
			{
				return getDefaultPaymentTermId();
			}

			return Optional.ofNullable(firstId);
		}
		catch (final DBMoreThanOneRecordsFoundException e)
		{
			// augment and rethrow
			throw e.appendParametersToMessage().setParameter("paymentTermQuery", query);
		}
	}

	private PaymentTermMap getIndexedPaymentTerms()
	{
		return cache.getOrLoad(0, this::retrieveIndexedPaymentTerms);
	}

	private PaymentTermMap retrieveIndexedPaymentTerms()
	{
		final List<PaymentTerm> paymentTerms = newLoaderAndSaver().loadAll();
		return PaymentTermMap.ofList(paymentTerms);
	}

	@Override
	public PaymentTermLoaderAndSaver newLoaderAndSaver()
	{
		return PaymentTermLoaderAndSaver.builder()
				.trxManager(trxManager)
				.queryBL(queryBL)
				.build();
	}

	@Nullable
	@Override
	public PaymentTermId getOrCreateDerivedPaymentTerm(
			@Nullable final PaymentTermId basePaymentTermId,
			@Nullable final Percent discount)
	{
		if (basePaymentTermId == null || discount == null)
		{
			return null;
		}
		return derivedPaymentTermsCache.getOrLoad(BasePaymentTermIdAndDiscount.of(basePaymentTermId, discount), this::getOrCreateDerivedPaymentTerm0);
	}

	@Value(staticConstructor = "of")
	private static class BasePaymentTermIdAndDiscount
	{
		@NonNull PaymentTermId basePaymentTermId;
		@NonNull Percent discount;
	}

	private PaymentTermId getOrCreateDerivedPaymentTerm0(@NonNull BasePaymentTermIdAndDiscount basePaymentTermIdAndDiscount)
	{
		final PaymentTermId basePaymentTermId = basePaymentTermIdAndDiscount.getBasePaymentTermId();
		final Percent discount = basePaymentTermIdAndDiscount.getDiscount();

		final I_C_PaymentTerm basePaymentTermRecord = newLoaderAndSaver().getRecordById(basePaymentTermId);

		// see if the designed payment term already exists
		final I_C_PaymentTerm existingDerivedPaymentTermRecord = queryBL.createQueryBuilderOutOfTrx(I_C_PaymentTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsValid, true)
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Discount, discount.toBigDecimal())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_AD_Client_ID, basePaymentTermRecord.getAD_Client_ID())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_AD_Org_ID, basePaymentTermRecord.getAD_Org_ID())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Discount2, basePaymentTermRecord.getDiscount2())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_DiscountDays2, basePaymentTermRecord.getDiscountDays2())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_AfterDelivery, basePaymentTermRecord.isAfterDelivery())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_FixMonthCutoff, basePaymentTermRecord.getFixMonthCutoff())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_FixMonthDay, basePaymentTermRecord.getFixMonthDay())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_FixMonthOffset, basePaymentTermRecord.getFixMonthOffset())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_GraceDays, basePaymentTermRecord.getGraceDays())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsDueFixed, basePaymentTermRecord.isDueFixed())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsNextBusinessDay, basePaymentTermRecord.isNextBusinessDay())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_NetDay, basePaymentTermRecord.getNetDay())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_NetDays, basePaymentTermRecord.getNetDays())
				.orderBy(I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID)
				.create()
				.first();
		if (existingDerivedPaymentTermRecord != null)
		{
			return PaymentTermId.ofRepoId(existingDerivedPaymentTermRecord.getC_PaymentTerm_ID());
		}

		final I_C_PaymentTerm newPaymentTerm = newInstance(I_C_PaymentTerm.class);
		InterfaceWrapperHelper.copyValues(basePaymentTermRecord, newPaymentTerm);
		newPaymentTerm.setDiscount(discount.toBigDecimal());

		final String newName = basePaymentTermRecord.getName() + " (=>" + discount.toBigDecimal() + " %)";
		newPaymentTerm.setName(newName);
		saveRecord(newPaymentTerm);

		return PaymentTermId.ofRepoId(newPaymentTerm.getC_PaymentTerm_ID());
	}
}
