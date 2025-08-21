package org.adempiere.exceptions;

import java.io.Serial;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Exception thrown when a database connection acquiring was timed out.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DBConnectionAcquireTimeoutException extends DBNoConnectionException
{
	@Serial
	private static final long serialVersionUID = -6558012776228675922L;

	public DBConnectionAcquireTimeoutException(final Throwable timeoutException)
	{
		super(timeoutException.getLocalizedMessage(), timeoutException);
	}
}
