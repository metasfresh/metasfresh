package de.metas.edi.api;

import de.metas.handlingunits.model.I_C_OLCand;
import de.metas.util.ISingletonService;

public interface IEDIOLCandBL extends ISingletonService
{
	/**
	 * Decides for the given olCand whether the HU item capacity from our own master data (<code>false</code>) shall be date, or the one that was imported into the given <code>olCand</code> (
	 * <code>true</code>).
	 *
	 * @return <code>true</code> as long as we have a capacity in our master data
	 */
	boolean isManualQtyItemCapacity(I_C_OLCand olCand);

	/**
	 * @return true if the input data source is EDI
	 */
	boolean isEDIInput(de.metas.ordercandidate.model.I_C_OLCand olCand);
}
