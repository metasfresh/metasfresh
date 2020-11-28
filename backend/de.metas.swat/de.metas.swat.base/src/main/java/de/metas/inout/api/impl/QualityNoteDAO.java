package de.metas.inout.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.AttributeListValueChangeRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;

import de.metas.inout.QualityNoteId;
import de.metas.inout.api.IQualityNoteDAO;
import de.metas.inout.model.I_M_QualityNote;
import de.metas.util.Services;
import lombok.NonNull;

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

public class QualityNoteDAO implements IQualityNoteDAO
{
	public static final AttributeCode QualityNoteAttribute = AttributeCode.ofString("QualityNotice");

	@Override
	public I_M_QualityNote getById(@NonNull final QualityNoteId qualityNoteId)
	{
		return loadOutOfTrx(qualityNoteId, I_M_QualityNote.class);
	}

	@Override
	public I_M_QualityNote retrieveQualityNoteForValue(final Properties ctx, final String value)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_QualityNote.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_QualityNote.COLUMN_Value, value)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_QualityNote.class);
	}

	@Override
	public AttributeId getQualityNoteAttributeId()
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		return attributeDAO.retrieveAttributeIdByValueOrNull(QualityNoteAttribute);
	}

	@Override
	public AttributeListValue retrieveAttribueValueForQualityNote(final I_M_QualityNote qualityNote)
	{
		final AttributeId attributeId = getQualityNoteAttributeId();
		return Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(attributeId, qualityNote.getValue());
	}

	@Override
	public void deleteAttribueValueForQualityNote(final I_M_QualityNote qualityNote)
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

		final AttributeId attributeId = getQualityNoteAttributeId();
		attributesRepo.deleteAttributeValueByCode(attributeId, qualityNote.getValue());
	}

	@Override
	public void modifyAttributeValueName(final I_M_QualityNote qualityNote)
	{
		final AttributeListValue attributeValueForQualityNote = retrieveAttribueValueForQualityNote(qualityNote);
		if (attributeValueForQualityNote == null)
		{
			// shall not happen. All M_QualityNote entries shall have a similar M_AttributeValue
			return;
		}

		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		attributesRepo.changeAttributeValue(AttributeListValueChangeRequest.builder()
				.id(attributeValueForQualityNote.getId())
				.name(qualityNote.getName())
				.active(qualityNote.isActive())
				.build());
	}

}
