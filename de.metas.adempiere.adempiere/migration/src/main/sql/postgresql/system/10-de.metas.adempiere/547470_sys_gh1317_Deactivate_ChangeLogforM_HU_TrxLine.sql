--
-- Deactivate AD_ChangeLog for M_HU_TrxLine
--
UPDATE AD_Table SET IsChangeLog='N',Updated=TO_TIMESTAMP('2017-04-12 10:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540515
;
UPDATE AD_Table SET Help='I deactivated AD_ChangeLog for this table, because the lines are created once and then remain static and won''t be changed', IsDeleteable='N',Updated=TO_TIMESTAMP('2017-04-12 10:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540515
;
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-12 10:09:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540515
;