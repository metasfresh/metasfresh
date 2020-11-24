-- 2020-11-16T12:33:07.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,572164,0,TO_TIMESTAMP('2020-11-16 13:33:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550317,'N','N','LU Gebinde-GTIN',270,'E',TO_TIMESTAMP('2020-11-16 13:33:07','YYYY-MM-DD HH24:MI:SS'),100,'GTIN_LU_PackingMaterial')
;

-- 2020-11-16T12:33:07.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,572165,0,TO_TIMESTAMP('2020-11-16 13:33:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550318,'N','N','TU Gebinde-GTIN',280,'E',TO_TIMESTAMP('2020-11-16 13:33:07','YYYY-MM-DD HH24:MI:SS'),100,'GTIN_TU_PackingMaterial')
;

-- 2020-11-16T12:33:22.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2020-11-16 13:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550318
;

-- 2020-11-16T12:33:26.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2020-11-16 13:33:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550317
;

-- 2020-11-16T13:18:00.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572166,577135,0,10,632,'GTIN',TO_TIMESTAMP('2020-11-16 14:18:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,50,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'GTIN',0,0,TO_TIMESTAMP('2020-11-16 14:18:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-11-16T13:18:00.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572166 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-11-16T13:18:00.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577135) 
;

-- 2020-11-16T13:18:10.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product','ALTER TABLE public.C_BPartner_Product ADD COLUMN GTIN VARCHAR(50)')
;

-- 2020-11-16T13:18:50.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572166,625825,0,562,0,TO_TIMESTAMP('2020-11-16 14:18:50','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','GTIN',280,240,0,1,1,TO_TIMESTAMP('2020-11-16 14:18:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-16T13:18:50.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=625825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-11-16T13:18:50.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577135) 
;

-- 2020-11-16T13:18:50.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=625825
;

-- 2020-11-16T13:18:50.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(625825)
;

-- 2020-11-16T13:19:46.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,625825,0,562,1000021,575093,'F',TO_TIMESTAMP('2020-11-16 14:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'GTIN',53,0,0,TO_TIMESTAMP('2020-11-16 14:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-16T13:20:05.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-11-16 14:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000150
;

-- 2020-11-16T13:20:05.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2020-11-16 14:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575093
;

-- 2020-11-16T14:01:22.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-16 15:01:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578548 AND AD_Language='de_CH'
;

-- 2020-11-16T14:01:22.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578548,'de_CH') 
;

-- 2020-11-16T14:01:38.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-16 15:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578548 AND AD_Language='de_DE'
;

-- 2020-11-16T14:01:38.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578548,'de_DE') 
;

-- 2020-11-16T14:01:38.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578548,'de_DE') 
;

-- 2020-11-16T14:01:38.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='GTIN_LU_PackingMaterial', Name='LU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL WHERE AD_Element_ID=578548
;

-- 2020-11-16T14:01:38.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GTIN_LU_PackingMaterial', Name='LU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL, AD_Element_ID=578548 WHERE UPPER(ColumnName)='GTIN_LU_PACKINGMATERIAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-16T14:01:38.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GTIN_LU_PackingMaterial', Name='LU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL WHERE AD_Element_ID=578548 AND IsCentrallyMaintained='Y'
;

-- 2020-11-16T14:01:38.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578548) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578548)
;

-- 2020-11-16T14:01:38.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='LU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578548
;

-- 2020-11-16T14:01:38.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='LU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL WHERE AD_Element_ID = 578548
;

-- 2020-11-16T14:01:38.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'LU Gebinde-GTIN', Description = 'GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578548
;

-- 2020-11-16T14:01:56.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='LU Packaging-GTIN', PrintName='LU Packaging-GTIN',Updated=TO_TIMESTAMP('2020-11-16 15:01:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578548 AND AD_Language='en_US'
;

-- 2020-11-16T14:01:56.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578548,'en_US') 
;

-- 2020-11-16T14:02:23.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-16 15:02:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578547 AND AD_Language='de_CH'
;

-- 2020-11-16T14:02:23.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578547,'de_CH') 
;

-- 2020-11-16T14:02:30.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-16 15:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578547 AND AD_Language='de_DE'
;

-- 2020-11-16T14:02:30.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578547,'de_DE') 
;

-- 2020-11-16T14:02:30.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578547,'de_DE') 
;

-- 2020-11-16T14:02:30.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='GTIN_TU_PackingMaterial', Name='TU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL WHERE AD_Element_ID=578547
;

-- 2020-11-16T14:02:30.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GTIN_TU_PackingMaterial', Name='TU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL, AD_Element_ID=578547 WHERE UPPER(ColumnName)='GTIN_TU_PACKINGMATERIAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-16T14:02:30.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GTIN_TU_PackingMaterial', Name='TU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL WHERE AD_Element_ID=578547 AND IsCentrallyMaintained='Y'
;

-- 2020-11-16T14:02:30.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='TU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578547) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578547)
;

-- 2020-11-16T14:02:30.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='TU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578547
;

-- 2020-11-16T14:02:30.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='TU Gebinde-GTIN', Description='GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', Help=NULL WHERE AD_Element_ID = 578547
;

-- 2020-11-16T14:02:30.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'TU Gebinde-GTIN', Description = 'GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578547
;

-- 2020-11-16T14:02:44.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='TU packaging-GTIN', PrintName='TU packaging-GTIN',Updated=TO_TIMESTAMP('2020-11-16 15:02:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578547 AND AD_Language='en_US'
;

-- 2020-11-16T14:02:44.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578547,'en_US') 
;

-- 2020-11-16T14:04:00.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_product','GTIN','VARCHAR(50)',null,null)
;

