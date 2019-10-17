package de.metas.translation.interceptor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.ElementChangedEvent;
import org.adempiere.ad.element.api.ElementChangedEvent.ChangedField;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Element_Trl;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Services;

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

@Interceptor(I_AD_Element_Trl.class)
@Component
public class AD_Element_Trl
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void beforeElementTrlChanged(final I_AD_Element_Trl adElementTrl)
	{
		assertNotChangingRegularAndCustomizationFields(adElementTrl);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void afterElementTrlChanged(final I_AD_Element_Trl adElementTrl)
	{
		final ElementChangedEvent event = extractElementChangedEvent(adElementTrl);
		Services.get(IElementTranslationBL.class).updateTranslations(event);
	}

	private void assertNotChangingRegularAndCustomizationFields(final I_AD_Element_Trl adElementTrl)
	{
		final Set<String> changedRegularFields = new HashSet<>();
		final Set<String> changedCustomizationFields = new HashSet<>();

		if (isValueChanged(adElementTrl, I_AD_Element_Trl.COLUMNNAME_IsUseCustomization))
		{
			changedCustomizationFields.add(I_AD_Element_Trl.COLUMNNAME_IsUseCustomization);
		}

		//
		for (final ChangedField field : ChangedField.values())
		{
			if (field.hasCustomizedField() && isValueChanged(adElementTrl, field.getCustomizationColumnName()))
			{
				changedCustomizationFields.add(field.getCustomizationColumnName());
			}

			if (isValueChanged(adElementTrl, field.getColumnName()))
			{
				changedRegularFields.add(field.getColumnName());
			}
		}

		//
		if (!changedRegularFields.isEmpty() && !changedCustomizationFields.isEmpty())
		{
			throw new AdempiereException("Changing regular fields and customization fields is not allowed."
					+ "\n Regular fields changed: " + changedRegularFields
					+ "\n Customization fields changed: " + changedCustomizationFields)
							.markAsUserValidationError();
		}
	}

	private ElementChangedEvent extractElementChangedEvent(final I_AD_Element_Trl adElementTrl)
	{
		// assertNotChangingRegularAndCustomizationFields();

		final AdElementId adElementId = AdElementId.ofRepoId(adElementTrl.getAD_Element_ID());
		final String adLanguage = adElementTrl.getAD_Language();

		final Set<ChangedField> changedFields = ElementChangedEvent.ChangedField.streamAll()
				.filter(columnField -> isValueChanged(adElementTrl, columnField))
				.collect(ImmutableSet.toImmutableSet());

		final String columnName = extractStringValue(adElementTrl, ChangedField.ColumnName);
		//
		final String name = extractStringValue(adElementTrl, ChangedField.Name);
		final String description = extractStringValue(adElementTrl, ChangedField.Description);
		final String help = extractStringValue(adElementTrl, ChangedField.Help);
		final String printName = extractStringValue(adElementTrl, ChangedField.PrintName);
		final String commitWarning = extractStringValue(adElementTrl, ChangedField.CommitWarning);
		final String poName = extractStringValue(adElementTrl, ChangedField.PO_Name);
		final String poPrintName = extractStringValue(adElementTrl, ChangedField.PO_PrintName);
		final String poDescription = extractStringValue(adElementTrl, ChangedField.PO_Description);
		final String poHelp = extractStringValue(adElementTrl, ChangedField.PO_Help);
		final String webuiNameBrowse = extractStringValue(adElementTrl, ChangedField.WebuiNameBrowse);
		final String webuiNameNew = extractStringValue(adElementTrl, ChangedField.WebuiNameNew);
		final String webuiNameNewBreadcrumb = extractStringValue(adElementTrl, ChangedField.WebuiNameNewBreadcrumb);

		return ElementChangedEvent.builder()
				.adElementId(adElementId)
				.adLanguage(adLanguage)
				.updatedColumns(changedFields)
				.name(name)
				.columnName(columnName)
				.description(description)
				.help(help)
				.printName(printName)
				.commitWarning(commitWarning)
				.poName(poName)
				.poPrintName(poPrintName)
				.poDescription(poDescription)
				.poHelp(poHelp)
				.webuiNameBrowse(webuiNameBrowse)
				.webuiNameNew(webuiNameNew)
				.webuiNameNewBreadcrumb(webuiNameNewBreadcrumb)
				.build();
	}

	private boolean isValueChanged(final I_AD_Element_Trl adElementTrl, final String columnName)
	{
		return InterfaceWrapperHelper.isValueChanged(adElementTrl, columnName);
	}

	private boolean isValueChanged(final I_AD_Element_Trl adElementTrl, final ChangedField columnField)
	{
		String valueNew = extractStringValue(adElementTrl, columnField);
		String valueOld = extractStringValueOld(adElementTrl, columnField);

		return !Objects.equals(valueOld, valueNew);
	}

	private String extractStringValueOld(final I_AD_Element_Trl adElementTrl, final ChangedField columnField)
	{
		final I_AD_Element_Trl adElementTrlOld = InterfaceWrapperHelper.createOld(adElementTrl, I_AD_Element_Trl.class);
		return extractStringValue(adElementTrlOld, columnField);
	}

	private String extractStringValue(final I_AD_Element_Trl adElementTrl, final ChangedField columnField)
	{
		if (columnField.hasCustomizedField() && adElementTrl.isUseCustomization())
		{
			return extractStringValue(adElementTrl, columnField.getCustomizationColumnName());
		}

		return extractStringValue(adElementTrl, columnField.getColumnName());
	}

	private String extractStringValue(final I_AD_Element_Trl adElementTrl, final String columnName)
	{
		return InterfaceWrapperHelper.getValue(adElementTrl, columnName)
				.map(Object::toString)
				.orElse(null);
	}

}
