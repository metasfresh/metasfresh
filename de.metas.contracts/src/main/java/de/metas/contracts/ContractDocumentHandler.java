package de.metas.contracts;

import java.io.File;
import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;


class ContractDocumentHandler implements DocumentHandler
{
	private static I_C_Flatrate_Term extractContract(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_Flatrate_Term.class);
	}

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractContract(docFields).getContractDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractContract(docFields).getCreatedBy();
	}

	@Override
	public int getC_Currency_ID(final DocumentTableFields docFields)
	{
		return extractContract(docFields).getC_Currency_ID();
	}

	@Override
	public BigDecimal getApprovalAmt(final DocumentTableFields docFields)
	{
		return BigDecimal.ZERO;
	}

	@Override
	public File createPDF(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String prepareIt(final DocumentTableFields docFields)
	{
		return IDocument.STATUS_InProgress;
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		final I_C_Flatrate_Term contract = extractContract(docFields);
		contract.setDocAction(IDocument.ACTION_None);
		return IDocument.STATUS_Completed;
	}

	@Override
	public void approveIt(final DocumentTableFields docFields)
	{
	}

	@Override
	public void rejectIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void voidIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void closeIt(final DocumentTableFields docFields)
	{
		//TODO
	}

	@Override
	public void unCloseIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void reverseCorrectIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void reverseAccrualIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}
}
