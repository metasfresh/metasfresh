package de.metas.handlingunits.picking.impl.HUPickingSlotBLs;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsBL.TopLevelHusQuery;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.impl.HUPickingSlotBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageEngineService;
import de.metas.storage.IStorageQuery;
import de.metas.storage.IStorageRecord;
import de.metas.storage.spi.hu.impl.HUStorageRecord;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Locator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

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

/**
 * Contains methods used to support
 * {@link HUPickingSlotBL#retrieveAvailableHUsToPick(de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery)} and
 * {@link HUPickingSlotBL#retrieveAvailableSourceHUs(de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@UtilityClass
public class RetrieveAvailableHUsToPick
{
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	public List<I_M_HU> retrieveAvailableHUsToPick(
			@NonNull final PickingHUsQuery query,
			@NonNull final Function<List<I_M_HU>, List<I_M_HU>> vhuToEndResultFunction)
	{
		if (query.getShipmentSchedules().isEmpty())
		{
			return Collections.emptyList();
		}

		final List<I_M_HU> vhus = retrieveVHUsFromStorage(
				query.getShipmentSchedules(),
				query.isOnlyIfAttributesMatchWithShipmentSchedules(),
				query.isExcludeAllReserved());

		final List<I_M_HU> result = vhuToEndResultFunction.apply(vhus);

		if (!query.isOnlyTopLevelHUs())
		{
			return result; // we are done
		}

		// we need to filter out everything that is not toplevel
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		return handlingUnitsBL.getTopLevelHUs(TopLevelHusQuery.builder().hus(result).includeAll(false).build());
	}

	private List<I_M_HU> retrieveVHUsFromStorage(
			@NonNull final List<I_M_ShipmentSchedule> shipmentSchedules,
			final boolean considerAttributes,
			final boolean isExcludeAllReserved)
	{
		//
		// Create storage queries from shipment schedules
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		final ArrayList<IStorageQuery> storageQueries = new ArrayList<>();
		for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			final IStorageQuery storageQuery = shipmentScheduleBL.createStorageQuery(shipmentSchedule, considerAttributes, isExcludeAllReserved);
			storageQueries.add(storageQuery);
		}

		//
		// Retrieve Storage records
		final IStorageEngineService storageEngineProvider = Services.get(IStorageEngineService.class);
		final IStorageEngine storageEngine = storageEngineProvider.getStorageEngine();
		final IContextAware context = PlainContextAware.createUsingOutOfTransaction();
		final Collection<IStorageRecord> storageRecords = storageEngine.retrieveStorageRecords(context, storageQueries);

		//
		// Fetch VHUs from storage records
		final List<I_M_HU> vhus = new ArrayList<>();
		for (final IStorageRecord storageRecord : storageRecords)
		{
			addToVhusIfValid(storageRecord, vhus);
		}
		return vhus;
	}

	private void addToVhusIfValid(
			@NonNull final IStorageRecord storageRecord,
			@NonNull final List<I_M_HU> vhus)
	{

		final HUStorageRecord huStorageRecord = HUStorageRecord.cast(storageRecord);
		final I_M_HU vhu = huStorageRecord.getVHU();

		// Skip those VHUs which are not about active HUs
		// (i.e. we are skipping things which were already picked)
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
		if (!(huStatusBL.isStatusActive(vhu)))
		{
			return;
		}

		final I_M_Locator locator = huStorageRecord.getLocator();
		if (locator != null && locator.getM_Locator_ID() != vhu.getM_Locator_ID())
		{
			return;
		}

		final PickingCandidateRepository pickingCandidatesRepo = SpringContextHolder.instance.getBean(PickingCandidateRepository.class);

		final HuId huId = HuId.ofRepoId(vhu.getM_HU_ID());
		if (pickingCandidatesRepo.isHuIdPicked(huId))
		{
			return;
		}

		final ProductId productId = huStorageRecord.getProductId();
		if (!huAttributesBL.areMandatoryPickingAttributesFulfilled(huId, productId))
		{
			return;
		}

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		if (!handlingUnitsBL.isHUHierarchyCleared(HuId.ofRepoId(vhu.getM_HU_ID())))
		{
			return;
		}

		// What was the purpose if this check?
		// At any rate, it caused the problem that if you selected a top-level VHU as source-HU,
		// you could never pick from it because it was not added to 'vhus'.
		// final SourceHUsService sourceHuService = SourceHUsService.get();
		// if (sourceHuService.isSourceHu(vhu.getM_HU_ID()))
		// {
		// return;
		// }

		vhus.add(vhu);
	}
}
