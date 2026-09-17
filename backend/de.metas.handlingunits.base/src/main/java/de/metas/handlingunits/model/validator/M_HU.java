package de.metas.handlingunits.model.validator;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.segments.ShipmentScheduleSegmentFromHU;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_Warehouse;

@Interceptor(I_M_HU.class)
public class M_HU
{
	public static final M_HU INSTANCE = new M_HU();

	private final transient Logger logger = LogManager.getLogger(getClass());

	private M_HU()
	{
	}

	/**
	 * Checks if HU is valid.
	 *
	 * @param hu
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
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
	 * Sets the HU's {@code AD_Org_ID} from its locator's warehouse at creation time, when that warehouse has a
	 * real org, so that a physical HU always belongs to the org that owns its warehouse, regardless of the
	 * creating context's org.
	 *
	 * @param hu
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setOrgFromWarehouse(@NonNull final I_M_HU hu)
	{
		final I_M_Warehouse warehouse = IHandlingUnitsBL.extractWarehouseOrNull(hu);
		if (warehouse == null)
		{
			return; // no physical location => keep the context org
		}

		final int warehouseOrgId = warehouse.getAD_Org_ID();
		if (warehouseOrgId <= 0)
		{
			// Warehouse itself carries no real org (0 = * / ANY). Stamping org 0 here would re-introduce the
			// very org-0 physical HU this interceptor exists to prevent, so keep the context org instead —
			// symmetric with the data-repair migration, which likewise only repairs HUs whose warehouse has a
			// real org (w.ad_org_id > 0).
			return;
		}

		hu.setAD_Org_ID(warehouseOrgId);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_HU.COLUMNNAME_HUStatus)
	public void validateStatusChange(@NonNull final I_M_HU hu)
	{
		final I_M_HU oldHu = InterfaceWrapperHelper.createOld(hu, I_M_HU.class);
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
		huStatusBL.assertStatusChangeIsAllowed(hu, oldHu.getHUStatus(), hu.getHUStatus());
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_HU.COLUMNNAME_M_Locator_ID)
	public void validateLocatorChange(@NonNull final I_M_HU hu)
	{
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
		huStatusBL.assertLocatorChangeIsAllowed(hu, hu.getHUStatus());
	}

	/**
	 * Updates the status, locator BP and BPL for child handling units.
	 *
	 * Note that this method only updates the direct children, but that will cause it to be called again and so on.
	 *
	 * @param hu
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = {
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
			Services.get(IHUStatusBL.class).setHUStatus(huContext, childHU, parentHUStatus);

			childHU.setIsActive(parentIsActive);
			childHU.setC_BPartner_ID(parentBPartnerId);
			childHU.setC_BPartner_Location_ID(parentBPLocationId);
			childHU.setM_Locator_ID(parentLocatorId);
			handlingUnitsDAO.saveHU(childHU);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE }, ifColumnsChanged = {
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

		final ShipmentScheduleSegmentFromHU storageSegment = new ShipmentScheduleSegmentFromHU(hu);
		Services.get(IShipmentScheduleInvalidateBL.class).notifySegmentChanged(storageSegment);
	}
}
