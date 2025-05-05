package de.metas.handlingunits.attribute.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.HUAndPIAttributes;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.PIAttributes;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * {@link IHUAttributesDAO} implementation which acts like a save buffer:
 * <ul>
 * <li>automatically loads attributes from underlying {@link IHUAttributesDAO}, if they does not already exist in our local cache
 * <li>on save, instead of directly saving them we are just adding them to the cache/buffer. Later, on {@link #flush()} everything will be saved.
 * </ul>
 * <p>
 * NOTE to developer: Please make sure all public methods are synchronized.
 *
 * @author tsa
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

	private final boolean _autoflushEnabled = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_AutoFlushEnabledInitial, false); // false to be backward compatible;

	// Status
	/**
	 * Cache: huKey to huAtributeKey to {@link I_M_HU_Attribute}
	 */
	@ToStringBuilder(skip = true)
	private final HashMap<HuId, HUAttributesMap> _hu2huAttributes = new HashMap<>();
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
		if (!idsToSaveFromLastFlush.isEmpty())
		{
			new AdempiereException("WARNING: It could be that following M_HU_Attribute_IDs have changes which will never be saved to database: " + idsToSaveFromLastFlush)
					.throwIfDeveloperModeOrLogWarningElse(logger);
		}

		if (!_huAttributesToRemove.isEmpty())
		{
			new AdempiereException("WARNING: It could be that following M_HU_Attributes to be removed are skipped: " + _huAttributesToRemove)
					.throwIfDeveloperModeOrLogWarningElse(logger);
		}
	}

	/**
	 * @return <code>false</code> by default, for performance reasons. Note that we do call {@link #flush()} on commit, see the <code>SaveOnCommitHUAttributesDAOTrxListener</code> in
	 * {@link SaveOnCommitHUAttributesDAO}.
	 */
	public synchronized final boolean isAutoflushEnabled()
	{
		return _autoflushEnabled;
	}

	private boolean isIncrementalFlush()
	{
		return false;
	}

	@Override
	public synchronized I_M_HU_Attribute newHUAttribute(final Object contextProvider)
	{
		final I_M_HU_Attribute huAttribute = db.newHUAttribute(contextProvider);
		setReadonly(huAttribute, true);
		return huAttribute;
	}

	private static void setReadonly(final Object model, final boolean readonly)
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
		final HUAttributesMap huAttributes = getHUAttributesMap(hu);

		final I_M_HU_Attribute huAttributeOld = huAttributes.put(huAttribute);

		//
		// Check if cache is valid
		// NOTE: shall not happen, but even if it happens, i think it will not corrupt the data, so it's ok to just log it when running in production (08776).
		if (huAttributeOld != null && !Util.same(huAttribute, huAttributeOld))
		{
			final HUException ex = new HUException("HU attribute shall not exist in internal cache or it shall be the same instance."
					+ "\n HU Attribute: " + huAttribute + " (" + huAttribute.getCreated() + ")"
					+ "\n HU Attribute - trx: " + trxManager.get(InterfaceWrapperHelper.getTrxName(huAttribute), OnTrxMissingPolicy.ReturnTrxNone)
					+ "\n HU Attribute(in cache): " + huAttributeOld + " (" + huAttributeOld.getCreated() + ")"
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
		final HUAttributesMap huAttributes = getHUAttributesMap(hu);

		huAttributes.remove(huAttribute);

		//
		// Case: HU Attribute was already saved in database so we will need to enqueue it to be deleted
		// NOTE: in case of autoflush, we already deleted it
		if (!isAutoflushEnabled() && huAttribute.getM_HU_Attribute_ID() > 0)
		{
			_huAttributesToRemove.add(huAttribute);
		}
	}

	@Override
	public List<I_M_HU_Attribute> retrieveAllAttributesNoCache(final Collection<HuId> huIds)
	{
		return db.retrieveAllAttributesNoCache(huIds);
	}

	@Override
	public synchronized HUAndPIAttributes retrieveAttributesOrdered(final I_M_HU hu)
	{
		final HUAttributesMap huAttributes = getHUAttributesMap(hu);
		final List<I_M_HU_Attribute> huAttributesList = huAttributes.toList();
		final PIAttributes piAttributes = createPIAttributes(huAttributesList);
		final ImmutableList<I_M_HU_Attribute> huAttributesSorted = HUAttributesBySeqNoComparator.of(piAttributes).sortAndCopy(huAttributesList);
		return HUAndPIAttributes.of(huAttributesSorted, piAttributes);
	}

	private PIAttributes createPIAttributes(final Collection<I_M_HU_Attribute> huAttributesList)
	{
		final IHUPIAttributesDAO piAttributesRepo = Services.get(IHUPIAttributesDAO.class);

		final ImmutableSet<Integer> piAttributeIds = huAttributesList.stream().map(I_M_HU_Attribute::getM_HU_PI_Attribute_ID).collect(ImmutableSet.toImmutableSet());
		return piAttributesRepo.retrievePIAttributesByIds(piAttributeIds);
	}

	private HUAttributesMap getHUAttributesMap(final I_M_HU hu)
	{
		return getHUAttributesMap(hu, this::retrieveHUAttributesMap);
	}

	private HUAttributesMap retrieveHUAttributesMap(final I_M_HU hu)
	{
		final HUAttributesMap huAttributes;
		final HUAndPIAttributes huAndPIAttributes = db.retrieveAttributesOrdered(hu);
		huAttributes = HUAttributesMap.of(huAndPIAttributes);
		return huAttributes;
	}

	private HUAttributesMap getHUAttributesMap(final I_M_HU hu, final Function<I_M_HU, HUAttributesMap> loader)
	{
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		return _hu2huAttributes.computeIfAbsent(huId, k -> loader.apply(hu));
	}

	@Override
	public synchronized I_M_HU_Attribute retrieveAttribute(final I_M_HU hu, final AttributeId attributeId)
	{
		return getHUAttributesMap(hu)
				.getByAttributeIdOrNull(attributeId);
	}

	/**
	 * Save all attributes to database
	 */
	@Override
	public synchronized final void flush()
	{
		logger.trace("Start flushing");

		final String trxName = trxManager.getThreadInheritedTrxName();
		logger.trace("TrxName={}", trxName);

		// Remove queued attributes
		for (final Iterator<I_M_HU_Attribute> it = _huAttributesToRemove.iterator(); it.hasNext(); )
		{
			final I_M_HU_Attribute huAttributeToRemove = it.next();

			deleteFromDatabase(huAttributeToRemove, trxName);

			// Also delete it from our list to prevent trying to delete it again
			it.remove();
		}

		//
		// Save all attributes
		for (final HUAttributesMap huAttributes : _hu2huAttributes.values())
		{
			if (huAttributes == null || huAttributes.isEmpty())
			{
				continue;
			}

			for (final I_M_HU_Attribute huAttribute : huAttributes)
			{
				saveToDatabase(huAttribute, trxName);
			}

			// NOTE: we are not deleting it from our map because we are using that map as a cache too
			// it.remove();
		}

		logger.trace("Flushing done");
	}

	private void saveToDatabase(final I_M_HU_Attribute model, final String trxName)
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

	private void deleteFromDatabase(final Object model, final String trxName)
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

	/**
	 * Debugging method which logs in console the given huAttribute.
	 */
	private void trace(final String message, final I_M_HU_Attribute huAttribute)
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

	private static class HUAttributesMap implements Iterable<I_M_HU_Attribute>
	{
		public static HUAttributesMap of(final HUAndPIAttributes huAndPIAttributes)
		{
			final ImmutableList<I_M_HU_Attribute> huAttributesList = huAndPIAttributes.getHuAttributes();
			final HashMap<AttributeId, I_M_HU_Attribute> huAttributes = new HashMap<>(huAttributesList.size());
			for (final I_M_HU_Attribute huAttribute : huAttributesList)
			{
				// Make sure HU attributes are cached using ThreadInherited trx and NOT hu's transaction (08776)
				InterfaceWrapperHelper.setTrxName(huAttribute, ITrx.TRXNAME_ThreadInherited);

				final AttributeId attributeId = AttributeId.ofRepoId(huAttribute.getM_Attribute_ID());
				huAttributes.put(attributeId, huAttribute);
				setReadonly(huAttribute, true);
			}

			return new HUAttributesMap(huAttributes);
		}

		private final HashMap<AttributeId, I_M_HU_Attribute> huAttributes;

		private HUAttributesMap(final HashMap<AttributeId, I_M_HU_Attribute> huAttributes)
		{
			this.huAttributes = huAttributes;
		}

		public I_M_HU_Attribute getByAttributeIdOrNull(final AttributeId attributeId)
		{
			return huAttributes.get(attributeId);
		}

		public boolean isEmpty()
		{
			return huAttributes.isEmpty();
		}

		@Override
		public Iterator<I_M_HU_Attribute> iterator()
		{
			return huAttributes.values().iterator();
		}

		public List<I_M_HU_Attribute> toList()
		{
			return ImmutableList.copyOf(huAttributes.values());
		}

		@Nullable
		public I_M_HU_Attribute put(final I_M_HU_Attribute huAttribute)
		{
			final AttributeId attributeId = AttributeId.ofRepoId(huAttribute.getM_Attribute_ID());
			return huAttributes.put(attributeId, huAttribute);
		}

		public void remove(final I_M_HU_Attribute huAttribute)
		{
			final AttributeId attributeId = AttributeId.ofRepoId(huAttribute.getM_Attribute_ID());
			final I_M_HU_Attribute huAttributeRemoved = huAttributes.remove(attributeId);
			if (!Util.same(huAttribute, huAttributeRemoved))
			{
				throw new AdempiereException("Given " + huAttribute + " was not found in internal cache or it's different (" + huAttributeRemoved + ")");
			}
		}
	}
}
