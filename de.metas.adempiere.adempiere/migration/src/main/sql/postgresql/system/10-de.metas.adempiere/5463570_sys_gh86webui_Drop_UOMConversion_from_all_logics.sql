/*
select w.Name, tt.Name, f.Name, t.TableName, c.ColumnName, f.DisplayLogic, f.IsDisplayed
from AD_Field f
inner join AD_Tab tt on (tt.AD_Tab_ID=f.AD_Tab_ID)
inner join AD_Window w on (w.AD_Window_ID=tt.AD_Window_ID)
inner join AD_Column c on (c.AD_Column_ID=f.AD_Column_ID)
inner join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
where f.displayLogic like '%UOMConversion%'
order by t.TableName, c.ColumnName, f.AD_FIeld_ID
*/
/*
"Rechnung";"Rechnungsposition";"Berechn. Menge";"C_InvoiceLine";"QtyInvoiced";"@UOMConversion@=Y | @Processed@=Y";"N"
"Auftrag";"Auftragsposition";"Bestellte Menge";"C_OrderLine";"QtyOrdered";"@UOMConversion@=Y | @Processed@='Y'";"N"
"B2B Auftrag  (internal)";"Auftragsposition";"Bestellte Menge";"C_OrderLine";"QtyOrdered";"@IsComment@=N & @UOMConversion@=Y | @Processed@='Y'";"N"
"Lieferung";"Lieferposition";"Bewegungs-Menge";"M_InOutLine";"MovementQty";"@UOMConversion@=Y | @Processed@=Y";"N"
"Wareneingang";"Wareneingangsposition";"Bewegungs-Menge";"M_InOutLine";"MovementQty";"@UOMConversion@=Y | @Processed@=Y";"N"
"Kundenrücklieferung";"Position";"Bewegungs-Menge";"M_InOutLine";"MovementQty";"@UOMConversion@=Y | @Processed@=Y";"Y"
"Lieferantenrücklieferung";"Positionen";"Bewegungs-Menge";"M_InOutLine";"MovementQty";"@UOMConversion@=Y | @Processed@=Y";"Y"
"Leergut Rückgabe";"Positionen";"Bewegungs-Menge";"M_InOutLine";"MovementQty";"@UOMConversion@=Y | @Processed@=Y";"Y"
"Leergut Rücknahme";"Position";"Bewegungs-Menge";"M_InOutLine";"MovementQty";"@UOMConversion@=Y | @Processed@=Y";"Y"
*/

-- 2017-05-26T17:00:54.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-05-26 17:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1130
;

-- 2017-05-26T17:01:27.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsComment@=N',Updated=TO_TIMESTAMP('2017-05-26 17:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=544962
;

-- 2017-05-26T17:03:09.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-05-26 17:03:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2999
;

-- 2017-05-26T17:05:23.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-05-26 17:05:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2719
;

-- 2017-05-26T17:05:55.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-05-26 17:05:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3516
;

-- 2017-05-26T17:06:46.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-05-26 17:06:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57771
;

-- 2017-05-26T17:07:14.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-05-26 17:07:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57910
;

-- 2017-05-26T17:07:36.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-05-26 17:07:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557638
;

-- 2017-05-26T17:08:18.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-05-26 17:08:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557738
;

