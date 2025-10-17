package de.metas.order.paymentschedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class InvoicePayScheduleId implements RepoIdAware
{
	@JsonCreator
	public static InvoicePayScheduleId ofRepoId(final int repoId)
	{
		return new InvoicePayScheduleId(repoId);
	}

	@Nullable
	public static InvoicePayScheduleId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<InvoicePayScheduleId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private InvoicePayScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_InvoicePaySchedule_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final InvoicePayScheduleId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final InvoicePayScheduleId id1, @Nullable final InvoicePayScheduleId id2)
	{
		return Objects.equals(id1, id2);
	}
}