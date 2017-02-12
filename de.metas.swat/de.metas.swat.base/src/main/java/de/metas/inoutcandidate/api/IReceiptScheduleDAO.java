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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.ISingletonService;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public interface IReceiptScheduleDAO extends ISingletonService
{
	/**
	 * Retrieve an iterator over receipt schedules fetched by query.
	 * 
	 * The receipt schedules will be ordered by {@link I_M_ReceiptSchedule#COLUMNNAME_HeaderAggregationKey}.
	 * 
	 * @param query
	 * @return
	 */
	Iterator<I_M_ReceiptSchedule> retrieve(final IQuery<I_M_ReceiptSchedule> query);

	I_M_ReceiptSchedule retrieveForRecord(final Object model);

	List<I_M_ReceiptSchedule_Alloc> retrieveRsaForRs(I_M_ReceiptSchedule rs);

	List<I_M_ReceiptSchedule_Alloc> retrieveRsaForRs(I_M_ReceiptSchedule rs, String trxName);

	I_M_ReceiptSchedule_Alloc retrieveRsaForRs(I_M_ReceiptSchedule receiptSchedule, I_M_InOutLine receiptLine);

	List<I_M_ReceiptSchedule_Alloc> retrieveRsaForInOutLine(org.compiere.model.I_M_InOutLine line);

	<T extends I_M_ReceiptSchedule_Alloc> IQueryBuilder<T> createRsaForRsQueryBuilder(I_M_ReceiptSchedule rs, Class<T> receiptScheduleAllocClass, String trxName);

	<T extends I_M_ReceiptSchedule_Alloc> IQueryBuilder<T> createRsaForRsQueryBuilder(I_M_ReceiptSchedule rs, Class<T> receiptScheduleAllocClass);

	/**
	 * Retrieves completed receipts for given receipt schedule.
	 * 
	 * @param receiptSchedule
	 * @return
	 */
	List<I_M_InOut> retrieveCompletedReceipts(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Retrieve all the receipt schedules for the given inout line
	 * 
	 * @param iol
	 * @return
	 */
	List<I_M_ReceiptSchedule> retrieveRsForInOutLine(I_M_InOutLine iol);

	/**
	 * Retrieve all the receipt schedules that are linked with the given invoice candidate
	 * 
	 * @param candidate
	 * @return
	 */
	Set<I_M_ReceiptSchedule> retrieveForInvoiceCandidate(I_C_Invoice_Candidate candidate);
}
