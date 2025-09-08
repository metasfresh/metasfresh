package de.metas.payment.sumup;

import com.google.common.collect.ImmutableList;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.util.Services;
import de.metas.util.lang.BooleanThreadLocal;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class SumUpEventsDispatcher
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final ImmutableList<SumUpTransactionStatusChangedListener> statusChangedListeners;
	@NonNull private final IEventBusFactory eventBusFactory;

	@NonNull private static final BooleanThreadLocal forceSendChangeEventsThreadLocal = new BooleanThreadLocal(false);

	private static final Topic EVENTS_TOPIC = Topic.distributedAndAsync("de.metas.payment.sumup.events");

	public SumUpEventsDispatcher(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull Optional<List<SumUpTransactionStatusChangedListener>> statusChangedListeners
	)
	{
		this.eventBusFactory = eventBusFactory;
		this.statusChangedListeners = statusChangedListeners.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
	}

	@PostConstruct
	private void postConstruct()
	{
		getEventBus().subscribeOn(SumUpTransactionStatusChangedEvent.class, this::handleEvent);
	}

	public static IAutoCloseable temporaryForceSendingChangeEventsIf(final boolean condition)
	{
		return forceSendChangeEventsThreadLocal.temporarySetToTrueIf(condition);
	}

	private static boolean isForceSendingChangeEvents()
	{
		return forceSendChangeEventsThreadLocal.isTrue();
	}

	private IEventBus getEventBus() {return eventBusFactory.getEventBus(EVENTS_TOPIC);}

	private void handleEvent(@NonNull final SumUpTransactionStatusChangedEvent event)
	{
		fireLocalListeners(event);
	}

	private void fireLocalListeners(final @NonNull SumUpTransactionStatusChangedEvent event)
	{
		try (final IAutoCloseable ignored = Env.switchContext(newTemporaryCtx(event)))
		{
			statusChangedListeners.forEach(listener -> listener.onStatusChanged(event));
		}
	}

	private static Properties newTemporaryCtx(final @NonNull SumUpTransactionStatusChangedEvent event)
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientId(ctx, event.getClientId());
		Env.setOrgId(ctx, event.getOrgId());
		return ctx;
	}

	private void fireLocalAndRemoteListenersAfterTrxCommit(final @NonNull SumUpTransactionStatusChangedEvent event)
	{
		trxManager.accumulateAndProcessAfterCommit(
				SumUpEventsDispatcher.class.getSimpleName(),
				ImmutableList.of(event),
				this::fireLocalAndRemoteListenersNow);
	}

	private void fireLocalAndRemoteListenersNow(@NonNull final List<SumUpTransactionStatusChangedEvent> events)
	{
		final IEventBus eventBus = getEventBus();

		// NOTE: we don't have to fireLocalListeners 
		// because we assume we will also get back this event and then we will handle it
		events.forEach(eventBus::enqueueObject);
	}

	public void fireNewTransaction(@NonNull final SumUpTransaction trx)
	{
		fireLocalAndRemoteListenersAfterTrxCommit(SumUpTransactionStatusChangedEvent.ofNewTransaction(trx));
	}

	public void fireStatusChangedIfNeeded(
			@NonNull final SumUpTransaction trx,
			@NonNull final SumUpTransaction trxPrev)
	{
		if (!isForceSendingChangeEvents() && hasChanges(trx, trxPrev))
		{
			return;
		}

		fireLocalAndRemoteListenersAfterTrxCommit(SumUpTransactionStatusChangedEvent.ofChangedTransaction(trx, trxPrev));
	}

	private static boolean hasChanges(final @NonNull SumUpTransaction trx, final @NonNull SumUpTransaction trxPrev)
	{
		return SumUpTransactionStatus.equals(trx.getStatus(), trxPrev.getStatus())
				&& trx.isRefunded() == trxPrev.isRefunded();
	}
}
