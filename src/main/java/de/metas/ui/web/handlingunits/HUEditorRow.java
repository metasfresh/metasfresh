package de.metas.ui.web.handlingunits;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.StringUtils;
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
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
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

	static final int HUSTATUS_AD_Reference_ID = X_M_HU.HUSTATUS_AD_Reference_ID;

	private final DocumentPath documentPath;
	private final HUEditorRowId rowId;
	private final HUEditorRowType type;
	private final boolean topLevel;
	private final boolean processed;

	public static final String FIELDNAME_M_HU_ID = I_M_HU.COLUMNNAME_M_HU_ID;
	@ViewColumn(fieldName = FIELDNAME_M_HU_ID, widgetType = DocumentFieldWidgetType.Integer)
	private final int huId;

	public static final String FIELDNAME_HUCode = I_M_HU.COLUMNNAME_Value;
	@ViewColumn(fieldName = FIELDNAME_HUCode, captionKey = "HUCode", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 10)
	})
	private final String code;

	public static final String FIELDNAME_Product = I_M_HU.COLUMNNAME_M_Product_ID;
	@ViewColumn(fieldName = FIELDNAME_Product, widgetType = DocumentFieldWidgetType.Lookup, sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 20)
	})
	private final JSONLookupValue product;

	public static final String FIELDNAME_HU_UnitType = "HU_UnitType";
	@ViewColumn(fieldName = FIELDNAME_HU_UnitType, widgetType = DocumentFieldWidgetType.Text, sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30)
	})
	private final JSONLookupValue huUnitType;

	public static final String FIELDNAME_PackingInfo = I_M_HU.COLUMNNAME_M_HU_PI_Item_Product_ID;
	@ViewColumn(fieldName = FIELDNAME_PackingInfo, widgetType = DocumentFieldWidgetType.Text, sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	private final String packingInfo;

	public static final String FIELDNAME_QtyCU = "QtyCU";
	@ViewColumn(fieldName = FIELDNAME_QtyCU, widgetType = DocumentFieldWidgetType.Quantity, sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
	})
	private final BigDecimal qtyCU;

	public static final String FIELDNAME_UOM = "C_UOM_ID";
	@ViewColumn(fieldName = FIELDNAME_UOM, widgetType = DocumentFieldWidgetType.Lookup, sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60),
	})
	private final JSONLookupValue uom;

	public static final String FIELDNAME_HUStatus = I_M_HU.COLUMNNAME_HUStatus;
	@ViewColumn(fieldName = FIELDNAME_HUStatus, widgetType = DocumentFieldWidgetType.Lookup, sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70),
	})
	private final JSONLookupValue huStatus;

	public static final String FIELDNAME_BestBeforeDate = "BestBeforeDate";
	@ViewColumn(fieldName = FIELDNAME_BestBeforeDate, widgetType = DocumentFieldWidgetType.Date, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 80, displayed = false),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 80, displayed = false)
	})
	private final Date bestBeforeDate;

	public static final String FIELDNAME_Locator = I_M_HU.COLUMNNAME_M_Locator_ID;
	@ViewColumn(fieldName = FIELDNAME_Locator, widgetType = DocumentFieldWidgetType.Lookup, sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 90, displayed = false),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 90, displayed = false)
	})
	private final JSONLookupValue locator;

	private final Supplier<HUEditorRowAttributes> attributesSupplier;

	private final List<HUEditorRow> includedRows;

	private transient String _summary; // lazy
	private transient Map<String, Object> _values; // lazy

	private HUEditorRow(final Builder builder)
	{
		documentPath = builder.getDocumentPath();
		rowId = builder.getRowId();

		type = builder.getType();
		topLevel = builder.isTopLevel();
		processed = builder.isProcessed();

		huId = rowId.getHuId();
		code = builder.code;
		huUnitType = builder.huUnitType;
		huStatus = builder.huStatus;
		packingInfo = builder.packingInfo;
		product = builder.product;
		uom = builder.uom;
		qtyCU = builder.qtyCU;
		bestBeforeDate = builder.getBestBeforeDate();
		locator = builder.getLocator();

		includedRows = builder.buildIncludedRows();

		final HUEditorRowAttributesProvider attributesProvider = builder.getAttributesProviderOrNull();
		if (attributesProvider != null)
		{
			final DocumentId attributesKey = attributesProvider.createAttributeKey(huId);
			attributesSupplier = () -> attributesProvider.getAttributes(rowId.toDocumentId(), attributesKey);
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

	public HUEditorRowId getHURowId()
	{
		return rowId;
	}

	@Override
	public DocumentId getId()
	{
		return getHURowId().toDocumentId();
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
	public List<HUEditorRow> getIncludedRows()
	{
		return includedRows;
	}

	public Optional<HUEditorRow> getIncludedRowById(final DocumentId rowId)
	{
		return streamRecursive()
				.filter(row -> rowId.equals(row.getId()))
				.map(HUEditorRow::cast)
				.findFirst();
	}

	public boolean hasDirectChild(final DocumentId childId)
	{
		return getIncludedRows()
				.stream()
				.filter(row -> childId.equals(row.getId()))
				.findAny()
				.isPresent();
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
		return load(huId, I_M_HU.class);
	}

	public boolean isHUPlanningReceiptOwnerPM()
	{
		// TODO: cache it or better it shall be provided when the row is created
		final I_M_HU hu = getM_HU();
		if (hu == null)
		{
			return false;
		}
		return hu.isHUPlanningReceiptOwnerPM();
	}

	public String getValue()
	{
		return code;
	}

	public JSONLookupValue getHUStatus()
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

	public boolean hasIncludedTUs()
	{
		return getIncludedRows().stream().anyMatch(HUEditorRow::isTU);
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
		return productLV == null ? null : productLV.getCaption();
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

	/**
	 * @param stringFilter
	 * @param adLanguage AD_Language (used to get the right row's string representation)
	 * @return true if the row is matching the string filter
	 */
	public boolean matchesStringFilter(final String stringFilter)
	{
		if (Check.isEmpty(stringFilter, true))
		{
			return true;
		}

		final String rowDisplayName = getSummary();

		final Function<String, String> normalizer = s -> StringUtils.stripDiacritics(s.trim()).toLowerCase();
		final String rowDisplayNameNorm = normalizer.apply(rowDisplayName);
		final String stringFilterNorm = normalizer.apply(stringFilter);

		return rowDisplayNameNorm.contains(stringFilterNorm);
	}

	//
	//
	//
	//
	//
	public static final class Builder
	{
		private final WindowId windowId;
		private HUEditorRowId _rowId;
		private Boolean topLevel;
		private HUEditorRowType type;
		private Boolean processed;

		private String code;
		private JSONLookupValue huUnitType;
		private JSONLookupValue huStatus;
		private String packingInfo;
		private JSONLookupValue product;
		private JSONLookupValue uom;
		private BigDecimal qtyCU;
		private Date bestBeforeDate;
		private JSONLookupValue locator;

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
			final HUEditorRowId rowId = getRowId();
			return DocumentPath.rootDocumentPath(windowId, rowId.getHuId());
		}

		public Builder setRowId(final HUEditorRowId rowId)
		{
			_rowId = rowId;
			return this;
		}

		/** @return row ID */
		private HUEditorRowId getRowId()
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

		public Builder setBestBeforeDate(final Date bestBeforeDate)
		{
			this.bestBeforeDate = bestBeforeDate;
			return this;
		}

		private Date getBestBeforeDate()
		{
			return bestBeforeDate != null ? (Date)bestBeforeDate.clone() : null;
		}

		public Builder setLocator(final JSONLookupValue locator)
		{
			this.locator = locator;
			return this;
		}

		private JSONLookupValue getLocator()
		{
			return locator;
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

	@lombok.Builder
	@lombok.Value
	public static class HUEditorRowHierarchy
	{
		
		@NonNull private final HUEditorRow cuRow;
		@Nullable private final HUEditorView view;
		@Nullable private final DocumentCollection documentCollection;
		@Nullable private final HUEditorRow parentRow;
		@Nullable private final HUEditorRow topLevelRow;
	}
}
