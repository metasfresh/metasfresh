package de.metas.purchasecandidate;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.DimensionService;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.metas.adempiere.model.I_M_Product;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemRepository;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.quantity.Quantity;

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

public class PurchaseCandidateRepositoryTest
{
	private static final BigDecimal TWO = ONE.add(ONE);

	private static final int VENDOR_ID = 20;

	private ReferenceGenerator referenceGenerator;

	private DimensionService dimensionService;

	private PurchaseCandidateRepository purchaseCandidateRepository;

	private I_C_UOM uom;

	private I_M_Product productRecord;

	private I_C_PurchaseCandidate purchaseCandidateRecord;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		referenceGenerator = Mockito.mock(ReferenceGenerator.class);

		dimensionService = Mockito.mock(DimensionService.class);

		purchaseCandidateRepository = new PurchaseCandidateRepository(
				new PurchaseItemRepository(),
				referenceGenerator,
				new BPPurchaseScheduleService(new BPPurchaseScheduleRepository()),
				dimensionService);

		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		productRecord = newInstance(I_M_Product.class);
		productRecord.setValue("product.Value");
		productRecord.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(productRecord);

		purchaseCandidateRecord = newInstance(I_C_PurchaseCandidate.class);
		purchaseCandidateRecord.setVendor_ID(VENDOR_ID);
		purchaseCandidateRecord.setProcessed(true);
		purchaseCandidateRecord.setM_WarehousePO_ID(30);
		purchaseCandidateRecord.setM_Product_ID(productRecord.getM_Product_ID());
		purchaseCandidateRecord.setDemandReference("DemandReference");
		purchaseCandidateRecord.setC_UOM_ID(uom.getC_UOM_ID());
		purchaseCandidateRecord.setQtyToPurchase(TEN);
		purchaseCandidateRecord.setPurchaseDatePromised(SystemTime.asTimestamp());
		purchaseCandidateRecord.setExternalHeaderId("H1");
		purchaseCandidateRecord.setExternalLineId("H1");
		saveRecord(purchaseCandidateRecord);
	}

	@Test
	public void save()
	{
		Mockito.doReturn("nextDemandReference").when(referenceGenerator).getNextDemandReference();

		final PurchaseCandidate purchaseCandidate = PurchaseCandidateTestTool.createPurchaseCandidate(0, Quantity.of(TEN, uom));

		assertThat(purchaseCandidate.getGroupReference().getDemandReference()).isEqualTo(DemandGroupReference.REFERENCE_NOT_YET_SET); // guard

		// invoke the method under test
		final PurchaseCandidateId id = purchaseCandidateRepository.save(purchaseCandidate);

		final List<I_C_PurchaseCandidate> candidateRecords = POJOLookupMap.get().getRecords(I_C_PurchaseCandidate.class);
		// assertThat(candidateRecords).hasSize(1); // there is also the record we created in the init method, so it's >1

		assertThat(candidateRecords)
				.filteredOn(candidateRecord -> candidateRecord.getC_PurchaseCandidate_ID() == id.getRepoId())
				.hasSize(1)
				.allSatisfy(candidateRecord -> {
					assertThat(candidateRecord.getC_PurchaseCandidate_ID()).isEqualTo(id.getRepoId());
					assertThat(candidateRecord.getDemandReference()).isEqualTo("nextDemandReference");
				});
	}

	/**
	 * Retrieves a purchase candidate record that has a purchase order line attached to it.
	 * Expects that purchase order line to be represented as a purchaseorderItem.
	 */
	@Test
	public void getById_with_purchaseOrderLine()
	{
		final I_C_OrderLine purchaseOrderLineRecord = newInstance(I_C_OrderLine.class);
		purchaseOrderLineRecord.setQtyOrdered(ONE);
		purchaseOrderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		purchaseOrderLineRecord.setC_Order_ID(40);
		saveRecord(purchaseOrderLineRecord);

		final I_C_PurchaseCandidate_Alloc purchaseCandidateAllocRecord = newInstance(I_C_PurchaseCandidate_Alloc.class);
		purchaseCandidateAllocRecord.setC_PurchaseCandidate(purchaseCandidateRecord);
		purchaseCandidateAllocRecord.setC_OrderPO_ID(purchaseOrderLineRecord.getC_Order_ID());
		purchaseCandidateAllocRecord.setC_OrderLinePO(purchaseOrderLineRecord);
		purchaseCandidateAllocRecord.setDatePromised(de.metas.common.util.time.SystemTime.asTimestamp());
		purchaseCandidateAllocRecord.setRemotePurchaseOrderId(NullVendorGatewayInvoker.NO_REMOTE_PURCHASE_ID);
		saveRecord(purchaseCandidateAllocRecord);

		final PurchaseCandidateId id = PurchaseCandidateId.ofRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID());

		// invoke the method under test
		final PurchaseCandidate purchaseCandidate = purchaseCandidateRepository.getById(id);

		assertThat(purchaseCandidate.isProcessed()).isTrue();
		assertThat(purchaseCandidate.getQtyToPurchase().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(purchaseCandidate.getQtyToPurchase().getUomId().getRepoId()).isEqualTo(uom.getC_UOM_ID());
		assertThat(purchaseCandidate.getPurchasedQty().toBigDecimal()).isEqualByComparingTo(ONE);
		assertThat(purchaseCandidate.getPurchasedQty().getUomId().getRepoId()).isEqualTo(uom.getC_UOM_ID());

		assertThat(purchaseCandidate.getPurchaseErrorItems()).isEmpty(); // because or single purchaseCandidateAllocRecord has AD_Issue_ID<=0
		assertThat(purchaseCandidate.getPurchaseOrderItems()).hasSize(1);

		final PurchaseOrderItem purchaseOrderItem = purchaseCandidate.getPurchaseOrderItems().get(0);
		assertThat(purchaseOrderItem.getPurchasedQty().toBigDecimal()).isEqualByComparingTo(ONE);
		assertThat(purchaseOrderItem.getPurchasedQty().getUomId().getRepoId()).isEqualTo(uom.getC_UOM_ID());
		assertThat(purchaseOrderItem.getVendorId().getRepoId()).isEqualTo(VENDOR_ID);
		assertThat(purchaseOrderItem.getPurchaseOrderAndLineId().getOrderLineRepoId()).isEqualTo(purchaseOrderLineRecord.getC_OrderLine_ID());
	}

	@Test
	public void getById_with_profitInfo()
	{
		final CurrencyId currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		purchaseCandidateRecord.setC_Currency_ID(currencyId.getRepoId());
		purchaseCandidateRecord.setProfitPurchasePriceActual(ONE);
		purchaseCandidateRecord.setProfitSalesPriceActual(TEN);
		purchaseCandidateRecord.setPurchasePriceActual(TWO);
		saveRecord(purchaseCandidateRecord);

		final PurchaseCandidateId id = PurchaseCandidateId.ofRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID());

		// invoke the method under test
		final PurchaseCandidate purchaseCandidate = purchaseCandidateRepository.getById(id);

		final PurchaseProfitInfo profitInfo = purchaseCandidate.getProfitInfoOrNull();
		assertThat(profitInfo).isNotNull();

		assertThat(profitInfo.getCommonCurrency())
				.isEqualTo(currencyId);

		assertThat(profitInfo.getProfitPurchasePriceActual())
				.isPresent()
				.contains(Money.of(ONE, currencyId));

		assertThat(profitInfo.getProfitSalesPriceActual())
				.isPresent()
				.contains(Money.of(TEN, currencyId));

		assertThat(profitInfo.getPurchasePriceActual())
				.isPresent()
				.contains(Money.of(TWO, currencyId));
	}
}
