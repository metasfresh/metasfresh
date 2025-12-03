
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
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.request.JsonConfidentialType;
import de.metas.common.rest_api.request.JsonRequestPriority;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.inout.InOutId;
import de.metas.inout.QualityNoteId;
import de.metas.inout.api.IQualityNoteDAO;
import de.metas.inout.model.I_M_QualityNote;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectService;
import de.metas.request.RequestConfidentialType;
import de.metas.request.RequestId;
import de.metas.request.RequestPriority;
import de.metas.request.RequestStatusId;
import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestBL;
import de.metas.request.api.RequestCandidate;
import de.metas.request.api.impl.RequestStatusService;
import de.metas.request.api.impl.RequestTypeService;
import de.metas.rest_api.v2.bpartner.BPartnerMasterdataProvider;
import de.metas.rest_api.v2.ordercandidates.impl.ProductMasterDataProvider;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;
import org.compiere.model.I_R_RequestType;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZoneId;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;
import static org.compiere.util.TimeUtil.asZonedDateTime;

@Service
@RequiredArgsConstructor
public class RequestRestService
{
	private final IRequestBL requestBL = Services.get(IRequestBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQualityNoteDAO qualityNoteDAO = Services.get(IQualityNoteDAO.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final RequestStatusService requestStatusService;
	@NonNull private final BPartnerMasterdataProvider bPartnerMasterdataProvider;
	@NonNull private final ProductMasterDataProvider productMasterDataProvider;
	@NonNull private final ProjectService projectService;
	@NonNull private final RequestTypeService requestTypeService;

	public JsonRRequestUpsertResponse upsert(@NonNull final JsonRRequestUpsertRequest request)
	{
		final I_R_Request persistedRequest = requestBL.createRequest(createRequestCandidate(request));
		return JsonRRequestUpsertResponse.builder()
				.requestId(JsonMetasfreshId.of(persistedRequest.getR_Request_ID()))
				.build();
	}

	@Nullable
	public JsonRRequest getByIdOrNull(@NonNull final RequestId requestId)
	{
		final I_R_Request request = requestBL.getById(requestId);

		return toJson(request);
	}

	private JsonRRequest toJson(@NonNull final I_R_Request request)
	{
		final I_AD_Org org = orgDAO.getById(request.getAD_Org_ID());
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());
		final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);
		final I_R_RequestType requestType = requestTypeService.getById(RequestTypeId.ofRepoId(request.getR_RequestType_ID()));
		final JsonRRequest.JsonRRequestBuilder builder = JsonRRequest.builder()
				.id(JsonMetasfreshId.of(request.getR_Request_ID()))
				.orgCode(org.getValue())
				.requestType(requestType.getInternalName())
				.bpartnerId(JsonMetasfreshId.ofOrNull(request.getC_BPartner_ID()))
				.userId(JsonMetasfreshId.ofOrNull(request.getAD_User_ID()))
				.priority(JsonRequestPriority.fromJson(request.getPriority()))
				.summary(request.getSummary())
				.confidentialityLevel(JsonConfidentialType.fromJson(request.getConfidentialType()))
				.vendorId(JsonMetasfreshId.ofOrNull(request.getC_BP_Vendor_ID()))
				.salesRepId(JsonMetasfreshId.ofOrNull(request.getSalesRep_ID()))
				.dateDelivered(TimeUtil.asLocalDate(request.getDateDelivered(), orgZoneId))
				.dateTrx(TimeUtil.asLocalDate(request.getDateTrx(), orgZoneId))
				.reminderDate(TimeUtil.asLocalDate(request.getReminderDate(), orgZoneId))
				.projectId(JsonMetasfreshId.ofOrNull(request.getC_Project_ID()))
				.productId(JsonMetasfreshId.ofOrNull(request.getM_Product_ID()))
				.orderId(JsonMetasfreshId.ofOrNull(request.getC_Order_ID()))
				.inOutId(JsonMetasfreshId.ofOrNull(request.getM_InOut_ID()))
				.invoiceId(JsonMetasfreshId.ofOrNull(request.getC_Invoice_ID()))
				.paymentId(JsonMetasfreshId.ofOrNull(request.getC_Payment_ID()));
		final QualityNoteId qualityNoteId = QualityNoteId.ofRepoIdOrNull(request.getM_QualityNote_ID());
		if (qualityNoteId != null)
		{
			builder.qualityNoteValue(qualityNoteDAO.getById(qualityNoteId).getValue());
		}
		final RequestStatusId statusId = RequestStatusId.ofRepoIdOrNull(request.getR_Status_ID());
		if (statusId != null)
		{
			builder.statusName(requestStatusService.getById(statusId).getName());
		}
		return builder.build();
	}

