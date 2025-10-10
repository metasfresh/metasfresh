



-- 2025-10-07T18:06:53.489Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584100,0,'PasswordChangeDate',TO_TIMESTAMP('2025-10-07 18:06:53.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Passwortänderungsdatum','Passwortänderungsdatum',TO_TIMESTAMP('2025-10-07 18:06:53.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-07T18:06:53.495Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584100 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PasswordChangeDate
-- 2025-10-07T18:07:10.274Z
UPDATE AD_Element_Trl SET Name='Password Change Date', PrintName='Password Change Date',Updated=TO_TIMESTAMP('2025-10-07 18:07:10.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584100 AND AD_Language='en_US'
;

-- 2025-10-07T18:07:10.276Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T18:07:10.534Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584100,'en_US')
;

-- Column: AD_User.PasswordChangeDate
-- 2025-10-07T18:07:19.551Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591281,584100,0,15,114,'XX','PasswordChangeDate',TO_TIMESTAMP('2025-10-07 18:07:19.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Passwortänderungsdatum',0,0,TO_TIMESTAMP('2025-10-07 18:07:19.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-07T18:07:19.554Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591281 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-07T18:07:19.558Z
/* DDL */  select update_Column_Translation_From_AD_Element(584100)
;

-- Column: AD_User.PasswordChangeDate
-- 2025-10-07T18:08:54.572Z
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2025-10-07 18:08:54.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591281
;



-- 2025-10-07T18:12:10.182Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN PasswordChangeDate TIMESTAMP WITH TIME ZONE')
;


-- Value: AD_User_ChangePassword
-- Classname: de.metas.user.process.AD_User_ChangePassword
-- 2025-10-07T18:28:12.159Z
UPDATE AD_Process SET Description='This process allows changing another user''s password. The current password must be known unless the logged-in role has the "Allow Password Change For Others" permission.
',Updated=TO_TIMESTAMP('2025-10-07 18:28:12.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540799
;

-- 2025-10-07T18:28:12.163Z
UPDATE AD_Process_Trl trl SET Description='This process allows changing another user''s password. The current password must be known unless the logged-in role has the "Allow Password Change For Others" permission.
' WHERE AD_Process_ID=540799 AND AD_Language='de_DE'
;

-- Value: AD_User_ChangePassword
-- Classname: de.metas.user.process.AD_User_ChangePassword
-- 2025-10-07T18:29:07.220Z
UPDATE AD_Process SET Description='',Updated=TO_TIMESTAMP('2025-10-07 18:29:07.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540799
;

-- 2025-10-07T18:29:07.221Z
UPDATE AD_Process_Trl trl SET Description='' WHERE AD_Process_ID=540799 AND AD_Language='de_DE'
;

-- Element: OldPassword
-- 2025-10-07T18:29:55.165Z
UPDATE AD_Element_Trl SET Description='The current password must be known unless the logged-in role has the "Allow Password Change For Others" permission.',Updated=TO_TIMESTAMP('2025-10-07 18:29:55.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2571 AND AD_Language='en_US'
;

-- 2025-10-07T18:29:55.166Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T18:29:55.467Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2571,'en_US')
;

-- Element: OldPassword
-- 2025-10-07T18:37:30.984Z
UPDATE AD_Element_Trl SET Description='Das aktuelle Passwort muss bekannt sein, es sei denn, die angemeldete Rolle verfügt über die Berechtigung „Passwortänderung für andere erlauben“.',Updated=TO_TIMESTAMP('2025-10-07 18:37:30.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2571 AND AD_Language='de_CH'
;

-- 2025-10-07T18:37:30.987Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T18:37:31.374Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2571,'de_CH')
;

-- Element: OldPassword
-- 2025-10-07T18:37:33.434Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-07 18:37:33.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2571 AND AD_Language='de_CH'
;

-- 2025-10-07T18:37:33.436Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2571,'de_CH')
;

-- Element: OldPassword
-- 2025-10-07T18:37:40.994Z
UPDATE AD_Element_Trl SET Description='Das aktuelle Passwort muss bekannt sein, es sei denn, die angemeldete Rolle verfügt über die Berechtigung „Passwortänderung für andere erlauben“.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-07 18:37:40.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2571 AND AD_Language='de_DE'
;

-- 2025-10-07T18:37:40.995Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T18:37:41.477Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2571,'de_DE')
;

-- 2025-10-07T18:37:41.478Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2571,'de_DE')
;

-- Element: OldPassword
-- 2025-10-07T18:38:14.052Z
UPDATE AD_Element_Trl SET Description='Das alte Passwort muss bekannt sein, es sei denn, die angemeldete Rolle verfügt über die Berechtigung „Passwortänderung für andere erlauben“.',Updated=TO_TIMESTAMP('2025-10-07 18:38:14.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2571 AND AD_Language='de_DE'
;

-- 2025-10-07T18:38:14.053Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T18:38:14.391Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2571,'de_DE')
;

-- 2025-10-07T18:38:14.393Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2571,'de_DE')
;

-- Element: OldPassword
-- 2025-10-07T18:38:27.877Z
UPDATE AD_Element_Trl SET Description='Das alte Passwort muss bekannt sein, es sei denn, die angemeldete Rolle verfügt über die Berechtigung „Passwortänderung für andere erlauben“.',Updated=TO_TIMESTAMP('2025-10-07 18:38:27.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2571 AND AD_Language='de_CH'
;

-- 2025-10-07T18:38:27.878Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T18:38:28.085Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2571,'de_CH')
;




