package de.metas.handlingunits.allocation.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.util.HUByIdComparator;
import de.metas.handlingunits.util.HUListCursor;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.spi.impl.WeightTareAttributeValueCallout;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.util.Util.ArrayKey;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * Contains common BL used when loading from an {@link IAllocationRequest} to an {@link IAllocationResult}
 *
 * @author al
 *
 */
public abstract class AbstractProducerDestination implements IHUProducerAllocationDestination
{
	//
	// Services
	protected final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);

	/** Error message which is thrown when the result of allocating to a new HU is ZERO */
	private static final AdMessageKey MSG_QTY_LOAD_ERROR = AdMessageKey.of("AbstractProducerDestination.load_Error");

	/**
	 * DynAttr used to flag HUs which were internally created by this producer
	 */
	private static final ModelDynAttributeAccessor<I_M_HU, AbstractProducerDestination> DYNATTR_Producer = new ModelDynAttributeAccessor<>(AbstractProducerDestination.class);
	private static final ModelDynAttributeAccessor<I_M_HU, Boolean> DYNATTR_IsEmptyHU = new ModelDynAttributeAccessor<>(AbstractProducerDestination.class.getName(), "IsEmptyHU", Boolean.class);

	//
	// Parameters
	private LocatorId _locatorId = null;
	private String _huStatus = null;
	private ClearanceStatusInfo _huClearanceStatusInfo = null;
	private BPartnerId _bpartnerId = null;
	private int _bpartnerLocationId = -1;
	private I_M_HU_LUTU_Configuration _lutuConfiguration = null;
	private boolean _isHUPlanningReceiptOwnerPM = false; // default false

	/**
	 *
	 * <code>true</code> if this producer is in configurable state (i.e. nothing was produced yet)
	 */
	private boolean _configurable = true;

	private final HashMap<ArrayKey, HUListCursor> currentHUs = new HashMap<>();

	/**
	 * Set of created HUs or already existing HUs that need to be considered as "created".
	 *
	 * NOTE: this set will not accept a HU to be added if there is another one with the same M_HU_ID
	 */
	private final Set<I_M_HU> _createdHUs = new TreeSet<>(HUByIdComparator.instance);

	private final Set<I_M_HU> _createdNonAggregateHUs = new TreeSet<>(HUByIdComparator.instance);

	/**
	 * The number of HUs that were not really created as {@link I_M_HU} instances, but are represented within a HU-aggregation item and its VHU.<br>
	 * This number together with the size of {@link #_createdNonAggregateHUs} is returned by {@link #getCreatedHUsCount()}.
	 */
	private int aggregatedHUsCount;

	public AbstractProducerDestination()
	{
		super();
	}

	/**
	 * @return {@code true} if we are allowed to create a new HU <b>or allocate to the current aggregate/"bag"-HU</b> in case is needed.
	 *         Generally, "needed" means that we still have an {@link IAllocationRequest} that is not yet completely fulfilled.
	 * 
	 */
	public abstract boolean isAllowCreateNewHU();

	/**
	 * Gets HU PI to be used when creating new HUs
	 */
	protected abstract I_M_HU_PI getM_HU_PI();

	/**
	 * Allocate the given <code>request</code> to the given <code>hu</code>.
	 *
	 * @return allocation result
	 */
	protected abstract IAllocationResult loadHU(final I_M_HU hu, final IAllocationRequest request);

	/**
	 * Gets current HU to allocate on.
	 * <ul>
	 * <li>If there is no current HU and we are allowed to create a new one, a new one will be created and returned.
	 * <li>If we are not allowed to create a new one then <code>null</code> will be returned
	 *
	 * @return current HU cursor having the current positioned or <code>null</code> if no more HU are allowed to be created/used
	 */
	private HUListCursor getCreateCurrentHU(final IAllocationRequest request)
	{
		final HUListCursor currentHUCursor = getCurrentHUCursor(request);

		// If we have a current HU and it's not null
		// => cursor is positioned
		if (currentHUCursor.hasCurrent() && currentHUCursor.current() != null)
		{
			return currentHUCursor;
		}

		// If we have a next not-null HU top pick, advance cursor and return the next one
		// => advance the cursor until we found a current not null HU
		while (currentHUCursor.hasNext())
		{
			currentHUCursor.next();
			if (currentHUCursor.current() != null)
			{
				return currentHUCursor;
			}
		}

		//
		// If we reach this point, there is no HUs to pick so we will need to create a new one...
		//

		// Check if we allow to create a new HU
		if (!isAllowCreateNewHU())
		{
			return null; // no HU to allocate on
		}

		//
		// Create a new HU
		final I_M_HU currentHU = createNewHU(request);
		currentHUCursor.append(currentHU);
		currentHUCursor.next(); // position the cursor on newly created HU

		return currentHUCursor;
	}

	private HUListCursor getCurrentHUCursor(final IAllocationRequest request)
	{
		final ArrayKey currentHUKey = extractCurrentHUKey(request);
		return currentHUs.computeIfAbsent(currentHUKey, k -> new HUListCursor());
	}

	protected ArrayKey extractCurrentHUKey(final IAllocationRequest request)
	{
		return ArrayKey.of(request.getProductId());
	}

	private void prepareToLoad(final IHUContext huContext, final I_M_HU hu)
	{
		// TODO: why not setting TrxName inherited?
		final String currentHUTrxName = InterfaceWrapperHelper.getTrxName(hu);
		final String contextTrxName = huContext.getTrxName();
		if (!Objects.equals(currentHUTrxName, contextTrxName))
		{
			InterfaceWrapperHelper.setTrxName(hu, contextTrxName);
		}
	}

	/**
	 * Creates a new handling unit for given <code>request</code>.
	 *
	 * The newly created HU will be also added to created HUs list.
	 *
	 * @return created handling unit; never return null
	 */
	private I_M_HU createNewHU(final IAllocationRequest request)
	{
		//
		// Create HU Builder
		final IHUBuilder huBuilder = createHUBuilder(request);

		//
		// Get the HU PI to use
		final I_M_HU_PI pi = getM_HU_PI();

		//
		// Create the new HU
		final I_M_HU hu = huBuilder.create(pi);

		// Flag the newly created HU as internally created
		// see "destroyHU" method
		DYNATTR_Producer.setValue(hu, this);

		DYNATTR_IsEmptyHU.setValue(hu, true);

		addToCreateHUs0(hu);

		return hu;
	}

	/**
	 * Creates {@link IHUBuilder} instance which will be used for creating a new HU.
	 *
	 * @return {@link IHUBuilder} instance to use
	 */
	private IHUBuilder createHUBuilder(final IAllocationRequest request)
	{
		//
		// Get parent item (if any) on which we shall include this new HU
		final I_M_HU_Item parentItem = getParent_HU_Item();

		//
		// Configure HU Builder
		final IHUBuilder huBuilder = AllocationUtils.createHUBuilder(request);
		huBuilder.setM_HU_Item_Parent(parentItem);
		huBuilder.setLocatorId(getLocatorId());
		final String huStatus = getHUStatus();

		if (!Check.isEmpty(huStatus, true))
		{
			huBuilder.setHUStatus(huStatus);
		}
		final BPartnerId bpartnerId = getBPartnerId();
		if (bpartnerId != null)
		{
			huBuilder.setBPartnerId(bpartnerId);
		}
		final int bpartnerLocationId = getC_BPartner_Location_ID();
		if (bpartnerLocationId > 0)
		{
			huBuilder.setC_BPartner_Location_ID(bpartnerLocationId);
		}

		//
		// Link to LU/TU Configuration if any
		huBuilder.setM_HU_LUTU_Configuration(getM_HU_LUTU_Configuration());

		huBuilder.setHUPlanningReceiptOwnerPM(isHUPlanningReceiptOwnerPM());

		huBuilder.setHUClearanceStatusInfo(CoalesceUtil.coalesce(getHUClearanceStatusInfo(), request.getClearanceStatusInfo()));

		return huBuilder;
	}

	/**
	 *
	 * @return the parent item to which newly created HUs shall be added. May return <code>null</code>, if the new HU shall have no parent.
	 *
	 * @see #createNewHU(IAllocationRequest)
	 * @see IHUBuilder#setM_HU_Item_Parent(I_M_HU_Item)
	 */
	protected abstract I_M_HU_Item getParent_HU_Item();

	@Override
	public IHUProducerAllocationDestination setLocatorId(final LocatorId locatorId)
	{
		assertConfigurable();
		_locatorId = locatorId;
		return this;
	}

	protected final LocatorId getLocatorId()
	{
		return _locatorId;
	}

	@Override
	public final IHUProducerAllocationDestination setHUStatus(final String huStatus)
	{
		assertConfigurable();
		_huStatus = huStatus;
		return this;
	}

	protected final String getHUStatus()
	{
		return _huStatus;
	}

	@Override
	public IHUProducerAllocationDestination setBPartnerId(final BPartnerId bpartnerId)
	{
		assertConfigurable();
		_bpartnerId = bpartnerId;
		return this;
	}

	protected final BPartnerId getBPartnerId()
	{
		return _bpartnerId;
	}

	@Override
	public final IHUProducerAllocationDestination setBPartnerAndLocationId(@NonNull final BPartnerLocationId bpartnerLocationId)
	{
		setBPartnerId(bpartnerLocationId.getBpartnerId());
		setC_BPartner_Location_ID(bpartnerLocationId.getRepoId());
		return this;
	}

	@Override
	public IHUProducerAllocationDestination setC_BPartner_Location_ID(final int bpartnerLocationId)
	{
		assertConfigurable();
		_bpartnerLocationId = bpartnerLocationId;
		return this;
	}

	protected final int getC_BPartner_Location_ID()
	{
		return _bpartnerLocationId;
	}

	/**
	 * Add given HU to our internal list of created HUs only if {@link #isAllowCreateNewHU()} return true.
	 *
	 * @return true if it was added; false if maximum capacity was reached and no other HUs are allowed
	 */
	protected final boolean addToCreatedHUsIfAllowCreateNewHU(final I_M_HU hu)
	{
		if (!isAllowCreateNewHU())
		{
			return false;
		}

		addToCreateHUs0(hu);
		return true;
	}

	private boolean addToCreateHUs0(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");
		final boolean added = _createdHUs.add(hu);
		if (added)
		{
			afterHUAddedToCreatedList(hu);
		}

		if (Services.get(IHandlingUnitsBL.class).isAggregateHU(hu))
		{
			// don't increase the counter before the first load, because it's already increased after each load;
			// if we increase it now, the number will always be <loaded aggregate VHUs + 1> (i.e. too big)
			// incAggregatedHUsCount();
		}
		else
		{
			_createdNonAggregateHUs.add(hu); // this is a subset of _createdHUs
		}

		return added;
	}

	private void incAggregatedHUsCount()
	{
		aggregatedHUsCount++;
	}

	/**
	 * Method called after an HU was added to HU created list.
	 *
	 * To be implemented by extending classes.
	 */
	protected void afterHUAddedToCreatedList(final I_M_HU hu)
	{
		// nothing at this level.
	}

	/**
	 * Add given HUs to our internal list of created HUs
	 */
	protected final void addToCreatedHUs(final Collection<I_M_HU> hus)
	{
		if (hus == null || hus.isEmpty())
		{
			return;
		}

		for (final I_M_HU hu : hus)
		{
			addToCreateHUs0(hu);
		}
	}

	public final void addToCreatedHUsIfAllowCreateNewHU(final Collection<I_M_HU> hus)
	{
		if (hus == null || hus.isEmpty())
		{
			return;
		}

		for (final I_M_HU hu : hus)
		{
			addToCreatedHUsIfAllowCreateNewHU(hu);
		}
	}

	/**
	 * Method called after an HU was removed from HU created list.
	 *
	 * To be implemented by extending classes.
	 */
	protected void afterHURemovedFromCreatedList(final I_M_HU hu)
	{
		// nothing at this level.
	}

	@Override
	public final List<I_M_HU> getCreatedHUs()
	{
		if (_createdHUs.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Make sure all created HUs have the thread inherited transaction name
		InterfaceWrapperHelper.setThreadInheritedTrxName(_createdHUs);
		return ImmutableList.copyOf(_createdHUs);
	}

	@Override
	public final Optional<I_M_HU> getSingleCreatedHU()
	{
		if (_createdHUs.isEmpty())
		{
			return Optional.empty();
		}
		else if (_createdHUs.size() == 1)
		{
			return Optional.of(_createdHUs.iterator().next());
		}
		else
		{
			throw new AdempiereException("Expected only one created HU but found more")
					.appendParametersToMessage()
					.setParameter("createdHUs", _createdHUs)
					.setParameter("producer", this);
		}
	}

	@Override
	public final Optional<HuId> getSingleCreatedHuId()
	{
		return getSingleCreatedHU()
				.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()));
	}

	@Override
	public final int getCreatedHUsCount()
	{
		return _createdNonAggregateHUs.size() + aggregatedHUsCount;
	}

	/**
	 * ...also uses {@link IHUBuilder} to create the HU and its child-HUs according to the value returned by {@link #getM_HU_PI()}.
	 */
	@Override
	public final IAllocationResult load(final IAllocationRequest request)
	{
		// Notify that we are about to start the loading
		loadStarting(request);

		// Make sure that from this point on, the Destination is no longer configurable
		setNotConfigurable();

		// Create the initial result
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request);

		// Try allocating the request until everything is completed.
		while (!result.isCompleted())
		{
			// Get/create current HU
			final HUListCursor currentHUCursor = getCreateCurrentHU(request);

			if (currentHUCursor == null)
			{
				// If there is no current HU to work with, stop here. This happens e.g. if there are 5 TU allowed on one LU and we already created those 5 TUs.
				// that's not a problem per se, it just means that
				// either another component needs to finish the job. e.g. we might need to go back from TU level to LU level and create another palet,
				// or there are already pre-loaded HUs that have everything we needed
				break;
			}
			I_M_HU currentHU = currentHUCursor.current();

			//
			// Create current actual request, perform the allocation to current HU and merge the result back
			prepareToLoad(request.getHuContext(), currentHU);
			final IAllocationRequest currentRequest = AllocationUtils.createQtyRequestForRemaining(request, result);
			final IAllocationResult currentResult = loadHU(currentHU, currentRequest);
			AllocationUtils.mergeAllocationResult(result, currentResult);

			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

			//
			// Nothing was allocated in this turn and we just created a new HU
			// => destroy newly created HU
			if (currentResult.isZeroAllocated() && DYNATTR_IsEmptyHU.isSet(currentHU))
			{
				// this is OK *if* currentHU is an aggregate VHU, because the loadHU method shall only load anything to an aggregate VHU if it can completely "fill" one of the represented TUs.
				// e.g. if the aggregate VHU represents IFCOs with a capacity of 40kg and the request is only about 39kg, then loadHU shall not allocate anything to the aggregate VHU.
				if (!handlingUnitsBL.isAggregateHU(currentHU))
				{
					final I_M_HU_PI_Version currentHU_PI_Version = handlingUnitsBL.getPIVersion(currentHU);

					// if we could not allocate to a new not-aggregate HU, so we surely can not allocate to new ones either
					// throw exception because shall not happen
					if (developerModeBL.isEnabled())
					{
						throw new AdempiereException("Allocated ZERO quantity on a newly created HU"
								+ "\n Current Request: " + currentRequest
								+ "\n Current Result: " + currentResult
								+ "\n Initial Request: " + request
								+ "\n PI: " + (currentHU_PI_Version != null ? currentHU_PI_Version.getName() : ""));
					}

					// throw a nice user friendly error
					throw new AdempiereException(MSG_QTY_LOAD_ERROR, currentHU_PI_Version != null ? currentHU_PI_Version.getName() : "");
				}
				// destroyCurrentHU(currentHUCursor);
				// currentHU = null;
			}

			if (handlingUnitsBL.isAggregateHU(currentHU))
			{
				final boolean somethingWasAllocated = !currentResult.isZeroAllocated();
				if (somethingWasAllocated)
				{
					// we need to increase aggregatedHUsCount, so the system knows that now the "bag" represents yet one more HU.
					// note that in the case of IsEmptyHU=true, the qty was already increased when the HU was created
					incAggregatedHUsCount();
				}
			}

			// Make sure current HU is no longer flagged as a new and empty HU
			DYNATTR_IsEmptyHU.reset(currentHU);

			if (currentResult.getQtyToAllocate().signum() != 0) // It seems that current HU is fully loaded and there is still stuff left to allocate
			{
				if (handlingUnitsBL.isAggregateHU(currentHU) && isAllowCreateNewHU())
				{
					if (currentResult.isZeroAllocated())
					{
						// there is something left to allocate, but the loadHU did not want to allocate onto currentHU which is aggregate.
						// this only happens if the request's qty is less than a full unit of the TUs (e.g. IFCOs) that the aggregated 'currentHU' represents.
						// to stay in the IFCO-example, we now need to allocate to a partial IFCO. In order to do just that, we need to close the current HU.
						currentHUCursor.closeCurrent();
					}
					else
					{
						// don't proceed to a new HU. currentHU is a "bag" and we can load more into it.
					}
				}
				else
				{
					currentHUCursor.closeCurrent(); // close the current "not-bag" HU and move on
				}

				currentHU = null;
			}
			else
			{
				if (handlingUnitsBL.isAggregateHU(currentHU) && !isAllowCreateNewHU())
				{
					// we were able to allocate it all, but
					// if currentHU is an aggregate and it's not allowed to represent another one within the same aggregate,
					// then we also need to close it here.
					// Otherwise there would be another loadHU() invocation with would increase the HA item's qty once again (despite not loading anything further).
					// Note that there is LUTUProducerDestinationTransferTests.testForCorrectItemQtyOnTwoTrxCandidates() just for this case :-)
					currentHUCursor.closeCurrent();
				}
			}
		}

		//
		// Check if the result is completed (i.e. all requested qty was allocated) and if not, call the "loadRemaining"
		if (!result.isCompleted())
		{
			final IAllocationRequest currentRequest = AllocationUtils.createQtyRequestForRemaining(request, result);
			final IAllocationResult currentResult = loadRemaining(currentRequest);
			AllocationUtils.mergeAllocationResult(result, currentResult);
		}

		//
		// Notify that we finished the loading
		loadFinished(result, request.getHuContext());

		return result;
	}

	/**
	 * Called by {@link #load(IAllocationRequest)} right before actual load is starting.
	 *
	 * In this method, implementators can do further configurations and loadings if needed.
	 */
	protected void loadStarting(final IAllocationRequest request)
	{
		// nothing at this level
	}

	/**
	 * Called by {@link #load(IAllocationRequest)} right before exiting.<br>
	 * In this method, implementors can do final polishing because the loader exits.
	 * <p>
	 * <b>IMPORTANT:</b> overriding methods should invoke {@code super}, unless they know what they do.
	 * <p>
	 * gh #460: this implementation handles aggregate HU and updates their PackingMaterial items.
	 * Afterwards it invokes {@link IMutableAllocationResult#aggregateTransactions()} to combine all trx candidates that belong to the same (aggregate) VHU.
	 *
	 * @param result_IGNORED current result (that will be also returned by {@link #load(IAllocationRequest)} method); won't be changed by this method, but maybe by overriding methods.
	 */
	@OverridingMethodsMustInvokeSuper
	protected void loadFinished(final IMutableAllocationResult result_IGNORED, final IHUContext huContext)
	{
		// TODO: i think we can move this stuff or something better into a model interceptor that is fired when item.qty is changed
		_createdHUs.forEach(
				hu -> {
					final IAttributeStorage attributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
					final IWeightable weightable = Weightables.wrap(attributeStorage);
					final AttributeCode weightTareAttribute = weightable.getWeightTareAttribute();
					if (attributeStorage.hasAttribute(weightTareAttribute))
					{
						final BigDecimal tareOfHU = WeightTareAttributeValueCallout.calculateWeightTare(hu);

						final BigDecimal taresOfChildren = attributeStorage
								.getChildAttributeStorages(true) // loadIfNeeded=true because we need to make sure to have all tares that exist. not matter if those storages are already on memory or no.
								.stream()
								.filter(s -> s.hasAttribute(weightTareAttribute))
								.map(s -> s.getValueAsBigDecimal(weightTareAttribute))
								.reduce(BigDecimal.ZERO, BigDecimal::add);

						attributeStorage
								.setValue(weightTareAttribute, tareOfHU.add(taresOfChildren));
					}
				});
	}

	/**
	 * Method called when after {@link #load(IAllocationRequest)}-ing we could not allocate all qty.
	 * This implementation does nothing, but can be overridden.
	 *
	 * @param request request with remaining qty which was not allocated yet
	 * @return result
	 */
	protected IAllocationResult loadRemaining(final IAllocationRequest request)
	{
		// nothing at this level
		return AllocationUtils.nullResult();
	}

	public final AbstractProducerDestination setM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		assertConfigurable();
		_lutuConfiguration = lutuConfiguration;
		return this;
	}

	public final I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration()
	{
		return _lutuConfiguration;
	}

	@Override
	public final IHUProducerAllocationDestination setIsHUPlanningReceiptOwnerPM(boolean isHUPlanningReceiptOwnerPM)
	{
		this._isHUPlanningReceiptOwnerPM = isHUPlanningReceiptOwnerPM;
		return this;
	}

	public final boolean isHUPlanningReceiptOwnerPM()
	{
		return _isHUPlanningReceiptOwnerPM;
	}

	/**
	 * Sets this producer in "non-configurable" state. No further configuration to this producer will be allowed after calling this method.
	 */
	protected final void setNotConfigurable()
	{
		_configurable = false;
	}

	/**
	 * Makes sure producer is in configurable state. If not, and exception will be thrown.
	 */
	protected final void assertConfigurable()
	{
		if (!_configurable)
		{
			throw new HUException("This producer is not configurable anymore: " + this);
		}
	}

	@Override
	public final IHUProducerAllocationDestination setHUClearanceStatusInfo(final ClearanceStatusInfo huClearanceStatusInfo)
	{
		assertConfigurable();
		_huClearanceStatusInfo = huClearanceStatusInfo;
		return this;
	}

	public final ClearanceStatusInfo getHUClearanceStatusInfo()
	{
		return _huClearanceStatusInfo;
	}
}
