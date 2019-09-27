package de.metas.material.dispo.service.event.handler.attributes;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.attributes.AttributesChangedEvent;
import de.metas.material.event.attributes.AttributesKeyWithASI;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Profile(Profiles.PROFILE_MaterialDispo)
public class AttributesChangedHandler implements MaterialEventHandler<AttributesChangedEvent>
{
	private final CandidateChangeService candidateChangeHandler;

	public AttributesChangedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler)
	{
		this.candidateChangeHandler = candidateChangeHandler;
	}

	@Override
	public Collection<Class<? extends AttributesChangedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(AttributesChangedEvent.class);
	}

	@Override
	public void handleEvent(final AttributesChangedEvent event)
	{
		final List<Candidate> candidates = createCandidates(event);
		for (final Candidate candidate : candidates)
		{
			candidateChangeHandler.onCandidateNewOrChange(candidate);
		}
	}

	private List<Candidate> createCandidates(final AttributesChangedEvent event)
	{
		return ImmutableList.of(
				createCandidate(event, false),
				createCandidate(event, true));
	}

	private Candidate createCandidate(final AttributesChangedEvent event, final boolean fromTrx)
	{
		final BigDecimal qty;
		final AttributesKeyWithASI attributes;
		if (fromTrx)
		{
			qty = event.getQty().negate();
			attributes = event.getOldStorageAttributes();
		}
		else
		{
			qty = event.getQty();
			attributes = event.getNewStorageAttributes();
		}

		return Candidate.builderForEventDescr(event.getEventDescriptor())
				.type(null) // TODO: ATTRIBUTES_CHANGED?
				.materialDescriptor(MaterialDescriptor.builder()
						.warehouseId(event.getWarehouseId())
						.quantity(qty)
						.date(event.getDate())
						.productDescriptor(toProductDescriptor(event.getProductId(), attributes))
						.build())
				.build();
	}

	private static ProductDescriptor toProductDescriptor(final int productId, final AttributesKeyWithASI attributes)
	{
		return ProductDescriptor.forProductAndAttributes(productId, attributes.getAttributesKey(), attributes.getAttributeSetInstanceId().getRepoId());
	}

}
