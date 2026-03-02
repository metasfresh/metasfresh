package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.QtyTU;
import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.ui.web.view.IViewRow;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
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
	@NonNull UomId uomId;
	@NonNull SupplyType supplyType;
	@Nullable Instant datePromised;
	@Nullable BPartnerId vendorBPartnerId;
	/** Pipe-separated attribute key matching the cockpit view's computed key (e.g., via SE203_MaterialCockpit_ComputeAttributesKey).
	 * Stored as-is into M_QtyReservation.AttributesKey so reservations match back to cockpit rows. */
	@Nullable AttributesKey attributesKey;
	@NonNull QtyTU qtyTU;
	@NonNull BigDecimal qtyStock;

	private static final String COLUMNNAME_QtyTU = "QtyTU";
	private static final String COLUMNNAME_SupplyType = "SupplyType";
	private static final String COLUMNNAME_DatePromised = "DatePromised";
	private static final String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";

	@NonNull
	public static MaterialCockpitV2RowVO ofViewRow(@NonNull final IViewRow row)
	{
		return builder()
				.productId(row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Product_ID, ProductId::ofRepoId))
				.warehouseId(row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Warehouse_ID, WarehouseId::ofRepoId))
				.uomId(row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_C_UOM_ID, UomId::ofRepoId))
				.supplyType(extractSupplyType(row))
				.datePromised(extractDatePromised(row))
				.vendorBPartnerId(extractVendorBPartnerId(row))
				.attributesKey(extractAttributesKey(row))
				.qtyTU(QtyTU.ofBigDecimal(row.getFieldValueAsBigDecimal(COLUMNNAME_QtyTU, BigDecimal.ZERO)))
				.qtyStock(row.getFieldValueAsBigDecimal(I_QtyDemand_QtySupply_V.COLUMNNAME_QtyStock, BigDecimal.ZERO))
				.build();
	}

	@NonNull
	private static SupplyType extractSupplyType(@NonNull final IViewRow row)
	{
		final Object value = row.getFieldNameAndJsonValues().get(COLUMNNAME_SupplyType);
		return value instanceof String ? SupplyType.ofNullableCode((String)value) : SupplyType.ON_HAND;
	}

	@Nullable
	private static Instant extractDatePromised(@NonNull final IViewRow row)
	{
		final Object value = row.getFieldNameAndJsonValues().get(COLUMNNAME_DatePromised);
		if (value instanceof java.sql.Timestamp)
		{
			return ((java.sql.Timestamp)value).toInstant();
		}
		if (value instanceof Instant)
		{
			return (Instant)value;
		}
		return null;
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
		return null;
	}
}
