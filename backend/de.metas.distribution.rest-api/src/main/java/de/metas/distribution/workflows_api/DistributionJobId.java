package de.metas.distribution.workflows_api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.util.lang.RepoIdAwares;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
public class DistributionJobId
{
	@NonNull private final DDOrderId ddOrderId;

	private DistributionJobId(@NonNull final DDOrderId ddOrderId)
	{
		this.ddOrderId = ddOrderId;
	}

	public static DistributionJobId ofDDOrderId(final @NonNull DDOrderId ddOrderId)
	{
		return new DistributionJobId(ddOrderId);
	}

	@JsonCreator
	public static DistributionJobId ofJson(final @NonNull Object json)
	{
		final DDOrderId ddOrderId = RepoIdAwares.ofObject(json, DDOrderId.class, DDOrderId::ofRepoId);
		return ofDDOrderId(ddOrderId);
	}

	public static DistributionJobId ofWFProcessId(final WFProcessId wfProcessId)
	{
		final DDOrderId ddOrderId = wfProcessId.getRepoIdAssumingApplicationId(DistributionMobileApplication.APPLICATION_ID, DDOrderId::ofRepoId);
		return ofDDOrderId(ddOrderId);
	}

	@JsonValue
	public String toString()
	{
		return toJson();
	}

	@NonNull
	private String toJson()
	{
		return String.valueOf(ddOrderId.getRepoId());
	}

	public DDOrderId toDDOrderId() {return ddOrderId;}

	public WFProcessId toWFProcessId() {return WFProcessId.ofIdPart(DistributionMobileApplication.APPLICATION_ID, ddOrderId);}
}
