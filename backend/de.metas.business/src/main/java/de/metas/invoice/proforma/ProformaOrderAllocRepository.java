/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoice.proforma;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Proforma_Order_Alloc;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ProformaOrderAllocRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ProformaOrderAlloc getById(@NonNull final ProformaOrderAllocId proformaOrderAllocId)
	{
		return fromRecord(load(proformaOrderAllocId, I_C_Proforma_Order_Alloc.class));
	}

	public ProformaOrderAlloc create(@NonNull final InvoiceId proformaInvoiceId, @NonNull final OrderId purchaseOrderId)
	{
		final I_C_Proforma_Order_Alloc record = newInstance(I_C_Proforma_Order_Alloc.class);
		record.setC_Invoice_ID(proformaInvoiceId.getRepoId());
		record.setC_Order_ID(purchaseOrderId.getRepoId());
		saveRecord(record);
		return fromRecord(record);
	}

	public boolean existsByInvoiceAndOrder(
			@NonNull final InvoiceId invoiceId,
			@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_Proforma_Order_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Proforma_Order_Alloc.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addEqualsFilter(I_C_Proforma_Order_Alloc.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.anyMatch();
	}

	public boolean existsByOrder(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_Proforma_Order_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Proforma_Order_Alloc.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.anyMatch();
	}

	@NonNull
	public ImmutableList<ProformaOrderAlloc> getByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return queryBL.createQueryBuilder(I_C_Proforma_Order_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Proforma_Order_Alloc.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create()
				.stream()
				.map(ProformaOrderAllocRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ImmutableList<ProformaOrderAlloc> getByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_Proforma_Order_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Proforma_Order_Alloc.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.stream()
				.map(ProformaOrderAllocRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Returns the {@link OrderId} of the active proforma-order allocation for the given proforma invoice, or empty if none.
	 * This is a narrower projection than {@link #findByProformaInvoiceId} — prefer it when only the order ID is needed.
	 */
	@NonNull
	public Optional<OrderId> findOrderIdByProformaInvoiceId(@NonNull final InvoiceId proformaInvoiceId)
	{
		return queryBL.createQueryBuilder(I_C_Proforma_Order_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Proforma_Order_Alloc.COLUMNNAME_C_Invoice_ID, proformaInvoiceId)
				.create()
				.firstOnlyOptional(I_C_Proforma_Order_Alloc.class)
				.map(r -> OrderId.ofRepoId(r.getC_Order_ID()));
	}

	@NonNull
	public Optional<InvoiceId> findProformaInvoiceIdByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_Proforma_Order_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Proforma_Order_Alloc.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.firstOnlyOptional(I_C_Proforma_Order_Alloc.class)
				.map(r -> InvoiceId.ofRepoId(r.getC_Invoice_ID()));
	}

	/**
	 * Returns the single active proforma-order allocation for the given proforma invoice, or empty if none exists.
	 * Used by the C_Payment interceptor to look up the order after a payment status change.
	 */
	@NonNull
	public Optional<ProformaOrderAlloc> findByProformaInvoiceId(@NonNull final InvoiceId proformaInvoiceId)
	{
		return queryBL.createQueryBuilder(I_C_Proforma_Order_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Proforma_Order_Alloc.COLUMNNAME_C_Invoice_ID, proformaInvoiceId)
				.create()
				.firstOnlyOptional(I_C_Proforma_Order_Alloc.class)
				.map(ProformaOrderAllocRepository::fromRecord);
	}

	/**
	 * Returns the single active proforma-order allocation for the given order, or empty if none exists.
	 * The unique partial index on {@code (C_Order_ID) WHERE IsActive='Y'} guarantees at most one active record.
	 */
	@NonNull
	public Optional<ProformaOrderAlloc> findActiveByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_Proforma_Order_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Proforma_Order_Alloc.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.firstOnlyOptional(I_C_Proforma_Order_Alloc.class)
				.map(ProformaOrderAllocRepository::fromRecord);
	}

	public void deleteById(@NonNull final ProformaOrderAllocId proformaOrderAllocId)
	{
		queryBL.createQueryBuilder(I_C_Proforma_Order_Alloc.class)
				.addEqualsFilter(I_C_Proforma_Order_Alloc.COLUMNNAME_C_Proforma_Order_Alloc_ID, proformaOrderAllocId)
				.create()
				.delete();
	}

	private static ProformaOrderAlloc fromRecord(@NonNull final I_C_Proforma_Order_Alloc record)
	{
		return ProformaOrderAlloc.builder()
				.id(ProformaOrderAllocId.ofRepoId(record.getC_Proforma_Order_Alloc_ID()))
				.invoiceId(InvoiceId.ofRepoId(record.getC_Invoice_ID()))
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.build();
	}
}
