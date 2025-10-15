package de.metas.payment.paymentterm.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_PaySchedule;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, PaymentTermMap> cache = CCache.<Integer, PaymentTermMap>builder()
			.tableName(I_C_PaymentTerm.Table_Name)
			.additionalTableNameToResetFor(I_C_PaymentTerm_Break.Table_Name)
			.initialCapacity(1)
			.build();

	@Override
	public @NonNull PaymentTerm getById(@NonNull final PaymentTermId paymentTermId)
	{
		return getByIdIfExists(paymentTermId)
				.orElseThrow(() -> new AdempiereException("No active payment term found for " + paymentTermId));
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

	public Optional<PaymentTerm> getByIdIfExists(@NonNull final PaymentTermId paymentTermId)
	{
		return getIndexedPaymentTerms().getById(paymentTermId);
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

		final int dbPaymentTermId = queryBL
				.createQueryBuilder(I_C_PaymentTerm.class)
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

	private PaymentTermMap getIndexedPaymentTerms()
	{
		return cache.getOrLoad(0, this::retrieveIndexedPaymentTerms);
	}

	private PaymentTermMap retrieveIndexedPaymentTerms()
	{
		final ImmutableList<I_C_PaymentTerm> paymentTermRecords = queryBL
				.createQueryBuilder(I_C_PaymentTerm.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_C_PaymentTerm.class)
				.collect(ImmutableList.toImmutableList());

		if (paymentTermRecords.isEmpty())
		{
			return new PaymentTermMap(ImmutableList.of());
		}

		final ImmutableList<PaymentTermId> paymentTermIds = paymentTermRecords.stream()
				.map(PaymentTermRepository::extractId)
				.collect(ImmutableList.toImmutableList());

		final ImmutableListMultimap<PaymentTermId, PaymentTermBreak> breaksByPaymentTermId =
				retrievePaymentTermBreaksForMultipleTerms(paymentTermIds);

		final ImmutableList<PaymentTerm> paymentTerms = paymentTermRecords.stream()
				.map(record -> fromRecord(record, breaksByPaymentTermId.get(extractId(record))))
				.collect(ImmutableList.toImmutableList());

		return new PaymentTermMap(paymentTerms);

	}

	private ImmutableListMultimap<PaymentTermId, PaymentTermBreak> retrievePaymentTermBreaksForMultipleTerms(
			@NonNull final Collection<PaymentTermId> paymentTermIds)
	{
		if (paymentTermIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryBL
				.createQueryBuilder(I_C_PaymentTerm_Break.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID, paymentTermIds)
				.orderBy(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID)
				.orderBy(I_M_DiscountSchemaBreak.COLUMNNAME_SeqNo)
				.create()
				.stream(I_C_PaymentTerm_Break.class)
				.map(PaymentTermRepository::toPaymentTermBreak)
				.collect(GuavaCollectors.toImmutableListMultimap(termBreak -> termBreak.getId().getPaymentTermId()));
	}

	@NonNull
	@Override
	public ImmutableListMultimap<PaymentTermId, PaymentTermBreak> retrievePaymentTermBreaks(@NonNull final PaymentTermId paymentTermId)
	{
		return retrievePaymentTermBreaksForMultipleTerms(ImmutableList.of(paymentTermId));
	}

	static PaymentTerm fromRecord(@NonNull final I_C_PaymentTerm record, @NonNull final ImmutableList<PaymentTermBreak> breaks)
	{
		return PaymentTerm.builder()
				.id(extractId(record))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.value(record.getValue())
				.name(record.getName())
				.description(record.getDescription())
				.discountDays(record.getDiscountDays())
				.netDays(record.getNetDays())
				.graceDays(record.getGraceDays())
				.netDay(record.getNetDay())
				.discountDays(record.getDiscountDays())
				._default(record.isDefault())
				.isComplex(record.isComplex())
				.discount(Percent.of(record.getDiscount()))
				.breaks(breaks)
				.build();

	}

	@NonNull
	private static PaymentTermId extractId(@NonNull final I_C_PaymentTerm record)
	{
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}

	private static class PaymentTermMap
	{
		private final ImmutableMap<PaymentTermId, PaymentTerm> paymentTermsById;

		private PaymentTermMap(final Collection<PaymentTerm> paymentTerms)
		{
			paymentTermsById = Maps.uniqueIndex(paymentTerms, PaymentTerm::getId);
		}

		public Optional<PaymentTerm> getById(final PaymentTermId id)
		{
			return Optional.ofNullable(paymentTermsById.get(id));
		}
	}

	private static PaymentTermBreak toPaymentTermBreak(@NonNull final I_C_PaymentTerm_Break record)
	{
		final PaymentTermBreakId id = PaymentTermBreakId.ofRepoId(record.getC_PaymentTerm_ID(), record.getC_PaymentTerm_Break_ID());

		return PaymentTermBreak.builder()
				.id(id)
				.paymentTermId(id.getPaymentTermId())
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.description(record.getDescription())
				.percent(Percent.of(record.getPercent()))
				.referenceDateType(ReferenceDateType.ofCode(record.getReferenceDateType()))
				.offsetDays(record.getOffsetDays())
				.build();
	}

	@Override
	public PaymentTermBreak getPaymentTermBreakById(@NonNull final PaymentTermBreakId id)
	{
		return getById(id.getPaymentTermId()).getBreakById(id);
	}


	@Override
	public boolean hasPaySchedule(@NonNull final PaymentTermId paymentTermId)
	{
		return queryBL
				.createQueryBuilder(I_C_PaySchedule.class)
				.addEqualsFilter(I_C_PaySchedule.COLUMNNAME_C_PaymentTerm_ID, paymentTermId)
				.create()
				.anyMatch();
	}

}
