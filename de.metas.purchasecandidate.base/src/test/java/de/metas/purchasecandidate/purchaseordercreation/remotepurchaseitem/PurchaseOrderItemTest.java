package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.junit.Test;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateTestTool;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

public class PurchaseOrderItemTest
{
	@Test
	public void toString_without_StackOverflowError()
	{
		final PurchaseCandidate purchaseCandidate = PurchaseCandidateTestTool.createPurchaseCandidate(20);

		final PurchaseOrderItem purchaseOrderItem = purchaseCandidate.createOrderItem()
				.purchasedQty(TEN)
				.datePromised(SystemTime.asTimestamp())
				.transactionReference(TableRecordReference.of("sometable", 40))
				.remotePurchaseOrderId("remotePurchaseOrderId")
				.buildAndAddToParent();

		assertThat(purchaseOrderItem.toString()).isNotBlank();
	}

}
