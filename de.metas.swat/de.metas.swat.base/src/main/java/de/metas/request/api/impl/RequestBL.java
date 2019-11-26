package de.metas.request.api.impl;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;
import org.compiere.model.X_R_Request;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import com.google.common.annotations.VisibleForTesting;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.IMsgBL;
import de.metas.inout.QualityNoteId;
import de.metas.inout.api.IQualityNoteDAO;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inout.model.I_M_QualityNote;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestBL;
import de.metas.request.api.IRequestDAO;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.request.api.RequestCandidate;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
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

public class RequestBL implements IRequestBL
{
	@VisibleForTesting
	protected static final String MSG_R_Request_From_InOut_Summary = "R_Request_From_InOut_Summary";

	@Override
	public I_R_Request createRequestFromInOutLine(@NonNull final I_M_InOutLine line)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		if (Check.isEmpty(line.getQualityDiscountPercent()))
		{
			// Shall not happen. Do nothing
			return null;
		}

		final I_M_InOut inout = line.getM_InOut();

		final RequestTypeId requestTypeId = getRequestTypeId(SOTrx.ofBoolean(inout.isSOTrx()));

		final I_M_QualityNote qualityNote = getQualityNoteOrNull(line);

		final String performanceType = qualityNote == null ? null : qualityNote.getPerformanceType();
		final QualityNoteId qualityNoteId = qualityNote == null ? null : QualityNoteId.ofRepoId(qualityNote.getM_QualityNote_ID());

		final String summary = msgBL.getMsg(Env.getCtx(), MSG_R_Request_From_InOut_Summary);

		final RequestCandidate requestCandidate = RequestCandidate.builder()
				.summary(summary)
				.confidentialType(X_R_Request.CONFIDENTIALTYPE_Internal)
				.orgId(OrgId.ofRepoId(line.getAD_Org_ID()))
				.productId(ProductId.ofRepoId(line.getM_Product_ID()))
				.recordRef(TableRecordReference.of(inout))
				.requestTypeId(requestTypeId)
				.partnerId(BPartnerId.ofRepoId(inout.getC_BPartner_ID()))
				.userId(UserId.ofRepoIdOrNull(inout.getAD_User_ID()))
				.dateDelivered(TimeUtil.asZonedDateTime(inout.getMovementDate()))
				.qualityNoteId(qualityNoteId)
				.performanceType(performanceType)
				.build();

		return createRequest(requestCandidate);

	}

	private I_M_QualityNote getQualityNoteOrNull(final I_M_InOutLine line)
	{
		final IQualityNoteDAO qualityNoteDAO = Services.get(IQualityNoteDAO.class);

		final AttributeId qualityNoteAttributeId = qualityNoteDAO.getQualityNoteAttributeId();
		if (qualityNoteAttributeId == null)
		{
			// nothing to do. Quality Note attribute not defined
			return null;
		}

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(line.getM_AttributeSetInstance_ID());
		final I_M_AttributeInstance qualityNoteAI = Services.get(IAttributeDAO.class).retrieveAttributeInstance(asiId, qualityNoteAttributeId);
		if (qualityNoteAI == null)
		{
			// nothing to do. The Quality Note is not in the attribute instance
			return null;
		}

		final String qualityNoteValueFromASI = qualityNoteAI.getValue();
		if (Check.isEmpty(qualityNoteValueFromASI))
		{
			// nothing to do. Quality Note was not set in the Attrbute Set Instance
			return null;
		}

		final I_M_QualityNote qualityNote = qualityNoteDAO.retrieveQualityNoteForValue(Env.getCtx(), qualityNoteValueFromASI);
		Check.assumeNotNull(qualityNote, "QualityNote not null");

		// Note: If the inout line on which the request is based has more than one qualityNotes, only the first one is set into the request
		return qualityNote;

	}

	private RequestTypeId getRequestTypeId(final SOTrx soTrx)
	{
		final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);
		return soTrx.isSales()
				? requestTypeDAO.retrieveCustomerRequestTypeId()
				: requestTypeDAO.retrieveVendorRequestTypeId();
	}

	@Override
	public I_R_Request createRequestFromDDOrderLine(@NonNull final I_DD_OrderLine ddOrderLine)
	{
		final I_DD_Order ddOrder = ddOrderLine.getDD_Order();

		final RequestTypeId requestTypeId = getRequestTypeId(SOTrx.ofBoolean(ddOrder.isSOTrx()));

		final RequestCandidate requestCandidate = RequestCandidate.builder()
				.summary(ddOrderLine.getDescription()) // TODO: Decide what to put here
				.confidentialType(X_R_Request.CONFIDENTIALTYPE_Internal)
				.orgId(OrgId.ofRepoId(ddOrderLine.getAD_Org_ID()))
				.productId(ProductId.ofRepoId(ddOrderLine.getM_Product_ID()))
				.recordRef(TableRecordReference.of(ddOrder))
				.requestTypeId(requestTypeId)
				.partnerId(BPartnerId.ofRepoId(ddOrder.getC_BPartner_ID()))
				.userId(UserId.ofRepoIdOrNull(ddOrder.getAD_User_ID()))
				.dateDelivered(TimeUtil.asZonedDateTime(ddOrder.getDatePromised()))
				.build();

		return createRequest(requestCandidate);
	}

	private I_R_Request createRequest(final RequestCandidate requestCandidate)
	{
		final IRequestDAO requestsRepo = Services.get(IRequestDAO.class);
		return requestsRepo.createRequest(requestCandidate);
	}

}
