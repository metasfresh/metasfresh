package de.metas.hu_consolidation.mobile.job;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.handlingunits.model.I_M_HU_Consolidation_Job;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

import static de.metas.hu_consolidation.mobile.HUConsolidationApplication.APPLICATION_ID;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class HUConsolidationJobId implements RepoIdAware
{
	int repoId;

	private HUConsolidationJobId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_M_HU_Consolidation_Job.COLUMNNAME_M_HU_Consolidation_Job_ID);
	}

	public static HUConsolidationJobId ofRepoId(final int repoId) {return new HUConsolidationJobId(repoId);}

	@Nullable
	public static HUConsolidationJobId ofRepoIdOrNull(@Nullable final Integer repoId) {return repoId != null && repoId > 0 ? new HUConsolidationJobId(repoId) : null;}

	public static Optional<HUConsolidationJobId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	@JsonCreator
	public static HUConsolidationJobId ofObject(@NonNull final Object object) {return RepoIdAwares.ofObject(object, HUConsolidationJobId.class, HUConsolidationJobId::ofRepoId);}

	public static int toRepoId(@Nullable final HUConsolidationJobId id) {return id != null ? id.getRepoId() : -1;}

	@JsonValue
	public int toJson() {return getRepoId();}

	public static boolean equals(@Nullable final HUConsolidationJobId o1, @Nullable final HUConsolidationJobId o2) {return Objects.equals(o1, o2);}

	public WFProcessId toWFProcessId() {return WFProcessId.ofIdPart(APPLICATION_ID, this);}

	public static HUConsolidationJobId ofWFProcessId(@NonNull final WFProcessId wfProcessId)
	{
		return wfProcessId.getRepoIdAssumingApplicationId(APPLICATION_ID, HUConsolidationJobId::ofRepoId);
	}

}
