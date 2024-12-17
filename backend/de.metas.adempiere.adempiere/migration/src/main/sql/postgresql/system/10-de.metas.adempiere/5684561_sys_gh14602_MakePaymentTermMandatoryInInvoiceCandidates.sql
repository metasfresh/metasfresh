/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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


-- make it mandatory


-- 2023-04-07T10:25:52.683Z
INSERT INTO t_alter_column values('c_invoice_candidate','C_PaymentTerm_ID','NUMERIC(10)',null,null)
;

-- 2023-04-07T10:25:52.693Z
INSERT INTO t_alter_column values('c_invoice_candidate','C_PaymentTerm_ID',null,'NOT NULL',null)
;