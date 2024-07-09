package de.metas.banking.callout;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.banking.service.IBankStatementBL;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_DocType;

@Callout(I_C_BankStatement.class)
public class C_BankStatement
{
	private final IBankStatementBL bankStatementBL;

	public C_BankStatement(
			@NonNull final IBankStatementBL bankStatementBL)
	{
		this.bankStatementBL = bankStatementBL;
	}

	@CalloutMethod(columnNames = {
			I_C_BankStatement.COLUMNNAME_BeginningBalance,
			I_C_BankStatement.COLUMNNAME_StatementDifference })
	public void updateEndingBalance(final I_C_BankStatement bankStatement, final ICalloutField unused)
	{
		bankStatementBL.updateEndingBalance(bankStatement);
	}

	@CalloutMethod(columnNames = I_C_BankStatement.COLUMNNAME_C_DocType_ID)
	public void onDocTypeChanged(final I_C_BankStatement bankStatement)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(bankStatement.getC_DocType_ID());
		if (docTypeId == null)
		{
			return;
		}

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		final I_C_DocType docType = docTypeDAO.getRecordById(docTypeId);
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(docType)
				.setOldDocumentNo(bankStatement.getDocumentNo())
				.setDocumentModel(bankStatement)
				.buildOrNull();

		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			bankStatement.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}
}
