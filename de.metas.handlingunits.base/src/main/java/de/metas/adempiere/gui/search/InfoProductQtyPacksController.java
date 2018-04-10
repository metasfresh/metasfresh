package de.metas.adempiere.gui.search;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.Services;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.compiere.apps.search.IInfoQueryCriteria;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.apps.search.IInfoWindowGridRowBuilders;
import org.compiere.apps.search.InfoColumnControllerAdapter;
import org.compiere.apps.search.Info_Column;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.swing.CEditor;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.gui.search.impl.OrderLineHUPackingGridRowBuilder;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * Implements QtyPack editable column.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/05111_Product_Info_quickentry_also_for_qty_of_Packing_Items_%28109744468671%29
 */
public class InfoProductQtyPacksController extends InfoColumnControllerAdapter implements IInfoQueryCriteria
{
	private final IHUPackingAwareBL packingAwareBL = Services.get(IHUPackingAwareBL.class);

	private final Map<ArrayKey, IHUPackingAware> packingInfos = new HashMap<ArrayKey, IHUPackingAware>();

	private IInfoSimple infoWindow;
	private Info_Column infoColumn;
	private I_AD_InfoColumn infoColumnDef;

	private Info_Column qtyInfoColumn;

	public static final String M_HU_PI_Item_Product_table_alias = "hu_pip";

	@Override
	public void init(final IInfoSimple parent, final I_AD_InfoColumn infoColumn, final String searchText)
	{
		infoWindow = parent;
		infoColumnDef = infoColumn;
	}

	@Override
	public I_AD_InfoColumn getAD_InfoColumn()
	{
		return infoColumnDef;
	}

	@Override
	public void customize(final IInfoSimple infoWindow, final Info_Column infoColumn)
	{
		this.infoColumn = infoColumn;
		infoColumn.setReadOnly(false);
	}

	@Override
	public void afterInfoWindowInit(final IInfoSimple infoWindow)
	{
		final InfoProductQtyController qtyController = infoWindow.getInfoColumnControllerOrNull(IHUPackingAware.COLUMNNAME_Qty_CU, InfoProductQtyController.class);
		if (qtyController != null)
		{
			qtyController.setGridConvertAfterLoadDelegate(this);
			qtyInfoColumn = qtyController.getInfoColumn();
		}
		// nothing
	}

	/**
	 * Flag used to avoid calling {@link #gridValueChanged(Info_Column, int, Object)} recursivelly
	 */
	private boolean valueChanging = false;

	@Override
	public void gridValueChanged(final Info_Column infoColumn, final int rowIndexModel, final Object value)
	{
		// NOTE: we need to make sure we avoid recursions
		// e.g. when QtyPacks is changed then Qty is changed and then QtyPacks is wanted to be changed again
		if (valueChanging)
		{
			return;
		}
		try
		{
			valueChanging = true;
			gridValueChanged0(infoColumn, rowIndexModel, value);
		}
		finally
		{
			valueChanging = false;
		}
	}

	private void gridValueChanged0(final Info_Column infoColumn, final int rowIndexModel, final Object value)
	{
		final IHUPackingAware record = packingAwareBL.create(infoWindow, rowIndexModel);

		if (infoColumn == this.infoColumn)
		{
			final Integer qtyPacks = InfoProductQtyPacksController.toIntegerOrNull(value);
			if (qtyPacks != null)
			{
				packingAwareBL.setQtyCUFromQtyTU(record, qtyPacks);
			}
		}
		else
		{
			packingAwareBL.setQtyTU(record);
		}

		//
		// Save or remove from our cache
		if (isValid(record))
		{
			save(record);
		}
		else
		{
			remove(record);
		}
	}

	private static boolean isValid(final IHUPackingAware record)
	{
		if (record == null)
		{
			return false;
		}

		return record.getM_Product_ID() > 0
				&& record.getM_HU_PI_Item_Product_ID() > 0
				&& isValidQty(record)
				&& record.getQtyTU() != null && record.getQtyTU().signum() != 0;
	}

	private static boolean isValidQty(final IHUPackingAware record)
	{
		if (record == null)
		{
			return false;
		}
		return record.getQty() != null && record.getQty().signum() != 0;
	}

	private static Integer toIntegerOrNull(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		if (!(value instanceof Number))
		{
			return null;
		}

		final int valueInt = ((Number)value).intValue();
		return valueInt;
	}

	private void save(final IHUPackingAware record)
	{
		final IHUPackingAware plainRecord = packingAwareBL.createPlain();
		packingAwareBL.copy(plainRecord, record);

		final ArrayKey key = mkKey(plainRecord);
		packingInfos.put(key, plainRecord);
	}

	private void remove(final IHUPackingAware record)
	{
		final ArrayKey key = mkKey(record);
		packingInfos.remove(key);
	}

