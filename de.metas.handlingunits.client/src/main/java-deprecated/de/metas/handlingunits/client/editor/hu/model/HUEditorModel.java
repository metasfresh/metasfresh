package de.metas.handlingunits.client.editor.hu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.tree.TreePath;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.FilterUtils;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.api.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.api.IHUAttributesDAO;
import de.metas.handlingunits.client.editor.allocation.model.HUDocumentsModel;
import de.metas.handlingunits.client.editor.command.model.history.IHistory;
import de.metas.handlingunits.client.editor.command.model.history.impl.History;
import de.metas.handlingunits.client.editor.command.view.history.IHistorySupport;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.model.context.menu.impl.HUEditorModelCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.column.impl.HUAttributeColumn;
import de.metas.handlingunits.client.editor.hu.view.column.impl.MHUDocumentLineColumn;
import de.metas.handlingunits.client.editor.hu.view.column.impl.MHUItemColumn;
import de.metas.handlingunits.client.editor.hu.view.column.impl.MProductColumn;
import de.metas.handlingunits.client.editor.hu.view.column.impl.QtyColumn;
import de.metas.handlingunits.client.editor.hu.view.column.impl.SelectionColumn;
import de.metas.handlingunits.client.editor.model.AbstractHUTreeTableModel;
import de.metas.handlingunits.client.editor.view.column.ITreeTableColumn;
import de.metas.handlingunits.document.IDataSource;
import de.metas.handlingunits.document.IHUReference;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.tree.factory.IHUTreeNodeFactory;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.RootHUTreeNode;

public class HUEditorModel extends AbstractHUTreeTableModel<IHUTreeNode>
{
	private final Properties ctx;
	private final IContextAware globalContextProvider;
	private final IHUTreeNodeFactory treeNodeFactory;

	private final History history = new History();

	public static final String COLUMNNAME_M_HU_Item = "M_HU_Item";
	private final ITreeTableColumn<IHUTreeNode> displayNameColumn = new MHUItemColumn(HUEditorModel.COLUMNNAME_M_HU_Item);

	public static final String COLUMNNAME_Selected = " ";
	private final ITreeTableColumn<IHUTreeNode> selectColumn = new SelectionColumn(COLUMNNAME_Selected, this);

	public static final String COLUMNNAME_M_HU_DocumentLine = "M_HU_AllocationSourceLine";
	private final ITreeTableColumn<IHUTreeNode> documentLineColumn = new MHUDocumentLineColumn(HUEditorModel.COLUMNNAME_M_HU_DocumentLine, this);

	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
	private final ITreeTableColumn<IHUTreeNode> productColumn = new MProductColumn(HUEditorModel.COLUMNNAME_M_Product_ID, this);

	public static final String COLUMNNAME_Qty = "Qty";
	private final ITreeTableColumn<IHUTreeNode> qtyColumn = new QtyColumn(HUEditorModel.COLUMNNAME_Qty, this);

	/**
	 * Handling units allocation model.
	 */
	private final HUDocumentsModel documentsModel;

	public static final String PROPERTY_DataSource = "DataSource";
	private IDataSource dataSource;

	public HUEditorModel(final Properties ctx, final HUDocumentsModel qtyAllocationModel)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;

		globalContextProvider = new PlainContextAware(ctx, ITrx.TRXNAME_ThreadInherited);
		this.treeNodeFactory = Services.get(IHUTreeNodeFactory.class);

		addColumnAdapter(selectColumn);
		addColumnAdapter(displayNameColumn);
		addColumnAdapter(documentLineColumn);
		addColumnAdapter(productColumn);
		addColumnAdapter(qtyColumn);
		setHierarchicalColumn(displayNameColumn);

		for (final I_M_Attribute attribute : Services.get(IHUAttributesDAO.class).retrieveDisplayedAttributes(ctx))
		{
			final HUAttributeColumn attributeColumn = new HUAttributeColumn(attribute, this);
			addColumnAdapter(attributeColumn);
		}

		this.documentsModel = qtyAllocationModel;

