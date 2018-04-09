package de.metas.ordercandidate.rest;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Data
@Builder
public final class JsonOLCandCreateRequest
{
	private JsonBPartnerInfo bpartner;
	private JsonBPartnerInfo billBPartner;
	private JsonBPartnerInfo dropShipBPartner;
	private JsonBPartnerInfo handOverBPartner;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateRequired;
	private int flatrateConditionsId;

	private String productCode;
	private String productDescription;
	private BigDecimal qty;
	private int uomId;
	private int huPIItemProductId;

	private int pricingSystemId;
	private BigDecimal price;
	private BigDecimal discount;
	// private String currencyCode; // shall come from pricingSystem/priceList

	private String poReference;
	
	private String externalId;
}
