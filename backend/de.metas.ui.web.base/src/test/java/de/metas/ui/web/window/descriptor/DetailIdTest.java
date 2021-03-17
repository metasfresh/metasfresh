package de.metas.ui.web.window.descriptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.element.api.AdTabId;
import org.junit.jupiter.api.Test;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class DetailIdTest
{

	@Test
	public void fromAD_Tab_ID()
	{
		final DetailId detailId = DetailId.fromAD_Tab_ID(AdTabId.ofRepoId(39));

		assertThat(detailId.toAdTabId()).isEqualTo(AdTabId.ofRepoId(39));
		assertThat(detailId.toJson()).isEqualTo("AD_Tab-39");
	}

	@Test
	public void fromJson()
	{
		final DetailId detailId = DetailId.fromJson("AD_Tab-39");

		assertThat(detailId.toAdTabId()).isEqualTo(AdTabId.ofRepoId(39));
		assertThat(detailId.toJson()).isEqualTo("AD_Tab-39");
	}

	@Test
	public void fromPrefixAndId()
	{
		final DetailId detailId = DetailId.fromPrefixAndId("prefix", 38);

		assertThat(detailId.toJson()).isEqualTo("prefix-38");
		assertThat(detailId).isEqualTo(DetailId.fromJson("prefix-38"));
	}
}
