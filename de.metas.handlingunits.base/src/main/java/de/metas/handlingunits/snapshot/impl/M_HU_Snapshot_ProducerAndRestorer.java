package de.metas.handlingunits.snapshot.impl;

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

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.TrxRunnableAdapter;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Snapshot;
import de.metas.handlingunits.snapshot.ISnapshotProducer;
import de.metas.handlingunits.snapshot.ISnapshotRestorer;
import lombok.NonNull;

/**
 * Implementation responsible with recursively doing the {@link I_M_HU} snapshots and restoring from them.
 *
 * @author tsa
 *
 */
public class M_HU_Snapshot_ProducerAndRestorer implements ISnapshotRestorer<I_M_HU>, ISnapshotProducer<I_M_HU>
{
	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final M_HU_SnapshotHandler huSnapshotHandler = new M_HU_SnapshotHandler();

	private final Set<Integer> _huIds = new HashSet<>();

	@Override
	public void restoreFromSnapshot()
	{
		// If there are no models to be restored, then do nothing.
		if (!hasModelsToSnapshot())
		{
			return;
		}

		final IContextAware contextInitial = huSnapshotHandler.getContext();

		trxManager.run(contextInitial.getTrxName(), new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				final IContextAware context = PlainContextAware.newWithTrxName(contextInitial.getCtx(), localTrxName);
				setContext(context);

				restoreInTrx();
			}

			@Override
			public void doFinally()
			{
				huSnapshotHandler.setContext(contextInitial); // restore the context
			}
		});
	}

	private final void restoreInTrx()
	{
		final Collection<Integer> huIdsToRestore = getHUIdsAndClear();
		final List<I_M_HU> husToRestore = Services.get(IHandlingUnitsDAO.class).retrieveByIds(huIdsToRestore);

		for (final I_M_HU hu : husToRestore)
		{
			final I_M_HU_Snapshot huSnapshot = huSnapshotHandler.retrieveModelSnapshot(hu);
			huSnapshotHandler.restoreModelFromSnapshot(hu, huSnapshot);
		}
	}

	@Override
	public ISnapshotProducer<I_M_HU> createSnapshots()
	{
		// If there are no HUs to snapshot, then do nothing
		// NOTE: this is an accepted case in case we are using the producer together with some other incremental BLs
		// which are snapshoting HUs while they are proceeding.
		final Set<Integer> initialHUIds = ImmutableSet.copyOf(getHUIdsAndClear());
		if (initialHUIds.isEmpty())
		{
			return this;
		}

		//
		// Collect all M_HU_IDs and M_HU_Item_IDs from starting HUs to the bottom.
		final Set<Integer> huIds = new HashSet<>();
		final Set<Integer> huItemIds = new HashSet<>();
		huSnapshotHandler.collectHUAndItemIds(initialHUIds, huIds, huItemIds);
		//
		// Make sure we have collected something
		// (shall not happen)
		Check.assumeNotEmpty(huIds, "huIds not empty");
		Check.assumeNotEmpty(huItemIds, "huItemIds not empty");

		//
		// Create snapshot for M_HUs and their depending records
		huSnapshotHandler.createSnapshotsByIds(huIds);

		final M_HU_Attribute_SnapshotHandler huAttributesSnapshotHandler = new M_HU_Attribute_SnapshotHandler(huSnapshotHandler);
		huAttributesSnapshotHandler.createSnapshotsByParentIds(huIds);

		final M_HU_Storage_SnapshotHandler huStoargeSnapshotHandler = new M_HU_Storage_SnapshotHandler(huSnapshotHandler);
		huStoargeSnapshotHandler.createSnapshotsByParentIds(huIds);

		final M_HU_Item_SnapshotHandler huItemSnapshotHandler = new M_HU_Item_SnapshotHandler(huSnapshotHandler);
		huItemSnapshotHandler.createSnapshotsByParentIds(huIds);

		final M_HU_Item_Storage_SnapshotHandler huItemStorageSnapshotHandler = new M_HU_Item_Storage_SnapshotHandler(huItemSnapshotHandler);
		huItemStorageSnapshotHandler.createSnapshotsByParentIds(huItemIds);

		return this;
	}

	@Override
	public ISnapshotRestorer<I_M_HU> setSnapshotId(final String snapshotId)
	{
		huSnapshotHandler.setSnapshotId(snapshotId);
		return this;
	}

	@Override
	public String getSnapshotId()
	{
		return huSnapshotHandler.getSnapshotId();
	}

	@Override
	public M_HU_Snapshot_ProducerAndRestorer setContext(final IContextAware context)
	{
		huSnapshotHandler.setContext(context);
		return this;
	}

	@Override
	public M_HU_Snapshot_ProducerAndRestorer setDateTrx(final Date dateTrx)
	{
		huSnapshotHandler.setDateTrx(dateTrx);
		return this;
	}

	@Override
	public M_HU_Snapshot_ProducerAndRestorer setReferencedModel(final Object referencedModel)
	{
		huSnapshotHandler.setReferencedModel(referencedModel);
		return this;
	}

	@Override
	public M_HU_Snapshot_ProducerAndRestorer addModel(@NonNull final I_M_HU model)
	{
		_huIds.add(model.getM_HU_ID());
		return this;
	}

	@Override
	public final M_HU_Snapshot_ProducerAndRestorer addModels(final Collection<? extends I_M_HU> models)
	{
		models.forEach(this::addModel);
		return this;
	}

	/**
	 * Gets currently enqueued models and it also clears the internal queue.
	 *
	 * @return enqueued models to be restored or snapshot-ed
	 */
	private final Set<Integer> getHUIdsAndClear()
	{
		final Set<Integer> modelIds = new HashSet<>(_huIds);
		_huIds.clear();
		return modelIds;
	}

	private final boolean hasModelsToSnapshot()
	{
		return !_huIds.isEmpty();
	}

	@Override
	public ISnapshotRestorer<I_M_HU> addModelId(final int huId)
	{
		_huIds.add(huId);
		return this;
	}

	@Override
	public ISnapshotRestorer<I_M_HU> addModelIds(final Collection<Integer> huIds)
	{
		_huIds.addAll(huIds);
		return this;
	}
}
