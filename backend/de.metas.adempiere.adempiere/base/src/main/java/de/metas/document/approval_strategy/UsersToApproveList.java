package de.metas.document.approval_strategy;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

class UsersToApproveList implements Iterable<UserId>
{
	private final ArrayList<UserId> list = new ArrayList<>();

	public static UsersToApproveList empty()
	{
		return new UsersToApproveList();
	}

	public static UsersToApproveList ofNullable(@Nullable final UserId userId)
	{
		final UsersToApproveList result = empty();
		result.add(userId);
		return result;
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

		list.remove(userId);
		list.add(userId);
	}

	public ImmutableList<UserId> toList() {return ImmutableList.copyOf(list);}
}
