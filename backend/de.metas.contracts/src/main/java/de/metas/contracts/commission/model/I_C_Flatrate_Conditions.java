package de.metas.contracts.commission.model;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

import org.adempiere.model.ModelColumn;

public interface I_C_Flatrate_Conditions extends de.metas.contracts.model.I_C_Flatrate_Conditions
{
	String COLUMNNAME_C_HierarchyCommissionSettings_ID = "C_HierarchyCommissionSettings_ID";

	int getC_HierarchyCommissionSettings_ID();

	void setC_HierarchyCommissionSettings_ID(int C_HierarchyCommissionSettings_ID);

	/**
	 * Set Mediated commission settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_MediatedCommissionSettings_ID(int C_MediatedCommissionSettings_ID);

	/**
	 * Get Mediated commission settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_MediatedCommissionSettings_ID();

	String COLUMNNAME_C_MediatedCommissionSettings_ID = "C_MediatedCommissionSettings_ID";

	/**
	 * Set C_Customer_Trade_Margin.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Customer_Trade_Margin_ID (int C_Customer_Trade_Margin_ID);

	/**
	 * Get C_Customer_Trade_Margin.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Customer_Trade_Margin_ID();

	ModelColumn<de.metas.contracts.model.I_C_Flatrate_Conditions, I_C_Customer_Trade_Margin> COLUMN_C_Customer_Trade_Margin_ID = new ModelColumn<>(de.metas.contracts.model.I_C_Flatrate_Conditions.class, "C_Customer_Trade_Margin_ID", de.metas.contracts.commission.model.I_C_Customer_Trade_Margin.class);
	String COLUMNNAME_C_Customer_Trade_Margin_ID = "C_Customer_Trade_Margin_ID";


	/**
	 * Set License fee settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_LicenseFeeSettings_ID (int C_LicenseFeeSettings_ID);

	/**
	 * Get License fee settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_LicenseFeeSettings_ID();

	ModelColumn<de.metas.contracts.model.I_C_Flatrate_Conditions, Object> COLUMN_C_LicenseFeeSettings_ID = new ModelColumn<>(de.metas.contracts.model.I_C_Flatrate_Conditions.class, "C_LicenseFeeSettings_ID", null);
	String COLUMNNAME_C_LicenseFeeSettings_ID = "C_LicenseFeeSettings_ID";

}
