package de.metas.handlingunits.pporder.api.impl;

import java.math.BigDecimal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTrxListener;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.impl.HUTransaction;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import lombok.Builder;
import lombok.Data;

/* package */abstract class AbstractPPOrderReceiptHUProducer implements IPPOrderReceiptHUProducer
{
	// Services
	protected final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	protected final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	protected final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	protected final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	//
	private transient I_M_HU_LUTU_Configuration _lutuConfiguration;
	private Date _movementDate;

	protected abstract I_M_Product getM_Product();

	protected abstract Object getAllocationRequestReferencedModel();

	protected abstract IAllocationSource createAllocationSource();

	protected abstract IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager();

	protected abstract void processReceiptCandidate(PPOrderReceiptCandidate candidate);

	protected abstract void setAssignedHUs(final Collection<I_M_HU> hus);

	@Override
	public final List<I_M_HU> receiveHUs(final BigDecimal qtyToReceive, final I_C_UOM uom)
	{
		return trxManager.call(() -> createHUsInTrx(qtyToReceive, uom));
	}

	private final List<I_M_HU> createHUsInTrx(final BigDecimal qtyToReceive, final I_C_UOM uom)
	{
		//
		// Create HU Context
		trxManager.assertThreadInheritedTrxExists();
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		//
		final PPOrderReceiptCandidateCollector ppOrderReceiptCandidateCollector = new PPOrderReceiptCandidateCollector();
		huContext.getTrxListeners().addListener(ppOrderReceiptCandidateCollector);

		//
		// Create Allocation Source
		final IAllocationSource ppOrderAllocationSource = createAllocationSource();

		//
		// Create Allocation Destination
		final IHUProducerAllocationDestination huProducerDestination = createAllocationDestination()
				// Make sure we are generating the HUs in Planning (task 08077)
				.setHUStatus(X_M_HU.HUSTATUS_Planning);

		//
		// Create Allocation Request
		final IAllocationRequest allocationRequest = createAllocationRequest(huContext, qtyToReceive, uom);

		//
		// Execute transfer
		final HULoader loader = HULoader.of(ppOrderAllocationSource, huProducerDestination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false);
		final IAllocationResult allocationResult = loader.load(allocationRequest);
		Check.assume(allocationResult.isCompleted(), "Result shall be completed: {}", allocationResult);

		//
		// Process receipt candidates
		ppOrderReceiptCandidateCollector
				.streamReceiptCandidates()
				.forEach(this::processReceiptCandidate);

		//
		// Assign created HUs to Receipt Cost Collector
		final List<I_M_HU> createdHUs = huProducerDestination.getCreatedHUs();
		InterfaceWrapperHelper.setThreadInheritedTrxName(createdHUs);
		setAssignedHUs(createdHUs);

		//
		// Return created HUs
		return createdHUs;
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public IPPOrderReceiptHUProducer setMovementDate(final Date movementDate)
	{
		Check.assumeNotNull(movementDate, "Parameter movementDate is not null");
		_movementDate = movementDate;
		return this;
	}

	protected final Date getMovementDate()
	{
		if (_movementDate == null)
		{
			_movementDate = SystemTime.asTimestamp();
		}
		return _movementDate;
	}

	private IAllocationRequest createAllocationRequest(final IHUContext huContext, final BigDecimal qtyToReceive, final I_C_UOM uom)
	{
		final I_M_Product product = getM_Product();
		final Date date = getMovementDate();
		final Object referencedModel = getAllocationRequestReferencedModel();

		final IAllocationRequest allocationRequest = AllocationUtils.createQtyRequest(huContext,
				product, // product
				qtyToReceive, // the quantity to receive
				uom,
				date, // transaction date
				referencedModel // referenced model
		);

		return allocationRequest;
	}

	private final IHUProducerAllocationDestination createAllocationDestination()
	{
		final I_M_HU_LUTU_Configuration lutuConfiguration = getCreateLUTUConfiguration();
		final ILUTUProducerAllocationDestination lutuProducer = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
		return lutuProducer;
	}

	@Override
	public final IPPOrderReceiptHUProducer setM_HU_LUTU_Configuration(I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		Check.assumeNotNull(lutuConfiguration, "Parameter lutuConfiguration is not null");
		this._lutuConfiguration = lutuConfiguration;
		return this;
	}

	private final I_M_HU_LUTU_Configuration getCreateLUTUConfiguration()
	{
		if (_lutuConfiguration == null)
		{
			_lutuConfiguration = createReceiptLUTUConfigurationManager()
					.startEditing() // start editing just to make sure the LU/TU configuration is created if does not already exist
					.pushBackToModel()
					.getLUTUConfiguration();
		}
		return _lutuConfiguration;
	}

	protected I_PP_Order_Qty preparePPOrderQty(final PPOrderReceiptCandidate candidate)
	{
		final Quantity quantity = candidate.getQty();

		final I_PP_Order_Qty ppOrderQty = InterfaceWrapperHelper.newInstance(I_PP_Order_Qty.class);
		ppOrderQty.setM_Locator_ID(candidate.getLocatorId());
		ppOrderQty.setM_HU_ID(candidate.getTopLevelHUId());
		ppOrderQty.setM_Product_ID(candidate.getProductId());
		ppOrderQty.setQty(quantity.getQty());
		ppOrderQty.setC_UOM(quantity.getUOM());
		ppOrderQty.setMovementDate(TimeUtil.asTimestamp(getMovementDate()));
		ppOrderQty.setProcessed(false);

		// no save because we did not fill mandatory fields like PP_Order, PP_Order_BOMLine

		return ppOrderQty;
	}

	/**
	 * Listens on after load {@link HUTransaction}s, aggregates them and creates manufacturing receipt candidates.
	 */
	private static final class PPOrderReceiptCandidateCollector implements IHUTrxListener
	{
		private final Map<Integer, PPOrderReceiptCandidate> receiptCandidatesByTopLevelHUId = new HashMap<>();

		public Stream<PPOrderReceiptCandidate> streamReceiptCandidates()
		{
			return receiptCandidatesByTopLevelHUId.values()
					.stream()
					.filter(candidate -> !candidate.isZeroQty());
		}

		@Override
		public void afterLoad(final IHUContext huContext, final List<IAllocationResult> loadResults)
		{
			loadResults.stream()
					.flatMap(loadResult -> loadResult.getTransactions().stream())
					.forEach(huTransaction -> afterLoad(huContext, huTransaction));
		}

		private void afterLoad(final IHUContext huContext, final IHUTransaction huTransaction)
		{
			final I_M_HU hu = huTransaction.getM_HU();
			if (hu == null)
			{
				return;
			}

			final I_M_Product product = huTransaction.getProduct();
			if (product == null)
			{
				return; // shall not happen
			}

			final Quantity quantity = huTransaction.getQuantity();
			final I_M_HU topLevelHU = Services.get(IHandlingUnitsBL.class).getTopLevelParent(hu);

			final PPOrderReceiptCandidate candidate = receiptCandidatesByTopLevelHUId.computeIfAbsent(topLevelHU.getM_HU_ID(), topLevelHUId -> PPOrderReceiptCandidate.builder()
					.locatorId(huTransaction.getM_Locator().getM_Locator_ID())
					.topLevelHUId(topLevelHUId)
					.productId(product.getM_Product_ID())
					.build());
			candidate.addQty(quantity);
		}
	}

	@Data
	@Builder
	public static final class PPOrderReceiptCandidate
	{
		private final int locatorId;
		private final int topLevelHUId;
		private final int productId;
		private Quantity qty = null;

		public PPOrderReceiptCandidate addQty(final Quantity qtyToAdd)
		{
			Check.assumeNotNull(qtyToAdd, "Parameter qtyToAdd is not null");
			if (qty == null)
			{
				qty = qtyToAdd;
			}
			else
			{
				qty = qty.add(qtyToAdd);
			}
			return this;
		}

		public boolean isZeroQty()
		{
			return qty == null || qty.isZero();
		}
	}

}
