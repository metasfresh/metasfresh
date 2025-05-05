package de.metas.banking.payment;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_PaySelection;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PaySelectionDocumentHandler implements DocumentHandler
{
	@Override
	public String getSummary(DocumentTableFields docFields)
	{
		return extractPaySelection(docFields).getName();
	}

	@Override
	public String getDocumentInfo(DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public InstantAndOrgId getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		final I_C_PaySelection paySelection = extractPaySelection(docFields);
		return InstantAndOrgId.ofTimestamp(paySelection.getPayDate(), OrgId.ofRepoId(paySelection.getAD_Org_ID()));
	}

	@Override
	public int getDoc_User_ID(DocumentTableFields docFields)
	{
		return extractPaySelection(docFields).getCreatedBy();
	}

	@Override
	public DocStatus completeIt(DocumentTableFields docFields)
	{
		final I_C_PaySelection paySelection = extractPaySelection(docFields);

		Services.get(IPaySelectionBL.class).completePaySelection(paySelection);

		return DocStatus.Completed;
	}

	@Override
	public void reactivateIt(DocumentTableFields docFields)
	{
		final I_C_PaySelection paySelection = extractPaySelection(docFields);

		Services.get(IPaySelectionBL.class).reactivatePaySelection(paySelection);
	}

	private static I_C_PaySelection extractPaySelection(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_PaySelection.class);
	}
}
