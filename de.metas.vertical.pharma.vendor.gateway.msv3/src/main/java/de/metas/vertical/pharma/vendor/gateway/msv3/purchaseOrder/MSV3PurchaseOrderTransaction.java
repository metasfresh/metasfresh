package de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.vendor.gateway.api.order.MSV3OrderResponsePackageItemPartRepoId;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemPartId;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3ClientException;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3FaultInfoDataPersister;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Bestellung;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Bestellung_Transaction;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Setter
public class MSV3PurchaseOrderTransaction
{
	private final OrgId orgId;

	@Getter
	private final OrderCreateRequest request;

	@Getter
	private OrderResponse response;

	private MSV3OrderResponsePackageItemPartRepoIds responseItemPartRepoIds;

	private FaultInfo faultInfo;
	private Exception otherException;

	@Builder
	private MSV3PurchaseOrderTransaction(
			@NonNull final OrderCreateRequest request,
			@NonNull final OrgId orgId)
	{
		this.request = request;
		this.orgId = orgId;
	}

	public I_MSV3_Bestellung_Transaction store()
	{
		final MSV3PurchaseOrderRequestPersister purchaseOrderRequestPersister = MSV3PurchaseOrderRequestPersister.createNewForOrgId(orgId);

		final I_MSV3_Bestellung_Transaction transactionRecord = newInstanceOutOfTrx(I_MSV3_Bestellung_Transaction.class);
		transactionRecord.setAD_Org_ID(orgId.getRepoId());

		final I_MSV3_Bestellung requestRecord = purchaseOrderRequestPersister.storePurchaseOrderRequest(request);
		transactionRecord.setMSV3_Bestellung(requestRecord);

		if (response != null)
		{
			final MSV3PurchaseOrderResponsePersister purchaseOrderResponsePersister = MSV3PurchaseOrderResponsePersister.createNewForOrgId(orgId);

			final I_MSV3_BestellungAntwort bestellungAntwortRecord = //
					purchaseOrderResponsePersister.storePurchaseOrderResponse(response);
			transactionRecord.setMSV3_BestellungAntwort(bestellungAntwortRecord);

			responseItemPartRepoIds = purchaseOrderResponsePersister.getResponseItemPartRepoIds();
		}
		if (faultInfo != null)
		{
			final I_MSV3_FaultInfo faultInfoRecord = Msv3FaultInfoDataPersister
					.newInstanceWithOrgId(orgId)
					.storeMsv3FaultInfoOrNull(faultInfo);
			transactionRecord.setMSV3_FaultInfo(faultInfoRecord);
		}
		if (otherException != null)
		{
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(otherException);
			transactionRecord.setAD_Issue_ID(issueId.getRepoId());
		}

		save(transactionRecord);
		return transactionRecord;
	}

	public RuntimeException getExceptionOrNull()
	{
		if (faultInfo == null && otherException == null)
		{
			return null;
		}
		return Msv3ClientException.builder()
				.msv3FaultInfo(faultInfo)
				.cause(otherException)
				.build();
	}

	public MSV3OrderResponsePackageItemPartRepoId getResponseItemPartRepoId(final OrderResponsePackageItemPartId partId)
	{
		return responseItemPartRepoIds.getRepoId(partId);
	}
}
