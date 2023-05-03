/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.order.invoicecandidate.approvedforinvoice.finders.impl;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.approvedforinvoice.IApprovedForInvoicingFinder;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDependentFinder implements IApprovedForInvoicingFinder
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	@Override
	@NonNull
	public String getTableName()
	{
		return I_C_Order.Table_Name;
	}

	@Override
	@NonNull
	public List<I_C_Invoice_Candidate> findApprovedForReference(final @NonNull TableRecordReference recordReference)
	{
		final OrderId orderId = recordReference.getIdAssumingTableName(I_C_Order.Table_Name, OrderId::ofRepoId);

		final TableRecordReferenceSet recordReferenceSet = orderDAO.retrieveOrderLines(orderId)
				.stream()
				.map(orderLine -> TableRecordReference.of(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID()))
				.collect(TableRecordReferenceSet.collect());

		return invoiceCandDAO.retrieveApprovedForInvoiceReferencing(recordReferenceSet);
	}
}
