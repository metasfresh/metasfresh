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

package de.metas.tax.api;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
public class TaxQuery
{
	@Nullable
	OrgId orgId;

	@Nullable
	WarehouseId warehouseId;

	@NonNull
	Instant dateOfInterest;

	@NonNull
	BPartnerLocationId bPartnerLocationId;

	Boolean isSoTrx;

	@Nullable
	TaxCategoryId taxCategoryId;

	@Builder
	public TaxQuery(@Nullable final OrgId orgId,
			@Nullable final WarehouseId warehouseId,
			final @NonNull Instant dateOfInterest,
			final @NonNull BPartnerLocationId bPartnerLocationId,
			final Boolean isSoTrx,
			@Nullable final TaxCategoryId taxCategoryId)
	{
		this.orgId = orgId;
		this.warehouseId = warehouseId;
		this.dateOfInterest = dateOfInterest;

		this.bPartnerLocationId = bPartnerLocationId;
		this.isSoTrx = isSoTrx;
		this.taxCategoryId = taxCategoryId;

		validate();
	}

	private void validate()
	{
		if (orgId == null && warehouseId == null)
		{
			throw new AdempiereException("At least one of the given orgId or warehouseId needs to be non-empty: " + this);
		}
	}
}
