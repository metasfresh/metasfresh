-- Run mode: SWING_CLIENT

-- 2025-08-30T20:01:31.947Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583888,0,'IsAssociation',TO_TIMESTAMP('2025-08-30 20:01:31.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Verband','Verband',TO_TIMESTAMP('2025-08-30 20:01:31.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-30T20:01:31.955Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583888 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAssociation
-- 2025-08-30T20:01:51.890Z
UPDATE AD_Element_Trl SET Name='Association', PrintName='Association',Updated=TO_TIMESTAMP('2025-08-30 20:01:51.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583888 AND AD_Language='en_US'
;

-- 2025-08-30T20:01:51.893Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-30T20:01:52.200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583888,'en_US')
;

-- Column: C_BP_Group.IsAssociation
-- 2025-08-30T20:04:09.272Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590706,583888,0,20,394,'XX','IsAssociation',TO_TIMESTAMP('2025-08-30 20:04:09.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verband',0,0,TO_TIMESTAMP('2025-08-30 20:04:09.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-30T20:04:09.276Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590706 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-30T20:04:09.283Z
/* DDL */  select update_Column_Translation_From_AD_Element(583888)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Verband
-- Column: C_BP_Group.IsAssociation
-- 2025-08-30T20:09:08.719Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590706,752676,0,322,0,TO_TIMESTAMP('2025-08-30 20:09:07.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Verband',0,0,240,0,1,1,TO_TIMESTAMP('2025-08-30 20:09:07.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-30T20:09:08.722Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752676 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-30T20:09:08.724Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583888)
;

-- 2025-08-30T20:09:08.736Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752676
;

-- 2025-08-30T20:09:08.739Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752676)
;

-- 2025-08-30T20:09:30.065Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN IsAssociation CHAR(1) DEFAULT ''N'' CHECK (IsAssociation IN (''Y'',''N'')) NOT NULL')
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 20 -> flags.Verband
-- Column: C_BP_Group.IsAssociation
-- 2025-08-30T20:10:07.137Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752676,0,322,540482,636565,'F',TO_TIMESTAMP('2025-08-30 20:10:06.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Verband',50,0,0,TO_TIMESTAMP('2025-08-30 20:10:06.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-02T06:00:08.888Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590709,187,0,19,394,'XX','C_BPartner_ID',TO_TIMESTAMP('2025-09-02 06:00:08.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Bezeichnet einen Geschäftspartner','D',0,10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2025-09-02 06:00:08.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-02T06:00:08.891Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590709 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-02T06:00:08.897Z
/* DDL */  select update_Column_Translation_From_AD_Element(187)
;

-- 2025-09-02T06:00:11.836Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2025-09-02T06:00:12.069Z
ALTER TABLE C_BP_Group ADD CONSTRAINT CBPartner_CBPGroup FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-02T06:00:49.549Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590710,189,0,19,394,'XX','C_BPartner_Location_ID',TO_TIMESTAMP('2025-09-02 06:00:49.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort',0,0,TO_TIMESTAMP('2025-09-02 06:00:49.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-02T06:00:49.551Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590710 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-02T06:00:49.554Z
/* DDL */  select update_Column_Translation_From_AD_Element(189)
;

-- 2025-09-02T06:00:51.257Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN C_BPartner_Location_ID NUMERIC(10)')
;

-- 2025-09-02T06:00:51.410Z
ALTER TABLE C_BP_Group ADD CONSTRAINT CBPartnerLocation_CBPGroup FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BP_Group.AD_User_ID
-- 2025-09-02T06:01:03.622Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590711,138,0,19,394,'XX','AD_User_ID',TO_TIMESTAMP('2025-09-02 06:01:03.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','User within the system - Internal or Business Partner Contact','D',0,10,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ansprechpartner',0,0,TO_TIMESTAMP('2025-09-02 06:01:03.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-02T06:01:03.624Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590711 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-02T06:01:03.627Z
/* DDL */  select update_Column_Translation_From_AD_Element(138)
;

-- 2025-09-02T06:01:05.838Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN AD_User_ID NUMERIC(10)')
;

-- 2025-09-02T06:01:05.995Z
ALTER TABLE C_BP_Group ADD CONSTRAINT ADUser_CBPGroup FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Geschäftspartner
-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-02T06:05:53.467Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590709,752680,0,322,0,TO_TIMESTAMP('2025-09-02 06:05:53.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',0,'D',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Geschäftspartner',0,0,250,0,1,1,TO_TIMESTAMP('2025-09-02 06:05:53.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-02T06:05:53.469Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752680 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-02T06:05:53.471Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2025-09-02T06:05:53.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752680
;

-- 2025-09-02T06:05:53.644Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752680)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Standort
-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-02T06:06:04.658Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590710,752681,0,322,0,TO_TIMESTAMP('2025-09-02 06:06:04.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',0,'D',0,'Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Standort',0,0,260,0,1,1,TO_TIMESTAMP('2025-09-02 06:06:04.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-02T06:06:04.660Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752681 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-02T06:06:04.662Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189)
;

-- 2025-09-02T06:06:04.674Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752681
;

-- 2025-09-02T06:06:04.675Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752681)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Ansprechpartner
-- Column: C_BP_Group.AD_User_ID
-- 2025-09-02T06:06:15.501Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590711,752682,0,322,0,TO_TIMESTAMP('2025-09-02 06:06:15.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact',0,'D',0,'The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Ansprechpartner',0,0,270,0,1,1,TO_TIMESTAMP('2025-09-02 06:06:15.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-02T06:06:15.503Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752682 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-02T06:06:15.504Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138)
;

-- 2025-09-02T06:06:15.517Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752682
;

-- 2025-09-02T06:06:15.519Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752682)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-02T06:09:27.508Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752680,0,322,540480,636569,'F',TO_TIMESTAMP('2025-09-02 06:09:27.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',30,0,0,TO_TIMESTAMP('2025-09-02 06:09:27.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-02T06:11:32.312Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=138, AD_Val_Rule_ID=540244, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-09-02 06:11:32.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590709
;

-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-02T06:13:30.858Z
UPDATE AD_Column SET AD_Val_Rule_ID=540557,Updated=TO_TIMESTAMP('2025-09-02 06:13:30.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590710
;

-- Column: C_BP_Group.AD_User_ID
-- 2025-09-02T06:14:09.999Z
UPDATE AD_Column SET AD_Val_Rule_ID=123,Updated=TO_TIMESTAMP('2025-09-02 06:14:09.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590711
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-02T06:17:14.068Z
UPDATE AD_UI_Element SET UIStyle='primary', WidgetSize='L',Updated=TO_TIMESTAMP('2025-09-02 06:17:14.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636569
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Standort
-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-02T06:19:16.771Z
UPDATE AD_Field SET DisplayLength=10,Updated=TO_TIMESTAMP('2025-09-02 06:19:16.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752681
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Geschäftspartner
-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-02T06:19:43.399Z
UPDATE AD_Field SET DisplayLength=10,Updated=TO_TIMESTAMP('2025-09-02 06:19:43.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752680
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Ansprechpartner
-- Column: C_BP_Group.AD_User_ID
-- 2025-09-02T06:20:09.886Z
UPDATE AD_Field SET DisplayLength=10, SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2025-09-02 06:20:09.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752682
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Standort
-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-02T06:20:25.485Z
UPDATE AD_Field SET SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2025-09-02 06:20:25.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752681
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Geschäftspartner
-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-02T06:20:33.443Z
UPDATE AD_Field SET SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2025-09-02 06:20:33.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752680
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Standort
-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-02T06:21:33.623Z
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2025-09-02 06:21:33.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752681
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Ansprechpartner
-- Column: C_BP_Group.AD_User_ID
-- 2025-09-02T06:21:47.508Z
UPDATE AD_Field SET IsFieldOnly='Y', IsSameLine='Y',Updated=TO_TIMESTAMP('2025-09-02 06:21:47.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752682
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Standort
-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-02T06:21:55.435Z
UPDATE AD_Field SET IsFieldOnly='Y',Updated=TO_TIMESTAMP('2025-09-02 06:21:55.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752681
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Standort
-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-02T06:22:16.092Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752681,0,322,540480,636570,'F',TO_TIMESTAMP('2025-09-02 06:22:15.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Standort',40,0,0,TO_TIMESTAMP('2025-09-02 06:22:15.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Ansprechpartner
-- Column: C_BP_Group.AD_User_ID
-- 2025-09-02T06:22:24.907Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752682,0,322,540480,636571,'F',TO_TIMESTAMP('2025-09-02 06:22:24.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','N','N',0,'Ansprechpartner',50,0,0,TO_TIMESTAMP('2025-09-02 06:22:24.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Geschäftspartner
-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-02T06:39:32.887Z
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2025-09-02 06:39:32.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752680
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Geschäftspartner
-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-02T06:42:10.756Z
UPDATE AD_Field SET SeqNo=NULL, SortNo=NULL,Updated=TO_TIMESTAMP('2025-09-02 06:42:10.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752680
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Standort
-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-02T06:42:52.611Z
UPDATE AD_Field SET IsFieldOnly='N', IsSameLine='N', SeqNo=NULL, SortNo=NULL,Updated=TO_TIMESTAMP('2025-09-02 06:42:52.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752681
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Ansprechpartner
-- Column: C_BP_Group.AD_User_ID
-- 2025-09-02T06:43:39.140Z
UPDATE AD_Field SET IsFieldOnly='N', IsSameLine='N',Updated=TO_TIMESTAMP('2025-09-02 06:43:39.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752682
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Standort
-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-02T06:44:38.212Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=636570
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Ansprechpartner
-- Column: C_BP_Group.AD_User_ID
-- 2025-09-02T06:44:38.246Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=636571
;

-- 2025-09-02T06:49:24.003Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,752681,0,542112,636569,TO_TIMESTAMP('2025-09-02 06:49:23.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-02 06:49:23.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-02T06:49:38.312Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,752682,0,542113,636569,TO_TIMESTAMP('2025-09-02 06:49:38.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,'widget',TO_TIMESTAMP('2025-09-02 06:49:38.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-02T08:18:48.394Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583890,0,'Parent_BP_Group_ID',TO_TIMESTAMP('2025-09-02 08:18:48.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Eltern-Geschäftspartnergruppe','Eltern-Geschäftspartnergruppe',TO_TIMESTAMP('2025-09-02 08:18:48.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-02T08:18:48.399Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583890 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Parent_BP_Group_ID
-- 2025-09-02T08:19:14.057Z
UPDATE AD_Element_Trl SET Name='Parent Business Partner Group', PrintName='Parent Business Partner Group',Updated=TO_TIMESTAMP('2025-09-02 08:19:14.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583890 AND AD_Language='en_US'
;

-- 2025-09-02T08:19:14.058Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-02T08:19:14.332Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583890,'en_US')
;

-- Column: C_BP_Group.C_PaymentTerm_ID
-- 2025-09-02T10:27:42.129Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590719,204,0,19,394,'XX','C_PaymentTerm_ID',TO_TIMESTAMP('2025-09-02 10:27:41.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Die Bedingungen für die Bezahlung dieses Vorgangs','D',0,10,'Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsbedingung',0,0,TO_TIMESTAMP('2025-09-02 10:27:41.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-02T10:27:42.133Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590719 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-02T10:27:42.142Z
/* DDL */  select update_Column_Translation_From_AD_Element(204)
;

-- 2025-09-02T10:27:44.208Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN C_PaymentTerm_ID NUMERIC(10)')
;

-- 2025-09-02T10:27:44.502Z
ALTER TABLE C_BP_Group ADD CONSTRAINT CPaymentTerm_CBPGroup FOREIGN KEY (C_PaymentTerm_ID) REFERENCES public.C_PaymentTerm DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BP_Group.PO_PaymentTerm_ID
-- 2025-09-02T10:28:20.066Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590720,1576,0,30,227,394,'XX','PO_PaymentTerm_ID',TO_TIMESTAMP('2025-09-02 10:28:19.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Zahlungskondition für die Bestellung','D',0,10,'Die "Zahlungskondition" zeigen die Vorgaben an, die gelten sollen, wenn diese Bestellung zu einer Rechnung wird.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungskondition',0,0,TO_TIMESTAMP('2025-09-02 10:28:19.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-02T10:28:20.068Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590720 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-02T10:28:20.071Z
/* DDL */  select update_Column_Translation_From_AD_Element(1576)
;

-- 2025-09-02T10:28:22.662Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN PO_PaymentTerm_ID NUMERIC(10)')
;

-- 2025-09-02T10:28:22.847Z
ALTER TABLE C_BP_Group ADD CONSTRAINT POPaymentTerm_CBPGroup FOREIGN KEY (PO_PaymentTerm_ID) REFERENCES public.C_PaymentTerm DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BP_Group.FreightCostRule
-- 2025-09-02T10:28:45.217Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590721,1007,0,17,153,394,'XX','FreightCostRule',TO_TIMESTAMP('2025-09-02 10:28:44.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Methode zur Berechnung von Frachtkosten','D',0,1,'"Frachtkostenberechnung" gibt an, auf welche Weise die Kosten für Fracht berechnet werden.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Frachtkostenberechnung',0,0,TO_TIMESTAMP('2025-09-02 10:28:44.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-02T10:28:45.218Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590721 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-02T10:28:45.321Z
/* DDL */  select update_Column_Translation_From_AD_Element(1007)
;

-- 2025-09-02T10:28:48.691Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN FreightCostRule CHAR(1)')
;

-- Column: C_BP_Group.Parent_BP_Group_ID
-- 2025-09-02T10:29:44.476Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590722,583890,0,30,540996,394,'XX','Parent_BP_Group_ID',TO_TIMESTAMP('2025-09-02 10:29:44.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eltern-Geschäftspartnergruppe',0,0,TO_TIMESTAMP('2025-09-02 10:29:44.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-02T10:29:44.478Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590722 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-02T10:29:44.480Z
/* DDL */  select update_Column_Translation_From_AD_Element(583890)
;

-- 2025-09-02T10:29:51.320Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN Parent_BP_Group_ID NUMERIC(10)')
;

-- 2025-09-02T10:29:51.494Z
ALTER TABLE C_BP_Group ADD CONSTRAINT ParentBPGroup_CBPGroup FOREIGN KEY (Parent_BP_Group_ID) REFERENCES public.C_BP_Group DEFERRABLE INITIALLY DEFERRED
;

-- Run mode: SWING_CLIENT

-- Column: C_BP_Group.PaymentRulePO
-- 2025-09-04T08:57:17.690Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590809,950,0,17,195,394,'XX','PaymentRulePO',TO_TIMESTAMP('2025-09-04 08:57:17.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Möglichkeiten der Bezahlung einer Bestellung','D',0,1,'"Zahlungsweise" zeigt die Arten der Zahlungen für Einkäufe an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsweise',0,0,TO_TIMESTAMP('2025-09-04 08:57:17.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-04T08:57:17.692Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590809 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-04T08:57:17.717Z
/* DDL */  select update_Column_Translation_From_AD_Element(950)
;

-- 2025-09-04T08:57:23.697Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN PaymentRulePO CHAR(1)')
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Eltern-Geschäftspartnergruppe
-- Column: C_BP_Group.Parent_BP_Group_ID
-- 2025-09-04T16:59:41.735Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590722,753503,0,322,0,TO_TIMESTAMP('2025-09-04 16:59:41.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Eltern-Geschäftspartnergruppe',0,0,250,0,1,1,TO_TIMESTAMP('2025-09-04 16:59:41.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T16:59:41.739Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753503 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-04T16:59:41.744Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583890)
;

-- 2025-09-04T16:59:41.759Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753503
;

-- 2025-09-04T16:59:41.763Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753503)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Frachtkostenberechnung
-- Column: C_BP_Group.FreightCostRule
-- 2025-09-04T17:01:24.464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590721,753504,0,322,0,TO_TIMESTAMP('2025-09-04 17:01:24.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode zur Berechnung von Frachtkosten',0,'D',0,'"Frachtkostenberechnung" gibt an, auf welche Weise die Kosten für Fracht berechnet werden.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Frachtkostenberechnung',0,0,260,0,1,1,TO_TIMESTAMP('2025-09-04 17:01:24.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T17:01:24.465Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753504 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-04T17:01:24.467Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1007)
;

-- 2025-09-04T17:01:24.489Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753504
;

-- 2025-09-04T17:01:24.491Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753504)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Zahlungskondition
-- Column: C_BP_Group.PO_PaymentTerm_ID
-- 2025-09-04T17:01:43.732Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590720,753505,0,322,0,TO_TIMESTAMP('2025-09-04 17:01:43.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zahlungskondition für die Bestellung',0,'D',0,'Die "Zahlungskondition" zeigen die Vorgaben an, die gelten sollen, wenn diese Bestellung zu einer Rechnung wird.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Zahlungskondition',0,0,270,0,1,1,TO_TIMESTAMP('2025-09-04 17:01:43.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T17:01:43.734Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753505 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-04T17:01:43.735Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1576)
;

-- 2025-09-04T17:01:43.744Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753505
;

-- 2025-09-04T17:01:43.745Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753505)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Zahlungsbedingung
-- Column: C_BP_Group.C_PaymentTerm_ID
-- 2025-09-04T17:01:58.162Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590719,753506,0,322,0,TO_TIMESTAMP('2025-09-04 17:01:57.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Bedingungen für die Bezahlung dieses Vorgangs',0,'D',0,'Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Zahlungsbedingung',0,0,280,0,1,1,TO_TIMESTAMP('2025-09-04 17:01:57.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T17:01:58.164Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753506 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-04T17:01:58.166Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(204)
;

-- 2025-09-04T17:01:58.200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753506
;

-- 2025-09-04T17:01:58.201Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753506)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> description.Zahlungsbedingung
-- Column: C_BP_Group.C_PaymentTerm_ID
-- 2025-09-04T17:46:21.818Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753506,0,322,540481,636946,'F',TO_TIMESTAMP('2025-09-04 17:46:21.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Bedingungen für die Bezahlung dieses Vorgangs','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','N','N','Y','N','N','N',0,'Zahlungsbedingung',35,0,0,TO_TIMESTAMP('2025-09-04 17:46:21.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> description.Zahlungskondition
-- Column: C_BP_Group.PO_PaymentTerm_ID
-- 2025-09-04T17:46:57.483Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753505,0,322,540481,636947,'F',TO_TIMESTAMP('2025-09-04 17:46:57.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zahlungskondition für die Bestellung','Die "Zahlungskondition" zeigen die Vorgaben an, die gelten sollen, wenn diese Bestellung zu einer Rechnung wird.','Y','N','N','Y','N','N','N',0,'Zahlungskondition',55,0,0,TO_TIMESTAMP('2025-09-04 17:46:57.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 20 -> tax.Frachtkostenberechnung
-- Column: C_BP_Group.FreightCostRule
-- 2025-09-04T17:48:06.979Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753504,0,322,540483,636948,'F',TO_TIMESTAMP('2025-09-04 17:48:06.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode zur Berechnung von Frachtkosten','"Frachtkostenberechnung" gibt an, auf welche Weise die Kosten für Fracht berechnet werden.','Y','N','N','Y','N','N','N',0,'Frachtkostenberechnung',5,0,0,TO_TIMESTAMP('2025-09-04 17:48:06.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 20 -> tax.Eltern-Geschäftspartnergruppe
-- Column: C_BP_Group.Parent_BP_Group_ID
-- 2025-09-04T17:49:13.896Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753503,0,322,540483,636949,'F',TO_TIMESTAMP('2025-09-04 17:49:13.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Eltern-Geschäftspartnergruppe',40,0,0,TO_TIMESTAMP('2025-09-04 17:49:13.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T17:58:01.454Z
INSERT INTO t_alter_column values('c_bp_group','Parent_BP_Group_ID','NUMERIC(10)',null,null)
;

-- Run mode: SWING_CLIENT

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-05T13:38:27.380Z
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=542112
;

-- 2025-09-05T13:38:27.388Z
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=542113
;

-- 2025-09-05T13:38:27.394Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=636569
;

-- 2025-09-05T13:38:27.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752680
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Geschäftspartner
-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-05T13:38:27.400Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=752680
;

-- 2025-09-05T13:38:27.406Z
DELETE FROM AD_Field WHERE AD_Field_ID=752680
;

-- 2025-09-05T13:38:27.408Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE C_BP_Group DROP COLUMN IF EXISTS C_BPartner_ID')
;

-- Column: C_BP_Group.C_BPartner_ID
-- 2025-09-05T13:38:27.618Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590709
;

-- 2025-09-05T13:38:27.624Z
DELETE FROM AD_Column WHERE AD_Column_ID=590709
;

-- 2025-09-05T13:38:33.199Z
INSERT INTO t_alter_column values('c_bp_group','AD_User_ID','NUMERIC(10)',null,null)
;

-- 2025-09-05T13:38:39.341Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752682
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Ansprechpartner
-- Column: C_BP_Group.AD_User_ID
-- 2025-09-05T13:38:39.345Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=752682
;

-- 2025-09-05T13:38:39.350Z
DELETE FROM AD_Field WHERE AD_Field_ID=752682
;

-- 2025-09-05T13:38:39.353Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE C_BP_Group DROP COLUMN IF EXISTS AD_User_ID')
;

-- Column: C_BP_Group.AD_User_ID
-- 2025-09-05T13:38:39.536Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590711
;

-- 2025-09-05T13:38:39.543Z
DELETE FROM AD_Column WHERE AD_Column_ID=590711
;

-- 2025-09-05T13:38:47.634Z
INSERT INTO t_alter_column values('c_bp_group','C_BPartner_Location_ID','NUMERIC(10)',null,null)
;

-- 2025-09-05T13:38:53.455Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752681
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Standort
-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-05T13:38:53.458Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=752681
;

-- 2025-09-05T13:38:53.462Z
DELETE FROM AD_Field WHERE AD_Field_ID=752681
;

-- 2025-09-05T13:38:53.464Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE C_BP_Group DROP COLUMN IF EXISTS C_BPartner_Location_ID')
;

-- Column: C_BP_Group.C_BPartner_Location_ID
-- 2025-09-05T13:38:53.627Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590710
;

-- 2025-09-05T13:38:53.631Z
DELETE FROM AD_Column WHERE AD_Column_ID=590710
;

-- Column: C_BP_Group.Bill_BPartner_ID
-- 2025-09-05T13:54:26.599Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590812,2039,0,30,541045,394,'XX','Bill_BPartner_ID',TO_TIMESTAMP('2025-09-05 13:54:26.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Geschäftspartner für die Rechnungsstellung','D',0,10,'Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungspartner',0,0,TO_TIMESTAMP('2025-09-05 13:54:26.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-05T13:54:26.602Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590812 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-05T13:54:26.606Z
/* DDL */  select update_Column_Translation_From_AD_Element(2039)
;

-- 2025-09-05T13:54:29.802Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN Bill_BPartner_ID NUMERIC(10)')
;

-- 2025-09-05T13:54:29.975Z
ALTER TABLE C_BP_Group ADD CONSTRAINT BillBPartner_CBPGroup FOREIGN KEY (Bill_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2025-09-05T13:54:39.254Z
INSERT INTO t_alter_column values('c_bp_group','Bill_BPartner_ID','NUMERIC(10)',null,null)
;

-- Column: C_BP_Group.Bill_Location_ID
-- 2025-09-05T13:55:38.559Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590813,2040,0,30,159,394,540557,'XX','Bill_Location_ID',TO_TIMESTAMP('2025-09-05 13:55:38.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Standort des Geschäftspartners für die Rechnungsstellung','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungsstandort',0,0,TO_TIMESTAMP('2025-09-05 13:55:38.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-05T13:55:38.561Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590813 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-05T13:55:38.563Z
/* DDL */  select update_Column_Translation_From_AD_Element(2040)
;

-- 2025-09-05T13:55:40.375Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN Bill_Location_ID NUMERIC(10)')
;

-- 2025-09-05T13:55:40.541Z
ALTER TABLE C_BP_Group ADD CONSTRAINT BillLocation_CBPGroup FOREIGN KEY (Bill_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BP_Group.Bill_User_ID
-- 2025-09-05T13:56:14.959Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590814,2041,0,30,110,394,123,'XX','Bill_User_ID',TO_TIMESTAMP('2025-09-05 13:56:14.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Ansprechpartner des Geschäftspartners für die Rechnungsstellung','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungskontakt',0,0,TO_TIMESTAMP('2025-09-05 13:56:14.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-05T13:56:14.961Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590814 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-05T13:56:15.070Z
/* DDL */  select update_Column_Translation_From_AD_Element(2041)
;

-- 2025-09-05T13:56:16.973Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN Bill_User_ID NUMERIC(10)')
;

-- 2025-09-05T13:56:17.170Z
ALTER TABLE C_BP_Group ADD CONSTRAINT BillUser_CBPGroup FOREIGN KEY (Bill_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Rechnungspartner
-- Column: C_BP_Group.Bill_BPartner_ID
-- 2025-09-05T14:06:27.679Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590812,753508,0,322,0,TO_TIMESTAMP('2025-09-05 14:06:27.458000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Geschäftspartner für die Rechnungsstellung',0,'D',0,'Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Rechnungspartner',0,0,290,0,1,1,TO_TIMESTAMP('2025-09-05 14:06:27.458000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-05T14:06:27.681Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753508 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-05T14:06:27.684Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2039)
;

-- 2025-09-05T14:06:27.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753508
;

-- 2025-09-05T14:06:27.702Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753508)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Rechnungsstandort
-- Column: C_BP_Group.Bill_Location_ID
-- 2025-09-05T14:06:38.511Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590813,753509,0,322,0,TO_TIMESTAMP('2025-09-05 14:06:38.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Standort des Geschäftspartners für die Rechnungsstellung',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Rechnungsstandort',0,0,300,0,1,1,TO_TIMESTAMP('2025-09-05 14:06:38.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-05T14:06:38.514Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753509 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-05T14:06:38.517Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2040)
;

-- 2025-09-05T14:06:38.532Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753509
;

-- 2025-09-05T14:06:38.534Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753509)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Rechnungskontakt
-- Column: C_BP_Group.Bill_User_ID
-- 2025-09-05T14:06:59.373Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590814,753510,0,322,0,TO_TIMESTAMP('2025-09-05 14:06:59.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Rechnungskontakt',0,0,310,0,1,1,TO_TIMESTAMP('2025-09-05 14:06:59.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-05T14:06:59.375Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753510 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-05T14:06:59.376Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2041)
;

-- 2025-09-05T14:06:59.382Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753510
;

-- 2025-09-05T14:06:59.383Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753510)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Rechnungspartner
-- Column: C_BP_Group.Bill_BPartner_ID
-- 2025-09-05T14:07:30.956Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753508,0,322,540480,636950,'F',TO_TIMESTAMP('2025-09-05 14:07:30.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Geschäftspartner für die Rechnungsstellung','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','Y','N','N','N',0,'Rechnungspartner',30,0,0,TO_TIMESTAMP('2025-09-05 14:07:30.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-05T14:07:40.314Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,753509,0,542140,636950,TO_TIMESTAMP('2025-09-05 14:07:40.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-05 14:07:40.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-05T14:07:51.645Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,753510,0,542141,636950,TO_TIMESTAMP('2025-09-05 14:07:51.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,'widget',TO_TIMESTAMP('2025-09-05 14:07:51.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Rechnungsstandort
-- Column: C_BP_Group.Bill_Location_ID
-- 2025-09-05T14:08:11.780Z
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2025-09-05 14:08:11.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753509
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Rechnungskontakt
-- Column: C_BP_Group.Bill_User_ID
-- 2025-09-05T14:08:15.213Z
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2025-09-05 14:08:15.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753510
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Rechnungspartner
-- Column: C_BP_Group.Bill_BPartner_ID
-- 2025-09-05T14:08:31.500Z
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2025-09-05 14:08:31.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636950
;

-- Column: C_BP_Group.Bill_Location_ID
-- 2025-09-05T14:24:25.137Z
UPDATE AD_Column SET AD_Reference_ID=30, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-09-05 14:24:25.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590813
;

-- Column: C_BP_Group.Bill_Location_ID
-- 2025-09-05T14:30:43.945Z
UPDATE AD_Column SET AD_Val_Rule_ID=119,Updated=TO_TIMESTAMP('2025-09-05 14:30:43.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590813
;

-- Column: C_BP_Group.Bill_User_ID
-- 2025-09-05T14:31:26.851Z
UPDATE AD_Column SET AD_Val_Rule_ID=191,Updated=TO_TIMESTAMP('2025-09-05 14:31:26.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590814
;

