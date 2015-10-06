package de.metas.commission.service;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Product;

import de.metas.commission.model.I_C_AdvComSponsorParam;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionTerm;

public interface ICommissionTermDAO extends ISingletonService
{
	String PARAM_TABLE = I_C_AdvComSponsorParam.Table_Name;

	void deleteParameters(I_C_AdvCommissionTerm term);

	void createParameters(I_C_AdvCommissionTerm term);

	I_C_AdvCommissionTerm retrieveSalesRepFactCollector(Properties ctx, int contractId, String trxName);

	I_C_AdvCommissionTerm retrieveFor(I_C_AdvCommissionCondition contract, I_C_AdvComSystem_Type comSystemType);

	List<I_C_AdvCommissionTerm> retrieveForPayrollConcept(Properties ctx, int payrollConceptId, String trxName);

	List<I_C_AdvCommissionTerm> retrieveFor(I_C_AdvCommissionCondition condition);

	List<I_C_AdvCommissionTerm> retrieveAll(Properties ctx, int orgId, String trxName);

	List<I_C_AdvCommissionTerm> retrieveAll(Properties ctx, I_C_AdvComSystem_Type type, int adOrgId, String trxName);

	List<I_C_AdvCommissionTerm> retrieveTermsForProductAndSystemType(I_C_AdvComSystem_Type type, I_M_Product product);

	I_C_AdvCommissionTerm retrieveTermForSponsorAndProductAndSystemType(ICommissionContext comCtx);

}
