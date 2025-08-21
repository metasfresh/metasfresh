package de.metas.translation.interceptor;

import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.translation.api.impl.MobileApplicationTranslationService;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Mobile_Application_Trl;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

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

@Interceptor(I_Mobile_Application_Trl.class)
@Component
public class Mobile_Application_Trl
{

	private final MobileApplicationTranslationService mobileApplicationTranslationService;

	public Mobile_Application_Trl(final @NonNull MobileApplicationTranslationService mobileApplicationTranslationService)
	{
		this.mobileApplicationTranslationService = mobileApplicationTranslationService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void beforeMobileApplicationTrlChanged(final I_Mobile_Application_Trl mobileApplicationTrl)
	{
		assertNotChangingRegularAndCustomizationFields(mobileApplicationTrl);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void afterMobileApplicationTrlChanged(final I_Mobile_Application_Trl mobileApplicationTrl)
	{
		final MobileApplicationRepoId mobileApplicationRepoId = MobileApplicationRepoId.ofRepoId(mobileApplicationTrl.getMobile_Application_ID());
		final String adLanguage = mobileApplicationTrl.getAD_Language();

		mobileApplicationTranslationService.updateMobileApplicationTrl(mobileApplicationRepoId, adLanguage);
	}

	private static void assertNotChangingRegularAndCustomizationFields(final I_Mobile_Application_Trl mobileApplicationTrl)
	{
		final Set<String> changedRegularFields = new HashSet<>();
		final Set<String> changedCustomizationFields = new HashSet<>();

		if (isValueChanged(mobileApplicationTrl, I_Mobile_Application_Trl.COLUMNNAME_IsUseCustomization))
		{
			changedCustomizationFields.add(I_Mobile_Application_Trl.COLUMNNAME_IsUseCustomization);
		}

		//
		for (final MobileApplicationTranslatedColumn field : MobileApplicationTranslatedColumn.values())
		{
			if (field.hasCustomizedField() && isValueChanged(mobileApplicationTrl, field.getCustomizationColumnName()))
			{
				changedCustomizationFields.add(field.getCustomizationColumnName());
			}

			if (isValueChanged(mobileApplicationTrl, field.getColumnName()))
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

	private static boolean isValueChanged(final I_Mobile_Application_Trl mobileApplicationTrl, final String columnName)
	{
		return InterfaceWrapperHelper.isValueChanged(mobileApplicationTrl, columnName);
	}

	private enum MobileApplicationTranslatedColumn
	{
		Name(I_Mobile_Application_Trl.COLUMNNAME_Name, I_Mobile_Application_Trl.COLUMNNAME_Name_Customized), //
		Description(I_Mobile_Application_Trl.COLUMNNAME_Description, I_Mobile_Application_Trl.COLUMNNAME_Description_Customized), //
		;

		@Getter
		private final String columnName;
		@Getter
		private final String customizationColumnName;

		MobileApplicationTranslatedColumn(
				@NonNull final String columnName,
				@Nullable final String customizationColumnName)
		{
			this.columnName = columnName;
			this.customizationColumnName = customizationColumnName;
		}

		public boolean hasCustomizedField()
		{
			return getCustomizationColumnName() != null;
		}
	}

}
