/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.name.strategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

@EqualsAndHashCode
public class BPartnerNameAndGreetingStrategyId
{
	private static final Interner<BPartnerNameAndGreetingStrategyId> interner = Interners.newStrongInterner();

	private final String stringRepresentation;

	@JsonCreator
	public static BPartnerNameAndGreetingStrategyId ofString(@NonNull final String stringRepresentation)
	{
		return interner.intern(new BPartnerNameAndGreetingStrategyId(stringRepresentation));
	}

	private BPartnerNameAndGreetingStrategyId(@NonNull final String stringRepresentation)
	{
		this.stringRepresentation = StringUtils.trimBlankToNull(stringRepresentation);
		if (this.stringRepresentation == null)
		{
			throw new AdempiereException("Invalid BPartnerNameAndGreetingStrategyId: `" + stringRepresentation + "`");
		}
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return stringRepresentation;
	}
}
