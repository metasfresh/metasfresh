package de.metas.inoutcandidate.qty_reservation;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.QtyTU;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.project.ProjectValue;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

@Value
@Builder
public class MaterialCockpitV2RowVO
{
	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@NonNull SupplyType supplyType;
	@NonNull AvailabilityType availabilityType;
	@Nullable Instant datePromised;
	@Nullable BPartnerId vendorBPartnerId;
	@Nullable AttributesKey attributesKey;
	@Nullable ProjectValue projectValue;
	@NonNull QtyTU qtyTU;
	@NonNull Quantity qtyStock;

	public Quantity computeQtyCUToReserve(@NonNull final QtyTU qtyTUToReserve)
	{
		if (!qtyTUToReserve.isPositive()) {return qtyStock.toZero();}
		if (!qtyTU.isPositive() || qtyStock.signum() <= 0) {return qtyStock.toZero();}

		return getQtyCUsPerTU().multiply(qtyTUToReserve.toInt());
	}

	private Quantity getQtyCUsPerTU()
	{
		if (!qtyTU.isPositive()) {throw new AdempiereException("qtyTU shall be positive");}
		return qtyStock.divide(qtyTU.toInt());
	}

	public boolean isAvailableForReservation()
	{
		return availabilityType == AvailabilityType.AVAILABLE
				&& qtyTU.isPositive();
	}

	public boolean isAvailableForUnReservation()
	{
		return availabilityType == AvailabilityType.RESERVED
				&& qtyTU.isPositive();
	}

	/**
	 * Constructs a {@link MaterialCockpitV2RowVO} from a JDBC {@link ResultSet},
	 * reading columns from the {@code QtyDemand_QtySupply_V} (or SE203_MaterialCockpit_V) view.
	 *
	 * <p>Expected columns: M_Product_ID, M_Warehouse_ID, C_UOM_ID, SupplyType, AvailabilityStatus,
	 * DatePromised, C_BPartner_Vendor_ID, AttributesKey, ProjectValue, QtyTU, QtyStock.
	 */
	public static MaterialCockpitV2RowVO ofResultSet(@NonNull final ResultSet rs) throws SQLException
	{
		final int productId = rs.getInt("M_Product_ID");
		final int warehouseId = rs.getInt("M_Warehouse_ID");
		final int uomId = rs.getInt("C_UOM_ID");

		final String supplyTypeStr = rs.getString("SupplyType");
		final String availabilityStatusStr = rs.getString("AvailabilityStatus");

		final Timestamp datePromisedTS = rs.getTimestamp("DatePromised");
		final int vendorBPartnerId = rs.getInt("C_BPartner_Vendor_ID");
		final String attributesKeyStr = rs.getString("AttributesKey");
		final String projectValueStr = rs.getString("ProjectValue");

		final BigDecimal qtyTUBD = rs.getBigDecimal("QtyTU");
		final BigDecimal qtyStockBD = rs.getBigDecimal("QtyStock");

		return MaterialCockpitV2RowVO.builder()
				.productId(ProductId.ofRepoId(productId))
				.warehouseId(WarehouseId.ofRepoId(warehouseId))
				.supplyType(supplyTypeStr != null ? SupplyType.ofCode(supplyTypeStr) : SupplyType.ON_HAND)
				.availabilityType(availabilityStatusStr != null ? AvailabilityType.ofCode(availabilityStatusStr) : AvailabilityType.AVAILABLE)
				.datePromised(datePromisedTS != null ? datePromisedTS.toInstant() : null)
				.vendorBPartnerId(BPartnerId.ofRepoIdOrNull(vendorBPartnerId))
				.attributesKey(attributesKeyStr != null && !attributesKeyStr.isEmpty() ? AttributesKey.ofString(attributesKeyStr) : null)
				.projectValue(ProjectValue.ofNullableString(projectValueStr))
				.qtyTU(QtyTU.ofBigDecimal(qtyTUBD != null ? qtyTUBD : BigDecimal.ZERO))
				.qtyStock(Quantitys.of(qtyStockBD != null ? qtyStockBD : BigDecimal.ZERO, UomId.ofRepoId(uomId)))
				.build();
	}
}
