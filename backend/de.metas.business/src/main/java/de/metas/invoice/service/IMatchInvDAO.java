package de.metas.invoice.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;

import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.MatchInvId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IMatchInvDAO extends ISingletonService
{
	I_M_MatchInv getById(int matchInvId);

	/**
	 * Retrieves the (active) records that reference the given invoice line.
	 */
	List<I_M_MatchInv> retrieveForInvoiceLine(I_C_InvoiceLine il);

	/**
	 * Retrieves the (active) records that reference the given invoice line.
	 */
	IQueryBuilder<I_M_MatchInv> retrieveForInvoiceLineQuery(I_C_InvoiceLine il);

	/**
	 * Retrieves the (active) records that reference the given inout line.
	 */
	List<I_M_MatchInv> retrieveForInOutLine(I_M_InOutLine iol);

	Set<MatchInvId> retrieveIdsProcessedButNotPostedForInOutLines(@NonNull Set<InOutLineId> inoutLineIds);

	Set<MatchInvId> retrieveIdsProcessedButNotPostedForInvoiceLines(@NonNull Set<InvoiceLineId> invoiceLineIds);

	/**
	 * Retrieves all (active or not) {@link I_M_MatchInv} records of given {@link I_M_InOut}.
	 */
	List<I_M_MatchInv> retrieveForInOut(I_M_InOut inout);

	/**
	 * Retrieves the quantity (in stock UOM) of given <code>iol</code> which was matched with {@link I_C_InvoiceLine}s.
	 *
	 * i.e. aggregates all (active) {@link I_M_MatchInv} records referencing the given <code>iol</code> and returns their <code>Qty</code> sum.
	 *
	 * @param initialQtys usually zero; the method will add its results to this parameter. The result will have the same UOMs.
	 */
	StockQtyAndUOMQty retrieveQtysInvoiced(I_M_InOutLine iol, StockQtyAndUOMQty initialQtys);

	/**
	 * Retrieves the quantity of given <code>invoiceLine</code> which was matched with {@link I_M_InOutLine}s.
	 *
	 * i.e. aggregates all (active) {@link I_M_MatchInv} records referencing the given <code>invoiceLine</code> and returns their <code>Qty</code> sum.
	 *
	 * NOTE: the quantity is NOT credit memo adjusted, NOR IsSOTrx adjusted.
	 */
	StockQtyAndUOMQty retrieveQtyMatched(I_C_InvoiceLine invoiceLine);

	boolean hasMatchInvs(I_C_InvoiceLine invoiceLine, I_M_InOutLine inoutLine, String trxName);
}
