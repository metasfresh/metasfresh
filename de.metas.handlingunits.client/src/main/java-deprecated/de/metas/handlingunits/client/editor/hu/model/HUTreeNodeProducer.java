package de.metas.handlingunits.client.editor.hu.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TrxRunnable;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.api.IHUTransaction;
import de.metas.handlingunits.api.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.tree.factory.IHUTreeNodeFactory;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode.IQtyRequest;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentLineTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;

public class HUTreeNodeProducer
{
	private final Object contextProvider;
	private final HUEditorModel model;
	private final IHUTreeNode rootNode;
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUTreeNodeFactory nodeFactory;

	public HUTreeNodeProducer(final HUEditorModel model)
	{
		super();
		this.model = model;
		this.contextProvider = model.getGlobalContextProvider();
		this.rootNode = model.getRoot();
		this.nodeFactory = model.getHUTreeNodeFactory();
	}

	public void load(final BigDecimal qty, final HUDocumentLineTreeNode documentLineNode)
	{
		final I_M_HU_PI huPI = getM_HU_PI(documentLineNode);
		if (huPI == null)
		{
			return;
		}

		final HUProducerDestination producer = createProducer(huPI);
		final I_C_UOM uom = documentLineNode.getC_UOM();

		final IAllocationRequest request = AllocationUtils.createQtyRequest(documentLineNode.getProduct(),
				qty,
				uom,
				SystemTime.asTimestamp(), // Date
				documentLineNode.getHUDocumentLine().getTrxReferencedModel() // reference model
				);
		final IAllocationResult result = load(producer, request);

		Check.assume(result.isCompleted(), "All qty is not allocated!");

		final List<IHUTreeNode> nodes = createNodes(producer.getCreatedHUs());
		final List<HUItemMITreeNode> itemNodes = extractHUItemMINodes(nodes);

		for (IHUTransaction trx : result.getTransactions())
		{
			final I_M_HU_Item trxItem = trx.getM_HU_Item();
			if (trxItem != null)
			{
				final HUItemMITreeNode huItemNode = findNodeByItem(itemNodes, trxItem);
				if (huItemNode == null)
				{
					continue;
				}

				final IQtyRequest qtyRequest = documentLineNode.requestQty(trx.getQty());
				huItemNode.setHUDocumentTreeNodeInitial(null);
				huItemNode.setHUDocumentTreeNode(documentLineNode);
				huItemNode.setQtyRequestInitial(qtyRequest);
				huItemNode.setQtyRequest(qtyRequest);

				final List<IHUTreeNodeProduct> products = huItemNode.getAvailableProducts();
				if (!products.isEmpty())
				{
					huItemNode.setProduct(products.get(0));
				}
			}
		}

		//
		// Add nodes to root tree node:
		for (IHUTreeNode node : nodes)
		{
			model.addNode(node, rootNode);
		}
	}

	private IAllocationResult load(final HUProducerDestination producer, final IAllocationRequest request)
	{
		final IAllocationResult[] result = new IAllocationResult[1];

		Services.get(ITrxManager.class).run(new TrxRunnable()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				result[0] = producer.load(request);
			}
		});

		return result[0];
	}

	private HUItemMITreeNode findNodeByItem(List<HUItemMITreeNode> itemNodes, I_M_HU_Item item)
	{
		for (final HUItemMITreeNode node : itemNodes)
		{
			if (node.getM_HU_Item().getM_HU_Item_ID() == item.getM_HU_Item_ID())
			{
				return node;
			}
		}

		return null;
	}

	private List<HUItemMITreeNode> extractHUItemMINodes(List<IHUTreeNode> nodes)
	{
		final List<HUItemMITreeNode> result = new ArrayList<HUItemMITreeNode>();
		for (final IHUTreeNode node : nodes)
		{
			final List<HUItemMITreeNode> nodeResult = extractHUItemMINodes(node);
			result.addAll(nodeResult);
		}

		return result;
	}

	private List<HUItemMITreeNode> extractHUItemMINodes(IHUTreeNode node)
	{
		final List<HUItemMITreeNode> result = new ArrayList<HUItemMITreeNode>();
		if (node instanceof HUItemMITreeNode)
		{
			final HUItemMITreeNode itemNode = (HUItemMITreeNode)node;
			result.add(itemNode);
		}

		for (final IHUTreeNode child : node.getChildren())
		{
			final List<HUItemMITreeNode> childResult = extractHUItemMINodes(child);
			result.addAll(childResult);
		}

		return result;
	}

	private Set<Integer> huIdsAddedToNodes = new HashSet<Integer>();

	private List<IHUTreeNode> createNodes(List<I_M_HU> hus)
	{
		final List<IHUTreeNode> nodes = new ArrayList<IHUTreeNode>();
		for (final I_M_HU hu : hus)
		{
			final IHUTreeNode node = createNode(hu);
			if (node != null)
			{
				nodes.add(node);
			}
		}

		return nodes;
	}

	private IHUTreeNode createNode(I_M_HU hu)
	{
		final int huId = hu.getM_HU_ID();
		if (!huIdsAddedToNodes.add(huId))
		{
			// HU was already converted to node and it was added.
			// Don't add it twice
			return null;
		}

		final boolean readonly = false;
		final IHUTreeNode node = nodeFactory.createNodeRecursively(hu, readonly);

		return node;
	}

	private HUProducerDestination createProducer(final I_M_HU_PI pi)
	{
		final HUProducerDestination producer = new HUProducerDestination(contextProvider, pi);
		producer.setHandlingUnitsDAO(handlingUnitsDAO);
		return producer;
	}

	private I_M_HU_PI getM_HU_PI(HUDocumentLineTreeNode documentLineNode)
	{
		final List<I_M_HU_PI> huPIs = documentLineNode.getHUDocumentLine().getAvailableHandlingUnitPIs();
		if (huPIs.isEmpty())
		{
			return null;
		}

		// Assume first one as suggestion
		return huPIs.get(0);
	}
}
