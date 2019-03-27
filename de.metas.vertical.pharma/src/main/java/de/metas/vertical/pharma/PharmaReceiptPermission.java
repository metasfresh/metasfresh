package de.metas.vertical.pharma;

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

import de.metas.util.GuavaCollectors;
import de.metas.vertical.pharma.model.I_C_BPartner;
import lombok.Getter;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;
import java.util.Map;

/**
 * A Receipt permission means that
 * I, as a customer(receiver) can RECEIVE from some particular vendor.
 *
 * So Receipt Permission is for a BPartner who is a vendor
 * and
 * Shipment Permission is for a BPartner who is a customer
 *
 * This enum is related to PharmaShipmentPermission
 */
public enum PharmaReceiptPermission
{
	TYPE_A(I_C_BPartner.ReceiptPermissionPharma_TypeA),
	TYPE_B(I_C_BPartner.ReceiptPermissionPharma_TypeB),
	TYPE_C(I_C_BPartner.ReceiptPermissionPharma_TypeC);

	@Getter
	private final String code;

	PharmaReceiptPermission(final String code)
	{
		this.code = code;
	}

	public static PharmaReceiptPermission forCode(final String code)
	{
		final PharmaReceiptPermission type = code2type.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PharmaReceiptPermission.class + " found for code: " + code);
		}
		return type;
	}

	private static final Map<String, PharmaReceiptPermission> code2type = Arrays.stream(values())
			.collect(GuavaCollectors.toImmutableMapByKey(PharmaReceiptPermission::getCode));
}
