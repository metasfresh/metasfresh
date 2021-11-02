package de.metas.manufacturing.workflows_api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.model.I_PP_Order;

@Value
@Builder
public class ManufacturingJobLoaderSupportingServices
{
	@NonNull IPPOrderDAO ppOrderDAO;
	@NonNull IPPOrderRoutingRepository ppOrderRoutingRepository;

	public I_PP_Order getPPOrderRecordById(final PPOrderId ppOrderId)
	{
		return ppOrderDAO.getById(ppOrderId);
	}

	public PPOrderRouting getOrderRouting(final PPOrderId ppOrderId)
	{
		return ppOrderRoutingRepository.getByOrderId(ppOrderId);
	}
}
