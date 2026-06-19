package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.location.CountryId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ResourceId;
import de.metas.resource.ResourceTypeId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;

public class MasterdataContext
{
	public static final ClientId CLIENT_ID = ClientId.METASFRESH;
	public static final OrgId ORG_ID = OrgId.MAIN;
	public static final BPGroupId BP_GROUP_ID = BPGroupId.STANDARD;
	public static final CountryId COUNTRY_ID = CountryId.GERMANY;
	public static final LocalDate DEFAULT_ValidFrom = LocalDate.parse("2000-01-01");
	public static final ProductCategoryId PRODUCT_CATEGORY_STANDARD_ID = ProductCategoryId.ofRepoId(1000000);
	public static final String DEFAULT_TaxCategory_InternalName = "Normal";
	public static final BPartnerId METASFRESH_ORG_BPARTNER_ID = BPartnerId.ofRepoId(2155894);
	public static final BPartnerLocationId METASFRESH_ORG_BPARTNER_LOCATION_ID = BPartnerLocationId.ofRepoId(METASFRESH_ORG_BPARTNER_ID, 2202690);
	public static final ResourceId DEFAULT_PLANT_ID = ResourceId.ofRepoId(540006); // test
	public static final PPRoutingId DEFAULT_ROUTING_ID = PPRoutingId.ofRepoId(540118);  // Default Workflow for mobile UI Manufacturing
	public static final ResourceTypeId DEFAULT_MANUFACTURING_RESOURCE_TYPE_ID = ResourceTypeId.ofRepoId(1000000);  // S_ResourceType.Name = Produktionsressource
	public static final int STANDARD_AD_PRINTER_ID = 1000000;
	public static final int PRINT_TO_DISK_AD_PRINTERHW_ID = 540331;

	private final HashMap<TypeAndIdentifier, RepoIdAware> identifiers = new HashMap<>();
	private final HashMap<Identifier, Object> objects = new HashMap<>();

	public @NonNull String getAdLanguage() {return Env.getADLanguageOrBaseLanguage();}

	public <T extends RepoIdAware> void putIdentifier(@NonNull final Identifier identifier, @NonNull final T id)
	{
		final TypeAndIdentifier typeAndIdentifier = TypeAndIdentifier.of(id.getClass(), identifier);
		final RepoIdAware prevId = identifiers.get(typeAndIdentifier);
		if (prevId != null && !RepoIdAwares.equals(prevId, id))
		{
			throw new IllegalArgumentException("Identifier already exists: " + typeAndIdentifier
					+ "\n prevId=" + prevId
					+ "\n newId=" + id);
		}

		identifiers.put(typeAndIdentifier, id);
	}

	public <T extends RepoIdAware> void putIdentifierIfAbsent(@NonNull final Identifier identifier, @NonNull final T id)
	{
		final TypeAndIdentifier typeAndIdentifier = TypeAndIdentifier.of(id.getClass(), identifier);
		if (identifiers.containsKey(typeAndIdentifier))
		{
			return;
		}

		identifiers.put(typeAndIdentifier, id);
	}

	public <T extends RepoIdAware> ImmutableSet<T> getIds(@NonNull final Set<Identifier> identifiers, final Class<T> idClass)
	{
		return identifiers.stream()
				.map(identifier -> this.getId(identifier, idClass))
				.collect(ImmutableSet.toImmutableSet());
	}

	public <T extends RepoIdAware> T getId(@NonNull final Identifier identifier, final Class<T> idClass)
	{
		return getOptionalId(identifier, idClass)
				.orElseThrow(() -> new IllegalArgumentException("No actual ID found for " + identifier + "/" + idClass.getSimpleName() + " in " + identifiers.keySet()));
	}

	public <T extends RepoIdAware> Optional<T> getOptionalId(@NonNull final Identifier identifier, final Class<T> idClass)
	{
		if (identifier.isNullPlaceholder())
		{
			return Optional.empty();
		}

		final TypeAndIdentifier typeAndIdentifier = TypeAndIdentifier.of(idClass, identifier);
		//noinspection unchecked
		final T id = (T)identifiers.get(typeAndIdentifier);
		return Optional.ofNullable(id);
	}

	/**
	 * Suffix used when registering a BPartner's single default location.
	 * When a BPartner is created without explicit locations, a default location
	 * is created and registered with this suffix appended to the BPartner identifier.
	 *
	 * @see de.metas.frontend_testing.masterdata.bpartner.CreateBPartnerCommand
	 */
	public static final String SINGLE_BP_LOCATION_SUFFIX = "_singleBPLocationI";

	/**
	 * Resolves a BPartnerLocationId by identifier, with automatic fallback to the
	 * {@code _singleBPLocationI} suffix for BPartners that have a single default location.
	 * <p>
	 * When a BPartner is created without explicit locations, a default location is created
	 * and registered with the identifier {@code <bpIdentifier>_singleBPLocationI}.
	 * This method handles that pattern transparently.
	 *
	 * @param identifier the identifier to resolve (can be either a direct BPartnerLocationId
	 *                   identifier or a BPartnerId identifier that has a single default location)
	 * @return the resolved BPartnerLocationId, or empty if not found
	 */
	public Optional<BPartnerLocationId> getOptionalBPartnerLocationId(@NonNull final Identifier identifier)
	{
		// Try direct lookup first
		final Optional<BPartnerLocationId> directLookup = getOptionalId(identifier, BPartnerLocationId.class);
		if (directLookup.isPresent())
		{
			return directLookup;
		}

		// Fallback: try with _singleBPLocationI suffix (for single-location BPartners)
		return getOptionalId(
				Identifier.ofString(identifier.getAsString() + SINGLE_BP_LOCATION_SUFFIX),
				BPartnerLocationId.class);
	}

