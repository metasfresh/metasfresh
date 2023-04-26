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
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.Optional;

@Value
public class ExternalSystemOtherConfig implements IExternalSystemChildConfig
{
	public static final String OTHER_EXTERNAL_SYSTEM_CHILD_VALUE = "OtherChildSysValue";

	ExternalSystemOtherConfigId id;
	ExternalSystemOtherValue value;
	List<ExternalSystemOtherConfigParameter> parameters;

	@Builder
	public ExternalSystemOtherConfig(
			@NonNull final ExternalSystemOtherConfigId id,
			@Singular @NonNull final List<ExternalSystemOtherConfigParameter> parameters)
	{
		this.id = id;
		this.parameters = parameters;
		this.value = ExternalSystemOtherValue.of(id.getExternalSystemParentConfigId());
	}

	@NonNull
	public static ExternalSystemOtherConfig cast(@NonNull final IExternalSystemChildConfig externalSystemChildConfig)
	{
		return (ExternalSystemOtherConfig)externalSystemChildConfig;
	}

	@Override
	public String getValue()
	{
		return value.getStringValue();
	}

	public boolean isSyncBudgetProjectsEnabled()
	{
		return getBooleanValueForParam(ExternalSystemOtherKnownParams.EXPORT_BUDGET_PROJECT.getDbName());
	}

	public boolean isSyncWOProjectsEnabled()
	{
		return getBooleanValueForParam(ExternalSystemOtherKnownParams.EXPORT_WO_PROJECT.getDbName());
	}

	public boolean isSyncBPartnerEnabled()
	{
		return getBooleanValueForParam(ExternalSystemOtherKnownParams.EXPORT_BPARTNER.getDbName());
	}

	public boolean isAutoSendDefaultShippingAddress()
	{
		return getBooleanValueForParam(ExternalSystemOtherKnownParams.AUTO_EXPORT_DEFAULT_SHIPPING_ADDRESS.getDbName());
	}

	@NonNull
	private Optional<ExternalSystemOtherConfigParameter> getParameterByName(@NonNull final String name)
	{
		return parameters.stream()
				.filter(parameter -> parameter.getName().equals(name))
				.findFirst();
	}

	private boolean getBooleanValueForParam(@NonNull final String paramName)
	{
		return getParameterByName(paramName)
				.map(ExternalSystemOtherConfigParameter::getValue)
				.map(value -> StringUtils.toBoolean(value, false))
				.orElse(false);
	}
}
