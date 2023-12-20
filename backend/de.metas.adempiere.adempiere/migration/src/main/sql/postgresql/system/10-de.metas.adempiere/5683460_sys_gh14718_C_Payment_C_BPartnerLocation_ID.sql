-- Column: C_Payment.C_BPartner_Location_ID
-- 2023-03-30T21:59:31.369Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586382,189,0,18,159,335,'C_BPartner_Location_ID',TO_TIMESTAMP('2023-03-31 00:59:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort',0,0,TO_TIMESTAMP('2023-03-31 00:59:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-30T21:59:31.858Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586382 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-30T21:59:32.115Z
/* DDL */  select update_Column_Translation_From_AD_Element(189) 
;

-- 2023-03-30T21:59:35.710Z
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE public.C_Payment ADD COLUMN C_BPartner_Location_ID NUMERIC(10)')
;

-- 2023-03-30T21:59:36.173Z
ALTER TABLE C_Payment ADD CONSTRAINT CBPartnerLocation_CPayment FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- 2023-03-30T22:00:29.319Z
INSERT INTO t_alter_column values('c_payment','C_BPartner_Location_ID','NUMERIC(10)',null,null)
;

-- Field: Zahlung(195,D) -> Zahlung(330,D) -> Standort
-- Column: C_Payment.C_BPartner_Location_ID
-- 2023-03-30T22:01:26.818Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586382,713583,0,330,TO_TIMESTAMP('2023-03-31 01:01:26','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'D','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2023-03-31 01:01:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T22:01:26.825Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713583 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-30T22:01:26.849Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2023-03-30T22:01:26.907Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713583
;

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

-- 2023-03-30T22:01:26.911Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713583)
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> default.Standort
-- Column: C_Payment.C_BPartner_Location_ID
-- 2023-03-30T22:01:53.926Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,713583,0,330,616491,540064,'F',TO_TIMESTAMP('2023-03-31 01:01:53','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Standort',120,0,0,TO_TIMESTAMP('2023-03-31 01:01:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> default.Standort
-- Column: C_Payment.C_BPartner_Location_ID
-- 2023-03-30T22:02:12.056Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2023-03-31 01:02:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616491
;

-- Column: C_Payment.C_BPartner_Location_ID
-- 2023-03-30T22:11:37.799Z
UPDATE AD_Column SET AD_Val_Rule_ID=131,Updated=TO_TIMESTAMP('2023-03-31 01:11:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586382
;

-- Column: Fact_Acct.C_BPartner_Location_ID
-- 2023-03-30T22:12:18.136Z
UPDATE AD_Column SET AD_Val_Rule_ID=131,Updated=TO_TIMESTAMP('2023-03-31 01:12:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586378
;

