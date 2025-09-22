/*
 * #%L
 * de.metas.acct.base
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

-- change from Fact_Acct to Fact_Acct_Transactions_View
-- without the fix there will be an SQL-error when switching to baselang de_CH, because there is already another AD_Reference_Trl with the name 'Fact_Acct'
Update AD_Reference_Trl set Updated='2025-09-22 14:43', UpdatedBy=99, Name='Fact_Acct_Transactions_View' where AD_Reference_ID=540773 and AD_Language='de_CH';