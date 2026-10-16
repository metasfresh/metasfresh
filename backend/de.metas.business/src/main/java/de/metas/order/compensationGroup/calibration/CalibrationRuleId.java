package de.metas.order.compensationGroup.calibration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class CalibrationRuleId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static CalibrationRuleId ofRepoId(final int repoId)
	{
		return new CalibrationRuleId(repoId);
	}

	@Nullable
	public static CalibrationRuleId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final CalibrationRuleId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	private CalibrationRuleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_CompensationGroup_CalibrationRule_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final CalibrationRuleId id1, @Nullable final CalibrationRuleId id2)
	{
		return Objects.equals(id1, id2);
	}
}
