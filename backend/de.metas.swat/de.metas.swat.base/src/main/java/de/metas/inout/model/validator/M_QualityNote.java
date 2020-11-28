package de.metas.inout.model.validator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.ModelValidator;

import de.metas.inout.api.IQualityNoteDAO;
import de.metas.inout.model.I_M_QualityNote;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * 
 * This intercepts actions on M_QualityNote table and translates them in the M_AttributeValue entries linked with the qualityNotes
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Interceptor(I_M_QualityNote.class)
public class M_QualityNote
{

	/**
	 * When a new M_QualityNote entry is created, also create an M_AttributeValue entry with the same Value, Name and IsActive values
	 * 
	 * @param qualityNote
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void createOnNew(final I_M_QualityNote qualityNote)
	{
		final IQualityNoteDAO qualityNoteDAO = Services.get(IQualityNoteDAO.class);
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

		attributesRepo.createAttributeValue(AttributeListValueCreateRequest.builder()
				.attributeId(qualityNoteDAO.getQualityNoteAttributeId())
				.value(qualityNote.getValue())
				.name(qualityNote.getName())
				.active(qualityNote.isActive())
				.build());
	}

	/**
	 * When an M_QualityNote entry is deleted, also delete the linked M_AttributeValue entry
	 * 
	 * @param qualityNote
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void onDelete(final I_M_QualityNote qualityNote)
	{
		final IQualityNoteDAO qualityNoteDAO = Services.get(IQualityNoteDAO.class);

		// Note: the check for null is not needed. The deletion will only happen if the M_AttributeValue entry exists, any way.
		qualityNoteDAO.deleteAttribueValueForQualityNote(qualityNote);
	}

	/**
	 * Modify the linked M_AttributeValue entry each time an M_QualityNote entry has its IsActive or Name values modified.
	 * 
	 * @param qualityNote
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_M_QualityNote.COLUMNNAME_Name, I_M_QualityNote.COLUMNNAME_IsActive })
	public void onChange(final I_M_QualityNote qualityNote)
	{
		final IQualityNoteDAO qualityNoteDAO = Services.get(IQualityNoteDAO.class);

		qualityNoteDAO.modifyAttributeValueName(qualityNote);
	}
}
