/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.config;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class MobileUIPickingUserProfile
{
	public static final MobileUIPickingUserProfile DEFAULT = builder().name("default").build();

	@NonNull String name;
	@NonNull @Builder.Default ImmutableSet<BPartnerId> onlyBPartnerIds = ImmutableSet.of();
	boolean isAllowPickingAnyHU;
	@NonNull @Builder.Default CreateShipmentPolicy createShipmentPolicy = CreateShipmentPolicy.DO_NOT_CREATE;
}
