-- me03#29671: Normalize ManufacturingResourceType label terminology (EN + DE)
-- Reference inventory: ai-work/29671/ad-label-inventory.md
-- Terminology locked: ai-work/29671/terminology.md

-- AD_Ref_List: ManufacturingResourceType=ES — set EN Name to "External System"
-- 2026-05-06T10:15:00Z
UPDATE AD_Ref_List SET Name='External System', Updated=TO_TIMESTAMP('2026-05-06 10:15:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0 WHERE AD_Ref_List_ID=543704;

-- AD_Ref_List: ManufacturingResourceType=PL — set EN Name to "Production Line"
-- 2026-05-06T10:15:01Z
UPDATE AD_Ref_List SET Name='Production Line', Updated=TO_TIMESTAMP('2026-05-06 10:15:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0 WHERE AD_Ref_List_ID=53244;

-- AD_Ref_List_Trl (de_DE) for PL — Linie → Produktionslinie
-- 2026-05-06T10:15:02Z
UPDATE AD_Ref_List_Trl SET Name='Produktionslinie', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-06 10:15:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0 WHERE AD_Ref_List_ID=53244 AND AD_Language='de_DE';

-- AD_Ref_List_Trl (de_CH) for PL — Linie → Produktionslinie
-- 2026-05-06T10:15:03Z
UPDATE AD_Ref_List_Trl SET Name='Produktionslinie', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-06 10:15:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0 WHERE AD_Ref_List_ID=53244 AND AD_Language='de_CH';

-- AD_Ref_List: ManufacturingResourceType=PT — set EN Name to "Plant"
-- 2026-05-06T10:15:04Z
UPDATE AD_Ref_List SET Name='Plant', Updated=TO_TIMESTAMP('2026-05-06 10:15:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0 WHERE AD_Ref_List_ID=53245;

-- AD_Ref_List: ManufacturingResourceType=WC — set EN Name to "Work Center"
-- 2026-05-06T10:15:05Z
UPDATE AD_Ref_List SET Name='Work Center', Updated=TO_TIMESTAMP('2026-05-06 10:15:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0 WHERE AD_Ref_List_ID=53246;

-- AD_Ref_List_Trl (de_DE) for WC — Abteilung → Arbeitsbereich
-- 2026-05-06T10:15:06Z
UPDATE AD_Ref_List_Trl SET Name='Arbeitsbereich', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-06 10:15:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0 WHERE AD_Ref_List_ID=53246 AND AD_Language='de_DE';

-- AD_Ref_List_Trl (de_CH) for WC — Abteilung → Arbeitsbereich
-- 2026-05-06T10:15:07Z
UPDATE AD_Ref_List_Trl SET Name='Arbeitsbereich', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-06 10:15:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0 WHERE AD_Ref_List_ID=53246 AND AD_Language='de_CH';

-- AD_Ref_List: ManufacturingResourceType=WS — set EN Name to "Workstation"
-- 2026-05-06T10:15:08Z
UPDATE AD_Ref_List SET Name='Workstation', Updated=TO_TIMESTAMP('2026-05-06 10:15:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0 WHERE AD_Ref_List_ID=53247;
