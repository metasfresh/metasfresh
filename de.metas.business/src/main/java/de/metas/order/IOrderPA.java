package de.metas.order;

import java.util.List;

import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

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

	/**
	 * Loads the lines of a given order from DB
	 * 
	 * @param order
	 * @param clazz
	 * @return
	 */
	<T extends I_C_OrderLine> List<T> retrieveOrderLines(I_C_Order order, Class<T> clazz);

	/**
	 * No trxName required. Implementation uses <code>order</code>'s internal trxName.
	 * <p>
	 * FIXME: move this method to a "BL" service API
	 * 
	 * @param order
	 * @param ols
	 */
	void reserveStock(I_C_Order order, I_C_OrderLine... ols);
}
