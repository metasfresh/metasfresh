-- 2021-06-28T10:34:30.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI (AD_Client_ID,AD_Org_ID,ChartType,Created,CreatedBy,IsActive,IsApplySecuritySettings,IsGenerateComparation,KPI_AllowedStaledTimeInSec,KPI_DataSource_Type,Name,Source_Table_ID,SQL_WhereClause,Updated,UpdatedBy,WEBUI_KPI_ID) VALUES (0,0,'M',TO_TIMESTAMP('2021-06-28 13:34:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N',1,'S','Not processed ESR lines',540410,'WHERE EXISTS(
        SELECT 1
        FROM ESR_Import imp
        WHERE TRUE
          AND imp.ESR_Import_ID = ESR_ImportLine.ESR_Import_ID
          AND imp.IsActive = ''Y''
          AND imp.Processed = ''N''
    )
  AND ESRTrxType != ''999'' -- receipt
  AND ESRTrxType != ''995'' -- payment
  AND Processed = ''N''
',TO_TIMESTAMP('2021-06-28 13:34:30','YYYY-MM-DD HH24:MI:SS'),100,540006)
;

-- 2021-06-28T10:37:33.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,UOMSymbol,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,1997,0,11,TO_TIMESTAMP('2021-06-28 13:37:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Counter','count(1)','items',TO_TIMESTAMP('2021-06-28 13:37:33','YYYY-MM-DD HH24:MI:SS'),100,540006,540006)
;

-- 2021-06-28T10:37:33.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540006 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2021-06-28T10:39:28.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET IsApplySecuritySettings='N',Updated=TO_TIMESTAMP('2021-06-28 13:39:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540006
;

