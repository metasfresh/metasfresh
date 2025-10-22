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

package de.metas.payment.paymentterm.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaySchedule;
import de.metas.payment.paymentterm.PayScheduleId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_PaySchedule;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Builder
public class PaymentTermLoaderAndSaver
{
	@NonNull private final ITrxManager trxManager;
	@NonNull private final IQueryBL queryBL;

	@NonNull private final HashMap<PaymentTermId, ImmutableList<I_C_PaymentTerm_Break>> breaksByPaymentTermId = new HashMap<>();
	@NonNull private final HashMap<PaymentTermId, ImmutableList<I_C_PaySchedule>> paySchedulesByPaymentTermId = new HashMap<>();
	
	

	public PaymentTermMap loadAll()
	{
		final ImmutableList<I_C_PaymentTerm> paymentTermRecords = queryBL.createQueryBuilder(I_C_PaymentTerm.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_C_PaymentTerm.class)
				.collect(ImmutableList.toImmutableList());

		if (paymentTermRecords.isEmpty())
		{
			return new PaymentTermMap(ImmutableList.of());
		}

		final ImmutableSet<PaymentTermId> paymentTermIds = paymentTermRecords.stream()
				.map(PaymentTermLoaderAndSaver::extractId)
				.collect(ImmutableSet.toImmutableSet());

		final Map<PaymentTermId, ImmutableList<I_C_PaymentTerm_Break>> paymentTermBreaksByPaymentTermId = getBreaksByPaymentTermIds(paymentTermIds);
		final Map<PaymentTermId, ImmutableList<I_C_PaySchedule>> paySchedulesByPaymentTermId = getPaySchedulesByPaymentTermIds(paymentTermIds);

		final ImmutableList<PaymentTerm> paymentTerms = paymentTermRecords.stream()
				.map(record -> fromRecord(record, paymentTermBreaksByPaymentTermId, paySchedulesByPaymentTermId))
				.collect(ImmutableList.toImmutableList());

		return new PaymentTermMap(paymentTerms);
	}

	@NonNull
	public ImmutableList<PaymentTermBreak> retrievePaymentTermBreaksList(@NonNull final PaymentTermId paymentTermId)
	{
		return ImmutableList.copyOf(retrievePaymentTermBreaksForMultipleTerms(ImmutableList.of(paymentTermId)).get(paymentTermId));
	}

