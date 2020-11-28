package de.metas.bpartner.process;

import org.compiere.model.I_C_BPartner_Stats;

import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerStatsBL;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;

public class C_BPartner_Stats_ComputeBPartnerStats extends JavaProcess implements IProcessPrecondition
{
	private final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);
	private final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);


	@Override
	protected String doIt()
	{
		final I_C_BPartner_Stats stats = getRecord(I_C_BPartner_Stats.class);
		final BPartnerStats bpStats = Services.get(IBPartnerStatsDAO.class).getCreateBPartnerStats(stats.getC_BPartner());
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
