package de.metas.util;

import com.google.common.collect.ImmutableSet;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@EqualsAndHashCode
@ToString
public final class InSetPredicate<T> implements Predicate<T>
{
	private enum Mode
	{ANY, NONE, ONLY}

	@NonNull private final Mode mode;
	private final Set<T> onlyValues;

	private static final InSetPredicate<Object> ANY = new InSetPredicate<>(Mode.ANY, ImmutableSet.of());
	private static final InSetPredicate<Object> NONE = new InSetPredicate<>(Mode.NONE, ImmutableSet.of());

	private InSetPredicate(
			@NonNull final Mode mode,
			@NonNull final Set<T> onlyValues)
	{
		if (mode == Mode.ONLY && onlyValues.isEmpty())
		{
			throw Check.mkEx("onlyValues shall not be empty when mode is ONLY");
		}

		this.mode = mode;
		this.onlyValues = onlyValues;
	}

	public static <T> InSetPredicate<T> any()
	{
		//noinspection unchecked
		return (InSetPredicate<T>)ANY;
	}

	public static <T> InSetPredicate<T> none()
	{
		//noinspection unchecked
		return (InSetPredicate<T>)NONE;
	}

	public static <T> InSetPredicate<T> only(@NonNull final Set<T> onlyValues)
	{
		return onlyValues.isEmpty()
				? none()
				: new InSetPredicate<>(Mode.ONLY, toUnmodifiableSet(new HashSet<>(onlyValues)));
	}

	@SafeVarargs
	public static <T> InSetPredicate<T> only(final T... onlyValues)
	{
		return onlyValues == null || onlyValues.length == 0
				? none()
				: new InSetPredicate<>(Mode.ONLY, toUnmodifiableSet(Arrays.asList(onlyValues)));
	}

	private static <T> Set<T> toUnmodifiableSet(@NonNull final Collection<T> collection)
	{
		if (collection.isEmpty())
		{
			return ImmutableSet.of();
		}
		else if (collection instanceof ImmutableSet)
		{
			return (ImmutableSet<T>)collection;
		}
		else
		{
			// avoid using ImmutableSet because the given set might contain null values
			return Collections.unmodifiableSet(new HashSet<>(collection));
		}
	}

	public boolean isAny() {return mode == Mode.ANY;}

	public boolean isNone() {return mode == Mode.NONE;}

	public boolean isOnly() {return mode == Mode.ONLY;}

	@Override
	public boolean test(@Nullable final T value)
	{
		if (mode == Mode.ANY)
		{
			return true;
		}
		else if (mode == Mode.NONE)
		{
			return false;
		}
		else if (mode == Mode.ONLY)
		{
			return onlyValues.contains(value);
		}
		else
		{
			throw Check.mkEx("Unknown mode: " + this); // shall not happen
		}
	}

	private void assertNotAny()
	{
		if (isAny())
		{
			throw Check.mkEx("Expected predicate to not be ANY");
		}
	}

	public @NonNull Set<T> toSet()
	{
		assertNotAny();
		return onlyValues; // we can return it as is because it's already readonly
	}
}
