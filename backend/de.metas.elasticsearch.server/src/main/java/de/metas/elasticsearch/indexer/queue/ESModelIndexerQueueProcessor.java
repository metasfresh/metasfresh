/*
 * #%L
 * de.metas.elasticsearch.server
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.elasticsearch.indexer.queue;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.elasticsearch.IESSystem;
import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.elasticsearch.indexer.engine.ESModelIndexer;
import de.metas.elasticsearch.indexer.source.ESModelIndexerDataSources;
import de.metas.elasticsearch.indexer.source.ESModelToIndex;
import de.metas.elasticsearch.indexer.engine.IESIndexerResult;
import de.metas.elasticsearch.indexer.registry.ESModelIndexersRegistry;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Profile(Profiles.PROFILE_App)
@Component
public class ESModelIndexerQueueProcessor implements Consumer<ESModelIndexEvent>
{
	private final IESSystem esSystem = Services.get(IESSystem.class);
	private final IEventBus eventBus;
	private final ESModelIndexersRegistry esModelIndexersRegistry;

	public ESModelIndexerQueueProcessor(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final ESModelIndexersRegistry esModelIndexersRegistry)
	{
		this.eventBus = eventBusFactory.getEventBus(ESModelIndexerQueue.TOPIC);
		this.esModelIndexersRegistry = esModelIndexersRegistry;
	}

	@PostConstruct
	private void postConstruct()
	{
		eventBus.subscribeOn(ESModelIndexEvent.class, this);
	}

	@Override
	public void accept(@NonNull final ESModelIndexEvent event)
	{
		esSystem.assertEnabled();

		switch (event.getType())
		{
			case ADD:
				add(event.getModelIndexerId(), event.getModelTableName(), event.getModelIds());
				break;
			case REMOVE:
				remove(event.getModelIndexerId(), event.getModelIds());
				break;
			default:
				throw new AdempiereException("Unsupported type: " + event);
		}
	}

	private List<PO> retrieveModels(@NonNull final String modelTableName, final @NonNull Set<Integer> modelIds)
	{
		return TableModelLoader.instance.getPOs(Env.getCtx(), modelTableName, modelIds, ITrx.TRXNAME_ThreadInherited);
	}

	private void add(final @NonNull ESModelIndexerId modelIndexerId, final @NonNull String modelTableName, final @NonNull Set<Integer> modelIds)
	{
		final List<PO> allPOs = retrieveModels(modelTableName, modelIds);
		if (allPOs.isEmpty())
		{
			throw new AdempiereException("No source models found");
		}

		final ArrayList<ESModelToIndex> modelsToAdd = new ArrayList<>();
		final HashSet<String> modelIdsToRemove = new HashSet<>();
		for (final PO po : allPOs)
		{
			if (InterfaceWrapperHelper.isActive(po))
			{
				modelsToAdd.add(ESModelToIndex.ofPO(po));
			}
			else
			{
				final int recordId = InterfaceWrapperHelper.getId(po);
				modelIdsToRemove.add(String.valueOf(recordId));
			}
		}

		if (!modelIdsToRemove.isEmpty())
		{
			final ESModelIndexer modelIndexer = esModelIndexersRegistry.getModelIndexerById(modelIndexerId);
			final IESIndexerResult result = modelIndexer.removeFromIndexByIds(modelIdsToRemove);
			Loggables.addLog(result.getSummary());
			result.throwExceptionIfAnyFailure();
		}

		if (!modelsToAdd.isEmpty())
		{
			final ESModelIndexer modelIndexer = esModelIndexersRegistry.getModelIndexerById(modelIndexerId);
			final IESIndexerResult result = modelIndexer.addToIndex(ESModelIndexerDataSources.ofCollection(modelsToAdd));
			Loggables.addLog(result.getSummary());
			result.throwExceptionIfAnyFailure();
		}
	}

	private void remove(final @NonNull ESModelIndexerId modelIndexerId, final @NonNull Set<Integer> idsToRemove)
	{
		if (idsToRemove.isEmpty())
		{
			throw new AdempiereException("No source models found");
		}

		final ESModelIndexer modelIndexer = esModelIndexersRegistry.getModelIndexerById(modelIndexerId);

		final IESIndexerResult result = modelIndexer.removeFromIndexByIds(idsToRemove.stream()
				.map(String::valueOf)
				.collect(ImmutableList.toImmutableList()));
		Loggables.addLog(result.getSummary());
		result.throwExceptionIfAnyFailure();
	}

}
