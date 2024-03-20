package de.metas.ui.web.handlingunits;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.report.HUReportAwareViewRow;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout.Displayed;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Stream;

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
 */
@EqualsAndHashCode
public final class HUEditorRow implements IViewRow, HUReportAwareViewRow
{
	public static final String SYSCFG_PREFIX = "de.metas.ui.web.handlingunits.field";


	public static Builder builder(final WindowId windowId)
	{
		return new Builder(windowId);
	}

	public static HUEditorRow cast(final IViewRow viewRow)
	{
		return (HUEditorRow)viewRow;
	}

	private final DocumentPath documentPath;
	private final HUEditorRowId rowId;
	private final HUEditorRowType type;
	@Getter private final boolean topLevel;
	private final boolean processed;
	@Getter
	private final BPartnerId bpartnerId;
	@Getter
	private final LocatorId locatorId;

	public static final String FIELDNAME_M_HU_ID = I_M_HU.COLUMNNAME_M_HU_ID;
	@Getter @ViewColumn(fieldName = FIELDNAME_M_HU_ID, widgetType = DocumentFieldWidgetType.Integer)
	private final HuId huId;

	public static final String FIELDNAME_HUCode = I_M_HU.COLUMNNAME_Value;
	@ViewColumn(fieldName = FIELDNAME_HUCode, captionKey = "HUCode",//
			widgetSize = WidgetSize.Small,//
			widgetType = DocumentFieldWidgetType.Text, //
			layouts = {
					@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10),
					@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 10)
			})
	private final String code;

	public static final String FIELDNAME_Locator = I_M_HU.COLUMNNAME_M_Locator_ID;
	@ViewColumn(fieldName = FIELDNAME_Locator, //
			captionKey = FIELDNAME_Locator, //
			widgetType = DocumentFieldWidgetType.Text, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 15, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final JSONLookupValue locator;

	public static final String FIELDNAME_Product = I_M_HU.COLUMNNAME_M_Product_ID;
	@ViewColumn(fieldName = FIELDNAME_Product, widgetType = DocumentFieldWidgetType.Lookup, sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 20)
	})
	private final JSONLookupValue product;

	public static final String FIELDNAME_SerialNo = "SerialNo";
	@ViewColumn(fieldName = FIELDNAME_SerialNo, widgetType = DocumentFieldWidgetType.Text, sorting = false,
			layouts = {
					@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 23, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX),
					@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 23, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final String serialNo;

	public static final String FIELDNAME_ServiceContract = "ServiceContract";
	@ViewColumn(fieldName = FIELDNAME_ServiceContract, widgetType = DocumentFieldWidgetType.Text, sorting = false,
			layouts = {
					@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 23, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX),
					@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 23, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final String serviceContract;

	public static final String FIELDNAME_IsOwnPalette = I_M_HU.COLUMNNAME_HUPlanningReceiptOwnerPM;
	@ViewColumn(fieldName = FIELDNAME_IsOwnPalette, widgetType = DocumentFieldWidgetType.YesNo, sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 25)
	})
	private final Boolean isOwnPalette;

	public static final String FIELDNAME_HU_UnitType = "HU_UnitType";
	@ViewColumn(fieldName = FIELDNAME_HU_UnitType, //
			widgetType = DocumentFieldWidgetType.Text, //

			sorting = false, //
			restrictToMediaTypes = { MediaType.SCREEN }, //
			layouts = {
					@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30)
			})
	private final JSONLookupValue huUnitType;

	public static final String FIELDNAME_PackingInfo = I_M_HU.COLUMNNAME_M_HU_PI_Item_Product_ID;
	@ViewColumn(fieldName = FIELDNAME_PackingInfo, //
			captionKey = FIELDNAME_PackingInfo, //
			widgetType = DocumentFieldWidgetType.Text, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final String packingInfo;

	public static final String FIELDNAME_QtyCU = "QtyCU";
	@ViewColumn(fieldName = FIELDNAME_QtyCU, //
			widgetType = DocumentFieldWidgetType.Quantity,//
			widgetSize = WidgetSize.Small, sorting = false,
			layouts = {
					@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50),
					@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
			})
	private final BigDecimal qtyCU;

	public static final String FIELDNAME_UOM = I_M_Product.COLUMNNAME_C_UOM_ID;
	@ViewColumn(fieldName = FIELDNAME_UOM, //
			captionKey = FIELDNAME_UOM, //
			widgetType = DocumentFieldWidgetType.Text, //
			widgetSize = WidgetSize.Small,
			layouts = {
					@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX),
					@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 60)
			})
	private final JSONLookupValue uom;


	public static final String FIELDNAME_HUStatus = I_M_HU.COLUMNNAME_HUStatus;
	@ViewColumn(fieldName = FIELDNAME_HUStatus,//
			widgetType = DocumentFieldWidgetType.Lookup, //
			widgetSize = WidgetSize.Small,//
			sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70),
	})
	private final JSONLookupValue huStatusDisplay;

	public static final String FIELDNAME_IsReserved = I_M_HU.COLUMNNAME_IsReserved;
	private final boolean huReserved;

	private final String huStatus;

	public static final String FIELDNAME_BestBeforeDate = "BestBeforeDate";
	@ViewColumn(fieldName = FIELDNAME_BestBeforeDate, widgetType = DocumentFieldWidgetType.LocalDate, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 80, displayed = Displayed.FALSE),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 80, displayed = Displayed.FALSE)
	})
	private final LocalDate bestBeforeDate;

	public static final String FIELDNAME_WeightGross = "WeightGross";
	@ViewColumn(fieldName = FIELDNAME_WeightGross, widgetType = DocumentFieldWidgetType.Quantity, seqNo = 90, displayed = Displayed.FALSE)
	private final BigDecimal weightGross;

	public static final String FIELDNAME_ClearanceStatus = I_M_HU.COLUMNNAME_ClearanceStatus;
	@ViewColumn(fieldName = FIELDNAME_ClearanceStatus, widgetType = DocumentFieldWidgetType.Text, sorting = false, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final JSONLookupValue clearanceStatus;


	public static final String FIELDNAME_PROJECT = I_M_HU.COLUMNNAME_C_Project_ID;
	@ViewColumn(fieldName = FIELDNAME_PROJECT, //
			captionKey = FIELDNAME_PROJECT, //
			widgetType = DocumentFieldWidgetType.Text, //
			layouts = {
				@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 95, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX),
				@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 95)
			})
	private final JSONLookupValue project;


	private final Optional<HUEditorRowAttributesSupplier> attributesSupplier;

	private final List<HUEditorRow> includedRows;

	@Getter
	private final ImmutableMultimap<OrderLineId, HUEditorRow> includedOrderLineReservations;

	private transient String _summary; // lazy
	private final ViewRowFieldNameAndJsonValuesHolder<HUEditorRow> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(HUEditorRow.class);

	private HUEditorRow(@NonNull final Builder builder)
	{
		documentPath = builder.getDocumentPath();
		rowId = builder.getRowId();

		type = builder.getType();
		topLevel = builder.isTopLevel();
		processed = builder.isProcessed();
		bpartnerId = builder.getBPartnerId();

		huId = rowId.getHuId();
		code = builder.code;
		huUnitType = builder.huUnitType;

		huStatus = builder.huStatus;
		huReserved = builder.huReserved;
		huStatusDisplay = builder.huStatusDisplay;

		packingInfo = builder.packingInfo;
		product = builder.product;
		isOwnPalette = builder.isOwnPalette;
		uom = builder.uom;
		qtyCU = builder.qtyCU;
		weightGross = builder.getWeightGross();
		bestBeforeDate = builder.getBestBeforeDate();
		project = builder.project;

		clearanceStatus = builder.clearanceStatus;

		this.locatorId = builder.locatorId;
		this.locator = locatorId != null
				? JSONLookupValue.of(locatorId, builder.locatorCaption)
				: null;

		includedRows = builder.buildIncludedRows();
		includedOrderLineReservations = builder.prepareIncludedOrderLineReservations(this);

		final HUEditorRowAttributesProvider attributesProvider = builder.getAttributesProviderOrNull();
		if (attributesProvider != null)
		{
			attributesSupplier = Optional.of(HUEditorRowAttributesSupplier.builder()
					.viewRowId(rowId.toDocumentId())
					.huId(huId)
					.provider(attributesProvider)
					.build());
		}
		else
		{
			attributesSupplier = Optional.empty();
		}

		if (attributesSupplier.isPresent() && attributesSupplier.get().get().hasAttribute(AttributeConstants.ATTR_SerialNo))
		{
			serialNo = attributesSupplier.get().get().getValueAsString(AttributeConstants.ATTR_SerialNo);
		}
		else
		{
			serialNo = null;
		}

		serviceContract = null;
	}

	@Override
	public boolean hasAttributes()
	{
		return attributesSupplier.isPresent();
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

	public JSONLookupValue getClearanceStatus()
	{
		return clearanceStatus;
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

	@Override
	public ImmutableSet<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}

	@Override
	public HUEditorRowAttributes getAttributes() throws EntityNotFoundException
	{
		if (!attributesSupplier.isPresent())
		{
			throw new EntityNotFoundException("row does not support attributes");
		}

		final HUEditorRowAttributes attributes = attributesSupplier.get().get();
		if (attributes == null)
		{
			throw new EntityNotFoundException("row does not support attributes");
		}

		return attributes;
	}

	public Optional<HUEditorRowAttributesSupplier> getAttributesSupplier()
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

	public boolean hasDirectChild(@NonNull final DocumentId childId)
	{
		return getIncludedRows()
				.stream()
				.anyMatch(row -> childId.equals(row.getId()));
	}

	@Override
	public HuUnitType getHUUnitTypeOrNull()
	{
		return getType().toHUUnitTypeOrNull();
	}

	/**
	 * @return the wrapped HU or {@code null} if there is none.
	 */
	@Nullable
	public I_M_HU getM_HU()
	{
		final HuId huId = getHuId();
		if (huId == null)
		{
			return null;
		}

		return Services.get(IHandlingUnitsDAO.class).getById(huId);
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

	public String getHUStatus()
	{
		return huStatus;
	}

	public JSONLookupValue getHUStatusDisplay()
	{
		return huStatusDisplay;
	}

	public JSONLookupValue getProjectDisplay()
	{
		return project;
	}

	public boolean isHUStatusPlanning()
	{
		return X_M_HU.HUSTATUS_Planning.equals(huStatus);
	}

	public boolean isHUStatusActive()
	{
		return X_M_HU.HUSTATUS_Active.equals(huStatus);
	}

	public boolean isHUStatusDestroyed()
	{
		return X_M_HU.HUSTATUS_Destroyed.equals(huStatus);
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

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean hasIncludedTUs()
	{
		return getIncludedRows().stream().anyMatch(HUEditorRow::isTU);
	}

	@Override
	public Stream<HUReportAwareViewRow> streamIncludedHUReportAwareRows()
	{
		return getIncludedRows().stream().map(HUEditorRow::toHUReportAwareViewRow);
	}

	private HUReportAwareViewRow toHUReportAwareViewRow() {return this;}

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

	@Nullable
	public ProductId getProductId()
	{
		final JSONLookupValue productLV = getProduct();
		return productLV != null ? ProductId.ofRepoId(productLV.getKeyAsInt()) : null;
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
	 * @return the ID of the wrapped UOM or {@code -1} if there is none.
	 */
	@Nullable
	public UomId getUomId()
	{
		final JSONLookupValue uomLV = getUOM();
		return uomLV == null ? null : UomId.ofRepoIdOrNull(uomLV.getKeyAsInt());
	}

	/**
	 * @return the wrapped UOM or {@code null} if there is none.
	 */
	@Nullable
	public I_C_UOM getC_UOM()
	{
		final UomId uomId = getUomId();
		return uomId != null
				? Services.get(IUOMDAO.class).getById(uomId)
				: null;
	}

	/**
	 * The CU qty of this line. Generally {@code null}, unless this line represents exactly one {@link IHUProductStorage}.
	 */
	@Nullable
	public BigDecimal getQtyCU()
	{
		return qtyCU;
	}

	@Nullable
	public Quantity getQtyCUAsQuantity()
	{
		final BigDecimal qtyCU = getQtyCU();
		if (qtyCU == null)
		{
			return null;
		}

		final I_C_UOM uom = getC_UOM();
		if (uom == null)
		{
			return null;
		}

		return Quantity.of(qtyCU, uom);
	}

	public LookupValue toLookupValue()
	{
		return IntegerLookupValue.of(HuId.toRepoId(getHuId()), getSummary());
	}

	/**
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

	@NonNull
	public ImmutableSet<HuId> getAllHuIds()
	{
		final ImmutableSet.Builder<HuId> huIdCollector = ImmutableSet.builder();
		huIdCollector.add(getHuId());

		final Stack<HUEditorRow> rowsToProcess = new Stack<>();
		if (includedRows != null)
		{
			includedRows.forEach(rowsToProcess::push);
		}

		while (!rowsToProcess.isEmpty())
		{
			final HUEditorRow currentRow = rowsToProcess.pop();
			huIdCollector.add(currentRow.getHuId());

			Optional.ofNullable(currentRow.getIncludedRows())
					.ifPresent(inclRows -> inclRows.forEach(rowsToProcess::push));
		}

		return huIdCollector.build();
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

		private String huStatus;
		private boolean huReserved;
		private JSONLookupValue huStatusDisplay;

		private String packingInfo;
		private JSONLookupValue product;
		private JSONLookupValue project;
		private Boolean isOwnPalette;
		private JSONLookupValue uom;
		private BigDecimal qtyCU;
		private BigDecimal weightGross;
		private LocalDate bestBeforeDate;
		private LocatorId locatorId;
		private String locatorCaption;
		private BPartnerId bpartnerId;

		@Nullable
		private JSONLookupValue clearanceStatus;

		private List<HUEditorRow> includedRows = null;
		@Nullable
		private OrderLineId orderLineReservation = null;

		@Nullable
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

		/**
		 * @return row ID
		 */
		private HUEditorRowId getRowId()
		{
			return Check.assumeNotNull(_rowId, "Parameter rowId is not null");
		}

		private HUEditorRowType getType()
		{
			return Check.assumeNotNull(type, "Parameter type is not null");
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
			return Check.assumeNotNull(topLevel, "Parameter topLevel is not null");
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
				return processed;
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

		public Builder setHUStatus(final String huStatus)
		{
			this.huStatus = Check.assumeNotEmpty(huStatus, "Parameter huStatus may not be empty");
			return this;
		}

		public Builder setHUStatusDisplay(final JSONLookupValue huStatusDisplay)
		{
			this.huStatusDisplay = Check.assumeNotNull(huStatusDisplay, "Parameter huStatusDisplay may not be null");
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

		public Builder setProject(final JSONLookupValue project)
		{
			this.project = project;
			return this;
		}

		public Builder setIsOwnPalette(final Boolean isOwnPalette)
		{
			this.isOwnPalette = isOwnPalette;
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

		public Builder setWeightGross(final BigDecimal weightGross)
		{
			this.weightGross = weightGross;
			return this;
		}

		private BigDecimal getWeightGross()
		{
			return weightGross;
		}

		public Builder setBestBeforeDate(final LocalDate bestBeforeDate)
		{
			this.bestBeforeDate = bestBeforeDate;
			return this;
		}

		private LocalDate getBestBeforeDate()
		{
			return bestBeforeDate;
		}

		public Builder setLocator(final LocatorId locatorId, final String locatorCaption)
		{
			this.locatorId = locatorId;
			this.locatorCaption = locatorCaption;
			return this;
		}

		public Builder setBPartnerId(final BPartnerId bpartnerId)
		{
			this.bpartnerId = bpartnerId;
			return this;
		}

		private BPartnerId getBPartnerId()
		{
			return bpartnerId;
		}

		@Nullable
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

		public Builder setReservedForOrderLine(@Nullable final OrderLineId orderLineId)
		{
			orderLineReservation = orderLineId;
			huReserved = orderLineId != null;
			return this;
		}

		public Builder setClearanceStatus(@Nullable final JSONLookupValue clearanceStatusLookupValue)
		{
			clearanceStatus = clearanceStatusLookupValue;
			return this;
		}

		/**
		 * @param currentRow the row that is currently constructed using this builder
		 */
		private ImmutableMultimap<OrderLineId, HUEditorRow> prepareIncludedOrderLineReservations(@NonNull final HUEditorRow currentRow)
		{
			final ImmutableMultimap.Builder<OrderLineId, HUEditorRow> includedOrderLineReservationsBuilder = ImmutableMultimap.builder();

			for (final HUEditorRow includedRow : buildIncludedRows())
			{
				includedOrderLineReservationsBuilder.putAll(includedRow.getIncludedOrderLineReservations());
			}
			if (orderLineReservation != null)
			{
				includedOrderLineReservationsBuilder.put(orderLineReservation, currentRow);
			}
			return includedOrderLineReservationsBuilder.build();
		}
	}

	@lombok.Builder
	@lombok.Value
	public static class HUEditorRowHierarchy
	{
		@NonNull HUEditorRow cuRow;
		@Nullable
		HUEditorRow parentRow;
		@Nullable
		HUEditorRow topLevelRow;
	}
}
