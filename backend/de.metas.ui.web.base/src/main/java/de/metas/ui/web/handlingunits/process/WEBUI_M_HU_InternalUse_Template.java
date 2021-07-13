/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateRequest;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateResponse;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.List;

abstract class WEBUI_M_HU_InternalUse_Template extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

	private boolean somethingWasProcessed;

	@Override
	protected final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		if (!streamSelectedHUIds(HUEditorRowFilter.Select.ONLY_TOPLEVEL).findAny().isPresent())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}

		if (streamSelectedHUs(HUEditorRowFilter.Select.ONLY_TOPLEVEL).noneMatch(huStatusBL::isPhysicalHU))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only 'physical' HUs are eligible");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final String doIt()
	{
		final HUInternalUseInventoryCreateResponse result = inventoryService.moveToGarbage(createHUInternalUseInventoryCreateRequest());
		somethingWasProcessed = !result.getInventories().isEmpty();
		return MSG_OK;
	}

	protected abstract HUInternalUseInventoryCreateRequest createHUInternalUseInventoryCreateRequest();

	@NonNull
	protected final List<I_M_HU> getHUsToInternalUse()
	{
		final List<I_M_HU> hus = streamSelectedHUs(HUEditorRowFilter.Select.ONLY_TOPLEVEL).collect(ImmutableList.toImmutableList());
		if (hus.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}
		return hus;
	}

	@Override
	protected final void postProcess(final boolean success)
	{
		if (somethingWasProcessed)
		{
			getView().invalidateAll();
		}
	}
}
