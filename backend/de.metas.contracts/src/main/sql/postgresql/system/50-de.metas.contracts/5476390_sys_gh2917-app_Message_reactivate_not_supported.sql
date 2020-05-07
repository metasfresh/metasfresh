-- 2017-11-06T08:23:52.252
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544564,0,TO_TIMESTAMP('2017-11-06 08:23:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Die Belegaktion "Reaktivierung" ist nicht erlaubt.
Hinweis: Es besteht die Möglichkeit, die Reaktivierung für einzelne Vertragsarten zu erlauben (System-Konfigurator de.metas.contracts.C_Flatrate_Term.allow_reactivate_*).','E',TO_TIMESTAMP('2017-11-06 08:23:47','YYYY-MM-DD HH24:MI:SS'),100,'Flatrate_DocAction_Not_Reactivate_Supported')
;

-- 2017-11-06T08:23:52.268
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544564 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-11-06T08:24:01.456
-- URL zum Konzept
UPDATE AD_Message SET Value='Flatrate_DocAction_Reactivate_Not_Supported',Updated=TO_TIMESTAMP('2017-11-06 08:24:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544564
;