	@NonNull
	private ImmutableListMultimap<PaymentTermId, PaymentTermBreak> retrievePaymentTermBreaksForMultipleTerms(@NonNull final Collection<PaymentTermId> paymentTermIds)
	{
		if (paymentTermIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryPaymentTermBreaks(paymentTermIds)
				.stream(I_C_PaymentTerm_Break.class)
				.map(PaymentTermLoaderAndSaver::fromRecord)
				.collect(GuavaCollectors.toImmutableListMultimap(termBreak -> termBreak.getId().getPaymentTermId()));
	}

	private IQuery<I_C_PaymentTerm_Break> queryPaymentTermBreaks(final @NotNull Collection<PaymentTermId> paymentTermIds)
	{
		Check.assumeNotEmpty(paymentTermIds, "paymentTermIds is not empty");
		return queryBL.createQueryBuilder(I_C_PaymentTerm_Break.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID, paymentTermIds)
				.orderBy(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID)
				.orderBy(I_M_DiscountSchemaBreak.COLUMNNAME_SeqNo)
				.create();
	}

	@NonNull
	private static PaymentTermId extractId(@NonNull final I_C_PaymentTerm record)
	{
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}

	public boolean hasPaymentTermBreaks(final @NonNull PaymentTermId paymentTermId)
	{
		return queryPaymentTermBreaks(ImmutableSet.of(paymentTermId)).anyMatch();
	}

	public boolean hasPaySchedule(final @NonNull PaymentTermId paymentTermId)
	{
		return queryPaySchedules(ImmutableSet.of(paymentTermId)).anyMatch();
	}

	private IQuery<I_C_PaySchedule> queryPaySchedules(final @NotNull Collection<PaymentTermId> paymentTermIds)
	{
		Check.assumeNotEmpty(paymentTermIds, "paymentTermIds is not empty");
		return queryBL.createQueryBuilder(I_C_PaySchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_PaySchedule.COLUMNNAME_C_PaymentTerm_ID, paymentTermIds)
				.orderBy(I_C_PaySchedule.COLUMNNAME_C_PaymentTerm_ID)
				.create();
	}

	private static PaymentTermBreak fromRecord(@NonNull final I_C_PaymentTerm_Break record)
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

	@NonNull
	static private PaymentTerm fromRecord(
			@NonNull final I_C_PaymentTerm record,
			@NonNull final Map<PaymentTermId, ImmutableList<I_C_PaymentTerm_Break>> paymentTermBreaksByPaymentTermId,
			@NonNull final Map<PaymentTermId, ImmutableList<I_C_PaySchedule>> paySchedulesByPaymentTermId)
	{
		final PaymentTermId paymentTermId = extractId(record);
		return PaymentTerm.builder()
				.id(paymentTermId)
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
				.isDefault(record.isDefault())
				.discount(Percent.of(record.getDiscount()))
				.breaks(paymentTermBreaksByPaymentTermId.get(paymentTermId)
						.stream()
						.map(PaymentTermLoaderAndSaver::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.paySchedules(paySchedulesByPaymentTermId.get(paymentTermId)
						.stream()
						.map(PaymentTermLoaderAndSaver::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.build();

	}

	private List<I_C_PaymentTerm_Break> getBreaksByPaymentTermId(@NonNull final PaymentTermId paymentTermId)
	{
		return CollectionUtils.getOrLoadReturningMap(breaksByPaymentTermId, paymentTermId, this::retrieveBreaksByPaymentTermId);
	}

	private Map<PaymentTermId, ImmutableList<I_C_PaymentTerm_Break>> getBreaksByPaymentTermIds(@NonNull final Set<PaymentTermId> paymentTermIds)
	{
		return CollectionUtils.getAllOrLoadReturningMap(breaksByPaymentTermId, paymentTermIds, this::retrieveBreaksByPaymentTermId);
	}

	private Map<PaymentTermId, ImmutableList<I_C_PaymentTerm_Break>> retrieveBreaksByPaymentTermId(@NonNull final Set<PaymentTermId> paymentTermIds)
	{
		if (paymentTermIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<PaymentTermId, ImmutableList<I_C_PaymentTerm_Break>> recordsByPaymentTermId = queryBL.createQueryBuilder(I_C_PaymentTerm_Break.class)
				.addInArrayFilter(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID, paymentTermIds)
				.orderBy(I_C_PaymentTerm_Break.COLUMNNAME_SeqNo)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						PaymentTermLoaderAndSaver::extractPaymentTermIdFromBreakRecord,
						ImmutableList.toImmutableList()
				));

		final HashMap<PaymentTermId, ImmutableList<I_C_PaymentTerm_Break>> result = new HashMap<>();
		for (final PaymentTermId paymentTermId : paymentTermIds)
		{
			final ImmutableList<I_C_PaymentTerm_Break> records = recordsByPaymentTermId.get(paymentTermId);
			result.put(paymentTermId, records != null ? records : ImmutableList.of());
		}

		return result;
	}

	private Map<PaymentTermId, ImmutableList<I_C_PaySchedule>> getPaySchedulesByPaymentTermIds(@NonNull final Set<PaymentTermId> paymentTermIds)
	{
		return CollectionUtils.getAllOrLoadReturningMap(paySchedulesByPaymentTermId, paymentTermIds, this::retrievePaySchedsByPaymentTermId);
	}

	private List<I_C_PaySchedule> getPaySchedulesByPaymentTermId(@NonNull final PaymentTermId paymentTermId)
	{
		return CollectionUtils.getOrLoadReturningMap(paySchedulesByPaymentTermId, paymentTermId, this::retrievePaySchedsByPaymentTermId);
	}

	private Map<PaymentTermId, ImmutableList<I_C_PaySchedule>> retrievePaySchedsByPaymentTermId(@NonNull final Set<PaymentTermId> paymentTermIds)
	{
		if (paymentTermIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<PaymentTermId, ImmutableList<I_C_PaySchedule>> recordsByPaymentTermId = queryBL.createQueryBuilder(I_C_PaySchedule.class)
				.addInArrayFilter(I_C_PaySchedule.COLUMNNAME_C_PaymentTerm_ID, paymentTermIds)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						PaymentTermLoaderAndSaver::extractPaymentTermIdFromPayScheduleRecord,
						ImmutableList.toImmutableList()
				));

		final HashMap<PaymentTermId, ImmutableList<I_C_PaySchedule>> result = new HashMap<>();
		for (final PaymentTermId paymentTermId : paymentTermIds)
		{
			final ImmutableList<I_C_PaySchedule> records = recordsByPaymentTermId.get(paymentTermId);
			result.put(paymentTermId, records != null ? records : ImmutableList.of());
		}

		return result;
	}

	private static PaySchedule fromRecord(@NonNull final I_C_PaySchedule record)
	{
		final PayScheduleId id = PayScheduleId.ofRepoId(record.getC_PaySchedule_ID());
		return PaySchedule.builder()
				.id(id)
				.paymentTermId(extractPaymentTermIdFromPayScheduleRecord(record))
				.discount(Percent.of(record.getDiscount()))
				.netDay(record.getNetDay())
				.discountDays(record.getDiscountDays())
				.graceDays(record.getGraceDays())
				.netDays(record.getNetDays())
				.build();
	}

	private static PaymentTermId extractPaymentTermIdFromBreakRecord(@NonNull final I_C_PaymentTerm_Break record)
	{
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}

	private static PaymentTermId extractPaymentTermIdFromPayScheduleRecord(@NonNull final I_C_PaySchedule record) {return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());}

	private void invalidateCache(final PaymentTermId paymentTermId)
	{
		paySchedulesByPaymentTermId.remove(paymentTermId);
		breaksByPaymentTermId.remove(paymentTermId);
	}

	//
	// Saving and Updating
	//

	public void save(@NonNull final PaymentTerm paymentTerm)
	{
		trxManager.runInThreadInheritedTrx(() -> save0(paymentTerm));
	}

	private void save0(@NonNull final PaymentTerm paymentTerm)
	{
		final PaymentTermId paymentTermId = paymentTerm.getId();
		final I_C_PaymentTerm paymentTermRecord = InterfaceWrapperHelper.load(paymentTermId, I_C_PaymentTerm.class);

		// main
		updateRecord(paymentTerm, paymentTermRecord);
		InterfaceWrapperHelper.save(paymentTermRecord);

		// paymentTerm.getSortedBreaks().forEach(line -> savePaymentTermBreak(line, paymentTermId));
		// paymentTerm.getPaySchedules().forEach(line -> savePaySchedule(line, paymentTermId));

		// breaks
		final HashMap<PaymentTermBreakId, I_C_PaymentTerm_Break> paymentTermBreakRecords = getBreaksByPaymentTermId(paymentTermId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> PaymentTermBreakId.ofRepoId(paymentTermId, record.getC_PaymentTerm_Break_ID())));

		for (final PaymentTermBreak paymentTermBreak : paymentTerm.getSortedBreaks())
		{
			final I_C_PaymentTerm_Break record = paymentTermBreakRecords.remove(paymentTermBreak.getId());
			if (record == null)
			{
				throw new AdempiereException("No Payment term break found by " + paymentTermBreak.getId());
			}

			updateRecord(record, paymentTermBreak);
			InterfaceWrapperHelper.save(record);
		}

		// pay schedules
		final HashMap<PayScheduleId, I_C_PaySchedule> payScheduleRecords = getPaySchedulesByPaymentTermId(paymentTermId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> PayScheduleId.ofRepoId(record.getC_PaySchedule_ID())));

		for (final PaySchedule paySchedule : paymentTerm.getPaySchedules())
		{
			final I_C_PaySchedule record = payScheduleRecords.remove(paySchedule.getId());
			if (record == null)
			{
				throw new AdempiereException("No Pay schedule found by " + paySchedule.getId());
			}

			updateRecord(record, paySchedule);
			InterfaceWrapperHelper.save(record);
		}

		invalidateCache(paymentTermId);
	}

