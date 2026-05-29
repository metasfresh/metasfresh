package de.metas.cucumber.stepdefs;

import com.google.common.base.Stopwatch;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.ItemProvider.ProviderResult;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
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
	@Nullable private final Supplier<T> dataSupplier;
	@Nullable private final Function<T, BooleanWithReason> validateUsingFunction;
	@Nullable private final Supplier<String> logContext;

	@Builder
	private ItemFetcherExecutor(
			@Nullable final Integer maxWaitSeconds,
			@Nullable final Long checkingIntervalMs,
			@Nullable final ItemProvider<T> worker,
			@Nullable final IQuery<T> query,
			@Nullable final Supplier<T> dataSupplier,
			@Nullable final Function<T, BooleanWithReason> validateUsingFunction,
			@Nullable final Supplier<String> logContext)
	{
		this.maxWaitSeconds = CoalesceUtil.coalesceNotNull(maxWaitSeconds, 60);
		this.checkingIntervalMs = CoalesceUtil.coalesceNotNull(checkingIntervalMs, 500L);
		this.workerParam = worker;
		this.query = query;
		this.dataSupplier = dataSupplier;
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
				return optionalItem.map(ProviderResult::resultWasFound)
						.orElseGet(() -> ProviderResult.resultWasNotFound("item not found"));
			});
		}

		public ItemFetcherExecutorBuilder<T> workerFromBooleanSupplier(@NonNull final Supplier<Boolean> booleanSupplier)
		{
			return worker(() -> {
				final Boolean ok = booleanSupplier.get();
				if (ok != null && ok)
				{
					return ProviderResult.resultWasFound(null);
				}
				else
				{
					return ProviderResult.resultWasNotFound("Not valid yet");
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
		ProviderResult<T> lastWorkerResult = worker.execute();
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
			Check.assumeNull(dataSupplier, "dataSupplier is not set");
			Check.assumeNull(validateUsingFunction, "validatorFunction is not set");
			return workerParam;
		}
		else if (query != null)
		{
			Check.assumeNull(workerParam, "workerParam is not set");
			Check.assumeNull(dataSupplier, "dataSupplier is not set");
			return new QueryBasedItemProvider<>(query, validateUsingFunction);
		}
		else if (dataSupplier != null)
		{
			Check.assumeNull(workerParam, "workerParam is not set");
			Check.assumeNull(query, "query is not set");
			return new DataSupplierBasedItemProvider<>(dataSupplier, validateUsingFunction);
		}
		else
		{
			throw new AdempiereException("worker or query must be set");
		}
	}

	//
	//
	// ----------------------------------------------------------------------------
	//
	//

	@RequiredArgsConstructor
	private static class QueryBasedItemProvider<T> implements ItemProvider<T>
	{
		@NonNull private final IQuery<T> query;
		@Nullable private final Function<T, BooleanWithReason> validateUsingFunction;

		@Override
		public ProviderResult<T> execute()
		{
			SharedTestContext.put("query", query);

			final List<T> items = query.list();
			SharedTestContext.put("items", items);

			return checkValid(items);
		}

		private ProviderResult<T> checkValid(final List<T> items)
		{
			if (items.isEmpty())
			{
				return ProviderResult.resultWasNotFound("No item found for " + query);
			}
			else if (items.size() == 1)
			{
				return checkValid(items.get(0));
			}
			else
			{
				final ArrayList<T> validItems = new ArrayList<>();
				final ArrayList<String> notValidErrors = new ArrayList<>();
				for (final T item : items)
				{
					final ProviderResult<T> valid = checkValid(item);
					if (valid.isResultFound())
					{
						validItems.add(item);
					}
					else
					{
						notValidErrors.add(valid.getLog());
					}
				}

				if (validItems.isEmpty())
				{
					return ProviderResult.resultWasNotFound("No valid items found because "
							+ "\n\t" + String.join("\n\t", notValidErrors));
				}
				else if (validItems.size() == 1)
				{
					return ProviderResult.resultWasFound(validItems.get(0));
				}
				else
				{
					return ProviderResult.resultWasNotFound("Multiple valid items found: " + validItems);
				}
			}
		}

		private ProviderResult<T> checkValid(final T item)
		{
			if (validateUsingFunction != null)
			{
				try
				{
					final BooleanWithReason valid = validateUsingFunction.apply(item);
					if (valid.isFalse())
					{
						return ProviderResult.resultWasNotFound(valid.getReasonAsString());
					}
				}
				catch (final Exception | AssertionError ex)
				{
					return ProviderResult.resultWasNotFound(ex);
				}
			}

			return ProviderResult.resultWasFound(item);
		}

	}

	//
	//
	// ----------------------------------------------------------------------------
	//
	//

	@RequiredArgsConstructor
	private static class DataSupplierBasedItemProvider<T> implements ItemProvider<T>
	{
		@NonNull private final Supplier<T> dataSupplier;
		@Nullable private final Function<T, BooleanWithReason> validateUsingFunction;

		@Override
		public ProviderResult<T> execute()
		{
			final T data = dataSupplier.get();
			SharedTestContext.put("data", data);

			return checkValid(data);
		}

		private ProviderResult<T> checkValid(final T data)
		{
			if (validateUsingFunction != null)
			{
				try
				{
					final BooleanWithReason valid = validateUsingFunction.apply(data);
					if (valid.isFalse())
					{
						return ProviderResult.resultWasNotFound(valid.getReasonAsString());
					}
				}
				catch (final Exception | AssertionError ex)
				{
					return ProviderResult.resultWasNotFound(ex);
				}
			}

			return ProviderResult.resultWasFound(data);
		}

	}

}
