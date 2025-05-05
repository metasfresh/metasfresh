package de.metas.ui.web.split_shipment;

import de.metas.i18n.TranslatableStrings;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.Quantity;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewHeaderProperty;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

@Value
@Builder
class ShipmentScheduleInfo
{
	@NonNull String shipBPartnerName;
	@NonNull String billBPartnerName;
	@Nullable String salesOrderDocumentNo;
	@NonNull String productName;
	@NonNull Quantity qtyToDeliver;
	boolean readonly;

	public I_C_UOM getUom() {return qtyToDeliver.getUOM();}

	public ViewHeaderProperties toViewHeaderProperties()
	{
		final ArrayList<ViewHeaderProperty> entries = new ArrayList<>();

		if (!Check.isBlank(salesOrderDocumentNo))
		{
			entries.add(ViewHeaderProperty.builder()
					.caption(TranslatableStrings.adElementOrMessage(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID))
					.value(salesOrderDocumentNo)
					.build());
		}

		entries.add(ViewHeaderProperty.builder()
				.caption(TranslatableStrings.adElementOrMessage(I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID))
				.value(shipBPartnerName)
				.build());
		if (!Objects.equals(billBPartnerName, shipBPartnerName))
		{
			entries.add(ViewHeaderProperty.builder()
					.caption(TranslatableStrings.adElementOrMessage(I_M_ShipmentSchedule.COLUMNNAME_Bill_BPartner_ID))
					.value(billBPartnerName)
					.build());
		}

		entries.add(ViewHeaderProperty.builder()
				.caption(TranslatableStrings.adElementOrMessage(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID))
				.value(productName)
				.build());

		entries.add(ViewHeaderProperty.builder()
				.caption(TranslatableStrings.adElementOrMessage(I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver))
				.value(TranslatableStrings.quantity(qtyToDeliver.toBigDecimal(), qtyToDeliver.getUOMSymbol()))
				.build());

		return ViewHeaderProperties.ofEntries(entries);
	}
}
