-- 2018-02-23T12:47:25.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AllowZoomTo='N',Updated=TO_TIMESTAMP('2018-02-23 12:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557365
;

-- 2018-02-23T12:47:34.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AllowZoomTo='N',Updated=TO_TIMESTAMP('2018-02-23 12:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557516
;

-- 2018-02-23T12:48:56.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AllowZoomTo='N',Updated=TO_TIMESTAMP('2018-02-23 12:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556520
;

-- 2018-02-23T12:49:08.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AllowZoomTo='Y',Updated=TO_TIMESTAMP('2018-02-23 12:49:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556534
;


CREATE INDEX IF NOT EXISTS md_candidate_c_order_id
  ON public.md_candidate
  USING btree
  (c_order_id);
COMMENT ON INDEX public.md_candidate_c_order_id
  IS 'this index supports zooming from order to material dispo';

CREATE INDEX IF NOT EXISTS md_candidate_m_shipmentschedule_id
  ON public.md_candidate
  USING btree
  (m_shipmentschedule_id);
COMMENT ON INDEX public.md_candidate_c_order_id
  IS 'this index supports zooming from shipment dispo to material dispo';

 CREATE INDEX IF NOT EXISTS md_candidate_m_forecast_id
  ON public.md_candidate
  USING btree
  (m_forecast_id);
 COMMENT ON INDEX public.md_candidate_c_order_id
  IS 'this index supports zooming from forecast to material dispo';
 