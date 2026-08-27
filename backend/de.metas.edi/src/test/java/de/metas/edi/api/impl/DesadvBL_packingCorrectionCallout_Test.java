package de.metas.edi.api.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_Desadv_M_InOut;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.inoutcandidate.callout.M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
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
 * Companion to {@link DesadvBL_recomputeDesadvStatusFromInOuts_Test}: it proves that the
 * <i>mechanism</i> that test's accepted-behaviour case narrates is real, by driving the actual
 * production callout instead of hand-writing its output.
 * <p>
 * {@code DesadvBL_recomputeDesadvStatusFromInOuts_Test
 * .packingCorrectionLoweringTheOrderedQty_flipsTheDesadvToSent_acceptedBehaviour} starts from
 * an {@code M_ShipmentSchedule} whose {@code QtyOrdered_Override} is a literal {@code 8}. That
 * literal is the <b>output of a computation</b>, so a test that hand-sets it proves only "given such
 * an override, the header flips" — not that a Packing-Item correction ever produces one. This test
 * closes exactly that gap:
 * <ol>
 *   <li>it builds an <b>open</b> {@code M_ShipmentSchedule} for 4 TU of a 5 kg/TU packing item;</li>
 *   <li>it sets {@code M_HU_PI_Item_Product_Override_ID} to a 2 kg/TU packing item — the clerk's
 *       correction in the Lieferdisposition window;</li>
 *   <li>it calls the real annotated callout
 *       {@link M_ShipmentSchedule#updateShipmentScheduleQtys} (the one registered in
 *       {@code de.metas.handlingunits.model.validator.Main}), which goes through
 *       {@code IHUPackingAwareBL#setQtyCUFromQtyTU} → {@code ShipmentScheduleHUPackingAware#setQty};</li>
 *   <li>and it asserts where the recomputed qty lands: {@code M_ShipmentSchedule.QtyOrdered_Override}.</li>
 * </ol>
 * Finally it hands that schedule — unmodified — to
 * {@link DesadvBL#updateQtyOrdered_OverrideFromShipSchedAndSave(de.metas.inoutcandidate.model.I_M_ShipmentSchedule)},
 * i.e. what the EDI interceptor {@code de.metas.edi.model.validator.M_ShipmentSchedule} does on the
 * {@code AFTER_CHANGE} of {@code QtyOrdered_Override}, and asserts that the {@code EDI_DesadvLine}
 * ends up with the very value the sibling test fabricates.
 * <p>
 * This test is GREEN by design and stays green: it pins the mechanism only. Whether the resulting
 * header status is acceptable is the customer-contract question, and that is owned by the
 * accepted-behaviour sibling — deliberately not re-asserted here.
 */
class DesadvBL_packingCorrectionCallout_Test
{
	private HUTestHelper huTestHelper;
	private DesadvBL desadvBL;

	private I_C_UOM uomKg;
	private ProductId productId;
	private I_M_HU_PI_Item_Product piItemProduct_5kgPerTU;
	private I_M_HU_PI_Item_Product piItemProduct_2kgPerTU;

	@BeforeEach
	void beforeEach()
	{
		// HUTestHelper does the AdempiereTestHelper.get().init() itself
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();

		// see the same comment in DesadvBL_recomputeDesadvStatusFromInOuts_Test: the no-context
		// getBooleanValue overload is scoped to ClientId.SYSTEM
		Services.get(ISysConfigBL.class).setValue(
				EDIWorkpackageProcessor.SYS_CONFIG_OneDesadvPerShipment,
				true,
				ClientId.SYSTEM,
				OrgId.ANY);

		uomKg = huTestHelper.uomKg;
		final I_M_Product product = BusinessTestHelper.createProduct("Pilzmischung", uomKg);
		productId = ProductId.ofRepoId(product.getM_Product_ID());

		piItemProduct_5kgPerTU = createPackingItem("TU-5kg", "5");
		piItemProduct_2kgPerTU = createPackingItem("TU-2kg", "2");

		desadvBL = DesadvBL.newInstanceForUnitTesting();
	}

	/** A transport-unit packing instruction that holds {@code qtyCUsPerTU} kg of the test product. */
	private I_M_HU_PI_Item_Product createPackingItem(final String name, final String qtyCUsPerTU)
	{
		final I_M_HU_PI huPI = huTestHelper.createHUDefinition(name, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item materialItem = huTestHelper.createHU_PI_Item_Material(huPI);
		return huTestHelper.assignProduct(materialItem, productId, new BigDecimal(qtyCUsPerTU), uomKg);
	}

	@Test
	void packingItemCorrection_writesTheRecomputedQty_intoQtyOrdered_Override_andOnToTheDesadvLine()
	{
		// ── the DESADV side: 20 kg ordered (4 TU x 5 kg), 10 kg delivered and already Sent ──
		final I_EDI_Desadv desadv = newInstance(I_EDI_Desadv.class);
		desadv.setFulfillmentPercent(new BigDecimal("50"));
		desadv.setEDI_ExportStatus(EDIExportStatus.Pending.getCode());
		saveRecord(desadv);

		final I_EDI_DesadvLine desadvLine = newInstance(I_EDI_DesadvLine.class);
		desadvLine.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		desadvLine.setQtyOrdered(new BigDecimal("20"));
		desadvLine.setQtyOrdered_Override(null);
		desadvLine.setQtyDeliveredInStockingUOM(new BigDecimal("10"));
		saveRecord(desadvLine);

		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setEDI_ExportStatus(EDIExportStatus.Sent.getCode());
		inOut.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		saveRecord(inOut);

		final I_EDI_Desadv_M_InOut junction = newInstance(I_EDI_Desadv_M_InOut.class);
		junction.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		junction.setM_InOut_ID(inOut.getM_InOut_ID());
		saveRecord(junction);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
		orderLine.setM_Product_ID(productId.getRepoId());
		saveRecord(orderLine);

		// ── the OPEN shipment disposition: 4 TU of the 5 kg packing item ──
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		schedule.setM_Product_ID(productId.getRepoId());
		schedule.setC_UOM_ID(uomKg.getC_UOM_ID());
		schedule.setQtyOrdered_TU(new BigDecimal("4"));
		schedule.setQtyTU_Calculated(new BigDecimal("4"));
		schedule.setM_HU_PI_Item_Product_Calculated(piItemProduct_5kgPerTU);
		schedule.setM_HU_PI_Item_Product_ID(piItemProduct_5kgPerTU.getM_HU_PI_Item_Product_ID());
		schedule.setQtyDelivered(new BigDecimal("10"));
		schedule.setIsClosed(false);
		saveRecord(schedule);

		assertThat(InterfaceWrapperHelper.isNull(schedule, I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override))
				.as("precondition: no override yet — the column must be SQL NULL before the correction")
				.isTrue();

		// ── the packing correction the clerk makes: 4 TU x 5 kg becomes 4 TU x 2 kg ──
		schedule.setM_HU_PI_Item_Product_Override_ID(piItemProduct_2kgPerTU.getM_HU_PI_Item_Product_ID());
		saveRecord(schedule);

		// invoke the REAL production callout — no hand-written override anywhere in this test
		M_ShipmentSchedule.instance.updateShipmentScheduleQtys(schedule, null);
		saveRecord(schedule);

		// ── the claim under test: the callout writes QtyOrdered_TU x CUsPerTU into QtyOrdered_Override ──
		assertThat(schedule.isClosed())
				.as("the callout must not close anything — the disposition stays open")
				.isFalse();
		assertThat(schedule.getQtyOrdered_Override())
				.as("the packing correction recomputes ordered as 4 TU x 2 kg = 8 kg and writes it "
						+ "into M_ShipmentSchedule.QtyOrdered_Override")
				.isEqualByComparingTo("8");

		// ── and that value reaches the EDI_DesadvLine unchanged, because the schedule is OPEN ──
		desadvBL.updateQtyOrdered_OverrideFromShipSchedAndSave(schedule);

		InterfaceWrapperHelper.refresh(desadvLine);
		assertThat(desadvLine.getQtyOrdered_Override())
				.as("an OPEN schedule's override is copied to the desadv line verbatim — this is the "
						+ "value DesadvBL_recomputeDesadvStatusFromInOuts_Test hand-sets as a literal")
				.isEqualByComparingTo("8");
	}
}
