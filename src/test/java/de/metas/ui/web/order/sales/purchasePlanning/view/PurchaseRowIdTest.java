package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.purchasecandidate.AvailabilityCheck.AvailabilityResult.Type;
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
	public void groupId_toDocumentId()
	{
		final DocumentId documentId = PurchaseRowId.groupId(20).toDocumentId();
		assertThat(documentId.toString()).isEqualTo("20");
	}

	@Test
	public void lineId_toDocumentId()
	{
		final DocumentId documentId = PurchaseRowId.lineId(30, 40).toDocumentId();
		assertThat(documentId.toString()).isEqualTo("30" + PurchaseRowId.PARTS_SEPARATOR + "40");
	}

	@Test
	public void withAvailability()
	{
		final PurchaseRowId lineId = PurchaseRowId.lineId(10, 20);
		final PurchaseRowId availabilityId = lineId.withAvailability(Type.AVAILABLE);

		assertThat(lineId.toDocumentId()).isNotEqualTo(availabilityId.toDocumentId());
		assertThat(availabilityId.toDocumentId().toString())
				.isEqualTo("10" + PurchaseRowId.PARTS_SEPARATOR + "20" + PurchaseRowId.PARTS_SEPARATOR + Type.AVAILABLE.toString());
	}

	@Test
	public void fromDocumentId()
	{
		final DocumentId documentId = DocumentId.ofString("1000007_2156423_AVAILABLE");
		final PurchaseRowId purchaseRowId = PurchaseRowId.fromDocumentId(documentId);
		assertThat(purchaseRowId.getSalesOrderLineId()).isEqualTo(1000007);
		assertThat(purchaseRowId.getVendorBPartnerId()).isEqualTo(2156423);
		assertThat(purchaseRowId.getAvailabilityType()).isEqualTo(Type.AVAILABLE);
	}

}
