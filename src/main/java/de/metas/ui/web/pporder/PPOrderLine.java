package de.metas.ui.web.pporder;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.view.ForwardingDocumentView;
import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PPOrderLine extends ForwardingDocumentView implements IPPOrderBOMLine
{
	public static final PPOrderLine of(final String tableName, final int recordId, final IDocumentView delegate)
	{
		return new PPOrderLine(tableName, recordId, delegate);
	}

	public static final PPOrderLine cast(final IDocumentView viewRecord)
	{
		return (PPOrderLine)viewRecord;
	}

	private final String tableName;
	private final int recordId;

	private PPOrderLine(final String tableName, final int recordId, final IDocumentView delegate)
	{
		super(delegate);
		this.tableName = tableName;
		this.recordId = recordId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("tableName", tableName)
				.add("recordId", recordId)
				.add("delegate", getDelegate())
				.toString();
	}

	public String getTableName()
	{
		return tableName;
	}

	public int getRecordId()
	{
		return recordId;
	}

	@Override
	public PPOrderLineType getType()
	{
		return PPOrderLineType.cast(super.getType());
	}

	public String getBOMLineType()
	{
		return (String)getDelegate().getFieldValueAsJson(IPPOrderBOMLine.COLUMNNAME_BOMType);
	}

	public JSONLookupValue getProduct()
	{
		return (JSONLookupValue)getDelegate().getFieldValueAsJson(IPPOrderBOMLine.COLUMNNAME_M_Product_ID);
	}

	public int getM_Product_ID()
	{
		final JSONLookupValue product = getProduct();
		return product == null ? -1 : product.getKeyAsInt();
	}

	public JSONLookupValue getUOM()
	{
		return (JSONLookupValue)getDelegate().getFieldValueAsJson(IPPOrderBOMLine.COLUMNNAME_C_UOM_ID);
	}

	public int getC_UOM_ID()
	{
		final JSONLookupValue uom = getUOM();
		return uom == null ? -1 : uom.getKeyAsInt();
	}

	public BigDecimal getQty()
	{
		return (BigDecimal)getDelegate().getFieldValueAsJson(IPPOrderBOMLine.COLUMNNAME_Qty);
	}

	public BigDecimal getQtyPlan()
	{
		return (BigDecimal)getDelegate().getFieldValueAsJson(IPPOrderBOMLine.COLUMNNAME_QtyPlan);
	}

	public boolean isReceipt()
	{
		return getType().canReceive();
	}

	@Override
	public List<PPOrderLine> getIncludedDocuments()
	{
		return getIncludedDocuments(PPOrderLine.class);
	}

}
