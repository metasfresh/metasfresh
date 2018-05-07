package de.metas.shipper.gateway.derkurier.misc;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import de.metas.email.Mailbox;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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

@Value
public class DerKurierShipperConfig
{
	String restApiBaseUrl;

	String customerNumber;

	ParcelNumberGenerator parcelNumberGenerator;

	Mailbox deliveryOrderMailBoxOrNull;

	String deliveryOrderRecipientEmailOrNull;

	int parcelNumberAdSequenceId;

	@Builder
	private DerKurierShipperConfig(
			@NonNull final String restApiBaseUrl,
			@NonNull final String customerNumber,
			@Nullable final Mailbox deliveryOrderMailBoxOrNull,
			@Nullable String deliveryOrderRecipientEmailOrNull,
			final int parcelNumberAdSequenceId)
	{
		this.customerNumber = Check.assumeNotEmpty(customerNumber, "Parameter customerNumber is not empty");
		this.restApiBaseUrl = Check.assumeNotEmpty(restApiBaseUrl, "Parameter restApiBaseUrl is not empty");

		final boolean mailBoxIsSet = deliveryOrderMailBoxOrNull != null;
		final boolean mailAddressIsSet = !Check.isEmpty(deliveryOrderRecipientEmailOrNull, true);
		Check.errorIf(mailBoxIsSet != mailAddressIsSet, "If a mailbox is configured, then also a mail address needs to be set and vice versa.");
		this.deliveryOrderRecipientEmailOrNull = deliveryOrderRecipientEmailOrNull;
		this.deliveryOrderMailBoxOrNull = deliveryOrderMailBoxOrNull;

		Check.assume(parcelNumberAdSequenceId > 0 || parcelNumberAdSequenceId == ParcelNumberGenerator.NO_AD_SEQUENCE_ID_FOR_TESTING,
				"Parameter parcelNumberAdSequenceId is > 0");
		this.parcelNumberAdSequenceId = parcelNumberAdSequenceId;
		this.parcelNumberGenerator = new ParcelNumberGenerator(parcelNumberAdSequenceId);
	}
}
