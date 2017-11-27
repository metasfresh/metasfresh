/**
 * 
 */
package org.adempiere.impexp;

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



/**
 * Import Validator Interface
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2788276 ] Data Import Validator
 * 				https://sourceforge.net/tracker/?func=detail&aid=2788276&group_id=176962&atid=879335
 */
public interface IImportInterceptor
{
	/** Event triggered before all import records are validated */
	public static final int TIMING_BEFORE_VALIDATE = 10;
	/** Event triggered after all import records are validated */
	public static final int TIMING_AFTER_VALIDATE = 20;
	/** Event triggered before an import record is processed */
	public static final int TIMING_BEFORE_IMPORT = 30;
	/** Event triggered after an import record is processed */
	public static final int TIMING_AFTER_IMPORT = 40;
	
	public void onImport(IImportProcess<?> process, Object importModel, Object targetModel, int timing);
}
