package de.metas.handlingunits.model.validator;

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
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.MutableBigDecimal;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPCostCollectorBL;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.pporder.api.IHUPPCostCollectorBL;
import de.metas.handlingunits.pporder.api.impl.PPOrderHUAssignmentListener;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;

@Validator(I_PP_Cost_Collector.class)
public class PP_Cost_Collector
{

	@Init
	public void init()
	{
		Services.get(IHUAssignmentBL.class)
				.registerHUAssignmentListener(PPOrderHUAssignmentListener.instance);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void reverseCostCollector(final I_PP_Cost_Collector cc)
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		// Make sure given cost collector is the original document and not it's reversal
		if (ppCostCollectorBL.isReversal(cc))
		{
			return;
		}

		if (ppCostCollectorBL.isMaterialReceipt(cc, true)) // considerCoProductsAsReceipt=true
		{
			reverseCostCollector_Receipt(cc);
		}
		else if (ppCostCollectorBL.isMaterialIssue(cc, false)) // considerCoProductsAsIssue=false
		{
			reverseCostCollector_Issue(cc);
		}
		else
		{
			Services.get(IHUPPCostCollectorBL.class).assertNoHUAssignments(cc);
		}

	}

	/**
	 * On reversing {@link I_PP_Cost_Collector} of type Receipt (including Co/By-Product receipts):
	 * 
	 * <ul>
	 * <li>destroy all assigned top level HUs because they were created by this cost collector
	 * <li>make sure those HUs were not touched
	 * </ul>
	 * 
	 * @param cc
	 */
	private final void reverseCostCollector_Receipt(final I_PP_Cost_Collector cc)
	{
		// services
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		final IHUPPCostCollectorBL huPPCostCollectorBL = Services.get(IHUPPCostCollectorBL.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		//
		// Retrieve the HUs which were assigned to original cost collector
		final List<I_M_HU> hus = huPPCostCollectorBL.getTopLevelHUs(cc);
		if (hus.isEmpty())
		{
			return;
		}

		//
		// Get cost collector receipt infos.
		// We will validate the assigned top level HUs against these informations.
		final I_M_Product receiptProduct = cc.getM_Product();
		final I_M_Locator receiptLocator = cc.getM_Locator();
		final I_C_UOM receiptQtyUOM = cc.getC_UOM();
		final BigDecimal receiptQty;
		if (ppCostCollectorBL.isCoOrByProductReceipt(cc))
		{
			// NOTE: because a co/by product receipt is actually a negative issue, we need to negate the CC's MovementQty, in order to get a positive value.
			receiptQty = cc.getMovementQty().negate();
		}
		else
		{
			receiptQty = cc.getMovementQty();
		}

		//
		// Destroy assigned HUs
		final IContextAware context = InterfaceWrapperHelper.getContextAware(cc);
		huTrxBL.createHUContextProcessorExecutor(context)
				.run(new IHUContextProcessor()
				{
					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						final MutableBigDecimal huQtySum = new MutableBigDecimal();

						for (final I_M_HU hu : hus)
						{
							// Make sure the HU is on the same locator where we received it
							if (hu.getM_Locator_ID() != receiptLocator.getM_Locator_ID())
							{
								throw new HUException("@NotMatched@ @M_Locator_ID@"
										+ "\n @Expected@: " + receiptLocator
										+ "\n @Actual@: " + hu.getM_Locator()
										+ "\n @M_HU_ID@: " + handlingUnitsBL.getDisplayName(hu));
							}

							//
							// Sum up the quantity for our receipt product. We will check it at them end.
							// Make sure the HUs does contain any other product then our receipt product.
							final IHUStorage huStorage = huContext.getHUStorageFactory().getStorage(hu);
							for (final IHUProductStorage productStorage : huStorage.getProductStorages())
							{
								// Skip ZERO quantity storages => those are not relevant
								final BigDecimal qty = productStorage.getQty(receiptQtyUOM);
								if (qty.signum() == 0)
								{
									continue;
								}

								// Make sure we have HU stoarges only about our received product
								if (productStorage.getM_Product().getM_Product_ID() != receiptProduct.getM_Product_ID())
								{
									throw new HUException("@NotMatched@ @M_M_Product_ID@"
											+ "\n @Expected@: " + receiptProduct
											+ "\n @Actual@: " + productStorage.getM_Product()
											+ "\n @M_HU_ID@: " + handlingUnitsBL.getDisplayName(hu));
								}

								// sum up the HU qty for our received product
								huQtySum.add(qty);
							}

							handlingUnitsBL.markDestroyed(huContext, hu);
						} // each HU

						//
						// Make sure the SUM of all HU storages for our product matches the receipt quantity of this cost collector
						if (!huQtySum.comparesEqualTo(receiptQty))
						{
							throw new HUException("@NotMatched@ @Qty@"
									+ "\n @Expected@: " + receiptQty
									+ "\n @Actual@: " + huQtySum);
						}

						return NULL_RESULT;
					}
				});
	}

	private final void reverseCostCollector_Issue(final I_PP_Cost_Collector cc)
	{
		final IHUPPCostCollectorBL huPPCostCollectorBL = Services.get(IHUPPCostCollectorBL.class);
		huPPCostCollectorBL.restoreTopLevelHUs(cc);
	}
	
	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_CLOSE,
			ModelValidator.TIMING_BEFORE_VOID
	})
	public void  deactivatePPOrderProductAttributes (final I_PP_Cost_Collector costCollector)
	{
		Services.get(IPPOrderProductAttributeDAO.class).deactivateForCostCollector(costCollector);
	}
}
