package de.metas.material.planning.pporder;

import lombok.NonNull;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.load;

/**
 * Read access to {@code PP_Order} for material planning code.
 * {@code IPPOrderDAO} lives in de.metas.manufacturing, which depends on this module, so it cannot be used here.
 */
@Repository
public class PPOrderRepository
{
	/**
	 * @return the PP_Order, or {@code null} if it does not exist (e.g. deleted meanwhile)
	 */
	@Nullable
	public I_PP_Order getByIdOrNull(@NonNull final PPOrderId ppOrderId)
	{
		return load(ppOrderId, I_PP_Order.class);
	}
}
