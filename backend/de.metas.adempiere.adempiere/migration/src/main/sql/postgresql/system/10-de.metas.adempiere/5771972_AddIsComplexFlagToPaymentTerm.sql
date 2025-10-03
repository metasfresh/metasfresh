-- Element: C_PaymentTerm_Break_ID
-- 2025-10-02T09:36:04.177Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zahlungsbedingungsrate', PrintName='Zahlungsbedingungsrate',Updated=TO_TIMESTAMP('2025-10-02 09:36:04.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584054 AND AD_Language='de_CH'
;

-- 2025-10-02T09:36:04.185Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T09:36:04.805Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584054,'de_CH')
;

-- Element: C_PaymentTerm_Break_ID
-- 2025-10-02T09:36:09.057Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zahlungsbedingungsrate', PrintName='Zahlungsbedingungsrate',Updated=TO_TIMESTAMP('2025-10-02 09:36:09.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584054 AND AD_Language='de_DE'
;

-- 2025-10-02T09:36:09.059Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T09:36:11.388Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584054,'de_DE')
;

-- 2025-10-02T09:36:11.389Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584054,'de_DE')
;

-- Element: C_PaymentTerm_Break_ID
-- 2025-10-02T09:36:13.596Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-02 09:36:13.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584054 AND AD_Language='en_US'
;

-- 2025-10-02T09:36:13.599Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584054,'en_US')
;



------------------



-- Run mode: SWING_CLIENT

-- Run mode: SWING_CLIENT

-- 2025-10-02T10:51:35.038Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584060,0,'IsComplex',TO_TIMESTAMP('2025-10-02 10:51:33.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Indicates that this payment term defines multiple due dates or installment conditions.','D','Y','Installment','Installment',TO_TIMESTAMP('2025-10-02 10:51:33.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T10:51:35.049Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584060 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsComplex
-- 2025-10-02T10:52:40.249Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ratenzahlung', PrintName='Ratenzahlung',Updated=TO_TIMESTAMP('2025-10-02 10:52:40.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584060 AND AD_Language='de_CH'
;

-- 2025-10-02T10:52:40.251Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T10:52:40.613Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584060,'de_CH')
;

-- Element: IsComplex
-- 2025-10-02T10:52:50.323Z
UPDATE AD_Element_Trl SET Description='Kennzeichnet, dass diese Zahlungsbedingung mehrere Fälligkeitstermine bzw. Ratenzahlungen enthält.', IsTranslated='Y', Name='Ratenzahlung', PrintName='Ratenzahlung',Updated=TO_TIMESTAMP('2025-10-02 10:52:50.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584060 AND AD_Language='de_DE'
;

-- 2025-10-02T10:52:50.325Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T10:52:51.414Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584060,'de_DE')
;

-- 2025-10-02T10:52:51.415Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584060,'de_DE')
;

-- Element: IsComplex
-- 2025-10-02T10:52:53.595Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-02 10:52:53.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584060 AND AD_Language='en_US'
;

-- 2025-10-02T10:52:53.599Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584060,'en_US')
;

-- Element: IsComplex
-- 2025-10-02T10:53:01.836Z
UPDATE AD_Element_Trl SET Description='Kennzeichnet, dass diese Zahlungsbedingung mehrere Fälligkeitstermine bzw. Ratenzahlungen enthält.',Updated=TO_TIMESTAMP('2025-10-02 10:53:01.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584060 AND AD_Language='de_CH'
;

-- 2025-10-02T10:53:01.838Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T10:53:02.147Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584060,'de_CH')
;

