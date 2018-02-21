package de.metas.ui.web.material.cockpit;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_M_Product;
import de.metas.dimension.DimensionSpecGroup;
import de.metas.i18n.IMsgBL;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
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
	private static final String SEPARATOR = "-";
	private static final Joiner DOCUMENT_ID_JOINER = Joiner.on(SEPARATOR).skipNulls();

	private final Timestamp date;
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
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30) })
	@Getter // note that we use the getter for testing
	private final String productCategoryOrSubRowName;

	// Zusage Lieferant
	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Cockpit.COLUMNNAME_PMM_QtyPromised_OnDate, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40) })
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

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Cockpit.COLUMNNAME_QtyMaterialentnahme, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70) })
	private final BigDecimal qtyMaterialentnahme;

	// MRP MEnge
	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Cockpit.COLUMNNAME_QtyRequiredForProduction, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 80) })
	private final BigDecimal qtyRequiredForProduction;

	// Zaehlbestand
	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Cockpit.COLUMNNAME_QtyOnHandEstimate, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 90) })
	private final BigDecimal qtyOnHandEstimate;

	// zusagbar Zaehlbestand
	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Cockpit.COLUMNNAME_QtyAvailableToPromise, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100) })
	private final BigDecimal qtyAvailableToPromise;

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

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues;

	@lombok.Builder(builderClassName = "MainRowBuilder", builderMethodName = "mainRowBuilder")
	private MaterialCockpitRow(
			final BigDecimal pmmQtyPromised,
			final BigDecimal qtyReservedSale,
			final BigDecimal qtyReservedPurchase,
			final BigDecimal qtyMaterialentnahme,
			final BigDecimal qtyRequiredForProduction,
			final BigDecimal qtyAvailableToPromise,
			final BigDecimal qtyOnHandEstimate,
			final BigDecimal qtyOnHandStock,
			@Singular final List<MaterialCockpitRow> includedRows,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds)
	{
		Check.errorIf(includedRows.isEmpty(), "The given includedRows may not be empty");

		this.rowType = DefaultRowType.Row;

		this.date = extractDate(includedRows);
		this.productId = extractProductId(includedRows);

		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"main",
				date,
				productId));

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitConstants.WINDOWID_MaterialCockpitView,
				documentId);

		final I_M_Product product = load(productId, I_M_Product.class);
		this.productValue = product.getValue();
		this.productName = product.getName();
		this.productCategoryOrSubRowName = product.getM_Product_Category().getName();

		this.includedRows = includedRows;

		this.pmmQtyPromised = pmmQtyPromised;
		this.qtyReservedSale = qtyReservedSale;
		this.qtyReservedPurchase = qtyReservedPurchase;
		this.qtyMaterialentnahme = qtyMaterialentnahme;
		this.qtyRequiredForProduction = qtyRequiredForProduction;
		this.qtyOnHandEstimate = qtyOnHandEstimate;
		this.qtyAvailableToPromise = qtyAvailableToPromise;
		this.qtyOnHandStock = qtyOnHandStock;

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	private static Timestamp extractDate(final List<MaterialCockpitRow> includedRows)
	{
		final List<Timestamp> dates = includedRows.stream().map(row -> row.date).distinct().collect(ImmutableList.toImmutableList());
		Check.errorIf(dates.size() > 1, "The given includedRows have different dates={}; includedRows={}", dates, includedRows);

		return dates.get(0);
	}

	private static int extractProductId(final List<MaterialCockpitRow> includedRows)
	{
		final List<Integer> productIds = includedRows.stream().map(row -> row.productId).distinct().collect(ImmutableList.toImmutableList());
		Check.errorIf(productIds.size() > 1, "The given includedRows have different productIds={}; includedRows={}", productIds, includedRows);

		return productIds.get(0);
	}

	@lombok.Builder(builderClassName = "AttributeSubRowBuilder", builderMethodName = "attributeSubRowBuilder")
	private MaterialCockpitRow(
			final int productId,
			final Timestamp date,
			final DimensionSpecGroup dimensionGroup,
			final BigDecimal pmmQtyPromised,
			final BigDecimal qtyReservedSale,
			final BigDecimal qtyReservedPurchase,
			final BigDecimal qtyMaterialentnahme,
			final BigDecimal qtyRequiredForProduction,
			final BigDecimal qtyAvailableToPromise,
			final BigDecimal qtyOnHandStock,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds)
	{
		this.rowType = DefaultRowType.Line;

		final String dimensionGroupName = dimensionGroup.getGroupName().translate(Env.getAD_Language());

		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"attributes",
				date,
				productId,
				dimensionGroupName));

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitConstants.WINDOWID_MaterialCockpitView,
				documentId);

		final I_M_Product product = load(productId, I_M_Product.class);
		this.productValue = product.getValue();
		this.productName = product.getName();
		this.productCategoryOrSubRowName = dimensionGroupName;

		this.productId = productId;
		this.date = date;

		this.includedRows = ImmutableList.of();

		this.pmmQtyPromised = Util.coalesce(pmmQtyPromised, BigDecimal.ZERO);
		this.qtyReservedSale = Util.coalesce(qtyReservedSale, BigDecimal.ZERO);
		this.qtyReservedPurchase = Util.coalesce(qtyReservedPurchase, BigDecimal.ZERO);
		this.qtyMaterialentnahme = Util.coalesce(qtyMaterialentnahme, BigDecimal.ZERO);
		this.qtyRequiredForProduction = Util.coalesce(qtyRequiredForProduction, BigDecimal.ZERO);
		this.qtyOnHandEstimate = null;
		this.qtyOnHandStock = qtyOnHandStock;
		this.qtyAvailableToPromise = Util.coalesce(qtyAvailableToPromise, BigDecimal.ZERO);

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	@lombok.Builder(builderClassName = "CountingSubRowBuilder", builderMethodName = "countingSubRowBuilder")
	private MaterialCockpitRow(
			final int productId,
			final Timestamp date,
			final int plantId,
			final BigDecimal qtyOnHandEstimate,
			final BigDecimal qtyOnHandStock,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds)
	{
		this.rowType = DefaultRowType.Line;

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
				MaterialCockpitConstants.WINDOWID_MaterialCockpitView,
				documentId);

		final I_M_Product product = load(productId, I_M_Product.class);
		this.productValue = product.getValue();
		this.productName = product.getName();
		this.productCategoryOrSubRowName = plantName;

		this.productId = productId;
		this.date = date;

		this.includedRows = ImmutableList.of();

		this.pmmQtyPromised = null;
		this.qtyReservedSale = null;
		this.qtyReservedPurchase = null;
		this.qtyMaterialentnahme = null;
		this.qtyRequiredForProduction = null;
		this.qtyOnHandEstimate = Util.coalesce(qtyOnHandEstimate, BigDecimal.ZERO);
		this.qtyOnHandStock = qtyOnHandStock;
		this.qtyAvailableToPromise = null;

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
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		if (_fieldNameAndJsonValues == null)
		{
			_fieldNameAndJsonValues = ViewColumnHelper.extractJsonMap(this);
		}
		return _fieldNameAndJsonValues;
	}

}
