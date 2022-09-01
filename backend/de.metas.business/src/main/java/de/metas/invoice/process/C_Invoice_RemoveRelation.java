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

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Invoice_Relation;

/**
 * Process used to remove associations between C_Invoice records.
 * Currently, the only relation type is X_C_Invoice_Relation#C_INVOICE_RELATION_TYPE_PurchaseToSales between a Purchase invoice and a Sales invoice.
 */
public class C_Invoice_RemoveRelation extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Param(parameterName = I_C_Invoice_Relation.COLUMNNAME_C_Invoice_From_ID, mandatory = true)
	private int poInvoiceRepoId;
	@Param(parameterName = I_C_Invoice_Relation.COLUMNNAME_C_Invoice_Relation_Type, mandatory = true)
	private String relationToRemove;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();

		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		final boolean invoiceRelationsFound = queryBL.createQueryBuilder(I_C_Invoice_Relation.class)
				.addInSubQueryFilter(I_C_Invoice_Relation.COLUMN_C_Invoice_To_ID, I_C_Invoice.COLUMN_C_Invoice_ID,
						queryBL.createQueryBuilder(I_C_Invoice.class)
								.filter(context.getQueryFilter(I_C_Invoice.class))
								.create())
				.create().anyMatch();
		if (!invoiceRelationsFound)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No invoice relations found.");
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		removeRelations();
		return MSG_OK;
	}

	private void removeRelations()
	{
		queryBL.createQueryBuilder(I_C_Invoice_Relation.class)
				.addEqualsFilter(I_C_Invoice_Relation.COLUMNNAME_C_Invoice_Relation_Type, relationToRemove)
				.addEqualsFilter(I_C_Invoice_Relation.COLUMNNAME_C_Invoice_From_ID, poInvoiceRepoId)
				.addEqualsFilter(I_C_Invoice_Relation.COLUMNNAME_C_Invoice_To_ID, getRecord_ID())
				.create()
				.delete();
	}
}
