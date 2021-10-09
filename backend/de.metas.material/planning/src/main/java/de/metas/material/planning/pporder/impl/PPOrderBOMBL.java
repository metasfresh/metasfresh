package de.metas.material.planning.pporder.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.document.sequence.DocSequenceId;
import de.metas.i18n.IMsgBL;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.exception.MrpException;
import de.metas.material.planning.pporder.DraftPPOrderQuantities;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.OrderBOMLineQtyChangeRequest;
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.api.QtyCalculationsBOMLine;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.UnaryOperator;

@Service
public class PPOrderBOMBL implements IPPOrderBOMBL
{
	private final IProductBOMBL bomBL = Services.get(IProductBOMBL.class);
	private final IProductBOMDAO bomDAO = Services.get(IProductBOMDAO.class);
	private final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionService = Services.get(IUOMConversionBL.class);
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@Override
	public I_PP_Order_BOMLine getOrderBOMLineById(@NonNull final PPOrderBOMLineId orderBOMLineId)
	{
		return orderBOMsRepo.getOrderBOMLineById(orderBOMLineId);
	}

	@Override
	public PPOrderQuantities getQuantities(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final UomId targetUOMId)
	{
		final ProductId mainProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		return getQuantities(ppOrder)
				.convertQuantities(qty -> uomConversionService.convertQuantityTo(qty, mainProductId, targetUOMId));
	}

	@Override
	public PPOrderQuantities getQuantities(@NonNull final I_PP_Order ppOrder)
	{
		final I_C_UOM uom = uomDAO.getById(ppOrder.getC_UOM_ID());

		return PPOrderQuantities.builder()
				.qtyRequiredToProduce(Quantity.of(ppOrder.getQtyOrdered(), uom))
				.qtyRequiredToProduceBeforeClose(Quantity.of(ppOrder.getQtyBeforeClose(), uom))
				.qtyReceived(Quantity.of(ppOrder.getQtyDelivered(), uom))
				.qtyScrapped(Quantity.of(ppOrder.getQtyScrap(), uom))
				.qtyRejected(Quantity.of(ppOrder.getQtyReject(), uom))
				.qtyReserved(Quantity.of(ppOrder.getQtyReserved(), uom))
				.build();
	}

	@Override
	public void setQuantities(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final PPOrderQuantities from)
	{
		ppOrder.setC_UOM_ID(from.getUomId().getRepoId());
		ppOrder.setQtyOrdered(from.getQtyRequiredToProduce().toBigDecimal());
		ppOrder.setQtyBeforeClose(from.getQtyRequiredToProduceBeforeClose().toBigDecimal());
		ppOrder.setQtyDelivered(from.getQtyReceived().toBigDecimal());
		ppOrder.setQtyScrap(from.getQtyScrapped().toBigDecimal());
		ppOrder.setQtyReject(from.getQtyRejected().toBigDecimal());
		ppOrder.setQtyReserved(from.getQtyReserved().toBigDecimal());
	}

	@Override
	public void changeQuantities(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final UnaryOperator<PPOrderQuantities> updater)
	{
		final PPOrderQuantities qtys = getQuantities(ppOrder);
		final PPOrderQuantities changedQtys = updater.apply(qtys);
		setQuantities(ppOrder, changedQtys);
	}

	final void updateOrderBOMLine(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@NonNull final I_PP_Product_BOMLine bomLine)
	{
		orderBOMLine.setPP_Product_BOMLine(bomLine);

		orderBOMLine.setM_ChangeNotice_ID(bomLine.getM_ChangeNotice_ID());
		orderBOMLine.setDescription(bomLine.getDescription());
		orderBOMLine.setHelp(bomLine.getHelp());
		orderBOMLine.setAssay(bomLine.getAssay());
		orderBOMLine.setQtyBatch(bomLine.getQtyBatch());
		orderBOMLine.setQtyBOM(bomLine.getQtyBOM());
		orderBOMLine.setIsQtyPercentage(bomLine.isQtyPercentage());
		orderBOMLine.setComponentType(bomLine.getComponentType());
		orderBOMLine.setC_UOM_ID(bomLine.getC_UOM_ID());
		orderBOMLine.setForecast(bomLine.getForecast());
		orderBOMLine.setIsCritical(bomLine.isCritical());
		orderBOMLine.setIssueMethod(bomLine.getIssueMethod());
		orderBOMLine.setLeadTimeOffset(bomLine.getLeadTimeOffset());
		// orderBOMLine.setM_AttributeSetInstance_ID(bomLine.getM_AttributeSetInstance_ID()); // see below
		orderBOMLine.setM_Product_ID(bomLine.getM_Product_ID());
		orderBOMLine.setScrap(bomLine.getScrap());
		orderBOMLine.setValidFrom(bomLine.getValidFrom());
		orderBOMLine.setValidTo(bomLine.getValidTo());
		orderBOMLine.setBackflushGroup(bomLine.getBackflushGroup());
		// 06005
		orderBOMLine.setVariantGroup(bomLine.getVariantGroup());

		//
		// Copy ASI from PP_Product_BOMLine if any (08074)
		if (orderBOMLine.getM_AttributeSetInstance_ID() <= 0 && bomLine.getM_AttributeSetInstance_ID() > 0)
		{
			final I_M_AttributeSetInstance asi = bomLine.getM_AttributeSetInstance();
			final I_M_AttributeSetInstance asiCopy = attributesRepo.copy(asi);
			orderBOMLine.setM_AttributeSetInstance_ID(asiCopy.getM_AttributeSetInstance_ID());
		}

		orderBOMLine.setCULabelQuanitity(bomLine.getCULabelQuanitity());
		orderBOMLine.setShowSubBOMIngredients(bomLine.isShowSubBOMIngredients());
	}

