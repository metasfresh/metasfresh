package de.metas.handlingunits.hutransaction;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

/**
 * Declare business logic to create different {@link IHUTransactionCandidate}s for different purposes.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUTransactionBL extends ISingletonService
{
	/**
	 * Current use: creates a IHUTransaction for a LU, which is created from a receipt schedule.<br>
	 * Just so that HULoader.load0() will later on transfer the source's attributes also to the LU and not just to the TUs (task 06748).
	 *
	 * @param luHU
	 * @param luItemPI
	 * @param request
	 * @return
	 */
	IHUTransactionCandidate createLUTransactionForAttributeTransfer(I_M_HU luHU, I_M_HU_PI_Item luItemPI, IAllocationRequest request);

	/**
	 * This method is used to find out for a given document, an HU was changed another time after having been affected by a particular document.
	 * In the current use-case, we want to know if we can revert an inventory-document. The can only do that if the corresponding HUs were not changed further.
	 *
	 * @return {@code true} if for the given huId there is no HU-transaction after the one which references the given referencedRecord.
	 */
	boolean isLatestHUTrx(@NonNull final HuId huId, @NonNull final TableRecordReference tableRecordReference);

}
