package de.metas.payment.paymentterm.impl;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

import static de.metas.util.Check.isNotBlank;

/*
 * #%L
 * de.metas.business
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

public class PaymentTermRepository implements IPaymentTermRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull private final CCache<Integer, PaymentTermMap> cache = CCache.<Integer, PaymentTermMap>builder()
			.tableName(I_C_PaymentTerm.Table_Name)
			.additionalTableNameToResetFor(I_C_PaymentTerm_Break.Table_Name)
			.initialCapacity(1)
			.build();

	private PaymentTermLoaderAndSaver newLoaderAndSaver()
	{
		return PaymentTermLoaderAndSaver.builder()
				.queryBL(queryBL)
				.trxManager(trxManager)
				.build();
	}

	@Override
	public @NonNull PaymentTerm getById(@NonNull final PaymentTermId paymentTermId)
	{
		return getByIdIfExists(paymentTermId)
				.orElseThrow(() -> new AdempiereException("No active payment term found for " + paymentTermId));
	}

	private Optional<PaymentTerm> getByIdIfExists(@NonNull final PaymentTermId paymentTermId)
	{
		return getIndexedPaymentTerms().getById(paymentTermId);
	}

	private PaymentTermMap getIndexedPaymentTerms()
	{
		return cache.getOrLoad(0, this::retrieveIndexedPaymentTerms);
	}

	private PaymentTermMap retrieveIndexedPaymentTerms()
	{
		return newLoaderAndSaver().loadAll();
	}

	@NonNull
	@Override
	public ImmutableList<PaymentTermBreak> retrievePaymentTermBreaksList(@NonNull final PaymentTermId paymentTermId)
	{
		return newLoaderAndSaver().retrievePaymentTermBreaksList(paymentTermId);
	}

	@Override
	public boolean hasPaySchedule(@NonNull final PaymentTermId paymentTermId)
	{
		return !newLoaderAndSaver().getById(paymentTermId).getPaySchedules().isEmpty();
	}

	@Override
	public boolean hasPaymentTermBreaks(@NonNull final PaymentTermId paymentTermId)
	{
		return newLoaderAndSaver().hasPaymentTermBreaks(paymentTermId);
	}

	@Override
	public void updateById(@NonNull final PaymentTermId paymentTermId, @NonNull final Consumer<PaymentTerm> updater)
	{
		newLoaderAndSaver().updateById(paymentTermId, updater);
	}

	@Override
	@Deprecated
	/*
	 * Use method de.metas.payment.paymentterm.impl.PaymentTermRepository.getById instead
	 */
	public I_C_PaymentTerm getRecordById(@NonNull final PaymentTermId paymentTermId)
	{
		return InterfaceWrapperHelper.load(paymentTermId, I_C_PaymentTerm.class);
	}

	@Nullable
	@Override
	public Percent getPaymentTermDiscount(@Nullable final PaymentTermId paymentTermId)
	{
		if (paymentTermId == null)
		{
			return Percent.ZERO;
		}

		return getById(paymentTermId).getDiscount();
	}

	// this method is implemented after a code block from MOrder.beforeSave()
	@NonNull
	@Override
	public Optional<PaymentTermId> getDefaultPaymentTermId()
	{
		final int contextPaymentTerm = Env.getContextAsInt(Env.getCtx(), "#C_PaymentTerm_ID");
		if (contextPaymentTerm > 0)
		{
			return Optional.of(PaymentTermId.ofRepoId(contextPaymentTerm));
		}

		final int dbPaymentTermId = queryBL.createQueryBuilder(I_C_PaymentTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsDefault, true)
				.addOnlyContextClient(Env.getCtx())
				.create()
				.firstId();
		if (dbPaymentTermId > 0)
		{
			return Optional.of(PaymentTermId.ofRepoId(dbPaymentTermId));
		}

		return Optional.empty();
	}

	@NonNull
	public PaymentTermId retrievePaymentTermIdNotNull(@NonNull final PaymentTermQuery query)
	{
		return retrievePaymentTermId(query)
				.orElseThrow(() -> new AdempiereException("Could not find a payment term for the given query")
						.appendParametersToMessage()
						.setParameter("PaymentTermQuery", query));
	}

	@NonNull
	@Override
	public Optional<PaymentTermId> retrievePaymentTermId(@NonNull final PaymentTermQuery query)
	{
		final IQueryBuilder<I_C_PaymentTerm> queryBuilder;
		if (query.getBPartnerId() != null)
		{
			final IQueryBuilder<I_C_BPartner> tmp = queryBL
					.createQueryBuilder(I_C_BPartner.class)
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

}
