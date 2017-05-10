package de.metas.handlingunits.inout.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.GenericListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class CustomerReturnLineHUGenerator
{

	// // services
	// private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	// private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	// private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	//
	//
	// private ILUTUProducerAllocationDestination _lutuProducer;
	//
	// private final Map<Integer, IProductStorage> _inOutLine2productStorage = new HashMap<>();
	//
	// private I_M_InOutLine _inOutLine;
	//
	// private I_M_HU_LUTU_Configuration _lutuConfiguration;
	// private IDocumentLUTUConfigurationManager _lutuConfigurationManager;
	//
	//
	// public I_M_InOutLine getInOutLine()
	// {
	// return _inOutLine;
	// }
	//
	// public void setInOutLine(I_M_InOutLine inOutLine)
	// {
	// this._inOutLine = inOutLine;
	// }
	//
	// private IContextAware _contextInitial;
	// private Quantity _qtyToAllocateTarget = null;
	//
	// public static final CustomerReturnLineHUGenerator newInstance(final IContextAware context)
	// {
	// final IContextAware contextToUse;
	// if (ITrx.TRXNAME_ThreadInherited.equals(context.getTrxName()))
	// {
	// contextToUse = context;
	// }
	// else
	// {
	// contextToUse = PlainContextAware.newWithThreadInheritedTrx(context.getCtx());
	// }
	// return new CustomerReturnLineHUGenerator()
	// .setContext(contextToUse);
	// }
	//
	// private final IContextAware getContextInitial()
	// {
	// Check.assumeNotNull(_contextInitial, "_contextInitial not null");
	// return _contextInitial;
	// }
	//
	// private final CustomerReturnLineHUGenerator setContext(final IContextAware context)
	// {
	// // needs to be threadInherited because we run in our own little TrxRunnable and everything created from the request shall be committed when we commit that runnable's local transaction.
	// Check.errorUnless(ITrx.TRXNAME_ThreadInherited.equals(context.getTrxName()),
	// "The trxName of the given request's HUContext needs to be {} or 'null', but is {}",
	// ITrx.TRXNAME_ThreadInherited, context.getTrxName());
	//
	// _contextInitial = context;
	// return this;
	// }
	//
	// public List<I_M_HU> generate()
	// {
	// final Quantity qtyCUsTotal = getQtyToAllocateTarget();
	// Check.assume(!qtyCUsTotal.isInfinite(), "QtyToAllocate(target) shall not be infinite");
	//
	// final IAllocationRequest request = createAllocationRequest(qtyCUsTotal);
	// final List<I_M_HU> hus = generateLUTUHandlingUnitsForQtyToAllocate(request);
	//
	// //
	// // Update receipt schedule's LU/TU configuration
	// if (isUpdateCustomerReturnDefaultConfiguration())
	// {
	// trxManager.run(() -> {
	// final I_M_HU_LUTU_Configuration lutuConfiguration = getM_HU_LUTU_Configuration();
	// getLUTUConfigurationManager().setCurrentLUTUConfigurationAndSave(lutuConfiguration);
	// });
	// }
	//
	// return hus;
	// }
	//
	// private final Quantity getQtyToAllocateTarget()
	// {
	// if (_qtyToAllocateTarget == null || _qtyToAllocateTarget.signum() <= 0)
	// {
	// throw new AdempiereException("Quantity to return shall be greather than zero");
	// }
	// return _qtyToAllocateTarget;
	// }
	//
	// public CustomerReturnLineHUGenerator setQtyToAllocateTarget(final Quantity qtyToAllocateTarget)
	// {
	// _qtyToAllocateTarget = qtyToAllocateTarget;
	// return this;
	// }
	//
	// private final IAllocationRequest createAllocationRequest(final Quantity qty)
	// {
	// final IContextAware contextProvider = getContextInitial();
	// final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContextForProcessing(contextProvider);
	//
	// final I_M_InOutLine inOutLine = getInOutLine();
	//
	// // ForceQtyAllocation=true, because
	// // * we really want to allocate unload this quantity
	// // * on loading side, HUItemStorage is considering our enforced capacity even if we do force allocation
	// final boolean forceQtyAllocation = true;
	//
	// //
	// // Create Allocation Request
	// IAllocationRequest request = AllocationUtils.createQtyRequest(
	// huContext,
	// inOutLine.getM_Product(),
	// qty,
	// SystemTime.asDate(),
	// inOutLine, // referencedModel,
	// forceQtyAllocation);
	//
	// // TODO: Initial attributes
	// //
	// // Setup initial attribute value defaults
	// // final List<I_M_ReceiptSchedule> receiptSchedules = getReceiptSchedules();
	// // request = huReceiptScheduleBL.setInitialAttributeValueDefaults(request, receiptSchedules);
	//
	// return request;
	// }
	//
	// /**
	// * Create Handling Units for not allocated qty of line.
	// *
	// * @param ctx
	// * @param maxLUsToCreate
	// * @param trxName
	// * @return generated HUs
	// */
	// private List<I_M_HU> generateLUTUHandlingUnitsForQtyToAllocate0(final IAllocationRequest request)
	// {
	// Check.assumeNotNull(request, "request not null");
	//
	// markNotConfigurable();
	//
	// //
	// // Source: our line
	// final IAllocationSource source = createAllocationSource();
	//
	// //
	// // Destination: our configured LU/TU Producer
	// final ILUTUProducerAllocationDestination destination = getLUTUProducerAllocationDestination();
	//
	// //
	// // Execute transfer
	// final HULoader loader = HULoader.of(source, destination);
	//
	// // Allow Partial Unloads (from source): no, we need to over allocated if required
	// loader.setAllowPartialUnloads(false);
	// // Allow Partial Loads (to destination): yes, because it could happen that we already have some pre-generated HUs (see "beforeLUTUProducerExecution" method)
	// loader.setAllowPartialLoads(true); // to Destination
	//
	// final IAllocationResult result = loader.load(request);
	//
	// //
	// // Make sure everything was transferred
	// final boolean allowPartialResults = loader.isAllowPartialLoads() || loader.isAllowPartialUnloads();
	// if (!allowPartialResults && !result.isCompleted())
	// {
	// final String errmsg = "Cannot create HU for " + this;
	// throw new AdempiereException(errmsg);
	// }
	//
	// //
	// // Get the newly created HUs
	// final List<I_M_HU> handlingUnits = destination.getCreatedHUs();
	//
	// return handlingUnits;
	// }
	//
	// private IAllocationSource createAllocationSource()
	// {
	// final I_M_InOutLine inoutLine = getInOutLine();
	// Check.assumeNotNull(inoutLine, "_inOutLine not null");
	//
	// final IProductStorage productStorage = getProductStorage(inoutLine);
	// final IAllocationSource allocationSource = new GenericAllocationSourceDestination(productStorage, inoutLine);
	//
	// return allocationSource;
	// }
	//
	// private IProductStorage getProductStorage(final I_M_InOutLine inOutLine)
	// {
	// final int inOutLineId = inOutLine.getM_InOutLine_ID();
	// IProductStorage productStorage = _inOutLine2productStorage.get(inOutLineId);
	// if (productStorage == null)
	// {
	// final I_M_Product product = inOutLine.getM_Product();
	// final I_C_UOM uom = inOutLine.getC_UOM();
	// final BigDecimal qty = inOutLine.getQtyEntered();
	//
	// productStorage = new PlainProductStorage(product, uom, qty);
	// Check.assumeNotNull(productStorage, "productStorage not null");
	// _inOutLine2productStorage.put(inOutLineId, productStorage);
	// }
	//
	// return productStorage;
	// }
	//
	// public ILUTUProducerAllocationDestination getLUTUProducerAllocationDestination()
	// {
	// if (_lutuProducer != null)
	// {
	// return _lutuProducer;
	// }
	//
	// final I_M_HU_LUTU_Configuration lutuConfiguration = getM_HU_LUTU_Configuration();
	// _lutuProducer = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
	//
	// //
	// // Ask the "lutuProducer" to consider currently created HUs that are linked to our receipt schedule
	// // In case they are suitable, they will be used, else they will be destroyed.
	// // NOTE: we do this only if we have only one M_ReceiptSchedule
	// // final I_M_ReceiptSchedule receiptSchedule = getSingleReceiptScheduleOrNull();
	//// if (receiptSchedule != null)
	//// {
	//// final IHUAllocations huAllocations = getHUAllocations(receiptSchedule);
	//// _lutuProducer.setExistingHUs(huAllocations);
	//// }
	//
	// //TODO
	// //markNotConfigurable();
	// return _lutuProducer;
	// }
	//
	// public I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration()
	// {
	// if (_lutuConfiguration != null)
	// {
	// return _lutuConfiguration;
	// }
	//
	// _lutuConfiguration = getLUTUConfigurationManager().getCurrentLUTUConfigurationOrNull();
	// Check.assumeNotNull(_lutuConfiguration, "_lutuConfiguration not null");
	//
	// //markNotConfigurable();
	// return _lutuConfiguration;
	// }
	// public IDocumentLUTUConfigurationManager getLUTUConfigurationManager()
	// {
	// if (_lutuConfigurationManager == null)
	// {
	// final List<I_M_ReceiptSchedule> receiptSchedules = getReceiptSchedules();
	// _lutuConfigurationManager = huReceiptScheduleBL.createLUTUConfigurationManager(receiptSchedules);
	// markNotConfigurable();
	// }
	// return _lutuConfigurationManager;
	// }

	/**
	 * 
	 * @param context: the context to be used when creating the HUs. This context will also be used for the {@link IHUContext} the HU processing will have place with. <br>
	 *            If its {@code trxName} is not {@link ITrx#TRXNAME_ThreadInherited}, then this method will create a new context that only has the given context's {@link IContextAware#getCtx()} but not trxName.<br>
	 *            Because the {@link #generate()} method depends on the {@code trxName} being thread-inherited.
	 */
	public static final CustomerReturnLineHUGenerator newInstance(final IContextAware context)
	{
		final IContextAware contextToUse;
		if (ITrx.TRXNAME_ThreadInherited.equals(context.getTrxName()))
		{
			contextToUse = context;
		}
		else
		{
			contextToUse = PlainContextAware.newWithThreadInheritedTrx(context.getCtx());
		}
		return new CustomerReturnLineHUGenerator()
				.setContext(contextToUse);
	}

	// services
	private final transient IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	//
	// Parameters
	private IContextAware _contextInitial;
	private final List<I_M_InOutLine> _inOutLines = new ArrayList<>();
	private final Map<Integer, IProductStorage> _inOutLine2productStorage = new HashMap<>();
	private final Map<Integer, IHUAllocations> _inOutLine2huAllocations = new HashMap<>();
	private Quantity _qtyToAllocateTarget = null;
	//
	private boolean updateReceiptScheduleDefaultConfiguration = false; // default false, backward compatible; this flag is not considered by #generateAllPlanningHUs_InChunks()

	//
	// Status
	private boolean _configurable = true;
	private IDocumentLUTUConfigurationManager _lutuConfigurationManager;
	private I_M_HU_LUTU_Configuration _lutuConfiguration;
	private ILUTUProducerAllocationDestination _lutuProducer;


	private CustomerReturnLineHUGenerator()
	{
	}

	private final void assertConfigurable()
	{
		Check.assume(_configurable, "{} is still configurable", this);
	}

	private final void assertNotConfigurable()
	{
		Check.assume(!_configurable, "{} is not configurable anymore", this);
	}

	private final void markNotConfigurable()
	{
		_configurable = false;
	}

	private final IContextAware getContextInitial()
	{
		Check.assumeNotNull(_contextInitial, "_contextInitial not null");
		return _contextInitial;
	}

	private final CustomerReturnLineHUGenerator setContext(final IContextAware context)
	{
		// needs to be threadInherited because we run in our own little TrxRunnable and everything created from the request shall be committed when we commit that runnable's local transaction.
		Check.errorUnless(ITrx.TRXNAME_ThreadInherited.equals(context.getTrxName()),
				"The trxName of the given request's HUContext needs to be {} or 'null', but is {}",
				ITrx.TRXNAME_ThreadInherited, context.getTrxName());

		_contextInitial = context;
		return this;
	}

	/**
	 * Sets if, after generating the HUs, we shall also update receipt schedule's LUTU configuration.
	 * 
	 * IMPORTANT: this flag applies for {@link #generate()} but does not apply for {@link #generateAllPlanningHUs_InChunks()}.
	 * 
	 * @param updateReceiptScheduleDefaultConfiguration
	 */
	public CustomerReturnLineHUGenerator setUpdateReceiptScheduleDefaultConfiguration(final boolean updateReceiptScheduleDefaultConfiguration)
	{
		assertConfigurable();
		this.updateReceiptScheduleDefaultConfiguration = updateReceiptScheduleDefaultConfiguration;
		return this;
	}

	public boolean isUpdateReceiptScheduleDefaultConfiguration()
	{
		return updateReceiptScheduleDefaultConfiguration;
	}

	private final Quantity getQtyToAllocateTarget()
	{
		if (_qtyToAllocateTarget == null || _qtyToAllocateTarget.signum() <= 0)
		{
			throw new AdempiereException("Quantity to receive shall be greather than zero");
		}
		return _qtyToAllocateTarget;
	}

	public CustomerReturnLineHUGenerator setQtyToAllocateTarget(final Quantity qtyToAllocateTarget)
	{
		_qtyToAllocateTarget = qtyToAllocateTarget;
		return this;
	}

	private final I_M_InOutLine getSingleInOutLine()
	{
		final List<I_M_InOutLine> inOutLines = getInOutLines();
		Check.assumeNotEmpty(inOutLines, "_receiptSchedules not empty");
		Check.assume(inOutLines.size() == 1, "Only one inOutLine but there were {}", inOutLines);
		return inOutLines.get(0);
	}

	private final I_M_InOutLine getSingleInOutLineOrNull()
	{
		final List<I_M_InOutLine> inOutLines = getInOutLines();
		if (inOutLines.size() == 1)
		{
			return inOutLines.get(0);
		}
		else
		{
			return null;
		}
	}

	private final List<I_M_InOutLine> getInOutLines()
	{
		return _inOutLines;
	}

	public CustomerReturnLineHUGenerator addM_InOutLine(final I_M_InOutLine inOutLine)
	{
		assertConfigurable();
		Check.assumeNotNull(inOutLine, "inOutLine not null");

		Check.assume(!inOutLine.isPackagingMaterial(), "inOutLine shall not be about packing materials: {}", inOutLine);

		if (_inOutLines.contains(inOutLine))
		{
			return this;
		}

		_inOutLines.add(inOutLine);

		return this;
	}

	public CustomerReturnLineHUGenerator addM_InOutLines(final Collection<? extends I_M_InOutLine> inOutLines)
	{
		Check.assumeNotEmpty(inOutLines, "receiptSchedules not empty");
		for (final I_M_InOutLine inOutLine : inOutLines)
		{
			addM_InOutLine(inOutLine);
		}

		return this;
	}

	/**
	 * This method is important in getting precomputed HUs
	 * 
	 * @param schedule
	 * @return
	 */
	private IHUAllocations getHUAllocations(final I_M_InOutLine inOutLine)
	{
		final int inOutLineId = inOutLine.getM_InOutLine_ID();
		IHUAllocations huAllocations = _inOutLine2huAllocations.get(inOutLineId);
		if (huAllocations == null)
		{
			final IProductStorage productStorage = getProductStorage(inOutLine);
			huAllocations = new CustomerReturnLineHUAllocations(inOutLine, productStorage);
			_inOutLine2huAllocations.put(inOutLineId, huAllocations);
		}
		return huAllocations;
	}

	private IProductStorage getProductStorage(final I_M_InOutLine inOutLine)
	{
		final int inOutLineId = inOutLine.getM_InOutLine_ID();
		IProductStorage productStorage = _inOutLine2productStorage.get(inOutLineId);
		if (productStorage == null)
		{
			final I_M_Product product = inOutLine.getM_Product();
			final I_C_UOM uom = inOutLine.getC_UOM();
			final BigDecimal qty = inOutLine.getQtyEntered();

			productStorage = new PlainProductStorage(product, uom, qty);

			Check.assumeNotNull(productStorage, "productStorage not null");
			_inOutLine2productStorage.put(inOutLineId, productStorage);
		}
		return productStorage;
	}

	public CustomerReturnLineHUGenerator setProductStorage(final I_M_InOutLine inOutLine, final IProductStorage productStorage)
	{
		assertConfigurable();
		final int inOutLineId = inOutLine.getM_InOutLine_ID();
		_inOutLine2productStorage.put(inOutLineId, productStorage);
		return this;
	}

	private I_M_Product getM_Product()
	{
		final Map<Integer, I_M_Product> products = new HashMap<>();
		for (final I_M_InOutLine inOutLine : getInOutLines())
		{
			final IProductStorage productStorage = getProductStorage(inOutLine);
			final I_M_Product product = productStorage.getM_Product();
			products.put(product.getM_Product_ID(), product);
		}

		if (products.isEmpty())
		{
			throw new HUException("No products were found");
		}
		else if (products.size() == 1)
		{
			final I_M_Product product = products.values().iterator().next();
			return product;
		}
		else
		{
			// shall not happen
			throw new HUException("More then one products were found: " + products.values());
		}
	}

	/**
	 * Create the HUs (if necessary, also see the remarks on the class-javadoc). This will take place in a dedicated {@link TrxRunnable} which will be committed (or rolled back) within this method.
	 * 
	 * @return
	 */
	public List<I_M_HU> generate()
	{
		final Quantity qtyCUsTotal = getQtyToAllocateTarget();
		Check.assume(!qtyCUsTotal.isInfinite(), "QtyToAllocate(target) shall not be infinite");

		final IAllocationRequest request = createAllocationRequest(qtyCUsTotal);
		final List<I_M_HU> hus = generateLUTUHandlingUnitsForQtyToAllocate(request);

		//
		// Update receipt schedule's LU/TU configuration
		if (isUpdateReceiptScheduleDefaultConfiguration())
		{
			trxManager.run(() -> {
				final I_M_HU_LUTU_Configuration lutuConfiguration = getM_HU_LUTU_Configuration();
				getLUTUConfigurationManager().setCurrentLUTUConfigurationAndSave(lutuConfiguration);
			});
		}

		return hus;
	}

	/**
	 * Runs within a {@link TrxRunnable} with its own local transaction and commits on success.
	 * 
	 * @param request
	 * @return
	 */
	private List<I_M_HU> generateLUTUHandlingUnitsForQtyToAllocate(final IAllocationRequest request)
	{
		// needs to be threadInherited because we run in our own little TrxRunnable and everything created from the request shall be committed when we commit that runnable's local transaction.
		Check.errorUnless(ITrx.TRXNAME_ThreadInherited.equals(request.getHUContext().getTrxName()),
				"The trxName of the given request's HUContext needs to be {} or 'null', but is {}",
				ITrx.TRXNAME_ThreadInherited, request.getHUContext().getTrxName());

		final List<I_M_HU> result = new ArrayList<I_M_HU>();

		final String trxNamePrefix = getClass().getSimpleName();

		final ITrxRunConfig trxRunConfig = trxManager.newTrxRunConfigBuilder()
				.setTrxPropagation(TrxPropagation.REQUIRES_NEW)
				.setOnRunnableSuccess(OnRunnableSuccess.COMMIT)
				.build();

		trxManager.run(trxNamePrefix, trxRunConfig, new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName)
			{
				final List<I_M_HU> handlingUnits = generateLUTUHandlingUnitsForQtyToAllocate0(request);
				result.addAll(handlingUnits);
			}
		});

		// Make sure we are returning them out-of-transaction
		result.forEach(hu -> InterfaceWrapperHelper.setTrxName(hu, ITrx.TRXNAME_None));

		return result;
	}

	/**
	 * Create Handling Units for not allocated qty of line.
	 *
	 * @param ctx
	 * @param maxLUsToCreate
	 * @param trxName
	 * @return generated HUs
	 */
	private List<I_M_HU> generateLUTUHandlingUnitsForQtyToAllocate0(final IAllocationRequest request)
	{
		Check.assumeNotNull(request, "request not null");

		markNotConfigurable();

		//
		// Source: our line
		final IAllocationSource source = createAllocationSource();

		//
		// Destination: our configured LU/TU Producer
		final ILUTUProducerAllocationDestination destination = getLUTUProducerAllocationDestination();

		//
		// Execute transfer
		final HULoader loader = HULoader.of(source, destination);

		// Allow Partial Unloads (from source): no, we need to over allocated if required
		loader.setAllowPartialUnloads(false);
		// Allow Partial Loads (to destination): yes, because it could happen that we already have some pre-generated HUs (see "beforeLUTUProducerExecution" method)
		loader.setAllowPartialLoads(true); // to Destination

		final IAllocationResult result = loader.load(request);

		//
		// Make sure everything was transferred
		final boolean allowPartialResults = loader.isAllowPartialLoads() || loader.isAllowPartialUnloads();
		if (!allowPartialResults && !result.isCompleted())
		{
			final String errmsg = "Cannot create HU for " + this;
			throw new AdempiereException(errmsg);
		}

		//
		// Get the newly created HUs
		final List<I_M_HU> handlingUnits = destination.getCreatedHUs();

		return handlingUnits;
	}

	private IAllocationSource createAllocationSource()
	{
		final List<I_M_InOutLine> inOutLines = getInOutLines();
		Check.assumeNotEmpty(inOutLines, "_receiptSchedules not empty");

		final GenericListAllocationSourceDestination allocationSources = new GenericListAllocationSourceDestination();
		for (final I_M_InOutLine inOutLine : inOutLines)
		{
			final IProductStorage productStorage = getProductStorage(inOutLine);
			final IAllocationSource allocationSource = new GenericAllocationSourceDestination(productStorage, inOutLine);
			allocationSources.addAllocationSource(allocationSource);
		}

		return allocationSources;
	}

	private final IAllocationRequest createAllocationRequest(final BigDecimal qty, final I_C_UOM uom)
	{
		return createAllocationRequest(new Quantity(qty, uom));
	}

	/**
	 * Keep in sync with {@link de.metas.handlingunits.client.terminal.receipt.model.ReceiptScheduleCUKey.createVHU()}
	 * 
	 * @param qty
	 * @return
	 */
	private final IAllocationRequest createAllocationRequest(final Quantity qty)
	{
		final IContextAware contextProvider = getContextInitial();
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContextForProcessing(contextProvider);

		final I_M_InOutLine inOutLine = getSingleInOutLineOrNull();

		// ForceQtyAllocation=true, because
		// * we really want to allocate unload this quantity
		// * on loading side, HUItemStorage is considering our enforced capacity even if we do force allocation
		final boolean forceQtyAllocation = true;

		//
		// Create Allocation Request
		IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				getM_Product(),
				qty,
				SystemTime.asDate(),
				inOutLine, // referencedModel,
				forceQtyAllocation);

		//TODO: See about the initial attributes!!!
		//
		// Setup initial attribute value defaults
		//final List<I_M_ReceiptSchedule> receiptSchedules = getReceiptSchedules();
		//request = huReceiptScheduleBL.setInitialAttributeValueDefaults(request, receiptSchedules);

		return request;
	}

	/**
	 *
	 * @return receipt schedule's total qty to allocate
	 */
	private BigDecimal getQtyToAllocateTotal(final I_M_InOutLine inOutLine)
	{
		return getProductStorage(inOutLine).getQty();
	}

	public IDocumentLUTUConfigurationManager getLUTUConfigurationManager()
	{
		if (_lutuConfigurationManager == null)
		{
			final List<I_M_InOutLine> inOutLines = getInOutLines();
			_lutuConfigurationManager = huInOutBL.createLUTUConfigurationManager(inOutLines);
			markNotConfigurable();
		}
		return _lutuConfigurationManager;
	}

	/**
	 * Creates/Updates {@link I_M_InOutLine#COLUMNNAME_M_HU_LUTU_Configuration_ID}.
	 *
	 * Please note, this method is not updating the receipt schedule but only the {@link I_M_HU_LUTU_Configuration}.
	 * 
	 * IMPORTANT: this is used only by {@link #generateAllPlanningHUs_InChunks()}.
	 *
	 * Also, it:
	 * <ul>
	 * <li>Makes sure LU/TU configuration is adjusted to CUs/TUs to order quantity to not produce more HUs then needed. (07378)
	 * </ul>
	 */
	private void createInitialLUTUConfiguration(final I_M_InOutLine inOutLine)
	{
		Check.assumeNull(_lutuConfiguration, "_lutuConfiguration shall be null");
		Check.assumeNotNull(inOutLine, "inOutLine not null");

		// final I_M_ReceiptSchedule schedule = getM_ReceiptSchedule();

		//
		// Create LU/TU Configuration to use
		final IDocumentLUTUConfigurationManager lutuConfigurationManager = huInOutBL.createLUTUConfigurationManager(inOutLine);
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationManager.getCreateLUTUConfiguration();

		//
		// Make sure LU/TU configuration is adjusted to CUs/TUs to order quantity
		// to not produce more HUs then needed. (07378)
	
		final BigDecimal qtyToOrderCUs = inOutLine.getQtyEntered();
		final BigDecimal qtyToOrderTUs = inOutLine.getQtyEnteredTU();
		lutuConfigurationFactory.adjustForTotalQtyTUsAndCUs(lutuConfiguration, qtyToOrderTUs, qtyToOrderCUs);
		lutuConfigurationFactory.save(lutuConfiguration);
		_lutuConfiguration = lutuConfiguration;
	}

	/**
	 * Sets the LU/TU configuration to be used when generating HUs.
	 */
	public CustomerReturnLineHUGenerator setM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		assertConfigurable();

		Check.assumeNotNull(lutuConfiguration, "Parameter lutuConfiguration is not null");
		_lutuConfiguration = lutuConfiguration;
		return this;
	}

	/**
	 * @return the LU/TU configuration to be used when generating HUs.
	 */
	public I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration()
	{
		if (_lutuConfiguration != null)
		{
			return _lutuConfiguration;
		}

		_lutuConfiguration = getLUTUConfigurationManager().getCurrentLUTUConfigurationOrNull();
		Check.assumeNotNull(_lutuConfiguration, "_lutuConfiguration not null");

		markNotConfigurable();
		return _lutuConfiguration;
	}

	public ILUTUProducerAllocationDestination getLUTUProducerAllocationDestination()
	{
		if (_lutuProducer != null)
		{
			return _lutuProducer;
		}

		final I_M_HU_LUTU_Configuration lutuConfiguration = getM_HU_LUTU_Configuration();
		_lutuProducer = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);

		//
		// Ask the "lutuProducer" to consider currently created HUs that are linked to our receipt schedule
		// In case they are suitable, they will be used, else they will be destroyed.
		// NOTE: we do this only if we have only one M_ReceiptSchedule
		final I_M_InOutLine inOutLine = getSingleInOutLineOrNull();
		if (inOutLine != null)
		{
			final IHUAllocations huAllocations = getHUAllocations(inOutLine);
			_lutuProducer.setExistingHUs(huAllocations);
		}

		markNotConfigurable();
		return _lutuProducer;
	}

	/**
	 * Adjust LU/TU Configuration with actual <code>lutuProducer</code> values.
	 * 
	 * IMPORTANT: this is called only from {@link #generateAllPlanningHUs_InChunks()} so it's ONLY about when we are pregerating the HUs.
	 *
	 * NOTE:
	 * <ul>
	 * <li>this method is also saving the configuration if there were any changes.
	 * <li>this method is setting {@link I_M_InOutLine#setM_HU_LUTU_Configuration(I_M_HU_LUTU_Configuration)}
	 * </ul>
	 */
	private void updateLUTUConfigurationFromActualValues()
	{
		assertNotConfigurable();

		final ILUTUProducerAllocationDestination lutuProducer = getLUTUProducerAllocationDestination();

		//
		// Get the actual QtyTU which was used
		final int qtyTU;
		if (lutuProducer.getCreatedLUsCount() > 0)
		{
			// Case: we generated some LUs => QtyTU shall be the maximum number of TUs which were generated on an LU
			qtyTU = lutuProducer.getMaxTUsPerLU_ActuallyCreated();
		}
		else if (lutuProducer.isNoLU() && lutuProducer.getCreatedTUsForRemainingQtyCount() > 0)
		{
			// Case: we haven't generated any LU but we generated some TUs for remaining Qty => QtyTU shall be the number of TUs which were generated
			qtyTU = lutuProducer.getCreatedTUsForRemainingQtyCount();
		}
		else
		{
			// In any other case, we are not updating the QtyTU
			qtyTU = 0; // do nothing
		}

		boolean changed = false; // true if LU/TU configuration was changed and needs to be saved

		final I_M_HU_LUTU_Configuration lutuConfiguration = getM_HU_LUTU_Configuration();

		//
		// Update configuration's QtyTU, if it's greater then ZERO
		if (qtyTU > 0)
		{
			lutuConfiguration.setIsInfiniteQtyTU(false);
			lutuConfiguration.setQtyTU(BigDecimal.valueOf(qtyTU));
			changed = true;
		}

		//
		// If configuration was changed, save it
		if (changed)
		{
			// NOTE: we just need to update the LU/TU configuration with the actual generation values
			// so it's not like making a version of LU/TU configuration which is not compatible with actually generated HUs
			final boolean disableChangeCheckingOnSave = true;
			lutuConfigurationFactory.save(lutuConfiguration, disableChangeCheckingOnSave);
		}

		//
		// Link used configuration to this receipt schedule (for later use)
		getLUTUConfigurationManager().setCurrentLUTUConfigurationAndSave(lutuConfiguration);

		//
		// Flag the receipt schedule as HU prepared
		final I_M_InOutLine inOutLine = getSingleInOutLine();
		inOutLine.setIsHUPrepared(true);
		InterfaceWrapperHelper.save(inOutLine, ITrx.TRXNAME_ThreadInherited);
	}

	public void generateAllPlanningHUs_InChunks()
	{
		// NOTE: in this case we assume we have only one receipt schedule
		final I_M_InOutLine inOutLine = getSingleInOutLine();

		//
		// Don't generate planning HUs if we don't have open Quantity
		if (getQtyToAllocateTotal(inOutLine).signum() <= 0)
		{
			return;
		}

		//
		// Prepare for chunk processing
		// change the trxName because in the trxRunner of generateLUTUHandlingUnitsForQtyToAllocate() we will create plenty of records using this schedule's trxName.
		// those new records shall be committed as soon as the trxRunner finishes.
		InterfaceWrapperHelper.setTrxName(inOutLine, ITrx.TRXNAME_ThreadInherited);

		InterfaceWrapperHelper.setSaveDeleteDisabled(inOutLine, true); // make sure we are not changing 'schedule'

		//
		// Make sure LU/TU configuration is adjusted to CUs/TUs to order quantity
		// to not produce more HUs than needed.
		createInitialLUTUConfiguration(inOutLine);

		final BigDecimal qtyToOrderCUs = inOutLine.getQtyEntered(); // whole receipt schedule ordered Qty
		//Check.assume(inOutLine.getQtyMoved().signum() == 0, "No quantity was moved on {}", schedule);
		final I_C_UOM qtyToOrderCUsUOM = inOutLine.getC_UOM();
		final BigDecimal qtyToRequestCUsPerChunk = calculateQtyToRequestPerChunk(qtyToOrderCUs, qtyToOrderCUsUOM);

		//
		// Generate LU/TUs in chunks
		BigDecimal qtyToOrderCUsRemaining = qtyToOrderCUs;
		while (qtyToOrderCUsRemaining.signum() > 0)
		{
			final BigDecimal qtyToRequest = qtyToOrderCUsRemaining.min(qtyToRequestCUsPerChunk);
			final IAllocationRequest request = createAllocationRequest(qtyToRequest, qtyToOrderCUsUOM);
			generateLUTUHandlingUnitsForQtyToAllocate(request);

			qtyToOrderCUsRemaining = qtyToOrderCUsRemaining.subtract(qtyToRequest);
		}

		//
		// Adjust the LU/TU configuration if needed and push it back to receipt schedule (for later use)
		trxManager.run(() -> {
			InterfaceWrapperHelper.setSaveDeleteDisabled(inOutLine, false);
			updateLUTUConfigurationFromActualValues();
		});
	}

	private final BigDecimal calculateQtyToRequestPerChunk(final BigDecimal qtyToOrderCUs, final I_C_UOM qtyToOrderCUsUOM)
	{
		if (qtyToOrderCUs == null || qtyToOrderCUs.signum() <= 0)
		{
			return BigDecimal.ZERO;
		}

		final I_M_HU_LUTU_Configuration lutuConfiguration = getM_HU_LUTU_Configuration();

		if (lutuConfiguration.isInfiniteQtyCU())
		{
			return qtyToOrderCUs;
		}

		final BigDecimal qtyCUsPerTU = lutuConfiguration.getQtyCU();
		if (qtyCUsPerTU.signum() <= 0)
		{
			return qtyToOrderCUs;
		}

		if (lutuConfiguration.isInfiniteQtyTU())
		{
			return qtyToOrderCUs;
		}

		final BigDecimal qtyTUsPerLU = lutuConfiguration.getQtyTU();
		if (qtyTUsPerLU.signum() <= 0)
		{
			return qtyToOrderCUs;
		}

		final BigDecimal qtyCUsPerLU = qtyCUsPerTU.multiply(qtyTUsPerLU);
		return qtyCUsPerLU;
	}
}
