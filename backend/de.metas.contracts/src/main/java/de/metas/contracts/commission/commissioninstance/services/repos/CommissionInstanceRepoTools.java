package de.metas.contracts.commission.commissioninstance.services.repos;

import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocumentId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.mediatedorder.MediatedOrderLineDocId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate.SalesInvoiceCandidateDocumentId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline.SalesInvoiceLineDocumentId;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.order.OrderLineId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

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

public class CommissionInstanceRepoTools
{
	static CommissionTriggerDocumentId extractCommissionTriggerDocumentId(@NonNull final I_C_Commission_Instance instanceRecord)
	{
		final CommissionTriggerType triggerType = CommissionTriggerType.ofCode(instanceRecord.getCommissionTrigger_Type());
		switch (triggerType)
		{
			case InvoiceCandidate:
				return new SalesInvoiceCandidateDocumentId(InvoiceCandidateId.ofRepoId(instanceRecord.getC_Invoice_Candidate_ID()));
			case SalesInvoice:
				return new SalesInvoiceLineDocumentId(InvoiceAndLineId.ofRepoId(instanceRecord.getC_Invoice_ID(), instanceRecord.getC_InvoiceLine_ID()));
			case SalesCreditmemo:
				return new SalesInvoiceLineDocumentId(InvoiceAndLineId.ofRepoId(instanceRecord.getC_Invoice_ID(), instanceRecord.getC_InvoiceLine_ID()));
			case MediatedOrder:
				return new MediatedOrderLineDocId(OrderLineId.ofRepoId(instanceRecord.getC_OrderLine_ID()));
			default:
				throw new AdempiereException("Unexpected triggerType=" + triggerType);
		}
	}
}
