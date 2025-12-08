package de.metas.business_rule.descriptor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_AD_BusinessRule_Precondition;

import javax.annotation.Nullable;

@Value
public class BusinessRulePreconditionId implements RepoIdAware
{
	int repoId;

	private BusinessRulePreconditionId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_AD_BusinessRule_Precondition.COLUMNNAME_AD_BusinessRule_Precondition_ID);
	}

	@JsonCreator
	public static BusinessRulePreconditionId ofRepoId(final int repoId) {return new BusinessRulePreconditionId(repoId);}

	@Nullable
	public static BusinessRulePreconditionId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new BusinessRulePreconditionId(repoId) : null;}

	public static int toRepoId(@Nullable final BusinessRulePreconditionId BusinessRulePreconditionId) {return BusinessRulePreconditionId != null ? BusinessRulePreconditionId.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
