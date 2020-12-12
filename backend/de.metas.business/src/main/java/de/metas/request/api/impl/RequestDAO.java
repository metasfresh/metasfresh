package de.metas.request.api.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.inout.QualityNoteId;
import de.metas.order.model.I_C_Order;
import de.metas.product.ProductId;
import de.metas.request.RequestId;
import de.metas.request.api.IRequestDAO;
import de.metas.request.api.RequestCandidate;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;
import org.compiere.util.TimeUtil;

import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	@Override
	public I_R_Request createRequest(@NonNull final RequestCandidate candidate)
	{
		final I_R_Request request = newInstance(I_R_Request.class);
		;
		request.setSummary(candidate.getSummary());
		request.setConfidentialType(candidate.getConfidentialType());
		request.setAD_Org_ID(candidate.getOrgId().getRepoId());
		request.setM_Product_ID(ProductId.toRepoId(candidate.getProductId()));
		request.setAD_Table_ID(candidate.getRecordRef() != null ? candidate.getRecordRef().getAD_Table_ID() : -1);
		request.setRecord_ID(candidate.getRecordRef() != null ? candidate.getRecordRef().getRecord_ID() : -1);
		request.setC_BPartner_ID(BPartnerId.toRepoId(candidate.getPartnerId()));
		request.setC_Order_ID(candidate.getRecordRef() != null ?
				candidate.getRecordRef().getTableName().equals(I_C_Order.Table_Name) ? candidate.getRecordRef().getRecord_ID() : -1
				: -1);
		request.setM_InOut_ID(candidate.getRecordRef() != null ?
				candidate.getRecordRef().getTableName().equals(I_M_InOut.Table_Name) ? candidate.getRecordRef().getRecord_ID() : -1
				: -1);

		request.setDateTrx(SystemTime.asTimestamp());
		request.setAD_User_ID(UserId.toRepoId(candidate.getUserId()));
		request.setR_RequestType_ID(candidate.getRequestTypeId().getRepoId());
		request.setM_QualityNote_ID(QualityNoteId.toRepoId(candidate.getQualityNoteId()));
		request.setPerformanceType(candidate.getPerformanceType());
		request.setDateDelivered(TimeUtil.asTimestamp(candidate.getDateDelivered()));

		saveRecord(request);

		return request;
	}

	@Override
	public I_R_Request createEmptyRequest()
	{
		final I_R_Request request = newInstance(I_R_Request.class);
		request.setSummary("");
		return request;
	}

	@Override
	public Stream<RequestId> streamRequestIdsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_R_Request.class)
				.addEqualsFilter(I_R_Request.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.create()
				.listIds(RequestId::ofRepoId)
				.stream();
	}
}
