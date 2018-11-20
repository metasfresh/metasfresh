package de.metas.material.planning.pporder;

import java.util.List;

import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.util.ISingletonService;

public interface IPPOrderBOMDAO extends ISingletonService
{

	/**
	 * Retrieve (active) BOM Lines for a specific PP Order
	 * 
	 * @param order
	 * @return
	 */
	List<I_PP_Order_BOMLine> retrieveOrderBOMLines(I_PP_Order order);

	/**
	 * Retrieve (active) BOM Lines for a specific PP Order
	 * 
	 * @param order
	 * @param orderBOMLineClass
	 * @return
	 */
	<T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLines(I_PP_Order order, Class<T> orderBOMLineClass);

	<T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLines(PPOrderId orderId, Class<T> orderBOMLineClass);

	/**
	 * Retrive all (active or not) BOM Lines for given <code>orderBOM</code>.
	 * 
	 * @param orderBOM
	 * @return
	 */
	List<I_PP_Order_BOMLine> retrieveAllOrderBOMLines(I_PP_Order_BOM orderBOM);

	/**
	 * Retrive (active) Order BOM Line alternatives for given Component BOM Line.
	 * 
	 * @param orderBOMLine
	 * @param orderBOMLineClass
	 * @return
	 */
	<T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLineAlternatives(I_PP_Order_BOMLine orderBOMLine, Class<T> orderBOMLineClass);

	/**
	 * Retrieve Main Component BOM Line for a given alternative BOM Line
	 * 
	 * @param orderBOMLineAlternative
	 * @return
	 */
	I_PP_Order_BOMLine retrieveComponentBOMLineForAlternative(I_PP_Order_BOMLine orderBOMLineAlternative);

	I_PP_Order_BOM retrieveOrderBOM(I_PP_Order order);

	int retrieveNextLineNo(I_PP_Order order);

	I_PP_Order_BOMLine retrieveOrderBOMLine(I_PP_Order ppOrder, I_M_Product product);

	void save(I_PP_Order_BOM orderBOM);

	void save(I_PP_Order_BOMLine orderBOMLine);

}
