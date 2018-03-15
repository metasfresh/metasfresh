package de.metas.vertical.pharma.msv3.server.stockAvailability;

import de.metas.vertical.pharma.vendor.gateway.msv3.schema.Substitutionsgrund;
import lombok.Getter;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

public enum StockAvailabilitySubstitutionType
{
	SUCCESSOR_PRODUCT(Substitutionsgrund.NACHFOLGEPRODUKT), //
	RE_AND_PARALLEL_IMPORT(Substitutionsgrund.RE_UND_PARALLEL_IMPORT), //
	PROPOSAL(Substitutionsgrund.VORSCHLAG) //
	;

	@Getter
	private final Substitutionsgrund soapCode;

	private StockAvailabilitySubstitutionType(final Substitutionsgrund soapCode)
	{
		this.soapCode = soapCode;
	}
}
