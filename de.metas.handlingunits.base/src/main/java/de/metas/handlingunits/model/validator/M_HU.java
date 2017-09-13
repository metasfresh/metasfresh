package de.metas.handlingunits.model.validator;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.logging.LogManager;
import de.metas.storage.IStorageListeners;
import de.metas.storage.spi.hu.impl.StorageSegmentFromHU;

@Validator(I_M_HU.class)
public class M_HU
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	/**
	 * Checks if HU is valid.
	 *
	 * @param hu
	 */
	@ModelChange(timings =
		{ ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Check: LUs shall always be Top-Level
		if (handlingUnitsBL.isLoadingUnit(hu))
		{
			if (!handlingUnitsBL.isTopLevel(hu))
			{
				throw new HUException("Loading units shall always be top level"
						+ "\n@M_HU_ID@: " + hu.getValue() + " (ID=" + hu.getM_HU_ID() + ")");
			}
		}

		// FIXME: DEBUG
		if (hu.getM_HU_ID() > 0)
		{
			final String trxName = InterfaceWrapperHelper.getTrxName(hu);
			if (trxName == null || trxName.startsWith("POSave"))
			{
				final HUException ex = new HUException("Changing HUs out of transaction is not allowed"
						+ "\n HU: " + hu
						+ "\n trxName: " + trxName);
				if (Services.get(IDeveloperModeBL.class).isEnabled())
				{
					throw ex;
				}
				else
				{
					logger.warn(ex.getLocalizedMessage() + " [ IGNORED ]", ex);
				}
			}
		}
	}

	/**
	 * Updates the status, locator BP and BPL for child handling units.
	 * 
	 * Note that this method only updates the direct children, but that will cause it to be called again and so on.
	 *
	 * @param hu
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged =
		{
				I_M_HU.COLUMNNAME_HUStatus,
				I_M_HU.COLUMNNAME_IsActive,
				I_M_HU.COLUMNNAME_C_BPartner_ID,
				I_M_HU.COLUMNNAME_C_BPartner_Location_ID,
				I_M_HU.COLUMNNAME_M_Locator_ID
		})
	public void updateChildren(final I_M_HU hu)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		//
		// Retrieve included HUs
		final List<I_M_HU> childHUs = handlingUnitsDAO.retrieveIncludedHUs(hu);
		if (childHUs.isEmpty())
		{
			// no children => nothing to do
			return;
		}

		//
		// Extract relevant fields from parent
		// NOTE: make sure these fields are in the list of columns changed of ModelChange annotation
		final String parentHUStatus = hu.getHUStatus();
		final boolean parentIsActive = hu.isActive();
		final int parentBPartnerId = hu.getC_BPartner_ID();
		final int parentBPLocationId = hu.getC_BPartner_Location_ID();
		final int parentLocatorId = hu.getM_Locator_ID();

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(hu);
		final IHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(contextProvider);

		//
		// Iterate children and update relevant fields from parent
		for (final I_M_HU childHU : childHUs)
		{
			Services.get(IHandlingUnitsBL.class).setHUStatus(huContext, childHU, parentHUStatus);
			childHU.setIsActive(parentIsActive);
			childHU.setC_BPartner_ID(parentBPartnerId);
			childHU.setC_BPartner_Location_ID(parentBPLocationId);
			childHU.setM_Locator_ID(parentLocatorId);
			handlingUnitsDAO.saveHU(childHU);
		}
	}

	/**
	 * When a picked HU is destroyed, it has to be removed from the picking slot.
	 * 
	 * NOTE: We don't know if the old status of the HU is picked, but {@link}removeFromPickingSlotQueue
	 *
	 * @param hu
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged =
		{
				I_M_HU.COLUMNNAME_HUStatus
		})
	public void onDestroyedHU(final I_M_HU hu)
	{
		//
		// Make sure our HU is top level
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final boolean isTopLevel = handlingUnitsBL.isTopLevel(hu);
		if (!isTopLevel)
		{
			// Only do this for the top level HUs
			return;
		}

		//
		// Make sure our HU was destroyed
		final String huStatus = hu.getHUStatus();
		if (!X_M_HU.HUSTATUS_Destroyed.equals(huStatus))
		{
			// Do nothing in case the HU was not destroyed
			return;
		}

		//
		// At this point, it means we have a top-level, destroyed HU. We need to take it out from the picking slots
		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
		huPickingSlotBL.removeFromPickingSlotQueue(hu);
	}

	@ModelChange(timings =
		{ ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE }, ifColumnsChanged =
		{
				I_M_HU.COLUMNNAME_HUStatus,
				I_M_HU.COLUMNNAME_IsActive,
				I_M_HU.COLUMNNAME_C_BPartner_ID,
				I_M_HU.COLUMNNAME_M_Locator_ID
		})
	public void fireStorageSegmentChanged(final I_M_HU hu)
	{
		// Consider only VHUs
		if (!Services.get(IHandlingUnitsBL.class).isVirtual(hu))
		{
			return;
		}

		final StorageSegmentFromHU storageSegment = new StorageSegmentFromHU(hu);
		Services.get(IStorageListeners.class).notifyStorageSegmentChanged(storageSegment);
	}
}
