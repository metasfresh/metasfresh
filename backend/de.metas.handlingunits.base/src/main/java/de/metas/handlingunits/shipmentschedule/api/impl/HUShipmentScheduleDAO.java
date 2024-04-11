package de.metas.handlingunits.shipmentschedule.api.impl;

import com.google.common.base.Preconditions;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUComparator;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUFactory;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUSupportingServices;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.util.Env;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HUShipmentScheduleDAO implements IHUShipmentScheduleDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

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

	private List<I_M_ShipmentSchedule_QtyPicked> retrieveQtyPickedNotDeliveredForTopLevelHU(@NonNull final I_M_HU topLevelHU)
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

	@Override
	public List<ShipmentScheduleWithHU> retrieveShipmentSchedulesWithHUsFromHUs(@NonNull final List<I_M_HU> hus)
	{
		final ShipmentScheduleWithHUFactory factory = ShipmentScheduleWithHUFactory.builder()
				.supportingServices(ShipmentScheduleWithHUSupportingServices.getInstance())
				.huContext(huContextFactory.createMutableHUContext())
				.build();

		//
		// Iterate HUs and collect candidates from them
		final IHandlingUnitsBL handlingUnitsBL = handlingUnitsBL();
		final ArrayList<ShipmentScheduleWithHU> result = new ArrayList<>();
		for (final I_M_HU hu : hus)
		{
			// Make sure we are dealing with an top level HU
			if (!handlingUnitsBL.isTopLevel(hu))
			{
				throw new HUException("HU " + hu + " shall be top level");
			}

			//
			// Retrieve and create candidates from shipment schedule QtyPicked assignments
			final List<ShipmentScheduleWithHU> candidatesForHU = new ArrayList<>();
			final List<I_M_ShipmentSchedule_QtyPicked> shipmentSchedulesQtyPicked = retrieveQtyPickedNotDeliveredForTopLevelHU(hu);
			for (final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked : shipmentSchedulesQtyPicked)
			{
				if (!shipmentScheduleQtyPicked.isActive())
				{
					continue;
				}

				// NOTE: we allow negative Qtys too because they shall be part of a bigger transfer and overall qty can be positive
				// if (ssQtyPicked.getQtyPicked().signum() <= 0)
				// {
				// continue;
				// }

				final ShipmentScheduleWithHU candidate = factory.ofQtyPickedRecord(shipmentScheduleQtyPicked);
				candidatesForHU.add(candidate);
			}

			//
			// Add the candidates for current HU to the list of all collected candidates
			result.addAll(candidatesForHU);

			// Log if there were no candidates created for current HU.
			if (candidatesForHU.isEmpty())
			{
				Loggables.addLog("No eligible {} records found for hu {}",
						I_M_ShipmentSchedule_QtyPicked.Table_Name,
						handlingUnitsBL().getDisplayName(hu));
			}
		}

		//
		// Sort result
		result.sort(new ShipmentScheduleWithHUComparator());

		// TODO: make sure all shipment schedules are valid

		return result;
	}
}
