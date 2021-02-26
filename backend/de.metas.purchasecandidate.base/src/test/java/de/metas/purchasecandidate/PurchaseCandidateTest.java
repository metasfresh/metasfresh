package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.money.grossprofit.ProfitPriceActualFactory;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.quantity.Quantity;

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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, ProfitPriceActualFactory.class })
public class PurchaseCandidateTest
{
	private I_C_UOM EACH;
	private Quantity ONE;
	private Quantity TEN;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.EACH = createUOM("Ea");
		this.ONE = Quantity.of(BigDecimal.ONE, EACH);
		this.TEN = Quantity.of(BigDecimal.TEN, EACH);
	}

	private I_C_UOM createUOM(final String name)
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		save(uom);
		return uom;
	}

	@Test
	public void markProcessedAndCheckChanges()
	{
		final PurchaseCandidate candidate = PurchaseCandidateTestTool.createPurchaseCandidate(1, ONE);
		assertThat(candidate.getSalesOrderAndLineIdOrNull().getOrderLineId()).isEqualTo(PurchaseCandidateTestTool.SALES_ORDER_LINE_ID); // guard
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
		assertThat(candidate.isProcessed()).isFalse();
		assertThat(candidate.copy().isProcessed()).isFalse();

		candidate.markProcessed();
		assertThat(candidate.hasChanges()).isTrue();
		assertThat(candidate.copy().hasChanges()).isTrue();
		assertThat(candidate.isProcessed()).isTrue();
		assertThat(candidate.copy().isProcessed()).isTrue();
		assertThat(candidate.copy().getSalesOrderAndLineIdOrNull().getOrderLineId()).isEqualTo(PurchaseCandidateTestTool.SALES_ORDER_LINE_ID);

		candidate.markSaved(PurchaseCandidateId.ofRepoId(1));
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
	}

	@Test
	public void changeQtyRequiredAndCheckChanges()
	{
		final PurchaseCandidate candidate = PurchaseCandidateTestTool.createPurchaseCandidate(1, ONE);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();

		final Quantity newQtyRequired = candidate.getQtyToPurchase().add(ONE);
		candidate.setQtyToPurchase(newQtyRequired);

		assertThat(candidate.hasChanges()).isTrue();
		assertThat(candidate.copy().hasChanges()).isTrue();
		assertThat(candidate.getQtyToPurchase()).isEqualByComparingTo(newQtyRequired);
		assertThat(candidate.copy().getQtyToPurchase()).isEqualByComparingTo(newQtyRequired);

		candidate.markSaved(PurchaseCandidateId.ofRepoId(1));
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
	}

	@Test
	public void changeDatePromisedAndCheckChanges()
	{
		final PurchaseCandidate candidate = PurchaseCandidateTestTool.createPurchaseCandidate(1, ONE);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();

		final ZonedDateTime newDatePromised = candidate.getPurchaseDatePromised().plusDays(1);
		candidate.setPurchaseDatePromised(newDatePromised);

		assertThat(candidate.hasChanges()).isTrue();
		assertThat(candidate.copy().hasChanges()).isTrue();
		assertThat(candidate.getPurchaseDatePromised()).isEqualTo(newDatePromised);
		assertThat(candidate.copy().getPurchaseDatePromised()).isEqualTo(newDatePromised);

		candidate.markSaved(PurchaseCandidateId.ofRepoId(1));
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
	}

	@Test
	public void newErrorItem()
	{
		final PurchaseCandidate candidate1 = PurchaseCandidateTestTool.createPurchaseCandidate(20, ONE);

		final RuntimeException throwable = new RuntimeException();
		candidate1.createErrorItem()
				.throwable(throwable)
				.buildAndAdd();

		assertThat(candidate1.getPurchaseOrderItems()).isEmpty();
		final List<PurchaseErrorItem> purchaseErrorItems = candidate1.getPurchaseErrorItems();
		assertThat(purchaseErrorItems).hasSize(1);

		final PurchaseErrorItem purchaseErrorItem = purchaseErrorItems.get(0);
		assertThat(purchaseErrorItem.getOrgId()).isEqualTo(candidate1.getOrgId());
		assertThat(purchaseErrorItem.getPurchaseCandidateId()).isEqualTo(candidate1.getId());
		assertThat(purchaseErrorItem.getThrowable()).isSameAs(throwable);
	}

	@Test
	public void newOrderItem()
	{
		final PurchaseCandidate candidate1 = PurchaseCandidateTestTool.createPurchaseCandidate(20, ONE);

		candidate1.createOrderItem()
				.purchasedQty(TEN)
				.datePromised(de.metas.common.util.time.SystemTime.asZonedDateTime())
				.remotePurchaseOrderId("remotePurchaseOrderId")
				.transactionReference(TableRecordReference.of(I_AD_Table.Table_Name, 30))
				.buildAndAddToParent();

		candidate1.createOrderItem()
				.purchasedQty(ONE)
				.datePromised(SystemTime.asZonedDateTime())
				.remotePurchaseOrderId("remotePurchaseOrderId-2")
				.transactionReference(TableRecordReference.of(I_AD_Table.Table_Name, 40))
				.buildAndAddToParent();

		assertThat(candidate1.getPurchaseErrorItems()).isEmpty();
		final List<PurchaseOrderItem> purchaseOrderItems = candidate1.getPurchaseOrderItems();
		assertThat(purchaseOrderItems).hasSize(2);
		assertThat(candidate1.getPurchasedQty()).isEqualTo(TEN.add(ONE));

		final PurchaseOrderItem purchaseOrderItem1 = purchaseOrderItems.get(0);
		assertThat(purchaseOrderItem1.getOrgId()).isEqualTo(candidate1.getOrgId());
		assertThat(purchaseOrderItem1.getRemotePurchaseOrderId()).isEqualTo("remotePurchaseOrderId");
		assertThat(purchaseOrderItem1.getPurchaseCandidateId()).isEqualTo(candidate1.getId());
		assertThat(purchaseOrderItem1.getProductId()).isEqualTo(candidate1.getProductId());

		final PurchaseOrderItem purchaseOrderItem2 = purchaseOrderItems.get(1);
		assertThat(purchaseOrderItem2.getOrgId()).isEqualTo(candidate1.getOrgId());
		assertThat(purchaseOrderItem2.getRemotePurchaseOrderId()).isEqualTo("remotePurchaseOrderId-2");
		assertThat(purchaseOrderItem2.getPurchaseCandidateId()).isEqualTo(candidate1.getId());
		assertThat(purchaseOrderItem2.getProductId()).isEqualTo(candidate1.getProductId());
	}

	@Test
	public void testToString()
	{
		final PurchaseCandidate purchaseCandidate = PurchaseCandidateTestTool.createPurchaseCandidate(20, ONE);
		final String toString = purchaseCandidate.toString();
		assertThat(toString).isNotNull();
		assertThat(toString).startsWith("PurchaseCandidate(id=PurchaseCandidateId(repoId=20)");
	}
}
