package de.metas.payment.paymentinstructions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
public class PaymentInstructionsId implements RepoIdAware
{
	@JsonCreator
	public static PaymentInstructionsId ofRepoId(final int repoId)
	{
		return new PaymentInstructionsId(repoId);
	}

	@Nullable
	public static PaymentInstructionsId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PaymentInstructionsId(repoId) : null;
	}

	@NonNull
	public static Optional<PaymentInstructionsId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(final PaymentInstructionsId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private PaymentInstructionsId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Payment_Instructions_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
