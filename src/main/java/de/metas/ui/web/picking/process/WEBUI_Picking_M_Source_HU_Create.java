package de.metas.ui.web.picking.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_ACTIVE_UNPICKED_UNSELECTED_HU;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.util.Services;

import de.metas.handlingunits.IHUPickingSlotDAO;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * This process is available from the HU editor window opened by {@link WEBUI_Picking_OpenHUsToPick}.<br>
 * Its job is to flag the currently selected HUs so they are available as source-HUs for either {@link WEBUI_Picking_PickToNewHU} or {@link WEBUI_Picking_PickToExistingHU}.
 * 
 * @task https://github.com/metasfresh/metasfresh/issues/2298
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_M_Source_HU_Create
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final Optional<HUEditorRow> anyHU = retrieveEligibleHUEditorRows().findAny();

		if (anyHU.isPresent())
		{
			return ProcessPreconditionsResolution.accept();
		}

		final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_ACTIVE_UNPICKED_UNSELECTED_HU);
		return ProcessPreconditionsResolution.reject(reason);
	}

	@Override
	protected String doIt() throws Exception
	{
		retrieveEligibleHUEditorRows().forEach(
				huEditorRow -> {
					final I_M_Source_HU sourceHU = newInstance(I_M_Source_HU.class);
					sourceHU.setM_HU_ID(huEditorRow.getM_HU_ID());
					save(sourceHU);
					addLog("Create M_Source_HU with ID={} and M_HU_ID={}", sourceHU.getM_Source_HU_ID(), sourceHU.getM_HU_ID());
				});
		return MSG_OK;
	}

	private Stream<HUEditorRow> retrieveEligibleHUEditorRows()
	{
		final List<HUEditorRow> huEditorRows = getView().getByIds(getSelectedDocumentIds());
		final IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);

		final Stream<HUEditorRow> stream = huEditorRows.stream()
				.filter(huRow -> huRow.isTopLevel())
				.filter(huRow -> huRow.isHUStatusActive())
				.filter(huRow -> huPickingSlotDAO.isSourceHU(huRow.getM_HU_ID()))
				.filter(huRow -> huPickingSlotDAO.isHuIdPicked(huRow.getM_HU_ID()));

		return stream;
	}

	@Override
	protected HUEditorView getView()
	{
		return HUEditorView.cast(super.getView());
	}
}
