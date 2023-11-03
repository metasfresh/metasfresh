/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

@EqualsAndHashCode
public final class StepDefDataIdentifier
{
	public static final String SUFFIX = "Identifier";

	@NonNull private final String value;

	private StepDefDataIdentifier(@NonNull final String value)
	{
		this.value = value;
	}

	public static StepDefDataIdentifier ofString(@NonNull String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("Invalid identifier `" + value + "`");
		}
		return new StepDefDataIdentifier(valueNorm);
	}

	@Override
	public String toString() {return getAsString();}

	public String getAsString() {return value;}

	public <T> T lookupIn(@NonNull final StepDefData<T> table) {return table.get(getAsString());}

	public <T> void put(@NonNull final StepDefData<T> table, @NonNull T record) {table.put(this, record);}

	public <T> void putOrReplace(@NonNull final StepDefData<T> table, @NonNull T record) {table.putOrReplace(this, record);}
}
