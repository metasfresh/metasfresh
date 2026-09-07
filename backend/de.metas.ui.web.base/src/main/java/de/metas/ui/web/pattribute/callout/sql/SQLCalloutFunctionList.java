package de.metas.ui.web.pattribute.callout.sql;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

@EqualsAndHashCode
@ToString
public class SQLCalloutFunctionList implements Iterable<SQLCalloutFunction>
{
	public static final SQLCalloutFunctionList EMPTY = new SQLCalloutFunctionList(ImmutableList.of());

	private final ImmutableList<SQLCalloutFunction> list;

	private SQLCalloutFunctionList(@NonNull final ImmutableList<SQLCalloutFunction> list)
	{
		this.list = list;
	}

	public static SQLCalloutFunctionList ofList(@NonNull final List<SQLCalloutFunction> list)
	{
		return list.isEmpty() ? EMPTY : new SQLCalloutFunctionList(ImmutableList.copyOf(list));
	}

	@Override
	public @NotNull Iterator<SQLCalloutFunction> iterator()
	{
		return list.iterator();
	}

	public boolean isEmpty() {return list.isEmpty();}
}
