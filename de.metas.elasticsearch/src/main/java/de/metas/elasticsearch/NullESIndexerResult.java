package de.metas.elasticsearch;

import javax.annotation.concurrent.Immutable;

/*
 * #%L
 * de.metas.elasticsearch
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

@Immutable
/* package */final class NullESIndexerResult implements IESIndexerResult
{
	public static final transient NullESIndexerResult instance = new NullESIndexerResult();

	private NullESIndexerResult()
	{
		super();
	}

	@Override
	public String getSummary()
	{
		return "None";
	}

	@Override
	public long getDurationInMillis()
	{
		return 0;
	}

	@Override
	public int getTotalCount()
	{
		return 0;
	}

	@Override
	public int getOKCount()
	{
		return 0;
	}

	@Override
	public int getFailuresCount()
	{
		return 0;
	}

	@Override
	public boolean hasFailures()
	{
		return false;
	}

	@Override
	public String getFailureMessage()
	{
		return null;
	}

	@Override
	public void throwExceceptionIfAnyFailure()
	{
		// nothing
	}

}
