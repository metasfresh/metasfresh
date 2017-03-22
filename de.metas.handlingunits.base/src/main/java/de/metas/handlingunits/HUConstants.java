package de.metas.handlingunits;

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

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;

import de.metas.materialtracking.process.M_Material_Tracking_CreateOrUpdate_ID;

/**
 * Misc HU related constants
 *
 * @author tsa
 *
 */
public final class HUConstants
{
	public static final boolean DEBUG_07277_saveHUTrxLine = true; // at the end we really really need to save them!!!
	public static final boolean DEBUG_07277_processHUTrxAttribute = true; // at the end we really really need to process them!!!

	public static final boolean is_07277_createHUsInMemory()
	{
		// Check.assume(DEBUG_07277, "DEBUG_07277 enabled");

		return false;
	}

	// FIXME: test de.metas.customer.picking.service.impl.PackingService is failing beacuse of
	// de.metas.handlingunits.impl.HandlingUnitsDAO.createHUItem(I_M_HU, I_M_HU_PI_Item):
	// IncludedHUsLocalCache
	// .getCreate(item)
	// .setEmptyNotStaled();
	public static final boolean DEBUG_07504_Disable_IncludedHUsLocalCache = true;

	// NOTE: if not disabled, following test is failing: de.metas.handlingunits.attributes.impl.merge.MergeWeightAttributePropagationTest.testMergeFullWeightTransfer_LUs()
	public static final boolean DEBUG_07504_Disable_HUItemsLocalCache = true;

	public static final String SYSCONFIG_UseCacheWhenCreatingHUs = "de.metas.handlingunits.HUConstants.UseCacheWhenCreatingHUs";
	public static final boolean DEFAULT_UseCacheWhenCreatingHUs = true;

	/**
	 *
	 * @return true if we shall use caching while generating handling units
	 * @task http://dewiki908/mediawiki/index.php/07504_Performance_HU_%28108650725490%29
	 */
	public static boolean isUseCacheWhenCreatingHUs()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_UseCacheWhenCreatingHUs, DEFAULT_UseCacheWhenCreatingHUs);
	}

	private HUConstants()
	{
		super();
	};

	/**
	 * Enable/Disable the feature of quickly creating the shipments without any HU generation or something.
	 *
	 * NOTE: this will be a temporary solution until will go live with shipment HUs.
	 *
	 * @task http://dewiki908/mediawiki/index.php/08214_Fix_shipment_schedule_performances
	 */
	private static final boolean DEFAULT_QuickShipment = true;
	private static final String SYSCONFIG_QuickShipment = "de.metas.handlingunits.HUConstants.Fresh_QuickShipment";

	public static final boolean isQuickShipment()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_QuickShipment, DEFAULT_QuickShipment);
	}

	/**
	 * @return true if we shall reset the cache after HUEditor is called, and right before the process lines is called.
	 */
	public static final boolean is08793_HUSelectModel_ResetCacheBeforeProcess()
	{
		final String SYSCONFIG_08793_HUSelectModel_ResetCacheBeforeProcess = "de.metas.handlingunits.client.terminal.select.model.AbstractHUSelectModel.ResetCacheBeforeProcess";
		final boolean DEFAULT_08793_HUSelectModel_ResetCacheBeforeProcess = false;
		final boolean resetCacheBeforeProcessing = Services.get(ISysConfigBL.class).getBooleanValue(
				SYSCONFIG_08793_HUSelectModel_ResetCacheBeforeProcess,
				DEFAULT_08793_HUSelectModel_ResetCacheBeforeProcess);
		return resetCacheBeforeProcessing;
	}

	private static final String SYSCONFIG_AttributeStorageFailOnDisposed = "de.metas.handlingunits.attribute.storage.IAttributeStorage.FailOnDisposed";
	private static final boolean DEFAULT_AttributeStorageFailOnDisposed = true;

	public static final boolean isAttributeStorageFailOnDisposed()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(
				SYSCONFIG_AttributeStorageFailOnDisposed,
				DEFAULT_AttributeStorageFailOnDisposed);
	}

	public static final String DIM_PP_Order_ProductAttribute_To_Transfer = "PP_Order_ProductAttribute_Transfer";
	
	public static final String DIM_Barcode_Attributes = "DIM_Barcode_Attributes";

	/**
	 * @task http://dewiki908/mediawiki/index.php/09106_Material-Vorgangs-ID_nachtr%C3%A4glich_erfassen_%28101556035702%29
	 */
	public static final String PARAM_CHANGE_HU_MAterial_Tracking_ID = M_Material_Tracking_CreateOrUpdate_ID.class.getSimpleName() + ".CHANGE_HUs";

}
