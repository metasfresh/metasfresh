package de.metas.manufacturing.workflows_api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;
import org.eevolution.api.PPOrderId;

@Value
@Builder
public class ManufacturingWFProcessStartParams
{
	@NonNull PPOrderId ppOrderId;

	public Params toParams()
	{
		return Params.builder()
				.value("ppOrderId", ppOrderId.getRepoId())
				.build();
	}

	public static ManufacturingWFProcessStartParams ofParams(final Params params)
	{
		try
		{
			//noinspection ConstantConditions
			return builder()
					.ppOrderId(params.getParameterAsId("ppOrderId", PPOrderId.class))
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid params: " + params, ex);
		}
	}

}
