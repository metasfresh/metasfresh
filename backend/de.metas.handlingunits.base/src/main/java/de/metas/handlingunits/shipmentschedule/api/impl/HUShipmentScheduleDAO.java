package de.metas.handlingunits.shipmentschedule.api.impl;

import com.google.common.base.Preconditions;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.util.Env;

import java.util.List;
import java.util.Properties;

public class HUShipmentScheduleDAO implements IHUShipmentScheduleDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private IHandlingUnitsBL handlingUnitsBL() {return Services.get(IHandlingUnitsBL.class);}

	@Override
	public List<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForHU(@NonNull final I_M_HU hu)
	{
		return queryByHU(hu).create().list();
	}

	private IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryByHU(final @NonNull I_M_HU hu)
	{
		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, hu)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID); //creation sequence

		//
		// Filter HU based on it's type (LU/TU)
		final IHandlingUnitsBL handlingUnitsBL = handlingUnitsBL();
		if (handlingUnitsBL.isVirtual(hu))
		{
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID, hu.getM_HU_ID());
		}
		else if (handlingUnitsBL.isLoadingUnit(hu))
		{
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_LU_HU_ID, hu.getM_HU_ID());
		}
		else if (handlingUnitsBL.isTransportUnitOrVirtual(hu))
		{
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID, hu.getM_HU_ID());
		}
		else
		{
			throw new HUException("HU shall be a LU/TU/CU: " + handlingUnitsBL.getDisplayName(hu));
		}
		return queryBuilder;
	}

	private IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryByTopLevelHU(final @NonNull I_M_HU hu)
	{
		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, hu)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID); //creation sequence

		//
		// Filter by LU/TU/VHU
		final IHandlingUnitsBL handlingUnitsBL = handlingUnitsBL();
		if (handlingUnitsBL.isLoadingUnit(hu))
		{
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_LU_HU_ID, hu.getM_HU_ID());
		}
		else if (handlingUnitsBL.isTransportUnit(hu))
		{
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_LU_HU_ID, null);
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID, hu.getM_HU_ID());
		}
		else if (handlingUnitsBL.isVirtual(hu))
		{
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_LU_HU_ID, null);
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID, null);
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID, hu.getM_HU_ID());
		}
		else
		{
			throw new HUException("Unsupported HU type: " + handlingUnitsBL.getDisplayName(hu));
		}

		return queryBuilder;
	}

	@Override
	public List<I_M_ShipmentSchedule_QtyPicked> retrieveQtyPickedNotDeliveredForTopLevelHU(@NonNull final I_M_HU topLevelHU)
	{
		return queryByTopLevelHU(topLevelHU)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_InOutLine_ID, null) // Not delivered
				.create()
				.list();
	}

	@Override
	public List<I_M_ShipmentSchedule_QtyPicked> retrieveByTopLevelHUAndShipmentScheduleId(
			@NonNull final I_M_HU topLevelHU,
			@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return queryByTopLevelHU(topLevelHU)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.list();
	}

	@Override
	public List<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForTU(final int shipmentScheduleId, final int tuHUId, final String trxName)
	{
		Preconditions.checkArgument(shipmentScheduleId > 0, "shipmentScheduleId > 0");
		Preconditions.checkArgument(tuHUId > 0, "tuHUId > 0");

		final Properties ctx = Env.getCtx();
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID, tuHUId)
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID)
				.create()
				.list();
	}

	@Override
	public List<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForVHU(final I_M_HU vhu)
	{
		return retrieveSchedsQtyPickedForVHUQuery(vhu)
				.create()
				.list();
	}

	@Override
	public IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForVHUQuery(@NonNull final I_M_HU vhu)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, vhu)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID, vhu.getM_HU_ID())
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID);
	}

}
