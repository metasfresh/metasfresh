package de.metas.document.sequenceno;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class POReferenceAsSequenceNoProvider implements CustomSequenceNoProvider
{
	/** @return {@code true} if the given {@code documentModel} has a non-null {@code POReference} value. */
	@Override
	public boolean isApplicable(@NonNull final Object documentModel)
	{
		final String poReference = getPOReferenceOrNull(documentModel);
		return !Check.isEmpty(poReference, true);
	}

	/** @return the given {@code documentModel}'s {@code POReference} value. */
	@Override
	public String provideSequenceNo(@NonNull final Object documentModel)
	{
		final String poReference = getPOReferenceOrNull(documentModel);
		return Check.assumeNotEmpty(poReference.trim(), "The given documentModel needs to have a non-empty POreference value; documentModel={}", documentModel);
	}

	private String getPOReferenceOrNull(@NonNull final Object documentModel)
	{
		return InterfaceWrapperHelper.getValueOrNull(documentModel, "POReference");
	}
}
