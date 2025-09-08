package de.metas.handlingunits.hutransaction;

import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.warehouse.LocatorId;

import java.time.ZonedDateTime;

/**
 * Transaction Line Candidate. Use the constructor of {@link HUTransactionCandidate} to get instances.
 * <p>
 * Based on this object the actual {@link I_M_HU_Trx_Line}s can be created.
 *
 * @author tsa
 */
public interface IHUTransactionCandidate
{
	/**
	 * @return unique transaction ID
	 */
	String getId();

	/**
	 * The document which was demanding this transaction to be produced.
	 *
	 * @return referenced document/line (model)
	 */
	Object getReferencedModel();

	void setReferencedModel(Object referencedModel);

	/**
	 * Gets {@link #getM_HU_Item()}'s handling unit
	 *
	 * @return affected HU; might be null
	 */
	I_M_HU getM_HU();

	/**
	 * @see #getM_HU()
	 */
	default int getM_HU_ID()
	{
		final I_M_HU hu = getM_HU();
		return hu == null ? -1 : hu.getM_HU_ID();
	}

	/**
	 * Gets affected HU Item.
	 * <p>
	 * If this value is null it means that no HU Item is affected by this transaction but in most of the cases its counter part transaction will affect an HU Item.
	 *
	 * <p>
	 * e.g. Transferring the Qty from a receipt schedule to a handling unit. This can be the transaction which is subtracting the Qty from receipt schedule (so no HU Item is affected) and its
	 * counterpart is the transaction which is adding the Qty to an HU Item.
	 * </p>
	 *
	 * @return affected HU Item
	 */
	I_M_HU_Item getM_HU_Item();

	/**
	 * Gets actual Virtual HU Item to be used for storing the Qty.
	 * <p>
	 * NOTE: this VHU needs to be included in {@link #getM_HU_Item()}.
	 *
	 * @return VHU item or <code>null</code>
	 */
	I_M_HU_Item getVHU_Item();

	/**
	 * Gets actual Virtual HU to be used for storing the Qty
	 *
	 * @return VHU or <code>null</code>
	 */
	I_M_HU getVHU();

	/**
	 * Gets transaction product
	 * <p>
	 * i.e. the product which was transfered.
	 *
	 * @return transaction product; never returns null
	 */
	ProductId getProductId();

	/**
	 * Gets transaction Qty/UOM. It's value is absolute and it means:
	 * <ul>
	 * <li>negative: outbound qty
	 * <li>positive: inbound qty
	 * </ul>
	 *
	 * @return absolute Qty/UOM. Never return {@code null}.
	 */
	Quantity getQuantity();

	/**
	 * Gets counterpart transaction.
	 * <p>
	 * NOTE: each transaction shall have a counterpart. i.e. when qty was subtracted from one place, there shall be another place where the Qty was added.
	 *
	 * @return counterpart transaction
	 */
	IHUTransactionCandidate getCounterpart();

	/**
	 * Cross link this transaction with given transaction by cross setting their {@link #getCounterpart()} properties.
	 * <p>
	 * NOTE: DON'T call it directly. It will be called by API
	 */
	void pair(IHUTransactionCandidate counterpartTrx);

	/**
	 * @return transaction date
	 */
	ZonedDateTime getDate();

	/**
	 * @return locator which shall be used in the HU Trx
	 */
	LocatorId getLocatorId();

	/**
	 * @return HU status used in the HU Trx
	 */
	String getHUStatus();

	/**
	 * Force trxLine to be considered processed and never be actually processed.
	 * <p>
	 * i.e. don't change HU's storage
	 */
	void setSkipProcessing();

	/**
	 * @return true if trxLine is considered to be processed, but never actually processed
	 */
	boolean isSkipProcessing();
}
