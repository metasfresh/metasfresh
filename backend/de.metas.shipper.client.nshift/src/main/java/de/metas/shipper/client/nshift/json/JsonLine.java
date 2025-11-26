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
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder
@Jacksonized
public class JsonLine
{

	@JsonProperty("Number")
	int number;

	@JsonProperty("LineWeight")
	Integer lineWeight;

	@JsonProperty("PkgWeight")
	Integer pkgWeight;

	@JsonProperty("DimensionalWeight")
	Integer dimensionalWeight;

	@JsonProperty("Height")
	Integer height;

	@JsonProperty("Length")
	Integer length;

	@JsonProperty("Width")
	Integer width;

	@JsonProperty("LineVol")
	Long lineVol;

	@JsonProperty("PkgVol")
	Long pkgVol;

	@JsonProperty("Loadmeter")
	Integer loadmeter;

	@JsonProperty("GoodsType")
	Integer goodsTypeID;

	@JsonProperty("GoodsTypeName")
	String goodsTypeName;

	@JsonProperty("GoodsTypeKey1")
	String goodsTypeKey1;

	@JsonProperty("GoodsTypeKey2")
	String goodsTypeKey2;

	@JsonProperty("RecycleCount")
	Integer recycleCount;

	@JsonProperty("RecycleTypeID")
	Integer recycleTypeID;

	@JsonProperty("RecycleTypeName")
	String recycleTypeName;

	@JsonProperty("LineUnits")
	List<JsonLineUnit> lineUnits;

	@JsonProperty("Pkgs")
	@Singular
	List<JsonPackage> pkgs;

	@JsonProperty("References")
	@Singular
	List<JsonReference> references;
}
