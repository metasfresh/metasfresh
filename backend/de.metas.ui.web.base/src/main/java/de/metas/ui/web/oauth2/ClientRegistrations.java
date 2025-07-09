package de.metas.ui.web.oauth2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ClientRegistrations implements Iterable<ClientRegistration>
{
	public static final ClientRegistrations EMPTY = new ClientRegistrations(ImmutableList.of());

	private final ImmutableMap<String, ClientRegistration> byRegistrationId;

	private ClientRegistrations(final List<ClientRegistration> list)
	{
		this.byRegistrationId = Maps.uniqueIndex(list, ClientRegistration::getRegistrationId);
	}

	public static ClientRegistrations ofList(final List<ClientRegistration> list)
	{
		return !list.isEmpty() ? new ClientRegistrations(list) : EMPTY;
	}

	public boolean isEmpty() {return byRegistrationId.isEmpty();}

	public Optional<ClientRegistration> getById(@NonNull final String registrationId)
	{
		return Optional.ofNullable(byRegistrationId.get(registrationId));
	}

	@Override
	public @NotNull Iterator<ClientRegistration> iterator()
	{
		return byRegistrationId.values().iterator();
	}

	public Stream<ClientRegistration> stream()
	{
		return byRegistrationId.values().stream();
	}
}
