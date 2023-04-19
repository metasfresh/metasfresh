package de.metas.costing;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.product.ProductId;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.business
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

public interface ICostDetailRepository
{
	CostDetail create(CostDetail.CostDetailBuilder costDetailBuilder);

	CostDetail updateDateAcct(@NonNull CostDetail costDetail, @NonNull Instant newDateAcct);

	void delete(CostDetail costDetail);

	Optional<CostDetail> firstOnly(CostDetailQuery query);

	Stream<CostDetail> stream(CostDetailQuery query);

	ImmutableList<CostDetail> list(@NonNull CostDetailQuery query);

	default ImmutableList<CostDetail> listByDocumentRef(@NonNull final CostingDocumentRef documentRef)
	{
		return list(CostDetailQuery.builder()
				.documentRef(documentRef)
				.orderBy(CostDetailQuery.OrderBy.ID_ASC)
				.build());
	}

	default ImmutableList<CostDetail> listByDocumentRefAndAcctSchemaId(
			@NonNull final CostingDocumentRef documentRef,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		return list(CostDetailQuery.builder()
				.documentRef(documentRef)
				.acctSchemaId(acctSchemaId)
				.orderBy(CostDetailQuery.OrderBy.ID_ASC)
				.build());
	}

	boolean hasCostDetailsByProductId(ProductId productId);
}
