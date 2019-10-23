package org.compiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;


/**
 *  Load html-src (text) stored in JAR, e.g. to load a style-sheet
 */
public class StoredHtmlSrc extends MultiPartElement implements Printable {
	
	private static final long serialVersionUID = 50303119083373138L;
	
	/**	Logger					*/
	protected static Logger 	log = LogManager.getLogger(StoredHtmlSrc.class.getName());
	
	
	/**
	 *  Load html-src (text) stored in JAR, e.g. to load a style-sheet
	 *  @param elementType e.g. elementType=STYLE
	 *  @param srcLocation package/filename in SRC e.g. org/compiere/util/standard.css
	 *  todo if needed: also write for SinglePartElement and StringElement
	 */
	public StoredHtmlSrc(String elementType, String srcLocation) {
		this.setElementType(elementType);
		
		URL url = getClass().getClassLoader().getResource(srcLocation);
		if (url==null) {
			log.warn("failed to load html-src: " + srcLocation);
			return;
		}			
		InputStreamReader ins;
		try {
			ins = new InputStreamReader(url.openStream());
			BufferedReader bufferedReader = new BufferedReader( ins );
			String cssLine;
			String result="";
			while ((cssLine = bufferedReader.readLine()) != null) 
				result+=cssLine;
			this.setTagText(result);
		} catch (IOException e1) {
			log.warn("failed to load html-src: " + srcLocation);
		}
	}
}
