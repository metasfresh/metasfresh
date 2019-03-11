-- 2019-03-11T15:25:05.081
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564469,543753,0,10,540841,'GlobalID',TO_TIMESTAMP('2019-03-11 15:25:04','YYYY-MM-DD HH24:MI:SS'),100,'N','D',250,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Global ID',0,0,TO_TIMESTAMP('2019-03-11 15:25:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-11T15:25:05.088
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564469 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 2019-03-11T15:37:57.956
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564469,577694,0,540879,TO_TIMESTAMP('2019-03-11 15:37:57','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','N','N','N','N','N','N','N','Global ID',TO_TIMESTAMP('2019-03-11 15:37:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-11T15:37:57.976
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577694 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-11T15:38:54.715
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577694,0,540879,557832,542251,'F',TO_TIMESTAMP('2019-03-11 15:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Global ID',90,0,0,TO_TIMESTAMP('2019-03-11 15:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-11T15:39:20.193
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-03-11 15:39:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557832
;

-- 2019-03-11T15:39:22.364
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2019-03-11 15:39:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556929
;

-- 2019-03-11T15:39:25.194
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2019-03-11 15:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556930
;

-- 2019-03-11T15:39:28.097
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2019-03-11 15:39:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556937
;





-- 2019-03-11T17:54:08.764
-- #298 changing anz. stellen
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576218,0,'BP_Global_ID',TO_TIMESTAMP('2019-03-11 17:54:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Global ID','Global ID',TO_TIMESTAMP('2019-03-11 17:54:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-11T17:54:08.774
-- #298 changing anz. stellen
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576218 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-11T17:54:26.174
-- #298 changing anz. stellen
UPDATE AD_Column SET AD_Reference_ID=19, FieldLength=10, Help=NULL, AD_Element_ID=576218, ColumnName='BP_Global_ID', Description=NULL, Name='Global ID',Updated=TO_TIMESTAMP('2019-03-11 17:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564469
;

-- 2019-03-11T17:56:34.712
-- #298 changing anz. stellen
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2019-03-11 17:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564469
;

-- 2019-03-11T17:56:45.753
-- #298 changing anz. stellen
UPDATE AD_Element SET ColumnName='BP_GlobalID',Updated=TO_TIMESTAMP('2019-03-11 17:56:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576218
;

-- 2019-03-11T17:56:45.783
-- #298 changing anz. stellen
UPDATE AD_Column SET ColumnName='BP_GlobalID', Name='Global ID', Description=NULL, Help=NULL WHERE AD_Element_ID=576218
;

-- 2019-03-11T17:56:45.783
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='BP_GlobalID', Name='Global ID', Description=NULL, Help=NULL, AD_Element_ID=576218 WHERE UPPER(ColumnName)='BP_GLOBALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-11T17:56:45.783
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='BP_GlobalID', Name='Global ID', Description=NULL, Help=NULL WHERE AD_Element_ID=576218 AND IsCentrallyMaintained='Y'
;
