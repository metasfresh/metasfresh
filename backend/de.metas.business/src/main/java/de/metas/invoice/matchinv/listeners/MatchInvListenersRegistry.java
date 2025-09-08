package de.metas.invoice.matchinv.listeners;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MatchInvListenersRegistry
{
	private static final Logger logger = LogManager.getLogger(MatchInvListenersRegistry.class);

	private final ImmutableList<MatchInvListener> listeners;

	public MatchInvListenersRegistry(
			@NonNull final Optional<List<MatchInvListener>> optionalListeners)
	{
		this.listeners = optionalListeners.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
		logger.info("Listeners: {}", this.listeners);
	}

	public void fireAfterCreated(@NonNull final MatchInv matchInv)
	{
		listeners.forEach(listener -> listener.onAfterCreated(matchInv));
	}

	public void fireAfterDeleted(@NonNull final ImmutableList<MatchInv> matchInvs)
	{
		if (matchInvs.isEmpty())
		{
			return;
		}

		listeners.forEach(listener -> listener.onAfterDeleted(matchInvs));
	}
}
