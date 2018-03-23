package de.metas.inoutcandidate.spi;

import java.util.Comparator;

/*
 * #%L
 * de.metas.swat.base
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

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.util.CCache;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.inoutcandidate.model.I_M_IolCandHandler_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_AttributeConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * This interface declares the pluggable main component to create and handle {@link I_M_ShipmentSchedule} records for
 * other records from a specific source table (e.g. order lines or subscription lines).
 *
 * Implementors are also related to {@link I_M_IolCandHandler} records.
 *
 * Implementors can be registered by calling {@link IShipmentScheduleHandlerBL#registerHandler(Properties, ShipmentScheduleHandler)}.
 *
 * Interface methods should only be called by the {@link IShipmentScheduleHandlerBL} implementation.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public abstract class ShipmentScheduleHandler
{
	@Value
	@Builder
	public static class AttributeConfig
	{
		int orgId;

		/** mostly to help with debugging */
		int attributeConfigId;

		int attributeId;

		boolean onlyIfInReferencedRecordAsi;
	}

	private final static CCache<Integer, ImmutableList<AttributeConfig>> cache = CCache.newCache(
			I_M_ShipmentSchedule_AttributeConfig.Table_Name + "#by#" + I_M_ShipmentSchedule_AttributeConfig.COLUMN_M_IolCandHandler_ID,
			5,
			CCache.EXPIREMINUTES_Never);

	private static ImmutableList<AttributeConfig> loadAttributeConfigs(final int M_IolCandHandler_ID)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_AttributeConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_AttributeConfig.COLUMN_M_IolCandHandler_ID, M_IolCandHandler_ID)
				.create()
				.stream()
				.map(attributeConfigRecord -> AttributeConfig.builder()
						.attributeConfigId(attributeConfigRecord.getM_ShipmentSchedule_AttributeConfig_ID())
						.orgId(attributeConfigRecord.getAD_Org_ID())
						.attributeId(attributeConfigRecord.getM_Attribute_ID())
						.onlyIfInReferencedRecordAsi(attributeConfigRecord.isOnlyIfInReferencedASI()).build())
				.collect(ImmutableList.toImmutableList());
	}

	private int M_IolCandHandler_ID = -1;

	/**
	 * Identifies database records that are currently missing one or more {@link I_M_ShipmentSchedule}s.
	 * <p>
	 * Note:
	 * <ul>
	 * <li>The framework will create a {@link I_M_IolCandHandler_Log} record for every object returned by this method.</li>
	 * <li>Implementors should check for <code>I_M_IolCandHandler_Log</code> to make sure that they don't repeatedly return records are then vetoed by some {@link IInOutCandHandlerListener}</li>
	 * </ul>
	 *
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public abstract List<Object> retrieveModelsWithMissingCandidates(Properties ctx, String trxName);

	/**
	 * Creates missing candidates for the given model.
	 *
	 * SPI-implementors can assume that this method is only called with objects that
	 * <ul>
	 * <li>can be handled by {@link InterfaceWrapperHelper} and</li>
	 * <li>belong to the table that is returned by their own {@link #getSourceTable()} implementation</li>
	 * <li>the framework will look up and set the shipment schedules' {@link I_M_ShipmentSchedule#COLUMNNAME_M_IolCandHandler_ID} with the correct value for this handler.
	 * <li>the instances returned by this method's implementation will be saved by the framework</li>
	 * </ul>
	 *
	 * @param model
	 *
	 * @return
	 */
	public abstract List<I_M_ShipmentSchedule> createCandidatesFor(Object model);

	/**
	 * Invalidates invoice candidates for the given model.
	 *
	 * SPI-implementors can assume that this method is only called with objects that
	 * <ul>
	 * <li>can be handled by {@link InterfaceWrapperHelper} and</li>
	 * <li>belong to the table that is returned by {@link #getSourceTable()}</li>
	 * </ul>
	 *
	 * @param model
	 */
	public abstract void invalidateCandidatesFor(Object model);

	/**
	 * Returns the table this handler is responsible for. SPI-implementors can assume that their methods are only called
	 * with objects that
	 * <ul>
	 * <li>can be handled by {@link InterfaceWrapperHelper} and</li>
	 * <li>belong to the table that is returned by this method</li>
	 * </ul>
	 *
	 * @return
	 */
	public abstract String getSourceTable();

	/**
	 * Create a new deliver request for the given <code>sched</code>.<br>
	 * This method shall be called by {@link IShipmentScheduleHandlerBL#createDeliverRequest(I_M_ShipmentSchedule)}, not directly by a user.
	 *
	 * @param sched
	 * @return
	 */
	public abstract IDeliverRequest createDeliverRequest(I_M_ShipmentSchedule sched);

	/**
	 * Invoked by the framework while an instance is initialized.
	 */
	public void setM_IolCandHandler_IDOneTimeOnly(final int M_IolCandHandler_ID)
	{
		Check.errorIf(this.M_IolCandHandler_ID > -1,
				"This instance already has an M_IolCandHandler_ID;this={}; given parameter M_IolCandHandler_ID={}",
				this, M_IolCandHandler_ID);

		this.M_IolCandHandler_ID = M_IolCandHandler_ID;
	}

	public final boolean attributeShallBePartOfShipmentLine(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_M_Attribute attribute)
	{
		final Optional<AttributeConfig> attributeConfigIfPresent = findMatchingAttributeConfig(
				shipmentSchedule.getAD_Org_ID(),
				attribute);
		if (!attributeConfigIfPresent.isPresent())
		{
			return false;
		}

		final AttributeConfig attributeConfigToUse = attributeConfigIfPresent.get();

		final boolean onlyIfInReferencedRecordAsi = attributeConfigToUse.isOnlyIfInReferencedRecordAsi();
		if (!onlyIfInReferencedRecordAsi)
		{
			return true;
		}

		final TableRecordReference referencesRecord = TableRecordReference.ofReferenced(shipmentSchedule);
		final IAttributeSetInstanceAware asiAware = Services.get(IAttributeSetInstanceAwareFactoryService.class)
				.createOrNull(referencesRecord.getModel());
		if (asiAware == null)
		{
			return true;
		}

		final I_M_AttributeInstance attributeInstance = Services.get(IAttributeDAO.class)
				.retrieveAttributeInstance(
						asiAware.getM_AttributeSetInstance(),
						attributeConfigToUse.getAttributeId());

		final boolean referencedRecordAsiContainsAttribute = attributeInstance != null;
		if (!referencedRecordAsiContainsAttribute)
		{
			return false;
		}

		final boolean referencedRecordAsiHasValue = attributeInstance.getM_AttributeValue_ID() > 0
						&& !attributeInstance.getM_AttributeValue().isNullFieldValue();
		return referencedRecordAsiHasValue;
	}

	@VisibleForTesting
	Optional<AttributeConfig> findMatchingAttributeConfig(
			final int orgId,
			final I_M_Attribute m_Attribute)
	{
		final ImmutableList<AttributeConfig> attributeConfigs = getAttributeConfigs();

		final Comparator<AttributeConfig> orgComparator = Comparator
				.comparing(AttributeConfig::getOrgId)
				.reversed();

		final Optional<AttributeConfig> matchingConfigIfPresent = attributeConfigs
				.stream()
				.filter(c -> c.getAttributeId() == m_Attribute.getM_Attribute_ID())
				.sorted(orgComparator)
				.findFirst();

		final Optional<AttributeConfig> wildCardConfigIfPresent = attributeConfigs
				.stream()
				.filter(c -> c.getAttributeId() <= 0)
				.sorted(orgComparator)
				.findFirst();

		final Optional<AttributeConfig> attributeConfigIfPresent = //
				Stream.of(matchingConfigIfPresent, wildCardConfigIfPresent)
						.filter(Optional::isPresent)
						.map(Optional::get)
						.findFirst();
		return attributeConfigIfPresent;
	}

	/**
	 * Visible so that we can stub out the cache in tests.
	 */
	@VisibleForTesting
	ImmutableList<AttributeConfig> getAttributeConfigs()
	{
		final ImmutableList<AttributeConfig> attributeConfigs = //
				cache.getOrLoad(M_IolCandHandler_ID, () -> loadAttributeConfigs(M_IolCandHandler_ID));
		return attributeConfigs;
	}

	public static <T extends ShipmentScheduleHandler> T createNewInstance(@NonNull final Class<T> handlerClass)
	{
		try
		{
			final T handler = handlerClass.newInstance();
			return handler;
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
