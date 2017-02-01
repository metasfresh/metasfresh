package de.metas.handlingunits.receiptschedule.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attributes.impl.AbstractWeightAttributeTest;
import de.metas.handlingunits.attributes.impl.LUWeightsExpectations;
import de.metas.handlingunits.attributes.impl.TUWeightsExpectations;
import de.metas.handlingunits.expectations.HUAttributeExpectation;
import de.metas.handlingunits.expectations.HUWeightsExpectation;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;

/**
 * Base class for quickly setting up tests with RS-HU-WeightAttribute allocations & operations
 *
 * @author al
 *
 */
public class AbstractRSAllocationWithWeightAttributeTest extends AbstractWeightAttributeTest
{
	protected final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	protected final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	protected final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
	protected final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);

	protected BigDecimal weightNetPaloxe;
	protected BigDecimal weightTarePaloxe;
	protected BigDecimal weightGrossPaloxe;
	/** Weights expectation when using {@link #weightGrossPaloxe} */
	protected HUWeightsExpectation<Object> standardPaloxeWeightExpectation;

	protected BigDecimal rs_PriceActual = new BigDecimal("13");
	protected HUAttributeExpectation<Object> rs_PriceActual_Expectation = null;

	protected I_M_ReceiptSchedule receiptSchedule = null;

	protected ReceiptScheduleHUAllocations rsAllocations = null;

	protected HUReceiptScheduleWeightNetAdjuster rsNetWeightAdjuster = null;

	/**
	 * Initialize {@link #receiptSchedule} with 4300 (10 x Paloxe x 430 kg)
	 */
	@Override
	protected void afterInitialize()
	{
		//
		// Important! We don't want the HUContext to rebuild itself when it's duplicated
		//
		// final int adOrgId = 0;
		// Services.get(ISysConfigBL.class).setValue(HUConstants.SYSCONFIG_UseCacheWhenCreatingHUs, false, adOrgId);
		//
		huContext.setProperty(IHUContext.PROPERTY_Configured, true);

		//
		// Setup weights
		weightNetPaloxe = materialItemProductTomato_430.getQty(); // 430 (WeightNet)
		weightTarePaloxe = helper.pPaloxe_Weight_75kg; // 75 (WeightTare)
		weightGrossPaloxe = weightNetPaloxe.add(weightTarePaloxe); // 505=430+75 (WeightGross=Net+Tare)
		standardPaloxeWeightExpectation = HUWeightsExpectation.newExpectation()
				.gross(weightGrossPaloxe)
				.net(weightNetPaloxe)
				.tare(weightTarePaloxe);

		//
		// Setup Cost/Price
		{
			// make sure PriceActual is configured
			Check.assumeNotNull(rs_PriceActual, "rs_PriceActual not null");
			Check.assume(rs_PriceActual.signum() > 0, "Price Actual > 0: {}", rs_PriceActual);

			rs_PriceActual_Expectation = HUAttributeExpectation.newExpectation()
					.attribute(Constants.ATTR_CostPrice)
					.attribute(helper.attr_CostPrice) // just to be sure ;)
					.piAttribute(helper.huDefVirtual_Attr_CostPrice)
					.valueNumber(rs_PriceActual);
		}

		//
		// Create Receipt Schedule
		final BigDecimal qty = BigDecimal.valueOf(4300);
		receiptSchedule = createReceiptSchedule(qty);

		//
		// Create WeightNet Adjuster instace for testing
		rsNetWeightAdjuster = new HUReceiptScheduleWeightNetAdjuster(huContext);

		//
		// Misc
		// NOT (we don't care): enabling strict values because we want to make sure that only defined fields are used
		// POJOWrapper.getWrapper(rs).setStrictValues(true);
	}

	protected I_M_ReceiptSchedule createReceiptSchedule(final BigDecimal qty)
	{
		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class, helper.contextProvider);
		receiptSchedule.setM_Product(pTomato);
		receiptSchedule.setC_UOM(uomKg);
		receiptSchedule.setQtyOrdered(qty);
		receiptSchedule.setQtyMoved(BigDecimal.ZERO);
		
		//
		// BPartner (needed for event notification)
		{
			final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, helper.contextProvider);
			InterfaceWrapperHelper.save(bpartner);
			receiptSchedule.setC_BPartner(bpartner);
		}

		//
		// Setup purchase order for our receipt schedule
		{
			final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class, receiptSchedule);
			order.setIsSOTrx(false); // purchase
			InterfaceWrapperHelper.save(order);

			final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class, receiptSchedule);
			orderLine.setC_Order(order);
			orderLine.setM_Product(receiptSchedule.getM_Product());
			orderLine.setC_UOM(receiptSchedule.getC_UOM());
			orderLine.setQtyOrdered(receiptSchedule.getQtyOrdered());
			orderLine.setPriceActual(rs_PriceActual);
			InterfaceWrapperHelper.save(orderLine);

			// link to receipt schedule:
			receiptSchedule.setC_Order(order);
			receiptSchedule.setC_OrderLine(orderLine);
			receiptSchedule.setAD_Table_ID(InterfaceWrapperHelper.getModelTableId(orderLine));
			receiptSchedule.setRecord_ID(orderLine.getC_OrderLine_ID());
		}

		//
		// Important!
		//
		// In order to have correct redistribution test cases, we need to have an ASI set (i.e HUAttributeStorage -> ASIAttributeStorage, Apply Transfer Strategy)
		{
			final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class, helper.contextProvider);
			InterfaceWrapperHelper.save(asi);

			receiptSchedule.setM_AttributeSetInstance(asi);
		}

		//
		// Configure RS warehouse
		receiptSchedule.setM_Warehouse(helper.defaultWarehouse);

		//
		// Save the receipt schedule
		InterfaceWrapperHelper.save(receiptSchedule);

		return receiptSchedule;
	}

	/**
	 * Initialize Receipt Schedule HU Allocations for given HUs
	 *
	 * @param tusToAllocate TUs to Allocate
	 */
	protected final void initReceiptScheduleAllocations(final List<I_M_HU> tusToAllocate)
	{
		rsAllocations = new ReceiptScheduleHUAllocations(receiptSchedule);
		rsAllocations.addAssignedHUs(tusToAllocate);

		final I_M_HU luHU = null; // no LU
		for (final I_M_HU tuHU : tusToAllocate)
		{
			InterfaceWrapperHelper.setTrxName(tuHU, ITrx.TRXNAME_None); // FIXME workaround
			for (final I_M_HU vhu : handlingUnitsDAO.retrieveIncludedHUs(tuHU))
			{
				final BigDecimal qtyToAllocate = huContext.getHUStorageFactory().getStorage(vhu).getQtyForProductStorages();

				final boolean deleteOldTUAllocations = false; // don't care; there shall be none
				rsAllocations.allocate(luHU, tuHU, vhu, qtyToAllocate, uomKg, deleteOldTUAllocations);
			}

			// Set HU_CostPrice
			final IAttributeStorage tuAttributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(tuHU);
			rs_PriceActual_Expectation.updateCostPriceToTU(tuAttributeStorage);
		}
	}

	protected final void recreateAllocationsForTopLevelTUs(final List<I_M_HU> tusToAllocate, final boolean deleteOldTUAllocations)
	{
		final I_M_HU luHU = null; // no LU
		for (final I_M_HU tuHU : tusToAllocate)
		{
			List<I_M_HU> vhus;
			if (handlingUnitsBL.isVirtual(tuHU))
			{
				vhus = Collections.singletonList(tuHU);
			}
			else
			{
				vhus = handlingUnitsDAO.retrieveIncludedHUs(tuHU);
			}

			boolean deleteOldTUAllocationsToUse = deleteOldTUAllocations;
			for (final I_M_HU vhu : vhus)
			{
				final BigDecimal qtyToAllocate = huContext.getHUStorageFactory().getStorage(vhu).getQtyForProductStorages();

				rsAllocations.allocate(luHU, tuHU, vhu, qtyToAllocate, uomKg, deleteOldTUAllocationsToUse);
				deleteOldTUAllocationsToUse = false;
			}

			// Set HU_CostPrice
			final IAttributeStorage tuAttributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(tuHU);
			rs_PriceActual_Expectation.updateCostPriceToTU(tuAttributeStorage);
		}
	}

	@Override
	protected LUWeightsExpectations<Object> newLUWeightsExpectations()
	{
		return super.newLUWeightsExpectations()
				.setVHUCostPriceExpectation(rs_PriceActual_Expectation);
	}

	@Override
	protected TUWeightsExpectations<Object> newTUWeightsExpectations()
	{
		return super.newTUWeightsExpectations()
				.setVHUCostPriceExpectation(rs_PriceActual_Expectation);
	}

	/**
	 * Set WeightGross to given HUs using the standard value ({@link #weightGrossPaloxe}).
	 *
	 * After that, it will test if the HUs weights are matching our standard expectations ({@link #standardPaloxeWeightExpectation}).
	 *
	 * @param hus
	 */
	protected void setWeightGrossToStandardAndTest(final List<I_M_HU> hus)
	{
		setWeightGross(hus, standardPaloxeWeightExpectation.getWeightGross());

		newTUWeightsExpectations()
				.setDefaultTUExpectation(standardPaloxeWeightExpectation)
				.assertExpected(hus);
	}

}