	private ArrayKey mkKey(final IHUPackingAware record)
	{
		final int huPiItemProductId = record.getM_HU_PI_Item_Product_ID();
		return Util.mkKey(record.getM_Product_ID(), huPiItemProductId);
	}

	@Override
	public List<String> getDependsOnColumnNames()
	{
		return Arrays.asList(
				IHUPackingAware.COLUMNNAME_Qty_CU,
				IHUPackingAware.COLUMNNAME_M_HU_PI_Item_Product_ID
				, IHUPackingAware.COLUMNNAME_M_AttributeSetInstance_ID
				);
	}

	private static final Set<String> propagationKeyColumnNames = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
			IHUPackingAware.COLUMNNAME_M_Product_ID
			, IHUPackingAware.COLUMNNAME_M_AttributeSetInstance_ID
			, IHUPackingAware.COLUMNNAME_M_HU_PI_Item_Product_ID
			, IHUPackingAware.COLUMNNAME_C_BPartner_ID
			)));

	@Override
	public Set<String> getValuePropagationKeyColumnNames(final Info_Column infoColumn)
	{
		return propagationKeyColumnNames;
	}

	@Override
	public Object gridConvertAfterLoad(final Info_Column infoColumn, final int rowIndexModel, final int rowRecordId, final Object data)
	{
		if (Util.same(infoColumn, qtyInfoColumn))
		{
			final IHUPackingAware rowRecord = packingAwareBL.create(infoWindow, rowIndexModel);
			final IHUPackingAware record = getSavedHUPackingAware(rowRecord);
			if (record != null)
			{
				// Already saved record with an M_HU_PI_Item_Product.
				return record.getQty();
			}
			else
			{
				// If the record has M_HU_PI_Item_Product, return the quantity anyway.
				if (null != rowRecord && rowRecord.getM_HU_PI_Item_Product_ID() <= 0)
				{
					return rowRecord.getQty() == null ? InfoColumnControllerAdapter.RETURN_NULL : rowRecord.getQty();
				}
			}
		}

		return data;
	}

	@Override
	public int getParameterCount()
	{
		return 0;
	}

	@Override
	public String getLabel(final int index)
	{
		return null;
	}

	@Override
	public Object getParameterComponent(final int index)
	{
		return null;
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
		return new String[0];
	}

	@Override
	public String getText()
	{
		return null;
	}

	@Override
	public void save(final Properties ctx, final int windowNo, final IInfoWindowGridRowBuilders builders)
	{
		for (final IHUPackingAware record : packingInfos.values())
		{
			// We need to create a key that depends on both the product and the PIIP.
			// Note that we assume that both columns are read-only and therefore won't change!
			// Keep in sync with the other controllers in this package!
			// It's 1:17am, it has to be rolled out tomorrow and i *won't* make it any nicer tonight.
			// Future generations will have to live with this shit or rewrite it.
			final int recordId = new HashCodeBuilder()
					.append(record.getM_Product_ID())
					.append(record.getM_HU_PI_Item_Product_ID())
					.toHashCode();

			final OrderLineHUPackingGridRowBuilder builder = new OrderLineHUPackingGridRowBuilder();
			builder.setSource(record);
			builders.addGridTabRowBuilder(recordId, builder);
		}
	}

	/**
	 * Gets existing {@link IHUPackingAware} record for given row (wrapped as IHUPackingAware) or null.
	 *
	 * @param rowIndexModel
	 * @return {@link IHUPackingAware} or null
	 */
	private IHUPackingAware getSavedHUPackingAware(final IHUPackingAware rowRecord)
	{
		final ArrayKey key = mkKey(rowRecord);
		return packingInfos.get(key);
	}

	@Override
	public void prepareEditor(final CEditor editor, final Object value, final int rowIndexModel, final int columnIndexModel)
	{
		// nothing
	}

	@Override
	public String getProductCombinations()
	{
		final List<Integer> piItemProductIds = new ArrayList<Integer>();
		for (final IHUPackingAware record : packingInfos.values())
		{
			piItemProductIds.add(record.getM_HU_PI_Item_Product_ID());
		}

		if (!piItemProductIds.isEmpty() && piItemProductIds != null)
		{
			final StringBuilder sb = new StringBuilder(piItemProductIds.get(0).toString());
			for (int i = 1; i < piItemProductIds.size(); i++)
			{
				sb.append(", " + piItemProductIds.get(i).toString());

			}
			return " AND (" + M_HU_PI_Item_Product_table_alias + "." + I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID + " IN " + " ( " + sb + " ) "
					+ " OR " + M_HU_PI_Item_Product_table_alias + "." + I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID + " IS NULL" + ") ";
		}

		return null;
	}

}
