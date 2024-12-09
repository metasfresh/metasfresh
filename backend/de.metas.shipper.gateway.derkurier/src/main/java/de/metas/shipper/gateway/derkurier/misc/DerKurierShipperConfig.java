package de.metas.shipper.gateway.derkurier.misc;

<<<<<<< HEAD
import java.time.LocalTime;

import javax.annotation.Nullable;

import de.metas.email.EMailAddress;
import de.metas.email.mailboxes.Mailbox;
=======
import de.metas.email.EMailAddress;
import de.metas.email.mailboxes.MailboxId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

<<<<<<< HEAD
=======
import javax.annotation.Nullable;
import java.time.LocalTime;
import java.util.Optional;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
=======
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
public class DerKurierShipperConfig
{
	String restApiBaseUrl;

	String customerNumber;

	ParcelNumberGenerator parcelNumberGenerator;

<<<<<<< HEAD
	Mailbox deliveryOrderMailBoxOrNull;
=======
	Optional<MailboxId> deliveryOrderMailBoxId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	EMailAddress deliveryOrderRecipientEmailOrNull;

	int parcelNumberAdSequenceId;

	String collectorCode;
	String customerCode;

	LocalTime desiredTimeFrom;
	LocalTime desiredTimeTo;

	@Builder
	private DerKurierShipperConfig(
			@NonNull final String restApiBaseUrl,
			@NonNull final String customerNumber,
<<<<<<< HEAD
			@Nullable final Mailbox deliveryOrderMailBoxOrNull,
=======
			@Nullable final MailboxId deliveryOrderMailBoxId,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			@Nullable final EMailAddress deliveryOrderRecipientEmailOrNull,
			@NonNull String collectorCode,
			@NonNull String customerCode,
			final int parcelNumberAdSequenceId,
			@NonNull LocalTime desiredTimeFrom,
			@NonNull LocalTime desiredTimeTo)
	{
		this.customerNumber = Check.assumeNotEmpty(customerNumber, "Parameter customerNumber is not empty");
		this.restApiBaseUrl = Check.assumeNotEmpty(restApiBaseUrl, "Parameter restApiBaseUrl is not empty");

<<<<<<< HEAD
		final boolean mailBoxIsSet = deliveryOrderMailBoxOrNull != null;
		final boolean mailAddressIsSet = deliveryOrderRecipientEmailOrNull != null;
		Check.errorIf(mailBoxIsSet != mailAddressIsSet, "If a mailbox is configured, then also a mail address needs to be set and vice versa.");
		this.deliveryOrderRecipientEmailOrNull = deliveryOrderRecipientEmailOrNull;
		this.deliveryOrderMailBoxOrNull = deliveryOrderMailBoxOrNull;
=======
		final boolean mailBoxIsSet = deliveryOrderMailBoxId != null;
		final boolean mailAddressIsSet = deliveryOrderRecipientEmailOrNull != null;
		Check.errorIf(mailBoxIsSet != mailAddressIsSet, "If a mailbox is configured, then also a mail address needs to be set and vice versa.");
		this.deliveryOrderRecipientEmailOrNull = deliveryOrderRecipientEmailOrNull;
		this.deliveryOrderMailBoxId = Optional.ofNullable(deliveryOrderMailBoxId);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		Check.assume(parcelNumberAdSequenceId > 0 || parcelNumberAdSequenceId == ParcelNumberGenerator.NO_AD_SEQUENCE_ID_FOR_TESTING,
				"Parameter parcelNumberAdSequenceId is > 0");
		this.parcelNumberAdSequenceId = parcelNumberAdSequenceId;
		this.parcelNumberGenerator = new ParcelNumberGenerator(parcelNumberAdSequenceId);

		this.collectorCode = collectorCode;
		this.customerCode = customerCode;
		
		this.desiredTimeFrom = desiredTimeFrom;
		this.desiredTimeTo = desiredTimeTo;
	}
}
