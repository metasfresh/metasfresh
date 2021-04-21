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
import de.metas.vertical.healthcare.alberta.model.I_C_OLCand_AlbertaOrder;
import de.metas.vertical.healthcare.alberta.model.I_C_OLCand_AlbertaTherapy;
import de.metas.vertical.healthcare.alberta.model.I_C_OLCand_AlbertaTherapyType;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderCompositeInfo;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class AlbertaOrderDAO
{
	public void createAlbertaOrder(@NonNull final AlbertaOrderCompositeInfo request)
	{
		if (request.noAlbertaInfoSet())
		{
			return;
		}

		final I_C_OLCand_AlbertaOrder albertaOrder = InterfaceWrapperHelper.newInstance(I_C_OLCand_AlbertaOrder.class);
		albertaOrder.setAD_Org_ID(request.getOrgId().getRepoId());
		albertaOrder.setC_OLCand_ID(request.getOlCandId().getRepoId());
		albertaOrder.setAnnotation(request.getAnnotation());

		if (request.getDoctorBPartnerId() != null)
		{
			albertaOrder.setC_Doctor_BPartner_ID(request.getDoctorBPartnerId().getRepoId());
		}

		if (request.getPharmacyBPartnerId() != null)
		{
			albertaOrder.setC_Doctor_BPartner_ID(request.getPharmacyBPartnerId().getRepoId());
		}

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
		albertaOrder.setSalesLineId(request.getSalesLineId());
		albertaOrder.setUnit(request.getUnit());
		albertaOrder.setIsPrivateSale(Boolean.TRUE.equals(request.getIsPrivateSale()));
		albertaOrder.setIsRentalEquipment(Boolean.TRUE.equals(request.getIsRentalEquipment()));
		albertaOrder.setDurationAmount(request.getDurationAmount());
		albertaOrder.setTimePeriod(request.getTimePeriod());

		saveRecord(albertaOrder);
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
}
