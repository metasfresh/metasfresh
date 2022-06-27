package org.adempiere.util.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

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
		if (oldValue == null && newValue == null)
		{
			return nullValues();
		}

		return OldAndNewValues.<T>builder().oldValue(oldValue).newValue(newValue).build();
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

	public <NT> OldAndNewValues<NT> mapNonNulls(@NonNull final Function<T, NT> mapper)
	{
		final OldAndNewValues<NT> result = ofOldAndNewValues(
				oldValue != null ? mapper.apply(oldValue) : null,
				newValue != null ? mapper.apply(newValue) : null
		);

		if (this.equals(result))
		{
			//noinspection unchecked
			return (OldAndNewValues<NT>)this;
		}

		return result;
	}

	public <NT> OldAndNewValues<NT> map(@NonNull final Function<T, NT> mapper)
	{
		final OldAndNewValues<NT> result = ofOldAndNewValues(
				mapper.apply(oldValue),
				mapper.apply(newValue)
		);

		if (this.equals(result))
		{
			//noinspection unchecked
			return (OldAndNewValues<NT>)this;
		}

		return result;
	}

}
