package de.metas.forex.document;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.forex.ForexContract;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractRepository;
import de.metas.forex.ForexContractService;
import de.metas.organization.InstantAndOrgId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.compiere.model.X_GL_Journal;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class ForexContractDocumentHandler implements DocumentHandler
{
	private final ForexContractService forexContractService;

	public ForexContractDocumentHandler(@NonNull final ForexContractService forexContractService) {this.forexContractService = forexContractService;}

	private static I_C_ForeignExchangeContract extractRecord(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_ForeignExchangeContract.class);
	}

	private static ForexContract extractContract(final DocumentTableFields docFields)
	{
		return ForexContractRepository.fromRecord(extractRecord(docFields));
	}

	private void updateContract(final DocumentTableFields docFields, final Consumer<ForexContract> updater)
	{
		forexContractService.updateWhileSaving(extractRecord(docFields), updater);
	}

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractContract(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return extractContract(docFields).getDocumentNo();
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractContract(docFields).getCreatedBy().getRepoId();
	}

	@Override
	public InstantAndOrgId getDocumentDate(final DocumentTableFields docFields)
	{
		final ForexContract contract = extractContract(docFields);
		return InstantAndOrgId.ofInstant(contract.getCreated(), contract.getOrgId());
	}

	@Override
	public int getC_Currency_ID(final DocumentTableFields docFields)
	{
		return extractContract(docFields).getCurrencyId().getRepoId();
	}

	@Override
	public BigDecimal getApprovalAmt(final DocumentTableFields docFields)
	{
		return extractContract(docFields).getAmount().toBigDecimal();
	}

	@Override
	public DocStatus completeIt(final DocumentTableFields docFields)
	{
		updateContract(docFields, contract -> {
			if (contract.getAmount().signum() <= 0)
			{
				throw new AdempiereException("Amount shall be positive");
			}
		});

		return DocStatus.Completed;
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		final I_C_ForeignExchangeContract record = extractRecord(docFields);
		final ForexContractId forexContractId = ForexContractId.ofRepoId(record.getC_ForeignExchangeContract_ID());

		if (forexContractService.hasAllocations(forexContractId))
		{
			throw new AdempiereException("Re-Activating a FEC which has allocations is not allowed");
		}

		record.setProcessed(false);
		record.setDocAction(X_GL_Journal.DOCACTION_Complete);
	}
}
