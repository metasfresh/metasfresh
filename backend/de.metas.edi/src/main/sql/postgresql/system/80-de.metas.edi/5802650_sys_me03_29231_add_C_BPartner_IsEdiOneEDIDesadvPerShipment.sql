-- Run mode: SWING_CLIENT

-- Element: IsEdiOneEDIDesadvPerShipment
-- 2026-05-14T14:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584880/*From ID Server*/,0,'IsEdiOneEDIDesadvPerShipment',TO_TIMESTAMP('2026-05-14 14:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','Ein DESADV pro Lieferung','Ein DESADV pro Lieferung',TO_TIMESTAMP('2026-05-14 14:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T14:00:00.001Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584880 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsEdiOneEDIDesadvPerShipment (en_US)
-- 2026-05-14T14:00:12.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='One DESADV per shipment', PrintName='One DESADV per shipment',Updated=TO_TIMESTAMP('2026-05-14 14:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584880 AND AD_Language='en_US'
;

-- 2026-05-14T14:00:12.001Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-14T14:00:12.002Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584880,'en_US')
;

-- Element: IsEdiOneEDIDesadvPerShipment (de_CH)
-- 2026-05-14T14:00:14.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ein DESADV pro Lieferung', PrintName='Ein DESADV pro Lieferung',Updated=TO_TIMESTAMP('2026-05-14 14:00:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584880 AND AD_Language='de_CH'
;

-- 2026-05-14T14:00:14.001Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584880,'de_CH')
;

-- Element: IsEdiOneEDIDesadvPerShipment (de_DE)
-- 2026-05-14T14:00:18.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ein DESADV pro Lieferung', PrintName='Ein DESADV pro Lieferung',Updated=TO_TIMESTAMP('2026-05-14 14:00:18.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584880 AND AD_Language='de_DE'
;

-- 2026-05-14T14:00:18.001Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-14T14:00:18.002Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584880,'de_DE')
;

-- 2026-05-14T14:00:18.003Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584880,'de_DE')
;

-- Column: C_BPartner.IsEdiOneEDIDesadvPerShipment
-- 2026-05-14T14:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592530/*From ID Server*/,584880/*From ID Server*/,0,20/*YesNo*/,291/*C_BPartner*/,'XX','IsEdiOneEDIDesadvPerShipment',TO_TIMESTAMP('2026-05-14 14:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.esb.edi',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ein DESADV pro Lieferung','NP',0,0,TO_TIMESTAMP('2026-05-14 14:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-14T14:01:00.001Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592530 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-14T14:01:00.002Z
/* DDL */  select update_Column_Translation_From_AD_Element(584880)
;

-- 2026-05-14T14:01:01.000Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsEdiOneEDIDesadvPerShipment CHAR(1) DEFAULT ''N'' NOT NULL')
;

-- Field: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> Ein DESADV pro Lieferung
-- Column: C_BPartner.IsEdiOneEDIDesadvPerShipment
-- 2026-05-14T14:02:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592530/*From ID Server*/,779955/*From ID Server*/,0,548980/*EDI-Konfiguration tab*/,TO_TIMESTAMP('2026-05-14 14:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn aktiviert: für diesen EDI-Geschäftspartner wird ein DESADV pro Lieferung erzeugt — auch wenn die Lieferung mehrere Aufträge zusammenfasst. Die Kd-Bestellnummer wird weiterhin auf jeder DESADV-Position ausgegeben. Nur wirksam, wenn EdiDESADVSendingMode=''Externes System''.',1,'@EdiDESADVSendingMode@=''E''','de.metas.esb.edi','Y','N','N','N','N','N','N','N','Ein DESADV pro Lieferung',TO_TIMESTAMP('2026-05-14 14:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T14:02:00.001Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=779955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-14T14:02:00.002Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584880)
;

-- 2026-05-14T14:02:00.003Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=779955
;

-- 2026-05-14T14:02:00.004Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(779955)
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.Ein DESADV pro Lieferung
-- Column: C_BPartner.IsEdiOneEDIDesadvPerShipment
-- 2026-05-14T14:02:30.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,779955/*From ID Server*/,0,548980/*EDI-Konfiguration tab*/,554789/*UI ElementGroup*/,651661/*From ID Server*/,'F',TO_TIMESTAMP('2026-05-14 14:02:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Ein DESADV pro Lieferung',90,0,0,TO_TIMESTAMP('2026-05-14 14:02:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T14:02:30.001Z
SELECT add_missing_translations()
;
