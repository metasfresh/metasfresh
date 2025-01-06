/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.inout.returns.customer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
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
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnableAdapter;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generates HUs for given customer return line(s).
 */
class CustomerReturnLineHUGenerator
{

	/**
	 * @param context: the context to be used when creating the HUs. This context will also be used for the {@link IHUContext} the HU processing will have place with. <br>
	 *                 If its {@code trxName} is not {@link ITrx#TRXNAME_ThreadInherited}, then this method will create a new context that only has the given context's {@link IContextAware#getCtx()} but not trxName.<br>
	 *                 Because the {@link #generate()} method depends on the {@code trxName} being thread-inherited.
	 */
	public static CustomerReturnLineHUGenerator newInstance(final IContextAware context)
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
	private Quantity _qtyToAllocateTarget = null;
	private ImmutableList<IHUTrxListener> _customerListeners = ImmutableList.of(); //default value, to be set by de.metas.handlingunits.inout.returns.customer.CustomerReturnLineHUGenerator.setIHUTrxListeners

	//
	// Status
	private boolean _configurable = true;
	private IDocumentLUTUConfigurationManager _lutuConfigurationManager;
	private I_M_HU_LUTU_Configuration _lutuConfiguration;
	private ILUTUProducerAllocationDestination _lutuProducer;

	private CustomerReturnLineHUGenerator()
	{
	}

	private void assertConfigurable()
	{
		Check.assume(_configurable, "{} is still configurable", this);
	}

	private void markNotConfigurable()
	{
		_configurable = false;
	}

	private IContextAware getContextInitial()
	{
		Check.assumeNotNull(_contextInitial, "_contextInitial not null");
		return _contextInitial;
	}

	private CustomerReturnLineHUGenerator setContext(final IContextAware context)
	{
		// needs to be threadInherited because we run in our own little TrxRunnable and everything created from the request shall be committed when we commit that runnable's local transaction.
		Check.errorUnless(ITrx.TRXNAME_ThreadInherited.equals(context.getTrxName()),
				"The trxName of the given request's HUContext needs to be {} or 'null', but is {}",
				ITrx.TRXNAME_ThreadInherited, context.getTrxName());

		_contextInitial = context;
		return this;
	}

	private Quantity getQtyToAllocateTarget()
	{
		if (_qtyToAllocateTarget == null || _qtyToAllocateTarget.signum() <= 0)
		{
			throw new AdempiereException("Quantity to receive shall be greather than zero");
		}
		return _qtyToAllocateTarget;
	}

	private void setQtyToAllocateTarget(final Quantity qtyToAllocateTarget)
	{
		_qtyToAllocateTarget = qtyToAllocateTarget;
	}

	@Nullable
	private I_M_InOutLine getSingleInOutLineOrNull()
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

	private List<I_M_InOutLine> getInOutLines()
	{
		return _inOutLines;
	}

	public CustomerReturnLineHUGenerator addM_InOutLine(@NonNull final I_M_InOutLine inOutLine)
	{
		assertConfigurable();

		Check.assume(!inOutLine.isPackagingMaterial(), "inOutLine shall not be about packing materials: {}", inOutLine);

		if (_inOutLines.contains(inOutLine))
		{
			return this;
		}

		_inOutLines.add(inOutLine);

		//
		// Get/Create and Edit LU/TU configuration
		final IDocumentLUTUConfigurationManager lutuConfigurationManager = getLUTUConfigurationManager();
		final I_M_HU_LUTU_Configuration lutuConfigurationEffective = lutuConfigurationManager.getCreateLUTUConfiguration();

		InterfaceWrapperHelper.save(lutuConfigurationEffective, ITrx.TRXNAME_None);
		inOutLine.setM_HU_LUTU_Configuration(lutuConfigurationEffective);

		//
		// Calculate the target CUs that we want to allocate
		final ILUTUProducerAllocationDestination lutuProducer = getLUTUProducerAllocationDestination();
		final Quantity qtyCUsTotal = lutuProducer.calculateTotalQtyCU();// Quantity.of(customerReturnLine.getQtyEntered(), customerReturnLine.getC_UOM());
		if (qtyCUsTotal.isInfinite())
		{
			throw new AdempiereException("LU/TU configuration is resulting to infinite quantity: " + lutuConfigurationEffective);
		}
		setQtyToAllocateTarget(qtyCUsTotal);

		return this;
	}

