package de.metas.handlingunits.document;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * Implementations of this interface are used to manage HU allocations to a particular document line.
 * <p>
 * NOTE: all these methods are using {@link ITrx#TRXNAME_ThreadInherited} transaction.
 *
 * @author tsa
 */
public interface IHUAllocations
{
	/**
	 * Create LU/TU {@link I_M_HU_Assignment} and allocate given HUs. Assignment is fairly generic, but creating allocations depends on this interface's implementor.
	 *
	 * @param qtyToAllocate          quantity to allocate. Contains both the quantity in the respective stock-UOM and (optionally, if the HU has a weight) the actual catch-weight.
	 * @param deleteOldTUAllocations if true, delete ALL old allocations between the TU and the document (be careful with this, as it might delete allocations which are still desired)
	 */
	void allocate(@Nullable final I_M_HU luHU,
			@Nullable final I_M_HU tuHU,
			@Nullable final I_M_HU vhu,
			@NonNull final StockQtyAndUOMQty qtyToAllocate,
			final boolean deleteOldTUAllocations);

	/**
	 * Remove all assignments and allocations (directly, from database)
	 */
	void clearAssignmentsAndAllocations();

	/**
	 * Gets assigned HUs.
	 * <p>
	 * WARNING: this method will NOT exclude those HUs which are destroyed. We will keep this logic because there could be API which are depending to this feature.
	 *
	 * @return assigned HUs
	 */
	List<I_M_HU> getAssignedHUs();

	/**
	 * Assign given HUs.
	 *
	 * @param husToAssign
	 */
	void addAssignedHUs(Collection<I_M_HU> husToAssign);

	/**
	 * Unassign given HUs and also delete their allocations.
	 *
	 * @param husToUnassign
	 */
	void removeAssignedHUs(Collection<I_M_HU> husToUnassign);

	/**
	 * Unassign given HU and then mark it as destroyed.
	 *
	 * @param huToDestroy
	 */
	void destroyAssignedHU(I_M_HU huToDestroy);
}
