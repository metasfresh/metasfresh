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

package de.metas.shipper.client.nshift.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.shipper.client.nshift.json.JsonAddress;
import de.metas.shipper.client.nshift.json.JsonAmount;
import de.metas.shipper.client.nshift.json.JsonLine;
import de.metas.shipper.client.nshift.json.JsonShipmentDocument;
import de.metas.shipper.client.nshift.json.JsonReference;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonShipmentResponse
{

	@JsonProperty("ShpCSID")
	Integer shpCSID;

	@JsonProperty("ShpTag")
	String shpTag;

	@JsonProperty("InstallationID")
	String installationID;

	@JsonProperty("PhysicalInstallationID")
	String physicalInstallationID;

	@JsonProperty("Kind")
	Integer kind;

	@JsonProperty("ShpNo")
	String shpNo;

	@JsonProperty("OrderNo")
	String orderNo;

	@JsonProperty("PickupDt")
	String pickupDt;

	@JsonProperty("LabelPrintDt")
	String labelPrintDt;

	@JsonProperty("SubmitDt")
	String submitDt;

	@JsonProperty("Vol")
	Long vol;

	@JsonProperty("Weight")
	Integer weight;

	@JsonProperty("Height")
	Integer height;

	@JsonProperty("Length")
	Integer length;

	@JsonProperty("Width")
	Integer width;

	@JsonProperty("ActorCSID")
	Integer actorCSID;

	@JsonProperty("Temperature")
	Integer temperature;

	@JsonProperty("CarriagePayer")
	Integer carriagePayer;

	@JsonProperty("CarrierConceptID")
	Integer carrierConceptID;

	@JsonProperty("CarrierCSID")
	Integer carrierCSID;

	@JsonProperty("SubcarrierConceptID")
	Integer subcarrierConceptID;

	@JsonProperty("SubcarrierCSID")
	Integer subcarrierCSID;

	@JsonProperty("ProdConceptID")
	Integer prodConceptID;

	@JsonProperty("ProdCSID")
	Integer prodCSID;

	@JsonProperty("StackCSID")
	Integer stackCSID;

	@JsonProperty("PickupTerminal")
	String pickupTerminal;

	@JsonProperty("AgentNo")
	String agentNo;

	@JsonProperty("PayerAccountAtCarrier")
	String payerAccountAtCarrier;

	@JsonProperty("SenderAccountAtCarrier")
	String senderAccountAtCarrier;

	@JsonProperty("Addresses")
	List<JsonAddress> addresses;

	@JsonProperty("Lines")
	List<JsonLine> lines;

	@JsonProperty("Labels")
	@Singular
	List<JsonShipmentResponseLabel> labels;

	@JsonProperty("ShpDocuments")
	List<JsonShipmentDocument> shpDocuments;

	@JsonProperty("CorrelationID")
	String correlationID;

	@JsonProperty("Amounts")
	List<JsonAmount> amounts;

	@JsonProperty("References")
	@Singular
	List<JsonReference> references;
}