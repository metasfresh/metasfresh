package de.metas.cucumber.stepdefs.pos;

import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpEventsDispatcher;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.SumUpTransactionStatusChangedEvent;
import de.metas.payment.sumup.SumUpTransactionStatusChangedListener;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static de.metas.cucumber.stepdefs.StepDefConstants.CLIENT_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;

/**
 * Records the client transaction ids of the SumUp status events this JVM handled. Picked up by component scanning.
 * <p>
 * SumUp status events are delivered asynchronously, on the event bus' single executor thread, in the order they were
 * fired. {@link #waitUntilFiredEventsAreHandled()} fires a sentinel event and waits until this probe saw it: by then
 * every event fired before the sentinel has been handled by all listeners, so the steps that follow see the outcome
 * without sleeping (and see "nothing happened" only after it really had the chance to happen).
 */
@Component
public class SumUpEventsTestProbe implements SumUpTransactionStatusChangedListener
{
	private static final int MAX_WAIT_SECONDS = 60;

	private final Set<SumUpClientTransactionId> handledClientTransactionIds = ConcurrentHashMap.newKeySet();

	@Override
	public void onStatusChanged(@NonNull final SumUpTransactionStatusChangedEvent event)
	{
		handledClientTransactionIds.add(event.getTrx().getClientTransactionId());
	}

	private boolean isHandled(@NonNull final SumUpClientTransactionId clientTransactionId)
	{
		return handledClientTransactionIds.contains(clientTransactionId);
	}

	/**
	 * Returns once every SumUp status event fired so far has been handled by all listeners. The sentinel event has no
	 * POS reference, so the POS listener ignores it.
	 */
	public static void waitUntilFiredEventsAreHandled() throws InterruptedException
	{
		final SumUpClientTransactionId sentinelId = SumUpClientTransactionId.ofString("cucumber-sentinel-" + UUID.randomUUID());
		SpringContextHolder.instance.getBean(SumUpEventsDispatcher.class).fireNewTransaction(SumUpTransaction.builder()
				.configId(SumUpConfigId.ofRepoId(1))
				.externalId(SumUpTransactionExternalId.ofString(sentinelId.getAsString()))
				.clientTransactionId(sentinelId)
				.merchantCode(SumUpMerchantCode.ofString("SENTINEL"))
				.timestamp(SystemTime.asInstant())
				.status(SumUpTransactionStatus.PENDING)
				.amount(Amount.zero(CurrencyCode.EUR))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.build());

		final SumUpEventsTestProbe probe = SpringContextHolder.instance.getBean(SumUpEventsTestProbe.class);
		StepDefUtil.tryAndWait(MAX_WAIT_SECONDS, 100, () -> probe.isHandled(sentinelId));
	}
}
