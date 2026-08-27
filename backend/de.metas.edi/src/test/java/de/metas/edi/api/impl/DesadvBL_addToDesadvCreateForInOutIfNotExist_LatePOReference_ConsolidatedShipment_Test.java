package de.metas.edi.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.filter.GenerateReceiptScheduleForModelAggregateFilter;
import de.metas.organization.OrgId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.ProductId;
import de.metas.sscc18.ISSCC18CodeBL;
import de.metas.sscc18.impl.SSCC18CodeBL;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.edi
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

/**
 * <b>KNOWN-FAILING BY DESIGN — both tests in this class are RED.</b> They pin what a consolidated
 * shipment <i>must</i> do, not what it does today, and they are expected to stay RED until a human
 * decides how to resolve the gap. Do not "fix" them by relaxing the assertions.
 * <p>
 * <b>Not forgotten failures.</b> The decision is pending a read-only query against the affected
 * production instance, which has to establish whether it can produce a consolidated multi-order
 * shipment at all before the gap's priority can be judged.
 * <p>
 * <b>The scenario.</b> A shipment consolidates lines from two or more source orders. The legacy
 * interceptor {@code M_InOutLine.unsetM_InOut_C_Order_ID} clears {@code M_InOut.C_Order_ID} for such
 * a shipment ({@code EDIDocumentBL#isValidShipment} explicitly tolerates that and falls back to
 * "does any InOutLine reference a C_OrderLine"). Separately, a source order can have been completed
 * while its {@code POReference} was still empty — so no {@code EDI_Desadv} was created for it and none
 * of its {@code C_OrderLine}s carry an {@code EDI_DesadvLine_ID}. The clerk enters the
 * {@code POReference} afterwards; from then on the order looks EDI-relevant again.
 * <p>
 * <b>What {@link DesadvBL#addToDesadvCreateForInOutIfNotExist(I_M_InOut)} does with that combination.</b>
 * It derives the source DESADVs by walking {@code inOutLine → orderLine → desadvLine → desadv}, and the
 * late-POReference order lines have no {@code EDI_DesadvLine_ID}, so that walk skips them. The
 * order-driven fallback that would create the missing DESADV lines is reachable only when
 * {@code inOut.getC_Order_ID() > 0} — never for a consolidated shipment. Two outcomes follow, one per
 * test:
 * <ol>
 *   <li>{@link #consolidatedShipment_allSourceOrdersGotPOReferenceLate_getsADesadvWithDeliveredQtys()} —
 *       <b>every</b> source order is late, so the walk finds nothing at all, the fallback is skipped,
 *       and the remaining {@code POReference} branch finds no pre-existing DESADV to match. The
 *       shipment gets <b>no DESADV whatsoever</b> and nothing is ever exported.</li>
 *   <li>{@link #consolidatedShipment_oneSourceOrderGotPOReferenceLate_itsQtyIsNotDropped()} —
 *       <b>one</b> source order is already wired, so the walk succeeds for that one; the fallback is
 *       therefore skipped again and the late order's line is <b>silently dropped</b>. A DESADV goes
 *       out, understating the delivered qty by exactly that order's share, with no error anywhere.</li>
 * </ol>
 * The first is a visible non-delivery; the second is the dangerous one, because the recipient receives
 * a DESADV that disagrees with the pallet actually shipped.
 */
class DesadvBL_addToDesadvCreateForInOutIfNotExist_LatePOReference_ConsolidatedShipment_Test
{
	private DesadvBL desadvBL;
	private HUTestHelper huTestHelper;

	private I_C_UOM stockUOM;
	private ProductId productId;
	private I_M_HU_PI_Item_Product huPIItemProductRecord;

	private I_C_BPartner recipientBPartner;
	private I_C_BPartner_Location recipientLocation;
	private BPartnerId recipientBPartnerId;

	private int sscc18SerialNo;

