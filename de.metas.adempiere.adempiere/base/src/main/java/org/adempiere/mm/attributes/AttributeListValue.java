package org.adempiere.mm.attributes;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
@Builder
public class AttributeListValue
{
	@NonNull
	AttributeValueId id;

	@NonNull
	AttributeId attributeId;

	@Nullable
	String value;

	@NonNull
	String name;

	@Nullable
	String description;

	@NonNull
	AttributeListValueTrxRestriction availableForTrx;

	boolean active;
	boolean nullFieldValue;

	public int getValueAsInt()
	{
		try
		{
			return Integer.valueOf(value);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed converting value '" + value + "' to int")
					.appendParametersToMessage()
					.setParameter("AttributeListValue", this);

		}
	}

	public ITranslatableString getNameTrl()
	{
		return TranslatableStrings.anyLanguage(getName());
	}

	public boolean isMatchingSOTrx(@Nullable final SOTrx soTrx)
	{
		return availableForTrx.isMatchingSOTrx(soTrx);
	}
}
