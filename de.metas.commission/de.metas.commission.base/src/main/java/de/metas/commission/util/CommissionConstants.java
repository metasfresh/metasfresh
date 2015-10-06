package de.metas.commission.util;

/*
 * #%L
 * de.metas.commission.base
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


import java.sql.Timestamp;

import org.adempiere.util.MiscUtils;

import de.metas.commission.interfaces.I_C_BPartner;

public final class CommissionConstants
{
	public static final String ENTITY_TYPE = "de.metas.commission";

	@Deprecated
	public static final int DEFAULT_PARENT_SPONSOR_ID = 64041;

	public final static Timestamp VALID_RANGE_MIN = MiscUtils.toTimeStamp("2000-01-01");

	public final static Timestamp VALID_RANGE_MAX = MiscUtils.toTimeStamp("9999-12-31");

	private CommissionConstants()
	{
	}

	/**
	 * @deprecated use {@link I_C_BPartner#COLUMNNAME_C_Sponsor_Parent_ID} instead
	 */
	@Deprecated
	public static final String C_BPartner_C_Sponsor_Parent_ID = "C_Sponsor_Parent_ID";

	public static final String C_Invoice_IS_COMMISSION_LOCK = "IsComissionLock";

	public static final String C_BPartner_IS_PARENT_SPONSOR_READWRITE = "IsParentSponsorReadWrite";

	public static final String HR_Concept_TYPE_Provider = "P";

	public static final String HR_Concept_ClassName = "ClassName";

	public static final String COMMISSON_INVOICE_DOCSUBTYPE_CALC = "CA";

	public static final String COMMISSON_INVOICE_DOCSUBTYPE_CORRECTION = "CC";
}
