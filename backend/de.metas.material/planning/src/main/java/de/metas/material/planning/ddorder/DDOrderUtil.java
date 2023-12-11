package de.metas.material.planning.ddorder;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.exception.MrpException;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.eevolution.model.I_DD_NetworkDistributionLine;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Properties;

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

	public int retrieveOrgBPartnerId(@NonNull final Properties ctx, final int orgId)
	{
		final I_AD_Org org = InterfaceWrapperHelper.create(ctx, orgId, I_AD_Org.class, ITrx.TRXNAME_None);

		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(org);

		return orgBPartner.getC_BPartner_ID();
	}

	public BPartnerLocationId retrieveOrgBPartnerLocationId(@NonNull final OrgId orgId)
	{
		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		return bpartnerOrgBL.retrieveOrgBPLocationId(orgId);
	}

	/**
	 *
	 * @param productPlanningData may be {@code null} as of gh #1635
	 * @param networkLine may also be {@code null} as of gh #1635
	 * @return
	 */
	public int calculateDurationDays(
			@Nullable final ProductPlanning productPlanningData,
			@Nullable final I_DD_NetworkDistributionLine networkLine)
	{
		//
		// Leadtime
		final int leadtimeDays;
		if (productPlanningData != null)
		{
			leadtimeDays = productPlanningData.getLeadTimeDays();
			Check.assume(leadtimeDays >= 0, MrpException.class, "leadtimeDays >= 0");
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
			transferTimeFromNetworkLine = networkLine.getTransfertTime().intValueExact();
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
			Check.assume(transferTime >= 0, MrpException.class, "transferTime >= 0");
		}
		else
		{
			transferTime = 0;
		}

		final int durationTotalDays = leadtimeDays + transferTime;
		return durationTotalDays;
	}
}
