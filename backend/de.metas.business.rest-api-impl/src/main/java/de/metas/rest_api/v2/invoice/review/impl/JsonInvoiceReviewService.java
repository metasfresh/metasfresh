/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.invoice.review.impl;

import de.metas.RestUtils;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.CoalesceUtil;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceQuery;
import de.metas.invoice.review.InvoiceReview;
import de.metas.invoice.review.InvoiceReviewId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.impl.InvoiceReviewRepository;
import de.metas.organization.OrgId;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.invoice.JsonCreateInvoiceReviewResponse;
import de.metas.rest_api.v2.invoice.JsonCreateInvoiceReviewResponseResult;
import de.metas.rest_api.v2.invoice.JsonInvoiceReviewUpsertItem;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class JsonInvoiceReviewService
{
	private final InvoiceReviewRepository invoiceReviewRepository;
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	public JsonInvoiceReviewService(@NonNull final InvoiceReviewRepository invoiceReviewRepository)
	{
		this.invoiceReviewRepository = invoiceReviewRepository;
	}

	public Optional<JsonCreateInvoiceReviewResponse> upsert(@NonNull final JsonInvoiceReviewUpsertItem jsonInvoiceReviewUpsertItem)
	{
		final OrgId orgId = getOrgId(jsonInvoiceReviewUpsertItem.getOrgCode());
		final InvoiceQuery invoiceQuery = createInvoiceQueryOrNull(jsonInvoiceReviewUpsertItem, orgId);
		if (invoiceQuery == null)
		{
			return Optional.empty();
		}
		final Optional<InvoiceId> invoiceId = invoiceDAO.retrieveIdByInvoiceQuery(invoiceQuery);
		if (!invoiceId.isPresent())
		{
			return Optional.empty();
		}

		final Map<String, Object> extendedProps = CoalesceUtil.coalesceNotNull(jsonInvoiceReviewUpsertItem.getExtendedProps(), Collections.emptyMap());
		final InvoiceReview invoiceReview = invoiceReviewRepository.getOrCreateByInvoiceId(invoiceId.get(), orgId)
				.withExtendedProps(extendedProps);
		final InvoiceReviewId id = invoiceReviewRepository.save(invoiceReview);
		return Optional.of(JsonCreateInvoiceReviewResponse.builder()
				.result(JsonCreateInvoiceReviewResponseResult.builder()
						.reviewId(JsonMetasfreshId.of(id.getRepoId()))
						.build())
				.build());
	}

	@Nullable
	private InvoiceQuery createInvoiceQueryOrNull(@NonNull final JsonInvoiceReviewUpsertItem jsonInvoiceReviewUpsertItem, @NonNull final OrgId orgId)
	{

		final InvoiceQuery.InvoiceQueryBuilder invoiceQueryBuilder = InvoiceQuery.builder()
				.orgId(orgId);

		final IdentifierString identifierString = IdentifierString.ofOrNull(jsonInvoiceReviewUpsertItem.getInvoiceIdentifier());

		if (identifierString == null)
		{
			return null;
		}
		switch (identifierString.getType())
		{
			case METASFRESH_ID:
				invoiceQueryBuilder.invoiceId(identifierString.asMetasfreshId().getValue());
				break;
			case EXTERNAL_ID:
				invoiceQueryBuilder.externalId(identifierString.asExternalId());
				break;
			default:
				throw new AdempiereException("Invalid identifierString: " + identifierString);
		}
		return invoiceQueryBuilder.build();
	}

	private static OrgId getOrgId(@Nullable final String orgCode)
	{
		return Optional.ofNullable(RestUtils.retrieveOrgIdOrDefault(orgCode))
				.filter(OrgId::isRegular)
				.orElseThrow(() -> new AdempiereException("Cannot find the orgId from either orgCode=" + orgCode + " or the current user's context."));
	}
}
