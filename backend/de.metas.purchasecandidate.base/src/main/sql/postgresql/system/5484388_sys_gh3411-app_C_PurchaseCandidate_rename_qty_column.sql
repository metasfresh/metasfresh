
--
-- tweak C_PurchaseCandidate table
--

-- 2018-01-31T17:15:56.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543847,0,'QtyToPurchase',TO_TIMESTAMP('2018-01-31 17:15:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','Bestellmenge','Bestellmenge',TO_TIMESTAMP('2018-01-31 17:15:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-31T17:15:56.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543847 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-31T17:16:11.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543847, ColumnName='QtyToPurchase', Description=NULL, Help=NULL, Name='Bestellmenge',Updated=TO_TIMESTAMP('2018-01-31 17:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557860
;

-- 2018-01-31T17:16:11.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bestellmenge', Description=NULL, Help=NULL WHERE AD_Column_ID=557860
;

ALTER TABLE C_PurchaseCandidate RENAME COLUMN QtyRequiered TO QtyToPurchase;

