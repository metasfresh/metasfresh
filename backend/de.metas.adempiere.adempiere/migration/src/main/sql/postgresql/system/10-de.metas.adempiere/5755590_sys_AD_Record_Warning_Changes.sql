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

