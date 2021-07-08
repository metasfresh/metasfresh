/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.external;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.IExternalSystem;
import de.metas.order.InvoiceRule;
import de.metas.serviceprovider.model.X_S_ExternalProjectReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum ExternalSystem implements IExternalSystem
{
	GITHUB(X_S_ExternalProjectReference.EXTERNALSYSTEM_Github),
	EVERHOUR(X_S_ExternalProjectReference.EXTERNALSYSTEM_Everhour);

	private final String code;

	public static ExternalSystem cast(final IExternalSystem externalSystem)
	{
		return (ExternalSystem)externalSystem;
	}

	@NonNull
	public static Optional<ExternalSystem> of(@NonNull final String code)
	{
		return Stream.of(values())
				.filter(type -> type.getCode().equals(code))
				.findFirst();
	}

	public static ExternalSystem ofCode(@NonNull final String code)
	{
		final ExternalSystem type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ExternalSystem.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, ExternalSystem> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ExternalSystem::getCode);
}

