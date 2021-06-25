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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.Timestamp;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Value
public class TaxQuery
{
	@NonNull
	OrgId orgId;

	@Nullable
	WarehouseId warehouseId;

	@Nullable
	CountryId fromCountryId;

	@NonNull
	Timestamp dateOfInterest;

	@Nullable
	BPartnerId bpartnerId;

	@Nullable
	BPartnerLocationId bPartnerLocationId;

	@Nullable
	Boolean isSoTrx;

	@Nullable
	TaxCategoryId taxCategoryId;

	@Builder
	public TaxQuery(@NonNull final OrgId orgId,
			@Nullable final WarehouseId warehouseId,
			@Nullable final CountryId fromCountryId,
			@Nullable final Timestamp dateOfInterest,
			@Nullable final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bPartnerLocationId,
			@Nullable final Boolean isSoTrx,
			@Nullable final TaxCategoryId taxCategoryId)
	{
		this.orgId = orgId;
		this.warehouseId = warehouseId;
		this.fromCountryId = fromCountryId;
		this.dateOfInterest = coalesce(dateOfInterest, Env.getDate());
		this.bPartnerLocationId = bPartnerLocationId;
		if (bpartnerId == null && bPartnerLocationId != null)
		{
			this.bpartnerId = this.bPartnerLocationId.getBpartnerId();
		}
		else
		{
			this.bpartnerId = bpartnerId;
		}
		this.isSoTrx = isSoTrx;
		this.taxCategoryId = taxCategoryId;
	}
}
