-- 2022-12-21T08:50:32.352Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581890,0,'StartNewMonth',TO_TIMESTAMP('2022-12-21 10:50:32','YYYY-MM-DD HH24:MI:SS'),100,'Restart the sequence with Start on every 1st of the month','D','The Restart Sequence Every Month checkbox indicates that the documents sequencing should return to the starting number on the first day of the month.','Y','Nummernfolge jedes Monat neu beginnen','Nummernfolge jedes Monat neu beginnen',TO_TIMESTAMP('2022-12-21 10:50:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-21T08:50:32.358Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581890 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: StartNewMonth
-- 2022-12-21T08:51:00.878Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Restart sequence every Month', PrintName='Restart sequence every Month',Updated=TO_TIMESTAMP('2022-12-21 10:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581890 AND AD_Language='en_US'
;

-- 2022-12-21T08:51:00.911Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581890,'en_US')
;

-- Column: AD_Sequence.StartNewMonth
-- Column: AD_Sequence.StartNewMonth
-- 2022-12-21T08:56:21.789Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585422,581890,0,20,115,'StartNewMonth',TO_TIMESTAMP('2022-12-21 10:56:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Restart the sequence with Start on every 1st of the month','D',0,1,'The Restart Sequence Every Month checkbox indicates that the documents sequencing should return to the starting number on the first day of the month.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Nummernfolge jedes Monat neu beginnen',0,0,TO_TIMESTAMP('2022-12-21 10:56:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-21T08:56:21.791Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585422 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-21T08:56:21.794Z
/* DDL */  select update_Column_Translation_From_AD_Element(581890) 
;

-- 2022-12-21T08:56:25.240Z
/* DDL */ SELECT public.db_alter_table('AD_Sequence','ALTER TABLE public.AD_Sequence ADD COLUMN StartNewMonth CHAR(1) CHECK (StartNewMonth IN (''Y'',''N''))')
;

-- 2022-12-21T08:58:08.737Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581891,0,'CalendarMonth',TO_TIMESTAMP('2022-12-21 10:58:08','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','Monat','Monat',TO_TIMESTAMP('2022-12-21 10:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-21T08:58:08.738Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581891 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CalendarMonth
-- 2022-12-21T08:58:57.483Z
UPDATE AD_Element_Trl SET Name='Month', PrintName='Month',Updated=TO_TIMESTAMP('2022-12-21 10:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581891 AND AD_Language='en_US'
;

-- 2022-12-21T08:58:57.485Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581891,'en_US') 
;

-- Element: CalendarMonth
-- 2022-12-21T08:59:06.007Z
UPDATE AD_Element_Trl SET Name='Mois', PrintName='Mois',Updated=TO_TIMESTAMP('2022-12-21 10:59:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581891 AND AD_Language='fr_CH'
;

-- 2022-12-21T08:59:06.009Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581891,'fr_CH') 
;

-- Column: AD_Sequence_No.CalendarMonth
-- Column: AD_Sequence_No.CalendarMonth
-- 2022-12-21T08:59:49.557Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585423,581891,0,22,122,'CalendarMonth',TO_TIMESTAMP('2022-12-21 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Monat',0,0,TO_TIMESTAMP('2022-12-21 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-21T08:59:49.559Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585423 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-21T08:59:49.561Z
/* DDL */  select update_Column_Translation_From_AD_Element(581891) 
;

-- 2022-12-21T08:59:56.895Z
/* DDL */ SELECT public.db_alter_table('AD_Sequence_No','ALTER TABLE public.AD_Sequence_No ADD COLUMN CalendarMonth NUMERIC')
;

-- Field: Nummernfolgen -> Reihenfolge -> Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- Field: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- 2022-12-21T09:23:47.639Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585422,710053,0,146,0,TO_TIMESTAMP('2022-12-21 11:23:47','YYYY-MM-DD HH24:MI:SS'),100,'Restart the sequence with Start on every 1st of the month',0,'D','The Restart Sequence Every Month checkbox indicates that the documents sequencing should return to the starting number on the first day of the month.',0,'Y','Y','Y','N','N','N','N','N','Nummernfolge jedes Monat neu beginnen',0,180,0,1,1,TO_TIMESTAMP('2022-12-21 11:23:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-21T09:23:47.643Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-21T09:23:47.645Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581890) 
;

-- 2022-12-21T09:23:47.656Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710053
;

-- 2022-12-21T09:23:47.657Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710053)
;

-- UI Element: Nummernfolgen -> Reihenfolge.Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- UI Element: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> main -> 20 -> flags.Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- 2022-12-21T09:24:51.361Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710053,0,146,540413,614571,'F',TO_TIMESTAMP('2022-12-21 11:24:51','YYYY-MM-DD HH24:MI:SS'),100,'Restart the sequence with Start on every 1st of the month','The Restart Sequence Every Month checkbox indicates that the documents sequencing should return to the starting number on the first day of the month.','Y','N','N','Y','N','N','N',0,'Nummernfolge jedes Monat neu beginnen',45,0,0,TO_TIMESTAMP('2022-12-21 11:24:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Nummernfolgen -> Reihenfolge.Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- UI Element: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> main -> 20 -> flags.Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- 2022-12-21T09:25:01.500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-12-21 11:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614571
;

-- UI Element: Nummernfolgen -> Reihenfolge.Nummer starten mit
-- Column: AD_Sequence.StartNo
-- UI Element: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> main -> 20 -> flags.Nummer starten mit
-- Column: AD_Sequence.StartNo
-- 2022-12-21T09:25:01.513Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-12-21 11:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544141
;

-- UI Element: Nummernfolgen -> Reihenfolge.Datum Spalte
-- Column: AD_Sequence.DateColumn
-- UI Element: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> main -> 20 -> flags.Datum Spalte
-- Column: AD_Sequence.DateColumn
-- 2022-12-21T09:25:01.520Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-12-21 11:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544142
;

-- UI Element: Nummernfolgen -> Reihenfolge.Als Datensatz ID
-- Column: AD_Sequence.IsTableID
-- UI Element: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> main -> 20 -> flags.Als Datensatz ID
-- Column: AD_Sequence.IsTableID
-- 2022-12-21T09:25:01.526Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-12-21 11:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544137
;

-- UI Element: Nummernfolgen -> Reihenfolge.Automatische Nummerierung
-- Column: AD_Sequence.IsAutoSequence
-- UI Element: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> main -> 20 -> flags.Automatische Nummerierung
-- Column: AD_Sequence.IsAutoSequence
-- 2022-12-21T09:25:01.532Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-12-21 11:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544134
;

-- UI Element: Nummernfolgen -> Reihenfolge.Sektion
-- Column: AD_Sequence.AD_Org_ID
-- UI Element: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> main -> 20 -> org.Sektion
-- Column: AD_Sequence.AD_Org_ID
-- 2022-12-21T09:25:01.537Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-12-21 11:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544135
;

-- Field: Nummernfolgen -> Reihenfolge -> Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- Field: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- 2022-12-21T09:27:38.694Z
UPDATE AD_Field SET DisplayLogic='@StartNewYear@=Y',Updated=TO_TIMESTAMP('2022-12-21 11:27:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710053
;

-- Column: AD_Sequence_No.CalendarMonth
-- Column: AD_Sequence_No.CalendarMonth
-- 2022-12-21T09:49:10.925Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2022-12-21 11:49:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585423
;

-- 2022-12-21T09:49:13.596Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarMonth','VARCHAR(2)',null,null)
;

-- 2022-12-21T10:48:13.925Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541586,'S',TO_TIMESTAMP('2022-12-21 12:48:13','YYYY-MM-DD HH24:MI:SS'),100,'Provides the format that de.metas.document.sequenceno.DateSequenceProvider shall use in formatting its prefix','D','Y','de.metas.document.seqNo.DateSequenceProvider.dateFormat',TO_TIMESTAMP('2022-12-21 12:48:13','YYYY-MM-DD HH24:MI:SS'),100,'wwu')
;

-- 2022-12-21T11:32:29.295Z
INSERT INTO AD_JavaClass (AD_Client_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,Description,EntityType,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540073,540040,0,'de.metas.document.sequenceno.DateSequenceProvider',TO_TIMESTAMP('2022-12-21 13:32:29','YYYY-MM-DD HH24:MI:SS'),100,'This class is applicable if the respective document model has a Date column, otherwise it uses the system date. It returns a formatted prefix for the sequence number. The formatting is based on the "de.metas.document.seqNo.DateSequenceProvider.dateFormat" sysconfig.','D','Y','N','DateSequenceProvider',TO_TIMESTAMP('2022-12-21 13:32:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-21T11:38:42.011Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581892,0,'LotNo_Sequence_ID',TO_TIMESTAMP('2022-12-21 13:38:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nummernfolge für Lotnummer','Nummernfolge für Lotnummer',TO_TIMESTAMP('2022-12-21 13:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-21T11:38:42.012Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581892 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LotNo_Sequence_ID
-- 2022-12-21T11:39:16.048Z
UPDATE AD_Element_Trl SET Name='Lot No. Sequence', PrintName='Lot No. Sequence',Updated=TO_TIMESTAMP('2022-12-21 13:39:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581892 AND AD_Language='en_US'
;

-- 2022-12-21T11:39:16.049Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581892,'en_US') 
;

-- Column: PP_Product_BOM.LotNo_Sequence_ID
-- Column: PP_Product_BOM.LotNo_Sequence_ID
-- 2022-12-21T11:40:03.707Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585424,581892,0,30,128,53018,'LotNo_Sequence_ID',TO_TIMESTAMP('2022-12-21 13:40:03','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Nummernfolge für Lotnummer',0,0,TO_TIMESTAMP('2022-12-21 13:40:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-21T11:40:03.708Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585424 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-21T11:40:03.710Z
/* DDL */  select update_Column_Translation_From_AD_Element(581892) 
;

-- Column: PP_Order_BOM.LotNo_Sequence_ID
-- Column: PP_Order_BOM.LotNo_Sequence_ID
-- 2022-12-21T11:41:32.246Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585425,581892,0,30,128,53026,'LotNo_Sequence_ID',TO_TIMESTAMP('2022-12-21 13:41:32','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Nummernfolge für Lotnummer',0,0,TO_TIMESTAMP('2022-12-21 13:41:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-21T11:41:32.247Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585425 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-21T11:41:32.249Z
/* DDL */  select update_Column_Translation_From_AD_Element(581892) 
;

-- 2022-12-21T11:41:44.926Z
/* DDL */ SELECT public.db_alter_table('PP_Order_BOM','ALTER TABLE public.PP_Order_BOM ADD COLUMN LotNo_Sequence_ID NUMERIC(10)')
;

-- 2022-12-21T11:41:45.063Z
ALTER TABLE PP_Order_BOM ADD CONSTRAINT LotNoSequence_PPOrderBOM FOREIGN KEY (LotNo_Sequence_ID) REFERENCES public.AD_Sequence DEFERRABLE INITIALLY DEFERRED
;

-- 2022-12-21T11:42:00.960Z
/* DDL */ SELECT public.db_alter_table('PP_Product_BOM','ALTER TABLE public.PP_Product_BOM ADD COLUMN LotNo_Sequence_ID NUMERIC(10)')
;

-- 2022-12-21T11:42:01.229Z
ALTER TABLE PP_Product_BOM ADD CONSTRAINT LotNoSequence_PPProductBOM FOREIGN KEY (LotNo_Sequence_ID) REFERENCES public.AD_Sequence DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_DocType.LotNo_Sequence_ID
-- Column: C_DocType.LotNo_Sequence_ID
-- 2022-12-21T17:41:50.107Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585426,581892,0,30,128,217,'LotNo_Sequence_ID',TO_TIMESTAMP('2022-12-21 19:41:49','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Nummernfolge für Lotnummer',0,0,TO_TIMESTAMP('2022-12-21 19:41:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-21T17:41:50.109Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585426 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-21T17:41:50.111Z
/* DDL */  select update_Column_Translation_From_AD_Element(581892) 
;

-- 2022-12-21T17:42:31.830Z
/* DDL */ SELECT public.db_alter_table('C_DocType','ALTER TABLE public.C_DocType ADD COLUMN LotNo_Sequence_ID NUMERIC(10)')
;

-- 2022-12-21T17:42:32.345Z
ALTER TABLE C_DocType ADD CONSTRAINT LotNoSequence_CDocType FOREIGN KEY (LotNo_Sequence_ID) REFERENCES public.AD_Sequence DEFERRABLE INITIALLY DEFERRED
;


-- Field: Stücklistenkonfiguration Version -> Stücklistenartikel -> Nummernfolge für Lotnummer
-- Column: PP_Product_BOM.LotNo_Sequence_ID
-- Field: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> Nummernfolge für Lotnummer
-- Column: PP_Product_BOM.LotNo_Sequence_ID
-- 2022-12-22T09:08:54.368Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585424,710054,0,53028,0,TO_TIMESTAMP('2022-12-22 11:08:54','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Nummernfolge für Lotnummer',0,200,0,1,1,TO_TIMESTAMP('2022-12-22 11:08:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-22T09:08:54.372Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-22T09:08:54.397Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581892)
;

-- 2022-12-22T09:08:54.408Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710054
;

-- 2022-12-22T09:08:54.409Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710054)
;

-- UI Element: Stücklistenkonfiguration Version -> Stücklistenartikel.Nummernfolge für Lotnummer
-- Column: PP_Product_BOM.LotNo_Sequence_ID
-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> main -> 20 -> attributes.Nummernfolge für Lotnummer
-- Column: PP_Product_BOM.LotNo_Sequence_ID
-- 2022-12-22T09:09:40.438Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710054,0,53028,542424,614572,'F',TO_TIMESTAMP('2022-12-22 11:09:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Nummernfolge für Lotnummer',20,0,0,TO_TIMESTAMP('2022-12-22 11:09:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Produktionsauftrag -> Produkt -> Nummernfolge für Lotnummer
-- Column: PP_Order_BOM.LotNo_Sequence_ID
-- Field: Produktionsauftrag(53009,EE01) -> Produkt(53040,EE01) -> Nummernfolge für Lotnummer
-- Column: PP_Order_BOM.LotNo_Sequence_ID
-- 2022-12-22T09:13:22.889Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585425,710055,0,53040,0,TO_TIMESTAMP('2022-12-22 11:13:22','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Nummernfolge für Lotnummer',0,190,0,1,1,TO_TIMESTAMP('2022-12-22 11:13:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-22T09:13:22.890Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-22T09:13:22.891Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581892)
;

-- 2022-12-22T09:13:22.893Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710055
;

-- 2022-12-22T09:13:22.893Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710055)
;

-- UI Element: Produktionsauftrag -> Produkt.Nummernfolge für Lotnummer
-- Column: PP_Order_BOM.LotNo_Sequence_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produkt(53040,EE01) -> main -> 10 -> default.Nummernfolge für Lotnummer
-- Column: PP_Order_BOM.LotNo_Sequence_ID
-- 2022-12-22T09:14:18.736Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710055,0,53040,540839,614573,'F',TO_TIMESTAMP('2022-12-22 11:14:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Nummernfolge für Lotnummer',168,0,0,TO_TIMESTAMP('2022-12-22 11:14:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsauftrag -> Produkt.Nummernfolge für Lotnummer
-- Column: PP_Order_BOM.LotNo_Sequence_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produkt(53040,EE01) -> main -> 10 -> default.Nummernfolge für Lotnummer
-- Column: PP_Order_BOM.LotNo_Sequence_ID
-- 2022-12-22T09:14:26.997Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-12-22 11:14:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614573
;

-- UI Element: Produktionsauftrag -> Produkt.Sektion
-- Column: PP_Order_BOM.AD_Org_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produkt(53040,EE01) -> main -> 10 -> default.Sektion
-- Column: PP_Order_BOM.AD_Org_ID
-- 2022-12-22T09:14:27.011Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-12-22 11:14:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546582
;

-- Field: Belegart -> Belegart -> Nummernfolge für Lotnummer
-- Column: C_DocType.LotNo_Sequence_ID
-- Field: Belegart(135,D) -> Belegart(167,D) -> Nummernfolge für Lotnummer
-- Column: C_DocType.LotNo_Sequence_ID
-- 2022-12-22T16:06:16.738Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585426,710064,0,167,0,TO_TIMESTAMP('2022-12-22 18:06:16','YYYY-MM-DD HH24:MI:SS'),100,0,'@DocBaseType/XXX@=MMR','D',0,'Y','Y','Y','N','N','N','N','N','Nummernfolge für Lotnummer',0,340,0,1,1,TO_TIMESTAMP('2022-12-22 18:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-22T16:06:16.740Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-22T16:06:16.767Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581892)
;

-- 2022-12-22T16:06:16.778Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710064
;

-- 2022-12-22T16:06:16.779Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710064)
;

-- UI Element: Belegart -> Belegart.Nummernfolge für Lotnummer
-- Column: C_DocType.LotNo_Sequence_ID
-- UI Element: Belegart(135,D) -> Belegart(167,D) -> advanced edit -> 10 -> advanced edit.Nummernfolge für Lotnummer
-- Column: C_DocType.LotNo_Sequence_ID
-- 2022-12-22T16:07:07.339Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710064,0,167,540402,614576,'F',TO_TIMESTAMP('2022-12-22 18:07:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Nummernfolge für Lotnummer',300,0,0,TO_TIMESTAMP('2022-12-22 18:07:07','YYYY-MM-DD HH24:MI:SS'),100)
;
