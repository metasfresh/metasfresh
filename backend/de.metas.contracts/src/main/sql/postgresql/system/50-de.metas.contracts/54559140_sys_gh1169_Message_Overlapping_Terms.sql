-- 2017-03-27T15:19:32.260
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544282,0,TO_TIMESTAMP('2017-03-27 15:19:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.flatrate','Y','Der neu erstellte Vertrag {0} für den Partner {1} konnte nicht fertig gestellt werden, weil er zeitlich mit einem anderen Vertrag zu den selben Produkten überlappt.
','I',TO_TIMESTAMP('2017-03-27 15:19:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.flatrate.process.C_Flatrate_Term_Create.OverlappingTerm')
;

-- 2017-03-27T15:19:32.266
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544282 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

