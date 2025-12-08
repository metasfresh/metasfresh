package org.adempiere.ad.persistence.custom_columns;

import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.function.BiConsumer;

@EqualsAndHashCode
@ToString
public class CustomColumnsPOValues
{
	private static final CustomColumnsPOValues EMPTY = new CustomColumnsPOValues(ImmutableMap.of());

	private final ImmutableMap<String, Object> map;

	private CustomColumnsPOValues(@NonNull final ImmutableMap<String, Object> map)
	{
		this.map = map;
	}

	@NonNull
	public static CustomColumnsPOValues ofPOValues(@NonNull final ImmutableMap<String, Object> map)
	{
		return !map.isEmpty()
				? new CustomColumnsPOValues(map)
				: EMPTY;
	}

	public void forEach(@NonNull final BiConsumer<String, Object> consumer)
	{
		map.forEach(consumer);
	}
}
