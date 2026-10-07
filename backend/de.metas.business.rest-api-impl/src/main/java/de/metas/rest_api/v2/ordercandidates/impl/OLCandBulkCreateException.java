package de.metas.rest_api.v2.ordercandidates.impl;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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

public class OLCandBulkCreateException extends AdempiereException
{
	@Getter
	private final ImmutableList<Throwable> errors;

	public OLCandBulkCreateException(@NonNull final ImmutableList<Throwable> errors)
	{
		super(TranslatableStrings.constant(buildMessage(errors)));
		this.errors = errors;
	}

	private static String buildMessage(final ImmutableList<Throwable> errors)
	{
		return errors.size() + " order-candidate line(s) could not be created: "
				+ errors.stream().map(Throwable::getMessage).collect(Collectors.joining(" | "));
	}
}
