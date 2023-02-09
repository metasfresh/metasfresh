package de.metas.inoutcandidate.api;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public interface IReceiptScheduleDAO extends ISingletonService
{
	<T extends I_M_ReceiptSchedule> T getById(@NonNull ReceiptScheduleId id, @NonNull Class<T> modelClass);

	/**
	 * Retrieve an iterator over receipt schedules fetched by query.
	 * <p>
	 * The receipt schedules will be ordered by {@link I_M_ReceiptSchedule#COLUMNNAME_HeaderAggregationKey}.
	 */
	Iterator<I_M_ReceiptSchedule> retrieve(IQuery<I_M_ReceiptSchedule> query);

	@Nullable
	I_M_ReceiptSchedule retrieveForRecord(@Nullable Object model);

	List<I_M_ReceiptSchedule_Alloc> retrieveRsaForRs(I_M_ReceiptSchedule rs);

	List<I_M_ReceiptSchedule_Alloc> retrieveRsaForRs(I_M_ReceiptSchedule rs, String trxName);

	I_M_ReceiptSchedule_Alloc retrieveRsaForRs(I_M_ReceiptSchedule receiptSchedule, I_M_InOutLine receiptLine);

	List<I_M_ReceiptSchedule_Alloc> retrieveRsaForInOut(I_M_InOut receipt);

	List<I_M_ReceiptSchedule_Alloc> retrieveRsaForInOutLine(org.compiere.model.I_M_InOutLine line);

	<T extends I_M_ReceiptSchedule_Alloc> IQueryBuilder<T> createRsaForRsQueryBuilder(I_M_ReceiptSchedule rs, Class<T> receiptScheduleAllocClass, String trxName);

	<T extends I_M_ReceiptSchedule_Alloc> IQueryBuilder<T> createRsaForRsQueryBuilder(I_M_ReceiptSchedule rs, Class<T> receiptScheduleAllocClass);

	/**
	 * Retrieves completed receipts for given receipt schedule.
	 */
	List<I_M_InOut> retrieveCompletedReceipts(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Retrieve all the receipt schedules for the given inout line
	 */
	List<I_M_ReceiptSchedule> retrieveRsForInOutLine(I_M_InOutLine iol);

	/**
	 * Retrieve all the receipt schedules that are linked with the given invoice candidate
	 */
	Set<I_M_ReceiptSchedule> retrieveForInvoiceCandidate(I_C_Invoice_Candidate candidate);

	IQueryBuilder<I_M_ReceiptSchedule> createQueryForReceiptScheduleSelection(Properties ctx, IQueryFilter<I_M_ReceiptSchedule> userSelectionFilter);

	boolean existsExportedReceiptScheduleForOrder(@NonNull OrderId orderId);

	<T extends I_M_ReceiptSchedule> Map<ReceiptScheduleId, T> getByIds(ImmutableSet<ReceiptScheduleId> receiptScheduleIds, Class<T> type);

	default Map<ReceiptScheduleId, I_M_ReceiptSchedule> getByIds(@NonNull final ImmutableSet<ReceiptScheduleId> receiptScheduleIds)
	{
		return getByIds(receiptScheduleIds, I_M_ReceiptSchedule.class);
	}

	I_M_ReceiptSchedule getById(ReceiptScheduleId receiptScheduleId);
}
