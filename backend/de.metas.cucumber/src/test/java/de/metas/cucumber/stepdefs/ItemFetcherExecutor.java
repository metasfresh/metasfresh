package de.metas.cucumber.stepdefs;

import com.google.common.base.Stopwatch;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemFetcherExecutor<T>
{
	private static final Logger logger = LogManager.getLogger(ItemFetcherExecutor.class);

	private final int maxWaitSeconds;
	private final long checkingIntervalMs;
	@Nullable private final ItemProvider<T> workerParam;
	@Nullable private final IQuery<T> query;
	@Nullable private final Function<T, BooleanWithReason> validateUsingFunction;
	@Nullable private final Supplier<String> logContext;

	@Builder
	private ItemFetcherExecutor(
			@Nullable final Integer maxWaitSeconds,
			@Nullable final Long checkingIntervalMs,
			@Nullable final ItemProvider<T> worker,
			@Nullable final IQuery<T> query,
			@Nullable final Function<T, BooleanWithReason> validateUsingFunction,
			@Nullable final Supplier<String> logContext)
	{
		this.maxWaitSeconds = CoalesceUtil.coalesceNotNull(maxWaitSeconds, 60);
		this.checkingIntervalMs = CoalesceUtil.coalesceNotNull(checkingIntervalMs, 500L);
		this.workerParam = worker;
		this.query = query;
		this.validateUsingFunction = validateUsingFunction;
		this.logContext = logContext;
	}

	public static class ItemFetcherExecutorBuilder<T>
	{
		public T execute() throws InterruptedException
		{
			return build().execute();
		}

		public ItemFetcherExecutorBuilder<T> workerFromOptionalSupplier(@NonNull final Supplier<Optional<T>> optionalSupplier)
		{
			return worker(() -> {
				final Optional<T> optionalItem = optionalSupplier.get();
				return optionalItem.map(ItemProvider.ProviderResult::resultWasFound)
						.orElseGet(() -> ItemProvider.ProviderResult.resultWasNotFound("item not found"));
			});
		}

		public ItemFetcherExecutorBuilder<T> workerFromBooleanSupplier(@NonNull final Supplier<Boolean> booleanSupplier)
		{
			return worker(() -> {
				final Boolean ok = booleanSupplier.get();
				if (ok != null && ok)
				{
					return ItemProvider.ProviderResult.resultWasFound(null);
				}
				else
				{
					return ItemProvider.ProviderResult.resultWasNotFound("Not valid yet");
				}
			});
		}

		public ItemFetcherExecutorBuilder<T> validateUsingConsumer(@Nullable final Consumer<T> validator)
		{
			final Function<T, BooleanWithReason> validatorFunction;
			if (validator == null)
			{
				validatorFunction = null;
			}
			else
			{
				validatorFunction = (item) -> {
					validator.accept(item);
					return BooleanWithReason.TRUE;
				};
			}

			return validateUsingFunction(validatorFunction);
		}

		public ItemFetcherExecutorBuilder<T> logContextUsingRunnable(@Nullable final Runnable runnable)
		{
			if (runnable != null)
			{
				return logContext(() -> {
					runnable.run();
					return "context logged above";
				});
			}
			else
			{
				return logContext(null);
			}

		}
	}

	public T execute() throws InterruptedException
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		//
		// First try, lets be optimistic that we might get the result on first try
		final ItemProvider<T> worker = createWorker();
		ItemProvider.ProviderResult<T> lastWorkerResult = worker.execute();
		if (lastWorkerResult.isResultFound())
		{
			return lastWorkerResult.getResult();
		}
		else
		{
			logger.info("Got no result on first try because: {}", lastWorkerResult.getLog());
		}

		//
		// Wait given millis and then invoke the worker again and again until max wait time
		int iteration = 1; // NOTE: start from 1 because we already had the first iteration above
		final long maxWaitSeconds = StepDefUtil.getMaxWaitSecondsEffective(this.maxWaitSeconds);
		final long deadLineMillis = StepDefUtil.computeDeadLineMillis(maxWaitSeconds);
		while (deadLineMillis > System.currentTimeMillis())
		{
			iteration++;
			logger.info("Waiting {}ms (iteration={}, accumulated wait time={}, maxWaitSeconds={})", checkingIntervalMs, iteration, stopwatch, maxWaitSeconds);
			//noinspection BusyWait
			Thread.sleep(checkingIntervalMs);

			lastWorkerResult = worker.execute();
			if (lastWorkerResult.isResultFound())
			{
				stopwatch.stop();
				final T resultValue = lastWorkerResult.getResult();
				logger.info("Got result after {} tries and it took {}: {}", iteration, stopwatch, resultValue);
				return resultValue;
			}
			else
			{
				logger.info("After {} tries and {}, got NO result because: {}", iteration, stopwatch, lastWorkerResult.getLog());
			}
		}

		//
		// Got no result
		final String context = Optional.ofNullable(logContext).map(Supplier::get).orElse("not provided");
		Assertions.fail("the given supplier didn't succeed within the " + maxWaitSeconds + " seconds timeout (" + iteration + " tries). "
				+ "The logging output of the last try is:\n" + lastWorkerResult.getLog()
				+ "\n Context: " + context);
		return null; // will never get here because fail throws
	}

	@NonNull
	private ItemProvider<T> createWorker()
	{
		if (workerParam != null)
		{
			Check.assumeNull(query, "query is not set");
			Check.assumeNull(validateUsingFunction, "validatorFunction is not set");
			return workerParam;
		}
		else if (query != null)
		{
			return createWorkerFromQueryAndValidator(query, validateUsingFunction);
		}
		else
		{
			throw new AdempiereException("worker or query must be set");
		}
	}

	@NonNull
	private static <T> ItemProvider<T> createWorkerFromQueryAndValidator(
			@NonNull final IQuery<T> query,
			@Nullable final Function<T, BooleanWithReason> validateUsingFunction)
	{
		return () -> {
			SharedTestContext.put("query", query);

			final T item = query.firstOnlyOptional().orElse(null);
			if (item == null)
			{
				return ItemProvider.ProviderResult.resultWasNotFound("No item found for " + query);
			}

			if (validateUsingFunction != null)
			{
				SharedTestContext.put("item", item);

				try
				{
					final BooleanWithReason valid = validateUsingFunction.apply(item);
					if (valid.isFalse())
					{
						return ItemProvider.ProviderResult.resultWasNotFound(valid.getReasonAsString());
					}
				}
				catch (final Exception | AssertionError ex)
				{
					return ItemProvider.ProviderResult.resultWasNotFound(ex.getLocalizedMessage());
				}
			}

			return ItemProvider.ProviderResult.resultWasFound(item);
		};
	}
}
