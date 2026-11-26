package de.metas.frontend_testing.masterdata.sysconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.ModelValidator;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RestoreSysconfigsCommandTest
{
	private static final String NAME = "RestoreSysconfigsCommandTest.sysconfig";
	private static final String NAME_FAILING_TO_SAVE = "RestoreSysconfigsCommandTest.failingToSave";

	private ISysConfigBL sysConfigBL;
	private ISysConfigDAO sysConfigDAO;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		sysConfigBL = Services.get(ISysConfigBL.class);
		sysConfigDAO = Services.get(ISysConfigDAO.class);
	}

	@Test
	public void systemSysconfig_restoreOfPreviousValues_shouldRestoreTheSystemRow()
	{
		sysConfigBL.setValue(NAME, "old", ClientAndOrgId.SYSTEM.getClientId(), ClientAndOrgId.SYSTEM.getOrgId());

		final ImmutableList<JsonSysconfig> previous = setSystemSysconfig("new");
		assertThat(previous).containsExactly(JsonSysconfig.of(NAME, "old", ClientAndOrgId.SYSTEM));
		assertThat(sysConfigBL.getValue(NAME, ClientAndOrgId.SYSTEM)).isEqualTo("new");

		restore(previous);
		assertThat(sysConfigBL.getValue(NAME, ClientAndOrgId.SYSTEM)).isEqualTo("old");
	}

	@Test
	public void systemSysconfig_restoreOfMissingRow_shouldDeleteTheRow()
	{
		final ImmutableList<JsonSysconfig> previous = setSystemSysconfig("new");
		assertThat(previous).containsExactly(JsonSysconfig.of(NAME, null, ClientAndOrgId.SYSTEM));

		restore(previous);
		assertThat(sysConfigDAO.getValueOfExactRow(NAME, ClientAndOrgId.SYSTEM)).isEmpty();
		assertThat(sysConfigBL.getValue(NAME, "default")).isEqualTo("default");
	}

	@Test
	public void systemSysconfig_rowWithNoValueMarker_shouldBeRestoredAsSuch()
	{
		sysConfigDAO.setValue(NAME, null, ClientAndOrgId.SYSTEM); // stores the no-value marker "-"

		final ImmutableList<JsonSysconfig> previous = setSystemSysconfig("new");
		assertThat(previous).containsExactly(JsonSysconfig.of(NAME, "-", ClientAndOrgId.SYSTEM));

		restore(previous);
		assertThat(sysConfigDAO.getValueOfExactRow(NAME, ClientAndOrgId.SYSTEM)).contains("-");
	}

	@Test
	public void systemSysconfig_capturesTheExactRow_notTheValueInEffectThere()
	{
		sysConfigBL.setValue(NAME, "main", ClientAndOrgId.MAIN.getClientId(), ClientAndOrgId.MAIN.getOrgId());

		final ImmutableList<JsonSysconfig> previous = setSystemSysconfig("new");

		assertThat(previous).containsExactly(JsonSysconfig.of(NAME, null, ClientAndOrgId.SYSTEM));
	}

	@Test
	public void callAndRestoreOnFailure_whenAWriteFails_shouldRestoreTheRowsWrittenBeforeIt()
	{
		sysConfigBL.setValue(NAME, "old", ClientAndOrgId.SYSTEM.getClientId(), ClientAndOrgId.SYSTEM.getOrgId());
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new AD_SysConfig(NAME_FAILING_TO_SAVE));

		final LinkedHashMap<String, String> sysconfigs = new LinkedHashMap<>();
		sysconfigs.put(NAME, "new");
		sysconfigs.put(NAME_FAILING_TO_SAVE, "x");
		final PreviousSysconfigsCollector collector = new PreviousSysconfigsCollector();

		assertThatThrownBy(() -> collector.callAndRestoreOnFailure(() -> {
			SystemSysconfigCommand.builder()
					.systemSysconfigs(sysconfigs)
					.previousSysconfigsCollector(collector)
					.build()
					.execute();
			return null;
		})).hasMessageContaining(NAME_FAILING_TO_SAVE);

		assertThat(sysConfigDAO.getValueOfExactRow(NAME, ClientAndOrgId.SYSTEM)).contains("old");
	}

	@Test
	public void restore_shouldWriteExactlyTheGivenRow()
	{
		sysConfigBL.setValue(NAME, "system", ClientAndOrgId.SYSTEM.getClientId(), ClientAndOrgId.SYSTEM.getOrgId());

		restore(ImmutableList.of(JsonSysconfig.of(NAME, "main", ClientAndOrgId.MAIN)));

		assertThat(sysConfigBL.getValue(NAME, ClientAndOrgId.SYSTEM)).isEqualTo("system");
		assertThat(sysConfigBL.getValue(NAME, ClientAndOrgId.MAIN)).isEqualTo("main");
	}

	@Test
	public void restore_ofARowThatDidNotExist_shouldRemoveTheRowSoThatLowerLevelsShowAgain()
	{
		sysConfigBL.setValue(NAME, "system", ClientAndOrgId.SYSTEM.getClientId(), ClientAndOrgId.SYSTEM.getOrgId());
		sysConfigBL.setValue(NAME, "written by the test", ClientAndOrgId.MAIN.getClientId(), ClientAndOrgId.MAIN.getOrgId());

		restore(ImmutableList.of(JsonSysconfig.of(NAME, null, ClientAndOrgId.MAIN)));

		assertThat(sysConfigBL.getValue(NAME, ClientAndOrgId.MAIN)).isEqualTo("system");
	}

	@Test
	public void collector_shouldKeepTheFirstValuePerRow()
	{
		final PreviousSysconfigsCollector collector = new PreviousSysconfigsCollector();
		collector.add(JsonSysconfig.of(NAME, "first", ClientAndOrgId.SYSTEM));
		collector.add(JsonSysconfig.of(NAME, "main", ClientAndOrgId.MAIN));
		collector.add(JsonSysconfig.of(NAME, "second", ClientAndOrgId.SYSTEM));

		assertThat(collector.toList()).containsExactly(
				JsonSysconfig.of(NAME, "first", ClientAndOrgId.SYSTEM),
				JsonSysconfig.of(NAME, "main", ClientAndOrgId.MAIN));
	}

	@Test
	public void json_shouldKeepANullValue() throws Exception
	{
		final ObjectMapper objectMapper = new ObjectMapper();
		final String json = objectMapper.writeValueAsString(JsonSysconfig.of(NAME, null, ClientAndOrgId.MAIN));

		assertThat(json).isEqualTo("{\"name\":\"" + NAME + "\",\"value\":null,\"clientId\":1000000,\"orgId\":1000000}");
		assertThat(objectMapper.readValue(json, JsonSysconfig.class)).isEqualTo(JsonSysconfig.of(NAME, null, ClientAndOrgId.MAIN));
	}

	private static ImmutableList<JsonSysconfig> setSystemSysconfig(final String value)
	{
		final PreviousSysconfigsCollector collector = new PreviousSysconfigsCollector();
		SystemSysconfigCommand.builder()
				.systemSysconfigs(ImmutableMap.of(NAME, value))
				.previousSysconfigsCollector(collector)
				.build()
				.execute();
		return collector.toList();
	}

	private static void restore(final List<JsonSysconfig> sysconfigs)
	{
		RestoreSysconfigsCommand.builder()
				.sysconfigs(sysconfigs)
				.build()
				.execute();
	}

	/** Fails saving the sysconfig with the given name (class name = table name, as required for model interceptors). */
	@Interceptor(I_AD_SysConfig.class)
	@RequiredArgsConstructor
	public static class AD_SysConfig
	{
		private final String name;

		@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
		public void beforeSave(final I_AD_SysConfig record)
		{
			if (name.equals(record.getName()))
			{
				throw new AdempiereException("Cannot save " + name);
			}
		}
	}
}
