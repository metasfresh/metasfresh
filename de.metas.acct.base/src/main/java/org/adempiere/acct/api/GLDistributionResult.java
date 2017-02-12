package org.adempiere.acct.api;

import java.util.List;

import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableList;

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
 * The result of a {@link GLDistributionBuilder} run.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class GLDistributionResult
{
	public static final GLDistributionResult of(final List<GLDistributionResultLine> resultLines)
	{
		return new GLDistributionResult(resultLines);
	}

	private final List<GLDistributionResultLine> resultLines;

	private GLDistributionResult(final List<GLDistributionResultLine> resultLines)
	{
		super();
		this.resultLines = ImmutableList.copyOf(resultLines);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public List<GLDistributionResultLine> getResultLines()
	{
		return resultLines;
	}
}
