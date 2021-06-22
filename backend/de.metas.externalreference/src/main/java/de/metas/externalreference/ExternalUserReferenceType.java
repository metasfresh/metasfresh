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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.externalreference.model.X_S_ExternalReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_User;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ExternalUserReferenceType implements IExternalReferenceType
{
	USER_ID(X_S_ExternalReference.TYPE_UserID, I_AD_User.Table_Name);

	/**
	 * Used by the external system (e.g. {code customer})
	 */
	private final String code;

	/**
	 * metasfresh {@link org.compiere.model.I_AD_Table#Table_Name} (e.g. {@code C_BPartner}) of the records that are referenced by an external reference with tis type
	 */
	private final String tableName;

	public static ExternalUserReferenceType cast(final IExternalReferenceType externalReferenceType)
	{
		return (ExternalUserReferenceType)externalReferenceType;
	}

	private static final ImmutableMap<String, ExternalUserReferenceType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ExternalUserReferenceType::getCode);

	public static ExternalUserReferenceType ofCode(@NonNull final String code)
	{
		final ExternalUserReferenceType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ExternalUserReferenceType.class + " found for code: " + code);
		}
		return type;
	}
}
