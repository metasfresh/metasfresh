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

package de.metas.invoice;

import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class ManualInvoiceLine
{
	@NonNull
	InvoiceAndLineId id;

	@NonNull
	BigDecimal priceEntered;

	@NonNull  
	BigDecimal qtyInvoiced;

	@NonNull
	TaxId taxId;

	@Nullable
	UomId priceUomId;

	@Nullable
	ProductId productId;

	@Nullable
	UomId uomId;

	@Nullable
	String externalLineId;

	@Nullable
	Integer line;

	@Nullable
	String lineDescription;
	
	boolean isManualPrice;

	boolean isActive;
}
