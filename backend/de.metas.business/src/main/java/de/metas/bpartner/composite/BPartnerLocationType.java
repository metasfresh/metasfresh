package de.metas.bpartner.composite;

import java.util.Optional;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

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

@Data
@JsonPropertyOrder(alphabetic = true/* we want the serialized json to be less flaky in our snapshot files */)
public class BPartnerLocationType
{
	public static final String BILL_TO = "billTo";
	public static final String BILL_TO_DEFAULT = "billToDefault";
	public static final String SHIP_TO = "shipTo";
	public static final String SHIP_TO_DEFAULT = "shipToDefault";

	@JsonInclude(Include.NON_ABSENT)
	private final Optional<Boolean> billTo;

	@JsonInclude(Include.NON_ABSENT)
	private Optional<Boolean> billToDefault;

	@JsonInclude(Include.NON_ABSENT)
	private final Optional<Boolean> shipTo;

	@JsonInclude(Include.NON_ABSENT)
	private Optional<Boolean> shipToDefault;

	@Builder
	public BPartnerLocationType(
			@Nullable final Boolean billTo,
			@Nullable final Boolean billToDefault,
			@Nullable final Boolean shipTo,
			@Nullable final Boolean shipToDefault)
	{
		this.billToDefault = Optional.ofNullable(billToDefault);
		if (this.billToDefault.orElse(false) && billTo == null)
		{
			this.billTo = Optional.of(true); // billToDefault always implies billTo
		}
		else
		{
			this.billTo = Optional.ofNullable(billTo);
		}

		this.shipToDefault = Optional.ofNullable(shipToDefault);
		if (this.shipToDefault.orElse(false) && shipTo == null)
		{
			this.shipTo = Optional.of(true); // shipToDefault always implies shipTo
		}
		else
		{
			this.shipTo = Optional.ofNullable(shipTo);
		}
	}

	/** copy constructor, see {@link #deepCopy()}. */
	private BPartnerLocationType(
			@Nullable final Optional<Boolean> billTo,
			@Nullable final Optional<Boolean> billToDefault,
			@Nullable final Optional<Boolean> shipTo,
			@Nullable final Optional<Boolean> shipToDefault)
	{
		this.billTo = billTo;
		this.billToDefault = billToDefault;
		this.shipTo = shipTo;
		this.shipToDefault = shipToDefault;
	}

	public BPartnerLocationType deepCopy()
	{
		return new BPartnerLocationType(billTo, billToDefault, shipTo, shipToDefault);
	}

	public boolean getIsShipToOr(final boolean defaultValue)
	{
		return shipTo.orElse(defaultValue);
	}

	public boolean getIsShipToDefaultOr(final boolean defaultValue)
	{
		return shipToDefault.orElse(defaultValue);
	}

	public boolean getIsBillToOr(final boolean defaultValue)
	{
		return billTo.orElse(defaultValue);
	}

	public boolean getIsBillToDefaultOr(final boolean defaultValue)
	{
		return billToDefault.orElse(defaultValue);
	}

	public void setBillToDefault(boolean billToDefault)
	{
		this.billToDefault = Optional.of(billToDefault);
	}

	public void setShipToDefault(boolean shipToDefault)
	{
		this.shipToDefault = Optional.of(shipToDefault);
	}
}
