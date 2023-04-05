/*
 * #%L
 * de.metas.acct.base
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

UPDATE c_bp_bankaccount_acct
SET realizedgain_acct=schemaDefault.realizedgain_acct,
    realizedloss_acct=schemaDefault.realizedloss_acct,
    Updated=TO_TIMESTAMP('2023-04-03 17:30:55', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
FROM c_bp_bankaccount_acct as bankAccount
         INNER JOIN c_acctschema_default schemaDefault on bankAccount.c_acctschema_id = schemaDefault.c_acctschema_id
WHERE bankAccount.realizedgain_acct is null
  OR bankAccount.realizedloss_acct is null
  and c_bp_bankaccount_acct.c_bp_bankaccount_acct_id = bankAccount.c_bp_bankaccount_acct_id;