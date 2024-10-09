

-- change the name and also change the increment from +10 to +1
-- Column: EDI_Desadv_Pack.SeqNo
-- Column: EDI_Desadv_Pack.SeqNo
-- 2024-09-17T12:10:22.924Z
UPDATE AD_Column SET AD_Element_ID=566, ColumnName='SeqNo', DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+1 AS DefaultValue FROM EDI_Desadv_Pack WHERE EDI_Desadv_ID=@EDI_Desadv_ID/0@', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge', Name='Reihenfolge',Updated=TO_TIMESTAMP('2024-09-17 14:10:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588875
;

-- 2024-09-17T12:10:22.929Z
UPDATE AD_Field SET Name='Reihenfolge', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Column_ID=588875
;

-- 2024-09-17T12:10:22.936Z
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;



SELECT public.db_alter_table('EDI_Desadv_Pack','ALTER TABLE public.EDI_Desadv_Pack RENAME COLUMN Line TO SeqNo')
;

-- Column: EDI_Desadv_Pack.M_HU_PackagingCode_ID
-- Column: EDI_Desadv_Pack.M_HU_PackagingCode_ID
-- 2024-09-17T12:17:34.336Z
UPDATE AD_Column SET AD_Element_ID=577196, AD_Reference_ID=19, AD_Val_Rule_ID=NULL, ColumnName='M_HU_PackagingCode_ID', Description=NULL, Help=NULL, IsExcludeFromZoomTargets='N', Name='Verpackungscode', TechnicalNote='Note that a EDI_Desadv_Pack might also describe a TU.',Updated=TO_TIMESTAMP('2024-09-17 14:17:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583433
;

-- 2024-09-17T12:17:34.342Z
UPDATE AD_Field SET Name='Verpackungscode', Description=NULL, Help=NULL WHERE AD_Column_ID=583433
;

-- 2024-09-17T12:17:34.357Z
/* DDL */  select update_Column_Translation_From_AD_Element(577196) 
;

-- 2024-09-17T12:20:35.555Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583256,0,'M_HU_PackagingCode_Text',TO_TIMESTAMP('2024-09-17 14:20:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','The current PackagingCode string from the current M_HU_PackagingCode_ID. 
Not for display, just for EDI-export.','Y','M_HU_PackagingCode_Text','M_HU_PackagingCode_Text',TO_TIMESTAMP('2024-09-17 14:20:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-17T12:20:35.565Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583256 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: EDI_Desadv_Pack.M_HU_PackagingCode_Text
-- Column SQL (old): (select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_LU_ID)
-- Column: EDI_Desadv_Pack.M_HU_PackagingCode_Text
-- Column SQL (old): (select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_LU_ID)
-- 2024-09-17T12:21:09.977Z
UPDATE AD_Column SET AD_Element_ID=583256, ColumnName='M_HU_PackagingCode_Text', ColumnSQL='(select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_ID)', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_ID. 
Not for display, just for EDI-export.', IsExcludeFromZoomTargets='Y', IsLazyLoading='Y', Name='M_HU_PackagingCode_Text', TechnicalNote='The current PackagingCode string from the current M_HU_PackagingCode_ID. 
Not for display, just for DESADV-export. The exporter makes sure that we won''t export a stale value here.',Updated=TO_TIMESTAMP('2024-09-17 14:21:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583430
;

-- 2024-09-17T12:21:09.981Z
UPDATE AD_Field SET Name='M_HU_PackagingCode_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_ID. 
Not for display, just for EDI-export.' WHERE AD_Column_ID=583430
;

-- 2024-09-17T12:21:09.985Z
/* DDL */  select update_Column_Translation_From_AD_Element(583256) 
;

-- Column: EDI_Desadv_Pack.M_HU_PackagingCode_Text
-- Source Table: M_HU_PackagingCode
-- 2024-09-17T12:21:52.452Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,583430,0,540162,542170,TO_TIMESTAMP('2024-09-17 14:21:52','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',569171,569173,541423,TO_TIMESTAMP('2024-09-17 14:21:52','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2024-09-17T13:21:00.641Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583257,0,'GTIN_PackingMaterial',TO_TIMESTAMP('2024-09-17 15:21:00','YYYY-MM-DD HH24:MI:SS'),100,'GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.','de.metas.handlingunits','Y','Gebinde-GTIN','Gebinde-GTIN',TO_TIMESTAMP('2024-09-17 15:21:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-17T13:21:00.648Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583257 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: GTIN_PackingMaterial
-- 2024-09-17T13:21:07.220Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-17 15:21:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583257 AND AD_Language='de_CH'
;

-- 2024-09-17T13:21:07.229Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583257,'de_CH') 
;

-- Element: GTIN_PackingMaterial
-- 2024-09-17T13:21:09.823Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-17 15:21:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583257 AND AD_Language='de_DE'
;

-- 2024-09-17T13:21:09.828Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583257,'de_DE') 
;

-- 2024-09-17T13:21:09.832Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583257,'de_DE') 
;

-- Element: GTIN_PackingMaterial
-- 2024-09-17T13:21:58.594Z
UPDATE AD_Element_Trl SET Description='GTIN of the container used, e.g. pallet. Is determined automatically via the packing instruction from the product master data for the respective delivery recipient.', IsTranslated='Y', Name='Packingmaterial-GTIN', PrintName='Packingmaterial-GTIN',Updated=TO_TIMESTAMP('2024-09-17 15:21:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583257 AND AD_Language='en_US'
;

-- 2024-09-17T13:21:58.600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583257,'en_US') 
;

-- Column: EDI_Desadv_Pack.GTIN_PackingMaterial
-- Column: EDI_Desadv_Pack.GTIN_PackingMaterial
-- 2024-09-17T13:22:17.351Z
UPDATE AD_Column SET AD_Element_ID=583257, ColumnName='GTIN_PackingMaterial', Description='GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL, IsExcludeFromZoomTargets='Y', Name='Gebinde-GTIN',Updated=TO_TIMESTAMP('2024-09-17 15:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583431
;

-- 2024-09-17T13:22:17.355Z
UPDATE AD_Field SET Name='Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL WHERE AD_Column_ID=583431
;

-- 2024-09-17T13:22:17.360Z
/* DDL */  select update_Column_Translation_From_AD_Element(583257) 
;




-- Column: EDI_Desadv_Pack_Item.Line
-- Column: EDI_Desadv_Pack_Item.Line
-- 2024-09-17T12:26:59.846Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588984,439,0,11,542171,'XX','Line',TO_TIMESTAMP('2024-09-17 14:26:59','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM EDI_Desadv_Pack_Item WHERE EDI_Desadv_Pack_ID IN (select EDI_Desadv_Pack_ID from EDI_Desadv_Pack where EDI_Desadv_ID=@EDI_Desadv_ID/0@)','Einzelne Zeile in dem Dokument','de.metas.esb.edi',0,22,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zeile Nr.',0,0,TO_TIMESTAMP('2024-09-17 14:26:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-17T12:26:59.850Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588984 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-17T12:26:59.859Z
/* DDL */  select update_Column_Translation_From_AD_Element(439) 
;


-- Column: EDI_Desadv_Pack_Item.Line
-- Column: EDI_Desadv_Pack_Item.Line
-- 2024-09-17T12:27:57.399Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-09-17 14:27:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588984
;

-- 2024-09-17T12:27:59.485Z
/* DDL */ SELECT public.db_alter_table('EDI_Desadv_Pack_Item','ALTER TABLE public.EDI_Desadv_Pack_Item ADD COLUMN Line NUMERIC(10)')
;
