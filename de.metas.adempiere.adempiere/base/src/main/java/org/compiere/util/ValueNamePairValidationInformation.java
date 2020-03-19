/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.compiere.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.i18n.AdMessageKey;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.ValueNamePairValidationInformation.ValueNamePairValidationInformationBuilder;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonDeserialize(builder = ValueNamePairValidationInformationBuilder.class)
public class ValueNamePairValidationInformation
{
	@NonNull
	@Default
	private AdMessageKey title = AdMessageKey.of("de.metas.popupinfo.popupTitle");

	@NonNull
	private AdMessageKey question;

	@NonNull
	@Default
	private AdMessageKey answerYes = AdMessageKey.of("de.metas.popupinfo.yes");

	@NonNull
	@Default
	private AdMessageKey answerNo = AdMessageKey.of("de.metas.popupinfo.no");

	@JsonPOJOBuilder(withPrefix = "")
	public static class ValueNamePairValidationInformationBuilder
	{
	}
}
