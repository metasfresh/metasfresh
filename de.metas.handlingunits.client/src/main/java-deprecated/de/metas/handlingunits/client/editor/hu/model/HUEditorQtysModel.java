package de.metas.handlingunits.client.editor.hu.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import de.metas.handlingunits.api.IHUCapacityDefinition;
import de.metas.handlingunits.document.IDataSource;
import de.metas.handlingunits.tree.node.ITreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;
import de.metas.handlingunits.tree.node.hu.impl.ItemProductHUTreeNodeProduct;

public class HUEditorQtysModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6035744394073504125L;

	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final HUEditorModel editorModel;

	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
	public static final String COLUMNNAME_QtyRequired = "QtyRequired";
	public static final String COLUMNNAME_Qty = "Qty";
	public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	private static final List<String> COLUMNNAMES = Arrays.asList(
			COLUMNNAME_M_Product_ID,
			COLUMNNAME_QtyRequired,
			COLUMNNAME_Qty,
			COLUMNNAME_C_UOM_ID
			);
	private static final int COLUMN_COUNT = COLUMNNAMES.size();

	private final Map<Integer, QtyLine> productId2Lines = new LinkedHashMap<Integer, QtyLine>();
	private final List<QtyLine> lines = new ArrayList<QtyLine>();

	/**
	 * Map: Node Object to {@link TreeNodeSnapshot}
	 */
	private IdentityHashMap<Object, TreeNodeSnapshot> node2snapshots = new IdentityHashMap<Object, TreeNodeSnapshot>();

	/**
	 * Class which contains only relevant values from our nodes
	 * 
	 * @author tsa
	 * 
	 */
	private static class TreeNodeSnapshot
	{
		private final I_M_Product product;
		private final BigDecimal qtyRequired;
		private final BigDecimal qty;
		private final I_C_UOM uom;
		private final boolean selected;

		public TreeNodeSnapshot(
				final I_M_Product product,
				final BigDecimal qtyRequired,
				final BigDecimal qty,
				final I_C_UOM uom,
				final boolean selected)
		{
			super();
			this.product = product;
			this.qtyRequired = qtyRequired;
			this.qty = qty;
			this.uom = uom;
			this.selected = selected;
		}

		@Override
		public String toString()
		{
			return "TreeNodeSnapshot ["
					+ "product=" + product
					+ ", qtyRequired=" + qtyRequired
					+ ", qty=" + qty
					+ ", uom=" + uom
					+ ", selected=" + selected
					+ "]";
		}

		@Override
		public int hashCode()
		{
			return new HashcodeBuilder()
					.append(product)
					.append(qtyRequired)
					.append(qty)
					.append(uom)
					.append(selected)
					.toHashcode();
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			final TreeNodeSnapshot other = EqualsBuilder.getOther(this, obj);
			if (other == null)
			{
				return false;
			}
			return new EqualsBuilder()
					.appendByRef(this.product, other.product)
					.append(this.qtyRequired, other.qtyRequired)
					.append(this.qty, other.qty)
					.appendByRef(this.uom, other.uom)
					.append(this.selected, other.selected)
					.isEqual();
		}

		public I_M_Product getProduct()
		{
			return product;
		}

		public BigDecimal getQtyRequired()
		{
			return qtyRequired;
		}

		public BigDecimal getQty()
		{
			return qty;
		}

		public I_C_UOM getUom()
		{
			return uom;
		}

		public boolean isSelected()
		{
			return selected;
		}
	}

	private static class QtyLine
	{
		private final I_M_Product product;
		private BigDecimal qtyRequired = BigDecimal.ZERO;
		private BigDecimal qty = BigDecimal.ZERO;
		private final I_C_UOM uom;

		public QtyLine(final I_M_Product product, final I_C_UOM uom)
		{
			super();
			this.product = product;
			this.uom = uom;
		}

		public BigDecimal getQty()
		{
			return qty;
		}

		public void addQty(final BigDecimal qtyDiff)
		{
			this.qty = this.qty.add(qtyDiff);
		}

		public I_M_Product getM_Product()
		{
			return product;
		}

		public BigDecimal getQtyRequired()
		{
			return qtyRequired;
		}

		public void addQtyRequired(final BigDecimal qtyRequiredDiff)
		{
			this.qtyRequired = this.qtyRequired.add(qtyRequiredDiff);
		}

		public I_C_UOM getC_UOM()
		{
			return uom;
		}
	}

	public HUEditorQtysModel(final HUEditorModel editorModel)
	{
		super();
		this.editorModel = editorModel;
		updateFromModel();

		this.editorModel.addPropertyChangeListener(HUEditorModel.PROPERTY_DataSource, new PropertyChangeListener()
		{

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				updateFromModel();
			}
		});

		this.editorModel.addTreeModelListener(new TreeModelListener()
		{

			@Override
			public void treeStructureChanged(TreeModelEvent e)
			{
				updateFromModel();
			}

			@Override
			public void treeNodesRemoved(TreeModelEvent e)
			{
				final boolean removed = true;
				updateFromNodesRecursively(e.getChildren(), removed);
			}

			@Override
			public void treeNodesInserted(TreeModelEvent e)
			{
				final boolean removed = false;
				updateFromNodesRecursively(e.getChildren(), removed);
			}

			@Override
			public void treeNodesChanged(TreeModelEvent e)
			{
				final boolean removed = false;
				updateFromNodesRecursively(e.getChildren(), removed);
			}
		});

		// TODO: implement qty required
	}

	@Override
	public int getRowCount()
	{
		return lines.size();
	}

	@Override
	public int getColumnCount()
	{
		return COLUMN_COUNT;
	}

	@Override
	public String getColumnName(final int columnIndex)
	{
		final Properties ctx = editorModel.getCtx();
		final String columnName = COLUMNNAMES.get(columnIndex);
		return Msg.translate(ctx, columnName);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		final QtyLine line = lines.get(rowIndex);
		final String columnName = COLUMNNAMES.get(columnIndex);

		if (COLUMNNAME_M_Product_ID.equals(columnName))
		{
			return line.getM_Product().getName();
		}
		else if (COLUMNNAME_QtyRequired.equals(columnName))
		{
			return line.getQtyRequired();
		}
		else if (COLUMNNAME_Qty.equals(columnName))
		{
			return line.getQty();
		}
		else if (COLUMNNAME_C_UOM_ID.equals(columnName))
		{
			return line.getC_UOM().getUOMSymbol();
		}
		else
		{
			return null;
		}
	}

	private void updateFromModel()
	{
		lines.clear();
		productId2Lines.clear();
		node2snapshots.clear();

		if (editorModel == null)
		{
			return;
		}

		//
		// Update from target capacities => Qty Required
		final IDataSource dataSource = editorModel.getDataSource();
		if (dataSource != null)
		{
			for (final IHUCapacityDefinition targetCapacity : dataSource.getTargetCapacities())
			{
				final boolean removed = false;
				updateFromObject(targetCapacity, removed);
			}
		}

		//
		// Update from HU Tree Nodes => Qty
		{
			final IHUTreeNode editorModelRoot = editorModel.getRoot();
			if (editorModelRoot == null)
			{
				return;
			}

			final boolean removed = false;
			updateFromNodeRecursively(editorModelRoot, removed);
		}
	}

	private void updateFromNodesRecursively(final Object[] nodes, final boolean removed)
	{
		if (nodes == null || nodes.length <= 0)
		{
			return;
		}

		for (final Object nodeObj : nodes)
		{
			final ITreeNode<?> node = (ITreeNode<?>)nodeObj;
			updateFromNodeRecursively(node, removed);
		}
	}

	private void updateFromNodeRecursively(final ITreeNode<?> node, final boolean removed)
	{
		updateFromObject(node, removed);

		//
		// Recursively add/remove node's children
		if (node != null)
		{
			for (final ITreeNode<?> child : node.getChildren())
			{
				updateFromNodeRecursively(child, removed);
			}
		}
	}

	private void updateFromObject(final Object node, final boolean removed)
	{
		if (node == null)
		{
			// Guard
			return;
		}

		final TreeNodeSnapshot nodeSnapshotPrevious = node2snapshots.get(node);
		final TreeNodeSnapshot nodeSnapshot = createTreeNodeSnapshot(node);

		//
		// Check if something changed
		if (!removed && Util.equals(nodeSnapshotPrevious, nodeSnapshot))
		{
			return;
		}

		//
		// Revert the effect of previous node snapshot
		updateFromNodeSnapshot(nodeSnapshotPrevious, true); // remove=true

		//
		// Add new snapshot
		if (!removed && nodeSnapshot != null)
		{
			// node was added
			updateFromNodeSnapshot(nodeSnapshot, false); // remove=false => ADD
			node2snapshots.put(node, nodeSnapshot);
		}
		else
		{
			// node was removed, nothing to do, just remove it from map
			node2snapshots.remove(node);
		}
	}

	private void updateFromNodeSnapshot(final TreeNodeSnapshot nodeSnapshot, final boolean remove)
	{
		if (nodeSnapshot == null)
		{
			return;
		}

		if (!nodeSnapshot.isSelected())
		{
			// was never selected, nothing to do
			return;
		}
		final I_M_Product product = nodeSnapshot.getProduct();
		if (product == null)
		{
			// no product
			return;
		}

		final I_C_UOM uom = nodeSnapshot.getUom();
		if (uom == null)
		{
			// no UOM
			return;
		}

		final BigDecimal qtyRequired = nodeSnapshot.getQtyRequired();
		final BigDecimal qty = nodeSnapshot.getQty();

		if (qtyRequired.signum() == 0 && qty.signum() == 0)
		{
			// zero qtys, nothing to do
			return;
		}

		final BigDecimal qtyRequiredDiff = remove ? qtyRequired.negate() : qtyRequired;
		final BigDecimal qtyDiff = remove ? qty.negate() : qty;

		final int productId = product.getM_Product_ID();
		final QtyLine lineExisting = productId2Lines.get(productId);
		final QtyLine line;
		if (lineExisting == null)
		{
			line = new QtyLine(product, uom);

			lines.add(line);
			productId2Lines.put(productId, line);
		}
		else
		{
			line = lineExisting;
		}

		//
		// Convert QtyRequired to line's UOM and add it to line
		final BigDecimal qtyRequiredConv = uomConversionBL.convertQty(
				line.getM_Product(),
				qtyRequiredDiff,
				uom, line.getC_UOM());
		line.addQtyRequired(qtyRequiredConv);

		//
		// Convert Qty to line's UOM and add it to line
		final BigDecimal qtyConv = uomConversionBL.convertQty(
				line.getM_Product(),
				qtyDiff,
				uom, line.getC_UOM());
		line.addQty(qtyConv);

		//
		// Fire events
		final int rowIndex = lines.indexOf(line);
		if (lineExisting == null)
		{
			fireTableRowsInserted(rowIndex, rowIndex);
		}
		else
		{
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
	}

	private TreeNodeSnapshot createTreeNodeSnapshot(final Object nodeObj)
	{
		if (nodeObj instanceof HUItemMITreeNode)
		{
			final HUItemMITreeNode node = (HUItemMITreeNode)nodeObj;
			final ItemProductHUTreeNodeProduct itemProduct = node.getProduct();
			final I_M_Product product;
			final I_C_UOM uom;
			if (itemProduct != null)
			{
				product = itemProduct.getM_Product();
				uom = node.getC_UOM();
			}
			else
			{
				product = null;
				uom = null;
			}

			final BigDecimal qty = node.getQty();
			final boolean selected = node.isSelected();
			final BigDecimal qtyRequired = BigDecimal.ZERO;

			return new TreeNodeSnapshot(product, qtyRequired, qty, uom, selected);
		}
		else if (nodeObj instanceof IHUCapacityDefinition)
		{
			final IHUCapacityDefinition capacity = (IHUCapacityDefinition)nodeObj;
			final I_M_Product product = capacity.getM_Product();
			final BigDecimal qtyRequired = capacity.getCapacity();
			final BigDecimal qty = BigDecimal.ZERO;
			final I_C_UOM uom = capacity.getC_UOM();
			final boolean selected = true;
			return new TreeNodeSnapshot(product, qtyRequired, qty, uom, selected);
		}
		else
		{
			return null;
		}
	}
}
