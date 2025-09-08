-- Run mode: SWING_CLIENT

-- Column: C_InvoiceLine.Account_ID
-- 2025-07-11T12:59:02.605Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590530,148,0,30,362,333,252,'XX','Account_ID',TO_TIMESTAMP('2025-07-11 12:59:01.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Verwendetes Konto','D',0,10,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Konto',0,0,TO_TIMESTAMP('2025-07-11 12:59:01.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-11T12:59:02.664Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590530 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-11T12:59:02.781Z
/* DDL */  select update_Column_Translation_From_AD_Element(148)
;

-- 2025-07-11T12:59:16.622Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN Account_ID NUMERIC(10)')
;

-- 2025-07-11T12:59:17.082Z
ALTER TABLE C_InvoiceLine ADD CONSTRAINT Account_CInvoiceLine FOREIGN KEY (Account_ID) REFERENCES public.C_ElementValue DEFERRABLE INITIALLY DEFERRED
;

-- Field: Kreditoren Rechnung_OLD(183,D) -> Rechnungsposition(291,D) -> Konto
-- Column: C_InvoiceLine.Account_ID
-- 2025-07-11T13:01:21.642Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590530,750254,0,291,0,TO_TIMESTAMP('2025-07-11 13:01:20.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwendetes Konto',0,'D',0,'Das verwendete (Standard-) Konto',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Konto',0,0,110,0,1,1,TO_TIMESTAMP('2025-07-11 13:01:20.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-11T13:01:21.702Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750254 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-11T13:01:21.764Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(148)
;

-- 2025-07-11T13:01:21.826Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750254
;

-- 2025-07-11T13:01:21.881Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750254)
;

-- UI Element: Kreditoren Rechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Konto
-- Column: C_InvoiceLine.Account_ID
-- 2025-07-11T13:02:07.645Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750254,0,291,540219,635273,'F',TO_TIMESTAMP('2025-07-11 13:02:06.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwendetes Konto','Das verwendete (Standard-) Konto','Y','N','N','Y','N','N','N',0,'Konto',55,0,0,TO_TIMESTAMP('2025-07-11 13:02:06.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_InvoiceLine.Account_ID
-- 2025-07-11T13:07:26.792Z
UPDATE AD_Column SET FieldLength=22,Updated=TO_TIMESTAMP('2025-07-11 13:07:26.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590530
;

-- 2025-07-11T13:07:37.014Z
INSERT INTO t_alter_column values('c_invoiceline','Account_ID','NUMERIC(10)',null,null)
;

-- Column: C_InvoiceLine.Account_ID
-- 2025-07-11T13:08:38.966Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2025-07-11 13:08:38.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590530
;

-- UI Element: Kreditoren Rechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Konto
-- Column: C_InvoiceLine.Account_ID
-- 2025-07-11T13:09:35.373Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-07-11 13:09:35.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635273
;

-- UI Element: Kreditoren Rechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Beschreibung
-- Column: C_InvoiceLine.Description
-- 2025-07-11T13:09:35.713Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-07-11 13:09:35.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=542667
;

-- UI Element: Kreditoren Rechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Steuer
-- Column: C_InvoiceLine.C_Tax_ID
-- 2025-07-11T13:09:36.048Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-07-11 13:09:36.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=542668
;

-- UI Element: Kreditoren Rechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Zeilennetto
-- Column: C_InvoiceLine.LineNetAmt
-- 2025-07-11T13:09:36.378Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-07-11 13:09:36.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=542669
;

-- UI Element: Kreditoren Rechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Packaging Material
-- Column: C_InvoiceLine.IsPackagingMaterial
-- 2025-07-11T13:09:36.712Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-07-11 13:09:36.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=542670
;

-- UI Element: Kreditoren Rechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Project
-- Column: C_InvoiceLine.C_Project_ID
-- 2025-07-11T13:09:37.045Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-07-11 13:09:37.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573745
;

-- UI Element: Kreditoren Rechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Sektion
-- Column: C_InvoiceLine.AD_Org_ID
-- 2025-07-11T13:09:37.383Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-07-11 13:09:37.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549111
;

-- 2025-07-11T13:12:11.129Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583806,0,TO_TIMESTAMP('2025-07-11 13:12:10.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Konto anzeigen','Konto anzeigen',TO_TIMESTAMP('2025-07-11 13:12:10.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-11T13:12:11.187Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583806 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-07-11T13:12:31.908Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='View account', PrintName='View account',Updated=TO_TIMESTAMP('2025-07-11 13:12:31.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583806 AND AD_Language='en_US'
;

-- 2025-07-11T13:12:31.965Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-11T13:12:35.665Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583806,'en_US')
;

-- Field: Kreditoren Rechnung_OLD(183,D) -> Rechnungsposition(291,D) -> Konto anzeigen
-- Column: C_InvoiceLine.Account_ID
-- 2025-07-11T13:12:54.495Z
UPDATE AD_Field SET AD_Name_ID=583806, Description=NULL, Help=NULL, Name='Konto anzeigen',Updated=TO_TIMESTAMP('2025-07-11 13:12:54.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=750254
;

-- 2025-07-11T13:12:54.551Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Konto anzeigen' WHERE AD_Field_ID=750254 AND AD_Language='de_DE'
;

-- 2025-07-11T13:12:54.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583806)
;

-- 2025-07-11T13:12:54.665Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750254
;

-- 2025-07-11T13:12:54.717Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750254)
;
