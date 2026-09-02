package de.metas.request.api.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.inout.InOutId;
import de.metas.inout.QualityNoteId;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.order.model.I_C_Order;
import de.metas.payment.PaymentId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.request.RequestId;
import de.metas.request.RequestPriority;
import de.metas.request.RequestStatusId;
import de.metas.request.api.IRequestDAO;
import de.metas.request.api.RequestCandidate;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.stream.Stream;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.common.util.CoalesceUtil.coalesceNotNull;
import static de.metas.common.util.CoalesceUtil.optionalOfFirstNonNullSupplied;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.compiere.util.TimeUtil.asTimestamp;

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
	public I_R_Request getById(@NonNull final RequestId id)
	{
		return InterfaceWrapperHelper.load(id, I_R_Request.class);
	}

	@Override
	public void save(@NonNull final I_R_Request request)
	{
		InterfaceWrapperHelper.saveRecord(request);
	}

	@Override
	public I_R_Request createRequest(@NonNull final RequestCandidate candidate)
	{
		final I_R_Request request = newInstance(I_R_Request.class);

		request.setSummary(candidate.getSummary());
		request.setConfidentialType(candidate.getConfidentialType().getCode());
		request.setAD_Org_ID(candidate.getOrgId().getRepoId());
		request.setM_Product_ID(ProductId.toRepoId(candidate.getProductId()));
		request.setAD_Table_ID(candidate.getRecordRef() != null ? candidate.getRecordRef().getAD_Table_ID() : -1);
		request.setRecord_ID(candidate.getRecordRef() != null ? candidate.getRecordRef().getRecord_ID() : -1);
		request.setC_BPartner_ID(BPartnerId.toRepoId(candidate.getPartnerId()));
		optionalOfFirstNonNullSupplied(candidate::getOrderId,
				() -> getIdFromReferenceOrNull(candidate, I_C_Order.Table_Name, OrderId::ofRepoId))
				.ifPresent(orderId -> request.setC_Order_ID(orderId.getRepoId()));
		optionalOfFirstNonNullSupplied(candidate::getInOutId,
				() -> getIdFromReferenceOrNull(candidate, I_M_InOut.Table_Name, InOutId::ofRepoId))
				.ifPresent(inOutId -> request.setM_InOut_ID(inOutId.getRepoId()));
		optionalOfFirstNonNullSupplied(candidate::getInvoiceId,
				() -> getIdFromReferenceOrNull(candidate, I_C_Invoice.Table_Name, InvoiceId::ofRepoId))
				.ifPresent(invoiceId -> request.setM_InOut_ID(invoiceId.getRepoId()));

		optionalOfFirstNonNullSupplied(candidate::getPaymentId,
				() -> getIdFromReferenceOrNull(candidate, I_C_Invoice.Table_Name, PaymentId::ofRepoId))
				.ifPresent(paymentId -> request.setM_InOut_ID(paymentId.getRepoId()));

		request.setC_Project_ID(ProjectId.toRepoId(candidate.getProjectId()));

		request.setDateTrx(coalesce(asTimestamp(candidate.getDateTrx()), SystemTime.asTimestamp()));
		request.setReminderDate(asTimestamp(candidate.getReminderDate()));
		request.setAD_User_ID(UserId.toRepoId(candidate.getUserId()));
		request.setR_RequestType_ID(candidate.getRequestTypeId().getRepoId());
		request.setM_QualityNote_ID(QualityNoteId.toRepoId(candidate.getQualityNoteId()));
		request.setPerformanceType(candidate.getPerformanceType());
		request.setDateDelivered(asTimestamp(candidate.getDateDelivered()));
		request.setR_Status_ID(RequestStatusId.toRepoId(candidate.getStatusId()));
		if (candidate.getPriority() != null)
		{
			request.setPriority(coalesceNotNull(RequestPriority.toValue(candidate.getPriority()), RequestPriority.Medium.getCode()));
		}
		request.setSalesRep_ID(UserId.toRepoId(candidate.getSalesRepId()));
		request.setC_BP_Vendor_ID(BPartnerId.toRepoId(candidate.getVendorId()));

		save(request);

		return request;
	}

	@Nullable
	private static <T extends RepoIdAware> T getIdFromReferenceOrNull(final @NonNull RequestCandidate candidate, @NonNull final String tableName, @NonNull final Function<Integer, T> idMapper)
	{
		if (candidate.getRecordRef() != null && candidate.getRecordRef().getTableName().equals(tableName))
		{
			return idMapper.apply(candidate.getRecordRef().getRecord_ID());
		}
		return null;
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
				.idsAsSet(RequestId::ofRepoId)
				.stream();
	}
}
