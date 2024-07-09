package de.metas.workflow.execution.approval.strategy;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

@EqualsAndHashCode
@ToString
public class UsersToApproveList implements Iterable<UserId>
{
	private final LinkedHashSet<UserId> list = new LinkedHashSet<>();

	public static UsersToApproveList empty()
	{
		return new UsersToApproveList();
	}

	public static UsersToApproveList of(@NonNull final UserId userId)
	{
		final UsersToApproveList result = empty();
		result.add(userId);
		return result;
	}

	public static UsersToApproveList ofNullable(@Nullable final UserId userId)
	{
		return userId != null ? of(userId) : empty();
	}

	public static UsersToApproveList ofCollection(@NonNull final Collection<UserId> collection)
	{
		final UsersToApproveList result = empty();
		result.addAll(collection);
		return result;
	}

	@NotNull
	@Override
	public Iterator<UserId> iterator() {return list.iterator();}

	public void addAll(@Nullable final UsersToApproveList other)
	{
		if (other == null)
		{
			return;
		}
		addAll((other.list));
	}

	public void addAll(@Nullable final Collection<UserId> collection)
	{
		if (collection == null || collection.isEmpty())
		{
			return;
		}

		collection.forEach(this::add);
	}

	public void add(@Nullable final UserId userId)
	{
		if (userId == null)
		{
			return;
		}

		list.remove(userId); // remove it so we make sure it will be added last
		list.add(userId);
	}

	public void remove(@Nullable final UserId userId)
	{
		if (userId != null)
		{
			list.remove(userId);
		}
	}

	public ImmutableList<UserId> toList() {return ImmutableList.copyOf(list);}
}
