package de.metas.rest_api.invoicecandidates.impl;

import de.metas.common.rest_api.v2.JsonDocTypeInfo;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.order.impl.DocTypeService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgQuery;
import de.metas.rest_api.invoicecandidates.request.JsonEnqueueForInvoicingRequest;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidateReference;
import de.metas.rest_api.invoicecandidates.response.JsonEnqueueForInvoicingResponse;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.web.exception.InvalidEntityException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Service
@RequiredArgsConstructor
public class InvoiceJsonConverters
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final DocTypeService docTypeService;

	@NonNull
	public final InvoiceCandidateMultiQuery fromJson(
			@NonNull final List<JsonInvoiceCandidateReference> invoiceCandidates)
	{
		validateCandidates(invoiceCandidates);

		final InvoiceCandidateMultiQuery.InvoiceCandidateMultiQueryBuilder multiQuery = InvoiceCandidateMultiQuery.builder();

		invoiceCandidates.forEach(invoiceCandidate -> {
			final OrgId orgId = getOrgId(invoiceCandidate);

			multiQuery.query(InvoiceCandidateQuery.builder()
					.orgId(orgId)
					.externalIds(getExternalHeaderIdWithExternalLineIds(invoiceCandidate))
					.orderDocTypeId(getOrderDocTypeId(invoiceCandidate.getOrderDocumentType(), orgId))
					.orderDocumentNo(invoiceCandidate.getOrderDocumentNo())
					.orderLines(invoiceCandidate.getOrderLines())
					.build());
		});

		return multiQuery.build();
	}

	@NonNull
	public static JsonEnqueueForInvoicingResponse toJson(@NonNull final IInvoiceCandidateEnqueueResult enqueueResult)
	{
		return JsonEnqueueForInvoicingResponse.builder()
				.invoiceCandidateEnqueuedCount(enqueueResult.getInvoiceCandidateEnqueuedCount())
				.summaryTranslated(enqueueResult.getSummaryTranslated(Env.getCtx()))
				.totalNetAmtToInvoiceChecksum(enqueueResult.getTotalNetAmtToInvoiceChecksum())
				.workpackageEnqueuedCount(enqueueResult.getWorkpackageEnqueuedCount())
				.workpackageQueueSizeBeforeEnqueueing(enqueueResult.getWorkpackageQueueSizeBeforeEnqueueing())
				.build();
	}

	@NonNull
	public static InvoicingParams createInvoicingParams(@NonNull final JsonEnqueueForInvoicingRequest request)
	{
		return InvoicingParams.builder()
				.dateAcct(request.getDateAcct())
				.dateInvoiced(request.getDateInvoiced())
				.ignoreInvoiceSchedule(request.getIgnoreInvoiceSchedule())
				.poReference(request.getPoReference())
				.supplementMissingPaymentTermIds(request.getSupplementMissingPaymentTermIds())
				.updateLocationAndContactForInvoice(request.getUpdateLocationAndContactForInvoice())
				.completeInvoices(request.getCompleteInvoices())
				.build();
	}

	@Nullable
	private static ExternalHeaderIdWithExternalLineIds getExternalHeaderIdWithExternalLineIds(@NonNull final JsonInvoiceCandidateReference invoiceCandidate)
	{
		return Optional.ofNullable(invoiceCandidate.getExternalHeaderId())
				.map(externalHeaderId -> ExternalHeaderIdWithExternalLineIds.builder()
						.externalHeaderId(JsonExternalIds.toExternalId(externalHeaderId))
						.externalLineIds(JsonExternalIds.toExternalIds(invoiceCandidate.getExternalLineIds()))
						.build())
				.orElse(null);
	}

	private static void validateCandidates(@NonNull final List<JsonInvoiceCandidateReference> invoiceCandidates)
	{
		if (invoiceCandidates.isEmpty())
		{
			throw new InvalidEntityException(TranslatableStrings.constant("The request's invoiceCandidates array may not be empty"));
		}

		invoiceCandidates.forEach(invoiceCandidate -> {
			validateReferencePresence(invoiceCandidate);
			validateExternalLineIdsConsistency(invoiceCandidate);
			validateOrderLinesConsistency(invoiceCandidate);
			validateReferenceTypeExclusivity(invoiceCandidate);
			validateOrderDocumentTypeWithOrgCode(invoiceCandidate);
		});
	}

	private static void validateReferencePresence(@NonNull final JsonInvoiceCandidateReference invoiceCandidate)
	{
		final boolean hasAnyReference =
				invoiceCandidate.getExternalHeaderId() != null
						|| invoiceCandidate.getOrderDocumentNo() != null
						|| invoiceCandidate.getOrgCode() != null
						|| invoiceCandidate.getOrderDocumentType() != null;

		if (!hasAnyReference)
		{
			throw new InvalidEntityException(TranslatableStrings.constant(
					"Must have at least one of externalHeaderId, orderDocumentNo, orgCode, or orderDocumentType."));
		}
	}

	private static void validateExternalLineIdsConsistency(@NonNull final JsonInvoiceCandidateReference invoiceCandidate)
	{
		if (!Check.isEmpty(invoiceCandidate.getExternalLineIds()) && invoiceCandidate.getExternalHeaderId() == null)
		{
			throw new InvalidEntityException(TranslatableStrings.constant(
					"externalLineIds were provided, but externalHeaderId is missing."));
		}
	}

	private static void validateOrderLinesConsistency(@NonNull final JsonInvoiceCandidateReference invoiceCandidate)
	{
		if (!Check.isEmpty(invoiceCandidate.getOrderLines()) && invoiceCandidate.getOrderDocumentNo() == null)
		{
			throw new InvalidEntityException(TranslatableStrings.constant(
					"orderLines were provided, but orderDocumentNo is missing. Order lines require an order document number."));
		}
	}

	private static void validateReferenceTypeExclusivity(@NonNull final JsonInvoiceCandidateReference invoiceCandidate)
	{
		if (invoiceCandidate.getExternalHeaderId() != null && invoiceCandidate.getOrderDocumentNo() != null)
		{
			throw new InvalidEntityException(TranslatableStrings.constant(
					"Both externalHeaderId and orderDocumentNo are provided. Only one reference type should be used."));
		}
	}

	private static void validateOrderDocumentTypeWithOrgCode(@NonNull final JsonInvoiceCandidateReference invoiceCandidate)
	{
		if (invoiceCandidate.getOrderDocumentType() != null && Check.isBlank(invoiceCandidate.getOrgCode()))
		{
			throw new InvalidEntityException(TranslatableStrings.constant(
					"When specifying Order Document Type, the org code also has to be specified"));
		}
	}

	@Nullable
	private OrgId getOrgId(@NonNull final JsonInvoiceCandidateReference invoiceCandidate)
	{
		final String orgCode = invoiceCandidate.getOrgCode();
		if (Check.isNotBlank(orgCode))
		{
			return orgDAO.retrieveOrgIdBy(OrgQuery.ofValue(orgCode))
					.orElseThrow(() -> MissingResourceException.builder()
							.resourceName("organisation")
							.resourceIdentifier("(val-)" + orgCode)
							.build());
		}
		else
		{
			return null;
		}
	}

	@Nullable
	private DocTypeId getOrderDocTypeId(
			@Nullable final JsonDocTypeInfo orderDocumentType,
			@Nullable final OrgId orgId)
	{
		if (orderDocumentType == null)
		{
			return null;
		}

		if (orgId == null)
		{
			throw new InvalidEntityException(TranslatableStrings.constant(
					"When specifying Order Document Type, the org code also has to be specified"));
		}

		final DocBaseType docBaseType = Optional.of(orderDocumentType)
				.map(JsonDocTypeInfo::getDocBaseType)
				.map(DocBaseType::ofCode)
				.orElse(null);

		final DocSubType subType = Optional.of(orderDocumentType)
				.map(JsonDocTypeInfo::getDocSubType)
				.map(DocSubType::ofNullableCode)
				.orElse(DocSubType.ANY);

		return Optional.ofNullable(docBaseType)
				.map(baseType -> docTypeService.getDocTypeId(docBaseType, subType, orgId))
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("DocType")
						.parentResource(orderDocumentType)
						.build());
	}
}
