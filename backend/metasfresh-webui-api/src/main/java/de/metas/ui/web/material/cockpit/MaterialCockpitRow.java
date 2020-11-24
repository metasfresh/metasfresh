package de.metas.ui.web.material.cockpit;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.dimension.DimensionSpecGroup;
import de.metas.i18n.IMsgBL;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout.Displayed;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.collections.ListUtils;
import de.metas.common.util.CoalesceUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
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
@EqualsAndHashCode(of = "documentId")
public class MaterialCockpitRow implements IViewRow
{
	/** Please keep its prefix in sync with {@link MaterialCockpitViewFactory#SYSCFG_DisplayIncludedRows} */
	public static final String SYSCFG_PREFIX = "de.metas.ui.web.material.cockpit.field";

	public static MaterialCockpitRow cast(final IViewRow row)
	{
		return (MaterialCockpitRow)row;
	}

	private static final String SEPARATOR = "-";
	private static final Joiner DOCUMENT_ID_JOINER = Joiner.on(SEPARATOR).skipNulls();

	private final LocalDate date;
	@Getter
	private final int productId;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, //
			captionKey = I_MD_Cockpit.COLUMNNAME_ProductValue, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10) })
	private final String productValue;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, //
			captionKey = I_MD_Cockpit.COLUMNNAME_ProductName, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20) })
	private final String productName;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, //
			captionKey = I_M_Product.COLUMNNAME_M_Product_Category_ID, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	@Getter
	@VisibleForTesting
	private final String productCategoryOrSubRowName;

	@Getter
	private final DimensionSpecGroup dimensionGroupOrNull;

	public static final String FIELDNAME_Manufacturer_ID = I_M_Product.COLUMNNAME_Manufacturer_ID;
	@ViewColumn(fieldName = FIELDNAME_Manufacturer_ID, //
			captionKey = FIELDNAME_Manufacturer_ID, //
			widgetType = DocumentFieldWidgetType.Lookup, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 32, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	/** Use supplier in order to make this work with unit tests; getting the LookupValue uses LookupDAO.retrieveLookupDisplayInfo which goes directly to the DB. */
	private final Supplier<LookupValue> manufacturer;

	public static final String FIELDNAME_PackageSize = I_M_Product.COLUMNNAME_PackageSize;
	@ViewColumn(fieldName = FIELDNAME_PackageSize, //
			captionKey = FIELDNAME_PackageSize, //
			widgetType = DocumentFieldWidgetType.Text, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 34, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final String packageSize;

	public static final String FIELDNAME_C_UOM_ID = I_M_Product.COLUMNNAME_C_UOM_ID;
	@ViewColumn(fieldName = FIELDNAME_C_UOM_ID, //
			captionKey = FIELDNAME_C_UOM_ID, //
			widgetType = DocumentFieldWidgetType.Lookup, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 32, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	/** Use supplier in order to make this work with unit tests; getting the LookupValue uses LookupDAO.retrieveLookupDisplayInfo which goes directly to the DB. */
	private final Supplier<LookupValue> uom;

	// Zusage Lieferant
	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Cockpit.COLUMNNAME_PMM_QtyPromised_OnDate, //
			layouts = {
					@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40, //
							displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final BigDecimal pmmQtyPromised;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Cockpit.COLUMNNAME_QtyReserved_Sale, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50) })
	private final BigDecimal qtyReservedSale;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Cockpit.COLUMNNAME_QtyReserved_Purchase, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60) })
	@Getter // note that we use the getter for testing
	private final BigDecimal qtyReservedPurchase;

	public static final String FIELDNAME_QtyMaterialentnahme = I_MD_Cockpit.COLUMNNAME_QtyMaterialentnahme;
	@ViewColumn(fieldName = FIELDNAME_QtyMaterialentnahme, //
			captionKey = FIELDNAME_QtyMaterialentnahme, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyMaterialentnahme;

	// MRP MEnge
	public static final String FIELDNAME_QtyRequiredForProduction = I_MD_Cockpit.COLUMNNAME_QtyRequiredForProduction;
	@ViewColumn(fieldName = FIELDNAME_QtyRequiredForProduction, //
			captionKey = FIELDNAME_QtyRequiredForProduction, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 80, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyRequiredForProduction;

	// Zaehlbestand
	public static final String FIELDNAME_QtyOnHandEstimate = I_MD_Cockpit.COLUMNNAME_QtyOnHandEstimate;
	@ViewColumn(fieldName = FIELDNAME_QtyOnHandEstimate, //
			captionKey = FIELDNAME_QtyOnHandEstimate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 90, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyOnHandEstimate;

	// zusagbar Zaehlbestand
	public static final String FIELDNAME_QtyAvailableToPromiseEstimate = I_MD_Cockpit.COLUMNNAME_QtyAvailableToPromiseEstimate;
	@ViewColumn(fieldName = FIELDNAME_QtyAvailableToPromiseEstimate, //
			captionKey = FIELDNAME_QtyAvailableToPromiseEstimate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyAvailableToPromiseEstimate;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Stock.COLUMNNAME_QtyOnHand, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 110) })
	@Getter // note that we use the getter for testing
	private final BigDecimal qtyOnHandStock;

	private final DocumentId documentId;

	private final DocumentPath documentPath;

	private final List<MaterialCockpitRow> includedRows;

	private final IViewRowType rowType;

	@Getter
	private final Set<Integer> allIncludedCockpitRecordIds;

	@Getter
	private final Set<Integer> allIncludedStockRecordIds;

	private final ViewRowFieldNameAndJsonValuesHolder<MaterialCockpitRow> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(MaterialCockpitRow.class);

	@lombok.Builder(builderClassName = "MainRowBuilder", builderMethodName = "mainRowBuilder")
	private MaterialCockpitRow(
			final Quantity pmmQtyPromised,
			final Quantity qtyReservedSale,
			final Quantity qtyReservedPurchase,
			final Quantity qtyMaterialentnahme,
			final Quantity qtyRequiredForProduction,
			final Quantity qtyAvailableToPromiseEstimate,
			final Quantity qtyOnHandEstimate,
			final Quantity qtyOnHandStock,

			@Singular final List<MaterialCockpitRow> includedRows,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds)
	{
		Check.errorIf(includedRows.isEmpty(), "The given includedRows may not be empty");

		this.rowType = DefaultRowType.Row;

		this.date = extractDate(includedRows);

		this.dimensionGroupOrNull = null;

		this.productId = extractProductId(includedRows);

		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"main",
				date,
				productId));

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView,
				documentId);

		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final I_M_Product productRecord = productDAO.getById(ProductId.ofRepoId(productId));
		final I_M_Product_Category productCategoryRecord = productDAO.getProductCategoryById(ProductCategoryId.ofRepoId(productRecord.getM_Product_Category_ID()));

		this.productValue = productRecord.getValue();
		this.productName = productRecord.getName();
		this.productCategoryOrSubRowName = productCategoryRecord.getName();

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;

		final int uomRepoId = CoalesceUtil.firstGreaterThanZero(productRecord.getPackage_UOM_ID(), productRecord.getC_UOM_ID());

		this.uom = () -> lookupFactory
				.searchInTableLookup(I_C_UOM.Table_Name)
				.findById(uomRepoId);
		this.manufacturer = () -> lookupFactory
				.searchInTableLookup(I_C_BPartner.Table_Name)
				.findById(productRecord.getManufacturer_ID());

		this.packageSize = productRecord.getPackageSize();

		this.includedRows = includedRows;

		this.pmmQtyPromised = Quantity.toBigDecimal(pmmQtyPromised);
		this.qtyReservedSale = Quantity.toBigDecimal(qtyReservedSale);
		this.qtyReservedPurchase = Quantity.toBigDecimal(qtyReservedPurchase);
		this.qtyMaterialentnahme = Quantity.toBigDecimal(qtyMaterialentnahme);
		this.qtyRequiredForProduction = Quantity.toBigDecimal(qtyRequiredForProduction);
		this.qtyOnHandEstimate = Quantity.toBigDecimal(qtyOnHandEstimate);
		this.qtyAvailableToPromiseEstimate = Quantity.toBigDecimal(qtyAvailableToPromiseEstimate);
		this.qtyOnHandStock = Quantity.toBigDecimal(qtyOnHandStock);

		final List<Quantity> quantitiesToVerify = Arrays.asList(
				pmmQtyPromised,
				qtyReservedSale,
				qtyReservedPurchase,
				qtyReservedPurchase,
				qtyMaterialentnahme,
				qtyRequiredForProduction,
				qtyOnHandEstimate,
				qtyAvailableToPromiseEstimate,
				qtyOnHandStock);
		assertNullOrCommonUomId(quantitiesToVerify);

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	private void assertNullOrCommonUomId(@NonNull final List<Quantity> quantitiesToVerify)
	{
		final boolean notOK = CollectionUtils.hasDifferentValues(
				ListUtils.copyAndFilter(quantitiesToVerify, Objects::nonNull),
				Quantity::getUomId);
		Check.errorIf(notOK, "Some of the given quantities have different UOMs; quantities={}", quantitiesToVerify);
	}

	private static LocalDate extractDate(final List<MaterialCockpitRow> includedRows)
	{
		return CollectionUtils.extractSingleElement(includedRows, row -> row.date);
	}

	private static int extractProductId(final List<MaterialCockpitRow> includedRows)
	{
		return CollectionUtils.extractSingleElement(includedRows, MaterialCockpitRow::getProductId);
	}

	@lombok.Builder(builderClassName = "AttributeSubRowBuilder", builderMethodName = "attributeSubRowBuilder")
	private MaterialCockpitRow(
			final int productId,
			final LocalDate date,
			@NonNull final DimensionSpecGroup dimensionGroup,
			final Quantity pmmQtyPromised,
			final Quantity qtyReservedSale,
			final Quantity qtyReservedPurchase,
			final Quantity qtyMaterialentnahme,
			final Quantity qtyRequiredForProduction,
			final Quantity qtyAvailableToPromiseEstimate,
			final Quantity qtyOnHandStock,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds)
	{
		this.rowType = DefaultRowType.Line;

		this.dimensionGroupOrNull = dimensionGroup;
		final String dimensionGroupName = dimensionGroup.getGroupName().translate(Env.getAD_Language());

		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"attributes",
				date,
				productId,
				dimensionGroupName));

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView,
				documentId);

		this.productId = productId;

		final I_M_Product productRecord = loadOutOfTrx(productId, I_M_Product.class);
		this.productValue = productRecord.getValue();
		this.productName = productRecord.getName();
		this.productCategoryOrSubRowName = dimensionGroupName;

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;

		final int uomRepoId = CoalesceUtil.firstGreaterThanZero(productRecord.getPackage_UOM_ID(), productRecord.getC_UOM_ID());
		this.uom = () -> lookupFactory
				.searchInTableLookup(I_C_UOM.Table_Name)
				.findById(uomRepoId);

		this.manufacturer = () -> lookupFactory
				.searchInTableLookup(I_C_BPartner.Table_Name)
				.findById(productRecord.getManufacturer_ID());

		this.packageSize = productRecord.getPackageSize();

		this.date = date;

		this.includedRows = ImmutableList.of();

		this.pmmQtyPromised = Quantity.toBigDecimal(pmmQtyPromised);
		this.qtyReservedSale = Quantity.toBigDecimal(qtyReservedSale);
		this.qtyReservedPurchase = Quantity.toBigDecimal(qtyReservedPurchase);
		this.qtyMaterialentnahme = Quantity.toBigDecimal(qtyMaterialentnahme);
		this.qtyRequiredForProduction = Quantity.toBigDecimal(qtyRequiredForProduction);
		this.qtyOnHandEstimate = null;
		this.qtyOnHandStock = Quantity.toBigDecimal(qtyOnHandStock);
		this.qtyAvailableToPromiseEstimate = Quantity.toBigDecimal(qtyAvailableToPromiseEstimate);

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	@lombok.Builder(builderClassName = "CountingSubRowBuilder", builderMethodName = "countingSubRowBuilder")
	private MaterialCockpitRow(
			final int productId,
			final LocalDate date,
			final int plantId,
			@Nullable final Quantity qtyOnHandEstimate,
			@Nullable final Quantity qtyOnHandStock,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds)
	{
		this.rowType = DefaultRowType.Line;

		this.dimensionGroupOrNull = null;

		final String plantName;
		if (plantId > 0)
		{
			final I_S_Resource plant = loadOutOfTrx(plantId, I_S_Resource.class);
			plantName = plant.getName();
		}
		else
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			plantName = msgBL.getMsg(Env.getCtx(), "de.metas.ui.web.material.cockpit.MaterialCockpitRow.No_Plant_Info");
		}
		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"countingRow",
				date,
				productId,
				plantName));

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView,
				documentId);

		this.productId = productId;

		final I_M_Product productRecord = loadOutOfTrx(productId, I_M_Product.class);
		this.productValue = productRecord.getValue();
		this.productName = productRecord.getName();
		this.productCategoryOrSubRowName = plantName;

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;

		final int uomRepoId = CoalesceUtil.firstGreaterThanZero(productRecord.getPackage_UOM_ID(), productRecord.getC_UOM_ID());
		this.uom = () -> lookupFactory
				.searchInTableLookup(I_C_UOM.Table_Name)
				.findById(uomRepoId);

		this.manufacturer = () -> lookupFactory
				.searchInTableLookup(I_C_BPartner.Table_Name)
				.findById(productRecord.getManufacturer_ID());

		this.packageSize = productRecord.getPackageSize();

		this.date = date;
		this.includedRows = ImmutableList.of();

		this.pmmQtyPromised = null;
		this.qtyReservedSale = null;
		this.qtyReservedPurchase = null;
		this.qtyMaterialentnahme = null;
		this.qtyRequiredForProduction = null;
		this.qtyOnHandEstimate = Quantity.toBigDecimal(qtyOnHandEstimate);
		this.qtyOnHandStock = Quantity.toBigDecimal(qtyOnHandStock);
		this.qtyAvailableToPromiseEstimate = null;

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	@Override
	public List<MaterialCockpitRow> getIncludedRows()
	{
		return includedRows;
	}

	@Override
	public DocumentId getId()
	{
		return documentId;
	}

	@Override
	public IViewRowType getType()
	{
		return rowType;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	/**
	 * Return false, because with true, all rows are "grayed" out. This does not mean that the rows are editable.
	 */
	@Override
	public boolean isProcessed()
	{
		return false;
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

}
