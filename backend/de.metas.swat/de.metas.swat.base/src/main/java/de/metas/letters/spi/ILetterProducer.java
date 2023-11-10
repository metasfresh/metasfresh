package de.metas.letters.spi;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.letters.api.ITextTemplateBL;
import de.metas.letters.model.I_C_Letter;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;

/**
 * Implementations of this producer interface will be responsible of generating letters for various sources.
 * 
 * This kind of producers will be used in methods like {@link ITextTemplateBL#createLetters(java.util.Iterator, ILetterProducer)}.
 * 
 * @author tsa
 * 
 * @param <T> item's type
 */
public interface ILetterProducer<T>
{
	/**
	 * Updates the given draft letter and set specific fields like C_BPartner_ID, C_BPartner_Location_ID and C_BP_Contact_ID.
	 * 
	 * @param letterDraft
	 * @param item source item
	 * @return true if letter was updated and is valid; false if letter shall not be created
	 */
	boolean createLetter(I_C_Letter letterDraft, T item);

	/**
	 * Retrieves the text template ID to be used for creating the letter from given item.
	 * 
	 * @param item
	 * @return text template to be used
	 */
	int getBoilerplateID(T item);

	/**
	 * Create text template attributes to be used when parsing the text template for given item.
	 * 
	 * @param item
	 * @return text template attributes
	 */
	BoilerPlateContext createAttributes(T item);
}
