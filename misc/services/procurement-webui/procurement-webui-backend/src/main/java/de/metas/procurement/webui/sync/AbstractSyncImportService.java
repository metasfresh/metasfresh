package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.dto.IConfirmableDTO;
import de.metas.procurement.webui.model.AbstractEntity;
import de.metas.procurement.webui.model.AbstractTranslationEntity;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public abstract class AbstractSyncImportService
{
	protected final transient Logger logger = LoggerFactory.getLogger(getClass());

	protected final <T extends AbstractEntity> Map<String, T> mapByUuid(final List<T> list)
	{
		if (list.isEmpty())
		{
			return new HashMap<>();
		}

		final Map<String, T> map = new HashMap<>(list.size());
		for (final T entry : list)
		{
			final String uuid = entry.getUuid();
			final T entryOld = map.put(uuid, entry);
			if (entryOld != null)
			{
				logger.warn("Duplicate UUID {} in {}.\n Discarding old entry: {}", uuid, list, entryOld);
				// throw new IllegalArgumentException("Duplicate UUID " + uuid + " in " + list);
			}
		}
		return map;
	}

	protected static final <T extends AbstractTranslationEntity<?>> Map<String, T> mapByLanguage(final List<T> list)
	{
		if (list.isEmpty())
		{
			return new HashMap<>();
		}

		final Map<String, T> map = new HashMap<>(list.size());
		for (final T e : list)
		{
			map.put(e.getLanguage(), e);
		}
		return map;
	}

	/**
	 * Throws an {@link IllegalDeleteRequestException} if the given <code>syncModel</code> has <code>isDeleted</code>.
	 */
	protected void assertNotDeleteRequest(final IConfirmableDTO syncModel, final String reason)
	{
		if (syncModel.isDeleted())
		{
			throw new IllegalDeleteRequestException("Setting Deleted flag to " + syncModel + " is not allowed while: " + reason);
		}
	}

	protected <T extends IConfirmableDTO> T assertNotDeleteRequest_WarnAndFix(@NonNull final T syncModel, final String reason)
	{
		if (syncModel.isDeleted())
		{
			logger.warn("Setting Deleted flag to " + syncModel + " is not allowed while: " + reason + ". Unsetting the flag and going forward.");
			return (T)syncModel.withNotDeleted();
		}
		return syncModel;
	}

	private static class IllegalDeleteRequestException extends RuntimeException
	{
		private static final long serialVersionUID = 8217968386550762496L;

		IllegalDeleteRequestException(final String message)
		{
			super(message);
		}
	}

}
