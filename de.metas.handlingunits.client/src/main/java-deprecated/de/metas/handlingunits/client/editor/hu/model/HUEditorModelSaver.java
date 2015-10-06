package de.metas.handlingunits.client.editor.hu.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IdentityHashSet;
import org.adempiere.util.collections.Predicate;
import org.adempiere.util.time.SystemTime;
import org.compiere.Adempiere;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TrxRunnable;
import org.compiere.util.Util;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.api.IHUTransaction;
import de.metas.handlingunits.api.IHUTrxBL;
import de.metas.handlingunits.api.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.api.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.tree.node.ITreeNode;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentLineTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemPMTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.RootHUTreeNode;

/**
 * Saves whole model to database
 * 
 */
public class HUEditorModelSaver
{
	private final HUEditorModel huEditorModel;
	private final List<Runnable> afterSaveActions = new ArrayList<Runnable>();

	private boolean executed = false;
	private IContextAware context;
	// NOTE: it's important to use IdentityHashSet because we want to search the models by reference
	private final IdentityHashSet<Object> modelsSaveTrace = new IdentityHashSet<Object>();

	private class ResetHUItemTreeNodeToInitialValues implements Runnable
	{
		private final HUItemMITreeNode itemNode;

		public ResetHUItemTreeNodeToInitialValues(final HUItemMITreeNode itemNode)
		{
			super();
			this.itemNode = itemNode;
		}

		@Override
		public void run()
		{
			itemNode.setHUDocumentTreeNodeInitial(itemNode.getHUDocumentLineTreeNode());
			itemNode.setQtyRequestInitial(itemNode.getQtyRequest());
			huEditorModel.fireNodeChanged(itemNode);
		}

	}

	public HUEditorModelSaver(final HUEditorModel huEditorModel)
	{
		super();

		Check.assumeNotNull(huEditorModel, "huEditorModel not null");
		this.huEditorModel = huEditorModel;
	}

	public Timestamp getDayDate()
	{
		return SystemTime.asDayTimestamp();
	}

	public IHUStorageFactory getStorageFactory()
	{
		return Services.get(IHandlingUnitsBL.class).getStorageFactory();
	}

