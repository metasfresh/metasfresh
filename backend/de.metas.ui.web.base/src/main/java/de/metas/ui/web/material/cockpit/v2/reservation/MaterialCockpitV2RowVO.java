package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.QtyTU;
import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.ui.web.view.IViewRow;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
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
	@NonNull QtyTU qtyTU;
	@NonNull Quantity qtyStock;

	private static final String COLUMNNAME_QtyTU = "QtyTU";
	private static final String COLUMNNAME_SupplyType = "SupplyType";
	private static final String COLUMNNAME_AvailabilityStatus = "AvailabilityStatus";
	private static final String COLUMNNAME_DatePromised = "DatePromised";
	private static final String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";

	@NonNull
	public static MaterialCockpitV2RowVO ofViewRow(@NonNull final IViewRow row)
	{
		return builder()
				.productId(row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Product_ID, ProductId::ofRepoId))
				.warehouseId(row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Warehouse_ID, WarehouseId::ofRepoId))
				.supplyType(extractSupplyType(row))
				.availabilityType(extractAvailabilityType(row))
				.datePromised(extractDatePromised(row))
				.vendorBPartnerId(extractVendorBPartnerId(row))
				.attributesKey(extractAttributesKey(row))
				.qtyTU(QtyTU.ofBigDecimal(row.getFieldValueAsBigDecimal(COLUMNNAME_QtyTU, BigDecimal.ZERO)))
				.qtyStock(extractQtyStock(row))
				.build();
	}

	private static Quantity extractQtyStock(@NonNull final IViewRow row)
	{
		final UomId uomId = row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_C_UOM_ID, UomId::ofRepoId);
		final BigDecimal qtyStockBD = row.getFieldValueAsBigDecimal(I_QtyDemand_QtySupply_V.COLUMNNAME_QtyStock, BigDecimal.ZERO);
		return Quantitys.of(qtyStockBD, uomId);
	}

	@NonNull
	private static SupplyType extractSupplyType(@NonNull final IViewRow row)
	{
		return row.getFieldNameAndJsonValues().getAsEnum(COLUMNNAME_SupplyType, SupplyType.class);
	}

	@NonNull
	private static AvailabilityType extractAvailabilityType(@NonNull final IViewRow row)
	{
		return row.getFieldNameAndJsonValues().getAsEnum(COLUMNNAME_AvailabilityStatus, AvailabilityType.class);
	}

	@Nullable
	private static Instant extractDatePromised(@NonNull final IViewRow row)
	{
		return row.getFieldNameAndJsonValues().getAsInstant(COLUMNNAME_DatePromised);
	}

	@Nullable
	private static BPartnerId extractVendorBPartnerId(@NonNull final IViewRow row)
	{
		final int id = row.getFieldValueAsInt(COLUMNNAME_C_BPartner_Vendor_ID, -1);
		return BPartnerId.ofRepoIdOrNull(id);
	}

	@Nullable
	private static AttributesKey extractAttributesKey(@NonNull final IViewRow row)
	{
		final Object value = row.getFieldNameAndJsonValues().get(I_QtyDemand_QtySupply_V.COLUMNNAME_AttributesKey);
		if (value instanceof String)
		{
			final String str = (String)value;
			return str.isEmpty() ? null : AttributesKey.ofString(str);
		}
		else if (value instanceof AttributesKey)
		{
			return (AttributesKey)value;
		}
		else
		{
			return null;
		}
	}

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

}

