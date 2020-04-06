-- 10.09.2015 09:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Form (AccessLevel,AD_Client_ID,AD_Form_ID,AD_Org_ID,Classname,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsOneInstanceOnly,Modal,Name,Updated,UpdatedBy) VALUES ('3',0,540065,0,'org.adempiere.uom.form.UOMConversionCheckFormPanel',TO_TIMESTAMP('2015-09-10 09:14:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Test UOM Conversions',TO_TIMESTAMP('2015-09-10 09:14:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.09.2015 09:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Form_Trl (AD_Language,AD_Form_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Form_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Form t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Form_ID=540065 AND NOT EXISTS (SELECT * FROM AD_Form_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Form_ID=t.AD_Form_ID)
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Form_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('X',0,540065,540646,0,TO_TIMESTAMP('2015-09-10 09:15:02','YYYY-MM-DD HH24:MI:SS'),100,'D','UOMConversionCheckFormPanel','Y','N','N','N','Test UOM Conversions',TO_TIMESTAMP('2015-09-10 09:15:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540646 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540646, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540646)
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=268 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=125 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=422 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=107 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=130 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=188 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=227 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=381 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=126 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=421 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=267 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=534 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=490 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=132 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=310 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53432 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=128 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=585 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53210 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=187 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53211 AND AD_Tree_ID=10
;

-- 10.09.2015 09:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540646 AND AD_Tree_ID=10
;















-- 10.09.2015 09:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543608,0,TO_TIMESTAMP('2015-09-10 09:35:24','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','QtyConv','I',TO_TIMESTAMP('2015-09-10 09:35:24','YYYY-MM-DD HH24:MI:SS'),0,'QtyConv')
;

-- 10.09.2015 09:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543608 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 10.09.2015 11:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='D', MsgText='Menge (Maßeinheit nach)',Updated=TO_TIMESTAMP('2015-09-10 11:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543608
;

-- 10.09.2015 11:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543608
;

