package de.metas.document.refid.api;

/*
 * #%L
 * de.metas.document.refid
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

import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.spi.IReferenceNoGenerator;

import java.util.List;

public interface IReferenceNoGeneratorInstance
{
	/**
	 * @return generated reference number or {@link IReferenceNoGenerator#REFERENCENO_None} if no reference number was generated
	 */
	String generateReferenceNo(Object sourceModel);

	I_C_ReferenceNo_Type getType();

	IReferenceNoGenerator getGenerator();

	List<Integer> getAssignedTableIds();

}
