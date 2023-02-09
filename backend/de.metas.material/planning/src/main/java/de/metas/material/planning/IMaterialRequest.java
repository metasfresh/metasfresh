package de.metas.material.planning;

import de.metas.material.planning.event.SupplyRequiredHandler;
import de.metas.quantity.Quantity;

import java.time.Instant;

/**
 * Instances of this interface specify a material demand. Currently there is no "real" service from which an instance can be obtained.
 * <p>
 * See {@link SupplyRequiredHandler}.
 *
 * Note that there is a sub interface in libero which contains additional fields and it therefore more tightly coupled to libero.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IMaterialRequest
{
	/** @return context */
	IMaterialPlanningContext getMrpContext();

	/** @return how much quantity is needed to supply */
	Quantity getQtyToSupply();

	/** @return demand date; i.e. the date when quantity to supply is really needed */
	Instant getDemandDate();

	/**
	 * @return C_BPartner_ID or -1
	 */
	int getMrpDemandBPartnerId();

	/**
	 * @return sales C_OrderLine_ID or -1
	 */
	int getMrpDemandOrderLineSOId();

	/**
	 * @return sales M_ShipmentSchedule_ID or -1
	 */
	int getMrpDemandShipmentScheduleId();

	boolean isSimulated();
}
