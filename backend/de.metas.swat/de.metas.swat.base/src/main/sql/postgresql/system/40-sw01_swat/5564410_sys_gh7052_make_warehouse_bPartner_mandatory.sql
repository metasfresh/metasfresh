
-- fill in m_warehouse.c_bpartner_id based on c_bpartner_location_id

UPDATE 
    m_warehouse
SET 
    c_bpartner_id = b_loc.c_bpartner_id
FROM 
     m_warehouse w
         INNER JOIN c_bpartner_location b_loc on w.c_bpartner_location_id = b_loc.c_bpartner_location_id;
		 
-- 2020-07-29T09:20:22.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-07-29 12:20:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540343
;

-- 2020-07-29T09:20:24.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_warehouse','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2020-07-29T09:20:24.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_warehouse','C_BPartner_ID',null,'NOT NULL',null)
;

