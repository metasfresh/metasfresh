package de.metas.bpartner.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;

import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;


public class C_BPartner_Stats_ComputeBPartnerStats extends JavaProcess implements IProcessPrecondition
{
	private final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);


	@Override
	protected String doIt()
	{
		final I_C_BPartner_Stats stats = getRecord(I_C_BPartner_Stats.class);
		final I_C_BPartner statsPartner = partnerDAO.getById(BPartnerId.ofRepoId(stats.getC_BPartner_ID()));
		final BPartnerStats bpStats = bpartnerStatsDAO.getCreateBPartnerStats(statsPartner);
		bpartnerStatsDAO.updateBPartnerStatistics(bpStats);
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
