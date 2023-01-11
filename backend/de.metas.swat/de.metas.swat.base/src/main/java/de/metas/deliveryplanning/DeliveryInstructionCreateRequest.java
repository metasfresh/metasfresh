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

package de.metas.deliveryplanning;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.incoterms.IncotermsId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class DeliveryInstructionCreateRequest
{
	@NonNull OrgId orgId;

	@NonNull BPartnerId shipperBPartnerId;

	@NonNull BPartnerLocationId shipperLocationId;

	@Nullable
	IncotermsId incotermsId;

	@Nullable String incotermLocation;

	@Nullable
	String loadingTime;

	@Nullable
	String deliveryTime;

	@Nullable
	ForwarderId forwarderId;


	@Nullable
	MeansOfTransportationId meansOfTransportationId;

	boolean processed;
}
