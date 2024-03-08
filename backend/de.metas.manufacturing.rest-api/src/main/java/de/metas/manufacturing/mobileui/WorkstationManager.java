package de.metas.manufacturing.mobileui;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.service.MobileApplication;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class WorkstationManager implements MobileApplication
{
	private static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("workstationManager");
	private static final AdMessageKey MSG_Caption = AdMessageKey.of("mobileui.workstationManager.appName");
	public static final MobileApplicationInfo APPLICATION_INFO = MobileApplicationInfo.builder()
			.id(APPLICATION_ID)
			.caption(TranslatableStrings.adMessage(MSG_Caption))
			.showInMainMenu(false)
			.build();

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}

	@Override
	public @NonNull MobileApplicationInfo getApplicationInfo(@NonNull UserId loggedUserId) {return APPLICATION_INFO;}
}
