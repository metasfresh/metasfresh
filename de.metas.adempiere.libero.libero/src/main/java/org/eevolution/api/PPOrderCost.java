package org.eevolution.api;

import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.experimental.Wither;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class PPOrderCost
{
	@NonFinal
	int repoId;

	@NonNull
	CostSegmentAndElement costSegmentAndElement;

	@NonNull
	@Wither
	CostPrice price;

	/** DON'T call it directly. It's called only by API */
	public void setRepoId(final int repoId)
	{
		Check.assumeGreaterThanZero(repoId, "repoId");
		this.repoId = repoId;
	}

	public ProductId getProductId()
	{
		return getCostSegmentAndElement().getProductId();
	}

	public CostElementId getCostElementId()
	{
		return getCostSegmentAndElement().getCostElementId();
	}
}