	@BeforeEach
	void beforeEach()
	{
		final ReceiptScheduleProducerFactory receiptScheduleProducerFactory =
				new ReceiptScheduleProducerFactory(new GenerateReceiptScheduleForModelAggregateFilter(ImmutableList.of()));
		Services.registerService(IReceiptScheduleProducerFactory.class, receiptScheduleProducerFactory);

		// AdempiereTestHelper.get().init() is done by huTestHelper; it has to run before we register our own SSCC18CodeBL
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();

		sscc18SerialNo = 0;
		final SSCC18CodeBL sscc18CodeBL = new SSCC18CodeBL();
		sscc18CodeBL.setOverrideNextSerialNumberProvider(orgId -> ++sscc18SerialNo);
		Services.registerService(ISSCC18CodeBL.class, sscc18CodeBL);
		Services.get(ISysConfigBL.class).setValue(SSCC18CodeBL.SYSCONFIG_ManufacturerCode, "111111", ClientId.METASFRESH, OrgId.ANY);

		stockUOM = huTestHelper.uomKg;
		final I_M_Product product = BusinessTestHelper.createProduct("product-30013", stockUOM);
		productId = ProductId.ofRepoId(product.getM_Product_ID());

		final I_M_HU_PI huDefIFCO = huTestHelper.createHUDefinition("IFCO-30013", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item materialItem = huTestHelper.createHU_PI_Item_Material(huDefIFCO);
		huPIItemProductRecord = huTestHelper.assignProduct(materialItem, productId, new BigDecimal("5"), stockUOM);

		// a real bpartner + location is needed because the order-driven fallback creates a brand-new
		// EDI_Desadv header, and that requires resolving a bill-to location (OrderBL.getBillToLocationId)
		recipientBPartner = BusinessTestHelper.createBPartner("recipient-30013");
		recipientLocation = BusinessTestHelper.createBPartnerLocation(recipientBPartner);
		recipientBPartnerId = BPartnerId.ofRepoId(recipientBPartner.getC_BPartner_ID());

		desadvBL = DesadvBL.newInstanceForUnitTesting();
	}

	/**
	 * An order that was completed while its {@code POReference} was still empty and got it only
	 * afterwards: it has a {@code POReference} but neither an {@code EDI_Desadv} nor any
	 * {@code C_OrderLine.EDI_DesadvLine_ID}.
	 */
	private I_C_OrderLine createOrderWithLatePOReference(final String poReference, final String qtyOrdered)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(recipientBPartner.getC_BPartner_ID());
		order.setC_BPartner_Location_ID(recipientLocation.getC_BPartner_Location_ID());
		order.setPOReference(poReference);   // entered only after the order was completed
		saveRecord(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setC_BPartner_ID(recipientBPartner.getC_BPartner_ID());
		orderLine.setM_Product_ID(productId.getRepoId());
		orderLine.setM_HU_PI_Item_Product_ID(huPIItemProductRecord.getM_HU_PI_Item_Product_ID());
		orderLine.setC_UOM_ID(stockUOM.getC_UOM_ID());
		orderLine.setQtyEntered(new BigDecimal(qtyOrdered));
		orderLine.setQtyOrdered(new BigDecimal(qtyOrdered));
		orderLine.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.NominalWeight.getCode());
		orderLine.setLine(10);
		saveRecord(orderLine);

		assertThat(orderLine.getEDI_DesadvLine_ID())
				.as("precondition: a late-POReference order line is not wired to any DESADV line")
				.isZero();
		return orderLine;
	}

	/**
	 * The consolidated shipment: {@code C_Order_ID = 0}, which is a 1:1 mimic of what
	 * {@code M_InOutLine.unsetM_InOut_C_Order_ID} leaves behind once lines from a second source order
	 * are added (see the tolerance for it in {@code EDIDocumentBL#isValidShipment}).
	 */
	private I_M_InOut createConsolidatedShipment(final String poReference)
	{
		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setC_Order_ID(0);
		shipment.setPOReference(poReference);
		shipment.setC_BPartner_ID(recipientBPartner.getC_BPartner_ID());
		shipment.setC_BPartner_Location_ID(recipientLocation.getC_BPartner_Location_ID());
		saveRecord(shipment);
		return shipment;
	}

	private void addShipmentLine(final I_M_InOut shipment, final I_C_OrderLine orderLine, final String movementQty)
	{
		final I_M_InOutLine shipmentLine = newInstance(I_M_InOutLine.class);
		shipmentLine.setM_InOut_ID(shipment.getM_InOut_ID());
		shipmentLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		shipmentLine.setM_Product_ID(productId.getRepoId());
		shipmentLine.setC_UOM_ID(stockUOM.getC_UOM_ID());
		shipmentLine.setMovementQty(new BigDecimal(movementQty));
		shipmentLine.setQtyEntered(new BigDecimal(movementQty));
		saveRecord(shipmentLine);
	}

	/**
	 * <b>RED.</b> Every source order of the consolidated shipment got its {@code POReference} late, so
	 * no order line is wired to a DESADV line yet. The line walk finds nothing, and the order-driven
	 * fallback is unreachable because {@code C_Order_ID} is 0 — the shipment ends up with no DESADV at
	 * all and is never exported.
	 */
	@Test
	void consolidatedShipment_allSourceOrdersGotPOReferenceLate_getsADesadvWithDeliveredQtys()
	{
		final I_C_OrderLine orderLineA = createOrderWithLatePOReference("PO-30013-CONSOLIDATED-A", "3");
		final I_C_OrderLine orderLineB = createOrderWithLatePOReference("PO-30013-CONSOLIDATED-B", "5");

		final I_M_InOut shipment = createConsolidatedShipment("PO-30013-CONSOLIDATED-A");
		addShipmentLine(shipment, orderLineA, "3");
		addShipmentLine(shipment, orderLineB, "5");

		// ── invoke ──
		final I_EDI_Desadv result = desadvBL.addToDesadvCreateForInOutIfNotExist(shipment);

		// ── assert ──
		assertThat(result)
				.as("a consolidated shipment whose source orders got their POReference late must still get a DESADV")
				.isNotNull();

		InterfaceWrapperHelper.refresh(orderLineA);
		InterfaceWrapperHelper.refresh(orderLineB);
		assertThat(orderLineA.getEDI_DesadvLine_ID())
				.as("order line A must be wired to a DESADV line")
				.isPositive();
		assertThat(orderLineB.getEDI_DesadvLine_ID())
				.as("order line B must be wired to a DESADV line")
				.isPositive();

		assertThat(result.getSumDeliveredInStockingUOM())
				.as("both source orders' shipped quantities must reach the DESADV")
				.isEqualByComparingTo("8");
	}

