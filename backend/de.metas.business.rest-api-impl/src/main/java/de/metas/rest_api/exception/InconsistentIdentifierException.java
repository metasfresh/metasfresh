package de.metas.rest_api.exception;

import static de.metas.util.Check.assumeNotEmpty;
import org.adempiere.exceptions.AdempiereException;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/** Thrown if a request could not be processed, because one of the required resources (e.g. product, or business partner) does not exist in metasfresh. */
public class InconsistentIdentifierException extends AdempiereException
{
	private static final long serialVersionUID = -3485523266695546853L;

	/**
	 *
	 * @param resourceName name of the resource in terms of the respective endpoint. Example: {@code "billPartner"}.
	 * @param resourceIdentifier identifier with which the lookup was attempted. Example: {@code "ext-product123"}. Can be {@code null} if the missing resource had not to be explicitly specified, such as a default location.
	 * @param parentResource
	 * @param detail optional detail message. If not {@code null}, it is incorporated into the exception message.
	 * @param cause
	 */
	@Builder
	private InconsistentIdentifierException(
			@NonNull final String resourceName,
			@NonNull final String resourceIdentifier,
			@NonNull final String inconsitentPropertyName,
			@NonNull final String inconsitentPropertyValue)
	{
		super(buildMessage(
				resourceName,
				resourceIdentifier,
				inconsitentPropertyName,
				inconsitentPropertyValue));
		appendParametersToMessage();
	}

	private static ITranslatableString buildMessage(
			@NonNull final String resourceName,
			@NonNull final String resourceIdentifier,
			@NonNull final String inconsitentPropertyName,
			@NonNull final String inconsitentPropertyValue)
	{
		final TranslatableStringBuilder result = TranslatableStrings.builder();
		result.append(TranslatableStrings.constant("The resource with resourceName="
				+ assumeNotEmpty(resourceName, "Parameter 'resourceName' may not be empty")));

		result.append(" has a property " + inconsitentPropertyName + "=" + inconsitentPropertyValue + " that is inconsistent with its own identifier=" + resourceIdentifier);

		return result.build();
	}
}
