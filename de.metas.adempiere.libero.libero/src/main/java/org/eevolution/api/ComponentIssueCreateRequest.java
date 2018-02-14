package org.eevolution.api;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_Order_BOMLine;

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
	int locatorId;
	int attributeSetInstanceId;
	Date movementDate;
	I_C_UOM qtyUOM;
	BigDecimal qtyIssue;
	BigDecimal qtyScrap;
	BigDecimal qtyReject;

	@Builder
	private ComponentIssueCreateRequest(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			final int locatorId,
			final int attributeSetInstanceId,
			@Nullable final Date movementDate,
			@NonNull final I_C_UOM qtyUOM,
			@NonNull final BigDecimal qtyIssue,
			@Nullable final BigDecimal qtyScrap,
			@Nullable final BigDecimal qtyReject)
	{
		Check.assume(locatorId > 0, "locatorId > 0");

		this.orderBOMLine = orderBOMLine;
		this.locatorId = locatorId;
		this.attributeSetInstanceId = attributeSetInstanceId > 0 ? attributeSetInstanceId : 0;
		this.movementDate = movementDate != null ? movementDate : SystemTime.asTimestamp();
		this.qtyUOM = qtyUOM;
		this.qtyIssue = qtyIssue;
		this.qtyScrap = qtyScrap != null ? qtyScrap : BigDecimal.ZERO;
		this.qtyReject = qtyReject != null ? qtyReject : BigDecimal.ZERO;
	}
}
