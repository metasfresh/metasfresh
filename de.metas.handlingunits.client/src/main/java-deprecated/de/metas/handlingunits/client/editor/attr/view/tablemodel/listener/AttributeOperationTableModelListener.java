package de.metas.handlingunits.client.editor.attr.view.tablemodel.listener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.adempiere.util.Check;

import de.metas.handlingunits.client.editor.model.AbstractHUTreeTableModel;

public class AttributeOperationTableModelListener implements TableModelListener
{
	private final AbstractHUTreeTableModel<?> treeTableModel;

	public AttributeOperationTableModelListener(final AbstractHUTreeTableModel<?> treeTableModel)
	{
		super();

		Check.assumeNotNull(treeTableModel, "treeTableModel not null");
		this.treeTableModel = treeTableModel;
	}

	@Override
	public void tableChanged(final TableModelEvent e)
	{
		treeTableModel.refreshCurrentSelection();
	}
}
