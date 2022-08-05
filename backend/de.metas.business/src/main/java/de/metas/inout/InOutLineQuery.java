/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.inout;

import de.metas.bpartner.BPartnerId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Collection;

@Value
@Builder
public class InOutLineQuery
{
	@Nullable
	ClientAndOrgId clientandOrgId;
	@Nullable
	BPartnerId bPartnerId;
	@Nullable
	ProductId productId;
	@Nullable
	Timestamp dateFrom;
	@Nullable
	Timestamp dateTo;
	@Singular
	@Nullable
	Collection<InOutDocStatus> docStatuses;

	public InOutLineQuery(@Nullable final ClientAndOrgId clientandOrgId, @Nullable final BPartnerId bPartnerId, @Nullable final ProductId productId, @Nullable final Timestamp dateFrom, @Nullable final Timestamp dateTo, @Nullable final Collection<InOutDocStatus> docStatuses)
	{
		if (BPartnerId.toRepoId(bPartnerId) <= 0 && ProductId.toRepoId(productId) <= 0)
		{
			throw new IllegalArgumentException("Either billPartnerId or productId must be set");
		}

		this.clientandOrgId = clientandOrgId;
		this.bPartnerId = bPartnerId;
		this.productId = productId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.docStatuses = docStatuses;
	}


}
