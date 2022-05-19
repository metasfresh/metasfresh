package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import lombok.NonNull;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.model.I_PP_Order;

import java.util.HashMap;
import java.util.Objects;

class ManufacturingJobLoader
{
	private final ManufacturingJobLoaderSupportingServices supportingServices;

	private final HashMap<PPOrderId, I_PP_Order> ppOrders = new HashMap<>();

	ManufacturingJobLoader(@NonNull final ManufacturingJobLoaderSupportingServices supportingServices)
	{
		this.supportingServices = supportingServices;
	}

	public ManufacturingJobLoader addToCache(@NonNull final I_PP_Order ppOrder)
	{
		ppOrders.put(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()), ppOrder);
		return this;
	}

	public ManufacturingJob load(final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = getPPOrderRecordById(ppOrderId);
		final PPOrderRouting routing = supportingServices.getOrderRouting(ppOrderId);

		return ManufacturingJob.builder()
				.ppOrderId(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
				.responsibleId(UserId.ofRepoId(ppOrder.getAD_User_Responsible_ID()))
				.activities(routing.getActivities()
						.stream()
						.map(this::toJobActivity)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private I_PP_Order getPPOrderRecordById(final PPOrderId ppOrderId)
	{
		return ppOrders.computeIfAbsent(ppOrderId, supportingServices::getPPOrderRecordById);
	}

	private ManufacturingJobActivity toJobActivity(@NonNull final PPOrderRoutingActivity from)
	{
		return ManufacturingJobActivity.builder()
				.ppOrderRoutingActivityId(Objects.requireNonNull(from.getId()))
				.code(from.getCode())
				.build();
	}

}
