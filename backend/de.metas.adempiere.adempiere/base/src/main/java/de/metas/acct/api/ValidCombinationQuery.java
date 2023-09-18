package de.metas.acct.api;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.Set;

@Builder
@Jacksonized
public record ValidCombinationQuery(
		@Nullable ClientId clientId,
		@Nullable AcctSchemaId acctSchemaId,
		@Nullable AcctSchemaElementTypeAndValue elementTypeAndValue)
{
	public static ValidCombinationQuery ofAcctSchemaId(@NonNull final AcctSchemaId acctSchemaId)
	{
		return builder().acctSchemaId(acctSchemaId).build();
	}

	public static ValidCombinationQuery ofClientId(@NonNull final ClientId clientId)
	{
		return builder().clientId(clientId).build();
	}

	public static ValidCombinationQuery ofElementTypeAndValue(@NonNull AcctSchemaElementType elementType, final int value)
	{
		return builder()
				.elementTypeAndValue(AcctSchemaElementTypeAndValue.builder()
						.type(elementType)
						.value(value)
						.build())
				.build();
	}

	public static Set<ValidCombinationQuery> ofElementTypesAndValue(@NonNull Set<AcctSchemaElementType> elementTypes, final int value)
	{
		return elementTypes.stream()
				.map(elementType -> ofElementTypeAndValue(elementType, value))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Builder
	@Jacksonized
	public record AcctSchemaElementTypeAndValue(
			@NonNull AcctSchemaElementType type,
			int value
	) {}
}
