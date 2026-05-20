package de.metas.workflow.rest_api.service;

import de.metas.user.UserId;
import lombok.NonNull;

/**
 * SPI: notified inside the transaction that handles trolley release.
 * Implementations may throw to roll back the release (e.g. if a domain-specific abort fails).
 */
public interface TrolleyReleaseListener
{
	void onTrolleyReleased(@NonNull UserId userId);
}
