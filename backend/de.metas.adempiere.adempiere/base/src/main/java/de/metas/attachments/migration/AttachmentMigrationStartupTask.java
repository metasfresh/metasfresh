package de.metas.attachments.migration;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.springframework.stereotype.Component;

import de.metas.util.Loggables;
import lombok.NonNull;

@Component
public class AttachmentMigrationStartupTask implements IStartupHouseKeepingTask
{
	private final AttachmentMigrationService migrateAttachmentService;

	public AttachmentMigrationStartupTask(@NonNull final AttachmentMigrationService migrateAttachmentService)
	{
		this.migrateAttachmentService = migrateAttachmentService;
	}

	@Override
	public void executeTask()
	{
		Loggables.addLog("Checking if there are still AD_Attachment records.");
		 migrateAttachmentService.isExistRecordsToMigrateCheckDB();
	}
}
