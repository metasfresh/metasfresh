package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MasterdataContext
{
	private final HashMap<TypeAndIdentifier, RepoIdAware> identifiers = new HashMap<>();

	public <T extends RepoIdAware> void putIdentifier(@NonNull final Identifier identifier, @NonNull final T id)
	{
		final TypeAndIdentifier typeAndIdentifier = TypeAndIdentifier.of(id.getClass(), identifier);
		final RepoIdAware prevId = identifiers.get(typeAndIdentifier);
		if (prevId != null && !Objects.equals(prevId, id))
		{
			throw new IllegalArgumentException("Identifier already exists: " + typeAndIdentifier
					+ "\n prevId=" + prevId
					+ "\n newId=" + id);
		}

		identifiers.put(typeAndIdentifier, id);
	}

	public Map<String, Object> toJson()
	{
		final HashMap<String, Object> result = new HashMap<>();

		identifiers.forEach((typeAndIdentifier, id) -> result.put(typeAndIdentifier.toJsonString(), convertIdToJson(id)));

		return result;
	}

	public void putFromJson(@Nullable final Map<String, Object> json)
	{
		if (json == null || json.isEmpty())
		{
			return;
		}

		json.forEach((key, value) -> {
			final TypeAndIdentifier typeAndIdentifier = TypeAndIdentifier.ofJsonString(key);
			final RepoIdAware id = convertJsonToId(value, typeAndIdentifier.getType());
			putIdentifier(typeAndIdentifier.getIdentifier(), id);
		});
	}

	private static Object convertIdToJson(@NonNull final RepoIdAware id)
	{
		if (id instanceof BPartnerLocationId)
		{
			final BPartnerLocationId bpLocationId = (BPartnerLocationId)id;
			return bpLocationId.getBpartnerId().getRepoId() + "_" + bpLocationId.getRepoId();
		}
		else
		{
			return id.getRepoId();
		}
	}

	@NonNull
	private static RepoIdAware convertJsonToId(@Nullable final Object json, @NonNull final Class<? extends RepoIdAware> type)
	{
		if (Check.isEmpty(json))
		{
			throw new AdempiereException("Empty identifier not allowed: " + type);
		}

		try
		{
			if (type.isAssignableFrom(BPartnerLocationId.class))
			{
				final String[] parts = json.toString().split("_");
				return BPartnerLocationId.ofRepoId(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			}
			else
			{
				return RepoIdAwares.ofObject(json, type);
			}
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed converting `" + json + "` to " + type, ex);
		}
	}

	@Value(staticConstructor = "of")
	private static class TypeAndIdentifier
	{
		@NonNull Class<? extends RepoIdAware> type;
		@NonNull Identifier identifier;

		@NonNull
		@JsonCreator
		public static TypeAndIdentifier ofJsonString(@NonNull final String json)
		{
			final int idx = json.indexOf(":");
			if (idx <= 0)
			{
				throw new IllegalArgumentException("Invalid json string: " + json);
			}

			try
			{
				final String className = json.substring(0, idx);
				final Identifier identifier = Identifier.ofString(json.substring(idx + 1));
				final Class<? extends RepoIdAware> type = Util.loadClass(RepoIdAware.class, className);
				return of(type, identifier);
			}
			catch (final Exception ex)
			{
				throw new IllegalArgumentException("Invalid json string: " + json, ex);
			}
		}

		@JsonValue
		public String toJsonString()
		{
			return type.getName() + ":" + identifier.getAsString();
		}
	}
}
