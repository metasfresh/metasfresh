-- Run mode: SWING_CLIENT

-- Element: AD_Record_Warning_ID
-- 2025-05-20T15:36:46.755Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Warnhinweise', PrintName='Warnhinweise',Updated=TO_TIMESTAMP('2025-05-20 15:36:46.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583396 AND AD_Language='de_CH'
;

-- 2025-05-20T15:36:46.763Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-20T15:36:47.599Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583396,'de_CH')
;

-- Element: AD_Record_Warning_ID
-- 2025-05-20T15:36:54.217Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Warnhinweise', PrintName='Warnhinweise',Updated=TO_TIMESTAMP('2025-05-20 15:36:54.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583396 AND AD_Language='de_DE'
;

-- 2025-05-20T15:36:54.224Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-20T15:37:01.455Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583396,'de_DE')
;

-- 2025-05-20T15:37:01.462Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583396,'de_DE')
;

-- Element: MsgText
-- 2025-05-20T15:40:24.790Z
UPDATE AD_Element_Trl SET Description='Textuelle Informations-, Menü- oder Fehlermeldung', Help='Der Hinweistext gibt die Nachricht an, die angezeigt wird.', Name='Hinweistext', PrintName='Hinweistext',Updated=TO_TIMESTAMP('2025-05-20 15:40:24.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=463 AND AD_Language='de_CH'
;

-- 2025-05-20T15:40:24.796Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-20T15:40:26.803Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(463,'de_CH')
;

-- Element: MsgText
-- 2025-05-20T15:40:28.540Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-20 15:40:28.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=463 AND AD_Language='de_CH'
;

-- 2025-05-20T15:40:28.552Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(463,'de_CH')
;

-- Element: MsgText
-- 2025-05-20T15:40:59.963Z
UPDATE AD_Element_Trl SET Description='Textuelle Informations-, Menü- oder Fehlermeldung', Help='Der Hinweistext gibt die Nachricht an, die angezeigt wird.', IsTranslated='Y', Name='Hinweistext', PrintName='Hinweistext',Updated=TO_TIMESTAMP('2025-05-20 15:40:59.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=463 AND AD_Language='de_DE'
;

-- 2025-05-20T15:40:59.964Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-20T15:41:00.659Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(463,'de_DE')
;

-- 2025-05-20T15:41:00.661Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(463,'de_DE')
;

-- Column: AD_Record_Warning.AD_User_ID
-- 2025-05-20T15:48:54.796Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590005,138,0,30,542455,'XX','AD_User_ID',TO_TIMESTAMP('2025-05-20 15:48:54.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','User within the system - Internal or Business Partner Contact','D',0,10,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ansprechpartner',0,0,TO_TIMESTAMP('2025-05-20 15:48:54.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-05-20T15:48:54.799Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590005 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-20T15:48:54.801Z
/* DDL */  select update_Column_Translation_From_AD_Element(138)
;

-- 2025-05-20T15:48:57.549Z
/* DDL */ SELECT public.db_alter_table('AD_Record_Warning','ALTER TABLE public.AD_Record_Warning ADD COLUMN AD_User_ID NUMERIC(10)')
;

-- 2025-05-20T15:48:57.559Z
ALTER TABLE AD_Record_Warning ADD CONSTRAINT ADUser_ADRecordWarning FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;


-----------------------



-- Migrate existing data: set AD_User_ID to "Migration"


UPDATE AD_Record_Warning SET AD_User_ID = 99 WHERE AD_User_ID  IS NULL;

-----------------------


-- Column: AD_Record_Warning.AD_User_ID
-- 2025-05-20T15:55:43.956Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-05-20 15:55:43.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590005
;

-- 2025-05-20T15:55:45.097Z
INSERT INTO t_alter_column values('ad_record_warning','AD_User_ID','NUMERIC(10)',null,null)
;

-- 2025-05-20T15:55:45.100Z
INSERT INTO t_alter_column values('ad_record_warning','AD_User_ID',null,'NOT NULL',null)
;






-- Field: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> Ansprechpartner
-- Column: AD_Record_Warning.AD_User_ID
-- 2025-05-20T16:11:04.451Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590005,746208,0,547698,TO_TIMESTAMP('2025-05-20 16:11:04.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact',10,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2025-05-20 16:11:04.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-20T16:11:04.453Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=746208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-20T16:11:04.455Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138)
;

-- 2025-05-20T16:11:04.502Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=746208
;

-- 2025-05-20T16:11:04.505Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(746208)
;

-- UI Column: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20
-- UI Element Group: user
-- 2025-05-20T16:11:26.825Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547678,552988,TO_TIMESTAMP('2025-05-20 16:11:26.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','user',30,TO_TIMESTAMP('2025-05-20 16:11:26.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20
-- UI Element Group: user
-- 2025-05-20T16:11:30.200Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2025-05-20 16:11:30.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552988
;

-- UI Column: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-05-20T16:11:33.567Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2025-05-20 16:11:33.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552189
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> user.Ansprechpartner
-- Column: AD_Record_Warning.AD_User_ID
-- 2025-05-20T16:11:45.848Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,746208,0,547698,552988,633169,'F',TO_TIMESTAMP('2025-05-20 16:11:45.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','Ansprechpartner',10,0,0,TO_TIMESTAMP('2025-05-20 16:11:45.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;



-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 10 -> record.Datensatz-ID
-- Column: AD_Record_Warning.Record_ID
-- 2025-05-22T05:55:48.398Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-05-22 05:55:48.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627398
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> user.Ansprechpartner
-- Column: AD_Record_Warning.AD_User_ID
-- 2025-05-22T05:55:48.407Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-05-22 05:55:48.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633169
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 10 -> default.Erstellt
-- Column: AD_Record_Warning.Created
-- 2025-05-22T05:55:48.414Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-05-22 05:55:48.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627402
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 10 -> record.DB-Tabelle
-- Column: AD_Record_Warning.AD_Table_ID
-- 2025-05-22T05:55:48.422Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-05-22 05:55:48.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627397
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 10 -> default.Message Text
-- Column: AD_Record_Warning.MsgText
-- 2025-05-22T05:55:48.429Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-05-22 05:55:48.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627396
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- 2025-05-22T05:55:48.437Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-05-22 05:55:48.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627400
;

-- 2025-05-22T05:56:47.594Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583654,0,'Acknowledged',TO_TIMESTAMP('2025-05-22 05:56:47.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Acknowledged','Acknowledged',TO_TIMESTAMP('2025-05-22 05:56:47.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-22T05:56:47.602Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583654 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Acknowledged
-- 2025-05-22T05:56:59.321Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bestätigt', PrintName='Bestätigt',Updated=TO_TIMESTAMP('2025-05-22 05:56:59.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583654 AND AD_Language='de_CH'
;

-- 2025-05-22T05:56:59.328Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-22T05:57:00.175Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583654,'de_CH')
;

-- Element: Acknowledged
-- 2025-05-22T05:57:07.239Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bestätigt', PrintName='Bestätigt',Updated=TO_TIMESTAMP('2025-05-22 05:57:07.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583654 AND AD_Language='de_DE'
;

-- 2025-05-22T05:57:07.246Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-22T05:57:09.255Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583654,'de_DE')
;

-- 2025-05-22T05:57:09.257Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583654,'de_DE')
;

-- 2025-05-22T05:57:46.195Z
UPDATE AD_Element SET ColumnName='IsAcknowledged',Updated=TO_TIMESTAMP('2025-05-22 05:57:46.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583654
;

-- 2025-05-22T05:57:46.201Z
UPDATE AD_Column SET ColumnName='IsAcknowledged' WHERE AD_Element_ID=583654
;

-- 2025-05-22T05:57:46.206Z
UPDATE AD_Process_Para SET ColumnName='IsAcknowledged' WHERE AD_Element_ID=583654
;

-- 2025-05-22T05:57:46.224Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583654,'de_DE')
;

-- Column: AD_Record_Warning.IsAcknowledged
-- 2025-05-22T05:58:03.308Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590018,583654,0,20,542455,'XX','IsAcknowledged',TO_TIMESTAMP('2025-05-22 05:58:03.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Bestätigt',0,0,TO_TIMESTAMP('2025-05-22 05:58:03.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-05-22T05:58:03.316Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590018 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-22T05:58:03.330Z
/* DDL */  select update_Column_Translation_From_AD_Element(583654)
;

-- 2025-05-22T05:58:06.383Z
/* DDL */ SELECT public.db_alter_table('AD_Record_Warning','ALTER TABLE public.AD_Record_Warning ADD COLUMN IsAcknowledged CHAR(1) DEFAULT ''N'' CHECK (IsAcknowledged IN (''Y'',''N'')) NOT NULL')
;

-- Field: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> Bestätigt
-- Column: AD_Record_Warning.IsAcknowledged
-- 2025-05-22T05:58:30.107Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590018,746216,0,547698,TO_TIMESTAMP('2025-05-22 05:58:29.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Bestätigt',TO_TIMESTAMP('2025-05-22 05:58:29.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-22T05:58:30.109Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=746216 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-22T05:58:30.110Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583654)
;

-- 2025-05-22T05:58:30.113Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=746216
;

-- 2025-05-22T05:58:30.114Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(746216)
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> flags.Bestätigt
-- Column: AD_Record_Warning.IsAcknowledged
-- 2025-05-22T05:59:02.039Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,746216,0,547698,552188,633172,'F',TO_TIMESTAMP('2025-05-22 05:59:01.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Bestätigt',20,0,0,TO_TIMESTAMP('2025-05-22 05:59:01.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- 2025-05-22T05:59:24.151Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-05-22 05:59:24.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627400
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> flags.Bestätigt
-- Column: AD_Record_Warning.IsAcknowledged
-- 2025-05-22T05:59:24.158Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-05-22 05:59:24.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633172
;

