/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.other;

import de.metas.externalsystem.IExternalSystemChildConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
public class ExternalSystemOtherConfig implements IExternalSystemChildConfig
{
	public static final String OTHER_EXTERNAL_SYSTEM_CHILD_VALUE = "OtherChildSysValue";

	ExternalSystemOtherConfigId id;
	List<ExternalSystemOtherConfigParameter> parameters;

	@Builder
	public ExternalSystemOtherConfig(
			@NonNull final ExternalSystemOtherConfigId id,
			@Singular @NonNull final List<ExternalSystemOtherConfigParameter> parameters)
	{
		this.id = id;
		this.parameters = parameters;
	}

	@NonNull
	public static ExternalSystemOtherConfig cast(@NonNull final IExternalSystemChildConfig externalSystemChildConfig)
	{
		return (ExternalSystemOtherConfig)externalSystemChildConfig;
	}

	@Override
	public String getValue()
	{
		return String.valueOf(id.getRepoId());
	}
}
