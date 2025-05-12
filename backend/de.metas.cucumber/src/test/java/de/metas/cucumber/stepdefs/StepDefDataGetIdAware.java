package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableSet;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public interface StepDefDataGetIdAware<ID extends RepoIdAware, RecordType>
{
	//
	// Methods you have to actually implement:
	ID extractIdFromRecord(RecordType record);

	//
	// Optional methods you might want to implement
	default boolean isAllowDuplicateRecordsForSameIdentifier(ID id) {return false;}

	//
	// Methods implemented by StepDefData:
	@NonNull
	RecordType get(@NonNull final StepDefDataIdentifier identifier);

	Optional<RecordType> getOptional(@NonNull final StepDefDataIdentifier identifier);

	ImmutableSet<StepDefDataIdentifier> getIdentifiers();

	void put(@NonNull final StepDefDataIdentifier identifier, @NonNull final RecordType newRecord);

	void putOrReplace(@NonNull final StepDefDataIdentifier identifier, @NonNull final RecordType record);

	//
	// Helper methods
	default ID getId(@NonNull final StepDefDataIdentifier identifier)
	{
		return extractIdFromRecord(get(identifier));
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

	default Optional<StepDefDataIdentifier> getFirstIdentifierById(@NonNull final ID id) {return getFirstIdentifierById(id, null);}

	default Optional<StepDefDataIdentifier> getFirstIdentifierById(@NonNull final ID id, @Nullable final StepDefDataIdentifier excludeIdentifier)
	{
		for (final StepDefDataIdentifier identifier : getIdentifiers())
		{
			if (excludeIdentifier != null && StepDefDataIdentifier.equals(excludeIdentifier, identifier))
			{
				continue;
			}

			final ID currentId = getId(identifier);
			if (Objects.equals(currentId, id))
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
