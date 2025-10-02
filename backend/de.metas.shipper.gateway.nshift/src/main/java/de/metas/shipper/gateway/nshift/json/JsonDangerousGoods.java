/*
 * #%L
 * de.metas.shipper.gateway.nshift
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
package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder
public class JsonDangerousGoods
{

	@JsonProperty("AdditionalHandlingInformation")
	String additionalHandlingInformation;
	@JsonProperty("ADRtankSpecialProvisions")
	String adrTankSpecialProvisions;
	@JsonProperty("ADRtankTankCode")
	String adrTankTankCode;
	@JsonProperty("ArticleNo")
	String articleNo;
	@JsonProperty("ArticleName")
	String articleName;
	@JsonProperty("AutoSummarizeMass")
	String autoSummarizeMass;
	@JsonProperty("Class")
	JsonDGClass dgClass;
	@JsonProperty("Classification")
	String classification;
	@JsonProperty("ClassName")
	String className;
	@JsonProperty("Count")
	Integer count;
	@JsonProperty("DeclarantName")
	String declarantName;
	@JsonProperty("Description")
	String description;
	@JsonProperty("DGMId")
	String dgmId;
	@JsonProperty("EMSNo")
	String emsNo;
	@JsonProperty("EMSProc")
	String emsProc;
	@JsonProperty("ExceptedQuantities")
	String exceptedQuantities;
	@JsonProperty("FlashPoint")
	String flashPoint;
	@JsonProperty("GoodsLineID")
	Integer goodsLineID;
	@JsonProperty("GoodsLineNo")
	Integer goodsLineNo;
	@JsonProperty("GrossWeight")
	Integer grossWeight;
	@JsonProperty("IdentificationNo")
	String identificationNo;
	@JsonProperty("ItemGrossWeight")
	Long itemGrossWeight;
	@JsonProperty("ItemNetWeight")
	Long itemNetWeight;
	@JsonProperty("Kind")
	JsonDGKind kind;
	@JsonProperty("KindName")
	String kindName;
	@JsonProperty("KitAmount")
	Integer kitAmount;
	@JsonProperty("Labels")
	List<JsonLabel> labels;
	@JsonProperty("LimitedQuantities")
	String limitedQuantities;
	@JsonProperty("LQCount")
	Integer lqCount;
	@JsonProperty("LQGrossWeight")
	Integer lqGrossWeight;
	@JsonProperty("MarinePollutant")
	JsonMarinePollutant marinePollutant;
	@JsonProperty("MfagNo")
	String mfagNo;
	@JsonProperty("MixedPackingProvision")
	String mixedPackingProvision;
	@JsonProperty("Name")
	String name;
	@JsonProperty("NameRoad")
	String nameRoad;
	@JsonProperty("NameSea")
	String nameSea;
	@JsonProperty("NEQ")
	BigDecimal neq;
	@JsonProperty("NetWeight")
	Integer netWeight;
	@JsonProperty("NOS")
	String nos;
	@JsonProperty("OuterPackingAmount")
	Integer outerPackingAmount;
	@JsonProperty("OuterPackingBundleID")
	Integer outerPackingBundleID;
	@JsonProperty("OuterPackingType")
	String outerPackingType;
	@JsonProperty("OuterPackingTypeID")
	String outerPackingTypeID;
	@JsonProperty("PackingGroup")
	JsonDGPackingGroup packingGroup;
	@JsonProperty("PackingGroupName")
	String packingGroupName;
	@JsonProperty("PackingInstructions")
	String packingInstructions;
	@JsonProperty("PackingTypeID")
	Integer packingTypeID;
	@JsonProperty("PackingTypeKey")
	String packingTypeKey;
	@JsonProperty("PackingTypeName")
	String packingTypeName;
	@JsonProperty("PageNo")
	String pageNo;
	@JsonProperty("Point")
	BigDecimal point;
	@JsonProperty("PortTankBulkContInstructions")
	String portTankBulkContInstructions;
	@JsonProperty("PortTankBulkContSpecialProvisions")
	String portTankBulkContSpecialProvisions;
	@JsonProperty("SecondaryClasses")
	String secondaryClasses;
	@JsonProperty("ShipperReference")
	String shipperReference;
	@JsonProperty("ShortName")
	String shortName;
	@JsonProperty("SpecialPackingProvision")
	String specialPackingProvision;
	@JsonProperty("SpecialProvisions")
	String specialProvisions;
	@JsonProperty("SpecialProvisionsBulk")
	String specialProvisionsBulk;
	@JsonProperty("SpecialProvisionsHandling")
	String specialProvisionsHandling;
	@JsonProperty("SpecialProvisionsOperation")
	String specialProvisionsOperation;
	@JsonProperty("SpecialProvisionsPackages")
	String specialProvisionsPackages;
	@JsonProperty("StowingCategory")
	String stowingCategory;
	@JsonProperty("SubsidiaryRisk")
	String subsidiaryRisk;
	@JsonProperty("TARE")
	Integer tare;
	@JsonProperty("TotalGross")
	Integer totalGross;
	@JsonProperty("TransportCategory")
	String transportCategory;
	@JsonProperty("TransportInTank")
	String transportInTank;
	@JsonProperty("TransportMode")
	String transportMode;
	@JsonProperty("TransportModeName")
	String transportModeName;
	@JsonProperty("UN")
	Integer un;
	@JsonProperty("UnitOfMeasure")
	String unitOfMeasure;
	@JsonProperty("UnitOfMeasureName")
	String unitOfMeasureName;
	@JsonProperty("Vehicle")
	String vehicle;
	@JsonProperty("Vol")
	Integer vol;
}