package de.metas.inoutcandidate.api;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;

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
	Stream<Packageable> stream(PackageableQuery query);

	/**
	 * @return The current QtyPickedPlanned (qty that was picked but not yet processed) for the given schedule if found
	 */
	Optional<Quantity> retrieveQtyPickedPlanned(ShipmentScheduleId shipmentScheduleId);

	Packageable getByShipmentScheduleId(ShipmentScheduleId shipmentScheduleId);

	List<Packageable> getByShipmentScheduleIds(Collection<ShipmentScheduleId> shipmentScheduleIds);
}
