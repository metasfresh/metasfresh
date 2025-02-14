package de.metas.ui.web;

import de.metas.Profiles;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Profile(Profiles.PROFILE_Webui)
public class WebRestApiApplicationHealthIndicator implements HealthIndicator
{
	private final AtomicBoolean statusHolder = new AtomicBoolean(false);

	@Override
	public Health health()
	{
		final boolean isUp = statusHolder.get();
		return isUp
				? Health.up().build()
				: Health.down().build();
	}

	public void setStatusUp()
	{
		statusHolder.set(true);
	}
}
