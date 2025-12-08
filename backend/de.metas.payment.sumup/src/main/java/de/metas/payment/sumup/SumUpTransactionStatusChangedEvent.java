package de.metas.payment.sumup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class SumUpTransactionStatusChangedEvent
{
	@NonNull SumUpTransaction trx;
	@Nullable SumUpTransaction trxPrev;

	public static SumUpTransactionStatusChangedEvent ofNewTransaction(@NonNull final SumUpTransaction trx)
	{
		return builderFrom(trx).build();
	}

	public static SumUpTransactionStatusChangedEvent ofChangedTransaction(
			@NonNull final SumUpTransaction trx,
			@NonNull final SumUpTransaction trxPrev)
	{
		return builderFrom(trx)
				.trxPrev(trxPrev)
				.build();
	}

	private static SumUpTransactionStatusChangedEventBuilder builderFrom(final @NonNull SumUpTransaction trx)
	{
		return builder().trx(trx);
	}

	@NonNull
	public ClientId getClientId() {return trx.getClientAndOrgId().getClientId();}

	@NonNull
	public OrgId getOrgId() {return trx.getClientAndOrgId().getOrgId();}

	@NonNull
	public SumUpTransactionStatus getStatusNew() {return trx.getStatus();}

	@Nullable
	public SumUpPOSRef getPosRef() {return trx.getPosRef();}
}
