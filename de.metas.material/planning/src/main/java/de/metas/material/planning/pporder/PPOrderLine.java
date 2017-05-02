package de.metas.material.planning.pporder;

import java.math.BigDecimal;
import java.util.Date;

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

/**
 * Not needed, because itcan be taken directly from the parent ppOrder:
 * <ul>
 * <li>orgId</li>
 * <li>warehouseId</li>
 * <li>locatorId</li>
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Data
@Builder
@Wither
public class PPOrderLine
{

	@NonNull
	private final Integer changeNoticeId;

	private final String description;

	private final String help;

	private final BigDecimal assay;

	@NonNull
	private final BigDecimal qtyBatch;

	@NonNull
	private final BigDecimal qtyBOM;

	@NonNull
	private final Boolean qtyPercentage;

	@NonNull
	private final String componentType;

	@NonNull
	private final Integer uomId;

	private final BigDecimal forecast;

	@NonNull
	private final Boolean critical;

	@NonNull
	private final String issueMethod;

	private final Integer leadTimeOffset;

	@NonNull
	private final Integer productId;

	private final BigDecimal scrap;

	@NonNull
	private final Date validFrom;

	private final Date validTo;

	private final String backflushGroup;

	private final String variantGroup; // 06005

	private final Integer attributeSetInstanceId;

	@NonNull
	private final BigDecimal qtyRequired;

}
