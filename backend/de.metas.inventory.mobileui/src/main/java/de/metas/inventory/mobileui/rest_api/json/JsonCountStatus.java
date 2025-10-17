package de.metas.inventory.mobileui.rest_api.json;

import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public enum JsonCountStatus
{
	NOT_COUNTED,
	COUNTING_IN_PROGRESS,
	COUNTED,
	;

	public static JsonCountStatus ofIsCountedFlag(final boolean isCounted)
	{
		return isCounted ? COUNTED : NOT_COUNTED;
	}

	/**
	 * @return combined status. The following rules are applied:
	 * <pre>
	 *  NOT_COUNTED + NOT_COUNTED => NOT_COUNTED
	 *  NOT_COUNTED + COUNTING_IN_PROGRESS => COUNTING_IN_PROGRESS
	 *  NOT_COUNTED + COUNTED => COUNTING_IN_PROGRESS
	 *  COUNTING_IN_PROGRESS + NOT_COUNTED => COUNTING_IN_PROGRESS
	 *  COUNTING_IN_PROGRESS + COUNTING_IN_PROGRESS => COUNTING_IN_PROGRESS
	 *  COUNTING_IN_PROGRESS + COUNTED => COUNTING_IN_PROGRESS
	 *  COUNTED + NOT_COUNTED => COUNTING_IN_PROGRESS
	 *  COUNTED + COUNTING_IN_PROGRESS => COUNTING_IN_PROGRESS
	 *  COUNTED + COUNTED => COUNTED
	 *  </pre>
	 */
	public JsonCountStatus combine(@NonNull final JsonCountStatus other)
	{
		return this.equals(other) ? this : COUNTING_IN_PROGRESS;
	}

	public static <T> Optional<JsonCountStatus> combine(@NonNull final Collection<T> list, @NonNull final Function<T, JsonCountStatus> mapper)
	{
		return list.stream().map(mapper).reduce(JsonCountStatus::combine);
	}

}
