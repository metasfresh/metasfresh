/*
 * #%L
 * de.metas.externalreference
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalreference.greeting;

import de.metas.externalreference.IExternalReferenceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Greeting;

import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_Greeting;

@AllArgsConstructor
@Getter
public enum GreetingExternalReferenceType implements IExternalReferenceType
{
	GREETING(TYPE_Greeting, I_C_Greeting.Table_Name);

	private final String code;
	private final String tableName;
	
	@NonNull
	public static GreetingExternalReferenceType ofCode(final String code)
	{
		if (GREETING.getCode().equals(code))
		{
			return GREETING;
		}
		throw new AdempiereException("Unsupported code " + code + " for GreetingExternalReferenceType. Hint: only 'GREETING' is allowed");
	}
}
