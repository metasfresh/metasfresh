package org.adempiere.util.lang;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
public class OldAndNewValues<T>
{
	@Nullable T oldValue;
	@Nullable T newValue;

	public static <T> OldAndNewValues<T> ofOldAndNewValues(
			@Nullable final T oldValue,
			@Nullable final T newValue)
	{
		return OldAndNewValues.<T>builder().oldValue(oldValue).newValue(newValue).build();
	}

	public static <T> OldAndNewValues<T> ofSameValue(@Nullable final T value)
	{
		return value != null
				? OldAndNewValues.<T>builder().oldValue(null).newValue(null).build()
				: nullValues();
	}

	private static final OldAndNewValues<Object> NULL_VALUES = builder().build();

	public static <T> OldAndNewValues<T> nullValues()
	{
		//noinspection unchecked
		return (OldAndNewValues<T>)NULL_VALUES;
	}

	public boolean isValueChanged()
	{
		return !Objects.equals(oldValue, newValue);
	}
}
