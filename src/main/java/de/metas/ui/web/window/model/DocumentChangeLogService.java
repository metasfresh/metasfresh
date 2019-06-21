package de.metas.ui.web.window.model;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;

import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.ad.table.RecordChangeLogRepository;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangeLog;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;

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

@Service
public class DocumentChangeLogService
{
	private static final Logger logger = LogManager.getLogger(DocumentChangeLogService.class);

	private final IUserDAO usersRepo = Services.get(IUserDAO.class);
	private final RecordChangeLogRepository changeLogRepository;

	public DocumentChangeLogService(final RecordChangeLogRepository changeLogRepository)
	{
		this.changeLogRepository = changeLogRepository;
	}

	public JSONDocumentChangeLog getJSONDocumentChangeLog(@NonNull final TableRecordReference recordRef, final String adLanguage)
	{
		final RecordChangeLog changeLog = changeLogRepository.getSummaryByRecord(recordRef);
		return toJson(changeLog, adLanguage);
	}

	private JSONDocumentChangeLog toJson(RecordChangeLog changeLog, final String adLanguage)
	{
		final JSONDocumentChangeLog json = new JSONDocumentChangeLog();
		json.setCreatedByUsername(usersRepo.retrieveUserFullname(changeLog.getCreatedByUserId()));
		json.setLastChangedByUsername(usersRepo.retrieveUserFullname(changeLog.getLastChangedByUserId()));

		final ZonedDateTime created = TimeUtil.asZonedDateTime(changeLog.getCreatedTimestamp());
		json.setCreatedTimestamp(formatTimestamp(created, adLanguage));

		final ZonedDateTime updated = TimeUtil.asZonedDateTime(changeLog.getLastChangedTimestamp());
		json.setLastChangedTimestamp(formatTimestamp(updated, adLanguage));

		return json;
	}

	private String formatTimestamp(final ZonedDateTime timestamp, final String adLanguage)
	{
		if (timestamp == null)
		{
			return null;
		}

		try
		{
			final Language language = Language.getLanguage(adLanguage);
			final SimpleDateFormat dateFormat = DisplayType.getDateFormat(DisplayType.DateTime, language);
			return dateFormat.format(TimeUtil.asDate(timestamp));
		}
		catch (Exception ex)
		{
			logger.warn("Failed formating {}. Returning it a string.", timestamp, ex);
			return timestamp.toString();
		}
	}
}
