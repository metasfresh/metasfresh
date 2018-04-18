package org.adempiere.bpartner.process;

import org.adempiere.bpartner.service.BPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsBL.CalculateSOCreditStatusRequest;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.X_C_BPartner_Stats;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;

/**
 * This process set credit status to Credit Stop or removes it, in function by parameter <code>IsSetCreditStop</code> <br>
 * The status <code>CreditStop</code> is removed only if a new credit limit was added and that new credit limit allows it
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_BPartner_Stats_AddRemoveCreditStopStatus extends JavaProcess implements IProcessPrecondition
{
	private  final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);
	private  final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

	@Param(parameterName = "IsSetCreditStop", mandatory = true)
	private boolean isSetCreditStop;

	@Override
	protected String doIt()
	{
		final I_C_BPartner_Stats bpStats = getRecord(I_C_BPartner_Stats.class);
		final String creditStatus;
		if (isSetCreditStop)
		{
			creditStatus = X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop;
		}
		else
		{
			final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(bpStats.getC_BPartner());
			final CalculateSOCreditStatusRequest request = CalculateSOCreditStatusRequest.builder()
					.stat(stats)
					.forceCheckCreditStatus(true)
					.date(SystemTime.asDayTimestamp())
					.build();
			creditStatus = bpartnerStatsBL.calculateProjectedSOCreditStatus(request);
		}

		bpStats.setSOCreditStatus(creditStatus);
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
