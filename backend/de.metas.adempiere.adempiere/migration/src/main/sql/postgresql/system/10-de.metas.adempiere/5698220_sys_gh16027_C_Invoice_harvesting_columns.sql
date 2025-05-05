-- Column: C_Invoice.C_Harvesting_Calendar_ID
-- 2023-08-08T10:04:50.202684900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587250,581157,0,30,540260,318,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-08-08 13:04:50.013','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender',0,0,TO_TIMESTAMP('2023-08-08 13:04:50.013','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-08T10:04:50.202684900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587250 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-08T10:04:50.721767200Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157) 
;

-- 2023-08-08T10:06:38.030663100Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;

-- 2023-08-08T10:06:40.566093Z
ALTER TABLE C_Invoice ADD CONSTRAINT CHarvestingCalendar_CInvoice FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice.Harvesting_Year_ID
-- 2023-08-08T10:07:14.670888700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587251,582471,0,30,540133,318,540647,'Harvesting_Year_ID',TO_TIMESTAMP('2023-08-08 13:07:14.513','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-08-08 13:07:14.513','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-08T10:07:14.686385Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587251 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-08T10:07:15.157601300Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471) 
;

-- 2023-08-08T10:07:16.751147700Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-08-08T10:07:17.755007400Z
ALTER TABLE C_Invoice ADD CONSTRAINT HarvestingYear_CInvoice FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;



-- Column: C_Invoice.M_SectionCode_ID
-- 2023-08-08T10:09:00.175660600Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2023-08-08 13:09:00.175','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584006
;

-- Column: C_Invoice.AD_Org_ID
-- 2023-08-08T10:09:00.667565900Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2023-08-08 13:09:00.667','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3486
;

-- Column: C_Invoice.C_BPartner_ID
-- 2023-08-08T10:09:01.158347200Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2023-08-08 13:09:01.158','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3499
;

-- Column: C_Invoice.DocumentNo
-- 2023-08-08T10:09:01.758866100Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2023-08-08 13:09:01.758','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3492
;

-- Column: C_Invoice.IsPaid
-- 2023-08-08T10:09:02.242379700Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2023-08-08 13:09:02.242','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=5025
;

-- Column: C_Invoice.POReference
-- 2023-08-08T10:09:02.724757400Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2023-08-08 13:09:02.724','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3785
;

-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2023-08-08T10:09:03.250900700Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2023-08-08 13:09:03.25','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3781
;

-- Column: C_Invoice.DateInvoiced
-- 2023-08-08T10:09:03.860157700Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2023-08-08 13:09:03.86','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3783
;

-- Column: C_Invoice.C_Harvesting_Calendar_ID
-- 2023-08-08T10:09:04.377681300Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2023-08-08 13:09:04.377','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587250
;

-- Column: C_Invoice.Harvesting_Year_ID
-- 2023-08-08T10:09:04.882703300Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2023-08-08 13:09:04.882','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587251
;

-- Column: C_Invoice.M_Warehouse_ID
-- 2023-08-08T10:09:05.369343900Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2023-08-08 13:09:05.369','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=571520
;


-- Column: C_Invoice.M_Warehouse_ID
-- 2023-08-11T12:48:57.561321400Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-08-11 15:48:57.561','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=571520
;

-- 2023-08-11T12:49:00.604617600Z
INSERT INTO t_alter_column values('c_invoice','M_Warehouse_ID','NUMERIC(10)',null,null)
;




