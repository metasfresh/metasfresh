package de.metas.order.model.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerSupplierApprovalService;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.elasticsearch.IESSystem;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.event.Topic;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.event.OrderUserNotifications;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.slf4j.Logger;

import java.util.List;

/**
 * @author metas-dev <dev@metasfresh.com>
 */
public class OrderModuleInterceptor extends AbstractModuleInterceptor
{
	private static final Logger logger = LogManager.getLogger(OrderModuleInterceptor.class);
	private final OrderGroupCompensationChangesHandler groupChangesHandler = SpringContextHolder.instance.getBean(OrderGroupCompensationChangesHandler.class);
	private final OrderLineDetailRepository orderLineDetailRepository = SpringContextHolder.instance.getBean(OrderLineDetailRepository.class);
	private final BPartnerSupplierApprovalService bPartnerSupplierApprovalService = SpringContextHolder.instance.getBean(BPartnerSupplierApprovalService.class);

	private final IBPartnerBL bpartnerBL = SpringContextHolder.instance.getBean(IBPartnerBL.class);
	private final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);
	private final IESSystem esSystem = Services.get(IESSystem.class);

	@Override
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of(OrderUserNotifications.USER_NOTIFICATIONS_TOPIC);
	}

	@Override
	protected void registerInterceptors(@NonNull final IModelValidationEngine engine)
	{
		engine.addModelValidator(new de.metas.order.model.interceptor.C_Order(bpartnerBL, orderLineDetailRepository, documentLocationBL, bPartnerSupplierApprovalService)); // FRESH-348
		engine.addModelValidator(new de.metas.order.model.interceptor.C_OrderLine(groupChangesHandler, orderLineDetailRepository, bPartnerSupplierApprovalService));

		setupElasticsearchIndexing();
	}

	private void setupElasticsearchIndexing()
	{
		final BooleanWithReason enabled = esSystem.getEnabled();
		if (enabled.isFalse())
		{
			logger.info("Skip setup because elasticsearch System is not enabled (reason: {})", enabled.getReason());
			return;
		}

		try
		{
			esSystem.newModelIndexerConfig(ESModelIndexerProfile.KPI, "orders", I_C_OrderLine.class)
					.triggerOnDocumentChanged(I_C_Order.class, I_C_OrderLine.COLUMN_C_Order_ID)
					.triggerOnDelete()
					.buildAndInstall();
		}
		catch(final Exception ex)
		{
			logger.warn("Failed installing triggers for orders to elasticsearch indexer", ex);
		}
	}
}
