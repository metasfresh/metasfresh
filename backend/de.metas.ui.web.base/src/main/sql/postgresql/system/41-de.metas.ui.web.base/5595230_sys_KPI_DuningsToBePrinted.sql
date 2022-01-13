-- 2021-06-28T07:12:10.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI (AD_Client_ID,AD_Org_ID,ChartType,Created,CreatedBy,IsActive,IsApplySecuritySettings,IsGenerateComparation,KPI_AllowedStaledTimeInSec,KPI_DataSource_Type,Name,Source_Table_ID,SQL_WhereClause,Updated,UpdatedBy,WEBUI_KPI_ID) VALUES (0,0,'M',TO_TIMESTAMP('2021-06-28 10:12:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N',10,'S','Dunnings to be printed',540435,'c_printing_queue.ItemName = ''Mahnung''
AND NOT EXISTS(SELECT pjl.C_Printing_Queue_ID FROM C_Print_Job_Line pjl WHERE pjl.C_Printing_Queue_ID = c_printing_queue.C_Printing_Queue_ID)
',TO_TIMESTAMP('2021-06-28 10:12:09','YYYY-MM-DD HH24:MI:SS'),100,540005)
;

-- 2021-06-28T07:13:31.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,UOMSymbol,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,542584,0,11,TO_TIMESTAMP('2021-06-28 10:13:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Enqueued','COUNT(1)','items',TO_TIMESTAMP('2021-06-28 10:13:31','YYYY-MM-DD HH24:MI:SS'),100,540005,540005)
;

-- 2021-06-28T07:13:31.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540005 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2021-06-28T07:13:43.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET IsApplySecuritySettings='Y',Updated=TO_TIMESTAMP('2021-06-28 10:13:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540005
;

