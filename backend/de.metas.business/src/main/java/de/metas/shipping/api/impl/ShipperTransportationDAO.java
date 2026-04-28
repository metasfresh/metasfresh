/*
 * #%L
 * de.metas.business
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

package de.metas.shipping.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.impl.CreateShipperTransportationRequest;
import de.metas.handlingunits.impl.ShipperTransportationQuery;
import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.X_M_ShipperTransportation;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Package;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ShipperTransportationDAO implements IShipperTransportationDAO
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	@Override
	public I_M_ShipperTransportation getById(@NonNull final ShipperTransportationId shipperItransportationId)
	{
		final I_M_ShipperTransportation shipperTransportation = retrieve(shipperItransportationId);
		if (shipperTransportation == null)
		{
			throw new AdempiereException("@NotFound@: " + shipperItransportationId);
		}
		return shipperTransportation;
	}

	@Override
	public List<I_M_ShippingPackage> retrieveShippingPackages(@NonNull final ShipperTransportationId shipperTransportationId)
	{
		return queryBL
				.createQueryBuilder(I_M_ShippingPackage.class)
				.filter(new EqualsQueryFilter<>(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, shipperTransportationId))
				.create()
				.list(I_M_ShippingPackage.class);
	}

	@Nullable
	@Override
	public ShipperTransportationId retrieveNextOpenShipperTransportationIdOrNull(@NonNull final ShipperId shipperId, @Nullable final Instant beforeDate)
	{
		final IQueryBuilder<I_M_ShipperTransportation> builder = queryBL
				.createQueryBuilder(I_M_ShipperTransportation.class)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_DocStatus, X_M_ShipperTransportation.DOCSTATUS_Drafted) // Drafts
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID, shipperId);
		if (beforeDate != null)
		{
			builder.addCompareFilter(I_M_ShipperTransportation.COLUMNNAME_DateToBeFetched, CompareQueryFilter.Operator.LESS_OR_EQUAL, beforeDate);
		}
		return builder
				.orderByDescending(I_M_ShipperTransportation.COLUMNNAME_DateToBeFetched)

				.create()
				.firstId(ShipperTransportationId::ofRepoIdOrNull);
	}

	@Override
	public List<I_M_ShippingPackage> retrieveShippingPackages(final I_M_Package mpackage)
	{
		Check.assumeNotNull(mpackage, "mpackage not null");

		return queryBL
				.createQueryBuilder(I_M_ShippingPackage.class, mpackage)
				.filter(new EqualsQueryFilter<>(I_M_ShippingPackage.COLUMNNAME_M_Package_ID, mpackage.getM_Package_ID()))
				.create()
				.list(I_M_ShippingPackage.class);
	}

	@Override
	public I_M_ShipperTransportation retrieve(@NonNull final ShipperTransportationId shipperTransportationId)
	{
		return load(shipperTransportationId, I_M_ShipperTransportation.class);
	}

	@Override
	public ShipperTransportationId create(@NonNull final CreateShipperTransportationRequest request)
	{
		final I_M_ShipperTransportation shipperTransportation = newInstance(I_M_ShipperTransportation.class);

		shipperTransportation.setAD_Org_ID(request.getOrgId().getRepoId());
		shipperTransportation.setM_Shipper_ID(request.getShipperId().getRepoId());
		shipperTransportation.setPickupTimeFrom(TimeUtil.asTimestamp(request.getPickupTimeFrom()));
		shipperTransportation.setPickupTimeTo(TimeUtil.asTimestamp(request.getPickupTimeTo()));
		shipperTransportation.setShipper_BPartner_ID(request.getShipperBPartnerAndLocationId().getBpartnerId().getRepoId());
		shipperTransportation.setShipper_Location_ID(request.getShipperBPartnerAndLocationId().getRepoId());
		shipperTransportation.setDateDoc(TimeUtil.asTimestamp(request.getShipDate()));
		shipperTransportation.setIsSOTrx(SOTrx.toBoolean(request.getIsSOTrx()));
		shipperTransportation.setAssignAnonymouslyPickedHUs(request.isAssignAnonymouslyPickedHUs());

		saveRecord(shipperTransportation);

		return ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());
	}

	@NonNull
	public Optional<ShipperTransportationId> getSingleByQuery(@NonNull final ShipperTransportationQuery shipperTransportationQuery)
	{
		return toSqlQuery(shipperTransportationQuery).firstIdOptional(ShipperTransportationId::ofRepoIdOrNull);
	}

	private IQuery<I_M_ShipperTransportation> toSqlQuery(final @NonNull ShipperTransportationQuery query)
	{
		if (query.isAny())
		{
			throw new AdempiereException("Any query is not allowed");
		}
		final IQueryBuilder<I_M_ShipperTransportation> builder;

		final Collection<OrderId> orderIds = query.getOrderIds();
		final Collection<OrderLineId> orderLineIds = query.getOrderLineIds();
		if (!orderIds.isEmpty() || !orderLineIds.isEmpty())
		{
			final IQueryBuilder<I_M_ShippingPackage> shippingPackageBuilder = queryBL.createQueryBuilder(I_M_ShippingPackage.class);

			if (!orderIds.isEmpty())
			{
				shippingPackageBuilder.addInArrayFilter(I_M_ShippingPackage.COLUMNNAME_C_Order_ID, orderIds);
			}
			if (!orderLineIds.isEmpty())
			{
				shippingPackageBuilder.addInArrayFilter(I_M_ShippingPackage.COLUMNNAME_C_OrderLine_ID, orderLineIds);
			}

			builder = shippingPackageBuilder
					.andCollect(I_M_ShippingPackage.COLUMN_M_ShipperTransportation_ID)
					.addOnlyActiveRecordsFilter();
		}
		else
		{
			builder = queryBL.createQueryBuilder(I_M_ShipperTransportation.class)
					.addOnlyActiveRecordsFilter();
		}

		final ShipperId shipperId = query.getShipperId();
		if (shipperId != null)
		{
			builder.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID, shipperId);
		}

		final BPartnerLocationId shipperBPartnerAndLocationId = query.getShipperBPartnerAndLocationId();
		if (shipperBPartnerAndLocationId != null)
		{
			builder.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID, shipperBPartnerAndLocationId.getBpartnerId())
					.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Shipper_Location_ID, shipperBPartnerAndLocationId.getRepoId());
		}

		final OrgId orgId = query.getOrgId();
		if (orgId != null)
		{
			builder.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_AD_Org_ID, orgId);
		}

		final LocalDate shipDate = query.getShipDate();
		if (shipDate != null)
		{
			builder.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_DateDoc, TimeUtil.asTimestamp(shipDate));
		}

		final ShipperTransportationId shipperTransportationToExclude = query.getShipperTransportationToExclude();
		if (shipperTransportationToExclude != null)
		{
			builder.addNotEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, shipperTransportationToExclude);
		}

		final Boolean processed = query.getProcessed();
		if (processed != null)
		{
			builder.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Processed, processed);
		}

		return builder.create();
	}

	@Override
	public @NonNull ShipperTransportationId getOrCreate(@NonNull final CreateShipperTransportationRequest request)
	{
		return getSingleByQuery(ShipperTransportationQuery.builder()
				.shipperId(request.getShipperId())
				.shipperBPartnerAndLocationId(request.getShipperBPartnerAndLocationId())
				.shipDate(request.getShipDate())
				.orgId(request.getOrgId())
				.build())
				.orElseGet(() -> create(request));
	}

	@Override
	public ImmutableList<OrderId> retrieveOrderIds(@NonNull final ShipperTransportationId shipperTransportationId)
	{
		return queryBL
				.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, shipperTransportationId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_M_ShippingPackage.COLUMNNAME_C_Order_ID, OrderId.class);

	}

	@Override
	public Collection<I_M_ShipperTransportation> getByQuery(@NonNull final ShipperTransportationQuery query)
	{
		return toSqlQuery(query)
				.list();
	}

	@Override
	public boolean anyMatch(@NonNull final ShipperTransportationQuery query)
	{
		return toSqlQuery(query)
				.anyMatch();
	}
}
