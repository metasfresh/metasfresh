-- 2021-07-15T07:39:10.949Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575063,579498,0,30,540272,541563,540531,'RepairServicePerformed_Product_ID',TO_TIMESTAMP('2021-07-15 10:39:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.servicerepair',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Dienstleistungsaufwand',0,0,TO_TIMESTAMP('2021-07-15 10:39:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-15T07:39:10.988Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575063 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-15T07:39:11.066Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579498) 
;

-- 2021-07-15T07:39:29.814Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project_Repair_Task','ALTER TABLE public.C_Project_Repair_Task ADD COLUMN RepairServicePerformed_Product_ID NUMERIC(10)')
;

-- 2021-07-15T07:39:29.881Z
-- URL zum Konzept
ALTER TABLE C_Project_Repair_Task ADD CONSTRAINT RepairServicePerformedProduct_CProjectRepairTask FOREIGN KEY (RepairServicePerformed_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2021-07-15T07:40:20.256Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575063,650316,0,543308,TO_TIMESTAMP('2021-07-15 10:40:19','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.servicerepair','Y','N','N','N','N','N','N','N','Dienstleistungsaufwand',TO_TIMESTAMP('2021-07-15 10:40:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-15T07:40:20.295Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-15T07:40:20.333Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579498) 
;

-- 2021-07-15T07:40:20.377Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650316
;

-- 2021-07-15T07:40:20.415Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(650316)
;

-- 2021-07-15T07:40:53.284Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@Type/X@=W & @Status/X@=CO',Updated=TO_TIMESTAMP('2021-07-15 10:40:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650316
;

-- 2021-07-15T07:41:05.889Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2021-07-15 10:41:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650316
;

-- 2021-07-15T07:41:12.927Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2021-07-15 10:41:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650316
;

