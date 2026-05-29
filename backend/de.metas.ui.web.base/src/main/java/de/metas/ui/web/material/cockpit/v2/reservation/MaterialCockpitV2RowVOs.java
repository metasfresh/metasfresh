package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.QtyTU;
import de.metas.inoutcandidate.qty_reservation.MaterialCockpitV2RowVO;
import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.project.ProjectValue;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.ui.web.view.IViewRow;
import de.metas.uom.UomId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@UtilityClass
public class MaterialCockpitV2RowVOs
{
	private static final String COLUMNNAME_QtyTU = "QtyTU";
	private static final String COLUMNNAME_SupplyType = "SupplyType";
	private static final String COLUMNNAME_AvailabilityStatus = "AvailabilityStatus";
	private static final String COLUMNNAME_DatePromised = "DatePromised";
	private static final String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";
	private static final String COLUMNNAME_ProjectValue = "ProjectValue";

	@NonNull
	public static MaterialCockpitV2RowVO ofViewRow(@NonNull final IViewRow row)
	{
		return MaterialCockpitV2RowVO.builder()
				.productId(row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Product_ID, ProductId::ofRepoId))
				.warehouseId(row.getFieldValueAsRepoId(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Warehouse_ID, WarehouseId::ofRepoId))
				.supplyType(extractSupplyType(row))
				.availabilityType(extractAvailabilityType(row))
				.datePromised(extractDatePromised(row))
				.vendorBPartnerId(extractVendorBPartnerId(row))
				.attributesKey(extractAttributesKey(row))
				.projectValue(extractProjectValue(row))
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
	private static de.metas.inoutcandidate.qty_reservation.SupplyType extractSupplyType(@NonNull final IViewRow row)
	{
		return row.getFieldNameAndJsonValues().getAsEnum(COLUMNNAME_SupplyType, de.metas.inoutcandidate.qty_reservation.SupplyType.class);
	}

	@NonNull
	private static de.metas.inoutcandidate.qty_reservation.AvailabilityType extractAvailabilityType(@NonNull final IViewRow row)
	{
		return row.getFieldNameAndJsonValues().getAsEnum(COLUMNNAME_AvailabilityStatus, de.metas.inoutcandidate.qty_reservation.AvailabilityType.class);
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

	@Nullable
	private static ProjectValue extractProjectValue(@NonNull final IViewRow row)
	{
		return ProjectValue.ofNullableString(row.getFieldNameAndJsonValues().getAsString(COLUMNNAME_ProjectValue));
	}

}
