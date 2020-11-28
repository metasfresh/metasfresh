package de.metas.handlingunits.model.validator;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPCostCollectorBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.ModelValidator;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.PPCostCollectorId;

import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

@Interceptor(I_PP_Cost_Collector.class)
public class PP_Cost_Collector
{
	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void reverseCostCollector(final I_PP_Cost_Collector cc)
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		// Make sure given cost collector is the original document and not it's reversal
		if (ppCostCollectorBL.isReversal(cc))
		{
			return;
		}

		if (ppCostCollectorBL.isMaterialReceiptOrCoProduct(cc))
		{
			reverseCostCollector_Receipt(cc);
		}
		else if (ppCostCollectorBL.isAnyComponentIssue(cc))
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
	 */
	private void reverseCostCollector_Receipt(final I_PP_Cost_Collector cc)
	{
		// services
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
		// We will validate the assigned top level HUs against this information.
		final ProductId receiptProductId = ProductId.ofRepoId(cc.getM_Product_ID());
		final int receiptLocatorId = cc.getM_Locator_ID();
		final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
		final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);
		final Quantity receiptQty;
		if (costCollectorType.isCoOrByProductReceipt())
		{
			// NOTE: because a co/by product receipt is actually a negative issue, we need to negate the CC's MovementQty, in order to get a positive value.
			receiptQty = costCollectorBL.getMovementQty(cc).negate();
		}
		else
		{
			receiptQty = costCollectorBL.getMovementQty(cc);
		}

		//
		// Destroy assigned HUs
		final IContextAware context = InterfaceWrapperHelper.getContextAware(cc);
		huTrxBL.createHUContextProcessorExecutor(context)
				.run(huContext -> {
					Quantity huQtySum = Quantity.zero(receiptQty.getUOM());
					for (final I_M_HU hu : hus)
					{
						// Make sure the HU is on the same locator where we received it
						if (hu.getM_Locator_ID() != receiptLocatorId)
						{
							throw new HUException("M_Locator_ID mismatch between HU and cost collector")
									.appendParametersToMessage()
									.setParameter("Expected M_Locator_ID", receiptLocatorId)
									.setParameter("Actual M_Locator", IHandlingUnitsBL.extractLocatorOrNull(hu))
									.setParameter("PP_Cost_Collector", cc)
									.setParameter("M_HU", handlingUnitsBL.getDisplayName(hu));
						}

						//
						// Sum up the quantity for our receipt product. We will check it at them end.
						// Make sure the HUs does contain any other product then our receipt product.
						final IHUStorage huStorage = huContext.getHUStorageFactory().getStorage(hu);
						for (final IHUProductStorage productStorage : huStorage.getProductStorages())
						{
							// Skip ZERO quantity storages => those are not relevant
							final Quantity qty = productStorage.getQty(huQtySum.getUOM());
							if (qty.signum() == 0)
							{
								continue;
							}

							// Make sure we have HU storages only about our received product
							if (!ProductId.equals(productStorage.getProductId(), receiptProductId))
							{
								final IProductBL productsService = Services.get(IProductBL.class);
								throw new HUException("M_Product_ID mismatch between HU and cost collector")
										.appendParametersToMessage()
										.setParameter("Expected M_Product", productsService.getProductValueAndName(receiptProductId))
										.setParameter("Actual M_Product", productsService.getProductValueAndName(productStorage.getProductId()))
										.setParameter("PP_Cost_Collector", cc)
										.setParameter("M_HU", handlingUnitsBL.getDisplayName(hu));
							}

							// sum up the HU qty for our received product
							huQtySum = huQtySum.add(qty);
						}
					} // each HU

					//
					// Make sure the SUM of all HU storages for our product matches the receipt quantity of this cost collector
					if (!huQtySum.qtyAndUomCompareToEquals(receiptQty))
					{
						throw new HUException("Quantity mismatch between sum quantity of HUs and cost collector")
								.appendParametersToMessage()
								.setParameter("Expected quantity", receiptQty)
								.setParameter("Actual HUs quantity sum", huQtySum)
								.setParameter("PP_Cost_Collector", cc)
								.setParameter("M_HUs", hus);
					}

					// Destroy the HUs
					for (final I_M_HU hu : hus)
					{
						handlingUnitsBL.markDestroyed(huContext, hu);
					}
				});

		//
		// Delete receipt candidates
		final Set<Integer> huIds = hus.stream().map(I_M_HU::getM_HU_ID).collect(ImmutableSet.toImmutableSet());
		final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(cc.getPP_Order_ID());
		huPPOrderQtyDAO
				.retrieveOrderQtys(ppOrderId)
				.stream()
				.filter(receiptCandidate -> huIds.contains(receiptCandidate.getM_HU_ID()))
				.forEach(receiptCandidate -> {
					receiptCandidate.setProcessed(false);
					huPPOrderQtyDAO.delete(receiptCandidate);
				});
	}

	private void reverseCostCollector_Issue(@NonNull final I_PP_Cost_Collector cc)
	{
		final IHUPPCostCollectorBL huPPCostCollectorBL = Services.get(IHUPPCostCollectorBL.class);
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
		huPPCostCollectorBL.restoreTopLevelHUs(cc);

		// Delete issue candidate
		final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(cc.getPP_Order_ID());
		final I_PP_Order_Qty issueCandidate = huPPOrderQtyDAO.retrieveOrderQtyForCostCollector(
				ppOrderId,
				PPCostCollectorId.ofRepoId(cc.getPP_Cost_Collector_ID()));
		if (issueCandidate != null)
		{
			final I_M_HU huToVerify = issueCandidate.getM_HU();

			// "active" might also be fine, depending on whether the HU was issued using a legacy swing client
			if (!huStatusBL.isStatusActiveOrIssued(huToVerify))
			{
				throw new HUException("Expected the HU's status to be 'issued' (or 'active') again but it wasn't.")
						.setParameter("HUStatus", huToVerify.getHUStatus())
						.setParameter("HU", huToVerify)
						.setParameter("candidate", issueCandidate)
						.setParameter("costCollector", cc)
						.appendParametersToMessage();
			}

			issueCandidate.setProcessed(false);
			huPPOrderQtyDAO.delete(issueCandidate);
		}

		// set the HU back to 'active' if needed
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(getContextAware(cc));
		for (final I_M_HU topLevelHU : huPPCostCollectorBL.getTopLevelHUs(cc))
		{
			if (huStatusBL.isStatusIssued(topLevelHU))
			{
				huStatusBL.setHUStatus(huContext, topLevelHU, X_M_HU.HUSTATUS_Active);
				handlingUnitsDAO.saveHU(topLevelHU);
			}
		}
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_CLOSE,
			ModelValidator.TIMING_BEFORE_VOID
	})
	public void deactivatePPOrderProductAttributes(final I_PP_Cost_Collector costCollector)
	{
		Services.get(IPPOrderProductAttributeDAO.class).deactivateForCostCollector(costCollector.getPP_Cost_Collector_ID());
	}
}
