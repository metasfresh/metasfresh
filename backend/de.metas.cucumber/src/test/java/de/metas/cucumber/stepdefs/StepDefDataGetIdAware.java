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

	//
	// Helper methods
	default ID getId(@NonNull final StepDefDataIdentifier identifier)
	{
		return extractIdFromRecord(get(identifier));
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
}