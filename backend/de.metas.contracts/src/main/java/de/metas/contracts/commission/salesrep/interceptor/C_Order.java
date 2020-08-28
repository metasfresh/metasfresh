package de.metas.contracts.commission.salesrep.interceptor;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.commission.salesrep.DocumentSalesRepDescriptor;
import de.metas.contracts.commission.salesrep.DocumentSalesRepDescriptorFactory;
import de.metas.contracts.commission.salesrep.DocumentSalesRepDescriptorService;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Callout(I_C_Order.class)
@Interceptor(I_C_Order.class)
public class C_Order
{
	private DocumentSalesRepDescriptorFactory documentSalesRepDescriptorFactory;
	private DocumentSalesRepDescriptorService documentSalesRepDescriptorService;

	public C_Order(
			@NonNull final DocumentSalesRepDescriptorFactory documentSalesRepDescriptorFactory,
			@NonNull final DocumentSalesRepDescriptorService documentSalesRepDescriptorService)
	{
		this.documentSalesRepDescriptorService = documentSalesRepDescriptorService;
		this.documentSalesRepDescriptorFactory = documentSalesRepDescriptorFactory;

		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_C_BPartner_ID, I_C_Order.COLUMNNAME_Bill_BPartner_ID })
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Order.COLUMNNAME_C_BPartner_ID, I_C_Order.COLUMNNAME_Bill_BPartner_ID })
	public void updateSalesPartnerFromCustomer(@NonNull final I_C_Order orderRecord)
	{
		final DocumentSalesRepDescriptor documentSalesRepDescriptor = documentSalesRepDescriptorFactory.forDocumentRecord(orderRecord);

		documentSalesRepDescriptorService.updateFromCustomer(documentSalesRepDescriptor);

		documentSalesRepDescriptor.syncToRecord();
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_SalesPartnerCode)
	public void updateSalesPartnerFromCode(@NonNull final I_C_Order orderRecord)
	{
		final DocumentSalesRepDescriptor documentSalesRepDescriptor = documentSalesRepDescriptorFactory.forDocumentRecord(orderRecord);

		documentSalesRepDescriptorService.updateFromSalesPartnerCode(documentSalesRepDescriptor);

		documentSalesRepDescriptor.syncToRecord();
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_C_BPartner_SalesRep_ID)
	public void updateSalesPartnerInOrder(@NonNull final I_C_Order orderRecord)
	{
		final DocumentSalesRepDescriptor documentSalesRepDescriptor = documentSalesRepDescriptorFactory.forDocumentRecord(orderRecord);

		documentSalesRepDescriptorService.updateFromSalesRep(documentSalesRepDescriptor);

		documentSalesRepDescriptor.syncToRecord();
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_Order.COLUMNNAME_C_BPartner_SalesRep_ID)
	public void updateSalesPartnerInCustomerMaterdata(@NonNull final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return;
		}

		final BPartnerId effectiveBillPartnerId = extractEffectiveBillPartnerId(orderRecord);
		if (effectiveBillPartnerId == null)
		{
			return; // no customer whose mater data we we could update
		}

		final BPartnerId salesBPartnerId = BPartnerId.ofRepoIdOrNull(orderRecord.getC_BPartner_SalesRep_ID());
		if (salesBPartnerId == null)
		{
			return; // leave the master data untouched
		}

		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final I_C_BPartner billBPartnerRecord = bpartnerDAO.getById(effectiveBillPartnerId);
		billBPartnerRecord.setC_BPartner_SalesRep_ID(orderRecord.getC_BPartner_SalesRep_ID());
		saveRecord(billBPartnerRecord);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void preventCompleteIfMissingSalesPartner(@NonNull final I_C_Order orderRecord)
	{
		final DocumentSalesRepDescriptor documentSalesRepDescriptor = documentSalesRepDescriptorFactory.forDocumentRecord(orderRecord);

		if (documentSalesRepDescriptor.validatesOK())
		{
			return; // nothing to do
		}

		throw documentSalesRepDescriptorService.createMissingSalesRepException();
	}

	private BPartnerId extractEffectiveBillPartnerId(@NonNull final I_C_Order orderRecord)
	{
		return BPartnerId.ofRepoIdOrNull(firstGreaterThanZero(
				orderRecord.getBill_BPartner_ID(),
				orderRecord.getC_BPartner_ID()));
	}
}
