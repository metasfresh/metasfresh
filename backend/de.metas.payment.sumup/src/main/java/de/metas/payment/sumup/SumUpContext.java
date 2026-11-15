package de.metas.payment.sumup;

import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;

import java.util.Properties;

/**
 * Context for work that runs outside a login, e.g. on the event dispatcher or the SumUp poller thread.
 */
public final class SumUpContext
{
	private SumUpContext() {}

	/**
	 * Switches the current thread to a fresh context with the given client and org (as if logged in there), until the
	 * returned closeable is closed.
	 */
	public static IAutoCloseable temporarySwitchTo(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientAndOrgId(ctx, clientAndOrgId);
		return Env.switchContext(ctx);
	}
}
