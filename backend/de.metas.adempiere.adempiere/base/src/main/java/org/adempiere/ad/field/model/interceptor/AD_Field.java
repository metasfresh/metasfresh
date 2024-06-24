package org.adempiere.ad.field.model.interceptor;

import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.column.model.interceptor.AD_Column;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.IADElementDAO;
import org.adempiere.ad.element.api.IElementLinkBL;
import org.adempiere.ad.expression.api.impl.LogicExpressionCompiler;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.MinimalColumnInfo;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.ModelValidator;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

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
	private static final Logger logger = LogManager.getLogger(AD_Field.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = { I_AD_Field.COLUMNNAME_AD_Name_ID, I_AD_Field.COLUMNNAME_AD_Column_ID })
	public void calloutOnNameOrColumnChanged(final I_AD_Field field)
	{
		updateFieldFromElement(field);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_AD_Field.COLUMNNAME_DisplayLogic })
	public void onBeforeSave_ValidateLogicExpressions(final I_AD_Field field)
	{
		if (!Check.isEmpty(field.getDisplayLogic(), true))
		{
			LogicExpressionCompiler.instance.compile(field.getDisplayLogic());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = {
					I_AD_Field.COLUMNNAME_AD_Column_ID,
					I_AD_Field.COLUMNNAME_AD_Reference_ID,
					I_AD_Field.COLUMNNAME_AD_Reference_Value_ID })
	public void onBeforeSave_ValidateReference(final I_AD_Field field)
	{
		final AdColumnId adColumnId = AdColumnId.ofRepoIdOrNull(field.getAD_Column_ID());
		final int adReferenceId = field.getAD_Reference_ID();
		if (adColumnId != null && DisplayType.isAnyLookup(adReferenceId))
		{
			final MLookupInfo lookupInfo;
			try
			{
				final MinimalColumnInfo column = tableDAO.getMinimalColumnInfo(adColumnId);
				final String ctxTableName = tableDAO.retrieveTableName(column.getAdTableId());
				final AdValRuleId adValRuleId = CoalesceUtil.coalesceSuppliers(
						() -> AdValRuleId.ofRepoIdOrNull(field.getAD_Val_Rule_ID()),
						column::getAdValRuleId
				);
				lookupInfo = MLookupFactory.getLookupInfo(
						Integer.MAX_VALUE, // WindowNo
						adReferenceId,
						ctxTableName, // ctxTableName
						column.getColumnName(), // ctxColumnName
						field.getAD_Reference_Value_ID(),
						column.isParent(), // IsParent,
						adValRuleId != null ? adValRuleId.getRepoId() : -1
				);
			}
			catch (final Exception ex)
			{
				AD_Column.fireExceptionInvalidLookup(logger, ex);
				return;
			}

			if (lookupInfo == null)
			{
				AD_Column.fireExceptionInvalidLookup(logger, null);
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = { I_AD_Field.COLUMNNAME_AD_Name_ID, I_AD_Field.COLUMNNAME_AD_Column_ID })
	public void onAfterSave_WhenNameOrColumnChanged(final I_AD_Field field)
	{
		updateTranslationsForElement(field);
		recreateElementLinkForField(field);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_AD_Field.COLUMNNAME_AD_Name_ID, I_AD_Field.COLUMNNAME_AD_Column_ID })
	public void onBeforeSave_WhenNameOrColumnChanged(final I_AD_Field field)
	{
		updateFieldFromElement(field);
	}

	private void updateFieldFromElement(final I_AD_Field field)
	{
		final AdElementId fieldElementId = getFieldElementIdOrNull(field);
		if (fieldElementId == null)
		{
			// Nothing to do. the element was not yet saved.
			return;
		}

		final I_AD_Element fieldElement = Services.get(IADElementDAO.class).getById(fieldElementId.getRepoId());

		field.setName(fieldElement.getName());
		field.setDescription(fieldElement.getDescription());
		field.setHelp(fieldElement.getHelp());
	}

	private void updateTranslationsForElement(final I_AD_Field field)
	{
		final AdElementId fieldElementId = getFieldElementIdOrNull(field);
		if (fieldElementId == null)
		{
			// Nothing to do. the element was not yet saved.
			return;
		}

		final IElementTranslationBL elementTranslationBL = Services.get(IElementTranslationBL.class);
		elementTranslationBL.updateFieldTranslationsFromAD_Name(fieldElementId);
	}

	private AdElementId getFieldElementIdOrNull(final I_AD_Field field)
	{
		if (field.getAD_Name_ID() > 0)
		{
			return AdElementId.ofRepoId(field.getAD_Name_ID());
		}
		else if (field.getAD_Column_ID() > 0)
		{
			// the AD_Name_ID was set to null. Get back to the values from the AD_Column
			final I_AD_Column fieldColumn = field.getAD_Column();
			return AdElementId.ofRepoId(fieldColumn.getAD_Element_ID());
		}
		else
		{
			// Nothing to do. the element was not yet saved.
			return null;
		}
	}

	private void recreateElementLinkForField(final I_AD_Field field)
	{
		final AdFieldId adFieldId = AdFieldId.ofRepoIdOrNull(field.getAD_Field_ID());
		if (adFieldId != null)
		{
			final IElementLinkBL elementLinksService = Services.get(IElementLinkBL.class);
			elementLinksService.recreateADElementLinkForFieldId(adFieldId);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void onBeforeFieldDelete(final I_AD_Field field)
	{
		final AdFieldId adFieldId = AdFieldId.ofRepoId(field.getAD_Field_ID());

		final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
		adWindowDAO.deleteUIElementsByFieldId(adFieldId);

		final IElementLinkBL elementLinksService = Services.get(IElementLinkBL.class);
		elementLinksService.deleteExistingADElementLinkForFieldId(adFieldId);
	}
}
