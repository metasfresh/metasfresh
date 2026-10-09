package de.metas.ui.web.oauth2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class MetasfreshClientRegistrations implements Iterable<MetasfreshClientRegistration>
{
	public static final MetasfreshClientRegistrations EMPTY = new MetasfreshClientRegistrations(ImmutableList.of());

	private final ImmutableMap<String, MetasfreshClientRegistration> byRegistrationId;

	private MetasfreshClientRegistrations(final List<MetasfreshClientRegistration> list)
	{
		this.byRegistrationId = Maps.uniqueIndex(list, MetasfreshClientRegistration::getRegistrationId);
	}

	public static MetasfreshClientRegistrations ofList(final List<MetasfreshClientRegistration> list)
	{
		return !list.isEmpty() ? new MetasfreshClientRegistrations(list) : EMPTY;
	}

	public static Collector<MetasfreshClientRegistration, ?, MetasfreshClientRegistrations> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(MetasfreshClientRegistrations::ofList);
	}

	public boolean isEmpty() {return byRegistrationId.isEmpty();}

	public Optional<MetasfreshClientRegistration> getById(@NonNull final String registrationId)
	{
		return Optional.ofNullable(byRegistrationId.get(registrationId));
	}

	@Override
	public @NotNull Iterator<MetasfreshClientRegistration> iterator()
	{
		return byRegistrationId.values().iterator();
	}

	public Stream<MetasfreshClientRegistration> stream()
	{
		return byRegistrationId.values().stream();
	}
}
