package de.metas.handlingunits.tree.node.allocation;

import java.math.BigDecimal;

import org.compiere.model.I_M_Product;

import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.tree.node.ITreeNode;

public interface IHUDocumentTreeNode extends ITreeNode<IHUDocumentTreeNode>
{
	public static interface IQtyRequest
	{
		BigDecimal getQty();
	}

	/**
	 * @return {@link I_M_Product}
	 */
	I_M_Product getProduct();

	/**
	 * @return {@link BigDecimal} full quantity
	 */
	BigDecimal getQty();

	/**
	 * @return {@link BigDecimal} allocated quantity
	 */
	BigDecimal getQtyAllocated();

	/**
	 * Request Qty to allocate
	 * 
	 * @param qty
	 */
	IQtyRequest requestQty(final BigDecimal qty);

	void requestQty(IQtyRequest qtyRequest);

	void releaseQty(IQtyRequest qtyRequest);

	/**
	 * @return {@link BigDecimal} remaining quantity
	 */
	BigDecimal getQtyRemaining();

	/**
	 * 
	 * @param qty
	 * @return actual unlocked qty; never return null
	 */
	IQtyRequest unlockQty(BigDecimal qty);

	IHUTreeNodeCMActionProvider getHUTreeNodeCMActionProvider();
}
