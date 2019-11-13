package org.eevolution.api;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.quantity.Quantity;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	I_PP_Order_BOMLine orderBOMLine;
	LocatorId locatorId;
	AttributeSetInstanceId attributeSetInstanceId;
	ZonedDateTime movementDate;
	Quantity qtyIssue;
	Quantity qtyScrap;
	Quantity qtyReject;
	int pickingCandidateId;

	@Builder
	private ComponentIssueCreateRequest(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@NonNull final LocatorId locatorId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@Nullable final ZonedDateTime movementDate,
			@NonNull final Quantity qtyIssue,
			@Nullable final Quantity qtyScrap,
			@Nullable final Quantity qtyReject,
			final int pickingCandidateId)
	{
		this.orderBOMLine = orderBOMLine;
		this.locatorId = locatorId;
		this.attributeSetInstanceId = attributeSetInstanceId != null ? attributeSetInstanceId : AttributeSetInstanceId.NONE;
		this.movementDate = movementDate != null ? movementDate : SystemTime.asZonedDateTime();
		this.qtyIssue = qtyIssue;
		this.qtyScrap = qtyScrap != null ? qtyScrap : qtyIssue.toZero();
		this.qtyReject = qtyReject != null ? qtyReject : qtyIssue.toZero();
		this.pickingCandidateId = pickingCandidateId > 0 ? pickingCandidateId : -1;
	}
}
