/*
 * #%L
 * de.metas.business
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

-- Name: C_Tax_For_Tax_Departure_Country
-- 2023-07-05T16:50:49.319Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541798,TO_TIMESTAMP('2023-07-05 19:50:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','C_Tax_For_Tax_Departure_Country',TO_TIMESTAMP('2023-07-05 19:50:49','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-07-05T16:50:49.337Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541798 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Tax_For_Tax_Departure_Country
-- Table: C_Tax
-- Key: C_Tax.C_Tax_ID
-- 2023-07-05T16:51:44.684Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2240,0,541798,261,TO_TIMESTAMP('2023-07-05 19:51:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N',TO_TIMESTAMP('2023-07-05 19:51:44','YYYY-MM-DD HH24:MI:SS'),100,'@C_Tax_Departure_Country_ID/-1@ <= 0 OR C_Country_ID=@C_Tax_Departure_Country_ID/-1@')
;

-- Name: VAT_Code_for_SO
-- 2023-07-05T17:04:13.238Z
UPDATE AD_Val_Rule SET Code='(C_Vat_Code.IsSoTrx =''Y'' OR C_Vat_Code.IsSoTrx IS NULL) AND C_Vat_Code.AD_Org_ID IN (@AD_Org_ID/-1@, 0) AND (@C_Tax_Departure_Country_ID/-1@ <= 0 OR C_Vat_Code.C_Tax_ID in (SELECT tax.C_Tax_ID from C_Tax tax where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@))',Updated=TO_TIMESTAMP('2023-07-05 20:04:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540610
;

-- Name: VAT_Code_for_PO
-- 2023-07-05T17:11:22.981Z
UPDATE AD_Val_Rule SET Code='(C_Vat_Code.IsSoTrx =''N'' OR C_Vat_Code.IsSoTrx IS NULL) AND C_Vat_Code.AD_Org_ID IN (@AD_Org_ID/-1@, 0) AND (@C_Tax_Departure_Country_ID/-1@ <= 0 OR C_Vat_Code.C_Tax_ID in (SELECT tax.C_Tax_ID from C_Tax tax where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@))', EntityType='de.metas.tax',Updated=TO_TIMESTAMP('2023-07-05 20:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540611
;

-- Name: C_Tax_For_Country
-- 2023-07-05T17:15:12.395Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540644,'(@C_Tax_Departure_Country_ID/-1@ <= 0 OR C_Tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@)',TO_TIMESTAMP('2023-07-05 20:15:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.tax','Y','C_Tax_For_Country','S',TO_TIMESTAMP('2023-07-05 20:15:12','YYYY-MM-DD HH24:MI:SS'),100)
;

