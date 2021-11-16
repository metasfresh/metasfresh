-- 2021-10-21T10:31:30.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', IsUpdateable='Y',Updated=TO_TIMESTAMP('2021-10-21 13:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552808
;

-- 2021-10-21T10:31:31.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','M_HU_ID','NUMERIC(10)',null,null)
;

-- 2021-10-21T10:31:31.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','M_HU_ID',null,'NULL',null)
;

-- 2021-10-21T10:31:42.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-10-21 13:31:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577828
;

-- 2021-10-21T10:31:46.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','PickFrom_HU_ID','NUMERIC(10)',null,null)
;

-- 2021-10-21T10:31:46.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','PickFrom_HU_ID',null,'NOT NULL',null)
;

-- 2021-10-21T10:31:56.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-10-21 13:31:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577822
;

-- 2021-10-21T10:31:59.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2021-10-21T10:31:59.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','C_UOM_ID',null,'NOT NULL',null)
;

-- 2021-10-21T10:33:53.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-10-21 13:33:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577821
;

-- 2021-10-21T10:33:53.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','DD_Order_ID','NUMERIC(10)',null,null)
;

-- 2021-10-21T10:33:53.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','DD_Order_ID',null,'NOT NULL',null)
;

-- 2021-10-21T10:34:16.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='0', IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-10-21 13:34:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577823
;

-- 2021-10-21T10:34:18.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','QtyToPick','NUMERIC',null,'0')
;

-- 2021-10-21T10:34:18.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE DD_OrderLine_HU_Candidate SET QtyToPick=0 WHERE QtyToPick IS NULL
;

-- 2021-10-21T10:34:18.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','QtyToPick',null,'NOT NULL',null)
;

