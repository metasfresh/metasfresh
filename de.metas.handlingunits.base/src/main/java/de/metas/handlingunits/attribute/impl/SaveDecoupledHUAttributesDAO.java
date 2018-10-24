package de.metas.handlingunits.attribute.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.PIAttributes;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * {@link IHUAttributesDAO} implementation which acts like a save buffer:
 * <ul>
 * <li>automatically loads attributes from underlying {@link IHUAttributesDAO}, if they does not already exist in our local cache
 * <li>on save, instead of directly saving them we are just adding them to the cache/buffer. Later, on {@link #flush()} everything will be saved.
 * </ul>
 *
 * NOTE to developer: Please make sure all public methods are synchronized.
 *
 * @author tsa
 *
 */
public class SaveDecoupledHUAttributesDAO implements IHUAttributesDAO
{
	/**
	 * Set this to <code>true</code> in {@link ISysConfigBL} to avoid M_HU_Attributes from not beeing saved, depending on your trxactions and stuff.
	 */
	public static final String SYSCONFIG_AutoFlushEnabledInitial = SaveDecoupledHUAttributesDAO.class.getName() + ".AutoflushEnabledInitial";

	// services
	private static final transient Logger logger = LogManager.getLogger(SaveDecoupledHUAttributesDAO.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	// Parameters
	@ToStringBuilder(skip = true)
	private final IHUAttributesDAO db;

	private boolean _autoflushEnabled = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_AutoFlushEnabledInitial, false); // false to be backward compatible;
	private boolean _incrementalFlush = false;

	// Status
	/** Cache: huKey to huAtributeKey to {@link I_M_HU_Attribute} */
	@ToStringBuilder(skip = true)
	private final Map<Object, Map<Object, I_M_HU_Attribute>> _hu2huAttributes = new HashMap<>();
	@ToStringBuilder(skip = true)
	private final List<I_M_HU_Attribute> _huAttributesToRemove = new ArrayList<>();
	private final Set<Integer> idsToSaveFromLastFlush = new HashSet<>();

	public SaveDecoupledHUAttributesDAO(final IHUAttributesDAO db)
	{
		super();
		Check.assumeNotNull(db, "db not null");
		this.db = db;
	}

	@Override
	public synchronized String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	protected void finalize() throws Throwable
	{
		if (idsToSaveFromLastFlush != null && !idsToSaveFromLastFlush.isEmpty())
		{
			new AdempiereException("WARNING: It could be that following M_HU_Attribute_IDs have changes which will never be saved to database: " + idsToSaveFromLastFlush)
					.throwIfDeveloperModeOrLogWarningElse(logger);
		}

		if (_huAttributesToRemove != null && !_huAttributesToRemove.isEmpty())
		{
			new AdempiereException("WARNING: It could be that following M_HU_Attributes to be removed are skipped: " + _huAttributesToRemove)
					.throwIfDeveloperModeOrLogWarningElse(logger);
		}
	}

	public synchronized final SaveDecoupledHUAttributesDAO setAutoflushEnabled(final boolean autoflushEnabled)
	{
		if (this._autoflushEnabled == autoflushEnabled)
		{
			return this;
		}

		// If we are about to enable autoflush, we need to flush existing values first
		if (autoflushEnabled)
		{
			flush();
		}

		this._autoflushEnabled = autoflushEnabled;
		return this;
	}

	/**
	 *
	 * @return <code>false</code> by default, for performance reasons. Note that we do call {@link #flush()} on commit, see the <code>SaveOnCommitHUAttributesDAOTrxListener</code> in
	 *         {@link SaveOnCommitHUAttributesDAO}.
	 */
	public synchronized final boolean isAutoflushEnabled()
	{
		return this._autoflushEnabled;
	}

	/**
	 * Enables/Disabled incremental flush.
	 *
	 * Incremental flush means that {@link #flush()} will save only the objects which were enqueued to be saved after last flush.
	 * If this functionality is not enabled (default), all objects from internal flush are tried to be saved on {@link #flush()}.
	 *
	 * NOTE: in future, i think we can consider to take out this method ALWAYS do incremental flush.
	 *
	 * @param incrementalFlush
	 * @return
	 */
	public synchronized final SaveDecoupledHUAttributesDAO setIncrementalFlush(final boolean incrementalFlush)
	{
		this._incrementalFlush = incrementalFlush;
		return this;
	}

	private final boolean isIncrementalFlush()
	{
		return _incrementalFlush;
	}

	private final Object mkHUKey(final I_M_HU hu)
	{
		return hu.getM_HU_ID();
	}

	private final Object mkHUAttributeKey(final I_M_HU_Attribute huAttribute)
	{
		return mkHUAttributeKey(huAttribute.getM_HU_ID(), AttributeId.ofRepoId(huAttribute.getM_Attribute_ID()));
	}

	private final Object mkHUAttributeKey(final int huId, final AttributeId attributeId)
	{
		return Util.mkKey(huId, attributeId);
	}

	@Override
	public synchronized I_M_HU_Attribute newHUAttribute(final Object contextProvider)
	{
		final I_M_HU_Attribute huAttribute = db.newHUAttribute(contextProvider);
		setReadonly(huAttribute, true);
		return huAttribute;
	}

	private final void setReadonly(final Object model, final boolean readonly)
	{
		if (model == null)
		{
			return;
		}
		InterfaceWrapperHelper.setSaveDeleteDisabled(model, readonly);
	}

	@Override
	public synchronized void save(final I_M_HU_Attribute huAttribute)
	{
		final int huAttributeId = huAttribute.getM_HU_Attribute_ID();

		final boolean autoflushEnabled = isAutoflushEnabled();
		if (autoflushEnabled)
		{
			if (huAttributeId > 0)
			{
				idsToSaveFromLastFlush.add(huAttributeId);
			}
			saveToDatabase(huAttribute, ITrx.TRXNAME_ThreadInherited);
		}

		final I_M_HU hu = huAttribute.getM_HU();
		final Map<Object, I_M_HU_Attribute> huAttributes = getHUAttributes(hu, true);

		final Object huAttributeKey = mkHUAttributeKey(huAttribute);
		final I_M_HU_Attribute huAttributeOld = huAttributes.put(huAttributeKey, huAttribute);

		//
		// Check if cache is valid
		// NOTE: shall not happen, but even if it happens, i think it will not corrupt the data, so it's ok to just log it when running in production (08776).
		if (huAttributeOld != null && !Util.same(huAttribute, huAttributeOld))
		{
			final HUException ex = new HUException("HU attribute shall not exist in internal cache or it shall be the same instance."
					+ "\n HU Attribute: " + huAttribute + " (" + (huAttribute == null ? null : huAttribute.getCreated()) + ")"
					+ "\n HU Attribute - trx: " + trxManager.get(InterfaceWrapperHelper.getTrxName(huAttribute), OnTrxMissingPolicy.ReturnTrxNone)
					+ "\n HU Attribute(in cache): " + huAttributeOld + " (" + (huAttributeOld == null ? null : huAttributeOld.getCreated()) + ")"
					+ "\n HU: " + hu
					+ "\n HU Trx: " + trxManager.get(InterfaceWrapperHelper.getTrxName(hu), OnTrxMissingPolicy.ReturnTrxNone)
					+ "\n Autoflush enabled: " + autoflushEnabled);
			if (Services.get(IDeveloperModeBL.class).isEnabled())
			{
				throw ex;
			}
			logger.warn(ex.getLocalizedMessage(), ex);
		}

		//
		// Track M_HU_Attribute_ID of changed HU Attributes
		// This set will be used for doing incremental flushes.
		if (!autoflushEnabled // if autoflush was enabled, record was already saved
				&& huAttributeId > 0)
		{
			idsToSaveFromLastFlush.add(huAttributeId);
		}

		setReadonly(huAttribute, true);
	}

	@Override
	public synchronized void delete(final I_M_HU_Attribute huAttribute)
	{
		if (isAutoflushEnabled())
		{
			deleteFromDatabase(huAttribute, ITrx.TRXNAME_ThreadInherited);
		}

		final I_M_HU hu = huAttribute.getM_HU();
		final Map<Object, I_M_HU_Attribute> huAttributes = getHUAttributes(hu, true);

		final Object huAttributeKey = mkHUAttributeKey(huAttribute);
		final I_M_HU_Attribute huAttributeToRemove = huAttributes.remove(huAttributeKey);
		if (!Util.same(huAttribute, huAttributeToRemove))
		{
			throw new IllegalStateException("Given " + huAttribute + " was not found in internal cache or it's different (" + huAttributeToRemove + ")");
		}

		//
		// Case: HU Attribute was already saved in database so we will need to enqueue it to be deleted
		// NOTE: in case of autoflush, we already deleted it
		if (!isAutoflushEnabled() && huAttribute.getM_HU_Attribute_ID() > 0)
		{
			_huAttributesToRemove.add(huAttribute);
		}
	}

	@Override
	public synchronized void initHUAttributes(final I_M_HU hu)
	{
		final boolean retrieveIfNotFound = false; // init with a empty list
		getHUAttributes(hu, retrieveIfNotFound);
	}

	@Override
	public synchronized List<I_M_HU_Attribute> retrieveAttributesOrdered(final I_M_HU hu)
	{
		final boolean retrieveIfNotFound = true;
		final Map<Object, I_M_HU_Attribute> huAttributes = getHUAttributes(hu, retrieveIfNotFound);
		if (huAttributes.isEmpty())
		{
			return ImmutableList.of();
		}

		final HuPackingInstructionsVersionId piVersionId = Services.get(IHandlingUnitsBL.class).getEffectivePIVersionId(hu);
		final PIAttributes piAttributes = Services.get(IHUPIAttributesDAO.class).retrievePIAttributes(piVersionId);
		
		return huAttributes.values()
				.stream()
				.sorted(HUAttributesBySeqNoComparator.of(piAttributes))
				.collect(ImmutableList.toImmutableList());
	}

	private final Map<Object, I_M_HU_Attribute> getHUAttributes(final I_M_HU hu, final boolean retrieveIfNotFound)
	{
		final Object huKey = mkHUKey(hu);

		Map<Object, I_M_HU_Attribute> huAttributes = _hu2huAttributes.get(huKey);
		if (huAttributes != null)
		{
			return huAttributes;
		}

		if (retrieveIfNotFound)
		{
			final List<I_M_HU_Attribute> huAttributesList = db.retrieveAttributesOrdered(hu);
			huAttributes = new HashMap<>(huAttributesList.size());
			for (final I_M_HU_Attribute huAttribute : huAttributesList)
			{
				// Make sure HU attributes are cached using ThreadInherited trx and NOT hu's transaction (08776)
				InterfaceWrapperHelper.setTrxName(huAttribute, ITrx.TRXNAME_ThreadInherited);

				final Object huAttributeKey = mkHUAttributeKey(huAttribute);
				huAttributes.put(huAttributeKey, huAttribute);
				setReadonly(huAttribute, true);
			}
		}
		else
		{
			huAttributes = new HashMap<>();
		}

		_hu2huAttributes.put(huKey, huAttributes);

		return huAttributes;
	}

	@Override
	public synchronized I_M_HU_Attribute retrieveAttribute(final I_M_HU hu, final AttributeId attributeId)
	{
		final boolean retrieveIfNotFound = true;
		final Map<Object, I_M_HU_Attribute> huAttributes = getHUAttributes(hu, retrieveIfNotFound);

		final Object huAttributeKey = mkHUAttributeKey(hu.getM_HU_ID(), attributeId);
		return huAttributes.get(huAttributeKey);
	}

	/**
	 * Save all attributes to database
	 */
	public synchronized final void flush()
	{
		logger.trace("Start flushing");

		final String trxName = trxManager.getThreadInheritedTrxName();
		logger.trace("TrxName={}", trxName);

		// Remove queued attributes
		for (final Iterator<I_M_HU_Attribute> it = _huAttributesToRemove.iterator(); it.hasNext();)
		{
			final I_M_HU_Attribute huAttributeToRemove = it.next();

			deleteFromDatabase(huAttributeToRemove, trxName);

			// Also delete it from our list to prevent trying to delete it again
			it.remove();
		}

		//
		// Save all attributes
		for (final Iterator<Map<Object, I_M_HU_Attribute>> it = _hu2huAttributes.values().iterator(); it.hasNext();)
		{
			final Map<Object, I_M_HU_Attribute> huAttributes = it.next();

			if (huAttributes == null || huAttributes.isEmpty())
			{
				continue;
			}

			for (final I_M_HU_Attribute huAttribute : huAttributes.values())
			{
				saveToDatabase(huAttribute, trxName);
			}

			// NOTE: we are not deleting it from our map because we are using that map as a cache too
			// it.remove();
		}

		logger.trace("Flushing done");
	}

	private final void saveToDatabase(final I_M_HU_Attribute model, final String trxName)
	{
		//
		// If incremental flush is enabled and our record it's not in the list of records to be saved from last flush
		// => do nothing
		final int huAttributeId = model.getM_HU_Attribute_ID();
		if (isIncrementalFlush()
				&& huAttributeId > 0
				&& !idsToSaveFromLastFlush.contains(huAttributeId))
		{
			trace("skip from saving", model);
			return;
		}

		setReadonly(model, false);
		try
		{
			trace("saving to db", model);

			// NOTE: i think it would be better if we would use here the delegated DAO
			InterfaceWrapperHelper.save(model, trxName);

			// Incremental flush: remove the HU Attribute from "to save" list because it was already saved
			idsToSaveFromLastFlush.remove(huAttributeId);
		}
		finally
		{
			setReadonly(model, true);
		}
	}

	private final void deleteFromDatabase(final Object model, final String trxName)
	{
		setReadonly(model, false);
		final String modelTrxName = InterfaceWrapperHelper.getTrxName(model);
		try
		{
			// NOTE: i think it would be better if we would use here the delegated DAO
			InterfaceWrapperHelper.setTrxName(model, trxName);
			InterfaceWrapperHelper.delete(model);
		}
		finally
		{
			InterfaceWrapperHelper.setTrxName(model, modelTrxName); // restore model's trxName
			setReadonly(model, true);
		}
	}

	@Override
	public synchronized IAutoCloseable temporaryDisableAutoflush()
	{
		// If autoflush was not enabled, then we don't have to disable it because it's already disabled
		if (!isAutoflushEnabled())
		{
			return NullAutoCloseable.instance;
		}

		//
		// Disable the autoflush now
		setAutoflushEnabled(false);

		//
		// Return an AutoCloseable which will restore the "autoflush" status
		return new IAutoCloseable()
		{
			private boolean closed = false;

			@Override
			public void close()
			{
				// If closed method is called more then once, do nothing
				// (respect the "method needs to be idempotent" requirement from method contract)
				if (closed)
				{
					return;
				}
				closed = true;

				// Enable back the autoflush
				setAutoflushEnabled(true);
			}
		};
	}

	/**
	 * Debugging method which logs in console the given huAttribute.
	 *
	 * @param message
	 * @param huAttribute
	 */
	private final void trace(final String message, final I_M_HU_Attribute huAttribute)
	{
		if (!logger.isTraceEnabled())
		{
			return;
		}

		final I_M_HU_Attribute modelOld = InterfaceWrapperHelper.createOld(huAttribute, I_M_HU_Attribute.class);
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		final I_M_Attribute attribute = attributesRepo.getAttributeById(huAttribute.getM_Attribute_ID());
		final String modelChangeInfo = ""
				+ Services.get(IHandlingUnitsBL.class).getDisplayName(huAttribute.getM_HU())
				+ " - "
				+ attribute.getValue() + "/" + attribute.getName()
				+ ": "
				+ modelOld.getValue() + "->" + huAttribute.getValue()
				+ ", " + modelOld.getValueNumber() + "->" + huAttribute.getValueNumber()
				//
				+ (InterfaceWrapperHelper.hasChanges(huAttribute) ? ", HasChanges" : "");

		final String daoStatus = "IncrementalFlush=" + isIncrementalFlush() + ", IdsToSaveFromLastFlush=" + idsToSaveFromLastFlush;

		logger.trace("" + message + ": " + modelChangeInfo + " -- " + huAttribute + " -- " + daoStatus);
	}

	@Override
	public synchronized void flushAndClearCache()
	{
		// First of all, we flush everything that's pending, to make sure nothing is lost.
		flush();

		_hu2huAttributes.clear();
		logger.trace("cached cleared");
	}
}
