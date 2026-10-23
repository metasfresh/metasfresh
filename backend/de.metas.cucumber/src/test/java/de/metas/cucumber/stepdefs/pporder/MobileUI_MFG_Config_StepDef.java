package de.metas.cucumber.stepdefs.pporder;

import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_MobileUI_MFG_Config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.adempiere.model.InterfaceWrapperHelper.deleteRecord;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Steps that change the global {@code MobileUI_MFG_Config} flags.
 * <p>
 * A step changes every active {@code MobileUI_MFG_Config} record, or creates one if there is none.
 * These records are shared by every scenario on the executor, so each change is recorded and put back by the {@code @After} hook,
 * also when the scenario fails part-way.
 * <p>
 * Flag values are {@code 'Y'} or {@code 'N'}. Example:
 * <pre>
 * Background:
 *   And set MobileUI_MFG_Config IsFinishedGoodsCatchWeightScanRequired to 'N' and IsCaptureCatchWeightAtReceipt to 'Y'
 *
 * Scenario: ...
 *   And set MobileUI_MFG_Config IsAllowIssuingAnyHU to 'Y'
 * </pre>
 */
public class MobileUI_MFG_Config_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final List<ConfigOverride> overrides = new ArrayList<>();

	/**
	 * Sets {@code IsAllowIssuingAnyHU} on the global config(s); put back after the scenario.
	 *
	 * @see #restoreConfigOverrides()
	 */
	@And("set MobileUI_MFG_Config IsAllowIssuingAnyHU to {string}")
	public void setIsAllowIssuingAnyHU(@NonNull final String value)
	{
		final boolean isAllow = toBoolean(value);
		changeGlobalConfigs(config -> config.setIsAllowIssuingAnyHU(isAllow));
	}

	/**
	 * Sets {@code IsFinishedGoodsCatchWeightScanRequired} and {@code IsCaptureCatchWeightAtReceipt} on the global config(s);
	 * put back after the scenario.
	 *
	 * @see #restoreConfigOverrides()
	 */
	@And("set MobileUI_MFG_Config IsFinishedGoodsCatchWeightScanRequired to {string} and IsCaptureCatchWeightAtReceipt to {string}")
	public void setFinishedGoodsCatchWeightFlags(@NonNull final String scanRequired, @NonNull final String captureAtReceipt)
	{
		final boolean isScanRequired = toBoolean(scanRequired);
		final boolean isCaptureAtReceipt = toBoolean(captureAtReceipt);
		changeGlobalConfigs(config -> {
			config.setIsFinishedGoodsCatchWeightScanRequired(isScanRequired);
			config.setIsCaptureCatchWeightAtReceipt(isCaptureAtReceipt);
		});
	}

	private static boolean toBoolean(@NonNull final String value)
	{
		Check.assume("Y".equals(value) || "N".equals(value), "Expected 'Y' or 'N' but got {}", value);
		return "Y".equals(value);
	}

	private void changeGlobalConfigs(@NonNull final Consumer<I_MobileUI_MFG_Config> change)
	{
		final List<I_MobileUI_MFG_Config> configs = queryBL.createQueryBuilder(I_MobileUI_MFG_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
		if (configs.isEmpty())
		{
			final I_MobileUI_MFG_Config newConfig = newInstance(I_MobileUI_MFG_Config.class);
			change.accept(newConfig);
			saveRecord(newConfig);
			overrides.add(ConfigOverride.created(newConfig.getMobileUI_MFG_Config_ID()));
		}
		else
		{
			for (final I_MobileUI_MFG_Config config : configs)
			{
				overrides.add(ConfigOverride.updated(config));
				change.accept(config);
				saveRecord(config);
			}
		}
	}

	/**
	 * Puts back the values from before this scenario, last change first (a Background and the scenario body may both change them).
	 */
	@After
	public void restoreConfigOverrides()
	{
		for (int i = overrides.size() - 1; i >= 0; i--)
		{
			final ConfigOverride override = overrides.get(i);
			final I_MobileUI_MFG_Config config = load(override.getConfigId(), I_MobileUI_MFG_Config.class);
			if (config == null)
			{
				continue;
			}
			if (override.isCreated())
			{
				deleteRecord(config);
			}
			else
			{
				config.setIsAllowIssuingAnyHU(override.isAllowIssuingAnyHUBefore());
				config.setIsFinishedGoodsCatchWeightScanRequired(override.isScanRequiredBefore());
				config.setIsCaptureCatchWeightAtReceipt(override.isCaptureAtReceiptBefore());
				saveRecord(config);
			}
		}
		overrides.clear();
	}

	@Value
	private static class ConfigOverride
	{
		int configId;
		boolean created;
		boolean allowIssuingAnyHUBefore;
		boolean scanRequiredBefore;
		boolean captureAtReceiptBefore;

		static ConfigOverride created(final int configId)
		{
			return new ConfigOverride(configId, true, false, false, false);
		}

		static ConfigOverride updated(@NonNull final I_MobileUI_MFG_Config config)
		{
			return new ConfigOverride(
					config.getMobileUI_MFG_Config_ID(),
					false,
					config.isAllowIssuingAnyHU(),
					config.isFinishedGoodsCatchWeightScanRequired(),
					config.isCaptureCatchWeightAtReceipt());
		}
	}
}
