package de.metas.distribution.workflows_api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;
import de.metas.distribution.ddorder.DDOrderId;

@Value
@Builder
public class DistributionWFProcessStartParams
{
	@NonNull DDOrderId ddOrderId;

	public Params toParams()
	{
		return Params.builder()
				.value("ddOrderId", ddOrderId.getRepoId())
				.build();
	}

	public static DistributionWFProcessStartParams ofParams(final Params params)
	{
		try
		{
			//noinspection ConstantConditions
			return builder()
					.ddOrderId(params.getParameterAsId("ddOrderId", DDOrderId.class))
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid params: " + params, ex);
		}
	}

}
