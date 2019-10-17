-- 2018-10-30T14:18:10.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,565649,0,'QtyPickedNotDelivered',TO_TIMESTAMP('2018-10-30 14:18:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Qty Picked Not (yet) Delivered','Qty Picked Not Delivered',TO_TIMESTAMP('2018-10-30 14:18:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T14:18:10.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=565649 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-30T14:18:58.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,565650,0,'QtyPickedAndDelivered',TO_TIMESTAMP('2018-10-30 14:18:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Qty Picked And Delivered','Qty Picked And Delivered',TO_TIMESTAMP('2018-10-30 14:18:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T14:18:58.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=565650 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-30T14:20:08.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Menge kommissioniert (noch) nicht geliefert', PrintName='Menge kommissioniert (noch) nicht geliefert',Updated=TO_TIMESTAMP('2018-10-30 14:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=565649
;

-- 2018-10-30T14:20:08.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyPickedNotDelivered', Name='Menge kommissioniert (noch) nicht geliefert', Description=NULL, Help=NULL WHERE AD_Element_ID=565649
;

-- 2018-10-30T14:20:08.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPickedNotDelivered', Name='Menge kommissioniert (noch) nicht geliefert', Description=NULL, Help=NULL, AD_Element_ID=565649 WHERE UPPER(ColumnName)='QTYPICKEDNOTDELIVERED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-30T14:20:08.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPickedNotDelivered', Name='Menge kommissioniert (noch) nicht geliefert', Description=NULL, Help=NULL WHERE AD_Element_ID=565649 AND IsCentrallyMaintained='Y'
;

-- 2018-10-30T14:20:08.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge kommissioniert (noch) nicht geliefert', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=565649) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 565649)
;

-- 2018-10-30T14:20:08.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Menge kommissioniert (noch) nicht geliefert', Name='Menge kommissioniert (noch) nicht geliefert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=565649)
;

-- 2018-10-30T14:20:18.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-30 14:20:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Menge kommissioniert (noch) nicht geliefert',PrintName='Menge kommissioniert (noch) nicht geliefert' WHERE AD_Element_ID=565649 AND AD_Language='de_CH'
;

-- 2018-10-30T14:20:18.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(565649,'de_CH') 
;

-- 2018-10-30T14:20:34.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Menge kommissioniert und geliefert', PrintName='Menge kommissioniert und geliefert',Updated=TO_TIMESTAMP('2018-10-30 14:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=565650
;

-- 2018-10-30T14:20:34.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyPickedAndDelivered', Name='Menge kommissioniert und geliefert', Description=NULL, Help=NULL WHERE AD_Element_ID=565650
;

-- 2018-10-30T14:20:34.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPickedAndDelivered', Name='Menge kommissioniert und geliefert', Description=NULL, Help=NULL, AD_Element_ID=565650 WHERE UPPER(ColumnName)='QTYPICKEDANDDELIVERED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-30T14:20:34.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPickedAndDelivered', Name='Menge kommissioniert und geliefert', Description=NULL, Help=NULL WHERE AD_Element_ID=565650 AND IsCentrallyMaintained='Y'
;

-- 2018-10-30T14:20:34.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge kommissioniert und geliefert', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=565650) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 565650)
;

-- 2018-10-30T14:20:34.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Menge kommissioniert und geliefert', Name='Menge kommissioniert und geliefert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=565650)
;

-- 2018-10-30T14:20:41.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-30 14:20:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Menge kommissioniert und geliefert',PrintName='Menge kommissioniert und geliefert' WHERE AD_Element_ID=565650 AND AD_Language='de_CH'
;

-- 2018-10-30T14:20:41.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(565650,'de_CH') 
;

-- 2018-10-30T14:20:49.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-30 14:20:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=565650 AND AD_Language='en_US'
;

-- 2018-10-30T14:20:49.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(565650,'en_US') 
;

-- 2018-10-30T14:20:58.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-30 14:20:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=565649 AND AD_Language='en_US'
;

-- 2018-10-30T14:20:58.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(565649,'en_US') 
;

