package de.metas.inoutcandidate.split;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ShipmentScheduleSplitService
{
	@NonNull private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	@NonNull private final ShipmentScheduleSplitRepository repository;

	public List<ShipmentScheduleSplit> getByShipmentScheduleIdExcludingVoidedShipments(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final NotVoidedShipmentsPredicate notVoidedShipmentsPredicate = new NotVoidedShipmentsPredicate(inOutBL);

		return repository.getByShipmentScheduleId(shipmentScheduleId).stream()
				.filter(notVoidedShipmentsPredicate)
				.collect(ImmutableList.toImmutableList());
	}

	public ShipmentScheduleSplit changeById(@Nullable final ShipmentScheduleSplitId id, @NonNull final Consumer<ShipmentScheduleSplit> updater) {return repository.changeById(id, updater);}

	public void save(@NonNull final ShipmentScheduleSplit split) {repository.save(split);}

	public void deleteByIds(@NonNull final Collection<ShipmentScheduleSplitId> ids) {repository.deleteByIds(ids);}

	@RequiredArgsConstructor
	private static class NotVoidedShipmentsPredicate implements Predicate<ShipmentScheduleSplit>
	{
		@NonNull private final IInOutBL inOutBL;
		@NonNull private final HashMap<InOutId, DocStatus> shipmentDocStatuses = new HashMap<>();

		public void warmUpFor(final Collection<ShipmentScheduleSplit> splits)
		{
			final ImmutableListMultimap<InOutId, ShipmentScheduleSplit> splitsByShipmentId = splits.stream()
					.filter(split -> split.getShipmentId() != null)
					.collect(ImmutableListMultimap.toImmutableListMultimap(ShipmentScheduleSplit::getShipmentId, split -> split));

			inOutBL.getByIds(splitsByShipmentId.keySet())
					.forEach(shipment -> shipmentDocStatuses.put(InOutId.ofRepoId(shipment.getM_InOut_ID()), DocStatus.ofCode(shipment.getDocStatus())));
		}

		private DocStatus getDocStatus(final InOutId shipmentId)
		{
			return shipmentDocStatuses.computeIfAbsent(shipmentId, inOutBL::getDocStatus);
		}

		@Override
		public boolean test(final ShipmentScheduleSplit split)
		{
			final InOutId shipmentId = split.getShipmentId();
			if (shipmentId == null)
			{
				return true;
			}

			return !getDocStatus(shipmentId).isClosedReversedOrVoided();
		}
	}
}
