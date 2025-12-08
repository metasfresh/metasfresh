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

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.CreateASIRequest;

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
	@Singular
	List<CreateASIRequest> asiRequestList;

	@Nullable
	String externalResourceURL;

	@Builder
	public ApplyReceiptScheduleChangesRequest(
			@NonNull final ReceiptScheduleId receiptScheduleId,
			@Nullable final BigDecimal qtyInStockingUOM,
			@Nullable final CreateASIRequest asiRequestList,
			@Nullable final String externalResourceURL)
	{
		if (qtyInStockingUOM == null
				&& Check.isEmpty(asiRequestList)
				&& Check.isBlank(externalResourceURL))
		{
			throw new AdempiereException("Empty request!");
		}

		this.receiptScheduleId = receiptScheduleId;
		this.qtyInStockingUOM = qtyInStockingUOM;
		this.externalResourceURL = externalResourceURL;
		if (asiRequestList != null && !asiRequestList.isEmpty())
		{
			this.asiRequestList = ImmutableList.of(asiRequestList);
		}
		else
		{
			this.asiRequestList = ImmutableList.of();
		}
	}

	public boolean isSingleASI()
	{
		return !Check.isEmpty(asiRequestList) && asiRequestList.size() == 1;
	}

	@NonNull
	public CreateASIRequest getSingleASIRequest()
	{
		if (!isSingleASI())
		{
			throw new AdempiereException(" request contains more than one ASI!");
		}

		return asiRequestList.get(0);
	}
}
