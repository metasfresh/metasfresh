-- Run mode: SWING_CLIENT

-- gh#27441: Create a dedicated AD_Element for the "Verfügbar" label used in the sales availability context.
-- The previous migration scripts reused AD_Element 1762 (IsAvailable / "Ressource ist verfügbar") which
-- has misleading description and help text from the resource scheduling domain.

-- New AD_Element for "Verfügbar" in sales availability context
-- 2026-02-18T15:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584543,0,TO_TIMESTAMP('2026-02-18 15:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
'Zeigt an, ob die bestellte Menge kurzfristig aus dem Lagerbestand bedient werden kann.',
'D','Y','Verfügbar','Verfügbar',
TO_TIMESTAMP('2026-02-18 15:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T15:00:00.100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584543
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US translation
-- 2026-02-18T15:00:00.200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Available', PrintName='Available',
Description='Indicates whether the ordered quantity is available for sales at short notice.',
Updated=TO_TIMESTAMP('2026-02-18 15:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Element_ID=584543 AND AD_Language='en_US'
;

-- 2026-02-18T15:00:00.300Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-18T15:00:00.400Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584543,'en_US')
;

-- Update AD_Field in Auftrag_OLD (window 143) to use the new element
-- Field: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> Verfügbar
-- Column: C_OrderLine.InsufficientQtyAvailableForSalesColor_ID
-- 2026-02-18T15:00:00.500Z
UPDATE AD_Field SET AD_Name_ID=584543,
Description='Zeigt an, ob die bestellte Menge kurzfristig aus dem Lagerbestand bedient werden kann.',
Help=NULL,
Updated=TO_TIMESTAMP('2026-02-18 15:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Field_ID=578421
;

-- 2026-02-18T15:00:00.600Z
UPDATE AD_Field_Trl trl SET
Description='Zeigt an, ob die bestellte Menge kurzfristig aus dem Lagerbestand bedient werden kann.',
Help=NULL
WHERE AD_Field_ID=578421 AND AD_Language='de_DE'
;

-- 2026-02-18T15:00:00.700Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584543)
;

-- 2026-02-18T15:00:00.800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=578421
;

-- 2026-02-18T15:00:00.900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(578421)
;

-- Update AD_Field in Lieferdisposition (window 500221) to use the new element
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Verfügbar
-- Column: M_ShipmentSchedule.InsufficientQtyAvailableForSalesColor_ID
-- 2026-02-18T15:00:01.000Z
UPDATE AD_Field SET AD_Name_ID=584543,
Description='Zeigt an, ob die bestellte Menge kurzfristig aus dem Lagerbestand bedient werden kann.',
Help=NULL,
Updated=TO_TIMESTAMP('2026-02-18 15:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Field_ID=774164
;

-- 2026-02-18T15:00:01.100Z
UPDATE AD_Field_Trl trl SET
Description='Zeigt an, ob die bestellte Menge kurzfristig aus dem Lagerbestand bedient werden kann.',
Help=NULL
WHERE AD_Field_ID=774164 AND AD_Language='de_DE'
;

-- 2026-02-18T15:00:01.200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774164
;

-- 2026-02-18T15:00:01.300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774164)
;
