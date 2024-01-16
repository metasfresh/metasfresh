/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.invoice.process;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Invoice_Relation;

import javax.annotation.Nullable;

/**
 * Process used to create associations between C_Invoice records.
 * Currently, the only relation type is X_C_Invoice_Relation#C_INVOICE_RELATION_TYPE_PurchaseToSales between a Purchase invoice and a Sales invoice.
 */
public class C_Invoice_CreateRelation extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@Param(parameterName = I_C_Invoice_Relation.COLUMNNAME_C_Invoice_From_ID, mandatory = true)
	private int fromInvoiceRepoId;
	@Param(parameterName = I_C_Invoice_Relation.COLUMNNAME_C_Invoice_Relation_Type, mandatory = true)
	private String relationToCreate;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		final InvoiceId toInvoiceId = InvoiceId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Invoice invoice = invoiceBL.getById(toInvoiceId);
		if (invoiceBL.isVendorInvoice(invoiceBL.getC_DocType(invoice).getDocBaseType()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Only Sales Invoices accepted.");
		}
		if (recordAlreadyExists(null, toInvoiceId, null))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("A relation already exists.");
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final InvoiceId fromInvoiceId = InvoiceId.ofRepoId(fromInvoiceRepoId);
		final InvoiceId toInvoiceID = InvoiceId.ofRepoId(getRecord_ID());

		final boolean alreadyExists = recordAlreadyExists(fromInvoiceId, toInvoiceID, relationToCreate);
		if (alreadyExists)
		{
			return MSG_OK;
		}
		final I_C_Invoice fromInvoice = invoiceBL.getById(fromInvoiceId);
		final I_C_Invoice toInvoice = invoiceBL.getById(toInvoiceID);
		if (fromInvoice.getAD_Org_ID() != toInvoice.getAD_Org_ID())
		{
			return MSG_Error + "DifferentOrgs";
		}
		InterfaceWrapperHelper.save(createRelation(toInvoice));
		return MSG_OK;
	}

	private boolean recordAlreadyExists(@Nullable final InvoiceId fromInvoiceId, final InvoiceId toInvoiceID, @Nullable final String relationType)
	{
		final IQueryBuilder<I_C_Invoice_Relation> queryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Relation.class)
				.addEqualsFilter(I_C_Invoice_Relation.COLUMNNAME_C_Invoice_To_ID, toInvoiceID);
		if (!Check.isBlank(relationType))
		{
			queryBuilder.addEqualsFilter(I_C_Invoice_Relation.COLUMN_C_Invoice_Relation_Type, relationType);
		}
		if (fromInvoiceId != null)
		{
			queryBuilder.addEqualsFilter(I_C_Invoice_Relation.COLUMNNAME_C_Invoice_From_ID, fromInvoiceId);
		}
		return queryBuilder.create()
				.anyMatch();
	}

	private I_C_Invoice_Relation createRelation(@NonNull final I_C_Invoice toInvoice)
	{
		final I_C_Invoice_Relation invoiceRelation = InterfaceWrapperHelper.newInstance(I_C_Invoice_Relation.class, toInvoice);
		invoiceRelation.setC_Invoice_Relation_Type(relationToCreate);
		invoiceRelation.setC_Invoice_From_ID(fromInvoiceRepoId);
		invoiceRelation.setC_Invoice_To_ID(toInvoice.getC_Invoice_ID());
		invoiceRelation.setAD_Org_ID(toInvoice.getAD_Org_ID());
		return invoiceRelation;
	}
}
