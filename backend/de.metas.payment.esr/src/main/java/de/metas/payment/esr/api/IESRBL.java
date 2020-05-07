package de.metas.payment.esr.api;

/*
 * #%L
 * de.metas.payment.esr
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


import org.adempiere.util.ISingletonService;

public interface IESRBL extends ISingletonService
{
	/**
	 * This method checks if the given {@code sourceModel} should receive ESR document reference strings (see package {@code de.metas.payment.esr.document}). The method assumes that the given PO has
	 * tableName = C_Invoice.
	 * 
	 * @param source
	 * @return
	 */
	boolean appliesForESRDocumentRefId(Object sourceModel);
}
