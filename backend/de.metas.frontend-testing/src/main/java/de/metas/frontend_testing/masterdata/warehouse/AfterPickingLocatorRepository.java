package de.metas.frontend_testing.masterdata.warehouse;

import de.metas.handlingunits.model.I_M_Locator;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Marks a test warehouse's locator as {@code M_Locator.IsAfterPickingLocator}.
 */
class AfterPickingLocatorRepository
{
	void markAsAfterPickingLocator(@NonNull final LocatorId locatorId)
	{
		final I_M_Locator locator = load(locatorId, I_M_Locator.class);
		locator.setIsAfterPickingLocator(true);
		saveRecord(locator);
	}
}
