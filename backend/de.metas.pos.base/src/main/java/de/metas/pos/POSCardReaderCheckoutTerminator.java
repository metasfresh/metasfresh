package de.metas.pos;

import de.metas.logging.LogManager;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * The cashier's release of a till card payment whose result is not known (a card sale's payment or a card settlement of an
 * invoice) cancels the checkout on the terminal's default card reader, unless the payment's own SumUp transaction is already
 * final at SumUp.
 */
@Component
@RequiredArgsConstructor
public class POSCardReaderCheckoutTerminator
{
	private static final Logger logger = LogManager.getLogger(POSCardReaderCheckoutTerminator.class);

	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final SumUpService sumUpService;

	/**
	 * Asks the terminal's default card reader to abort its checkout, unless the payment's own SumUp transaction is known and
	 * SumUp reports it as no longer PENDING: SumUp's terminate aborts whatever checkout the reader runs (it takes no checkout
	 * id), which then can only be another one (e.g. another till's, on a shared reader). So:
	 * <ul>
	 * <li>no own SumUp transaction (e.g. a stopped checkout, or no answer from SumUp) → terminate: the reader may still run the
	 * payment's checkout;</li>
	 * <li>otherwise → terminate only if the transaction, refreshed from SumUp first, is still PENDING (a failed lookup leaves
	 * the last known status).</li>
	 * </ul>
	 * Must run outside any transaction and without a lock held. Residual limit: while the own transaction is PENDING, a
	 * shared reader may already run another checkout; SumUp offers no way to tell.
	 *
	 * @param refreshOwnTransaction      refreshes the payment's own SumUp transaction from SumUp and returns it as then stored;
	 *                                   empty if the payment has none
	 * @param releasedPaymentDescription the payment being released, only for logging
	 */
	public void terminateUnlessFinished(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final Supplier<Optional<SumUpTransaction>> refreshOwnTransaction,
			@NonNull final String releasedPaymentDescription)
	{
		final boolean isFinishedAtSumUp = refreshOwnTransaction.get()
				.map(sumUpTrx -> !sumUpTrx.getStatus().isPending())
				.orElse(false);
		if (isFinishedAtSumUp)
		{
			return;
		}

		terminate(posTerminalId, releasedPaymentDescription);
	}

	/**
	 * Asks the terminal's default card reader to abort its checkout. Best effort: SumUp does not confirm it, and a failure
	 * (e.g. a 4xx because the reader runs no checkout) is only logged; the re-check that follows decides.
	 */
	private void terminate(@NonNull final POSTerminalId posTerminalId, @NonNull final String releasedPaymentDescription)
	{
		try
		{
			final POSTerminalPaymentProcessorConfig paymentProcessorConfig = posTerminalService.getPOSTerminalById(posTerminalId).getPaymentProcessorConfigNotNull();
			final SumUpConfigId sumUpConfigId = Check.assumeNotNull(paymentProcessorConfig.getSumUpConfigId(), "sumUpConfigId is set for {}", paymentProcessorConfig);
			sumUpService.terminateCardReaderCheckout(sumUpConfigId, sumUpService.getConfig(sumUpConfigId).getDefaultCardReaderExternalIdNotNull());
		}
		catch (final Exception ex)
		{
			logger.warn("Could not terminate the card reader checkout of {}; releasing anyway", releasedPaymentDescription, ex);
		}
	}
}
