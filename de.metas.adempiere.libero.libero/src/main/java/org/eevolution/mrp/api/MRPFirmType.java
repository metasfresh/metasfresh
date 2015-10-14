package org.eevolution.mrp.api;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eevolution.model.X_PP_MRP;

public enum MRPFirmType
{
	/**
	 * Firm MRP records (i.e DocStatus=IP, CO).
	 * 
	 * synonym: released
	 */
	Firm(X_PP_MRP.DOCSTATUS_InProgress, X_PP_MRP.DOCSTATUS_Completed),

	/**
	 * Not Firm MRP records (i.e DocStatus=DR)
	 */
	NotFirm(X_PP_MRP.DOCSTATUS_Drafted),

	/**
	 * Firm and Not Firm MRP records (i.e. DocStatus=DR, IP, CO)
	 */
	FirmAndNotFirm(X_PP_MRP.DOCSTATUS_Drafted, X_PP_MRP.DOCSTATUS_InProgress, X_PP_MRP.DOCSTATUS_Completed),

	/**
	 * MRP records for closed documents (i.e. DocStatus=CL)
	 */
	Closed(X_PP_MRP.DOCSTATUS_Closed),

	/**
	 * MRP records for voided documents (i.e. DocStatus=VO, RE)
	 */
	Voided(X_PP_MRP.DOCSTATUS_Voided, X_PP_MRP.DOCSTATUS_Reversed),

	;

	//
	// MRPFirmType implementation:
	//

	private final List<String> docStatuses;

	MRPFirmType(String... docStatuses)
	{
		this.docStatuses = Collections.unmodifiableList(Arrays.asList(docStatuses));
	}

	public final List<String> getDocStatuses()
	{
		return docStatuses;
	}

	public final boolean hasDocStatus(final String docStatus)
	{
		return docStatuses.contains(docStatus);
	}
}
