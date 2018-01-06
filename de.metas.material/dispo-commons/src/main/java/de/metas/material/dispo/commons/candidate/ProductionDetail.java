package de.metas.material.dispo.commons.candidate;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo
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
@Value
public class ProductionDetail
{
	int plantId;

	int uomId;

	int productPlanningId;

	int productBomLineId;

	String description;

	int ppOrderId;

	String ppOrderDocStatus;

	int ppOrderLineId;

	@Builder
	private ProductionDetail(
			int plantId,
			int uomId,
			int productPlanningId,
			int productBomLineId,
			String description,
			int ppOrderId,
			String ppOrderDocStatus,
			int ppOrderLineId)
	{
		final boolean detailForPpOrderHead = productBomLineId <= 0;
		if (detailForPpOrderHead)
		{
			// these two need to be available when using this productionDetail to ppOrder pojo
			checkIdGreaterThanZero("plantId", plantId);
			checkIdGreaterThanZero("uomId", uomId);
		}
		this.plantId = plantId;
		this.uomId = uomId;

		this.productPlanningId = productPlanningId;
		this.productBomLineId = productBomLineId;
		this.description = description;
		this.ppOrderId = ppOrderId;
		this.ppOrderDocStatus = ppOrderDocStatus;
		this.ppOrderLineId = ppOrderLineId;
	}
}
