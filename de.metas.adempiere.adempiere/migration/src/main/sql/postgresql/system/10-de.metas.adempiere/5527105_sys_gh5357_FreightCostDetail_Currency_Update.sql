

-- 2019-07-15T17:29:30.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-07-15 17:29:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568420
;





UPDATE M_FreightCostDetail d
SET C_Currency_ID = coalesce((select c.C_Currency_ID from C_Country c where c.C_Country_ID = d.C_Country_Id), 102);




-- 2019-07-15T17:56:13.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_freightcostdetail','C_Currency_ID','NUMERIC(10)',null,null)
;

-- 2019-07-15T17:56:13.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_freightcostdetail','C_Currency_ID',null,'NOT NULL',null)
;

