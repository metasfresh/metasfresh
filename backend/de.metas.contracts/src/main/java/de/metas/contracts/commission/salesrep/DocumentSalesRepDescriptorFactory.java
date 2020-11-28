package de.metas.contracts.commission.salesrep;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Customer;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import lombok.NonNull;

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

@Repository
public class DocumentSalesRepDescriptorFactory
{
	public DocumentSalesRepDescriptor forDocumentRecord(@NonNull final I_C_Invoice invoiceRecord)
	{
		final Customer customer = Customer.ofOrNull(BPartnerId.ofRepoIdOrNull(invoiceRecord.getC_BPartner_ID()));
		final Beneficiary salesRep = Beneficiary.ofOrNull(BPartnerId.ofRepoIdOrNull(invoiceRecord.getC_BPartner_SalesRep_ID()));

		return new InvoiceRecordSalesRepDescriptor(
				invoiceRecord,
				OrgId.ofRepoId(invoiceRecord.getAD_Org_ID()),
				SOTrx.ofBoolean(invoiceRecord.isSOTrx()),
				customer,
				salesRep,
				invoiceRecord.getSalesPartnerCode(),
				invoiceRecord.isSalesPartnerRequired());
	}

	public DocumentSalesRepDescriptor forDocumentRecord(@NonNull final I_C_Order orderRecord)
	{
		final Customer customer = extractEffectiveBillPartnerId(orderRecord);
		final Beneficiary salesRep = Beneficiary.ofOrNull(BPartnerId.ofRepoIdOrNull(orderRecord.getC_BPartner_SalesRep_ID()));

		return new OrderRecordSalesRepDescriptor(
				orderRecord,
				OrgId.ofRepoId(orderRecord.getAD_Org_ID()),
				SOTrx.ofBoolean(orderRecord.isSOTrx()),
				customer,
				salesRep,
				orderRecord.getSalesPartnerCode(),
				orderRecord.isSalesPartnerRequired());
	}

	private Customer extractEffectiveBillPartnerId(@NonNull final I_C_Order orderRecord)
	{
		return Customer.ofOrNull(BPartnerId.ofRepoIdOrNull(firstGreaterThanZero(
				orderRecord.getBill_BPartner_ID(),
				orderRecord.getC_BPartner_ID())));
	}
}
