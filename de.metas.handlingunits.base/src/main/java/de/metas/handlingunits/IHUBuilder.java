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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUStorageFactory;

/**
 * Handling Unit Builder Helper
 *
 * @author tsa
 *
 */
public interface IHUBuilder
{
	/**
	 * Create handling unit by calling {@link #create(I_M_HU_PI_Version)} with the current version of the given <code>pi</code>.
	 *
	 * @param pi
	 * @return created handling unit; never return null
	 */
	I_M_HU create(I_M_HU_PI pi);

	/**
	 * Create handling unit using the given <code>piVersion</code> and (as parent item) the {@link I_M_HU_Item} that was previously set via {@link #setM_HU_Item_Parent(I_M_HU_Item)}.
	 *
	 * @param piVersion
	 * @return created handling unit; never return null
	 */
	I_M_HU create(I_M_HU_PI_Version piVersion);

	Date getDate();

	void setDate(Date date);

	void setStorageFactory(IHUStorageFactory storageFactory);

	void setListener(IHUIteratorListener listener);

	I_M_HU_Item getM_HU_Item_Parent();

	void setM_HU_Item_Parent(I_M_HU_Item parentItem);

	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product();

	void setM_HU_PI_Item_Product(I_M_HU_PI_Item_Product piip);

	I_C_BPartner getC_BPartner();

	void setC_BPartner(I_C_BPartner bpartner);

	void setC_BPartner_Location_ID(int bpartnerLocationId);

	/**
	 * Sets M_Locator to be set on newly create HU.
	 *
	 * In case HU Item Parent (see {@link #setM_HU_Item_Parent(I_M_HU_Item)}) is set then locator from parent will be used and this one will be ignored.
	 *
	 * @param locator
	 */
	void setM_Locator(I_M_Locator locator);

	I_M_Locator getM_Locator();

	/**
	 * Sets {@link I_M_HU#COLUMN_HUStatus} to be used when creating a new HU.
	 *
	 * In case HU Item Parent (see {@link #setM_HU_Item_Parent(I_M_HU_Item)}) is set then HUStatus from parent will be used and this one will be ignored.
	 *
	 * @param huStatus HU Status (see X_M_HU.HUSTATUS_*); null is not accepted
	 */
	void setHUStatus(String huStatus);

	String getHUStatus();

	void setM_HU_LUTU_Configuration(I_M_HU_LUTU_Configuration lutuConfiguration);

	I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration();

	/**
	 * For material receipts, flag that the HU's PM owner will be "us". See {@link I_M_HU#isHUPlanningReceiptOwnerPM()}
	 *
	 * @param huPlanningReceiptOwnerPM
	 */
	void setHUPlanningReceiptOwnerPM(boolean huPlanningReceiptOwnerPM);

	/**
	 * @return true if the HU's owner is "us". See {@link I_M_HU#isHUPlanningReceiptOwnerPM()}
	 */
	boolean isHUPlanningReceiptOwnerPM();

}
