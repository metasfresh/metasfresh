package de.metas.handlingunits.receiptschedule.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.impl.AbstractDocumentLUTUConfigurationHandler;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.interfaces.I_C_BPartner;

/* package */class ReceiptScheduleDocumentLUTUConfigurationHandler extends AbstractDocumentLUTUConfigurationHandler<I_M_ReceiptSchedule>
{
	public static final transient ReceiptScheduleDocumentLUTUConfigurationHandler instance = new ReceiptScheduleDocumentLUTUConfigurationHandler();

	private ReceiptScheduleDocumentLUTUConfigurationHandler()
	{
		super();
	}

	@Override
	public I_M_HU_LUTU_Configuration getCurrentLUTUConfigurationOrNull(final I_M_ReceiptSchedule documentLine)
	{
		Check.assumeNotNull(documentLine, "documentLine not null");

		final I_M_HU_LUTU_Configuration lutuConfiguration = documentLine.getM_HU_LUTU_Configuration();
		if (lutuConfiguration == null || lutuConfiguration.getM_HU_LUTU_Configuration_ID() <= 0)
		{
			return null;
		}

		return lutuConfiguration;
	}

	@Override
	public void setCurrentLUTUConfiguration(final I_M_ReceiptSchedule documentLine, final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		documentLine.setM_HU_LUTU_Configuration(lutuConfiguration);
	}

	@Override
	public I_M_HU_LUTU_Configuration createNewLUTUConfiguration(final I_M_ReceiptSchedule documentLine)
	{
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
		final ILUTUConfigurationFactory lutuFactory = Services.get(ILUTUConfigurationFactory.class);

		final I_M_HU_PI_Item_Product tuPIItemProduct = getM_HU_PI_Item_Product(documentLine);
		final I_M_Product cuProduct = documentLine.getM_Product();
		final I_C_UOM cuUOM = documentLine.getC_UOM();

		final I_C_BPartner bpartner = receiptScheduleBL.getC_BPartner_Effective(documentLine);
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuFactory.createLUTUConfiguration(tuPIItemProduct, cuProduct, cuUOM, bpartner);

		//
		// Update LU/TU configuration
		updateLUTUConfiguration(lutuConfiguration, documentLine);
		
		// NOTE: don't save it

		return lutuConfiguration;
	}

	@Override
	public void updateLUTUConfiguration(final I_M_HU_LUTU_Configuration lutuConfiguration, final I_M_ReceiptSchedule documentLine)
	{
		Check.assumeNotNull(lutuConfiguration, "lutuConfiguration not null");
		Check.assumeNotNull(documentLine, "documentLine not null");

		// Services
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

		//
		// Set BPartner / Location to be used
		final I_C_BPartner bpartner = receiptScheduleBL.getC_BPartner_Effective(documentLine);
		final int bpartnerLocationId = receiptScheduleBL.getC_BPartner_Location_Effective_ID(documentLine);
		lutuConfiguration.setC_BPartner(bpartner);
		lutuConfiguration.setC_BPartner_Location_ID(bpartnerLocationId);

		//
		// Set Locator
		final I_M_Locator locator = receiptScheduleBL.getM_Locator_Effective(documentLine);
		lutuConfiguration.setM_Locator(locator);

		//
		// Set HUStatus=Planning because receipt schedules are always about planning
		lutuConfiguration.setHUStatus(X_M_HU.HUSTATUS_Planning);
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_M_HU_PI_Item_Product tuPIItemProduct = receiptSchedule.getM_HU_PI_Item_Product();
		if (tuPIItemProduct != null && tuPIItemProduct.getM_HU_PI_Item_Product_ID() > 0)
		{
			return tuPIItemProduct;
		}

		//
		// Fallback: return Virtual PI item
		final Properties ctx = InterfaceWrapperHelper.getCtx(receiptSchedule);
		return Services.get(IHUPIItemProductDAO.class).retrieveVirtualPIMaterialItemProduct(ctx);
	}
}
