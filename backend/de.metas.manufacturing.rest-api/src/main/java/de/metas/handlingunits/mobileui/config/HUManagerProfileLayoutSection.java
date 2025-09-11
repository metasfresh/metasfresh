package de.metas.handlingunits.mobileui.config;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_MobileUI_HUManager_LayoutSection;

@RequiredArgsConstructor
@Getter
public enum HUManagerProfileLayoutSection implements ReferenceListAwareEnum
{
	DisplayName(X_MobileUI_HUManager_LayoutSection.LAYOUTSECTION_DisplayName),
	QRCode(X_MobileUI_HUManager_LayoutSection.LAYOUTSECTION_QRCode),
	EANCode(X_MobileUI_HUManager_LayoutSection.LAYOUTSECTION_EANCode),
	Locator(X_MobileUI_HUManager_LayoutSection.LAYOUTSECTION_Locator),
	HUStatus(X_MobileUI_HUManager_LayoutSection.LAYOUTSECTION_HUStatus),
	ClearanceStatus(X_MobileUI_HUManager_LayoutSection.LAYOUTSECTION_ClearanceStatus),
	Product(X_MobileUI_HUManager_LayoutSection.LAYOUTSECTION_Product),
	Qty(X_MobileUI_HUManager_LayoutSection.LAYOUTSECTION_Qty),
	Attributes(X_MobileUI_HUManager_LayoutSection.LAYOUTSECTION_Attributes),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<HUManagerProfileLayoutSection> index = ReferenceListAwareEnums.index(values());

	private final String code;

	public static HUManagerProfileLayoutSection ofCode(@NonNull final String code) {return index.ofCode(code);}
}
