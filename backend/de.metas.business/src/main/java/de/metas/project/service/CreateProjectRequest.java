/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.project.service;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductId;
import de.metas.project.ProjectCategory;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class CreateProjectRequest
{
	@NonNull
	OrgId orgId;

	@NonNull
	ProjectCategory projectCategory;

	@NonNull
	BPartnerLocationId bpartnerAndLocationId;
	@Nullable
	BPartnerContactId contactId;

	@NonNull
	CurrencyId currencyId;
	@NonNull
	PriceListVersionId priceListVersionId;

	@NonNull
	WarehouseId warehouseId;

	@Singular
	@NonNull List<ProjectLine> lines;

	@Value
	@Builder
	public static class ProjectLine
	{
		@NonNull ProductId productId;
		@NonNull Quantity plannedQty;
	}
}
