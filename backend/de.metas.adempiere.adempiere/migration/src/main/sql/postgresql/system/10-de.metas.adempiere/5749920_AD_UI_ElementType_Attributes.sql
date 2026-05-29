-- Run mode: SWING_CLIENT

-- Reference: AD_UI_ElementType
-- Value: A
-- ValueName: Attributes
-- 2025-03-24T18:04:21.051Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540736,543866,TO_TIMESTAMP('2025-03-24 18:04:20.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Attributes',TO_TIMESTAMP('2025-03-24 18:04:20.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','Attributes')
;

-- 2025-03-24T18:04:21.053Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543866 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Field: Fenster Verwaltung(102,D) -> UI Element(540754,D) -> Feld
-- Column: AD_UI_Element.AD_Field_ID
-- 2025-03-24T18:07:39.623Z
UPDATE AD_Field SET DisplayLogic='@AD_UI_ElementType@=F | @AD_UI_ElementType@=A',Updated=TO_TIMESTAMP('2025-03-24 18:07:39.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=557167
;

