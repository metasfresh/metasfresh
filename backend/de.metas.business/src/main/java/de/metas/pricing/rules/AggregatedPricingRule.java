package de.metas.pricing.rules;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Helper class which aggregates multiple {@link IPricingRule}s.
 *
 * @author tsa
 *
 */
@ToString
public final class AggregatedPricingRule implements IPricingRule
{
	public static AggregatedPricingRule of(@NonNull final List<IPricingRule> rules)
	{
		return !rules.isEmpty() ? new AggregatedPricingRule(rules) : EMPTY;
	}

	public static AggregatedPricingRule ofNullables(@Nullable IPricingRule... rulesArray)
	{
		if (rulesArray == null || rulesArray.length == 0)
		{
			return EMPTY;
		}

		final ImmutableList<IPricingRule> list = Stream.of(rulesArray).filter(Objects::nonNull).collect(ImmutableList.toImmutableList());
		return of(list);
	}

	private static final Logger logger = LogManager.getLogger(AggregatedPricingRule.class);

	private static final AggregatedPricingRule EMPTY = new AggregatedPricingRule(ImmutableList.of());

	private final ImmutableList<IPricingRule> rules;

	private AggregatedPricingRule(final List<IPricingRule> rules)
	{
		this.rules = ImmutableList.copyOf(rules);
	}

	/**
	 * For optimization reasons, this method always returns true.
	 * <p>
	 * In {@link #calculate(IPricingContext, IPricingResult)}, each child {@link IPricingRule} is evaluated and executed if applies.
	 *
	 * @return always returns true
	 * @see de.metas.pricing.rules.IPricingRule#applies(de.metas.pricing.IPricingContext, de.metas.pricing.IPricingResult)
	 */
	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		return true;
	}

	/**
	 * Executes all rules that can be applied.
	 * <p>
	 * Please note that calculation won't stop after first rule that matched.
	 */
	@Override
	public void calculate(@NonNull final IPricingContext pricingCtx, @NonNull final IPricingResult result)
	{
		logger.debug("Evaluating pricing rules with pricingContext: {}", pricingCtx);

		for (final IPricingRule rule : rules)
		{
			try (final MDCCloseable ignored = MDC.putCloseable("PricingRule", rule.getClass().getSimpleName()))
			{
				// NOTE: we are NOT checking if the pricing result was already calculated, on purpose, because:
				// * we want to give flexiblity to pricing rules to override the pricing
				// * we want to support the case of Discount rules which apply on already calculated pricing result

				//
				// Preliminary check if there is a chance this pricing rule to be applied
				if (!rule.applies(pricingCtx, result))
				{
					Loggables.withLogger(logger, Level.DEBUG).addLog("Skipped rule {}, result: {}", rule, result);
					continue;
				}

				//
				// Try applying it
				rule.calculate(pricingCtx, result);

				//
				// Add it to applied pricing rules list
				// FIXME: make sure the rule was really applied (i.e. calculated). Consider asking the calculate() to return a boolean if it really did some changes.
				// At the moment, there is no way to figure out that a pricing rule which was preliminary considered as appliable
				// was not actually applied because when "calculate()" method was invoked while retrieving data,
				// it found out that it cannot be applied.
				// As a side effect on some pricing results you will get a list of applied rules like: ProductScalePrice, PriceListVersionVB, PriceListVersion, Discount,
				// which means that ProductScalePrice and PriceListVersionVB were not actually applied because they found out that while doing the "calculate()".
				result.addPricingRuleApplied(rule);
				Loggables.withLogger(logger, Level.DEBUG).addLog("Applied rule {}; calculated={}, result: {}", rule, result.isCalculated(), result);
			}
		}
	}
}
