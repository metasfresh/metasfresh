/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl.model;

import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder
@ToString(of = { "baseUrl" })
public class DhlClientConfig
{


	@NonNull
	String baseUrl;

	@NonNull
	String applicationID; // CIG Auth (https://entwickler.dhl.de/en/group/ep/authentifizierung)

	@NonNull
	String applicationToken; // CIG Auth (https://entwickler.dhl.de/en/group/ep/authentifizierung)

	@Nullable
	String accountNumber; // DHL Business Customer Portal (also presented as EKP); billing number of the national product

	@Nullable
	String accountNumberInternational; // billing number of the international product

	@NonNull
	String username; // DHL Business Customer Portal

	@NonNull
	String signature; // DHL Business Customer Portal

	@NonNull
	UomId lengthUomId;

	@NonNull
	String trackingUrlBase;

	@Nullable
	public String getBillingNumber(@NonNull final DhlShipperProduct product)
	{
		switch (product)
		{
			case Dhl_Paket:
				return accountNumber;
			case Dhl_PaketInternational:
				return accountNumberInternational;
			default:
				throw new AdempiereException("Unsupported DHL product: " + product);
		}
	}

}
