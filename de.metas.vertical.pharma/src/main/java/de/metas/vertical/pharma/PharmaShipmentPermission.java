package de.metas.vertical.pharma;

import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.GuavaCollectors;
import de.metas.vertical.pharma.model.I_C_BPartner;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
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

/**
 * A Shipment permission means that
 * I, as a vendor can SHIP TO a particular customer.
 *
 * So Shipment Permission is for a BPartner who is a customer
 * and
 * Receipt Permission is for a BPartner who is a vendor
 *
 * This enum is related to PharmaReceiptPermission
 */
public enum PharmaShipmentPermission
{
	TYPE_A(I_C_BPartner.ShipmentPermissionPharma_TypeA), //
	TYPE_B(I_C_BPartner.ShipmentPermissionPharma_TypeB), //
	TYPE_C(I_C_BPartner.ShipmentPermissionPharma_TypeC);

	@Getter
	private final String code;

	PharmaShipmentPermission(final String code)
	{
		this.code = code;
	}

	public static PharmaShipmentPermission forCode(@NonNull final String code)
	{
		final PharmaShipmentPermission type = code2type.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PharmaShipmentPermission.class + " found for code: " + code);
		}
		return type;
	}

	private static final Map<String, PharmaShipmentPermission> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(PharmaShipmentPermission::getCode));

}
