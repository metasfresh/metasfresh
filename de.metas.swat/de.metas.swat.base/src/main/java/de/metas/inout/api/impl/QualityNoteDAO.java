package de.metas.inout.api.impl;

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

import de.metas.inout.api.IQualityNoteDAO;
import de.metas.inout.model.I_M_QualityNote;

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
	public static String QualityNoteAttribute = "QualityNotice";

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
	public I_M_Attribute getQualityNoteAttribute(final Properties ctx)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		return attributeDAO.retrieveAttributeByValue(ctx, QualityNoteAttribute, I_M_Attribute.class);
	}

	@Override
	public I_M_AttributeValue retrieveAttribueValueForQualityNote(final I_M_QualityNote qualityNote)
	{

		return createQueryForQualityNote(qualityNote).firstOnly(I_M_AttributeValue.class);
	}

	private IQuery<I_M_AttributeValue> createQueryForQualityNote(final I_M_QualityNote qualityNote)
	{
		final I_M_Attribute attribute = getQualityNoteAttribute(InterfaceWrapperHelper.getCtx(qualityNote));
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_AttributeValue.class, qualityNote)
				.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_M_Attribute_ID, attribute.getM_Attribute_ID())
				.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_Value, qualityNote.getValue())
				.addOnlyActiveRecordsFilter()
				.create();
	}
	
	@Override
	public void deleteAttribueValueForQualityNote(final I_M_QualityNote qualityNote)
	{

		createQueryForQualityNote(qualityNote).delete();
	}
	
	@Override
	public void modifyAttributeValueName(final I_M_QualityNote qualityNote)
	{
		final I_M_AttributeValue attribueValueForQualityNote = retrieveAttribueValueForQualityNote(qualityNote);

		final String noteName = qualityNote.getName();
		
		if(attribueValueForQualityNote.getName().compareTo(noteName) != 0)
		{
			attribueValueForQualityNote.setName(noteName);
		}
		
		InterfaceWrapperHelper.save(attribueValueForQualityNote);
	}


}
