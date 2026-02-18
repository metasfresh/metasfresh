-- Run mode: SWING_CLIENT

-- Element: IsDunningContact
-- 2025-10-28T07:13:36.017Z
UPDATE AD_Element_Trl SET Name='Mahnung Kontakt', PrintName='Mahnung Kontakt',Updated=TO_TIMESTAMP('2025-10-28 07:13:36.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582402 AND AD_Language='de_CH'
;

-- 2025-10-28T07:13:36.028Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-28T07:13:36.428Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582402,'de_CH')
;

-- Element: IsDunningContact
-- 2025-10-28T07:13:41.827Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-28 07:13:41.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582402 AND AD_Language='en_US'
;

-- 2025-10-28T07:13:41.829Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582402,'en_US')
;

-- Element: IsDunningContact
-- 2025-10-28T07:13:43.448Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-28 07:13:43.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582402 AND AD_Language='de_CH'
;

-- 2025-10-28T07:13:43.452Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582402,'de_CH')
;

-- Element: IsDunningContact
-- 2025-10-28T07:13:52.957Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mahnung Kontakt', PrintName='Mahnung Kontakt',Updated=TO_TIMESTAMP('2025-10-28 07:13:52.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582402 AND AD_Language='de_DE'
;

-- 2025-10-28T07:13:52.958Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-28T07:13:56.610Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582402,'de_DE')
;

-- 2025-10-28T07:13:56.612Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582402,'de_DE')
;

-- 2025-10-28T07:16:09.040Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584179,0,'IsDunningContact_Default',TO_TIMESTAMP('2025-10-28 07:16:08.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Mahnung Standard Kontakt','Mahnung Standard Kontakt',TO_TIMESTAMP('2025-10-28 07:16:08.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-28T07:16:09.045Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584179 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsDunningContact_Default
-- 2025-10-28T07:16:35.711Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dunning default contact', PrintName='Dunning default contact',Updated=TO_TIMESTAMP('2025-10-28 07:16:35.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584179 AND AD_Language='en_US'
;

-- 2025-10-28T07:16:35.712Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-28T07:16:35.929Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584179,'en_US')
;

-- Element: IsDunningContact_Default
-- 2025-10-28T07:16:36.601Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-28 07:16:36.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584179 AND AD_Language='de_CH'
;

-- 2025-10-28T07:16:36.605Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584179,'de_CH')
;

-- Element: IsDunningContact_Default
-- 2025-10-28T07:54:26.399Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-28 07:54:26.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584179 AND AD_Language='de_DE'
;

-- 2025-10-28T07:54:26.411Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584179,'de_DE')
;

-- 2025-10-28T07:54:26.426Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584179,'de_DE')
;

