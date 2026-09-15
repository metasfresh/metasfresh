-- IDs allocated from idserver.metas.de on 2026-07-02:
--   AD_Field       781320 (PP_Order.Kostendifferenz on window 53009 / tab 53054)
--   AD_UI_Element  652428 (same field, UI element in the 'menge' group of tab 53054)

-- Surface the read-only Kostendifferenz virtual column (AD_Column_ID=592918, added in
-- 5811360) on the Production Order header tab (AD_Window_ID=53009, AD_Tab_ID=53054),
-- placed in the existing 'menge' (quantities/costs) element group (AD_UI_ElementGroup_ID=540186),
-- right after the last field there (M_HU_PI_Item_Product_ID, SeqNo=70/SeqNoGrid=100).
-- Read-only display field: label/help come from AD_Element 585075 (Task 4), not re-specified here.

INSERT INTO AD_Field (AD_Client_ID,AD_Org_ID,IsActive,CreatedBy,UpdatedBy,Created,Updated,
                       AD_Tab_ID,AD_Column_ID,AD_Field_ID,
                       IsDisplayed,IsReadOnly,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,
                       EntityType,SeqNo,SeqNoGrid,IsDisplayedGrid,
                       IsAlwaysUpdateable,DisplayLength,ColumnDisplayLength,SpanX,SpanY,
                       IsExcludeFromZoomTargets,IsOverrideFilterDefaultValue,IsHideGridColumnIfEmpty,
                       Name,Description)
VALUES (0,0,'Y',100,100,
        TO_TIMESTAMP('2026-07-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-07-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),
        53054,592918,781320 /*From ID Server*/,
        'Y','Y','N','N','N','N',
        'D',80,0,'N',
        'N',22,132,1,1,
        'Y','N','N',
        'Kostendifferenz','Kostendifferenz')
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name, Description, Help, IsTranslated,
                          AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781320
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Propagate en_US translation from AD_Element 585075 onto AD_Field_Trl (mirrors the AD_Column path).
UPDATE AD_Field_Trl
SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y',
    Updated=TO_TIMESTAMP('2026-07-02 11:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM AD_Element_Trl et
WHERE AD_Field_Trl.AD_Field_ID = 781320
  AND AD_Field_Trl.AD_Language = 'en_US'
  AND et.AD_Element_ID = 585075
  AND et.AD_Language = 'en_US'
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,IsActive,CreatedBy,UpdatedBy,Created,Updated,
                            AD_Tab_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,
                            IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,
                            Name,SeqNo,SeqNoGrid,SeqNo_SideList)
VALUES (0,0,'Y',100,100,
        TO_TIMESTAMP('2026-07-02 11:00:30','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-07-02 11:00:30','YYYY-MM-DD HH24:MI:SS'),
        53054,781320,540186,652428 /*From ID Server*/,'F',
        'N','Y','N','N',
        'Kostendifferenz',80,0,0)
;
