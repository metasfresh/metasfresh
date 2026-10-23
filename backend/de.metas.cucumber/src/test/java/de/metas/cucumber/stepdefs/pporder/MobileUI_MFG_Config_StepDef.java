package de.metas.cucumber.stepdefs.pporder;

import de.metas.cache.CacheMgt;
import de.metas.util.Services;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_MobileUI_MFG_Config;

import java.util.ArrayList;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.deleteRecord;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Global {@code MobileUI_MFG_Config} flags. They are shared by every scenario on the executor, so each change made here
 * is recorded and put back by the {@code @After} hook, also when the scenario fails part-way.
 */
public class MobileUI_MFG_Config_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final List<ConfigOverride> overrides = new ArrayList<>();

	@And("set MobileUI_MFG_Config IsFinishedGoodsCatchWeightScanRequired to {string} and IsCaptureCatchWeightAtReceipt to {string}")
	public void setFinishedGoodsCatchWeightFlags(@NonNull final String scanRequired, @NonNull final String captureAtReceipt)
	{
		final List<I_MobileUI_MFG_Config> configs = queryBL.createQueryBuilder(I_MobileUI_MFG_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
		if (configs.isEmpty())
		{
			final I_MobileUI_MFG_Config newConfig = newInstance(I_MobileUI_MFG_Config.class);
			setFlags(newConfig, scanRequired, captureAtReceipt);
			overrides.add(ConfigOverride.created(newConfig.getMobileUI_MFG_Config_ID()));
		}
		else
		{
			for (final I_MobileUI_MFG_Config config : configs)
			{
				overrides.add(ConfigOverride.updated(config));
				setFlags(config, scanRequired, captureAtReceipt);
			}
		}

		// the global config cache is not invalidated by MobileUI_MFG_Config changes
		CacheMgt.get().reset();
	}

	private static void setFlags(@NonNull final I_MobileUI_MFG_Config config, @NonNull final String scanRequired, @NonNull final String captureAtReceipt)
	{
		config.setIsFinishedGoodsCatchWeightScanRequired("Y".equals(scanRequired));
		config.setIsCaptureCatchWeightAtReceipt("Y".equals(captureAtReceipt));
		saveRecord(config);
	}

	/**
	 * Puts back the values from before this scenario, last change first (a Background and the scenario body may both change them).
	 */
	@After
	public void restoreConfigOverrides()
	{
		if (overrides.isEmpty())
		{
			return;
		}

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
				config.setIsFinishedGoodsCatchWeightScanRequired(override.isScanRequiredBefore());
				config.setIsCaptureCatchWeightAtReceipt(override.isCaptureAtReceiptBefore());
				saveRecord(config);
			}
		}
		overrides.clear();
		CacheMgt.get().reset();
	}

	@Value
	private static class ConfigOverride
	{
		int configId;
		boolean created;
		boolean scanRequiredBefore;
		boolean captureAtReceiptBefore;

		static ConfigOverride created(final int configId)
		{
			return new ConfigOverride(configId, true, false, false);
		}

		static ConfigOverride updated(@NonNull final I_MobileUI_MFG_Config config)
		{
			return new ConfigOverride(config.getMobileUI_MFG_Config_ID(), false, config.isFinishedGoodsCatchWeightScanRequired(), config.isCaptureCatchWeightAtReceipt());
		}
	}
}
