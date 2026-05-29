package de.metas.picking.rest_api.json;

import de.metas.manufacturing.workflows_api.ManufacturingWFProcessStartParams;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.eevolution.api.PPOrderId;

import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonPickFromManufacturingOrder
{
	@NonNull Map<String, Object> wfParameters;
	
	public static JsonPickFromManufacturingOrder ofPPOrderId(@NonNull final PPOrderId ppOrderId)
	{
		return builder()
				.wfParameters(ManufacturingWFProcessStartParams.ofPPOrderId(ppOrderId).toParams().toJson())
				.build();
	}
}
