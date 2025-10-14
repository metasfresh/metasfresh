package de.metas.inventory.mobileui.job.qrcode;

import de.metas.handlingunits.HuId;
import de.metas.i18n.ITranslatableString;
import de.metas.inventory.InventoryLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.warehouse.LocatorId;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class ResolveHUResponse
{
	@NonNull InventoryLineId lineId;
	@NonNull LocatorId locatorId;
	@Nullable HuId huId;
	@NonNull ProductId productId;
	@NonNull Quantity qtyBooked;

	@NonNull List<Attribute> attributes;

	@Value
	@Builder
	public static class Attribute
	{
		@NonNull AttributeCode attributeCode;
		@NonNull ITranslatableString displayName;
		@NonNull AttributeValueType valueType;
		@Nullable Object value;

		public String getValueAsString()
		{
			return value != null ? value.toString() : null;
		}

		public BigDecimal getValueAsBigDecimal()
		{
			return value != null ? NumberUtils.asBigDecimal(value) : null;
		}

		public LocalDate getValueAsLocalDate()
		{
			return value == null ? null : TimeUtil.asLocalDate(value);
		}
	}
}
