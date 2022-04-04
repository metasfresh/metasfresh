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

package de.metas.externalsystem.ebay;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableMap;
import de.metas.externalsystem.model.X_ExternalSystem_Config_Ebay;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

@AllArgsConstructor
@Getter
public enum ApiMode implements ReferenceListAwareEnum
{
	PRODUCTION(X_ExternalSystem_Config_Ebay.API_MODE_PRODUCTION),
	SANDBOX(X_ExternalSystem_Config_Ebay.API_MODE_SANDBOX);

	private final String code;

	@JsonCreator
	public static ApiMode ofCode(@NonNull final String code)
	{
		final ApiMode type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ApiMode.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, ApiMode> typesByCode = ReferenceListAwareEnums.indexByCode(values());
}
