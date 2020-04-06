package de.metas.ui.web.material.cockpit.stockdetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.stock.HUStockInfo;
import de.metas.ui.web.material.cockpit.MaterialCockpitUtil;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public class StockDetailsRow implements IViewRow
{

	public static StockDetailsRow of(@NonNull final HUStockInfo huStockInfo)
	{
		return new StockDetailsRow(huStockInfo);
	}

	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 10)
	})
	LookupValue product;

	@ViewColumn(captionKey = "M_Attribute_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 20)
	})
	LookupValue attribute;

	@ViewColumn(captionKey = "AttributeValue", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 30)
	})
	String attributeValue;

	@ViewColumn(captionKey = "M_Locator_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	LookupValue locator;

	@ViewColumn(captionKey = "M_HU_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
	})
	LookupValue hu;

	@ViewColumn(captionKey = "HUStatus", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 60)
	})
	String huStatus;

	@ViewColumn(captionKey = "C_BPartner_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 70)
	})
	LookupValue bPartner;

	@ViewColumn(captionKey = "Qty", widgetType = DocumentFieldWidgetType.Quantity, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 80),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 80)
	})
	private final BigDecimal qty;

	@ViewColumn(captionKey = "C_UOM_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 90),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 90)
	})
	LookupValue uom;

	HUStockInfo huStockInfo;

	private StockDetailsRow(@NonNull final HUStockInfo huStockInfo)
	{
		this.huStockInfo = huStockInfo;

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;

		this.hu = lookupFactory
				.searchInTableLookup(I_M_HU.Table_Name)
				.findById(huStockInfo.getHuId().getRepoId());

		this.product = lookupFactory
				.searchInTableLookup(I_M_Product.Table_Name)
				.findById(huStockInfo.getProductId().getRepoId());

		this.locator = lookupFactory
				.searchInTableLookup(I_M_Locator.Table_Name)
				.findById(huStockInfo.getLocatorId().getRepoId());

		this.huStatus = huStockInfo.getHuStatus().translate(Env.getAD_Language());

		if (huStockInfo.getBPartnerId() == null)
		{
			this.bPartner = null;
		}
		else
		{
			this.bPartner = lookupFactory
					.searchInTableLookup(I_C_BPartner.Table_Name)
					.findById(huStockInfo.getBPartnerId().getRepoId());
		}

		this.qty = huStockInfo.getQty().toBigDecimal();

		this.uom = lookupFactory
				.searchInTableLookup(I_C_UOM.Table_Name)
				.findById(huStockInfo.getQty().getUOMId());

		this.attribute = lookupFactory
				.searchInTableLookup(I_M_Attribute.Table_Name)
				.findById(huStockInfo.getAttributeId().getRepoId());

		this.attributeValue = huStockInfo.getAttributeValue();
	}

	@Override
	public DocumentId getId()
	{
		final ImmutableList<Object> keyParts = ImmutableList.of(huStockInfo.getHuStorageRepoId(), huStockInfo.getHuAttributeRepoId());
		return DocumentId.ofComposedKeyParts(keyParts);
	}

	@Override
	public boolean isProcessed()
	{
		return true;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return DocumentPath.rootDocumentPath(
				MaterialCockpitUtil.WINDOW_MaterialCockpit_StockDetailView,
				getId());
	}

	@Override
	public Set<String> getFieldNames()
	{
		return ViewColumnHelper.extractFieldNames(this);
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return ViewColumnHelper.extractJsonMap(this);
	}

	@Override
	public Collection<? extends IViewRow> getIncludedRows()
	{
		return ImmutableList.of();
	}
}
