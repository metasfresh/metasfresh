/*
 * #%L
 * de.metas.business
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

package de.metas.material.maturing;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class MaturingConfigLine
{
	@NonNull
	MaturingConfigLineId id;
	@NonNull
	MaturingConfigId maturingConfigId;

	@NonNull ProductId fromProductId;
	@NonNull ProductId maturedProductId;
	@NonNull @Builder.Default OrgId orgId = OrgId.ANY;
	@NonNull @Builder.Default Integer maturityAge = Integer.valueOf(0);
	public MaturingConfigLineId getIdNotNull() {return Check.assumeNotNull(id, "Maturing Config Line is saved: {}", this);}
}
