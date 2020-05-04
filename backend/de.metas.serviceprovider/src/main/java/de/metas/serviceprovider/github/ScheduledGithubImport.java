/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.github;

import de.metas.process.Param;

import java.time.LocalDate;

public class ScheduledGithubImport extends GithubImportProcess
{
	@Param(parameterName = "OffsetDays")
	private int offsetDays;

	@Override
	protected String doIt() throws Exception
	{
		final LocalDate dateFrom = LocalDate.now().minusDays(offsetDays);

		setDateFrom(dateFrom);

		return super.doIt();
	}
}
