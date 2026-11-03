package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableSet;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface StepDefDataGetIdAware<ID extends RepoIdAware, RecordType>
{
	//
	// Methods you have to actually implement:
	ID extractIdFromRecord(RecordType record);

	//
	// Optional methods you might want to implement
	default boolean isAllowDuplicateRecordsForSameIdentifier(ID id) {return false;}

	default ID parseId(final StepDefDataIdentifier identifier)
	{
		throw new AdempiereException("Parsing the ID from identifier not implemented for " + getClass());
	}

	//
	// Methods implemented by StepDefData:
	@NonNull
	RecordType get(@NonNull final StepDefDataIdentifier identifier);

	Optional<RecordType> getOptional(@NonNull final StepDefDataIdentifier identifier);

	ImmutableSet<StepDefDataIdentifier> getIdentifiers();

	void put(@NonNull final StepDefDataIdentifier identifier, @NonNull final RecordType newRecord);

	void putOrReplace(@NonNull final StepDefDataIdentifier identifier, @NonNull final RecordType record);

	/**
	 * @return the given identifier's record id read directly from what is already known, without loading or
	 * refreshing the record; see {@code StepDefData.peekRecordRepoId} for the exact semantics.
	 */
	Optional<Integer> peekRecordRepoId(@NonNull final StepDefDataIdentifier identifier);

	//
	// Helper methods
	default ID getId(@NonNull final StepDefDataIdentifier identifier)
	{
		return extractIdFromRecord(get(identifier));
	}

	default Set<ID> getIds(@NonNull final Collection<StepDefDataIdentifier> identifiers)
	{
		return identifiers.stream()
				.distinct()
				.map(this::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	default ID getIdOfNullable(@Nullable final StepDefDataIdentifier identifier)
	{
		return identifier == null || identifier.isNullPlaceholder() ? null : getId(identifier);
	}

	default ID getId(@NonNull final String identifier)
	{
		return getId(StepDefDataIdentifier.ofString(identifier));
	}

	default Optional<ID> getIdOptional(@NonNull final StepDefDataIdentifier identifier)
	{
		return getOptional(identifier).map(this::extractIdFromRecord);
	}

	default ID getIdOrParse(@NonNull final StepDefDataIdentifier identifier)
	{
		return getIdOptional(identifier)
				.orElseGet(() -> parseId(identifier));
	}

	default Stream<ID> streamIds() {return getIdentifiers().stream().map(this::getId).distinct();}

	default Optional<StepDefDataIdentifier> getFirstIdentifierById(@NonNull final ID id) {return getFirstIdentifierById(id, null);}

	default Optional<StepDefDataIdentifier> getFirstIdentifierById(@NonNull final ID id, @Nullable final StepDefDataIdentifier excludeIdentifier)
	{
		for (final StepDefDataIdentifier identifier : getIdentifiers())
		{
			if (excludeIdentifier != null && StepDefDataIdentifier.equals(excludeIdentifier, identifier))
			{
				continue;
			}

			// Compare against the id already known for this identifier (no load/refresh of its record) where
			// possible, so a since-deleted underlying row (e.g. a compensation line removed by application logic
			// after being identified) can no longer break registering an unrelated, later identifier. Only
			// identifiers whose item has nothing to peek at (a plain, non-model record -- never lazily loaded
			// anyway) fall back to the normal id lookup.
			final boolean matches = peekRecordRepoId(identifier)
					.map(repoId -> repoId == id.getRepoId())
					.orElseGet(() -> Objects.equals(getId(identifier), id));
			if (matches)
			{
				return Optional.of(identifier);
			}
		}

		return Optional.empty();
	}

	default Optional<StepDefDataIdentifier> getFirstIdentifierByRecord(@NonNull final RecordType record)
	{
		return getFirstIdentifierById(extractIdFromRecord(record));
	}

	default Optional<RecordType> getFirstById(@NonNull final ID id) {return getFirstIdentifierById(id, null).map(this::get);}

	default void putOrReplaceIfSameId(final StepDefDataIdentifier identifier, final RecordType newRecord)
	{
		final ID newId = extractIdFromRecord(newRecord);
		final ID currentId = getIdOptional(identifier).orElse(null);
		if (currentId != null && !Objects.equals(currentId, newId))
		{
			throw new RuntimeException("Cannot replace " + identifier + " because its current id is " + currentId + " and the new id is " + newId);
		}

		putOrReplace(identifier, newRecord);
	}
}
