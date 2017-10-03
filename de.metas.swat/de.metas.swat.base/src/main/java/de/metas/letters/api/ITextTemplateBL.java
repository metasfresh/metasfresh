package de.metas.letters.api;

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


import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.letters.model.I_AD_BoilerPlate;
import de.metas.letters.model.I_C_Letter;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.letters.spi.ILetterProducer;

public interface ITextTemplateBL extends ISingletonService
{
	/**
	 * Return available templates for logged in user. Read-only templates are also returned
	 * 
	 * @return list of available {@link I_AD_BoilerPlate}s
	 */
	List<I_AD_BoilerPlate> getAvailableLetterTemplates();

	String parseText(Properties ctx, String text, boolean isEmbeded, BoilerPlateContext attributes, String trxName);

	void setLetterBodyParsed(I_C_Letter letter, BoilerPlateContext attributes);

	void setLocationContactAndOrg(I_C_Letter letter);

	byte[] createPDF(LetterPDFCreateRequest request);

	/**
	 * 
	 * @param letter
	 * @return true if letter is empty or subject and body are missing
	 */
	boolean isEmpty(I_C_Letter letter);

	/**
	 * Sets AD_BoilerPlate field, but also LetterSubject and LetterBody fields.
	 * 
	 * @param letter
	 * @param textTemplate
	 */
	void setAD_BoilerPlate(I_C_Letter letter, I_AD_BoilerPlate textTemplate);

	boolean canUpdate(I_AD_BoilerPlate textTemplate);

	/**
	 * Mass produce letters from given source using given producer.
	 * 
	 * @param source
	 * @param producer
	 */
	<T> void createLetters(Iterator<T> source, ILetterProducer<T> producer);

	<T> I_C_Letter createLetter(T item, ILetterProducer<T> producer);

}
