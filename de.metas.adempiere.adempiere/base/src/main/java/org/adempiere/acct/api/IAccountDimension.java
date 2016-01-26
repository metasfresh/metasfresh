package org.adempiere.acct.api;

import org.adempiere.acct.api.impl.AcctSegmentType;

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
 * Account dimension: holds all accounting segments (AD_Org_ID, C_ElementValue_ID, M_Product_ID, C_BPartner_ID etc).
 * 
 * @author tsa
 *
 */
public interface IAccountDimension
{
	int getSegmentValue(AcctSegmentType segmentType);

	boolean isSegmentValueSet(AcctSegmentType segmentType);

	/**
	 * Creates a new {@link IAccountDimension} instance by cloning this one and applying all segment values which were defined in given <code>override</code>.
	 * 
	 * @param overrides
	 */
	IAccountDimension applyOverrides(IAccountDimension overrides);

	/** @return account alias */
	String getAlias();

	int getAD_Client_ID();

	int getAD_Org_ID();

	int getC_AcctSchema_ID();

	int getC_ElementValue_ID();

	int getC_SubAcct_ID();

	int getM_Product_ID();

	int getC_BPartner_ID();

	int getAD_OrgTrx_ID();

	int getC_LocFrom_ID();

	int getC_LocTo_ID();

	int getC_SalesRegion_ID();

	int getC_Project_ID();

	int getC_Campaign_ID();

	int getC_Activity_ID();

	int getUser1_ID();

	int getUser2_ID();

	int getUserElement1_ID();

	int getUserElement2_ID();
}
