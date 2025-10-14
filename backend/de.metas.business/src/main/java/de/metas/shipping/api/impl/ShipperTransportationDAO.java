package de.metas.shipping.api.impl;

import de.metas.handlingunits.impl.CreateShipperTransportationRequest;
import de.metas.handlingunits.impl.ShipperTransportationQuery;
import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.api.ShipperTransportation;
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
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ShipperTransportationDAO implements IShipperTransportationDAO
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_M_ShipperTransportation getById(@NonNull final ShipperTransportationId shipperItransportationId)
	{
		final I_M_ShipperTransportation shipperTransportation = load(shipperItransportationId.getRepoId(), I_M_ShipperTransportation.class);
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
		return queryBL.createQueryBuilder(I_M_ShipperTransportation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID, query.getShipperId())
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID, query.getShipperBPartnerAndLocationId().getBpartnerId())
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Shipper_Location_ID, query.getShipperBPartnerAndLocationId().getRepoId())
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_DateDoc, TimeUtil.asTimestamp(query.getShipDate()))
				.create();
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


	@Nullable
	@Override
	public ShipperTransportation getEarliestShipperTransportationByOrderId(final OrderId orderId)
	{
		final I_M_ShipperTransportation record = queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_C_Order_ID, orderId)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_M_ShippingPackage.COLUMN_M_ShipperTransportation_ID)
				.orderBy(I_M_ShipperTransportation.COLUMNNAME_DateDoc)
				.create()
				.first(I_M_ShipperTransportation.class);

		if (record == null)
		{
			return null;
		}

		final ShipperTransportationId id = ShipperTransportationId.ofRepoId(record.getM_ShipperTransportation_ID());
		return ShipperTransportation.builder()
				.id(id)
				.billOfLadingDate(TimeUtil.asInstant(record.getBLDate()))
				.ETADate(TimeUtil.asInstant(record.getETA()))
				.build();


	}
}