	@Override
	public void createOrderBOMAndLines(@NonNull final I_PP_Order ppOrder)
	{
		PPOrderBOMCreateCommand.builder()
				.ppOrderBOMsBL(this)
				.ppOrder(ppOrder)
				.build()
				.execute();
	}

	Quantity computeQtyRequiredByQtyOfFinishedGoods(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@NonNull final Quantity qtyFinishedGood)
	{
		final I_PP_Order order = orderBOMLine.getPP_Order();
		return toQtyCalculationsBOMLine(order, orderBOMLine).computeQtyRequired(qtyFinishedGood);
	}

	@Override
	public Quantity computeQtyRequiredByQtyOfFinishedGoods(
			@NonNull final PPOrderLine ppOrderLinePojo,
			@NonNull final Quantity qtyFinishedGood)
	{
		return toQtyCalculationsBOMLine(ppOrderLinePojo).computeQtyRequired(qtyFinishedGood);
	}

	@Override
	public Quantity computeQtyToIssueBasedOnFinishedGoodReceipt(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@NonNull final I_C_UOM targetUOM,
			@NonNull final DraftPPOrderQuantities draftQuantities)
	{
		PPOrderUtil.assertIssue(orderBOMLine); // only issuing is supported

		//
		// Get how much finish goods were delivered
		final I_PP_Order ppOrder = orderBOMLine.getPP_Order();
		final Quantity qtyDelivered_FinishedGood = addQuantities(
				getQuantities(ppOrder).getQtyReceived(),
				draftQuantities.getQtyReceived(),
				ProductId.ofRepoId(ppOrder.getM_Product_ID()));

		//
		// Calculate how much we can issue at max, based on how much finish goods we delivered
		final Quantity qtyToIssueTarget = toQtyCalculationsBOMLine(ppOrder, orderBOMLine).computeQtyRequired(qtyDelivered_FinishedGood);
		if (qtyToIssueTarget.signum() <= 0)
		{
			return Quantity.zero(targetUOM);
		}

		//
		// How much was already issued
		final Quantity qtyIssued = addQuantities(
				getQuantities(orderBOMLine).getQtyIssuedOrReceived(),
				draftQuantities.getQtyIssuedOrReceived(PPOrderBOMLineId.ofRepoId(orderBOMLine.getPP_Order_BOMLine_ID())),
				ProductId.ofRepoId(orderBOMLine.getM_Product_ID()));

		//
		// Effective qtyToIssue: how much we need to issue (max) - how much we already issued
		final UOMConversionContext conversionCtx = extractUOMConversionContext(orderBOMLine);
		final Quantity qtyToIssueEffective = uomConversionService.convertQuantityTo(qtyToIssueTarget, conversionCtx, qtyIssued.getUOM())
				.subtract(qtyIssued);
		if (qtyToIssueEffective.signum() <= 0)
		{
			return Quantity.zero(targetUOM); // we issued everything that was needed...
		}

		//
		return uomConversionService.convertQuantityTo(qtyToIssueEffective, conversionCtx, targetUOM);
	}

	private Quantity addQuantities(
			@NonNull final Quantity qty,
			@NonNull final Optional<Quantity> optionalQtyToAdd,
			@NonNull final ProductId productId)
	{
		final Quantity qtyToAdd = optionalQtyToAdd.orElse(null);
		if (qtyToAdd == null)
		{
			return qty;
		}

		final Quantity qtyToAddConv = uomConversionService.convertQuantityTo(qtyToAdd, productId, qty.getUomId());
		return qty.add(qtyToAddConv);
	}

