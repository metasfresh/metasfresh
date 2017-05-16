package de.metas.material.dispo;

import lombok.Builder;
import lombok.Data;

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
@Data
@Builder
public class ProductionCandidateDetail
{
	/**
	 * Only set if this instance related to a ppOrder header.
	 */
	private final int plantId;

	private final int uomId;

	private final int productPlanningId;

	private final int productBomLineId;

	private final String description;

	private final int ppOrderId;

	private final String ppOrderDocStatus;

	private final int ppOrderLineId;
}
