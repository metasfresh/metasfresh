package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline;

import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocumentId;
<<<<<<< HEAD
import de.metas.invoice.InvoiceLineId;
=======
import de.metas.invoice.InvoiceAndLineId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

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

@Value
public class SalesInvoiceLineDocumentId implements CommissionTriggerDocumentId
{
	public static SalesInvoiceLineDocumentId cast(final CommissionTriggerDocumentId commissionTriggerDocInfo)
	{
		return (SalesInvoiceLineDocumentId)commissionTriggerDocInfo;
	}

<<<<<<< HEAD
	InvoiceLineId invoiceLineId;
=======
	InvoiceAndLineId invoiceAndLineId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@Override
	public RepoIdAware getRepoIdAware()
	{
<<<<<<< HEAD
		return invoiceLineId;
=======
		return invoiceAndLineId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
