package de.metas.banking.document;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.time.LocalDate;

import org.compiere.util.TimeUtil;

import de.metas.banking.model.I_C_BankStatement;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
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

public class BankStatementDocumentHandler implements DocumentHandler
{

	@Override
	public String getSummary(DocumentTableFields docFields)
	{
		return extractBankStatement(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public LocalDate getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		final I_C_BankStatement bankStatement = extractBankStatement(docFields);
		return TimeUtil.asLocalDate(bankStatement.getStatementDate());
	}

	@Override
	public int getDoc_User_ID(DocumentTableFields docFields)
	{
		return extractBankStatement(docFields).getCreatedBy();
	}

	@Override
	public String completeIt(DocumentTableFields docFields)
	{
		final I_C_BankStatement bankStatement = extractBankStatement(docFields);
		bankStatement.setProcessed(true);
		bankStatement.setDocAction(IDocument.ACTION_ReActivate);

		return IDocument.STATUS_Completed;
	}

	@Override
	public void reactivateIt(DocumentTableFields docFields)
	{
		final I_C_BankStatement bankStatement = extractBankStatement(docFields);

		bankStatement.setProcessed(false);
		bankStatement.setDocAction(IDocument.ACTION_Complete);

	}

	private static I_C_BankStatement extractBankStatement(final DocumentTableFields docFields)
	{
		return create(docFields, I_C_BankStatement.class);
	}
}


