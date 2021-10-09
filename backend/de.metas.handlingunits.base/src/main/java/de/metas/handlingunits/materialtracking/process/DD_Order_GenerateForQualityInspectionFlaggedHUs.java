package de.metas.handlingunits.materialtracking.process;

import java.util.Iterator;

import de.metas.organization.IOrgDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;
import de.metas.handlingunits.ddorder.api.impl.HUs2DDOrderProducer;
import de.metas.handlingunits.ddorder.api.impl.HUs2DDOrderProducer.HUToDistribute;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Process used to generate DD_Orders to move the HUs flagged as "scheduled from Quality Inspection" from a given warehouse to a given warehouse.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task 08639
 */
public class DD_Order_GenerateForQualityInspectionFlaggedHUs extends JavaProcess
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	// services
	private final transient IHUDDOrderDAO huDDOrderDAO = Services.get(IHUDDOrderDAO.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final transient IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	// Parameters
	private I_M_Warehouse warehouseFrom;
	private I_M_Warehouse warehouseTo;

	@Override
	protected void prepare()
	{
		final IRangeAwareParams params = getParameterAsIParams();
		warehouseFrom = getM_Warehouse(params, "M_Warehouse_ID");
		warehouseTo = getM_Warehouse(params, "M_Warehouse_Dest_ID");

		// make sure user selected different warehouses
		if (warehouseFrom.getM_Warehouse_ID() == warehouseTo.getM_Warehouse_ID())
		{
			throw new AdempiereException("@M_Warehouse_ID@ = @M_Warehouse_Dest_ID@");
		}
	}

	private final I_M_Warehouse getM_Warehouse(final IRangeAwareParams params, final String warehouseParameterName)
	{
		final int warehouseId = params.getParameterAsInt(warehouseParameterName, -1);
		if (warehouseId <= 0)
		{
			throw new FillMandatoryException(warehouseParameterName);
		}
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(getCtx(), warehouseId, I_M_Warehouse.class, ITrx.TRXNAME_None);
		if (warehouse == null)
		{
			throw new FillMandatoryException(warehouseParameterName);
		}
		return warehouse;
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final I_M_Locator locatorTo = warehouseBL.getDefaultLocator(warehouseTo);

		// Organization BPartner & Location
		final I_AD_Org org = orgDAO.getById(OrgId.ofRepoId(warehouseTo.getAD_Org_ID()));
		final I_C_BPartner orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(org);
		Check.assumeNotNull(orgBPartner, "Org BPartner shall exist for {}", org);

		final BPartnerLocationId orgBPLocationId = bpartnerOrgBL.retrieveOrgBPLocationId(OrgId.ofRepoId(org.getAD_Org_ID()));

		HUs2DDOrderProducer.newProducer()
				.setContext(getCtx())
				.setM_Locator_To(locatorTo)
				.setBpartnerId(orgBPartner.getC_BPartner_ID())
				.setBpartnerLocationId(BPartnerLocationId.toRepoId(orgBPLocationId))
				.setHUs(retriveHUs())
				.process();
		return MSG_OK;
	}

	private final Iterator<HUToDistribute> retriveHUs()
	{
		return handlingUnitsDAO.createHUQueryBuilder()
				.setContext(getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setOnlyTopLevelHUs()
				.addOnlyInWarehouseId(WarehouseId.ofRepoId(warehouseFrom.getM_Warehouse_ID()))
				.addOnlyWithAttribute(IHUMaterialTrackingBL.ATTRIBUTENAME_IsQualityInspection, IHUMaterialTrackingBL.ATTRIBUTEVALUE_IsQualityInspection_Yes)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.addFilter(huDDOrderDAO.getHUsNotAlreadyScheduledToMoveFilter())
				//
				.createQuery()
				.stream(I_M_HU.class)
				.map(HUToDistribute::ofHU)
				.iterator();
	}
}
