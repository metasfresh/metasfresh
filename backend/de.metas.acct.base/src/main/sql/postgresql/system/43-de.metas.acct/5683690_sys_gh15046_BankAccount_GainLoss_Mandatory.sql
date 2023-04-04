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

-- Column: C_BP_BankAccount_Acct.RealizedGain_Acct
-- 2023-04-03T10:22:41.091Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-04-03 13:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586380
;

-- 2023-04-03T10:22:43.486Z
INSERT INTO t_alter_column values('c_bp_bankaccount_acct','RealizedGain_Acct','NUMERIC(10)',null,null)
;

-- 2023-04-03T10:22:43.497Z
INSERT INTO t_alter_column values('c_bp_bankaccount_acct','RealizedGain_Acct',null,'NOT NULL',null)
;

-- Column: C_BP_BankAccount_Acct.RealizedLoss_Acct
-- 2023-04-03T10:22:49.987Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-04-03 13:22:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586381
;

-- 2023-04-03T10:22:52.346Z
INSERT INTO t_alter_column values('c_bp_bankaccount_acct','RealizedLoss_Acct','NUMERIC(10)',null,null)
;

-- 2023-04-03T10:22:52.358Z
INSERT INTO t_alter_column values('c_bp_bankaccount_acct','RealizedLoss_Acct',null,'NOT NULL',null)
;

-- UI Element: Bank Account(540337,D) -> Buchführung(540813,D) -> main -> 10 -> default.Realized Gain Acct
-- Column: C_BP_BankAccount_Acct.RealizedGain_Acct
-- 2023-04-03T10:24:05.998Z
UPDATE AD_UI_Element SET SeqNo=156,Updated=TO_TIMESTAMP('2023-04-03 13:24:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616489
;

-- UI Element: Bank Account(540337,D) -> Buchführung(540813,D) -> main -> 10 -> default.Realized Loss Acct
-- Column: C_BP_BankAccount_Acct.RealizedLoss_Acct
-- 2023-04-03T10:24:11.772Z
UPDATE AD_UI_Element SET SeqNo=157,Updated=TO_TIMESTAMP('2023-04-03 13:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616490
;

