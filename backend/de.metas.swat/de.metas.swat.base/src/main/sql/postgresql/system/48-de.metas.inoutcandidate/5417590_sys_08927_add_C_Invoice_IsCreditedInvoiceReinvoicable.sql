
-- 04.06.2015 09:38
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542843,0,'IsCreditedInvoiceReinvoicable',TO_TIMESTAMP('2015-06-04 09:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Wenn dieser Wert "Ja" ist und diese Gutschrift zu einer Rechnung gehört, dann sind die betreffenden Rechnungskandidaten erneut abrechenbar, sofern "komplett abgerechnet abw." nicht auf "Ja" gesetzt wurde.','de.metas.invoicecandidate','Y','Gutgeschriebener Betrag erneut abrechenbar','Gutgeschriebener Betrag erneut abrechenbar',TO_TIMESTAMP('2015-06-04 09:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.06.2015 09:38
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542843 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 04.06.2015 09:38
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552530,542843,0,20,318,'N','IsCreditedInvoiceReinvoicable',TO_TIMESTAMP('2015-06-04 09:38:47','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn dieser Wert "Ja" ist und diese Gutschrift zu einer Rechnung gehört, dann sind die betreffenden Rechnungskandidaten erneut abrechenbar, sofern "komplett abgerechnet abw." nicht auf "Ja" gesetzt wurde.','de.metas.invoicecandidate',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Gutgeschriebener Betrag erneut abrechenbar',0,TO_TIMESTAMP('2015-06-04 09:38:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.06.2015 09:38
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552530 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.06.2015 09:38
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2015-06-04 09:38:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552530
;

-- 04.06.2015 09:40
-- URL zum Konzept
ALTER TABLE C_Invoice ADD IsCreditedInvoiceReinvoicable CHAR(1) DEFAULT 'N' CHECK (IsCreditedInvoiceReinvoicable IN ('Y','N')) NOT NULL
;

-- 04.06.2015 10:04
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552530,556177,0,263,0,TO_TIMESTAMP('2015-06-04 10:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Wenn dieser Wert "Ja" ist und diese Gutschrift zu einer Rechnung gehört, dann sind die betreffenden Rechnungskandidaten erneut abrechenbar, sofern "komplett abgerechnet abw." nicht auf "Ja" gesetzt wurde.',0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','N','Gutgeschriebener Betrag erneut abrechenbar',202,222,0,TO_TIMESTAMP('2015-06-04 10:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.06.2015 10:04
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556177 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

