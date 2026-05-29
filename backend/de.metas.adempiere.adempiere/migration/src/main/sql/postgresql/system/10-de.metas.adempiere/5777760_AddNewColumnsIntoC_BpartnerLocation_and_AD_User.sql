-- Run mode: SWING_CLIENT

-- 2025-11-20T10:30:13.641Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584243,0,'IsDefaultVisitorAddress',TO_TIMESTAMP('2025-11-20 10:30:13.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Indicates whether this address is the default visitor address.','D','Y','Default Visitor Address','Default Visitor Address',TO_TIMESTAMP('2025-11-20 10:30:13.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T10:30:13.650Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584243 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsDefaultVisitorAddress
-- 2025-11-20T10:30:34.810Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Standard‑Besucheradresse', PrintName='Standard‑Besucheradresse',Updated=TO_TIMESTAMP('2025-11-20 10:30:34.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584243 AND AD_Language='de_CH'
;

-- 2025-11-20T10:30:34.811Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-20T10:30:35.022Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584243,'de_CH')
;

-- Element: IsDefaultVisitorAddress
-- 2025-11-20T10:30:39.210Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Standard‑Besucheradresse', PrintName='Standard‑Besucheradresse',Updated=TO_TIMESTAMP('2025-11-20 10:30:39.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584243 AND AD_Language='de_DE'
;

-- 2025-11-20T10:30:39.211Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-20T10:30:39.674Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584243,'de_DE')
;

-- 2025-11-20T10:30:39.676Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584243,'de_DE')
;

-- Element: IsDefaultVisitorAddress
-- 2025-11-20T10:30:44.441Z
UPDATE AD_Element_Trl SET Description='Gibt an, ob diese Adresse die Standard‑Besucheradresse ist.',Updated=TO_TIMESTAMP('2025-11-20 10:30:44.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584243 AND AD_Language='de_DE'
;

-- 2025-11-20T10:30:44.442Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-20T10:30:44.843Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584243,'de_DE')
;

-- 2025-11-20T10:30:44.845Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584243,'de_DE')
;

-- Element: IsDefaultVisitorAddress
-- 2025-11-20T10:30:48.018Z
UPDATE AD_Element_Trl SET Description='Gibt an, ob diese Adresse die Standard‑Besucheradresse ist.',Updated=TO_TIMESTAMP('2025-11-20 10:30:48.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584243 AND AD_Language='de_CH'
;

-- 2025-11-20T10:30:48.019Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-20T10:30:48.221Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584243,'de_CH')
;

-- Element: IsDefaultVisitorAddress
-- 2025-11-20T10:30:53.391Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-20 10:30:53.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584243 AND AD_Language='en_US'
;

-- 2025-11-20T10:30:53.393Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584243,'en_US')
;

