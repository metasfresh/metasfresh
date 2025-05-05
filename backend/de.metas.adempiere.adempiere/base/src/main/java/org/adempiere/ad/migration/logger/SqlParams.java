package org.adempiere.ad.migration.logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(of = "map")
@ToString(of = "map")
public class SqlParams
{
	public static final SqlParams EMPTY = new SqlParams(ImmutableMap.of());

	private final Map<Integer, Object> map;
	private transient List<Object> _list = null; // lazy

	private SqlParams(@NonNull final Map<Integer, Object> map)
	{
		this.map = map;
	}

	public static SqlParams ofMap(@Nullable final Map<Integer, Object> sqlParamsMap)
	{
		if (sqlParamsMap == null || sqlParamsMap.isEmpty())
		{
			return SqlParams.EMPTY;
		}

		// no need to copy if already immutable
		if (sqlParamsMap instanceof ImmutableMap)
		{
			return new SqlParams(sqlParamsMap);
		}

		return new SqlParams(new HashMap<>(sqlParamsMap));
	}

	public boolean isEmpty() {return map.isEmpty();}

	public List<Object> toList()
	{
		List<Object> list = this._list;
		if (list == null)
		{
			list = this._list = buildList();
		}
		return list;
	}

	private List<Object> buildList()
	{
		if (map.isEmpty())
		{
			return ImmutableList.of();
		}

		final int lastIndex = map.keySet()
				.stream()
				.mapToInt(key -> key)
				.max()
				.orElseThrow(() -> new AdempiereException("Params shall have at least one element: " + map));

		final ArrayList<Object> list = new ArrayList<>(lastIndex + 1);
		for (int index = 1; index <= lastIndex; index++)
		{
			final Object value = map.get(index);
			list.add(value);
		}

		return Collections.unmodifiableList(list);
	}
}
