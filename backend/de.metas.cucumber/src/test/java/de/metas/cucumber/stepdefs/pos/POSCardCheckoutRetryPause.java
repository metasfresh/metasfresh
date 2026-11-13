package de.metas.cucumber.stepdefs.pos;

import de.metas.pos.POSOrdersService;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * The wait of the till's card checkout between two attempts of storing SumUp's answer (its step 3, see
 * {@link POSOrdersService#checkoutPayment}), for cucumber. Picked up by component scanning, it replaces the plain sleep.
 * <p>
 * Normally it sleeps the given time, as the plain sleep does. Once {@link #pauseNextWait() armed}, the next wait pauses
 * until {@link #resume()} (at most 60 s): the checkout then holds no lock and no transaction, so a scenario can let other
 * things happen "while storing SumUp's answer is retried", deterministically. State is static (one per JVM), reset by
 * {@link #reset()} after every scenario.
 */
@Component
public class POSCardCheckoutRetryPause implements POSOrdersService.Sleeper
{
	private static final int MAX_PAUSE_SECONDS = 60;

	private static final Object lock = new Object();
	@Nullable private static CountDownLatch paused;
	@Nullable private static CountDownLatch resumed;
	// whether the armed pause is still to be taken by the next wait
	private static boolean armed;

	@Override
	public void sleep(@NonNull final Duration duration)
	{
		final CountDownLatch pausedNow;
		final CountDownLatch resumedNow;
		synchronized (lock)
		{
			pausedNow = armed ? paused : null;
			resumedNow = armed ? resumed : null;
			// only the next wait pauses
			armed = false;
		}

		try
		{
			if (pausedNow == null || resumedNow == null)
			{
				Thread.sleep(duration.toMillis());
				return;
			}

			pausedNow.countDown();
			if (!resumedNow.await(MAX_PAUSE_SECONDS, TimeUnit.SECONDS))
			{
				throw new IllegalStateException("The paused card checkout was not resumed within " + MAX_PAUSE_SECONDS + " s");
			}
		}
		catch (final InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * The next wait between two attempts of storing a card checkout's answer pauses until {@link #resume()}.
	 */
	public static void pauseNextWait()
	{
		synchronized (lock)
		{
			paused = new CountDownLatch(1);
			resumed = new CountDownLatch(1);
			armed = true;
		}
	}

	/**
	 * Waits (at most 60 s) until a card checkout paused in the wait armed by {@link #pauseNextWait()}.
	 */
	public static void awaitPaused() throws InterruptedException
	{
		final CountDownLatch pausedNow;
		synchronized (lock)
		{
			pausedNow = paused;
		}
		if (pausedNow == null)
		{
			throw new IllegalStateException("No pause is armed");
		}
		if (!pausedNow.await(MAX_PAUSE_SECONDS, TimeUnit.SECONDS))
		{
			throw new IllegalStateException("No card checkout paused within " + MAX_PAUSE_SECONDS + " s");
		}
	}

	/**
	 * Lets the paused card checkout go on (and stops pausing, if no checkout paused yet).
	 */
	public static void resume()
	{
		synchronized (lock)
		{
			if (resumed != null)
			{
				resumed.countDown();
			}
			paused = null;
			resumed = null;
			armed = false;
		}
	}

	public static void reset()
	{
		resume();
	}
}
