package de.metas.ui.web.vaadin.window;

/*
 * #%L
 * de.metas.ui.web.vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class WindowConstants
{
	public static final PropertyName PROPERTYNAME_WindowRoot = PropertyName.of("#Window#Root");
	public static final PropertyName PROPERTYNAME_WindowTitle = PropertyName.of("#Window#Title");
	public static final PropertyName PROPERTYNAME_RecordSummary = PropertyName.of("#Window#RecordSummary");
	public static final PropertyName PROPERTYNAME_RecordAditionalSummary = PropertyName.of("#Window#RecordAditionalSummary");

	public static final PropertyName PROPERTYNAME_GridRowId = PropertyName.of("#RowId");
	
	public static final PropertyName PROPERTYNAME_IsActive = PropertyName.of("IsActive");
	public static final PropertyName PROPERTYNAME_Processed = PropertyName.of("Processed");
	public static final PropertyName PROPERTYNAME_Processing = PropertyName.of("Processing");

	public static enum OnChangesFound
	{
		Discard, Ask,
	}

	public static final PropertyName lookupValuesName(final PropertyName propertyName)
	{
		return PropertyName.of(propertyName.toString() + "#values");
	}

	public static final PropertyName readonlyFlagName(final PropertyName propertyName)
	{
		return PropertyName.of(propertyName.toString() + "#readonly");
	}

	public static final PropertyName displayFlagName(final PropertyName propertyName)
	{
		return PropertyName.of(propertyName.toString() + "#displayed");
	}

	public static final PropertyName mandatoryFlagName(final PropertyName propertyName)
	{
		return PropertyName.of(propertyName.toString() + "#mandatory");
	}

	private WindowConstants()
	{
	}
}
