
/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.rest_api.request;

import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.inout.QualityNoteId;
import de.metas.inout.api.IQualityNoteDAO;
import de.metas.inout.model.I_M_QualityNote;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.request.RequestStatusId;
import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestBL;
import de.metas.request.api.RequestCandidate;
import de.metas.request.api.impl.RequestService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.order.sales.OrderService;
import de.metas.rest_api.v2.ordercandidates.impl.MasterdataProvider;
import de.metas.rest_api.v2.ordercandidates.impl.ProductMasterDataProvider;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;
import org.compiere.model.X_R_Request;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
@RequiredArgsConstructor
public class RequestRestService
{
	private final IRequestBL requestBL = Services.get(IRequestBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQualityNoteDAO qualityNoteDAO = Services.get(IQualityNoteDAO.class);
	@NonNull private final OrderService orderService;
	@NonNull private final RequestService requestService;

	public JsonRRequest upsert(@NonNull final JsonRRequest request, final MasterdataProvider masterdataProvider)
	{
		final I_R_Request persistedRequest = requestBL.createRequest(createRequestCandidate(request, masterdataProvider));
		return request.withRequestId(JsonMetasfreshId.of(persistedRequest.getR_Request_ID()));
	}

	private RequestCandidate createRequestCandidate(final @NonNull JsonRRequest request, final MasterdataProvider masterdataProvider)
	{
		final I_AD_Org org = orgDAO.getById(retrieveOrgIdOrDefault(request.getOrgCode()));
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final RequestTypeId requestTypeId = requestService.retrieveByInternalName(request.getRequestType());
		if (requestTypeId == null)
		{
			throw new AdempiereException("@Invalid@ @R_RequestType_ID@");
		}

		final String confidentialType = request.getConfidentialityLevel() != null
				? request.getConfidentialityLevel().getCode()
				: X_R_Request.CONFIDENTIALTYPE_Internal;

		final ZonedDateTime dateDelivered = request.getDateDelivered() != null
				? request.getDateDelivered().atStartOfDay(ZoneId.systemDefault())
				: ZonedDateTime.now();

		final UserId userId = resolveUserIdOrNull(ExternalIdentifier.ofOrNull(request.getUserIdentifier()), masterdataProvider, orgId);
		final ProductId productId = resolveProductIdOrNull(ExternalIdentifier.ofOrNull(request.getProductIdentifier()), masterdataProvider, orgId);
		final BPartnerId bPartnerId = resolveBPartnerIdOrNull(ExternalIdentifier.ofOrNull(request.getBpartnerIdentifier()), masterdataProvider, orgId);

		final OrderId orderId = resolveOrderId(IdentifierString.ofOrNull(request.getOrderIdentifier()), orgId);
		TableRecordReference recordRef = null;
		if (orderId != null)
		{
			recordRef = TableRecordReference.of(I_C_Order.Table_Name, orderId.getRepoId());
		}

		final JsonMetasfreshId inOutId = request.getInOutId();
		if (inOutId != null)
		{
			recordRef = TableRecordReference.of(I_M_InOut.Table_Name, inOutId.getValue());
		}

		final String qualityNote = request.getQualityNote();
		QualityNoteId qualityNoteId = null;
		String performanceType = null;
		if (qualityNote != null)
		{
			final I_M_QualityNote poQualityNote = qualityNoteDAO.retrieveQualityNoteForValue(InterfaceWrapperHelper.getCtx(org), qualityNote);
			qualityNoteId = QualityNoteId.ofRepoId(poQualityNote.getM_QualityNote_ID());
			performanceType = poQualityNote.getPerformanceType();
		}

		RequestStatusId statusId = null;
		if (Check.isNotBlank(request.getStatus()))
		{
			statusId = requestService.getStatusIdByRequestTypeIdAndName(requestTypeId, request.getStatus());
		}
		return RequestCandidate.builder()
				.orgId(orgId)
				.requestTypeId(requestTypeId)
				.partnerId(bPartnerId)
				.userId(userId)
				.confidentialType(confidentialType)
				.summary(request.getSummary())
				.dateDelivered(dateDelivered)
				.productId(productId)
				.recordRef(recordRef)
				.performanceType(performanceType)
				.qualityNoteId(qualityNoteId)
				.isEscalated(request.getIsEscalated())
				.isSelfService(request.getIsSelfService())
				.result(request.getStatus())
				.statusId(statusId)
				.build();
	}

	@Nullable
	private static ProductId resolveProductIdOrNull(final @Nullable ExternalIdentifier productIdentifier, @NonNull final MasterdataProvider masterdataProvider, @NonNull final OrgId orgId)
	{
		if (productIdentifier == null)
		{
			return null;
		}
		final ProductMasterDataProvider.ProductInfo productInfo = masterdataProvider.getProductInfo(productIdentifier, orgId);
		if (productInfo == null)
		{
			throw new AdempiereException("@NotFound@ @M_Product_ID@");
		}
		return productInfo.getProductId();
	}

	@Nullable
	private static UserId resolveUserIdOrNull(final @Nullable ExternalIdentifier userIdentifier, @NonNull final MasterdataProvider masterdataProvider, @NonNull final OrgId orgId)
	{
		if (userIdentifier == null)
		{
			return null;
		}
		return masterdataProvider.resolveUserExternalIdentifier(orgId, userIdentifier)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @AD_User_ID@"));
	}

	@Nullable
	private static BPartnerId resolveBPartnerIdOrNull(final @Nullable ExternalIdentifier bpartnerIdentifier, @NonNull final MasterdataProvider masterdataProvider, @NonNull final OrgId orgId)
	{
		if (bpartnerIdentifier == null)
		{
			return null;
		}
		return masterdataProvider.resolveBPartnerExternalIdentifier(orgId, bpartnerIdentifier)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @C_BPartner_ID@"));
	}

	@Nullable
	private OrderId resolveOrderId(final @Nullable IdentifierString identifierString, @NonNull final OrgId orgId)
	{
		if (identifierString == null)
		{
			return null;
		}
		return orderService.resolveOrderId(identifierString, orgId)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @C_Order_ID@"));
	}
}
