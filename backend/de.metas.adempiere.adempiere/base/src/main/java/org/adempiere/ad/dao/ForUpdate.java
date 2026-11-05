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
	 * Appends {@code FOR NO KEY UPDATE}.
	 *
	 * <p>Weaker than {@link #FOR_UPDATE}: conflicts with itself, {@link #FOR_UPDATE}, and {@code FOR SHARE},
	 * but is <em>compatible</em> with {@code FOR KEY SHARE}.
	 * PostgreSQL acquires {@code FOR KEY SHARE} at commit time to validate {@code DEFERRABLE INITIALLY DEFERRED}
	 * foreign-key constraints on child rows that reference the locked row; {@code FOR NO KEY UPDATE} does
	 * not block those acquisitions, so it eliminates the deadlock between cost writers and concurrent
	 * transactions that are committing FK-referencing child rows.
	 *
	 * <p>Use this strength for read-modify-write patterns on rows whose primary-key columns are never
	 * changed by the modifier (e.g. M_Cost rows — cost writers update cost amounts, not the key).
	 * Cost writers are still mutually exclusive: two concurrent {@code FOR NO KEY UPDATE} holders conflict
	 * and serialize exactly as they would under {@code FOR UPDATE}.
	 */
	FOR_NO_KEY_UPDATE("FOR NO KEY UPDATE"),

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
