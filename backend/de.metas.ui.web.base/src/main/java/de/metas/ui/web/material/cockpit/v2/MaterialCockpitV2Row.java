package de.metas.ui.web.material.cockpit.v2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@ToString
@EqualsAndHashCode(of = "documentId")
public class MaterialCockpitV2Row implements IViewRow
{
	@Getter
	private final DocumentId documentId;
	private final DocumentPath documentPath;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text,
			captionKey = "ProductValue",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10) })
	private final String productValue;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text,
			captionKey = "ProductName",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20) })
	private final String productName;

	@ViewColumn(fieldName = "M_Product_Category_ID",
			widgetType = DocumentFieldWidgetType.Lookup,
			captionKey = "M_Product_Category_ID",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30) })
	private final LookupValue productCategory;

	@ViewColumn(fieldName = "M_Warehouse_ID",
			widgetType = DocumentFieldWidgetType.Lookup,
			captionKey = "M_Warehouse_ID",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40) })
	private final LookupValue warehouse;

	@ViewColumn(fieldName = "SupplyType",
			widgetType = DocumentFieldWidgetType.Text,
			captionKey = "SupplyType",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50) })
	private final String supplyType;

	@ViewColumn(fieldName = "C_BPartner_Vendor_ID",
			widgetType = DocumentFieldWidgetType.Lookup,
			captionKey = "C_BPartner_Vendor_ID",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60) })
	private final LookupValue vendor;

	@ViewColumn(fieldName = "DatePromised",
			widgetType = DocumentFieldWidgetType.Date,
			captionKey = "DatePromised",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70) })
	private final java.util.Date datePromised;

	@ViewColumn(fieldName = "QtyOnHand",
			widgetType = DocumentFieldWidgetType.Quantity,
			captionKey = "QtyOnHand",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 80) })
	private final BigDecimal qtyOnHand;

	@ViewColumn(fieldName = "QtyTU",
			widgetType = DocumentFieldWidgetType.Quantity,
			captionKey = "QtyTU",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 90) })
	private final BigDecimal qtyTU;

	@ViewColumn(fieldName = "QtyLU",
			widgetType = DocumentFieldWidgetType.Quantity,
			captionKey = "QtyLU",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100) })
	private final BigDecimal qtyLU;

	@ViewColumn(fieldName = "WeightNet",
			widgetType = DocumentFieldWidgetType.Quantity,
			captionKey = "WeightNet",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 110) })
	private final BigDecimal weightNet;

	@ViewColumn(fieldName = "LastCostPrice",
			widgetType = DocumentFieldWidgetType.CostPrice,
			captionKey = "LastCostPrice",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 120) })
	private final BigDecimal lastCostPrice;

	@ViewColumn(fieldName = "C_UOM_ID",
			widgetType = DocumentFieldWidgetType.Lookup,
			captionKey = "C_UOM_ID",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 130) })
	private final LookupValue uom;

	// Customer-specific columns (from SE203_MaterialCockpit_V)
	@ViewColumn(fieldName = "Kaliber",
			widgetType = DocumentFieldWidgetType.Text,
			captionKey = "Kaliber",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 140) })
	@Nullable
	private final String kaliber;

	@ViewColumn(fieldName = "Herkunft",
			widgetType = DocumentFieldWidgetType.Text,
			captionKey = "Herkunft",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 150) })
	@Nullable
	private final String herkunft;

	@ViewColumn(fieldName = "Marke",
			widgetType = DocumentFieldWidgetType.Text,
			captionKey = "Marke",
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 160) })
	@Nullable
	private final String marke;

	private final ViewRowFieldNameAndJsonValuesHolder<MaterialCockpitV2Row> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(MaterialCockpitV2Row.class);

	@Builder
	private MaterialCockpitV2Row(
			@NonNull final DocumentId documentId,
			@NonNull final String productValue,
			@NonNull final String productName,
			@Nullable final LookupValue productCategory,
			@Nullable final LookupValue warehouse,
			@NonNull final String supplyType,
			@Nullable final LookupValue vendor,
			@Nullable final java.util.Date datePromised,
			@Nullable final BigDecimal qtyOnHand,
			@Nullable final BigDecimal qtyTU,
			@Nullable final BigDecimal qtyLU,
			@Nullable final BigDecimal weightNet,
			@Nullable final BigDecimal lastCostPrice,
			@Nullable final LookupValue uom,
			@Nullable final String kaliber,
			@Nullable final String herkunft,
			@Nullable final String marke)
	{
		this.documentId = documentId;
		this.documentPath = DocumentPath.rootDocumentPath(MaterialCockpitV2Util.WINDOWID_MaterialCockpitV2, documentId);
		this.productValue = productValue;
		this.productName = productName;
		this.productCategory = productCategory;
		this.warehouse = warehouse;
		this.supplyType = supplyType;
		this.vendor = vendor;
		this.datePromised = datePromised;
		this.qtyOnHand = qtyOnHand;
		this.qtyTU = qtyTU;
		this.qtyLU = qtyLU;
		this.weightNet = weightNet;
		this.lastCostPrice = lastCostPrice;
		this.uom = uom;
		this.kaliber = kaliber;
		this.herkunft = herkunft;
		this.marke = marke;
	}

	@Override
	public DocumentId getId()
	{
		return documentId;
	}

	@Override
	public IViewRowType getType()
	{
		return DefaultRowType.Row;
	}

	@Override
	public boolean isProcessed()
	{
		return true; // read-only view
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
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
	public List<? extends IViewRow> getIncludedRows()
	{
		return ImmutableList.of();
	}
}
