package de.metas.handlingunits.client.editor.allocation.model.exception;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

/**
 * Exception thrown when allocation fails
 * 
 * @author al
 */
public class IllegalQtyAllocationException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6632099785506510159L;

	private final IHUTreeNode node;
	private final BigDecimal qty;

	public IllegalQtyAllocationException(final IHUTreeNode node, final BigDecimal qty)
	{
		super("Node " + node + " quantity could not be allocated (qty=" + qty + ")");
		this.node = node;
		this.qty = qty;
	}

	public IHUTreeNode getNode()
	{
		return node;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

}
