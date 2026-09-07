-- Run mode: SWING_CLIENT

-- 2026-03-18T07:04:08.111Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584685,0,'GLN_GCPLength',TO_TIMESTAMP('2026-03-18 07:04:07.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Länge des GCP (GS1 Company Prefix) in der GLN','D','Y','GLN-GCP Länge','GLN-GCP Länge',TO_TIMESTAMP('2026-03-18 07:04:07.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-18T07:04:08.133Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584685 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: GLN_GCPLength
-- 2026-03-18T07:04:58.556Z
UPDATE AD_Element_Trl SET Description='Length of the GCP (GS1 Company Prefix) in the GLN', IsTranslated='Y', Name='GLN-GCP Length', PrintName='GLN-GCP Length',Updated=TO_TIMESTAMP('2026-03-18 07:04:58.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584685 AND AD_Language='en_US'
;

-- 2026-03-18T07:04:58.560Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-18T07:04:59.205Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584685,'en_US')
;

-- Element: GLN_GCPLength
-- 2026-03-18T07:05:00.247Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-18 07:05:00.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584685 AND AD_Language='de_CH'
;

-- 2026-03-18T07:05:00.249Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584685,'de_CH')
;

-- Element: GLN_GCPLength
-- 2026-03-18T07:05:06.414Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-18 07:05:06.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584685 AND AD_Language='de_DE'
;

-- 2026-03-18T07:05:06.416Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584685,'de_DE')
;

-- 2026-03-18T07:05:06.417Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584685,'de_DE')
;

-- Column: C_BPartner_Location.GLN_GCPLength
-- 2026-03-18T07:07:55.885Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592254,584685,0,11,293,'XX','GLN_GCPLength',TO_TIMESTAMP('2026-03-18 07:07:55.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','7','Länge des GCP (GS1 Company Prefix) in der GLN','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'GLN-GCP Länge',0,0,TO_TIMESTAMP('2026-03-18 07:07:55.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-03-18T07:07:55.890Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592254 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-03-18T07:07:55.898Z
/* DDL */  select update_Column_Translation_From_AD_Element(584685)
;

-- 2026-03-18T07:11:37.136Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN GLN_GCPLength NUMERIC(10) DEFAULT 7 NOT NULL')
;

-- 2026-03-18T07:12:15.889Z
INSERT INTO t_alter_column values('c_bpartner_location','GLN_GCPLength','NUMERIC(10)',null,'7')
;

-- 2026-03-18T07:12:15.896Z
UPDATE C_BPartner_Location SET GLN_GCPLength=7 WHERE GLN_GCPLength IS NULL
;

-- Field: Geschäftspartner(123,D) -> Adresse(222,D) -> GLN-GCP Länge
-- Column: C_BPartner_Location.GLN_GCPLength
-- 2026-03-18T07:08:09.402Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592254,774942,0,222,TO_TIMESTAMP('2026-03-18 07:08:09.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Länge des GCP (GS1 Company Prefix) in der GLN',2,'D','Y','N','N','N','N','N','N','N','GLN-GCP Länge',TO_TIMESTAMP('2026-03-18 07:08:09.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-18T07:08:09.405Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-18T07:08:09.410Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584685)
;

-- 2026-03-18T07:08:09.423Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774942
;

-- 2026-03-18T07:08:09.432Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774942)
;

-- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.GLN-GCP Länge
-- Column: C_BPartner_Location.GLN_GCPLength
-- 2026-03-18T07:08:53.065Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774942,0,222,1000034,648587,'F',TO_TIMESTAMP('2026-03-18 07:08:52.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Länge des GCP (GS1 Company Prefix) in der GLN','Y','Y','N','Y','N','N','N',0,'GLN-GCP Länge',45,0,0,TO_TIMESTAMP('2026-03-18 07:08:52.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Geschäftspartner Dist-Orgs(540366,U) -> Adresse(540874,D) -> GLN-GCP Länge
-- Column: C_BPartner_Location.GLN_GCPLength
-- 2026-03-18T11:19:18.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592254,774943,0,540874,TO_TIMESTAMP('2026-03-18 11:19:18.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Länge des GCP (GS1 Company Prefix) in der GLN',2,'D','Y','N','N','N','N','N','N','N','GLN-GCP Länge',TO_TIMESTAMP('2026-03-18 11:19:18.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-18T11:19:18.379Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-18T11:19:18.392Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584685)
;

-- 2026-03-18T11:19:18.415Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774943
;

-- 2026-03-18T11:19:18.432Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774943)
;

-- UI Element: Geschäftspartner Dist-Orgs(540366,U) -> Adresse(540874,D) -> main -> 10 -> default.GLN-GCP Länge
-- Column: C_BPartner_Location.GLN_GCPLength
-- 2026-03-18T11:20:16.636Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774943,0,540874,541147,648588,'F',TO_TIMESTAMP('2026-03-18 11:20:16.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Länge des GCP (GS1 Company Prefix) in der GLN','Y','Y','N','Y','N','N','N',0,'GLN-GCP Länge',45,0,0,TO_TIMESTAMP('2026-03-18 11:20:16.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Organisation Stammdaten(540676,D) -> Adresse(541854,U) -> GLN-GCP Länge
-- Column: C_BPartner_Location.GLN_GCPLength
-- 2026-03-18T11:24:32.901Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592254,774944,0,541854,TO_TIMESTAMP('2026-03-18 11:24:32.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Länge des GCP (GS1 Company Prefix) in der GLN',2,'U','Y','N','N','N','N','N','N','N','GLN-GCP Länge',TO_TIMESTAMP('2026-03-18 11:24:32.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-18T11:24:32.904Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774944 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-18T11:24:32.906Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584685)
;

-- 2026-03-18T11:24:32.913Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774944
;

-- 2026-03-18T11:24:32.914Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774944)
;

-- UI Element: Organisation Stammdaten(540676,D) -> Adresse(541854,U) -> main -> 10 -> default.GLN-GCP Länge
-- Column: C_BPartner_Location.GLN_GCPLength
-- 2026-03-18T11:25:01.842Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774944,0,541854,542736,648589,'F',TO_TIMESTAMP('2026-03-18 11:25:01.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Länge des GCP (GS1 Company Prefix) in der GLN','Y','Y','N','Y','N','N','N',0,'GLN-GCP Länge',150,0,0,TO_TIMESTAMP('2026-03-18 11:25:01.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Organisation Stammdaten(540676,D) -> Adresse(541854,U) -> main -> 10 -> default.GLN-GCP Länge
-- Column: C_BPartner_Location.GLN_GCPLength
-- 2026-03-18T11:31:17.233Z
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2026-03-18 11:31:17.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648589
;
