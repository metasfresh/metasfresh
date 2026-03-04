package de.metas.inoutcandidate.qty_reservation;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class QtyReservationId implements RepoIdAware
{
	int repoId;

	public static QtyReservationId ofRepoId(final int repoId)
	{
		return new QtyReservationId(repoId);
	}

	@Nullable
	public static QtyReservationId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new QtyReservationId(repoId) : null;
	}

	private QtyReservationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_QtyReservation_ID");
	}

	@Override
	public int getRepoId()
	{
		return repoId;
	}
}
