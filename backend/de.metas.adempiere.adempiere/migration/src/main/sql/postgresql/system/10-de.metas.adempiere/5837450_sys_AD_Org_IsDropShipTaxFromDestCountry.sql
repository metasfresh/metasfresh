-- AD_Org: switch "Drop-ship: tax as in destination country" (IsDropShipTaxFromDestCountry), shown in the Organisation window header tab next to "One-Stop Shop (OSS)".
-- IDs from the central ID server (2026-11-26): AD_Element 585636; AD_Column 593864; AD_Field 788200; AD_UI_Element 656525.

-- AD_Element IsDropShipTaxFromDestCountry (585636)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585636 /*From ID Server*/,0,'IsDropShipTaxFromDestCountry',TO_TIMESTAMP('2026-11-26 21:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn angehakt, wird eine Verkaufszeile aus einem Streckengeschäfts-Lager in ein Land, für das die Organisation eine Fiskalvertretung hat, wie eine Lieferung innerhalb dieses Landes besteuert.','D','Y','Streckengeschäft: Steuer wie im Bestimmungsland','Streckengeschäft: Steuer wie im Bestimmungsland',TO_TIMESTAMP('2026-11-26 21:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585636 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Drop-ship: tax as in destination country', PrintName='Drop-ship: tax as in destination country', Description='If checked, a sales line from a drop-ship warehouse to a country where the organisation has a fiscal representation is taxed like a delivery within that country.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-26 21:00:11','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585636 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-26 21:00:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585636 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-26 21:00:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585636 AND AD_Language='de_CH'
;

-- Column AD_Org.IsDropShipTaxFromDestCountry (593864): YesNo, default N, mandatory, updatable
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593864 /*From ID Server*/,585636,0,20,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='AD_Org'),'IsDropShipTaxFromDestCountry',TO_TIMESTAMP('2026-11-26 21:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Wenn angehakt, wird eine Verkaufszeile aus einem Streckengeschäfts-Lager in ein Land, für das die Organisation eine Fiskalvertretung hat, wie eine Lieferung innerhalb dieses Landes besteuert.','D',1,'Y','Y','N','N','N','N','N','Y','N','N','N','N','Y','N','Streckengeschäft: Steuer wie im Bestimmungsland',0,TO_TIMESTAMP('2026-11-26 21:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593864 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Physical column
/* DDL */ SELECT public.db_alter_table('AD_Org','ALTER TABLE public.AD_Org ADD COLUMN IsDropShipTaxFromDestCountry CHAR(1) DEFAULT ''N'' CHECK (IsDropShipTaxFromDestCountry IN (''Y'',''N'')) NOT NULL')
;

-- Organisation window, header tab 143: field right after "One-Stop Shop (OSS)", single view only (not in grid)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593864,788200 /*From ID Server*/,0,143,0,TO_TIMESTAMP('2026-11-26 21:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn angehakt, wird eine Verkaufszeile aus einem Streckengeschäfts-Lager in ein Land, für das die Organisation eine Fiskalvertretung hat, wie eine Lieferung innerhalb dieses Landes besteuert.',0,'D',0,'Y','Y','N','N','N','N','N','N','Streckengeschäft: Steuer wie im Bestimmungsland',90,0,0,1,1,TO_TIMESTAMP('2026-11-26 21:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=788200 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=788200
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(788200)
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,788200,0,143,540467,656525 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-26 21:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn angehakt, wird eine Verkaufszeile aus einem Streckengeschäfts-Lager in ein Land, für das die Organisation eine Fiskalvertretung hat, wie eine Lieferung innerhalb dieses Landes besteuert.','Y','N','N','Y','N','N','N',0,'Streckengeschäft: Steuer wie im Bestimmungsland',40,0,0,TO_TIMESTAMP('2026-11-26 21:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- propagate the element translations to the column / field _Trl rows (per language; propagation only touches rows whose Updated differs from the element translation's, hence the distinct timestamps above)
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585636,'en_US')
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585636,'de_DE')
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585636,'de_CH')
;
