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


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.compiere.apps.search.IGridTabRowBuilder;
import org.compiere.apps.search.IInfoQueryCriteria;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.apps.search.IInfoWindowGridRowBuilders;
import org.compiere.apps.search.InfoColumnControllerAdapter;
import org.compiere.apps.search.Info_Column;
import org.compiere.grid.ed.IVPAttributeContext;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_C_OrderLine;
import org.compiere.swing.CEditor;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.IProductPA;

/**
 * Controller to be used for setting ASI in InfoProduct quick input.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/05769_Merkmalssatz_in_Produkt_Info_%28105973894938%29
 */
public class InfoProductASIController extends InfoColumnControllerAdapter implements IInfoQueryCriteria
{
	private static final transient Logger logger = LogManager.getLogger(InfoProductASIController.class);
	
	private final Map<Integer, Integer> recordId2asi = new HashMap<Integer, Integer>();

	private IInfoSimple parent;
	private I_AD_InfoColumn infoColumnDef;

	private class VPAttributeContext implements IVPAttributeContext
	{
		private final int rowIndexModel;

		public VPAttributeContext(final int rowIndexModel)
		{
			super();
			this.rowIndexModel = rowIndexModel;
		}

		@Override
		public int getWindowNo()
		{
			return Env.WINDOW_None;
		}

		@Override
		public int getTabNo()
		{
			return Env.TAB_None;
		}

		@Override
		public boolean isSOTrx()
		{
			return false;
		}

		@Override
		public int getM_Warehouse_ID()
		{
			return -1;
		}

		@Override
		public int getM_Product_ID()
		{
			return parent.getRecordId(rowIndexModel);
		}

		@Override
		public int getM_Locator_ID()
		{
			return -1;
		}

		@Override
		public int getM_AttributeSet_ID()
		{
			final int productId = getM_Product_ID();
			if (productId <= 0)
			{
				return -1;
			}

			final I_M_Product product = Services.get(IProductPA.class).retrieveProduct(Env.getCtx(), productId, false, ITrx.TRXNAME_None);
			if (product == null)
			{
				return -1;
			}

			// use the method from the service so if the product doesn't have an AS, it can be taken from product category
			final int productAttributeSet_ID = Services.get(IProductBL.class).getM_AttributeSet_ID(product);
			return productAttributeSet_ID;
		}

		@Override
		public int getC_DocType_ID()
		{
			return -1;
		}

		@Override
		public int getC_BPartner_ID()
		{
			return -1;
		}

		@Override
		public Boolean getSOTrx()
		{
			return null;
		}
	};

	private static class OrderLineProductASIGridRowBuilder implements IGridTabRowBuilder
	{
		private int asiId = -1;

		public OrderLineProductASIGridRowBuilder()
		{
			super();
		}

		@Override
		public boolean isValid()
		{
			return asiId > 0;
		}

		@Override
		public void apply(final Object model)
		{
			if (!InterfaceWrapperHelper.isInstanceOf(model, I_C_OrderLine.class))
			{
				logger.debug("Skip applying because it's not an order line: {}", model);
				return;
			}

			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
			applyOnOrderLine(orderLine);
		}

		private void applyOnOrderLine(final I_C_OrderLine orderLine)
		{
			if (asiId > 0)
			{
				// only set the ASI if one was specified!
				orderLine.setM_AttributeSetInstance_ID(asiId);
			}
		}

		@Override
		public boolean isCreateNewRecord()
		{
			return true;
		}

		public void setSource(final int recordKey_IGNORED, final int asiId)
		{
			this.asiId = asiId;
		}

		@Override
		public void setSource(final Object model)
		{
			// shall never been called
			throw new UnsupportedOperationException();
		}

		@Override
		public String toString()
		{
			return String.format("OrderLineProductASIGridRowBuilder [asiId=%s, isValid()=%s, isCreateNewRecord()=%s]", asiId, isValid(), isCreateNewRecord());
		}
	}

	@Override
	public void init(final IInfoSimple parent, final I_AD_InfoColumn infoColumn, final String searchText)
	{
		this.parent = parent;
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
		infoColumn.setReadOnly(false);
		infoColumn.setDisplayType(DisplayType.PAttribute); // redundant, just to be sure
		infoColumn.setColClass(KeyNamePair.class);
		infoColumn.setColSQL("NULL");
		infoColumn.setKeyPairColSQL("NULL");
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

		final KeyNamePair asi = (KeyNamePair)value;
		setASI(recordId, asi);
	}

	private void setASI(final int recordId, final KeyNamePair asi)
	{
		if (isValidASI(asi))
		{
			recordId2asi.put(recordId, asi.getKey());
		}
		else
		{
			recordId2asi.remove(recordId);
		}
	}

	private boolean isValidASI(final KeyNamePair asi)
	{
		if (asi == null)
		{
			return false;
		}
		if (asi.getKey() <= 0)
		{
			return false;
		}
		return true;
	}

	@Override
	public List<String> getDependsOnColumnNames()
	{
		return Collections.emptyList();
	}

	@Override
	public Set<String> getValuePropagationKeyColumnNames(final Info_Column infoColumn)
	{
		return Collections.singleton(InfoProductQtyController.COLUMNNAME_M_Product_ID);
	}

	@Override
	public Object gridConvertAfterLoad(final Info_Column infoColumn, final int rowIndexModel, final int rowRecordId, final Object data)
	{
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
		for (final Map.Entry<Integer, Integer> e : recordId2asi.entrySet())
		{
			final Integer recordId = e.getKey();
			if (recordId == null || recordId <= 0)
			{
				continue;
			}

			final Integer asiId = e.getValue();
			if (asiId == null || asiId <= 0)
			{
				continue;
			}

			final OrderLineProductASIGridRowBuilder productQtyBuilder = new OrderLineProductASIGridRowBuilder();
			productQtyBuilder.setSource(recordId, asiId);
			builders.addGridTabRowBuilder(recordId, productQtyBuilder);
		}
	}

	@Override
	public void prepareEditor(final CEditor editor, final Object value, final int rowIndexModel, final int columnIndexModel)
	{
		final VPAttributeContext attributeContext = new VPAttributeContext(rowIndexModel);
		editor.putClientProperty(IVPAttributeContext.ATTR_NAME, attributeContext);
		// nothing
	}

	@Override
	public String getProductCombinations()
	{
		// nothing to do
		return null;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

}
