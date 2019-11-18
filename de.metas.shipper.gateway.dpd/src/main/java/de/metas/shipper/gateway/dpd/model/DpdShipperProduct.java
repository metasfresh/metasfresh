/*
 * #%L
 * de.metas.shipper.gateway.dpd
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

package de.metas.shipper.gateway.dpd.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.shipper.gateway.spi.model.ShipperProduct;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

/**
 * as of 2019.10.29 there's no way for the customer to change the service type!
 * It is hardcoded inside CreateDraftDeliveryOrder, and that's it.
 */
public enum DpdShipperProduct implements ShipperProduct
{

	DPD_CLASSIC("CL"),
	DPD_E830("E830"),
	DPD_E10("E10"),
	DPD_E12("E12"),
	DPD_E18("E18"),
	DPD_EXPRESS("IE2"),
	DPD_PARCELLetter("PL"),
	DPD_PARCELLetterPlus("PL+"),
	DPD_International_Mail("MAIL");

	@Getter
	private final String code;

	DpdShipperProduct(final String code)
	{
		this.code = code;
	}

	@NonNull
	public static DpdShipperProduct ofCode(final String code)
	{
		final DpdShipperProduct type = code2type.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + DpdShipperProduct.class + " for code=" + code + " exists.");
		}
		return type;
	}

	private static final ImmutableMap<String, DpdShipperProduct> code2type = Maps.uniqueIndex(Arrays.asList(values()), DpdShipperProduct::getCode);
}
