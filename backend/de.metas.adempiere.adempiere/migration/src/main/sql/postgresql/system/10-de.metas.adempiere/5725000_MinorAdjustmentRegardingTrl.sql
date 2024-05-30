-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spaltenname
-- Column: ModCntr_Type.ColumnName
-- 2024-05-30T05:37:02.584Z
UPDATE AD_Field SET EntityType='de.metas.contracts', IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2024-05-30 08:37:02.583','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728716
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spaltenname
-- Column: ModCntr_Type.ColumnName
-- 2024-05-30T05:37:05.558Z
UPDATE AD_Field SET IsAlwaysUpdateable='N',Updated=TO_TIMESTAMP('2024-05-30 08:37:05.558','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728716
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spaltenname
-- Column: ModCntr_Type.ColumnName
-- 2024-05-30T05:39:04.344Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-05-30 08:39:04.344','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728716
;


UPDATE modcntr_type
set value ='Protein - Average Added Value On Shipped Quantity',
    name = 'Protein - Durchschnittliche gelieferte Menge',
    description= 'Erstellt die Lieferlogs inklusive Protein für Protein Rechnungspositionen und Details in der Schlusszahlung'
where modcntr_type_id=540025;



UPDATE modcntr_type
set value ='HL - Average Added Value On Shipped Quantity',
    name = 'HL - Durchschnittliche gelieferte Menge',
    description= 'Erstellt die Lieferlogs inklusive HL für HL Rechnungspositionen und Details in der Schlusszahlung'
where modcntr_type_id=540024;

-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-30T06:33:16.975Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-05-30 09:33:16.975','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588286
;

-- 2024-05-30T06:33:18.732Z
INSERT INTO t_alter_column values('m_productprice','ScalePriceQuantityFrom','VARCHAR(5)',null,'Q')
;

-- 2024-05-30T06:33:18.745Z
UPDATE M_ProductPrice SET ScalePriceQuantityFrom='Q' WHERE ScalePriceQuantityFrom IS NULL
;

-- 2024-05-30T06:33:18.750Z
INSERT INTO t_alter_column values('m_productprice','ScalePriceQuantityFrom',null,'NOT NULL',null)
;

