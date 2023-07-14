package de.metas.order.model.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerSupplierApprovalService;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.event.Topic;
import de.metas.order.event.OrderUserNotifications;
import de.metas.order.impl.OrderLineDetailRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.SpringContextHolder;

import java.util.List;

/**
 * @author metas-dev <dev@metasfresh.com>
 */
public class OrderModuleInterceptor extends AbstractModuleInterceptor
{
	private final OrderLineDetailRepository orderLineDetailRepository = SpringContextHolder.instance.getBean(OrderLineDetailRepository.class);
	private final BPartnerSupplierApprovalService bPartnerSupplierApprovalService = SpringContextHolder.instance.getBean(BPartnerSupplierApprovalService.class);
	private final IBPartnerBL bpartnerBL = SpringContextHolder.instance.getBean(IBPartnerBL.class);
	private final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);

	@Override
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of(OrderUserNotifications.USER_NOTIFICATIONS_TOPIC);
	}

	@Override
	protected void registerInterceptors(@NonNull final IModelValidationEngine engine)
	{
		engine.addModelValidator(new de.metas.order.model.interceptor.C_Order(bpartnerBL, orderLineDetailRepository, documentLocationBL, bPartnerSupplierApprovalService)); // FRESH-348
	}
}
