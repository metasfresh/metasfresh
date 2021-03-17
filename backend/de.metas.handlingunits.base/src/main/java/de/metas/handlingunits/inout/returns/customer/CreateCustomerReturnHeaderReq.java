/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.inout.returns.customer;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.inout.returns.IReturnsDocTypeIdProvider;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Value
@Builder
public class CreateCustomerReturnHeaderReq
{
	@NonNull OrgId orgId;

	@NonNull
	IReturnsDocTypeIdProvider returnDocTypeIdProvider;

	@NonNull
	BPartnerLocationId bPartnerLocationId;

	@NonNull
	WarehouseId warehouseId;

	@Nullable
	I_C_Order order;

	@Nullable
	LocalDate movementDate;

	@Nullable
	ZonedDateTime dateReceived;

	@Nullable
	String externalId;

	@Nullable
	String externalResourceURL;
}
