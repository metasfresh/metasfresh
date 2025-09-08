package de.metas.inventory;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.Language;
import lombok.NonNull;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class InventoryValuationService
{
	private static final String SQL_SELECT = "SELECT * FROM de_metas_acct.report_InventoryValue("
			+ " p_DateAcct => ?,"
			+ " p_M_Product_ID => ?,"
			+ " p_M_Warehouse_ID => ?,"
			+ " p_AD_Language => ?"
			+ ")";

	public InventoryValuationResponse report(final InventoryValuationRequest request)
	{
		final String adLanguage = CoalesceUtil.coalesceNotNull(request.getAdLanguage(), Language.getBaseAD_Language());
		final ImmutableList<InventoryValue> rows = DB.retrieveRows(
				SQL_SELECT,
				new Object[] { request.getDateAcct(), request.getProductId(), request.getWarehouseId(), adLanguage },
				InventoryValuationService::retrieveInventoryValueRow
		);

		return InventoryValuationResponse.builder()
				.lines(rows)
				.build();
	}

	private static InventoryValue retrieveInventoryValueRow(final ResultSet rs) throws SQLException
	{
		return InventoryValue.builder()
				.combination(rs.getString("combination"))
				.description(rs.getString("description"))
				.activityName(rs.getString("ActivityName"))
				.warehouseName(rs.getString("WarehouseName"))
				.productValue(rs.getString("ProductValue"))
				.productName(rs.getString("ProductName"))
				.qty(rs.getBigDecimal("Qty"))
				.uomSymbol(rs.getString("UOMSymbol"))
				.accounted(retrieveAmounts(rs, "Acct"))
				.costing(retrieveAmounts(rs, "Costing"))
				.inventoryValueAcctAmt(retrieveBigDecimalOrZero(rs, "InventoryValueAcctAmt"))
				.build();
	}

	private static InventoryValue.Amounts retrieveAmounts(@NonNull final ResultSet rs, @NonNull final String prefix) throws SQLException
	{
		return InventoryValue.Amounts.builder()
				.costPrice(retrieveBigDecimalOrZero(rs, prefix + "_CostPrice"))
				.expectedAmt(retrieveBigDecimalOrZero(rs, prefix + "_ExpectedAmt"))
				.errorAmt(retrieveBigDecimalOrZero(rs, prefix + "_ErrorAmt"))
				.build();
	}

	@NonNull
	private static BigDecimal retrieveBigDecimalOrZero(final @NonNull ResultSet rs, final @NonNull String columnName) throws SQLException
	{
		return CoalesceUtil.coalesceNotNull(rs.getBigDecimal(columnName), BigDecimal.ZERO);
	}
}
