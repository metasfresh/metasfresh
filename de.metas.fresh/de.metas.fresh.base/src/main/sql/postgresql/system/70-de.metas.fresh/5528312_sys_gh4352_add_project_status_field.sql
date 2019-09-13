
-- 2018-04-14T14:46:25.837
-- project window
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540849,TO_TIMESTAMP('2018-04-14 14:46:25','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Projectstatus',TO_TIMESTAMP('2018-04-14 14:46:25','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-04-14T14:46:25.838
-- project window
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540849 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-04-14T14:47:08.957
-- project window
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,13562,0,540849,776,TO_TIMESTAMP('2018-04-14 14:47:08','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',TO_TIMESTAMP('2018-04-14 14:47:08','YYYY-MM-DD HH24:MI:SS'),100,'R_StatusCategory_ID=1000002')
;

-- 2018-04-14T14:47:19.584
-- project window
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559693,2706,0,18,540849,203,'N','R_Status_ID',TO_TIMESTAMP('2018-04-14 14:47:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Request Status','D',10,'Status if the request (open, closed, investigating, ..)','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Status',0,0,TO_TIMESTAMP('2018-04-14 14:47:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-04-14T14:47:19.587
-- project window
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559693 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- we do not want to create this column which is renamed later
--/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN R_Status_ID NUMERIC(10)')
--ALTER TABLE C_Project ADD CONSTRAINT RStatus_CProject FOREIGN KEY (R_Status_ID) REFERENCES public.R_Status DEFERRABLE INITIALLY DEFERRED
;

-- 2018-04-14T14:50:16.954
-- project window
UPDATE AD_Ref_Table SET AD_Display=13570,Updated=TO_TIMESTAMP('2018-04-14 14:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540849
;


--

-- 2018-05-14T14:08:56.369
-- description fields
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544077,0,'R_Project_Status_ID',TO_TIMESTAMP('2018-05-14 14:08:56','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Projektstatus','Projektstatus',TO_TIMESTAMP('2018-05-14 14:08:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-14T14:08:56.374
-- description fields
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544077 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-14T14:09:07.196
-- description fields
UPDATE AD_Column SET AD_Element_ID=544077, AD_Reference_ID=19, ColumnName='R_Project_Status_ID', Description=NULL, EntityType='U', Help=NULL, Name='Projektstatus',Updated=TO_TIMESTAMP('2018-05-14 14:09:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559693
;


-- 2018-05-14T14:09:12.582
-- description fields
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2018-05-14 14:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559693
;

-- 2018-05-14T14:09:15.021
-- description fields
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN R_Project_Status_ID NUMERIC(10)')
;

-- 2018-05-14T14:09:15.297
-- description fields
ALTER TABLE C_Project ADD CONSTRAINT RProjectStatus_CProject FOREIGN KEY (R_Project_Status_ID) REFERENCES public.R_Status DEFERRABLE INITIALLY DEFERRED
;

COMMIT;
-- 2018-05-14T14:34:33.126
-- description fields
INSERT INTO t_alter_column values('c_project','R_Project_Status_ID','NUMERIC(10)',null,null)
;

-- display the field in window



-- 2019-07-29T13:04:55.711Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559693,582478,0,541830,TO_TIMESTAMP('2019-07-29 15:04:55','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','N','N','N','N','N','N','N','Projektstatus',TO_TIMESTAMP('2019-07-29 15:04:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:04:55.713Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582478 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T13:04:55.717Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544077) 
;

-- 2019-07-29T13:04:55.724Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582478
;

-- 2019-07-29T13:04:55.725Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582478)
;

-- 2019-07-29T13:05:08.801Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 15:05:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582478
;

--rnaming other UI element  along the way
UPDATE AD_UI_Element SET Name='Projektnummer',Updated=TO_TIMESTAMP('2019-07-29 15:05:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560249
;

-- 2019-07-29T13:06:03.860Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582478,0,541830,542695,560308,'F',TO_TIMESTAMP('2019-07-29 15:06:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektstatus',25,0,0,TO_TIMESTAMP('2019-07-29 15:06:03','YYYY-MM-DD HH24:MI:SS'),100)
;

UPDATE AD_Ref_Table SET WhereClause='R_StatusCategory_ID=540009',Updated=TO_TIMESTAMP('2019-07-29 15:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540849
;

-- 2019-07-29T13:28:42.307Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582284,0,541830,542696,560309,'F',TO_TIMESTAMP('2019-07-29 15:28:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Datum AE',40,0,0,TO_TIMESTAMP('2019-07-29 15:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:29:01.223Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582285,0,541830,542696,560310,'F',TO_TIMESTAMP('2019-07-29 15:29:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektabschluss',50,0,0,TO_TIMESTAMP('2019-07-29 15:29:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- establishing metasfresh default for org and client

-- 2019-07-29T13:33:00.742Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560254
;

-- 2019-07-29T13:33:09.946Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541784,542703,TO_TIMESTAMP('2019-07-29 15:33:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2019-07-29 15:33:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:33:22.158Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582272,0,541830,542703,560311,'F',TO_TIMESTAMP('2019-07-29 15:33:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2019-07-29 15:33:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:33:31.598Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582273,0,541830,542703,560312,'F',TO_TIMESTAMP('2019-07-29 15:33:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2019-07-29 15:33:31','YYYY-MM-DD HH24:MI:SS'),100)
;
