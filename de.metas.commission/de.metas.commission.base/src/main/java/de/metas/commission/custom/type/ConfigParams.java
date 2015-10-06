package de.metas.commission.custom.type;

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


import org.compiere.util.DisplayType;

import de.metas.adempiere.util.Parameter;

public final class ConfigParams
{

	public static final String NAME_LEVEL_MAX = "LEVEL_MAX";
	public static final Parameter PARAM_LEVEL_MAX = new Parameter(ConfigParams.NAME_LEVEL_MAX, "Max. Provisionsebene", "", DisplayType.Integer, 0);
	public static final String PARAM_COMMISSION_ = "Commission_Percent_";
	public static final String PARAM_HIERARCHY_SKIP_CUSTOMERS = "Hierarchy_Skip_Customers";

	/**
	 * This parameter actually makes only sense with difference commissions.
	 */
	public static final String PARAM_SUBTRACT_POLINE_DISCOUNT = "SubtractPOLineDiscount";

	public static final String PARAM_MIN_APV_FOR_ = "Min_APV_for_";

	public static final String PARAM_MAX_COMMISSION = "Max_Commission";

	public static final String K = "K";
	public static final String SG1A = "1a";
	public static final String SG1B = "1b";
	public static final String SG2A = "2a";
	public static final String SG2B = "2b";
	public static final String SG2C = "2c";
	public static final String SG2D = "2d";
	public static final String SG2E = "2e";
	public static final String SG3A = "3a";
	public static final String SG3B = "3b";
	public static final String SG3C = "3c";
	public static final String SG3D = "3d";
	public static final String SG3E = "3e";

	public static final String PARAM_6EDL12M_FOR_ = "6EDL12M_for_";
	public static final String PARAM_DS_OF_SG_ = "DiscountSchema_of_";
	public static final String PARAM_PERIODS_LOOKBACK = "Periods_Lookback";
	public static final String PARAM_ADV_RANK_RELEVANT = "ADV_Rank_Relevant";
	public static final String PARAM_ADV_MAX_LEVEL = "ADV_MAX_LEVEL";

	public static final String PARAM_COMMISSION_PERCENT = "CommissionPercent";
	public static final String PARAM_COMMISSION_POINTS = "CommissionPoints";

	/**
	 * Shall we stop commission computation after first sales rep was encounted
	 * 
	 * @task http://dewiki908/mediawiki/index.php/04632_Provision_-_Option%2C_dass_nur_der_erste_VPn_HSP_bekommt_%282013072610000021%29
	 */
	public static final String PARAM_STOP_AFTER_FIRST_SALES_REP = "StopAfterFirstSalesRep";

	private ConfigParams()
	{
	}

}
