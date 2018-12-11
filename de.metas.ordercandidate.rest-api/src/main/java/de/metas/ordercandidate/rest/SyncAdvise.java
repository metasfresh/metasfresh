package de.metas.ordercandidate.rest;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
		CREATE(false/* fail */, true/* create */),

		FAIL(true/* fail */, false/* create */);

		@Getter
		private final boolean fail;

		@Getter
		private final boolean create;

		private IfNotExists(boolean fail, boolean create)
		{
			this.fail = fail;
			this.create = create;
		}
	}

	public enum IfExists
	{
		UPDATE(true),

		DONT_UPDATE(false);

		@Getter
		private final boolean update;

		private IfExists(boolean update)
		{
			this.update = update;
		}
	}

	@Default
	private IfNotExists ifNotExists = IfNotExists.FAIL;

	@Default
	private IfExists ifExists = IfExists.DONT_UPDATE;

	public boolean isFailIfNotExists()
	{
		return IfNotExists.FAIL.equals(ifNotExists);
	}
}
