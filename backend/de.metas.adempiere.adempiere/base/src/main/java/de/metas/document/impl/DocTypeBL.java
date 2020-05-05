package de.metas.document.impl;

import org.adempiere.model.InterfaceWrapperHelper;

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

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import lombok.NonNull;

public class DocTypeBL implements IDocTypeBL
{
	@Override
	public ITranslatableString getNameById(@NonNull final DocTypeId docTypeId)
	{
		final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);
		final I_C_DocType docType = docTypesRepo.getById(docTypeId);
		return InterfaceWrapperHelper.getModelTranslationMap(docType)
				.getColumnTrl(I_C_DocType.COLUMNNAME_Name, docType.getName());
	}

	@Override
	public boolean isSalesQuotation(@NonNull final DocTypeId docTypeId)
	{
		final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);
		final I_C_DocType dt = docTypesRepo.getById(docTypeId);
		return isSalesQuotation(dt);
	}

	@Override
	public boolean isSalesQuotation(final I_C_DocType dt)
	{
		return X_C_DocType.DOCBASETYPE_SalesOrder.equals(dt.getDocBaseType())
				&& X_C_DocType.DOCSUBTYPE_Quotation.equals(dt.getDocSubType());
	}

	@Override
	public boolean isSalesProposal(@NonNull final DocTypeId docTypeId)
	{
		final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);
		final I_C_DocType dt = docTypesRepo.getById(docTypeId);
		return isSalesProposal(dt);
	}

	@Override
	public boolean isSalesProposal(final I_C_DocType dt)
	{
		return X_C_DocType.DOCBASETYPE_SalesOrder.equals(dt.getDocBaseType())
				&& X_C_DocType.DOCSUBTYPE_Proposal.equals(dt.getDocSubType());
	}

	@Override
	public boolean isSalesProposalOrQuotation(@NonNull final DocTypeId docTypeId)
	{
		final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);
		final I_C_DocType dt = docTypesRepo.getById(docTypeId);
		return isSalesProposalOrQuotation(dt);
	}

	@Override
	public boolean isSalesProposalOrQuotation(final I_C_DocType dt)
	{
		return isSalesProposal(dt) || isSalesQuotation(dt);
	}

	@Override
	public boolean isSOTrx(@NonNull final String docBaseType)
	{
		return X_C_DocType.DOCBASETYPE_SalesOrder.equals(docBaseType)
				|| X_C_DocType.DOCBASETYPE_MaterialDelivery.equals(docBaseType)
				|| docBaseType.startsWith("AR"); // Account Receivables (Invoice, Payment Receipt)
	}

	@Override
	public boolean isPrepay(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType docType = Services.get(IDocTypeDAO.class).getById(docTypeId);
		return isPrepay(docType);
	}

	@Override
	public boolean isPrepay(final I_C_DocType dt)
	{
		return X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(dt.getDocSubType())
				&& X_C_DocType.DOCBASETYPE_SalesOrder.equals(dt.getDocBaseType());
	}
}
