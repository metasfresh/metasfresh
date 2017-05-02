package de.metas.material.planning.pporder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.compiere.model.I_S_Resource;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Wither;

/*
 * #%L
 * metasfresh-material-planning
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
@Wither
public class PPOrder
{
	@NonNull
	private final Integer orgId;

	/**
	 * The {@link I_S_Resource#getS_Resource_ID()} of the plant, as specified by the respective product planning record.
	 */
	@NonNull
	private final Integer plantId;

	@NonNull
	private final Integer warehouseId;

	@NonNull
	private final Integer plannerId;

	@NonNull
	private final Integer productId;

	@NonNull
	private final Integer uomId;

	/**
	 * The end product's UOM according to the underlying BOM (whose ID is in {@link #getProductBomId()}). Might differ from {@link #getUomId()}.
	 */
	@NonNull
	private Integer productBomUomId;

	@NonNull
	private final Integer productBomId;

	@NonNull
	private final Integer workflowId;

	@NonNull
	private final Date dateOrdered;

	@NonNull
	private final Date datePromised;

	@NonNull
	private final Date dateStartSchedule;

	@NonNull
	private final BigDecimal quantity;

	/**
	 * Attention, might be {@code null}.
	 */
	private final List<PPOrderLine> lines;
}
