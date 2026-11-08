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

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.uom.UomId;
import de.metas.util.Check;
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
	private static final AdMessageKey MSG_MissingBillingNumber = AdMessageKey.of("de.metas.shipper.gateway.dhl.MissingBillingNumber");


	int shipperConfigId; // DHL_Shipper_Config_ID, named in error messages

	@NonNull
	String shipperName; // name of the config's M_Shipper, named in error messages

	@NonNull
	String baseUrl;

	@NonNull
	String applicationID; // CIG Auth (https://entwickler.dhl.de/en/group/ep/authentifizierung)

	@NonNull
	String applicationToken; // CIG Auth (https://entwickler.dhl.de/en/group/ep/authentifizierung)

	@Nullable
	String accountNumber; // billing number ("Abrechnungsnummer") of the national product V01PAK: 10-digit EKP (customer number) + procedure 01 + 2-digit participation

	@Nullable
	String accountNumberInternational; // billing number of the international product V53WPAK: 10-digit EKP + procedure 53 + 2-digit participation

	@NonNull
	String username; // DHL Business Customer Portal

	@NonNull
	String signature; // DHL Business Customer Portal

	@NonNull
	UomId lengthUomId;

	@NonNull
	String trackingUrlBase;

	/**
	 * @throws AdempiereException if the configuration has no billing number for the given product
	 */
	@NonNull
	public String getBillingNumber(@NonNull final DhlShipperProduct product)
	{
		final String billingNumber;
		final String columnName;
		switch (product)
		{
			case Dhl_Paket:
				billingNumber = accountNumber;
				columnName = I_DHL_Shipper_Config.COLUMNNAME_AccountNumber;
				break;
			case Dhl_PaketInternational:
				billingNumber = accountNumberInternational;
				columnName = I_DHL_Shipper_Config.COLUMNNAME_AccountNumber_International;
				break;
			default:
				throw new AdempiereException("Unsupported DHL product: " + product);
		}

		if (Check.isBlank(billingNumber))
		{
			throw new AdempiereException(MSG_MissingBillingNumber, String.valueOf(shipperConfigId), shipperName, product.getCode(), TranslatableStrings.adElementOrMessage(columnName));
		}
		return billingNumber;
	}

}
