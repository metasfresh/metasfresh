package de.metas.material.planning.event;

import java.util.Date;

import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
@Wither
public class MaterialRequest implements IMaterialRequest
{
	Quantity qtyToSupply;

	@Default
	int mrpDemandOrderLineSOId = -1;

	@Default
	int mrpDemandBPartnerId = -1;

	IMaterialPlanningContext  mrpContext;

	Date demandDate;
}
