-- 2018-02-16T14:21:32.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (EntityType,Processing,AD_Client_ID,IsActive,CreatedBy,ModelPackage,Description,AD_EntityType_ID,IsDisplayed,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES ('de.metas.datev','N',0,'Y',100,'de.metas.datev.model','',540223,'Y',0,'de.metas.datev',100,TO_TIMESTAMP('2018-02-16 14:21:31','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 14:21:31','YYYY-MM-DD HH24:MI:SS'))
;

update AD_Column set EntityType='de.metas.datev' where AD_Column_ID in (531088, 531087);

-- 2018-02-16T14:24:27.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='de.metas.datev',Updated=TO_TIMESTAMP('2018-02-16 14:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540105
;

-- 2018-02-16T14:24:36.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET EntityType='de.metas.datev',Updated=TO_TIMESTAMP('2018-02-16 14:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540235
;

-- 2018-02-16T14:24:55.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='de.metas.datev',Updated=TO_TIMESTAMP('2018-02-16 14:24:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540106
;

-- 2018-02-16T14:25:00.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET EntityType='de.metas.datev',Updated=TO_TIMESTAMP('2018-02-16 14:25:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540236
;

update AD_Field set EntityType='de.metas.datev' where AD_Field_ID in (547745, 541815);

-- 2018-02-16T14:30:03.908
-- Drop Datev Export form (missing java code)
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540260
;
DELETE FROM AD_Menu WHERE AD_Menu_ID=540260
;
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540260 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;
DELETE FROM  AD_Form_Trl WHERE AD_Form_ID=540028
;
DELETE FROM AD_Form WHERE AD_Form_ID=540028
;




update AD_Message set EntityType='de.metas.datev' where EntityType='de.metas.datev_export';
update AD_SysConfig set EntityType='de.metas.datev' where EntityType='de.metas.datev_export';

delete from AD_EntityType where EntityType='de.metas.datev_export';
