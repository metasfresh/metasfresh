package de.metas.handlingunits.materialtracking.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.model.I_M_Attribute;
import de.metas.inoutcandidate.spi.IReceiptScheduleWarehouseDestProvider;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Provides the destination warehouse based on ASI's {@link IHUMaterialTrackingBL#ATTRIBUTENAME_QualityInspectionCycle} attribute value.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class QualityInspectionWarehouseDestProvider implements IReceiptScheduleWarehouseDestProvider
{
	public static final transient QualityInspectionWarehouseDestProvider instance = new QualityInspectionWarehouseDestProvider();

	private static final String SYSCONFIG_QualityInspectionWarehouseDest_Prefix = "QualityInspectionWarehouseDest";

	private QualityInspectionWarehouseDestProvider()
	{
		super();
	}

	@Override
	public I_M_Warehouse getWarehouseDest(final IContext context)
	{
		final I_M_AttributeSetInstance asi = context.getM_AttributeSetInstance();
		if (asi == null || asi.getM_AttributeSetInstance_ID() <= 0)
		{
			return null;
		}

		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final I_M_Attribute qualityInspectionCycleAttribute = attributeDAO.retrieveAttributeByValue(context.getCtx(), IHUMaterialTrackingBL.ATTRIBUTENAME_QualityInspectionCycle, I_M_Attribute.class);
		if(qualityInspectionCycleAttribute == null)
		{
			return null;
		}
		
		final int qualityInspectionCycleAttributeId = qualityInspectionCycleAttribute.getM_Attribute_ID();
		final I_M_AttributeInstance qualityInspectionCycleAttributeInstance = attributeDAO.retrieveAttributeInstance(asi, qualityInspectionCycleAttributeId);
		if (qualityInspectionCycleAttributeInstance == null)
		{
			return null;
		}

		final String qualityInspectionCycleValue = qualityInspectionCycleAttributeInstance.getValue();
		if (Check.isEmpty(qualityInspectionCycleValue, true))
		{
			return null;
		}

		final String sysconfigName = SYSCONFIG_QualityInspectionWarehouseDest_Prefix
				+ "." + context.getM_Warehouse_ID()
				+ "." + qualityInspectionCycleValue;

		final int warehouseDestId = Services.get(ISysConfigBL.class).getIntValue(sysconfigName, -1, context.getAD_Client_ID(), context.getAD_Org_ID());
		if (warehouseDestId <= 0)
		{
			return null;
		}

		final I_M_Warehouse warehouseDest = InterfaceWrapperHelper.create(context.getCtx(), warehouseDestId, I_M_Warehouse.class, ITrx.TRXNAME_None);
		return warehouseDest;
	}

}