-- Column: C_BPartner_Location.IsDefaultVisitorAddress
-- 2025-11-20T10:31:09.721Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591595,584243,0,20,293,'XX','IsDefaultVisitorAddress',TO_TIMESTAMP('2025-11-20 10:31:09.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Gibt an, ob diese Adresse die Standard‑Besucheradresse ist.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Standard‑Besucheradresse',0,0,TO_TIMESTAMP('2025-11-20 10:31:09.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-20T10:31:09.723Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591595 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-20T10:31:09.726Z
/* DDL */  select update_Column_Translation_From_AD_Element(584243)
;

-- 2025-11-20T10:31:11.906Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN IsDefaultVisitorAddress CHAR(1) DEFAULT ''N'' CHECK (IsDefaultVisitorAddress IN (''Y'',''N'')) NOT NULL')
;

-- 2025-11-20T10:34:53.803Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,BeforeChangeWarning,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540839,0,293,'IsDefaultVisitorAddress=''N''','SQLS','Möchten sie wirklich die standard Rechnungsanschrift ändern?',TO_TIMESTAMP('2025-11-20 10:34:53.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Only one default visitor address may be active at a time.','Y','Y','IsDefaultVisitorAddress','N',TO_TIMESTAMP('2025-11-20 10:34:53.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsDefaultVisitorAddress=''Y'' and isActive=''Y''')
;

-- 2025-11-20T10:34:53.806Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540839 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-11-20T10:35:06.550Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-20 10:35:06.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540839 AND AD_Language='en_US'
;

-- 2025-11-20T10:35:21.778Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Es darf nur eine Standardbesucheradresse aktiviert sein.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-20 10:35:21.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540839 AND AD_Language='de_CH'
;

-- 2025-11-20T10:35:21.780Z
UPDATE AD_Index_Table base SET ErrorMsg=trl.ErrorMsg, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Index_Table_Trl trl  WHERE trl.AD_Index_Table_ID=base.AD_Index_Table_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-20T10:35:25.466Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Es darf nur eine Standardbesucheradresse aktiviert sein.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-20 10:35:25.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540839 AND AD_Language='de_DE'
;

-- 2025-11-20T10:35:25.467Z
UPDATE AD_Index_Table base SET ErrorMsg=trl.ErrorMsg, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Index_Table_Trl trl  WHERE trl.AD_Index_Table_ID=base.AD_Index_Table_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-20T10:35:43.383Z
UPDATE AD_Index_Table SET BeforeChangeWarning='',Updated=TO_TIMESTAMP('2025-11-20 10:35:43.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540839
;

-- 2025-11-20T10:36:01.398Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2958,541488,540839,0,TO_TIMESTAMP('2025-11-20 10:36:01.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2025-11-20 10:36:01.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T10:36:24.670Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591595,541489,540839,0,TO_TIMESTAMP('2025-11-20 10:36:24.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',20,TO_TIMESTAMP('2025-11-20 10:36:24.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T10:36:32.421Z
CREATE UNIQUE INDEX IsDefaultVisitorAddress ON C_BPartner_Location (C_BPartner_ID,IsDefaultVisitorAddress) WHERE IsDefaultVisitorAddress='Y' and isActive='Y'
;

-- 2025-11-20T10:36:32.427Z
CREATE OR REPLACE FUNCTION IsDefaultVisitorAddress_tgfn()
    RETURNS TRIGGER AS $IsDefaultVisitorAddress_tg$
BEGIN
    IF TG_OP='INSERT' THEN
        UPDATE C_BPartner_Location SET IsDefaultVisitorAddress='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsDefaultVisitorAddress=NEW.IsDefaultVisitorAddress AND C_BPartner_Location_ID<>NEW.C_BPartner_Location_ID AND IsDefaultVisitorAddress='Y' and isActive='Y';
    ELSE
        IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsDefaultVisitorAddress<>NEW.IsDefaultVisitorAddress THEN
            UPDATE C_BPartner_Location SET IsDefaultVisitorAddress='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsDefaultVisitorAddress=NEW.IsDefaultVisitorAddress AND C_BPartner_Location_ID<>NEW.C_BPartner_Location_ID AND IsDefaultVisitorAddress='Y' and isActive='Y';
        END IF;
    END IF;
    RETURN NEW;
END;
$IsDefaultVisitorAddress_tg$ LANGUAGE plpgsql;
;

-- 2025-11-20T10:36:32.430Z
DROP TRIGGER IF EXISTS IsDefaultVisitorAddress_tg ON C_BPartner_Location
;

-- 2025-11-20T10:36:32.431Z
CREATE TRIGGER IsDefaultVisitorAddress_tg BEFORE INSERT OR UPDATE  ON C_BPartner_Location FOR EACH ROW EXECUTE PROCEDURE IsDefaultVisitorAddress_tgfn()
;

-- Field: Geschäftspartner(123,D) -> Adresse(222,D) -> Standard‑Besucheradresse
-- Column: C_BPartner_Location.IsDefaultVisitorAddress
-- 2025-11-20T10:37:23.862Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591595,756215,0,222,0,TO_TIMESTAMP('2025-11-20 10:37:23.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gibt an, ob diese Adresse die Standard‑Besucheradresse ist.',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Standard‑Besucheradresse',0,0,240,0,1,1,TO_TIMESTAMP('2025-11-20 10:37:23.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T10:37:23.867Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756215 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-20T10:37:23.869Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584243)
;

-- 2025-11-20T10:37:23.878Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756215
;

-- 2025-11-20T10:37:23.881Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756215)
;

-- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Standard‑Besucheradresse
-- Column: C_BPartner_Location.IsDefaultVisitorAddress
-- 2025-11-20T10:37:59.056Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756215,0,222,1000034,638761,'F',TO_TIMESTAMP('2025-11-20 10:37:58.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gibt an, ob diese Adresse die Standard‑Besucheradresse ist.','Y','N','N','Y','Y','N','N','Standard‑Besucheradresse',122,90,0,TO_TIMESTAMP('2025-11-20 10:37:58.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T10:39:37.409Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584244,0,'Department',TO_TIMESTAMP('2025-11-20 10:39:37.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Abteilung','Abteilung',TO_TIMESTAMP('2025-11-20 10:39:37.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T10:39:37.411Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584244 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Department
-- 2025-11-20T10:39:40.127Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-20 10:39:40.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584244 AND AD_Language='de_CH'
;

-- 2025-11-20T10:39:40.130Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584244,'de_CH')
;

-- Element: Department
-- 2025-11-20T10:39:43.793Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-20 10:39:43.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584244 AND AD_Language='de_DE'
;

-- 2025-11-20T10:39:43.798Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584244,'de_DE')
;

-- 2025-11-20T10:39:43.801Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584244,'de_DE')
;

-- Element: Department
-- 2025-11-20T10:39:53.467Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Department', PrintName='Department',Updated=TO_TIMESTAMP('2025-11-20 10:39:53.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584244 AND AD_Language='en_US'
;

-- 2025-11-20T10:39:53.468Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-20T10:39:53.660Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584244,'en_US')
;

-- Column: AD_User.Department
-- 2025-11-20T10:40:19.434Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591596,584244,0,10,114,'XX','Department',TO_TIMESTAMP('2025-11-20 10:40:19.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abteilung',0,0,TO_TIMESTAMP('2025-11-20 10:40:19.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-20T10:40:19.436Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591596 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-20T10:40:19.439Z
/* DDL */  select update_Column_Translation_From_AD_Element(584244)
;

-- 2025-11-20T10:40:30.773Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN Department VARCHAR(255)')
;

