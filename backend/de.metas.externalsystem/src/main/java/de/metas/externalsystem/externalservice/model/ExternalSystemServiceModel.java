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

package de.metas.externalsystem.externalservice.model;

import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.externalservice.common.ExternalStatus;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
public class ExternalSystemServiceModel implements IExternalSystemService
{
	@NonNull
	ExternalSystemServiceId id;

	@NonNull
	ExternalSystemType externalSystemType;

	@NonNull
	String serviceValue;

	@NonNull
	String name;

	@Nullable
	String description;

	@Nullable
	String enableCommand;

	@Nullable
	String disableCommand;

	@Builder
	ExternalSystemServiceModel(
			@NonNull final ExternalSystemServiceId id,
			@NonNull final ExternalSystemType externalSystemType,
			@NonNull final String serviceValue,
			@NonNull final String name,
			@Nullable final String description,
			@Nullable final String enableCommand,
			@Nullable final String disableCommand)
	{
		this.id = id;
		this.externalSystemType = externalSystemType;
		this.serviceValue = serviceValue;
		this.name = name;
		this.description = description;
		this.enableCommand = enableCommand;
		this.disableCommand = disableCommand;
	}

	@Override
	public String getExternalSystemTypeCode()
	{
		return externalSystemType.getCode();
	}


	@NonNull
	public Optional<ExternalStatus> getStatusByCommandOptional(@NonNull final String command)
	{
		if (Check.isNotBlank(enableCommand) && enableCommand.equals(command))
		{
			return Optional.of(ExternalStatus.ACTIVE);
		}

		if (Check.isNotBlank(disableCommand) && disableCommand.equals(command))
		{
			return Optional.of(ExternalStatus.INACTIVE);
		}

		return Optional.empty();
	}

	@NonNull
	public ExternalStatus getStatusByCommand(@NonNull final String command)
	{
		return getStatusByCommandOptional(command)
				.orElseThrow(() -> new AdempiereException("Unknown command!")
						.appendParametersToMessage()
						.setParameter("EnableCommand", getEnableCommand())
						.setParameter("DisableCommand", getDisableCommand())
						.setParameter("Given command", command));
	}

	public boolean matchesCommand(@NonNull final String command)
	{
		return getStatusByCommandOptional(command).isPresent();
	}
}
