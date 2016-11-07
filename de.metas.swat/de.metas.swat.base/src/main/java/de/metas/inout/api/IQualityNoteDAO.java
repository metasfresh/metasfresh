package de.metas.inout.api;

import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

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

public interface IQualityNoteDAO extends ISingletonService
{

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
	I_M_Attribute getQualityNoteAttribute(Properties ctx);

	/**
	 * @param qualityNote
	 * @return the qualityNote attribute value that fits the qualityNote entry if found, null otherwise
	 */
	I_M_AttributeValue retrieveAttribueValueForQualityNote(I_M_QualityNote qualityNote);

	/**
	 * Delete the M_Attribute value that fits the given QualityNote
	 * 
	 * @param qualityNote
	 */
	void deleteAttribueValueForQualityNote(I_M_QualityNote qualityNote);

	/**
	 * Set the name form thequalityNote to the corresponding attribute value if necessary
	 * 
	 * @param qualityNote
	 */
	void modifyAttributeValueName(I_M_QualityNote qualityNote);
}