	private IProductStorage getProductStorage(final I_M_InOutLine inOutLine)
	{
		final int inOutLineId = inOutLine.getM_InOutLine_ID();
		IProductStorage productStorage = _inOutLine2productStorage.get(inOutLineId);
		if (productStorage == null)
		{
			final ProductId productId = ProductId.ofRepoId(inOutLine.getM_Product_ID());
			final I_C_UOM uom = Services.get(IUOMDAO.class).getById(inOutLine.getC_UOM_ID());
			final BigDecimal qty = inOutLine.getQtyEntered();

			productStorage = new PlainProductStorage(productId, uom, qty);
			_inOutLine2productStorage.put(inOutLineId, productStorage);
		}
		return productStorage;
	}

	private ProductId getSingleProductId()
	{
		final Set<ProductId> productIds = getInOutLines()
				.stream()
				.map(this::getProductStorage)
				.map(IProductStorage::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		if (productIds.isEmpty())
		{
			throw new HUException("No products were found");
		}
		else if (productIds.size() == 1)
		{
			return productIds.iterator().next();
		}
		else
		{
			// shall not happen
			throw new HUException("More then one products were found: " + productIds);
		}
	}

	/**
	 * Create the HUs (if necessary, also see the remarks on the class-javadoc). This will take place in a dedicated {@link TrxRunnable} which will be committed (or rolled back) within this method.
	 */
	public List<I_M_HU> generate()
	{
		final Quantity qtyCUsTotal = getQtyToAllocateTarget();
		Check.assume(!qtyCUsTotal.isInfinite(), "QtyToAllocate(target) shall not be infinite");

		final IAllocationRequest request = createAllocationRequest(qtyCUsTotal);
		return generateLUTUHandlingUnitsForQtyToAllocate(request);
	}

	/**
	 * Runs within a {@link TrxRunnable} with its own local transaction and commits on success.
	 */
	private List<I_M_HU> generateLUTUHandlingUnitsForQtyToAllocate(final IAllocationRequest request)
	{
		// needs to be threadInherited because we run in our own little TrxRunnable and everything created from the request shall be committed when we commit that runnable's local transaction.
		Check.errorUnless(ITrx.TRXNAME_ThreadInherited.equals(request.getHuContext().getTrxName()),
				"The trxName of the given request's HUContext needs to be {} or 'null', but is {}",
				ITrx.TRXNAME_ThreadInherited, request.getHuContext().getTrxName());

		final List<I_M_HU> result = new ArrayList<>();

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
		return destination.getCreatedHUs();
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

	private IAllocationRequest createAllocationRequest(final Quantity qty)
	{
		final IContextAware contextProvider = getContextInitial();
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContextForProcessing(contextProvider);

		huContext.getTrxListeners().addListeners(this._customerListeners);

		final I_M_InOutLine inOutLine = getSingleInOutLineOrNull();

		// ForceQtyAllocation=true, because
		// * we really want to allocate unload this quantity
		// * on loading side, HUItemStorage is considering our enforced capacity even if we do force allocation
		final boolean forceQtyAllocation = true;

		//
		// Create Allocation Request

		// TODO: See about the initial attributes!!!
		//
		// Setup initial attribute value defaults
		// final List<I_M_ReceiptSchedule> receiptSchedules = getReceiptSchedules();
		// request = huReceiptScheduleBL.setInitialAttributeValueDefaults(request, receiptSchedules);

		return AllocationUtils.createQtyRequest(
				huContext,
				getSingleProductId(),
				qty,
				SystemTime.asZonedDateTime(),
				inOutLine, // referencedModel,
				forceQtyAllocation);
	}

	private IDocumentLUTUConfigurationManager getLUTUConfigurationManager()
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
	 * @return the LU/TU configuration to be used when generating HUs.
	 */
	private I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration()
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

	private ILUTUProducerAllocationDestination getLUTUProducerAllocationDestination()
	{
		if (_lutuProducer != null)
		{
			return _lutuProducer;
		}

		final I_M_HU_LUTU_Configuration lutuConfiguration = getM_HU_LUTU_Configuration();
		_lutuProducer = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);

		markNotConfigurable();
		return _lutuProducer;
	}

	CustomerReturnLineHUGenerator setIHUTrxListeners(@NonNull final List<IHUTrxListener> customerListeners)
	{
		assertConfigurable();

		this._customerListeners = ImmutableList.copyOf(customerListeners);
		return this;
	}
}
