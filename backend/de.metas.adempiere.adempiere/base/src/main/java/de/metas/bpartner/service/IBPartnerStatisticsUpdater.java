package de.metas.bpartner.service;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_BPartner_Stats;

/**
 * Service used to update {@link I_C_BPartner_Stats#COLUMNNAME_OpenItems}, {@link I_C_BPartner_Stats#COLUMN_SO_CreditUsed}, {@link I_C_BPartner_Stats#COLUMN_ActualLifeTimeValue} and {@link I_C_BPartner_Stats#COLUMN_SOCreditStatus}
 *
 * @author tsa
 *
 */
public interface IBPartnerStatisticsUpdater extends ISingletonService
{
	ModelDynAttributeAccessor<I_C_AllocationHdr, Boolean> DYNATTR_DisableUpdateTotalOpenBalances = new ModelDynAttributeAccessor<>("org.adempiere.bpartner.service.IBPartnerTotalOpenBalanceUpdater.DisableUpdateTotalOpenBalances", Boolean.class);

	void updateBPartnerStatistics(@NonNull final BPartnerStatisticsUpdateRequest request);

	@Value
	public static class BPartnerStatisticsUpdateRequest
	{
		final ImmutableSet<Integer> bpartnerIds;

		/**
		 * This flag decides if BPartnerStatsService#resetCreditStatusFromBPGroup(I_C_BPartner) will also be invoked
		 */
		final boolean alsoResetCreditStatusFromBPGroup;

		@Builder
		private BPartnerStatisticsUpdateRequest(
				@NonNull @Singular final ImmutableSet<Integer> bpartnerIds,
				final boolean alsoResetCreditStatusFromBPGroup)
		{
			this.bpartnerIds = bpartnerIds.stream()
					.filter(bpartnerId -> bpartnerId > 0)
					.collect(ImmutableSet.toImmutableSet());
			Check.assumeNotEmpty(this.bpartnerIds, "bpartnerIds is not empty");

			this.alsoResetCreditStatusFromBPGroup = alsoResetCreditStatusFromBPGroup;
		}
	}
}
