package de.metas.adempiere.gui.search;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JCheckBox;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.compiere.apps.search.IInfoColumnController;
import org.compiere.apps.search.IInfoQueryCriteria;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.apps.search.IInfoWindowGridRowBuilders;
import org.compiere.apps.search.InfoColumnControllerAdapter;
import org.compiere.apps.search.Info_Column;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.swing.CEditor;
import org.compiere.util.DB;

import de.metas.util.Services;

/**
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/04831_Produktinfo_Auftrag_%28103646722250%29
 */
public class InfoProductQtyController extends InfoColumnControllerAdapter implements IInfoQueryCriteria
{
	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	private final Map<Integer, BigDecimal> record2qty = new HashMap<>();
	private final Map<Integer, Integer> record2productId = new HashMap<>();

	private IInfoSimple parent;
	private Info_Column infoColumn;
	private I_AD_InfoColumn infoColumnDef;
	private JCheckBox checkbox;

	private IInfoColumnController gridConvertAfterLoadDelegate = null;

	@Override
	public void customize(final IInfoSimple infoWindow, final Info_Column infoColumn)
	{
		this.infoColumn = infoColumn;
		infoColumn.setReadOnly(false);
	}

	public Info_Column getInfoColumn()
	{
		return infoColumn;
	}

	@Override
	public List<String> getDependsOnColumnNames()
	{
		return Collections.emptyList();
	}

	@Override
	public Set<String> getValuePropagationKeyColumnNames(final Info_Column infoColumn)
	{
		return Collections.singleton(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void gridValueChanged(final Info_Column infoColumn, final int rowIndexModel, final Object value)
	{
		if (rowIndexModel < 0)
		{
			return;
		}

		final int rowId = parent.getRecordId(rowIndexModel);
		if (rowId <= 0)
		{
			return;
		}

		final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
		final IHUPackingAware record = huPackingAwareBL.create(parent, rowIndexModel);

		// We need to create a key that depends on both the product and the PIIP.
		// Note that we assume that both columns are read-only and therefore won't change!
		// Keep in sync with the other controllers in this package!
		// It's 1:17am, it has to be rolled out tomorrow and i *won't* make it any nicer tonight.
		// Future generations will have to live with this shit or rewrite it.
		final int recordId = new HashCodeBuilder()
				.append(record.getM_Product_ID())
				.append(record.getM_HU_PI_Item_Product_ID())
				.toHashCode();

		final BigDecimal qty = (BigDecimal)value;
		setQty(recordId, record.getM_Product_ID(), qty);
	}

	private void setQty(final int recordId, final int productId, final BigDecimal qty)
	{
		if (qty == null || qty.signum() == 0)
		{
			record2qty.remove(recordId);
			record2productId.remove(recordId);
		}
		else
		{
			record2qty.put(recordId, qty);
			record2productId.put(recordId, productId);
		}

		updateDisplay();
	}

	private void updateDisplay()
	{
		final boolean enabled = !record2qty.isEmpty();
		checkbox.setEnabled(enabled);
		checkbox.setVisible(enabled);
	}

	@Override
	public Object gridConvertAfterLoad(final Info_Column infoColumn, final int rowIndexModel, final int rowRecordId, final Object data)
	{
		if (gridConvertAfterLoadDelegate != null)
		{
			final Object valueConv = gridConvertAfterLoadDelegate.gridConvertAfterLoad(infoColumn, rowIndexModel, rowRecordId, data);
			if (valueConv != null)
			{
				if (InfoColumnControllerAdapter.RETURN_NULL.equals(valueConv))
				{
					return null;
				}
				return valueConv;
			}
		}

		//
		// Fallback to standard functionality: just get the Qty for given recordId
		if (record2qty.containsKey(rowRecordId))
		{
			final BigDecimal qty = record2qty.get(rowRecordId);
			return qty;
		}

		return data;
	}

	public void setGridConvertAfterLoadDelegate(final IInfoColumnController gridConvertAfterLoadDelegate)
	{
		this.gridConvertAfterLoadDelegate = gridConvertAfterLoadDelegate;
	}

	@Override
	public void save(final Properties ctx, final int windowNo, final IInfoWindowGridRowBuilders builders)
	{
		for (final Map.Entry<Integer, BigDecimal> e : record2qty.entrySet())
		{
			final Integer recordKey = e.getKey();
			if (recordKey == null)
			{
				continue;
			}

			final BigDecimal qty = e.getValue();
			if (qty == null || qty.signum() == 0)
			{
				continue;
			}

			final OrderLineProductQtyGridRowBuilder productQtyBuilder = new OrderLineProductQtyGridRowBuilder();
			final Integer productId = record2productId.get(recordKey);

			// productQtyBuilder.setSource(recordKey, productId, qty);
			productQtyBuilder.setSource(productId, qty);
			builders.addGridTabRowBuilder(recordKey, productQtyBuilder);
		}
	}

	@Override
	public void init(final IInfoSimple parent, final I_AD_InfoColumn infoColumn, final String searchText)
	{
		this.parent = parent;
		infoColumnDef = infoColumn;

		checkbox = new JCheckBox(infoColumn.getName());
		checkbox.addActionListener(e -> {
			InfoProductQtyController.this.parent.setIgnoreLoading(checkbox.isSelected());
			InfoProductQtyController.this.parent.executeQuery();
		});

		updateDisplay();
	}

	@Override
	public I_AD_InfoColumn getAD_InfoColumn()
	{
		return infoColumnDef;
	}

	@Override
	public int getParameterCount()
	{
		return 1;
	}

	@Override
	public String getLabel(final int index)
	{
		return null;
	}

	@Override
	public Object getParameterComponent(final int index)
	{
		return checkbox;
	}

	@Override
	public Object getParameterToComponent(final int index)
	{
		return null;
	}

	@Override
	public Object getParameterValue(final int index, final boolean returnValueTo)
	{
		return null;
	}

	@Override
	public String[] getWhereClauses(final List<Object> params)
	{
		if (!checkbox.isEnabled() || !checkbox.isSelected())
		{
			return null;
		}

		if (record2productId.isEmpty())
		{
			return new String[] { "1=2" };
		}

		final String productColumnName = org.compiere.model.I_M_Product.Table_Name + "." + org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;

		final StringBuilder whereClause = new StringBuilder(productColumnName + " IN " + DB.buildSqlList(record2productId.values(), params));
		if (gridConvertAfterLoadDelegate != null)
		{
			final String productComb = gridConvertAfterLoadDelegate.getProductCombinations();
			if (productComb != null)
			{
				whereClause.append(productComb);
			}
		}
		//
		// 05135: We need just to display rows that have Qtys, but DON'T discard other filtering criterias
		// return new String[] { WHERECLAUSE_CLEAR_PREVIOUS, whereClause, WHERECLAUSE_STOP};
		return new String[] { whereClause.toString() };
	}

	@Override
	public String getText()
	{
		return null;
	}

	@Override
	public void prepareEditor(final CEditor editor, final Object value, final int rowIndexModel, final int columnIndexModel)
	{
		// nothing
	}

	@Override
	public String getProductCombinations()
	{
		// nothing to do
		return null;
	}
}
