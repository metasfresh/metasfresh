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

package de.metas.contacts.partialpayment.service.impl;

import de.metas.calendar.CalendarId;
import de.metas.contacts.partialpayment.InterimInvoiceOverview;
import de.metas.contacts.partialpayment.InterimInvoiceOverviewId;
import de.metas.contacts.partialpayment.service.IInterimInvoiceOverviewDAO;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_PartialPayment_Overview;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineId;
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
		return queryBL.createQueryBuilder(I_C_PartialPayment_Overview.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PartialPayment_Overview.COLUMNNAME_C_PartialPayment_Overview_ID, id)
				.create()
				.firstOptional(I_C_PartialPayment_Overview.class)
				.map(this::fromDbObject)
				.orElse(null);
	}

	public void save(@NonNull final InterimInvoiceOverview interimInvoiceOverview)
	{
		InterfaceWrapperHelper.save(toDbObject(interimInvoiceOverview));
	}

	private InterimInvoiceOverview fromDbObject(@NonNull final I_C_PartialPayment_Overview dbObject)
	{
		return InterimInvoiceOverview.builder()
				.id(InterimInvoiceOverviewId.ofRepoId(dbObject.getC_PartialPayment_Overview_ID()))
				.flatrateTermId(FlatrateTermId.ofRepoId(dbObject.getC_FlatrateTerm_ID()))
				.orderLineId(OrderLineId.ofRepoId(dbObject.getC_OrderLine_ID()))
				.withholdingInvoiceCandidateId(InvoiceCandidateId.ofRepoIdOrNull(dbObject.getC_Invoice_Candidate_Withholding_ID()))
				.partialPaymentInvoiceCandidateId(InvoiceCandidateId.ofRepoIdOrNull(dbObject.getC_PartialPayment_Overview_ID()))
				.uomId(UomId.ofRepoIdOrNull(dbObject.getC_UOM_ID()))
				.currencyId(CurrencyId.ofRepoIdOrNull(dbObject.getC_Currency_ID()))
				.calendarId(CalendarId.ofRepoIdOrNull(dbObject.getC_Calendar_ID()))
				.qtyOrdered(dbObject.getQtyOrdered())
				.qtyDelivered(dbObject.getQtyDeliveredInUOM())
				.qtyInvoiced(dbObject.getQtyInvoiced())
				.build();
	}

	private I_C_PartialPayment_Overview toDbObject(@NonNull final InterimInvoiceOverview object)
	{
		final I_C_PartialPayment_Overview dbObject = InterfaceWrapperHelper.loadOrNew(object.getId(), I_C_PartialPayment_Overview.class);
		dbObject.setC_FlatrateTerm_ID(object.getFlatrateTermId().getRepoId());
		dbObject.setC_OrderLine_ID(object.getOrderLineId().getRepoId());
		dbObject.setC_Invoice_Candidate_Withholding_ID(InvoiceCandidateId.toRepoId(object.getWithholdingInvoiceCandidateId()));
		dbObject.setC_PartialPayment_Overview_ID(InvoiceCandidateId.toRepoId(object.getPartialPaymentInvoiceCandidateId()));
		dbObject.setC_UOM_ID(UomId.toRepoId(object.getUomId()));
		dbObject.setC_Currency_ID(CurrencyId.toRepoId(object.getCurrencyId()));
		dbObject.setC_Calendar_ID(CalendarId.toRepoId(object.getCalendarId()));
		dbObject.setQtyOrdered(object.getQtyOrdered());
		dbObject.setQtyDeliveredInUOM(object.getQtyDelivered());
		dbObject.setQtyInvoiced(object.getQtyInvoiced());
		return dbObject;
	}



}
