package de.metas.inoutcandidate.split;

import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

	public List<ShipmentScheduleSplit> getByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId) {return repository.getByShipmentScheduleId(shipmentScheduleId);}

	public ShipmentScheduleSplit changeById(@Nullable final ShipmentScheduleSplitId id, @NonNull final Consumer<ShipmentScheduleSplit> updater) {return repository.changeById(id, updater);}

	public void save(@NonNull final ShipmentScheduleSplit split) {repository.save(split);}

	public void deleteByIds(@NonNull final Collection<ShipmentScheduleSplitId> ids) {repository.deleteByIds(ids);}
}
