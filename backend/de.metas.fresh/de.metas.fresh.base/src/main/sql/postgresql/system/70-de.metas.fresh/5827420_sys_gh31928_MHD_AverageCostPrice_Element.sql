-- General AD_Element for the "Standard EK" (AverageCostPrice) column of the MHD Liste report.
-- The HU_MHD_Report process exports HU_MHD_V with IsTranslateExcelHeaders='Y', so the Excel column
-- header is resolved from the AD_Element whose ColumnName matches the view column alias.
-- The sibling "Letzter EK" column reuses the existing general AD_Element with ColumnName='LastCostPrice'.

-- AD_Element 585511 (AverageCostPrice / "Standard EK"), from idserver.metas.de
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585511 /*From ID Server*/,0,'AverageCostPrice',TO_TIMESTAMP('2026-10-01 10:00:00.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Standard EK','Standard EK',TO_TIMESTAMP('2026-10-01 10:00:00.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Seed AD_Element_Trl rows for all active system languages (copies the German base text)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585511 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- English override
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Average Cost Price', PrintName='Average Cost Price',Updated=TO_TIMESTAMP('2026-10-01 10:00:12.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585511 AND AD_Language='en_US'
;

-- Sync base row from the base-language translation (no-op when the base language is not en_US)
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Cascade the translation to any dependent TRL tables
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585511,'en_US')
;
