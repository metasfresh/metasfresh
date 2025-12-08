package de.metas.notification;

import com.google.common.collect.ImmutableSet;
import de.metas.util.GuavaCollectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Collection;
import java.util.stream.Collector;

@Value
@EqualsAndHashCode(doNotUseGetters = true)
public class NotificationGroupCCs
{
	@NonNull public static final NotificationGroupCCs EMPTY = new NotificationGroupCCs(ImmutableSet.of());

	@NonNull @Getter(AccessLevel.NONE) ImmutableSet<Recipient> recipients;

	private NotificationGroupCCs(@NonNull final ImmutableSet<Recipient> recipients)
	{
		this.recipients = recipients;
	}

	private static NotificationGroupCCs ofCollection(@NonNull final Collection<Recipient> recipients)
	{
		return recipients.isEmpty() ? EMPTY : new NotificationGroupCCs(ImmutableSet.copyOf(recipients));
	}

	public static Collector<Recipient, ?, NotificationGroupCCs> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(NotificationGroupCCs::ofCollection);
	}

	public ImmutableSet<Recipient> toSet() {return recipients;}
}
