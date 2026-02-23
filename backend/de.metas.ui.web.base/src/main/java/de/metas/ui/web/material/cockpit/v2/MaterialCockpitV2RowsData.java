package de.metas.ui.web.material.cockpit.v2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaterialCockpitV2RowsData implements IRowsData<MaterialCockpitV2Row>
{
	private final String sql;
	private final List<Object> sqlParams;
	private ImmutableMap<DocumentId, MaterialCockpitV2Row> rows;

	public MaterialCockpitV2RowsData(@NonNull final String sql, @NonNull final List<Object> sqlParams)
	{
		this.sql = sql;
		this.sqlParams = ImmutableList.copyOf(sqlParams);
		this.rows = loadRows();
	}

	@Override
	public Map<DocumentId, MaterialCockpitV2Row> getDocumentId2TopLevelRows()
	{
		return rows;
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(@NonNull final TableRecordReferenceSet recordRefs)
	{
		// For a SQL view, any change invalidates everything
		return DocumentIdsSelection.ALL;
	}

	@Override
	public void invalidateAll()
	{
		rows = loadRows();
	}

	private ImmutableMap<DocumentId, MaterialCockpitV2Row> loadRows()
	{
		final List<MaterialCockpitV2Row> rowsList = DB.retrieveRows(sql, sqlParams, MaterialCockpitV2RowsData::toRow);

		final ImmutableMap.Builder<DocumentId, MaterialCockpitV2Row> builder = ImmutableMap.builder();
		for (final MaterialCockpitV2Row row : rowsList)
		{
			builder.put(row.getId(), row);
		}
		return builder.build();
	}

	@NonNull
	private static MaterialCockpitV2Row toRow(@NonNull final ResultSet rs) throws SQLException
	{
		final int viewId = rs.getInt("QtyDemand_QtySupply_V_ID");
		final DocumentId documentId = DocumentId.of(viewId);

		final int productId = rs.getInt("M_Product_ID");
		final int productCategoryId = rs.getInt("M_Product_Category_ID");
		final int warehouseId = rs.getInt("M_Warehouse_ID");
		final int uomId = rs.getInt("C_UOM_ID");
		final int vendorId = rs.getInt("C_BPartner_Vendor_ID");

		return MaterialCockpitV2Row.builder()
				.documentId(documentId)
				.productValue(rs.getString("ProductValue"))
				.productName(rs.getString("ProductName"))
				.productCategory(toLookupValueOrNull(productCategoryId, "M_Product_Category"))
				.warehouse(toLookupValueOrNull(warehouseId, "M_Warehouse"))
				.supplyType(rs.getString("SupplyType"))
				.vendor(toLookupValueOrNull(vendorId, "C_BPartner"))
				.datePromised(rs.getTimestamp("DatePromised"))
				.qtyOnHand(rs.getBigDecimal("QtyOnHand"))
				.qtyTU(rs.getBigDecimal("QtyTU"))
				.qtyLU(rs.getBigDecimal("QtyLU"))
				.weightNet(rs.getBigDecimal("WeightNet"))
				.lastCostPrice(rs.getBigDecimal("LastCostPrice"))
				.uom(toLookupValueOrNull(uomId, "C_UOM"))
				.kaliber(getStringOrNull(rs, "Kaliber"))
				.herkunft(getStringOrNull(rs, "Herkunft"))
				.marke(getStringOrNull(rs, "Marke"))
				.build();
	}

	@Nullable
	private static LookupValue toLookupValueOrNull(final int id, @NonNull final String tableName)
	{
		if (id <= 0)
		{
			return null;
		}
		// Use simple lookup; the WebUI will resolve display names
		return IntegerLookupValue.of(id, tableName + "#" + id);
	}

	@Nullable
	private static String getStringOrNull(@NonNull final ResultSet rs, @NonNull final String columnName)
	{
		try
		{
			return rs.getString(columnName);
		}
		catch (final SQLException e)
		{
			// Column not present in this view variant (e.g. base view has no customer columns)
			return null;
		}
	}
}
