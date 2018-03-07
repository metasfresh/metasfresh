/**
 *
 */
package org.adempiere.impexp.product;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_PriceList;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Builder
@Getter
public class ProductPriceRequest
{
	private final org.compiere.model.I_M_Product product;
	private final I_M_PriceList priceList;
	@NonNull
	private final BigDecimal price;
	final Timestamp validDate;
	@NonNull
	final I_C_TaxCategory taxCategory;
}