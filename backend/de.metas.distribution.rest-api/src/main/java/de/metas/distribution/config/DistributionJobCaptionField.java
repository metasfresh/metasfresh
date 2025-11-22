package de.metas.distribution.config;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_MobileUI_UserProfile_DD_CaptionItem;

@RequiredArgsConstructor
@Getter
public enum DistributionJobCaptionField implements ReferenceListAwareEnum
{
	LocatorFrom(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_LocatorFrom),
	LocatorTo(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_LocatorTo),
	WarehouseFrom(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_WarehouseFrom),
	WarehouseTo(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_WarehouseTo),
	Plant(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_Plant),
	PickDate(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_PickDate),
	Qty(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_Qty),
	ProductGTIN(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_ProductGTIN),
	ProductValueAndName(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_ProductValueAndName),
	SourceDoc(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_SourceDoc),
	Priority(X_MobileUI_UserProfile_DD_CaptionItem.FIELDNAME_Priority),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<DistributionJobCaptionField> index = ReferenceListAwareEnums.index(values());

	private final String code;

	public static DistributionJobCaptionField ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static DistributionJobCaptionField ofCodeOrName(@NonNull final String code) {return index.ofCodeOrName(code);}
}
