package de.metas.rest_api.invoicecandidates.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Value
public class InvoiceCandEnqueuerResult
{
	private String summaryTranslated;

	private int invoiceCandidateEnqueuedCount;

	private int workpackageEnqueuedCount;

	private int workpackageQueueSizeBeforeEnqueueing;

	private BigDecimal totalNetAmtToInvoiceChecksum;

	@JsonCreator
	@Builder(toBuilder = true)
	private InvoiceCandEnqueuerResult(
			@JsonProperty("summaryTranslated") final String summaryTranslated,
			@JsonProperty("invoiceCandidateEnqueuedCount") int invoiceCandidateEnqueuedCount,
			@JsonProperty("workpackageEnqueuedCount") int workpackageEnqueuedCount,
			@JsonProperty("workpackageQueueSizeBeforeEnqueueing") int workpackageQueueSizeBeforeEnqueueing,
			@JsonProperty("totalNetAmtToInvoiceChecksum") final BigDecimal totalNetAmtToInvoiceChecksum)
	{
		this.summaryTranslated = summaryTranslated;
		this.invoiceCandidateEnqueuedCount = invoiceCandidateEnqueuedCount;
		this.workpackageEnqueuedCount = workpackageEnqueuedCount;
		this.workpackageQueueSizeBeforeEnqueueing = workpackageQueueSizeBeforeEnqueueing;
		this.totalNetAmtToInvoiceChecksum = totalNetAmtToInvoiceChecksum;
	}

}
