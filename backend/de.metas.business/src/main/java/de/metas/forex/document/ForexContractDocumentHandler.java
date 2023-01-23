package de.metas.forex.document;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractService;
import de.metas.organization.InstantAndOrgId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.compiere.model.X_GL_Journal;

import java.math.BigDecimal;

public class ForexContractDocumentHandler implements DocumentHandler
{
	private final ForexContractService forexContractService;

	public ForexContractDocumentHandler(@NonNull final ForexContractService forexContractService) {this.forexContractService = forexContractService;}

	private static I_C_ForeignExchangeContract extractRecord(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_ForeignExchangeContract.class);
	}

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getDocumentNo();
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getCreatedBy();
	}

	@Override
	public InstantAndOrgId getDocumentDate(final DocumentTableFields docFields)
	{
		final I_C_ForeignExchangeContract record = extractRecord(docFields);
		return InstantAndOrgId.ofTimestamp(record.getCreated(), record.getAD_Org_ID());
	}

	@Override
	public int getC_Currency_ID(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getC_Currency_ID();
	}

	@Override
	public BigDecimal getApprovalAmt(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getFEC_Amount();
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		return DocStatus.Completed.getCode();
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		final I_C_ForeignExchangeContract record = extractRecord(docFields);

		final ForexContractId contractId = ForexContractId.ofRepoId(record.getC_ForeignExchangeContract_ID());
		if (forexContractService.hasAllocations(contractId))
		{
			throw new AdempiereException("Re-Activating a FEC which has allocations is not allowed");
		}

		record.setProcessed(false);
		record.setDocAction(X_GL_Journal.DOCACTION_Complete);
	}
}