	@NonNull
	private static UOMConversionContext extractUOMConversionContext(final @NonNull I_PP_Order_BOMLine orderBOMLine)
	{
		return UOMConversionContext.of(ProductId.ofRepoId(orderBOMLine.getM_Product_ID()));
	}

	@VisibleForTesting
	QtyCalculationsBOMLine toQtyCalculationsBOMLine(@NonNull final PPOrderLine ppOrderBOMLine)
	{
		final I_PP_Product_BOMLine bomLine = bomDAO.getBOMLineById(ppOrderBOMLine.getProductBomLineId());
		final ProductBOMId bomId = ProductBOMId.ofRepoId(bomLine.getPP_Product_BOM_ID());
		final I_PP_Product_BOM bom = bomDAO.getById(bomId);
		return bomBL.toQtyCalculationsBOMLine(bomLine, bom);
	}

	@VisibleForTesting
	QtyCalculationsBOMLine toQtyCalculationsBOMLine(
			@NonNull final I_PP_Order order,
			@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		return QtyCalculationsBOMLine.builder()
				.bomProductId(ProductId.ofRepoId(order.getM_Product_ID()))
				.bomProductUOM(uomDAO.getById(order.getC_UOM_ID()))
				.componentType(BOMComponentType.ofCode(orderBOMLine.getComponentType()))
				//
				.productId(ProductId.ofRepoId(orderBOMLine.getM_Product_ID()))
				.qtyPercentage(orderBOMLine.isQtyPercentage())
				.qtyForOneFinishedGood(orderBOMLine.getQtyBOM())
				.percentOfFinishedGood(Percent.of(orderBOMLine.getQtyBatch()))
				.scrap(Percent.of(orderBOMLine.getScrap()))
				//
				.uom(getBOMLineUOM(orderBOMLine))
				//
				.orderBOMLineId(PPOrderBOMLineId.ofRepoIdOrNull(orderBOMLine.getPP_Order_BOMLine_ID()))
				//
				.build();
	}

	/**
	 * Explode Phantom Items.
	 * <p>
	 * TODO: check if BOM and BOM Lines are valid
	 */
	@Override
	public void explodePhantom(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@NonNull final Quantity qtyOrdered)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(orderBOMLine.getComponentType());
		if (!componentType.isPhantom())
		{
			throw new MrpException("Only Phantom lines can be exploded");
		}

		final ProductId bomProductId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final I_PP_Product_BOM bom = bomDAO.getDefaultBOMByProductId(bomProductId).orElse(null);
		if (bom == null)
		{
			return;
		}

