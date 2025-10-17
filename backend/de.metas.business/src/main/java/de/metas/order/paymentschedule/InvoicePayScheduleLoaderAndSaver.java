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
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InvoicePaySchedule;
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
public class InvoicePayScheduleLoaderAndSaver
{
	@NonNull private final ITrxManager trxManager;
	@NonNull private final IQueryBL queryBL;

	private final HashMap<InvoiceId, ImmutableList<I_C_InvoicePaySchedule>> recordsByInvoiceId = new HashMap<>();

	public Optional<InvoicePaySchedule> loadByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		final List<I_C_InvoicePaySchedule> records = getRecordsByInvoiceId(invoiceId);
		if (records.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableList<InvoicePayScheduleLine> lines = records.stream()
				.map(InvoicePayScheduleLoaderAndSaver::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return Optional.of(InvoicePaySchedule.ofList(invoiceId, lines));
	}

	private List<I_C_InvoicePaySchedule> getRecordsByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return CollectionUtils.getOrLoadReturningMap(recordsByInvoiceId, invoiceId, this::retrieveRecordsByInvoiceId);
	}

	private Map<InvoiceId, ImmutableList<I_C_InvoicePaySchedule>> retrieveRecordsByInvoiceId(@NonNull final Set<InvoiceId> invoiceIds)
	{
		if (invoiceIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<InvoiceId, ImmutableList<I_C_InvoicePaySchedule>> recordsByInvoiceId = queryBL.createQueryBuilder(I_C_InvoicePaySchedule.class)
				.addInArrayFilter(I_C_InvoicePaySchedule.COLUMNNAME_C_Invoice_ID, invoiceIds)
				.orderBy(I_C_InvoicePaySchedule.COLUMNNAME_C_OrderPaySchedule_ID)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						InvoicePayScheduleLoaderAndSaver::extractInvoiceId,
						ImmutableList.toImmutableList()
				));

		final HashMap<InvoiceId, ImmutableList<I_C_InvoicePaySchedule>> result = new HashMap<>();
		for (final InvoiceId invoiceId : invoiceIds)
		{
			final ImmutableList<I_C_InvoicePaySchedule> records = recordsByInvoiceId.get(invoiceId);
			result.put(invoiceId, records != null ? records : ImmutableList.of());
		}

		return result;
	}

	private void invalidateCache(final InvoiceId invoiceId)
	{
		recordsByInvoiceId.remove(invoiceId);
	}

	private static InvoiceId extractInvoiceId(@NonNull final I_C_InvoicePaySchedule record)
	{
		return InvoiceId.ofRepoId(record.getC_Invoice_ID());
	}

	@NonNull
	private static InvoicePayScheduleLine fromRecord(@NonNull final I_C_InvoicePaySchedule record)
	{
		return InvoicePayScheduleLine.builder()
				.id(InvoicePayScheduleId.ofRepoId(record.getC_InvoicePaySchedule_ID()))
				.orderPayScheduleId(OrderPayScheduleId.ofRepoId(record.getC_OrderPaySchedule_ID()))
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				// .dueAmount(Money.of(record.getDueAmt(), CurrencyId.ofRepoId(record.getC_Currency_ID()))) TODO
				.dueDate(record.getDueDate().toInstant())
				.build();
	}

	public void save(@NonNull final InvoicePaySchedule invoicePaySchedule)
	{
		trxManager.runInThreadInheritedTrx(() -> save0(invoicePaySchedule));
	}

	private void save0(@NonNull final InvoicePaySchedule invoicePaySchedule)
	{
		final InvoiceId invoiceId = invoicePaySchedule.getInvoiceId();
		final HashMap<InvoicePayScheduleId, I_C_InvoicePaySchedule> records = getRecordsByInvoiceId(invoiceId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> InvoicePayScheduleId.ofRepoId(record.getC_InvoicePaySchedule_ID())));

		for (final InvoicePayScheduleLine line : invoicePaySchedule.getLines())
		{
			final I_C_InvoicePaySchedule record = records.remove(line.getId());
			if (record == null)
			{
				throw new AdempiereException("No record found by " + line.getId());
			}

			updateRecord(record, line);
			InterfaceWrapperHelper.save(record);
		}

		InterfaceWrapperHelper.deleteAll(records.values());

		invalidateCache(invoiceId);
	}

	private static void updateRecord(final I_C_InvoicePaySchedule record, final @NotNull InvoicePayScheduleLine from)
	{
		record.setC_Order_ID(from.getOrderId().getRepoId());
		record.setC_OrderPaySchedule_ID(from.getOrderPayScheduleId().getRepoId());
		record.setC_Invoice_ID(from.getInvoiceId().getRepoId());
		record.setDueAmt(from.getDueAmount().toBigDecimal());
		// record.setC_Currency_ID(from.getDueAmount().getCurrencyId().getRepoId()); TODO
		record.setDueDate(TimeUtil.asTimestamp(from.getDueDate()));
	}

	public void updateById(@NonNull final InvoiceId invoiceId, @NonNull final Consumer<InvoicePaySchedule> updater)
	{
		trxManager.runInThreadInheritedTrx(() -> updateById0(invoiceId, updater));
	}

	private void updateById0(@NonNull final InvoiceId invoiceId, @NonNull final Consumer<InvoicePaySchedule> updater)
	{
		final InvoicePaySchedule paySchedules = loadByInvoiceId(invoiceId).orElse(null);
		if (paySchedules == null)
		{
			return;
		}

		updater.accept(paySchedules);
		save(paySchedules);
	}
}