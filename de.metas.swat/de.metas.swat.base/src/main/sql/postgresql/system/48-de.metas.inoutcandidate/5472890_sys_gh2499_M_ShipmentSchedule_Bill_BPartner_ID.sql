-- 2017-09-26T20:35:33.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=2039, AD_Reference_Value_ID=138, ColumnName='Bill_BPartner_ID', Description='Geschäftspartners für die Rechnungsstellung', Help='Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt', Name='Rechnungspartner',Updated=TO_TIMESTAMP('2017-09-26 20:35:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557312
;

-- 2017-09-26T20:35:33.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungspartner', Description='Geschäftspartners für die Rechnungsstellung', Help='Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt' WHERE AD_Column_ID=557312 AND IsCentrallyMaintained='Y'
;

alter table M_Shipment_Constraint rename C_BPartner_ID to Bill_BPartner_ID;

