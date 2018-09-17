package de.metas.ui.web.handlingunits.process;

import org.compiere.Adempiere;

import de.metas.handlingunits.locking.HULotNumberLockService;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_M_HU_MoveToAnotherWarehouse extends WEBUI_M_HU_MoveToAnotherWarehouse_Helper
{
	private final transient HULotNumberLockService lotNumberLockService = Adempiere.getBean(HULotNumberLockService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final boolean lockedHUs = streamSelectedHUs(Select.ONLY_TOPLEVEL)
				.filter(huRecord -> lotNumberLockService.isLockedHU(huRecord))
				.findAny()
				.isPresent();
		if (lockedHUs)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Some HUs are locked");
		}

		return super.checkPreconditionsApplicable();
	}
}
