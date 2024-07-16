package de.metas.inoutcandidate.split;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.Dimension;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Split;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class ShipmentScheduleSplitRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public List<ShipmentScheduleSplit> getByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_Split.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_Split.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.orderBy(I_M_ShipmentSchedule_Split.COLUMNNAME_M_ShipmentSchedule_Split_ID)
				.create()
				.stream()
				.map(ShipmentScheduleSplitRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private static ShipmentScheduleSplit fromRecord(final I_M_ShipmentSchedule_Split record)
	{
		return ShipmentScheduleSplit.builder()
				.id(ShipmentScheduleSplitId.ofRepoId(record.getM_ShipmentSchedule_Split_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.deliveryDate(record.getDeliveryDate().toLocalDateTime().toLocalDate())
				.qtyToDeliver(Quantitys.of(record.getQtyToDeliver(), UomId.ofRepoId(record.getC_UOM_ID())))
				.dimension(extractDimension(record))
				.shipmentLineId(InOutAndLineId.ofRepoIdOrNull(record.getM_InOut_ID(), record.getM_InOutLine_ID()))
				.processed(record.isProcessed())
				.build();
	}

	private static void updateRecord(final I_M_ShipmentSchedule_Split record, final ShipmentScheduleSplit from)
	{
		final ShipmentScheduleSplitId recordId = ShipmentScheduleSplitId.ofRepoIdOrNull(record.getM_ShipmentSchedule_Split_ID());
		if (!ShipmentScheduleSplitId.equals(recordId, from.getId()))
		{
			throw new AdempiereException("ID not matching: " + record + ", " + from);
		}

		final ShipmentScheduleId recordShipmentScheduleId = ShipmentScheduleId.ofRepoIdOrNull(record.getM_ShipmentSchedule_ID());
		if (recordShipmentScheduleId != null && !ShipmentScheduleId.equals(recordShipmentScheduleId, from.getShipmentScheduleId()))
		{
			throw new AdempiereException("shipmentScheduleId not matching: " + record + "," + from);
		}

		record.setM_ShipmentSchedule_ID(from.getShipmentScheduleId().getRepoId());
		record.setDeliveryDate(Timestamp.valueOf(from.getDeliveryDate().atStartOfDay()));
		record.setC_UOM_ID(from.getQtyToDeliver().getUomId().getRepoId());
		record.setQtyToDeliver(from.getQtyToDeliver().toBigDecimal());
		record.setM_InOut_ID(from.getShipmentLineId() != null ? from.getShipmentLineId().getInOutId().getRepoId() : -1);
		record.setM_InOutLine_ID(from.getShipmentLineId() != null ? from.getShipmentLineId().getInOutLineId().getRepoId() : -1);
		record.setProcessed(from.isProcessed());

		updateRecordFromDimension(record, from.getDimension());
	}

	private static Dimension extractDimension(@NonNull final I_M_ShipmentSchedule_Split record)
	{
		return Dimension.builder()
				.userElementNumber1(record.getUserElementNumber1())
				.userElementNumber2(record.getUserElementNumber2())
				.build();
	}

	private static void updateRecordFromDimension(@NonNull final I_M_ShipmentSchedule_Split record, @NonNull final Dimension from)
	{
		record.setUserElementNumber1(from.getUserElementNumber1());
		record.setUserElementNumber2(from.getUserElementNumber2());
	}

	public ShipmentScheduleSplit changeById(
			@NonNull final ShipmentScheduleSplitId splitId,
			@NonNull final Consumer<ShipmentScheduleSplit> updater)
	{
		final I_M_ShipmentSchedule_Split record = getRecordById(splitId);
		final ShipmentScheduleSplit split = fromRecord(record);
		updater.accept(split);
		updateRecord(record, split);
		InterfaceWrapperHelper.saveRecord(record);

		return fromRecord(record);
	}

	private I_M_ShipmentSchedule_Split getRecordById(final @NotNull ShipmentScheduleSplitId id)
	{
		final I_M_ShipmentSchedule_Split record = InterfaceWrapperHelper.load(id, I_M_ShipmentSchedule_Split.class);
		return Check.assumeNotNull(record, "record shall exist for {}", id);
	}

	public void save(@NonNull final ShipmentScheduleSplit split)
	{
		final I_M_ShipmentSchedule_Split record = split.getId() != null
				? getRecordById(split.getId())
				: InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_Split.class);

		updateRecord(record, split);
		InterfaceWrapperHelper.saveRecord(record);
		split.setId(ShipmentScheduleSplitId.ofRepoId(record.getM_ShipmentSchedule_Split_ID()));
	}

	public void deleteByIds(@NonNull final Collection<ShipmentScheduleSplitId> ids)
	{
		if (ids.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_M_ShipmentSchedule_Split.class)
				.addInArrayFilter(I_M_ShipmentSchedule_Split.COLUMNNAME_M_ShipmentSchedule_Split_ID, ids)
				.create()
				.delete(true);
	}
}
