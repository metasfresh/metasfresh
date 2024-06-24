package org.eevolution.api;

import de.metas.common.util.time.SystemTime;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License; or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful;
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not; see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public class ComponentIssueCreateRequest
{
	@NonNull I_PP_Order_BOMLine orderBOMLine;
	@NonNull ProductId productId;
	@NonNull LocatorId locatorId;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;
	@NonNull ZonedDateTime movementDate;
	@NonNull Quantity qtyIssue;
	@NonNull Quantity qtyScrap;
	@NonNull Quantity qtyReject;
	int pickingCandidateId;

	@Builder
	private ComponentIssueCreateRequest(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@Nullable ProductId productId,
			@NonNull final LocatorId locatorId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@Nullable final ZonedDateTime movementDate,
			@NonNull final Quantity qtyIssue,
			@Nullable final Quantity qtyScrap,
			@Nullable final Quantity qtyReject,
			final int pickingCandidateId)
	{
		this.orderBOMLine = orderBOMLine;
		this.productId = productId != null ? productId : ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		this.locatorId = locatorId;
		this.attributeSetInstanceId = attributeSetInstanceId != null ? attributeSetInstanceId : AttributeSetInstanceId.NONE;
		this.movementDate = movementDate != null ? movementDate : SystemTime.asZonedDateTime();
		this.qtyIssue = qtyIssue;
		this.qtyScrap = qtyScrap != null ? qtyScrap : qtyIssue.toZero();
		this.qtyReject = qtyReject != null ? qtyReject : qtyIssue.toZero();
		this.pickingCandidateId = pickingCandidateId > 0 ? pickingCandidateId : -1;
	}
}
