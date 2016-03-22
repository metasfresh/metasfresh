package org.eevolution.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.exceptions.BOMExpiredException;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.product.IProductBL;
import de.metas.product.IStorageBL;

public class PPOrderBOMBL implements IPPOrderBOMBL
{
	private final transient Logger log = LogManager.getLogger(getClass());

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
		orderBOM.setM_Product_ID(bom.getM_Product_ID());
		orderBOM.setName(bom.getName());
		orderBOM.setRevision(bom.getRevision());
		orderBOM.setValidFrom(bom.getValidFrom());
		orderBOM.setValidTo(bom.getValidTo());
		orderBOM.setValue(bom.getValue());
		orderBOM.setDocumentNo(bom.getDocumentNo());
		orderBOM.setC_UOM_ID(bom.getC_UOM_ID());

		InterfaceWrapperHelper.save(orderBOM);
		return orderBOM;
	}

	private I_PP_Order_BOMLine createOrderBOMLine(final I_PP_Order_BOM orderBOM, final I_PP_Product_BOMLine bomLine)
	{
		final I_PP_Order ppOrder = orderBOM.getPP_Order();

		final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class, ppOrder);
		// Set Defaults
		orderBOMLine.setDescription("");
		orderBOMLine.setQtyDelivered(Env.ZERO);
		orderBOMLine.setQtyDeliveredActual(Env.ZERO);
		orderBOMLine.setQtyUsageVariance(Env.ZERO);
		orderBOMLine.setQtyPost(Env.ZERO);
		orderBOMLine.setQtyReject(Env.ZERO);
		orderBOMLine.setQtyRequiered(Env.ZERO);
		orderBOMLine.setQtyReserved(Env.ZERO);
		orderBOMLine.setQtyScrap(Env.ZERO);

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
		setQtyRequired(orderBOMLine, ppOrder.getQtyOrdered(), ppOrder.getC_UOM());

		//
		// Save & return
		InterfaceWrapperHelper.save(orderBOMLine);
		return orderBOMLine;
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

	// public for testing
	public final void updateOrderBOMLine(final I_PP_Order_BOMLine orderBOMLine, final I_PP_Product_BOMLine bomLine)
	{
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
	}

	@Override
	public I_PP_Order_BOM createOrderBOMAndLines(final I_PP_Order ppOrder)
	{
		final I_PP_Product_BOM productBOM = ppOrder.getPP_Product_BOM();
		// Product from Order should be same as product from BOM - teo_sarca [ 2817870 ]
		if (ppOrder.getM_Product_ID() != productBOM.getM_Product_ID())
		{
			throw new LiberoException("@NotMatch@ @PP_Product_BOM_ID@ , @M_Product_ID@");
		}

		// Product BOM Configuration should be verified - teo_sarca [ 2817870 ]
		final I_M_Product product = productBOM.getM_Product();
		if (!product.isVerified())
		{
			throw new LiberoException("Product BOM Configuration not verified. Please verify the product first - " + product.getValue()); // TODO: translate
		}

		//
		// Create BOM Head
		final Timestamp dateStartSchedule = ppOrder.getDateStartSchedule();
		if (!Services.get(IProductBOMBL.class).isValidFromTo(productBOM, dateStartSchedule))
		{
			throw new BOMExpiredException(productBOM, dateStartSchedule);
		}

		final I_PP_Order_BOM orderBOM = createOrderBOM(ppOrder, productBOM);

		final List<I_PP_Product_BOMLine> productBOMLines = Services.get(IProductBOMDAO.class).retrieveLines(productBOM);
		for (I_PP_Product_BOMLine productBOMLine : productBOMLines)
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

	private void setQtyRequired(final I_PP_Order_BOMLine orderBOMLine,
			final BigDecimal qtyFinishedGood,
			final I_C_UOM qtyFinishedGoodUOM)
	{
		final BigDecimal qtyRequired = calculateQtyRequired(orderBOMLine, qtyFinishedGood, qtyFinishedGoodUOM);
		orderBOMLine.setQtyRequiered(qtyRequired);
	}

	/**
	 * Calculates how much qty is required (standard) for given BOM Line, considering the given quantity of finished goods.
	 * 
	 * @param orderBOMLine
	 * @param qtyFinishedGood
	 * @param qtyFinishedGoodUOM
	 * @return quantity required to be issued (standard)
	 */
	/* package */BigDecimal calculateQtyRequired(final I_PP_Order_BOMLine orderBOMLine, final BigDecimal qtyFinishedGood, I_C_UOM qtyFinishedGoodUOM)
	{
		final BigDecimal multiplier = getQtyMultiplier(orderBOMLine);

		final BigDecimal qtyRequired;
		if (isComponentType(orderBOMLine,
				X_PP_Order_BOMLine.COMPONENTTYPE_Component
				, X_PP_Order_BOMLine.COMPONENTTYPE_Phantom
				, X_PP_Order_BOMLine.COMPONENTTYPE_Packing
				, X_PP_Order_BOMLine.COMPONENTTYPE_By_Product
				, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product
				, X_PP_Order_BOMLine.COMPONENTTYPE_Variant))
		{
			qtyRequired = qtyFinishedGood.multiply(multiplier).setScale(8, RoundingMode.UP);
		}
		else if (isComponentType(orderBOMLine, X_PP_Order_BOMLine.COMPONENTTYPE_Tools))
		{
			qtyRequired = multiplier;
		}
		else
		{
			throw new LiberoException("@NotSupported@ @ComponentType@ " + orderBOMLine.getComponentType());
		}

		//
		// Adjust the qtyRequired by adding the scrap percentage to it.
		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
		final BigDecimal qtyScrap = orderBOMLine.getScrap();
		final BigDecimal qtyRequiredPlusScrap = productBOMBL.calculateQtyWithScrap(qtyRequired, qtyScrap);
		return qtyRequiredPlusScrap;
	}

	@Override
	public BigDecimal calculateQtyRequiredProjected(final I_PP_Order_BOMLine orderBOMLine)
	{
		final I_PP_Order ppOrder = orderBOMLine.getPP_Order();
		final BigDecimal qtyRequired_FinishedGood = ppOrder.getQtyOrdered();
		final BigDecimal qtyDelivered_FinishedGood = ppOrder.getQtyDelivered();
		final BigDecimal qtyRequiredActual_FinishedGood = qtyRequired_FinishedGood.max(qtyDelivered_FinishedGood);
		final I_C_UOM qtyRequiredActual_FinishedGoodUOM = ppOrder.getC_UOM();

		return calculateQtyRequired(orderBOMLine, qtyRequiredActual_FinishedGood, qtyRequiredActual_FinishedGoodUOM);
	}

	@Override
	public BigDecimal calculateQtyRequiredBasedOnFinishedGoodReceipt(final I_PP_Order_BOMLine orderBOMLine)
	{
		final I_PP_Order ppOrder = orderBOMLine.getPP_Order();
		final BigDecimal qtyDelivered_FinishedGood = ppOrder.getQtyDelivered();
		final I_C_UOM qtyDelivered_FinishedGoodUOM = ppOrder.getC_UOM();
		return calculateQtyRequired(orderBOMLine, qtyDelivered_FinishedGood, qtyDelivered_FinishedGoodUOM);
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
	/* package */BigDecimal getQtyMultiplier(final I_PP_Order_BOMLine orderBOMLine)
	{
		BigDecimal qty;
		if (orderBOMLine.isQtyPercentage())
		{
			qty = orderBOMLine.getQtyBatch().divide(Env.ONEHUNDRED, 8, RoundingMode.HALF_UP);

			//
			// We also need to multiply by BOM UOM to BOM Line UOM multiplier
			// see http://dewiki908/mediawiki/index.php/06973_Fix_percentual_BOM_line_quantities_calculation_%28108941319640%29
			final I_PP_Order_BOM orderBOM = orderBOMLine.getPP_Order_BOM();
			final I_M_Product bomProduct = orderBOM.getM_Product();
			final I_C_UOM bomUOM = orderBOM.getC_UOM();

			final I_C_UOM bomLineUOM = orderBOMLine.getC_UOM();
			Check.assumeNotNull(bomLineUOM, "bomLineUOM not null");

			final BigDecimal bomToLineUOMMultiplier = Services.get(IUOMConversionBL.class)
					.convertQty(bomProduct, BigDecimal.ONE, bomUOM, bomLineUOM);
			qty = qty.multiply(bomToLineUOMMultiplier);
		}
		else
		{
			qty = orderBOMLine.getQtyBOM();
		}
		return qty;
	}

	@Override
	public boolean isComponentType(final I_PP_Order_BOMLine orderBOMLine, final String... componentTypes)
	{
		final String currentType = orderBOMLine.getComponentType();
		for (final String type : componentTypes)
		{
			if (currentType.equals(type))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isCoProduct(final I_PP_Order_BOMLine bomLine)
	{
		return isComponentType(bomLine, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product);
	}

	@Override
	public boolean isByProduct(final I_PP_Order_BOMLine bomLine)
	{
		Check.assumeNotNull(bomLine, "bomLine not null");
		final String componentType = bomLine.getComponentType();
		return isByProduct(componentType);
	}

	@Override
	public boolean isByProduct(final String componentType)
	{
		return X_PP_Order_BOMLine.COMPONENTTYPE_By_Product.equals(componentType);
	}

	@Override
	public boolean isCoOrByProduct(final I_PP_Order_BOMLine bomLine)
	{
		return isComponentType(bomLine, X_PP_Order_BOMLine.COMPONENTTYPE_By_Product, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product);
	}

	@Override
	public boolean isVariant(final I_PP_Order_BOMLine bomLine)
	{
		return isComponentType(bomLine, X_PP_Order_BOMLine.COMPONENTTYPE_Variant);
	}

	@Override
	public boolean isReceipt(final I_PP_Order_BOMLine bomLine)
	{
		return isComponentType(bomLine, X_PP_Order_BOMLine.COMPONENTTYPE_By_Product, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product);
	}

	@Override
	public void assertReceipt(final I_PP_Order_BOMLine bomLine)
	{
		Check.assume(isReceipt(bomLine), LiberoException.class, "BOM Line shall be of type receipt: {0}", bomLine);
	}

	public void assertIssue(final I_PP_Order_BOMLine bomLine)
	{
		Check.assume(!isReceipt(bomLine), LiberoException.class, "BOM Line shall be of type issue: {0}", bomLine);
	}

	@Override
	public boolean isComponent(final I_PP_Order_BOMLine bomLine)
	{
		return isComponentType(bomLine, X_PP_Order_BOMLine.COMPONENTTYPE_Component, X_PP_Order_BOMLine.COMPONENTTYPE_Packing);
	}

	/**
	 * Explode Phantom Items.
	 * 
	 * TODO: check if BOM and BOM Lines are valid
	 * 
	 * @param qtyOrdered
	 */
	@Override
	public void explodePhantom(final I_PP_Order_BOMLine orderBOMLine, final BigDecimal qtyOrdered)
	{
		final String componentType = orderBOMLine.getComponentType();
		if (!X_PP_Order_BOMLine.COMPONENTTYPE_Phantom.equals(componentType))
		{
			throw new LiberoException("Only Phantom lines can be exploded");
		}

		final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);

		final I_M_Product product = orderBOMLine.getM_Product();
		final I_PP_Product_BOM bom = productBOMDAO.retrieveDefaultBOM(product);
		if (bom == null)
		{
			return;
		}

		final I_C_UOM qtyOrderedUOM = orderBOMLine.getC_UOM();

		for (final I_PP_Product_BOMLine productBOMLine : productBOMDAO.retrieveLines(bom))
		{
			createOrderBOMLineFromPhantomLine(orderBOMLine, productBOMLine, qtyOrdered, qtyOrderedUOM);
		}
	}

	private I_PP_Order_BOMLine createOrderBOMLineFromPhantomLine(
			final I_PP_Order_BOMLine phantomOrderBOMLine,
			final I_PP_Product_BOMLine productBOMLine,
			final BigDecimal qtyOrdered, I_C_UOM qtyOrderedUOM)
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
		setQtyRequired(orderBOMLine, qtyOrdered, qtyOrderedUOM);

		//
		// Save and return
		InterfaceWrapperHelper.save(orderBOMLine);
		return orderBOMLine;
	}

	@Override
	public BigDecimal getQtyToIssue(final I_PP_Order_BOMLine orderBOMLine)
	{
		final BigDecimal qtyToIssueRequiered = getQtyRequiredToIssue(orderBOMLine);
		return getQtyToIssue(orderBOMLine, qtyToIssueRequiered);
	}

	@Override
	public BigDecimal getQtyToIssue(final I_PP_Order_BOMLine orderBOMLine, final BigDecimal qtyToIssueRequiered)
	{
		final BigDecimal qtyIssued = orderBOMLine.getQtyDelivered();
		final BigDecimal qtyToIssue = qtyToIssueRequiered.subtract(qtyIssued);
		return qtyToIssue;
	}

	@Override
	public BigDecimal getQtyRequiredToIssue(final I_PP_Order_BOMLine orderBOMLine)
	{
		// assertIssue(orderBOMLine); // not checking atm because there are some BLs which relly on that
		final BigDecimal qtyToIssueRequiered = orderBOMLine.getQtyRequiered();
		return qtyToIssueRequiered;
	}

	@Override
	public BigDecimal getQtyRequiredToReceive(final I_PP_Order_BOMLine orderBOMLine)
	{
		assertReceipt(orderBOMLine);

		final BigDecimal qtyRequired = orderBOMLine.getQtyRequiered();
		return adjustCoProductQty(qtyRequired);
	}

	@Override
	public BigDecimal getQtyToReceive(final I_PP_Order_BOMLine orderBOMLine)
	{
		assertReceipt(orderBOMLine);

		final BigDecimal qtyToIssue = getQtyToIssue(orderBOMLine);
		return adjustCoProductQty(qtyToIssue);
	}

	@Override
	public final BigDecimal adjustCoProductQty(final BigDecimal qty)
	{
		return qty.negate();
	}

	@Override
	public void reserveStock(final I_PP_Order_BOMLine orderBOMLine)
	{
		//
		// Check if locator was changed. If yes, we need to unreserve first what was reserved before
		final I_PP_Order_BOMLine orderBOMLineOld = InterfaceWrapperHelper.create(orderBOMLine, I_PP_Order_BOMLine.class, true);
		if (orderBOMLineOld.getM_Locator_ID() != orderBOMLine.getM_Locator_ID())
		{
			final BigDecimal qtyReservedNew = reserveStock(orderBOMLineOld, BigDecimal.ZERO);
			if (qtyReservedNew.signum() != 0)
			{
				throw new LiberoException("Cannot unreserve all stock for " + orderBOMLineOld);
			}
			orderBOMLine.setQtyReserved(BigDecimal.ZERO);
		}

		//
		// Adjust stock reservation to "how much we still need to issue"
		final BigDecimal qtyReservedTarget;
		if (isQtyReservationEnabled(orderBOMLine))
		{
			qtyReservedTarget = getQtyToIssue(orderBOMLine);
		}
		else
		{
			qtyReservedTarget = BigDecimal.ZERO;
		}
		final BigDecimal qtyReservedNew = reserveStock(orderBOMLine, qtyReservedTarget);
		orderBOMLine.setQtyReserved(qtyReservedNew);

	} // reserveStock

	/**
	 * Reserve/Unreserve stock.
	 * 
	 * NOTE: this method is not changing the given <code>orderBOMLine</code>.
	 * 
	 * @param orderBOMLine
	 * @param qtyReservedRequested how much we want to have reserved at the end.
	 * @return how much was actual reserved at the end; you can use this value to set {@link I_PP_Order_BOMLine#setQtyReserved(BigDecimal)}.
	 */
	private BigDecimal reserveStock(final I_PP_Order_BOMLine orderBOMLine, final BigDecimal qtyReservedRequested)
	{
		//
		// Calculate how much we really need to reserve more/less:
		final BigDecimal qtyReservedActual = orderBOMLine.getQtyReserved();
		BigDecimal qtyReservedTarget = qtyReservedRequested;
		final BigDecimal qtyReservedDiff;
		final I_M_Product product = orderBOMLine.getM_Product();
		if (!Services.get(IProductBL.class).isStocked(product))
		{
			//
			// Case: we are dealing with a product which is not stocked
			// => we need to make sure we have zero reservations
			qtyReservedDiff = qtyReservedActual.negate();
			qtyReservedTarget = BigDecimal.ZERO;
		}
		else if (qtyReservedTarget.signum() < 0)
		{
			//
			// Case: We issued more then it was needed
			// We just need to unreserve what was reserved until now
			qtyReservedDiff = qtyReservedActual.negate();
			qtyReservedTarget = BigDecimal.ZERO;
		}
		else
		{
			qtyReservedDiff = qtyReservedTarget.subtract(qtyReservedActual);
		}

		//
		// Update Storage (if we have something to reserve/unreserve)
		if (qtyReservedDiff.signum() != 0)
		{
			final I_M_Locator locator = orderBOMLine.getM_Locator();
			final Properties ctx = InterfaceWrapperHelper.getCtx(orderBOMLine);
			final String trxName = InterfaceWrapperHelper.getTrxName(orderBOMLine);
			Services.get(IStorageBL.class).addQtyReserved(ctx,
					locator,
					product.getM_Product_ID(),
					orderBOMLine.getM_AttributeSetInstance_ID(),
					qtyReservedDiff,
					trxName);
		}

		// update line
		// orderBOMLine.setQtyReserved(qtyReservedTarget);

		return qtyReservedTarget;
	}

	@Override
	public void addDescription(final I_PP_Order_BOMLine orderBOMLine, final String description)
	{
		String desc = orderBOMLine.getDescription();
		if (desc == null)
		{
			orderBOMLine.setDescription(description);
		}
		else
		{
			orderBOMLine.setDescription(desc + " | " + description);
		}
	}	// addDescription

	private final static String DYNATTR_ForceQtyReservation = PPOrderBOMBL.class.getName() + "#ForceQtyReservation";

	@Override
	public void setForceQtyReservation(final I_PP_Order_BOMLine orderBOMLine, final boolean forceQtyReservation)
	{
		InterfaceWrapperHelper.setDynAttribute(orderBOMLine, DYNATTR_ForceQtyReservation, forceQtyReservation);
	}

	private boolean isQtyReservationEnabled(final I_PP_Order_BOMLine orderBOMLine)
	{
		if (orderBOMLine.isProcessed())
		{
			return true;
		}

		final Boolean forceQtyReservation = InterfaceWrapperHelper.getDynAttribute(orderBOMLine, DYNATTR_ForceQtyReservation);
		if (forceQtyReservation != null && forceQtyReservation.booleanValue())
		{
			return true;
		}

		// If we already have reserved a quantity, continue doing reservations
		if (orderBOMLine.getQtyReserved().signum() != 0)
		{
			return true;
		}

		return false;
	}

	@Override
	public void addQtyDelivered(final I_PP_Order_BOMLine ppOrderBOMLine,
			final boolean isUsageVariance,
			final BigDecimal qtyDeliveredToAdd)
	{
		Check.assumeNotNull(ppOrderBOMLine, "ppOrderBOMLine not null");
		Check.assumeNotNull(qtyDeliveredToAdd, "qtyDeliveredToAdd not null");

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

		InterfaceWrapperHelper.save(line);
	}

	@Override
	public void unclose(final I_PP_Order_BOMLine line)
	{
		final BigDecimal qtyRequiredBeforeClose = line.getQtyBeforeClose();

		line.setQtyRequiered(qtyRequiredBeforeClose);
		line.setQtyBeforeClose(BigDecimal.ZERO);

		InterfaceWrapperHelper.save(line);
	}

	@Override
	public void reserveStock(final List<I_PP_Order_BOMLine> lines)
	{
		final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

		// Always check and (un) Reserve Inventory
		for (final I_PP_Order_BOMLine line : lines)
		{
			ppOrderBOMBL.setForceQtyReservation(line, true);
			ppOrderBOMBL.reserveStock(line);
			InterfaceWrapperHelper.save(line);
		}
	} // reserveStock
}