-- Column: AD_User.IsDunningContact_Default
-- 2025-10-28T07:56:39.380Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591444,584179,0,20,114,'XX','IsDunningContact_Default',TO_TIMESTAMP('2025-10-28 07:56:39.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Mahnung Standard Kontakt',0,0,TO_TIMESTAMP('2025-10-28 07:56:39.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-28T07:56:39.386Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591444 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-28T07:56:39.397Z
/* DDL */  select update_Column_Translation_From_AD_Element(584179)
;

-- 2025-10-28T07:56:44.066Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN IsDunningContact_Default CHAR(1) DEFAULT ''N'' CHECK (IsDunningContact_Default IN (''Y'',''N'')) NOT NULL')
;

-- 2025-10-28T07:57:42.039Z
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540827
;

-- 2025-10-28T07:57:42.060Z
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540827
;

DROP INDEX IF EXISTS ad_user_isdunningcontact
;

-- 2025-10-28T08:09:27.648Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,BeforeChangeWarning,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540833,0,114,'IsDunningContact_Default = ''N''','SQLS','Möchten sie wirklich den Standard Mahnungs Kontakt ändern?',TO_TIMESTAMP('2025-10-28 08:09:27.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Es darf nur eine Standard Mahnungs Kontakt aktiviert sein. Bei Änderung wird bei dem vorherige Standard Mahnungs Kontakt automatisch der Haken entfernt.','Y','Y','ad_user_c_bpartner_id_isDunningContact_Default_uq','N',TO_TIMESTAMP('2025-10-28 08:09:27.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_BPartner_ID IS NOT NULL AND IsDunningContact_Default = ''Y''')
;

-- 2025-10-28T08:09:27.654Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540833 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-10-28T08:09:49.731Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5844,541479,540833,0,TO_TIMESTAMP('2025-10-28 08:09:49.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2025-10-28 08:09:49.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-28T08:11:31.532Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one default dunning contact may be activated. If changed, the previous standard dunning contact automatically removes the hook.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-28 08:11:31.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540833 AND AD_Language='en_US'
;

-- 2025-10-28T08:11:31.534Z
UPDATE AD_Index_Table base SET ErrorMsg=trl.ErrorMsg, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Index_Table_Trl trl  WHERE trl.AD_Index_Table_ID=base.AD_Index_Table_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-28T08:11:32.897Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-28 08:11:32.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540833 AND AD_Language='de_CH'
;

-- 2025-10-28T08:11:34.205Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-28 08:11:34.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540833 AND AD_Language='de_DE'
;

-- 2025-10-28T15:50:02.115Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591444,541480,540833,0,TO_TIMESTAMP('2025-10-28 15:50:01.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',20,TO_TIMESTAMP('2025-10-28 15:50:01.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-28T15:50:03.645Z
DROP INDEX IF EXISTS ad_user_c_bpartner_id_isdunningcontact_default_uq
;

-- 2025-10-28T15:50:03.650Z
CREATE UNIQUE INDEX ad_user_c_bpartner_id_isDunningContact_Default_uq ON AD_User (C_BPartner_ID,IsDunningContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsDunningContact_Default = 'Y'
;

-- 2025-10-28T15:50:03.662Z
CREATE OR REPLACE FUNCTION ad_user_c_bpartner_id_isDunningContact_Default_uq_tgfn()
    RETURNS TRIGGER AS $ad_user_c_bpartner_id_isDunningContact_Default_uq_tg$
BEGIN
    IF TG_OP='INSERT' THEN
        UPDATE AD_User SET IsDunningContact_Default = 'N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsDunningContact_Default=NEW.IsDunningContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND isDunningContact_Default = 'Y';
    ELSE
        IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsDunningContact_Default<>NEW.IsDunningContact_Default THEN
            UPDATE AD_User SET IsDunningContact_Default = 'N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsDunningContact_Default=NEW.IsDunningContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND isDunningContact_Default = 'Y';
        END IF;
    END IF;
    RETURN NEW;
END;
$ad_user_c_bpartner_id_isDunningContact_Default_uq_tg$ LANGUAGE plpgsql;
;

-- 2025-10-28T15:50:03.664Z
DROP TRIGGER IF EXISTS ad_user_c_bpartner_id_isDunningContact_Default_uq_tg ON AD_User
;

-- 2025-10-28T15:50:03.665Z
CREATE TRIGGER ad_user_c_bpartner_id_isDunningContact_Default_uq_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE ad_user_c_bpartner_id_isDunningContact_Default_uq_tgfn()
;

-- Field: Geschäftspartner(123,D) -> Nutzer / Kontakt(496,D) -> Mahnung Standard Kontakt
-- Column: AD_User.IsDunningContact_Default
-- 2025-10-28T08:14:18.512Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591444,755106,0,496,TO_TIMESTAMP('2025-10-28 08:14:18.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Mahnung Standard Kontakt',TO_TIMESTAMP('2025-10-28 08:14:18.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-28T08:14:18.514Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-28T08:14:18.517Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584179)
;

-- 2025-10-28T08:14:18.526Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755106
;

-- 2025-10-28T08:14:18.527Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755106)
;

-- UI Element: Geschäftspartner(123,D) -> Nutzer / Kontakt(496,D) -> main -> 10 -> main.Mahnung Standard Kontakt
-- Column: AD_User.IsDunningContact_Default
-- 2025-10-28T08:16:58.190Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755106,0,496,540578,637956,'F',TO_TIMESTAMP('2025-10-28 08:16:58.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Mahnung Standard Kontakt',260,0,0,TO_TIMESTAMP('2025-10-28 08:16:58.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Nutzer / Kontakt(496,D) -> main -> 10 -> main.Mahnung Standard Kontakt
-- Column: AD_User.IsDunningContact_Default
-- 2025-10-28T08:19:42.770Z
UPDATE AD_UI_Element SET SeqNo=147,Updated=TO_TIMESTAMP('2025-10-28 08:19:42.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637956
;

