/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contacts.invoice.interim.service.impl;

import de.metas.contacts.invoice.interim.InterimInvoiceOverview;
import de.metas.contacts.invoice.interim.InterimInvoiceOverviewId;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceOverviewDAO;
import de.metas.contracts.FlatrateTermId;
import org.compiere.model.I_C_InterimInvoice_FlatrateTerm;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class InterimInvoiceOverviewDAO implements IInterimInvoiceOverviewDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Nullable
	public InterimInvoiceOverview getById(@NonNull final InterimInvoiceOverviewId id)
	{
		return queryBL.createQueryBuilder(I_C_InterimInvoice_FlatrateTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_Interim_Invoice_Candidate_ID, id)
				.create()
				.firstOptional(I_C_InterimInvoice_FlatrateTerm.class)
				.map(this::fromDbObject)
				.orElse(null);
	}

	public InterimInvoiceOverviewId save(@NonNull final InterimInvoiceOverview interimInvoiceOverview)
	{
		final I_C_InterimInvoice_FlatrateTerm model = toDbObject(interimInvoiceOverview);
		InterfaceWrapperHelper.save(model);
		return InterimInvoiceOverviewId.ofRepoId(model.getC_InterimInvoice_FlatrateTerm_ID());
	}

	private InterimInvoiceOverview fromDbObject(@NonNull final I_C_InterimInvoice_FlatrateTerm dbObject)
	{
		return InterimInvoiceOverview.builder()
				.id(InterimInvoiceOverviewId.ofRepoId(dbObject.getC_InterimInvoice_FlatrateTerm_ID()))
				.flatrateTermId(FlatrateTermId.ofRepoId(dbObject.getC_Flatrate_Term_ID()))
				.orderLineId(OrderLineId.ofRepoId(dbObject.getC_OrderLine_ID()))
				.withholdingInvoiceCandidateId(InvoiceCandidateId.ofRepoIdOrNull(dbObject.getC_Invoice_Candidate_Withholding_ID()))
				.partialPaymentInvoiceCandidateId(InvoiceCandidateId.ofRepoIdOrNull(dbObject.getC_Interim_Invoice_Candidate_ID()))
				.productId(ProductId.ofRepoId(dbObject.getM_Product_ID()))
				.uomId(UomId.ofRepoIdOrNull(dbObject.getC_UOM_ID()))
				.currencyId(CurrencyId.ofRepoIdOrNull(dbObject.getC_Currency_ID()))
				.qtyOrdered(dbObject.getQtyOrdered())
				.qtyDelivered(dbObject.getQtyDeliveredInUOM())
				.qtyInvoiced(dbObject.getQtyInvoiced())
				.build();
	}

	private I_C_InterimInvoice_FlatrateTerm toDbObject(@NonNull final InterimInvoiceOverview object)
	{
		final I_C_InterimInvoice_FlatrateTerm dbObject = InterfaceWrapperHelper.loadOrNew(object.getId(), I_C_InterimInvoice_FlatrateTerm.class);
		dbObject.setC_Flatrate_Term_ID(object.getFlatrateTermId().getRepoId());
		dbObject.setC_OrderLine_ID(object.getOrderLineId().getRepoId());
		dbObject.setC_Invoice_Candidate_Withholding_ID(InvoiceCandidateId.toRepoId(object.getWithholdingInvoiceCandidateId()));
		dbObject.setC_Interim_Invoice_Candidate_ID(InvoiceCandidateId.toRepoId(object.getPartialPaymentInvoiceCandidateId()));
		dbObject.setM_Product_ID(ProductId.toRepoId(object.getProductId()));
		dbObject.setC_UOM_ID(UomId.toRepoId(object.getUomId()));
		dbObject.setC_Currency_ID(CurrencyId.toRepoId(object.getCurrencyId()));
		dbObject.setQtyOrdered(object.getQtyOrdered());
		dbObject.setQtyDeliveredInUOM(object.getQtyDelivered());
		dbObject.setQtyInvoiced(object.getQtyInvoiced());
		return dbObject;
	}

}