	private static void updateRecord(@NonNull final PaymentTerm source, @NonNull final I_C_PaymentTerm target)
	{
		target.setAD_Org_ID(source.getOrgId().getRepoId());
		target.setValue(source.getValue());
		target.setName(source.getName());
		target.setDescription(source.getDescription());

		target.setDiscountDays(source.getDiscountDays());
		target.setDiscountDays2(source.getDiscountDays2());
		target.setNetDays(source.getNetDays());
		target.setGraceDays(source.getGraceDays());
		target.setNetDay(source.getNetDay());
		target.setIsDefault(source.isDefault());
		target.setIsComplex(source.isComplex());

		target.setDiscount(CoalesceUtil.coalesceNotNull(Percent.toBigDecimalOrNull(source.getDiscount()), BigDecimal.ZERO));
		target.setDiscount2(CoalesceUtil.coalesceNotNull(Percent.toBigDecimalOrNull(source.getDiscount2()), BigDecimal.ZERO));
	}

	private static void updateRecord(@NonNull final I_C_PaymentTerm_Break record, @NonNull final PaymentTermBreak from)
	{
		record.setDescription(from.getDescription());
		record.setSeqNo(from.getSeqNo().toInt());
		record.setReferenceDateType(from.getReferenceDateType().getCode());
		record.setPercent(from.getPercent().toInt());
		record.setOffsetDays(from.getOffsetDays());
	}

	private static void updateRecord(@NonNull final I_C_PaySchedule record, @NonNull final PaySchedule from)
	{
		record.setDiscount(from.getDiscount().toBigDecimal());
		record.setNetDay(from.getNetDay());
		record.setDiscountDays(from.getDiscountDays());
		record.setGraceDays(from.getGraceDays());
		record.setNetDays(from.getNetDays());
		record.setPercentage(from.getPercentage().toBigDecimal());
	}

	public void updateById(@NonNull final PaymentTermId paymentTermId, @NonNull final Consumer<PaymentTerm> updater)
	{
		trxManager.runInThreadInheritedTrx(() -> updateById0(paymentTermId, updater));
	}

	private void updateById0(@NonNull final PaymentTermId paymentTermId, @NonNull final Consumer<PaymentTerm> updater)
	{
		final PaymentTerm paymentTerm = getByIdIfExists(paymentTermId).orElse(null);
		if (paymentTerm == null)
		{
			return;
		}

		updater.accept(paymentTerm);
		save(paymentTerm);
	}
}