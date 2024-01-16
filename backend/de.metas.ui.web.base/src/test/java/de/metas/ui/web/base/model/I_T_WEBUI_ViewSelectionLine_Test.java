/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.base.model;

import de.metas.elasticsearch.model.I_T_ES_FTS_Search_Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class I_T_WEBUI_ViewSelectionLine_Test
{
	@Test
	void checkIntKeys()
	{
		assertThat(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_IntKeys).isEqualTo(I_T_WEBUI_ViewSelection.COLUMNNAME_IntKeys);
		assertThat(I_T_ES_FTS_Search_Result.COLUMNNAME_IntKeys).isEqualTo(I_T_WEBUI_ViewSelection.COLUMNNAME_IntKeys);
	}

	@Test
	void checkStringKeys()
	{
		assertThat(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_StringKeys).isEqualTo(I_T_WEBUI_ViewSelection.COLUMNNAME_StringKeys);
		assertThat(I_T_ES_FTS_Search_Result.COLUMNNAME_StringKeys).isEqualTo(I_T_WEBUI_ViewSelection.COLUMNNAME_StringKeys);
	}

	@Test
	void checkTimestampKeys()
	{
		assertThat(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_TimestampKeys).isEqualTo(I_T_WEBUI_ViewSelection.COLUMNNAME_TimestampKeys);
	}
}