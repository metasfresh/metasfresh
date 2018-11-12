package org.adempiere.ad.field.model.interceptor;

import java.sql.SQLException;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.expression.api.impl.LogicExpressionCompiler;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Interceptor(I_AD_Field.class)
@Callout(I_AD_Field.class)
@Component
public class AD_Field
{
	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_AD_Field.COLUMNNAME_AD_Name_ID)
	@CalloutMethod(columnNames = { I_AD_Field.COLUMNNAME_AD_Name_ID, I_AD_Field.COLUMNNAME_AD_Column_ID })
	public void onNameIDChanged(final I_AD_Field field) throws SQLException
	{
		final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

		final I_AD_Element fieldElement;

		if (field.getAD_Name_ID() > 0)

		{
			fieldElement = field.getAD_Name();
		}
		else if (field.getAD_Column_ID() > 0)
		{
			// the AD_Name_ID was set to null. Get back to the values from the AD_Column
			final I_AD_Column fieldColumn = field.getAD_Column();
			fieldElement = fieldColumn.getAD_Element();
		}
		else
		{
			// Nothing to do. the element was not yet saved.
			return;
		}

		field.setName(fieldElement.getName());
		field.setDescription(fieldElement.getDescription());
		field.setHelp(fieldElement.getHelp());

		final AdFieldId adFieldId = AdFieldId.ofRepoIdOrNull(field.getAD_Field_ID());

		if (adFieldId == null)
		{
			// nothing to do. The field was not yet saved
			return;
		}

		adWindowDAO.deleteExistingADElementLinkForFieldId(adFieldId);
		adWindowDAO.createADElementLinkForFieldId(adFieldId);

	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_AD_Field.COLUMNNAME_AD_Name_ID)
	public void updateTranslationsForElement(final I_AD_Field field)
	{
		final I_AD_Element fieldElement;

		if (field.getAD_Name_ID() <= 0)
		{
			// the AD_Name_ID was set to null. Get back to the values from the AD_Column
			final I_AD_Column fieldColumn = field.getAD_Column();
			fieldElement = fieldColumn.getAD_Element();
		}
		else
		{
			fieldElement = field.getAD_Name();
		}

		if (fieldElement == null)
		{
			// not yet saved; do nothing
			return;
		}

		// in the end, make sure the translation fields are also updated
		Services.get(IElementTranslationBL.class).updateFieldTranslationsFromAD_Name(AdElementId.ofRepoId(fieldElement.getAD_Element_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_AD_Field.COLUMNNAME_DisplayLogic })
	public void validateLogicExpressions(final I_AD_Field field)
	{
		if (!Check.isEmpty(field.getDisplayLogic(), true))
		{
			LogicExpressionCompiler.instance.compile(field.getDisplayLogic());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void onFieldDelete(final I_AD_Field field) throws SQLException
	{
		final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

		final AdFieldId adFieldId = AdFieldId.ofRepoId(field.getAD_Field_ID());

		adWindowDAO.deleteExistingADElementLinkForFieldId(adFieldId);
	}

}
