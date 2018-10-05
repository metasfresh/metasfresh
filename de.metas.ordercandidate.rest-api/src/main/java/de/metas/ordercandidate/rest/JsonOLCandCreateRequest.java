package de.metas.ordercandidate.rest;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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

/**
 * Specifies a single order line candidate for be created by the system
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Data
@Builder(toBuilder = true)
public final class JsonOLCandCreateRequest
{
	private JsonOrganization org;

	@NonNull
	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "Internal name of the {@code AD_InputDataSource} record that tells where this OLCand came from.")
	private String dataSourceInternalName; // TODO new property; make sure it's put through

	@Nullable
	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "Internal name of the {@code AD_InputDataSource} record that tells what shall be happen with this OLCand.")
	private String dataDestInternalName; // TODO new property; make sure it's put through

	private JsonBPartnerInfo bpartner;
	private JsonBPartnerInfo billBPartner;
	private JsonBPartnerInfo dropShipBPartner;
	private JsonBPartnerInfo handOverBPartner;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "This translates to {@code C_OLCand.datePromised}.")
	private LocalDate dateRequired;

	private int flatrateConditionsId;

	private JsonProductInfo product;

	@Nullable
	private String productDescription;

	private BigDecimal qty;

	@Nullable
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = " This translates to {@code C_UOM.X12DE355}.\n"
					+ "The respective UOM needs to exist in metasfresh and it's ID is set as {@code C_OLCand.C_UOM_ID}.\n"
					+ "Note that if this is set, then there also needs to exist a UOM-conversion rule between this UOM and the {@link #product}'s UOM")
	private String uomCode;

	private int packingMaterialId;

	private String pricingSystemCode;

	@Nullable
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "If set, then the order line candidate will be created with a manual (i.e. not coming from metasfresh) price.")
	private BigDecimal price;

	@Nullable
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "If set, then the order line candidate will be created with a manual (i.e. not coming from metasfresh) discount.")
	private BigDecimal discount;
	// private String currencyCode; // shall come from pricingSystem/priceList

	@NonNull
	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "External reference (document number) on a remote system. Unique in conjunction with <code>dataSourceInternalName</code>.")
	private String poReference;

	@Nullable
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "Can be set if the invoice's document date is already know from the external system and shall be forwarded to the invoice candidate.\n"
					+ "This works only if not an order line but an invoice candidate is directly created for the respective order line candidate.\n"
					+ "Therefore, please make sure to get the {@link #adInputDataSourceInternalName} right.")
	private LocalDate dateInvoiced; // TODO new property; make sure it's put through
}
