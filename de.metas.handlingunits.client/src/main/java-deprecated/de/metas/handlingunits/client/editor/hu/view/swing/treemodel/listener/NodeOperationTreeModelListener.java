package de.metas.handlingunits.client.editor.hu.view.swing.treemodel.listener;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeSelectionModel;

import org.adempiere.util.Check;
import org.jdesktop.swingx.JXTreeTable;

import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;

public class NodeOperationTreeModelListener implements TreeModelListener
{
	private final HUEditorModel huEditorModel;
	private final JXTreeTable treeTable;

	public NodeOperationTreeModelListener(final HUEditorModel huEditorModel, final JXTreeTable treeTable)
	{
		super();

		Check.assumeNotNull(huEditorModel, "huEditorModel not null");
		this.huEditorModel = huEditorModel;

		this.treeTable = treeTable;
	}

	@Override
	public void treeNodesChanged(final TreeModelEvent e)
	{
		huEditorModel.refreshCurrentSelection();
	}

	@Override
	public void treeNodesInserted(final TreeModelEvent e)
	{
		// just refresh, nothing else
		// expanding / selecting node here will happen before the call to HUEditorModel.insertNodeInto.fireChildAdded(...) and it will screw things up.
		huEditorModel.refreshCurrentSelection();
	}

	@Override
	public void treeNodesRemoved(final TreeModelEvent e)
	{
		huEditorModel.refreshCurrentSelection();
	}

	@Override
	public void treeStructureChanged(final TreeModelEvent e)
	{
		// select changed path
		final TreeSelectionModel selectionModel = huEditorModel.getSelectionModel();
		selectionModel.clearSelection();
		selectionModel.setSelectionPath(e.getTreePath());

		// expand changed path
		// FIXME: get rid of treeTable calls, only model would be enough...
		treeTable.expandPath(e.getTreePath());
	}
}
