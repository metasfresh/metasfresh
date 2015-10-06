package de.metas.handlingunits.tree.node.hu;

import java.math.BigDecimal;
import java.util.List;

import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.tree.node.ITreeNode;

/**
 * Contains hierarchy and product logic
 * 
 * @author tsa
 * @author al
 */
public interface IHUTreeNode extends ITreeNode<IHUTreeNode>
{
	@Override
	String getDisplayName();

	void setDisplayName(String displayName);

	IHUTreeNodeProduct getProduct();

	void setProduct(IHUTreeNodeProduct product);

	List<IHUTreeNodeProduct> getAvailableProducts();

	BigDecimal getQty();

	boolean isReadonly();

	void setReadonly(boolean readonly);

	IHUTreeNodeCMActionProvider getHUTreeNodeCMActionProvider();

	public void addHUTreeNodeCMActionProvider(final IHUTreeNodeCMActionProvider provider);

	boolean isSelected();

	void setSelected(boolean selected);
}
