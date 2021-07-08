/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.references.zoom_into;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ImmutableTranslatableString;
import org.adempiere.ad.element.api.AdWindowId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

class CustomizedWindowInfoMapTest
{
	private static CustomizedWindowInfo customizedWindowInfo(final int baseWindowId, final int customizedWindowId)
	{
		return CustomizedWindowInfo.builder()
				.baseWindowId(AdWindowId.ofRepoId(baseWindowId))
				.customizationWindowId(AdWindowId.ofRepoId(customizedWindowId))
				.customizationWindowCaption(caption(customizedWindowId))
				.build();
	}

	private static ImmutableTranslatableString caption(final int customizedWindowId)
	{
		return ImmutableTranslatableString.builder()
				.defaultValue("" + customizedWindowId)
				.build();
	}

	@Nested
	public class ofList
	{
		@Test
		public void case_15to16()
		{
			final CustomizedWindowInfoMap map = CustomizedWindowInfoMap.ofList(Collections.singletonList(
					customizedWindowInfo(15, 16)));

			final CustomizedWindowInfo expectedWindowInfo = CustomizedWindowInfo.builder()
					.customizationWindowCaption(caption(16))
					.baseWindowId(AdWindowId.ofRepoId(15))
					.previousCustomizationWindowIds(ImmutableList.of())
					.customizationWindowId(AdWindowId.ofRepoId(16))
					.build();

			for (final int adWindowRepoId : Arrays.asList(15, 16))
			{
				Assertions.assertThat(map.getCustomizedWindowInfo(AdWindowId.ofRepoId(adWindowRepoId)))
						.isPresent()
						.contains(expectedWindowInfo);
			}

		}

		@Test
		public void case_15to16_25to26_14to15_26to27_16to25()
		{
			final CustomizedWindowInfoMap map = CustomizedWindowInfoMap.ofList(Arrays.asList(
					customizedWindowInfo(15, 16),
					customizedWindowInfo(25, 26),
					customizedWindowInfo(14, 15),
					customizedWindowInfo(26, 27),
					customizedWindowInfo(16, 25)));

			final CustomizedWindowInfo expectedWindowInfo = CustomizedWindowInfo.builder()
					.customizationWindowCaption(caption(27))
					.baseWindowId(AdWindowId.ofRepoId(14))
					.previousCustomizationWindowIds(ImmutableList.of(
							AdWindowId.ofRepoId(15),
							AdWindowId.ofRepoId(16),
							AdWindowId.ofRepoId(25),
							AdWindowId.ofRepoId(26)))
					.customizationWindowId(AdWindowId.ofRepoId(27))
					.build();

			for (final int adWindowRepoId : Arrays.asList(14, 15, 16, 25, 26, 26))
			{
				Assertions.assertThat(map.getCustomizedWindowInfo(AdWindowId.ofRepoId(adWindowRepoId)))
						.isPresent()
						.contains(expectedWindowInfo);
			}
		}
	}
}