		final IHUTreeNode root = new RootHUTreeNode();
		setRoot(root);
	}

	public Properties getCtx()
	{
		return ctx;
	}

	public IHistory getHistory()
	{
		return history;
	}

	public IHistorySupport getHistorySupport()
	{
		return history.getHistorySupport();
	}

	public IHUTreeNode getSelectedNode()
	{
		Check.assumeNotNull(getSelectionModel(), "selectionModel not null");

		final TreePath path = getSelectionModel().getSelectionPath();
		if (path == null)
		{
			return null;
		}

		final Object lastPathComponent = path.getLastPathComponent();
		// cover any misplaced case
		if (lastPathComponent instanceof TreePath)
		{
			return getRoot();
		}
		final IHUTreeNode node = (IHUTreeNode)lastPathComponent;
		return node;
	}

	public IContextAware getGlobalContextProvider()
	{
		return globalContextProvider;
	}

	public HUDocumentsModel getHUDocumentsModel()
	{
		return documentsModel;
	}

	public void save()
	{
		final HUEditorModelSaver synchronizer = new HUEditorModelSaver(this);
		synchronizer.save();

		refreshCurrentSelection();
	}

	public IDataSource getDataSource()
	{
		return this.dataSource;
	}

	public void setDataSource(final IDataSource dataSource)
	{
		final IDataSource dataSourceOld = this.dataSource;
		if (dataSourceOld == dataSource)
		{
			// nothing changed
			return;
		}

		this.dataSource = dataSource;
		pcs.firePropertyChange(PROPERTY_DataSource, dataSourceOld, this.dataSource);

		//
		// Subsequent actions:
		{
			loadHandlingUnits(dataSource.getHandlingUnits());

			//
			// Load nodes source line allocations and qtys
			final HUEditorModelLoader loader = new HUEditorModelLoader(this);
			loader.load();
		}
	}

	private void loadHandlingUnits(final List<IHUReference> huReferences)
	{
		final IHUTreeNode root = getRoot();

		//
		// Build HU list (no duplicates) and handle Readonly flag
		final List<I_M_HU> hus = new ArrayList<I_M_HU>();
		final Map<Integer, Boolean> huId2readonly = new HashMap<Integer, Boolean>();
		for (final IHUReference huRef : huReferences)
		{
			final I_M_HU hu = huRef.getM_HU();
			final int huId = hu.getM_HU_ID();
			final boolean readonly = huRef.isReadonly();

			final Boolean readonlyPrev = huId2readonly.get(huId);
			if (readonlyPrev == null)
			{
				hus.add(hu);
				huId2readonly.put(huId, readonly);
			}
			else
			{
				// If any HU reference asks form Readonly we mark the HU as readonly
				if (readonly)
				{
					huId2readonly.put(huId, true);
				}
			}

		}

		for (final I_M_HU hu : hus)
		{
			// FIXME al: this should be done elsewhere
			// add parent of handling units once, and only once!
			final I_M_HU finalTopLevelHU;
			{
				// we need to use it in a nested class, so we use this trick to keep it final, but also destroy the auxiliary once done
				I_M_HU topLevelHU = hu;
				while (true)
				{
					final I_M_HU parentAux = Services.get(IHandlingUnitsDAO.class).retrieveParent(topLevelHU);
					if (parentAux == null)
					{
						break;
					}
					topLevelHU = parentAux;
				}
				finalTopLevelHU = topLevelHU;
			}

			final Predicate<IHUTreeNode> hasDuplicates = new Predicate<IHUTreeNode>()
			{
				@Override
				public boolean evaluate(final IHUTreeNode node)
				{
					Check.assume(node instanceof HUTreeNode, "{} instanceof HUTreeNode", node);
					final I_M_HU hu = ((HUTreeNode)node).getM_HU();
					return finalTopLevelHU.getM_HU_ID() == hu.getM_HU_ID();
				}
			};
			if (!FilterUtils.filter(root.getChildren(), hasDuplicates).isEmpty())
			{
				continue;
			}

			final boolean readonly = huId2readonly.get(hu.getM_HU_ID());
			final IHUTreeNode node = getHUTreeNodeFactory().createNodeRecursively(finalTopLevelHU, readonly);
			root.addChild(node);
		}
	}

	public IHUTreeNodeCMActionProvider getHUTreeNodeCMActionProvider()
	{
		return HUEditorModelCMActionProvider.instance;
	}

	public IHUTreeNodeFactory getHUTreeNodeFactory()
	{
		return treeNodeFactory;
	}

	@Override
	protected void treeNodeInserted(final IHUTreeNode newChild, final IHUTreeNode parent, final int index)
	{
		final IHUTreeNodeFactory factory = getHUTreeNodeFactory();
		factory.updateFromParent(newChild);
	}

}
