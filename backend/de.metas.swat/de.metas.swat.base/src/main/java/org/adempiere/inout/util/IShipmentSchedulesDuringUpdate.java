package org.adempiere.inout.util;

import java.util.List;

public interface IShipmentSchedulesDuringUpdate
{

	public static enum CompleteStatus
	{
		OK,
		INCOMPLETE_LINE,
		INCOMPLETE_ORDER
	};

	/**
	 * 
	 * @return a copy of the list of {@link DeliveryGroupCandidate}s stored in this instance.
	 */
	List<DeliveryGroupCandidate> getCandidates();

	/**
	 * 
	 * @return the number of {@link DeliveryGroupCandidate}s this instance contains.
	 */
	int size();

	/**
	 * Note: no need for a 'shipperID'-parameter (as in {@link #getInOutForShipper(int, int)}) because there are not two different shipperId for the same order.
	 * 
	 * @param orderId
	 * @param pPartnerAddress
	 * @return
	 */
	public DeliveryGroupCandidate getInOutForOrderId(int orderId, int warehouseId, String bPartnerAddress);

	public void addGroup(DeliveryGroupCandidate deliveryGroupCandidate);

	/**
	 * 
	 * @param shipperId
	 * @param bPartnerAddress
	 * @return the inOut with the given parameters
	 * @throws IllegalStateException if no inOut with the given bPartnerLocationId and shipperId has been added
	 * 
	 */
	public DeliveryGroupCandidate getInOutForShipper(int shipperId, int warehouseId, String bPartnerAddress);

	public void addLine(DeliveryLineCandidate deliveryLineCandidate);

	public DeliveryLineCandidate getLineCandidateForShipmentScheduleId(int shipmentScheduleId);

	/**
	 * Adds a custom status info for the given iol. Usally the info explains, why an open order line won't be delivered this time.
	 * 
	 * @param inOutLine
	 * @param string
	 */
	void addStatusInfo(DeliveryLineCandidate inOutLine, String string);

	String getStatusInfos(DeliveryLineCandidate inOutLine);
}
