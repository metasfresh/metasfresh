package de.metas.rest_api.utils;

import static de.metas.util.Check.assumeNotEmpty;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import lombok.NonNull;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public class MissingPropertyException extends AdempiereException
{
	private static final long serialVersionUID = -3485523266695546853L;

	public MissingPropertyException(
			@NonNull final String propertyName,
			@Nullable final Object objectWithMissingProperty)
	{
		super(assumeNotEmpty(propertyName, "Parameter 'propertyName may not be null"));

		appendParametersToMessage();
		setParameter("objectWithMissingProperty", objectWithMissingProperty);
	}
}