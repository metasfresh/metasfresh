-- Value: HULocatorNotMatchingPickFromLocator
-- 2025-01-23T15:37:10.850Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545492,0,TO_TIMESTAMP('2025-01-23 15:37:10.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Der Lagerort der HU stimmt nicht mit dem Lagerort im Auftrag überein.','E',TO_TIMESTAMP('2025-01-23 15:37:10.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HULocatorNotMatchingPickFromLocator')
;

-- 2025-01-23T15:37:10.852Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545492 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: HULocatorNotMatchingPickFromLocator
-- 2025-01-23T15:37:23.324Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The HU''s locator does not match the order''s locator.',Updated=TO_TIMESTAMP('2025-01-23 15:37:23.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545492
;

-- Value: HULocatorNotMatchingPickFromLocator
-- 2025-01-23T15:37:26.019Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-01-23 15:37:26.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545492
;

-- Value: HULocatorNotMatchingPickFromLocator
-- 2025-01-23T15:37:29.254Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-01-23 15:37:29.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545492
;

