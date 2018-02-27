package de.metas.handlingunits.pporder.api.impl;

import java.math.BigDecimal;
import java.util.ArrayList;

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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUContext;
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
import de.metas.handlingunits.attribute.IPPOrderProductAttributeBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.pporder.api.impl.AbstractPPOrderReceiptHUProducer.CreateReceiptCandidateRequest;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/* package */abstract class AbstractPPOrderReceiptHUProducer implements IPPOrderReceiptHUProducer
{
	// Services
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final transient IPPOrderProductAttributeBL ppOrderProductAttributeBL = Services.get(IPPOrderProductAttributeBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	// Parameters
	private final int _ppOrderId;
	private transient I_M_HU_LUTU_Configuration _lutuConfiguration;
	private Date _movementDate;
	@Deprecated
	private boolean _skipCreateCandidates;

	// State
	private final List<I_PP_Order_Qty> createdCandidates = new ArrayList<>();

	protected abstract I_M_Product getM_Product();

	protected abstract Object getAllocationRequestReferencedModel();

	protected abstract IAllocationSource createAllocationSource();

	protected abstract IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager();

	protected abstract I_PP_Order_Qty newCandidate();

	protected abstract void setAssignedHUs(final Collection<I_M_HU> hus);

	public AbstractPPOrderReceiptHUProducer(final int ppOrderId)
	{
		Preconditions.checkArgument(ppOrderId > 0, "ppOrderId not valid");
		this._ppOrderId = ppOrderId;
	}

	@Override
	@Deprecated
	public void setSkipCreateCandidates()
	{
		this._skipCreateCandidates = true;
	}

	@Deprecated
	private boolean isSkipCreateCandidates()
	{
		return _skipCreateCandidates;
	}

	private int getPP_Order_ID()
	{
		return _ppOrderId;
	}

	@Override
	public final List<I_M_HU> createReceiptCandidatesAndPlanningHUs()
	{
		return trxManager.call(() -> {

			final I_M_HU_LUTU_Configuration lutuConfig = getCreateLUTUConfiguration();
			final Quantity qtyCUsTotal = lutuConfigurationFactory.calculateQtyCUsTotal(lutuConfig);
			if (qtyCUsTotal.isZero())
			{
				throw new AdempiereException("Zero quantity to receive");
			}
			else if (qtyCUsTotal.isInfinite())
			{
				throw new AdempiereException("Quantity to receive was not determined");
			}

			return createHUsInTrx(qtyCUsTotal.getQty(), qtyCUsTotal.getUOM());
		});
	}

	@Override
	public final List<I_M_HU> createReceiptCandidatesAndPlanningHUs(final BigDecimal qtyToReceive, final I_C_UOM uom)
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
		final CreateReceiptCandidateRequestCollector ppOrderReceiptCandidateCollector = new CreateReceiptCandidateRequestCollector();
		huContext.getTrxListeners().callAfterLoad(ppOrderReceiptCandidateCollector::addAllocationResults);

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
		// Create receipt candidates
		if (!isSkipCreateCandidates())
		{
			ppOrderReceiptCandidateCollector
					.streamRequests()
					.forEach(this::createReceiptCandidateFromRequest);
		}

		//
		// Generate the HUs
		final List<I_M_HU> createdHUs = huProducerDestination.getCreatedHUs();

		//
		// Update received HUs
		InterfaceWrapperHelper.setThreadInheritedTrxName(createdHUs); // just to be sure
		updateReceivedHUs(createdHUs);

		//
		// Return created HUs
		return createdHUs;
	}

	@Override
	public void createReceiptCandidatesFromPlanningHU(final I_M_HU planningHU)
	{
		Preconditions.checkNotNull(planningHU);
		if (!X_M_HU.HUSTATUS_Planning.equals(planningHU.getHUStatus()))
		{
			throw new HUException("HU " + planningHU + " shall have status Planning but it has " + planningHU.getHUStatus());
		}

		huTrxBL.process(huContext -> {
			InterfaceWrapperHelper.setThreadInheritedTrxName(planningHU); // just to be sure

			//
			// Delete previously created candidates
			// Assume there are no processed one, and even if it would be it would fail on DAO level
			huPPOrderQtyDAO.streamOrderQtys(getPP_Order_ID())
					.filter(candidate -> candidate.getM_HU_ID() == planningHU.getM_HU_ID())
					.forEach(huPPOrderQtyDAO::delete);

			// Extract it if not top level
			huTrxBL.setParentHU(huContext,
					null,
					planningHU,
					true // destroyOldParentIfEmptyStorage
			);

			final int topLevelHUId = planningHU.getM_HU_ID();
			final int locatorId = planningHU.getM_Locator_ID();

			// Stream all product storages
			// ... and create planning receipt candidates
			huContext.getHUStorageFactory()
					.getStorage(planningHU)
					.getProductStorages().stream()
					// FIXME: validate if the product from storage is accepted
					//
					// Create candidate request
					.map(productStorage -> CreateReceiptCandidateRequest.builder()
							.locatorId(locatorId)
							.topLevelHUId(topLevelHUId)
							.productId(productStorage.getM_Product().getM_Product_ID())
							.build()
							.addQty(productStorage.getQty(), productStorage.getC_UOM()))
					//
					// Create candidate from request
					.forEach(this::createReceiptCandidateFromRequest);

			//
			updateReceivedHUs(ImmutableSet.of(planningHU));
		});
	}

	private void updateReceivedHUs(final Collection<I_M_HU> hus)
	{
		//
		// Modify the HU Attributes based on the attributes already existing from issuing (task 08177)
		ppOrderProductAttributeBL.updateHUAttributes(hus, getPP_Order_ID());

		//
		// Assign HUs to PP_Order/PP_Order_BOMLine
		setAssignedHUs(hus);
	}

	/**
	 * Creates candidate from request.
	 *
	 * NOTE: when implementing you could start by calling {@link #prepareReceiptCandidate(CreateReceiptCandidateRequest)}.
	 *
	 * @param request
	 * @return candidate
	 */
	private void createReceiptCandidateFromRequest(@NonNull final CreateReceiptCandidateRequest request)
	{
		final Date movementDate = getMovementDate();

		final I_PP_Order_Qty candidate = newCandidate();
		candidate.setM_Locator_ID(request.getLocatorId());
		candidate.setM_HU_ID(request.getTopLevelHUId());
		candidate.setM_Product_ID(request.getProductId());
		candidate.setQty(request.getQty().getQty());
		candidate.setC_UOM(request.getQty().getUOM());
		candidate.setMovementDate(TimeUtil.asTimestamp(movementDate));
		candidate.setProcessed(false);
		huPPOrderQtyDAO.save(candidate);

		createdCandidates.add(candidate);
	}

	@Override
	public List<I_PP_Order_Qty> getCreatedCandidates()
	{
		return ImmutableList.copyOf(createdCandidates);
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
				referencedModel, // referenced model
				true // forceQtyAllocation: make sure we will transfer the given qty, no matter what
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
	public final IPPOrderReceiptHUProducer setM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		Check.assumeNotNull(lutuConfiguration, "Parameter lutuConfiguration is not null");
		_lutuConfiguration = lutuConfiguration;
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

	/**
	 * Aggregates {@link HUTransactionCandidate}s and creates manufacturing receipt candidates requests.
	 */
	private static final class CreateReceiptCandidateRequestCollector
	{
		private final Map<Integer, CreateReceiptCandidateRequest> requestsByTopLevelHUId = new HashMap<>();

		public Stream<CreateReceiptCandidateRequest> streamRequests()
		{
			return requestsByTopLevelHUId.values()
					.stream()
					.filter(candidate -> !candidate.isZeroQty());
		}

		public void addAllocationResults(final IHUContext IGNORED, @NonNull final List<IAllocationResult> loadResults)
		{
			loadResults.stream()
					.flatMap(loadResult -> loadResult.getTransactions().stream())
					.forEach(this::addHUTransaction);
		}

		private void addHUTransaction(final IHUTransactionCandidate huTransaction)
		{
			final I_M_HU hu = huTransaction.getM_HU();
			if (hu == null)
			{
				return;
			}

			final Quantity quantity = huTransaction.getQuantity();
			if (quantity.isZero())
			{
				return;
			}

			final I_M_HU topLevelHU = Services.get(IHandlingUnitsBL.class).getTopLevelParent(hu);

			final CreateReceiptCandidateRequest request = requestsByTopLevelHUId.computeIfAbsent(topLevelHU.getM_HU_ID(), topLevelHUId -> CreateReceiptCandidateRequest.builder()
					.locatorId(huTransaction.getM_Locator().getM_Locator_ID())
					.topLevelHUId(topLevelHUId)
					.productId(huTransaction.getProductId())
					.build());
			request.addQty(quantity);
		}
	}

	@Data
	@Builder
	static final class CreateReceiptCandidateRequest
	{
		private final int locatorId;
		private final int topLevelHUId;
		private final int productId;
		private Quantity qty;

		public CreateReceiptCandidateRequest addQty(final Quantity qtyToAdd)
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

		public CreateReceiptCandidateRequest addQty(final BigDecimal qty, final I_C_UOM uom)
		{
			addQty(new Quantity(qty, uom));
			return this;
		}

		public boolean isZeroQty()
		{
			return qty == null || qty.isZero();
		}
	}

}
