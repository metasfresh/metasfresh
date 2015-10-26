-- 12.10.2015 15:00:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542906,0,'DESADV_SumPercentage',TO_TIMESTAMP('2015-10-12 15:00:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','DESADV_SumPercentage','DESADV_SumPercentage',TO_TIMESTAMP('2015-10-12 15:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.10.2015 15:00:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542906 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 12.10.2015 15:11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543657,0,TO_TIMESTAMP('2015-10-12 15:11:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','[15:08:25] Tobias Schoeneberg: Im System ist hinterlegt, dass ein DESADV-Beleg erst als EDI-Dokument gesendet werden darf, wenn mindestens {0}% der beauftragten Menge ausgeliefert wurde.
Falls bestimmte DESADV-Belege ausnahmsweise trotzdem gesendet werden sollen, verständigen Sie bitte einen Administrator, 
um den "Geliefert % Minimum"-Wert der betreffenden Datensatzes herunter zu setzen.
with {0} being the desadv-record''s minimum-shipped-value','E',TO_TIMESTAMP('2015-10-12 15:11:37','YYYY-MM-DD HH24:MI:SS'),100,'EDI_DESADV_RefuseSending')
;

-- 12.10.2015 15:11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543657 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 12.10.2015 15:12:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='EDI_DESADV_SumPercentage', Name='Geliefert %', PrintName='Geliefert %',Updated=TO_TIMESTAMP('2015-10-12 15:12:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542906
;

-- 12.10.2015 15:12:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542906
;

-- 12.10.2015 15:12:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EDI_DESADV_SumPercentage', Name='Geliefert %', Description=NULL, Help=NULL WHERE AD_Element_ID=542906
;

-- 12.10.2015 15:12:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDI_DESADV_SumPercentage', Name='Geliefert %', Description=NULL, Help=NULL, AD_Element_ID=542906 WHERE UPPER(ColumnName)='EDI_DESADV_SUMPERCENTAGE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 12.10.2015 15:12:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDI_DESADV_SumPercentage', Name='Geliefert %', Description=NULL, Help=NULL WHERE AD_Element_ID=542906 AND IsCentrallyMaintained='Y'
;

-- 12.10.2015 15:12:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geliefert %', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542906) AND IsCentrallyMaintained='Y'
;

-- 12.10.2015 15:12:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geliefert %', Name='Geliefert %' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542906)
;

-- 12.10.2015 15:13:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552776,542906,0,22,540644,'N','EDI_DESADV_SumPercentage',TO_TIMESTAMP('2015-10-12 15:13:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geliefert %',0,TO_TIMESTAMP('2015-10-12 15:13:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.10.2015 15:13:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552776 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.10.2015 15:13:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542908,0,'EDI_DESADV_MinimumSumPercentage',TO_TIMESTAMP('2015-10-12 15:13:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','Geliefert % Minimum','Geliefert % Minimum',TO_TIMESTAMP('2015-10-12 15:13:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.10.2015 15:13:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542908 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

















-- 12.10.2015 15:25:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552779,542908,0,22,540644,'N','EDI_DESADV_MinimumSumPercentage',TO_TIMESTAMP('2015-10-12 15:25:29','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geliefert % Minimum',0,TO_TIMESTAMP('2015-10-12 15:25:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.10.2015 15:25:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552779 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.10.2015 15:25:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE EDI_Desadv ADD EDI_DESADV_MinimumSumPercentage NUMERIC DEFAULT NULL 
;

-- 12.10.2015 15:25:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE EDI_Desadv ADD EDI_DESADV_SumPercentage NUMERIC DEFAULT NULL 
;



-- 12.10.2015 16:09:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540892,'C',TO_TIMESTAMP('2015-10-12 16:09:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','de.metas.esb.edi.DefaultMinimumPercentage',TO_TIMESTAMP('2015-10-12 16:09:54','YYYY-MM-DD HH24:MI:SS'),100,'10')
;



-- 12.10.2015 18:14:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Im System ist hinterlegt, dass ein DESADV-Beleg erst als EDI-Dokument gesendet werden darf, wenn mindestens {0}% der beauftragten Menge ausgeliefert wurde.
Falls dieser DESADV-Beleg ausnahmsweise trotzdem gesendet werden soll, verständigen Sie bitte einen Administrator, 
um den "Geliefert % Minimum"-Wert dieses Datensatzes herunter zu setzen.
[15:09:14] Tobias Schoeneberg: with {0} being the desadv-record''s minimum-shipped-value',Updated=TO_TIMESTAMP('2015-10-12 18:14:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543657
;

-- 12.10.2015 18:14:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543657
;


-- 13.10.2015 11:28:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,552776,556362,0,540662,88,TO_TIMESTAMP('2015-10-13 11:28:27','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi',0,'Y','Y','Y','Y','N','N','N','N','Y','Geliefert %',85,95,TO_TIMESTAMP('2015-10-13 11:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.10.2015 11:28:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556362 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 13.10.2015 11:29:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,552779,556363,0,540662,119,TO_TIMESTAMP('2015-10-13 11:29:17','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.esb.edi',0,'Y','Y','Y','Y','N','N','N','N','Y','Geliefert % Minimum',105,96,TO_TIMESTAMP('2015-10-13 11:29:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.10.2015 11:29:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556363 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 13.10.2015 11:29:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-10-13 11:29:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556363
;

-- 13.10.2015 11:30:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-10-13 11:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556362
;

-- 13.10.2015 13:34:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' SELECT ( CASE WHEN   	SUM (l.QtyEnterred) = 0 THEN NULL 	ELSE 		SUM (l.QtyDeliveredInUOM)/SUM (l.QtyEntered) 	END FROM EDI_DesadvLine l WHERE l.EDI_Desadv_ID = EDI_Desadv.EDI_Desadv_ID )', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-10-13 13:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552776
;

-- 13.10.2015 13:34:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2015-10-13 13:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552776
;

commit;
-- 13.10.2015 13:34:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','EDI_DESADV_SumPercentage','NUMERIC',null,'NULL')
;

commit;




-- 13.10.2015 13:43:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' (SELECT  CASE WHEN   	SUM (l.QtyEnterred) = 0 THEN NULL 	ELSE 		SUM (l.QtyDeliveredInUOM)/SUM (l.QtyEntered) 	END FROM EDI_DesadvLine l WHERE l.EDI_Desadv_ID = EDI_Desadv.EDI_Desadv_ID )',Updated=TO_TIMESTAMP('2015-10-13 13:43:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552776
;

-- 13.10.2015 13:45:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' (SELECT   	CASE WHEN   	 		SUM (l.QtyEntered) = 0  	THEN NULL 	 	ELSE 		 		SUM (l.QtyDeliveredInUOM)/SUM (l.QtyEntered) 	 	END  FROM EDI_DesadvLine l WHERE l.EDI_Desadv_ID = EDI_Desadv.EDI_Desadv_ID )',Updated=TO_TIMESTAMP('2015-10-13 13:45:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552776
;

-- 13.10.2015 13:49:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' (SELECT   	CASE WHEN   	 		SUM (l.QtyEntered) = 0  	THEN NULL 	 	ELSE 		 		(SUM (l.QtyDeliveredInUOM)/SUM (l.QtyEntered) ) * 100 	END  FROM EDI_DesadvLine l WHERE l.EDI_Desadv_ID = EDI_Desadv.EDI_Desadv_ID )',Updated=TO_TIMESTAMP('2015-10-13 13:49:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552776
;

-- 13.10.2015 14:05:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' (select   	CASE WHEN   	 		SUM (l.QtyEntered) = 0  	THEN NULL 	 	ELSE 		 		round ((SUM (l.QtyDeliveredInUOM)/SUM (l.QtyEntered) ) * 100, 2) 	END  from EDI_DesadvLine l where l.EDI_Desadv_ID = EDI_Desadv.EDI_Desadv_ID )',Updated=TO_TIMESTAMP('2015-10-13 14:05:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552776
;



-- 13.10.2015 16:33:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Im System ist hinterlegt, dass ein DESADV-Beleg erst als EDI-Dokument gesendet werden darf, wenn mindestens {0}% der beauftragten Menge ausgeliefert wurde. Folgende Belegnummern liegen unterhalb dieser Schwelle:
{1}
Falls bestimmte DESADV-Belege ausnahmsweise trotzdem gesendet werden sollen, verständigen Sie bitte einen Administrator, 
um die jeweiligen "Geliefert % Minimum"-Werte herunter zu setzen.',Updated=TO_TIMESTAMP('2015-10-13 16:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543657
;

-- 13.10.2015 16:33:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543657
;


