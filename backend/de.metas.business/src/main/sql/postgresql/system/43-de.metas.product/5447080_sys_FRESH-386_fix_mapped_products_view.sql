
-- Jun 13, 2016 1:24 PM
-- URL zum Konzept
UPDATE AD_Element SET Name='Zugeordnetes Produkt', PrintName='Zugeordnetes Produkt',Updated=TO_TIMESTAMP('2016-06-13 13:24:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542963
;

-- Jun 13, 2016 1:24 PM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542963
;

-- Jun 13, 2016 1:24 PM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_Product_Mapped_V_ID', Name='Zugeordnetes Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID=542963
;

-- Jun 13, 2016 1:24 PM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_Mapped_V_ID', Name='Zugeordnetes Produkt', Description=NULL, Help=NULL, AD_Element_ID=542963 WHERE UPPER(ColumnName)='M_PRODUCT_MAPPED_V_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Jun 13, 2016 1:24 PM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_Mapped_V_ID', Name='Zugeordnetes Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID=542963 AND IsCentrallyMaintained='Y'
;

-- Jun 13, 2016 1:24 PM
-- URL zum Konzept
UPDATE AD_Field SET Name='Zugeordnetes Produkt', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542963) AND IsCentrallyMaintained='Y'
;

-- Jun 13, 2016 1:24 PM
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zugeordnetes Produkt', Name='Zugeordnetes Produkt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542963)
;

-- Jun 13, 2016 1:25 PM
-- URL zum Konzept
UPDATE AD_Element SET Name='Produktzuordnung-Ansicht', PrintName='Produktzuordnung-Ansicht',Updated=TO_TIMESTAMP('2016-06-13 13:25:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542963
;

-- Jun 13, 2016 1:25 PM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542963
;

-- Jun 13, 2016 1:25 PM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_Product_Mapped_V_ID', Name='Produktzuordnung-Ansicht', Description=NULL, Help=NULL WHERE AD_Element_ID=542963
;

-- Jun 13, 2016 1:25 PM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_Mapped_V_ID', Name='Produktzuordnung-Ansicht', Description=NULL, Help=NULL, AD_Element_ID=542963 WHERE UPPER(ColumnName)='M_PRODUCT_MAPPED_V_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Jun 13, 2016 1:25 PM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_Mapped_V_ID', Name='Produktzuordnung-Ansicht', Description=NULL, Help=NULL WHERE AD_Element_ID=542963 AND IsCentrallyMaintained='Y'
;

-- Jun 13, 2016 1:25 PM
-- URL zum Konzept
UPDATE AD_Field SET Name='Produktzuordnung-Ansicht', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542963) AND IsCentrallyMaintained='Y'
;

-- Jun 13, 2016 1:25 PM
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Produktzuordnung-Ansicht', Name='Produktzuordnung-Ansicht' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542963)
;

-- Jun 13, 2016 1:26 PM
-- URL zum Konzept
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-06-13 13:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=553168
;

-- Jun 13, 2016 1:27 PM
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=553168, Parent_Column_ID=552999,Updated=TO_TIMESTAMP('2016-06-13 13:27:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- Jun 13, 2016 1:35 PM
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.product', IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-06-13 13:35:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556617
;

-- clean up old never used inactive tab
-- Jun 13, 2016 1:36 PM
-- URL zum Konzept
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=540721
;

-- Jun 13, 2016 1:36 PM
-- URL zum Konzept
DELETE FROM AD_Tab WHERE AD_Tab_ID=540721
;

-- update columns and elements that somehow ended up with AD_Client_ID=1000000
UPDATE AD_Column SET AD_Client_ID=0, Updated=now(), UpdatedBy=99 WHERE AD_Table_ID=540704 AND  AD_Client_ID!=0;
UPDATE AD_Element SET AD_Client_ID=0, Updated=now(), UpdatedBy=99 WHERE AD_Element_ID IN (SELECT AD_Element_ID from AD_Column WHERE AD_Table_ID=540704) AND AD_Client_ID!=0;

--
-- DDL, fix the view itself
--

DROP VIEW If EXISTS M_Product_Mapped_V;
CREATE VIEW M_Product_Mapped_V AS
SELECT 
DISTINCT
	p.M_Product_Mapping_ID,
	p2.M_Product_ID,
	p2.M_Product_ID AS M_Product_Mapped_V_ID,
	p2.AD_Client_ID, 
	p2.AD_Org_ID,
	p2.Created,
	p2.CreatedBy,
	p2.IsActive,
	p2.Updated,
	p2.UpdatedBy
FROM 
	M_Product p 
	JOIN M_Product p2 ON p2.M_Product_Mapping_ID=p.M_Product_Mapping_ID
	
	/* only joining M_Product_Mapping because we want to exclude inactive mappings */
	JOIN M_Product_Mapping m ON m.M_Product_Mapping_ID=p.M_Product_Mapping_ID 
WHERE true
	AND m.IsActive='Y';
