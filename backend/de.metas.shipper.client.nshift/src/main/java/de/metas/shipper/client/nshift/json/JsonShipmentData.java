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
package de.metas.shipper.client.nshift.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder
public class JsonShipmentData
{

	@JsonProperty("OverrideShpNo")
	String overrideShpNo;

	@JsonProperty("OrderNo")
	String orderNo;

	@JsonProperty("PickupDt")
	LocalDate pickupDt;

	@JsonProperty("ExpireDt")
	LocalDate expireDt;

	@JsonProperty("ActorCSID")
	Integer actorCSID;

	@JsonProperty("StackCSID")
	Integer stackCSID;

	@JsonProperty("ProdConceptID")
	int prodConceptID;

	@JsonProperty("ProdCSID")
	Integer prodCSID;

	@JsonProperty("PickupTerminal")
	String pickupTerminal;

	@JsonProperty("AgentNo")
	String agentNo;

	@JsonProperty("PayerAccountAtCarrier")
	String payerAccountAtCarrier;

	@JsonProperty("SenderAccountAtCarrier")
	String senderAccountAtCarrier;

	@JsonProperty("SenderAccountAtBank")
	String senderAccountAtBank;

	@JsonProperty("Services")
	@Singular
	List<Integer> services;

	@JsonProperty("Addresses")
	@Singular
	List<JsonAddress> addresses;

	@JsonProperty("Lines")
	@Singular
	List<JsonLine> lines;

	@JsonProperty("References")
	@Singular
	List<JsonShipmentReference> references;

	@JsonProperty("DetailGroups")
	@Singular
	List<JsonDetailGroup> detailGroups;

	@JsonProperty("Amounts")
	List<JsonAmount> amounts;

	@JsonProperty("Messages")
	List<JsonShipmentMessage> messages;

	@JsonProperty("DangerousGoods")
	@Singular
	List<JsonDangerousGoods> dangerousGoods;
}
