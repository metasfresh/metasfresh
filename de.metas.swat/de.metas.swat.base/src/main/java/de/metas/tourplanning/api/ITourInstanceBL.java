package de.metas.tourplanning.api;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;

import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour_Instance;

public interface ITourInstanceBL extends ISingletonService
{
	boolean isGenericTourInstance(I_M_Tour_Instance tourInstance);

	/**
	 * Checks {@link I_M_DeliveryDay#getM_Tour_Instance_ID()} assignment and if needed creates one.
	 * 
	 * @param deliveryDay
	 */
	void updateTourInstanceAssigment(I_M_DeliveryDay deliveryDay);

	void assignToTourInstance(I_M_DeliveryDay deliveryDay, I_M_Tour_Instance tourInstance);

	I_M_Tour_Instance createTourInstanceDraft(IContextAware context, I_M_DeliveryDay deliveryDay);

	void process(I_M_Tour_Instance tourInstance);

	void unprocess(I_M_Tour_Instance tourInstance);

}
