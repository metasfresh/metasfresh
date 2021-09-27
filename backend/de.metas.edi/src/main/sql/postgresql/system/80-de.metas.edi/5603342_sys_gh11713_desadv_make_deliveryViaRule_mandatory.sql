-- 2021-09-11T08:36:38.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', TechnicalNote='It''s mandatory because C_Order.DeliveryRule is also mandatory; therefore we will always have a value to put in.',Updated=TO_TIMESTAMP('2021-09-11 10:36:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=576641
;

-- 2021-09-11T08:36:40.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','DeliveryViaRule','VARCHAR(2)',null,null)
;

-- 2021-09-11T08:36:40.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','DeliveryViaRule',null,'NOT NULL',null)
;