	/**
	 * <b>RED.</b> The more dangerous half: source order A is already wired to a DESADV, source order B
	 * got its {@code POReference} late. The line walk succeeds for A, so {@code sequencesByDesadv} is
	 * non-empty and the order-driven fallback is skipped again — B's line is silently dropped. A DESADV
	 * <i>is</i> exported, understating the delivery by B's quantity, with no error anywhere.
	 */
	@Test
	void consolidatedShipment_oneSourceOrderGotPOReferenceLate_itsQtyIsNotDropped()
	{
		// ── source order A: completed with a POReference, so it already has its DESADV and desadv line ──
		final I_EDI_Desadv desadvA = newInstance(I_EDI_Desadv.class);
		desadvA.setPOReference("PO-30013-CONSOLIDATED-A");
		desadvA.setC_BPartner_ID(recipientBPartner.getC_BPartner_ID());
		desadvA.setC_BPartner_Location_ID(recipientLocation.getC_BPartner_Location_ID());
		saveRecord(desadvA);

		final I_EDI_DesadvLine desadvLineA = newInstance(I_EDI_DesadvLine.class);
		desadvLineA.setEDI_Desadv_ID(desadvA.getEDI_Desadv_ID());
		desadvLineA.setM_Product_ID(productId.getRepoId());
		desadvLineA.setC_UOM_ID(stockUOM.getC_UOM_ID());
		desadvLineA.setQtyEntered(new BigDecimal("3"));
		desadvLineA.setQtyDeliveredInStockingUOM(BigDecimal.ZERO);
		desadvLineA.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.NominalWeight.getCode());
		saveRecord(desadvLineA);

		final I_C_Order orderA = newInstance(I_C_Order.class);
		orderA.setC_BPartner_ID(recipientBPartner.getC_BPartner_ID());
		orderA.setC_BPartner_Location_ID(recipientLocation.getC_BPartner_Location_ID());
		orderA.setPOReference("PO-30013-CONSOLIDATED-A");
		orderA.setEDI_Desadv_ID(desadvA.getEDI_Desadv_ID());
		saveRecord(orderA);

		final I_C_OrderLine orderLineA = newInstance(I_C_OrderLine.class);
		orderLineA.setC_Order_ID(orderA.getC_Order_ID());
		orderLineA.setC_BPartner_ID(recipientBPartner.getC_BPartner_ID());
		orderLineA.setEDI_DesadvLine_ID(desadvLineA.getEDI_DesadvLine_ID());
		orderLineA.setM_Product_ID(productId.getRepoId());
		orderLineA.setM_HU_PI_Item_Product_ID(huPIItemProductRecord.getM_HU_PI_Item_Product_ID());
		orderLineA.setC_UOM_ID(stockUOM.getC_UOM_ID());
		orderLineA.setQtyEntered(new BigDecimal("3"));
		orderLineA.setQtyOrdered(new BigDecimal("3"));
		orderLineA.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.NominalWeight.getCode());
		orderLineA.setLine(10);
		saveRecord(orderLineA);

		// ── source order B: got its POReference only after being completed -> no DESADV, no desadv line ──
		final I_C_OrderLine orderLineB = createOrderWithLatePOReference("PO-30013-CONSOLIDATED-B", "5");

		final I_M_InOut shipment = createConsolidatedShipment("PO-30013-CONSOLIDATED-A");
		addShipmentLine(shipment, orderLineA, "3");
		addShipmentLine(shipment, orderLineB, "5");

		// ── invoke ──
		final I_EDI_Desadv result = desadvBL.addToDesadvCreateForInOutIfNotExist(shipment);

		// ── assert ──
		assertThat(result)
				.as("guard: source order A is wired, so a DESADV is found and returned")
				.isNotNull();

		InterfaceWrapperHelper.refresh(orderLineB);
		assertThat(orderLineB.getEDI_DesadvLine_ID())
				.as("order line B (late POReference) must be wired to a DESADV line too")
				.isPositive();

		InterfaceWrapperHelper.refresh(desadvLineA);
		assertThat(desadvLineA.getQtyDeliveredInStockingUOM())
				.as("guard: order A's shipped qty did reach its desadv line")
				.isEqualByComparingTo("3");
	}
}
