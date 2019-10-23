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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMQtys;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.sequence.DocSequenceId;
import de.metas.i18n.IMsgBL;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.exception.MrpException;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.OrderBOMLineQtyChangeRequest;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

@Service
public class PPOrderBOMBL implements IPPOrderBOMBL
{
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
			final I_M_AttributeSetInstance asiCopy = Services.get(IAttributeDAO.class).copy(asi);
			orderBOMLine.setM_AttributeSetInstance(asiCopy);
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

	void setQtyRequired(final I_PP_Order_BOMLine orderBOMLine, final Quantity qtyFinishedGood)
	{
		final Quantity qtyRequired = computeQtyRequired(fromRecord(orderBOMLine), qtyFinishedGood.toBigDecimal());
		orderBOMLine.setQtyRequiered(qtyRequired.toBigDecimal());
	}

	@Override
	public Quantity computeQtyRequired(
			@NonNull final PPOrderLine ppOrderLinePojo,
			@NonNull final BigDecimal qtyFinishedGood)
	{
		return computeQtyRequired(fromPojo(ppOrderLinePojo), qtyFinishedGood);
	}

	/**
	 * Calculates how much qty is required (standard) for given BOM Line, considering the given quantity of finished goods.
	 *
	 * @param orderBOMLine
	 * @param qtyFinishedGood
	 * @param qtyFinishedGoodUOM
	 * @return standard quantity required to be issued (standard UOM)
	 */
	@VisibleForTesting
	Quantity computeQtyRequired(
			@NonNull final PPOrderBomLineAware orderBOMLine,
			@NonNull final BigDecimal qtyFinishedGood)
	{
		final BigDecimal multiplier = getQtyMultiplier(orderBOMLine);

		final BigDecimal qtyRequired;
		if (orderBOMLine.getComponentType().isTools())
		{
			qtyRequired = multiplier;
		}
		else
		{
			qtyRequired = qtyFinishedGood.multiply(multiplier).setScale(8, RoundingMode.UP);
		}

		//
		// Adjust the qtyRequired by adding the scrap percentage to it.
		final Percent qtyScrap = orderBOMLine.getScrap();
		final BigDecimal qtyRequiredPlusScrap = ProductBOMQtys.computeQtyWithScrap(qtyRequired, qtyScrap);
		return Quantity.of(qtyRequiredPlusScrap, orderBOMLine.getUom());
	}

	@Override
	public Quantity computeQtyToIssueBasedOnFinishedGoodReceipt(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@NonNull final I_C_UOM uom)
	{
		PPOrderUtil.assertIssue(orderBOMLine); // only issuing is supported

		//
		// Get how much finish goods were delivered
		final I_PP_Order ppOrder = orderBOMLine.getPP_Order();
		final BigDecimal qtyDelivered_FinishedGood = ppOrder.getQtyDelivered();

		//
		// Calculate how much we can issue at max, based on how much finish goods we delivered
		final Quantity qtyToIssueTarget = computeQtyRequired(fromRecord(orderBOMLine), qtyDelivered_FinishedGood);
		if (qtyToIssueTarget.signum() <= 0)
		{
			return Quantity.zero(uom);
		}

		// How much was already issued
		final Quantity qtyIssued = getQtyIssuedOrReceived(orderBOMLine);

		// Effective qtyToIssue: how much we need to issue (max) - how much we already issued
		final IUOMConversionBL uomConversionService = Services.get(IUOMConversionBL.class);
		final UOMConversionContext conversionCtx = UOMConversionContext.of(ProductId.ofRepoId(orderBOMLine.getM_Product_ID()));
		final Quantity qtyToIssueEffective = uomConversionService.convertQuantityTo(qtyToIssueTarget, conversionCtx, qtyIssued.getUOM())
				.subtract(qtyIssued);
		if (qtyToIssueEffective.signum() <= 0)
		{
			return Quantity.zero(uom); // we issued everything that was needed...
		}

		//
		return uomConversionService.convertQuantityTo(qtyToIssueEffective, conversionCtx, uom);
	}

	@lombok.Value
	@lombok.Builder
	private static class PPOrderBomLineAware
	{
		@NonNull
		ProductId bomProductId;
		@NonNull
		I_C_UOM bomProductUOM;

		@NonNull
		BOMComponentType componentType;

		boolean qtyPercentage;
		@NonNull
		BigDecimal qtyBOM;
		@NonNull
		Percent qtyBatch;
		@NonNull
		Percent scrap;

