package de.metas.distribution.ddorder.movement.schedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class DDOrderMoveScheduleId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static DDOrderMoveScheduleId ofRepoId(final int repoId)
	{
		return new DDOrderMoveScheduleId(repoId);
	}

	@Nullable
	public static DDOrderMoveScheduleId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new DDOrderMoveScheduleId(repoId) : null;
	}

	public static DDOrderMoveScheduleId ofJson(@NonNull final String string)
	{
		try
		{
			return ofRepoId(Integer.parseInt(string));
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid id: " + string);
		}
	}

	private DDOrderMoveScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "DD_Order_MoveSchedule_ID");
	}

	@JsonValue
	public int toJson() {return getRepoId();}

	public static boolean equals(@Nullable final DDOrderMoveScheduleId id1, @Nullable final DDOrderMoveScheduleId id2)
	{
		return Objects.equals(id1, id2);
	}
}
