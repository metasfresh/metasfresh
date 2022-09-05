package de.metas.contracts.commission.salesrep.interceptor;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductBL;
import de.metas.contracts.commission.salesrep.DocumentSalesRepDescriptor;
import de.metas.contracts.commission.salesrep.DocumentSalesRepDescriptorFactory;
import de.metas.contracts.commission.salesrep.DocumentSalesRepDescriptorService;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Component
@Callout(I_C_Invoice.class)
@Interceptor(I_C_Invoice.class)
public class C_Invoice
{
	private final DocumentSalesRepDescriptorFactory documentSalesRepDescriptorFactory;
	private final DocumentSalesRepDescriptorService documentSalesRepDescriptorService;
	private final IBPartnerBL bpartnerBL;

	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IBPartnerProductBL partnerProductBL = Services.get(IBPartnerProductBL.class);

	public C_Invoice(
			@NonNull final IBPartnerBL bpartnerBL,
			@NonNull final DocumentSalesRepDescriptorFactory documentSalesRepDescriptorFactory,
			@NonNull final DocumentSalesRepDescriptorService documentSalesRepDescriptorService)
	{
		this.bpartnerBL = bpartnerBL;
		this.documentSalesRepDescriptorService = documentSalesRepDescriptorService;
		this.documentSalesRepDescriptorFactory = documentSalesRepDescriptorFactory;

		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_Invoice.COLUMNNAME_C_BPartner_ID)
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_Invoice.COLUMNNAME_C_BPartner_ID)
	public void updateSalesPartnerFromBPartner(@NonNull final I_C_Invoice invoiceRecord)
	{
		final DocumentSalesRepDescriptor documentSalesRepDescriptor = documentSalesRepDescriptorFactory.forDocumentRecord(invoiceRecord);

		documentSalesRepDescriptorService.updateFromCustomer(documentSalesRepDescriptor);

		documentSalesRepDescriptor.syncToRecord();
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_SalesPartnerCode)
	public void updateSalesPartnerFromCode(@NonNull final I_C_Invoice invoiceRecord)
	{
		final DocumentSalesRepDescriptor documentSalesRepDescriptor = documentSalesRepDescriptorFactory.forDocumentRecord(invoiceRecord);

		documentSalesRepDescriptorService.updateFromSalesPartnerCode(documentSalesRepDescriptor);

		documentSalesRepDescriptor.syncToRecord();
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_C_BPartner_SalesRep_ID)
	public void updateSalesPartnerInInvoice(@NonNull final I_C_Invoice invoiceRecord)
	{

		final DocumentSalesRepDescriptor documentSalesRepDescriptor = documentSalesRepDescriptorFactory.forDocumentRecord(invoiceRecord);

		documentSalesRepDescriptorService.updateFromSalesRep(documentSalesRepDescriptor);

		documentSalesRepDescriptor.syncToRecord();
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void preventCompleteIfMissingSalesPartner(@NonNull final I_C_Invoice invoiceRecord)
	{
		final DocumentSalesRepDescriptor documentSalesRepDescriptor = documentSalesRepDescriptorFactory.forDocumentRecord(invoiceRecord);

		if (documentSalesRepDescriptor.validatesOK())
		{
			return; // nothing to do
		}

		throw documentSalesRepDescriptorService.createMissingSalesRepException();
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_Order.COLUMNNAME_C_BPartner_SalesRep_ID)
	public void updateSalesPartnerInCustomerMaterdata(@NonNull final I_C_Invoice invoiceRecord)
	{
		if (!invoiceRecord.isSOTrx())
		{
			return;
		}

		final BPartnerId customerBPartnerId = BPartnerId.ofRepoIdOrNull(invoiceRecord.getC_BPartner_ID());
		if (customerBPartnerId == null)
		{
			return; // no customer whose mater data we we could update
		}

		final BPartnerId salesBPartnerId = BPartnerId.ofRepoIdOrNull(invoiceRecord.getC_BPartner_SalesRep_ID());
		if (salesBPartnerId == null || customerBPartnerId.equals(salesBPartnerId))
		{
			return; // leave the master data untouched
		}

		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_BPartner billBPartnerRecord = bpartnerDAO.getById(customerBPartnerId);
		billBPartnerRecord.setC_BPartner_SalesRep_ID(salesBPartnerId.getRepoId());
		saveRecord(billBPartnerRecord);
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE}, ifColumnsChanged = { I_C_Invoice.COLUMNNAME_C_BPartner_SalesRep_ID, I_C_Invoice.COLUMNNAME_C_BPartner_ID })
	public void validateSalesRep(final I_C_Invoice invoice)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final BPartnerId salesRepId = BPartnerId.ofRepoIdOrNull(invoice.getC_BPartner_SalesRep_ID());
		bpartnerBL.validateSalesRep(bPartnerId, salesRepId);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Invoice.COLUMNNAME_C_BPartner_ID)
	public void checkExcludedProducts(@NonNull final I_C_Invoice invoiceRecord)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID());
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
		final SOTrx soTrx = SOTrx.ofBooleanNotNull(invoiceRecord.isSOTrx());

		invoiceDAO.retrieveLines(invoiceId)
				.stream()
				.map(I_C_InvoiceLine::getM_Product_ID)
				.map(ProductId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.forEach(productId -> partnerProductBL.assertNotExcludedFromTransaction(soTrx, productId, bPartnerId));
	}
}