	public void save()
	{
		Check.assume(!executed, "save shall not be executed before");
		executed = true;

		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName)
			{
				context = new PlainContextAware(huEditorModel.getCtx(), localTrxName);
				save0();
			}
		});

		executeAfterSave();

		if (Adempiere.isUnitTestMode())
		{
			POJOLookupMap.get().dumpStatus();
		}
	}

	private void save0()
	{
		Check.assumeNotNull(context, "context shall not be null");
		Check.assume(!Services.get(ITrxManager.class).isNull(context.getTrxName()), "Invalid transaction: {0}", context.getTrxName());

		//
		// Save the HU structure & Attributes
		final IHUTreeNode huRoot = huEditorModel.getRoot();
		saveCascade(huRoot);

		//
		// Save HUDocuments to Items transactions
		final List<IHUTreeNode> nodes = HUEditorModelSaver.getChildrenPostOrder(huRoot);
		for (final IHUDocumentLine documentLine : getDocumentLines())
		{
			final List<HUItemMITreeNode> documentLineNodes = getNodesForDocumentLine(documentLine, nodes);
			createHUTransactions(documentLine, documentLineNodes);
		}
	}

	private void saveCascade(final IHUTreeNode node)
	{
		saveNode(node);

		for (final IHUTreeNode child : node.getChildren())
		{
			saveCascade(child);
		}
	}

	private void saveNode(final IHUTreeNode node)
	{
		final String trxName = context.getTrxName();

		if (node == null)
		{
			// skip null nodes... just in case
		}
		else if (node instanceof RootHUTreeNode)
		{
			// nothing
		}
		else if (node instanceof HUItemMITreeNode)
		{
			final HUItemMITreeNode huItemNode = (HUItemMITreeNode)node;
			final I_M_HU_Item huItem = huItemNode.getM_HU_Item();
			if (modelsSaveTrace.contains(huItem))
			{
				// already saved
				return;
			}

			final I_M_HU hu = huItemNode.getParent().getM_HU();
			huItem.setM_HU_ID(hu.getM_HU_ID()); // setting by ID

			InterfaceWrapperHelper.save(huItem, trxName);
			modelsSaveTrace.add(huItem);
		}
		else if (node instanceof HUItemPMTreeNode)
		{
			final HUItemPMTreeNode huItemPMNode = (HUItemPMTreeNode)node;
			final I_M_HU_Item huItem = huItemPMNode.getM_HU_Item();
			if (modelsSaveTrace.contains(huItem))
			{
				// already saved
				return;
			}

			final I_M_HU hu = huItemPMNode.getParent().getM_HU();
			huItem.setM_HU_ID(hu.getM_HU_ID()); // setting by ID

			// FIXME shall we introduce M_HU_Item.M_PackingMaterial_ID?

			InterfaceWrapperHelper.save(huItem, trxName);
			modelsSaveTrace.add(huItem);
		}
		else if (node instanceof HUItemHUTreeNode)
		{
			final HUItemHUTreeNode huItemPMNode = (HUItemHUTreeNode)node;
			final I_M_HU_Item huItem = huItemPMNode.getM_HU_Item();
			if (modelsSaveTrace.contains(huItem))
			{
				// already saved
				return;
			}

			final I_M_HU hu = huItemPMNode.getParent().getM_HU();
			huItem.setM_HU_ID(hu.getM_HU_ID()); // setting by ID

			InterfaceWrapperHelper.save(huItem, trxName);
			modelsSaveTrace.add(huItem);
		}
		else if (node instanceof HUTreeNode)
		{
			final HUTreeNode huNode = (HUTreeNode)node;
			saveNode_HU(huNode);
		}
		else
		{
			throw new AdempiereException("Unsupported node type: " + node.getClass());
		}
	}

	private void saveNode_HU(final HUTreeNode huNode)
	{
		final String trxName = context.getTrxName();

		final I_M_HU hu = huNode.getM_HU();
		if (modelsSaveTrace.contains(hu))
		{
			// already saved
			return;
		}

		//
		// Old Parent HU_Item
		final I_M_HU_Item parentHUItemOld;
		if (hu.getM_HU_Item_Parent_ID() > 0)
		{
			parentHUItemOld = hu.getM_HU_Item_Parent();
			InterfaceWrapperHelper.setTrxName(parentHUItemOld, trxName);
		}
		else
		{
			parentHUItemOld = null;
		}

		//
		// Get new Parent HU_Item
		final I_M_HU_Item parentHUItemNew;
		final HUItemHUTreeNode parentNode = huNode.getParentItemHU();
		if (parentNode != null)
		{
			parentHUItemNew = parentNode.getM_HU_Item();
			InterfaceWrapperHelper.setTrxName(parentHUItemNew, trxName);
		}
		else
		{
			parentHUItemNew = null;
		}

		createParentHUItemChangedTransactions(hu, parentHUItemNew);
		
		// Make sure is saved, even if createParentHUItemChangedTransactions() does nothing
		InterfaceWrapperHelper.save(hu, trxName);
		
		modelsSaveTrace.add(hu);

		//
		// Also save the attributes
		final IAttributeStorage attributes = huNode.getAttributeStorage();
		Services.get(IHUAttributesBL.class).saveStorageAttributes(hu, attributes, trxName);
	}

	private void createParentHUItemChangedTransactions(I_M_HU hu, I_M_HU_Item parentHUItemNew)
	{
		// TODO: better move this part to HUTrxBL processing
		
		final String trxName = context.getTrxName();
		
		//
		// Fetch old Parent HU_Item
		final I_M_HU_Item parentHUItemOld;
		final int parentHUItemOldId;
		if (hu.getM_HU_Item_Parent_ID() > 0)
		{
			parentHUItemOld = hu.getM_HU_Item_Parent();
			parentHUItemOldId = parentHUItemOld.getM_HU_Item_ID();
			InterfaceWrapperHelper.setTrxName(parentHUItemOld, trxName);
		}
		else
		{
			parentHUItemOld = null;
			parentHUItemOldId = -1;
		}

		//
		// Fetch new Parent HU_Item
		final int parentHUItemNewId;
		if (parentHUItemNew != null && parentHUItemNew.getM_HU_Item_ID() > 0)
		{
			parentHUItemNewId = parentHUItemNew.getM_HU_Item_ID();
		}
		else
		{
			parentHUItemNewId = -1;
		}

		//
		// HU was not moved from one item to another => nothing to do
		if (parentHUItemOldId == parentHUItemNewId)
		{
			return;
		}

		//
		// HU was moved from old item
		if (parentHUItemOldId > 0)
		{
			final IHUItemStorage parentHUItemOldStorage = getStorageFactory().getStorage(parentHUItemOld);
			final boolean released = parentHUItemOldStorage.releaseHU(hu);
			if (!released)
			{
				throw new AdempiereException("Cannot remove " + hu + " from " + parentHUItemOldStorage);
			}
		}

		//
		// HU was moved to new item
		if (parentHUItemNewId > 0)
		{
			final IHUItemStorage parentHUItemNewStorage = getStorageFactory().getStorage(parentHUItemNew);
			final boolean added = parentHUItemNewStorage.requestNewHU();
			if (!added)
			{
				throw new AdempiereException("Cannot add " + hu + " to " + parentHUItemNewStorage);
			}
			
			hu.setM_Locator_ID(parentHUItemNew.getM_HU().getM_Locator_ID());
		}
		
		hu.setM_HU_Item_Parent(parentHUItemNew);
		InterfaceWrapperHelper.save(hu, trxName);
	}

	private void createHUTransactions(final IHUDocumentLine documentLine, final List<HUItemMITreeNode> matchingItemNodes)
	{
		assignHandlingUnits(context, documentLine, matchingItemNodes);

		// create quantity transactions
		final List<IHUTransaction> trxCandidates = createTrxCandidates(matchingItemNodes);
		Services.get(IHUTrxBL.class).createTrx(context, trxCandidates);
	}

	private void assignHandlingUnits(final IContextAware context, final IHUDocumentLine documentLine, final List<HUItemMITreeNode> matchingItemNodes)
	{
		final List<I_M_HU> handlingUnits = new ArrayList<I_M_HU>();
		for (final HUItemMITreeNode matchingItemNode : matchingItemNodes)
		{
			final I_M_HU_Item item = matchingItemNode.getM_HU_Item();
			handlingUnits.add(item.getM_HU());
		}

		documentLine.setAssignedHandlingUnits(handlingUnits, context.getTrxName());
	}

	/**
	 * @return all {@link IHUDocumentLine}s
	 */
	private List<IHUDocumentLine> getDocumentLines()
	{
		final List<IHUDocumentLine> documentLines = new ArrayList<IHUDocumentLine>();

		final ITreeNode<IHUDocumentTreeNode> documentsRoot = huEditorModel.getHUDocumentsModel().getRoot();
		final List<IHUDocumentTreeNode> nodes = documentsRoot.getChildren();
		for (final IHUDocumentTreeNode node : nodes)
		{
			final List<IHUDocumentTreeNode> lineNodes = node.getChildren();
			for (final IHUDocumentTreeNode lineNode : lineNodes)
			{
				Check.assume(lineNode instanceof HUDocumentLineTreeNode, "{0} instanceof HUDocumentLineTreeNode", lineNode);
				final IHUDocumentLine documentLine = ((HUDocumentLineTreeNode)lineNode).getHUDocumentLine();

				documentLines.add(documentLine);
			}
		}

		return documentLines;
	}

	private static List<IHUTreeNode> getChildrenPostOrder(final IHUTreeNode node)
	{
		return HUEditorModelSaver.navigatePostOrder(node, null); // predicate=null => accept all
	}

	private static List<IHUTreeNode> navigatePostOrder(final IHUTreeNode node, final Predicate<IHUTreeNode> predicate)
	{
		final List<IHUTreeNode> result = new ArrayList<IHUTreeNode>();

		final List<IHUTreeNode> children = node.getChildren();
		if (children != null)
		{
			for (final IHUTreeNode child : children)
			{
				final List<IHUTreeNode> childResult = HUEditorModelSaver.navigatePostOrder(child, predicate);
				result.addAll(childResult);
			}
		}

		final boolean accept = predicate == null || predicate.evaluate(node);
		if (accept)
		{
			result.add(node);
		}

		return result;
	}

	private List<HUItemMITreeNode> getNodesForDocumentLine(final IHUDocumentLine documentLine, final List<IHUTreeNode> nodes)
	{
		final List<HUItemMITreeNode> result = new ArrayList<HUItemMITreeNode>();
		for (final IHUTreeNode node : nodes)
		{
			if (!(node instanceof HUItemMITreeNode))
			{
				continue;
			}

			final HUItemMITreeNode itemNode = (HUItemMITreeNode)node;

			if (isDocumentLineMatchesItem(documentLine, itemNode))
			{
				result.add(itemNode);
			}
		}

		return result;
	}

	private boolean isDocumentLineMatchesItem(final IHUDocumentLine documentLine, final HUItemMITreeNode node)
	{
		Check.assumeNotNull(documentLine, "documentLine not null");
		final HUItemMITreeNode itemNode = node;
		if (!X_M_HU_PI_Item.ITEMTYPE_Material.equals(itemNode.getM_HU_PI_Item().getItemType()))
		{
			return false;
		}

		final IHUDocumentLine itemNodeDocumentLine = itemNode.getHUDocumentLine();
		if (!Util.equals(itemNodeDocumentLine, documentLine))
		{
			return false;
		}

		return true;
	}

	private List<IHUTransaction> createTrxCandidates(final List<HUItemMITreeNode> itemNodes)
	{
		final List<IHUTransaction> trxCandidates = new ArrayList<IHUTransaction>();
		for (final HUItemMITreeNode itemNode : itemNodes)
		{
			final List<IHUTransaction> itemNodeTrxCandidates = createTrxCandidate(itemNode);
			if (itemNodeTrxCandidates == null)
			{
				continue;
			}
			trxCandidates.addAll(itemNodeTrxCandidates);
		}

		return trxCandidates;
	}

	private List<IHUTransaction> createTrxCandidate(final HUItemMITreeNode itemNode)
	{
		// final I_M_Product product = itemNode.getProduct().getM_Product();
		final List<IHUTransaction> result = new ArrayList<IHUTransaction>();

		final HUDocumentLineTreeNode documentLineNodeInitial = itemNode.getHUDocumentTreeNodeInitial();
		final BigDecimal qtyInitial = itemNode.getQtyInitial();

		final HUDocumentLineTreeNode documentLineNode = itemNode.getHUDocumentLineTreeNode();
		final BigDecimal qtyFinal = itemNode.getQty();

		if (documentLineNode == documentLineNodeInitial)
		{
			final BigDecimal qty = qtyFinal.subtract(qtyInitial);
			if (qty.signum() == 0)
			{
				return null;
			}

			result.addAll(createTrxCandidate(itemNode, documentLineNode, qty));
		}
		else
		{
			if (documentLineNodeInitial != null)
			{
				result.addAll(createTrxCandidate(itemNode, documentLineNodeInitial, qtyInitial.negate()));
			}
			result.addAll(createTrxCandidate(itemNode, documentLineNode, qtyFinal));
		}

		// After save is completed and transaction is commited we need to reset the note qtys
		afterSaveActions.add(new ResetHUItemTreeNodeToInitialValues(itemNode));

		return result;
	}

	private List<IHUTransaction> createTrxCandidate(final HUItemMITreeNode itemNode, final HUDocumentLineTreeNode documentLineNode, final BigDecimal qty)
	{
		if (qty.signum() == 0)
		{
			return Collections.emptyList();
		}
		Check.assumeNotNull(documentLineNode, "documentLineNode not null");

		final I_M_Product product = documentLineNode.getProduct();
		final I_C_UOM uom = itemNode.getC_UOM();
		final Date date = getDayDate();

		final Object fromModel = documentLineNode.getTrxReferencedModel();
		final Object toModel = itemNode.getM_HU_Item();

		final IAllocationRequest request = AllocationUtils.createQtyRequest(product, qty, uom, date, fromModel);

		return Services.get(IHUTrxBL.class).createQtyTransferCandidates(request, fromModel, toModel);
	}

	private void executeAfterSave()
	{
		for (final Iterator<Runnable> it = afterSaveActions.iterator(); it.hasNext();)
		{
			final Runnable action = it.next();
			action.run();
		}
	}
}