-- Column: C_PaymentTerm.IsComplex
-- 2025-10-02T10:53:34.123Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591239,584060,0,20,113,'XX','IsComplex',TO_TIMESTAMP('2025-10-02 10:53:33.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Kennzeichnet, dass diese Zahlungsbedingung mehrere Fälligkeitstermine bzw. Ratenzahlungen enthält.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ratenzahlung',0,0,TO_TIMESTAMP('2025-10-02 10:53:33.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-02T10:53:34.126Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591239 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-02T10:53:34.133Z
/* DDL */  select update_Column_Translation_From_AD_Element(584060)
;

-- 2025-10-02T10:53:35.820Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE public.C_PaymentTerm ADD COLUMN IsComplex CHAR(1) DEFAULT ''N'' CHECK (IsComplex IN (''Y'',''N'')) NOT NULL')
;

-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Ratenzahlung
-- Column: C_PaymentTerm.IsComplex
-- 2025-10-02T10:53:49.287Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591239,754543,0,184,TO_TIMESTAMP('2025-10-02 10:53:49.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kennzeichnet, dass diese Zahlungsbedingung mehrere Fälligkeitstermine bzw. Ratenzahlungen enthält.',1,'D','Y','N','N','N','N','N','N','N','Ratenzahlung',TO_TIMESTAMP('2025-10-02 10:53:49.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T10:53:49.292Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754543 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T10:53:49.295Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584060)
;

-- 2025-10-02T10:53:49.306Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754543
;

-- 2025-10-02T10:53:49.314Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754543)
;

-- UI Element: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> main -> 20 -> flags.Ratenzahlung
-- Column: C_PaymentTerm.IsComplex
-- 2025-10-02T10:54:27.273Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754543,0,184,540544,637541,'F',TO_TIMESTAMP('2025-10-02 10:54:27.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kennzeichnet, dass diese Zahlungsbedingung mehrere Fälligkeitstermine bzw. Ratenzahlungen enthält.','Y','N','N','Y','N','N','N',0,'Ratenzahlung',50,0,0,TO_TIMESTAMP('2025-10-02 10:54:27.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Zahlungsbedingung(141,D) -> Zahlungsbedingungsrate
-- Table: C_PaymentTerm_Break
-- 2025-10-02T10:54:50.786Z
UPDATE AD_Tab SET ReadOnlyLogic='@IsComplex@=''N''',Updated=TO_TIMESTAMP('2025-10-02 10:54:50.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548448
;



-- 2025-10-02T10:51:35.038Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584060,0,'IsComplex',TO_TIMESTAMP('2025-10-02 10:51:33.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Indicates that this payment term defines multiple due dates or installment conditions.','D','Y','Installment','Installment',TO_TIMESTAMP('2025-10-02 10:51:33.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T10:51:35.049Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584060 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsComplex
-- 2025-10-02T10:52:40.249Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ratenzahlung', PrintName='Ratenzahlung',Updated=TO_TIMESTAMP('2025-10-02 10:52:40.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584060 AND AD_Language='de_CH'
;

-- 2025-10-02T10:52:40.251Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T10:52:40.613Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584060,'de_CH')
;

-- Element: IsComplex
-- 2025-10-02T10:52:50.323Z
UPDATE AD_Element_Trl SET Description='Kennzeichnet, dass diese Zahlungsbedingung mehrere Fälligkeitstermine bzw. Ratenzahlungen enthält.', IsTranslated='Y', Name='Ratenzahlung', PrintName='Ratenzahlung',Updated=TO_TIMESTAMP('2025-10-02 10:52:50.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584060 AND AD_Language='de_DE'
;

-- 2025-10-02T10:52:50.325Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T10:52:51.414Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584060,'de_DE')
;

-- 2025-10-02T10:52:51.415Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584060,'de_DE')
;

-- Element: IsComplex
-- 2025-10-02T10:52:53.595Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-02 10:52:53.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584060 AND AD_Language='en_US'
;

-- 2025-10-02T10:52:53.599Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584060,'en_US')
;

-- Element: IsComplex
-- 2025-10-02T10:53:01.836Z
UPDATE AD_Element_Trl SET Description='Kennzeichnet, dass diese Zahlungsbedingung mehrere Fälligkeitstermine bzw. Ratenzahlungen enthält.',Updated=TO_TIMESTAMP('2025-10-02 10:53:01.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584060 AND AD_Language='de_CH'
;

-- 2025-10-02T10:53:01.838Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T10:53:02.147Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584060,'de_CH')
;

-- Column: C_PaymentTerm.IsComplex
-- 2025-10-02T10:53:34.123Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591239,584060,0,20,113,'XX','IsComplex',TO_TIMESTAMP('2025-10-02 10:53:33.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Kennzeichnet, dass diese Zahlungsbedingung mehrere Fälligkeitstermine bzw. Ratenzahlungen enthält.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ratenzahlung',0,0,TO_TIMESTAMP('2025-10-02 10:53:33.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-02T10:53:34.126Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591239 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-02T10:53:34.133Z
/* DDL */  select update_Column_Translation_From_AD_Element(584060)
;

-- 2025-10-02T10:53:35.820Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE public.C_PaymentTerm ADD COLUMN IsComplex CHAR(1) DEFAULT ''N'' CHECK (IsComplex IN (''Y'',''N'')) NOT NULL')
;

-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Ratenzahlung
-- Column: C_PaymentTerm.IsComplex
-- 2025-10-02T10:53:49.287Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591239,754543,0,184,TO_TIMESTAMP('2025-10-02 10:53:49.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kennzeichnet, dass diese Zahlungsbedingung mehrere Fälligkeitstermine bzw. Ratenzahlungen enthält.',1,'D','Y','N','N','N','N','N','N','N','Ratenzahlung',TO_TIMESTAMP('2025-10-02 10:53:49.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T10:53:49.292Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754543 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T10:53:49.295Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584060)
;

-- 2025-10-02T10:53:49.306Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754543
;

-- 2025-10-02T10:53:49.314Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754543)
;

-- UI Element: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> main -> 20 -> flags.Ratenzahlung
-- Column: C_PaymentTerm.IsComplex
-- 2025-10-02T10:54:27.273Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754543,0,184,540544,637541,'F',TO_TIMESTAMP('2025-10-02 10:54:27.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kennzeichnet, dass diese Zahlungsbedingung mehrere Fälligkeitstermine bzw. Ratenzahlungen enthält.','Y','N','N','Y','N','N','N',0,'Ratenzahlung',50,0,0,TO_TIMESTAMP('2025-10-02 10:54:27.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;


