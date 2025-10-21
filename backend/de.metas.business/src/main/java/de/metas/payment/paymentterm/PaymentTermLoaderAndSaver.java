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

package de.metas.payment.paymentterm;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.util.CoalesceUtil;
import de.metas.organization.OrgId;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_PaySchedule;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_PaymentTerm_Break;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public class PaymentTermLoaderAndSaver
{
	@NonNull private final ITrxManager trxManager;
	@NonNull private final IQueryBL queryBL;

	private final HashMap<PaymentTermId, ImmutableList<I_C_PaymentTerm_Break>> breaksByPaymentTermId = new HashMap<>();
	private final HashMap<PaymentTermId, ImmutableList<I_C_PaySchedule>> paySchedsByPaymentTermId = new HashMap<>();

	public Optional<PaymentTerm> loadByPaymentTermId(@NonNull final PaymentTermId paymentTermId)
	{
		final List<I_C_PaymentTerm_Break> breakRecords = getBreaksByPaymentTermId(paymentTermId);
		final List<I_C_PaySchedule> paysSchedRecords = getPaySchedsByPaymentTermId(paymentTermId);

		final ImmutableList<PaymentTermBreak> breaks = breakRecords.stream()
				.map(PaymentTermLoaderAndSaver::toPaymentTermBreak)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<PaySchedule> paySchedules = paysSchedRecords.stream()
				.map(PaymentTermLoaderAndSaver::toPaySchedule)
				.collect(ImmutableList.toImmutableList());

		return fromRecord()
	}

	@NonNull
	static private PaymentTerm fromRecord(@NonNull final I_C_PaymentTerm record,
										  @NonNull final ImmutableList<PaymentTermBreak> breaks,
										  @NonNull final ImmutableList<PaySchedule> paySchedules)
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
				.invoicePaySchedules(ImmutableList.of())
				.build();

	}

	@NonNull
	private static PaymentTermId extractId(@NonNull final I_C_PaymentTerm record)
	{
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}

	private List<I_C_PaymentTerm_Break> getBreaksByPaymentTermId(@NonNull final PaymentTermId paymentTermId)
	{
		return CollectionUtils.getOrLoadReturningMap(breaksByPaymentTermId, paymentTermId, this::retrieveBreaksByPaymentTermId);
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
						PaymentTermLoaderAndSaver::extractPaymentTermId,
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

	private List<I_C_PaySchedule> getPaySchedsByPaymentTermId(@NonNull final PaymentTermId paymentTermId)
	{
		return CollectionUtils.getOrLoadReturningMap(paySchedsByPaymentTermId, paymentTermId, this::retrievePaySchedsByPaymentTermId);
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
						PaymentTermLoaderAndSaver::extractPaymentTermId,
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

	private void invalidateCache(final PaymentTermId paymentTermId)
	{
		paySchedsByPaymentTermId.remove(paymentTermId);
		breaksByPaymentTermId.remove(paymentTermId);
	}

	private static PaymentTermId extractPaymentTermId(@NonNull final I_C_PaymentTerm_Break record)
	{
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}

	private static PaymentTermId extractPaymentTermId(@NonNull final I_C_PaySchedule record)
	{
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
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

	private static PaySchedule toPaySchedule(@NonNull final I_C_PaySchedule record)
	{
		final PayScheduleId id = PayScheduleId.ofRepoId(record.getC_PaySchedule_ID());
		return PaySchedule.builder()
				.id(id)
				.paymentTermId(extractPaymentTermId(record))
				.discount(record.getDiscount() != null ? Percent.of(record.getDiscount()) : null)
				.netDay(record.getNetDay())
				.discountDays(record.getDiscountDays())
				.graceDays(record.getGraceDays())
				.netDays(record.getNetDays())
				.build();
	}

	//
	// Saving and Updating
	//

	public void save(@NonNull final PaymentTerm paymentTerm)
	{
		trxManager.runInThreadInheritedTrx(() -> save0(paymentTerme));
	}

	private void save0(@NonNull final PaymentTerm paymentTerm)
	{
		// TODO

		invalidateCache(paymentTerm.getId());
	}

	private void updateRecord(@NonNull final PaymentTerm source, @NonNull final I_C_PaymentTerm target)
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
		target.setIsDefault(source.is_default());
		target.setIsComplex(source.isComplex());

		target.setDiscount(CoalesceUtil.coalesceNotNull(Percent.toBigDecimalOrNull(source.getDiscount()), BigDecimal.ZERO));
		target.setDiscount2(CoalesceUtil.coalesceNotNull(Percent.toBigDecimalOrNull(source.getDiscount2()), BigDecimal.ZERO));
	}

}