-- 2018-10-30T14:21:23.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=565649, ColumnName='QtyPickedNotDelivered', Description=NULL, FieldLength=10, Help=NULL, Name='Menge kommissioniert (noch) nicht geliefert',Updated=TO_TIMESTAMP('2018-10-30 14:21:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559749
;

-- 2018-10-30T14:21:23.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge kommissioniert (noch) nicht geliefert', Description=NULL, Help=NULL WHERE AD_Column_ID=559749
;

-- 2018-10-30T14:21:41.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,563392,565650,0,29,540823,'QtyPickedAndDelivered',TO_TIMESTAMP('2018-10-30 14:21:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate',131089,'Y','Y','N','N','N','N','N','N','N','N','N','Menge kommissioniert und geliefert',TO_TIMESTAMP('2018-10-30 14:21:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-10-30T14:21:41.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563392 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-30T14:22:05.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560511,569769,0,540830,TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition',10,'de.metas.inoutcandidate','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','N','N','N','N','N','Auftragsposition',TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T14:22:05.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569769 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-30T14:22:05.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560728,569770,0,540830,TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',10,'de.metas.inoutcandidate','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','N','N','N','N','N','Merkmale',TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T14:22:05.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569770 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-30T14:22:05.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,561007,569771,0,540830,TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Warehouse Type',TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T14:22:05.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-30T14:22:05.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,561013,569772,0,540830,TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge',131089,'de.metas.inoutcandidate','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','N','N','N','N','N','N','Gelieferte Menge',TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T14:22:05.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569772 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-30T14:22:05.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,561014,569773,0,540830,TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100,'Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren',131089,'de.metas.inoutcandidate','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.','Y','N','N','N','N','N','N','N','Zeilennetto',TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T14:22:05.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569773 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-30T14:22:05.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,561015,569774,0,540830,TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',10,'de.metas.inoutcandidate','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T14:22:05.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569774 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-30T14:22:06.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563245,569775,0,540830,TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Locked By',TO_TIMESTAMP('2018-10-30 14:22:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T14:22:06.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569775 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-30T14:22:06.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563392,569776,0,540830,TO_TIMESTAMP('2018-10-30 14:22:06','YYYY-MM-DD HH24:MI:SS'),100,131089,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Menge kommissioniert und geliefert',TO_TIMESTAMP('2018-10-30 14:22:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T14:22:06.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569776 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-30T15:11:04.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,572439,0,'QtyPickedOrDelivered',TO_TIMESTAMP('2018-10-30 15:11:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Menge kommissioniert oder geliefert','Menge kommissioniert oder geliefert',TO_TIMESTAMP('2018-10-30 15:11:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T15:11:04.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=572439 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-30T15:11:09.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-30 15:11:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=572439 AND AD_Language='de_CH'
;

-- 2018-10-30T15:11:09.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572439,'de_CH') 
;

-- 2018-10-30T15:11:26.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-30 15:11:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty Picked or Delivered',PrintName='Qty Picked or Delivered' WHERE AD_Element_ID=572439 AND AD_Language='en_US'
;

-- 2018-10-30T15:11:26.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572439,'en_US') 
;

-- 2018-10-30T15:11:40.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,563395,572439,0,29,540823,'QtyPickedOrDelivered',TO_TIMESTAMP('2018-10-30 15:11:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate',131089,'Y','Y','N','N','N','N','N','N','N','N','N','Menge kommissioniert oder geliefert',TO_TIMESTAMP('2018-10-30 15:11:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-10-30T15:11:40.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563395 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-30T15:39:42.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563395,569779,0,540830,TO_TIMESTAMP('2018-10-30 15:39:42','YYYY-MM-DD HH24:MI:SS'),100,131089,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Menge kommissioniert oder geliefert',TO_TIMESTAMP('2018-10-30 15:39:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T15:39:42.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569779 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-30T15:41:24.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=569779,Updated=TO_TIMESTAMP('2018-10-30 15:41:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548008
;

