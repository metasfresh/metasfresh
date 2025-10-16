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

package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
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
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Builder
public class OrderPayScheduleLoaderAndSaver
{
	@NonNull private final ITrxManager trxManager;
	@NonNull private final IQueryBL queryBL;

	private final HashMap<OrderId, ImmutableList<I_C_OrderPaySchedule>> recordsByOrderId = new HashMap<>();

	public Optional<OrderPaySchedule> loadByOrderId(@NonNull final OrderId orderId)
	{
		final List<I_C_OrderPaySchedule> records = getRecordsByOrderId(orderId);
		if (records.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableList<OrderPayScheduleLine> lines = records.stream()
				.map(OrderPayScheduleLoaderAndSaver::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return Optional.of(OrderPaySchedule.ofList(orderId, lines));
	}

	private List<I_C_OrderPaySchedule> getRecordsByOrderId(@NonNull final OrderId orderId)
	{
		return CollectionUtils.getOrLoadReturningMap(recordsByOrderId, orderId, this::retrieveRecordsByOrderId);
	}

	private Map<OrderId, ImmutableList<I_C_OrderPaySchedule>> retrieveRecordsByOrderId(@NonNull final Set<OrderId> orderIds)
	{
		if (orderIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<OrderId, ImmutableList<I_C_OrderPaySchedule>> recordsByOrderId = queryBL.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addInArrayFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderIds)
				.orderBy(I_C_OrderPaySchedule.COLUMNNAME_SeqNo)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						OrderPayScheduleLoaderAndSaver::extractOrderId,
						ImmutableList.toImmutableList()
				));

		final HashMap<OrderId, ImmutableList<I_C_OrderPaySchedule>> result = new HashMap<>();
		for (final OrderId orderId : orderIds)
		{
			final ImmutableList<I_C_OrderPaySchedule> records = recordsByOrderId.get(orderId);
			result.put(orderId, records != null ? records : ImmutableList.of());
		}

		return result;
	}

	private void invalidateCache(final OrderId orderId)
	{
		recordsByOrderId.remove(orderId);
	}

	private static OrderId extractOrderId(@NonNull final I_C_OrderPaySchedule record)
	{
		return OrderId.ofRepoId(record.getC_Order_ID());
	}

	@NonNull
	private static OrderPayScheduleLine fromRecord(@NonNull final I_C_OrderPaySchedule record)
	{
		return OrderPayScheduleLine.builder()
				.id(OrderPayScheduleId.ofRepoId(record.getC_OrderPaySchedule_ID()))
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.paymentTermBreakId(PaymentTermBreakId.ofRepoId(record.getC_PaymentTerm_ID(), record.getC_PaymentTerm_Break_ID()))
				.dueAmount(Money.of(record.getDueAmt(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.dueDate(record.getDueDate().toInstant())
				.percent(Percent.of(record.getPercent()))
				.offsetDays(record.getOffsetDays())
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.referenceDateType(ReferenceDateType.ofCode(record.getReferenceDateType()))
				.status(OrderPayScheduleStatus.ofCode(record.getStatus()))
				.build();
	}

	//
	//
	//
	// --- Saving and Updating --------------------------------------------------
	//
	//
	//

	public void save(@NonNull final OrderPaySchedule orderPaySchedule)
	{
		trxManager.runInThreadInheritedTrx(() -> save0(orderPaySchedule));
	}

	private void save0(@NonNull final OrderPaySchedule orderPaySchedule)
	{
		final OrderId orderId = orderPaySchedule.getOrderId();
		final HashMap<OrderPayScheduleId, I_C_OrderPaySchedule> records = getRecordsByOrderId(orderId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> OrderPayScheduleId.ofRepoId(record.getC_OrderPaySchedule_ID())));

		for (final OrderPayScheduleLine line : orderPaySchedule.getLines())
		{
			final I_C_OrderPaySchedule record = records.remove(line.getId());
			if (record == null)
			{
				throw new AdempiereException("No record found by " + line.getId());
			}

			updateRecord(record, line);
			InterfaceWrapperHelper.save(record);
		}

		InterfaceWrapperHelper.deleteAll(records.values());

		invalidateCache(orderId);
	}

	private static void updateRecord(final I_C_OrderPaySchedule record, final @NotNull OrderPayScheduleLine from)
	{
		record.setC_Order_ID(from.getOrderId().getRepoId());
		record.setC_PaymentTerm_ID(from.getPaymentTermBreakId().getPaymentTermId().getRepoId());
		record.setC_PaymentTerm_Break_ID(from.getPaymentTermBreakId().getRepoId());
		record.setDueAmt(from.getDueAmount().toBigDecimal());
		record.setC_Currency_ID(from.getDueAmount().getCurrencyId().getRepoId());
		record.setDueDate(TimeUtil.asTimestamp(from.getDueDate()));
		record.setPercent(from.getPercent().toInt());
		record.setOffsetDays(from.getOffsetDays());
		record.setReferenceDateType(from.getReferenceDateType().getCode());
		record.setSeqNo(from.getSeqNo().toInt());
		record.setStatus(OrderPayScheduleStatus.toCodeOrNull(from.getStatus()));
	}

	public void updateById(@NonNull final OrderId orderId, @NonNull final Consumer<OrderPaySchedule> updater)
	{
		trxManager.runInThreadInheritedTrx(() -> updateById0(orderId, updater));
	}

	private void updateById0(@NonNull final OrderId orderId, @NonNull final Consumer<OrderPaySchedule> updater)
	{
		final OrderPaySchedule paySchedules = loadByOrderId(orderId).orElse(null);
		if (paySchedules == null)
		{
			return;
		}

		updater.accept(paySchedules);
		save(paySchedules);
	}
}