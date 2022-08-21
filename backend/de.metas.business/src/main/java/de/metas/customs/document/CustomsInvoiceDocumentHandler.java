package de.metas.customs.document;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Customs_Invoice;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class CustomsInvoiceDocumentHandler  implements DocumentHandler
{

	@Override
	public String getSummary(DocumentTableFields docFields)
	{
		return extractCustomsInvoice(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public InstantAndOrgId getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		final I_C_Customs_Invoice customsInvoice = extractCustomsInvoice(docFields);
		return InstantAndOrgId.ofTimestamp(customsInvoice.getDateInvoiced(), OrgId.ofRepoId(customsInvoice.getAD_Org_ID()));
	}

	@Override
	public int getDoc_User_ID(DocumentTableFields docFields)
	{
		return extractCustomsInvoice(docFields).getCreatedBy();
	}

	@Override
	public String completeIt(DocumentTableFields docFields)
	{
		final I_C_Customs_Invoice shipmentDeclaration = extractCustomsInvoice(docFields);
		shipmentDeclaration.setProcessed(true);
		shipmentDeclaration.setDocAction(IDocument.ACTION_ReActivate);

		return IDocument.STATUS_Completed;
	}

	@Override
	public void reactivateIt(DocumentTableFields docFields)
	{
		final I_C_Customs_Invoice customsInvoice = extractCustomsInvoice(docFields);

		customsInvoice.setProcessed(false);
		customsInvoice.setDocAction(IDocument.ACTION_Complete);

	}

	private static I_C_Customs_Invoice extractCustomsInvoice(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_Customs_Invoice.class);
	}
}


