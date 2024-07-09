package de.metas.handlingunits.attribute.storage.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IAttributeValueListener;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.exceptions.AttributeNotFoundException;
import de.metas.handlingunits.attribute.impl.AbstractAttributeValue;
import de.metas.handlingunits.attribute.impl.NullAttributeValue;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagationContext;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagator;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagatorFactory;
import de.metas.handlingunits.attribute.propagation.impl.HUAttributePropagationContext;
import de.metas.handlingunits.attribute.propagation.impl.NoPropagationHUAttributePropagator;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.AttributeSourceDocument;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.CurrentAttributeValueContextProvider;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.NamePair;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractAttributeStorage implements IAttributeStorage
{
	// Services
	protected final transient Logger logger = LogManager.getLogger(getClass());
	private final IHUAttributePropagatorFactory huAttributePropagatorFactory = Services.get(IHUAttributePropagatorFactory.class);
	private final IHUPIAttributesDAO huPIAttributesDAO = Services.get(IHUPIAttributesDAO.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IAttributesBL attributesBL = Services.get(IAttributesBL.class);

	// Factories and other DAOs
	private final IAttributeStorageFactory storageFactory;
	private final IHUAttributesDAO huAttributesDAO;
	private final IHUStorageDAO huStorageDAO;

	// Attributes
	private IndexedAttributeValues _indexedAttributeValues = IndexedAttributeValues.NULL;
	private final ReentrantLock _indexedAttributeValuesLock = new ReentrantLock();

	private final CompositeAttributeStorageListener listeners = new CompositeAttributeStorageListener();

	/**
	 * Listens on {@link IAttributeValue} events and forwards them to {@link #onAttributeValueChanged(IAttributeValueContext, IAttributeValue, Object, Object)}.
	 * Note that in the case of {@link AbstractAttributeValue}, this listener is (also) called if {@link #setValue(I_M_Attribute, Object)} is called.
	 * <ul>
	 * <code>AbstractAttributeStorage.setValue</code>
	 * <code>NoPropagationHUAttributePropagator.</code>
	 * </ul>
	 */
	private final IAttributeValueListener attributeValueListener = new IAttributeValueListener()
	{
		@Override
		public void onValueChanged(final IAttributeValueContext attributeValueContext, final IAttributeValue attributeValue, final Object valueOld, final Object valueNew)
		{
			onAttributeValueChanged(attributeValueContext, attributeValue, valueOld, valueNew);
		}

		// @formatter:off
		@Override
		public String toString() {return "AbstractAttributeStorage[<anonymous IAttributeValueListener>]";}
		// @formatter:on
	};

	/**
	 * Callouts invoker listener
	 * <p>
	 * NOTE: we are keeping a hard reference here because listeners are registered weakly.
	 */
	private final CalloutAttributeStorageListener calloutAttributeStorageListener;

	/**
	 * Sort {@link IAttributeValue}s by DisplaySeqNo
	 */
	private static final Comparator<IAttributeValue> attributeValueSortBySeqNo = (o1, o2) -> {
		// Sort by DisplaySeqNo
		final int displaySeqNoCmp = o1.getDisplaySeqNo() - o2.getDisplaySeqNo();
		if (displaySeqNoCmp != 0)
		{
			return displaySeqNoCmp;
		}

		// Fallback: sort by M_Attribute_ID (just to have a predictible order)
		final int attributeIdCmp = o1.getM_Attribute().getM_Attribute_ID() - o2.getM_Attribute().getM_Attribute_ID();
		return attributeIdCmp;
	};

	public AbstractAttributeStorage(@NonNull final IAttributeStorageFactory storageFactory)
	{
		this.storageFactory = storageFactory;
		this.huAttributesDAO = storageFactory.getHUAttributesDAO();
		this.huStorageDAO = storageFactory.getHUStorageDAO();

		this.calloutAttributeStorageListener = new CalloutAttributeStorageListener();
		this.listeners.addAttributeStorageListener(calloutAttributeStorageListener);
	}

	@Override
	public final String toString()
	{
		final ToStringHelper stringHelper = MoreObjects.toStringHelper(this);
		toString(stringHelper);

		return stringHelper
				// .add("huAttributePropagatorFactory", huAttributePropagatorFactory)
				// .add("storageFactory", storageFactory)
				// .add("indexedAttributeValues", _indexedAttributeValues)
				// .add("listeners", listeners)
				// .add("attributeValueListener", attributeValueListener)
				.toString();
	}

	protected void toString(final ToStringHelper stringHelper)
	{
		// nothing on this level
	}

	@Override
	public final IAttributeStorageFactory getAttributeStorageFactory()
	{
		assertNotDisposed();

		return storageFactory;
	}

	protected final IHUAttributesDAO getHUAttributesDAO()
	{
		assertNotDisposed();

		return huAttributesDAO;
	}

	public final IHUStorageDAO getHUStorageDAO()
	{
		assertNotDisposed();

		return huStorageDAO;
	}

	protected final IHUPIAttributesDAO getHUPIAttributesDAO()
	{
		assertNotDisposed();

		return huPIAttributesDAO;
	}

	@Override
	public final void addListener(final IAttributeStorageListener listener)
	{
		assertNotDisposed();
		listeners.addAttributeStorageListener(listener);
	}

	@Override
	public final void removeListener(final IAttributeStorageListener listener)
	{
		// NOTE: we can accept removing a listener even if this storage is disposed,
		// because this method might be called as a reaction to a disposal event
		// assertNotDisposed();

		listeners.removeAttributeStorageListener(listener);
	}

	private void onAttributeValueChanged(final IAttributeValueContext attributeValueContext, final IAttributeValue attributeValue, final Object valueOld, final Object valueNew)
	{
		assertNotDisposed();

		// TODO: check if attributeValue is in our list of attribute values
		listeners.onAttributeValueChanged(attributeValueContext, this, attributeValue, valueOld);
	}

	/**
	 * Load or creates initial {@link IAttributeValue}s.
	 *
	 * @return {@link IAttributeValue}s
	 */
	protected abstract List<IAttributeValue> loadAttributeValues();

	/**
	 * Set the inner {@link #_indexedAttributeValues}, build up the indexing maps and add {@link #attributeValueListener}.
	 */
	private void setInnerAttributeValues(final List<IAttributeValue> attributeValues)
	{
		_indexedAttributeValuesLock.lock();
		try
		{
			if (!_indexedAttributeValues.isNull())
			{
				throw new AdempiereException("Inner attributeValues list was already set."
						+ "\n Attribute Storage: " + this
						+ "\n New attribute values: " + attributeValues);
			}

			// logger.debug("Setting attribute values: {}", attributeValues, new Exception("trace"));

			final IndexedAttributeValues indexedAttributeValues = IndexedAttributeValues.of(attributeValues);
			indexedAttributeValues.addAttributeValueListener(attributeValueListener);

			_indexedAttributeValues = indexedAttributeValues;
		}
		finally
		{
			_indexedAttributeValuesLock.unlock();
		}
	}

	private IndexedAttributeValues getIndexedAttributeValues()
	{
		_indexedAttributeValuesLock.lock();
		try
		{
			// Don't load attributes if we are generating them right in this moment
			// This case can happen when while generating the attributes some BL (e.g. callout) wants to access current attributes.
			if (_generateInitialAttributesRunning.get())
			{
				final HUException ex = new HUException("Accessing attribute storage while generating it's values is not allowed. Returning empty values."
						+ "\n Attribute Storage: " + this);
				logger.warn(ex.getLocalizedMessage(), ex);
				return _indexedAttributeValues;
			}

			// Load attributes if they where not loaded before
			if (_indexedAttributeValues.isNull())
			{
				final List<IAttributeValue> attributeValues = loadAttributeValues();
				setInnerAttributeValues(attributeValues);
			}

			return _indexedAttributeValues;
		}
		finally
		{
			_indexedAttributeValuesLock.unlock();
		}
	}

	private IndexedAttributeValues getIndexedAttributeValuesNoLoad()
	{
		// NOTE: even though we are not loading them, at least we want to make sure there is no loading in progress,
		// because that would produce unpredictable results
		_indexedAttributeValuesLock.lock();
		try
		{
			return _indexedAttributeValues;
		}
		finally
		{
			_indexedAttributeValuesLock.unlock();
		}
	}

	@Override
	public final List<IAttributeValue> getAttributeValues()
	{
		assertNotDisposed();

		return getIndexedAttributeValues().getAttributeValues();
	}

	/**
	 * Gets current {@link IAttributeValue}s.
	 * <p>
	 * Compared with {@link #getAttributeValues()} which is also loading them if needed, this method is just returning current ones, loaded or not.
	 *
	 * @return current attribute values
	 */
	protected final List<IAttributeValue> getAttributeValuesCurrent()
	{
		return getIndexedAttributeValuesNoLoad().getAttributeValues();
	}

	@Override
	public final IAttributeValue getAttributeValue(@NonNull final AttributeCode attributeCode)
	{
		final IAttributeValue attributeValue = getAttributeValueOrNull(attributeCode);
		if (NullAttributeValue.isNull(attributeValue))
		{
			throw new AttributeNotFoundException(attributeCode, this);
		}
		return attributeValue;
	}

	/**
	 * @return {@link IAttributeValue} or {@link NullAttributeValue#instance}
	 */
	private IAttributeValue getAttributeValueOrNull(@NonNull final AttributeCode attributeCode)
	{
		assertNotDisposed();

		final IAttributeValue attributeValue = getIndexedAttributeValues().getAttributeValueOrNull(attributeCode);
		if (attributeValue == null)
		{
			return NullAttributeValue.instance;
		}

		return attributeValue;
	}

	@Override
	public final boolean hasAttribute(@NonNull final AttributeCode attributeCode)
	{
		assertNotDisposed();
		return getIndexedAttributeValues().hasAttribute(attributeCode);
	}

	@Override
	public boolean hasAttribute(final AttributeId attributeId)
	{
		assertNotDisposed();
		return getIndexedAttributeValues().hasAttribute(attributeId);
	}

	@Override
	public final Collection<I_M_Attribute> getAttributes()
	{
		assertNotDisposed();
		return getIndexedAttributeValues().getAttributes();
	}

	@Nullable
	@Override
	public final I_M_Attribute getAttributeByIdIfExists(final int attributeId)
	{
		assertNotDisposed();

		if (attributeId <= 0)
		{
			return null;
		}

		return getIndexedAttributeValues().getAttributeOrNull(AttributeId.ofRepoId(attributeId));
	}

	@Override
	public final I_M_Attribute getAttributeByValueKeyOrNull(final AttributeCode attributeCode)
	{
		assertNotDisposed();

		final IndexedAttributeValues indexedAttributeValues = getIndexedAttributeValues();
		if (!indexedAttributeValues.hasAttributes())
		{
			throw new AttributeNotFoundException(attributeCode, this);
		}

		return indexedAttributeValues.getAttributeByCodeOrNull(attributeCode);
	}

	@Override
	public final void generateInitialAttributes(@Nullable final Map<AttributeId, Object> defaultAttributesValue)
	{
		assertNotDisposed();

		// Assume there were no attributes generated yet
		if (!getIndexedAttributeValuesNoLoad().isNull())
		{
			throw new AdempiereException("Cannot generate attributes because they were already generated: " + this);
		}

		final IAttributeValueContext attributesCtx = getCurrentPropagationContextOrNull();

		//
		// Generate initial attributes
		final List<IAttributeValue> attributeValues;
		final boolean generateInitialAttributesAlreadyRunning = _generateInitialAttributesRunning.getAndSet(true);
		Check.assume(!generateInitialAttributesAlreadyRunning, "Internal error: generateInitialAttributes is already running for {}", this);
		try
		{
			attributeValues = generateAndGetInitialAttributes(attributesCtx, defaultAttributesValue);
		}
		finally
		{
			_generateInitialAttributesRunning.set(false);
		}
		setInnerAttributeValues(attributeValues);

		//
		// Notify listeners
		// => callouts will be triggered
		if (attributeValues != null && !attributeValues.isEmpty())
		{
			for (final IAttributeValue attributeValue : attributeValues)
			{
				listeners.onAttributeValueCreated(attributesCtx, this, attributeValue);
			}
		}

		//
		// Notify parent that a new child attributes storage was added. This might result in child attributes beein gpropagate upwards from the child
		final IAttributeStorage parentAttributeStorage = getParentAttributeStorage();
		if (!NullAttributeStorage.instance.equals(parentAttributeStorage))
		{
			parentAttributeStorage.onChildAttributeStorageAdded(this);
		}
	}

	private final AtomicBoolean _generateInitialAttributesRunning = new AtomicBoolean(false);

	protected abstract List<IAttributeValue> generateAndGetInitialAttributes(final IAttributeValueContext attributesCtx, Map<AttributeId, Object> defaultAttributesValue);

	@Override
	public Object getValue(final AttributeCode attributeCode)
	{
		// assertNotDisposed(); // checked in next called method

		final IAttributeValue value = getAttributeValue(attributeCode);
		return value.getValue();
	}

	@Override
	public String getValueAsString(final AttributeCode attributeCode)
	{
		// assertNotDisposed(); // checked in next called method

		final IAttributeValue value = getAttributeValue(attributeCode);
		return value.getValueAsString();
	}

	@Override
	@Nullable
	public String getValueAsStringOrNull(@NonNull final AttributeCode attributeCode)
	{
		final IAttributeValue attributeValue = getAttributeValueOrNull(attributeCode);

		if (NullAttributeValue.isNull(attributeValue))
		{
			return null;
		}

		return attributeValue.getValueAsString();
	}

	@Override
	public BigDecimal getValueAsBigDecimal(final @NonNull AttributeCode attributeCode)
	{
		// assertNotDisposed(); // checked in next called method

		final IAttributeValue value = getAttributeValue(attributeCode);
		return value.getValueAsBigDecimal();
	}

	@Override
	public int getValueAsInt(final AttributeCode attributeCode)
	{
		// assertNotDisposed(); // checked in next called method

		final IAttributeValue value = getAttributeValue(attributeCode);
		return value.getValueAsInt();
	}

	@Override
	public Date getValueAsDate(final AttributeCode attributeCode)
	{
		// assertNotDisposed(); // checked in next called method

		final IAttributeValue value = getAttributeValue(attributeCode);
		return value.getValueAsDate();
	}

	@Override
	public String getValueName(final I_M_Attribute attribute)
	{
		assertNotDisposed();

		Check.assumeNotNull(attribute, "attribute not null");
		final Object value = getValue(attribute);
		Check.assumeNotNull(value, "attributeValue not null");
		final String valueStr = value.toString();

		//
		// Do not allow the M_AttributeValue to be null in this case. We're assuming that there are database entries for predefined values already.
		// If you're writing automatic tests, you'll have to make some entries.
		final AttributeListValue attributeValue = attributeDAO.retrieveAttributeValueOrNull(attribute, valueStr);
		Check.assumeNotNull(attributeValue, "M_AttributeValue was found for M_Attribute={}, M_Attribute.Value={}", attribute, valueStr);

		return attributeValue.getName();
	}

	@Override
	public Object getValueInitial(final I_M_Attribute attribute)
	{
		// assertNotDisposed(); // checked in next called method

		final IAttributeValue value = getAttributeValue(attribute);
		return value.getValueInitial();
	}

	@Override
	public BigDecimal getValueInitialAsBigDecimal(final AttributeCode attributeCode)
	{
		// assertNotDisposed(); // checked in next called method

		final IAttributeValue value = getAttributeValue(attributeCode);
		return value.getValueInitialAsBigDecimal();
	}

	private IHUAttributePropagationContext createPropagationContext(final I_M_Attribute attribute, final IHUAttributePropagator propagator)
	{
		final IHUAttributePropagationContext currentPropagationContext = getCurrentPropagationContextOrNull();
		if (currentPropagationContext == null)
		{
			return new HUAttributePropagationContext(this, propagator, attribute);
		}

		return currentPropagationContext.cloneForPropagation(this, attribute, propagator);
	}

	/**
	 * <b>Note: This is only package for testing purposes. NEVER call directly.</b><br>
	 *
	 * @return {@link IHUAttributePropagationContext} available or <code>null</code> if propagation wasn't started yet
	 */
	@VisibleForTesting
	/* package */ final IHUAttributePropagationContext getCurrentPropagationContextOrNull()
	{
		final IAttributeValueContext attributesContext = CurrentAttributeValueContextProvider.getCurrentAttributesContextOrNull();
		return toHUAttributePropagationContext(attributesContext);
	}

	private static IHUAttributePropagationContext toHUAttributePropagationContext(final IAttributeValueContext attributesContext)
	{
		Check.assumeInstanceOfOrNull(attributesContext, IHUAttributePropagationContext.class, "attributesContext");
		return (IHUAttributePropagationContext)attributesContext;
	}

	/**
	 * <b>Note: This is only package for testing purposes. NEVER call directly.</b><br>
	 * <br>
	 * Set propagation context in {@link CurrentAttributeValueContextProvider}. This method will validate that the new context will have the old registered context as a parent.<br>
	 * However, if <code>validateParentContext=false</code> (rollback), then don't check for parent, because we assume that when first setting it we have already checked.
	 */
	@VisibleForTesting
	/* package */ final IHUAttributePropagationContext setCurrentPropagationContext(final IHUAttributePropagationContext huAttributePropagationContext, final boolean validateParentContext)
	{
		final IAttributeValueContext attributesContextOld = CurrentAttributeValueContextProvider.setCurrentAttributesContext(huAttributePropagationContext);
		final IHUAttributePropagationContext huAttributePropagationContextOld = toHUAttributePropagationContext(attributesContextOld);

		//
		// This means that we're not rolling back propagation context, but setting it for the first time
		if (validateParentContext)
		{
			// currentPropagationContextBackup might be null, but then propagationContext should also have parent==null
			Check.errorIf(!Util.same(huAttributePropagationContext.getParent(), huAttributePropagationContextOld),
					"{} is not a parent of {}",
					huAttributePropagationContextOld, huAttributePropagationContext);
		}

		return huAttributePropagationContextOld;
	}

	@Override
	public final void setValueNoPropagate(final AttributeCode attributeCode, final Object value)
	{
		assertNotDisposed();

		final IndexedAttributeValues indexedAttributeValues = getIndexedAttributeValues();
		final I_M_Attribute attribute = indexedAttributeValues.getAttributeByCodeOrNull(attributeCode);
		if (attribute == null)
		{
			throw new AttributeNotFoundException(attributeCode, this);
		}

		//
		// Get NoPropagation propagator
		final String noPropagation = X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation;
		final IHUAttributePropagator noPropagationPropagator = huAttributePropagatorFactory.getPropagator(noPropagation);

		//
		// Create propagation context with NoPropagation propagator & set internal value
		final IHUAttributePropagationContext propagationContext = createPropagationContext(attribute, noPropagationPropagator);
		setValue(propagationContext, value);
	}

	@Override
	public void setValue(@NonNull final AttributeId attributeId, final Object value)
	{
		final I_M_Attribute attribute = getAttributeByIdIfExists(attributeId);
		if (attribute == null)
		{
			throw new AttributeNotFoundException(attributeId, this);
		}

		setValue(attribute, value);
	}

	@Override
	public final void setValue(final AttributeCode attributeCode, final Object value)
	{
		final I_M_Attribute attribute = getAttributeByValueKeyOrNull(attributeCode);
		if (attribute == null)
		{
			throw new AttributeNotFoundException(attributeCode, this);
		}

		setValue(attribute, value);
	}

	/**
	 * Note: Order of setting values is important here:
	 * <ol>
	 * <li>Set internal storage value and propagate in normal direction.</li>
	 * <li>Afterwards, propagate in opposite direction if possible without re-setting the internal value.</li>
	 * </ol>
	 */
	@Override
	public final void setValue(@NonNull final I_M_Attribute attribute, final Object value)
	{
		assertNotDisposed();

		final IHUAttributePropagator attributePropagator = huAttributePropagatorFactory.getPropagator(this, attribute);
		final IHUAttributePropagationContext propagationContext = createPropagationContext(attribute, attributePropagator);

		//
		// Note that this variable sort-of means:
		// * If pushing in reverse, keep doing so, but don't change direction again
		// * If not pushing in reverse, then push in normal direction first, then in reverse
		boolean pushingDirectionReverse = false;

		//
		// Find pushing direction based on propagation context hierarchy
		final IHUAttributePropagator lastPropagatorForAttribute = propagationContext.getLastPropagatorOrNull(attribute);
		if (lastPropagatorForAttribute != null
				&& attributePropagator.getReversalPropagationType()
				.equals(lastPropagatorForAttribute.getPropagationType()))              // if we're propagating in reverse
		{
			pushingDirectionReverse = true;
		}

		//
		// Propagate in the normal direction of the attribute (only if we're not currently pushing in opposite direction)
		if (!pushingDirectionReverse)
		{
			setValue(propagationContext, value);
		}

		//
		// There's no use to propagate in reverse if we don't have a no-propagation propagator
		if (!isPropagable(getAttributeValue(attribute)))
		{
			return;
		}

		//
		// On external input, propagate in the reverse direction of the attribute
		final IHUAttributePropagator reversalAttributePropagator = huAttributePropagatorFactory.getReversalPropagator(attributePropagator);
		final IHUAttributePropagationContext reversalPropagationContext = createPropagationContext(attribute, reversalAttributePropagator);
		reversalPropagationContext.setUpdateStorageValue(pushingDirectionReverse); // do not update storage inner value when propagating in reverse the first time (was set right before)
		setValue(reversalPropagationContext, value);
	}

	@Override
	public final NamePair setValueToNull(final I_M_Attribute attribute)
	{
		final IAttributeValue attributeValue = getAttributeValue(attribute);
		final NamePair nullValue = attributeValue.getNullAttributeValue();
		setValue(attribute, nullValue);

		return nullValue;
	}

	/**
	 * Propagate the given value according to the given <code>propagationContext</code>.<br>
	 * If just this storage's value shall be set the context shall contain the {@link NoPropagationHUAttributePropagator}.
	 * <p>
	 * Note: not only the actual propagation, but also the set-invocation to <code>this</code> storage is the propagator's job.
	 *
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	private void setValue(final IHUAttributePropagationContext propagationContext, final Object value)
	{
		Check.assumeNotNull(propagationContext, "propagationContext not null for {}", this);

		//
		// Avoid recursion in case value was already updated somewhere before this invocation
		if (propagationContext.isValueUpdatedBefore())
		{
			logger.debug("ALREADY UPDATED: Skipping attribute value propagation for Value={}, Attribute={}, this={}, propagationContext={}",
					value, propagationContext.getAttribute(), this, propagationContext);
			return;
		}

		final Object valueOld = getValue(propagationContext.getAttribute());
		if (Objects.equals(valueOld, value)
				//
				// We only wish to skip propagating when we're not updating internal storage value (because that happens in reversal and we want reversals)
				//
				&& propagationContext.isUpdateStorageValue())
		{
			// Nothing changed, it's pointless to set it again and call the propagator
			logger.debug("SAME VALUE: Skipping attribute value propagation for Value={}, =Attribute{}, this={}, propagationContext={}",
					value, propagationContext.getAttribute(), this, propagationContext);
			return;
		}

		// Make sure that while we recurse into a propagator, we have that propagator's context as our own 'currentPropagationContext'.
		// However, when we return from that invocation, we resort to our former context (see finally block).
		final IHUAttributePropagationContext currentPropagationContextBackup = setCurrentPropagationContext(propagationContext, true); // validateParentContext

		try
		{
			// Mark that in this context we have set the attribute value
			// We use this mark to avoid recursion
			propagationContext.setValueUpdateInvocation();

			final IHUAttributePropagator propagator = propagationContext.getPropagator();

			logger.debug("PROPAGATING with propagator={}: Setting Value={}, Attribute={}, this={}, propagationContext={}",
					propagator, value, propagationContext.getAttribute(), this, propagationContext);
			propagator.propagateValue(propagationContext, this, value);
		}
		finally
		{
			// Restore to old context
			setCurrentPropagationContext(currentPropagationContextBackup, false); // validateParentContext
		}
	}

	@Override
	public String getPropagationType(final I_M_Attribute attribute)
	{
		assertNotDisposed();

		final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
		final IAttributeValue attributeValue = getAttributeValueOrNull(attributeCode);
		return attributeValue.getPropagationType();
	}

	@Override
	public IAttributeAggregationStrategy retrieveAggregationStrategy(final I_M_Attribute attribute)
	{
		assertNotDisposed();

		final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
		final IAttributeValue attributeValue = getAttributeValueOrNull(attributeCode);
		return attributeValue.retrieveAggregationStrategy();
	}

	@Override
	public IAttributeSplitterStrategy retrieveSplitterStrategy(final I_M_Attribute attribute)
	{
		assertNotDisposed();

		final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
		final IAttributeValue attributeValue = getAttributeValueOrNull(attributeCode);
		return attributeValue.retrieveSplitterStrategy();
	}

	@Override
	public IHUAttributeTransferStrategy retrieveTransferStrategy(final I_M_Attribute attribute)
	{
		assertNotDisposed();

		final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
		final IAttributeValue attributeValue = getAttributeValueOrNull(attributeCode);
		return attributeValue.retrieveTransferStrategy();
	}

	@Override
	public boolean isPropagatedValue(final I_M_Attribute attribute)
	{
		assertNotDisposed();

		final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
		final IAttributeValue attributeValue = getAttributeValueOrNull(attributeCode);

		final String attributePropagationType = attributeValue.getPropagationType();

		if (X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation.equals(attributePropagationType))
		{
			return false;
		}
		else if (X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp.equals(attributePropagationType))
		{
			return isPropagatedFromChildren(this, attribute);
		}
		else if (X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown.equals(attributePropagationType))
		{
			return isPropagatedFromParents(this, attribute);
		}
		else
		{
			throw new AdempiereException("Not supported attribute propagation type: " + attributePropagationType);
		}
	}

	/**
	 * Helper method which checks if given attribute can <b>AND</b> actually it is propagated from children.
	 *
	 * @return true if given attribute is propagated from children
	 */
	private static boolean isPropagatedFromChildren(final IAttributeStorage attributeStorage, final I_M_Attribute attribute)
	{
		final Collection<IAttributeStorage> childAttributeStorages = attributeStorage.getChildAttributeStorages(true);
		Check.assumeNotNull(childAttributeStorages, "childAttributeSets not null");
		if (childAttributeStorages.isEmpty())
		{
			return false;
		}

		for (final IAttributeStorage childAttributeStorage : childAttributeStorages)
		{
			final String childAttributePropagationType = childAttributeStorage.getPropagationType(attribute);
			if (X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp.equals(childAttributePropagationType))
			{
				return true;
			}

			if (isPropagatedFromChildren(childAttributeStorage, attribute))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Helper method which checks if given attribute can <b>AND</b> actually it is propagated from it's parent.
	 *
	 * @return true if given attribute is propagated from it's parent
	 */
	private static boolean isPropagatedFromParents(final IAttributeStorage attributeStorage, final I_M_Attribute attribute)
	{
		final IAttributeStorage parentAttributeStorage = attributeStorage.getParentAttributeStorage();
		Check.assumeNotNull(parentAttributeStorage, "parentAttributeSet not null");
		if (NullAttributeStorage.instance.equals(parentAttributeStorage))
		{
			return false;
		}

		final String parentAttributePropagationType = parentAttributeStorage.getPropagationType(attribute);
		if (X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown.equals(parentAttributePropagationType))
		{
			return true;
		}

		return isPropagatedFromParents(parentAttributeStorage, attribute);
	}

	@Override
	public IAttributeValueCallout getAttributeValueCallout(final I_M_Attribute attribute)
	{
		assertNotDisposed();

		final IAttributeValue attributeValue = getAttributeValue(attribute);
		return attributeValue.getAttributeValueCallout();
	}

	@Override
	public String getAttributeValueType(final I_M_Attribute attribute)
	{
		assertNotDisposed();

		final IAttributeValue attributeValue = getAttributeValue(attribute);
		return attributeValue.getAttributeValueType();
	}

	/**
	 * Method called when this storage is notified that a child was added.
	 * <p>
	 * NOTE: in case our attribute storage implementation is caching the child storages, this is the place where we need to adjust our internal child storages cache
	 */
	protected abstract void addChildAttributeStorage(final IAttributeStorage childAttributeStorage);

	/**
	 * Method called when this storage is notified that a child was removed.
	 * <p>
	 * NOTE: in case our attribute storage implementation is caching the child storages, this is the place where we need to adjust our internal child storages cache
	 */
	protected abstract IAttributeStorage removeChildAttributeStorage(final IAttributeStorage childAttributeStorage);

	@Override
	public final void onChildAttributeStorageAdded(final IAttributeStorage childAttributeStorage)
	{
		assertNotDisposed();

		//
		// Add child storage to our inner list
		// (do this before you call "pushDown")
		addChildAttributeStorage(childAttributeStorage);

		//
		// Propagate from given children to all it's parents (... and children too)
		if (childAttributeStorage != null)
		{
			childAttributeStorage.pushUp();
		}
	}

	@Override
	public void onChildAttributeStorageRemoved(final IAttributeStorage childAttributeStorageRemoved)
	{
		//
		// Ask child to pushUp null values for it's attributes
		childAttributeStorageRemoved.pushUpRollback();

		//
		// Remove child storage from our inner list
		// (do this before you call "pushDown")
		removeChildAttributeStorage(childAttributeStorageRemoved);
	}

	@Override
	public void pushUp()
	{
		assertNotDisposed();

		for (final IAttributeValue attributeValue : getAttributeValues())
		{
			final boolean pushNullValue = false;
			pushAttributePropagation(attributeValue, X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp, pushNullValue);
		}
	}

	@Override
	public void pushUpRollback()
	{
		assertNotDisposed();

		for (final IAttributeValue attributeValue : getAttributeValues())
		{
			final boolean pushNullValue = true;
			pushAttributePropagation(attributeValue, X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp, pushNullValue);
		}
	}

	/**
	 * Push down attributes: force propagating attributes from this storage to all it's child storages
	 */
	@Override
	public void pushDown()
	{
		assertNotDisposed();

		for (final IAttributeValue attributeValue : getAttributeValues())
		{
			final boolean pushNullValue = false;
			pushAttributePropagation(attributeValue, X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown, pushNullValue);
		}
	}

	/**
	 * Check if given attribute can be propagated as desired.
	 * <p>
	 * NOTE: this method is not checking what kind of attribute is on parent level or on children level, that's the reason why it's static
	 *
	 * @param attributeValue attribute
	 * @return <code>true</code> if the given attribute's propagation type is not <code>NONE</code> and if it is equal to the given <code>desiredPropagationType</code>.
	 */
	private static boolean isPropagable(final IAttributeValue attributeValue)
	{
		Check.assumeNotNull(attributeValue, "attributeValue not null");

		//
		// Check if this attribute is propagated
		//
		// NOTE: we are not calling isPropagatedValue(attribute) method because at this moment it can be that not all information are available
		// so we just check if propagation type is not None
		final String attributePropagationType = attributeValue.getPropagationType();
		return !X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation.equals(attributePropagationType);
	}

	/**
	 * Propagate attribute's value:
	 * <ul>
	 * <li>UP to it's parent, if <code>desiredPropagationType</code> is {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_BottomUp}
	 * <li>DOWN to it's children, if <code>desiredPropagationType</code> is {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_TopDown}
	 * </ul>
	 * <p>
	 * Note that the method will do nothing if {@link #isPropagable(IAttributeValue)} returns <code>false</code>.
	 *
	 * @param attributeValue         attribute/value to be propagated (see {@link IAttributeValue#getValue()}).
	 * @param desiredPropagationType propagation direction (TopDown, BottomUp)
	 * @param pushNullValue          if true, then don't propagate the actual value of the given <code>attributeValue</code>, but propagate <code>null</code> instead (behave like a rollback)
	 * @see IHUAttributePropagatorFactory#getReversalPropagator(String)
	 */
	private void pushAttributePropagation(final IAttributeValue attributeValue,
										  final String desiredPropagationType,
										  final boolean pushNullValue)
	{
		//
		// Check if attribute is propagable
		if (!isPropagable(attributeValue))
		{
			return;
		}

		final I_M_Attribute attribute = attributeValue.getM_Attribute();

		final IHUAttributePropagator propagator = huAttributePropagatorFactory.getPropagator(desiredPropagationType);

		final Object value;
		if (pushNullValue)
		{
			value = null;
		}
		else
		{
			value = attributeValue.getValue();
		}

		// If we are asked to push null value instead of actual attribute's value (this is the case when we are preparing to remove this HU from it's parent)
		// we shall not allow the propagator to actually change this attribute's value to null because there wasn't such a change
		final boolean updateStorageValue = !pushNullValue;

		final IHUAttributePropagationContext propagationContext = createPropagationContext(attribute, propagator);
		propagationContext.setUpdateStorageValue(updateStorageValue);
		propagator.propagateValue(propagationContext, this, value);
	}

	@Override
	public boolean isReadonlyUI(final IAttributeValueContext ctx, final I_M_Attribute attribute)
	{
		assertNotDisposed();

		if (!attribute.isInstanceAttribute())
		{
			return true; // only instance attributes *might* be read-write
		}

		final IAttributeValueCallout callout = getAttributeValueCallout(attribute);
		if (callout.isAlwaysEditableUI(ctx, this, attribute))
		{
			return false; // never readonly
		}

		// Check if attribute is configured to be readonly
		final IAttributeValue attributeValue = getAttributeValue(attribute);
		if (attributeValue.isReadonlyUI())
		{
			return true;
		}

		//
		// The callout of this attribute value has the right to decide whether or not the attribute shall be readonly
		if (callout.isReadonlyUI(ctx, this, attribute))
		{
			return true;
		}

		// Propagated attributes are readonly
		if (isPropagatedValue(attribute))
		{
			return true;
		}

		// We assume attribute is editable (readonlyUI=false)
		return false;
	}

	@Override
	public boolean isDisplayedUI(final I_M_Attribute attribute, final Set<ProductId> productIds)
	{
		assertNotDisposed();

		final boolean isDisplayFromAttributeSet = productIds.stream().anyMatch(productId -> isDisplayFromAttributeSet(productId, attribute));
		if (!isDisplayFromAttributeSet)
		{
			return false;
		}

		if (!isDisplayFromCallout(attribute))
		{
			return false;
		}

		return getAttributeValue(attribute).isDisplayedUI();
	}

	private boolean isDisplayFromAttributeSet(final ProductId productId, final I_M_Attribute attribute)
	{
		final IAttributeValue attributeValue = getAttributeValue(attribute);

		if (attributeValue.isOnlyIfInProductAttributeSet())
		{
			final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
			return attributesBL.hasAttributeAssigned(productId, attributeId);
		}
		else
		{
			return true;
		}
	}

	private boolean isDisplayFromCallout(final I_M_Attribute attribute)
	{
		final IAttributeValueCallout callout = getAttributeValueCallout(attribute);

		return callout.isDisplayedUI(this, attribute);
	}

	@Override
	public boolean isMandatory(
			@NonNull final I_M_Attribute attribute,
			@NonNull final Set<ProductId> productIds,
			@Nullable final AttributeSourceDocument attributeSourceDocument)
	{
		final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
		if (getAttributeValue(attributeCode).isMandatory())
		{
			return true;
		}

		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
		return productIds.stream().anyMatch(productId -> isMandatoryForAttributeSet(attributeId, productId, attributeSourceDocument));
	}

	private boolean isMandatoryForAttributeSet(
			@NonNull final AttributeId attributeId,
			@NonNull final ProductId productId,
			@Nullable final AttributeSourceDocument attributeSourceDocument)
	{

		if(attributeSourceDocument == null)
		{
			// NOTE: don't check M_Attribute.IsMandatory because
			// we assume IAttributeValue.isMandatory implementation shall check that if it's relevant.
			return false;
		}
		else
		{
			return attributesBL.isMandatoryOn(productId, attributeId, attributeSourceDocument);
		}


	}

	@Nullable
	protected final Object getDefaultAttributeValue(@Nullable final Map<AttributeId, Object> defaultAttributesValue, final AttributeId attributeId)
	{
		if (defaultAttributesValue == null || defaultAttributesValue.isEmpty())
		{
			return null;
		}

		Check.assumeNotNull(attributeId, "attribute not null");
		return defaultAttributesValue.get(attributeId);
	}

	@Override
	public BigDecimal getStorageQtyOrZERO()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public boolean isVirtual()
	{
		return false;
	}

	@Override
	public boolean isNew(final I_M_Attribute attribute)
	{
		assertNotDisposed();

		final IAttributeValue attributeValue = getAttributeValue(attribute);
		return attributeValue.isNew();
	}

	/**
	 * Always returns <code>true</code>.
	 * Override this in your subclass, if it can actually be disposed, which is e.g. the case of <code>HUKeyAttributeStorage</code> in the handlingunits.client project.
	 */
	@Override
	public boolean assertNotDisposed()
	{
		// nothing at this level
		return true; // not disposed
	}

	@Override
	public final boolean assertNotDisposedTree()
	{
		final boolean notDisposed = assertNotDisposed();
		if (!notDisposed)
		{
			return false;
		}

		final boolean loadIfNeeded = false; // only verify children that were already loaded so far
		final Collection<IAttributeStorage> childAttributeStorages = getChildAttributeStorages(loadIfNeeded);

		for (final IAttributeStorage child : childAttributeStorages)
		{
			if (!child.assertNotDisposedTree())
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Utility method for subclasses. To be called if {@link #assertNotDisposed()} finds that the storage was disposed.
	 */
	protected void throwOrLogDisposedException(final Long disposedTS)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Accessing an already disposed AttributeStorage shall not be allowed");
		message.append("\n Storage: " + this);
		message.append("\n Disposed on: " + (disposedTS != null && disposedTS > 0 ? new Date(disposedTS).toString() : "<time unknown>"));

		int counter = 1;
		message.append("\n Direct children: ");
		for (final IAttributeStorage child : getChildAttributeStorages(false))
		{
			message.append("\n\t " + counter + ": " + child);
		}

		counter = 1;
		message.append("\n Parents: ");
		IAttributeStorage parentAttributeStorage = getParentAttributeStorage();

		while (parentAttributeStorage != null && !NullAttributeStorage.isNull(parentAttributeStorage))
		{
			message.append("\n\t " + counter + ": " + parentAttributeStorage);
			parentAttributeStorage = parentAttributeStorage.getParentAttributeStorage();
		}

		final HUException ex = new HUException(message.toString());
		if (HUConstants.isAttributeStorageFailOnDisposed())
		{
			throw ex;
		}
		else
		{
			logger.warn(ex.getLocalizedMessage(), ex);
		}
	}

	/**
	 * Fires {@link IAttributeStorageListener#onAttributeStorageDisposed(IAttributeStorage)} event.
	 * <p>
	 * Please make sure you are calling this method BEFORE clearing the listeners.
	 */
	protected final void fireAttributeStorageDisposed()
	{
		listeners.onAttributeStorageDisposed(this);
	}

	@Nullable
	@Override
	public AttributeValueId getAttributeValueIdOrNull(final AttributeCode attributeCode)
	{
		final I_M_Attribute attribute = getAttributeByValueKeyOrNull(attributeCode);
		if (attribute == null)
		{
			return null;
		}

		final String value = getValueAsStringOrNull(attributeCode);
		if (value == null)
		{
			return null;
		}

		return Optional.ofNullable(attributesBL.retrieveAttributeValueOrNull(attribute, value))
				.map(AttributeListValue::getId)
				.orElse(null);
	}

	@ToString
	private static final class IndexedAttributeValues
	{
		public static final IndexedAttributeValues NULL = new IndexedAttributeValues();

		public static IndexedAttributeValues of(final List<IAttributeValue> attributeValues)
		{
			if (attributeValues == null || attributeValues.isEmpty())
			{
				return NULL;
			}
			return new IndexedAttributeValues(attributeValues);
		}

		private final ImmutableList<IAttributeValue> attributeValues;
		private final ImmutableMap<AttributeId, I_M_Attribute> attributesById;
		private final ImmutableMap<AttributeCode, I_M_Attribute> attributesByCode;
		private final ImmutableMap<AttributeCode, IAttributeValue> attributeValuesByCode;

		/**
		 * Null constructor
		 */
		private IndexedAttributeValues()
		{
			attributeValues = ImmutableList.of();
			attributesById = ImmutableMap.of();
			attributesByCode = ImmutableMap.of();
			attributeValuesByCode = ImmutableMap.of();
		}

		private IndexedAttributeValues(final List<IAttributeValue> attributeValues)
		{
			final ArrayList<IAttributeValue> attributeValuesList = new ArrayList<>(attributeValues);
			attributeValuesList.sort(AbstractAttributeStorage.attributeValueSortBySeqNo);

			final Map<AttributeId, I_M_Attribute> attributeId2attribute = new HashMap<>(attributeValuesList.size());
			final Map<AttributeCode, I_M_Attribute> attributeKey2attribute = new HashMap<>(attributeValuesList.size());
			final Map<AttributeCode, IAttributeValue> attributeKey2attributeValue = new HashMap<>(attributeValuesList.size());
			for (final IAttributeValue attributeValue : attributeValuesList)
			{
				final I_M_Attribute attribute = attributeValue.getM_Attribute();
				final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
				final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());

				attributeId2attribute.put(attributeId, attribute);
				attributeKey2attribute.put(attributeCode, attribute);
				attributeKey2attributeValue.put(attributeCode, attributeValue);
			}

			this.attributeValues = ImmutableList.copyOf(attributeValuesList);
			attributesById = ImmutableMap.copyOf(attributeId2attribute);
			attributesByCode = ImmutableMap.copyOf(attributeKey2attribute);
			attributeValuesByCode = ImmutableMap.copyOf(attributeKey2attributeValue);
		}

		public void addAttributeValueListener(final IAttributeValueListener attributeValueListener)
		{
			for (final IAttributeValue attributeValue : attributeValues)
			{
				attributeValue.addAttributeValueListener(attributeValueListener);
			}
		}

		public List<IAttributeValue> getAttributeValues()
		{
			return attributeValues;
		}

		public IAttributeValue getAttributeValueOrNull(final AttributeCode attributeCode)
		{
			return attributeValuesByCode.get(attributeCode);
		}

		public boolean hasAttribute(final AttributeCode attributeCode)
		{
			return attributesByCode.containsKey(attributeCode);
		}

		public boolean hasAttribute(final AttributeId attributeId)
		{
			return attributesById.containsKey(attributeId);
		}

		/**
		 * @return this storage's attributes in the order in which they were added to the storage {@link de.metas.handlingunits.attribute.impl.HUAttributesBySeqNoComparator}.
		 */
		public ImmutableList<I_M_Attribute> getAttributes()
		{
			final ImmutableList.Builder<I_M_Attribute> result = ImmutableList.builder();
			for(final IAttributeValue attributeValue:attributeValues)
			{
				result.add(attributeValue.getM_Attribute());
			}
			return result.build();
		}

		public I_M_Attribute getAttributeOrNull(final AttributeId attributeId)
		{
			return attributesById.get(attributeId);
		}

		public boolean hasAttributes()
		{
			return !attributesByCode.isEmpty();
		}

		public I_M_Attribute getAttributeByCodeOrNull(final AttributeCode attributeCode)
		{
			return attributesByCode.get(attributeCode);
		}

		public boolean isNull()
		{
			return this == NULL;
		}

	}
}
