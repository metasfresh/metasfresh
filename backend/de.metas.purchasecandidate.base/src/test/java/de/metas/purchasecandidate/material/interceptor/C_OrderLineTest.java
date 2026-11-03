/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.purchasecandidate.material.interceptor;

import de.metas.adempiere.model.I_M_Product;
import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.DimensionService;
import de.metas.order.OrderLineId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.ReferenceGenerator;
import de.metas.purchasecandidate.material.RealPurchaseCandidateCleanUpService;
import de.metas.purchasecandidate.material.SimulatedPurchaseCandidateCleanUpService;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemRepository;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class C_OrderLineTest
{
	private PurchaseCandidateRepository purchaseCandidateRepository;

	private C_OrderLine c_OrderLine;

	private I_M_Product productRecord;

	private I_C_Order salesOrderRecord;

	private I_C_OrderLine salesOrderLineRecord;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final ReferenceGenerator referenceGenerator = Mockito.mock(ReferenceGenerator.class);
		final DimensionService dimensionService = Mockito.mock(DimensionService.class);
		purchaseCandidateRepository = new PurchaseCandidateRepository(
				new PurchaseItemRepository(),
				referenceGenerator,
				dimensionService);

		final SimulatedPurchaseCandidateCleanUpService simulatedPurchaseCandidateCleanUpService =
				new SimulatedPurchaseCandidateCleanUpService(purchaseCandidateRepository);
		final RealPurchaseCandidateCleanUpService realPurchaseCandidateCleanUpService =
				new RealPurchaseCandidateCleanUpService(purchaseCandidateRepository);

		c_OrderLine = new C_OrderLine(simulatedPurchaseCandidateCleanUpService, realPurchaseCandidateCleanUpService);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		productRecord = newInstance(I_M_Product.class);
		productRecord.setValue("product.Value");
		productRecord.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(productRecord);

		salesOrderRecord = newInstance(I_C_Order.class);
		salesOrderRecord.setIsSOTrx(true);
		saveRecord(salesOrderRecord);

		salesOrderLineRecord = newInstance(I_C_OrderLine.class);
		salesOrderLineRecord.setC_Order(salesOrderRecord);
		salesOrderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		salesOrderLineRecord.setQtyOrdered(BigDecimal.TEN);
		saveRecord(salesOrderLineRecord);
	}

	private I_C_PurchaseCandidate createCandidate(final boolean simulated, final boolean processed)
	{
		return createCandidate(salesOrderLineRecord, simulated, processed);
	}

	private I_C_PurchaseCandidate createCandidate(
			final I_C_OrderLine orderLineSO,
			final boolean simulated,
			final boolean processed)
	{
		final I_C_PurchaseCandidate candidateRecord = newInstance(I_C_PurchaseCandidate.class);
		candidateRecord.setC_OrderLineSO_ID(orderLineSO.getC_OrderLine_ID());
		candidateRecord.setM_Product_ID(productRecord.getM_Product_ID());
		candidateRecord.setM_WarehousePO_ID(30);
		candidateRecord.setC_UOM_ID(productRecord.getC_UOM_ID());
		candidateRecord.setQtyToPurchase(BigDecimal.TEN);
		candidateRecord.setPurchaseDatePromised(SystemTime.asTimestamp());
		candidateRecord.setIsSimulated(simulated);
		candidateRecord.setProcessed(processed);
		saveRecord(candidateRecord);
		return candidateRecord;
	}

	/**
	 * Creates a purchase order + purchase order line, representing the PO that a purchase candidate produced.
	 */
	private I_C_OrderLine createPurchaseOrderLine()
	{
		final I_C_Order purchaseOrderRecord = newInstance(I_C_Order.class);
		purchaseOrderRecord.setIsSOTrx(false);
		saveRecord(purchaseOrderRecord);

		final I_C_OrderLine purchaseOrderLineRecord = newInstance(I_C_OrderLine.class);
		purchaseOrderLineRecord.setC_Order(purchaseOrderRecord);
		purchaseOrderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		purchaseOrderLineRecord.setQtyOrdered(BigDecimal.ONE);
		saveRecord(purchaseOrderLineRecord);
		return purchaseOrderLineRecord;
	}

	/**
	 * Creates the {@code C_PurchaseCandidate_Alloc} record that is the real, ground-truth link between a purchase
	 * candidate and the purchase order line it produced.
	 */
	private void createAlloc(final I_C_PurchaseCandidate candidate, final I_C_OrderLine purchaseOrderLineRecord)
	{
		final I_C_PurchaseCandidate_Alloc allocRecord = newInstance(I_C_PurchaseCandidate_Alloc.class);
		allocRecord.setC_PurchaseCandidate_ID(candidate.getC_PurchaseCandidate_ID());
		allocRecord.setC_OrderLinePO_ID(purchaseOrderLineRecord.getC_OrderLine_ID());
		allocRecord.setC_OrderPO_ID(purchaseOrderLineRecord.getC_Order_ID());
		saveRecord(allocRecord);
	}

	private List<I_C_PurchaseCandidate> getAllCandidateRecords()
	{
		return POJOLookupMap.get().getRecords(I_C_PurchaseCandidate.class);
	}

	@Test
	public void simulatedCandidate_isStillDeleted_onOrderLineDelete()
	{
		final I_C_PurchaseCandidate simulatedCandidate = createCandidate(true, false);

		c_OrderLine.removeSimulatedPurchaseCandidate(salesOrderLineRecord);

		assertThat(getAllCandidateRecords())
				.noneMatch(record -> record.getC_PurchaseCandidate_ID() == simulatedCandidate.getC_PurchaseCandidate_ID());
	}

	@Test
	public void realCandidate_withNoPurchaseOrder_isDeleted_onOrderLineDelete()
	{
		final I_C_PurchaseCandidate realCandidate = createCandidate(false, false);

		c_OrderLine.deleteOrGuardRealPurchaseCandidate(salesOrderLineRecord);

		assertThat(getAllCandidateRecords())
				.noneMatch(record -> record.getC_PurchaseCandidate_ID() == realCandidate.getC_PurchaseCandidate_ID());
	}

	@Test
	public void realCandidate_thatProducedAPurchaseOrder_blocksDelete_onOrderLineDelete()
	{
		final I_C_PurchaseCandidate realCandidate = createCandidate(false, true);
		createAlloc(realCandidate, createPurchaseOrderLine());

		assertThatThrownBy(() -> c_OrderLine.deleteOrGuardRealPurchaseCandidate(salesOrderLineRecord))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("SalesOrderLine_CannotDelete_HasCompletedDocs");

		assertThat(getAllCandidateRecords())
				.anyMatch(record -> record.getC_PurchaseCandidate_ID() == realCandidate.getC_PurchaseCandidate_ID());
	}

	/**
	 * A candidate that is only partially fulfilled has a real {@code C_PurchaseCandidate_Alloc} row (the true link
	 * to the PO it produced) while still {@code Processed=false}. The guard must be based on the {@code Alloc}
	 * ground truth, not on {@code Processed} -- otherwise it would miss this case (false negative) and the
	 * candidate would be hard-deleted while the {@code Alloc} row still FK-references it.
	 */
	@Test
	public void realCandidate_partiallyFulfilled_blocksDelete_onOrderLineDelete()
	{
		final I_C_PurchaseCandidate realCandidate = createCandidate(false, false);
		createAlloc(realCandidate, createPurchaseOrderLine());

		assertThatThrownBy(() -> c_OrderLine.deleteOrGuardRealPurchaseCandidate(salesOrderLineRecord))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("SalesOrderLine_CannotDelete_HasCompletedDocs");

		assertThat(getAllCandidateRecords())
				.anyMatch(record -> record.getC_PurchaseCandidate_ID() == realCandidate.getC_PurchaseCandidate_ID());
	}

	@Test
	public void purchaseOrderLine_isNeverGuardedOrCascaded()
	{
		final I_C_OrderLine purchaseOrderLineRecord = createPurchaseOrderLine();

		// the candidate genuinely references the *purchase* order line under test (C_OrderLineSO_ID) and has
		// actually produced a PO (real Alloc row) -- if the isSOTrx gate were removed, this would block the delete
		final I_C_PurchaseCandidate realCandidate = createCandidate(purchaseOrderLineRecord, false, false);
		createAlloc(realCandidate, createPurchaseOrderLine());

		c_OrderLine.deleteOrGuardRealPurchaseCandidate(purchaseOrderLineRecord); // must not throw

		assertThat(getAllCandidateRecords())
				.anyMatch(record -> record.getC_PurchaseCandidate_ID() == realCandidate.getC_PurchaseCandidate_ID());
	}
}
