package de.metas.organization;

import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.common.util.CoalesceUtil.coalesce;

import javax.annotation.Nullable;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


@Value
public class OrgQuery
{
	public static OrgQuery ofValue(@NonNull final String value)
	{
		return OrgQuery.builder().orgValue(value).build();
	}

	boolean failIfNotExists;
	String orgValue;

	@Builder
	private OrgQuery(
			@Nullable final Boolean failIfNotExists,
			@NonNull final String orgValue)
	{
		this.failIfNotExists = coalesce(failIfNotExists, false);
		this.orgValue = assumeNotEmpty(orgValue.trim(), "Parameter 'orgValue' may not be empty");
	}
}