package de.metas.contracts.commission.salesrep;

import javax.annotation.Nullable;

import org.compiere.model.I_C_Invoice;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Customer;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import lombok.Getter;
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

public class InvoiceRecordSalesRepDescriptor extends DocumentSalesRepDescriptor
{
	@Getter
	private I_C_Invoice invoiceRecord;

	public InvoiceRecordSalesRepDescriptor(
			@NonNull final I_C_Invoice invoiceRecord,
			@NonNull final OrgId orgId,
			@NonNull final SOTrx soTrx,
			@Nullable final Customer customer,
			@Nullable final Beneficiary salesRep,
			@Nullable final String salesPartnerCode,
			final boolean salesRepRequired)
	{
		super(orgId, soTrx, customer, salesRep, salesPartnerCode, salesRepRequired);
		this.invoiceRecord = invoiceRecord;
	}

	public void syncToRecord()
	{
		invoiceRecord.setIsSalesPartnerRequired(isSalesRepRequired());
		invoiceRecord.setSalesPartnerCode(getSalesPartnerCode());
		invoiceRecord.setC_BPartner_SalesRep_ID(Beneficiary.toRepoId(getSalesRep()));
	}
}
