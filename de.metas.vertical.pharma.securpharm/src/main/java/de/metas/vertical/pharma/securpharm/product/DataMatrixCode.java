package de.metas.vertical.pharma.securpharm.product;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.securpharm
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

@Value
public class DataMatrixCode
{
	public static DataMatrixCode ofBase64Encoded(@NonNull final String codeBase64Encoded)
	{
		final String code = new String(Base64.getDecoder().decode(codeBase64Encoded.getBytes()));
		return new DataMatrixCode(code);
	}

	public static DataMatrixCode ofString(@NonNull final String code)
	{
		return new DataMatrixCode(code);
	}

	private final String code;

	private DataMatrixCode(@NonNull final String code)
	{
		this.code = code;
	}

	public String toBase64Url()
	{
		return Base64.getEncoder()
				.withoutPadding()
				.encodeToString(code.getBytes(StandardCharsets.UTF_8));
	}

}
