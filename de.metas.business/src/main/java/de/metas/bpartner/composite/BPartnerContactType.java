package de.metas.bpartner.composite;

import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class BPartnerContactType
{
	@JsonInclude(Include.NON_ABSENT)
	Optional<Boolean> defaultContact;

	@JsonInclude(Include.NON_ABSENT)
	Optional<Boolean> billToDefault;

	@JsonInclude(Include.NON_ABSENT)
	Optional<Boolean> shipToDefault;

	@Builder
	public BPartnerContactType(
			@Nullable final Boolean defaultContact,
			@Nullable final Boolean billToDefault,
			@Nullable final Boolean shipToDefault)
	{
		this.defaultContact = Optional.ofNullable(defaultContact);
		this.billToDefault = Optional.ofNullable(billToDefault);
		this.shipToDefault = Optional.ofNullable(shipToDefault);
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getDefaultContactNotNull()
	{
		return defaultContact.orElseThrow(() -> createException("defaultContact may not be null"));
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getBillToDefaultNotNull()
	{
		return billToDefault.orElseThrow(() -> createException("billToDefault may not be null"));
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getShipToDefaultNotNull()
	{
		return shipToDefault.orElseThrow(() -> createException("shipToDefault may not be null"));
	}

	private AdempiereException createException(@NonNull final String message)
	{
		return new AdempiereException(message)
				.appendParametersToMessage()
				.setParameter("contactLocationType", this);
	}

}
