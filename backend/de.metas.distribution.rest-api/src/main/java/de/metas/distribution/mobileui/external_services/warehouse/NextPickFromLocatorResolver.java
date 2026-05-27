package de.metas.distribution.mobileui.external_services.warehouse;

import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

public interface NextPickFromLocatorResolver
{
	AdMessageKey MSG_NO_ALTERNATIVE = AdMessageKey.of("MobileUI_DDOrder_SwitchPickFromLocator_NoAlternative");

	@NonNull LocatorId resolveNext(@NonNull LocatorId currentLocatorId);
}
