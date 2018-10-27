package de.metas.materialtracking.ch.lagerkonf;

/*
 * #%L
 * de.metas.materialtracking
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

import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_AdditionalFee;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_Month_Adj;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_ProcessingFee;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_Version;
import de.metas.util.ISingletonService;

public interface IQualityInspLagerKonfDAO extends ISingletonService
{
	List<I_M_QualityInsp_LagerKonf_Month_Adj> retriveMonthAdjustments(I_M_QualityInsp_LagerKonf_Version qualityInspLagerKonfVersion);

	List<I_M_QualityInsp_LagerKonf_ProcessingFee> retriveProcessingFees(I_M_QualityInsp_LagerKonf_Version qualityInspLagerKonfVersion);

	List<I_M_QualityInsp_LagerKonf_AdditionalFee> retriveAdditionalFees(I_M_QualityInsp_LagerKonf_Version qualityInspLagerKonfVersion);
}
