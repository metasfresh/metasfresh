package de.metas.frontend_testing;

import com.google.common.collect.ImmutableMap;
import de.metas.frontend_testing.masterdata.CreateMasterdataCommandSupportingServices;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataRequest;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import de.metas.security.permissions2.PermissionNotGrantedException;
import de.metas.util.Services;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import de.metas.organization.OrgId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FrontendTestingRestControllerTest
{
	private FrontendTestingRestController controller;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		controller = new FrontendTestingRestController(new UserAuthTokenFilterConfiguration(), new CreateMasterdataCommandSupportingServices());
	}

	private static void setEnabled(final String value)
	{
		Services.get(ISysConfigBL.class).setValue("frontend.testing", value, ClientId.SYSTEM, OrgId.ANY);
	}

	@Test
	void createMasterdata_refused_whenSysconfigNotSet()
	{
		assertThatThrownBy(() -> controller.createMasterdata(JsonCreateMasterdataRequest.builder().build()))
				.isInstanceOf(PermissionNotGrantedException.class);
	}

	@Test
	void createMasterdata_refused_whenSysconfigIsN()
	{
		setEnabled("N");
		assertThatThrownBy(() -> controller.createMasterdata(JsonCreateMasterdataRequest.builder().build()))
				.isInstanceOf(PermissionNotGrantedException.class);
	}

	@Test
	void createMasterdata_allowed_whenSysconfigIsY()
	{
		setEnabled("Y");
		final JsonCreateMasterdataResponse response = controller.createMasterdata(JsonCreateMasterdataRequest.builder()
				.sysconfigs(ImmutableMap.of("some.test.sysconfig", "abc"))
				.build());
		assertThat(response).isNotNull();
	}
}
