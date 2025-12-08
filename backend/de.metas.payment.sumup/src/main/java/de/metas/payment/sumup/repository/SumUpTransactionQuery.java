package de.metas.payment.sumup.repository;

import com.google.common.collect.ImmutableSet;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class SumUpTransactionQuery
{
	@Nullable SumUpTransactionExternalId externalId;
	@Nullable SumUpClientTransactionId clientTransactionId;
	@Nullable Set<SumUpTransactionId> localIds;
	@Nullable SumUpTransactionStatus status;
	@Nullable SumUpPOSRef posRef;

	@NonNull @Builder.Default QueryLimit limit = QueryLimit.ofInt(1000);

	public static SumUpTransactionQuery ofExternalId(@NonNull SumUpTransactionExternalId externalId)
	{
		return builder().externalId(externalId).build();
	}

	public static SumUpTransactionQuery ofClientTransactionId(@NonNull SumUpClientTransactionId clientTransactionId)
	{
		return builder().clientTransactionId(clientTransactionId).build();
	}

	public static SumUpTransactionQuery ofStatus(@NonNull SumUpTransactionStatus status)
	{
		return builder().status(status).build();
	}

	public static SumUpTransactionQuery ofLocalId(@NonNull SumUpTransactionId localId)
	{
		return ofLocalIds(ImmutableSet.of(localId));
	}

	public static SumUpTransactionQuery ofLocalIds(@NonNull Set<SumUpTransactionId> localIds)
	{
		Check.assumeNotEmpty(localIds, "localIds must not be empty");
		return builder()
				.localIds(localIds)
				.limit(QueryLimit.NO_LIMIT) // the limit is already set by the number of localIds
				.build();
	}
}
