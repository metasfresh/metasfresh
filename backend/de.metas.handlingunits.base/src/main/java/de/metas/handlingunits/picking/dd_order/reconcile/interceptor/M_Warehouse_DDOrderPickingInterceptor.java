package de.metas.handlingunits.picking.dd_order.reconcile.interceptor;

import de.metas.handlingunits.picking.dd_order.reconcile.DDOrderPickingReconcileBL;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_Warehouse.class)
@Component
@RequiredArgsConstructor
public class M_Warehouse_DDOrderPickingInterceptor
{
	@NonNull private final DDOrderPickingReconcileBL reconcileBL;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void assertConfigValid(@NonNull final I_M_Warehouse warehouse)
	{
		reconcileBL.assertWarehouseConfigurationIsValid(warehouse);
	}
}
