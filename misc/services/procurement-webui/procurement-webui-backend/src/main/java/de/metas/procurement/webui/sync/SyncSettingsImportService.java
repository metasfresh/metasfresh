package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutInfoMessageRequest;
import de.metas.procurement.webui.service.ISettingsService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
class SyncSettingsImportService extends AbstractSyncImportService
{
	private final ISettingsService settingsService;

	public SyncSettingsImportService(
			@NonNull final ISettingsService settingsService)
	{
		this.settingsService = settingsService;
	}

	public void importSyncInfoMessage(final PutInfoMessageRequest request)
	{
		settingsService.setInfoMessage(request.getMessage());
	}
}
