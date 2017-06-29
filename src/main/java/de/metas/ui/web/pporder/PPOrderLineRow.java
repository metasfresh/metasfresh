package de.metas.ui.web.pporder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowAttributes;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public class PPOrderLineRow implements IViewRow
{
	public static final Builder builder(final DocumentId rowId)
	{
		return new Builder(rowId);
	}

	public static final PPOrderLineRow cast(final IViewRow viewRecord)
	{
		return (PPOrderLineRow)viewRecord;
	}

	private final DocumentPath documentPath;
	private final DocumentId rowId;

	private final Supplier<? extends IViewRowAttributes> attributesSupplier;

	private final List<PPOrderLineRow> includedDocuments;

	private final boolean processed;
	private final int ppOrderId;
	private final int ppOrderBOMLineId;
	private final int ppOrderQtyId;
	private final int huId;

	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10))
	private final JSONLookupValue product;

	@ViewColumn(captionKey = "Code", widgetType = DocumentFieldWidgetType.Text, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20))
	private final String code;

	@ViewColumn(captionKey = "Type", widgetType = DocumentFieldWidgetType.Text, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30))
	private final PPOrderLineType type;

	@ViewColumn(captionKey = "PackingInfo", widgetType = DocumentFieldWidgetType.Text, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40))
	private final String packingInfo;

	@ViewColumn(captionKey = "QtyPlan", widgetType = DocumentFieldWidgetType.Quantity, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50))
	private final BigDecimal qtyPlan;

	@ViewColumn(captionKey = "Qty", widgetType = DocumentFieldWidgetType.Quantity, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60))
	private final BigDecimal qty;

	@ViewColumn(captionKey = "C_UOM_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70))
	private final JSONLookupValue uom;

	private transient ImmutableMap<String, Object> _values;

	private PPOrderLineRow(final Builder builder)
	{
		rowId = builder.getRowId();
		type = builder.getType();
		documentPath = builder.getDocumentPath();

		ppOrderId = builder.ppOrderId;
		ppOrderBOMLineId = builder.ppOrderBOMLineId;
		ppOrderQtyId = builder.ppOrderQtyId;
		huId = builder.huId;

		processed = builder.processed;

		//
		// Values
		product = builder.product;
		uom = builder.uom;
		packingInfo = builder.packingInfo;
		code = builder.code;
		qty = builder.qty;
		qtyPlan = builder.qtyPlan;

		attributesSupplier = builder.attributesSupplier;
		includedDocuments = builder.buildIncludedDocuments();
	}

	public int getPP_Order_ID()
	{
		return ppOrderId;
	}

	public int getPP_Order_BOMLine_ID()
	{
		return ppOrderBOMLineId;
	}

	public int getPP_Order_Qty_ID()
	{
		return ppOrderQtyId;
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		ImmutableMap<String, Object> values = _values;
		if (values == null)
		{
			values = _values = ViewColumnHelper.extractJsonMap(this);
		}
		return values;
	}

	@Override
	public boolean isProcessed()
	{
		return processed;
	}

	@Override
	public PPOrderLineType getType()
	{
		return type;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	public JSONLookupValue getProduct()
	{
		return product;
	}

	public int getM_Product_ID()
	{
		final JSONLookupValue product = getProduct();
		return product == null ? -1 : product.getKeyAsInt();
	}

	public JSONLookupValue getUOM()
	{
		return uom;
	}

	public int getC_UOM_ID()
	{
		final JSONLookupValue uom = getUOM();
		return uom == null ? -1 : uom.getKeyAsInt();
	}

	public I_C_UOM getC_UOM()
	{
		final int uomId = getC_UOM_ID();
		return InterfaceWrapperHelper.load(uomId, I_C_UOM.class);
	}

	public String getPackingInfo()
	{
		return packingInfo;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	public BigDecimal getQtyPlan()
	{
		return qtyPlan;
	}

	public boolean isReceipt()
	{
		return getType().canReceive();
	}

	public boolean isIssue()
	{
		return getType().canIssue();
	}

	@Override
	public List<PPOrderLineRow> getIncludedRows()
	{
		return includedDocuments;
	}

	@Override
	public boolean hasIncludedView()
	{
		return isIssue();
	}

	@Override
	public boolean hasAttributes()
	{
		return attributesSupplier != null;
	}

	@Override
	public IViewRowAttributes getAttributes() throws EntityNotFoundException
	{
		if (attributesSupplier == null)
		{
			throw new EntityNotFoundException("Document does not support attributes");
		}

		final IViewRowAttributes attributes = attributesSupplier.get();
		if (attributes == null)
		{
			throw new EntityNotFoundException("Document does not support attributes");
		}
		return attributes;
	}

	//
	//
	//
	//
	//
	public static final class Builder
	{
		private final DocumentId rowId;
		private PPOrderLineType type;

		private List<PPOrderLineRow> includedDocuments = null;

		private Supplier<? extends IViewRowAttributes> attributesSupplier;

		private int ppOrderId;
		private int ppOrderBOMLineId;
		private int ppOrderQtyId;
		private int huId;
		private boolean processed = false;

		private JSONLookupValue product;
		private String code;
		private JSONLookupValue uom;
		private String packingInfo;
		private BigDecimal qtyPlan;
		private BigDecimal qty;
		private boolean qtyAsSumOfIncludedQtys = false;

		private Builder(@NonNull final DocumentId rowId)
		{
			this.rowId = rowId;
		}

		public PPOrderLineRow build()
		{
			return new PPOrderLineRow(this);
		}

		public Builder ppOrder(final int ppOrderId)
		{
			this.ppOrderId = ppOrderId;
			ppOrderBOMLineId = -1;
			return this;
		}

		public Builder ppOrderBOMLineId(final int ppOrderId, final int ppOrderBOMLineId)
		{
			this.ppOrderId = ppOrderId;
			this.ppOrderBOMLineId = ppOrderBOMLineId;
			return this;
		}

		public Builder ppOrderQtyId(final int ppOrderQtyId)
		{
			this.ppOrderQtyId = ppOrderQtyId;
			return this;
		}

		public Builder huId(final int huId)
		{
			this.huId = huId;
			return this;
		}

		public Builder processed(final boolean processed)
		{
			this.processed = processed;
			return this;
		}

		private DocumentId getRowId()
		{
			return rowId;
		}

		private DocumentPath getDocumentPath()
		{
			final PPOrderLineType type = getType();
			if (type == PPOrderLineType.MainProduct)
			{
				return DocumentPath.rootDocumentPath(WebPPOrderConfig.AD_WINDOW_ID_PP_Order, DocumentId.of(ppOrderId));
			}
			else if (type.isBOMLine())
			{
				return DocumentPath.includedDocumentPath(WebPPOrderConfig.AD_WINDOW_ID_PP_Order, DocumentId.of(ppOrderId), WebPPOrderConfig.TABID_ID_PP_Order_BOMLine, DocumentId.of(ppOrderBOMLineId));
			}
			else if (type.isHUOrHUStorage())
			{
				return DocumentPath.rootDocumentPath(WEBUI_HU_Constants.WEBUI_HU_Window_ID, DocumentId.of(huId));
			}
			else
			{
				throw new IllegalStateException("Unknown type: " + type);
			}
		}

		private PPOrderLineType getType()
		{
			return type;
		}

		public Builder setType(final PPOrderLineType type)
		{
			this.type = type;
			return this;
		}

		public Builder setProcessed(final boolean processed)
		{
			this.processed = processed;
			return this;
		}

		public Builder setCode(final String code)
		{
			this.code = code;
			return this;
		}

		public Builder setProduct(final JSONLookupValue product)
		{
			this.product = product;
			return this;
		}

		public Builder setUOM(final JSONLookupValue uom)
		{
			this.uom = uom;
			return this;
		}

		public Builder setPackingInfo(final String packingInfo)
		{
			this.packingInfo = packingInfo;
			return this;
		}

		public Builder setQtyAsSumOfIncludedQtys()
		{
			qtyAsSumOfIncludedQtys = true;

			if (includedDocuments != null && !includedDocuments.isEmpty())
			{
				qty = includedDocuments.stream()
						.map(includedDocument -> includedDocument.getQty())
						.filter(includedQty -> includedQty != null && includedQty.signum() != 0) // non null/zero qtys only
						.reduce(BigDecimal.ZERO, (qtySum, includedQty) -> qtySum.add(includedQty));
			}

			return this;
		}

		public Builder setQty(final BigDecimal qty)
		{
			this.qty = qty;
			return this;
		}

		public Builder addQty(final BigDecimal qtyToAdd)
		{
			if (qtyToAdd == null)
			{
				return this;
			}

			if (qty == null)
			{
				qty = qtyToAdd;
			}
			else
			{
				qty = qty.add(qtyToAdd);
			}

			return this;
		}

		public Builder setQtyPlan(final BigDecimal qtyPlan)
		{
			this.qtyPlan = qtyPlan;
			return this;
		}

		public Builder setAttributesSupplier(final Supplier<? extends IViewRowAttributes> attributesSupplier)
		{
			this.attributesSupplier = attributesSupplier;
			return this;
		}

		/**
		 * Adds included row.
		 * 
		 * If {@link #setQtyAsSumOfIncludedQtys()} was already called it will also increase the Qty.
		 * 
		 * @param includedDocument
		 * @return
		 */
		public Builder addIncludedDocument(final PPOrderLineRow includedDocument)
		{
			if (includedDocuments == null)
			{
				includedDocuments = new ArrayList<>();
			}

			includedDocuments.add(includedDocument);

			if (qtyAsSumOfIncludedQtys)
			{
				addQty(includedDocument.getQty());
			}

			return this;
		}

		public <T> Builder addIncludedDocumentFrom(final Collection<T> includedDocObjs, Function<T, PPOrderLineRow> includedDocumentMapper)
		{
			if (includedDocObjs == null || includedDocObjs.isEmpty())
			{
				return this;
			}

			includedDocObjs.stream()
					.map(includedDocumentMapper)
					.forEach(this::addIncludedDocument);

			return this;
		}

		private List<PPOrderLineRow> buildIncludedDocuments()
		{
			if (includedDocuments == null || includedDocuments.isEmpty())
			{
				return ImmutableList.of();
			}

			return ImmutableList.copyOf(includedDocuments);
		}
	}

}
