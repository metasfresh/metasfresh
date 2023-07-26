/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.project.model;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Value
@Builder
public class ServiceRepairProjectInfo
{
	@NonNull ProjectId projectId;
	@NonNull ClientAndOrgId clientAndOrgId;

	@Nullable LocalDate dateContract;
	@Nullable ZonedDateTime dateFinish;

	@NonNull BPartnerId bpartnerId;
	@Nullable BPartnerLocationId bpartnerLocationId;
	@Nullable BPartnerContactId bpartnerContactId;

	@Nullable UserId salesRepId;

	@Nullable WarehouseId warehouseId;

	@Nullable PaymentTermId paymentTermId;
	@Nullable PriceListVersionId priceListVersionId;

	int campaignId;
}
