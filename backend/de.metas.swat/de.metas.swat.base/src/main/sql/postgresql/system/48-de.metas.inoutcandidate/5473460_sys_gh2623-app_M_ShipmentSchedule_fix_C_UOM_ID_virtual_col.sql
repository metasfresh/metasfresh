
--
-- rationale: this is also the UOM which the BL works with
--
-- 2017-10-02T17:40:38.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( select p.C_UOM_ID from M_Product p where p.M_Product_ID = M_ShipmentSchedule.M_Product_ID)',Updated=TO_TIMESTAMP('2017-10-02 17:40:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551099
;
