package de.metas.handlingunits.shipping.process;

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


import java.util.List;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Shipper;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.slf4j.Logger;

import de.metas.handlingunits.IHUPackageBL;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.IHUPickingSlotBL;
import de.metas.handlingunits.IHUPickingSlotBL.IQueueActionResult;
import de.metas.handlingunits.IHUPickingSlotDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.logging.LogManager;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.shipping.api.IShipperDAO;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.interfaces.I_M_Package;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;

/**
 * Moved from branch gastro_, /de.metas.handlingunits.base/src/main/java/de/metas/shipping/process/M_ShippingPackage_CreateFromPickingSlots.java.
 * Looks like the process was removed in this base line because it was considered obsolete, but now the customer also wants it in this base line.
 *
 * @task 08743.
 */
public class M_ShippingPackage_CreateFromPickingSlots extends SvrProcess implements ISvrProcessPrecondition
{
	private static final transient Logger logger = LogManager.getLogger(M_ShippingPackage_CreateFromPickingSlots.class);

	//
	// services
	private final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);
	private final IShipperTransportationBL shipperTransportationBL = Services.get(IShipperTransportationBL.class);
	private final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);
	private final IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private int p_M_ShipperTransportation_ID = -1;

	public static final String PARAM_C_BPartner_ID = "C_BPartner_ID";
	private int p_C_BPartner_ID = -1;

	private static final String CreateFromPickingSlots_MSG_DOC_PROCESSED = "CreateFromPickingSlots_Msg_Doc_Processed";

	private I_M_ShipperTransportation shipperTransportation;
	private I_M_Shipper shipper;

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		// task 06058: if the document is processed, we not allowed to run this process
		final I_M_ShipperTransportation shipperTransportation = context.getModel(I_M_ShipperTransportation.class);
		return !shipperTransportation.isProcessed();
	}

	@Override
	protected void prepare()
	{
		if (I_M_ShipperTransportation.Table_Name.equals(getTableName()))
		{
			p_M_ShipperTransportation_ID = getRecord_ID();
		}

		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				continue;
			}
			final String parameterName = para.getParameterName();
			if (PARAM_C_BPartner_ID.equals(parameterName))
			{
				p_C_BPartner_ID = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		//
		// Load M_ShipperTransportation and M_Shipper
		if (p_M_ShipperTransportation_ID <= 0)
		{
			throw new FillMandatoryException(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID);
		}

		shipperTransportation = InterfaceWrapperHelper.create(getCtx(), p_M_ShipperTransportation_ID, I_M_ShipperTransportation.class, get_TrxName());
		Check.assumeNotNull(shipperTransportation, "shipperTransportation not null");

		// task 06058: if the document is processed, we not allowed to run this process
		if (shipperTransportation.isProcessed())
		{
			throw new AdempiereException(msgBL.getMsg(getCtx(), CreateFromPickingSlots_MSG_DOC_PROCESSED));
		}

		final I_C_BPartner shipperBPartner = shipperTransportation.getShipper_BPartner();
		shipper = shipperDAO.retrieveForShipperBPartner(shipperBPartner);

		// ts: not brilliant, but note there aren't that many picking slots to i guess it's OK to iterate them all
		final List<I_M_PickingSlot> pickingSlots = InterfaceWrapperHelper.createList(
				pickingSlotDAO.retrievePickingSlots(getCtx(), get_TrxName()),
				I_M_PickingSlot.class);

		//
		// Create packages from picking slots
		for (final I_M_PickingSlot pickingSlot : pickingSlots)
		{
			// Filter out picking slots which are not for our bpartner
			final int pickingSlotBPartnerId = pickingSlot.getC_BPartner_ID();
			if (p_C_BPartner_ID > 0 && p_C_BPartner_ID != pickingSlotBPartnerId)
			{
				continue;
			}
			createShippingPackages(pickingSlot);
		}
		return "OK";
	}

	private void createShippingPackages(final I_M_PickingSlot pickingSlot)
	{
		for (final I_M_HU hu : huPickingSlotDAO.retrieveAllHUs(pickingSlot))
		{
			// tasks 09033: take care of the HU that might still be open in the picking terminal
			// we need to close it to prevent inconsistencies and to make sure that is has a C_BPartner_ID and C_BPartner_Location_ID.
			if (pickingSlot.getM_HU_ID() == hu.getM_HU_ID())
			{
				logger.debug("Closing M_HU {} that is still assigned to M_PickingSlot {}", new Object[] { hu, pickingSlot });

				final IQueueActionResult closeCurrentHUResult = huPickingSlotBL.closeCurrentHU(pickingSlot);
				logger.debug("Result of IHUPickingSlotBL.closeCurrentHU(): {}", closeCurrentHUResult);

				if (handlingUnitsBL.isDestroyed(hu))
				{
					logger.debug("Closing M_HU {} from M_PickingSlot {} destroyed the HU (probably because it was empty; skipping it)", new Object[] { hu, pickingSlot });
					continue;
				}
			}

			createShippingPackage(pickingSlot, hu);
		}
	}

	private void createShippingPackage(final I_M_PickingSlot pickingSlot, final I_M_HU hu)
	{
		if (huPackageDAO.isHUAssignedToPackage(hu))
		{
			logger.debug("M_HU {} is already assingned to a M_Package; returning", hu);
			return;
		}

		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(pickingSlot); // make sure that no one thinks picking slot will be used in createM_Package for anything else but context.
		final I_M_Package mpackage = huPackageBL.createM_Package(ctxAware, hu, shipper);

		final I_M_ShippingPackage shippingPackage = shipperTransportationBL.createShippingPackage(shipperTransportation, mpackage);
		if (shippingPackage == null)
		{
			logger.debug("Unable to create a M_ShippingPackage for M_ShipperTransportation {} and the newly created M_Package {}; returning",
					new Object[] { shipperTransportation, mpackage });
			return;
		}
		addLog("@M_HU_ID@: " + hu.getValue());
	}
}
