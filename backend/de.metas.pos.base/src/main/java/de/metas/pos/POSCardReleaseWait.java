package de.metas.pos;

import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * The wait after which the cashier may release a till card payment whose result is not known: a card sale's payment
 * ({@link POSOrdersService#releaseCardPayment}) or a card settlement of an invoice.
 */
@Component
public class POSCardReleaseWait
{
	private static final String SYSCONFIG_ReleaseAfterSeconds = "de.metas.pos.InvoiceCardSettlement.ReleaseAfterSeconds";
	private static final int DEFAULT_ReleaseAfterSeconds = 180;
	/**
	 * Above the worst-case send of a card checkout (2 × 60 s), so that no release cancels a checkout still being sent.
	 */
	private static final int MIN_ReleaseAfterSeconds = 150;

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * @return AD_SysConfig {@link #SYSCONFIG_ReleaseAfterSeconds} of the given client / org, default
	 * {@link #DEFAULT_ReleaseAfterSeconds}; a value below {@link #MIN_ReleaseAfterSeconds} is raised to it
	 */
	@NonNull
	public Duration getReleaseAfter(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final int seconds = sysConfigBL.getIntValue(SYSCONFIG_ReleaseAfterSeconds, DEFAULT_ReleaseAfterSeconds, clientAndOrgId);
		return Duration.ofSeconds(Math.max(seconds, MIN_ReleaseAfterSeconds));
	}
}
