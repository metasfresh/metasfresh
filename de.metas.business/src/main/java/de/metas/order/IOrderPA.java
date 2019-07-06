package de.metas.order;

import org.compiere.model.I_C_Order;

import de.metas.util.ISingletonService;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @deprecated Please use {@link IOrderDAO}
 */
@Deprecated
public interface IOrderPA extends ISingletonService
{
	I_C_Order copyOrder(I_C_Order oldOrder, boolean copyLines, String trxName);

	I_C_Order retrieveOrder(int orderId, String trxName);
}
