/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.inoutcandidate;

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleExportStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public class ShipmentScheduleRepository
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

	public List<ShipmentSchedule> getBy(@NonNull final ShipmentScheduleQuery query)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH);

		if (query.getExportStatus() != null)
		{
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_ExportStatus,
					query.getExportStatus().getCode());
		}
		if (query.getCanBeExportedFrom() != null)
		{
			queryBuilder.addCompareFilter(I_M_ShipmentSchedule.COLUMN_CanBeExportedFrom, CompareQueryFilter.Operator.LESS_OR_EQUAL,
					TimeUtil.asTimestamp(query.getCanBeExportedFrom()));
		}
		if (!query.isIncludeInvalid())
		{
			final IQuery<I_M_ShipmentSchedule_Recompute> recomputeQuery = queryBL.createQueryBuilder(I_M_ShipmentSchedule_Recompute.class).create();
			queryBuilder.addNotInSubQueryFilter(
					I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID,
					I_M_ShipmentSchedule_Recompute.COLUMNNAME_M_ShipmentSchedule_ID,
					recomputeQuery);
		}
		if (!query.isIncludeProcessed())
		{
			queryBuilder.addNotEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, true);
		}

		if (query.getLimit() > 0)
		{
			queryBuilder.setLimit(query.getLimit());
		}

		final List<I_M_ShipmentSchedule> records = queryBuilder
				.orderBy(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
				.create()
				.list();

		final ImmutableList.Builder<ShipmentSchedule> result = ImmutableList.builder();
		for (final I_M_ShipmentSchedule record : records)
		{
			final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID());

			final ShipmentSchedule.ShipmentScheduleBuilder shipmentScheduleBuilder = ShipmentSchedule.builder()
					.id(shipmentScheduleId)
					.orgId(orgId)
					.bPartnerId(shipmentScheduleEffectiveBL.getBPartnerId(record))
					.locationId(shipmentScheduleEffectiveBL.getBPartnerLocationId(record))
					.contactId(shipmentScheduleEffectiveBL.getBPartnerContactId(record))
					.orderId(OrderId.ofRepoIdOrNull(record.getC_Order_ID()))
					.productId(ProductId.ofRepoId(record.getM_Product_ID()))
					.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
					.quantityToDeliver(shipmentScheduleBL.getQtyToDeliver(record));
			if (record.getDateOrdered() != null)
			{
				shipmentScheduleBuilder.dateOrdered(record.getDateOrdered().toLocalDateTime());
			}
			result.add(shipmentScheduleBuilder.build());
		}
		return result.build();
	}

	@Value
	@Builder
	public static class ShipmentScheduleQuery
	{
		int limit;

		Instant canBeExportedFrom;

		ShipmentScheduleExportStatus exportStatus;

		@Builder.Default
		boolean includeInvalid = false;

		@Builder.Default
		boolean includeProcessed = false;
	}
}
