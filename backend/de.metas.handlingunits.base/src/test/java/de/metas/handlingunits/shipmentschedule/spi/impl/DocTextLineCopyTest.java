/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.shipmentschedule.spi.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.doctextline.DocTextLine;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.TextLineScope;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.ShipmentScheduleAllowConsolidatePredicateComposite;
import de.metas.inoutcandidate.api.impl.DefaultInOutGenerateResult;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.order.DeliveryRule;
import de.metas.order.OrderId;
import de.metas.freighcost.FreightCostRule;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Doc_TextLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the shipment producer's copy of an order's text lines onto the shipment it creates:
 * <ul>
 * <li>a partial shipment receives only the text lines whose run it contains ({@link #partialShipment_copiesOnlyTextLinesWhoseRunItContains()})
 * <li>a "belongs with the following lines" line is positioned at its own original {@code Line}, which sorts
 * immediately before its run's first shipment line, when that run is on the shipment
 * <li>a "whole document" line still prints, at the head of the shipment's lines, when none of its run made it
 * onto that particular shipment ({@link #documentScopeLine_printsAtHead_whenRunAbsent_ownPosition_whenRunPresent()})
 * <li>the copies are independent of the order's own text lines from the moment they are written
 * ({@link #editingTheOrderAfterwards_doesNotChangeAShipmentThatAlreadyExists()})
 * <li>a Line-number collision on every usable run member falls back to head placement, and a collision on
 * only SOME run members does not poison the whole run
 * <li>three carry-rule boundary cases the design states explicitly: block folding of consecutive text lines,
 * and both inclusive ({@code >=}) ties
 * <li>an inactive order line or text line is ignored as a run/block boundary
 * <li>a shipment aggregating two orders resolves each order's text lines independently (the multi-order
 * decision, documented in {@link TextLineShipmentCopier}'s class javadoc)
 * </ul>
 * <p>
 * Lives beside {@link InOutProducerFromShipmentScheduleWithHUTest} rather than next to
 * {@code DocTextLineRepositoryTest} (module {@code de.metas.business}): the code under test --
 * {@link TextLineShipmentCopier}, hooked into {@link InOutProducerFromShipmentScheduleWithHU} -- lives in this
 * module, and exercising it faithfully needs the real shipment-producer pipeline (candidate selection, the
 * Line-number-collision guard, the hook point relative to it), none of which {@code de.metas.business} can
 * reach.
 * <p>
 * <b>What this harness cannot reach:</b> it saves records through the in-memory {@code POJOLookupMap}, never
 * through the legacy {@code MInOutLine.beforeSave} that a real database runs, which rewrites a zero
 * {@code Line} to {@code MAX(Line)+10} before the row is ever written. Because of that, this suite cannot
 * observe what a collided shipment line's {@code Line} actually becomes on a real database. It does not need
 * to: {@link TextLineShipmentCopier} is deliberately written to never read a shipment line's {@code Line} to
 * detect a collision -- it consults the producer's own pre-computed collision set instead (see its javadoc).
 * That set ({@code ShipmentLineNoInfo}) is fed a Line value {@code ShipmentLineBuilder} captures BEFORE it
 * saves each shipment line, precisely so the save-time rewrite above can never be mistaken for a collision --
 * this harness and a real database agree on that value for exactly that reason, even though only a real
 * database ever performs the rewrite itself. The collision tests below pin the set-based behaviour directly,
 * which is what the fix actually relies on; {@link #producerAcrossTwoShipments_collisionSetIsSharedAcrossThem()}
 * covers the set's own, separate limitation -- it is never reset between shipments of one producer run.
 */
class DocTextLineCopyTest
{
	private final DocTextLineRepository docTextLineRepository = new DocTextLineRepository();

	private ITrxItemProcessorExecutorService trxItemProcessorExecutorService;
	private IHUContext huContext;
	private BPartnerLocationId bpartnerAndLocationId;
	private WarehouseId warehouseId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		SpringContextHolder.registerJUnitBean(new OrderEmailPropagationSysConfigRepository(sysConfigBL));
		Loggables.temporarySetLoggable(Loggables.console());

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		Services.registerService(IShipmentScheduleInvalidateBL.class, new ShipmentScheduleInvalidateBL(new PickingBOMService()));
		Services.get(IShipmentScheduleHandlerBL.class).registerHandler(OrderLineShipmentScheduleHandler.newInstanceWithoutExtensions());
		SpringContextHolder.registerJUnitBean(new ShipmentScheduleAllowConsolidatePredicateComposite(ImmutableList.of()));

		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);

		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH); // needed for notifications

		huContext = huContextFactory.createMutableHUContext();
		createDocType(DocBaseAndSubType.of(DocBaseType.Shipment));
		bpartnerAndLocationId = bpartnerAndLocation("BP");
		warehouseId = warehouse("WH");

		// virtual packing-instruction chain -- lets a shipment line be created with no real HU picking
		{
			final I_M_HU_PI pi = newInstance(I_M_HU_PI.class);
			pi.setM_HU_PI_ID(HuPackingInstructionsId.VIRTUAL.getRepoId());
			saveRecord(pi);

			final I_M_HU_PI_Version piv = newInstance(I_M_HU_PI_Version.class);
			piv.setM_HU_PI_ID(pi.getM_HU_PI_ID());
			piv.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
			piv.setIsCurrent(true);
			saveRecord(piv);

			final I_M_HU_PI_Item pii = newInstance(I_M_HU_PI_Item.class);
			pii.setM_HU_PI_Version_ID(piv.getM_HU_PI_Version_ID());
			pii.setM_HU_PI_Item_ID(HuPackingInstructionsItemId.VIRTUAL.getRepoId());
			saveRecord(pii);

			final I_M_HU_PI_Item_Product pip = newInstance(I_M_HU_PI_Item_Product.class);
			pip.setM_HU_PI_Item_ID(pii.getM_HU_PI_Item_ID());
			pip.setM_HU_PI_Item_Product_ID(HUPIItemProductId.VIRTUAL_HU.getRepoId());
			pip.setIsInfiniteCapacity(true);
			pip.setIsAllowAnyProduct(true);
			saveRecord(pip);
		}
	}

	private I_C_UOM uom(final String name)
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setX12DE355(name);
		saveRecord(uom);
		return uom;
	}

	private ProductId product(final String name, final I_C_UOM uom)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private BPartnerLocationId bpartnerAndLocation(final String name)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setValue(name);
		bpartner.setName(name);
		saveRecord(bpartner);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		saveRecord(bpLocation);

		return BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
	}

	private WarehouseId warehouse(final String name)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setName(name);
		saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	private void createDocType(final DocBaseAndSubType docBaseAndSubType)
	{
		final I_C_DocType docTypeRecord = newInstance(I_C_DocType.class);
		docTypeRecord.setDocBaseType(docBaseAndSubType.getDocBaseType().getCode());
		docTypeRecord.setDocSubType(docBaseAndSubType.getDocSubType().getCode());
		saveRecord(docTypeRecord);
	}

	private OrderId order()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		// a non-custom freight cost rule -- ShipmentScheduleBL#isConsolidateVetoedByOrderOfSched vetoes
		// consolidation for FreightCostRule=FixPrice; an unset value resolves ambiguously (observed to differ
		// depending on which other tests already ran in the same JVM fork), so it is pinned explicitly here.
		order.setFreightCostRule(FreightCostRule.Calculated.getCode());
		saveRecord(order);
		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	/** An order line at an explicit {@code Line} -- the collision guard and the run/position arithmetic both depend on real, distinct Lines. */
	private I_C_OrderLine orderLine(final OrderId orderId, final ProductId productId, final I_C_UOM uom, final int line)
	{
		return orderLine(orderId, productId, uom, line, true);
	}

	private I_C_OrderLine orderLine(final OrderId orderId, final ProductId productId, final I_C_UOM uom, final int line, final boolean active)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(orderId.getRepoId());
		orderLine.setM_Product_ID(productId.getRepoId());
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		orderLine.setQtyOrdered(new BigDecimal("5"));
		orderLine.setLine(line);
		orderLine.setIsActive(active);
		saveRecord(orderLine);
		return orderLine;
	}

	private ShipmentScheduleWithHU candidate(final I_C_OrderLine orderLine, final ProductId productId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setM_Warehouse_ID(warehouseId.getRepoId());
		shipmentSchedule.setC_BPartner_ID(bpartnerAndLocationId.getBpartnerId().getRepoId());
		shipmentSchedule.setC_BPartner_Location_ID(bpartnerAndLocationId.getRepoId());
		shipmentSchedule.setM_Product_ID(productId.getRepoId());
		shipmentSchedule.setQtyOrdered_Calculated(new BigDecimal("5"));
		shipmentSchedule.setQtyToDeliver(new BigDecimal("5"));
		shipmentSchedule.setDeliveryRule(DeliveryRule.AVAILABILITY.getCode());
		shipmentSchedule.setC_Order_ID(orderLine.getC_Order_ID());
		shipmentSchedule.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		shipmentSchedule.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_OrderLine.class));
		shipmentSchedule.setRecord_ID(orderLine.getC_OrderLine_ID());
		saveRecord(shipmentSchedule);

		return ShipmentScheduleWithHU.ofShipmentScheduleWithoutHu(
				huContext,
				shipmentSchedule,
				StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("5"), productId),
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);
	}

	private DocTextLine textLine(final OrderId orderId, final TextLineScope scope, final int line, final String text)
	{
		return textLine(orderId, scope, line, text, true);
	}

	private DocTextLine textLine(final OrderId orderId, final TextLineScope scope, final int line, final String text, final boolean active)
	{
		final I_C_Doc_TextLine record = newInstance(I_C_Doc_TextLine.class);
		record.setC_Order_ID(orderId.getRepoId());
		record.setTextLine(text);
		record.setLine(BigDecimal.valueOf(line));
		record.setTextLineScope(scope.getCode());
		record.setIsActive(active);
		saveRecord(record);

		return DocTextLine.builder()
				.id(de.metas.doctextline.DocTextLineId.ofRepoId(record.getC_Doc_TextLine_ID()))
				.documentRef(DocTextLineDocumentRef.ofOrderId(orderId))
				.textLine(text)
				.line(record.getLine())
				.scope(scope)
				.build();
	}

	private InOutGenerateResult process(final List<ShipmentScheduleWithHU> candidates)
	{
		final InOutProducerFromShipmentScheduleWithHU producer = new InOutProducerFromShipmentScheduleWithHU(new DefaultInOutGenerateResult(true));
		return trxItemProcessorExecutorService
				.<ShipmentScheduleWithHU, InOutGenerateResult>createExecutor()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setProcessor(producer)
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.process(candidates);
	}

	private I_M_InOut singleShipment(final InOutGenerateResult result)
	{
		assertThat(result.getInOuts()).hasSize(1);
		return result.getInOuts().iterator().next();
	}

	private List<DocTextLine> textLinesOnShipment(final I_M_InOut shipment)
	{
		return docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofInOutId(InOutId.ofRepoId(shipment.getM_InOut_ID())));
	}

	private int lineOf(final I_M_InOut shipment, final I_C_OrderLine orderLine)
	{
		final List<I_M_InOutLine> shipmentLines = Services.get(IInOutDAO.class).retrieveLines(shipment);
		return shipmentLines.stream()
				.filter(sl -> sl.getC_OrderLine_ID() == orderLine.getC_OrderLine_ID())
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("no shipment line for order line " + orderLine.getC_OrderLine_ID()))
				.getLine();
	}

	private DocTextLine copiedTextLine(final I_M_InOut shipment, final String text)
	{
		return textLinesOnShipment(shipment).stream()
				.filter(l -> text.equals(l.getTextLine()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("not copied onto the shipment: " + text));
	}

	@Test
	void partialShipment_copiesOnlyTextLinesWhoseRunItContains()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));
		final ProductId productB = product("B", uom("uom"));
		final ProductId productC = product("C", uom("uom"));

		final I_C_OrderLine ol10 = orderLine(orderId, productA, uom("uom"), 10);
		final I_C_OrderLine ol20 = orderLine(orderId, productB, uom("uom"), 20);
		final I_C_OrderLine ol30 = orderLine(orderId, productC, uom("uom"), 30);

		// each text line's run is exactly one order line: T@5 -> {ol10}, T@15 -> {ol20}, T@25 -> {ol30}
		final DocTextLine tA = textLine(orderId, TextLineScope.Following, 5, "before A");
		final DocTextLine tB = textLine(orderId, TextLineScope.Following, 15, "before B");
		final DocTextLine tC = textLine(orderId, TextLineScope.Following, 25, "before C");

		// partial shipment: ship ol10 and ol30, deliberately skip ol20 (and its own text line tB)
		final InOutGenerateResult result = process(ImmutableList.of(
				candidate(ol10, productA),
				candidate(ol30, productC)));

		final I_M_InOut shipment = singleShipment(result);
		final List<DocTextLine> copied = textLinesOnShipment(shipment);

		assertThat(copied).extracting(DocTextLine::getTextLine)
				.as("only the text lines whose run is on this shipment are copied")
				.containsExactly("before A", "before C");

		// stored TIED with the run's own (single) member -- see TextLineShipmentCopier's javadoc for why a
		// tie, not the text line's own original position, is what "immediately before the run" requires.
		assertThat(copiedTextLine(shipment, "before A").getLine())
				.isEqualByComparingTo(BigDecimal.valueOf(lineOf(shipment, ol10)));
		assertThat(copiedTextLine(shipment, "before C").getLine())
				.isEqualByComparingTo(BigDecimal.valueOf(lineOf(shipment, ol30)));

		// tB's own run (ol20) never shipped -- must not have been copied under any position
		assertThat(copied).extracting(DocTextLine::getTextLine).doesNotContain("before B");

		// the source rows are untouched
		assertThat(docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId)))
				.extracting(DocTextLine::getId)
				.containsExactlyInAnyOrder(tA.getId(), tB.getId(), tC.getId());
	}

	@Test
	void fullShipment_followingScopeLine_positionedBeforeItsRun()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));

		final I_C_OrderLine ol10 = orderLine(orderId, productA, uom("uom"), 10);
		final I_C_OrderLine ol20 = orderLine(orderId, productA, uom("uom"), 20);

		// the only text line in the order -- its run is unbounded to the right: both article lines
		textLine(orderId, TextLineScope.Following, 5, "intro");

		final InOutGenerateResult result = process(ImmutableList.of(
				candidate(ol10, productA),
				candidate(ol20, productA)));

		final I_M_InOut shipment = singleShipment(result);
		final List<DocTextLine> copied = textLinesOnShipment(shipment);

		assertThat(copied).hasSize(1);
		assertThat(copied.get(0).getTextLine()).isEqualTo("intro");
		// stored TIED with the run's first (numerically smallest) member -- ol10 -- not at its own original
		// position (5): see TextLineShipmentCopier's javadoc for why the tie is what "immediately before the
		// run" actually requires.
		assertThat(copied.get(0).getLine()).isEqualByComparingTo(BigDecimal.valueOf(lineOf(shipment, ol10)));
		assertThat(copied.get(0).getLine()).isLessThan(BigDecimal.valueOf(lineOf(shipment, ol20)));
	}

	@Test
	void documentScopeLine_printsAtHead_whenRunAbsent_ownPosition_whenRunPresent()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));
		final ProductId productB = product("B", uom("uom"));
		final ProductId productC = product("C", uom("uom"));

		final I_C_OrderLine ol10 = orderLine(orderId, productA, uom("uom"), 10);
		final I_C_OrderLine ol20 = orderLine(orderId, productB, uom("uom"), 20);
		final I_C_OrderLine ol30 = orderLine(orderId, productC, uom("uom"), 30);

		// present's run = [5, 25) = {ol10, ol20}; absent's run = [25, inf) = {ol30}
		textLine(orderId, TextLineScope.Document, 5, "present");
		final DocTextLine absent = textLine(orderId, TextLineScope.Document, 25, "absent");

		// partial shipment: ship ol10 and ol20 only -- ol30 (absent's whole run) never ships
		final InOutGenerateResult result = process(ImmutableList.of(
				candidate(ol10, productA),
				candidate(ol20, productB)));

		final I_M_InOut shipment = singleShipment(result);
		final List<DocTextLine> copied = textLinesOnShipment(shipment);

		assertThat(copied).extracting(DocTextLine::getTextLine)
				.as("a whole-document line is always carried, run present or not")
				.containsExactlyInAnyOrder("present", "absent");

		// tied with the run's first member (ol10), not present's own original position (5).
		assertThat(copiedTextLine(shipment, "present").getLine())
				.isEqualByComparingTo(BigDecimal.valueOf(lineOf(shipment, ol10)));

		// head case: no run member is on this shipment -- printed strictly before every real shipment line,
		// at the source line's own position minus the head offset.
		assertThat(copiedTextLine(shipment, "absent").getLine()).isEqualByComparingTo(absent.getLine().subtract(BigDecimal.valueOf(1_000_000)));
		assertThat(copiedTextLine(shipment, "absent").getLine()).isLessThan(BigDecimal.valueOf(lineOf(shipment, ol10)));
		assertThat(copiedTextLine(shipment, "absent").getLine()).isLessThan(BigDecimal.valueOf(lineOf(shipment, ol20)));
	}

	@Test
	void editingTheOrderAfterwards_doesNotChangeAShipmentThatAlreadyExists()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));
		final I_C_OrderLine ol10 = orderLine(orderId, productA, uom("uom"), 10);

		final DocTextLine original = textLine(orderId, TextLineScope.Following, 5, "before edit");

		final InOutGenerateResult result = process(ImmutableList.of(candidate(ol10, productA)));
		final I_M_InOut shipment = singleShipment(result);

		assertThat(textLinesOnShipment(shipment)).extracting(DocTextLine::getTextLine).containsExactly("before edit");

		// edit the ORDER's own text line after the shipment was created
		docTextLineRepository.updateTextAndScope(original.getId(), "after edit", TextLineScope.Following);

		assertThat(textLinesOnShipment(shipment))
				.as("editing the order's text line afterwards must not change a delivery note that already exists")
				.extracting(DocTextLine::getTextLine)
				.containsExactly("before edit");

		// delete the ORDER's own text line too -- the shipment's own copy must still stand
		docTextLineRepository.deleteById(original.getId());

		assertThat(docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId))).isEmpty();
		assertThat(textLinesOnShipment(shipment))
				.as("deleting the order's text line afterwards must not delete the shipment's own copy")
				.extracting(DocTextLine::getTextLine)
				.containsExactly("before edit");
	}

	@Test
	void collisionOnEveryRunMember_printsAtHead()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));
		final ProductId productB = product("B", uom("uom"));

		// two order lines sharing the same Line -- an aggregating shipment carrying both collides
		final I_C_OrderLine ol10a = orderLine(orderId, productA, uom("uom"), 10);
		final I_C_OrderLine ol10b = orderLine(orderId, productB, uom("uom"), 10);

		// its run is unbounded to the right, so it covers both colliding order lines -- and both are the
		// run's ONLY members, so with both collided there is no usable run member at all
		final DocTextLine intro = textLine(orderId, TextLineScope.Following, 5, "intro");

		final InOutGenerateResult result = process(ImmutableList.of(
				candidate(ol10a, productA),
				candidate(ol10b, productB)));

		final I_M_InOut shipment = singleShipment(result);

		// carried (the run IS present -- both colliding lines are genuinely on the shipment), but with no
		// usable position reference it is printed at the head rather than mispositioned at whatever a
		// collision-renumbered Line happens to be.
		assertThat(copiedTextLine(shipment, "intro").getLine())
				.isEqualByComparingTo(intro.getLine().subtract(BigDecimal.valueOf(1_000_000)));
	}

	@Test
	void collisionOnSomeRunMembers_stillTiesWithTheFirstUsableMember()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));
		final ProductId productB = product("B", uom("uom"));
		final ProductId productC = product("C", uom("uom"));

		// ol10a and ol10b collide with each other; ol30 is a third, unrelated, non-colliding run member
		final I_C_OrderLine ol10a = orderLine(orderId, productA, uom("uom"), 10);
		final I_C_OrderLine ol10b = orderLine(orderId, productB, uom("uom"), 10);
		final I_C_OrderLine ol30 = orderLine(orderId, productC, uom("uom"), 30);

		textLine(orderId, TextLineScope.Following, 5, "intro");

		// all three ship together: ol10a/ol10b collide, ol30 does not -- the run has a usable member
		final InOutGenerateResult result = process(ImmutableList.of(
				candidate(ol10a, productA),
				candidate(ol10b, productB),
				candidate(ol30, productC)));

		final I_M_InOut shipment = singleShipment(result);

		// a single unusable (collided) run member must not poison the run when another, usable member is
		// present -- positioned tied with that usable member (ol30), not punted to the head.
		assertThat(copiedTextLine(shipment, "intro").getLine())
				.isEqualByComparingTo(BigDecimal.valueOf(lineOf(shipment, ol30)));

		// pin the ORDERING too, not just the stored value: in this in-memory harness a collided line's own
		// Line is left at 0 by the collision guard (a real database instead renumbers it to the END of the
		// document -- see TextLineShipmentCopier's javadoc), so ol10a/ol10b render BEFORE "intro" here, the
		// opposite of what a real database would show. That is a harness artifact, not a defect: "intro" is
		// correctly placed relative to its actual, usable anchor (ol30) either way -- do not "fix" this
		// ordering back to put "intro" ahead of the collided lines, that would only be right in this harness.
		assertThat(lineOf(shipment, ol10a)).isZero();
		assertThat(lineOf(shipment, ol10b)).isZero();
		assertThat(copiedTextLine(shipment, "intro").getLine()).isGreaterThan(BigDecimal.ZERO);
	}

	@Test
	void consecutiveTextLines_shareTheRunOfTheLastLineInTheirBlock()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));
		final I_C_OrderLine ol30 = orderLine(orderId, productA, uom("uom"), 30);

		// two adjacent "following" text lines with no article between them -- they fold into one block, and
		// both share the run of the LAST one (ol30), not just t2's.
		textLine(orderId, TextLineScope.Following, 15, "t1");
		textLine(orderId, TextLineScope.Following, 20, "t2");

		final InOutGenerateResult result = process(ImmutableList.of(candidate(ol30, productA)));
		final I_M_InOut shipment = singleShipment(result);

		assertThat(textLinesOnShipment(shipment))
				.as("t1 shares t2's run via block folding, not just its own (empty) one")
				.extracting(DocTextLine::getTextLine)
				.containsExactlyInAnyOrder("t1", "t2");

		// t2 (the LAST member of the block) ties with the anchor (ol30); t1 (the earlier member) sits one
		// block-member step below it -- distinct from t2, and in their original relative order.
		final BigDecimal anchor = BigDecimal.valueOf(lineOf(shipment, ol30));
		assertThat(copiedTextLine(shipment, "t2").getLine()).isEqualByComparingTo(anchor);
		assertThat(copiedTextLine(shipment, "t1").getLine()).isEqualByComparingTo(anchor.subtract(new BigDecimal("0.0001")));
		assertThat(copiedTextLine(shipment, "t1").getLine()).isLessThan(copiedTextLine(shipment, "t2").getLine());
	}

	@Test
	void textLineTiedWithTheBoundaryArticle_stillCapsItsOwnBlockAtTheTie()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));
		final I_C_OrderLine ol10 = orderLine(orderId, productA, uom("uom"), 10);
		final I_C_OrderLine ol20 = orderLine(orderId, productA, uom("uom"), 20);

		// t1 is tied with ol10 -- inclusive (>=) means ol10 caps t1's own block, so t2 (a later text line)
		// does NOT fold into t1's block and does not inherit ol10 into its own run.
		final DocTextLine t1 = textLine(orderId, TextLineScope.Following, 10, "t1");
		textLine(orderId, TextLineScope.Following, 15, "t2");

		// ship only ol10 -- t2's own run is {ol20}, never on this shipment
		final InOutGenerateResult result = process(ImmutableList.of(candidate(ol10, productA)));
		final I_M_InOut shipment = singleShipment(result);

		assertThat(textLinesOnShipment(shipment))
				.as("t1's tie with ol10 caps its own block; t2 is a separate block whose run (ol20) is absent")
				.extracting(DocTextLine::getTextLine)
				.containsExactly("t1");
		assertThat(copiedTextLine(shipment, "t1").getLine()).isEqualByComparingTo(t1.getLine());
	}

	@Test
	void articleLineTiedWithTheRunsLowerBound_stillBelongsToTheRun()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));
		final I_C_OrderLine ol10 = orderLine(orderId, productA, uom("uom"), 10);
		orderLine(orderId, productA, uom("uom"), 20);

		// t's block ends at its own Line (10), tied with ol10 -- inclusive (>=) on the run's OWN lower bound
		// means ol10 still belongs to t's run despite the tie.
		final DocTextLine t = textLine(orderId, TextLineScope.Following, 10, "t");

		// ship only ol10 (the tied line) -- if the tie were excluded, t's run would be {ol20} only, absent here
		final InOutGenerateResult result = process(ImmutableList.of(candidate(ol10, productA)));
		final I_M_InOut shipment = singleShipment(result);

		assertThat(textLinesOnShipment(shipment))
				.as("the tied article (ol10) belongs to t's run despite the exact-Line tie")
				.extracting(DocTextLine::getTextLine)
				.containsExactly("t");
		assertThat(copiedTextLine(shipment, "t").getLine()).isEqualByComparingTo(t.getLine());
	}

	@Test
	void inactiveTextLine_isIgnoredAsABlockBoundary()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));
		final I_C_OrderLine ol30 = orderLine(orderId, productA, uom("uom"), 30);

		// same shape as the block-folding test, but t2 is INACTIVE: it must not fold with t1, and it must
		// never itself be copied.
		textLine(orderId, TextLineScope.Following, 15, "t1");
		textLine(orderId, TextLineScope.Following, 20, "ghost", false);

		final InOutGenerateResult result = process(ImmutableList.of(candidate(ol30, productA)));
		final I_M_InOut shipment = singleShipment(result);

		assertThat(textLinesOnShipment(shipment))
				.as("t1's own (unfolded, since the sibling is inactive) run is still {ol30}; the inactive sibling never appears")
				.extracting(DocTextLine::getTextLine)
				.containsExactly("t1");
		// t1 is a single-member block on its own -- ties with the anchor (ol30).
		assertThat(copiedTextLine(shipment, "t1").getLine()).isEqualByComparingTo(BigDecimal.valueOf(lineOf(shipment, ol30)));
	}

	@Test
	void inactiveOrderLine_isIgnoredAsANextArticleBoundary()
	{
		final OrderId orderId = order();
		final ProductId productA = product("A", uom("uom"));
		orderLine(orderId, productA, uom("uom"), 20, false); // inactive -- must not act as a boundary
		final I_C_OrderLine ol40 = orderLine(orderId, productA, uom("uom"), 40);

		// t1 and t2 fold into one block only if the ACTIVE next-article is ol40 (40), not the inactive line
		// at 20: with the inactive line wrongly acting as the boundary, t1's block would cap at 20 and t2
		// would not fold in, leaving t1 with an empty run (since the only active order line, ol40, is at 40).
		textLine(orderId, TextLineScope.Following, 15, "t1");
		textLine(orderId, TextLineScope.Following, 25, "t2");

		final InOutGenerateResult result = process(ImmutableList.of(candidate(ol40, productA)));
		final I_M_InOut shipment = singleShipment(result);

		assertThat(textLinesOnShipment(shipment))
				.as("the inactive order line at 20 must not cap t1's block short of the active ol40 at 40")
				.extracting(DocTextLine::getTextLine)
				.containsExactlyInAnyOrder("t1", "t2");

		// both fold into one block anchored on ol40: t2 (last) ties with it, t1 sits one step below.
		final BigDecimal anchor = BigDecimal.valueOf(lineOf(shipment, ol40));
		assertThat(copiedTextLine(shipment, "t2").getLine()).isEqualByComparingTo(anchor);
		assertThat(copiedTextLine(shipment, "t1").getLine()).isEqualByComparingTo(anchor.subtract(new BigDecimal("0.0001")));
	}

	/**
	 * Whether two different orders actually consolidate onto one shipment is a business decision this test
	 * does not need and must not depend on ({@code ShipmentScheduleBL#isConsolidateVetoedByOrderOfSched} and
	 * friends decide that, and it is not this class's concern) -- so this test builds the "one shipment
	 * carrying lines of two orders" state directly (shipment + shipment lines, no schedules/candidates
	 * involved) and calls {@link TextLineShipmentCopier} on it, exactly as
	 * {@link InOutProducerFromShipmentScheduleWithHU#processCurrentShipment()} would once such a shipment
	 * exists, whichever route created it.
	 */
	@Test
	void twoOrdersOnOneShipment_resolveTheirTextLinesIndependently()
	{
		final OrderId orderX = order();
		final I_C_OrderLine olX10 = orderLine(orderX, product("A", uom("uom")), uom("uom"), 10);
		final I_C_OrderLine olX30 = orderLine(orderX, product("C", uom("uom")), uom("uom"), 30);
		// "from X"'s run is {olX30} -- but on the shipment, order Y's own line (olY20, below) numerically
		// falls between the text's own order-side position (12) and its run: exactly the shape that must
		// NOT let another order's article print between a text line and its run.
		textLine(orderX, TextLineScope.Following, 12, "from X");

		final OrderId orderY = order();
		final I_C_OrderLine olY20 = orderLine(orderY, product("B", uom("uom")), uom("uom"), 20);
		textLine(orderY, TextLineScope.Following, 15, "from Y");

		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		saveRecord(shipment);

		final I_M_InOutLine shipmentLineX10 = newInstance(I_M_InOutLine.class);
		shipmentLineX10.setM_InOut_ID(shipment.getM_InOut_ID());
		shipmentLineX10.setC_Order_ID(orderX.getRepoId());
		shipmentLineX10.setC_OrderLine_ID(olX10.getC_OrderLine_ID());
		shipmentLineX10.setLine(olX10.getLine());
		saveRecord(shipmentLineX10);

		final I_M_InOutLine shipmentLineY20 = newInstance(I_M_InOutLine.class);
		shipmentLineY20.setM_InOut_ID(shipment.getM_InOut_ID());
		shipmentLineY20.setC_Order_ID(orderY.getRepoId());
		shipmentLineY20.setC_OrderLine_ID(olY20.getC_OrderLine_ID());
		shipmentLineY20.setLine(olY20.getLine());
		saveRecord(shipmentLineY20);

		final I_M_InOutLine shipmentLineX30 = newInstance(I_M_InOutLine.class);
		shipmentLineX30.setM_InOut_ID(shipment.getM_InOut_ID());
		shipmentLineX30.setC_Order_ID(orderX.getRepoId());
		shipmentLineX30.setC_OrderLine_ID(olX30.getC_OrderLine_ID());
		shipmentLineX30.setLine(olX30.getLine());
		saveRecord(shipmentLineX30);

		// see the multi-order decision in TextLineShipmentCopier's class javadoc: each order's text lines are
		// resolved against that order's own sequence, independently of the other order sharing the shipment.
		new TextLineShipmentCopier().copyTextLinesToShipment(shipment, ImmutableSet.of());

		assertThat(textLinesOnShipment(shipment))
				.as("both orders' text lines are carried, each resolved against its own order")
				.extracting(DocTextLine::getTextLine)
				.containsExactlyInAnyOrder("from X", "from Y");

		// "from X" ties with olX30 (its run's only member) -- NOT with its own original position (12),
		// which olY20 (a DIFFERENT order's line, at 20) would otherwise sort between it and its run.
		assertThat(copiedTextLine(shipment, "from X").getLine()).isEqualByComparingTo(BigDecimal.valueOf(olX30.getLine()));
		assertThat(copiedTextLine(shipment, "from Y").getLine()).isEqualByComparingTo(BigDecimal.valueOf(olY20.getLine()));

		// the merged rendering order is X10, Y20, "from X" (tied with -- so rendered immediately before --
		// X30), X30: "from X" sorts AFTER olY20, immediately before its own run. This is the reviewer's own
		// probe (order X lines 10/30, text at 12, order Y line 20, all on one shipment) now passing.
		assertThat(copiedTextLine(shipment, "from X").getLine()).isGreaterThan(BigDecimal.valueOf(olY20.getLine()));
	}

	/**
	 * The producer's collision-detection field ({@code ShipmentLineNoInfo}) is created once per producer run
	 * and never reset between shipments (see {@link TextLineShipmentCopier#copyTextLinesToShipment}'s
	 * javadoc) -- so a Line number that two DIFFERENT, non-consolidating shipments happen to reuse (neither
	 * has a real collision on its own) is still registered as colliding for BOTH, because the bookkeeping is
	 * keyed on the raw Line number across the whole run, not scoped per shipment. This is a known, pre-
	 * existing limitation of that bookkeeping -- not something this copier introduces or can correct from
	 * where it sits, since it only ever sees the collision set it is handed -- covered here so the behaviour
	 * is pinned rather than silently relied upon.
	 * <p>
	 * Getting two REAL shipments out of one producer needs two chunks; which candidates land in the same
	 * chunk is decided by an aggregation key that is itself AD-dictionary-driven (see
	 * {@code ShipmentScheduleHeaderAggregationKeyBuilder}) and not populated by this plain-JUnit harness, so a
	 * differing BPartner/Warehouse alone does not split a single {@link #process} call into two chunks here.
	 * This test drives the SAME producer instance through two separate executor runs instead -- each one
	 * unconditionally starts its own chunk (there is no "previous item" for the executor to compare against)
	 * -- which reaches the exact same internal state (one producer, two calls to
	 * {@code processCurrentShipment()}, one never-reset {@code shipmentLineNoInfo}) that a single run
	 * producing two chunks would in production.
	 */
	@Test
	void producerAcrossTwoShipments_collisionSetIsSharedAcrossThem()
	{
		final OrderId orderX = order();
		final I_C_OrderLine olX10 = orderLine(orderX, product("A", uom("uom")), uom("uom"), 10);

		final OrderId orderY = order();
		final I_C_OrderLine olY10 = orderLine(orderY, product("B", uom("uom")), uom("uom"), 10); // same Line as olX10, different shipment
		final DocTextLine tY = textLine(orderY, TextLineScope.Following, 5, "from Y");

		final InOutGenerateResult result = new DefaultInOutGenerateResult(true);
		final InOutProducerFromShipmentScheduleWithHU producer = new InOutProducerFromShipmentScheduleWithHU(result);

		processWith(producer, ImmutableList.of(candidate(olX10, product("A2", uom("uom")))));
		processWith(producer, ImmutableList.of(candidate(olY10, product("B2", uom("uom")))));

		assertThat(result.getInOuts())
				.as("the scenario needs two ACTUAL shipments -- else the shared, never-reset collision set is never exercised")
				.hasSize(2);

		final I_M_InOut shipmentY = result.getInOuts().stream()
				.filter(inOut -> inOut.getC_Order_ID() == orderY.getRepoId())
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("no shipment found for order Y"));

		// olY10's Line (10) collides, in the shared bookkeeping, with olX10's -- even though shipment Y alone
		// never had a collision. "from Y" therefore falls back to head placement, the same outcome a genuine
		// same-shipment collision would produce.
		assertThat(copiedTextLine(shipmentY, "from Y").getLine())
				.isEqualByComparingTo(tY.getLine().subtract(BigDecimal.valueOf(1_000_000)));
	}

	/** Runs {@code candidates} through {@code producer} in their own executor call -- see the caller's javadoc for why this, not one shared {@link #process} call, is what forces a second, genuinely separate chunk in this harness. */
	private void processWith(final InOutProducerFromShipmentScheduleWithHU producer, final List<ShipmentScheduleWithHU> candidates)
	{
		trxItemProcessorExecutorService
				.<ShipmentScheduleWithHU, InOutGenerateResult>createExecutor()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setProcessor(producer)
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.process(candidates);
	}
}
