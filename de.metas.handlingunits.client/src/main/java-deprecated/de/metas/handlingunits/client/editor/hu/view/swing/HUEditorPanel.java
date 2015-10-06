package de.metas.handlingunits.client.editor.hu.view.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.compiere.util.Env;
import org.jdesktop.swingx.JXMultiSplitPane;

import de.metas.handlingunits.attribute.api.IHUAttributeSet;
import de.metas.handlingunits.attribute.api.impl.NullHUAttributeSet;
import de.metas.handlingunits.client.editor.allocation.view.swing.HUDocumentsPanel;
import de.metas.handlingunits.client.editor.attr.model.HUAttributeSetModel;
import de.metas.handlingunits.client.editor.attr.view.swing.HUAttributeSetEditor;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.HUEditorQtysModel;
import de.metas.handlingunits.client.editor.hu.view.swing.mouse.listener.HUTreeTableMouseListener;
import de.metas.handlingunits.client.editor.hu.view.swing.splitpane.ThreeWayHorizontalSplitPaneModel;
import de.metas.handlingunits.client.editor.hu.view.swing.treemodel.listener.NodeOperationTreeModelListener;
import de.metas.handlingunits.document.IDataSource;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;

public class HUEditorPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5320892041695799291L;

	private final HUEditorModel editorModel;
	private final HUTreeTable<IHUTreeNode> editorTreeTable = new HUTreeTable<IHUTreeNode>();

	private final HUDocumentsPanel allocationPanel = new HUDocumentsPanel();
	private final HUAttributeSetEditor attributeSetEditor = new HUAttributeSetEditor();
	private final HUEditorQtysModel editorQtyModel;

	private final TableModelListener refreshEditorModelSelection = new TableModelListener()
	{
		@Override
		public void tableChanged(final TableModelEvent e)
		{
			editorModel.refreshCurrentSelection();

			// NOTE: in case something is changed in HUAttributeSetModel and the value is propagated, the up or down stream is not aware of this
			// all values are there but we just need to repaint the table
			// NOTE2: calling just invalidate() does not help
			editorTreeTable.repaint();
		}

	};

	public HUEditorPanel()
	{
		super();

		editorModel = new HUEditorModel(Env.getCtx(), allocationPanel.getModel());
		editorQtyModel = new HUEditorQtysModel(editorModel);

		initComponents();
		initLayout();
		initRootExpansion();
	}

	private void initComponents()
	{
		editorTreeTable.setTreeTableModel(editorModel);
		editorTreeTable.setAutoCreateColumnsFromModel(true);
		editorTreeTable.setAutoscrolls(true);
		editorTreeTable.setEditable(true);

		// treeTable.setComponentPopupMenu(popupMenu);
		editorTreeTable.addMouseListener(new HUTreeTableMouseListener(editorTreeTable));
		editorTreeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// see how to fix flicker - JTable L: 5792
		// this.treeTable.putClientProperty("terminateEditOnFocusLost", Boolean.FALSE);

		// expand elements on insertion, refresh selection on change
		editorModel.addTreeModelListener(new NodeOperationTreeModelListener(editorModel, editorTreeTable));

		editorTreeTable.getTreeSelectionModel().addTreeSelectionListener(new TreeSelectionListener()
		{
			@Override
			public void valueChanged(final TreeSelectionEvent e)
			{
				final IHUTreeNode node = editorModel.getSelectedNode();

				final HUAttributeSetModel attributeSetModel;
				if (node == null)
				{
					// no node was selected
					final IHUAttributeSet attributeSet = NullHUAttributeSet.instance;
					attributeSetModel = new HUAttributeSetModel(attributeSet);
					attributeSetModel.setReadonly(true);
				}
				else if (node instanceof HUTreeNode)
				{
					final HUTreeNode huTreeNode = (HUTreeNode)node;
					attributeSetModel = huTreeNode.getHUAttributeSetModel();
				}
				else
				{
					// use null attribute set instance
					final IHUAttributeSet attributeSet = NullHUAttributeSet.instance;
					attributeSetModel = new HUAttributeSetModel(attributeSet);
					attributeSetModel.setReadonly(true);
				}

				//
				// Make sure Action Executor is configured
				attributeSetModel.setActionExecutor(editorModel.getHistory());

				//
				// refresh selection on change
				attributeSetModel.removeTableModelListener(refreshEditorModelSelection);
				attributeSetModel.addTableModelListener(refreshEditorModelSelection);

				attributeSetEditor.setModel(attributeSetModel);
			}
		});
	}

	private void initLayout()
	{
		setLayout(new BorderLayout());
		// setPreferredSize(new Dimension(1200, 900));

		// now create the split pane which allows users to resize
		final JXMultiSplitPane jxMultiSplitPane = new JXMultiSplitPane();
		jxMultiSplitPane.setModel(new ThreeWayHorizontalSplitPaneModel(true));

		// Allocation Panel
		jxMultiSplitPane.add(allocationPanel, ThreeWayHorizontalSplitPaneModel.LEFT);

		// HU editor
		// NOTE: if we are not adding the treeTable to a scroll pane, the header columns won't be visible
		final JScrollPane treeTableScroll = new JScrollPane(editorTreeTable);
		jxMultiSplitPane.add(treeTableScroll, ThreeWayHorizontalSplitPaneModel.CENTER);

		//
		// RIGHT
		{
			final JPanel rightPanel = new JPanel();
			rightPanel.setLayout(new BorderLayout());
			jxMultiSplitPane.add(rightPanel, ThreeWayHorizontalSplitPaneModel.RIGHT);

			//
			// Attribute Set Editor
			attributeSetEditor.setMinimumSize(new Dimension(100, 300));
			rightPanel.add(attributeSetEditor, BorderLayout.CENTER);

			//
			// Qtys Summary table
			final HUEditorQtysPanel editorQtysPanel = new HUEditorQtysPanel(editorQtyModel);
			editorQtysPanel.setMinimumSize(new Dimension(100, 300));
			rightPanel.add(editorQtysPanel, BorderLayout.SOUTH);
		}

		this.add(jxMultiSplitPane, BorderLayout.CENTER);
	}

	private void initRootExpansion()
	{
		editorTreeTable.setRootVisible(true); // set the root to be visible in order to add actions to it
		editorTreeTable.expandAll();
	}

	public HUTreeTable<IHUTreeNode> getTreeTable()
	{
		return editorTreeTable;
	}

	public HUEditorModel getModel()
	{
		return editorModel;
	}

	public HUDocumentsPanel getAllocationPanel()
	{
		return allocationPanel;
	}

	public HUAttributeSetEditor getAttributeSetEditor()
	{
		return attributeSetEditor;
	}

	public void setDataDource(final IDataSource dataSource)
	{
		//
		// Init QtyAllocationModel
		allocationPanel.setDataSource(dataSource);

		//
		// Init HU Editor model
		getModel().setDataSource(dataSource);
		getTreeTable().expandAll();
	}

	private void stopEditing()
	{
		if (editorTreeTable.getCellEditor() != null)
		{
			editorTreeTable.getCellEditor().stopCellEditing();
		}

		attributeSetEditor.stopEditing();
	}

	public void save()
	{
		stopEditing();
		editorModel.save();
	}

	public void processDataSource()
	{
		save();

		allocationPanel.getModel().processDataSource();
	}
}
