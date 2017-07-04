package de.metas.ui.web.handlingunits;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.NonNull;

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

/**
 * HU Editor's row
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class HUEditorRow implements IViewRow
{
	public static final Builder builder(final WindowId windowId)
	{
		return new Builder(windowId);
	}

	public static final HUEditorRow cast(final IViewRow viewRow)
	{
		return (HUEditorRow)viewRow;
	}

	public static DocumentId rowIdFromM_HU_ID(final int huId)
	{
		return DocumentId.of(huId);
	}

	public static DocumentIdsSelection rowIdsFromM_HU_IDs(final Collection<Integer> huIds)
	{
		return DocumentIdsSelection.ofIntSet(huIds);
	}

	public static DocumentId rowIdFromM_HU_Storage(final int huId, final int productId)
	{
		return DocumentId.ofString("HU" + huId + "_P" + productId);
	}

	public static Set<Integer> rowIdsToM_HU_IDs(final Collection<DocumentId> rowIds)
	{
		return DocumentIdsSelection.of(rowIds).toIntSet();
	}

	static final int HUSTATUS_AD_Reference_ID = X_M_HU.HUSTATUS_AD_Reference_ID;

	private final DocumentPath documentPath;
	private final DocumentId rowId;
	private final HUEditorRowType type;
	private final boolean topLevel;
	private final boolean processed;

	static final String COLUMNNAME_M_HU_ID = I_M_HU.COLUMNNAME_M_HU_ID;
	@ViewColumn(fieldName = COLUMNNAME_M_HU_ID, widgetType = DocumentFieldWidgetType.Integer)
	private final int huId;

	@ViewColumn(captionKey = "HUCode", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 10)
	})
	private final String code;

	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 20)
	})
	private final JSONLookupValue product;

	@ViewColumn(captionKey = "HU_UnitType", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30)
			// @ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 30)
	})
	private final JSONLookupValue huUnitType;

	@ViewColumn(captionKey = "M_HU_PI_Item_Product_ID", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	private final String packingInfo;

	@ViewColumn(captionKey = "QtyCU", widgetType = DocumentFieldWidgetType.Quantity, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
	})
	private final BigDecimal qtyCU;

	@ViewColumn(captionKey = "C_UOM_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60),
			// @ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 60)
	})
	private final JSONLookupValue uom;

	@ViewColumn(captionKey = "HUStatus", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70),
			// @ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 70)
	})
	private final JSONLookupValue huStatus;

	private final Supplier<HUEditorRowAttributes> attributesSupplier;

	private final List<HUEditorRow> includedRows;

	private transient String _summary; // lazy
	private transient Map<String, Object> _values; // lazy

	private HUEditorRow(final Builder builder)
	{
		documentPath = builder.getDocumentPath();
		rowId = documentPath.getDocumentId();

		type = builder.getType();
		topLevel = builder.isTopLevel();
		processed = builder.isProcessed();

		huId = builder.huId;
		code = builder.code;
		huUnitType = builder.huUnitType;
		huStatus = builder.huStatus;
		packingInfo = builder.packingInfo;
		product = builder.product;
		uom = builder.uom;
		qtyCU = builder.qtyCU;

		includedRows = builder.buildIncludedRows();

		final HUEditorRowAttributesProvider attributesProvider = builder.getAttributesProviderOrNull();
		if (attributesProvider != null)
		{
			final DocumentId attributesKey = attributesProvider.createAttributeKey(huId);
			attributesSupplier = () -> attributesProvider.getAttributes(rowId, attributesKey);
		}
		else
		{
			attributesSupplier = null;
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("rowId", rowId)
				.add("summary", getSummary())
				.toString();
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	/**
	 * @return {@link HUEditorRowType}; never returns null.
	 */
	@Override
	public HUEditorRowType getType()
	{
		return type;
	}

	@Override
	public boolean isProcessed()
	{
		return processed;
	}

	Object getFieldValueAsJson(final String fieldName)
	{
		return getFieldNameAndJsonValues().get(fieldName);
	}

	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		Map<String, Object> values = _values;
		if (values == null)
		{
			values = _values = ViewColumnHelper.extractJsonMap(this);
		}
		return values;
	}

	@Override
	public boolean hasAttributes()
	{
		return attributesSupplier != null;
	}

	@Override
	public HUEditorRowAttributes getAttributes() throws EntityNotFoundException
	{
		if (attributesSupplier == null)
		{
			throw new EntityNotFoundException("row does not support attributes");
		}

		final HUEditorRowAttributes attributes = attributesSupplier.get();
		if (attributes == null)
		{
			throw new EntityNotFoundException("row does not support attributes");
		}
		return attributes;
	}

	public Supplier<HUEditorRowAttributes> getAttributesSupplier()
	{
		return attributesSupplier;
	}

	@Override
	public boolean hasIncludedView()
	{
		return false;
	}

	@Override
	public List<HUEditorRow> getIncludedRows()
	{
		return includedRows;
	}

	/** @return a stream of this row and all it's included rows recursively */
	public Stream<HUEditorRow> streamRecursive()
	{
		return streamRecursive(this);
	}

	/** @return a stream of given row and all it's included rows recursively */
	private static Stream<HUEditorRow> streamRecursive(final HUEditorRow row)
	{
		return row.getIncludedRows()
				.stream()
				.map(includedRow -> streamRecursive(includedRow))
				.reduce(Stream.of(row), Stream::concat);
	}

	/**
	 *
	 * @return the ID of the wrapped HU or a value {@code <= 0} if there is none.
	 */
	public int getM_HU_ID()
	{
		return huId;
	}

	/**
	 *
	 * @return the wrapped HU or {@code null} if there is none.
	 */
	public I_M_HU getM_HU()
	{
		final int huId = getM_HU_ID();
		if (huId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(Env.getCtx(), huId, I_M_HU.class, ITrx.TRXNAME_ThreadInherited);
	}

	public String getValue()
	{
		return code;
	}

	private JSONLookupValue getHUStatus()
	{
		return huStatus;
	}

	public String getHUStatusKey()
	{
		final JSONLookupValue jsonHUStatus = getHUStatus();
		return jsonHUStatus == null ? null : jsonHUStatus.getKey();
	}

	public boolean isHUStatusPlanning()
	{
		return X_M_HU.HUSTATUS_Planning.equals(getHUStatusKey());
	}

	public boolean isHUStatusActive()
	{
		return X_M_HU.HUSTATUS_Active.equals(getHUStatusKey());
	}

	public boolean isHUStatusDestroyed()
	{
		return X_M_HU.HUSTATUS_Destroyed.equals(getHUStatusKey());
	}

	public boolean isPureHU()
	{
		return getType().isPureHU();
	}

	public boolean isCU()
	{
		return getType().isCU();
	}

	public boolean isTU()
	{
		return getType() == HUEditorRowType.TU;
	}

	public boolean isLU()
	{
		return getType() == HUEditorRowType.LU;
	}

	public boolean isTopLevel()
	{
		return topLevel;
	}

	public String getSummary()
	{
		if (_summary == null)
		{
			_summary = buildSummary();
		}
		return _summary;
	}

	private String buildSummary()
	{
		final StringBuilder summary = new StringBuilder();
		final String value = getValue();
		if (!Check.isEmpty(value, true))
		{
			summary.append(value);
		}

		final String packingInfo = getPackingInfo();
		if (!Check.isEmpty(packingInfo, true))
		{
			if (summary.length() > 0)
			{
				summary.append(" ");
			}
			summary.append(packingInfo);
		}

		return summary.toString();
	}

	public JSONLookupValue getProduct()
	{
		return product;
	}

	public int getM_Product_ID()
	{
		final JSONLookupValue productLV = getProduct();
		return productLV == null ? -1 : productLV.getKeyAsInt();
	}

	public String getM_Product_DisplayName()
	{
		final JSONLookupValue productLV = getProduct();
		return productLV == null ? null : productLV.getName();
	}

	public I_M_Product getM_Product()
	{
		final int productId = getM_Product_ID();
		if (productId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);
	}

	public String getPackingInfo()
	{
		return packingInfo;
	}

	public JSONLookupValue getUOM()
	{
		return uom;
	}

	/**
	 *
	 * @return the ID of the wrapped UOM or {@code -1} if there is none.
	 */
	public int getC_UOM_ID()
	{
		final JSONLookupValue uomLV = getUOM();
		return uomLV == null ? -1 : uomLV.getKeyAsInt();
	}

	/**
	 *
	 * @return the wrapped UOM or {@code null} if there is none.
	 */
	public I_C_UOM getC_UOM()
	{
		final int uomId = getC_UOM_ID();
		if (uomId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(Env.getCtx(), uomId, I_C_UOM.class, ITrx.TRXNAME_None);
	}

	/**
	 * The CU qty of this line. Generally {@code null}, unless this line represents exactly one {@link IHUProductStorage}.
	 */
	public BigDecimal getQtyCU()
	{
		return qtyCU;
	}

	public LookupValue toLookupValue()
	{
		return IntegerLookupValue.of(getM_HU_ID(), getSummary());
	}

	//
	//
	//
	//
	//
	public static final class Builder
	{
		private final WindowId windowId;
		private DocumentId _rowId;
		private Boolean topLevel;
		private HUEditorRowType type;
		private Boolean processed;

		private Integer huId;
		private String code;
		private JSONLookupValue huUnitType;
		private JSONLookupValue huStatus;
		private String packingInfo;
		private JSONLookupValue product;
		private JSONLookupValue uom;
		private BigDecimal qtyCU;

		private List<HUEditorRow> includedRows = null;

		private HUEditorRowAttributesProvider attributesProvider;

		private Builder(@NonNull final WindowId windowId)
		{
			this.windowId = windowId;
		}

		public HUEditorRow build()
		{
			return new HUEditorRow(this);
		}

		private DocumentPath getDocumentPath()
		{
			final DocumentId rowId = getRowId();
			return DocumentPath.rootDocumentPath(windowId, rowId);
		}

		public Builder setRowId(final DocumentId rowId)
		{
			_rowId = rowId;
			return this;
		}

		/** @return row ID */
		private DocumentId getRowId()
		{
			Check.assumeNotNull(_rowId, "Parameter rowId is not null");
			return _rowId;
		}

		private HUEditorRowType getType()
		{
			Check.assumeNotNull(type, "Parameter type is not null");
			return type;
		}

		public Builder setType(final HUEditorRowType type)
		{
			this.type = type;
			return this;
		}

		public Builder setTopLevel(final boolean topLevel)
		{
			this.topLevel = topLevel;
			return this;
		}

		private boolean isTopLevel()
		{
			Check.assumeNotNull(topLevel, "Parameter topLevel is not null");
			return topLevel;
		}

		public Builder setProcessed(final boolean processed)
		{
			this.processed = processed;
			return this;
		}

		private boolean isProcessed()
		{
			if (processed == null)
			{
				// NOTE: don't take the "Processed" field if any, because in frontend we will end up with a lot of grayed out completed sales orders, for example.
				// return DisplayType.toBoolean(values.getOrDefault("Processed", false));
				return false;
			}
			else
			{
				return processed.booleanValue();
			}
		}

		public Builder setHUId(final Integer huId)
		{
			this.huId = huId;
			return this;
		}

		public Builder setCode(final String code)
		{
			this.code = code;
			return this;
		}

		public Builder setHUUnitType(final JSONLookupValue huUnitType)
		{
			this.huUnitType = huUnitType;
			return this;
		}

		public Builder setHUStatus(final JSONLookupValue huStatus)
		{
			this.huStatus = huStatus;
			return this;
		}

		public Builder setPackingInfo(final String packingInfo)
		{
			this.packingInfo = packingInfo;
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

		public Builder setQtyCU(final BigDecimal qtyCU)
		{
			this.qtyCU = qtyCU;
			return this;
		}

		private HUEditorRowAttributesProvider getAttributesProviderOrNull()
		{
			return attributesProvider;
		}

		public Builder setAttributesProvider(@Nullable final HUEditorRowAttributesProvider attributesProvider)
		{
			this.attributesProvider = attributesProvider;
			return this;
		}

		public Builder addIncludedRow(final HUEditorRow includedRow)
		{
			if (includedRows == null)
			{
				includedRows = new ArrayList<>();
			}

			includedRows.add(includedRow);

			return this;
		}

		private List<HUEditorRow> buildIncludedRows()
		{
			if (includedRows == null || includedRows.isEmpty())
			{
				return ImmutableList.of();
			}

			return ImmutableList.copyOf(includedRows);
		}
	}
}
