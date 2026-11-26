-- IDs allocated from idserver.metas.de on 2026-08-19:
--   AD_Ref_List 544344 (PickingJobField_Options -> ProductNames)

-- Reference: PickingJobField_Options
-- Value: ProductNames
-- ValueName: ProductNames
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541850,544344 /*From ID Server*/,TO_TIMESTAMP('2026-08-19 19:04:38.667253','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.picking','Y','Produktnamen (alle)',TO_TIMESTAMP('2026-08-19 19:04:38.667253','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ProductNames','ProductNames')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544344 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PickingJobField_Options -> ProductNames_ProductNames
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-19 19:04:39','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544344
;

-- Reference Item: PickingJobField_Options -> ProductNames_ProductNames
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-19 19:04:40','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544344
;

-- Reference Item: PickingJobField_Options -> ProductNames_ProductNames
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Product names (all)', Updated=TO_TIMESTAMP('2026-08-19 19:04:41','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544344
;

UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Description for the new value ProductNames (AD_Ref_List_ID=544344)
UPDATE AD_Ref_List SET Description='Alle Produktnamen des Auftrags, durch Komma getrennt.', Updated=TO_TIMESTAMP('2026-08-19 19:04:42','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544344
;

UPDATE AD_Ref_List_Trl SET Description='Alle Produktnamen des Auftrags, durch Komma getrennt.', Updated=TO_TIMESTAMP('2026-08-19 19:04:43','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544344 AND AD_Language='de_DE'
;

UPDATE AD_Ref_List_Trl SET Description='Alle Produktnamen des Auftrags, durch Komma getrennt.', Updated=TO_TIMESTAMP('2026-08-19 19:04:44','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544344 AND AD_Language='de_CH'
;

UPDATE AD_Ref_List_Trl SET Description='All product names of the job, comma separated.', Updated=TO_TIMESTAMP('2026-08-19 19:04:45','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544344 AND AD_Language='en_US'
;

-- Description for the existing value Product (AD_Ref_List_ID=543862)
UPDATE AD_Ref_List SET Description='Der Produktname — nur wenn der Auftrag genau ein Produkt enthält, sonst leer.', Updated=TO_TIMESTAMP('2026-08-19 19:04:46','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=543862
;

UPDATE AD_Ref_List_Trl SET Description='Der Produktname — nur wenn der Auftrag genau ein Produkt enthält, sonst leer.', Updated=TO_TIMESTAMP('2026-08-19 19:04:47','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=543862 AND AD_Language='de_DE'
;

UPDATE AD_Ref_List_Trl SET Description='Der Produktname — nur wenn der Auftrag genau ein Produkt enthält, sonst leer.', Updated=TO_TIMESTAMP('2026-08-19 19:04:48','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=543862 AND AD_Language='de_CH'
;

UPDATE AD_Ref_List_Trl SET Description='The product name — only when the job holds exactly one product, otherwise empty.', Updated=TO_TIMESTAMP('2026-08-19 19:04:49','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=543862 AND AD_Language='en_US'
;
