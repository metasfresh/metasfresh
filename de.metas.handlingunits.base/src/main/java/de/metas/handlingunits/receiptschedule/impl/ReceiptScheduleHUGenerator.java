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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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
import org.adempiere.uom.api.Quantity;
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
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;

/**
 * Helper class for massive generation of HUs for receipt schedule(s).
 * <p>
 * Notes:
 * <li>The respective {@link I_M_ReceiptSchedule_Alloc}s and {@link I_M_HU_Assignment}s are created via {@link ReceiptScheduleHUTrxListener}.
 * <li>This class can also be configured to go with pre existing HUs (if they are still valid) instead of creating new ones,
 * see {@link ILUTUProducerAllocationDestination#setExistingHUs(IHUAllocations)} which is called from this class.
 *
 * @author tsa
 *
 */
public class ReceiptScheduleHUGenerator
{
	/**
	 * 
	 * @param context: the context to be used when creating the HUs. This context will also be used for the {@link IHUContext} the HU processing will have place with. <br>
	 *            If its {@code trxName} is not {@link ITrx#TRXNAME_ThreadInherited}, then this method will create a new context that only has the given context's {@link IContextAware#getCtx()} but not trxName.<br>
	 *            Because the {@link #generate()} method depends on the {@code trxName} being thread-inherited.
	 */
	public static final ReceiptScheduleHUGenerator newInstance(final IContextAware context)
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
		return new ReceiptScheduleHUGenerator()
				.setContext(contextToUse);
	}

	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	//
	// Parameters
	private IContextAware _contextInitial;
	private final List<I_M_ReceiptSchedule> _receiptSchedules = new ArrayList<>();
	private final Map<Integer, IProductStorage> _receiptSchedule2productStorage = new HashMap<>();
	private final Map<Integer, IHUAllocations> _receiptSchedule2huAllocations = new HashMap<>();
	private Quantity _qtyToAllocateTarget = null;
	//
	private boolean updateReceiptScheduleDefaultConfiguration = false; // default false, backward compatible; this flag is not considered by #generateAllPlanningHUs_InChunks()

	//
	// Status
	private boolean _configurable = true;
	private IDocumentLUTUConfigurationManager _lutuConfigurationManager;
	private I_M_HU_LUTU_Configuration _lutuConfiguration;
	private ILUTUProducerAllocationDestination _lutuProducer;

	private ReceiptScheduleHUGenerator()
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

	private final ReceiptScheduleHUGenerator setContext(final IContextAware context)
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
	public ReceiptScheduleHUGenerator setUpdateReceiptScheduleDefaultConfiguration(final boolean updateReceiptScheduleDefaultConfiguration)
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
		if(_qtyToAllocateTarget == null || _qtyToAllocateTarget.signum() <= 0)
		{
			throw new AdempiereException("Quantity to receive shall be greather than zero");
		}
		return _qtyToAllocateTarget;
	}

	public ReceiptScheduleHUGenerator setQtyToAllocateTarget(final Quantity qtyToAllocateTarget)
	{
		_qtyToAllocateTarget = qtyToAllocateTarget;
		return this;
	}

	private final I_M_ReceiptSchedule getSingleReceiptSchedule()
	{
		final List<I_M_ReceiptSchedule> receiptSchedules = getReceiptSchedules();
		Check.assumeNotEmpty(receiptSchedules, "_receiptSchedules not empty");
		Check.assume(receiptSchedules.size() == 1, "Only one receipt schedule but there were {}", receiptSchedules);
		return receiptSchedules.get(0);
	}

	private final I_M_ReceiptSchedule getSingleReceiptScheduleOrNull()
	{
		final List<I_M_ReceiptSchedule> receiptSchedules = getReceiptSchedules();
		if (receiptSchedules.size() == 1)
		{
			return receiptSchedules.get(0);
		}
		else
		{
			return null;
		}
	}

	private final List<I_M_ReceiptSchedule> getReceiptSchedules()
	{
		return _receiptSchedules;
	}

	public ReceiptScheduleHUGenerator addM_ReceiptSchedule(final I_M_ReceiptSchedule receiptSchedule)
	{
		assertConfigurable();
		Check.assumeNotNull(receiptSchedule, "receiptSchedule not null");
		Check.assume(!Services.get(IReceiptScheduleBL.class).isClosed(receiptSchedule), "receipt schedule shall not be closed: {}", receiptSchedule);
		Check.assume(!receiptSchedule.isPackagingMaterial(), "receipt schedule shall not be about packing materials: {}", receiptSchedule);

		if (_receiptSchedules.contains(receiptSchedule))
		{
			return this;
		}

		_receiptSchedules.add(receiptSchedule);

		return this;
	}

	public ReceiptScheduleHUGenerator addM_ReceiptSchedules(final Collection<? extends I_M_ReceiptSchedule> receiptSchedules)
	{
		Check.assumeNotEmpty(receiptSchedules, "receiptSchedules not empty");
		for (final I_M_ReceiptSchedule receiptSchedule : receiptSchedules)
		{
			addM_ReceiptSchedule(receiptSchedule);
		}

		return this;
	}

	private I_C_OrderLine getC_OrderLine(final I_M_ReceiptSchedule schedule)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(schedule.getC_OrderLine(), I_C_OrderLine.class);
		Check.assumeNotNull(orderLine, "orderLine not null");
		return orderLine;
	}

	/**
	 * This method is important in getting precomputed HUs
	 * 
	 * @param schedule
	 * @return
	 */
	private IHUAllocations getHUAllocations(final I_M_ReceiptSchedule schedule)
	{
		final int receiptScheduleId = schedule.getM_ReceiptSchedule_ID();
		IHUAllocations huAllocations = _receiptSchedule2huAllocations.get(receiptScheduleId);
		if (huAllocations == null)
		{
			final IProductStorage productStorage = getProductStorage(schedule);
			huAllocations = new ReceiptScheduleHUAllocations(schedule, productStorage);
			_receiptSchedule2huAllocations.put(receiptScheduleId, huAllocations);
		}
		return huAllocations;
	}

	private IProductStorage getProductStorage(final I_M_ReceiptSchedule schedule)
	{
		final int receiptScheduleId = schedule.getM_ReceiptSchedule_ID();
		IProductStorage productStorage = _receiptSchedule2productStorage.get(receiptScheduleId);
		if (productStorage == null)
		{
			productStorage = huReceiptScheduleBL.createProductStorage(schedule);
			Check.assumeNotNull(productStorage, "productStorage not null");
			_receiptSchedule2productStorage.put(receiptScheduleId, productStorage);
		}
		return productStorage;
	}

	public ReceiptScheduleHUGenerator setProductStorage(final I_M_ReceiptSchedule schedule, final IProductStorage productStorage)
	{
		assertConfigurable();
		final int receiptScheduleId = schedule.getM_ReceiptSchedule_ID();
		_receiptSchedule2productStorage.put(receiptScheduleId, productStorage);
		return this;
	}

	private I_M_Product getM_Product()
	{
		final Map<Integer, I_M_Product> products = new HashMap<>();
		for (final I_M_ReceiptSchedule schedule : getReceiptSchedules())
		{
			final IProductStorage productStorage = getProductStorage(schedule);
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
		if(isUpdateReceiptScheduleDefaultConfiguration())
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
		final HULoader loader = new HULoader(source, destination);

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
		final List<I_M_ReceiptSchedule> receiptSchedules = getReceiptSchedules();
		Check.assumeNotEmpty(receiptSchedules, "_receiptSchedules not empty");

		final GenericListAllocationSourceDestination allocationSources = new GenericListAllocationSourceDestination();
		for (final I_M_ReceiptSchedule schedule : receiptSchedules)
		{
			final IProductStorage productStorage = getProductStorage(schedule);
			final IAllocationSource allocationSource = new GenericAllocationSourceDestination(productStorage, schedule);
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

		final I_M_ReceiptSchedule receiptSchedule = getSingleReceiptScheduleOrNull();

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
				receiptSchedule, // referencedModel,
				forceQtyAllocation);

		//
		// Setup initial attribute value defaults
		final List<I_M_ReceiptSchedule> receiptSchedules = getReceiptSchedules();
		request = huReceiptScheduleBL.setInitialAttributeValueDefaults(request, receiptSchedules);

		return request;
	}

	/**
	 *
	 * @return receipt schedule's total qty to allocate
	 */
	private BigDecimal getQtyToAllocateTotal(final I_M_ReceiptSchedule schedule)
	{
		return getProductStorage(schedule).getQty();
	}

	public IDocumentLUTUConfigurationManager getLUTUConfigurationManager()
	{
		if (_lutuConfigurationManager == null)
		{
			final List<I_M_ReceiptSchedule> receiptSchedules = getReceiptSchedules();
			_lutuConfigurationManager = huReceiptScheduleBL.createLUTUConfigurationManager(receiptSchedules);
			markNotConfigurable();
		}
		return _lutuConfigurationManager;
	}

	/**
	 * Creates/Updates {@link I_M_ReceiptSchedule#COLUMNNAME_M_HU_LUTU_Configuration_ID}.
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
	private void createInitialLUTUConfiguration(final I_M_ReceiptSchedule schedule)
	{
		Check.assumeNull(_lutuConfiguration, "_lutuConfiguration shall be null");
		Check.assumeNotNull(schedule, "schedule not null");

		// final I_M_ReceiptSchedule schedule = getM_ReceiptSchedule();

		//
		// Create LU/TU Configuration to use
		final IDocumentLUTUConfigurationManager lutuConfigurationManager = huReceiptScheduleBL.createLUTUConfigurationManager(schedule);
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationManager.getCreateLUTUConfiguration();

		//
		// Make sure LU/TU configuration is adjusted to CUs/TUs to order quantity
		// to not produce more HUs then needed. (07378)
		final I_C_OrderLine orderLine = getC_OrderLine(schedule);
		final BigDecimal qtyToOrderCUs = orderLine.getQtyOrdered();
		final BigDecimal qtyToOrderTUs = orderLine.getQtyEnteredTU();
		lutuConfigurationFactory.adjustForTotalQtyTUsAndCUs(lutuConfiguration, qtyToOrderTUs, qtyToOrderCUs);
		lutuConfigurationFactory.save(lutuConfiguration);
		_lutuConfiguration = lutuConfiguration;
	}
	
	/**
	 * Sets the LU/TU configuration to be used when generating HUs.
	 */
	public ReceiptScheduleHUGenerator setM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration lutuConfiguration)
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
		final I_M_ReceiptSchedule receiptSchedule = getSingleReceiptScheduleOrNull();
		if (receiptSchedule != null)
		{
			final IHUAllocations huAllocations = getHUAllocations(receiptSchedule);
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
	 * <li>this method is setting {@link I_M_ReceiptSchedule#setM_HU_LUTU_Configuration(I_M_HU_LUTU_Configuration)}
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
		final I_M_ReceiptSchedule receiptSchedule = getSingleReceiptSchedule();
		receiptSchedule.setIsHUPrepared(true);
		InterfaceWrapperHelper.save(receiptSchedule, ITrx.TRXNAME_ThreadInherited);
	}

	public void generateAllPlanningHUs_InChunks()
	{
		// NOTE: in this case we assume we have only one receipt schedule
		final I_M_ReceiptSchedule schedule = getSingleReceiptSchedule();

		//
		// Don't generate planning HUs if we don't have open Quantity
		if (getQtyToAllocateTotal(schedule).signum() <= 0)
		{
			return;
		}

		//
		// Prepare for chunk processing
		// change the trxName because in the trxRunner of generateLUTUHandlingUnitsForQtyToAllocate() we will create plenty of records using this schedule's trxName.
		// those new records shall be committed as soon as the trxRunner finishes.
		InterfaceWrapperHelper.setTrxName(schedule, ITrx.TRXNAME_ThreadInherited);

		InterfaceWrapperHelper.setSaveDeleteDisabled(schedule, true); // make sure we are not changing 'schedule'

		//
		// Make sure LU/TU configuration is adjusted to CUs/TUs to order quantity
		// to not produce more HUs than needed.
		createInitialLUTUConfiguration(schedule);

		final BigDecimal qtyToOrderCUs = schedule.getQtyOrdered(); // whole receipt schedule ordered Qty
		Check.assume(schedule.getQtyMoved().signum() == 0, "No quantity was moved on {}", schedule);
		final I_C_UOM qtyToOrderCUsUOM = schedule.getC_UOM();
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
			InterfaceWrapperHelper.setSaveDeleteDisabled(schedule, false);
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
