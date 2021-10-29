-- 2021-10-29T06:30:35.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580045) 
;

-- 2021-10-29T06:30:38.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_Reservation','ALTER TABLE public.M_HU_Reservation ADD COLUMN M_Picking_Job_Step_ID NUMERIC(10)')
;

-- 2021-10-29T06:30:38.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_HU_Reservation ADD CONSTRAINT MPickingJobStep_MHUReservation FOREIGN KEY (M_Picking_Job_Step_ID) REFERENCES public.M_Picking_Job_Step DEFERRABLE INITIALLY DEFERRED
;

-- 2021-10-29T06:31:43.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-10-29 09:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578100
;

-- 2021-10-29T06:31:47.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2021-10-29 09:31:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578100
;

