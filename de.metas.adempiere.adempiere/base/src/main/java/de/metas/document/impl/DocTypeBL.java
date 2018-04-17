package de.metas.document.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;

import de.metas.document.IDocTypeBL;
import lombok.NonNull;

public class DocTypeBL implements IDocTypeBL
{

	@Override
	public boolean isQuotation(final I_C_DocType dt)
	{
		return X_C_DocType.DOCSUBTYPE_Quotation.equals(dt.getDocSubType())
				&& X_C_DocType.DOCBASETYPE_SalesOrder.equals(dt.getDocBaseType());
	}

	@Override
	public boolean isProposal(final I_C_DocType dt)
	{
		return X_C_DocType.DOCSUBTYPE_Proposal.equals(dt.getDocSubType())
				&& X_C_DocType.DOCBASETYPE_SalesOrder.equals(dt.getDocBaseType());
	}

	@Override
	public boolean isOffer(final I_C_DocType dt)
	{
		return (X_C_DocType.DOCSUBTYPE_Proposal.equals(dt.getDocSubType())
				|| X_C_DocType.DOCSUBTYPE_Quotation.equals(dt.getDocSubType()))
				&& X_C_DocType.DOCBASETYPE_SalesOrder.equals(dt.getDocBaseType());
	}

	@Override
	public boolean isSOTrx(@NonNull final String docBaseType)
	{
		return X_C_DocType.DOCBASETYPE_SalesOrder.equals(docBaseType)
				|| X_C_DocType.DOCBASETYPE_MaterialDelivery.equals(docBaseType)
				|| docBaseType.startsWith("AR"); // Account Receivables (Invoice, Payment Receipt)
	}
}
