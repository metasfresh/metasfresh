package de.metas.document;

import static de.metas.util.Check.assumeNotEmpty;

import javax.annotation.Nullable;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import lombok.NonNull;
import lombok.Value;

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

@Value
public class DocBaseAndSubType
{
	public static DocBaseAndSubType of(@NonNull final String docBaseType)
	{
		final String docSubType = null;
		return interner.intern(new DocBaseAndSubType(docBaseType, docSubType));
	}

	public static DocBaseAndSubType of(
			@NonNull final String docBaseType,
			@Nullable final String docSubType)
	{
		return interner.intern(new DocBaseAndSubType(docBaseType, docSubType));
	}

	private static final Interner<DocBaseAndSubType> interner = Interners.newStrongInterner();

	String docBaseType;
	String docSubType;

	private DocBaseAndSubType(
			@NonNull final String docBaseType,
			@Nullable final String docSubType)
	{
		this.docBaseType = assumeNotEmpty(docBaseType, "Param docBaseType may not be empty");
		this.docSubType = docSubType;
	}

}
