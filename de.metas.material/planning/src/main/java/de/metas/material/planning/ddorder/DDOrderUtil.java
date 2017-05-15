package de.metas.material.planning.ddorder;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.service.IBPartnerOrgBL;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DDOrderUtil
{
	public int retrieveInTransitWarehouseId(@NonNull final Properties ctx, final int adOrgId)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

		final I_M_Warehouse warehouseInTransitForOrg = warehouseDAO.retrieveWarehouseInTransitForOrg(ctx, adOrgId);
		return warehouseInTransitForOrg.getM_Warehouse_ID();
	}

	public int retrieveOrgBPartnerId(@NonNull final Properties ctx, final int orgId)
	{
		final I_AD_Org org = InterfaceWrapperHelper.create(ctx, orgId, I_AD_Org.class, ITrx.TRXNAME_None);

		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(org);

		return orgBPartner.getC_BPartner_ID();
	}

	public int retrieveOrgBPartnerLocationId(@NonNull final Properties ctx, final int orgId)
	{
		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner_Location orgBPLocation = bpartnerOrgBL.retrieveOrgBPLocation(ctx, orgId, ITrx.TRXNAME_None);

		return orgBPLocation.getC_BPartner_Location_ID();
	}
}
