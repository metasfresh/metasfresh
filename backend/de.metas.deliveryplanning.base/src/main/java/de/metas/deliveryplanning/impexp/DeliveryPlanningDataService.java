/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.deliveryplanning.impexp;

import de.metas.impexp.DataImportRunId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPlanningDataService
{
	private final DeliveryPlanningDataRepository deliveryPlanningDataRepository;

	public DeliveryPlanningDataService(@NonNull final DeliveryPlanningDataRepository deliveryPlanningDataRepository)
	{
		this.deliveryPlanningDataRepository = deliveryPlanningDataRepository;
	}

	@NonNull
	public DeliveryPlanningData getById(@NonNull final DeliveryPlanningDataId deliveryPlanningDataId)
	{
		return deliveryPlanningDataRepository.getById(deliveryPlanningDataId);
	}

	public void save(@NonNull final DeliveryPlanningData deliveryPlanningData)
	{
		deliveryPlanningDataRepository.save(deliveryPlanningData);
	}

	public int assignDeliveryPlanningDataIdToDataImportRunId(@NonNull final DeliveryPlanningDataId deliveryPlanningDataId, @NonNull final DataImportRunId dataImportRunId)
	{
		return deliveryPlanningDataRepository.assignDeliveryPlanningDataIdToDataImportRunId(deliveryPlanningDataId, dataImportRunId);
	}
}
