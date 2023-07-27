-- Column: C_Order.C_Harvesting_Calendar_ID
-- 2023-07-21T06:00:06.480981800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587165,581157,0,30,540260,259,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-07-21 09:00:06.257','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender',0,0,TO_TIMESTAMP('2023-07-21 09:00:06.257','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-07-21T06:00:06.487934300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587165 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-21T06:00:07.486163900Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157) 
;

-- 2023-07-21T06:00:11.338486900Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;

-- 2023-07-21T06:00:14.807526Z
ALTER TABLE C_Order ADD CONSTRAINT CHarvestingCalendar_COrder FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Order.Harvesting_Year_ID
-- 2023-07-21T06:01:38.332805800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587166,582471,0,30,540133,259,'Harvesting_Year_ID',TO_TIMESTAMP('2023-07-21 09:01:38.1','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-07-21 09:01:38.1','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-07-21T06:01:38.336806200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587166 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-21T06:01:39.090552Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471) 
;

-- 2023-07-21T06:01:43.713111500Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-07-21T06:01:45.819572200Z
ALTER TABLE C_Order ADD CONSTRAINT HarvestingYear_COrder FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;



-- Column: C_Order.Harvesting_Year_ID
-- 2023-07-21T07:59:59.182765800Z
UPDATE AD_Column SET AD_Val_Rule_ID=540645, MandatoryLogic='@C_Harvesting_Calendar_ID/-1@!0',Updated=TO_TIMESTAMP('2023-07-21 10:59:59.182','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587166
;

-- 2023-07-21T08:00:02.556824900Z
INSERT INTO t_alter_column values('c_order','Harvesting_Year_ID','NUMERIC(10)',null,null)
;

-- Name: C_Year (Selected Calendar)
-- 2023-07-21T09:10:19.432399200Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540647,'C_Year.C_Calendar_ID = @C_Calendar_ID@',TO_TIMESTAMP('2023-07-21 12:10:19.176','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','C_Year (Selected Calendar)','S',TO_TIMESTAMP('2023-07-21 12:10:19.176','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: C_Order.Harvesting_Year_ID
-- 2023-07-21T09:10:39.947016500Z
UPDATE AD_Column SET AD_Val_Rule_ID=540647,Updated=TO_TIMESTAMP('2023-07-21 12:10:39.947','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587166
;

-- 2023-07-21T09:11:40.226487Z
INSERT INTO t_alter_column values('c_order','Harvesting_Year_ID','NUMERIC(10)',null,null)
;



-- Column: C_Order.Harvesting_Year_ID
-- 2023-07-21T12:35:06.396085600Z
UPDATE AD_Column SET AD_Val_Rule_ID=540647, FilterOperator='E',Updated=TO_TIMESTAMP('2023-07-21 15:35:06.396','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587166
;

-- Name: C_Year (Selected Calendar)
-- 2023-07-21T12:39:34.311499400Z
UPDATE AD_Val_Rule SET Code='C_Year.C_Calendar_ID = @C_Harvesting_Calendar_ID@',Updated=TO_TIMESTAMP('2023-07-21 15:39:34.31','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540647
;


