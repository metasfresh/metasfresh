package de.metas.error;

import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Collections;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Test double that only counts how often an AD_Issue would have been created, without touching the database.
 */
public class CountingErrorManager implements IErrorManager
{
	public int createIssueCount = 0;

	private AdIssueId nextIssueId()
	{
		createIssueCount++;
		return AdIssueId.ofRepoId(createIssueCount);
	}

	@Override
	public AdIssueId createIssue(final Throwable t)
	{
		return nextIssueId();
	}

	@Override
	public AdIssueId createIssue(final IssueCreateRequest request)
	{
		return nextIssueId();
	}

	@Override
	public AdIssueId insertRemoteIssue(final InsertRemoteIssueRequest request)
	{
		return nextIssueId();
	}

	@Override
	public void markIssueAcknowledged(final AdIssueId adIssueId)
	{
		// nothing
	}

	@Override
	public IssueCountersByCategory getIssueCountersByCategory(
			final TableRecordReference recordRef,
			final boolean onlyNotAcknowledged)
	{
		return IssueCountersByCategory.of(Collections.emptyMap());
	}
}
