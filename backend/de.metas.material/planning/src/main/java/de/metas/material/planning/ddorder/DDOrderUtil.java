package de.metas.material.planning.ddorder;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.util.Optional;

@UtilityClass
public class DDOrderUtil
{
	public WarehouseId retrieveInTransitWarehouseId(@NonNull final OrgId adOrgId)
	{
		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
		return warehousesRepo.getInTransitWarehouseId(adOrgId);
	}

	public Optional<WarehouseId> retrieveInTransitWarehouseIdIfExists(@NonNull final OrgId adOrgId)
	{
		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
		return warehousesRepo.getInTransitWarehouseIdIfExists(adOrgId);
	}

	public Optional<BPartnerId> retrieveOrgBPartnerId(final OrgId orgId)
	{
		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(orgId.getRepoId());

		return orgBPartner != null ? BPartnerId.optionalOfRepoId(orgBPartner.getC_BPartner_ID()) : Optional.empty();
	}

	public BPartnerLocationId retrieveOrgBPartnerLocationId(@NonNull final OrgId orgId)
	{
		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		return bpartnerOrgBL.retrieveOrgBPLocationId(orgId);
	}

	/**
	 * @param productPlanningData may be {@code null} as of gh #1635
	 * @param networkLine         may also be {@code null} as of gh #1635
	 */
	public int calculateDurationDays(
			@Nullable final ProductPlanning productPlanningData,
			@Nullable final DistributionNetworkLine networkLine)
	{
		//
		// Leadtime
		final int leadtimeDays;
		if (productPlanningData != null)
		{
			leadtimeDays = productPlanningData.getLeadTimeDays();
			Check.assume(leadtimeDays >= 0, "leadtimeDays >= 0");
		}
		else
		{
			leadtimeDays = 0;
		}

		//
		// Transfer time
		final int transferTimeFromNetworkLine;
		if (networkLine != null)
		{
			transferTimeFromNetworkLine = (int)networkLine.getTransferDuration().toDays();
		}
		else
		{
			transferTimeFromNetworkLine = 0;
		}
		final int transferTime;
		if (transferTimeFromNetworkLine > 0)
		{
			transferTime = transferTimeFromNetworkLine;
		}
		else if (productPlanningData != null)
		{
			transferTime = productPlanningData.getTransferTimeDays();
			Check.assume(transferTime >= 0, "transferTime >= 0");
		}
		else
		{
			transferTime = 0;
		}

		return leadtimeDays + transferTime;
	}
}
