package de.metas.handlingunits.picking.slot;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.requests.ReleasePickingSlotRequest;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingSlotId;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PickingSlotListenersDispatcher
{
	@NonNull private static final Logger logger = LogManager.getLogger(PickingSlotListenersDispatcher.class);
	@NonNull private final ImmutableList<PickingSlotListener> listeners;

	public PickingSlotListenersDispatcher(@NonNull final Optional<List<PickingSlotListener>> listeners)
	{
		this.listeners = listeners.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
		logger.info("listeners={}", this.listeners);
	}

	public boolean hasAllocationsForSlot(@NonNull final PickingSlotId pickingSlotId)
	{
		return listeners.stream().anyMatch(component -> component.hasAllocationsForSlot(pickingSlotId));
	}

	public void beforeReleasePickingSlot(@NonNull final ReleasePickingSlotRequest request)
	{
		listeners.forEach(component -> component.beforeReleasePickingSlot(request));
	}

}
