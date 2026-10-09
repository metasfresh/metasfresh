/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- old
-- 'C_Flatrate_Term.C_Flatrate_Term_ID IN (SELECT ft.C_Flatrate_Term_ID
--  FROM C_Flatrate_Term ft
--      INNER JOIN C_Flatrate_Conditions c ON (ft.c_flatrate_conditions_id = c.c_flatrate_conditions_id AND c.isActive = 'Y')
--      INNER JOIN ModCntr_Settings mc ON (@Harvesting_Year_ID/0@>0
--      AND mc.ModCntr_Settings_ID = c.ModCntr_Settings_ID
--      AND mc.isActive = 'Y'
--      AND (mc.M_Raw_Product_ID = @M_Product_ID@ OR mc.M_Processed_Product_ID = @M_Product_ID@)
--      AND mc.C_Year_ID = @Harvesting_Year_ID/0@ AND mc.isSOTrx = 'N')
--  WHERE ft.type_conditions = 'ModularContract' AND ft.isSOTrx = 'N' )'

-- Name: Purchase_Modular_Flatrate_Term_ID
-- 2025-03-28T08:59:51.275Z
UPDATE AD_Val_Rule SET Code='C_Flatrate_Term.C_Flatrate_Term_ID IN (SELECT ft.C_Flatrate_Term_ID FROM C_Flatrate_Term ft
    INNER JOIN C_Flatrate_Conditions c ON ft.c_flatrate_conditions_id = c.c_flatrate_conditions_id
    INNER JOIN ModCntr_Settings mc ON @Harvesting_Year_ID/0@>0 AND mc.ModCntr_Settings_ID = c.ModCntr_Settings_ID AND (mc.M_Raw_Product_ID = @M_Product_ID@ OR mc.M_Processed_Product_ID = @M_Product_ID@) AND mc.C_Year_ID = @Harvesting_Year_ID/0@ AND mc.isSOTrx = ''N''
    INNER JOIN C_Order o ON ft.c_order_term_id = o.c_order_id AND o.m_warehouse_id = @M_Warehouse_ID@
WHERE ft.type_conditions = ''ModularContract'' AND ft.isSOTrx = ''N'' AND ft.contractstatus <> ''Vo'' )',Updated=TO_TIMESTAMP('2025-03-28 09:59:51.273','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540694
;
