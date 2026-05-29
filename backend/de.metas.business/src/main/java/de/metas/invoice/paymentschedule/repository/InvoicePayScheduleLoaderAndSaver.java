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

package de.metas.invoice.paymentschedule.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentschedule.InvoicePaySchedule;
import de.metas.invoice.paymentschedule.InvoicePayScheduleLine;
import de.metas.invoice.paymentschedule.InvoicePayScheduleLineId;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InvoicePaySchedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Builder
class InvoicePayScheduleLoaderAndSaver
{
	@NonNull private final ITrxManager trxManager;
	@NonNull private final IQueryBL queryBL;

	private final HashMap<InvoiceId, ImmutableList<I_C_InvoicePaySchedule>> recordsByInvoiceId = new HashMap<>();

	public Optional<InvoicePaySchedule> loadByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return getRecordsByInvoiceId(invoiceId).stream()
				.map(InvoicePayScheduleConverter::fromRecord)
				.collect(InvoicePaySchedule.collect());
	}

	private void warmUpByInvoiceIds(final Set<InvoiceId> invoiceIds)
	{
		CollectionUtils.getAllOrLoad(recordsByInvoiceId, invoiceIds, this::retrieveRecordsByInvoiceId);
	}

	private List<I_C_InvoicePaySchedule> getRecordsByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return CollectionUtils.getOrLoad(recordsByInvoiceId, invoiceId, this::retrieveRecordsByInvoiceId);
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

	private void save0(@NonNull final InvoicePaySchedule invoicePaySchedule)
	{
		final InvoiceId invoiceId = invoicePaySchedule.getInvoiceId();
		final HashMap<InvoicePayScheduleLineId, I_C_InvoicePaySchedule> records = getRecordsByInvoiceId(invoiceId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> InvoicePayScheduleLineId.ofRepoId(record.getC_InvoicePaySchedule_ID())));

		for (final InvoicePayScheduleLine line : invoicePaySchedule.getLines())
		{
			final I_C_InvoicePaySchedule record = records.remove(line.getId());
			if (record == null)
			{
				throw new AdempiereException("No record found by " + line.getId());
			}

			InvoicePayScheduleConverter.updateRecord(record, line);
			InterfaceWrapperHelper.save(record);
		}

		InterfaceWrapperHelper.deleteAll(records.values());

		invalidateCache(invoiceId);
	}

	public void updateByIds(@NonNull final Set<InvoiceId> invoiceIds, @NonNull final Consumer<InvoicePaySchedule> updater)
	{
		if (invoiceIds.isEmpty()) {return;}

		trxManager.runInThreadInheritedTrx(() -> {
			warmUpByInvoiceIds(invoiceIds);
			invoiceIds.forEach(invoiceId -> updateById0(invoiceId, updater));
		});
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
		save0(paySchedules);
	}
}