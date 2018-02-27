package de.metas.material.cockpit.view.eventhandler;

import java.util.Collection;

import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-cockpit
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

@Service
@Profile(Profiles.PROFILE_App) // it's important to have just *one* instance of this listener, because on each event needs to be handled exactly once.
public class AbstractStockEstimateHandler
		implements MaterialEventHandler<AbstractStockEstimateEvent>
{
	private final MainDataRequestHandler dataUpdateRequestHandler;

	public AbstractStockEstimateHandler(
			@NonNull final MainDataRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends AbstractStockEstimateEvent>> getHandeledEventType()
	{
		return ImmutableList.of(StockEstimateCreatedEvent.class, StockEstimateDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final AbstractStockEstimateEvent event)
	{
		final UpdateMainDataRequest dataUpdateRequest = createDataUpdateRequestForEvent(event);
		dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);
	}

	private UpdateMainDataRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractStockEstimateEvent stockEstimateEvent)
	{
		final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.builder()
				.productDescriptor(stockEstimateEvent.getProductDescriptor())
				.date(TimeUtil.getDay(stockEstimateEvent.getDate()))
				.plantId(stockEstimateEvent.getPlantId())
				.build();

		final UpdateMainDataRequest request = UpdateMainDataRequest.builder()
				.identifier(identifier)
				.countedQty(stockEstimateEvent.getQuantityDelta())
				.build();
		return request;
	}
}
