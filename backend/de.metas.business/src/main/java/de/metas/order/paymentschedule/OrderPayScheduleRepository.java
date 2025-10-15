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

import de.metas.order.OrderId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import de.metas.util.lang.SeqNoProvider;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Consumer;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class OrderPayScheduleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private OrderPayScheduleLoaderAndSaver newLoaderAndSaver()
	{
		return OrderPayScheduleLoaderAndSaver.builder()
				.queryBL(queryBL)
				.build();
	}

	public void create(@NonNull final OrderPayScheduleCreateRequest request)
	{
		final SeqNoProvider seqNoProvider = SeqNoProvider.ofInt(10);
		request.getLines()
				.forEach(line -> createLine(line, request.getOrderId(), seqNoProvider.getAndIncrement()));
	}

	private void createLine(@NonNull final OrderPayScheduleCreateRequest.Line request, @NonNull final OrderId orderId, @NonNull final SeqNo seqNo)
	{
		final I_C_OrderPaySchedule record = newInstance(I_C_OrderPaySchedule.class);
		record.setC_Order_ID(orderId.getRepoId());
		record.setC_PaymentTerm_ID(request.getPaymentTermBreakId().getPaymentTermId().getRepoId());
		record.setC_PaymentTerm_Break_ID(request.getPaymentTermBreakId().getRepoId());
		record.setDueAmt(request.getDueAmount().toBigDecimal());
		record.setC_Currency_ID(request.getDueAmount().getCurrencyId().getRepoId());
		record.setDueDate(TimeUtil.asTimestamp(request.getDueDate()));
		record.setPercent(request.getPercent().toInt());
		record.setReferenceDateType(request.getReferenceDateType().getCode());
		record.setSeqNo(seqNo.toInt());
		record.setStatus(OrderPayScheduleStatus.toCodeOrNull(request.getOrderPayScheduleStatus()));
		saveRecord(record);
	}

	@NonNull
	public Optional<OrderPaySchedule> getByOrderId(@NonNull final OrderId orderId)
	{
		final OrderPaySchedule schedule = newLoaderAndSaver().loadByOrderId(orderId);

		if (schedule.getLines().isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(schedule);

	}

	public void deleteByOrderId(@NonNull final OrderId orderId)
	{
		queryBL.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.delete();
	}

	public void updateById(final OrderPayScheduleId id, final Consumer<OrderPayScheduleLine> updater)
	{
		trxManager.callInThreadInheritedTrx(() -> newLoaderAndSaver().updateById(id, updater));
	}

	public void save(final OrderPaySchedule orderPaySchedule) {newLoaderAndSaver().save(orderPaySchedule);}
}
