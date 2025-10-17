/*
 * #%L
 * de.metas.shipper.client.nshift
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.client.nshift;

import de.metas.common.util.Check;
import de.metas.shipper.client.nshift.json.JsonAddress;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class NShiftUtil
{
	public static JsonAddress.JsonAddressBuilder buildNShiftAddressBuilder(@NonNull final de.metas.common.delivery.v1.json.JsonAddress commonAddress, @NonNull final JsonAddressKind kind)
	{
		String street1 = commonAddress.getStreet();
		if (Check.isNotBlank(commonAddress.getHouseNo()))
		{
			street1 = street1 + " " + commonAddress.getHouseNo();
		}

		return JsonAddress.builder()
				.kind(kind)
				.name1(commonAddress.getCompanyName1())
				.name2(commonAddress.getCompanyName2())

				.street1(street1)
				.street2(commonAddress.getAdditionalAddressInfo())
				.postCode(commonAddress.getZipCode())
				.city(commonAddress.getCity())
				.countryCode(commonAddress.getCountry());
	}

	public static JsonAddress.JsonAddressBuilder buildNShiftReceiverAddress(
			@NonNull final de.metas.common.delivery.v1.json.JsonAddress deliveryAddress,
			@Nullable final de.metas.common.delivery.v1.json.JsonContact deliveryContact)
	{

		final JsonAddress.JsonAddressBuilder receiverAddressBuilder = buildNShiftAddressBuilder(deliveryAddress, JsonAddressKind.RECEIVER);

		if (deliveryContact != null)
		{
			if (Check.isNotBlank(deliveryContact.getPhone()))
			{
				receiverAddressBuilder.phone(deliveryContact.getPhone());
			}
			if (Check.isNotBlank(deliveryContact.getEmailAddress()))
			{
				receiverAddressBuilder.email(deliveryContact.getEmailAddress());
			}
		}
		return receiverAddressBuilder;
	}

}
