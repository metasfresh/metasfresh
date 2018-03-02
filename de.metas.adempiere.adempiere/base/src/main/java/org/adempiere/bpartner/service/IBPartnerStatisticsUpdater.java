package org.adempiere.bpartner.service;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.util.Check;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_BPartner_Stats;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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
	void updateBPartnerStatistics(@NonNull final BPartnerStatisticsUpdateRequest request);

	@Value
	public static class BPartnerStatisticsUpdateRequest
	{

		final ImmutableSet<Integer> bpartnerIds;
		final boolean updateFromBPGroup;

		@Builder
		private BPartnerStatisticsUpdateRequest(
				@NonNull @Singular final ImmutableSet<Integer> bpartnerIds,
				final boolean updateFromBPGroup)
		{
			this.bpartnerIds = bpartnerIds.stream()
					.filter(bpartnerId -> bpartnerId > 0)
					.collect(ImmutableSet.toImmutableSet());
			Check.assumeNotEmpty(this.bpartnerIds, "bpartnerIds is not empty");

			this.updateFromBPGroup = updateFromBPGroup;
		}

	}

}
