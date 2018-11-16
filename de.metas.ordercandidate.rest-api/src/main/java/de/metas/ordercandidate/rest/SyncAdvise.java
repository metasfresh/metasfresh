package de.metas.ordercandidate.rest;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder
public class SyncAdvise
{
	public static SyncAdvise createDefaultAdvise()
	{
		return SyncAdvise.builder().build();
	}

	public enum IfNotExists
	{
		CREATE,

		FAIL;
	}

	public enum IfExists
	{
		UPDATE,

		DONT_UPDATE;
	}


	@Default
	private IfNotExists ifNotExists = IfNotExists.FAIL;

	@Default
	private IfExists ifExists = IfExists.DONT_UPDATE;
}
