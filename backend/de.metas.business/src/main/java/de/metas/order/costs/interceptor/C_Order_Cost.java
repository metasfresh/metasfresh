package de.metas.order.costs.interceptor;

import de.metas.document.engine.DocStatus;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.costs.OrderCostId;
import de.metas.order.costs.OrderCostRepository;
import de.metas.order.costs.OrderCostRepositorySession;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order_Cost;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order_Cost.class)
@Component
public class C_Order_Cost
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final OrderCostRepository orderCostRepository;

	public C_Order_Cost(final OrderCostRepository orderCostRepository) {this.orderCostRepository = orderCostRepository;}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onBeforeDelete(final I_C_Order_Cost record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			final DocStatus orderDocStatus = orderBL.getDocStatus(OrderId.ofRepoId(record.getC_Order_ID()));
			if (!orderDocStatus.isDraftedOrInProgress())
			{
				throw new AdempiereException("Deleting order costs is not allowed when order is not Drafted");
			}

			final OrderAndLineId createdOrderLineId = OrderCostRepositorySession.extractCreatedOrderAndLineId(record);
			if (createdOrderLineId != null)
			{
				orderBL.deleteLineById(createdOrderLineId);
			}

			orderCostRepository.deleteDetails(OrderCostId.ofRepoId(record.getC_Order_Cost_ID()));
		}
	}
}
