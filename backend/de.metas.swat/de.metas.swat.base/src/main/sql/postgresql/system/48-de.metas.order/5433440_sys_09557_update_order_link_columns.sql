
-- 16.11.2015 07:08
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=540226, EntityType='de.metas.order',Updated=TO_TIMESTAMP('2015-11-16 07:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=55323
;

-- 16.11.2015 07:09
-- URL zum Konzept
UPDATE AD_Element SET Description='Mit diesem Feld referenziert eine Auftragsposition die ihr zugehörige Bestellposition.', EntityType='de.metas.order', Name='Zugehörige Bestellposition', PrintName='Zugehörige Bestellposition',Updated=TO_TIMESTAMP('2015-11-16 07:09:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53463
;

-- 16.11.2015 07:09
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=53463
;

-- 16.11.2015 07:09
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Link_OrderLine_ID', Name='Zugehörige Bestellposition', Description='Mit diesem Feld referenziert eine Auftragsposition die ihr zugehörige Bestellposition.', Help=NULL WHERE AD_Element_ID=53463
;

-- 16.11.2015 07:09
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Link_OrderLine_ID', Name='Zugehörige Bestellposition', Description='Mit diesem Feld referenziert eine Auftragsposition die ihr zugehörige Bestellposition.', Help=NULL, AD_Element_ID=53463 WHERE UPPER(ColumnName)='LINK_ORDERLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 16.11.2015 07:09
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Link_OrderLine_ID', Name='Zugehörige Bestellposition', Description='Mit diesem Feld referenziert eine Auftragsposition die ihr zugehörige Bestellposition.', Help=NULL WHERE AD_Element_ID=53463 AND IsCentrallyMaintained='Y'
;

-- 16.11.2015 07:09
-- URL zum Konzept
UPDATE AD_Field SET Name='Zugehörige Bestellposition', Description='Mit diesem Feld referenziert eine Auftragsposition die ihr zugehörige Bestellposition.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53463) AND IsCentrallyMaintained='Y'
;

-- 16.11.2015 07:09
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zugehörige Bestellposition', Name='Zugehörige Bestellposition' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53463)
;

-- 16.11.2015 07:16
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,55322,556413,0,186,0,TO_TIMESTAMP('2015-11-16 07:16:27','YYYY-MM-DD HH24:MI:SS'),100,'This field links a sales order to the purchase order that is generated from it.',0,'de.metas.order',0,'Y','Y','Y','Y','N','N','N','N','N','Linked Order',610,590,0,TO_TIMESTAMP('2015-11-16 07:16:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.11.2015 07:16
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556413 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 16.11.2015 07:16
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-11-16 07:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556413
;

-- 16.11.2015 07:17
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.order',Updated=TO_TIMESTAMP('2015-11-16 07:17:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=55322
;

-- 16.11.2015 07:18
-- URL zum Konzept
UPDATE AD_Element SET Description='Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Bestellposition referenzieren.',Updated=TO_TIMESTAMP('2015-11-16 07:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53463
;

-- 16.11.2015 07:18
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=53463
;

-- 16.11.2015 07:18
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Link_OrderLine_ID', Name='Zugehörige Bestellposition', Description='Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Bestellposition referenzieren.', Help=NULL WHERE AD_Element_ID=53463
;

-- 16.11.2015 07:18
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Link_OrderLine_ID', Name='Zugehörige Bestellposition', Description='Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Bestellposition referenzieren.', Help=NULL, AD_Element_ID=53463 WHERE UPPER(ColumnName)='LINK_ORDERLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 16.11.2015 07:18
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Link_OrderLine_ID', Name='Zugehörige Bestellposition', Description='Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Bestellposition referenzieren.', Help=NULL WHERE AD_Element_ID=53463 AND IsCentrallyMaintained='Y'
;

-- 16.11.2015 07:18
-- URL zum Konzept
UPDATE AD_Field SET Name='Zugehörige Bestellposition', Description='Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Bestellposition referenzieren.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53463) AND IsCentrallyMaintained='Y'
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Element SET Description='Mit diesem Feld kann ein Auftrag die ihr zugehörige Bestellung referenzieren.', EntityType='de.metas.order', Name='Zugehörige Bestellung', PrintName='Zugehörige Bestellung',Updated=TO_TIMESTAMP('2015-11-16 07:19:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53462
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=53462
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Link_Order_ID', Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihr zugehörige Bestellung referenzieren.', Help=NULL WHERE AD_Element_ID=53462
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Link_Order_ID', Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihr zugehörige Bestellung referenzieren.', Help=NULL, AD_Element_ID=53462 WHERE UPPER(ColumnName)='LINK_ORDER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Link_Order_ID', Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihr zugehörige Bestellung referenzieren.', Help=NULL WHERE AD_Element_ID=53462 AND IsCentrallyMaintained='Y'
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Field SET Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihr zugehörige Bestellung referenzieren.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53462) AND IsCentrallyMaintained='Y'
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zugehörige Bestellung', Name='Zugehörige Bestellung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53462)
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Element SET Description='Mit diesem Feld kann ein Auftrag die ihn zugehörige Bestellung referenzieren.',Updated=TO_TIMESTAMP('2015-11-16 07:19:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53462
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=53462
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Link_Order_ID', Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihn zugehörige Bestellung referenzieren.', Help=NULL WHERE AD_Element_ID=53462
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Link_Order_ID', Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihn zugehörige Bestellung referenzieren.', Help=NULL, AD_Element_ID=53462 WHERE UPPER(ColumnName)='LINK_ORDER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Link_Order_ID', Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihn zugehörige Bestellung referenzieren.', Help=NULL WHERE AD_Element_ID=53462 AND IsCentrallyMaintained='Y'
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Field SET Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihn zugehörige Bestellung referenzieren.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53462) AND IsCentrallyMaintained='Y'
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Element SET Description='Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.',Updated=TO_TIMESTAMP('2015-11-16 07:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53462
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=53462
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Link_Order_ID', Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.', Help=NULL WHERE AD_Element_ID=53462
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Link_Order_ID', Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.', Help=NULL, AD_Element_ID=53462 WHERE UPPER(ColumnName)='LINK_ORDER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Link_Order_ID', Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.', Help=NULL WHERE AD_Element_ID=53462 AND IsCentrallyMaintained='Y'
;

-- 16.11.2015 07:19
-- URL zum Konzept
UPDATE AD_Field SET Name='Zugehörige Bestellung', Description='Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53462) AND IsCentrallyMaintained='Y'
;

