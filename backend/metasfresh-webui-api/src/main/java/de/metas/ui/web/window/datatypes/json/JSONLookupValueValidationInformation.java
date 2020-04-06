package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.ValueNamePairValidationInformation;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONLookupValueValidationInformation
{
	@JsonProperty("title")
	private final String title;

	@JsonProperty("question")
	private final String question;

	@JsonProperty("answerYes")
	private final String answerYes;

	@JsonProperty("answerNo")
	private final String answerNo;

	@Builder
	private JSONLookupValueValidationInformation(
			@JsonProperty("title") final String title,
			@JsonProperty("question") final String question,
			@JsonProperty("answerYes") final String answerYes,
			@JsonProperty("answerNo") final String answerNo)
	{
		this.title = title;
		this.question = question;
		this.answerYes = answerYes;
		this.answerNo = answerNo;
	}

	public static JSONLookupValueValidationInformation ofNullable(
			@Nullable final ValueNamePairValidationInformation validationInformation,
			@NonNull final String adLanguage)
	{
		if (validationInformation == null)
		{
			return null;
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		return JSONLookupValueValidationInformation.builder()
				.title(msgBL.translate(adLanguage, validationInformation.getTitle().toAD_Message()))
				.question(msgBL.translate(adLanguage, validationInformation.getQuestion().toAD_Message()))
				.answerYes(msgBL.translate(adLanguage, validationInformation.getAnswerYes().toAD_Message()))
				.answerNo(msgBL.translate(adLanguage, validationInformation.getAnswerNo().toAD_Message()))
				.build();
	}
}
