/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.order.dao;

import de.metas.ordercandidate.api.OLCandId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.model.I_Alberta_Order;
import de.metas.vertical.healthcare.alberta.model.I_Alberta_OrderedArticleLine;
import de.metas.vertical.healthcare.alberta.model.I_C_OLCand_AlbertaTherapy;
import de.metas.vertical.healthcare.alberta.model.I_C_OLCand_AlbertaTherapyType;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderId;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderInfo;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderLineInfo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class AlbertaOrderDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void createAlbertaOrder(@NonNull final AlbertaOrderInfo request)
	{
		final AlbertaOrderId albertaOrderId = getByExternalId(request.getExternalId())
				.map(I_Alberta_Order::getAlberta_Order_ID)
				.map(AlbertaOrderId::ofRepoId)
				.orElseGet(() -> createAlbertaOrderHeader(request));

		if (request.getOrderLine() != null)
		{
			createAlbertaOrderLine(request.getOrderLine(), albertaOrderId, request.getOrgId());
		}
	}

	public void createAlbertaOrderTherapy(
			@NonNull final OLCandId olCandId,
			@NonNull final String therapy,
			@NonNull final OrgId orgId)
	{
		final I_C_OLCand_AlbertaTherapy orderTherapy = InterfaceWrapperHelper.newInstance(I_C_OLCand_AlbertaTherapy.class);

		orderTherapy.setAD_Org_ID(orgId.getRepoId());
		orderTherapy.setTherapy(therapy);
		orderTherapy.setC_OLCand_ID(olCandId.getRepoId());

		saveRecord(orderTherapy);
	}

	public void createAlbertaOrderTherapyType(
			@NonNull final OLCandId olCandId,
			@NonNull final String therapyType,
			@NonNull final OrgId orgId)
	{
		final I_C_OLCand_AlbertaTherapyType orderTherapyType = InterfaceWrapperHelper.newInstance(I_C_OLCand_AlbertaTherapyType.class);

		orderTherapyType.setAD_Org_ID(orgId.getRepoId());
		orderTherapyType.setTherapyType(therapyType);
		orderTherapyType.setC_OLCand_ID(olCandId.getRepoId());

		saveRecord(orderTherapyType);
	}

	@NonNull
	private Optional<I_Alberta_Order> getByExternalId(@NonNull final String externalId)
	{
		return queryBL.createQueryBuilder(I_Alberta_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_Alberta_Order.COLUMNNAME_ExternalId, externalId)
				.create()
				.firstOnlyOptional(I_Alberta_Order.class);
	}

	private void createAlbertaOrderLine(
			@NonNull final AlbertaOrderLineInfo request,
			@NonNull final AlbertaOrderId albertaOrderId,
			@NonNull final OrgId orgId)
	{
		final I_Alberta_OrderedArticleLine albertaOrderLine = InterfaceWrapperHelper.newInstance(I_Alberta_OrderedArticleLine.class);

		albertaOrderLine.setAlberta_Order_ID(albertaOrderId.getRepoId());
		albertaOrderLine.setC_OLCand_ID(request.getOlCandId().getRepoId());
		albertaOrderLine.setAD_Org_ID(orgId.getRepoId());
		albertaOrderLine.setExternalId(request.getExternalId());
		albertaOrderLine.setSalesLineId(request.getSalesLineId());
		albertaOrderLine.setUnit(request.getUnit());
		albertaOrderLine.setIsPrivateSale(Boolean.TRUE.equals(request.getIsPrivateSale()));
		albertaOrderLine.setIsRentalEquipment(Boolean.TRUE.equals(request.getIsRentalEquipment()));
		albertaOrderLine.setDurationAmount(request.getDurationAmount());
		albertaOrderLine.setTimePeriod(request.getTimePeriod());
		albertaOrderLine.setExternallyUpdatedAt(TimeUtil.asTimestamp(request.getUpdated()));

		saveRecord(albertaOrderLine);
	}

	@NonNull
	private AlbertaOrderId createAlbertaOrderHeader(@NonNull final AlbertaOrderInfo request)
	{
		final I_Alberta_Order albertaOrder = InterfaceWrapperHelper.newInstance(I_Alberta_Order.class);

		albertaOrder.setAD_Org_ID(request.getOrgId().getRepoId());
		albertaOrder.setAnnotation(request.getAnnotation());
		albertaOrder.setDeliveryInfo(request.getDeliveryInformation());
		albertaOrder.setDeliveryNote(request.getDeliveryNote());

		if (request.getDoctorBPartnerId() != null)
		{
			albertaOrder.setC_Doctor_BPartner_ID(request.getDoctorBPartnerId().getRepoId());
		}

		if (request.getPharmacyBPartnerId() != null)
		{
			albertaOrder.setC_Pharmacy_BPartner_ID(request.getPharmacyBPartnerId().getRepoId());
		}

		albertaOrder.setExternalId(request.getExternalId());
		albertaOrder.setRootId(request.getRootId());
		albertaOrder.setCreationDate(TimeUtil.asTimestamp(request.getCreationDate()));
		albertaOrder.setStartDate(TimeUtil.asTimestamp(request.getStartDate()));
		albertaOrder.setEndDate(TimeUtil.asTimestamp(request.getEndDate()));
		albertaOrder.setDayOfDelivery(request.getDayOfDelivery());
		albertaOrder.setNextDelivery(TimeUtil.asTimestamp(request.getNextDelivery()));
		albertaOrder.setIsInitialCare(Boolean.TRUE.equals(request.getIsInitialCare()));
		albertaOrder.setIsSeriesOrder(Boolean.TRUE.equals(request.getIsSeriesOrder()));
		albertaOrder.setIsArchived(Boolean.TRUE.equals(request.getIsArchived()));
		albertaOrder.setExternallyUpdatedAt(TimeUtil.asTimestamp(request.getUpdated()));

		saveRecord(albertaOrder);

		return AlbertaOrderId.ofRepoId(albertaOrder.getAlberta_Order_ID());
	}
}
