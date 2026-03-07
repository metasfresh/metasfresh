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
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a GLN (Global Location Number) with an optional lookup label for differentiating BPartners.
 * <p>
 * In some business scenarios, a single company has one physical GLN in the real world,
 * but multiple {@link org.compiere.model.I_C_BPartner} records need to be maintained in metasfresh
 * to represent different aspects or divisions of that company.
 * <p>
 * To uniquely identify such partners that share the same GLN, an additional
 * {@link org.compiere.model.I_C_BPartner#COLUMNNAME_Lookup_Label} can be set.
 * This class combines both the GLN and the optional label for lookup purposes.
 * <p>
 * String format: {@code {GLN}_{LABEL}} (e.g., "1234567890123_division_A")
 * <br>If no label is present, only the GLN is used (e.g., "1234567890123")
 * <p>
 * <b>Note:</b> The GLN portion is assumed to not contain underscores. The first underscore
 * in the string separates the GLN from the label.
 */
@Value
public class GlnWithLabel
{
	private static final Pattern PATTERN = Pattern.compile("([^_]+)_(.+)");

	public static GlnWithLabel ofString(@NonNull final String value)
	{
		try
		{
			final Matcher matcher = PATTERN.matcher(value);
			if (matcher.matches())
			{
				final GLN gln1 = GLN.ofString(matcher.group(1));
				return new GlnWithLabel(gln1, matcher.group(2));
			}
			final GLN gln = GLN.ofString(value);
			return new GlnWithLabel(gln, null);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Invalid format of GlnWithLabel string " + value, e);
		}
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
