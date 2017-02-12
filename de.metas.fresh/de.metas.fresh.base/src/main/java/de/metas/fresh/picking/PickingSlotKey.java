/**
 *
 */
package de.metas.fresh.picking;

/*
 * #%L
 * de.metas.fresh.base
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

import java.awt.Color;
import java.util.Collection;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyStatus;
import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.model.I_C_POSKey;
import de.metas.fresh.picking.form.IFreshPackingItem;
import de.metas.fresh.picking.terminal.FreshProductKey;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.IHUPickingSlotBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.logging.LogManager;
import de.metas.picking.terminal.Utils;
import de.metas.picking.terminal.Utils.PackingStates;

/**
 * Picking Slot Terminal Key
 *
 * @author cg
 *
 */
public class PickingSlotKey extends TerminalKey
{
	// services
	private static final Logger logger = LogManager.getLogger(PickingSlotKey.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);
	private final transient IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);

	public static final String PROPERTY_Status = "Status";

	private final I_M_PickingSlot pickingSlot;
	private final String tableName;
	private final PickingSlotKeyGroup pickingSlotKeyGroup;
	private final I_C_POSKey key;
	private final KeyNamePair value;
	private String name;

	private I_M_HU_PI_Item_Product piItemProduct;

	protected PickingSlotKey(final ITerminalContext terminalContext, final de.metas.picking.model.I_M_PickingSlot pickingSlot)
	{
		super(terminalContext);

		Check.assumeNotNull(pickingSlot, "pickingSlot");
		this.pickingSlot = InterfaceWrapperHelper.create(pickingSlot, I_M_PickingSlot.class);
		tableName = de.metas.picking.model.I_M_PickingSlot.Table_Name;
		key = createPOSKey(this.pickingSlot);
		pickingSlotKeyGroup = new PickingSlotKeyGroup(pickingSlot.getC_BPartner_ID(), pickingSlot.getC_BPartner_Location_ID());

		final String pickingSlotName = buildPickingSlotName();
		key.setName(pickingSlotName);

		value = new KeyNamePair(pickingSlot.getM_PickingSlot_ID(), pickingSlotName);
		status = new PickingSlotStatus();

		//
		// Preload the M_HU_PI_Item_Product from current HU, if any
		{
			I_M_HU hu = this.pickingSlot.getM_HU();
			if (hu != null)
			{
				this.piItemProduct = hu.getM_HU_PI_Item_Product();
			}
		}

		updateName();
	}

	//
	public void updateStatus(final PackingStates pState)
	{

		final PickingSlotStatus s = new PickingSlotStatus();
		s.setState(pState);

		// Set new name & fire events
		final ITerminalKeyStatus oldStatus = status;
		status = s;
		listeners.firePropertyChange(PickingSlotKey.PROPERTY_Status, oldStatus, status);
	}

	private static I_C_POSKey createPOSKey(final I_M_PickingSlot pickingSlot)
	{
		final I_C_POSKey key = InterfaceWrapperHelper.newInstance(I_C_POSKey.class, pickingSlot);
		InterfaceWrapperHelper.setSaveDeleteDisabled(key, true);
		return key;
	}

	public I_M_PickingSlot getM_PickingSlot()
	{
		return pickingSlot;
	}

	@Override
	public String getId()
	{
		return "" + key.getC_POSKey_ID()
				+ (value != null ? "#" + value.getKey() : "");
	}

	@Override
	public Object getName()
	{
		return name;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public String getText()
	{
		return key.getText();
	}

	@Override
	public int getSpanX()
	{
		return key.getSpanX();
	}

	@Override
	public int getSpanY()
	{
		return key.getSpanY();
	}

	@Override
	public boolean isActive()
	{
		return key.isActive();
	}

	@Override
	public IKeyLayout getSubKeyLayout()
	{
		// nothing to do
		return null;
	}

	private class PickingSlotStatus implements ITerminalKeyStatus
	{
		private Color color;
		private PackingStates packStatus;

		public PackingStates getPackStatus()
		{
			return packStatus;
		}

		public PickingSlotStatus()
		{
			packStatus = PackingStates.unpacked;
			setColor(packStatus.getColor());
		}

		public void setState(final PackingStates state)
		{
			packStatus = state;
			setColor(packStatus.getColor());
		}

		@Override
		public int getAD_Image_ID()
		{
			return key.getAD_Image_ID();
		}

		@Override
		public int getAD_PrintColor_ID()
		{
			return key.getAD_PrintColor_ID();
		}

		@Override
		public int getAD_PrintFont_ID()
		{
			return key.getAD_PrintFont_ID();
		}

		@Override
		public String getName()
		{
			return getPackStatus().name();
		}

		@Override
		public Color getColor()
		{
			return color;
		}

		public void setColor(final Color color)
		{
			this.color = color;
		}

	}

	public void setM_HU(final I_M_HU hu)
	{
		Check.assume(hu == null || hu.getM_HU_ID() > 0, "HU {} shall be saved", hu);
		pickingSlot.setM_HU(hu);
		InterfaceWrapperHelper.save(pickingSlot);

		updateName();
	}

	public I_M_HU getM_HU()
	{
		return getM_HU(false); // requery = false
	}

	public I_M_HU getM_HU(final boolean requery)
	{
		if (requery)
		{
			InterfaceWrapperHelper.refresh(pickingSlot);
			updateName();
		}

		final I_M_HU hu = pickingSlot.getM_HU();
		if (hu == null || hu.getM_HU_ID() <= 0)
		{
			return null;
		}
		return hu;
	}

	/**
	 * Same as {@link #hasOpenHU(boolean)} but is not re-querying the database.
	 *
	 * @return true if this picking slot has an open Handling Unit
	 */
	public boolean hasOpenHU()
	{
		final boolean requery = false;
		return hasOpenHU(requery);
	}

	/**
	 * Checks if this picking slot has an open Handling Unit.
	 *
	 * @param requery if true database is re-queried in order to have fresh informations
	 * @return true if this picking slot has an open Handling Unit
	 */
	public boolean hasOpenHU(final boolean requery)
	{
		final I_M_HU currentHU = getM_HU(requery);
		return currentHU != null;
	}

	/**
	 * Checks if this picking slot has an open Handling Unit and it's storage is NOT empty.
	 * 
	 * If there is no open HUs on this picking slot, this method returns false.
	 * 
	 * @return true if this picking slot has an open HU which is NOT empty.
	 */
	public boolean hasOpenNotEmptyHU()
	{
		final I_M_HU currentHU = getM_HU();
		if (currentHU == null)
		{
			return false;
		}

		final boolean empty = handlingUnitsBL
				.getStorageFactory()
				.getStorage(currentHU)
				.isEmpty();

		return !empty;
	}

	/**
	 * @return capacity of the current open HU or <code>null</code>
	 */
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return piItemProduct;
	}

	/**
	 * Gets total capacity of the HU which is currently open this picking slot.
	 * 
	 * @param product
	 * @param uom
	 * 
	 * @return
	 *         <ul>
	 *         <li>total capacity definition
	 *         <li>or <code>null</code> if the capacity information is not available
	 *         </ul>
	 */
	public IHUCapacityDefinition getHUTotalCapacity(final I_M_Product product, final I_C_UOM uom)
	{
		final I_M_HU_PI_Item_Product piItemProduct = getM_HU_PI_Item_Product();
		if (piItemProduct == null)
		{
			return null;
		}

		// Corner case (FRESH-194):
		// * picking slot contains an HU which was previously loaded with a different product, i.e. M_HU.M_HU_PI_Item_Product_ID is about a different product
		// * now user wants to load another product on that HU, so M_HU.M_HU_PI_Item_Product_ID.M_Product_ID will not match given "product"
		//
		// HOTFIX: just skip calculating the capacity and return null. In this case, it is assumed that the caller will skip enforcing the capacity.
		if (product != null
				&& !piItemProduct.isAllowAnyProduct()
				&& piItemProduct.getM_Product_ID() != product.getM_Product_ID())
		{
			logger.info("Product {} is not matching {}. Returning null capacity.", product, piItemProduct);
			return null;
		}

		return huCapacityBL.getCapacity(piItemProduct, product, uom);
	}

	private String buildPickingSlotName()
	{
		// Max text length
		// NOTE: please think twice before changing this number because it was tuned for 1024x740 resolution
		// see http://dewiki908/mediawiki/index.php/05863_Fenster_Kommissionierung_-_bessere_Ausnutzung_Kn%C3%B6pfefelder_f%C3%BCr_Textausgabe_%28102244669218%29
		final int maxLength = 23;

		final StringBuilder pickingSlotName = new StringBuilder();

		pickingSlotName.append("<div align=\"center\">")
				.append("<font size=\"5\">")
				.append(pickingSlot.getPickingSlot())
				.append("</font>")
				.append("</div>");

		if (pickingSlot.getM_Warehouse_ID() > 0)
		{
			final String warehouseName = Utils.mkTruncatedstring(pickingSlot.getM_Warehouse().getName(), maxLength);
			pickingSlotName.append("<br>")
					.append("<font size=\"3\">")
					.append(Util.maskHTML(warehouseName))
					.append("</font>");
		}

		if (null != pickingSlot.getC_BPartner())
		{
			final String bpName = Utils.mkTruncatedstring(pickingSlot.getC_BPartner().getName(), maxLength);
			pickingSlotName.append("<br>")
					.append("<font size=\"3\">")
					.append(Util.maskHTML(bpName))
					.append("</font>");
		}

		if (null != pickingSlot.getC_BPartner_Location())
		{
			final String bplName = Utils.mkTruncatedstring(pickingSlot.getC_BPartner_Location().getName(), maxLength);
			pickingSlotName.append("<br>")
					.append("<font size=\"3\">")
					.append(bplName)
					.append("</font>");
		}

		return pickingSlotName.toString();
	}

	/**
	 * Build and update Picking Slot Key's full name
	 */
	private void updateName()
	{
		//
		// Build HU's name
		final I_M_HU hu = pickingSlot.getM_HU();
		final String huName;
		if (hu != null)
		{
			final StringBuilder sb = new StringBuilder();

			final String piName = handlingUnitsBL.getEffectivePIVersion(hu).getM_HU_PI().getName();
			sb.append("<font size=\"3\">")
					.append(Util.maskHTML(piName))
					.append("</font>");

			final String huValue = hu.getValue();
			if (!Check.isEmpty(huValue, true))
			{
				sb.append("<font size=\"3\">").append(" (").append(huValue).append(")").append("</font>");
			}

			huName = sb.toString();
		}
		else
		{
			huName = null;
		}

		//
		// Build picking slot slot name
		final String pickingSlotName = buildPickingSlotName();

		//
		// Build Picking Slot full name
		final String nameNew;
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(pickingSlotName);
			if (!Check.isEmpty(huName, true))
			{
				sb.append("<br>")
						.append(huName);
			}
			nameNew = sb.toString();
		}

		//
		// Set new name & fire events
		final String nameOld = name;
		name = nameNew;
		listeners.firePropertyChange(ITerminalKey.PROPERTY_Name, nameOld, name);
	}

	public PickingSlotKeyGroup getPickingSlotKeyGroup()
	{
		return pickingSlotKeyGroup;
	}

	public void createHU(final PackingMaterialKey packingMaterialKey)
	{
		if (!isCompatible(packingMaterialKey))
		{
			throw new AdempiereException("@NotValid@ @M_HU_PackingMaterial_ID@ " + packingMaterialKey.getName());
		}

		final I_M_HU_PI_Item_Product itemProduct = packingMaterialKey.getM_HU_PI_Item_Product();

		createHU(itemProduct);
	}

	public void createHU(final I_M_HU_PI_Item_Product piItemProduct)
	{
		Check.assumeNotNull(piItemProduct, "piItemProduct not null");

		final I_M_PickingSlot pickingSlot = getM_PickingSlot();
		huPickingSlotBL.createCurrentHU(pickingSlot, piItemProduct);

		this.piItemProduct = piItemProduct;

		// Update picking slot name
		updateName();

	}

	/**
	 * Close current HU if any.
	 */
	public void closeCurrentHU()
	{
		final I_M_PickingSlot pickingSlot = getM_PickingSlot();
		huPickingSlotBL.closeCurrentHU(pickingSlot);

		this.piItemProduct = null;

		// Update picking slot name after current HU was removed
		updateName();
	}

	/**
	 * Add given HUs directly to picking slot queue
	 *
	 * @param hus
	 */
	public void addHUsToQueue(final Collection<I_M_HU> hus)
	{
		if (hus == null || hus.isEmpty())
		{
			return;
		}

		Check.assume(!hasOpenHU(), "Picking slot '{}' shall not have open HUs", getName());

		for (final I_M_HU hu : hus)
		{
			huPickingSlotBL.addToPickingSlotQueue(pickingSlot, hu);
		}
	}

	public boolean isCompatible(final PackingMaterialKey packingMaterialKey)
	{
		Check.assumeNotNull(packingMaterialKey, "packingMaterialKey not null");

		final I_M_PickingSlot pickingSlot = getM_PickingSlot();

		//
		// Picking Slot accepts any partner/location
		if (huPickingSlotBL.isAvailableForAnyBPartner(pickingSlot))
		{
			return true;
		}

		//
		// Check if packing material has at least on BP/BPL pair which is accepted by picking slot.
		for (final I_M_HU_PI_Item_Product restriction : packingMaterialKey.getRestrictions())
		{
			if (huPickingSlotBL.isAvailableForProduct(pickingSlot, restriction))
			{
				return true;
			}
		}
		// None matched => this packing material key is not compatible
		return false;
	}

	public boolean isCompatible(final FreshProductKey productKey)
	{
		Check.assumeNotNull(productKey, "productKey not null");

		final I_M_PickingSlot pickingSlot = getM_PickingSlot();

		//
		// Check if bpartner/location is accepted
		final int bpartnerId = productKey.getC_BPartner_ID();
		final int bpartnerLocationId = productKey.getC_BPartner_Location_ID();
		if (!huPickingSlotBL.isAvailableForBPartnerAndLocation(pickingSlot, bpartnerId, bpartnerLocationId))
		{
			return false;
		}

		//
		// Check if HU accepts our product
		// FIXME: at this moment this method is buggy because BPartner is not considered

		// final I_M_HU hu = getM_HU();
		// if (hu == null)
		// {
		// // no current HU => nothing to validate, accept all products
		// return true;
		// }
		//
		// final I_M_Product product = productKey.getM_Product();
		// if (!Services.get(IHUPIItemProductBL.class).isCompatibleProduct(hu, product))
		// {
		// return false;
		// }

		return true;
	}

	public boolean isCompatible(IFreshPackingItem packingItem)
	{
		if (packingItem == null)
		{
			return false;
		}

		final I_M_PickingSlot pickingSlot = getM_PickingSlot();

		//
		// Check if picking slot accepts packingItem's BPartner and Location
		final int bpartnerId = packingItem.getC_BPartner_ID();
		final int bpartnerLocationId = packingItem.getC_BPartner_Location_ID();
		if (!huPickingSlotBL.isAvailableForBPartnerAndLocation(pickingSlot, bpartnerId, bpartnerLocationId))
		{
			return false;
		}

		return true;
	}

	/**
	 * @return the {@link de.metas.picking.model.I_M_PickingSlot}'s C_BPartner_ID
	 */
	public int getC_BPartner_ID()
	{
		return getM_PickingSlot().getC_BPartner_ID();
	}

	/**
	 * @return the {@link de.metas.picking.model.I_M_PickingSlot}'s C_BPartner_Location_ID
	 */
	public int getC_BPartner_Location_ID()
	{
		return getM_PickingSlot().getC_BPartner_Location_ID();
	}

	/**
	 * If this is a dynamic picking slot (see {@link I_M_PickingSlot#isDynamic()}) and it's not allocated yet (i.e. BPartner and BP Location are not set), allocate it now.
	 *
	 * @param bpartnerId
	 * @param bpartnerLocationId
	 */
	public void allocateDynamicPickingSlotIfPossible(final int bpartnerId, final int bpartnerLocationId)
	{
		final I_M_PickingSlot pickingSlot = getM_PickingSlot();
		huPickingSlotBL.allocatePickingSlot(pickingSlot, bpartnerId, bpartnerLocationId);

		// make sure name gets up2date
		updateName();
	}

	/**
	 * Release the given dynamic picking slot.
	 */
	public void releasePickingSlot()
	{
		huPickingSlotBL.releasePickingSlot(getM_PickingSlot());
	}
}
