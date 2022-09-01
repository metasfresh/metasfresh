/*
 * #%L
 * metasfresh-report-service
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

package de.metas.report.jasper.class_loader.images.org;

import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrgImageResourceNameMatcherTest
{
	@ParameterizedTest
	@ValueSource(strings = {
			"de/metas/org/images/MyImage.png",
			"SOME_PREFIXde/metas/org/images/MyImage.png",
	})
	public void expect_imageColumnName_MyImage(@NonNull final String resourceName)
	{
		Assertions.assertThat(OrgImageResourceNameMatcher.instance.getImageColumnName(resourceName))
				.contains("MyImage");
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"MyImage.png",
			"de/NOT_metas/org/images/MyImage.png",
	})
	public void expect_NO_imageColumnName(@NonNull final String resourceName)
	{
		Assertions.assertThat(OrgImageResourceNameMatcher.instance.getImageColumnName(resourceName))
				.isEmpty();
	}
}