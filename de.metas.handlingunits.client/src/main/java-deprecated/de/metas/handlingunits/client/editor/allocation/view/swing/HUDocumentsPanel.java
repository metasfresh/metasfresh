package de.metas.handlingunits.client.editor.allocation.view.swing;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.metas.handlingunits.client.editor.allocation.model.HUDocumentsModel;
import de.metas.handlingunits.client.editor.allocation.view.swing.selectionmodel.NullSelectionModel;
import de.metas.handlingunits.client.editor.hu.view.swing.HUTreeTable;
import de.metas.handlingunits.document.IDataSource;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;

public class HUDocumentsPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4275044532483203416L;

	private final HUDocumentsModel documentsModel;
	private final HUTreeTable<IHUDocumentTreeNode> documentsTreeTable = new HUTreeTable<IHUDocumentTreeNode>();

	public HUDocumentsPanel()
	{
		super();

		documentsModel = new HUDocumentsModel();

		initComponents();
		initLayout();
		initRoot();
	}

	private void initComponents()
	{
		documentsTreeTable.setTreeTableModel(documentsModel);
		documentsTreeTable.setAutoscrolls(true);
		documentsTreeTable.setEditable(false);

		// treeTable.setComponentPopupMenu(popupMenu);
		documentsTreeTable.setSelectionModel(NullSelectionModel.instance);
	}

	private void initLayout()
	{
		final BorderLayout layout = new BorderLayout();

		setLayout(layout);

		final JScrollPane tableScroll = new JScrollPane(documentsTreeTable);

		this.add(tableScroll, BorderLayout.CENTER);
	}

	private void initRoot()
	{
		documentsTreeTable.setRootVisible(false);
	}

	public HUTreeTable<IHUDocumentTreeNode> getTreeTable()
	{
		return documentsTreeTable;
	}

	public HUDocumentsModel getModel()
	{
		return documentsModel;
	}

	public void expandAllNodes()
	{
		documentsTreeTable.expandAll();
	}

	public void setDataSource(final IDataSource dataSource)
	{
		documentsModel.setDataSource(dataSource);
		expandAllNodes();
	}
}
