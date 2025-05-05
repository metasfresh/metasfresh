/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2023 metas GmbH
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

-- Column: Fact_Acct.AccountConceptualName
-- 2023-02-09T14:44:10.055Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-09 16:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585960
;

-- 2023-02-09T14:44:11.665Z
INSERT INTO t_alter_column values('fact_acct','AccountConceptualName','VARCHAR(255)',null,null)
;

-- 2023-02-09T14:44:11.671Z
INSERT INTO t_alter_column values('fact_acct','AccountConceptualName',null,'NOT NULL',null)
;

