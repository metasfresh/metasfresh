package de.metas.ui.web.picking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.picking.api.PickingConfig;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.ui.web.view.DefaultViewProfileIdProvider;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.window.datatypes.WindowId;

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

@Component
public class PickingTerminalDefaultViewProfileIdProvider implements DefaultViewProfileIdProvider
{
	@Autowired
	private PickingConfigRepository pickingConfigRepo;

	@Override
	public ViewProfileId getDefaultProfileIdByWindowId(WindowId windowId)
	{
		if (PickingConstants.WINDOWID_PackageableView.equals(windowId))
		{
			final PickingConfig pickingConfig = pickingConfigRepo.getPickingConfig();
			final String defaultProfileIdStr = pickingConfig.getWebuiPickingTerminalViewProfileId();
			return ViewProfileId.fromJson(defaultProfileIdStr);
		}
		else
		{
			return null;
		}
	}

}
