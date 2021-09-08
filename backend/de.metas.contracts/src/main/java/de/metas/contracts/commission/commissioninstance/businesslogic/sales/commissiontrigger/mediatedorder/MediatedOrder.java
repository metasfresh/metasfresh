/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.mediatedorder;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocument;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.TimeUtil;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Represents a purchase order that is forwarded to completely handled by an external vendor.
 * There is no material receipt or purchase invoice in metasfresh.
 * Instead, metasfresh creates a purchase commission instance such that in the end, the vendor can pay a commission for the mediated order.
 */
@Value
@Builder
public class MediatedOrder
{
	@NonNull
	OrderId id;

	@NonNull
	OrgId orgId;

	@NonNull
	BPartnerId vendorBPartnerId;

	@NonNull
	BPartnerId orgBPartnerId;

	@NonNull
	LocalDate orderDateAcct;

	@NonNull
	Instant updated;

	@NonNull
	ImmutableList<MediatedOrderLine> mediatedOrderLines;

	@NonNull
	public ImmutableList<CommissionTriggerDocument> asCommissionTriggerDocuments()
	{
		final CommissionTriggerDocument.CommissionTriggerDocumentBuilder document = CommissionTriggerDocument.builder()
				.triggerType(CommissionTriggerType.MediatedOrder)
				.orgId(orgId)
				.salesRepBPartnerId(orgBPartnerId)
				.customerBPartnerId(vendorBPartnerId)
				.commissionDate(orderDateAcct);

		final ImmutableList.Builder<CommissionTriggerDocument> result = ImmutableList.builder();

		mediatedOrderLines.forEach(mediatedLine -> {
			document.id(MediatedOrderLineDocId.of(mediatedLine.getId()))

					.tradedCommissionPercent(Percent.ZERO)
					.forecastCommissionPoints(CommissionPoints.ZERO)
					.commissionPointsToInvoice(CommissionPoints.ZERO)
					.invoicedCommissionPoints(mediatedLine.getInvoicedCommissionPoints())

					.productId(mediatedLine.getProductId())
					.updated(TimeUtil.maxNotNull(updated, mediatedLine.getUpdated()));

			result.add(document.build());
		});

		return result.build();
	}
}
