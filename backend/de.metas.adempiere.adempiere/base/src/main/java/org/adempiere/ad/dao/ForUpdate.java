package org.adempiere.ad.dao;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

import lombok.Getter;

import javax.annotation.Nullable;

/**
 * Controls whether a query appends a PostgreSQL row-locking clause.
 */
public enum ForUpdate
{
	/** No locking clause; default behaviour. */
	NONE(null),

	/**
	 * Appends {@code FOR UPDATE}.
	 * The lock is held until the enclosing transaction commits or rolls back.
	 * Rows locked by a concurrent transaction will cause the query to block until that transaction ends.
	 */
	FOR_UPDATE("FOR UPDATE"),

	/**
	 * Appends {@code FOR UPDATE SKIP LOCKED}.
	 * Like {@link #FOR_UPDATE} but rows already locked by another transaction are silently skipped
	 * instead of causing the query to block. Useful for work-queue implementations.
	 */
	FOR_UPDATE_SKIP_LOCKED("FOR UPDATE SKIP LOCKED");

	@Getter
	@Nullable
	private final String sqlClause;

	ForUpdate(@Nullable final String sqlClause)
	{
		this.sqlClause = sqlClause;
	}
}
