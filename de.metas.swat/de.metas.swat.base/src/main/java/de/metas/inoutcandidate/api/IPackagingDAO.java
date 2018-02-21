package de.metas.inoutcandidate.api;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.I_M_PackagingContainer;
import org.adempiere.util.ISingletonService;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * Packaging related DAO
 * 
 * @author ts
 * @author tsa
 * 
 * @see <a href="http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29">(2009_0022_G61)</a>
 */
public interface IPackagingDAO extends ISingletonService
{
	@Deprecated
	default List<I_M_PackagingContainer> retrieveContainers(int wareHouseId, String trxName)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates an empty {@link IPackageableQuery}.
	 * 
	 * @return
	 */
	IPackageableQuery createPackageableQuery();

	/**
	 * Retrieve all {@link IPackageable} items for given <code>query</code>
	 * 
	 * @param ctx
	 * @param query
	 * @return
	 */
	List<IPackageable> retrievePackableLines(IPackageableQuery query);

	/**
	 * The QtyPickedPlanned is the qty that was picked, but not yet processed.
	 * 
	 * @param sched
	 * @return The current PtyPickedPlanned for the given schedule if found, null otherwise
	 */
	BigDecimal retrieveQtyPickedPlannedOrNull(I_M_ShipmentSchedule sched);
}
