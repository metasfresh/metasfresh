package de.metas.purchasecandidate;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.bpartner.BPartnerId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PurchaseCandidateReminder
{
	@JsonProperty("vendorBPartnerId")
	BPartnerId vendorBPartnerId;
	@JsonProperty("notificationTime")
	ZonedDateTime notificationTime;

	@Builder
	@JsonCreator
	private PurchaseCandidateReminder(
			@JsonProperty("vendorBPartnerId") @NonNull final BPartnerId vendorBPartnerId,
			@JsonProperty("notificationTime") @NonNull final ZonedDateTime notificationTime)
	{
		this.vendorBPartnerId = vendorBPartnerId;
		this.notificationTime = notificationTime;
	}
}
