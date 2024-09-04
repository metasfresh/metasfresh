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

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 10 -> primary.Description
-- Column: SAP_GLJournal.Description
-- 2024-08-19T10:38:15.811Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-08-19 13:38:15.811','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614522
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 10 -> accounting schema, posting type.Accounting Schema
-- Column: SAP_GLJournal.C_AcctSchema_ID
-- 2024-08-19T10:38:15.819Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-08-19 13:38:15.819','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614540
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 10 -> accounting schema, posting type.Posting Type
-- Column: SAP_GLJournal.PostingType
-- 2024-08-19T10:38:15.826Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-08-19 13:38:15.826','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614524
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 10 -> currency.Currency
-- Column: SAP_GLJournal.C_Currency_ID
-- 2024-08-19T10:38:15.833Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-08-19 13:38:15.833','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614525
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 10 -> control amts.Total Debit
-- Column: SAP_GLJournal.TotalDr
-- 2024-08-19T10:38:15.841Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-08-19 13:38:15.84','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614527
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 10 -> control amts.Total Credit
-- Column: SAP_GLJournal.TotalCr
-- 2024-08-19T10:38:15.848Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-08-19 13:38:15.848','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614528
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 20 -> doc status.Status
-- Column: SAP_GLJournal.DocStatus
-- 2024-08-19T10:38:15.854Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-08-19 13:38:15.854','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614533
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 20 -> posting.Posting status
-- Column: SAP_GLJournal.Posted
-- 2024-08-19T10:38:15.861Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-08-19 13:38:15.861','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614534
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 20 -> org.Section Code
-- Column: SAP_GLJournal.M_SectionCode_ID
-- 2024-08-19T10:38:15.868Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-08-19 13:38:15.868','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614536
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 20 -> org.Organisation
-- Column: SAP_GLJournal.AD_Org_ID
-- 2024-08-19T10:38:15.875Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-08-19 13:38:15.875','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614537
;

