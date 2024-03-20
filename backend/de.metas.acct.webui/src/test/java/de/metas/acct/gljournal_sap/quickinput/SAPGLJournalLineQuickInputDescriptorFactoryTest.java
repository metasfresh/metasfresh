package de.metas.acct.gljournal_sap.quickinput;

import org.junit.jupiter.api.Test;

class SAPGLJournalLineQuickInputDescriptorFactoryTest
{
	@Test
	void assertDefaultLayoutConfigIsValid()
	{
		// NOTE: even though we check this in SAPGLJournalLineQuickInputDescriptorFactory we also doing it here to make sure we get the issue on build
		
		SAPGLJournalLineQuickInputConfigValidator.assertValid(SAPGLJournalLineQuickInputDescriptorFactory.DEFAULT_LayoutConfig);
	}
}