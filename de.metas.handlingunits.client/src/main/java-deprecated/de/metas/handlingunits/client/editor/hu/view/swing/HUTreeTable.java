package de.metas.handlingunits.client.editor.hu.view.swing;

import java.awt.Component;

import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreePath;

import org.adempiere.util.Check;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.TreeTableModel;

import de.metas.handlingunits.client.editor.model.AbstractHUTreeTableModel;
import de.metas.handlingunits.tree.node.ITreeNode;

/**
 * Extension of {@link JXTreeTable} which handles following topics:
 * <ul>
 * <li>enforce using {@link AbstractHUTreeTableModel} as {@link TreeTableModel}
 * <li>configure correctly from model
 * <li>able to handle visible cells from model
 * </ul>
 * 
 * @author tsa
 * 
 * @param <T> node type
 */
public class HUTreeTable<T extends ITreeNode<T>> extends JXTreeTable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4877531114061327924L;

	// private static final transient TableCellRenderer TABLECELLRENDERER_NULL = new TableCellRenderer()
	// {
	// private final Component comp = new JLabel(" ");
	//
	// @Override
	// public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	// {
	// comp.setVisible(false);
	// return comp;
	// }
	// };

	private AbstractHUTreeTableModel<T> huTreeModel;

	public HUTreeTable()
	{
		super();
	}

	@Override
	public void setTreeTableModel(final TreeTableModel treeModel)
	{
		Check.assumeInstanceOfOrNull(treeModel, AbstractHUTreeTableModel.class, "treeModel");
		@SuppressWarnings("unchecked")
		final AbstractHUTreeTableModel<T> huTreeModelNew = (AbstractHUTreeTableModel<T>)treeModel;

		//
		// Unbind old model
		final AbstractHUTreeTableModel<T> huTreeModelOld = this.huTreeModel;
		if (huTreeModelOld != null)
		{
			huTreeModelOld.setSelectionModel(null);
		}

		//
		// Bind & configure new model
		if (huTreeModelNew != null)
		{
			huTreeModelNew.setSelectionModel(this.getTreeSelectionModel());

			final TreeNodeColumnFactory<T> columnFactory = new TreeNodeColumnFactory<T>(this, huTreeModelNew.getColumnAdapters());
			this.setColumnFactory(columnFactory);
			this.setAutoCreateColumnsFromModel(true);
		}
		this.huTreeModel = huTreeModelNew;
		super.setTreeTableModel(huTreeModelNew);
	}

	@Override
	public TreeTableModelAdapter getModel()
	{
		return (TreeTableModelAdapter)super.getModel();
	}

	public ITreeNode<T> nodeForRow(final int row)
	{
		// copy-paste from org.jdesktop.swingx.JXTreeTable.TreeTableModelAdapter.nodeForRow(int)

		// Issue #270-swingx: guard against invisible row
		final TreePath path = getPathForRow(row);
		final Object nodeObj = path != null ? path.getLastPathComponent() : null;
		if (nodeObj == null)
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final ITreeNode<T> node = (ITreeNode<T>)nodeObj;
		return node;
	}

	public boolean isCellVisible(final int row, final int column)
	{
		final ITreeNode<T> node = nodeForRow(row);
		if (node == null)
		{
			return false;
		}

		if (huTreeModel != null && !huTreeModel.isCellVisible(node, column))
		{
			return false;
		}

		return true;
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column)
	{
		// if (!isCellVisible(row, column))
		// {
		// return TABLECELLRENDERER_NULL;
		// }

		return super.getCellRenderer(row, column);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
		final Component comp = super.prepareRenderer(renderer, row, column);
		if (!isCellVisible(row, column))
		{
			// FIXME: this is not working
			comp.setVisible(false);
		}
		return comp;
	}

}
