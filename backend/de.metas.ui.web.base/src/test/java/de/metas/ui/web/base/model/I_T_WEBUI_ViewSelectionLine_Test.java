package de.metas.ui.web.base.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class I_T_WEBUI_ViewSelectionLine_Test
{
	@Test
	void checkIntKeys()
	{
		assertThat(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_IntKeys).isEqualTo(I_T_WEBUI_ViewSelection.COLUMNNAME_IntKeys);
	}

	@Test
	void checkStringKeys()
	{
		assertThat(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_StringKeys).isEqualTo(I_T_WEBUI_ViewSelection.COLUMNNAME_StringKeys);
	}
}