		@NonNull
		I_C_UOM uom;
	}

	@VisibleForTesting
	PPOrderBomLineAware fromPojo(@NonNull final PPOrderLine ppOrderBOMLine)
	{
		final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);
		final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

		final I_PP_Product_BOMLine bomLine = bomsRepo.getBOMLineById(ppOrderBOMLine.getProductBomLineId());
		final ProductBOMId bomId = ProductBOMId.ofRepoId(bomLine.getPP_Product_BOM_ID());
		final I_PP_Product_BOM bom = bomsRepo.getById(bomId);

		return PPOrderBomLineAware.builder()
				.bomProductId(ProductId.ofRepoId(bom.getM_Product_ID()))
				.bomProductUOM(uomsRepo.getById(bom.getC_UOM_ID()))
				.componentType(BOMComponentType.ofCode(bomLine.getComponentType()))
				//
				.qtyPercentage(bomLine.isQtyPercentage())
				.qtyBOM(bomLine.getQtyBOM())
				.qtyBatch(Percent.of(bomLine.getQtyBatch()))
				.scrap(Percent.of(bomLine.getScrap()))
				//
				.uom(uomsRepo.getById(bomLine.getC_UOM_ID()))
				//
				.build();
	}

	@VisibleForTesting
	PPOrderBomLineAware fromRecord(@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

		final I_PP_Order order = orderBOMLine.getPP_Order();

		return PPOrderBomLineAware.builder()
				.bomProductId(ProductId.ofRepoId(order.getM_Product_ID()))
				.bomProductUOM(uomsRepo.getById(order.getC_UOM_ID()))
				.componentType(BOMComponentType.ofCode(orderBOMLine.getComponentType()))
				//
				.qtyPercentage(orderBOMLine.isQtyPercentage())
				.qtyBOM(orderBOMLine.getQtyBOM())
				.qtyBatch(Percent.of(orderBOMLine.getQtyBatch()))
				.scrap(Percent.of(orderBOMLine.getScrap()))
				//
				.uom(uomsRepo.getById(orderBOMLine.getC_UOM_ID()))
				//
				.build();
	}

	/**
	 * Return Unified BOM Qty Multiplier.
	 *
	 * i.e. how much of this component is needed for 1 item of finished good.
	 *
	 * @param orderBOMLine
	 *
	 * @return If is percentage then QtyBatch / 100 will be returned, else QtyBOM.
	 */
	/* package */BigDecimal getQtyMultiplier(@NonNull final PPOrderBomLineAware orderBOMLine)
	{
		if (orderBOMLine.isQtyPercentage())
		{
			final Percent percentOfFinishGood = orderBOMLine.getQtyBatch();

			//
			// We also need to multiply by BOM UOM to BOM Line UOM multiplier
			// see http://dewiki908/mediawiki/index.php/06973_Fix_percentual_BOM_line_quantities_calculation_%28108941319640%29
			final ProductId bomProductId = orderBOMLine.getBomProductId();
			final I_C_UOM bomUOM = orderBOMLine.getBomProductUOM();

			final I_C_UOM bomLineUOM = orderBOMLine.getUom();

			final IUOMConversionBL uomConverter = Services.get(IUOMConversionBL.class);
			final BigDecimal bomToLineUOMMultiplier = uomConverter.convertQty(bomProductId, BigDecimal.ONE, bomUOM, bomLineUOM);
			return percentOfFinishGood.subtractFromBase(bomToLineUOMMultiplier, 8);
		}
		else
		{
			return orderBOMLine.getQtyBOM();
		}
	}

	/**
	 * Explode Phantom Items.
	 *
	 * TODO: check if BOM and BOM Lines are valid
	 *
	 * @param qtyOrdered
	 */
	@Override
	public void explodePhantom(@NonNull final I_PP_Order_BOMLine orderBOMLine, @NonNull final Quantity qtyOrdered)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(orderBOMLine.getComponentType());
		if (!componentType.isPhantom())
		{
			throw new MrpException("Only Phantom lines can be exploded");
		}

		final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);

		final ProductId bomProductId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final I_PP_Product_BOM bom = productBOMDAO.getDefaultBOMByProductId(bomProductId).orElse(null);
		if (bom == null)
		{
			return;
		}

		for (final I_PP_Product_BOMLine productBOMLine : productBOMDAO.retrieveLines(bom))
		{
			createOrderBOMLineFromPhantomLine(orderBOMLine, productBOMLine, qtyOrdered);
		}
	}

	private I_PP_Order_BOMLine createOrderBOMLineFromPhantomLine(
			final I_PP_Order_BOMLine phantomOrderBOMLine,
			final I_PP_Product_BOMLine productBOMLine,
			final Quantity qtyOrdered)
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
		setQtyRequired(orderBOMLine, qtyOrdered);

		//
		// Save and return
		final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
		ppOrderBOMsRepo.save(orderBOMLine);
		return orderBOMLine;
	}

	@Override
	public I_C_UOM getStockingUOM(final I_PP_Order_BOMLine orderBOMLine)
	{
		final ProductId productId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		return Services.get(IProductBL.class).getStockUOM(productId);
	}

	@Override
	public Quantity getQtyToIssue(final I_PP_Order_BOMLine orderBOMLine)
	{
		final Quantity qtyToIssueRequiered = getQtyRequiredToIssue(orderBOMLine);
		return getQtyToIssue(orderBOMLine, qtyToIssueRequiered);
	}

	@Override
	public Quantity getQtyToIssue(final I_PP_Order_BOMLine orderBOMLine, final Quantity qtyToIssueRequiered)
	{
		final Quantity qtyIssued = getQtyIssuedOrReceived(orderBOMLine);
		final Quantity qtyToIssue = qtyToIssueRequiered.subtract(qtyIssued);
		return qtyToIssue;
	}

	@Override
	public Quantity getQtyIssuedOrReceived(final I_PP_Order_BOMLine orderBOMLine)
	{
		final I_C_UOM uom = getStockingUOM(orderBOMLine);
		return Quantity.of(orderBOMLine.getQtyDelivered(), uom);
	}

	@Override
	public Quantity getQtyRequiredToIssue(final I_PP_Order_BOMLine orderBOMLine)
	{
		// assertIssue(orderBOMLine); // not checking atm because there are some BLs which relly on that
		final I_C_UOM uom = getStockingUOM(orderBOMLine);
		return Quantity.of(orderBOMLine.getQtyRequiered(), uom);
	}

	@Override
	public Quantity getQtyRequiredToReceive(final I_PP_Order_BOMLine orderBOMLine)
	{
		PPOrderUtil.assertReceipt(orderBOMLine);

		final I_C_UOM uom = getStockingUOM(orderBOMLine);
		final Quantity qtyRequired = Quantity.of(orderBOMLine.getQtyRequiered(), uom);
		return adjustCoProductQty(qtyRequired);
	}

	@Override
	public Percent getCoProductCostDistributionPercent(final I_PP_Order_BOMLine orderBOMLine)
	{
		final BOMComponentType bomComponentType = BOMComponentType.ofCode(orderBOMLine.getComponentType());
		Check.assume(bomComponentType.isCoProduct(), "Only co-products are allowing cost distribution percent but not {}, {}", bomComponentType, orderBOMLine);

		final BigDecimal qtyRequiredPositive = adjustCoProductQty(orderBOMLine.getQtyRequiered());
		return Percent.of(BigDecimal.ONE, qtyRequiredPositive, 4);
	}

	@Override
	public Quantity getQtyToReceive(final I_PP_Order_BOMLine orderBOMLine)
	{
		PPOrderUtil.assertReceipt(orderBOMLine);

		final Quantity qtyToIssue = getQtyToIssue(orderBOMLine);
		return adjustCoProductQty(qtyToIssue);
	}

	@Override
	public final BigDecimal adjustCoProductQty(final BigDecimal qty)
	{
		return qty.negate();
	}

	@Override
	public final Quantity adjustCoProductQty(final Quantity qty)
	{
		return qty.negate();
	}

	private static void addDescription(final I_PP_Order_BOMLine orderBOMLine, final String description)
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
		final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);

		final I_PP_Order_BOMLine orderBOMLine = orderBOMsRepo.getOrderBOMLineById(request.getOrderBOMLineId());

		//
		final Quantity qtyDeliveredToAdd = request.getQtyIssuedOrReceivedToAdd();
		if (!qtyDeliveredToAdd.isZero())
		{
			final BigDecimal qtyDeliveredOld = orderBOMLine.getQtyDelivered();
			final BigDecimal qtyDeliveredNew = qtyDeliveredOld.add(qtyDeliveredToAdd.toBigDecimal());
			orderBOMLine.setQtyDelivered(qtyDeliveredNew);

			// Set delivered date only if is a real quantity issue/receipt.
			if (!request.isUsageVariance())
			{
				orderBOMLine.setDateDelivered(TimeUtil.asTimestamp(request.getDate()));	// overwrite=last
			}

			//
			// In case the quantity is not coming from a usage variance cost collector, add it to QtyDeliveredActual
			if (!request.isUsageVariance())
			{
				final BigDecimal qtyDeliveredActualOld = orderBOMLine.getQtyDeliveredActual();
				final BigDecimal qtyDeliveredActualNew = qtyDeliveredActualOld.add(qtyDeliveredToAdd.toBigDecimal());
				orderBOMLine.setQtyDeliveredActual(qtyDeliveredActualNew);

			}
			//
			// In case the quantity is coming from a usage variance cost collector, increase QtyUsageVariance
			else
			{
				final BigDecimal qtyUsageVarianceOld = orderBOMLine.getQtyUsageVariance();
				final BigDecimal qtyUsageVarianceNew = qtyUsageVarianceOld.add(qtyDeliveredToAdd.toBigDecimal());
				orderBOMLine.setQtyUsageVariance(qtyUsageVarianceNew);
			}

			orderBOMLine.setM_AttributeSetInstance_ID(request.getAsiId().getRepoId());
		}

		//
		final Quantity qtyScrappedToAdd = request.getQtyScrappedToAdd();
		if (qtyScrappedToAdd != null && !qtyScrappedToAdd.isZero())
		{
			orderBOMLine.setQtyScrap(orderBOMLine.getQtyScrap().add(qtyScrappedToAdd.toBigDecimal()));
		}

		//
		final Quantity qtyRejectedToAdd = request.getQtyRejectedToAdd();
		if (qtyRejectedToAdd != null && !qtyRejectedToAdd.isZero())
		{
			orderBOMLine.setQtyReject(orderBOMLine.getQtyReject().add(qtyRejectedToAdd.toBigDecimal()));
		}

		orderBOMsRepo.save(orderBOMLine);
	}

	@Override
	public void voidBOMLine(final I_PP_Order_BOMLine line)
	{
		final BigDecimal qtyRequiredOld = line.getQtyRequiered();
		if (qtyRequiredOld.signum() != 0)
		{
			addDescription(line, Services.get(IMsgBL.class).parseTranslation(Env.getCtx(), "@Voided@ @QtyRequiered@ : (" + qtyRequiredOld + ")"));
			line.setQtyRequiered(BigDecimal.ZERO);
			line.setProcessed(true);

			final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
			orderBOMsRepo.save(line);
		}
	}

	@Override
	public void close(final I_PP_Order_BOMLine line)
	{
		final BigDecimal qtyRequired = line.getQtyRequiered();
		final BigDecimal qtyDelivered = line.getQtyDelivered();

		line.setQtyBeforeClose(qtyRequired);
		line.setQtyRequiered(qtyDelivered);

		line.setProcessed(true); // just to make sure (but it should be already set when the PP_Order was completed)

		final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
		ppOrderBOMsRepo.save(line);
	}

	@Override
	public void unclose(final I_PP_Order_BOMLine line)
	{
		final BigDecimal qtyRequiredBeforeClose = line.getQtyBeforeClose();

		line.setQtyRequiered(qtyRequiredBeforeClose);
		line.setQtyBeforeClose(BigDecimal.ZERO);

		final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
		ppOrderBOMsRepo.save(line);
	}

	@Override
	public boolean isSomethingReportedOnBOMLines(final PPOrderId ppOrderId)
	{
		final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);

		return orderBOMsRepo.retrieveOrderBOMLines(ppOrderId)
				.stream()
				.anyMatch(this::isSomethingReportedOnBOMLine);
	}

	private boolean isSomethingReportedOnBOMLine(final I_PP_Order_BOMLine orderBOMLine)
	{
		return orderBOMLine.getQtyDelivered().signum() != 0
				|| orderBOMLine.getQtyScrap().signum() != 0
				|| orderBOMLine.getQtyReject().signum() != 0;
	}

	@Override
	public Optional<DocSequenceId> getSerialNoSequenceId(@NonNull final PPOrderId ppOrderId)
	{
		final IPPOrderBOMDAO repo = Services.get(IPPOrderBOMDAO.class);
		final I_PP_Order_BOM orderBOM = repo.getByOrderId(ppOrderId);
		return DocSequenceId.optionalOfRepoId(orderBOM.getSerialNo_Sequence_ID());
	}
}
