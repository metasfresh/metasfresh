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

package de.metas.externalreference.bpartnerlocation;

import de.metas.externalreference.IExternalReferenceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Location;

import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_BPartnerLocation;

@AllArgsConstructor
@Getter
public enum BPLocationExternalReferenceType implements IExternalReferenceType
{
	BPARTNER_LOCATION(TYPE_BPartnerLocation, I_C_BPartner_Location.Table_Name);

	private final String code;
	private final String tableName;

	public static BPLocationExternalReferenceType ofCode(final String code)
	{
		if (BPARTNER_LOCATION.getCode().equals(code))
		{
			return BPARTNER_LOCATION;
		}
		throw new AdempiereException("Unsupported code " + code + " for BPLocationExternalReferenceType. Hint: only 'BPartnerLocation' is allowed");
	}
}
