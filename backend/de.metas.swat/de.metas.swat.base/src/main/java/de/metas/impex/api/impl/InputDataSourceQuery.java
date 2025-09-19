package de.metas.impex.api.impl;

import de.metas.impexp.InputDataSourceId;
import de.metas.organization.OrgId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
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
@Value
public class InputDataSourceQuery
{
OrgId orgId;

	ExternalId externalId;

	String internalName;

	InputDataSourceId inputDataSourceId;

	String value;

	@Builder(toBuilder = true)
	private InputDataSourceQuery(
			@NonNull final OrgId orgId,
			@Nullable final ExternalId externalId,
			@Nullable final String internalName,
			@Nullable final InputDataSourceId inputDataSourceId,
			@Nullable final String value)
	{
		this.orgId = orgId;
		this.externalId = externalId;
		this.internalName = internalName;
		this.inputDataSourceId = inputDataSourceId;
		this.value = value;
	}

}
