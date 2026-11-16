package de.metas.material.dispo.service.event.handler.attributes;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.attributes.AttributesChangedEvent;
import de.metas.material.event.attributes.AttributesKeyWithASI;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import de.metas.util.Services;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

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
public class AttributesChangedEventHandler implements MaterialEventHandler<AttributesChangedEvent>
{
	private static final Logger logger = LogManager.getLogger(AttributesChangedEventHandler.class);

	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final CandidateChangeService candidateChangeHandler;

	public AttributesChangedEventHandler(
			@NonNull final CandidateChangeService candidateChangeHandler)
	{
		this.candidateChangeHandler = candidateChangeHandler;
	}

	@Override
	public Collection<Class<? extends AttributesChangedEvent>> getHandledEventType()
	{
		return ImmutableList.of(AttributesChangedEvent.class);
	}

	@Override
	public void handleEvent(final AttributesChangedEvent event)
	{
		final WarehouseId warehouseId = event.getWarehouseId();
		if (warehouseBL.isIgnoreInMaterialDispo(warehouseId))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(
					"Ignoring {} for M_Warehouse_ID={} (warehouse is excluded from material-dispo: MRP_Exclude or IsDropShipWarehouse)",
					event.getClass().getSimpleName(),
					WarehouseId.toRepoId(warehouseId));
			return;
		}

		final Candidate fromCandidate = createCandidate(event, CandidateType.ATTRIBUTES_CHANGED_FROM);

		final MaterialDispoGroupId groupId = candidateChangeHandler.onCandidateNewOrChange(fromCandidate)
				.getEffectiveGroupId()
				.orElseThrow(() -> new AdempiereException("No groupId"));

		final Candidate toCandidate = createCandidate(event, CandidateType.ATTRIBUTES_CHANGED_TO)
				.withGroupId(groupId);
		candidateChangeHandler.onCandidateNewOrChange(toCandidate);
	}

	private Candidate createCandidate(final AttributesChangedEvent event, final CandidateType type)
	{
		final BigDecimal qty;
		final AttributesKeyWithASI attributes;
		if (CandidateType.ATTRIBUTES_CHANGED_FROM.equals(type))
		{
			qty = event.getQty().negate();
			attributes = event.getOldStorageAttributes();
		}
		else if (CandidateType.ATTRIBUTES_CHANGED_TO.equals(type))
		{
			qty = event.getQty();
			attributes = event.getNewStorageAttributes();
		}
		else
		{
			throw new AdempiereException("Invalid type: " + type); // really shall not happen
		}

		return Candidate.builderForEventDescriptor(event.getEventDescriptor())
				.type(type)
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
		return ProductDescriptor.forProductAndAttributes(productId,
														 CoalesceUtil.coalesceNotNull(attributes.getAttributesKey(), AttributesKey.NONE),
														 attributes.getAttributeSetInstanceId().getRepoId());
	}

}
