package de.metas.frontend_testing.masterdata;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Component;

/**
 * Services needed by the masterdata commands; grows together with the supported masterdata types.
 */
@Component
public class CreateMasterdataCommandSupportingServices
{
	@NonNull public final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
}
