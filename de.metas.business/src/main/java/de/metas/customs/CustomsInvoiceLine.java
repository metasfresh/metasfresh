package de.metas.customs;

import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Data
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomsInvoiceLine
{
	@NonFinal
	CustomsInvoiceLineId id;

	@NonFinal
	@Setter(AccessLevel.PACKAGE)
	int lineNo;

	@NonNull
	ProductId productId;

	@NonNull
	Quantity quantity;

	@NonNull
	UomId uomId;

	@NonNull
	OrgId orgId;

	@NonNull
	Money lineNetAmt;

}
