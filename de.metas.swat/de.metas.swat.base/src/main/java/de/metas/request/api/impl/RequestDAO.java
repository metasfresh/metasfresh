package de.metas.request.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_RequestType;
import org.compiere.model.X_R_Request;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import com.google.common.annotations.VisibleForTesting;

import de.metas.i18n.IMsgBL;
import de.metas.inout.api.IQualityNoteDAO;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inout.model.I_M_QualityNote;
import de.metas.request.api.IRequestDAO;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.request.model.I_R_Request;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class RequestDAO implements IRequestDAO
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

		final I_R_RequestType requestType = getRequestType(inout.isSOTrx());

		final I_M_QualityNote qualityNote = getQualityNoteOrNull(line);

		final String performanceType = qualityNote == null ? null : qualityNote.getPerformanceType();

		final String summary = msgBL.getMsg(Env.getCtx(), MSG_R_Request_From_InOut_Summary);

		final RequestFromDocumentLine requestCandidate = RequestFromDocumentLine.builder()
				.summary(summary)
				.confidentialType(X_R_Request.CONFIDENTIALTYPE_Internal)
				.orgId(line.getAD_Org_ID())
				.productId(line.getM_Product_ID())
				.tableId(getTableId(I_M_InOut.class))
				.recordId(inout.getM_InOut_ID())
				.requestTypeId(requestType.getR_RequestType_ID())
				.partnerId(inout.getC_BPartner_ID())
				.userId(inout.getAD_User_ID())
				.dateDelivered(inout.getMovementDate())
				.qualityNote(qualityNote)
				.performanceType(performanceType)
				.build();

		return createRequest(requestCandidate);

	}

	private I_M_QualityNote getQualityNoteOrNull(final I_M_InOutLine line)
	{
		final IQualityNoteDAO qualityNoteDAO = Services.get(IQualityNoteDAO.class);

		final I_M_Attribute qualityNoteAttribute = qualityNoteDAO.getQualityNoteAttribute(Env.getCtx());

		if (qualityNoteAttribute == null)
		{
			// nothing to do. Quality Note attribute not defined
			return null;
		}

		final I_M_AttributeInstance qualityNoteAI = Services.get(IAttributeDAO.class).retrieveAttributeInstance(line.getM_AttributeSetInstance(), qualityNoteAttribute.getM_Attribute_ID());

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

	private I_R_RequestType getRequestType(final boolean isSOTrx)
	{
		final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);

		return isSOTrx ? requestTypeDAO.retrieveCustomerRequestType() : requestTypeDAO.retrieveVendorRequestType();

	}

	@Override
	public I_R_Request createRequestFromDDOrderLine(@NonNull final I_DD_OrderLine ddOrderLine)
	{

		final I_DD_Order ddOrder = ddOrderLine.getDD_Order();

		final I_R_RequestType requestType = getRequestType(ddOrder.isSOTrx());

		final RequestFromDocumentLine requestCandidate = RequestFromDocumentLine.builder()
				.summary(ddOrderLine.getDescription()) // TODO: Decide what to put here
				.confidentialType(X_R_Request.CONFIDENTIALTYPE_Internal)
				.orgId(ddOrderLine.getAD_Org_ID())
				.productId(ddOrderLine.getM_Product_ID())
				.tableId(getTableId(I_DD_Order.class))
				.recordId(ddOrder.getDD_Order_ID())
				.requestTypeId(requestType.getR_RequestType_ID())
				.partnerId(ddOrder.getC_BPartner_ID())
				.userId(ddOrder.getAD_User_ID())
				.dateDelivered(ddOrder.getDatePromised())
				.build();

		return createRequest(requestCandidate);
	}

	@Value
	@Builder
	private static class RequestFromDocumentLine
	{

		int orgId;
		int productId;
		int tableId;
		int recordId;
		int partnerId;
		int userId;
		int requestTypeId;

		@NonNull
		Timestamp dateDelivered;
		@NonNull
		String confidentialType;
		@NonNull
		String summary;

		@Nullable
		I_M_QualityNote qualityNote;
		@Nullable
		String performanceType;

	}

	private I_R_Request createRequest(final RequestFromDocumentLine requestCandidate)
	{
		final I_R_Request request = newInstance(I_R_Request.class);

		request.setSummary(requestCandidate.getSummary());
		request.setConfidentialType(requestCandidate.getConfidentialType());
		request.setAD_Org_ID(requestCandidate.getOrgId());
		request.setM_Product_ID(requestCandidate.getProductId());
		request.setAD_Table_ID(requestCandidate.getTableId());
		request.setRecord_ID(requestCandidate.getRecordId());
		request.setC_BPartner_ID(requestCandidate.getPartnerId());
		request.setAD_User_ID(requestCandidate.getUserId());
		request.setR_RequestType_ID(requestCandidate.getRequestTypeId());
		request.setM_QualityNote(requestCandidate.getQualityNote());
		request.setPerformanceType(requestCandidate.getPerformanceType());
		request.setDateDelivered(requestCandidate.getDateDelivered());

		save(request);

		return request;
	}
}
