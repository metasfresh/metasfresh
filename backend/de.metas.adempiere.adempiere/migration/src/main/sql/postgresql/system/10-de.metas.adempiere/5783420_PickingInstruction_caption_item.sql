-- Run mode: SWING_CLIENT

-- Reference: MobileUI_UserProfile_DD_CaptionItem_Field
-- Value: PickingInstruction
-- ValueName: PickingInstruction
-- 2026-01-12T15:56:23.723Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542025,544097,TO_TIMESTAMP('2026-01-12 15:56:23.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Kommissionierhinweis',TO_TIMESTAMP('2026-01-12 15:56:23.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PickingInstruction','PickingInstruction')
;

-- 2026-01-12T15:56:23.746Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544097 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: MobileUI_UserProfile_DD_CaptionItem_Field -> PickingInstruction_PickingInstruction
-- 2026-01-12T15:56:29.961Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-12 15:56:29.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544097
;

-- Reference Item: MobileUI_UserProfile_DD_CaptionItem_Field -> PickingInstruction_PickingInstruction
-- 2026-01-12T15:56:34.905Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-12 15:56:34.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544097
;

-- Reference Item: MobileUI_UserProfile_DD_CaptionItem_Field -> PickingInstruction_PickingInstruction
-- 2026-01-12T15:56:59.008Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Picking Instruction',Updated=TO_TIMESTAMP('2026-01-12 15:56:59.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544097
;

-- 2026-01-12T15:56:59.010Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

