/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.bpartner;

import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * It happens that a company has one GLN in the real world, but it's decided that there shall be more than one {@link org.compiere.model.I_C_BPartner} records in metasfresh.
 * To identify such partners, an additional {@link org.compiere.model.I_C_BPartner#COLUMNNAME_Lookup_Label} can be set.
 */
@Value
public class GlnWithLabel
{
	public static GlnWithLabel ofString(@NonNull final String value)
	{
		final Pattern pattern = Pattern.compile("([^_]+)_(.+)");
		final Matcher matcher = pattern.matcher(value);
		if (matcher.matches())
		{
			final GLN gln1 = GLN.ofString(matcher.group(1));
			return new GlnWithLabel(gln1, matcher.group(2));
			
		}
		final GLN gln = GLN.ofString(value);
		return new GlnWithLabel(gln, null);
	}

	public static GlnWithLabel ofGLN(@NonNull final GLN gln, @Nullable final String label)
	{
		return new GlnWithLabel(gln, label);
	}

	@NonNull GLN gln;
	@Nullable String label;

	private GlnWithLabel(@NonNull final GLN gln, @Nullable final String label)
	{
		this.gln = gln;
		this.label = label;
	}
}
