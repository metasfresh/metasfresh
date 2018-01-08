package de.metas.material.dispo.commons.candidate;

import java.math.BigDecimal;

import org.adempiere.util.Check;

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

	int productPlanningId;

	int productBomLineId;

	String description;

	int ppOrderId;

	String ppOrderDocStatus;

	int ppOrderLineId;

	boolean advised;

	BigDecimal plannedQty;

	BigDecimal actualQty;

	@Builder(toBuilder = true)
	private ProductionDetail(
			int plantId,
			int productPlanningId,
			int productBomLineId,
			String description,
			int ppOrderId,
			String ppOrderDocStatus,
			int ppOrderLineId,
			boolean advised,
			BigDecimal plannedQty,
			BigDecimal actualQty)
	{
		this.advised = advised;

		final boolean detailForPpOrderHead = productBomLineId <= 0;
		if (advised && detailForPpOrderHead)
		{
			// these two need to be available when using this productionDetail to ppOrder pojo
			Check.errorIf(plantId <= 0, "Parameter plantId needs to be >= 0 for and advised PPOrder 'Header' productionDetail");
		}

		this.plantId = plantId;

		this.productPlanningId = productPlanningId;
		this.productBomLineId = productBomLineId;
		this.description = description;
		this.ppOrderId = ppOrderId;
		this.ppOrderDocStatus = ppOrderDocStatus;
		this.ppOrderLineId = ppOrderLineId;
		this.plannedQty = plannedQty;
		this.actualQty = actualQty;
	}
}
