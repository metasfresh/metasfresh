package de.metas.edi.process.export;

/*
 * #%L
 * de.metas.edi
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


import java.util.List;

import de.metas.edi.model.I_EDI_Document;

/**
 * Interface whose implementors can export a certain document to EDI.
 * 
 *
 * @param <T>
 */
public interface IExport<T extends I_EDI_Document>
{
	/**
	 * Export EDI data via Replication to ESB.
	 * 
	 * @return exceptions which occurred, if any
	 */
	List<Exception> doExport();

	/**
	 * @return the document registered in the export definition
	 */
	T getDocument();

	/**
	 * @return table identifier
	 */
	String getTableIdentifier();
}
