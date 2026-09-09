



-- 2026-05-06T11:13:03.289Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584832,0,'EmptyColumn',TO_TIMESTAMP('2026-05-06 11:13:02.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Field used as a placeholder','de.metas.datev','','Y','Empty Column','Empty Column',TO_TIMESTAMP('2026-05-06 11:13:02.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-06T11:13:03.297Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584832 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: EmptyColumn
-- 2026-05-06T11:14:36.385Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Leerspalte', PrintName='Leerspalte',Updated=TO_TIMESTAMP('2026-05-06 11:14:36.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584832 AND AD_Language in ('de_CH', 'de_DE')
;

-- 2026-05-06T11:14:36.387Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-06T11:14:36.593Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584832,'de_CH')
;


-- 2026-05-06T11:14:36.593Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584832,'de_DE')
;


-- 2026-05-06T11:14:40.493Z
UPDATE AD_Element SET Description='Field used as a structural placeholder to maintain the required DATEV CSV column sequence. It contains no data and is exported as an empty value.',Updated=TO_TIMESTAMP('2026-05-06 11:14:40.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584832
;

-- 2026-05-06T11:14:40.494Z
UPDATE AD_Element_Trl trl SET Description='Field used as a structural placeholder to maintain the required DATEV CSV column sequence. It contains no data and is exported as an empty value.' WHERE AD_Element_ID=584832 AND AD_Language='de_DE'
;

-- 2026-05-06T11:14:40.498Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584832,'de_DE')
;

-- Element: EmptyColumn
-- 2026-05-06T11:14:47.708Z
UPDATE AD_Element_Trl SET Description='Feld, das als struktureller Platzhalter dient, um die erforderliche DATEV-Spaltenreihenfolge einzuhalten. Es enthält keine Daten und wird als Leerwert exportiert.',Updated=TO_TIMESTAMP('2026-05-06 11:14:47.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584832 AND AD_Language='de_CH'
;

-- 2026-05-06T11:14:47.709Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-06T11:14:47.905Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584832,'de_CH')
;

-- Element: EmptyColumn
-- 2026-05-06T11:14:59.979Z
UPDATE AD_Element_Trl SET Description='Field used as a structural placeholder to maintain the required DATEV CSV column sequence. It contains no data and is exported as an empty value.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-06 11:14:59.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584832 AND AD_Language='en_US'
;

-- 2026-05-06T11:14:59.980Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-06T11:15:00.181Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584832,'en_US')
;

-- Column: DATEV_ExportLine.EmptyColumn
-- 2026-05-06T11:16:37.477Z
UPDATE AD_Column SET AD_Element_ID=584832, ColumnName='EmptyColumn', Description='Field used as a structural placeholder to maintain the required DATEV CSV column sequence. It contains no data and is exported as an empty value.', Help='', Name='Empty Column',Updated=TO_TIMESTAMP('2026-05-06 11:16:37.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592477
;

-- 2026-05-06T11:16:37.482Z
UPDATE AD_Column_Trl trl SET Name='Empty Column' WHERE AD_Column_ID=592477 AND AD_Language='de_DE'
;

-- 2026-05-06T11:16:37.483Z
UPDATE AD_Field SET Name='Empty Column', Description='Field used as a structural placeholder to maintain the required DATEV CSV column sequence. It contains no data and is exported as an empty value.', Help='' WHERE AD_Column_ID=592477
;

-- 2026-05-06T11:16:37.484Z
/* DDL */  select update_Column_Translation_From_AD_Element(584832)
;

-- 2026-05-06T11:16:43.384Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN EmptyColumn VARCHAR(255)')
;

-- 2026-05-06T11:16:43.384Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine DROP COLUMN NULLCOLUMNS')
;
