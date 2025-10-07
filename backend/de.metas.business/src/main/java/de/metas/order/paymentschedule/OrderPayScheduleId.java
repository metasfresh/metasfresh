package de.metas.order.paymentschedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class OrderPayScheduleId implements RepoIdAware
{
	@JsonCreator
	public static OrderPayScheduleId ofRepoId(final int repoId)
	{
		return new OrderPayScheduleId(repoId);
	}

	@Nullable
	public static OrderPayScheduleId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<OrderPayScheduleId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private OrderPayScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_OrderPaySchedule_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final OrderPayScheduleId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final OrderPayScheduleId id1, @Nullable final OrderPayScheduleId id2)
	{
		return Objects.equals(id1, id2);
	}
}