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

package de.metas.inoutcandidate.api;

import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
public class ApplyReceiptScheduleChangesRequest
{
	@NonNull
	ReceiptScheduleId receiptScheduleId;

	@Nullable
	BigDecimal qtyInStockingUOM;

	@Nullable
	List<CreateAttributeInstanceReq> attributes;

	@Builder
	public ApplyReceiptScheduleChangesRequest(
			@NonNull final ReceiptScheduleId receiptScheduleId,
			@Nullable final BigDecimal qtyInStockingUOM,
			@Nullable final List<CreateAttributeInstanceReq> attributes)
	{
		if (qtyInStockingUOM == null && Check.isEmpty(attributes))
		{
			throw new AdempiereException("Empty request!");
		}

		this.receiptScheduleId = receiptScheduleId;
		this.qtyInStockingUOM = qtyInStockingUOM;
		this.attributes = attributes;
	}

	public boolean hasAttributes()
	{
		return !Check.isEmpty(attributes);
	}

	@NonNull
	public List<CreateAttributeInstanceReq> getNonNullAttributeReqList()
	{
		if (Check.isEmpty(attributes))
		{
			throw new AdempiereException("CreateAttributeInstanceReq list is empty or null!");
		}

		return attributes;
	}
}
