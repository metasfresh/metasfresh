package org.adempiere.ad.element.api;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Element_Trl;

import com.google.common.collect.ImmutableSet;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
@Builder(toBuilder = true)
public class ElementChangedEvent
{
	@NonNull
	AdElementId adElementId;

	@Nullable
	String adLanguage;

	@Singular
	@Getter(AccessLevel.PRIVATE)
	ImmutableSet<ChangedField> updatedColumns;

	String columnName;
	String name;
	String printName;
	String description;
	String help;
	String commitWarning;

	String webuiNameBrowse;
	String webuiNameNew;
	String webuiNameNewBreadcrumb;

	String poName;
	String poPrintName;
	String poDescription;
	String poHelp;

	public boolean isChangedAnyOf(@NonNull final ChangedField... columnNames)
	{
		for (final ChangedField columnName : columnNames)
		{
			if (updatedColumns.contains(columnName))
			{
				return true;
			}
		}

		return false;
	}

	public static enum ChangedField
	{
		ColumnName(I_AD_Element.COLUMNNAME_ColumnName), //
		//
		Name(I_AD_Element_Trl.COLUMNNAME_Name, I_AD_Element_Trl.COLUMNNAME_Name_Customized), //
		Description(I_AD_Element_Trl.COLUMNNAME_Description, I_AD_Element_Trl.COLUMNNAME_Description_Customized), //
		Help(I_AD_Element_Trl.COLUMNNAME_Help, I_AD_Element_Trl.COLUMNNAME_Help_Customized), //
		PrintName(I_AD_Element.COLUMNNAME_PrintName), //
		PO_Description(I_AD_Element.COLUMNNAME_PO_Description), //
		PO_Help(I_AD_Element.COLUMNNAME_PO_Help), //
		PO_Name(I_AD_Element.COLUMNNAME_PO_Name), //
		PO_PrintName(I_AD_Element.COLUMNNAME_PO_PrintName), //
		CommitWarning(I_AD_Element.COLUMNNAME_CommitWarning), //
		WebuiNameBrowse(I_AD_Element.COLUMNNAME_WEBUI_NameBrowse), //
		WebuiNameNew(I_AD_Element.COLUMNNAME_WEBUI_NameNew), //
		WebuiNameNewBreadcrumb(I_AD_Element.COLUMNNAME_WEBUI_NameNewBreadcrumb) //
		;

		@Getter
		private final String columnName;
		@Getter
		private final String customizationColumnName;

		ChangedField(@NonNull final String columnName)
		{
			this.columnName = columnName;
			this.customizationColumnName = null;
		}

		ChangedField(
				@NonNull final String columnName,
				@Nullable final String customizationColumnName)
		{
			this.columnName = columnName;
			this.customizationColumnName = customizationColumnName;
		}

		public static Stream<ChangedField> streamAll()
		{
			return Stream.of(values());
		}

		public boolean hasCustomizedField()
		{
			return getCustomizationColumnName() != null;
		}
	}
}
