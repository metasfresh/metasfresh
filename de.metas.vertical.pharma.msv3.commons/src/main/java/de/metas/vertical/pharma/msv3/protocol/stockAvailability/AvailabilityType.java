package de.metas.vertical.pharma.msv3.protocol.stockAvailability;

import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitTyp;
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

public enum AvailabilityType
{
	SPECIFIC(VerfuegbarkeitTyp.SPEZIFISCH), //
	NON_SPECIFIC(VerfuegbarkeitTyp.UNSPEZIFISCH) //
	;

	@Getter
	private VerfuegbarkeitTyp soapCode;

	private AvailabilityType(final VerfuegbarkeitTyp soapCode)
	{
		this.soapCode = soapCode;
	}

}
