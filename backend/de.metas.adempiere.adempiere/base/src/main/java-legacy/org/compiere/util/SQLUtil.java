/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.compiere.util;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class SQLUtil
{
	/**
	 * Walks the {@link SQLWarning} chain and extracts all messages.
	 * In PostgreSQL, {@code RAISE NOTICE} messages are delivered as {@link SQLWarning}s,
	 * so this method is the bridge that captures them for logging to {@code AD_PInstance_Log}.
	 *
	 * @see ExecuteUpdateSQL which uses this to capture RAISE NOTICE output from SQL-type AD_Processes
	 */
	public List<String> extractWarningMessages(@Nullable SQLWarning warning)
	{
		final List<String> warningMessages = new ArrayList<>();

		SQLWarning currentWarning = warning;
		while (currentWarning != null)
		{
			warningMessages.add(currentWarning.getMessage());
			currentWarning = currentWarning.getNextWarning();
		}

		return warningMessages;
	}
}
