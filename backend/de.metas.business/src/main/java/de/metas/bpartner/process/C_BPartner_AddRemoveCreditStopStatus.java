package de.metas.bpartner.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.bpartner.service.impl.BPartnerStatsService;
import de.metas.bpartner.service.impl.CalculateCreditStatusRequest;
import de.metas.bpartner.service.impl.CreditStatus;
import de.metas.common.util.time.SystemTime;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;

/**
 * This process set credit status to Credit Stop or removes it, in function by parameter <code>IsSetCreditStop</code> <br>
 * The status <code>CreditStop</code> is removed only if a new credit limit was added and that new credit limit allows it
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_BPartner_AddRemoveCreditStopStatus extends JavaProcess implements IProcessPrecondition
{
	private  final BPartnerStatsService bpartnerStatsService = SpringContextHolder.instance.getBean(BPartnerStatsService.class);
	private  final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	@Param(parameterName = "setCreditStatus", mandatory = true)
	private SetCreditStatusEnum setCreditStatus;

	@Override
	protected String doIt()
	{
		final I_C_BPartner bPartner = bpartnerDAO.getById(BPartnerId.ofRepoId(getRecord_ID()));

		final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(bPartner);
		final CreditStatus creditStatus;
		final CreditStatus deliveryCreditStatus;

		if (SetCreditStatusEnum.CreditOK.equals(setCreditStatus)) {
			creditStatus = CreditStatus.CreditOK;
			deliveryCreditStatus = CreditStatus.CreditOK;
		}
		else if (SetCreditStatusEnum.CreditStop.equals(setCreditStatus))
		{
			creditStatus = CreditStatus.CreditStop;
			deliveryCreditStatus = CreditStatus.CreditStop;
		}
		else if (SetCreditStatusEnum.Calculate.equals(setCreditStatus))
		{
			final CalculateCreditStatusRequest request = CalculateCreditStatusRequest.builder()
					.stat(stats)
					.forceCheckCreditStatus(true)
					.date(SystemTime.asDayTimestamp())
					.build();
			creditStatus = bpartnerStatsService.calculateProjectedSOCreditStatus(request);
			deliveryCreditStatus = bpartnerStatsService.calculateProjectedDeliveryCreditStatus(request);
		}
		else
		{
			throw new AdempiereException("Invalid setCreditStatus value: " + setCreditStatus);
		}

		bpartnerStatsDAO.setSOCreditStatus(stats, creditStatus);
		bpartnerStatsDAO.setDeliveryCreditStatus(stats, deliveryCreditStatus);

		return "@Success@";
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_BPartner.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not running on C_BPartner table");
		}

		return ProcessPreconditionsResolution.accept();
	}
}
