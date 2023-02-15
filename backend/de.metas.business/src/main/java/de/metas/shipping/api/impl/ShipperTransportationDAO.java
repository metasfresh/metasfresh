package de.metas.shipping.api.impl;

import de.metas.bpartner.BPartnerId;
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
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Package;
import org.compiere.model.X_M_Delivery_Planning;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.load;

public class ShipperTransportationDAO implements IShipperTransportationDAO
{

	private IQueryBL queryBL = Services.get(IQueryBL.class);

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
				.filter(new EqualsQueryFilter<I_M_ShippingPackage>(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, shipperTransportationId))
				.create()
				.list(I_M_ShippingPackage.class);
	}

	@Override
	public <T extends I_M_ShipperTransportation> List<T> retrieveOpenShipperTransportations(final Properties ctx, final Class<T> clazz)
	{
		final IQueryBuilder<T> queryBuilder = queryBL
				.createQueryBuilder(clazz, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_DocStatus, X_M_ShipperTransportation.DOCSTATUS_Drafted) // Drafts
				;

		queryBuilder.orderBy()
				.addColumn(I_M_ShipperTransportation.COLUMNNAME_DocumentNo);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public ShipperTransportationId retrieveNextOpenShipperTransportationIdOrNull(final ShipperId shipperId)
	{
		return queryBL
				.createQueryBuilder(I_M_ShipperTransportation.class)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_DocStatus, X_M_ShipperTransportation.DOCSTATUS_Drafted) // Drafts
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID, shipperId)
				.orderBy(I_M_ShipperTransportation.COLUMNNAME_DateToBeFetched)

				.create()
				.firstId(ShipperTransportationId::ofRepoIdOrNull);
	}

	@Override
	public List<I_M_ShippingPackage> retrieveShippingPackages(final I_M_Package mpackage)
	{
		Check.assumeNotNull(mpackage, "mpackage not null");

		return queryBL
				.createQueryBuilder(I_M_ShippingPackage.class, mpackage)
				.filter(new EqualsQueryFilter<I_M_ShippingPackage>(I_M_ShippingPackage.COLUMNNAME_M_Package_ID, mpackage.getM_Package_ID()))
				.create()
				.list(I_M_ShippingPackage.class);
	}

	@Override
	public Iterator<I_M_ShippingPackage> retrieveCompletedOutgoingDeliveryInstructionLines(@NonNull final BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_Type, X_M_Delivery_Planning.M_DELIVERY_PLANNING_TYPE_Outgoing)
				.andCollect(I_M_Delivery_Planning.COLUMN_M_ShipperTransportation_ID, I_M_ShipperTransportation.class)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID, bPartnerId)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_DocStatus, X_M_ShipperTransportation.DOCSTATUS_Completed)
				.andCollectChildren(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, I_M_ShippingPackage.class)
				.create()
				.iterate(I_M_ShippingPackage.class);
	}

	@Override
	public I_M_ShipperTransportation retrieve(@NonNull final ShipperTransportationId shipperTransportationId)
	{
		return load(shipperTransportationId, I_M_ShipperTransportation.class);
	}
}
