package org.adempiere.ad.persistence.custom_columns;

import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CustomColumnsJsonValues
{
	private static final CustomColumnsJsonValues EMPTY = new CustomColumnsJsonValues(ImmutableMap.of());

	private final ImmutableMap<String, Object> map;

	private CustomColumnsJsonValues(@NonNull final ImmutableMap<String, Object> map)
	{
		this.map = map;
	}

	@NonNull
	public static CustomColumnsJsonValues ofJsonValuesMap(@NonNull final ImmutableMap<String, Object> map)
	{
		return !map.isEmpty()
				? new CustomColumnsJsonValues(map)
				: EMPTY;
	}

	@NonNull
	public ImmutableMap<String, Object> toMap()
	{
		return map;
	}
}
