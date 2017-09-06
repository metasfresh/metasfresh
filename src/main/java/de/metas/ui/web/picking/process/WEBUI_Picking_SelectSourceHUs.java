package de.metas.ui.web.picking.process;

import de.metas.process.JavaProcess;

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
public class WEBUI_Picking_SelectSourceHUs extends JavaProcess
{

	@Override
	protected String doIt() throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

}
