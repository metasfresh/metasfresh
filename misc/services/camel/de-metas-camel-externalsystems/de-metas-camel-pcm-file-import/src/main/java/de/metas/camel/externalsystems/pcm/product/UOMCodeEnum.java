/*
 * #%L
 * de-metas-camel-pcm-file-import
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

package de.metas.camel.externalsystems.pcm.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum UOMCodeEnum
{
	LTR("L", "LTR"),
	KGM("kg", "KGM"),
	GRM("g", "GRM"),
	PCE("ST", "PCE");
	
	private final String pcmCode;
	private final String x12de355Code;

	@NonNull
	public static Optional<String> getX12DE355CodeByPCMCode(@Nullable final String code)
	{
		if (StringUtils.isEmpty(code))
		{
			return Optional.empty();
		}

		return Arrays.stream(values())
				.filter(rating -> rating.getPcmCode().equals(code))
				.findFirst()
				.map(UOMCodeEnum::getX12de355Code);
	}
}
