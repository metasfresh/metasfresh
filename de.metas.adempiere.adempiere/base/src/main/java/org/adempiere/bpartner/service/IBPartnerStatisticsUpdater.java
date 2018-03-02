package org.adempiere.bpartner.service;

import java.util.Set;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_BPartner_Stats;

/**
 * Service used to update {@link I_C_BPartner_Stats#COLUMN_TotalOpenBalance}, {@link I_C_BPartner_Stats#COLUMN_SO_CreditUsed}, {@link I_C_BPartner_Stats#COLUMN_ActualLifeTimeValue} and {@link I_C_BPartner_Stats#COLUMN_SOCreditStatus}
 *
 * @author tsa
 *
 */
public interface IBPartnerStatisticsUpdater extends ISingletonService
{
	ModelDynAttributeAccessor<I_C_AllocationHdr, Boolean> DYNATTR_DisableUpdateTotalOpenBalances = new ModelDynAttributeAccessor<>("org.adempiere.bpartner.service.IBPartnerTotalOpenBalanceUpdater.DisableUpdateTotalOpenBalances", Boolean.class);

	/**
	 * This method will update all the bpartner statistics based on the legacy sql.
	 *
	 * @param ctx
	 * @param bpartnerIDs
	 * @param trxName
	 */
	void updateBPartnerStatistics(final Set<Integer> bpartnerIDs);

}
