package de.metas.acct.gljournal_sap.quickinput;

import com.google.common.annotations.VisibleForTesting;
import de.metas.quickinput.config.QuickInputConfigLayout;
import de.metas.quickinput.config.QuickInputConfigService;
import de.metas.sysconfig.SysConfigListener;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class SAPGLJournalLineQuickInputConfigProvider implements SysConfigListener
{
	@VisibleForTesting
	static final String SYSCONFIG_LayoutConfig = "SAP_GLJournalLine.quickInput.layout";

	@VisibleForTesting
	static final QuickInputConfigLayout DEFAULT_LayoutConfig = QuickInputConfigLayout.builder()
			.field(QuickInputConfigLayout.Field.builder().fieldName(ISAPGLJournalLineQuickInput.COLUMNNAME_PostingSign).mandatory(true).build())
			.field(QuickInputConfigLayout.Field.builder().fieldName(ISAPGLJournalLineQuickInput.COLUMNNAME_GL_Account_ID).mandatory(true).build())
			.field(QuickInputConfigLayout.Field.builder().fieldName(ISAPGLJournalLineQuickInput.COLUMNNAME_Amount).mandatory(true).build())
			.build();

	static
	{
		assertValid(DEFAULT_LayoutConfig);
	}

	private final QuickInputConfigService quickInputConfigService;

	public SAPGLJournalLineQuickInputConfigProvider(@NonNull final QuickInputConfigService quickInputConfigService) {this.quickInputConfigService = quickInputConfigService;}

	@Override
	public void assertValidSysConfigValue(final String sysconfigName, @Nullable final String value)
	{
		if (SYSCONFIG_LayoutConfig.equals(sysconfigName))
		{
			QuickInputConfigService.parseLayoutFromSysconfigValue(value)
					.ifPresent(SAPGLJournalLineQuickInputConfigProvider::assertValid);
		}
	}

	public QuickInputConfigLayout getLayoutConfig()
	{
		final QuickInputConfigLayout layoutConfig = quickInputConfigService.getLayoutBySysconfig(SYSCONFIG_LayoutConfig).orElse(DEFAULT_LayoutConfig);
		assertValid(layoutConfig);
		return layoutConfig;
	}

	@VisibleForTesting
	static void assertValid(@NonNull final QuickInputConfigLayout layoutConfig)
	{
		layoutConfig.assertFieldExistsAndIsMandatory(ISAPGLJournalLineQuickInput.COLUMNNAME_PostingSign);
		layoutConfig.assertFieldExistsAndIsMandatory(ISAPGLJournalLineQuickInput.COLUMNNAME_GL_Account_ID);
		layoutConfig.assertFieldExistsAndIsMandatory(ISAPGLJournalLineQuickInput.COLUMNNAME_Amount);
		layoutConfig.assertFieldNamesEligible(ISAPGLJournalLineQuickInput.COLUMNNAMES);
	}
}
