



-- 06.06.2016 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543084,0,'DropShip_BPartner_Override_ID',TO_TIMESTAMP('2016-06-06 17:16:50','YYYY-MM-DD HH24:MI:SS'),100,NULL,'D',NULL,'Y','Lieferempfänger abw.','Lieferempfänger abw.',TO_TIMESTAMP('2016-06-06 17:16:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543084 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.06.2016 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554406,543084,0,30,138,540244,'N','DropShip_BPartner_Override_ID',TO_TIMESTAMP('2016-06-06 17:18:35','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',10,'Y','N','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Lieferempfänger abw.',0,TO_TIMESTAMP('2016-06-06 17:18:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.06.2016 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554406 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.06.2016 17:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.ordercandidate',Updated=TO_TIMESTAMP('2016-06-06 17:19:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53458
;

-- 06.06.2016 17:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_OLCand ADD DropShip_BPartner_Override_ID NUMERIC(10) DEFAULT NULL 
;





-- 06.06.2016 17:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543085,0,'DropShip_BPartner_Location_Override_ID',TO_TIMESTAMP('2016-06-06 17:21:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Lieferadresse abw.','Lieferadresse abw.',TO_TIMESTAMP('2016-06-06 17:21:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 17:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543085 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.06.2016 17:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554407,543085,0,19,138,540244,'N','DropShip_BPartner_Location_Override_ID',TO_TIMESTAMP('2016-06-06 17:21:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',10,'Y','N','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Lieferadresse abw.',0,TO_TIMESTAMP('2016-06-06 17:21:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.06.2016 17:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554407 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.06.2016 17:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Val_Rule_ID=52022,Updated=TO_TIMESTAMP('2016-06-06 17:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554407
;

-- 06.06.2016 17:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=159,Updated=TO_TIMESTAMP('2016-06-06 17:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554407
;

-- 06.06.2016 17:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_OLCand ADD DropShip_BPartner_Location_Override_ID NUMERIC(10) DEFAULT NULL 
;





-- 06.06.2016 17:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543087,0,'DropShip_BPartner_Effective_ID',TO_TIMESTAMP('2016-06-06 17:44:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Lieferempfänger eff.','Lieferempfänger eff.',TO_TIMESTAMP('2016-06-06 17:44:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 17:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543087 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.06.2016 17:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554409,543087,0,30,138,540244,'N','DropShip_BPartner_Effective_ID','COALESCE(C_OLCand.DropShip_BPartner_Override_ID,C_OLCand.DropShip_BPartner_ID)',TO_TIMESTAMP('2016-06-06 17:45:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',10,'Y','N','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Lieferempfänger eff.',0,TO_TIMESTAMP('2016-06-06 17:45:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.06.2016 17:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554409 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;




-- 06.06.2016 17:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543088,0,'DropShip_BPartner_Location_Effective_ID',TO_TIMESTAMP('2016-06-06 17:46:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Lieferadresse eff.','Lieferadresse eff.',TO_TIMESTAMP('2016-06-06 17:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 17:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543088 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.06.2016 17:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554410,543088,0,30,159,540244,52022,'N','DropShip_BPartner_Location_Effective_ID',TO_TIMESTAMP('2016-06-06 17:46:57','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',10,'Y','N','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Lieferadresse eff.',0,TO_TIMESTAMP('2016-06-06 17:46:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.06.2016 17:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554410 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.06.2016 17:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' COALESCE(C_OLCand.DropShip_BPartner_Location_Override_ID,C_OLCand.DropShip_BPartner_Location_ID)', IsAlwaysUpdateable='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-06-06 17:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554410
;
























-- 06.06.2016 17:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='DropShip_Location_Override_ID',Updated=TO_TIMESTAMP('2016-06-06 17:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543085
;

-- 06.06.2016 17:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DropShip_Location_Override_ID', Name='Lieferadresse abw.', Description=NULL, Help=NULL WHERE AD_Element_ID=543085
;

-- 06.06.2016 17:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DropShip_Location_Override_ID', Name='Lieferadresse abw.', Description=NULL, Help=NULL, AD_Element_ID=543085 WHERE UPPER(ColumnName)='DROPSHIP_LOCATION_OVERRIDE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 06.06.2016 17:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DropShip_Location_Override_ID', Name='Lieferadresse abw.', Description=NULL, Help=NULL WHERE AD_Element_ID=543085 AND IsCentrallyMaintained='Y'
;




-- 06.06.2016 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='DropShip_Location_Effective_ID',Updated=TO_TIMESTAMP('2016-06-06 17:50:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543088
;

-- 06.06.2016 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DropShip_Location_Effective_ID', Name='Lieferadresse eff.', Description=NULL, Help=NULL WHERE AD_Element_ID=543088
;

-- 06.06.2016 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DropShip_Location_Effective_ID', Name='Lieferadresse eff.', Description=NULL, Help=NULL, AD_Element_ID=543088 WHERE UPPER(ColumnName)='DROPSHIP_LOCATION_EFFECTIVE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 06.06.2016 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DropShip_Location_Effective_ID', Name='Lieferadresse eff.', Description=NULL, Help=NULL WHERE AD_Element_ID=543088 AND IsCentrallyMaintained='Y'
;



-- 06.06.2016 17:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' COALESCE(C_OLCand.DropShip_Location_Override_ID,C_OLCand.DropShip_Location_ID)',Updated=TO_TIMESTAMP('2016-06-06 17:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554410
;





-- 06.06.2016 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543089,0,'HandOver_Partner_Override_ID',TO_TIMESTAMP('2016-06-06 18:02:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Übergabe an abw.','Übergabe an abw.',TO_TIMESTAMP('2016-06-06 18:02:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543089 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.06.2016 18:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554411,543089,0,30,173,540244,540208,'N','HandOver_Partner_Override_ID',TO_TIMESTAMP('2016-06-06 18:03:23','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',10,'Y','N','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Übergabe an abw.',0,TO_TIMESTAMP('2016-06-06 18:03:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.06.2016 18:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554411 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.06.2016 18:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_OLCand ADD HandOver_Partner_Override_ID NUMERIC(10) DEFAULT NULL 
;








-- 06.06.2016 18:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543090,0,'HandOver_Partner_Effective_ID',TO_TIMESTAMP('2016-06-06 18:04:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Übergabe an eff.','Übergabe an eff.',TO_TIMESTAMP('2016-06-06 18:04:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543090 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.06.2016 18:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554412,543090,0,30,173,540244,540208,'N','HandOver_Partner_Effective_ID','COALESCE(C_OLCand.HandOver_Partner_Override_ID,C_OLCand.HandOver_Partner_ID)',TO_TIMESTAMP('2016-06-06 18:05:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Übergabe an eff.',0,TO_TIMESTAMP('2016-06-06 18:05:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.06.2016 18:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554412 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;









-- 06.06.2016 18:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543091,0,'HandOver_Location_Override_ID',TO_TIMESTAMP('2016-06-06 18:06:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Übergabeadresse abw.','Übergabeadresse abw.',TO_TIMESTAMP('2016-06-06 18:06:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543091 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.06.2016 18:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554413,543091,0,30,159,540244,540207,'N','HandOver_Location_Override_ID',TO_TIMESTAMP('2016-06-06 18:06:38','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',10,'Y','N','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Übergabeadresse abw.',0,TO_TIMESTAMP('2016-06-06 18:06:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.06.2016 18:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554413 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.06.2016 18:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_OLCand ADD HandOver_Location_Override_ID NUMERIC(10) DEFAULT NULL 
;

-- 06.06.2016 18:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, IsAutocomplete='Y',Updated=TO_TIMESTAMP('2016-06-06 18:08:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549941
;

-- 06.06.2016 18:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2016-06-06 18:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554411
;


-- 06.06.2016 18:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543092,0,'HandOver_Location_Effective_ID',TO_TIMESTAMP('2016-06-06 18:09:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Übergabeadresse eff.','Übergabeadresse eff.',TO_TIMESTAMP('2016-06-06 18:09:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543092 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.06.2016 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554414,543092,0,30,159,540244,540207,'N','HandOver_Location_Effective_ID','COALESCE(C_OLCand.HandOver_Location_Override_ID,C_OLCand.HandOver_Location_ID)',TO_TIMESTAMP('2016-06-06 18:10:38','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Übergabeadresse eff.',0,TO_TIMESTAMP('2016-06-06 18:10:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.06.2016 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554414 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;



-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554406,556939,0,540282,TO_TIMESTAMP('2016-06-06 18:30:51','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','Y','Y','N','N','N','N','N','Lieferempfänger abw.',TO_TIMESTAMP('2016-06-06 18:30:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556939 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554407,556940,0,540282,TO_TIMESTAMP('2016-06-06 18:30:51','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','Y','Y','N','N','N','N','N','Lieferadresse abw.',TO_TIMESTAMP('2016-06-06 18:30:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556940 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554409,556941,0,540282,TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','Y','Y','N','N','N','N','N','Lieferempfänger eff.',TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556941 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554410,556942,0,540282,TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','Y','Y','N','N','N','N','N','Lieferadresse eff.',TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556942 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554411,556943,0,540282,TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','Y','Y','N','N','N','N','N','Übergabe an abw.',TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556943 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554412,556944,0,540282,TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','Y','Y','N','N','N','N','N','Übergabe an eff.',TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556944 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554413,556945,0,540282,TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','Y','Y','N','N','N','N','N','Übergabeadresse abw.',TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556945 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554414,556946,0,540282,TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','Y','Y','N','N','N','N','N','Übergabeadresse eff.',TO_TIMESTAMP('2016-06-06 18:30:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556946 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.06.2016 18:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=222, SeqNoGrid=222,Updated=TO_TIMESTAMP('2016-06-06 18:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556943
;

-- 06.06.2016 18:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=223, SeqNoGrid=223,Updated=TO_TIMESTAMP('2016-06-06 18:32:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556944
;

-- 06.06.2016 18:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=224, SeqNoGrid=224,Updated=TO_TIMESTAMP('2016-06-06 18:32:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553714
;

-- 06.06.2016 18:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=225, SeqNoGrid=225,Updated=TO_TIMESTAMP('2016-06-06 18:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556945
;

-- 06.06.2016 18:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2016-06-06 18:35:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553714
;

-- 06.06.2016 18:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=226, SeqNoGrid=226,Updated=TO_TIMESTAMP('2016-06-06 18:36:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556946
;

-- 06.06.2016 18:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=370, SeqNoGrid=370,Updated=TO_TIMESTAMP('2016-06-06 18:37:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553746
;

-- 06.06.2016 18:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=380, SeqNoGrid=380,Updated=TO_TIMESTAMP('2016-06-06 18:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556939
;

-- 06.06.2016 18:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=390, SeqNoGrid=390,Updated=TO_TIMESTAMP('2016-06-06 18:37:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556942
;

-- 06.06.2016 18:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=400,Updated=TO_TIMESTAMP('2016-06-06 18:37:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553745
;

-- 06.06.2016 18:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-06-06 18:37:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553746
;

-- 06.06.2016 18:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2016-06-06 18:37:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556939
;

-- 06.06.2016 18:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2016-06-06 18:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556942
;

-- 06.06.2016 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=370,Updated=TO_TIMESTAMP('2016-06-06 18:38:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553746
;

-- 06.06.2016 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=380,Updated=TO_TIMESTAMP('2016-06-06 18:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556939
;

-- 06.06.2016 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=390,Updated=TO_TIMESTAMP('2016-06-06 18:38:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556942
;

-- 06.06.2016 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=410,Updated=TO_TIMESTAMP('2016-06-06 18:38:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556940
;

-- 06.06.2016 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2016-06-06 18:38:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553745
;

-- 06.06.2016 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNoGrid=410,Updated=TO_TIMESTAMP('2016-06-06 18:38:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556940
;

-- 06.06.2016 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=400,Updated=TO_TIMESTAMP('2016-06-06 18:38:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553745
;

-- 06.06.2016 18:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=420, SeqNoGrid=420,Updated=TO_TIMESTAMP('2016-06-06 18:39:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556942
;

-- 06.06.2016 18:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=390, SeqNoGrid=390,Updated=TO_TIMESTAMP('2016-06-06 18:39:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556941
;

-- 06.06.2016 18:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_OLCand ADD DropShip_Location_Override_ID NUMERIC(10) DEFAULT NULL 
;

-- 06.06.2016 18:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540334,'C_BPartner_Location.C_BPartner_ID=@DropShip_BPartner_Override_ID@ AND C_BPartner_Location.IsShipTo=''Y'' AND C_BPartner_Location.IsActive=''Y''',TO_TIMESTAMP('2016-06-06 18:45:25','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','C_BPartner_Loc Ship - Drop Ship Override BPartner','S',TO_TIMESTAMP('2016-06-06 18:45:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Val_Rule_ID=540334,Updated=TO_TIMESTAMP('2016-06-06 18:46:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554407
;

-- 06.06.2016 18:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2016-06-06 18:46:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554410
;

-- 06.06.2016 18:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540335,'C_BPartner_Location_ID in (select C_BPartner_Location_ID from C_BPartner_Location l where l.IsHandOverLocation=''Y'' and (l.C_BPartner_ID=COALESCE(@HandOver_Partner_Override_ID@,@HandOver_Partner_ID@) OR l.C_BPartner_ID = @C_BPartner_ID@ ))',TO_TIMESTAMP('2016-06-06 18:50:14','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','C_BPartnerLocation_isHandover_Override','S',TO_TIMESTAMP('2016-06-06 18:50:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.06.2016 18:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540335,Updated=TO_TIMESTAMP('2016-06-06 18:50:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554413
;

-- 06.06.2016 18:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2016-06-06 18:50:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554414
;

-- 06.06.2016 18:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner_Location.C_BPartner_ID=COALESCE(@DropShip_BPartner_Override_ID@, @DropShip_BPartner_ID@) AND C_BPartner_Location.IsShipTo=''Y'' AND C_BPartner_Location.IsActive=''Y''', EntityType='de.metas.ordercandidate',Updated=TO_TIMESTAMP('2016-06-06 18:51:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540334
;

-- 06.06.2016 18:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET EntityType='de.metas.ordercandidate',Updated=TO_TIMESTAMP('2016-06-06 18:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540335
;

-- 06.06.2016 18:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=159,Updated=TO_TIMESTAMP('2016-06-06 18:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554414
;

-- 07.06.2016 10:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=159,Updated=TO_TIMESTAMP('2016-06-07 10:14:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554410
;

-- 07.06.2016 10:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner.C_BPartner_ID in (select C_BPartnerRelation_ID from C_BP_Relation r where r.C_BPartner_ID=@C_BPartner_ID@ and r.IsHandOverLocation =''Y'')',Updated=TO_TIMESTAMP('2016-06-07 10:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540208
;

-- 07.06.2016 10:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner_Location.C_BPartner_Location_ID in (select C_BPartner_Location_ID from C_BPartner_Location l where l.IsHandOverLocation=''Y'' and (l.C_BPartner_ID = @HandOver_Partner_Override_ID@ or l.C_BPartner_ID = @HandOver_Partner_ID@ ))',Updated=TO_TIMESTAMP('2016-06-07 10:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540335
;

-- 07.06.2016 11:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner_Location.IsHandoverLocation = ''Y''  AND (C_BPartner_Location.C_BPartner_ID = @HandOver_Partner_Override_ID@ or C_BPartner_Location.C_BPartner_ID = @HandOver_Partner_ID@ )',Updated=TO_TIMESTAMP('2016-06-07 11:01:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540335
;

