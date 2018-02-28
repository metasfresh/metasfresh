package de.metas.ordercandidate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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
@Builder
public class OLCandCreateRequest
{
	private OLCandBPartnerInfo bpartner;
	private OLCandBPartnerInfo billBPartner;
	private OLCandBPartnerInfo dropShipBPartner;
	private OLCandBPartnerInfo handOverBPartner;

	private LocalDate dateRequired;
	private int flatrateConditionsId;

	private int productId;
	private String productDescription;
	private BigDecimal qty;
	private int uomId;
	private int huPIItemProductId;

	private int pricingSystemId;
	private BigDecimal price;
	private BigDecimal discount;
	// private String currencyCode; // shall come from pricingSystem/priceList
}
