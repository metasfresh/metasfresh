package de.metas.inout.api;

import java.util.Properties;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;

import de.metas.inout.QualityNoteId;
import de.metas.inout.model.I_M_QualityNote;
import de.metas.util.ISingletonService;

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

public interface IQualityNoteDAO extends ISingletonService
{
	I_M_QualityNote getById(QualityNoteId qualityNoteId);

	/**
	 * 
	 * @param ctx
	 * @param name
	 * @return the qualityNote for the given value if found, null otherwise
	 */
	I_M_QualityNote retrieveQualityNoteForValue(Properties ctx, String value);

	/**
	 * The I_M_Attribute QualityNote
	 * 
	 * @param ctx
	 * @return
	 */
	AttributeId getQualityNoteAttributeId();

	/**
	 * @param qualityNote
	 * @return the qualityNote attribute value that fits the qualityNote entry if found, null otherwise
	 */
	AttributeListValue retrieveAttribueValueForQualityNote(I_M_QualityNote qualityNote);

	/**
	 * Delete the M_Attribute value that fits the given QualityNote
	 * 
	 * @param qualityNote
	 */
	void deleteAttribueValueForQualityNote(I_M_QualityNote qualityNote);

	/**
	 * Set the name form thequalityNote to the corresponding attribute value. Also set the right value for IsActive flag
	 * 
	 * @param qualityNote
	 */
	void modifyAttributeValueName(I_M_QualityNote qualityNote);
}
