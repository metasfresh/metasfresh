/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.util.AbstractPropertiesProxy;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.concurrent.InstantFuture;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.Util.ArrayKey;
import org.compiere.util.ValueNamePair;

/**
 * An intelligent MutableComboBoxModel, which determines what can be cached.
 *
 * <pre>
 *      Validated   - SQL is final / not dynamic
 *      AllLoaded   - All Records are loaded
 *
 * 	Get Info about Lookup
 * 	-	SQL
 * 	-	KeyColumn
 * 	-	Zoom Target
 * </pre>
 *
 * @author Jorg Janke
 * @version $Id: MLookup.java,v 1.4 2006/10/07 00:58:57 jjanke Exp $
 */
public final class MLookup extends Lookup implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5784044288965615466L;

	public static interface ILookupData
	{
		ArrayKey getValidationKey();

		boolean hasInactiveValues();

		boolean isAllLoaded();

		boolean isDirty();

		// Map<String, NamePair> getValues();
		boolean containsKey(final Object key);

		NamePair getByKey(final Object key);

		Collection<NamePair> getValues();

		int size();
	}

	private static class EmptyLookupData implements ILookupData
	{
		private final ArrayKey validationKey;
		private final boolean dirty;
		private final boolean allLoaded;

		public EmptyLookupData(final ArrayKey validationKey, final boolean dirty, final boolean allLoaded)
		{
			super();
			this.validationKey = validationKey;
			this.dirty = dirty;
			this.allLoaded = allLoaded;
		}

		@Override
		public ArrayKey getValidationKey()
		{
			return validationKey;
		}

		@Override
		public boolean isDirty()
		{
			return dirty;
		}

		@Override
		public boolean isAllLoaded()
		{
			return allLoaded;
		}

		@Override
		public boolean hasInactiveValues()
		{
			return false;
		}

		@Override
		public boolean containsKey(Object key)
		{
			return false;
		}

		@Override
		public NamePair getByKey(Object key)
		{
			return null;
		}

		@Override
		public Collection<NamePair> getValues()
		{
			return Collections.emptyList();
		}

		@Override
		public int size()
		{
			return 0;
		}
	};

	private static final ILookupData LOOKUPDATA_NOTINITIALIZED = new EmptyLookupData(null, true, false); // dirty, not all loaded
	private static final ILookupData LOOKUPDATA_HIGHVOLUME = new EmptyLookupData(null, false, false);

	/**
	 * MLookup Constructor
	 *
	 * @param info info
	 * @param TabNo tab no
	 */
	public MLookup(final Properties ctx, final int adColumnId, final MLookupInfo info, final int TabNo)
	{
		super(info.getDisplayType(), info.getWindowNo());
		this.ctx = ctx;
		this.adColumnId = adColumnId <= 0 ? -1 : adColumnId;
		m_info = info;

		//
		// Setup validation context
		final AbstractPropertiesProxy ctxDelegate = new AbstractPropertiesProxy()
		{
			private static final long serialVersionUID = 5733045203747077282L;

			@Override
			public Properties getDelegate()
			{
				// final Properties parentCtx = getLookupInfo().getCtx();
				// final int currentRow = Env.getContextAsInt(parentCtx, getWindowNo(), TabNo, GridTab.CTX_CurrentRow, true);
				// new GridRowCtx

				return getCtx();
			}
		};
		m_evalCtx = Services.get(IValidationRuleFactory.class).createValidationContext(ctxDelegate, info.getWindowNo(), TabNo, info.getTableName()); // metas
		log.debug(m_info.getKeyColumn());

		// // load into local lookup, if already cached
		// if (MLookupCache.loadFromCache(m_info, m_lookup))
		// return;

		// Don't load Search or CreatedBy/UpdatedBy
		if (m_info.getDisplayType() == DisplayType.Search || m_info.isCreadedUpdatedBy())
		{
			return;
		}
	}

	/** Inactive Marker Start */
	public static final String INACTIVE_S = "~";
	/** Inactive Marker End */
	public static final String INACTIVE_E = "~";
	/** Not Found Marker Start */
	public static final String NOTFOUND_S = "<";
	/** Not Found Marker End */
	public static final String NOTFOUND_E = ">";
	/** Number of max rows to load */
	public static final int MAX_ROWS = 10000;
	/** Indicator for Null */
	private static Integer MINUS_ONE = new Integer(-1);

	private Properties ctx;
	private final int adColumnId;
	/** The Lookup Info Value Object */
	private MLookupInfo m_info;
	private final IValidationContext m_evalCtx; // metas

	/* Refreshing - disable cashing */
	private boolean m_refreshing = false;
	/** Next Read for Parent */
	private long m_nextRead = 0;
	
	private Properties getCtx()
	{
		return ctx;
	}
	
	public void setCtx(final Properties ctx)
	{
		Check.assumeNotNull(ctx, "ctxNew not null");
		this.ctx = ctx;
	}

	public boolean isHighVolume()
	{
		return m_info.getDisplayType() == DisplayType.Search || m_info.isCreadedUpdatedBy();
	}

	/**
	 * Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_info != null)
			log.debug("Disposing: {}", m_info.getKeyColumn());

		interruptLoading();
		//
		clear();
		// m_lookup = null;
		m_lookupDirect = null;
		//
		m_info = null;
		//
		super.dispose();
	}   // dispose

	private Future<ILookupData> _futureLookupData = null;
	private static final Future<ILookupData> FUTURELOOKUPDATA_HIGHVOLUME = new InstantFuture<ILookupData>(LOOKUPDATA_HIGHVOLUME);

	private void interruptLoading()
	{
		if (_futureLookupData == null)
		{
			return;
		}
		if (_futureLookupData.isDone())
		{
			return;
		}
		_futureLookupData.cancel(true);
	}

	/**
	 * Wait until async Load Complete
	 */
	@Override
	public void loadComplete()
	{
		getLookupData(LookupDataState.NEW_IF_LOADING);
	}

	private enum FutureLookupDataState
	{
		CURRENT_OR_NULL,
		CURRENT_OR_NEW,
		NEW,
	};

	private Future<ILookupData> getFutureLookupData(final FutureLookupDataState state)
	{
		final boolean requery;
		if (state == FutureLookupDataState.CURRENT_OR_NULL)
		{
			return _futureLookupData;
		}
		else if (state == FutureLookupDataState.CURRENT_OR_NEW)
		{
			requery = false;
		}
		else if (state == FutureLookupDataState.NEW)
		{
			requery = true;
		}
		else
		{
			throw new IllegalArgumentException("state not supported: " + state);
		}

		if (_futureLookupData != null && _futureLookupData.isDone())
		{
			_futureLookupData = null;
		}

		if (_futureLookupData != null && requery)
		{
			_futureLookupData.cancel(true); // mayInterruptIfRunning=true
			_futureLookupData = null;
		}

		if (_futureLookupData == null)
		{
			if (isHighVolume())
			{
				_futureLookupData = FUTURELOOKUPDATA_HIGHVOLUME;
			}
			else
			{
				final MLookupLoader loader = new MLookupLoader(getValidationContext(), m_info);
				_futureLookupData = Services.get(ITaskExecutorService.class)
						.submit(loader, MLookup.class.getSimpleName());
			}
		}

		return _futureLookupData;
	}

	private static enum LookupDataState
	{
		CURRENT,
		NEW_IF_LOADING,
		NEW,
		ALWAYS_NEW,
	};

	private ILookupData getCurrentLookupData()
	{
		return getLookupData(LookupDataState.CURRENT);
	}

	private ILookupData _lookupData = LOOKUPDATA_NOTINITIALIZED;

	/**
	 * Retrieves {@link ILookupData}. Never return null
	 *
	 * @param state
	 * @return lookup data
	 */
	private final ILookupData getLookupData(final LookupDataState state)
	{
		final FutureLookupDataState futureLookupDataState;
		if (state == LookupDataState.CURRENT)
		{
			return _lookupData;
		}
		else if (state == LookupDataState.NEW_IF_LOADING)
		{
			futureLookupDataState = FutureLookupDataState.CURRENT_OR_NULL;
		}
		else if (state == LookupDataState.NEW)
		{
			futureLookupDataState = FutureLookupDataState.CURRENT_OR_NEW;
		}
		else if (state == LookupDataState.ALWAYS_NEW)
		{
			futureLookupDataState = FutureLookupDataState.NEW;
		}
		else
		{
			throw new IllegalArgumentException("state not supported: " + state);
		}

		final Future<ILookupData> future = getFutureLookupData(futureLookupDataState);
		if (future == null)
		{
			return _lookupData;
		}

		try
		{
			_lookupData = future.get();
		}
		catch (InterruptedException e)
		{
			log.warn("Interrupted while loading the lookup data: " + this, e);
		}
		catch (ExecutionException e)
		{
			log.warn("Failed loading the lookup data: " + this, e);

			// If our "future" is exactly the cached "future", get rid of it
			// because else it will throw the same exception over and over again
			// and our loading will never progress...
			if (future == _futureLookupData)
			{
				_futureLookupData = null;
			}

		}

		return _lookupData;
	}

	/**
	 * Get value (name) for key. If not found return null;
	 *
	 * @param key key (Integer for Keys or String for Lists)
	 * @return value
	 */
	@Override
	public NamePair get(final IValidationContext validationCtx, final Object key)
	{
		if (key == null || MINUS_ONE.equals(key))	// indicator for null
		{
			return null;
		}

		// auto refresh parent lookup
		if (m_info.isParent())
		{
			if (m_nextRead > 0 && m_nextRead < System.currentTimeMillis())
			{
				clear();
			}
			m_nextRead = System.currentTimeMillis() + 5000;	// 5 sec
		}

		final boolean checkCacheOnly = IValidationContext.CACHED == validationCtx;

		// Try cache
		final ILookupData dataCurrent = getLookupData(LookupDataState.CURRENT);
		final boolean dataCurrentValidated = checkCacheOnly || isValidated(validationCtx, dataCurrent);
		if (dataCurrentValidated)
		{
			final NamePair value = dataCurrent.getByKey(key);
			if (value != null)
			{
				return value;
			}
		}

		// Not found and waiting for loader
		final ILookupData dataNew = getLookupData(LookupDataState.NEW_IF_LOADING);
		if (dataCurrent != dataNew && (checkCacheOnly || isValidated(validationCtx, dataCurrent)))
		{
			// is most current
			final NamePair valueNew = dataNew.getByKey(key);
			if (valueNew != null)
			{
				return valueNew;
			}
		}
		else
		{
			// Key was not found in current Lookup Data and there was not loading a new one
			// Fine, but if it's an immutable lookup (i.e. result is not depending on external parameters)
			// then, this time we will do a direct lookup, but we will also trigger a lookup loading for next getters
			final boolean immutable = m_info.getValidationRule().isImmutable();
			if (immutable
					&& !isHighVolume()
					// Don't load it if it's flagged as parent because in most of the cases our parent link columns
					// are defined as TableDir and no validation rules,
					// which will imply loading a huge amount of data.
					&& !m_info.isParent()
					)
			{
				getFutureLookupData(FutureLookupDataState.NEW);
			}
		}

		// We were asked only for current cached value, and nothing found
		if (checkCacheOnly)
		{
			return null;
		}

		// Try to get it directly
		final boolean cacheLocal = m_info.getValidationRule().isImmutable();
		final boolean saveInCache = IValidationContext.DISABLED != validationCtx;
		return getDirect(validationCtx, key, saveInCache, cacheLocal);
		// metas end
	}	// get

	/**
	 * Get Display value (name). If not found return key embedded in inactive signs.
	 *
	 * @param key key
	 * @return value
	 */
	@Override
	public String getDisplay(final IValidationContext evalCtx, final Object key)
	{
		if (key == null)
			return "";
		//
		final Object display = get(evalCtx, key);
		if (display == null)
		{
			return NOTFOUND_S + key.toString() + NOTFOUND_E;
		}
		return display.toString();
	}	// getDisplay

	@Override
	public boolean isNotFoundDisplayValue(final String display)
	{
		if (display == null)
		{
			return true;
		}

		return display.startsWith(NOTFOUND_S) && display.endsWith(NOTFOUND_E);
	}

	/**
	 * The Lookup contains the key
	 *
	 * @param key key
	 * @return true if key is known
	 */
	@Override
	public boolean containsKey(final IValidationContext evalCtx, final Object key)
	{
		// should check direct too
		final ILookupData data = getCurrentLookupData();
		if (data.containsKey(key))
		{
			return true;
		}
		else
		{
			// metas: tsa: always check values directly because even if m_lookup is not empty that does not mean the m_lookup was fully populated
			// (e.g. an getDirect call was issued because and now we have one element in our Map)
			// if (m_lookup.size() > 0)
			// return false;
			// else

			return (get(evalCtx, key) != null);
		}
	}   // containsKey

	/**
	 * @return a string representation of the object.
	 */
	@Override
	public String toString()
	{
		return "MLookup[" + m_info.getKeyColumn()
				+ ",Size=" + size()
				// // metas: check only the flag and do not call isValidated() method because that method tries to parse the validation code
				// // which is time consuming when we try to render the grid because java.awt.Container.mixOnShowing() tries to log it
				// + ",Validated=" + (m_info != null && m_info.IsValidated)
				+ "-" + m_info.getValidationRule()
				+ "]";
	}	// toString

	/**
	 * Indicates whether some other object is "equal to" this one.
	 *
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> if this object is the same as the obj argument; <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(!(obj instanceof MLookup))
		{
			return false;
		}

		final MLookup other = (MLookup)obj;
		return adColumnId == other.adColumnId;
	}

	/**
	 * Return Size
	 *
	 * @return size
	 */
	public int size()
	{
		return getCurrentLookupData().size();
	}	// size

	/**
	 * Is it all loaded
	 *
	 * @return true, if all loaded
	 */
	public boolean isAllLoaded()
	{
		return getCurrentLookupData().isAllLoaded();
	}	// isAllLoaded

	/**
	 * Is the List fully Validated
	 *
	 * @return true, if validated
	 */
	@Override
	public boolean isValidated()
	{
		final ILookupData data = getCurrentLookupData();
		return isValidated(getValidationContext(), data);
	}

	public boolean isValidated(IValidationContext validationCtx, final ILookupData data)
	{
		if (m_info == null)
		{
			return false;
		}
		if (data.isDirty())
		{
			return false;
		}

		final IValidationRule validationRule = m_info.getValidationRule();
		if (validationRule.isImmutable())
		{
			return true;
		}

		final ArrayKey dataValidationKey = data.getValidationKey();
		if (dataValidationKey == null)
		{
			return false;
		}

		if (validationCtx == IValidationContext.DISABLED)
		{
			return true;
		}

		final Object parentValidationKey = Services.get(ILookupDAO.class).createValidationKey(validationCtx, m_info);
		final ArrayKey validationKeyNow = createValidationKey(validationCtx, m_info, parentValidationKey);
		if (!Check.equals(dataValidationKey, validationKeyNow))
		{
			return false;
		}

		// final IValidationRule validationRule = m_info.getValidationRule();
		// return !validationRule.isValidationRequired(m_evalCtx);

		return true;
	}

	/**
	 * Get Validation SQL
	 *
	 * @return Validation SQL
	 */
	@Override
	public String getValidation()
	{
		final IValidationRule validationRule = m_info.getValidationRule();
		final IStringExpression prefilterWhereClause = validationRule.getPrefilterWhereClause();
		final String validation = prefilterWhereClause.evaluate(m_evalCtx, OnVariableNotFound.ReturnNoResult);
		if(prefilterWhereClause.isNoResult(validation))
		{
			return "";
		}
		
		return validation;
	}   // getValidation

	/**
	 * Get Reference Value
	 *
	 * @return Reference Value
	 */
	public int getAD_Reference_Value_ID()
	{
		return m_info.getAD_Reference_Value_ID();
	}   // getAD_Reference_Value_ID

	/**
	 * Has inactive elements in list
	 *
	 * @return true, if list contains inactive values
	 */
	@Override
	public boolean hasInactive()
	{
		return getCurrentLookupData().hasInactiveValues();
	}   // hasInactive

	/**
	 * Return info as ArrayList containing Value/KeyNamePair
	 *
	 * @param onlyValidated only validated
	 * @param loadParent get Data even for parent lookups
	 * @return List
	 */
	private ILookupData getData(final boolean onlyValidated, final boolean loadParent)
	{
		final IValidationContext validationCtx = getValidationContext();

		// Never Loaded (correctly)
		ILookupData data = getLookupData(LookupDataState.NEW_IF_LOADING);
		final boolean validated = isValidated(validationCtx, data);
		if (onlyValidated && !validated)
		{
			data = getLookupData(LookupDataState.ALWAYS_NEW);
		}

		return data;
	}	// getData

	/**
	 * Return data as Array containing Value/KeyNamePair
	 *
	 * @param mandatory if not mandatory, an additional empty value is inserted
	 * @param onlyValidated only validated
	 * @param onlyActive only active
	 * @param temporary force load for temporary display
	 * @return list
	 */
	@Override
	public List<Object> getData(boolean mandatory, boolean onlyValidated, boolean onlyActive, boolean temporary)
	{
		// create list
		final ILookupData data = getData(onlyValidated, true); // loadParent=true

		final List<Object> list = new ArrayList<Object>(data.getValues());

		// Remove inactive values
		if (onlyActive && data.hasInactiveValues())
		{
			for (final Iterator<Object> it = list.iterator(); it.hasNext();)
			{
				final Object o = it.next();
				if (o != null)
				{
					String s = o.toString();
					if (s.startsWith(INACTIVE_S) && s.endsWith(INACTIVE_E))
					{
						it.remove();
					}
				}
			}
		}

		// Add Optional (empty) selection
		if (!mandatory)
		{
			final NamePair emptyItem;
			if (m_info.getKeyColumnFQ() != null && isNumbericKey())
			{
				emptyItem = KeyNamePair.EMPTY;
			}
			else
			{
				emptyItem = ValueNamePair.EMPTY;
			}
			list.add(0, emptyItem);
		}

		return list;
	}	// getData

	/** Save getDirect last return value */
	private Map<Object, NamePair> m_lookupDirect = null;
	private Map<Object, NamePair> m_lookupDirectContextCache = null;

	/**
	 * Get Data Direct from Table.
	 *
	 * @param evalCtx if {@code null} or {@link IValidationContext#NULL}, then this lookup's own {@code m_evalCtx} is used.
	 * @param key key
	 * @param saveInCache save in cache for r/w
	 * @param cacheLocal cache locally for r/o
	 * @return value
	 */
	@Override
	public NamePair getDirect(final IValidationContext evalCtx, final Object key, final boolean saveInCache, final boolean cacheLocal)
	{
		final IValidationContext evalCtxToUse;
		if (evalCtx == null || IValidationContext.NULL == evalCtx)
		{
			evalCtxToUse = getValidationContext();
		}
		else
		{
			evalCtxToUse = evalCtx;
		}

		// Nothing to query
		if (key == null)
		{
			return null;
		}

		//
		// Check lookup direct cache
		if (m_lookupDirect != null)		// Lookup cache
		{
			final NamePair directValue = m_lookupDirect.get(key);
			if (directValue != null)
			{
				return directValue;
			}
		}

		final boolean useContextCache = true;
		final Object contextCacheKey;
		if (useContextCache)
		{
			contextCacheKey = createValidationKey(evalCtxToUse, getLookupInfo(), key);
			if (m_lookupDirectContextCache != null)
			{
				if (m_lookupDirectContextCache.containsKey(contextCacheKey))
				{
					return m_lookupDirectContextCache.get(contextCacheKey);
				}

				// FIXME: here maybe we can implement some algo to expurge old values
			}
		}
		else
		{
			contextCacheKey = null;
		}

		NamePair directValue = null;
		try
		{
			directValue = Services.get(ILookupDAO.class).retrieveLookupValue(evalCtxToUse, m_info, key);
		}
		catch (Exception e)
		{
			log.error("Error getting direct value for key=" + key + " on " + m_info, e);
			directValue = null;
		}

		log.debug("{} - Direct value: {}", new Object[] { m_info, directValue });

		// Cache to R/W cache (m_lookup)
		if (saveInCache && IValidationContext.DISABLED != evalCtx && directValue != null)
		{
			// FIXME: shall we add direct values to lookup data?
			// m_lookup.put(directValue.getID(), directValue);
			// m_hasInactive = true;
		}

		// Cache Local
		if (cacheLocal && directValue != null)
		{
			if (m_lookupDirect == null)
				m_lookupDirect = new HashMap<Object, NamePair>();
			m_lookupDirect.put(key, directValue);
		}

		if (contextCacheKey != null)
		{
			if (m_lookupDirectContextCache == null)
			{
				// FIXME: please note this is a HUGE memory consumer because nobody is reseting this cache
				m_lookupDirectContextCache = new LinkedHashMap<Object, NamePair>();
			}
			m_lookupDirectContextCache.put(contextCacheKey, directValue);
		}

		return directValue;
	}	// getDirect

	/**
	 * Get Zoom
	 *
	 * @return Zoom AD_Window_ID
	 */
	@Override
	public int getZoom()
	{
		return m_info.ZoomWindow;
	}	// getZoom

	/**
	 * Get Zoom
	 *
	 * @param query query
	 * @return Zoom Window
	 */
	@Override
	public int getZoom(MQuery query)
	{
		// Case: there is no ZoomWindowPO or query is null
		// => return m_info.ZoomWindow directly because there is no point to search forward
		if (m_info.ZoomWindowPO <= 0 || query == null)
		{
			return m_info.ZoomWindow;
		}

		// Need to check SO/PO
		final boolean isSOTrx = DB.isSOTrx(m_info.getTableName(), query.getWhereClause(false));
		//
		if (!isSOTrx)
		{
			return m_info.ZoomWindowPO;
		}
		return m_info.ZoomWindow;
	}	// getZoom

	/**
	 * Get Zoom Query String
	 *
	 * @return Zoom SQL Where Clause
	 */
	@Override
	public MQuery getZoomQuery()
	{
		if (m_info == null)
		{
			return null;
		}
		return m_info.getZoomQuery();
	}	// getZoom

	/**
	 * Get underlying fully qualified Table.Column Name
	 *
	 * @return Key Column
	 */
	@Override
	public String getColumnName()
	{
		return m_info.getKeyColumnFQ();
	}	// g2etColumnName

	@Override
	public String getColumnNameNotFQ()
	{
		return m_info.getKeyColumn();
	}

	/**
	 * Refresh & return number of items read. Get get data of parent lookups
	 *
	 * @return no of items read
	 */
	@Override
	public int refresh()
	{
		if (m_refreshing)
			return 0;
		return refresh(true);
	}	// refresh

	/**
	 * Refresh & return number of items read
	 *
	 * @param loadParent get data of parent lookups
	 * @return no of items refresh
	 */
	private int refresh(boolean loadParent)
	{
		if (m_refreshing)
		{
			return 0;
		}

		if (!loadParent && m_info.isParent())
			return 0;

		// Don't load Search or CreatedBy/UpdatedBy
		if (isHighVolume())
		{
			// clear direct cache
			removeAllElements();
			return 0;
		}

		m_refreshing = true;
		try
		{
			// Cancel any existing future values and create a new one
			// This will ensure that we will have the latest values in our combobox when we do a refresh
			getFutureLookupData(FutureLookupDataState.NEW);

			// Populate our underlying Combobox model
			// NOTE: this one will trigger getData method
			fillComboBox(isMandatory(), true, true, false); // onlyValidated=true, onlyActive=true, temporary=false
		}
		finally
		{
			m_refreshing = false;
		}

		final ILookupData data = getCurrentLookupData();
		return data.size();
	}	// refresh

	/**
	 * Remove All cached Elements
	 *
	 * @see org.compiere.model.Lookup#removeAllElements()
	 */
	@Override
	public void removeAllElements()
	{
		super.removeAllElements();
		clear();
	}	// removeAllElements

	private void clear()
	{
		// if (m_lookup != null)
		// {
		// m_lookup.clear();
		// }
		if (m_lookupDirect != null)
		{
			m_lookupDirect.clear();
		}
	}

	@Override
	public String getInfoFactoryClass()
	{
		return m_info.InfoFactoryClass != null ? m_info.InfoFactoryClass : "";
	}

	static ArrayKey createValidationKey(final IValidationContext validationCtx, final MLookupInfo lookupInfo, final Object parentValidationKey)
	{
		final List<Object> keys = new ArrayList<Object>();

		// final String rowIndexStr = validationCtx.get_ValueAsString(GridTab.CTX_CurrentRow);
		// keys.add(rowIndexStr);

		if (IValidationContext.NULL != validationCtx)
		{
			for (final String parameterName : lookupInfo.getValidationRule().getAllParameters())
			{
				final String parameterValue = validationCtx.get_ValueAsString(parameterName);
				keys.add(parameterName);
				keys.add(parameterValue);
			}
		}

		keys.add(parentValidationKey);

		return new ArrayKey(keys.toArray());
	}

	// metas: begin
	@Override
	public String getTableName()
	{
		return m_info.getTableName();
	}

	@Override
	public boolean isAutoComplete()
	{
		return m_info != null && m_info.isAutoComplete();
	}

	public MLookupInfo getLookupInfo()
	{
		return m_info;
	}

	@Override
	public Set<String> getParameters()
	{
		return m_info.getValidationRule().getAllParameters();
	}

	@Override
	public IValidationContext getValidationContext()
	{
		return m_evalCtx;
	}

	public boolean isNumbericKey()
	{
		final boolean isNumeric = getColumnName().endsWith("_ID");
		return isNumeric;
	}

	@Override
	public NamePair suggestValidValue(final NamePair value)
	{
		return null;
	}

}	// MLookup
