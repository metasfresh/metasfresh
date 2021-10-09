/*
 * #%L
 * de.metas.elasticsearch
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

package de.metas.fulltextsearch.config;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

@EqualsAndHashCode
public final class ESCommand
{
	@NonNull
	private final String string;

	private ESCommand(@NonNull final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			throw new AdempiereException("Invalid blank ESCommand string: " + string);
		}

		this.string = stringNorm;
	}

	public static ESCommand ofString(final String string)
	{
		return new ESCommand(string);
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	public String getAsString()
	{
		return string;
	}
}
