/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Data
@Builder
public class ReceiptSchedule
{
	@NonNull
	private final ReceiptScheduleId id;

	@NonNull
	private final OrgId orgId;

	@NonNull
	private final BPartnerId vendorId;

	@Nullable
	private final OrderId orderId;

	@Nullable
	private final LocalDateTime dateOrdered;

	@NonNull
	private final ProductId productId;

	@NonNull
	private final Quantity quantityToDeliver;

	@Nullable
	private final AttributeSetInstanceId attributeSetInstanceId;

	@NonNull
	private APIExportStatus exportStatus;
}
