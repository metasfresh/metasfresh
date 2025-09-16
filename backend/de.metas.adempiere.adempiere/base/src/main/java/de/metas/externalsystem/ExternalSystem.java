package de.metas.externalsystem;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

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
@Builder(toBuilder = true)
public class ExternalSystem
{
	public static final ExternalSystem NULL = ExternalSystem.builder()
			.externalSystemId(null)
			.name("NULL")
			.value("NULL")
			.build();

	@RequiredArgsConstructor
	@Getter
	public enum SystemValue
	{
		Alberta("Alberta"),
		RabbitMQ("RabbitMQRESTAPI"),
		WOO("WOO"),
		GRSSignum("GRSSignum"),
		LeichUndMehl("LeichUndMehl"),
		ProCareManagement("ProCareManagement"),
		Shopware6("Shopware6"),
		Other("Other"),
		PrintingClient("PrintingClient"),
		Github("Github"),
		Everhour("Everhour");

		private final String value;

		@Nullable
		private static SystemValue ofValueOrNull(final String value)
		{
			return Arrays.stream(values())
					.filter(systemValue -> systemValue.getValue().equals(value))
					.findFirst()
					.orElse(null);
		}

		@Nullable
		private static SystemValue ofValue(final String value)
		{
			return Arrays.stream(values())
					.filter(systemValue -> systemValue.getValue().equals(value))
					.findFirst()
					.orElseThrow(() -> new AdempiereException("Unknown SystemValue " + value + " for ExternalSystem"));
		}
	}

	@Nullable ExternalSystemId externalSystemId;
	@NonNull String name;
	@NonNull String value;

	@Nullable
	SystemValue getSystemValueOrNull() {return SystemValue.ofValueOrNull(value);}

	@NonNull
	SystemValue getSystemValue() {return SystemValue.ofValue(value);}

	public void assertIdSet()
	{
		if (externalSystemId == null)
		{
			throw new AdempiereException("externalSystemId is not set for " + this);
		}

	}
}
