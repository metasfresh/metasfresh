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

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.adempiere.exceptions.AdempiereException;

@AllArgsConstructor
@Getter
public enum NullExternalReferenceType implements IExternalReferenceType
{
	NULL("NULL", "NULL");

	/**
	 * Used by the external system (e.g. {code customer})
	 */
	private final String code;

	/**
	 * metasfresh {@link org.compiere.model.I_AD_Table#Table_Name} (e.g. {@code C_BPartner}) of the records that are referenced by an external reference with tis type
	 */
	private final String tableName;

	public static NullExternalReferenceType ofCode(final String code)
	{
		if ("NULL" .equals(code))
		{
			return NULL;
		}
		throw new AdempiereException("Unsupported code " + code + " for NullExternalReferenceType. Hint: only 'NULL' is allowed");
	}
}