	/**
	 * Resolves a BPartnerLocationId by identifier, throwing if not found.
	 *
	 * @param identifier the identifier to resolve
	 * @return the resolved BPartnerLocationId
	 * @throws IllegalArgumentException if no BPartnerLocationId is found for the identifier
	 * @see #getOptionalBPartnerLocationId(Identifier)
	 */
	public BPartnerLocationId getBPartnerLocationId(@NonNull final Identifier identifier)
	{
		return getOptionalBPartnerLocationId(identifier)
				.orElseThrow(() -> new IllegalArgumentException(
						"No BPartnerLocationId found for identifier: " + identifier
								+ " (also tried: " + identifier.getAsString() + SINGLE_BP_LOCATION_SUFFIX + ")"));
	}

	public <T extends RepoIdAware> T getIdOfType(@NonNull final Class<T> idClass)
	{
		final List<T> result = getIdsOfType(idClass);

		if (result.isEmpty())
		{
			throw new IllegalArgumentException("No identifier found for " + idClass.getSimpleName());
		}
		else if (result.size() > 1)
		{
			throw new IllegalArgumentException("More than one identifier found for " + idClass.getSimpleName() + ": " + result);
		}
		else
		{
			return result.get(0);
		}
	}

	public <T extends RepoIdAware> Optional<T> getIdOfTypeIfUnique(@NonNull final Class<T> idClass)
	{
		final List<T> result = getIdsOfType(idClass);

		if (result.size() == 1)
		{
			return Optional.of(result.get(0));
		}
		else
		{
			return Optional.empty();
		}
	}

	private <T extends RepoIdAware> @NotNull List<T> getIdsOfType(final @NotNull Class<T> idClass)
	{
		return identifiers.entrySet()
				.stream()
				.filter(entry -> entry.getKey().isTypeMatch(idClass))
				.map(entry -> idClass.cast(entry.getValue()))
				.distinct()
				.collect(Collectors.toList());
	}

	public void putObject(@NonNull final Identifier identifier, @NonNull final Object object)
	{
		final Object prevObject = objects.get(identifier);
		if (prevObject != null)
		{
			throw new IllegalArgumentException("Object already exists: " + identifier
					+ "\n prevId=" + prevObject
					+ "\n newId=" + object);
		}
		objects.put(identifier, object);
	}

	public <T> T getObjectNotNull(@NonNull final Identifier identifier)
	{
		return this.<T>getObject(identifier)
				.orElseThrow(() -> new IllegalArgumentException("No object found for " + identifier + ".\n"
						+ "Available identifiers are: " + objects.keySet()));
	}

	public <T> Optional<T> getObject(@NonNull final Identifier identifier)
	{
		//noinspection unchecked
		final T object = (T)objects.get(identifier);
		return Optional.ofNullable(object);
	}

	public <T extends RepoIdAware> void putSameOrMissingId(
			@NonNull String what,
			@NonNull final Identifier identifier,
			@Nullable final T actualId,
			@NonNull final Class<T> idType
	)
	{
		if (identifier.isNullPlaceholder())
		{
			assertThat(actualId).as(what).isNull();
		}
		else
		{
			final T expectedId = getOptionalId(identifier, idType).orElse(null);
			if (expectedId == null)
			{
				assertThat(actualId).as(what).isNotNull();
				if (actualId != null)
				{
					putIdentifier(identifier, actualId);
				}
			}
			else
			{
				assertThat(actualId).as(what).isEqualTo(expectedId);
			}
		}
	}

	public Map<String, Object> toJson()
	{
		final HashMap<String, Object> result = new HashMap<>();

		identifiers.forEach((typeAndIdentifier, id) -> result.put(typeAndIdentifier.toJsonString(), convertIdToJson(id)));

		return result;
	}

	public void putFromJson(@Nullable Map<String, Object> json)
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

	private static Object convertIdToJson(@Nullable RepoIdAware id)
	{
		if (id == null)
		{
			return null;
		}
		else if (id instanceof LocatorId)
		{
			return ((LocatorId)id).toJson();
		}
		else if (id instanceof BPartnerLocationId)
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
	private static RepoIdAware convertJsonToId(@Nullable Object json, @NonNull final Class<? extends RepoIdAware> type)
	{
		if (Check.isEmpty(json))
		{
			throw new AdempiereException("Empty identifier not allowed: " + type);
		}

		try
		{
			if (type.isAssignableFrom(LocatorId.class))
			{
				return LocatorId.fromJson(json.toString());
			}
			else if (type.isAssignableFrom(BPartnerLocationId.class))
			{
				final String[] parts = json.toString().split("_");
				return BPartnerLocationId.ofRepoId(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			}
			else
			{
				return RepoIdAwares.ofObject(json, type);
			}
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed converting `" + json + "` to " + type);
		}
	}

	public <T extends RepoIdAware> String describeId(@Nullable final T id)
	{
		if (id == null)
		{
			return "<null>";
		}

		final List<TypeAndIdentifier> keys = identifiers.entrySet()
				.stream()
				.filter(entry -> Objects.equals(entry.getValue(), id))
				.map(Map.Entry::getKey)
				.distinct()
				.collect(Collectors.toList());

		if (keys.size() == 1)
		{
			final Identifier identifier = keys.get(0).getIdentifier();
			return identifier.getAsString() + "/" + id.getRepoId();
		}
		else
		{
			return id.toString();
		}
	}

	//
	//
	//
	//
	//

	@Value(staticConstructor = "of")
	private static class TypeAndIdentifier
	{
		@NonNull Class<? extends RepoIdAware> type;
		@NonNull Identifier identifier;

		public boolean isTypeMatch(@NonNull final Class<? extends RepoIdAware> type)
		{
			return this.type.equals(type);
		}

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
			catch (Exception ex)
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
