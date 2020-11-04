package de.metas.handlingunits.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.impl.HUAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

/* package */class MutableHUContext implements IMutableHUContext
{
	private final Properties ctx;

	@Nullable private String trxName;

	private Map<String, Object> _contextProperties = new HashMap<>();

	private IHUStorageFactory huStorageFactory = null;
	private IAttributeStorageFactory _attributesStorageFactory = null;
	private boolean _attributesStorageFactoryInitialized = false;
	private ZonedDateTime date = null;
	private CompositeHUTrxListener _trxListeners = null;

	final IHUContext huCtx = null; // task 07734: we don't want to track M_MaterialTrackings, so we don't need to provide a HU context.
	private IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> _huPackingMaterialsCollector = new HUPackingMaterialsCollector(null);

	private final List<EmptyHUListener> emptyHUListeners = new ArrayList<>();

	public MutableHUContext(@NonNull final Object contextProvider)
	{
		final IContextAware contextAware = InterfaceWrapperHelper.getContextAware(contextProvider);
		ctx = contextAware.getCtx();
		trxName = contextAware.getTrxName();
		date = DateTrxProvider.getDateTrx();
	}

	public MutableHUContext(final Properties ctx)
	{
		this(ctx, ITrx.TRXNAME_None);
	}

	public MutableHUContext(@NonNull final Properties ctx, @Nullable final String trxName)
	{
		this.ctx = ctx;

		this.trxName = trxName;
		date = SystemTime.asZonedDateTime();
	}

	@Override
	public String toString()
	{
		return "MutableHUContext ["
				+ "trxName=" + trxName
				+ ", huStorageFactory=" + huStorageFactory
				+ ", attributesStorageFactory=" + _attributesStorageFactory
				+ ", date=" + getDate()
				+ ", ctx=" + ctx
				+ "]";
	}

	@Override
	public Object setProperty(final String propertyName, final Object value)
	{
		return _contextProperties.put(propertyName, value);
	}

	@Override
	public <T> T getProperty(final String propertyName)
	{
		@SuppressWarnings("unchecked")
		final T propertyValue = (T)_contextProperties.get(propertyName);
		return propertyValue;
	}

	private final void setProperties(final Map<String, Object> contextProperties)
	{
		_contextProperties = contextProperties;
	}

	private final Map<String, Object> getProperties()
	{
		return _contextProperties;
	}

	@Override
	public IMutableHUContext copyAsMutable()
	{
		final MutableHUContext huContextCopy = new MutableHUContext(getCtx());
		huContextCopy.setTrxName(getTrxName());

		huContextCopy.setProperties(getProperties()); // copy properties from old context

		huContextCopy.setHUStorageFactory(getHUStorageFactory());
		huContextCopy.setHUAttributeStorageFactory(getHUAttributeStorageFactory());
		huContextCopy.setDate(getDate());
		huContextCopy.setHUPackingMaterialsCollector(_huPackingMaterialsCollector.copy());
		huContextCopy._trxListeners = getTrxListeners().copy(); // using the getter to make sure they are loaded

		emptyHUListeners.forEach(l -> huContextCopy.addEmptyHUListener(l));

		return huContextCopy;
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public String getTrxName()
	{
		return trxName;
	}

	@Override
	public void setTrxName(final String trxName)
	{
		this.trxName = trxName;
	}

	@Override
	public void setDate(@NonNull final ZonedDateTime date)
	{
		this.date = date;
	}

	@Override
	public ZonedDateTime getDate()
	{
		return date;
	}

	@Override
	public IHUStorageFactory getHUStorageFactory()
	{
		if (huStorageFactory == null)
		{
			huStorageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();

			//
			// Intercept HU Storage changes and forward the event to IAttributeStorage
			//
			// NOTEs:
			// * 06952: ts: commented it out, because the performance is prohibitive
			// * tsa: it seems it is also introducing a loop which prevents garbage collecting
			//
			// huStorageFactory.addHUStorageListener(new IHUStorageListener()
			// {
			// @Override
			// public void onQtyChanged(final HUStorageChangeEvent event)
			// {
			// final I_M_HU hu = event.getHUStorage().getM_HU();
			// final IAttributeStorage attributeStorage = getHUAttributeStorageFactory().getAttributeStorage(hu);
			// if (attributeStorage instanceof IHUStorageListener)
			// {
			// final IHUStorageListener delegate = (IHUStorageListener)attributeStorage;
			// delegate.onQtyChanged(event);
			// }
			// }
			// });
		}
		return huStorageFactory;
	}

	@Override
	public void setHUStorageFactory(final IHUStorageFactory huStorageFactory)
	{
		Check.assumeNotNull(huStorageFactory, "huStorageFactory not null");
		this.huStorageFactory = huStorageFactory;
	}

	@Override
	public IAttributeStorageFactory getHUAttributeStorageFactory()
	{
		if (_attributesStorageFactoryInitialized)
		{
			// shall not be null at this point
			return _attributesStorageFactory;
		}

		if (_attributesStorageFactory == null)
		{
			_attributesStorageFactory = Services.get(IAttributeStorageFactoryService.class)
					.prepareHUAttributeStorageFactory(HUAttributesDAO.instance);
		}

		final IHUStorageFactory huStorageFactory = getHUStorageFactory();
		_attributesStorageFactory.setHUStorageFactory(huStorageFactory);
		_attributesStorageFactoryInitialized = true;

		return _attributesStorageFactory;
	}

	@Override
	public void setHUAttributeStorageFactory(final IAttributeStorageFactory attributesStorageFactory)
	{
		Check.assumeNotNull(attributesStorageFactory, "attributesStorageFactory not null");
		Check.assume(!_attributesStorageFactoryInitialized, "attributesStorageFactory not already initialized");
		_attributesStorageFactory = attributesStorageFactory;
	}

	@Override
	public IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> getHUPackingMaterialsCollector()
	{

		return _huPackingMaterialsCollector;
	}

	@Override
	public IMutableHUContext setHUPackingMaterialsCollector(@NonNull final IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> huPackingMaterialsCollector)
	{
		this._huPackingMaterialsCollector = huPackingMaterialsCollector;
		return this;
	}

	@Override
	public CompositeHUTrxListener getTrxListeners()
	{
		if (_trxListeners == null)
		{
			final CompositeHUTrxListener trxListeners = new CompositeHUTrxListener();

			// Add system registered listeners
			final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
			trxListeners.addListeners(huTrxBL.getHUTrxListenersList());

			_trxListeners = trxListeners;
		}
		return _trxListeners;
	}

	@Override
	public void addEmptyHUListener(@NonNull final EmptyHUListener emptyHUListener)
	{
		emptyHUListeners.add(emptyHUListener);
	}

	@Override
	public List<EmptyHUListener> getEmptyHUListeners()
	{
		return ImmutableList.copyOf(emptyHUListeners);
	}
}
