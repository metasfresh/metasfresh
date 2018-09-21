package de.metas.tourplanning.api;

import de.metas.tourplanning.model.I_M_Tour_Instance;
import de.metas.util.ISingletonService;

public interface ITourInstanceDAO extends ISingletonService
{
	/**
	 * Retrieves open (not processed) and generic (a tour version without shipper transportation) tour instance.
	 * 
	 * @param params
	 * @return existing tour instance or null
	 */
	I_M_Tour_Instance retrieveTourInstance(final Object contextProvider, final ITourInstanceQueryParams params);

	boolean isTourInstanceMatches(I_M_Tour_Instance tourInstance, ITourInstanceQueryParams params);

	/**
	 * 
	 * @param tourInstance
	 * @return true if given <code>tourInstance</code> has delivery days assigned
	 */
	boolean hasDeliveryDays(I_M_Tour_Instance tourInstance);
}
