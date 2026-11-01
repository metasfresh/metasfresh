-- Run mode: WEBUI

-- Reference: MobileUI_UserProfile_DD_CaptionItem_Field
-- Value: SourceDocsWithPickingNote
-- ValueName: SourceDocsWithPickingNote
-- Caption element for the mobile distribution job list: lists the source documents of the job, each with its picking note
-- (one line per document, so a bundled line names every order it supplies: manufacturing orders with their picking instruction, sales orders without).
--
-- IDs allocated from idserver.metas.de on 2026-10-30:
--   AD_Ref_List 544384 (SourceDocsWithPickingNote, AD_Reference 542025)
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542025,544384/*From ID Server*/,TO_TIMESTAMP('2026-10-30 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Quellbelege mit Kommissionierhinweis',TO_TIMESTAMP('2026-10-30 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SourceDocsWithPickingNote','SourceDocsWithPickingNote')
;

-- 2026-10-30T12:00:00.001Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544384 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: MobileUI_UserProfile_DD_CaptionItem_Field -> SourceDocsWithPickingNote
-- 2026-10-30T12:00:00.002Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-30 12:00:00.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544384
;

-- Reference Item: MobileUI_UserProfile_DD_CaptionItem_Field -> SourceDocsWithPickingNote
-- 2026-10-30T12:00:00.003Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-30 12:00:00.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544384
;

-- Reference Item: MobileUI_UserProfile_DD_CaptionItem_Field -> SourceDocsWithPickingNote
-- 2026-10-30T12:00:00.004Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Source documents with picking note',Updated=TO_TIMESTAMP('2026-10-30 12:00:00.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544384
;

-- 2026-10-30T12:00:00.005Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
