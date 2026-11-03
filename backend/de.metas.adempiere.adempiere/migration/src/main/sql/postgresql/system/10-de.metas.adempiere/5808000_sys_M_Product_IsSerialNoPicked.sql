-- Serial No Picking — enforce serial-number scan in mobile picking
-- Adds the standard product flag M_Product.IsSerialNoPicked (Y/N, default N) + Product-window field.
-- Mirrors 5513990_sys_M_Product_IsQuotationGroupping.sql.

-- AD_Element
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585003 /*From ID Server*/,0,'IsSerialNoPicked',TO_TIMESTAMP('2026-06-16 02:25:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Seriennummer kommissionieren','Seriennummer kommissionieren',TO_TIMESTAMP('2026-06-16 02:25:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- AD_Element_Trl — seed all system languages from base (copies element time)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585003 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- AD_Element_Trl — de_DE description/help (later than element INSERT)
UPDATE AD_Element_Trl SET IsTranslated='Y', Description='Wenn aktiviert, muss beim Kommissionieren dieses Produkts in eine Handling Unit die Seriennummer gescannt werden.', Help='Wenn aktiviert, muss beim Kommissionieren dieses Produkts in eine Handling Unit die Seriennummer gescannt werden.', Updated=TO_TIMESTAMP('2026-06-16 02:25:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585003 AND AD_Language='de_DE'
;

-- AD_Element_Trl — en_US (later than element INSERT; distinct from de_DE)
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Serial No Picked', PrintName='Serial No Picked', Description='If enabled, picking this product into a handling unit requires the operator to scan the serial number.', Help='If enabled, picking this product into a handling unit requires the operator to scan the serial number.',Updated=TO_TIMESTAMP('2026-06-16 02:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585003 AND AD_Language='en_US'
;

-- propagate AD_Element_Trl to dependent _Trl tables (both translated languages, treated alike)
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585003,'en_US')
;
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585003,'de_DE')
;

-- AD_Column (PersonalDataCategory='NP' — technical flag, not personal data)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592813 /*From ID Server*/,585003,0,20,208,'IsSerialNoPicked',TO_TIMESTAMP('2026-06-16 02:26:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Seriennummer kommissionieren','NP',0,0,TO_TIMESTAMP('2026-06-16 02:26:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- AD_Column_Trl — seed
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=592813 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- physical column
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IsSerialNoPicked CHAR(1) DEFAULT ''N'' CHECK (IsSerialNoPicked IN (''Y'',''N'')) NOT NULL')
;

-- AD_Field on the Product window's main header tab 180 (later than element → propagation guard passes)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592813,781148 /*From ID Server*/,0,180,TO_TIMESTAMP('2026-06-16 02:27:00','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Seriennummer kommissionieren',TO_TIMESTAMP('2026-06-16 02:27:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- AD_Field_Trl — seed
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- AD_UI_Element — place in existing 'hu' element group 542064 (SeqNo 15: after IsHUtracing=10, before the existing HULabelPer=20)
-- IsAdvancedField='Y' to match every sibling in the 'hu' group (the group lives in the Advanced-Edit section); a standard-form field here would render orphaned from its Alt+E-only peers.
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,781148,0,180,652294 /*From ID Server*/,542064,'F',TO_TIMESTAMP('2026-06-16 02:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Seriennummer kommissionieren',15,0,0,TO_TIMESTAMP('2026-06-16 02:27:30','YYYY-MM-DD HH24:MI:SS'),100)
;
