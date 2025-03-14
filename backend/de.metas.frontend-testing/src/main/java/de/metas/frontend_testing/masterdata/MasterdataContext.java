package de.metas.frontend_testing.masterdata;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.location.CountryId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ResourceId;
import de.metas.resource.ResourceTypeId;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public static final PPRoutingId DEFAULT_ROUTING_ID = PPRoutingId.ofRepoId(540075); // test
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
		if (prevId != null)
		{
			throw new IllegalArgumentException("Identifier already exists: " + typeAndIdentifier
					+ "\n prevId=" + prevId
					+ "\n newId=" + id);
		}

		identifiers.put(typeAndIdentifier, id);
	}

	public <T extends RepoIdAware> T getId(@NonNull final Identifier identifier, final Class<T> idClass)
	{
		final TypeAndIdentifier typeAndIdentifier = TypeAndIdentifier.of(idClass, identifier);
		//noinspection unchecked
		final T id = (T)identifiers.get(typeAndIdentifier);
		if (id == null)
		{
			throw new IllegalArgumentException("No identifier found for " + typeAndIdentifier + " in " + identifiers.keySet());
		}
		return id;
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
	}
}