		for (final I_PP_Product_BOMLine productBOMLine : bomDAO.retrieveLines(bom))
		{
			createOrderBOMLineFromPhantomLine(orderBOMLine, productBOMLine, qtyOrdered);
		}
	}

	private void createOrderBOMLineFromPhantomLine(
			final I_PP_Order_BOMLine phantomOrderBOMLine,
			final I_PP_Product_BOMLine productBOMLine,
			final Quantity qtyFinishedGoods)
	{
		final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class, phantomOrderBOMLine);
		updateOrderBOMLine(orderBOMLine, productBOMLine);

		//
		// Copy setting from parent Order BOMLine
		orderBOMLine.setAD_Org_ID(orderBOMLine.getAD_Org_ID());
		orderBOMLine.setPP_Order_BOM_ID(orderBOMLine.getPP_Order_BOM_ID());
		orderBOMLine.setPP_Order_ID(orderBOMLine.getPP_Order_ID());
		orderBOMLine.setM_Warehouse_ID(orderBOMLine.getM_Warehouse_ID());
		orderBOMLine.setM_Locator_ID(orderBOMLine.getM_Locator_ID());

		//
		// Qtys
		final Quantity qtyRequired = computeQtyRequiredByQtyOfFinishedGoods(orderBOMLine, qtyFinishedGoods);
		setQuantities(orderBOMLine, OrderBOMLineQuantities.ofQtyRequired(qtyRequired));

		//
		orderBOMsRepo.save(orderBOMLine);
	}

	@Override
	public I_C_UOM getBOMLineUOM(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		final UomId uomId = getBOMLineUOMId(bomLine);
		return uomDAO.getById(uomId);
	}

	@NonNull
	@Override
	public UomId getBOMLineUOMId(final @NonNull I_PP_Order_BOMLine bomLine)
	{
		return UomId.ofRepoId(bomLine.getC_UOM_ID());
	}

	@NonNull
	@Override
	public UomId getBOMLineUOMId(@NonNull final PPOrderBOMLineId orderBOMLineId)
	{
		final I_PP_Order_BOMLine orderBOMLine = orderBOMsRepo.getOrderBOMLineById(orderBOMLineId);
		return getBOMLineUOMId(orderBOMLine);
	}

	private void changeQuantities(
			@NonNull final I_PP_Order_BOMLine record,
			@NonNull final UnaryOperator<OrderBOMLineQuantities> updater)
	{
		final OrderBOMLineQuantities qtys = getQuantities(record);
		final OrderBOMLineQuantities changedQtys = updater.apply(qtys);
		setQuantities(record, changedQtys);
	}

	@Override
	public OrderBOMLineQuantities getQuantities(@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		final I_C_UOM uom = getBOMLineUOM(orderBOMLine);
		return OrderBOMLineQuantities.builder()
				.qtyRequired(Quantity.of(orderBOMLine.getQtyRequiered(), uom))
				.qtyRequiredBeforeClose(Quantity.of(orderBOMLine.getQtyBeforeClose(), uom))
				.qtyIssuedOrReceived(Quantity.of(orderBOMLine.getQtyDelivered(), uom))
				.qtyIssuedOrReceivedActual(Quantity.of(orderBOMLine.getQtyDeliveredActual(), uom))
				.qtyReject(Quantity.of(orderBOMLine.getQtyReject(), uom))
				.qtyScrap(Quantity.of(orderBOMLine.getQtyScrap(), uom))
				.qtyUsageVariance(Quantity.of(orderBOMLine.getQtyUsageVariance(), uom))
				.qtyPost(Quantity.of(orderBOMLine.getQtyPost(), uom))
				.qtyReserved(Quantity.of(orderBOMLine.getQtyReserved(), uom))
				.build();
	}

	static void setQuantities(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@NonNull final OrderBOMLineQuantities from)
	{
		orderBOMLine.setC_UOM_ID(from.getUomId().getRepoId());
		orderBOMLine.setQtyRequiered(from.getQtyRequired().toBigDecimal());
		orderBOMLine.setQtyBeforeClose(from.getQtyRequiredBeforeClose().toBigDecimal());
		orderBOMLine.setQtyDelivered(from.getQtyIssuedOrReceived().toBigDecimal());
		orderBOMLine.setQtyDeliveredActual(from.getQtyIssuedOrReceivedActual().toBigDecimal());
		orderBOMLine.setQtyReject(from.getQtyReject().toBigDecimal());
		orderBOMLine.setQtyScrap(from.getQtyScrap().toBigDecimal());
		orderBOMLine.setQtyUsageVariance(from.getQtyUsageVariance().toBigDecimal());
		orderBOMLine.setQtyPost(from.getQtyPost().toBigDecimal());
		orderBOMLine.setQtyReserved(from.getQtyReserved().toBigDecimal());
	}

	@Override
	public Quantity getQtyToIssue(final I_PP_Order_BOMLine orderBOMLine)
	{
		return getQuantities(orderBOMLine).getRemainingQtyToIssue();
	}

	@Override
	public Quantity getQtyRequiredToIssue(final I_PP_Order_BOMLine orderBOMLine)
	{
		// assertIssue(orderBOMLine); // not checking atm because there are some BLs which rely on that
		return getQuantities(orderBOMLine).getQtyRequired();
	}

	@Override
	public void setQtyRequiredToIssueOrReceive(
			final I_PP_Order_BOMLine orderBOMLine,
			final Quantity qtyRequired)
	{
		changeQuantities(orderBOMLine, qtys -> qtys.withQtyRequired(qtyRequired));
	}

	@Override
	public Quantity getQtyRequiredToReceive(final I_PP_Order_BOMLine orderBOMLine)
	{
		PPOrderUtil.assertReceipt(orderBOMLine);
		return getQuantities(orderBOMLine).getQtyRequired_NegateBecauseIsCOProduct();
	}

	@Override
	public Percent getCoProductCostDistributionPercent(final I_PP_Order_BOMLine orderBOMLine)
	{
		final BOMComponentType bomComponentType = BOMComponentType.ofCode(orderBOMLine.getComponentType());
		Check.assume(bomComponentType.isCoProduct(), "Only co-products are allowing cost distribution percent but not {}, {}", bomComponentType, orderBOMLine);

		final Quantity qtyRequiredPositive = getQuantities(orderBOMLine).getQtyRequired_NegateBecauseIsCOProduct();
		return Percent.of(BigDecimal.ONE, qtyRequiredPositive.toBigDecimal(), 4);
	}

	@Override
	public Quantity getQtyToReceive(final I_PP_Order_BOMLine orderBOMLine)
	{
		PPOrderUtil.assertReceipt(orderBOMLine);

		final Quantity qtyToIssue = getQtyToIssue(orderBOMLine);
		return OrderBOMLineQuantities.adjustCoProductQty(qtyToIssue);
	}

	private static void addDescription(
			final I_PP_Order_BOMLine orderBOMLine,
			final String description)
	{
		final String desc = orderBOMLine.getDescription();
		if (desc == null)
		{
			orderBOMLine.setDescription(description);
		}
		else
		{
			orderBOMLine.setDescription(desc + " | " + description);
		}
	}

	@Override
	public void addQty(@NonNull final OrderBOMLineQtyChangeRequest request)
	{
		final I_PP_Order_BOMLine orderBOMLine = orderBOMsRepo.getOrderBOMLineById(request.getOrderBOMLineId());

		final @NonNull UOMConversionContext uomConversionContext = extractUOMConversionContext(orderBOMLine);
		final @NonNull UomId bomLineUOMId = getBOMLineUOMId(orderBOMLine);

		final OrderBOMLineQtyChangeRequest requestActual = request.convertQuantities(
				qty -> uomConversionService.convertQuantityTo(qty, uomConversionContext, bomLineUOMId)
		);

		changeQuantities(orderBOMLine, qtys -> qtys.reduce(requestActual));

		if (!requestActual.getQtyIssuedOrReceivedToAdd().isZero())
		{
			if (!requestActual.isUsageVariance())
			{
				orderBOMLine.setDateDelivered(TimeUtil.asTimestamp(requestActual.getDate()));    // overwrite=last
			}

			orderBOMLine.setM_AttributeSetInstance_ID(requestActual.getAsiId().getRepoId());
		}

		orderBOMsRepo.save(orderBOMLine);
	}

	@Override
	public void voidBOMLine(final I_PP_Order_BOMLine line)
	{
		final Quantity qtyRequiredOld = getQtyRequiredToIssue(line);
		if (qtyRequiredOld.signum() != 0)
		{
			addDescription(line, msgBL.parseTranslation(Env.getCtx(), "@Voided@ @QtyRequiered@ : (" + qtyRequiredOld + ")"));
			setQtyRequiredToIssueOrReceive(line, qtyRequiredOld.toZero());
			line.setProcessed(true);

			orderBOMsRepo.save(line);
		}
	}

	@Override
	public void close(final I_PP_Order_BOMLine line)
	{
		changeQuantities(line, OrderBOMLineQuantities::close);
		line.setProcessed(true); // just to make sure (but it should be already set when the PP_Order was completed)

		orderBOMsRepo.save(line);
	}

	@Override
	public void unclose(final I_PP_Order_BOMLine line)
	{
		changeQuantities(line, OrderBOMLineQuantities::unclose);
		orderBOMsRepo.save(line);
	}

	@Override
	public boolean isSomethingReported(@NonNull final I_PP_Order ppOrder)
	{
		if (getQuantities(ppOrder).isSomethingReported())
		{
			return true;
		}

		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		return orderBOMsRepo.retrieveOrderBOMLines(ppOrderId)
				.stream()
				.map(this::getQuantities)
				.anyMatch(OrderBOMLineQuantities::isSomethingReported);
	}

	@Override
	public Optional<DocSequenceId> getSerialNoSequenceId(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order_BOM orderBOM = orderBOMsRepo.getByOrderIdOrNull(ppOrderId);
		if (orderBOM == null)
		{
			throw new AdempiereException("@NotFound@ @PP_Order_BOM_ID@: " + ppOrderId);
		}
		return DocSequenceId.optionalOfRepoId(orderBOM.getSerialNo_Sequence_ID());
	}

	@Override
	public QtyCalculationsBOM getQtyCalculationsBOM(@NonNull final I_PP_Order order)
	{
		final ImmutableList<QtyCalculationsBOMLine> lines = orderBOMsRepo.retrieveOrderBOMLines(order)
				.stream()
				.map(orderBOMLineRecord -> toQtyCalculationsBOMLine(order, orderBOMLineRecord))
				.collect(ImmutableList.toImmutableList());

		return QtyCalculationsBOM.builder()
				.lines(lines)
				.orderId(PPOrderId.ofRepoIdOrNull(order.getPP_Order_ID()))
				.build();
	}
}
