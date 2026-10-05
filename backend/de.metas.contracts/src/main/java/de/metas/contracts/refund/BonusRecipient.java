package de.metas.contracts.refund;

import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * The partner that a refund (bonus) is issued to: the partner that the goods are shipped to, or the one that is invoiced.
 */
public enum BonusRecipient implements ReferenceListAwareEnum
{
	INVOICE_PARTNER(X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner),
	SHIPMENT_PARTNER(X_C_Flatrate_RefundConfig.BONUSRECIPIENT_ShipmentPartner);

	public static final int AD_Reference_ID = X_C_Flatrate_RefundConfig.BONUSRECIPIENT_AD_Reference_ID;

	private static final ReferenceListAwareEnums.ValuesIndex<BonusRecipient> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	BonusRecipient(@NonNull final String code)
	{
		this.code = code;
	}

	public static BonusRecipient ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static BonusRecipient ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}
}
