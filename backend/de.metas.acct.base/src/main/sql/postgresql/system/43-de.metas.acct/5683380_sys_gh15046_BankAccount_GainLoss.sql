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
-- 2023-03-30T14:03:42.940Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586380,535,0,25,540643,'RealizedGain_Acct',TO_TIMESTAMP('2023-03-30 17:03:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Realized Gain Account','D',0,10,'The Realized Gain Account indicates the account to be used when recording gains achieved from currency revaluation that have been realized.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Realized Gain Acct','NP',0,0,TO_TIMESTAMP('2023-03-30 17:03:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-30T14:03:42.943Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586380 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-30T14:03:43.994Z
/* DDL */  select update_Column_Translation_From_AD_Element(535) 
;

-- 2023-03-30T14:03:49.816Z
/* DDL */ SELECT public.db_alter_table('C_BP_BankAccount_Acct','ALTER TABLE public.C_BP_BankAccount_Acct ADD COLUMN RealizedGain_Acct NUMERIC(10)')
;

-- 2023-03-30T14:03:49.848Z
ALTER TABLE C_BP_BankAccount_Acct ADD CONSTRAINT RealizedGainA_CBPBankAccountAcct FOREIGN KEY (RealizedGain_Acct) REFERENCES public.C_ValidCombination DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BP_BankAccount_Acct.RealizedLoss_Acct
-- 2023-03-30T14:04:08.611Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586381,536,0,25,540643,'RealizedLoss_Acct',TO_TIMESTAMP('2023-03-30 17:04:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Realized Loss Account','D',0,10,'The Realized Loss Account indicates the account to be used when recording losses incurred from currency revaluation that have yet to be realized.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Realized Loss Acct',0,0,TO_TIMESTAMP('2023-03-30 17:04:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-30T14:04:08.614Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586381 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-30T14:04:09.222Z
/* DDL */  select update_Column_Translation_From_AD_Element(536) 
;

-- 2023-03-30T14:04:10.692Z
/* DDL */ SELECT public.db_alter_table('C_BP_BankAccount_Acct','ALTER TABLE public.C_BP_BankAccount_Acct ADD COLUMN RealizedLoss_Acct NUMERIC(10)')
;

-- 2023-03-30T14:04:10.698Z
ALTER TABLE C_BP_BankAccount_Acct ADD CONSTRAINT RealizedLossA_CBPBankAccountAcct FOREIGN KEY (RealizedLoss_Acct) REFERENCES public.C_ValidCombination DEFERRABLE INITIALLY DEFERRED
;

-- Field: Bank Account(540337,D) -> Buchführung(540813,D) -> Realized Gain Acct
-- Column: C_BP_BankAccount_Acct.RealizedGain_Acct
-- 2023-03-30T14:04:30.305Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586380,713581,0,540813,TO_TIMESTAMP('2023-03-30 17:04:30','YYYY-MM-DD HH24:MI:SS'),100,'Realized Gain Account',10,'D','The Realized Gain Account indicates the account to be used when recording gains achieved from currency revaluation that have been realized.','Y','N','N','N','N','N','N','N','Realized Gain Acct',TO_TIMESTAMP('2023-03-30 17:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T14:04:30.308Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713581 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-30T14:04:30.313Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(535) 
;

-- 2023-03-30T14:04:30.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713581
;

-- 2023-03-30T14:04:30.346Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713581)
;

-- Field: Bank Account(540337,D) -> Buchführung(540813,D) -> Realized Loss Acct
-- Column: C_BP_BankAccount_Acct.RealizedLoss_Acct
-- 2023-03-30T14:04:37.826Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586381,713582,0,540813,TO_TIMESTAMP('2023-03-30 17:04:37','YYYY-MM-DD HH24:MI:SS'),100,'Realized Loss Account',10,'D','The Realized Loss Account indicates the account to be used when recording losses incurred from currency revaluation that have yet to be realized.','Y','N','N','N','N','N','N','N','Realized Loss Acct',TO_TIMESTAMP('2023-03-30 17:04:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T14:04:37.829Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713582 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-30T14:04:37.834Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(536) 
;

-- 2023-03-30T14:04:37.844Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713582
;

-- 2023-03-30T14:04:37.853Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713582)
;

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

-- UI Element: Bank Account(540337,D) -> Buchführung(540813,D) -> main -> 10 -> default.Realized Gain Acct
-- Column: C_BP_BankAccount_Acct.RealizedGain_Acct
-- 2023-03-30T14:05:43.674Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,713581,0,540813,616489,540334,'F',TO_TIMESTAMP('2023-03-30 17:05:43','YYYY-MM-DD HH24:MI:SS'),100,'Realized Gain Account','The Realized Gain Account indicates the account to be used when recording gains achieved from currency revaluation that have been realized.','Y','N','N','Y','N','N','N',0,'Realized Gain Acct',170,0,0,TO_TIMESTAMP('2023-03-30 17:05:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Bank Account(540337,D) -> Buchführung(540813,D) -> main -> 10 -> default.Realized Loss Acct
-- Column: C_BP_BankAccount_Acct.RealizedLoss_Acct
-- 2023-03-30T14:05:52.906Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,713582,0,540813,616490,540334,'F',TO_TIMESTAMP('2023-03-30 17:05:52','YYYY-MM-DD HH24:MI:SS'),100,'Realized Loss Account','The Realized Loss Account indicates the account to be used when recording losses incurred from currency revaluation that have yet to be realized.','Y','N','N','Y','N','N','N',0,'Realized Loss Acct',180,0,0,TO_TIMESTAMP('2023-03-30 17:05:52','YYYY-MM-DD HH24:MI:SS'),100)
;

