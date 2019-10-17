package de.metas.vertical.pharma;

import de.metas.vertical.pharma.model.I_C_BPartner;
import lombok.Getter;

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

public enum PharmaCustomerPermission
{
	PHARMA_AGENT(I_C_BPartner.COLUMNNAME_IsPharmaAgentPermission), //
	PHARMACIE(I_C_BPartner.COLUMNNAME_IsPharmaciePermission), //
	PHARMA_MANUFACTURER(I_C_BPartner.COLUMNNAME_IsPharmaManufacturerPermission), //
	PHARMA_WHOLESALE(I_C_BPartner.COLUMNNAME_IsPharmaWholesalePermission), //
	VETERINARY_PHARMACY(I_C_BPartner.COLUMNNAME_IsVeterinaryPharmacyPermission), //
	PHARMA_NARCOTICS(I_C_BPartner.COLUMNNAME_IsPharmaCustomerNarcoticsPermission) //
	;

	@Getter
	private final String displayNameAdMessage;

	PharmaCustomerPermission(final String displayNameAdMessage)
	{
		this.displayNameAdMessage = displayNameAdMessage;
	}
}
