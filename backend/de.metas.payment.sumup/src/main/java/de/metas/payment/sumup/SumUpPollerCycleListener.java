package de.metas.payment.sumup;

/**
 * Called by the SumUp pending-transaction poller once per cycle, after the pending transactions were updated.
 * Implementations must be quick and must not rely on being called at a fixed rate.
 */
public interface SumUpPollerCycleListener
{
	void afterPollCycle();
}
