delete from AD_Process where Classname='de.metas.purchasing.process.POCreateFromPurchaseSchedule';


-- Dec 5, 2016 5:12 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540064
;

-- Dec 5, 2016 5:12 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=540064
;

-- Dec 5, 2016 5:12 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540064 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Dec 5, 2016 5:18 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ModelValidator WHERE AD_ModelValidator_ID=540090
;







-- Dec 5, 2016 5:31 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540011
;

-- Dec 5, 2016 5:32 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540013
;

-- Dec 5, 2016 5:32 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540010
;

-- Dec 5, 2016 5:33 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540223
;

-- Dec 5, 2016 5:33 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540223
;

-- Dec 5, 2016 5:33 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=501865
;

-- Dec 5, 2016 5:33 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=501865
;

-- Dec 5, 2016 5:33 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=501865 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Dec 5, 2016 5:34 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2016-12-05 17:34:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=501866
;

-- Dec 5, 2016 5:34 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=501865
;

-- Dec 5, 2016 5:34 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=501865
;

-- Dec 5, 2016 5:35 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=540819
;

-- Dec 5, 2016 5:35 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=540879
;

-- Dec 5, 2016 5:35 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=540820
;

-- Dec 5, 2016 5:35 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=501866
;

-- Dec 5, 2016 5:35 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=501866
;

delete from AD_Sequence where Name in ('M_PurchaseSchedule', 'DocumentNo_M_PurchaseSchedule');


-- Dec 5, 2016 5:40 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=541317
;

-- Dec 5, 2016 5:40 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=541317
;






create table backup.M_PurchaseSchedule_BKP20161205 as select * from M_PurchaseSchedule;
drop table M_PurchaseSchedule;
-- select * from M_PurchaseSchedule;







-- Dec 5, 2016 5:44 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=549632
;

-- Dec 5, 2016 5:45 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=547233
;

-- Dec 5, 2016 5:45 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=547233
;

-- Dec 5, 2016 5:45 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=548342
;

-- Dec 5, 2016 5:45 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=548342
;

-- Dec 5, 2016 5:46 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=545151
;

-- Dec 5, 2016 5:46 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=545151
;








alter table C_OrderLine drop column IsIndividualPOSchedule;






-- Dec 5, 2016 5:47 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540047
;

-- Dec 5, 2016 5:47 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540047
;

-- Dec 5, 2016 5:49 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540224
;

-- Dec 5, 2016 5:49 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540224
;

