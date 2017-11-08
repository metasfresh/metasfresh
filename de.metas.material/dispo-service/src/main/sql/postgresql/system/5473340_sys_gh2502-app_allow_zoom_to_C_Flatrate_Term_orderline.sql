--
-- Allow zoom from an order line to its flatrate-term
--
-- 2017-10-02T11:22:08.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AllowZoomTo='Y',Updated=TO_TIMESTAMP('2017-10-02 11:22:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547282
;

