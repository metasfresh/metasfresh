/*
 * #%L
 * de.metas.externalreference
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

package de.metas.externalreference;

import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ExternalReferenceTypes
{
	private final Map<String, IExternalReferenceType> typesByCode = new HashMap<>();

	public ExternalReferenceTypes()
	{
		registerType(NullExternalReferenceType.NULL);
		registerType(BPartnerExternalReferenceType.BPARTNER);
	}

	public void registerType(@NonNull final IExternalReferenceType type)
	{
		typesByCode.put(type.getCode(), type);
	}

	@NonNull
	public Optional<IExternalReferenceType> ofCode(@NonNull final String code)
	{
		final IExternalReferenceType externalReferenceType = typesByCode.get(code);
		return Optional.ofNullable(externalReferenceType);
	}
}
