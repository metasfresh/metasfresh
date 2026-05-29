package de.metas.frontend_testing.masterdata.product;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.security.SecureRandom;
import java.util.function.Predicate;

public class RandomProductValueGenerator
{
	private static final Logger logger = LogManager.getLogger(RandomProductValueGenerator.class);

	private static final int DEFAULT_SIZE = 10;
	private static final int MAX_RETRIES = 20;
	private static final String DIGITS = "0123456789";
	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private final SecureRandom random = new SecureRandom();

	private final int size;
	@NonNull private final String characterPool;
	@Nullable private final Predicate<String> validPredicate;

	@Builder
	public RandomProductValueGenerator(
			final int size,
			final boolean isIncludeDigits,
			final boolean isIncludeLetters,
			@Nullable final Predicate<String> validPredicate)
	{
		this.size = size > 0 ? size : DEFAULT_SIZE;
		this.validPredicate = validPredicate;

		final StringBuilder characterPoolBuilder = new StringBuilder();
		if (isIncludeDigits)
		{
			characterPoolBuilder.append(DIGITS);
		}
		if (isIncludeLetters)
		{
			characterPoolBuilder.append(LETTERS);
		}
		this.characterPool = characterPoolBuilder.toString();
		if (this.characterPool.isEmpty())
		{
			throw new AdempiereException("No character pool was specified!");
		}
	}

	public String next()
	{
		for (int i = 1; i <= MAX_RETRIES; i++)
		{
			@NotNull final String productValue = nextNoValidate();
			if (validPredicate == null || validPredicate.test(productValue))
			{
				return productValue;
			}

			logger.info("Failed to generate a valid product value ({}). Retrying... (try={}/{})", productValue, i, MAX_RETRIES);
		}

		throw new AdempiereException("Failed to generate a valid product value after " + MAX_RETRIES + " retries");
	}

	@NotNull
	private String nextNoValidate()
	{
		final StringBuilder result = new StringBuilder(size);

		for (int i = 0; i < size; i++)
		{
			int index = random.nextInt(characterPool.length());
			result.append(characterPool.charAt(index));
		}
		return result.toString();
	}

}
