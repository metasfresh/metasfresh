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
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.uom.api.UOMConversionContext;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.logging.LogManager;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.exception.BOMExpiredException;
import de.metas.material.planning.exception.MrpException;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

@Service
public class PPOrderBOMBL implements IPPOrderBOMBL
{
	private final static Logger log = LogManager.getLogger(PPOrderBOMBL.class);

	private I_PP_Order_BOM createOrderBOM(final I_PP_Order ppOrder, final I_PP_Product_BOM bom)
	{
		final I_PP_Order_BOM orderBOM = InterfaceWrapperHelper.newInstance(I_PP_Order_BOM.class, ppOrder);
		orderBOM.setAD_Org_ID(ppOrder.getAD_Org_ID());
		orderBOM.setPP_Order_ID(ppOrder.getPP_Order_ID());

		orderBOM.setBOMType(bom.getBOMType());
		orderBOM.setBOMUse(bom.getBOMUse());
		orderBOM.setM_ChangeNotice_ID(bom.getM_ChangeNotice_ID());
		orderBOM.setHelp(bom.getHelp());
		orderBOM.setProcessing(bom.isProcessing());
		orderBOM.setHelp(bom.getHelp());
		orderBOM.setDescription(bom.getDescription());
		orderBOM.setM_AttributeSetInstance_ID(bom.getM_AttributeSetInstance_ID());
		orderBOM.setM_Product_ID(bom.getM_Product_ID());  // the bom's M_Product_ID is also the ppOrder's M_Product_ID (enforced by PPOrderPojoSupplier.retriveAndVerifyBOM())
		orderBOM.setName(bom.getName());
		orderBOM.setRevision(bom.getRevision());
		orderBOM.setValidFrom(bom.getValidFrom());
		orderBOM.setValidTo(bom.getValidTo());
		orderBOM.setValue(bom.getValue());
		orderBOM.setDocumentNo(bom.getDocumentNo());
		orderBOM.setC_UOM_ID(bom.getC_UOM_ID()); // the bom's C_UOM_ID

		final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
		orderBOMsRepo.save(orderBOM);
		return orderBOM;
	}

	private I_PP_Order_BOMLine createOrderBOMLine(final I_PP_Order_BOM orderBOM, final I_PP_Product_BOMLine bomLine)
	{
		final I_PP_Order ppOrder = orderBOM.getPP_Order();

		final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class, ppOrder);

		// Set Defaults
		orderBOMLine.setDescription("");
		orderBOMLine.setQtyDelivered(BigDecimal.ZERO);
		orderBOMLine.setQtyDeliveredActual(BigDecimal.ZERO);
		orderBOMLine.setQtyUsageVariance(BigDecimal.ZERO);
		orderBOMLine.setQtyPost(BigDecimal.ZERO);
		orderBOMLine.setQtyReject(BigDecimal.ZERO);
		orderBOMLine.setQtyRequiered(BigDecimal.ZERO);
		orderBOMLine.setQtyReserved(BigDecimal.ZERO);
		orderBOMLine.setQtyScrap(BigDecimal.ZERO);

		//
		// Update from PP_Product BOM Line
		updateOrderBOMLine(orderBOMLine, bomLine);

		//
		// Set values from PP_Order:
		orderBOMLine.setAD_Org_ID(orderBOM.getAD_Org_ID());
		orderBOMLine.setPP_Order(ppOrder);
		orderBOMLine.setPP_Order_BOM(orderBOM);

		//
		// Warehouse and Locator
		updateWarehouseAndLocator(orderBOMLine);

		//
		// Set Qtys
		setQtyRequired(orderBOMLine, getQtyOrdered(ppOrder));

