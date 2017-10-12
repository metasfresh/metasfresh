package de.metas.ui.web.pporder.process;

import static de.metas.ui.web.handlingunits.WEBUI_HU_Constants.MSG_WEBUI_SELECT_ACTIVE_UNSELECTED_HU;

import org.adempiere.util.Services;

import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;

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
 * This process is available from the HU editor window opened by {@link WEBUI_PP_Order_HUEditor_Launcher}.<br>
 * Its job is to flag the currently selected HUs so they are available as source-HUs for
 * 
 * @task https://github.com/metasfresh/metasfresh/issues/2298
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_PP_Order_HUEditor_Create_M_Source_HUs
		extends WEBUI_PP_Order_HUEditor_ProcessBase
		implements IProcessPrecondition
{
	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final boolean anyHuMatches = retrieveSelectedAndEligibleHUEditorRows()
				.anyMatch(huRow -> huRow.isTopLevel());
		if (anyHuMatches)
		{
			return ProcessPreconditionsResolution.accept();
		}

		final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_WEBUI_SELECT_ACTIVE_UNSELECTED_HU);
		return ProcessPreconditionsResolution.reject(reason);
	}

	@Override
	protected String doIt() throws Exception
	{
		final SourceHUsService sourceHuService = SourceHUsService.get();
		
		retrieveSelectedAndEligibleHUEditorRows().forEach(
				huEditorRow -> {
					sourceHuService.addSourceHuMarker(huEditorRow.getM_HU_ID());
				});

		invalidateViewsAndPrepareReturn();
		return MSG_OK;
	}
}
