package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.ui.web.window.datatypes.DocumentId;

/*
 * #%L
 * metasfresh-webui-api
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

public class PurchaseRowIdTest
{
	@Test
	public void groupId()
	{
		final PurchaseRowId rowId = PurchaseRowId.groupId(20);

		final DocumentId documentId = rowId.toDocumentId();
		assertThat(documentId.toString()).isEqualTo("20");

		assertThat(rowId.isGroupRowId()).isTrue();
		assertThat(rowId.isLineRowId()).isFalse();
		assertThat(rowId.isAvailabilityRowId()).isFalse();
	}

	@Test
	public void lineId()
	{
		lineId_performWithParams("30", "40", "50");
		lineId_performWithParams("30", "40", "0");
	}

	private void lineId_performWithParams(
			final String salesOrderLineId,
			final String vendorBPartnerId,
			final String processedPurchaseCandidateId)
	{
		final PurchaseRowId rowId = PurchaseRowId.lineId(
				Integer.parseInt(salesOrderLineId),
				Integer.parseInt(vendorBPartnerId),
				Integer.parseInt(processedPurchaseCandidateId));

		final DocumentId documentId = rowId.toDocumentId();
		assertThat(documentId.toString()).isEqualTo(salesOrderLineId
				+ PurchaseRowId.PARTS_SEPARATOR + vendorBPartnerId
				+ PurchaseRowId.PARTS_SEPARATOR + processedPurchaseCandidateId);

		assertThat(rowId.isGroupRowId()).isFalse();
		assertThat(rowId.isLineRowId()).isTrue();
		assertThat(rowId.isAvailabilityRowId()).isFalse();
	}

	@Test
	public void withAvailability()
	{
		final PurchaseRowId rowId = PurchaseRowId.lineId(10, 20, 30);
		final PurchaseRowId availabilityRowId = rowId.withAvailability(Type.AVAILABLE, "someString");

		assertThat(rowId.toDocumentId()).isNotEqualTo(availabilityRowId.toDocumentId());
		assertThat(availabilityRowId.toDocumentId().toString())
				.isEqualTo("10"
						+ PurchaseRowId.PARTS_SEPARATOR + "20"
						+ PurchaseRowId.PARTS_SEPARATOR + "30"
						+ PurchaseRowId.PARTS_SEPARATOR + Type.AVAILABLE.toString()
						+ PurchaseRowId.PARTS_SEPARATOR + "someString");

		assertThat(availabilityRowId.isGroupRowId()).isFalse();
		assertThat(availabilityRowId.isLineRowId()).isFalse();
		assertThat(availabilityRowId.isAvailabilityRowId()).isTrue();
	}

	@Test
	public void fromDocumentId_Available()
	{
		final DocumentId documentId = DocumentId.ofString("1000007-2156423-0-AVAILABLE-11");
		final PurchaseRowId purchaseRowId = PurchaseRowId.fromDocumentId(documentId);
		assertThat(purchaseRowId.getSalesOrderLineId()).isEqualTo(1000007);
		assertThat(purchaseRowId.getVendorBPartnerId()).isEqualTo(2156423);
		assertThat(purchaseRowId.getProcessedPurchaseCandidateId()).isEqualTo(0);
		assertThat(purchaseRowId.getAvailabilityType()).isEqualTo(Type.AVAILABLE);
	}

	@Test
	public void fromDocumentId_NotAvailable()
	{
		final DocumentId documentId = DocumentId.ofString("1000007-2156423-32311-NOT_AVAILABLE-11");
		final PurchaseRowId purchaseRowId = PurchaseRowId.fromDocumentId(documentId);
		assertThat(purchaseRowId.getSalesOrderLineId()).isEqualTo(1000007);
		assertThat(purchaseRowId.getVendorBPartnerId()).isEqualTo(2156423);
		assertThat(purchaseRowId.getProcessedPurchaseCandidateId()).isEqualTo(32311);
		assertThat(purchaseRowId.getAvailabilityType()).isEqualTo(Type.NOT_AVAILABLE);
	}
}
