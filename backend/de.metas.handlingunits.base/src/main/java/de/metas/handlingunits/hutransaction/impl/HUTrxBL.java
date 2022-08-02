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

package de.metas.handlingunits.hutransaction.impl;

import com.google.common.collect.ImmutableList;
import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.allocation.impl.HUContextProcessorExecutor;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTransactionProcessor;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.hutransaction.IHUTrxDAO;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.impl.CompositeHUTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HUTrxBL implements IHUTrxBL
{
	private static final String DYNATTR_TableRecord = TrxLineTableRecordCacheLocal.class.getName();
	
	private final CompositeHUTrxListener _trxListeners = new CompositeHUTrxListener();
	
	private final IHUTrxDAO huTrxDAO = Services.get(IHUTrxDAO.class);

	private static final class TrxLineTableRecordCacheLocal extends TableRecordCacheLocal<I_M_HU_Trx_Line>
	{
		public TrxLineTableRecordCacheLocal(final I_M_HU_Trx_Line trxLine)
		{
			super(trxLine);
		}

		@Override
		protected int getAD_Table_ID()
		{
			final I_M_HU_Trx_Line trxLine = getParentModel();
			return trxLine.getAD_Table_ID();
		}

		@Override
		protected int getRecord_ID()
		{
			final I_M_HU_Trx_Line trxLine = getParentModel();
			return trxLine.getRecord_ID();
		}

		@Override
		protected void setTableAndRecordId(final int adTableId, final int recordId)
		{
			final I_M_HU_Trx_Line trxLine = getParentModel();
			trxLine.setAD_Table_ID(adTableId);
			trxLine.setRecord_ID(recordId);
		}
	}

	@Override
	public void addListener(final IHUTrxListener trxListener)
	{
		_trxListeners.addListener(trxListener);
	}

	@Override
	public List<IHUTrxListener> getHUTrxListenersList()
	{
		return _trxListeners.asList();
	}

	@Override
	public void transfer(
			final IHUContext huContext,
			final IAllocationSource source,
			final IAllocationDestination destination,
			final IAllocationRequest request)
	{
		Check.assumeNotNull(source, "source not null");
		Check.assumeNotNull(destination, "destination not null");
		Check.assumeNotNull(request, "request not null");

		final HULoader loader = HULoader.of(source, destination);
		loader.load(request);
	}

	private IHUTransactionProcessor createHUTransactionProcessor(final IHUContext huContext)
	{
		return new HUTransactionProcessor(huContext);
	}

	@Override
	public void createTrx(final IHUContext huContext, final IAllocationResult result)
	{
		final IHUTransactionProcessor trxProcessor = createHUTransactionProcessor(huContext);
		trxProcessor.createTrx(result);
	}

	@Override
	public Date getDateTrx(final I_M_HU_Trx_Line line)
	{
		return line.getDateTrx();
	}

	private TrxLineTableRecordCacheLocal getTrxLineTableRecordCacheLocal(final I_M_HU_Trx_Line trxLine)
	{
		Check.assumeNotNull(trxLine, "trxLine not null");
		TrxLineTableRecordCacheLocal recordRef = InterfaceWrapperHelper.getDynAttribute(trxLine, HUTrxBL.DYNATTR_TableRecord);
		if (recordRef == null)
		{
			recordRef = new TrxLineTableRecordCacheLocal(trxLine);
			InterfaceWrapperHelper.setDynAttribute(trxLine, HUTrxBL.DYNATTR_TableRecord, recordRef);
		}
		return recordRef;
	}

	@Override
	public <T> T getReferencedObjectOrNull(final I_M_HU_Trx_Line trxLine, final Class<T> modelClass)
	{
		final TrxLineTableRecordCacheLocal recordRef = getTrxLineTableRecordCacheLocal(trxLine);
		return recordRef.getValue(modelClass);
	}

	@Override
	public void setReferencedObject(final I_M_HU_Trx_Line trxLine, final Object referencedModel)
	{
		final TrxLineTableRecordCacheLocal recordRef = getTrxLineTableRecordCacheLocal(trxLine);
		recordRef.setValue(referencedModel);
	}

	@Override
	public void reverseTrxLines(final IHUContext huContext, final List<I_M_HU_Trx_Line> trxLines)
	{
		final IHUTransactionProcessor trxProcessor = createHUTransactionProcessor(huContext);
		trxProcessor.reverseTrxLines(trxLines);
	}
	
	@Override
	public void setParentHU(@NonNull final ChangeParentHURequest request)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Important: force pre-set HU in current transaction; all future assignments and data retrieval shall be done in current Trx
		// Afterwards, set the HU trx back to it's original one
		final I_M_HU hu = request.getHu();
		final String huTrxNameOld = InterfaceWrapperHelper.getTrxName(hu);

		try
		{
			final IHUContext huContext = request.getHuContext() != null
					? request.getHuContext()
					: handlingUnitsBL.createMutableHUContext(PlainContextAware.newWithThreadInheritedTrx());
			InterfaceWrapperHelper.setTrxName(hu, huContext.getTrxName());

			//
			// Make sure hu's Parent will change
			final I_M_HU_Item parentHUItem = request.getParentHUItem();
			if (parentHUItem == null)
			{
				if (handlingUnitsDAO.retrieveParentItem(hu) == null)
				{
					//
					// Nothing was changed: parent was null before
					return;
				}
			}
			else if (hu.getM_HU_Item_Parent_ID() == parentHUItem.getM_HU_Item_ID())
			{
				//
				// Nothing was changed: same references
				return;
			}

			if (request.isFailIfAggregateTU() && handlingUnitsBL.isAggregateHU(hu))
			{
				throw new AdempiereException("Changing parent for the entire Aggregate TU is not allowed")
						.setParameter("hu", hu);
			}

			final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
			final IAttributeStorage huAttributes = attributeStorageFactory.getAttributeStorage(hu);

			//
			// Fire attribute storage removed on old parent
			final IAttributeStorage parentAttributesOld;
			final I_M_HU_Item parentHUItemOld = handlingUnitsDAO.retrieveParentItem(hu);

			final I_M_HU parentHUOld;
			if (parentHUItemOld != null)
			{
				parentHUOld = parentHUItemOld.getM_HU();
				parentAttributesOld = attributeStorageFactory.getAttributeStorage(parentHUOld);
			}
			else
			{
				parentHUOld = null;
				parentAttributesOld = NullAttributeStorage.instance;
			}

			final boolean huPureVirtual = handlingUnitsBL.isPureVirtual(hu);
			if (!NullAttributeStorage.instance.equals(parentAttributesOld)
					&& !huPureVirtual) // don't propagate pure-virtual HUs; they normally get automatically re-propagated (i.e WeightNet)
			{
				parentAttributesOld.onChildAttributeStorageRemoved(huAttributes);
			}

			//
			// Revert rollup of the target HU's (the one we are assigning to the new parent) old parent storage
			final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
			final IHUStorage huStorageOld = huStorageFactory.getStorage(hu);
			huStorageOld.rollupRevert();

			//
			// Actually unlink the HU from old parent and link it to new parent
			// NOTE: we need to do this AFTER we notify the old parent storages
			handlingUnitsDAO.setParentItem(hu, parentHUItem);

			//
			// Rollup the target HU's (the one we are assigning to the new parent) new parent storage
			final IHUStorage huStorageNew = huStorageFactory.getStorage(hu);
			huStorageNew.rollup();

			//
			// Fire attribute storage added on new parent
			final IAttributeStorage parentAttributesNew;
			if (parentHUItem != null)
			{
				final I_M_HU parentHUNew = parentHUItem.getM_HU();
				parentAttributesNew = attributeStorageFactory.getAttributeStorage(parentHUNew);
			}
			else
			{
				parentAttributesNew = NullAttributeStorage.instance;
			}

			if (!NullAttributeStorage.instance.equals(parentAttributesNew))
			{
				parentAttributesNew.onChildAttributeStorageAdded(huAttributes);
			}

			huContext.getTrxListeners().huParentChanged(hu, parentHUItemOld);

			//
			// If allowed,
			// Mark old HU destroyed if that's the case
			if (request.isDestroyOldParentIfEmptyStorage()
					&& parentHUOld != null && parentHUOld.getM_HU_ID() > 0)
			{
				handlingUnitsBL.destroyIfEmptyStorage(huContext, parentHUOld);
			}
		}
		finally
		{
			InterfaceWrapperHelper.setTrxName(hu, huTrxNameOld);
		}
	}

	@Override
	public void extractHUFromParentIfNeeded(final I_M_HU hu)
	{
		extractHUFromParentIfNeeded(null, hu);
	}

	@Override
	public void extractHUFromParentIfNeeded(@Nullable final IHUContext huContext, @NonNull final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		if (handlingUnitsBL.isTopLevel(hu))
		{
			return;
		}

		InterfaceWrapperHelper.setTrxName(hu, ITrx.TRXNAME_ThreadInherited);

		setParentHU(ChangeParentHURequest.builder()
							.huContext(huContext)
							.parentHUItem(null)
							.hu(hu)
							.build());

	}

	@Override
	public IHUContextProcessorExecutor createHUContextProcessorExecutor(final IHUContext huContext)
	{
		return new HUContextProcessorExecutor(huContext);
	}

	@Override
	public IHUContextProcessorExecutor createHUContextProcessorExecutor(final IContextAware context)
	{
		final IHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing(context);
		return new HUContextProcessorExecutor(huContext);
	}

	@Override
	public List<IHUTransactionCandidate> aggregateTransactions(final List<IHUTransactionCandidate> transactions)
	{
		final Map<ArrayKey, IHUTransactionCandidate> transactionsAggregateMap = new HashMap<>();

		final List<IHUTransactionCandidate> notAggregated = new ArrayList<>();

		for (final IHUTransactionCandidate trx : transactions)
		{
			if (trx.getCounterpart() != null)
			{
				// we don't want to aggregate paired trxCandidates because we want to discard the trxCandidates this method was called with.
				// unless we don't have to, we don't want to delve into those intricacies...
				notAggregated.add(trx);

			}

			// note that we use the ID if we can, because we don't want this to fail e.g. because of two different versions of the "same" VHU-item
			final ArrayKey key = Util.mkKey(
					// trxCandidate.getCounterpart(),
					trx.getDate(),
					trx.getHUStatus(),
					// trxCandidate.getM_HU(), just delegates to HU_Item
					trx.getM_HU_Item() == null ? -1 : trx.getM_HU_Item().getM_HU_Item_ID(),
					trx.getLocatorId(),
					trx.getProductId() == null ? -1 : trx.getProductId().getRepoId(),
					// trxCandidate.getQuantity(),
					trx.getReferencedModel() == null ? -1 : TableRecordReference.of(trx.getReferencedModel()),
					// trxCandidate.getVHU(), just delegates to VHU_Item
					trx.getVHU_Item() == null ? -1 : trx.getVHU_Item().getM_HU_Item_ID(),
					trx.isSkipProcessing());

			transactionsAggregateMap.merge(key,
										   trx,
										   (existingCand, newCand) -> {

											   final HUTransactionCandidate mergedCandidate = new HUTransactionCandidate(existingCand.getReferencedModel(),
																														 existingCand.getM_HU_Item(),
																														 existingCand.getVHU_Item(),
																														 existingCand.getProductId(),
																														 existingCand.getQuantity().add(newCand.getQuantity()),
																														 existingCand.getDate(),
																														 existingCand.getLocatorId(),
																														 existingCand.getHUStatus());

											   if (existingCand.isSkipProcessing())
											   {
												   mergedCandidate.setSkipProcessing();
											   }

											   return mergedCandidate;
										   });

		}

		return ImmutableList.<IHUTransactionCandidate>builder()
				.addAll(notAggregated)
				.addAll(transactionsAggregateMap.values())
				.build();
	}
}
