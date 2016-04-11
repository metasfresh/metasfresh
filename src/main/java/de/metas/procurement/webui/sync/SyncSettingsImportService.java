package de.metas.procurement.webui.sync;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.metas.procurement.sync.protocol.SyncInfoMessageRequest;
import de.metas.procurement.webui.service.ISettingsService;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
@Transactional
public class SyncSettingsImportService extends AbstractSyncImportService
{
	@Autowired
	@Lazy
	private ISettingsService settingsService;

	public void importSyncInfoMessage(final SyncInfoMessageRequest request)
	{
		// logger.debug("Importing: {}", request);
		settingsService.setInfoMessage(request.getMessage());
	}
}
