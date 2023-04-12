/*
 * #%L
 * de-metas-common-externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.common.externalsystem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = JsonExternalSAPBPartnerImportSettings.JsonExternalSAPBPartnerImportSettingsBuilder.class)
public class JsonExternalSAPBPartnerImportSettings
{
	@NonNull
	@JsonProperty("seqNo")
	Integer seqNo;

	@NonNull
	@JsonProperty("partnerCodePattern")
	String partnerCodePattern;

	@NonNull
	@JsonProperty("singleBPartner")
	Boolean isSingleBPartner;

	@Nullable
	@JsonProperty("bpGroupName")
	String bpGroupName;

	@NonNull
	public static JsonExternalSAPBPartnerImportSettings of(
			@NonNull final Integer seqNo,
			@NonNull final String partnerCodePattern,
			@NonNull final Boolean isSingleBPartner,
			@Nullable final String bpGroupName)
	{
		return JsonExternalSAPBPartnerImportSettings.builder()
				.seqNo(seqNo)
				.partnerCodePattern(partnerCodePattern)
				.isSingleBPartner(isSingleBPartner)
				.bpGroupName(bpGroupName)
				.build();
	}

	@Builder
	public JsonExternalSAPBPartnerImportSettings(
			@NonNull final Integer seqNo,
			@NonNull final String partnerCodePattern,
			@NonNull final Boolean isSingleBPartner,
			@Nullable final String bpGroupName)
	{
		this.seqNo = seqNo;
		this.partnerCodePattern = partnerCodePattern;
		this.isSingleBPartner = isSingleBPartner;
		this.bpGroupName = bpGroupName;
	}
}