	private RequestCandidate createRequestCandidate(final @NonNull JsonRRequestUpsertRequest request)
	{
		final I_AD_Org org = orgDAO.getById(retrieveOrgIdOrDefault(request.getOrgCode()));
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());
		final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);

		RequestTypeId requestTypeId = requestTypeService.retrieveByInternalName(request.getRequestType());
		if (requestTypeId == null)
		{
			requestTypeId = requestTypeService.retrieveCustomerRequestTypeId();
		}

		final RequestConfidentialType confidentialType = request.getConfidentialityLevel() != null
				? RequestConfidentialType.ofCode(request.getConfidentialityLevel().getCode())
				: RequestConfidentialType.PartnerConfidential;

		final BPartnerId bPartnerId = resolveBPartnerIdOrNull(ExternalIdentifier.ofOrNull(request.getBpartnerIdentifier()), orgId, "C_BPartner_ID");
		final BPartnerId vendorId = getVendorIdOrNull(resolveBPartnerIdOrNull(ExternalIdentifier.ofOrNull(request.getVendorIdentifier()), orgId, "C_BP_Vendor_ID"));

		final UserId userId = resolveUserIdOrNull(ExternalIdentifier.ofOrNull(request.getUserIdentifier()), orgId, "AD_User_ID");
		final UserId salesRepId = getSalesRepIdOrNull(resolveUserIdOrNull(ExternalIdentifier.ofOrNull(request.getSalesRepIdentifier()), orgId, "SalesRep_ID"));
		final ProductId productId = resolveProductIdOrNull(ExternalIdentifier.ofOrNull(request.getProductIdentifier()), orgId);

		TableRecordReference recordRef = null;

		final OrderId orderId = request.getOrderId() == null ? null : OrderId.ofRepoId(request.getOrderId().getValue());
		if (orderId != null)
		{
			recordRef = TableRecordReference.of(I_C_Order.Table_Name, orderId.getRepoId());
		}
		final InOutId inOutId = request.getInOutId() == null ? null : InOutId.ofRepoId(request.getInOutId().getValue());
		if (inOutId != null)
		{
			recordRef = TableRecordReference.of(I_M_InOut.Table_Name, inOutId);
		}
		final InvoiceId invoiceId = request.getInvoiceId() == null ? null : InvoiceId.ofRepoId(request.getInvoiceId().getValue());
		if (invoiceId != null)
		{
			recordRef = TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId);
		}
		final PaymentId paymentId = request.getPaymentId() == null ? null : PaymentId.ofRepoId(request.getPaymentId().getValue());
		if (paymentId != null)
		{
			recordRef = TableRecordReference.of(I_C_Payment.Table_Name, paymentId);
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
		if (Check.isNotBlank(request.getStatusName()))
		{
			statusId = requestStatusService.getStatusIdByRequestTypeIdAndName(requestTypeId, request.getStatusName());
		}

		final RequestPriority priority = request.getPriority() == null ? null : RequestPriority.ofCode(request.getPriority().getCode());
		final ProjectId projectId = request.getProjectValue() == null ? null : projectService.getIdByValueOrNull(request.getProjectValue());

		return RequestCandidate.builder()
				.orgId(orgId)
				.requestTypeId(requestTypeId)
				.partnerId(bPartnerId)
				.userId(userId)
				.confidentialType(confidentialType)
				.summary(request.getSummary())
				.dateDelivered(asZonedDateTime(request.getDateDelivered(), orgZoneId))
				.dateTrx(asZonedDateTime(request.getDateTrx(), orgZoneId))
				.reminderDate(asZonedDateTime(request.getReminderDate(), orgZoneId))
				.productId(productId)
				.recordRef(recordRef)
				.performanceType(performanceType)
				.qualityNoteId(qualityNoteId)
				.priority(priority)
				.orderId(orderId)
				.inOutId(inOutId)
				.invoiceId(invoiceId)
				.paymentId(paymentId)
				.projectId(projectId)
				.vendorId(vendorId)
				.salesRepId(salesRepId)
				.statusId(statusId)
				.build();
	}

	@Nullable
	private BPartnerId getVendorIdOrNull(@Nullable final BPartnerId vendorId)
	{
		if (vendorId == null)
		{
			return null;
		}

		final I_C_BPartner bp = bpartnerDAO.getById(vendorId);
		//As per field validation rule, only allow active BPs marked as vendor
		final boolean isValidVendor = bp.isActive() && !bp.isSummary() && bp.isVendor();
		if (!isValidVendor)
		{
			throw new AdempiereException("@NotFound@ @C_BP_Vendor_ID@");
		}
		return vendorId;
	}

	@Nullable
	private UserId getSalesRepIdOrNull(@Nullable final UserId salesRepId)
	{
		if (salesRepId == null)
		{
			return null;
		}

		final I_AD_User user = userDAO.getById(salesRepId);
		//As per field validation rule, only allow AD_User.IsSystemUser = 'Y' as sales rep
		final boolean isValidSalesRep = user.isActive() && user.isSystemUser();
		if (!isValidSalesRep)
		{
			throw new AdempiereException("@NotFound@ @SalesRep_ID@");
		}
		return salesRepId;
	}

	@Nullable
	private ProductId resolveProductIdOrNull(final @Nullable ExternalIdentifier productIdentifier, @NonNull final OrgId orgId)
	{
		if (productIdentifier == null)
		{
			return null;
		}
		final ProductMasterDataProvider.ProductInfo productInfo = productMasterDataProvider.getProductInfo(productIdentifier, orgId);
		if (productInfo == null)
		{
			throw new AdempiereException("@NotFound@ @M_Product_ID@");
		}
		return productInfo.getProductId();
	}

	@Nullable
	private UserId resolveUserIdOrNull(final @Nullable ExternalIdentifier userIdentifier, @NonNull final OrgId orgId, @NonNull final String fieldName)
	{
		if (userIdentifier == null)
		{
			return null;
		}
		return bPartnerMasterdataProvider.resolveUserExternalIdentifier(orgId, userIdentifier)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @" + fieldName + "@"));
	}

	@Nullable
	private BPartnerId resolveBPartnerIdOrNull(final @Nullable ExternalIdentifier bpartnerIdentifier, @NonNull final OrgId orgId, @NonNull final String fieldName)
	{
		if (bpartnerIdentifier == null)
		{
			return null;
		}
		return bPartnerMasterdataProvider.resolveBPartnerExternalIdentifier(orgId, bpartnerIdentifier)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @" + fieldName + "@"));
	}
}
