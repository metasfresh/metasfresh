-- Run mode: SWING_CLIENT

-- 2025-11-18T10:26:22.330Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584220,0,'SasSignature',TO_TIMESTAMP('2025-11-18 10:26:22.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','SAS-Signatur','SAS-Signatur',TO_TIMESTAMP('2025-11-18 10:26:22.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-18T10:26:22.356Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584220 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SasSignature
-- 2025-11-18T10:26:46.693Z
UPDATE AD_Element_Trl SET Name='SAS-Signature', PrintName='SAS-Signature',Updated=TO_TIMESTAMP('2025-11-18 10:26:46.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584220 AND AD_Language='en_US'
;

-- 2025-11-18T10:26:46.693Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-18T10:26:47.227Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584220,'en_US')
;

-- Column: ExternalSystem_Outbound_Endpoint.SasSignature
-- 2025-11-18T10:27:13.906Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591552,584220,0,10,542551,'XX','SasSignature',TO_TIMESTAMP('2025-11-18 10:27:13.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SAS-Signatur',0,0,TO_TIMESTAMP('2025-11-18 10:27:13.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-18T10:27:13.908Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591552 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-18T10:27:13.912Z
/* DDL */  select update_Column_Translation_From_AD_Element(584220)
;

-- 2025-11-18T10:27:15.063Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN SasSignature VARCHAR(255)')
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> SAS-Signatur
-- Column: ExternalSystem_Outbound_Endpoint.SasSignature
-- 2025-11-18T10:28:00.579Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591552,756181,0,548506,TO_TIMESTAMP('2025-11-18 10:28:00.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','SAS-Signatur',TO_TIMESTAMP('2025-11-18 10:28:00.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-18T10:28:00.579Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756181 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-18T10:28:00.579Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584220)
;

-- 2025-11-18T10:28:00.610Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756181
;

-- 2025-11-18T10:28:00.610Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756181)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> SAS-Signatur
-- Column: ExternalSystem_Outbound_Endpoint.SasSignature
-- 2025-11-18T10:28:18.908Z
UPDATE AD_Field SET DisplayLogic='@AuthType/X@=''SAS''',Updated=TO_TIMESTAMP('2025-11-18 10:28:18.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756181
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.SAS-Signatur
-- Column: ExternalSystem_Outbound_Endpoint.SasSignature
-- 2025-11-18T10:28:35.071Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,756181,0,548506,638744,553738,'F',TO_TIMESTAMP('2025-11-18 10:28:34.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'SAS-Signatur',110,0,0,TO_TIMESTAMP('2025-11-18 10:28:34.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

