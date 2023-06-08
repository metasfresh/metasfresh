package de.metas.sysconfig;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SysConfigListenersRegistry
{
	private static final Logger logger = LogManager.getLogger(SysConfigListenersRegistry.class);
	private final ImmutableList<SysConfigListener> listeners;

	public SysConfigListenersRegistry(@NonNull final Optional<List<SysConfigListener>> listeners)
	{
		this.listeners = listeners.map(ImmutableList::copyOf).orElse(ImmutableList.of());
		logger.info("Listeners: {}", this.listeners);
	}

	public void assertValid(final String sysconfigName, final String value)
	{
		listeners.forEach(listener -> listener.assertValidSysConfigValue(sysconfigName, value));
	}

	public void fireValueChanged(final String sysconfigName, final String value)
	{
		listeners.forEach(listener -> listener.onSysConfigValueChanged(sysconfigName, value));
	}

}
