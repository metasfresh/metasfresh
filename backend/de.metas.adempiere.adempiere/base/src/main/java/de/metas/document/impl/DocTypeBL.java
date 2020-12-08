package de.metas.document.impl;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;

public class DocTypeBL implements IDocTypeBL
{
	private final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);

	@Override
	public ITranslatableString getNameById(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType docType = docTypesRepo.getById(docTypeId);
		return InterfaceWrapperHelper.getModelTranslationMap(docType)
				.getColumnTrl(I_C_DocType.COLUMNNAME_Name, docType.getName());
	}

	@Override
	public boolean isSalesQuotation(@NonNull final DocTypeId docTypeId)
	{
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
		final I_C_DocType docType = docTypesRepo.getById(docTypeId);
		return isPrepay(docType);
	}

	@Override
	public boolean isPrepay(final I_C_DocType dt)
	{
		return X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(dt.getDocSubType())
				&& X_C_DocType.DOCBASETYPE_SalesOrder.equals(dt.getDocBaseType());
	}

	@Override
	public boolean hasRequestType(@NonNull final DocTypeId docTypeId)
	{
		return docTypesRepo.getById(docTypeId).getR_RequestType_ID() > 0;
	}
}
