package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class POSOrderEventDispatcher
{
	@NonNull private static final Logger logger = LogManager.getLogger(POSOrderEventDispatcher.class);
	@NonNull private final ImmutableList<POSOrderEventListener> listeners;

	public POSOrderEventDispatcher(@NonNull final Optional<List<POSOrderEventListener>> listeners)
	{
		this.listeners = listeners.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
		logger.info("Listeners: {}", this.listeners);
	}

	public void fireOrderChanged(final POSOrder order)
	{
		listeners.forEach(listener -> listener.onChange(order));
	}
}
