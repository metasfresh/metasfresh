package de.metas.handlingunits.client.editor.hu.model;

import java.math.BigDecimal;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;

import de.metas.adempiere.form.IClientUI;
import de.metas.handlingunits.client.editor.allocation.model.HUDocumentsModel;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUItemStorageDetail;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode.IQtyRequest;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentLineTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;

/**
 * Loads model qtys and matchings from database
 * 
 * @author tsa
 * 
 */
public class HUEditorModelLoader
{
	private static final CLogger logger = CLogger.getCLogger(HUEditorModelLoader.class);

	private final HUEditorModel huEditorModel;

	private List<HUDocumentLineTreeNode> documentLineNodes;

	public HUEditorModelLoader(final HUEditorModel huEditorModel)
	{
		super();

		Check.assumeNotNull(huEditorModel, "huEditorModel not null");
		this.huEditorModel = huEditorModel;
	}

	public void load()
	{
		final HUDocumentsModel allocationModel = huEditorModel.getHUDocumentsModel();
		this.documentLineNodes = allocationModel.getHUDocumentLineTreeNodes();

		final IHUTreeNode rootNode = huEditorModel.getRoot();
		loadNode(rootNode);

		createHUTreeNodesForRemainingLines();
	}

	private void loadNode(final IHUTreeNode node)
	{
		if (node instanceof HUItemMITreeNode)
		{
			final HUItemMITreeNode itemTreeNode = (HUItemMITreeNode)node;
			loadNodeMI(itemTreeNode);
		}

		for (final IHUTreeNode child : node.getChildren())
		{
			loadNode(child);
		}
	}

	private void loadNodeMI(final HUItemMITreeNode itemTreeNode)
	{
		final IHUItemStorage storage = itemTreeNode.getStorage();

		// Item Qty which is allocated in other sources than ours
		BigDecimal qtyNotMatched = BigDecimal.ZERO;

		// Build a map of matched source line notes to Qty
		final IdentityHashMap<HUDocumentLineTreeNode, BigDecimal> matchedSourceLines2qty = new IdentityHashMap<HUDocumentLineTreeNode, BigDecimal>();

		for (final IHUItemStorageDetail storageDetail : storage.getDetails())
		{
			final BigDecimal qty = storageDetail.getQty();
			if (qty.signum() == 0)
			{
				continue;
			}

			HUDocumentLineTreeNode matchedSourceLineNode = getMatchedAllocationLineTreeNode(storageDetail.getAD_Table_ID(), storageDetail.getRecord_ID());
			//
			// Add Qty to Map
			if (matchedSourceLineNode == null)
			{
				// no matching source line node found
				// ... add qty to qty not matched
				qtyNotMatched = qtyNotMatched.add(qty);
			}
			else
			{
				// matching node found
				// ... add qty to map
				BigDecimal qtyMatched = matchedSourceLines2qty.get(matchedSourceLineNode);
				if (qtyMatched == null)
				{
					qtyMatched = qty;
				}
				else
				{
					qtyMatched = qtyMatched.add(qty);
				}
				//
				if (qtyMatched.signum() == 0)
				{
					// if qtyMatched is ZERO we can really delete it from our map
					matchedSourceLines2qty.remove(matchedSourceLineNode);
				}
				else
				{
					matchedSourceLines2qty.put(matchedSourceLineNode, qtyMatched);
				}
			}
		}

		//
		// Pick matched source line node
		// If there are multiple matched sources, pick the first one
		HUDocumentLineTreeNode matchedSourceLineNode = null;
		BigDecimal qtyMatched = BigDecimal.ZERO;
		for (final Map.Entry<HUDocumentLineTreeNode, BigDecimal> e : matchedSourceLines2qty.entrySet())
		{
			final BigDecimal qty = e.getValue();
			if (qty == null || qty.signum() == 0)
			{
				// skip zero lines (if any)
				continue;
			}

			if (matchedSourceLineNode == null)
			{
				// matching source line node was not yet picked
				// ... pick it now
				matchedSourceLineNode = e.getKey();
				qtyMatched = qty;
			}
			else
			{
				// matching source line node was already picked
				// .. just add the qty to not matched qty
				// NOTE: this shall not happen
				qtyNotMatched = qtyNotMatched.add(e.getValue());
			}
		}

		//
		// Link Item Node to Allocation Source Line Node (if any)
		if (matchedSourceLineNode != null)
		{
			final IQtyRequest qtyRequest = matchedSourceLineNode.unlockQty(qtyMatched);
			itemTreeNode.setHUDocumentTreeNodeInitial(matchedSourceLineNode);
			itemTreeNode.setHUDocumentTreeNode(matchedSourceLineNode);
			itemTreeNode.setQtyRequestInitial(qtyRequest);
			itemTreeNode.setQtyRequest(qtyRequest);

			// If there is a difference between qtyMatched(requested) and qty which was actually unlocked
			// book that qty to qty not matched
			final BigDecimal qtyRequestNotMatched = qtyMatched.subtract(qtyRequest.getQty());
			if (qtyRequestNotMatched.signum() != 0)
			{
				qtyNotMatched = qtyNotMatched.add(qtyRequestNotMatched);

				// NOTE: not sure if we shall throw an exception this case, but at least we are logging it
				final AdempiereException ex = new AdempiereException("Could not unlock all qty " + qtyMatched + " from " + matchedSourceLineNode + "."
						+ "\nFallback: add it to not-matched qty");
				logger.log(Level.WARNING, ex.getLocalizedMessage(), ex);
			}
		}

		//
		// Set Item's locked qty
		itemTreeNode.setQtyLocked(qtyNotMatched);

		//
		// Set Item Node Product: just pick the first product
		// FIXME This is subject to drop
		final List<IHUTreeNodeProduct> products = itemTreeNode.getAvailableProducts();
		if (!products.isEmpty())
		{
			itemTreeNode.setProduct(products.get(0));
		}
	}

	private HUDocumentLineTreeNode getMatchedAllocationLineTreeNode(final int adTableId, final int recordId)
	{
		for (final HUDocumentLineTreeNode documentLineNode : documentLineNodes)
		{
			final Object referencedModel = documentLineNode.getTrxReferencedModel();
			if (referencedModel == null)
			{
				continue;
			}
			if (adTableId != InterfaceWrapperHelper.getModelTableId(referencedModel))
			{
				continue;
			}
			if (recordId != InterfaceWrapperHelper.getId(referencedModel))
			{
				continue;
			}

			return documentLineNode;
		}

		return null;
	}

	private void createHUTreeNodesForRemainingLines()
	{
		try
		{
			createHUTreeNodesForRemainingLines0();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// Because it's not a critical error, we are just displaying the error to user, and then we can continue
			final int windowNo = 0; // N/A
			Services.get(IClientUI.class).warn(windowNo, e);
		}
	}

	private void createHUTreeNodesForRemainingLines0()
	{
		final HUTreeNodeProducer producer = new HUTreeNodeProducer(huEditorModel);
		for (final HUDocumentLineTreeNode documentLineNode : documentLineNodes)
		{
			final BigDecimal qty = documentLineNode.getQtyRemaining();
			if (qty.signum() == 0)
			{
				continue;
			}

			producer.load(qty, documentLineNode);
		}

	}
}
