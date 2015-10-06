package de.metas.handlingunits.client.editor.allocation.model;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;

import de.metas.handlingunits.client.editor.allocation.view.column.impl.MProductColumn;
import de.metas.handlingunits.client.editor.allocation.view.column.impl.QtyAllocatedColumn;
import de.metas.handlingunits.client.editor.allocation.view.column.impl.QtyColumn;
import de.metas.handlingunits.client.editor.allocation.view.column.impl.QtyRemainingColumn;
import de.metas.handlingunits.client.editor.model.AbstractHUTreeTableModel;
import de.metas.handlingunits.client.editor.view.column.ITreeTableColumn;
import de.metas.handlingunits.document.IDataSource;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.tree.factory.IHUDocumentTreeNodeFactory;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentLineTreeNode;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentTreeNode;
import de.metas.handlingunits.tree.node.allocation.impl.RootHUDocumentTreeNode;

public class HUDocumentsModel extends AbstractHUTreeTableModel<IHUDocumentTreeNode>
{
	public static final String COLUMNNAME_M_Product = "Product";
	private final ITreeTableColumn<IHUDocumentTreeNode> productColumn = new MProductColumn(HUDocumentsModel.COLUMNNAME_M_Product);

	public static final String COLUMNNAME_Qty = "Qty";
	private final ITreeTableColumn<IHUDocumentTreeNode> qtyColumn = new QtyColumn(HUDocumentsModel.COLUMNNAME_Qty);

	public static final String COLUMNNAME_QtyAllocated = "QtyAllocated";
	private final ITreeTableColumn<IHUDocumentTreeNode> qtyAllocatedColumn = new QtyAllocatedColumn(HUDocumentsModel.COLUMNNAME_QtyAllocated);

	public static final String COLUMNNAME_QtyRemaining = "QtyRemaining";
	private final ITreeTableColumn<IHUDocumentTreeNode> qtyRemainingColumn = new QtyRemainingColumn(HUDocumentsModel.COLUMNNAME_QtyRemaining);

	private IDataSource dataSource;

	public HUDocumentsModel()
	{
		super();

		addColumnAdapter(productColumn);
		addColumnAdapter(qtyColumn);
		addColumnAdapter(qtyAllocatedColumn);
		addColumnAdapter(qtyRemainingColumn);

		setRoot(new RootHUDocumentTreeNode());
	}

	public void setDataSource(final IDataSource dataSource)
	{
		Check.assumeNotNull(dataSource, "dataSource not null");
		this.dataSource = dataSource;

		clear();

		final List<IHUDocument> documents = new ArrayList<IHUDocument>(dataSource.getHUDocuments());
		final IHUDocumentTreeNode root = getRoot();
		for (final IHUDocument source : documents)
		{
			final IHUDocumentTreeNode documentTreeNode = Services.get(IHUDocumentTreeNodeFactory.class).createNode(source);
			addNode(documentTreeNode, root);
		}
	}

	public List<IHUDocumentTreeNode> getAllNodes()
	{
		return navigatePostOrder(getRoot(), new Predicate<IHUDocumentTreeNode>()
		{

			@Override
			public boolean evaluate(IHUDocumentTreeNode value)
			{
				return value != null;
			}
		});
	}

	public List<HUDocumentLineTreeNode> getHUDocumentLineTreeNodes()
	{
		final List<HUDocumentLineTreeNode> result = new ArrayList<HUDocumentLineTreeNode>();
		navigatePostOrder(getRoot(), new Predicate<IHUDocumentTreeNode>()
		{

			@Override
			public boolean evaluate(final IHUDocumentTreeNode value)
			{
				if (value instanceof HUDocumentLineTreeNode)
				{
					result.add((HUDocumentLineTreeNode)value);
				}
				return false;
			}
		});

		return result;
	}

	public List<IHUDocument> getHUDocuments()
	{
		final List<IHUDocument> documents = new ArrayList<IHUDocument>();
		navigatePostOrder(getRoot(), new Predicate<IHUDocumentTreeNode>()
		{

			@Override
			public boolean evaluate(final IHUDocumentTreeNode value)
			{
				if (value instanceof HUDocumentTreeNode)
				{
					final HUDocumentTreeNode documentNode = (HUDocumentTreeNode)value;
					final IHUDocument document = documentNode.getHUDocument();
					documents.add(document);
				}
				return false;
			}
		});

		return documents;
	}

	public boolean isEmpty()
	{
		return getRoot().getChildren().isEmpty();
	}

	public void clear()
	{
		removeChildren(getRoot());
	}

	public void processDataSource()
	{
		this.dataSource.process();
	}
}
