package org.eevolution.api;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.I_PP_Order_NodeNext;
import org.eevolution.model.I_PP_Order_Node_Asset;
import org.eevolution.model.I_PP_Order_Node_Product;
import org.eevolution.model.I_PP_Order_Workflow;

import de.metas.util.ISingletonService;

public interface IPPOrderWorkflowDAO extends ISingletonService
{

	List<I_PP_Order_Node> retrieveNodes(I_PP_Order order);

	List<I_PP_Order_Node> retrieveNodes(I_PP_Order_Workflow orderWorkflow);

	List<I_PP_Order_NodeNext> retrieveNodeNexts(I_PP_Order_Node node);

	void deleteOrderWorkflow(I_PP_Order ppOrder);

	/**
	 * Retrieves order's workflow (must always be only one).
	 * 
	 * @param order
	 * @return
	 */
	I_PP_Order_Workflow retrieveOrderWorkflow(I_PP_Order order);

	List<I_PP_Order_Node> retrieveOrderNodes(I_PP_Order order);

	List<I_PP_Order_Node_Asset> retrieveOrderNodeAssets(I_PP_Order order);

	List<I_PP_Order_NodeNext> retrieveOrderNodeNexts(I_PP_Order order);

	List<I_PP_Order_Node_Product> retrieveOrderNodeProducts(I_PP_Order order);

	/**
	 * retrieve resource from the given PP Order's first Node.
	 * 
	 * @param order
	 * @return
	 */
	I_S_Resource retrieveResourceForFirstNode(I_PP_Order order);

}
