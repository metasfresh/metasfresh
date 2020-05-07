--
-- rename the "folder" "Event log" to "Event" and more the "test event bus process" thingie there as well
--
-- 2018-01-10T07:36:48.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='event', Name='Event',Updated=TO_TIMESTAMP('2018-01-10 07:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541006
;

-- 2018-01-10T07:36:58.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541006, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541004 AND AD_Tree_ID=10
;

-- 2018-01-10T07:36:58.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541006, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541005 AND AD_Tree_ID=10
;

-- 2018-01-10T07:36:58.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541006, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540645 AND AD_Tree_ID=10
;

