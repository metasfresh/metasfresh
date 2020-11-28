package de.metas.inoutcandidate.api;

import org.compiere.model.I_M_InOut;

import de.metas.inoutcandidate.spi.impl.ReceiptQty;
import de.metas.util.ISingletonService;

/**
 * Contains business logic that goes with both shipment schedules and receipt schedules.
 */
public interface IInOutCandidateBL extends ISingletonService
{
	/**
	 * @param storeInouts if <code>true</code>, then the result will contain the actual {@link I_M_InOut}s that were created.<br>
	 *            This might cause memory problems. If false, the result will just contain the number of generated receipts.
	 */
	InOutGenerateResult createEmptyInOutGenerateResult(boolean storeInouts);

	ReceiptQty getQtyAndQuality(final org.compiere.model.I_M_InOutLine inoutLine);

}
