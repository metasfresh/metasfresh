package de.metas.handlingunits.attribute;

import java.util.Collection;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_ProductAttribute;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.ISingletonService;

public interface IPPOrderProductAttributeBL extends ISingletonService
{

	/**
	 * Transfer PP_Order's attributes (see {@link I_PP_Order_ProductAttribute}) to
	 * <ul>
	 * <li>given HUs
	 * <li>all already received HUs
	 * </ul>
	 * 
	 * @param ppOrder
	 * @param hus
	 */
	void updateHUAttributes(Collection<I_M_HU> hus, final PPOrderId fromPPOrderId);

	/**
	 * Create new PP_Order_ProductAttribute entries for the given cost collector
	 */
	void addPPOrderProductAttributes(I_PP_Cost_Collector costCollector);

	/**
	 * Create new PP_Order_ProductAttribute entries for the given Issue Candidate
	 */
	void addPPOrderProductAttributesFromIssueCandidate(I_PP_Order_Qty issueCandidate);
}
