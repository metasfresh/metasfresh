package de.metas.purchasecandidate;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Test;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PurchaseCandidateTest
{
	@Test
	public void markProcessedAndCheckChanges()
	{
		final PurchaseCandidate candidate = createDummyPurchaseCandidate(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
		assertThat(candidate.isProcessed()).isFalse();
		assertThat(candidate.copy().isProcessed()).isFalse();

		candidate.setPurchaseOrderLineIdAndMarkProcessed(123);
		assertThat(candidate.hasChanges()).isTrue();
		assertThat(candidate.copy().hasChanges()).isTrue();
		assertThat(candidate.isProcessed()).isTrue();
		assertThat(candidate.copy().isProcessed()).isTrue();
		assertThat(candidate.getPurchaseOrderLineId()).isEqualTo(123);
		assertThat(candidate.copy().getPurchaseOrderLineId()).isEqualTo(123);

		candidate.markSaved(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
	}

	@Test
	public void changeQtyRequiredAndCheckChanges()
	{
		final PurchaseCandidate candidate = createDummyPurchaseCandidate(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();

		final BigDecimal newQtyRequired = candidate.getQtyRequired().add(BigDecimal.ONE);
		candidate.setQtyRequired(newQtyRequired);

		assertThat(candidate.hasChanges()).isTrue();
		assertThat(candidate.copy().hasChanges()).isTrue();
		assertThat(candidate.getQtyRequired()).isEqualByComparingTo(newQtyRequired);
		assertThat(candidate.copy().getQtyRequired()).isEqualByComparingTo(newQtyRequired);

		candidate.markSaved(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
	}

	@Test
	public void changeDatePromisedAndCheckChanges()
	{
		final PurchaseCandidate candidate = createDummyPurchaseCandidate(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();

		final Date newDatePromised = TimeUtil.addDays(candidate.getDatePromised(), +1);
		candidate.setDatePromised(newDatePromised);

		assertThat(candidate.hasChanges()).isTrue();
		assertThat(candidate.copy().hasChanges()).isTrue();
		assertThat(candidate.getDatePromised()).isEqualTo(newDatePromised);
		assertThat(candidate.copy().getDatePromised()).isEqualTo(newDatePromised);

		candidate.markSaved(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
	}

	private PurchaseCandidate createDummyPurchaseCandidate(final int purchaseCandidateId)
	{
		return PurchaseCandidate.builder()
				.purchaseCandidateId(purchaseCandidateId)
				.salesOrderId(1)
				.salesOrderLineId(2)
				.orgId(3)
				.warehouseId(4)
				.productId(5)
				.uomId(6)
				.vendorBPartnerId(7)
				.vendorProductInfo(new VendorProductInfo(10, 7, 20, "productNo", "productName"))
				.qtyRequired(BigDecimal.ONE)
				.datePromised(SystemTime.asDayTimestamp())
				.processed(false)
				.locked(false)
				.build();

	}
}
