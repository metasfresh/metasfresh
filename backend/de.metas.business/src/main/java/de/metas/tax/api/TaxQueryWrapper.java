/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.tax.api;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;

import javax.annotation.Nullable;

@Value
public class TaxQueryWrapper
{
	private static final transient Logger logger = LogManager.getLogger(TaxQueryWrapper.class);
	@NonNull
	TaxQuery taxQuery;

	@Nullable
	StringBuilder taxQueryLogger;

	@Builder
	public TaxQueryWrapper(final @NonNull TaxQuery taxQuery,
			@Nullable final StringBuilder taxQueryLogger)
	{
		this.taxQuery = taxQuery;

		this.taxQueryLogger = taxQueryLogger;
	}

	public void addLog(final String msg, final Object... msgParameters)
	{
		addLog(Level.INFO, msg, msgParameters);
	}

	public void addLog(final Level level, final String msg, final Object... msgParameters)
	{
		final String message = StringUtils.formatMessage(msg, msgParameters);
		addLog(Loggables.withLogger(TaxQueryWrapper.logger, level), message);
	}

	private void addLog(final ILoggable loggable, final String message)
	{
		if (taxQueryLogger != null)
		{
			taxQueryLogger.append(message).append("\n");
		}
		loggable.addLog(message);
	}
}
