package de.metas.inoutcandidate.split;

import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Split;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ShipmentScheduleSplitService
{
	@NonNull private final ShipmentScheduleSplitRepository repository;
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	public List<ShipmentScheduleSplit> getByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return repository.getByShipmentScheduleId(shipmentScheduleId).stream().filter(this::isRelatedInOutValid).toList();
	}

	public List<ShipmentScheduleSplit> getRecordToProcessByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return repository.getRecordToProcessByShipmentScheduleId(shipmentScheduleId).stream().filter(this::isRelatedInOutValid).toList();
	}

	public ShipmentScheduleSplit changeById(@Nullable final ShipmentScheduleSplitId id, @NonNull final Consumer<ShipmentScheduleSplit> updater) {return repository.changeById(id, updater);}

	public void save(@NonNull final ShipmentScheduleSplit split) {repository.save(split);}

	public void deleteByIds(@NonNull final Collection<ShipmentScheduleSplitId> ids) {repository.deleteByIds(ids);}

	private boolean isRelatedInOutValid(@NonNull final ShipmentScheduleSplit split)
	{
		final I_M_ShipmentSchedule_Split record = repository.getRecordById(split.getId());
		final InOutId inOutId = InOutId.ofRepoIdOrNull(record.getM_InOut_ID());
		if (inOutId == null)
		{
			return true;
		}
		return !inOutBL.getDocStatus(inOutId).isClosedReversedOrVoided();
	}
}
