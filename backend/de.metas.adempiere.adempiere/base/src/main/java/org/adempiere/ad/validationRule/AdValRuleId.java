package org.adempiere.ad.validationRule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class AdValRuleId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static AdValRuleId ofRepoId(final int repoId)
	{
		return new AdValRuleId(repoId);
	}

	@Nullable
	public static AdValRuleId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new AdValRuleId(repoId) : null;
	}

	private AdValRuleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_Val_Rule_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
