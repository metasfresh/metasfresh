package de.metas.report.xls.engine;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.report.jasper.server.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ToString
public class ObjectXlsDataSource implements IXlsDataSource
{
	private final Collection<?> rows;
	private final Optional<String> reportFilename;

	@Builder
	private ObjectXlsDataSource(
			@NonNull final Collection<?> rows,
			@Nullable final String reportFilename)
	{
		this.rows = rows;
		this.reportFilename = Optional.ofNullable(StringUtils.trimBlankToNull(reportFilename));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Object> getRows()
	{
		return (Collection<Object>)rows;
	}

	@Override
	public Optional<String> getSuggestedFilename()
	{
		return reportFilename;
	}
}
