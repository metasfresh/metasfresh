/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoice.request;

import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.VatCodeId;
import de.metas.uom.UomId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;

@Value
@Builder
public class CreateInvoiceRequestLine
{
	@NonNull
	String externalLineId;

	@NonNull
	ProductId productId;

	@NonNull
	Quantity qtyToInvoice;
	
	@Nullable
	Integer line;

	@Nullable
	String lineDescription;

	@Nullable
	@Getter(AccessLevel.PACKAGE)
	BigDecimal priceEntered;

	@Nullable
	UomId priceUomId;

	@Nullable
	VatCodeId vatCodeId;

	@Nullable
	ElementValueId elementValueId;

	@Nullable
	ProductAcctType productAcctType;

	@Nullable
	Map<String, Object> extendedProps;
}
