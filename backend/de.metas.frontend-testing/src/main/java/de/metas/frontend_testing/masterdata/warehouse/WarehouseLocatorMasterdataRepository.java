package de.metas.frontend_testing.masterdata.warehouse;

import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Locator;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Persists the HU-module flag {@code M_Locator.IsAfterPickingLocator} of a test locator. Separate from
 * {@link WarehouseCommand} because persistence primitives ({@code InterfaceWrapperHelper.saveRecord}) belong in a
 * {@code *Repository}/{@code *DAO} rather than in a command (docs/coding-rules/service-injection.md section 4).
 * <p>
 * Repository Tables: M_Locator
 */
class WarehouseLocatorMasterdataRepository
{
	void markAsAfterPickingLocator(@NonNull final I_M_Locator locatorRecord)
	{
		// fully qualified: the HU module's I_M_Locator (which has IsAfterPickingLocator) clashes with the imported org.compiere one
		final de.metas.handlingunits.model.I_M_Locator huLocatorRecord = InterfaceWrapperHelper.create(locatorRecord, de.metas.handlingunits.model.I_M_Locator.class);
		huLocatorRecord.setIsAfterPickingLocator(true);
		saveRecord(huLocatorRecord);
	}
}
