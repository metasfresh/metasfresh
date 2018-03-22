package org.adempiere.bpartner.process;

import org.adempiere.bpartner.service.BPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsBL.CalculateSOCreditStatusRequest;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner_Stats;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

public class BPartnerCreditLimit_RemoveHoldStatus extends JavaProcess implements IProcessPrecondition
{
	final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);
	final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

	@Override
	protected String doIt()
	{
		final I_C_BPartner_Stats bpStats = getRecord(I_C_BPartner_Stats.class);
		final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(bpStats.getC_BPartner());
		final CalculateSOCreditStatusRequest request = CalculateSOCreditStatusRequest.builder()
				.stat(stats)
				.checkiAdditionalAmt(false)
				.date(SystemTime.asDayTimestamp())
				.build();
		final String calculatedCreditStatus = bpartnerStatsBL.calculateSOCreditStatus(request);
		bpStats.setSOCreditStatus(calculatedCreditStatus);
		InterfaceWrapperHelper.save(bpStats);
		return "@Success@";
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_BPartner_Stats.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not running on C_BPartner_Stats table");
		}

		return ProcessPreconditionsResolution.accept();
	}
}
