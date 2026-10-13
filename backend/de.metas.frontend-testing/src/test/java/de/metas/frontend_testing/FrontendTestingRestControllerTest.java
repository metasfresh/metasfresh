package de.metas.frontend_testing;

import com.google.common.collect.ImmutableMap;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.frontend_testing.masterdata.CreateMasterdataCommandSupportingServices;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataRequest;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import de.metas.frontend_testing.masterdata.inlinefilter.AdMetadataCacheInvalidator;
import de.metas.frontend_testing.masterdata.inlinefilter.InlineFilterRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.security.permissions2.PermissionNotGrantedException;
import de.metas.util.Services;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class FrontendTestingRestControllerTest
{
	private static final String SYSCONFIG_Enabled = "frontend.testing";

	private ISysConfigBL sysConfigBL;
	private UserAuthTokenFilterConfiguration userAuthTokenFilterConfiguration;
	private FrontendTestingRestController controller;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		sysConfigBL = Services.get(ISysConfigBL.class);
		userAuthTokenFilterConfiguration = mock(UserAuthTokenFilterConfiguration.class);
		controller = new FrontendTestingRestController(userAuthTokenFilterConfiguration, new CreateMasterdataCommandSupportingServices(
				new ExternalReferenceRepository(Services.get(IQueryBL.class), new ExternalSystems(), new ExternalReferenceTypes()),
				new ExternalReferenceTypes(),
				new ExternalSystems(),
				new InlineFilterRepository(),
				new AdMetadataCacheInvalidator()));
	}

	private void setEnabled(final String value)
	{
		sysConfigBL.setValue(SYSCONFIG_Enabled, value, ClientId.SYSTEM, OrgId.ANY);
	}

	private static JsonCreateMasterdataRequest requestWithSysconfig()
	{
		return JsonCreateMasterdataRequest.builder()
				.sysconfigs(ImmutableMap.of("x", "1"))
				.build();
	}

	@Test
	void createMasterdata_refused_whenSysconfigNotSet()
	{
		assertThatThrownBy(() -> controller.createMasterdata(requestWithSysconfig()))
				.isInstanceOf(PermissionNotGrantedException.class);
		assertThat(sysConfigBL.getValue("x", ClientAndOrgId.MAIN_ORG)).isNull();
	}

	@Test
	void createMasterdata_refused_whenSysconfigIsN()
	{
		setEnabled("N");
		assertThatThrownBy(() -> controller.createMasterdata(requestWithSysconfig()))
				.isInstanceOf(PermissionNotGrantedException.class);
		assertThat(sysConfigBL.getValue("x", ClientAndOrgId.MAIN_ORG)).isNull();
	}

	@Test
	void setSysconfigs_refused_whenSysconfigNotSet()
	{
		assertThatThrownBy(() -> controller.setSysconfigs(ImmutableMap.of("x", "1")))
				.isInstanceOf(PermissionNotGrantedException.class);
		assertThat(sysConfigBL.getValue("x", ClientAndOrgId.MAIN_ORG)).isNull();
	}

	@Test
	void setSysconfigs_refused_whenSysconfigIsN()
	{
		setEnabled("N");
		assertThatThrownBy(() -> controller.setSysconfigs(ImmutableMap.of("x", "1")))
				.isInstanceOf(PermissionNotGrantedException.class);
		assertThat(sysConfigBL.getValue("x", ClientAndOrgId.MAIN_ORG)).isNull();
	}

	@Test
	void createMasterdata_writesSysconfigs_andReportsPreviousValues_whenSysconfigIsY()
	{
		setEnabled("Y");
		sysConfigBL.setValue("existing", "old", ClientId.METASFRESH, OrgId.MAIN);

		final HashMap<String, String> sysconfigs = new HashMap<>();
		sysconfigs.put("existing", "new");
		sysconfigs.put("brandNew", "1");
		final JsonCreateMasterdataResponse response = controller.createMasterdata(JsonCreateMasterdataRequest.builder()
				.sysconfigs(sysconfigs)
				.build());

		assertThat(sysConfigBL.getValue("existing", ClientAndOrgId.MAIN_ORG)).isEqualTo("new");
		assertThat(sysConfigBL.getValue("brandNew", ClientAndOrgId.MAIN_ORG)).isEqualTo("1");
		final HashMap<String, String> expectedPrevious = new HashMap<>();
		expectedPrevious.put("existing", "old");
		expectedPrevious.put("brandNew", null);
		assertThat(response.getPreviousSysconfigs()).isEqualTo(expectedPrevious);
	}

	@Test
	void setSysconfigs_nullValueRejected_andNothingWritten_whenSysconfigIsY()
	{
		setEnabled("Y");
		final HashMap<String, String> sysconfigs = new HashMap<>();
		sysconfigs.put("x", "1");
		sysconfigs.put("y", null);

		assertThatThrownBy(() -> controller.setSysconfigs(sysconfigs))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("`y`");
		assertThat(sysConfigBL.getValue("x", ClientAndOrgId.MAIN_ORG)).isNull();
	}

	@Test
	void postConstruct_registersUnauthenticatedPath()
	{
		controller.postConstruct();

		verify(userAuthTokenFilterConfiguration).doNotAuthenticatePathsContaining(FrontendTestingRestController.ENDPOINT);
	}
}
