package org.adempiere.ad.element.api;

import javax.annotation.Nullable;

import org.compiere.model.I_AD_Element;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
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

	public static final ImmutableSet<String> ALL_COLUMN_NAMES = ImmutableSet.of(
			I_AD_Element.COLUMNNAME_Name,
			I_AD_Element.COLUMNNAME_ColumnName,
			I_AD_Element.COLUMNNAME_PrintName,
			I_AD_Element.COLUMNNAME_Description,
			I_AD_Element.COLUMNNAME_Help,
			I_AD_Element.COLUMNNAME_PO_Description,
			I_AD_Element.COLUMNNAME_PO_Help,
			I_AD_Element.COLUMNNAME_PO_Name,
			I_AD_Element.COLUMNNAME_PO_PrintName,
			I_AD_Element.COLUMNNAME_CommitWarning,
			I_AD_Element.COLUMNNAME_WEBUI_NameBrowse,
			I_AD_Element.COLUMNNAME_WEBUI_NameNew,
			I_AD_Element.COLUMNNAME_WEBUI_NameNewBreadcrumb
	);
	@Singular
	ImmutableSet<String> updatedColumns;

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

}
