-- 2021-09-15T08:38:19.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,576812,617,0,15,293,'ValidFrom',TO_TIMESTAMP('2021-09-15 11:38:19','YYYY-MM-DD HH24:MI:SS'),100,'N','','Gültig ab inklusiv (erster Tag)','D',0,7,'"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Gültig ab',0,0,TO_TIMESTAMP('2021-09-15 11:38:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-15T08:38:19.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=576812 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-15T08:38:19.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(617) 
;

-- 2021-09-15T10:02:36.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579830,0,'PreviousID',TO_TIMESTAMP('2021-09-15 13:02:36','YYYY-MM-DD HH24:MI:SS'),100,'Die Adresse, die in offenen Auftragsdispos, Rechnungsdispos und Vertragesperioden durch die aktuelle ersetzt wird.','D','Die Adresse, die in offenen Auftragsdispos, Rechnungsdispos und Vertragesperioden durch die aktuelle ersetzt wird.','Y','Vorherige Adresse','Vorherige Adresse',TO_TIMESTAMP('2021-09-15 13:02:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-15T10:02:36.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579830 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-15T10:03:03.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The address that will be replaced by the current one in open sales candidates, billing candidates and flatrate terms.', Help='The address that will be replaced by the current one in open sales candidates, billing candidates and flatrate terms.', Name='Previous Address', PrintName='Previous Address',Updated=TO_TIMESTAMP('2021-09-15 13:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579830 AND AD_Language='en_US'
;

-- 2021-09-15T10:03:03.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579830,'en_US') 
;

-- 2021-09-15T10:03:12.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-15 13:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579830 AND AD_Language='en_US'
;

-- 2021-09-15T10:03:12.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579830,'en_US') 
;

-- 2021-09-15T10:09:28.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,576824,579830,0,30,159,293,'PreviousID',TO_TIMESTAMP('2021-09-15 13:09:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Adresse, die durch die aktuelle ersetzt wird.','D',0,10,'Die Adresse, die durch die aktuelle ersetzt wird.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vorherige Adresse',0,0,TO_TIMESTAMP('2021-09-15 13:09:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-15T10:09:28.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=576824 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-15T10:09:28.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579830) 
;

-- 2021-09-15T10:11:03.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2021-09-15 13:11:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=576824
;

-- 2021-09-15T10:15:33.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@ValidFrom/null@!null', PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-09-15 13:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=576824
;

-- 2021-09-15T10:46:52.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2021-09-15 13:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=576824
;

-- 2021-09-15T10:47:28.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2021-09-15 13:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=576824
;

-- 2021-09-15T10:47:30.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN PreviousID NUMERIC')
;

-- 2021-09-15T10:47:40.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2021-09-15 13:47:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=576824
;

-- 2021-09-15T10:47:42.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_location','PreviousID','VARCHAR(10)',null,null)
;

-- 2021-09-15T10:52:59.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Previous_ID',Updated=TO_TIMESTAMP('2021-09-15 13:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579830
;

-- 2021-09-15T10:52:59.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Previous_ID', Name='Vorherige Adresse', Description='Die Adresse, die durch die aktuelle ersetzt wird.', Help='Die Adresse, die durch die aktuelle ersetzt wird.' WHERE AD_Element_ID=579830
;

-- 2021-09-15T10:52:59.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Previous_ID', Name='Vorherige Adresse', Description='Die Adresse, die durch die aktuelle ersetzt wird.', Help='Die Adresse, die durch die aktuelle ersetzt wird.', AD_Element_ID=579830 WHERE UPPER(ColumnName)='PREVIOUS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-15T10:52:59.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Previous_ID', Name='Vorherige Adresse', Description='Die Adresse, die durch die aktuelle ersetzt wird.', Help='Die Adresse, die durch die aktuelle ersetzt wird.' WHERE AD_Element_ID=579830 AND IsCentrallyMaintained='Y'
;

-- 2021-09-15T10:53:10.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN Previous_ID NUMERIC(10)')
;

-- 2021-09-15T10:53:10.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner_Location ADD CONSTRAINT Previous_CBPartnerLocation FOREIGN KEY (Previous_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- 2021-09-15T12:16:05.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540557,'EXISTS(SELECT *
             FROM c_bpartner_location l
             WHERE l.c_bpartner_location_id = c_bpartner_location.c_bpartner_location_id
               AND l.c_bpartner_id = @C_BPartner_ID/-1@
               AND l.c_bpartner_location_id != @C_BPartner_Location_ID/-1@
               AND l.isactive = ''Y''
               AND NOT EXISTS(SELECT 1 FROM c_bpartner_location l1 WHERE l.c_bpartner_location_id = l1.previous_Id))',TO_TIMESTAMP('2021-09-15 15:16:05','YYYY-MM-DD HH24:MI:SS'),100,'matches if active, same C_BPartner_ID and is not used by any Previous_ID','D','Y','C_Bpartner_Location_ID for bpartner - Current','S',TO_TIMESTAMP('2021-09-15 15:16:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-15T12:16:23.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540557,Updated=TO_TIMESTAMP('2021-09-15 15:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=576824
;

-- 2021-09-15T12:17:43.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,576812,659672,0,222,0,TO_TIMESTAMP('2021-09-15 15:17:43','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)',0,'D','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.',0,'Y','Y','N','N','N','N','N','N','Gültig ab',0,180,0,1,1,TO_TIMESTAMP('2021-09-15 15:17:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-15T12:17:43.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=659672 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-15T12:17:43.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(617) 
;

-- 2021-09-15T12:17:43.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=659672
;

-- 2021-09-15T12:17:43.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(659672)
;

-- 2021-09-15T12:18:18.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,576824,659673,0,222,0,TO_TIMESTAMP('2021-09-15 15:18:17','YYYY-MM-DD HH24:MI:SS'),100,'Die Adresse, die durch die aktuelle ersetzt wird.',0,'@ValidFrom/''1970-01-01''@ > ''1970-01-01''','D','Die Adresse, die durch die aktuelle ersetzt wird.',0,'Y','Y','Y','N','N','N','N','N','Vorherige Adresse',0,190,0,1,1,TO_TIMESTAMP('2021-09-15 15:18:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-15T12:18:18.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=659673 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-15T12:18:18.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579830) 
;

-- 2021-09-15T12:18:18.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=659673
;

-- 2021-09-15T12:18:18.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(659673)
;

-- 2021-09-15T12:34:30.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,659672,0,222,1000034,591135,'F',TO_TIMESTAMP('2021-09-15 15:34:30','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','N','N',0,'Gültig ab',160,0,0,TO_TIMESTAMP('2021-09-15 15:34:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-15T12:34:58.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,659673,0,222,1000034,591136,'F',TO_TIMESTAMP('2021-09-15 15:34:58','YYYY-MM-DD HH24:MI:SS'),100,'Die Adresse, die durch die aktuelle ersetzt wird.','Die Adresse, die durch die aktuelle ersetzt wird.','Y','N','N','Y','N','N','N',0,'Vorherige Adresse',170,0,0,TO_TIMESTAMP('2021-09-15 15:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-15T12:35:22.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=180,Updated=TO_TIMESTAMP('2021-09-15 15:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546540
;

-- 2021-09-15T12:35:38.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2021-09-15 15:35:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=591136
;

-- 2021-09-15T12:35:45.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2021-09-15 15:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546539
;

-- 2021-09-15T12:35:50.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2021-09-15 15:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=591135
;

-- 2021-09-15T12:36:42.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN ValidFrom TIMESTAMP WITHOUT TIME ZONE')
;

-- 2021-10-11T15:02:50.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540669,0,293,TO_TIMESTAMP('2021-10-11 18:02:49','YYYY-MM-DD HH24:MI:SS'),100,'Ensure that no address is replaced by more than one other address','D','Y','Y','PreviousID','N',TO_TIMESTAMP('2021-10-11 18:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-11T15:02:50.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540669 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-10-11T15:03:16.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,576824,541194,540669,0,TO_TIMESTAMP('2021-10-11 18:03:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-10-11 18:03:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-11T15:05:05.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX PreviousID ON C_BPartner_Location (Previous_ID)
;
 
-- 2021-10-13T10:21:32.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,584923,'Y','de.metas.contracts.bpartner.process.C_BPartner_Location_UpdateFromPreviousAddress','N',TO_TIMESTAMP('2021-10-13 13:21:31','YYYY-MM-DD HH24:MI:SS'),100,'Process used to perform business partner address updates that were scheduled, where one location is to replace another one.','D','Process used to perform business partner address updates that were scheduled, where one location is to replace another one.','Y','N','N','N','Y','N','N','N','Y','Y',0,'Update Scheduled BP Addresses','json','N','N','xls','https://github.com/metasfresh/metasfresh/issues/11850','Java',TO_TIMESTAMP('2021-10-13 13:21:31','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_Location_UpdateFromPreviousAddress')
;

-- 2021-10-13T10:21:32.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584923 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-10-13T10:24:03.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,584923,0,550080,TO_TIMESTAMP('2021-10-13 13:24:03','YYYY-MM-DD HH24:MI:SS'),100,'30 00 * * *','This process is scheduled to be run each night to perform business partner address updates that were scheduled, where one location is to replace another.','D',0,'D','Y','N',7,'N','C_BPartner_Location_UpdateFromPreviousAddress','N','P','C','NEW',100,TO_TIMESTAMP('2021-10-13 13:24:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-13T10:27:55.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='00 1 * * *',Updated=TO_TIMESTAMP('2021-10-13 13:27:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550080
;

-- 2021-10-13T13:40:45.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS(SELECT * FROM c_bpartner_location l WHERE l.c_bpartner_location_id = c_bpartner_location.c_bpartner_location_id AND l.c_bpartner_id = @C_BPartner_ID/-1@ AND l.c_bpartner_location_id != @C_BPartner_Location_ID/-1@ AND l.isactive = ''Y'' AND NOT EXISTS(SELECT 1 FROM c_bpartner_location l1 WHERE l.c_bpartner_location_id = l1.previous_Id 
AND l1.c_bpartner_location_id != @C_BPartner_Location_ID/-1@))',Updated=TO_TIMESTAMP('2021-10-13 16:40:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540557
;

-- 2021-10-13T15:00:10.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545065,0,TO_TIMESTAMP('2021-10-13 18:00:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Das Terminieren einer Adresse ist nur mit einem zukünftigen Datum möglich.
Wenn Sie eine Terminierung für das heutige oder ein vergangenes Datum 
vornehmen wollen, geben Sie stattdessen bitte das morgige Datum an.','E',TO_TIMESTAMP('2021-10-13 18:00:10','YYYY-MM-DD HH24:MI:SS'),100,'AddressTerminatedInThePast')
;

-- 2021-10-13T15:00:10.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545065 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-10-13T15:00:50.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Terminating an address is only possible with a future date. If you want to terminate an address for today''s date or for a date in the past please enter tomorrow''s date instead.',Updated=TO_TIMESTAMP('2021-10-13 18:00:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545065
;

-- 2021-10-13T15:02:20.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2021-10-13 18:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=659672
;

-- 2021-10-13T15:06:02.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET IsActive='N',Updated=TO_TIMESTAMP('2021-10-13 18:06:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550080
;
-- 2021-10-13T15:18:09.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AccessLevel='3',Updated=TO_TIMESTAMP('2021-10-13 18:18:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584923
;

-- 2021-10-14T12:04:31.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580048,0,TO_TIMESTAMP('2021-10-14 15:04:30','YYYY-MM-DD HH24:MI:SS'),100,'Datum ab dem die vorherige Adresse durch diese Adresse ersetzt werden soll','D','Datum ab dem die vorherige Adresse durch diese Adresse ersetzt werden soll','Y','Gültig ab','Gültig ab',TO_TIMESTAMP('2021-10-14 15:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T12:04:31.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580048 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-14T12:04:58.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Date from which onwards the previos address shall be replaced with this address', Help='Date from which onwards the previos address shall be replaced with this address', Name='Valid from', PrintName='Valid from',Updated=TO_TIMESTAMP('2021-10-14 15:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580048 AND AD_Language='en_US'
;

-- 2021-10-14T12:04:58.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580048,'en_US')
;

-- 2021-10-14T12:38:28.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580048, Description='Datum ab dem die vorherige Adresse durch diese Adresse ersetzt werden soll', Help='Datum ab dem die vorherige Adresse durch diese Adresse ersetzt werden soll', Name='Gültig ab',Updated=TO_TIMESTAMP('2021-10-14 15:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=659672
;

-- 2021-10-14T12:38:28.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580048)
;

-- 2021-10-14T12:38:28.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=659672
;

-- 2021-10-14T12:38:28.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(659672)
;

-- 2021-10-14T12:44:18.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540670,0,293,TO_TIMESTAMP('2021-10-14 15:44:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Previous_ID_and_ValidFrom','N',TO_TIMESTAMP('2021-10-14 15:44:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T12:44:18.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540670 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-10-14T12:44:43.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,576812,541195,540670,0,TO_TIMESTAMP('2021-10-14 15:44:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-10-14 15:44:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T12:44:52.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,576824,541196,540670,0,TO_TIMESTAMP('2021-10-14 15:44:52','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',20,TO_TIMESTAMP('2021-10-14 15:44:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T12:45:16.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET EntityType='D',Updated=TO_TIMESTAMP('2021-10-14 15:45:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=541196
;

-- 2021-10-14T12:47:06.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX Previous_ID_and_ValidFrom ON C_BPartner_Location (ValidFrom,Previous_ID)
;
