/*
 * #%L
 * de-metas-common-ordercandidates
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.ordercandidates.v2.request.alberta;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
@Builder
@JsonDeserialize(builder = JsonAlbertaOrderInfo.JsonAlbertaOrderInfoBuilder.class)
public class JsonAlbertaOrderInfo
{
	@JsonProperty("externalId")
	@NonNull
	String externalId;

	@JsonProperty("rootId")
	@Nullable
	String rootId;

	@JsonProperty("creationDate")
	@Nullable
	Instant creationDate;

	@JsonProperty("startDate")
	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate startDate;

	@JsonProperty("endDate")
	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate endDate;

	@JsonProperty("dayOfDelivery")
	@Nullable
	BigDecimal dayOfDelivery;

	@JsonProperty("nextDelivery")
	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate nextDelivery;

	@JsonProperty("doctorBPartnerIdentifier")
	@Nullable
	String doctorBPartnerIdentifier;

	@JsonProperty("pharmacyBPartnerIdentifier")
	@Nullable
	String pharmacyBPartnerIdentifier;

	@JsonProperty("isInitialCare")
	@Nullable
	Boolean isInitialCare;

	@JsonProperty("isSeriesOrder")
	@Nullable
	Boolean isSeriesOrder;

	@JsonProperty("isArchived")
	@Nullable
	Boolean isArchived;

	@JsonProperty("annotation")
	@Nullable
	String annotation;

	@JsonProperty("updated")
	@Nullable
	Instant updated;

	@JsonProperty("therapy")
	@Nullable
	String therapy;

	@JsonProperty("therapyTypes")
	@Nullable
	List<String> therapyTypes;

	@JsonProperty("deliveryInformation")
	@Nullable
	String deliveryInformation;

	@JsonProperty("deliveryNote")
	@Nullable
	String deliveryNote;

	@JsonProperty("orderLineInfo")
	@Nullable
	JsonAlbertaOrderLineInfo jsonAlbertaOrderLineInfo;
}
