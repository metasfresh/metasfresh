package de.metas.distribution.ddorder.lowlevel.interceptor;

import de.metas.document.engine.DocStatus;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderDocStatusChangedEvent;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.validator.PP_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * A dedicated model interceptor whose job it is to fire events on the {@link MetasfreshEventBusService}.<br>
 * I add this into a dedicated interceptor (as opposed to adding the method to {@link DD_Order}) because there is at least one test case where I want {@link PP_Order} to be invoked without events being fired.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Slf4j
@Interceptor(I_DD_Order.class)
@Component
@RequiredArgsConstructor
public class DD_Order_PostMaterialEvent
{
	@NonNull private final PostMaterialEventService materialEventService;
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final DDOrderLowLevelService ddOrderLowLevelService;
	@NonNull private final ReplenishInfoRepository replenishInfoRepository;

	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_DD_Order.COLUMNNAME_DocStatus)
	public void postMaterialEvent_ddOrderDocStatusChange(@NonNull final I_DD_Order ddOrderRecord)
	{
		final DocStatus newDocStatus = DocStatus.ofCode(ddOrderRecord.getDocStatus());

		final DDOrder ddOrder = loadDDOrderOrNull(ddOrderRecord, newDocStatus);

		materialEventService.enqueueEventAfterNextCommit(
				DDOrderDocStatusChangedEvent.builder()
						.eventDescriptor(EventDescriptor.ofClientAndOrg(ddOrderRecord.getAD_Client_ID(), ddOrderRecord.getAD_Org_ID()))
						.ddOrderId(ddOrderRecord.getDD_Order_ID())
						.newDocStatus(newDocStatus)
						.ddOrder(ddOrder)
						.build()
		);
	}

	@Nullable
	private DDOrder loadDDOrderOrNull(@NonNull final I_DD_Order ddOrderRecord, @NonNull final DocStatus newDocStatus)
	{
		if (!newDocStatus.isCompleted())
		{
			return null;
		}

		try
		{
			return DDOrderLoader.builder()
					.productPlanningDAO(productPlanningDAO)
					.distributionNetworkRepository(distributionNetworkRepository)
					.ddOrderLowLevelService(ddOrderLowLevelService)
					.replenishInfoRepository(replenishInfoRepository)
					.build()
					.load(ddOrderRecord);
		}
		catch (final Exception ex)
		{
			log.warn("Failed to load DDOrder DTO for DD_Order_ID={}; event will be sent without DDOrder payload and candidates won't be auto-created",
					ddOrderRecord.getDD_Order_ID(), ex);
			return null;
		}
	}
}
