/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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

-- 2024-08-01T06:52:32.172Z
UPDATE AD_Element SET ColumnName='SurchargeAmt',Updated=TO_TIMESTAMP('2024-08-01 08:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583195
;

-- 2024-08-01T06:52:32.202Z
UPDATE AD_Column SET ColumnName='SurchargeAmt' WHERE AD_Element_ID=583195
;

-- 2024-08-01T06:52:32.229Z
UPDATE AD_Process_Para SET ColumnName='SurchargeAmt' WHERE AD_Element_ID=583195
;

-- 2024-08-01T06:52:32.364Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583195,'de_DE') 
;