		//
		// Save & return
		final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
		ppOrderBOMsRepo.save(orderBOMLine);
		return orderBOMLine;
	}

	private Quantity getQtyOrdered(final I_PP_Order ppOrderRecord)
	{
		final I_C_UOM mainProductUOM = getMainProductStockingUOM(ppOrderRecord);
		return Quantity.of(ppOrderRecord.getQtyOrdered(), mainProductUOM);
	}

	private I_C_UOM getMainProductStockingUOM(final I_PP_Order ppOrder)
	{
		final ProductId mainProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		return Services.get(IProductBL.class).getStockingUOM(mainProductId);
	}

	@Override
	public final void updateWarehouseAndLocator(final I_PP_Order_BOMLine orderBOMLine)
	{
		final I_PP_Order ppOrder = orderBOMLine.getPP_Order();
		final int adOrgIdNew = ppOrder.getAD_Org_ID();
		final int warehouseIdNew = ppOrder.getM_Warehouse_ID();
		final I_M_Locator locatorNew = ppOrder.getM_Locator();

		orderBOMLine.setAD_Org_ID(adOrgIdNew);
		orderBOMLine.setM_Warehouse_ID(warehouseIdNew);
		orderBOMLine.setM_Locator(locatorNew);
	}

	private final void updateOrderBOMLine(
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
	public I_PP_Order_BOM createOrderBOMAndLines(@NonNull final I_PP_Order ppOrder)
	{
		final I_PP_Product_BOM productBOM = PPOrderUtil.verifyProductBOM(
				ppOrder.getM_Product_ID(),
				ppOrder.getDateStartSchedule(),
				ppOrder.getPP_Product_BOM());

		//
		// Create BOM Head
		final Timestamp dateStartSchedule = ppOrder.getDateStartSchedule();
		if (!Services.get(IProductBOMBL.class).isValidFromTo(productBOM, dateStartSchedule))
		{
			throw new BOMExpiredException(productBOM, dateStartSchedule);
		}

		final I_PP_Order_BOM orderBOM = createOrderBOM(ppOrder, productBOM);

		final List<I_PP_Product_BOMLine> productBOMLines = Services.get(IProductBOMDAO.class).retrieveLines(productBOM);
		for (final I_PP_Product_BOMLine productBOMLine : productBOMLines)
		{
			if (!Services.get(IProductBOMBL.class).isValidFromTo(productBOMLine, dateStartSchedule))
			{
				log.debug("BOM Line skiped - " + productBOMLine);
				continue;
			}

			createOrderBOMLine(orderBOM, productBOMLine);
		}

		return orderBOM;
	}

	private void setQtyRequired(final I_PP_Order_BOMLine orderBOMLine, final Quantity qtyFinishedGood)
	{
		final Quantity qtyRequired = calculateQtyRequired(fromRecord(orderBOMLine), qtyFinishedGood.getAsBigDecimal());
		orderBOMLine.setQtyRequiered(qtyRequired.getAsBigDecimal());
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
	Quantity calculateQtyRequired(
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
		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
		final Percent qtyScrap = orderBOMLine.getScrap();
		final BigDecimal qtyRequiredPlusScrap = productBOMBL.calculateQtyWithScrap(qtyRequired, qtyScrap);
		return Quantity.of(qtyRequiredPlusScrap, orderBOMLine.getBOMProductUOM());
	}

	@Override
	public Quantity calculateQtyRequired(
			@NonNull final PPOrderLine ppOrderLinePojo,
			@NonNull final BigDecimal qtyFinishedGood)
	{
		return calculateQtyRequired(fromPojo(ppOrderLinePojo), qtyFinishedGood);
	}

	@Override
	public Quantity calculateQtyToIssueBasedOnFinishedGoodReceipt(
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
		final Quantity qtyToIssueTarget = calculateQtyRequired(fromRecord(orderBOMLine), qtyDelivered_FinishedGood);
		if (qtyToIssueTarget.signum() <= 0)
		{
			return Quantity.zero(uom);
		}

		// How much was already issued
		final Quantity qtyIssued = getQtyIssuedOrReceived(orderBOMLine);

		// Effective qtyToIssue: how much we need to issue (max) - how much we already issued
		final Quantity qtyToIssueEffective = qtyToIssueTarget.subtract(qtyIssued);
		if (qtyToIssueEffective.signum() <= 0)
		{
			return Quantity.zero(uom); // we issued everything that was needed...
		}

		//
		final UOMConversionContext conversionCtx = UOMConversionContext.of(ProductId.ofRepoId(orderBOMLine.getM_Product_ID()));
		return Services.get(IUOMConversionBL.class).convertQuantityTo(qtyToIssueEffective, conversionCtx, uom);
	}

	private interface PPOrderBomLineAware
	{
		ProductId getBOMProductId();

		I_C_UOM getBOMProductUOM();

		BOMComponentType getComponentType();

		boolean isQtyPercentage();

		BigDecimal getQtyBOM();

		Percent getQtyBatch();

		I_C_UOM getC_UOM();

		Percent getScrap();
	}

	@VisibleForTesting
	PPOrderBomLineAware fromPojo(@NonNull final PPOrderLine ppOrderBOMLine)
	{
		return new PPOrderBomLineAware()
		{
			private I_PP_Product_BOM getProductBOM(final PPOrderLine ppOrderBOMLine)
			{
				final I_PP_Product_BOMLine productBomLine = getProductBomLine(ppOrderBOMLine);
				return productBomLine.getPP_Product_BOM();
			}

			private I_PP_Product_BOMLine getProductBomLine(@NonNull final PPOrderLine ppOrderBOMLine)
			{
				final I_PP_Product_BOMLine productBomLine = InterfaceWrapperHelper.create(Env.getCtx(), ppOrderBOMLine.getProductBomLineId(), I_PP_Product_BOMLine.class, ITrx.TRXNAME_None);
				return productBomLine;
			}

			@Override
			public boolean isQtyPercentage()
			{
				final I_PP_Product_BOMLine productBomLine = getProductBomLine(ppOrderBOMLine);
				return productBomLine.isQtyPercentage();
			}

			@Override
			public Percent getScrap()
			{
				final I_PP_Product_BOMLine productBomLine = getProductBomLine(ppOrderBOMLine);
				return Percent.of(productBomLine.getScrap());
			}

			@Override
			public Percent getQtyBatch()
			{
				final I_PP_Product_BOMLine productBomLine = getProductBomLine(ppOrderBOMLine);
				return Percent.of(productBomLine.getQtyBatch());
			}

			@Override
			public BigDecimal getQtyBOM()
			{
				final I_PP_Product_BOMLine productBomLine = getProductBomLine(ppOrderBOMLine);
				return productBomLine.getQtyBOM();
			}

			@Override
			public BOMComponentType getComponentType()
			{
				final I_PP_Product_BOMLine productBomLine = getProductBomLine(ppOrderBOMLine);
				return BOMComponentType.ofCode(productBomLine.getComponentType());
			}

			@Override
			public I_C_UOM getC_UOM()
			{
				final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

				final I_PP_Product_BOMLine productBomLine = getProductBomLine(ppOrderBOMLine);
				return uomsRepo.getById(productBomLine.getC_UOM_ID());
			}

			@Override
			public ProductId getBOMProductId()
			{
				final I_PP_Product_BOM productBOM = getProductBOM(ppOrderBOMLine);
				return ProductId.ofRepoId(productBOM.getM_Product_ID());
			}

			@Override
			public I_C_UOM getBOMProductUOM()
			{
				final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

				final I_PP_Product_BOM productBOM = getProductBOM(ppOrderBOMLine);
				return uomsRepo.getById(productBOM.getC_UOM_ID());
			}
		};
	}

	@VisibleForTesting
	PPOrderBomLineAware fromRecord(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		return new PPOrderBomLineAware()
		{
			@Override
			public boolean isQtyPercentage()
			{
				return ppOrderBOMLine.isQtyPercentage();
			}

			@Override
			public Percent getQtyBatch()
			{
				return Percent.of(ppOrderBOMLine.getQtyBatch());
			}

			@Override
			public BigDecimal getQtyBOM()
			{
				return ppOrderBOMLine.getQtyBOM();
			}

			@Override
			public ProductId getBOMProductId()
			{
				final I_PP_Order_BOM orderBOM = ppOrderBOMLine.getPP_Order_BOM();
				return ProductId.ofRepoId(orderBOM.getM_Product_ID());
			}

			@Override
			public I_C_UOM getBOMProductUOM()
			{
				final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

				final I_PP_Order_BOM orderBOM = ppOrderBOMLine.getPP_Order_BOM();
				return uomsRepo.getById(orderBOM.getC_UOM_ID());
			}

			@Override
			public I_C_UOM getC_UOM()
			{
				final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
				return uomsRepo.getById(ppOrderBOMLine.getC_UOM_ID());
			}

			@Override
			public Percent getScrap()
			{
				return Percent.of(ppOrderBOMLine.getScrap());
			}

			@Override
			public BOMComponentType getComponentType()
			{
				return BOMComponentType.ofCode(ppOrderBOMLine.getComponentType());
			}
		};
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
			final ProductId bomProductId = orderBOMLine.getBOMProductId();
			final I_C_UOM bomUOM = orderBOMLine.getBOMProductUOM();

			final I_C_UOM bomLineUOM = orderBOMLine.getC_UOM();
			Check.assumeNotNull(bomLineUOM, "bomLineUOM not null");

			final BigDecimal bomToLineUOMMultiplier = Services.get(IUOMConversionBL.class)
					.convertQty(bomProductId, BigDecimal.ONE, bomUOM, bomLineUOM);
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

		final I_M_Product product = orderBOMLine.getM_Product();
		final I_PP_Product_BOM bom = productBOMDAO.retrieveDefaultBOM(product);
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
		return Services.get(IProductBL.class).getStockingUOM(productId);
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

	private Quantity getQtyIssuedOrReceived(final I_PP_Order_BOMLine orderBOMLine)
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

	@Override
	public void addDescription(final I_PP_Order_BOMLine orderBOMLine, final String description)
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
	}	// addDescription

	@Override
	public void addQtyDelivered(
			@NonNull final I_PP_Order_BOMLine ppOrderBOMLine,
			final boolean isUsageVariance,
			@NonNull final BigDecimal qtyDeliveredToAdd)
	{
		if (qtyDeliveredToAdd.signum() == 0)
		{
			return;
		}

		final BigDecimal qtyDeliveredOld = ppOrderBOMLine.getQtyDelivered();
		final BigDecimal qtyDeliveredNew = qtyDeliveredOld.add(qtyDeliveredToAdd);
		ppOrderBOMLine.setQtyDelivered(qtyDeliveredNew);

		//
		// In case the quantity is not coming from a usage variance cost collector, add it to QtyDeliveredActual
		if (!isUsageVariance)
		{
			final BigDecimal qtyDeliveredActualOld = ppOrderBOMLine.getQtyDeliveredActual();
			final BigDecimal qtyDeliveredActualNew = qtyDeliveredActualOld.add(qtyDeliveredToAdd);
			ppOrderBOMLine.setQtyDeliveredActual(qtyDeliveredActualNew);
		}
		//
		// In case the quantity is coming from a usage variance cost collector, increate QtyUsageVariance
		else
		{
			final BigDecimal qtyUsageVarianceOld = ppOrderBOMLine.getQtyUsageVariance();
			final BigDecimal qtyUsageVarianceNew = qtyUsageVarianceOld.add(qtyDeliveredToAdd);
			ppOrderBOMLine.setQtyUsageVariance(qtyUsageVarianceNew);
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
}
