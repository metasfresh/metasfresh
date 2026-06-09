package de.metas.invoice.paymentschedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class InvoicePayScheduleLineId implements RepoIdAware
{
	@JsonCreator
	public static InvoicePayScheduleLineId ofRepoId(final int repoId)
	{
		return new InvoicePayScheduleLineId(repoId);
	}

	@Nullable
	public static InvoicePayScheduleLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<InvoicePayScheduleLineId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private InvoicePayScheduleLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_InvoicePaySchedule_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final InvoicePayScheduleLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final InvoicePayScheduleLineId id1, @Nullable final InvoicePayScheduleLineId id2)
	{
		return Objects.equals(id1, id2);
	}
}