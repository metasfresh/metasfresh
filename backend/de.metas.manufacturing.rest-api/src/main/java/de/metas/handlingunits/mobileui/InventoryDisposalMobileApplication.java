package de.metas.handlingunits.mobileui;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.service.MobileApplication;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class InventoryDisposalMobileApplication implements MobileApplication
{
	private static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("inventoryDisposal");
	private static final AdMessageKey MSG_Caption = AdMessageKey.of("mobileui.inventoryDisposal.appName");
	private static final MobileApplicationInfo APPLICATION_INFO = MobileApplicationInfo.builder()
			.id(APPLICATION_ID)
			.caption(TranslatableStrings.adMessage(MSG_Caption))
			.build();

	@Override
	public @NonNull MobileApplicationInfo getApplicationInfo()
	{
		return APPLICATION_INFO;
	}
}
